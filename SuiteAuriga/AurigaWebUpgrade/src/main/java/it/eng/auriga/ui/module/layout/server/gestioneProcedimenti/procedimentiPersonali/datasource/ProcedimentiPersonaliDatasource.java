/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfTrovalistalavoroBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.DestInvioNotifica;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.attiInLavorazione.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.SoggettiEstBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.datasource.CriteriIterNonSvoltBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiPersonali.bean.ProcedimentiPersonaliBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiPersonali.bean.ProcedimentiPersonaliXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiPersonali.bean.ProcedimentiPersonaliXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcCreator;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcCreator.AttributiName;
import it.eng.client.DmpkWfTrovalistalavoro;
import it.eng.document.function.bean.Flag;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.formati.FormatiUtil;
import it.eng.utility.ui.html.HtmlNormalizeUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "ProcedimentiPersonaliDatasource")
public class ProcedimentiPersonaliDatasource extends AurigaAbstractFetchDatasource<ProcedimentiPersonaliBean> {

	private static Logger mLogger = Logger.getLogger(ProcedimentiPersonaliDatasource.class);

	@Override
	public PaginatorBean<ProcedimentiPersonaliBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		boolean overflow = false;
		
		DmpkWfTrovalistalavoroBean input = createInputBean(false,criteria,loginBean);
	
		DmpkWfTrovalistalavoro lDmpkWfTrovalistalavoro = new DmpkWfTrovalistalavoro();
		StoreResultBean<DmpkWfTrovalistalavoroBean> lResultBean = lDmpkWfTrovalistalavoro.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
		
		if (lResultBean.isInError()){
			mLogger.error(lResultBean.getDefaultMessage());
			throw new StoreException(lResultBean);
		} else {
			overflow = manageOverflow(lResultBean.getDefaultMessage());
		}
		
		String xmlOut = lResultBean.getResultBean().getListaxmlout();
		
		List<ProcedimentiPersonaliXmlBean> lListResult = XmlListaUtility.recuperaLista(xmlOut, ProcedimentiPersonaliXmlBean.class);
		
		getSession().setAttribute(FETCH_SESSION_KEY, lListResult);
		
		List<ProcedimentiPersonaliBean> lList = new ArrayList<ProcedimentiPersonaliBean>(lListResult.size());		
		for (ProcedimentiPersonaliXmlBean lProcedimentiPersonaliXmlBean : lListResult){
			ProcedimentiPersonaliBean lProcedimentiPersonaliBean = new ProcedimentiPersonaliBean();
			BeanUtilsBean2.getInstance().copyProperties(lProcedimentiPersonaliBean, lProcedimentiPersonaliXmlBean);
			lProcedimentiPersonaliBean.setOggettoProcedimento(HtmlNormalizeUtil.getPlainXmlWithCarriageReturn(lProcedimentiPersonaliBean.getOggettoProcedimento()));
			lProcedimentiPersonaliBean.setFirmatoRispostaCedAutotutele(lProcedimentiPersonaliXmlBean.getFlgFirmatoRispostaCedAutotutele() != null && Flag.SETTED.toString().equalsIgnoreCase(lProcedimentiPersonaliXmlBean.getFlgFirmatoRispostaCedAutotutele()));
			lProcedimentiPersonaliBean.setConvertibileRispostaCedAutotutele(StringUtils.isNotBlank(lProcedimentiPersonaliXmlBean.getMimetypeRispostaCedAutotutele()) ? FormatiUtil.isConvertibile(getSession(), lProcedimentiPersonaliXmlBean.getMimetypeRispostaCedAutotutele()) : false);
			lProcedimentiPersonaliBean.setFlgPrevistaNumerazione(lProcedimentiPersonaliXmlBean.getFlgPrevistaNumerazione());
			lList.add(lProcedimentiPersonaliBean);
		}
		
		PaginatorBean<ProcedimentiPersonaliBean> lPaginatorBean = new PaginatorBean<ProcedimentiPersonaliBean>();
		lPaginatorBean.setData(lList);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? lList.size() - 1 : endRow);
		if(lResultBean.getResultBean().getNropaginaio() != null && lResultBean.getResultBean().getNropaginaio().intValue() > 0) {
			lPaginatorBean.setRowsForPage(input.getBachsizeio());
			lPaginatorBean.setTotalRows(lResultBean.getResultBean().getNrototrecout());
		} else {
			lPaginatorBean.setTotalRows(lList.size());			
		}
		lPaginatorBean.setOverflow(overflow);
		
		return lPaginatorBean;
	}
	
	private DmpkWfTrovalistalavoroBean createInputBean(boolean forNroRecordTot,AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String colOrderBy = null;
		String flgDescOrderBy = null;
		
		Integer overflowLimit = null;
		Integer bachSize = null;
		Integer nroPagina = null;
	
		String istruttoreAssegnatarioIstruttoria = null;
		String istruttoreAssegnatarioPreIstruttoria = null;
		String denominazioneRichiedente = null;
		String codFiscaleRichiedente = null;
		
		ProcedimentiPersonaliBean filter = new ProcedimentiPersonaliBean(); 
		
		buildFilterBeanFromCriteria(filter, criteria);
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){				
				if ("maxRecordVisualizzabili".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						String[] filterMaxRecordVisualizzabili = getNumberFilterValue(crit);
						overflowLimit = filterMaxRecordVisualizzabili[0] != null ? Integer.valueOf(filterMaxRecordVisualizzabili[0]) : null;
					}
				} else if ("nroRecordXPagina".equals(crit.getFieldName())) {
					//TODO PAGINAZIONE
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						String[] filterNroRecordXPagina = getNumberFilterValue(crit);
						bachSize = filterNroRecordXPagina[0] != null ? Integer.valueOf(filterNroRecordXPagina[0]) : null;
					}
				} else if ("nroPagina".equals(crit.getFieldName())) {
					//TODO PAGINAZIONE
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						String[] filterNroPagina = getNumberFilterValue(crit);
						nroPagina = filterNroPagina[0] != null ? Integer.valueOf(filterNroPagina[0]) : null;
					}
				} else if ("istruttoreAssegnatarioIstruttoria".equals(crit.getFieldName())) {
					istruttoreAssegnatarioIstruttoria = getTextFilterValue(crit);
				} else if ("istruttoreAssegnatarioPreIstruttoria".equals(crit.getFieldName())) {
					istruttoreAssegnatarioPreIstruttoria = getTextFilterValue(crit);
				} else if ("denominazioneRichiedente".equals(crit.getFieldName())) {
					denominazioneRichiedente = getTextFilterValue(crit);
				} else if ("codFiscaleRichiedente".equals(crit.getFieldName())) {
					codFiscaleRichiedente = getTextFilterValue(crit);
				}
			}
		}

				
		DmpkWfTrovalistalavoroBean input = new DmpkWfTrovalistalavoroBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		if(bachSize != null && bachSize.intValue() > 0) {
			input.setFlgsenzapaginazionein(null);
			input.setBachsizeio(bachSize);
			input.setNropaginaio(nroPagina != null ? nroPagina : new Integer(1));
		} else {
			input.setFlgsenzapaginazionein(1);
			input.setBachsizeio(overflowLimit);
			if (forNroRecordTot) {
				input.setOverflowlimitin(new Integer(-1));
			} else {
				input.setOverflowlimitin(overflowLimit);
			}
		}
		
		// Se in lista è attiva la paginazione o il limite di overflow, allora devo fare l'ordinamento lato server
		if((bachSize != null && bachSize.intValue() > 0) || (overflowLimit != null && overflowLimit.intValue() > 0)) {
			HashSet<String> setNumColonneOrdinabili = new HashSet<String>(Arrays.asList("2","3","4","5","7","10","11","12","13","14","16","18","19","25","29","30","62","63","64","65"));
			String[] colAndFlgDescOrderBy = getColAndFlgDescOrderBy(ProcedimentiPersonaliXmlBean.class, setNumColonneOrdinabili);
			colOrderBy = colAndFlgDescOrderBy[0];
			flgDescOrderBy = colAndFlgDescOrderBy[1];	
			input.setColorderbyio(colOrderBy);
			input.setFlgdescorderbyio(flgDescOrderBy);
		}
	
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		CriteriAvanzati scCriteriAvanzati = new CriteriAvanzati();		
		scCriteriAvanzati.setFlgSoloProcedimentiAmm("1");
		scCriteriAvanzati.setFlgSoloEseguibili("1");
		
		// Istruttore assegnatario (ISTRUTTORIA)
		if (StringUtils.isNotEmpty(istruttoreAssegnatarioIstruttoria)){
			scCriteriAvanzati.setIdUserAssegnatarioIstruttoria(istruttoreAssegnatarioIstruttoria);
		}
		
		// Istruttore assegnatario (PRE-ISTRUTTORIA)
		if (StringUtils.isNotEmpty(istruttoreAssegnatarioPreIstruttoria)){
			scCriteriAvanzati.setIdUserAssegnatarioPreIstruttoria(istruttoreAssegnatarioPreIstruttoria);
		}
		
		// Denominazione richiedente
		if (StringUtils.isNotEmpty(denominazioneRichiedente)){
			scCriteriAvanzati.setDenominazioneRichiedente(denominazioneRichiedente);
		}
				
		// Cod. fiscale richiedente
		if (StringUtils.isNotEmpty(codFiscaleRichiedente)){
			scCriteriAvanzati.setCodFiscaleRichiedente(codFiscaleRichiedente);
		}
				
		//Data presentazione
		if (filter.getDataPresentazioneStart() != null){
			scCriteriAvanzati.setDtPresentazioneInstanzaDa(filter.getDataPresentazioneStart());
		}
		if (filter.getDataPresentazioneEnd() != null){
			scCriteriAvanzati.setDtPresentazioneIstanzaA(filter.getDataPresentazioneEnd());
		}
		input.setCriteriavanzatiin(lXmlUtilitySerializer.bindXml(scCriteriAvanzati));
		
		//Tipo procedimento
		input.setIdprocesstypeio(StringUtils.isNotEmpty(filter.getIdProcessType())?new BigDecimal(filter.getIdProcessType()):null);

		//Oggetto
		input.setOggettoio(criteria.getCriterionByFieldName("oggetto")!=null?(String)criteria.getCriterionByFieldName("oggetto").getValue():null);
		
		//Data di avvio
		SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(FMT_STD_DATA);
		if (filter.getDataDiAvvioStart()!=null){
			input.setDtavviodaio(lSimpleDateFormat.format(filter.getDataDiAvvioStart()));
		}
		if (filter.getDataDiAvvioEnd()!=null){
			input.setDtavvioaio(lSimpleDateFormat.format(filter.getDataDiAvvioEnd()));
		}
		
		//Stato
		if (StringUtils.isNotEmpty(filter.getStato())){
			input.setFlgstatoprocio(filter.getStato());
		}
		
		//Avviati da me
		if (filter.getAvviatiDaMe()!=null && filter.getAvviatiDaMe()){
			input.setFlgavviouserlavio(1);
		}
		
		//In fase
		if (StringUtils.isNotEmpty(filter.getInFase())){
			input.setWfnamefasecorrio(filter.getInFase());
		}
		
		//Eseguibile task
		if (StringUtils.isNotEmpty(filter.getEseguibileTask())){
			input.setWfnameattesegio(filter.getEseguibileTask());
		}
		
		//Criteri iter svolto
		List<XmlListaSimpleBean> lListCriteriIterSvolto = new ArrayList<XmlListaSimpleBean>();
		//Svolta fase
		if (StringUtils.isNotEmpty(filter.getSvoltaFase())){
			String[] lStrings = filter.getSvoltaFase().split(";");
			for (String lStrFilter : lStrings){
				XmlListaSimpleBean lXmlListaSimpleBean = new XmlListaSimpleBean();
				lXmlListaSimpleBean.setKey("F");
				lXmlListaSimpleBean.setValue(lStrFilter);
				lListCriteriIterSvolto.add(lXmlListaSimpleBean);
			}
		}
		//Effettuato task
		if (StringUtils.isNotEmpty(filter.getEffettuatoTask())){
			String[] lStrings = filter.getEffettuatoTask().split(";");
			for (String lStrFilter : lStrings){
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
		//Da iniziare fase
		if (StringUtils.isNotEmpty(filter.getDaIniziareFase())){
			String[] lStrings = filter.getDaIniziareFase().split(";");
			for (String lStrFilter : lStrings){
				CriteriIterNonSvoltBean lCriteriIterNonSvoltBean = new CriteriIterNonSvoltBean();
				lCriteriIterNonSvoltBean.setFaseTask("F");
				lCriteriIterNonSvoltBean.setValue(lStrFilter);
				lCriteriIterNonSvoltBean.setTipo("1");
				lListCriteriIterNonSvolto.add(lCriteriIterNonSvoltBean);
			}
		}
		//Da completare fase
		if (StringUtils.isNotEmpty(filter.getDaCompletareFase())){
			String[] lStrings = filter.getDaCompletareFase().split(";");
			for (String lStrFilter : lStrings){
				CriteriIterNonSvoltBean lCriteriIterNonSvoltBean = new CriteriIterNonSvoltBean();
				lCriteriIterNonSvoltBean.setFaseTask("F");
				lCriteriIterNonSvoltBean.setValue(lStrFilter);
				lCriteriIterNonSvoltBean.setTipo("0");
				lListCriteriIterNonSvolto.add(lCriteriIterNonSvoltBean);
			}
		}
		//Da effettuare task
		if (StringUtils.isNotEmpty(filter.getDaEffettuareTask())){
			String[] lStrings = filter.getDaEffettuareTask().split(";");
			for (String lStrFilter : lStrings){
				CriteriIterNonSvoltBean lCriteriIterNonSvoltBean = new CriteriIterNonSvoltBean();
				lCriteriIterNonSvoltBean.setFaseTask("AE");
				lCriteriIterNonSvoltBean.setValue(lStrFilter);
				lCriteriIterNonSvoltBean.setTipo("1");
				lListCriteriIterNonSvolto.add(lCriteriIterNonSvoltBean);
			}
		}
		String criterIterNonSvolto = lXmlUtilitySerializer.bindXmlList(lListCriteriIterNonSvolto);
		input.setCriteriiternonsvoltoio(criterIterNonSvolto);
		
		//Scadenza
		if (filter.getScadenza()!=null){
			input.setTpscadio(filter.getScadenza().getTipoScadenza());
			input.setScadentronggio(filter.getScadenza().getEntroGiorni());
			input.setScaddaminnggio(filter.getScadenza().getTrascorsaDaGiorni());
			if (filter.getScadenza().getSoloSeTermineScadenzaNonAvvenuto()!=null && filter.getScadenza().getSoloSeTermineScadenzaNonAvvenuto())
				input.setFlgnoscadconevtfinio(1);
		}
		
		return input;
	}

	public AttributiProcBean buildNumber(AttributiName pAttributiName, AdvancedCriteria criteria) {
		return AttributiProcCreator.buildNumber(pAttributiName,
				criteria.getCriterionByFieldName(pAttributiName.getGuiValueFilter()));
	}

	@Override
	public Map<String, ErrorBean> validate(ProcedimentiPersonaliBean bean) throws Exception {
		return null;
	}

	@Override
	public String getNomeEntita() {
		return "procedimenti_personali";
	}

	protected List<DestInvioNotifica> getListaSceltaOrganigrammaFilterValue(Criterion crit) {
		
		if (crit != null && crit.getValue() != null) {
			ArrayList lista = new ArrayList<DestInvioNotifica>();
			if (crit.getValue() instanceof Map) {
				Map map = (Map) crit.getValue();
				for (Map val : (ArrayList<Map>) map.get("listaOrganigramma")) {
					DestInvioNotifica destInvioNotifica = new DestInvioNotifica();
					String chiave = (String) val.get("organigramma");
					destInvioNotifica.setTipo(chiave.substring(0, 2));
					destInvioNotifica.setId(chiave.substring(2));
					if (val.get("flgIncludiSottoUO") != null && ((Boolean) val.get("flgIncludiSottoUO"))) {
						destInvioNotifica.setFlgIncludiSottoUO(Flag.SETTED);
					}
					if (val.get("flgIncludiScrivanie") != null && ((Boolean) val.get("flgIncludiScrivanie"))) {
						destInvioNotifica.setFlgIncludiScrivanie(Flag.SETTED);
					}
					lista.add(destInvioNotifica);
				}
			} else {
				String value = getTextFilterValue(crit);
				if (StringUtils.isNotBlank(value)) {
					StringSplitterServer st = new StringSplitterServer(value, ";");
					while (st.hasMoreTokens()) {
						DestInvioNotifica destInvioNotifica = new DestInvioNotifica();
						String chiave = st.nextToken();
						destInvioNotifica.setTipo(chiave.substring(0, 2));
						destInvioNotifica.setId(chiave.substring(2));
						lista.add(destInvioNotifica);
					}
				}
			}
			return lista;
		}
		return null;
	}

	@Override
	public ProcedimentiPersonaliBean get(ProcedimentiPersonaliBean bean) throws Exception {
		return bean;
	}

	@Override
	public ProcedimentiPersonaliBean add(ProcedimentiPersonaliBean bean) throws Exception {
		return bean;
	}

	@Override
	public ProcedimentiPersonaliBean remove(ProcedimentiPersonaliBean bean) throws Exception {
		return bean;
	}

	@Override
	public ProcedimentiPersonaliBean update(ProcedimentiPersonaliBean bean, ProcedimentiPersonaliBean oldvalue)
			throws Exception {
		return bean;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		DmpkWfTrovalistalavoroBean input = createInputBean(true,criteria,loginBean);
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

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), ProcedimentiPersonaliXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(),  ProcedimentiPersonaliXmlBeanDeserializationHelper.class);

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
		
		DmpkWfTrovalistalavoroBean input = createInputBean(true,criteria,loginBean);
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
	
}