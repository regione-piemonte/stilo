/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocIuattoautannregBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocLoaddettattoautannregBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Registrazione;
import it.eng.auriga.ui.module.layout.server.attiAutorizzazione.datasource.bean.AttiAutorizzazioneAnnRegBean;
import it.eng.auriga.ui.module.layout.server.attiAutorizzazione.datasource.bean.AttiAutorizzazioneAnnRegXmlBean;
import it.eng.auriga.ui.module.layout.server.attiAutorizzazione.datasource.bean.AttiAutorizzazioneAnnRegXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.attiAutorizzazione.datasource.bean.RegDaAnnullareBean;
import it.eng.auriga.ui.module.layout.server.attiAutorizzazione.datasource.bean.XmlDatiAttoOutBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.client.AurigaService;
import it.eng.client.DmpkRegistrazionedocIuattoautannreg;
import it.eng.client.DmpkRegistrazionedocLoaddettattoautannreg;
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
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

@Datasource(id="AttiAutorizzazioneAnnRegDatasource")
public class AttiAutorizzazioneAnnRegDatasource extends AurigaAbstractFetchDatasource<AttiAutorizzazioneAnnRegBean>{
	
	@Override
	public PaginatorBean<AttiAutorizzazioneAnnRegBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		boolean overflow = false;
		
		// creo il bean per la ricerca, ma inizializzo anche le variabili che mi servono per determinare se effettivamente posso eseguire il recupero dei dati
		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(criteria, loginBean);
		
		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findrepositoryobject(
				getLocale(), 
				loginBean, 
				lFindRepositoryObjectBean
			).getList();
		} catch(Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		String xmlResultSetOut = (String) resFinder.get(0);
		
		String errorMessageOut = null;
		
		if (resFinder.size() > 5){ 
			errorMessageOut = (String) resFinder.get(5);
		}
		
		overflow = manageOverflow(errorMessageOut);
		
		// Conversione ListaRisultati ==> EngResultSet
		List<AttiAutorizzazioneAnnRegBean> data = new ArrayList<AttiAutorizzazioneAnnRegBean>();
		
		if (xmlResultSetOut != null){
			data = XmlListaUtility.recuperaLista(xmlResultSetOut, AttiAutorizzazioneAnnRegBean.class, new AttiAutorizzazioneAnnRegXmlBeanDeserializationHelper(createRemapConditionsMap()));
		}			
			
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);
				
		PaginatorBean<AttiAutorizzazioneAnnRegBean> lPaginatorBean = new PaginatorBean<AttiAutorizzazioneAnnRegBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		
		return lPaginatorBean;
	}	
	
	public AttiAutorizzazioneAnnRegBean inserisciRegInAttoAut(AttiAutorizzazioneAnnRegBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocIuattoautannregBean input = new DmpkRegistrazionedocIuattoautannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudattoio(bean.getIdAtto() != null ? new BigDecimal(bean.getIdAtto()) : null);
		input.setOggettoin(bean.getOggetto());
		input.setFlgregdaannullarein("I");
		input.setXmlregdaannullarein(creaXmlRegDaAnnullare(bean.getListaRegDaAnnullare()));
		
		DmpkRegistrazionedocIuattoautannreg dmpkRegistrazionedocIuattoautannreg = new DmpkRegistrazionedocIuattoautannreg();
		StoreResultBean<DmpkRegistrazionedocIuattoautannregBean> output = dmpkRegistrazionedocIuattoautannreg.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;		
	}
	
	public AttiAutorizzazioneAnnRegBean chiudiAtto(AttiAutorizzazioneAnnRegBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocIuattoautannregBean input = new DmpkRegistrazionedocIuattoautannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudattoio(bean.getIdAtto() != null ? new BigDecimal(bean.getIdAtto()) : null);
		input.setOggettoin(bean.getOggetto());
		input.setFlgattochiusoin(new Integer(1));
		input.setFlgregdaannullarein("I");
		input.setXmlregdaannullarein(null);
		
		DmpkRegistrazionedocIuattoautannreg dmpkRegistrazionedocIuattoautannreg = new DmpkRegistrazionedocIuattoautannreg();
		StoreResultBean<DmpkRegistrazionedocIuattoautannregBean> output = dmpkRegistrazionedocIuattoautannreg.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;		
	}
	
	@Override
	public AttiAutorizzazioneAnnRegBean get(AttiAutorizzazioneAnnRegBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocLoaddettattoautannregBean input = new DmpkRegistrazionedocLoaddettattoautannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setIdudattoio(new BigDecimal(bean.getIdAtto()));
				
		DmpkRegistrazionedocLoaddettattoautannreg loaddettattoautannreg = new DmpkRegistrazionedocLoaddettattoautannreg();
		StoreResultBean<DmpkRegistrazionedocLoaddettattoautannregBean> output = loaddettattoautannreg.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}		
		
		AttiAutorizzazioneAnnRegBean result = new AttiAutorizzazioneAnnRegBean();		
		
		if(StringUtils.isNotBlank(output.getResultBean().getXmldatiattoout())) {
			XmlDatiAttoOutBean scXmlDatiAtto = new XmlDatiAttoOutBean();
	        XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
	        scXmlDatiAtto = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getXmldatiattoout(), XmlDatiAttoOutBean.class);      	       
	        if(scXmlDatiAtto != null) {
				result.setIdAtto(bean.getIdAtto());	
	        	result.setNroBozza(scXmlDatiAtto.getNroRegNumIniziale());	        		
	        	result.setTsRegBozza(scXmlDatiAtto.getTsRegistrazioneRegNumIniziale() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(scXmlDatiAtto.getTsRegistrazioneRegNumIniziale()) : null);
	        	result.setDesUteBozza(scXmlDatiAtto.getDesUserRegNumIniziale());
	        	result.setNroAtto(scXmlDatiAtto.getNroRegNumFinale());	        		
	        	result.setTsRegAtto(scXmlDatiAtto.getTsRegistrazioneRegNumFinale() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(scXmlDatiAtto.getTsRegistrazioneRegNumFinale()) : null);
	        	result.setDesUteAtto(scXmlDatiAtto.getDesUserRegNumFinale());
	        	result.setOggetto(scXmlDatiAtto.getDesOgg());
	        	result.setListaRegDaAnnullare(scXmlDatiAtto.getRegistrazioniAutorizzate());
	        	result.setFlgAttoChiuso(StringUtils.isNotBlank(scXmlDatiAtto.getFlgAttoChiuso()) ? (scXmlDatiAtto.getFlgAttoChiuso()) : null);
			}					
		}								
				
		return result;		
	}
	
	@Override
	public AttiAutorizzazioneAnnRegBean update(AttiAutorizzazioneAnnRegBean bean, AttiAutorizzazioneAnnRegBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocIuattoautannregBean input = new DmpkRegistrazionedocIuattoautannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudattoio(StringUtils.isNotBlank(bean.getIdAtto()) ? new BigDecimal(bean.getIdAtto()) : null);		
		input.setOggettoin(bean.getOggetto());		
		input.setFlgregdaannullarein("C");
		input.setXmlregdaannullarein(creaXmlRegDaAnnullare(bean.getListaRegDaAnnullare()));
		
		DmpkRegistrazionedocIuattoautannreg dmpkRegistrazionedocIuattoautannreg = new DmpkRegistrazionedocIuattoautannreg();		
		StoreResultBean<DmpkRegistrazionedocIuattoautannregBean> output = dmpkRegistrazionedocIuattoautannreg.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;		
	}

	@Override
	public AttiAutorizzazioneAnnRegBean add(AttiAutorizzazioneAnnRegBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocIuattoautannregBean input = new DmpkRegistrazionedocIuattoautannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setOggettoin(bean.getOggetto());		
		input.setFlgregdaannullarein("C");		
		input.setXmlregdaannullarein(null);
		
		DmpkRegistrazionedocIuattoautannreg dmpkRegistrazionedocIuattoautannreg = new DmpkRegistrazionedocIuattoautannreg();
		StoreResultBean<DmpkRegistrazionedocIuattoautannregBean> output = dmpkRegistrazionedocIuattoautannreg.execute(getLocale(), loginBean, input);
						
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		bean.setIdAtto((String.valueOf(output.getResultBean().getIdudattoio())));
		
		return bean;
	}
		
	@Override
	public AttiAutorizzazioneAnnRegBean remove(AttiAutorizzazioneAnnRegBean bean) throws Exception {
		return null;
	}	
	
	private String creaXmlRegDaAnnullare(List<RegDaAnnullareBean> listaRegDaAnnullare) throws Exception {
		List<RegDaAnnullareBean> lista  = new ArrayList<RegDaAnnullareBean>();		
		if(listaRegDaAnnullare != null && !listaRegDaAnnullare.isEmpty()) {
			for(RegDaAnnullareBean regDaAnnullare : listaRegDaAnnullare) {
				/* Se valorizzato ID_UD la colonna 4 và lasciata vuota e và valorizzata solo colonna 7
				*  altrimenti vanno valorizzate entrambe
				*/
				if(StringUtils.isNotBlank(regDaAnnullare.getIdUd())) {
					regDaAnnullare.setNroReg(null);
					lista.add(regDaAnnullare);
				} else {
					lista.add(regDaAnnullare);
				}
			}								
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(lista);
	}

	
	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean filterBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = filterBean.getCriteria();

		// creo il bean per la ricerca, ma inizializzo anche le variabili che mi servono per determinare se effettivamente posso eseguire il recupero dei dati
		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(criteria, loginBean);

		lFindRepositoryObjectBean.setColsToReturn("1");
		lFindRepositoryObjectBean.setOverflowlimitin(-1);

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		String numTotRecOut = (String) resFinder.get(1);

		String errorMessageOut = null;

		if (resFinder.size() > 5) {
			errorMessageOut = (String) resFinder.get(5);
		}

		if (errorMessageOut != null && !"".equals(errorMessageOut)) {
			addMessage(errorMessageOut, "", MessageType.WARNING);
		}

		NroRecordTotBean retValue = new NroRecordTotBean();
		retValue.setNroRecordTot(Integer.valueOf(numTotRecOut));
		
		return retValue;
	}
	
	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
	
		AdvancedCriteria criteria = bean.getCriteria();

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(criteria, loginBean);

		lFindRepositoryObjectBean.setOverflowlimitin(-2);

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		String errorMessageOut = null;

		if (resFinder.size() > 5) {
			errorMessageOut = (String) resFinder.get(5);
		}

		if (errorMessageOut != null && !"".equals(errorMessageOut)) {
			addMessage(errorMessageOut, "", MessageType.WARNING);
		}

		// imposto l'id del job creato
		Integer jobId = Integer.valueOf((String) resFinder.get(6));
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), AttiAutorizzazioneAnnRegXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), AttiAutorizzazioneAnnRegXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Richiesta di esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return null;
	}

	
	protected FindRepositoryObjectBean createFindRepositoryObjectBean(AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {

		String restringiAttiAutAnnReg = StringUtils.isNotBlank(getExtraparams().get("restringiAttiAutAnnReg")) ? getExtraparams().get("restringiAttiAutAnnReg") : "tutti";
		
		String nroBozzaDa = null;
		String nroBozzaA = null;
		Date dtCreazioneDa = null;
		Date dtCreazioneA = null;
		String annoCreazioneDa = null;
		String annoCreazioneA = null;
		String colsToReturn = "2,4,14,15,18,54,201";
		
		String formatoEstremiReg = "<ANNO>/<NRO> del <DATA>";
		
		CriteriAvanzati scCriteriAvanzati = new CriteriAvanzati();
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("nroBozza".equals(crit.getFieldName())) {
					String[] estremiNroBozza = getNumberFilterValue(crit);
					nroBozzaDa = estremiNroBozza[0] != null ? estremiNroBozza[0] : null;
					nroBozzaA  = estremiNroBozza[1] != null ? estremiNroBozza[1] : null;
					
				} else if ("dtCreazione".equals(crit.getFieldName())) {
					Date[] estremiDataCreazione = getDateFilterValue(crit);
					if (dtCreazioneDa != null) {
						dtCreazioneDa = dtCreazioneDa.compareTo(estremiDataCreazione[0]) < 0 ? estremiDataCreazione[0] : dtCreazioneDa;
					} else {
						dtCreazioneDa = estremiDataCreazione[0];
					}
					if (dtCreazioneA != null) {
						dtCreazioneA = dtCreazioneA.compareTo(estremiDataCreazione[1]) > 0 ? estremiDataCreazione[1] : dtCreazioneA;
					} else {
						dtCreazioneA = estremiDataCreazione[1];
					}					
				} else if ("annoCreazione".equals(crit.getFieldName())) {
					String[] estremiAnnoCreazione = getNumberFilterValue(crit);
					annoCreazioneDa = estremiAnnoCreazione[0] != null ? estremiAnnoCreazione[0] : null;
					annoCreazioneA  = estremiAnnoCreazione[1] != null ? estremiAnnoCreazione[1] : null;
				}  
			}
		}
		
		// Data creazione, N° bozza atto, Anno creazione		
		List<Registrazione> listaRegistrazioni = new ArrayList<Registrazione>();
		if (  (StringUtils.isNotBlank(annoCreazioneDa)) || (StringUtils.isNotBlank(annoCreazioneA)) ||
			  (StringUtils.isNotBlank(nroBozzaDa))  || (StringUtils.isNotBlank(nroBozzaA)) || 
			  (dtCreazioneDa != null) || (dtCreazioneA != null)
		   ) {
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria("I");
			registrazione.setSigla(null);
			registrazione.setAnno(null);
			registrazione.setNumeroDa(nroBozzaDa);
			registrazione.setNumeroA(nroBozzaA);
			registrazione.setDataDa(dtCreazioneDa);
			registrazione.setDataA(dtCreazioneA);
			registrazione.setAnnoDa(annoCreazioneDa);
			registrazione.setAnnoA(annoCreazioneA);
			listaRegistrazioni.add(registrazione);
		}
		scCriteriAvanzati.setRegistrazioni(listaRegistrazioni);
        scCriteriAvanzati.setRestringiAttiAutAnnReg(restringiAttiAutAnnReg);
        
        XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
        String advancedFilters = lXmlUtilitySerializer.bindXml(scCriteriAvanzati);         
        
		FindRepositoryObjectBean lFindRepositoryObjectBean = new FindRepositoryObjectBean();
		lFindRepositoryObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());
		lFindRepositoryObjectBean.setFlgUdFolder("U");
		lFindRepositoryObjectBean.setAdvancedFilters(advancedFilters);
		lFindRepositoryObjectBean.setFlgSenzaPaginazione(new Integer(1));
		lFindRepositoryObjectBean.setColsToReturn(colsToReturn);
		lFindRepositoryObjectBean.setFormatoEstremiReg(formatoEstremiReg);
		return lFindRepositoryObjectBean;
	}
	
	private Map<String, String> createRemapConditionsMap() {
		return new HashMap<String, String>();
	}
}