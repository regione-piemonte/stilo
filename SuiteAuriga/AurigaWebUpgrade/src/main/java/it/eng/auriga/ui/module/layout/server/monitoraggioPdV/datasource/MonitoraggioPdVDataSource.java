/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_cs.bean.DmpkIntCsTrovapdvBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioPdV.datasource.bean.ErroriRdVBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioPdV.datasource.bean.PdVBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioPdV.datasource.bean.PdVXmlBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioPdV.datasource.bean.TrovaPdVFiltriXmlBean;
import it.eng.client.DmpkIntCsTrovapdv;
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

@Datasource(id="MonitoraggioPdVDataSource")
public class MonitoraggioPdVDataSource extends AurigaAbstractFetchDatasource<PdVBean> {
	
	boolean overflow = false;
	private static Logger mLogger = Logger.getLogger(MonitoraggioPdVDataSource.class);

	@Override
	public PaginatorBean<PdVBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		List<PdVBean> data = new ArrayList<PdVBean>();
		
		DmpkIntCsTrovapdvBean lDmpkIntCsTrovapdvBean = new DmpkIntCsTrovapdvBean();
		lDmpkIntCsTrovapdvBean.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkIntCsTrovapdvBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkIntCsTrovapdvBean.setColorderbyio(null);
		lDmpkIntCsTrovapdvBean.setFlgdescorderbyio(null);
		lDmpkIntCsTrovapdvBean.setFlgsenzapaginazionein(new Integer(1)); // lista non paginata
		lDmpkIntCsTrovapdvBean.setNropaginaio(null);
		lDmpkIntCsTrovapdvBean.setBachsizeio(null);
		lDmpkIntCsTrovapdvBean.setOverflowlimitin(null);
		lDmpkIntCsTrovapdvBean.setFlgsenzatotin(null);
		lDmpkIntCsTrovapdvBean.setFlgbatchsearchin(null);
		lDmpkIntCsTrovapdvBean.setFiltriio(new XmlUtilitySerializer().bindXml(buildFilter(criteria)));	 
		
		
		DmpkIntCsTrovapdv lDmpkIntCsTrovapdv = new DmpkIntCsTrovapdv();
		StoreResultBean<DmpkIntCsTrovapdvBean> output = lDmpkIntCsTrovapdv.execute(getLocale(), loginBean, lDmpkIntCsTrovapdvBean);
		
		if (output.isInError()) {
			throw new StoreException(output);
		} else {
			overflow = manageOverflow(output.getDefaultMessage());		
		}	
		
		if(StringUtils.isNotBlank(output.getResultBean().getResultout())) {			
			data = XmlListaUtility.recuperaLista(output.getResultBean().getResultout(), PdVBean.class);
		}
		
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);
		
		PaginatorBean<PdVBean> lPaginatorBean = new PaginatorBean<PdVBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}

	public List<PdVBean> getTestData() {
		
		List<PdVBean> data = new ArrayList<PdVBean>();
		
		PdVBean lPdVBean = new PdVBean();
		lPdVBean.setIdPdV("1");
		lPdVBean.setEtichetta("TEST");
		lPdVBean.setCodProcessoBancaProd("B3");
		//lPdVBean.setNomeProcessoBancaProd("ASSEGNI");
		lPdVBean.setNumDocumentiPdV("2");	
		lPdVBean.setDimensione("1536");	
		lPdVBean.setDataGenerazionePdV(new Date());
		lPdVBean.setImpronta("AAA");
		lPdVBean.setAlgoritmo("SHA-256");
		lPdVBean.setEncoding("BASE64");
		lPdVBean.setStato("creato");
		lPdVBean.setDataUltimoAggStato(new Date());
		lPdVBean.setErroriRdV(new ArrayList<ErroriRdVBean>());
		lPdVBean.setDataInvio(new Date());
		lPdVBean.setTempoRitornoRdV("111");
		lPdVBean.setTempoRitornoRicevutaAccRifTrasm("111");
		data.add(lPdVBean);
		
		return data;
	}

	private TrovaPdVFiltriXmlBean buildFilter(AdvancedCriteria criteria) {
		
		String idPdV = null;
		String etichetta = null;
		String stato = null;
		Date tsUltimoAggStatoDa = null;
		Date tsUltimoAggStatoA = null;
		Date dataGenerazionePdVDa = null;
		Date dataGenerazionePdVA = null;
		String nroDocPdVDa = null;
		String nroDocPdVA = null;
		String volDocPdVDa = null;
		String volDocPdVA = null;
		String codiciErrWarnTrasm = null;
		String msgErrWarnTrasm = null;
		String codiciErrWarnRecRdV = null;
		String msgErrWarnRecRdV = null;
		String tempoRicezRicTrasmDa = null;
		String tempoRicezRicTrasmA = null;
		String tempoSenzaRicTrasmDa = null;
		String tempoSenzaRicTrasmA = null;
		String tempoRicezioneRdVDa = null;
		String tempoRicezioneRdVA = null;
		String tempoSenzaRdVDa = null;
		String tempoSenzaRdVA = null;
		String codProcessoBancaProd = null;
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("idPdV".equals(crit.getFieldName())) {
					idPdV = getTextFilterValue(crit);
				} else if ("etichetta".equals(crit.getFieldName())) {
					etichetta = getTextFilterValue(crit);
				} else if ("stato".equals(crit.getFieldName())) {
					stato = getTextFilterValue(crit);
				} else if("tsUltimoAggStato".equals(crit.getFieldName())) {
					try {
						Date[] tsUltimoAggStato = getDateConOraFilterValue(crit);
						if (tsUltimoAggStato[0] != null) {
							tsUltimoAggStatoDa = tsUltimoAggStato[0];
						}
						if (tsUltimoAggStato[1] != null) {
							tsUltimoAggStatoA = tsUltimoAggStato[1];
						}
					} catch(Exception e) {}
				} else if ("dataGenerazionePdV".equals(crit.getFieldName())) {
					try {
						Date[] dataGenerazionePdV = getDateFilterValue(crit);
						dataGenerazionePdVDa = dataGenerazionePdV[0];
						dataGenerazionePdVA = dataGenerazionePdV[1];
					} catch(Exception e) {}
				} else if ("codProcessoBancaProd".equals(crit.getFieldName())) {
					codProcessoBancaProd = getTextFilterValue(crit);
				} else if("nroDocPdV".equals(crit.getFieldName())) {
					try {
						String[] nroDocPdV = getNumberFilterValue(crit);
						nroDocPdVDa = nroDocPdV[0] != null ? nroDocPdV[0] : null;
						nroDocPdVA  = nroDocPdV[1] != null ? nroDocPdV[1] : null;
					} catch(Exception e) {}
				} else if("volDocPdV".equals(crit.getFieldName())) {
					try {
						String[] volDocPdV = getNumberFilterValue(crit);
						volDocPdVDa = volDocPdV[0] != null ? volDocPdV[0] : null;
						volDocPdVA  = volDocPdV[1] != null ? volDocPdV[1] : null;
					} catch(Exception e) {}
				} else if("codiciErrWarnTrasm".equals(crit.getFieldName())) {
					codiciErrWarnTrasm = getTextFilterValue(crit);
				} else if("msgErrWarnTrasm".equals(crit.getFieldName())) {
					msgErrWarnTrasm = getTextFilterValue(crit);
				} else if("codiciErrWarnRecRdV".equals(crit.getFieldName())) {
					codiciErrWarnRecRdV = getTextFilterValue(crit);
				} else if("msgErrWarnRecRdV".equals(crit.getFieldName())) {
					msgErrWarnRecRdV = getTextFilterValue(crit);
				} else if("tempoRicezRicTrasm".equals(crit.getFieldName())) {
					try {
						String[] tempoRicezRicTrasm = getNumberFilterValue(crit);
						tempoRicezRicTrasmDa = tempoRicezRicTrasm[0] != null ? tempoRicezRicTrasm[0] : null;
						tempoRicezRicTrasmA  = tempoRicezRicTrasm[1] != null ? tempoRicezRicTrasm[1] : null;
					} catch(Exception e) {}
				} else if("tempoSenzaRicTrasm".equals(crit.getFieldName())) {
					try {
						String[] tempoSenzaRicTrasm = getNumberFilterValue(crit);
						tempoSenzaRicTrasmDa = tempoSenzaRicTrasm[0] != null ? tempoSenzaRicTrasm[0] : null;
						tempoRicezRicTrasmA  = tempoSenzaRicTrasm[1] != null ? tempoSenzaRicTrasm[1] : null;
					} catch(Exception e) {}
				} else if("tempoRicezioneRdV".equals(crit.getFieldName())) {
					try {
						String[] tempoRicezioneRdV = getNumberFilterValue(crit);
						tempoRicezioneRdVDa = tempoRicezioneRdV[0] != null ? tempoRicezioneRdV[0] : null;
						tempoRicezioneRdVA  = tempoRicezioneRdV[1] != null ? tempoRicezioneRdV[1] : null;
					} catch(Exception e) {}
				} else if("tempoSenzaRdV".equals(crit.getFieldName())) {
					try {
						String[] tempoSenzaRdV = getNumberFilterValue(crit);
						tempoSenzaRdVDa = tempoSenzaRdV[0] != null ? tempoSenzaRdV[0] : null;
						tempoSenzaRdVA  = tempoSenzaRdV[1] != null ? tempoSenzaRdV[1] : null;
					} catch(Exception e) {}
				}
			}
		}
		
		TrovaPdVFiltriXmlBean trovaPdVFiltriXmlBean = new TrovaPdVFiltriXmlBean();
		
		// IdPdV
		if (StringUtils.isNotBlank(idPdV)) {
			trovaPdVFiltriXmlBean.setIdPdV(idPdV);
		}
		// Etichetta
		if (StringUtils.isNotBlank(etichetta)) {
			trovaPdVFiltriXmlBean.setEtichetta(etichetta);
		}
		// Stati
		if (StringUtils.isNotBlank(stato)) {
			trovaPdVFiltriXmlBean.setStato(stato);
		}
		// TsUltimoAggStato
		if(tsUltimoAggStatoDa != null){
			trovaPdVFiltriXmlBean.setTsUltimoAggStatoDa(tsUltimoAggStatoDa);
		}
		if(tsUltimoAggStatoA != null){
			trovaPdVFiltriXmlBean.setTsUltimoAggStatoA(tsUltimoAggStatoA);
		}
		// DataGenerazionePdV
		if(dataGenerazionePdVDa != null){
			trovaPdVFiltriXmlBean.setDataGenerazionePdVDa(dataGenerazionePdVDa);
		}
		if(dataGenerazionePdVA != null){
			trovaPdVFiltriXmlBean.setDataGenerazionePdVA(dataGenerazionePdVA);
		}
		// CodProcessoBancaProd
		if (StringUtils.isNotBlank(codProcessoBancaProd)) {
			trovaPdVFiltriXmlBean.setCodProcessoBancaProd(codProcessoBancaProd);
		}
		// NroDocPdV
		if (StringUtils.isNotBlank(nroDocPdVDa)) {
			trovaPdVFiltriXmlBean.setNroDocPdVDa(nroDocPdVDa);
		}
		if (StringUtils.isNotBlank(nroDocPdVA)) {
			trovaPdVFiltriXmlBean.setNroDocPdVA(nroDocPdVA);
		}
		// VolDocPdV
		if (StringUtils.isNotBlank(volDocPdVDa)) {
			trovaPdVFiltriXmlBean.setVolDocPdVDa(volDocPdVDa);
		}
		if (StringUtils.isNotBlank(volDocPdVA)) {
			trovaPdVFiltriXmlBean.setVolDocPdVA(volDocPdVA);
		}
		// CodiciErrWarnTrasm
		if (StringUtils.isNotBlank(codiciErrWarnTrasm)) {
			trovaPdVFiltriXmlBean.setCodiciErrWarnTrasm(codiciErrWarnTrasm);
		}
		// MsgErrWarnTrasm
		if (StringUtils.isNotBlank(msgErrWarnTrasm)) {
			trovaPdVFiltriXmlBean.setMsgErrWarnTrasm(msgErrWarnTrasm);
		}
		// CodiciErrWarnRecRdV
		if (StringUtils.isNotBlank(codiciErrWarnRecRdV)) {
			trovaPdVFiltriXmlBean.setCodiciErrWarnRecRdV(codiciErrWarnRecRdV);
		}
		// MsgErrWarnRecRdV
		if (StringUtils.isNotBlank(msgErrWarnRecRdV)) {
			trovaPdVFiltriXmlBean.setMsgErrWarnRecRdV(msgErrWarnRecRdV);
		}
		// TempoRicezRicTrasm
		if (StringUtils.isNotBlank(tempoRicezRicTrasmDa)) {
			trovaPdVFiltriXmlBean.setTempoRicezRicTrasmDa(tempoRicezRicTrasmDa);
		}
		if (StringUtils.isNotBlank(tempoRicezRicTrasmA)) {
			trovaPdVFiltriXmlBean.setTempoRicezRicTrasmA(tempoRicezRicTrasmA);
		}
		//TempoSenzaRicTrasm
		if (StringUtils.isNotBlank(tempoSenzaRicTrasmDa)) {
			trovaPdVFiltriXmlBean.setTempoSenzaRicTrasmDa(tempoSenzaRicTrasmDa);
		}
		if (StringUtils.isNotBlank(tempoSenzaRicTrasmA)) {
			trovaPdVFiltriXmlBean.setTempoSenzaRicTrasmA(tempoSenzaRicTrasmA);
		}
		// TempoRicezioneRdV
		if (StringUtils.isNotBlank(tempoRicezioneRdVDa)) {
			trovaPdVFiltriXmlBean.setTempoRicezioneRdVDa(tempoRicezioneRdVDa);
		}
		if (StringUtils.isNotBlank(tempoRicezioneRdVA)) {
			trovaPdVFiltriXmlBean.setTempoRicezioneRdVA(tempoRicezioneRdVA);
		}
		// TempoSenzaRdV
		if (StringUtils.isNotBlank(tempoSenzaRdVDa)) {
			trovaPdVFiltriXmlBean.setTempoSenzaRdVDa(tempoSenzaRdVDa);
		}
		if (StringUtils.isNotBlank(tempoSenzaRdVA)) {
			trovaPdVFiltriXmlBean.setTempoSenzaRdVA(tempoSenzaRdVA);
		}

		return trovaPdVFiltriXmlBean;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkIntCsTrovapdvBean input = new DmpkIntCsTrovapdvBean();
		// richiesta di schedulazione del job asincrono di generazione del documento di esportazione della lista
		input.setOverflowlimitin(-2);
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		input.setColorderbyio(null);
		input.setFlgdescorderbyio(null);
		input.setFlgsenzapaginazionein(new Integer(1)); // lista non paginata
		input.setNropaginaio(null);
		input.setBachsizeio(null);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
		input.setFlgbatchsearchin(null);
		input.setFiltriio(new XmlUtilitySerializer().bindXml(buildFilter(bean.getCriteria())));
		
		DmpkIntCsTrovapdv dmpkIntCsTrovapdv = new DmpkIntCsTrovapdv();
		StoreResultBean<DmpkIntCsTrovapdvBean> output = dmpkIntCsTrovapdv.execute(getLocale(), loginBean, input);
		
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		Integer jobId = output.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), PdVXmlBean.class.getName());

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return bean;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkIntCsTrovapdvBean input = new DmpkIntCsTrovapdvBean();
		input.setOverflowlimitin(-1);
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		input.setColorderbyio(null);
		input.setFlgdescorderbyio(null);
		input.setFlgsenzapaginazionein(new Integer(1)); // lista non paginata
		input.setNropaginaio(null);
		input.setBachsizeio(null);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
		input.setFlgbatchsearchin(null);
		input.setFiltriio(new XmlUtilitySerializer().bindXml(buildFilter(bean.getCriteria())));
		
		DmpkIntCsTrovapdv dmpkIntCsTrovapdv = new DmpkIntCsTrovapdv();
		StoreResultBean<DmpkIntCsTrovapdvBean> output = dmpkIntCsTrovapdv.execute(getLocale(), loginBean, input);
		
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		// SETTO L'OUTPUT
		if (output.getResultBean().getResultout() != null) {
			bean.setNroRecordTot(output.getResultBean().getNrototrecout());
		}

		return bean;
	}
	
}