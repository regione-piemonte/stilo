/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGetbarcodedacapofilapraticaBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGetetichettaregBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGetinfoxetichetteregBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrodigregBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrospecxtipoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.client.DmpkRegistrazionedocGetbarcodedacapofilapratica;
import it.eng.client.DmpkRegistrazionedocGetetichettareg;
import it.eng.client.DmpkRegistrazionedocGetinfoxetichettereg;
import it.eng.client.DmpkRegistrazionedocGettimbrodigreg;
import it.eng.client.DmpkRegistrazionedocGettimbrospecxtipo;
import it.eng.document.function.bean.Flag;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "PreparaValoriEtichettaDatasource")
public class PreparaValoriEtichettaDatasource extends AbstractServiceDataSource<PreparaEtichettaBean, PreparaEtichettaBean> {

	private static Logger mLogger = Logger.getLogger(PreparaValoriEtichettaDatasource.class);
	
	@Override
	public PreparaEtichettaBean call(PreparaEtichettaBean bean) throws Exception {
		
		if (StringUtils.isNotBlank(bean.getNroEtichette())) {
			if (bean.getNroEtichette().contains(".")) {
				String[] valSize = bean.getNroEtichette().split("\\.");
				bean.setNroEtichette(valSize[0]);
			}	
		} else {
			// se arrivo dalla stampa automatica e salto le opzioni di stampa il numero di copie è sempre 1
			bean.setNroEtichette("1");
		}
		
		int nroCopie = Integer.parseInt(bean.getNroEtichette());
		 
		List<XmlInfoStampaBean> listaInfoXEtichetteReg = getInfoXEtichetteReg(bean);
		
		List<EtichettaBean> etichette = new ArrayList<EtichettaBean>();
		if (listaInfoXEtichetteReg != null) {
			for (int i = 0; i < listaInfoXEtichetteReg.size(); i++) {
				
				XmlInfoStampaBean lXmlInfoStampaBean = listaInfoXEtichetteReg.get(i);
				
				int nroAllegato = 0;
				if(StringUtils.isNotBlank(lXmlInfoStampaBean.getNroAllegato())) {
					nroAllegato = Integer.parseInt(lXmlInfoStampaBean.getNroAllegato());
				}
				
				DmpkRegistrazionedocGetetichettareg store = new DmpkRegistrazionedocGetetichettareg();
				DmpkRegistrazionedocGetetichettaregBean input = new DmpkRegistrazionedocGetetichettaregBean();
				input.setIdudio(new BigDecimal(bean.getIdUd()));
				input.setNroallegatoin(nroAllegato);
				
				XmlOpzioniStampaBean lXmlOpzioniStampaBean = new XmlOpzioniStampaBean();
				if(lXmlInfoStampaBean.getFlgRicevutaXMittente() != null && "1".equals(lXmlInfoStampaBean.getFlgRicevutaXMittente())) {
					lXmlOpzioniStampaBean.setFlgRicevutaXMittente(Flag.SETTED);	
					lXmlOpzioniStampaBean.setFlgHideBarcode(Flag.SETTED);
				} else {
					lXmlOpzioniStampaBean.setFlgRicevutaXMittente(Flag.NOT_SETTED);
					lXmlOpzioniStampaBean.setFlgHideBarcode(bean.getFlgHideBarcode() != null && bean.getFlgHideBarcode() ? Flag.SETTED : Flag.NOT_SETTED);
				}
				
				if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_ETICHETTA_ORIG_COPIA")) {
					lXmlOpzioniStampaBean.setNotazioneCopiaOriginale(lXmlInfoStampaBean.getNotazioneCopiaOriginale());
				}

				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setXmlopzionistampain(lXmlUtilitySerializer.bindXml(lXmlOpzioniStampaBean));	

				StoreResultBean<DmpkRegistrazionedocGetetichettaregBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
						input);
				if (output.isInError()) {
					mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
					throw new StoreException(output);
				}
				
				EtichettaBean lEtichettaBean = new EtichettaBean();
				lEtichettaBean.setNumeroAllegato(nroAllegato);
				
				if(bean.getIsEtichettaFromRepe() != null && bean.getIsEtichettaFromRepe()){
					if ((output.getResultBean().getTestoinchiaroout() != null) && (bean.getIsEtichettaFromModAss() == null || bean.getIsEtichettaFromModAss())){
						lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
					}
					if (output.getResultBean().getTestoinchiaro2aregout() != null) {
						lEtichettaBean.setTestoRepertorio(output.getResultBean().getTestoinchiaro2aregout().replaceAll("\\n", "\\|\\*\\|"));
					}
				} else {
					if ((output.getResultBean().getTestoinchiaroout() != null) && (bean.getFlgSegnRegPrincipale() != null && bean.getFlgSegnRegPrincipale())){
						lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
					}
					lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
					lEtichettaBean.setTestoBarcode(output.getResultBean().getTestoinchiarobarcodeout());
					if ((output.getResultBean().getTestoinchiaro2aregout() != null) && (bean.getFlgSegnRegSecondaria() != null && bean.getFlgSegnRegSecondaria())){
						lEtichettaBean.setTestoRepertorio(output.getResultBean().getTestoinchiaro2aregout().replaceAll("\\n", "\\|\\*\\|"));
					}
				}
				
				for(int n = 0; n < nroCopie; n++) {
					etichette.add(lEtichettaBean);
				}
			}
		}
		
		bean.setEtichette(etichette);
		bean.setNroEtichette(String.valueOf(etichette.size()));
		
		return bean;
	}

	/*
	XMLInfoOut: XML lista con i dati per le etichette da stampare
	Ogni riga contiene le colonne:
	-- 1) N.ro etichetta (da 1 in su)
	-- 2) N.ro allegato (se primario è vuoto)
	-- 3) Notazione: ORIGINALE/COPIA (può non essere valorizzato)
	-- 4) Flag ricevuta per esibente: valori 1/0
	 */

	private List<XmlInfoStampaBean> getInfoXEtichetteReg(PreparaEtichettaBean bean) throws Exception {

		DmpkRegistrazionedocGetinfoxetichettereg store = new DmpkRegistrazionedocGetinfoxetichettereg();
		DmpkRegistrazionedocGetinfoxetichetteregBean input = new DmpkRegistrazionedocGetinfoxetichetteregBean();
		input.setIdudin(new BigDecimal(bean.getIdUd()));
		
		XmlOpzioniStampaBean lXmlOpzioniStampaBean = new XmlOpzioniStampaBean();
		lXmlOpzioniStampaBean.setFlgRicevutaXMittente(bean.getFlgRicevutaXMittente() != null && bean.getFlgRicevutaXMittente() ? Flag.SETTED : Flag.NOT_SETTED);
		lXmlOpzioniStampaBean.setFlgPrimario(bean.getFlgPrimario() != null && bean.getFlgPrimario() ? Flag.SETTED : Flag.NOT_SETTED);
		lXmlOpzioniStampaBean.setFlgAllegati(bean.getFlgAllegati() != null && bean.getFlgAllegati() ? Flag.SETTED : Flag.NOT_SETTED);
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_ETICHETTA_ORIG_COPIA")) {
			lXmlOpzioniStampaBean.setNotazioneCopiaOriginale(bean.getNotazioneCopiaOriginale());
		}
		lXmlOpzioniStampaBean.setFlgHideBarcode(bean.getFlgHideBarcode() != null && bean.getFlgHideBarcode() ? Flag.SETTED : Flag.NOT_SETTED);

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setXmlopzionistampain(lXmlUtilitySerializer.bindXml(lXmlOpzioniStampaBean));
		
		StoreResultBean<DmpkRegistrazionedocGetinfoxetichetteregBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
				input);
		if (output.isInError()) {
			mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
			throw new StoreException(output);
		}
		output.getResultBean().getNroetichetteout(); // ATTENZIONE: questo output non indica il numero di copie da fare per ogni etichetta, ma il numero di chiamate da fare alla GetEtichettaReg, a seconda delle opzioni selezionate 
		
		if(StringUtils.isNotBlank(output.getResultBean().getXmlinfoout())) {
			return XmlListaUtility.recuperaLista(output.getResultBean().getXmlinfoout(), XmlInfoStampaBean.class);	
		}
		
		return null;
	}
		
	public PreparaEtichettaBean getEtichettaSingoloAllegato(PreparaEtichettaBean bean) throws Exception {
		
		if (StringUtils.isNotBlank(bean.getNroEtichette())) {
			if (bean.getNroEtichette().contains(".")) {
				String[] valSize = bean.getNroEtichette().split("\\.");
				bean.setNroEtichette(valSize[0]);
			}	
		} else {
			// Se arrivo dalla stampa automatica e salto le opzioni di stampa il numero di copie è sempre 1
			bean.setNroEtichette("1");
		}
		
		int nroCopie = Integer.parseInt(bean.getNroEtichette());
		
		int nroAllegato = 0;
		if(StringUtils.isNotBlank(bean.getNrAllegato())) {
			nroAllegato = Integer.parseInt(bean.getNrAllegato());
		}
		
		DmpkRegistrazionedocGetetichettareg store = new DmpkRegistrazionedocGetetichettareg();
		DmpkRegistrazionedocGetetichettaregBean input = new DmpkRegistrazionedocGetetichettaregBean();
		input.setIdudio(new BigDecimal(bean.getIdUd()));
		input.setNroallegatoin(nroAllegato);
		
		XmlOpzioniStampaBean lXmlOpzioniStampaBean = new XmlOpzioniStampaBean();
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_ETICHETTA_ORIG_COPIA")) {			
			lXmlOpzioniStampaBean.setNotazioneCopiaOriginale(bean.getNotazioneCopiaOriginale());
		}
		
		lXmlOpzioniStampaBean.setFlgHideBarcode(bean.getFlgHideBarcode() != null && bean.getFlgHideBarcode() ? Flag.SETTED : Flag.NOT_SETTED);

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setXmlopzionistampain(lXmlUtilitySerializer.bindXml(lXmlOpzioniStampaBean));	

		StoreResultBean<DmpkRegistrazionedocGetetichettaregBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
				input);
		if (output.isInError()) {
			throw new StoreException(output);
		}
		
		EtichettaBean lEtichettaBean = new EtichettaBean();
		lEtichettaBean.setNumeroAllegato(nroAllegato);
		if(bean.getFlgSegnRegPrincipale() != null && bean.getFlgSegnRegPrincipale()){
			lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
		}
		lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
		lEtichettaBean.setTestoBarcode(output.getResultBean().getTestoinchiarobarcodeout());
		if(output.getResultBean().getTestoinchiaro2aregout() != null && bean.getFlgSegnRegSecondaria() != null && bean.getFlgSegnRegSecondaria()){
			lEtichettaBean.setTestoRepertorio(output.getResultBean().getTestoinchiaro2aregout().replaceAll("\\n", "\\|\\*\\|"));
		}
		
		List<EtichettaBean> etichette = new ArrayList<EtichettaBean>();
		for(int n = 0; n < nroCopie; n++) {
			etichette.add(lEtichettaBean);
		}
		
		bean.setEtichette(etichette);
		bean.setNroEtichette(String.valueOf(etichette.size()));
		
		return bean;
	}
		
	/**
	 *  DATI SEGNATURA
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public PreparaEtichettaBean getEtichettaDatiSegnatura(PreparaEtichettaBean bean) throws Exception {
		
		PreparaEtichettaBean result = new PreparaEtichettaBean();
		List<EtichettaBean> etichette = new ArrayList<EtichettaBean>();
		
		
		Boolean isMultiple = getExtraparams() != null && getExtraparams().get("isMultiple") != null &&
				"true".equalsIgnoreCase(getExtraparams().get("isMultiple")) ? true : false;
		
		String provenienza = getExtraparams() != null && getExtraparams().get("provenienza") != null &&
				"A".equalsIgnoreCase(getExtraparams().get("provenienza")) ? "A" : "";
		
		String posizione = getExtraparams() != null && getExtraparams().get("posizione") != null &&
				"P".equalsIgnoreCase(getExtraparams().get("posizione")) ? "P" : "";
		
		Integer nroEtichette = getExtraparams() != null && getExtraparams().get("nroEtichette") != null ?
				Integer.valueOf(getExtraparams().get("nroEtichette")) : 0;
		
		//ETICHETTA MULTIPLA
		if(isMultiple){
			int size = nroEtichette;
			//ETICHETTA MULTIPLA DA ALLEGATO
			if("A".equals(provenienza)){
				//ETICHETTA MULTIPLA NR DOCUMENTO CON POSIZIONE
				if(posizione != null && "P".equals(posizione)){
					Integer nrAllegatoTemp = Integer.valueOf(bean.getNrAllegato());
					Integer cont = 0;
					while(cont < size){
						EtichettaBean lEtichettaBean = new EtichettaBean();
						
						if(bean.getBarcodePraticaPregressa() != null && "true".equals(bean.getBarcodePraticaPregressa())){
							
							DmpkRegistrazionedocGetbarcodedacapofilapraticaBean input = new DmpkRegistrazionedocGetbarcodedacapofilapraticaBean();
							DmpkRegistrazionedocGetbarcodedacapofilapratica store = new DmpkRegistrazionedocGetbarcodedacapofilapratica();
							input.setIdfolderin(bean.getIdFolder() != null && !"".equals(bean.getIdFolder()) ? new BigDecimal(bean.getIdFolder()) : null);
							input.setSezionepraticain(bean.getSezionePratica());
							input.setNroposizionein(posizione != null && "P".equals(posizione) ? nrAllegatoTemp : null);
							lEtichettaBean.setNumeroAllegato(posizione != null && "P".equals(posizione) ? nrAllegatoTemp : 0);
							
							StoreResultBean<DmpkRegistrazionedocGetbarcodedacapofilapraticaBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
									input);
							if (output.isInError()) {
								mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
								throw new StoreException(output);
							}
							
							lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
							lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
							etichette.add(lEtichettaBean);
							
						} else {
							
							DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
							DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
							input.setIdudio(new BigDecimal(bean.getIdUd()));
							input.setNroallegatoin(posizione != null && "P".equals(posizione) ? nrAllegatoTemp : null);
							lEtichettaBean.setNumeroAllegato(posizione != null && "P".equals(posizione) ? nrAllegatoTemp : 0);
							
							StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
									input);
							if (output.isInError()) {
								mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
								throw new StoreException(output);
							}
							
							lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
							lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
							etichette.add(lEtichettaBean);
						}
						
						nrAllegatoTemp++;
						cont++;
					}
				} 
				//ETICHETTA MULTIPLA NR DOCUMENTO SENZA POSIZIONE
				else {
					for(int index = 0; index < size; index++){
						EtichettaBean lEtichettaBean = new EtichettaBean();
						
						if(bean.getBarcodePraticaPregressa() != null && "true".equals(bean.getBarcodePraticaPregressa())){
							
							DmpkRegistrazionedocGetbarcodedacapofilapraticaBean input = new DmpkRegistrazionedocGetbarcodedacapofilapraticaBean();
							DmpkRegistrazionedocGetbarcodedacapofilapratica store = new DmpkRegistrazionedocGetbarcodedacapofilapratica();
							input.setIdfolderin(bean.getIdFolder() != null && !"".equals(bean.getIdFolder()) ? new BigDecimal(bean.getIdFolder()) : null);
							input.setSezionepraticain(bean.getSezionePratica());
							input.setNroposizionein(posizione != null && "P".equals(posizione) ? index : null);
							lEtichettaBean.setNumeroAllegato(posizione != null && "P".equals(posizione) ? index : 0);
							
							StoreResultBean<DmpkRegistrazionedocGetbarcodedacapofilapraticaBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
									input);
							if (output.isInError()) {
								mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
								throw new StoreException(output);
							}
							
							lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
							lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
							etichette.add(lEtichettaBean);							
						} else {
							DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
							DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
							input.setIdudio(new BigDecimal(bean.getIdUd()));
							input.setNroallegatoin(posizione != null && "P".equals(posizione) ? index : null);
							lEtichettaBean.setNumeroAllegato(posizione != null && "P".equals(posizione) ? index : 0);
							
							StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
									input);
							if (output.isInError()) {
								mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
								throw new StoreException(output);
							}
							lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
							lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
							etichette.add(lEtichettaBean);
						}
					}
				}
			} 
			//ETICHETTA MULTIPLA DA FILE PRIMARIO
			else {
				size = nroEtichette;
				for (int i = 0; i < size; i++) {
					EtichettaBean lEtichettaBean = new EtichettaBean();
					
					if(bean.getBarcodePraticaPregressa() != null && "true".equals(bean.getBarcodePraticaPregressa())){
						
						DmpkRegistrazionedocGetbarcodedacapofilapraticaBean input = new DmpkRegistrazionedocGetbarcodedacapofilapraticaBean();
						DmpkRegistrazionedocGetbarcodedacapofilapratica store = new DmpkRegistrazionedocGetbarcodedacapofilapratica();
						input.setIdfolderin(bean.getIdFolder() != null && !"".equals(bean.getIdFolder()) ? new BigDecimal(bean.getIdFolder()) : null);
						input.setSezionepraticain(bean.getSezionePratica());
						input.setNroposizionein(posizione != null && "P".equals(posizione) ? i : null);
						lEtichettaBean.setNumeroAllegato(posizione != null && "P".equals(posizione) ? i : 0);
						
						StoreResultBean<DmpkRegistrazionedocGetbarcodedacapofilapraticaBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
								input);
						if (output.isInError()) {
							mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
							throw new StoreException(output);
						}
						
						lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
						lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
						etichette.add(lEtichettaBean);
						
					} else {
					
						DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
						DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
						input.setIdudio(new BigDecimal(bean.getIdUd()));
						input.setNroallegatoin(posizione != null && "P".equals(posizione) ? i : null);
						lEtichettaBean.setNumeroAllegato(posizione != null && "P".equals(posizione) ? i : 0);
	
						StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
								input);
						if (output.isInError()) {
							mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
							throw new StoreException(output);
						}
						lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
						lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
						etichette.add(lEtichettaBean);
					}
				}
			}
			result.setNrAllegato(String.valueOf(size));
			result.setEtichette(etichette);
		}
		//ETICHETTA SINGOLA
		else {
			EtichettaBean lEtichettaBean = new EtichettaBean();
			
			if(bean.getBarcodePraticaPregressa() != null && "true".equals(bean.getBarcodePraticaPregressa())){
				
				DmpkRegistrazionedocGetbarcodedacapofilapraticaBean input = new DmpkRegistrazionedocGetbarcodedacapofilapraticaBean();
				DmpkRegistrazionedocGetbarcodedacapofilapratica store = new DmpkRegistrazionedocGetbarcodedacapofilapratica();
				input.setIdfolderin(bean.getIdFolder() != null && !"".equals(bean.getIdFolder()) ? new BigDecimal(bean.getIdFolder()) : null);
				input.setSezionepraticain(bean.getSezionePratica());
				input.setNroposizionein(bean.getNrAllegato() != null && !"".equals(bean.getNrAllegato()) ? new Integer(bean.getNrAllegato()) : null);
				
				StoreResultBean<DmpkRegistrazionedocGetbarcodedacapofilapraticaBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
						input);
				if (output.isInError()) {
					mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
					throw new StoreException(output);
				}
				
				lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
				lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
				etichette.add(lEtichettaBean);
				
			} else {
			
				DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
				DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
				input.setIdudio(new BigDecimal(bean.getIdUd()));
				input.setNroallegatoin(bean.getNrAllegato() != null && !"".equals(bean.getNrAllegato()) ? new Integer(bean.getNrAllegato()) : null);
	
				StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
					input);
				if (output.isInError()) {
					mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
					throw new StoreException(output);
				}
				lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
				lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
				etichette.add(lEtichettaBean);
			}
			result.setEtichette(etichette);
		}
		return result;
	}
	
	/**
	 * DATI TIPOLOGIA
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public PreparaEtichettaBean getEtichettaDatiTipologia(PreparaEtichettaBean bean) throws Exception {
		
		PreparaEtichettaBean result = new PreparaEtichettaBean();
		List<EtichettaBean> etichette = new ArrayList<EtichettaBean>();
		
		
		Boolean isMultiple = getExtraparams() != null && getExtraparams().get("isMultiple") != null &&
				"true".equalsIgnoreCase(getExtraparams().get("isMultiple")) ? true : false;
		
		String provenienza = getExtraparams() != null && getExtraparams().get("provenienza") != null &&
				"A".equalsIgnoreCase(getExtraparams().get("provenienza")) ? "A" : "";
		
		Integer nroEtichette = getExtraparams() != null && getExtraparams().get("nroEtichette") != null ?
				Integer.valueOf(getExtraparams().get("nroEtichette")) : 0;
		
		if(isMultiple){
			int size = 0;
			if("A".equals(provenienza)){
				Integer nrAllegatoTemp = Integer.valueOf(bean.getNrAllegato());
				Integer cont = 0;
				while(cont <= size){
					EtichettaBean lEtichettaBean = new EtichettaBean();
					DmpkRegistrazionedocGettimbrospecxtipo store = new DmpkRegistrazionedocGettimbrospecxtipo();
					DmpkRegistrazionedocGettimbrospecxtipoBean input = new DmpkRegistrazionedocGettimbrospecxtipoBean();
					if (StringUtils.isNotBlank(bean.getIdDoc())){
						input.setIddocfolderin(new BigDecimal(bean.getIdDoc()));
						input.setFlgdocfolderin("D");
					}else{
						input.setIddocfolderin(new BigDecimal(bean.getIdFolder()));
						input.setFlgdocfolderin("F");
					}
					lEtichettaBean.setNumeroAllegato(nrAllegatoTemp);

					StoreResultBean<DmpkRegistrazionedocGettimbrospecxtipoBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
							input);
					if (output.isInError()) {
						mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
						throw new StoreException(output);
					}
					lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
					lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
					etichette.add(lEtichettaBean);
					
					nrAllegatoTemp++;
					cont++;
				}
			} else {
				size = nroEtichette;
				for (int i = 0; i < size; i++) {
					EtichettaBean lEtichettaBean = new EtichettaBean();
					DmpkRegistrazionedocGettimbrospecxtipo store = new DmpkRegistrazionedocGettimbrospecxtipo();
					DmpkRegistrazionedocGettimbrospecxtipoBean input = new DmpkRegistrazionedocGettimbrospecxtipoBean();
					if (StringUtils.isNotBlank(bean.getIdDoc())){
						input.setIddocfolderin(new BigDecimal(bean.getIdDoc()));
						input.setFlgdocfolderin("D");
					}else{
						input.setIddocfolderin(new BigDecimal(bean.getIdFolder()));
						input.setFlgdocfolderin("F");
					}
					lEtichettaBean.setNumeroAllegato(i);
	
					StoreResultBean<DmpkRegistrazionedocGettimbrospecxtipoBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
							input);
					if (output.isInError()) {
						mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
						throw new StoreException(output);
					}
					lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
					lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
					etichette.add(lEtichettaBean);
				}
			}
			result.setNrAllegato(String.valueOf(size));
			result.setEtichette(etichette);
		} else {
			
			EtichettaBean lEtichettaBean = new EtichettaBean();
			
			DmpkRegistrazionedocGettimbrospecxtipo store = new DmpkRegistrazionedocGettimbrospecxtipo();
			DmpkRegistrazionedocGettimbrospecxtipoBean input = new DmpkRegistrazionedocGettimbrospecxtipoBean();
			if (StringUtils.isNotBlank(bean.getIdDoc())){
				input.setIddocfolderin(new BigDecimal(bean.getIdDoc()));
				input.setFlgdocfolderin("D");
			}else{
				input.setIddocfolderin(new BigDecimal(bean.getIdFolder()));
				input.setFlgdocfolderin("F");
			}

			StoreResultBean<DmpkRegistrazionedocGettimbrospecxtipoBean> output = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
				input);
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			}
			lEtichettaBean.setTesto(output.getResultBean().getTestoinchiaroout().replaceAll("\\n", "\\|\\*\\|"));
			lEtichettaBean.setBarcode(output.getResultBean().getContenutobarcodeout());
			etichette.add(lEtichettaBean);
		
			result.setEtichette(etichette);
		}
		
		return result;
	}
	
}