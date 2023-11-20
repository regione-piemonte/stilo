/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.DSOperationType;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean.Direction;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.SingletonDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.exception.ExceptionBean;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.module.layout.shared.util.ExceptionUtil;

/**
 * Implementa la comunincazione tra client e server tramite l'utilizzo dei Datasource di smartgwt
 * @author michele
 *
 */
@Controller
@RequestMapping("/datasourceservicerichieste")
public class DataSourceServiceRichieste extends AbstractRootService {

	private static Logger mLogger = Logger.getLogger(DataSourceServiceRichieste.class);
	
	private GsonBuilder builder =  GsonBuilderFactory.getIstance();

	@RequestMapping(value="/all", method=RequestMethod.POST)
	@ResponseBody	
	public String all(@RequestBody String json,HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception { 

		Gson gson = builder.create();
		ResponseBean response = new ResponseBean();
		OptionFetchDataSource datasource = null;

		final boolean flagCifratura = getFlagCifratura(session);
		
		try {
//			mLogger.debug("=========================================");
//            mLogger.debug("RICEVUTA RICHIESTA: \n"+json);
    		final String jsonDecrypted = decryptIfNeeded(json, flagCifratura);
//            mLogger.debug("=========================================");
			//Converto il json in RequestBean
			RequestBean request = gson.fromJson(jsonDecrypted, RequestBean.class);
			String user = servletrequest.getRemoteUser();
			
			//Parametri passati esternamente
			LinkedHashMap<String, String> extraparams = (LinkedHashMap<String, String>)request.getData().remove("EXTRA_PARAM");
			extraparams.put("user", user);
			extraparams.put("isFromFilter", "true");
			//Recupero il datasource server
			datasource = (OptionFetchDataSource)Class.forName(SingletonDataSource.getInstance().getDatasources().get(extraparams.get("sourceidobject"))).newInstance();
			datasource.setExtraparams(extraparams);
			datasource.setSession(session);
			datasource.setRequest(servletrequest);
			datasource.setResponse(servletresponse);
			DSOperationType type = getDSOperation(request.getOperationType());
			List datalist = new ArrayList();
			Object objret = null;
			response.setStatus(DSResponse.STATUS_SUCCESS);
			switch(type){

			case FETCH:
				//Per evitare che il range arrivi a null
				if(request.getStartRow()==null){
					request.setStartRow(0);
				}

				if(request.getEndRow()==null){
					request.setEndRow(((GenericConfigBean)SpringAppContext.getContext().getBean("GenericPropertyConfigurator")).getFiltrableSelectPageSize());
				}

				PaginatorBean<?> paginator = datasource.retrieveValuesFromFetch(criteriaMapping(request.getData(), extraparams),request.getStartRow(),request.getEndRow(),getOrderBy(request));
				if(paginator==null){
					paginator = new PaginatorBean();
					paginator.setStartRow(0);
					paginator.setEndRow(0);
					paginator.setTotalRows(0);

				}
				response.setStartRow(paginator.getStartRow());
				if (paginator.getTotalRows() < paginator.getEndRow())
					response.setEndRow(paginator.getTotalRows());
				else 
					response.setEndRow(paginator.getEndRow());
				response.setTotalRows(paginator.getTotalRows());
				response.setData(paginator.getData());					
				break;

			
			}
		}catch(Throwable e){
//			StringWriter sw = new StringWriter();
//			PrintWriter pw = new PrintWriter(sw);
			mLogger.warn(e);
			mLogger.error("Errore: " + e.getMessage(), e);
			response.setStatus(DSResponse.STATUS_FAILURE);
			//Aggiungo il messaggio di errore
			//			String lString = gson.toJson(e);
			datasource.addMessage(e.getMessage(), "", MessageType.ERROR);	
			List<ExceptionBean> lArrayList = new ArrayList<ExceptionBean>();
			ExceptionBean lExceptionBean = new ExceptionBean();
			lExceptionBean.setErrorCode(100);
			if (flagCifratura && isOracleError(e)) {
				lExceptionBean.setErrorMessage(normalizeErrorMessage(e.getMessage(), true));								
				lExceptionBean.setHtmlStackTrace(null);
			} else {
				lExceptionBean.setErrorMessage(normalizeErrorMessage(e.getMessage()));								
				lExceptionBean.setHtmlStackTrace(new ExceptionUtil().getStackTrace(e));
			}
			lArrayList.add(lExceptionBean);
			response.setData(lArrayList);
		}
		DSResponseJson dsrespjson = new DSResponseJson();
		response.setMessages(datasource.getMessages());

		dsrespjson.setResponse(response);

		String jsonresp = gson.toJson(dsrespjson);
//		mLogger.debug(jsonresp);
		
//		mLogger.debug("=========================================");
		final String jsonrespEncrypted = encrypt(jsonresp, flagCifratura);
//        mLogger.debug("INVIO RISPOSTA: \n"+jsonrespEncrypted);
//        mLogger.debug("=========================================");
        
		return jsonrespEncrypted;
	}
	
	/**
	 * Recupera il tipo di operazione
	 * @param value
	 * @return
	 */
	private DSOperationType getDSOperation(String value){
		DSOperationType[] types =  DSOperationType.values();
		for(DSOperationType type:types){
			if(type.getValue().equals(value)){
				return type;
			}
		}
		return null;
	}

	/**
	 * Mapping della mappa sul bean
	 * @param source
	 * @param map
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private Object beanMapping(AbstractDataSource<?,?> source,Map map) throws Exception{
		Object instance = source.getBeanClassIn().newInstance();
		if(instance instanceof Map) {
			instance = map;
		} else {
			//Converto la mappa in json 
			Gson gson = new Gson();
			String jsonmap = gson.toJson(map);

			//Recupero il sorgente
			instance = gson.fromJson(jsonmap, source.getBeanClassIn());	
		}
		return instance;
	}

	/**
	 * Creo l'AdvancedCriteria 
	 * @param source
	 * @param map
	 * @param extraparams 
	 * @return
	 */
	private AdvancedCriteria criteriaMapping(HashMap<String,Object> map, LinkedHashMap<String, String> extraparams) throws Exception{
		//Converto la mappa in json 
		Gson gson = new Gson();
		String jsonmap = gson.toJson(map);	
		AdvancedCriteria lAdvancedCriteria = new AdvancedCriteria();
		try {
		for (String lString : map.keySet()){
			Criterion lCriterion = new Criterion();
			
			lCriterion.setFieldName(lString);
			lCriterion.setValue(map.get(lString));						
			lAdvancedCriteria.addCriterion(lCriterion);
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lAdvancedCriteria;
//		return gson.fromJson(jsonmap, AdvancedCriteria.class);
	}

	/**
	 * Individua gli orderby da settare
	 * @param bean
	 * @return
	 */
	private List<OrderByBean> getOrderBy(RequestBean bean){
		List<OrderByBean> orders = null;
		if(bean.getSortBy()!=null){
			orders = new ArrayList<OrderByBean>();
			for(String order:bean.getSortBy()){
				if(order.startsWith("-")){
					orders.add(new OrderByBean(order.substring(1),Direction.DESC));
				}else{
					orders.add(new OrderByBean(order,Direction.ASC));
				}
			}
		}
		return orders;
	}

	/**
	 * Bean di Response
	 * @author michele
	 *
	 */
	private class DSResponseJson{

		private ResponseBean response;

		public ResponseBean getResponse() {
			return response;
		}

		public void setResponse(ResponseBean response) {
			this.response = response;
		}
	}
	
}