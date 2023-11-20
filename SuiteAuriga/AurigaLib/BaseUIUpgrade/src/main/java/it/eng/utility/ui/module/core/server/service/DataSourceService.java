/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.DSOperationType;

import it.eng.core.business.beans.AbstractBean;
import it.eng.core.performance.PerformanceLogger;
import it.eng.exception.VulnerabilityException;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.MessageUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean.Direction;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.ComparatorBean;
import it.eng.utility.ui.module.core.server.datasource.SingletonDataSource;
import it.eng.utility.ui.module.core.server.security.ISecurityChecker;
import it.eng.utility.ui.module.core.shared.annotation.ExceptionManager;
import it.eng.utility.ui.module.core.shared.annotation.ManagedException;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.exception.ExceptionBean;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.MaxRowConfiguratorBean;
import it.eng.utility.ui.module.layout.shared.util.ExceptionUtil;
import it.eng.utility.ui.user.UserUtil;

/**
 * Implementa la comunincazione tra client e server tramite l'utilizzo dei Datasource di smartgwt
 * 
 * @author michele
 *
 */
@Controller
@RequestMapping("/datasourceservice")
public class DataSourceService extends AbstractRootService {

	static Logger mLogger = Logger.getLogger(DataSourceService.class);

	private GsonBuilder builder = GsonBuilderFactory.getIstance();
	
	GenericConfigBean genericConfig = null;
	
	@RequestMapping(value = "/all", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> all(@RequestBody String json, HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse)
			throws Exception {

		Gson gson = builder.create();

		ResponseBean response = new ResponseBean();
		AbstractDataSource datasource = null;
		
		final boolean flagCifratura = getFlagCifratura(session);

		try {
			
			if(json != null && json.equals("isc_dataFormat=json")) {
				// se mi arriva il json vuoto vuol dire che mi è andato in errore il validatore script lato client 
				throw new VulnerabilityException("La richiesta è andata in errore o contiene degli script potenzialmente dannosi perciò verrà bloccata");    			
            }
			
//			mLogger.debug("=========================================");
//            mLogger.debug("RICEVUTA RICHIESTA: \n"+json);
            final String jsonDecrypted = decryptIfNeeded(json, flagCifratura);
//            mLogger.debug("=========================================");
            
            if(jsonDecrypted != null && (jsonDecrypted.equals("isc_dataFormat=json") || RequestValidatorServer.containsScript(session, jsonDecrypted))) {
            	throw new VulnerabilityException("La richiesta contiene degli script potenzialmente dannosi perciò verrà bloccata");    			
            }
	        
			// Converto il json in RequestBean
			RequestBean request = gson.fromJson(jsonDecrypted, RequestBean.class);

			// check eventuali per la sicurezza
			boolean checkSecurity = checkSecurity(request, servletrequest, servletresponse);
			if (!checkSecurity) {
				throw new Exception("Sessione non valida");
			}

			if (request == null || request.getData() == null)
				throw new Exception("Richiesta non valida");

			// Parametri passati esternamente
			LinkedHashMap<String, String> extraparams = (LinkedHashMap<String, String>) request.getData().remove("EXTRA_PARAM");

			// Recupero il fatto che sia un selectFilter
			Boolean lBooleanIsFilter = (Boolean) request.getData().remove("selectFilter");

			mLogger.debug("sourceidobject: " + extraparams.get("sourceidobject") + " -> "
					+ SingletonDataSource.getInstance().getDatasources().get(extraparams.get("sourceidobject")));
			// Recupero il datasource server
			datasource = (AbstractDataSource) Class.forName(SingletonDataSource.getInstance().getDatasources().get(extraparams.get("sourceidobject")))
					.newInstance();

			if (datasource == null)
				throw new Exception("Richiesta non valida");

			datasource.setExtraparams(extraparams);
			if (extraparams != null) {
				if (extraparams.containsKey("uuid")) {
					datasource.setUuid(extraparams.get("uuid"));
				}
			}
			if (StringUtils.isNotEmpty(datasource.getUuid())) {
				RequestContextHolder.currentRequestAttributes().setAttribute("uuid", datasource.getUuid(), RequestAttributes.SCOPE_REQUEST);
			} else {
				RequestContextHolder.currentRequestAttributes().removeAttribute("uuid", RequestAttributes.SCOPE_REQUEST);
			}
			datasource.setSession(session);
			datasource.setRequest(servletrequest);
			datasource.setResponse(servletresponse);
			DSOperationType type = getDSOperation(request.getOperationType());
			List datalist = new ArrayList();
			Object objret = null;
			response.setStatus(DSResponse.STATUS_SUCCESS);
			
			PerformanceLogger lPerformanceLogger = new PerformanceLogger(getUserInfoForPerformanceLogger(session), extraparams.get("sourceidobject") + "." + request.getOperationType() + " [" + extraparams.toString() + "]");
			lPerformanceLogger.start();
						
			switch (type) {

			case VALIDATE:
				Map<String, ErrorBean> mappingerror = datasource.validate(beanMapping(datasource, request.getData()));
				if (mappingerror != null && !mappingerror.isEmpty()) {
					response.setStatus(DSResponse.STATUS_VALIDATION_ERROR);
					// response.setErrors(mappingerror);
				}
				break;

			case FETCH:
				// Per evitare che il range arrivi a null
				if (request.getStartRow() == null) {
					request.setStartRow(0);
				}

				if (request.getEndRow() == null) {
					// Recupero quello specifico in base alla singola datasource
					String datasourceName = extraparams.get("sourceidobject");
					HashMap<String, Integer> lHashMap = MaxRowConfiguratorBean.getConfigurazioni();
					if (lHashMap != null && lHashMap.get(datasourceName) != null) {
						request.setEndRow(lHashMap.get(datasourceName) - 1);
					} else if (MaxRowConfiguratorBean.getDefaultValue() != null) {
						request.setEndRow(MaxRowConfiguratorBean.getDefaultValue() - 1);
					} else {
						request.setEndRow(null);
					}
				}

				PaginatorBean<?> paginator = datasource.stoppableFetch(criteriaMapping(request.getData(), lBooleanIsFilter), request.getStartRow(),
						request.getEndRow(), getOrderBy(request));
				 
				if (paginator == null) {
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
				if (paginator.getRowsForPage() != null && paginator.getRowsForPage().intValue() > 0) {
					response.setRowsForPage(paginator.getRowsForPage());					
				}
				if (paginator.getOverflow()) {
					response.setOverflowData(true);
					response.setOverflowSortField(paginator.getOverflowSortField());
					response.setOverflowSortDesc(paginator.getOverflowSortDesc());
				}
				break;
			case ADD:
				objret = datasource.add(beanMapping(datasource, request.getData()));
				datalist.add(objret);
				response.setStartRow(0);
				response.setEndRow(1);
				response.setTotalRows(1);
				response.setData(datalist);
				break;

			case REMOVE:
				objret = datasource.remove(beanMapping(datasource, request.getData()));
				datalist.add(objret);
				response.setStartRow(0);
				response.setEndRow(1);
				response.setTotalRows(1);
				response.setData(datalist);
				break;

			case UPDATE:
				objret = datasource.update(beanMapping(datasource, request.getData()), beanMapping(datasource, request.getOldValues()));
				datalist.add(objret);
				response.setStartRow(0);
				response.setEndRow(1);
				response.setTotalRows(1);
				response.setData(datalist);
				break;

			case CUSTOM:
				if ("export".equals(request.getOperationId())) {
					Map map = request.getData();
					// Converto la mappa in json
					String jsonmap = builder.create().toJson(map);
					// Recupero il sorgente
					ExportBean bean = gson.fromJson(jsonmap, ExportBean.class);
					MethodAccess access = MethodAccess.get(datasource.getClass());
					objret = access.invoke(datasource, request.getOperationId(), bean);
				} else if ("compareOldAndNew".equals(request.getOperationId())) {
					Map map = request.getData();
					Map mapOld = (Map) map.get("oldBean");
					Map mapNew = (Map) map.get("newBean");
					Class lClass = datasource.getBeanClassIn();
					ComparatorBean lComparatorBean = new ComparatorBean();
					String jsonmapold = gson.toJson(mapOld);
					// Recupero il sorgente
					Object instanceOld = gson.fromJson(jsonmapold, datasource.getBeanClassIn());
					String jsonmapnew = gson.toJson(mapNew);
					// Recupero il sorgente
					Object instanceNew = gson.fromJson(jsonmapnew, datasource.getBeanClassIn());
					lComparatorBean.setNewBean(instanceNew);
					lComparatorBean.setOldBean(instanceOld);
					objret = datasource.compareOldAndNew(lComparatorBean);
				} else {
					// Recupero e richiamo il metodo custom
					MethodAccess access = MethodAccess.get(datasource.getClass());
					Class lClass = null;
					for (Method lMethod : datasource.getClass().getDeclaredMethods()) {
						if (lMethod.getName().equals(request.getOperationId())) {
							if (lMethod.getModifiers() == 1)
								if (lMethod.getParameterTypes().length > 0)
									lClass = lMethod.getParameterTypes()[0];
						}
					}	
					Object obj = beanMapping(datasource, request.getData(), lClass);
					if(obj != null) {
						objret = access.invoke(datasource, request.getOperationId(), obj);
					} else {					
						objret = access.invoke(datasource, request.getOperationId());
					}
				}
				datalist.add(objret);
				response.setStartRow(0);
				response.setEndRow(1);
				response.setTotalRows(1);
				response.setData(datalist);
				break;
			}
			
			lPerformanceLogger.end();
			
		} catch (Throwable e) {
			mLogger.error("Errore: " + e.getMessage(), e);			
			if (e.getClass().getAnnotation(ManagedException.class) != null) {
				// E' una eccezione che non deve aprire la finestra popup ma solo visualizzare il messaggio a scomparsa
				ManagedException lManagedException = e.getClass().getAnnotation(ManagedException.class);
				int status = lManagedException.status();
				response.setStatus(status);
				Object lObjGestore = lManagedException.gestore().newInstance();
				ExceptionManager lExceptionManager = (ExceptionManager) lObjGestore;
				boolean hideErrorDetails = false;
				try {
					GenericConfigBean lGenericConfigBean = getGenericConfigBean();
					String sHide = lGenericConfigBean.getHideErrorDetails();
					// Se hideErrorDetails == true nascondo solo gli errori Oracle
					hideErrorDetails = (new Boolean(sHide).booleanValue()) && isOracleError(e);
				} catch (Exception exx) {
					mLogger.error("Errore nella lettura del GenericConfigBean", exx);
				}
				final String normalizedErrMsg = normalizeErrorMessage(e.getMessage(), hideErrorDetails);
				mLogger.info("flagCifratura: "+flagCifratura+". normalizedErrMsg: " + normalizedErrMsg);
				lExceptionManager.manageException(new Throwable(normalizedErrMsg, e.getCause()), datasource, response);
			} else if(e instanceof VulnerabilityException) {
				// E' una eccezione di vulnerabilità che deve aprire la finestra popup
				response.setStatus(DSResponse.STATUS_FAILURE);
				List<ExceptionBean> lArrayList = new ArrayList<ExceptionBean>();

				ExceptionBean lExceptionBean = null;
				lExceptionBean = new ExceptionBean();
				lExceptionBean.setErrorCode(100);
				lExceptionBean.setErrorMessage(e.getMessage());
				lExceptionBean.setHtmlStackTrace(null);				
				
				if(StringUtils.isNotBlank(lExceptionBean.getErrorMessage())) {
					String language = UserUtil.getLocale(session).getLanguage();
					String message = MessageUtil.getValue(language, session, lExceptionBean.getErrorMessage());
					// Se è un errore gestito
					if (StringUtils.isNotBlank(message) && !message.equals(lExceptionBean.getErrorMessage())) {
						lExceptionBean.setErrorMessage(message);
						lExceptionBean.setHtmlStackTrace(null);
					}
				}

				lArrayList.add(lExceptionBean);

				if (datasource != null) {
					datasource.addMessage(lExceptionBean.getErrorMessage(), "", MessageType.ERROR);
				}

				response.setData(lArrayList);
			} else {
				// E' una eccezione che deve aprire la finestra popup con stack trace
				response.setStatus(DSResponse.STATUS_FAILURE);
				List<ExceptionBean> lArrayList = new ArrayList<ExceptionBean>();

				boolean hideErrorDetails = false;
				
				try {
					GenericConfigBean lGenericConfigBean = getGenericConfigBean();
					String sHide = lGenericConfigBean.getHideErrorDetails();
					hideErrorDetails = new Boolean(sHide).booleanValue();
				} catch (Exception exx) {
					mLogger.error("Errore nella lettura del GenericConfigBean", exx);
				}
					
				// preparo
				ExceptionBean lExceptionBean = null;

				if (hideErrorDetails) {
					lExceptionBean = new ExceptionBean();
					lExceptionBean.setErrorCode(100);
					lExceptionBean.setErrorMessage(normalizeErrorMessage(e.getMessage(), hideErrorDetails));
					lExceptionBean.setHtmlStackTrace(null);

				} else {
					lExceptionBean = new ExceptionBean();
					lExceptionBean.setErrorCode(100);
					lExceptionBean.setErrorMessage(normalizeErrorMessage(e.getMessage(), hideErrorDetails));
					lExceptionBean.setHtmlStackTrace(new ExceptionUtil().getStackTrace(e));
				}
				
				if(StringUtils.isNotBlank(lExceptionBean.getErrorMessage())) {
					String language = UserUtil.getLocale(session).getLanguage();
					String message = MessageUtil.getValue(language, session, lExceptionBean.getErrorMessage());
					// Se è un errore gestito
					if (StringUtils.isNotBlank(message) && !message.equals(lExceptionBean.getErrorMessage())) {
						lExceptionBean.setErrorMessage(message);
						lExceptionBean.setHtmlStackTrace(null);
					}
				}

				lArrayList.add(lExceptionBean);

				if (datasource != null) {
					datasource.addMessage(lExceptionBean.getErrorMessage(), "", MessageType.ERROR);
				}

				response.setData(lArrayList);
			}
			response.setStartRow(-1);
			response.setEndRow(-1);
			response.setTotalRows(-1);
		}
		// Federico Cacco 04.05.2016
		// Modifico il content-type della response, per esigenze sicurezza Inail
		// String contentType = "text/html;charset=UTF-8";
		String contentType = "application/json;charset=UTF-8";
		try {
			GenericConfigBean lGenericConfigBean = getGenericConfigBean();
			// GenericConfigBean lGenericConfigBean = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
			String configuredContentType = lGenericConfigBean.getDatasourceServiceResponseContentType();
			if (configuredContentType != null && !"".equals(configuredContentType))
				contentType = configuredContentType;
		} catch (Exception exx) {
		}

		servletresponse.setContentType(contentType);
        if ( !contentType.contains("charset") ) {
        	servletresponse.setCharacterEncoding("UTF-8");
        }
		
		DSResponseJson dsrespjson = new DSResponseJson();

		if (datasource != null)
			response.setMessages(datasource.getMessages());

		dsrespjson.setResponse(response);

		builder.disableHtmlEscaping();
		Gson gsonNew = builder.create();
		String jsonresp = gsonNew.toJson(dsrespjson);
		servletresponse.setHeader("Content-Type", contentType);
		HttpHeaders responseHeaders = new HttpHeaders();
		// Federico Cacco 04.05.2016
		// Modifico il content-type della response, per esigenze sicurezza Inail
		//responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", contentType);

        final String lStringEncrypted = encrypt(jsonresp, flagCifratura);

		return new ResponseEntity<String>(lStringEncrypted, responseHeaders, HttpStatus.CREATED);
	}


	protected boolean checkSecurity(RequestBean request, HttpServletRequest servletrequest, HttpServletResponse servletresponse) {

		// NIENTE DA FARE QUI
		try {
			GenericConfigBean lGenericConfigBean = getGenericConfigBean();
			// GenericConfigBean lGenericConfigBean = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
			if (lGenericConfigBean != null) {
				String securityChecker = lGenericConfigBean.getSecurityChecker();
				if (securityChecker != null && !"".equals(securityChecker)) {
					ISecurityChecker checker = (ISecurityChecker) Class.forName(securityChecker).newInstance();
					return checker.check(request, servletrequest, servletresponse);
				}
			}
		} catch (Exception exx) {
			mLogger.error(exx.getMessage(), exx);
		}
		return true;

	}

	private Map<String, Object> validateRequest(Map<String, Object> map) {
		if (map != null && map.size() > 0) {
			for (String key : map.keySet()) {		
				if (map.get(key) != null) {
					if (map.get(key) instanceof String) {
						String currentValue = (String) map.get(key);
						map.put(key, replaceCharacters(currentValue));
					} else if (map.get(key) instanceof ArrayList) {
						ArrayList currentListValue = (ArrayList) map.get(key);
						for(Object obj : currentListValue) {
							if(obj instanceof Map) {
								Map<String, Object> row = (Map<String, Object>) obj;							
								if (row != null && row.size() > 0) {
									for (String col : row.keySet()) {
										if (row.get(col) != null && row.get(col) instanceof String) {
											String currentValue = (String) row.get(col);
											row.put(col, replaceCharacters(currentValue));
										}								
									}
								}						
							}
						}
						map.put(key, currentListValue);					
					} 
				}
			}
		}
		return map;
	}

	private String replaceCharacters(String currentValue) {
		currentValue = currentValue.replace("‘", "'");
		currentValue = currentValue.replace("“", "\"");
		currentValue = currentValue.replace("—", "-");
		currentValue = currentValue.replace("’", "'");
		currentValue = currentValue.replace("”", "\"");
		// Controllo se devo fare il parsing per sanare il codice html
		boolean escapeHtmlEnabled = false;
		try {
			GenericConfigBean lGenericConfigBean = getGenericConfigBean();
			// GenericConfigBean lGenericConfigBean = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
			escapeHtmlEnabled = lGenericConfigBean.getAttivaEscapeHtmlDSCall();
		} catch (Exception exx) {
		}
		if (escapeHtmlEnabled){
			// Federico Cacco 09.06.2016
			// Per esigenze sicurezza INAIL elimino tutti gli script passati nei parametri
			String regExp1 = "<\\s*script[^<]*(?:(?!</script>)<[^<]*)*<\\s*/\\s*script>";
			String regExp2 = "<\\s*script[^>]*>";
			String regExp3 = "<\\s*/\\s*script\\s*>";
			currentValue = Pattern.compile(regExp1, Pattern.CASE_INSENSITIVE).matcher(currentValue).replaceAll("");
			currentValue = Pattern.compile(regExp2, Pattern.CASE_INSENSITIVE).matcher(currentValue).replaceAll("");	
			currentValue = Pattern.compile(regExp3, Pattern.CASE_INSENSITIVE).matcher(currentValue).replaceAll("");
			currentValue = currentValue.replace("<", "&lt;");
			currentValue = currentValue.replace(">", "&gt;");
		}
		return currentValue;
	}


	/**
	 * Recupera il tipo di operazione
	 * 
	 * @param value
	 * @return
	 */
	private DSOperationType getDSOperation(String value) {
		DSOperationType[] types = DSOperationType.values();
		for (DSOperationType type : types) {
			if (type.getValue().equals(value)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Mapping della mappa sul bean
	 * 
	 * @param source
	 * @param map
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private Object beanMapping(AbstractDataSource<?, ?> source, Map map) throws Exception {
		Map<String, Object> resMap = validateRequest(map);
		Object instance = source.getBeanClassIn().newInstance();
		if (instance instanceof Map) {
			instance = resMap;
		} else {
			// Converto la mappa in json
			Gson gson = builder.create();
			String jsonmap = gson.toJson(resMap);
			// Recupero il sorgente
			instance = gson.fromJson(jsonmap, source.getBeanClassIn());
			if (instance instanceof AbstractBean) {
				for (Object key : resMap.keySet()) {
					((AbstractBean) instance).getUpdatedProperties().add((String) key);
				}
			}
		}
		return instance;
	}

	private Object beanMapping(AbstractDataSource<?, ?> source, Map map, Class pClass) throws Exception {
		Map<String, Object> resMap = validateRequest(map);
		if (pClass == null)
			return null;
		Object instance = pClass.newInstance();
		if (instance instanceof Map) {
			instance = resMap;
		} else {
			// Converto la mappa in json
			Gson gson = builder.create();
			String jsonmap = gson.toJson(resMap);
			// Recupero il sorgente
			instance = gson.fromJson(jsonmap, pClass);
			if (instance instanceof AbstractBean) {
				for (Object key : resMap.keySet()) {
					((AbstractBean) instance).getUpdatedProperties().add((String) key);
				}
			}
		}
		return instance;
	}

	/**
	 * Creo l'AdvancedCriteria
	 * 
	 * @param source
	 * @param map
	 * @return
	 */
	private AdvancedCriteria criteriaMapping(HashMap<String, Object> map, Boolean isSelectFilter) throws Exception {
		// Converto la mappa in json
		Gson gson = builder.create();
		String jsonmap = gson.toJson(map);
		AdvancedCriteria lAdvancedCriteria = null;
		if (!isSelectFilter) {
			lAdvancedCriteria = gson.fromJson(jsonmap, AdvancedCriteria.class);
		} else {
			lAdvancedCriteria = new AdvancedCriteria();
			try {
				for (String lString : map.keySet()) {
					Criterion lCriterion = new Criterion();

					lCriterion.setFieldName(lString);
					lCriterion.setValue(map.get(lString));
					lAdvancedCriteria.addCriterion(lCriterion);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return lAdvancedCriteria;
	}

	/**
	 * Individua gli orderby da settare
	 * 
	 * @param bean
	 * @return
	 */
	private List<OrderByBean> getOrderBy(RequestBean bean) {
		List<OrderByBean> orders = null;
		if (bean.getSortBy() != null) {
			orders = new ArrayList<OrderByBean>();
			for (String order : bean.getSortBy()) {
				if (order.startsWith("-")) {
					orders.add(new OrderByBean(order.substring(1), Direction.DESC));
				} else {
					orders.add(new OrderByBean(order, Direction.ASC));
				}
			}
		}
		return orders;
	}
	
	/**
	 * Bean di Response
	 * 
	 * @author michele
	 *
	 */
	private class DSResponseJson {

		private ResponseBean response;

		public ResponseBean getResponse() {
			return response;
		}

		public void setResponse(ResponseBean response) {
			this.response = response;
		}
	}
	
	/**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel performance log
	 */
	private static String getUserInfoForPerformanceLogger(HttpSession session) {
		String result = "";
		LoginBean loginBean = session != null ? (LoginBean) session.getAttribute("LOGIN_INFO") : null;
		if (loginBean != null && StringUtils.isNotBlank(loginBean.getToken())) {
			String idUser = loginBean.getIdUser().toPlainString();
			String idUserLavoro = loginBean.getIdUserLavoro();
			String userId = loginBean.getUserid();
			String denominazione = loginBean.getDenominazione();
			result = "[";
			if (StringUtils.isNotBlank(idUser)) {
				result += " idUser: " + idUser;
			}
			if (StringUtils.isNotBlank(idUserLavoro)) {
				result += " idUserLavoro: " + idUserLavoro;
			}
			if (StringUtils.isNotBlank(userId)) {
				result += " userId: " + userId;
			}
			if (StringUtils.isNotBlank(denominazione)) {
				result += " denominazione: " + denominazione;
			}
			result += "]";
		}
		return result;
	}
	
	private GenericConfigBean getGenericConfigBean() {
		if (genericConfig != null) {
			return genericConfig;
		} else {
			genericConfig = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
			return genericConfig;
		}
	}
}