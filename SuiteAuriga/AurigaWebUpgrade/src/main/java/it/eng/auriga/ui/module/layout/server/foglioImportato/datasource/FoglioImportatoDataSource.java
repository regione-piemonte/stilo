/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerTrovafogliximportBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.foglioImportato.bean.FoglioImportatoBean;
import it.eng.auriga.ui.module.layout.server.foglioImportato.bean.FoglioImportatoFilterXmlBean;
import it.eng.auriga.ui.module.layout.server.foglioImportato.bean.FoglioImportatoXmlBean;
import it.eng.auriga.ui.module.layout.server.foglioImportato.bean.FoglioImportatoXmlBeanDeserializationHelper;
import it.eng.client.DmpkBmanagerTrovafogliximport;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "FoglioImportatoDataSource")
public class FoglioImportatoDataSource extends AurigaAbstractFetchDatasource<FoglioImportatoBean> {
	
	@Override
	public PaginatorBean<FoglioImportatoBean> fetch(AdvancedCriteria criteria, Integer startRow,
			Integer endRow, List<OrderByBean> orderby) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		boolean overflow = false;
		
		DmpkBmanagerTrovafogliximportBean input = createTrovaFogliximportBean(criteria, loginBean);
		
		DmpkBmanagerTrovafogliximport dmpkBmanagerTrovafogliximport = new DmpkBmanagerTrovafogliximport();
		StoreResultBean<DmpkBmanagerTrovafogliximportBean> output = dmpkBmanagerTrovafogliximport.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				overflow = manageOverflow(output.getDefaultMessage());
				if(!overflow) {
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
		}
		
		String xmlOut = output.getResultBean().getFoglixmlout();
		List<FoglioImportatoXmlBean> storedProcedureResults = XmlListaUtility.recuperaLista(xmlOut, FoglioImportatoXmlBean.class);
		List<FoglioImportatoBean> returnValue = new ArrayList<FoglioImportatoBean>(storedProcedureResults.size());	
		
		getSession().setAttribute(FETCH_SESSION_KEY, storedProcedureResults);
		
		for (FoglioImportatoXmlBean contenutoFoglioXmlBean : storedProcedureResults){			
			FoglioImportatoBean currentRetrievedBean = new FoglioImportatoBean();
			BeanUtilsBean2.getInstance().copyProperties(currentRetrievedBean, contenutoFoglioXmlBean);
			returnValue.add(currentRetrievedBean);
		}
		
		PaginatorBean<FoglioImportatoBean> lPaginatorBean = new PaginatorBean<FoglioImportatoBean>();
		lPaginatorBean.setData(returnValue);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? returnValue.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(returnValue.size());
		lPaginatorBean.setOverflow(overflow);
		
		return lPaginatorBean;
	}
	
	private DmpkBmanagerTrovafogliximportBean createTrovaFogliximportBean(AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {
		
		FoglioImportatoFilterXmlBean filterFoglioXmlBean = new FoglioImportatoFilterXmlBean();
		
		String nomeFoglio = null;
		String stato = null;
		Date tsUploadDa = null;		
		Date tsUploadA = null;	
		Date tsInizioElabDa = null;		
		Date tsInizioElabA = null;	
		Date tsFineElabDa = null;		
		Date tsFineElabA = null;
		String tipiContenuto = null;
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("nomeFoglio".equals(crit.getFieldName())) {
					nomeFoglio = getTextFilterValue(crit);
				} else if ("stati".equals(crit.getFieldName())) {
					stato = getTextFilterValue(crit);
				} else if ("tsUpload".equals(crit.getFieldName())) {
					Date[] tsUpload = getDateConOraFilterValue(crit);
					if (tsUploadDa != null) {
						tsUploadDa = tsUploadDa.compareTo(tsUpload[0]) < 0 ? tsUpload[0] : tsUploadDa;
					} else {
						tsUploadDa = tsUpload[0];
					}
					if (tsUploadA != null) {
						tsUploadA = tsUploadA.compareTo(tsUpload[1]) > 0 ? tsUpload[1] : tsUploadA;
					} else {
						tsUploadA = tsUpload[1];
					}
				} else if ("tsInizioElab".equals(crit.getFieldName())) {
					Date[] tsInizioElab = getDateConOraFilterValue(crit);
					if (tsInizioElabDa != null) {
						tsInizioElabDa = tsInizioElabDa.compareTo(tsInizioElab[0]) < 0 ? tsInizioElab[0] : tsInizioElabDa;
					} else {
						tsInizioElabDa = tsInizioElab[0];
					}
					if (tsInizioElabA != null) {
						tsInizioElabA = tsInizioElabA.compareTo(tsInizioElab[1]) > 0 ? tsInizioElab[1] : tsInizioElabA;
					} else {
						tsInizioElabA = tsInizioElab[1];
					}
				}  else if ("tsFineElab".equals(crit.getFieldName())) {
					Date[] tsFineElab = getDateConOraFilterValue(crit);
					if (tsFineElabDa != null) {
						tsFineElabDa = tsFineElabDa.compareTo(tsFineElab[0]) < 0 ? tsFineElab[0] : tsFineElabDa;
					} else {
						tsFineElabDa = tsFineElab[0];
					}
					if (tsFineElabA != null) {
						tsFineElabA = tsFineElabA.compareTo(tsFineElab[1]) > 0 ? tsFineElab[1] : tsFineElabA;
					} else {
						tsFineElabA = tsFineElab[1];
					}
				} else if ("tipiContenuto".equals(crit.getFieldName())) {
					tipiContenuto = getTextFilterValue(crit);
				}
			}
		}
		
		if(StringUtils.isNotBlank(nomeFoglio)) {
			filterFoglioXmlBean.setNomeFoglio(nomeFoglio);
		}
		if(StringUtils.isNotBlank(stato)) {
			filterFoglioXmlBean.setStati(stato);
		}
		if(tsUploadDa != null){
			filterFoglioXmlBean.setTsUploadDa(tsUploadDa);
		}
		if(tsUploadA != null){
			filterFoglioXmlBean.setTsUploadA(tsUploadA);
		}
		if(tsInizioElabDa != null){
			filterFoglioXmlBean.setTsInizioElabDa(tsInizioElabDa);
		}
		if(tsInizioElabA != null){
			filterFoglioXmlBean.setTsInizioElabA(tsInizioElabA);
		}
		if(tsFineElabDa != null){
			filterFoglioXmlBean.setTsFineElabDa(tsFineElabDa);
		}
		if(tsFineElabA != null){
			filterFoglioXmlBean.setTsFineElabA(tsFineElabA);
		}
		if(StringUtils.isNotBlank(tipiContenuto)) {
			filterFoglioXmlBean.setTipiContenuto(tipiContenuto);
		}
		
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlFiltersFoglio = lXmlUtilitySerializer.bindXml(filterFoglioXmlBean);
		
		DmpkBmanagerTrovafogliximportBean input = new DmpkBmanagerTrovafogliximportBean();
		input.setFiltriio(xmlFiltersFoglio);
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		input.setFiltriio(xmlFiltersFoglio);
		input.setColorderbyio(null);			
		input.setFlgsenzapaginazionein(new Integer(1));
		
		return input;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		DmpkBmanagerTrovafogliximportBean input = createTrovaFogliximportBean(criteria, loginBean);
		input.setOverflowlimitin(-2);
		
		DmpkBmanagerTrovafogliximport dmpkBmanagerTrovafogliximport = new DmpkBmanagerTrovafogliximport();
		StoreResultBean<DmpkBmanagerTrovafogliximportBean> output = dmpkBmanagerTrovafogliximport.execute(getLocale(), loginBean, input);
		
		// imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio() != null ? output.getResultBean().getBachsizeio() : 0;
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), FoglioImportatoXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), FoglioImportatoXmlBeanDeserializationHelper.class);

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

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		DmpkBmanagerTrovafogliximportBean input = createTrovaFogliximportBean(criteria, loginBean);
		input.setOverflowlimitin(-1);
		
		DmpkBmanagerTrovafogliximport dmpkBmanagerTrovafogliximport = new DmpkBmanagerTrovafogliximport();
		StoreResultBean<DmpkBmanagerTrovafogliximportBean> output = dmpkBmanagerTrovafogliximport.execute(getLocale(), loginBean, input);
		
		String numTotRecOut = output.getResultBean().getNrototrecout() != null ? String.valueOf(output.getResultBean().getNrototrecout()) : "";

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
		}

		NroRecordTotBean retValue = new NroRecordTotBean();
		retValue.setNroRecordTot(Integer.valueOf(numTotRecOut));
		
		return retValue;
	}
	
	@Override
	public String getNomeEntita() {
		return "foglio_importato";
	}

}