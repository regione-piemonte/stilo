/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfTrovalistalavoroBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.attiInLavorazione.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.TSOXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.TSOXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.datasource.CriteriIterNonSvoltBean;
import it.eng.auriga.ui.module.layout.server.gestioneTSO.datasource.bean.TSOBean;
import it.eng.client.DmpkWfTrovalistalavoro;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author dbe4235
 *
 */

@Datasource(id = "TSOInIterDatasource")
public class TSOInIterDatasource extends AurigaAbstractFetchDatasource<TSOBean> {

	private static Logger mLogger = Logger.getLogger(TSOInIterDatasource.class);
	
	@Override
	public String getNomeEntita() {
		return "tso_in_iter";
	}
	
	@Override
	public PaginatorBean<TSOBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkWfTrovalistalavoroBean input = createInputBean(criteria,loginBean);
		
		boolean overflow = false;
		
		DmpkWfTrovalistalavoro lDmpkWfTrovalistalavoro = new DmpkWfTrovalistalavoro();
		StoreResultBean<DmpkWfTrovalistalavoroBean> lResultBean = lDmpkWfTrovalistalavoro.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), input);

		if (lResultBean.isInError()){
			mLogger.error(lResultBean.getDefaultMessage());
			throw new StoreException(lResultBean);
		} else {
			overflow = manageOverflow(lResultBean.getDefaultMessage());
		}

		String xmlOut = lResultBean.getResultBean().getListaxmlout();

		List<TSOXmlBean> lListResult = XmlListaUtility.recuperaLista(xmlOut, TSOXmlBean.class);
		List<TSOBean> lList = new ArrayList<TSOBean>(lListResult.size());
		for (TSOXmlBean lTSOXmlBean : lListResult) {
			TSOBean lTSOBean = new TSOBean();
			BeanUtilsBean2.getInstance().copyProperties(lTSOBean, lTSOXmlBean);
			lList.add(lTSOBean);
		}
		
		getSession().setAttribute(FETCH_SESSION_KEY, lListResult);

		PaginatorBean<TSOBean> lPaginatorBean = new PaginatorBean<TSOBean>();
		lPaginatorBean.setData(lList);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? lList.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(lList.size());
		lPaginatorBean.setOverflow(overflow);

		return lPaginatorBean;
	}
	
	private DmpkWfTrovalistalavoroBean createInputBean(AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		CriteriAvanzati scCriteriAvanzati = new CriteriAvanzati();
		
		TSOBean filter = new TSOBean();
		
		BeanWrapperImpl wrappperBean = BeanPropertyUtility.getBeanWrapper(filter);
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){				
				if ("dataRichiesta".equals(crit.getFieldName())) {
					Date[] protRichData = getDateFilterValue(crit);
					scCriteriAvanzati.setDataAttoDa(protRichData[0]);
					scCriteriAvanzati.setDataAttoA(protRichData[1]);	
				} else if("protRichNro".equals(crit.getFieldName())) {
					String[] protRichNro = getNumberFilterValue(crit);
					scCriteriAvanzati.setNroAttoDa(protRichNro[0]);
					scCriteriAvanzati.setNroAttoA(protRichNro[1]);					
				} else if("cognomePaziente".equals(crit.getFieldName())) {
					scCriteriAvanzati.setCognomePaziente(getTextFilterValue(crit));
				} else if("cognomeMedico".equals(crit.getFieldName())) {
					scCriteriAvanzati.setCognomeMedico(getTextFilterValue(crit));
				} else if("pazienteMinorenne".equals(crit.getFieldName())) {
					scCriteriAvanzati.setFlgPazienteMinorenne(getTextFilterValue(crit));
				} else if("istruttoriaInCarico".equals(crit.getFieldName())) {
					scCriteriAvanzati.setIdUserIstruttore(getTextFilterValue(crit));
				} else {
					buildFilterBeanFromCriterion(filter, crit, wrappperBean);
				}
			}
		}

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		DmpkWfTrovalistalavoroBean input = new DmpkWfTrovalistalavoroBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgsenzapaginazionein(1);
		input.setCriteriavanzatiin(lXmlUtilitySerializer.bindXml(scCriteriAvanzati));
		input.setIdprocesstypeio(new BigDecimal(ParametriDBUtil.getParametroDB(getSession(), "ID_PROCESS_TYPE_TSO")));
		
		//In fase
		if (StringUtils.isNotEmpty(filter.getInFase())) {
			input.setWfnamefasecorrio(filter.getInFase());
		}

		//Eseguibile task
		if (StringUtils.isNotEmpty(filter.getEseguibileTask())) {
			input.setWfnameattesegio(filter.getEseguibileTask());
		}
		
		//Criteri iter svolto
		List<XmlListaSimpleBean> lListCriteriIterSvolto = new ArrayList<XmlListaSimpleBean>();
		
		//Effettuato task
		if (StringUtils.isNotEmpty(filter.getEffettuatoTask())) {
			String[] lStrings = filter.getEffettuatoTask().split(";");
			for (String lStrFilter : lStrings) {
				XmlListaSimpleBean lXmlListaSimpleBean = new XmlListaSimpleBean();
				lXmlListaSimpleBean.setKey("AE");
				lXmlListaSimpleBean.setValue(lStrFilter);
				lListCriteriIterSvolto.add(lXmlListaSimpleBean);
			}
		}
		String criterIterSvolto = lXmlUtilitySerializer.bindXmlList(lListCriteriIterSvolto);
		input.setCriteriitersvoltoio(criterIterSvolto);
		
		//Criteri iter non svolto
		List<CriteriIterNonSvoltBean> lListCriteriIterNonSvolto = new ArrayList<CriteriIterNonSvoltBean>();
		
		//Da effettuare task
		if (StringUtils.isNotEmpty(filter.getDaEffettuareTask())) {
			String[] lStrings = filter.getDaEffettuareTask().split(";");
			for (String lStrFilter : lStrings) {
				CriteriIterNonSvoltBean lCriteriIterNonSvoltBean = new CriteriIterNonSvoltBean();
				lCriteriIterNonSvoltBean.setFaseTask("AE");
				lCriteriIterNonSvoltBean.setValue(lStrFilter);
				lCriteriIterNonSvoltBean.setTipo("1");
				lListCriteriIterNonSvolto.add(lCriteriIterNonSvoltBean);
			}
		}
		String criterIterNonSvolto = lXmlUtilitySerializer.bindXmlList(lListCriteriIterNonSvolto);
		input.setCriteriiternonsvoltoio(criterIterNonSvolto);

		return input;
	}
	
	@Override
	public TSOBean get(TSOBean bean) throws Exception {
		return bean;
	}

	@Override
	public TSOBean add(TSOBean bean) throws Exception {
		return bean;
	}

	@Override
	public TSOBean remove(TSOBean bean) throws Exception {
		return bean;
	}

	@Override
	public TSOBean update(TSOBean bean, TSOBean oldvalue) throws Exception {
		return bean;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		DmpkWfTrovalistalavoroBean input = createInputBean(criteria,loginBean);
		input.setOverflowlimitin(-1);
		
		DmpkWfTrovalistalavoro lDmpkWfTrovalistalavoro = new DmpkWfTrovalistalavoro();
		StoreResultBean<DmpkWfTrovalistalavoroBean> lResultBean = lDmpkWfTrovalistalavoro.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
		
		if (lResultBean.isInError()){
			throw new StoreException(lResultBean);
		}
		
		String numTotRecOut = lResultBean.getResultBean().getNrototrecout() != null ? String.valueOf(lResultBean.getResultBean().getNrototrecout()) : "";

		if(StringUtils.isNotBlank(lResultBean.getDefaultMessage())) {
			addMessage(lResultBean.getDefaultMessage(), "", MessageType.WARNING);
		}

		NroRecordTotBean retValue = new NroRecordTotBean();
		retValue.setNroRecordTot(Integer.valueOf(numTotRecOut));
		
		return retValue;
	}
	
	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		DmpkWfTrovalistalavoroBean input = createInputBean(criteria,loginBean);
		input.setOverflowlimitin(-2);
		
		DmpkWfTrovalistalavoro lDmpkWfTrovalistalavoro = new DmpkWfTrovalistalavoro();
		StoreResultBean<DmpkWfTrovalistalavoroBean> output = lDmpkWfTrovalistalavoro.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
		
		if (output.isInError()){
			throw new StoreException(output);
		}
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
		}
		
		// imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio() != null ? output.getResultBean().getBachsizeio() : 0;
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), TSOXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(),  TSOXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Richiesta di esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return null;
	}
	
	private Map<String, String> createRemapConditionsMap() {

		Map<String, String> retValue = new LinkedHashMap<String, String>();
		return retValue;
	}

}