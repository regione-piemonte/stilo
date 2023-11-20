/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityAccreditauserindominiologonBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityDreluouserBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityDuserBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityIuuserBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityLoaddettuserBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityLoaddettuserestesaBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTogliaccredusersinapplestBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTrovausersBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityUnlockuserBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaSoggettiBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.IndirizzoSoggettoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.IndirizzoSoggettoXmlBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.SoggettoGruppoBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.LoadAttrDinamicoListaDatasource;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributoBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.LoadAttrDinamicoListaInputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.LoadAttrDinamicoListaOutputBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.ApplEstAccredBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.ApplEstAccredOutBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.ApplEstAccredXmlBean;

import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.ClienteAssociatoUtenteXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.DRelUOUserBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.DocAssAUOBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.DocAssAUOXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.GestioneUtentiBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.GestioneUtentiXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.GestioneUtentiXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.GruppoClientiBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.SelezionaTipiAttoBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.SocietaUtenteBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.TogliAccredUsersInApplEstBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.UOCollegatePuntoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.UoAssociateUtenteBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.UoAssociateUtenteXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.VisibEmailCaselleUoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.client.DmpkDefSecurityAccreditauserindominiologon;
import it.eng.client.DmpkDefSecurityDreluouser;
import it.eng.client.DmpkDefSecurityDuser;
import it.eng.client.DmpkDefSecurityIuuser;
import it.eng.client.DmpkDefSecurityLoaddettuser;
import it.eng.client.DmpkDefSecurityLoaddettuserestesa;
import it.eng.client.DmpkDefSecurityTogliaccredusersinapplest;
import it.eng.client.DmpkDefSecurityTrovausers;
import it.eng.client.DmpkDefSecurityUnlockuser;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.MessageUtil;
import it.eng.utility.XmlUtility;
import it.eng.utility.authentication.LdapAuth;
import it.eng.utility.authentication.LdapAuthUserPropertiesBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AurigaGestioneUtentiDataSource")
public class AurigaGestioneUtentiDataSource extends AurigaAbstractFetchDatasource<GestioneUtentiBean> {

	private static final Logger log = Logger.getLogger(AurigaGestioneUtentiDataSource.class);

	@Override
	public String getNomeEntita() {
		return "gestioneutenti";
	}

	@Override
	public PaginatorBean<GestioneUtentiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT
		DmpkDefSecurityTrovausersBean input =  createFetchInput(loginBean, criteria, token, idUserLavoro, null);
					
		// Inizializzo l'OUTPUT
		DmpkDefSecurityTrovausers dmpkDefSecurityTrovausers = new DmpkDefSecurityTrovausers();
		StoreResultBean<DmpkDefSecurityTrovausersBean> output = dmpkDefSecurityTrovausers.execute(getLocale(), loginBean, input);
		
		boolean overflow = false;
		
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error(output.getDefaultMessage());
				throw new StoreException(output);
			} 
			else {
				overflow = manageOverflow(output.getDefaultMessage());			
			}
		}

		// SETTO L'OUTPUT
		List<GestioneUtentiBean> data = new ArrayList<GestioneUtentiBean>();
		String xmlResultSetOut = output.getResultBean().getListaxmlout();
		
		// Conversione ListaRisultati ==> EngResultSet
		if (xmlResultSetOut != null) {
			data = XmlListaUtility.recuperaLista(xmlResultSetOut, GestioneUtentiBean.class, new GestioneUtentiXmlBeanDeserializationHelper(createRemapConditions()));
		}
		
		
		//salvo i dati in sessione per renderli disponibili per l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);
		PaginatorBean<GestioneUtentiBean> lPaginatorBean = new PaginatorBean<GestioneUtentiBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}

	// Chiama il servizio con la lista dei clienti
	public GestioneUtentiBean getLoaddettuserestesa(GestioneUtentiBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityLoaddettuserestesaBean input = new DmpkDefSecurityLoaddettuserestesaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIduserio(new BigDecimal(bean.getIdUser()));

		DmpkDefSecurityLoaddettuserestesa loaddettuser = new DmpkDefSecurityLoaddettuserestesa();
		StoreResultBean<DmpkDefSecurityLoaddettuserestesaBean> output = loaddettuser.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error(output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		GestioneUtentiBean result = new GestioneUtentiBean();
		result.setIdUser(output.getResultBean().getIduserio() != null ? String.valueOf(output.getResultBean().getIduserio()) : null);
		result.setFlgDiSistema(output.getResultBean().getFlglockedout() != null ? output.getResultBean().getFlglockedout().intValue() : null);
		result.setDesUser(output.getResultBean().getDesuserio());

		StringSplitterServer st = new StringSplitterServer(result.getDesUser(), "|");
		String cognome = st.hasMoreTokens() ? st.nextToken() : null;
		String nome = st.hasMoreTokens() ? st.nextToken() : null;
		result.setCognome(cognome);
		result.setNome(nome);
		
		result.setCodFiscale(output.getResultBean().getCodfiscaleout());		
		result.setTitolo(output.getResultBean().getTitoloout());
		result.setNroMatricola(output.getResultBean().getNromatricolaout());
		
		if (output.getResultBean().getQualificaout() != null) {
			String par_QUALIFICHE_UTENTE_DA_DIZIONARIO = ParametriDBUtil.getParametroDB(getSession(), "QUALIFICHE_UTENTE_DA_DIZIONARIO");		
			if (par_QUALIFICHE_UTENTE_DA_DIZIONARIO != null && "true".equalsIgnoreCase(par_QUALIFICHE_UTENTE_DA_DIZIONARIO)) {			
				String[] values = output.getResultBean().getQualificaout().split(";");				
				if (values!=null && values.length>0){
					List<String> listaIdQualifiche = new ArrayList<String>();
					for (String item : values) {
						listaIdQualifiche.add(item);
					}
					result.setListaQualifiche(listaIdQualifiche);					
				}	
			}		
			else{
				result.setQualifica(output.getResultBean().getQualificaout());	
			}
		}
		

		result.setEmail(output.getResultBean().getEmailout());
		result.setDtIniVld(output.getResultBean().getDtiniziovldout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtiniziovldout()) : null);
		result.setDtFineVld(output.getResultBean().getDtfinevldout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtfinevldout()) : null);
		result.setUsername(output.getResultBean().getUsernameio());
		result.setIdProfilo(output.getResultBean().getIdprofiloout() != null ? String.valueOf(output.getResultBean().getIdprofiloout()) : null);
		result.setFlgValido(isValido(result.getDtIniVld(), result.getDtFineVld()) ? new Integer(1) : new Integer(0));
		result.setFlgIgnoreWarning(new Integer(0));
		result.setNomeProfilo(output.getResultBean().getNomeprofiloout());
		result.setIdSoggRubrica(output.getResultBean().getIdsoggrubricaout() != null ? String.valueOf(output.getResultBean().getIdsoggrubricaout()) : null);
		result.setAccountDefLocked(output.getResultBean().getAccountdeflockedout() != null ? String.valueOf(output.getResultBean().getAccountdeflockedout()) : "0");
		
		List<String> listaIdSubProfilo = new ArrayList<String>();
		for (SimpleKeyValueBean lIdSubProfilo : XmlUtility.recuperaListaSemplice(output.getResultBean().getXmlgruppiprivout())) {
			listaIdSubProfilo.add(lIdSubProfilo.getKey());
		}
		result.setIdSubProfilo(listaIdSubProfilo);
		
		// Leggo gli indirizzi
		List<IndirizzoSoggettoBean> listaIndirizzi = new ArrayList<IndirizzoSoggettoBean>();
		if (output.getResultBean().getIndirizziout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getIndirizziout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					IndirizzoSoggettoBean indirizzoBean = new IndirizzoSoggettoBean();
					indirizzoBean.setRowId(v.get(0)); // colonna 1 dell'xml : Identificativo dell'indirizzo/luogo (rowid)
					indirizzoBean.setTipo(v.get(1)); // colonna 2 dell'xml : Codice del tipo di indirizzo/luogo
					indirizzoBean.setDataValidoDal(v.get(6) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(6)) : null); // colonna 7 dell'xml : Data
																																	// di inzio validità
																																	// dell'indirizzo/luogo (nel
																																	// formato dato dal
																																	// parametro di conf.
																																	// FMT_STD_DATA)
					indirizzoBean.setDataValidoFinoAl(v.get(7) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(7)) : null); // colonna 8 dell'xml :
																																		// Data di fine validità
																																		// dell'indirizzo/luogo
																																		// (nel formato dato dal
																																		// parametro di conf.
																																		// FMT_STD_DATA)
					indirizzoBean.setCivico(v.get(10)); // colonna 11 dell'xml : N.° civico (senza eventuali appendici)
					indirizzoBean.setInterno(v.get(11)); // colonna 12 dell'xml : Interno
					String toponimoIndirizzo = v.get(9); // colonna 10 dell'xml : Indirizzo (senza civico) (alternativo o ridondante rispetto al campo
															// precedente) e senza tipo toponimo se questo è separato in colonna 24 (che quindi è valorizzata)
					if (StringUtils.isBlank(indirizzoBean.getStato()) || "200".equals(indirizzoBean.getStato())) {
						indirizzoBean.setToponimo(toponimoIndirizzo);
					} else {
						indirizzoBean.setIndirizzo(toponimoIndirizzo);
					}

					indirizzoBean.setCap(v.get(14)); // colonna 15 dell'xml : CAP
					indirizzoBean.setFrazione(v.get(15)); // colonna 16 dell'xml : Frazione
					if (StringUtils.isBlank(indirizzoBean.getStato()) || "200".equals(indirizzoBean.getStato())) {
						indirizzoBean.setComune(v.get(16)); // colonna 17 dell'xml : Codice ISTAT del comune italiano (viene considerato solo se è valorizzata
															// anche la colonna successiva)
						indirizzoBean.setNomeComune(v.get(17)); // colonna 18 dell'xml : Nome del comune italiano o della città (se estera)
					} else {
						indirizzoBean.setCitta(v.get(17)); // colonna 18 dell'xml : Nome del comune italiano o della città (se estera)
					}
					indirizzoBean.setStato(v.get(18)); // colonna 19 dell'xml : Codice ISTAT dello stato (viene considerato solo se è valorizzata anche la
														// colonna successiva)
					indirizzoBean.setProvincia(v.get(20)); // colonna 21 dell'xml : Targa provincia
					indirizzoBean.setZona(v.get(21)); // colonna 22 dell'xml : Zona dell'indirizzo
					indirizzoBean.setComplementoIndirizzo(v.get(22)); // colonna 23 dell'xml : Complemento dell'indirizzo
					indirizzoBean.setTipoToponimo(v.get(23)); // colonna 24 dell'xml : Tipo toponimo (i.e. via, piazza ecc) dell'indirizzo
					indirizzoBean.setAppendici(v.get(24)); // colonna 25 dell'xml : Appendici del civico

					listaIndirizzi.add(indirizzoBean);
				}
			}
		}
		result.setListaIndirizzi(listaIndirizzi);

		// Leggo le UO associate all'utente
		List<UoAssociateUtenteBean> listaUoAssociateUtente = new ArrayList<UoAssociateUtenteBean>();
		if (output.getResultBean().getXmlrelconuoout() != null) {
			listaUoAssociateUtente = XmlListaUtility.recuperaLista(output.getResultBean().getXmlrelconuoout(), UoAssociateUtenteBean.class);
		}
		
		result.setListaUoAssociateUtente(listaUoAssociateUtente);
		
		// Visualizzazione di documenti e fascicoli assegnati/inviati in copia alla struttura
		List<DocAssAUOBean> listaVisualizzaDocumentiFascicoliStruttura = new ArrayList<DocAssAUOBean>();
		if (output.getResultBean().getVisdocassinvauoxmlout() != null) {
			listaVisualizzaDocumentiFascicoliStruttura = XmlListaUtility.recuperaLista(output.getResultBean().getVisdocassinvauoxmlout(), DocAssAUOBean.class);
		}
		result.setListaVisualizzaDocumentiFascicoliStruttura(listaVisualizzaDocumentiFascicoliStruttura);
				
		// Modifica di documenti e fascicoli assegnati alla struttura
		List<DocAssAUOBean> listaModificaDocumentiFascicoliStruttura = new ArrayList<DocAssAUOBean>();
		if (output.getResultBean().getModdocassauoxmlout() != null) {
			listaModificaDocumentiFascicoliStruttura = XmlListaUtility.recuperaLista(output.getResultBean().getModdocassauoxmlout(), DocAssAUOBean.class);
		}
		result.setListaModificaDocumentiFascicoliStruttura(listaModificaDocumentiFascicoliStruttura);
		
		// Leggo la lista con le applicazioni esterne o loro istanze in cui e' accreditato l'utente	
		List<ApplEstAccredBean> listaApplEstAccredBean = new ArrayList<ApplEstAccredBean>();
		if (output.getResultBean().getXmlapplestaccredout() != null) {
			String xmlLista = output.getResultBean().getXmlapplestaccredout();			
			List<ApplEstAccredOutBean> lListResult = new ArrayList<ApplEstAccredOutBean>();			
			try {
				lListResult = XmlListaUtility.recuperaLista(xmlLista, ApplEstAccredOutBean.class);	
			} catch (Exception e) {
				throw new StoreException(e.getMessage());						
			}
			for (ApplEstAccredOutBean lApplEstAccredOutBean : lListResult){	
				ApplEstAccredBean applEstAccredBean = new ApplEstAccredBean();
				applEstAccredBean.setCodiceApplEst(lApplEstAccredOutBean.getCodiceApplEst());
				applEstAccredBean.setCodiceIstAppl(lApplEstAccredOutBean.getCodiceIstAppl());
				applEstAccredBean.setDenominazioneApplEst(lApplEstAccredOutBean.getDenominazioneApplEst());
				applEstAccredBean.setDenominazioneUoApplEst(lApplEstAccredOutBean.getDenominazioneUoApplEst());
				applEstAccredBean.setIdUtenteApplEst(lApplEstAccredOutBean.getIdUtenteApplEst());
				applEstAccredBean.setNriLivelliApplEst(lApplEstAccredOutBean.getNriLivelliApplEst());
				applEstAccredBean.setUsernameUtenteApplEst(lApplEstAccredOutBean.getUsernameUtenteApplEst());
				applEstAccredBean.setPasswordUtenteApplEst(lApplEstAccredOutBean.getPasswordUtenteApplEst());
				
				 // col. 9 : (flag 1/0) Se 1 indica se è un applicazione che usa credenziali diverse da quelle usate da Auriga
                if (lApplEstAccredOutBean.getFlgUsaCredenzialiDiverseAuriga()!=null && lApplEstAccredOutBean.getFlgUsaCredenzialiDiverseAuriga().equalsIgnoreCase("1"))
                	applEstAccredBean.setFlgUsaCredenzialiDiverseAuriga(true);
                else
                	applEstAccredBean.setFlgUsaCredenzialiDiverseAuriga(false);
                
               // Concatenazione di codice + “-“ + codice istanza
                String codiceApplIstEst = lApplEstAccredOutBean.getCodiceApplEst();
				if ( lApplEstAccredOutBean.getCodiceIstAppl() !=null && !lApplEstAccredOutBean.getCodiceIstAppl().equalsIgnoreCase("")){
					codiceApplIstEst = codiceApplIstEst + "-" + lApplEstAccredOutBean.getCodiceIstAppl();
				}
				applEstAccredBean.setCodiceApplIstEst(codiceApplIstEst); 
				
				// concatenazione di nri livelli + “-“ + denominazione UO
				String uoPerRegDoc = lApplEstAccredOutBean.getNriLivelliApplEst();   
				if ( lApplEstAccredOutBean.getDenominazioneUoApplEst() !=null && !lApplEstAccredOutBean.getDenominazioneUoApplEst().equalsIgnoreCase("")){
					uoPerRegDoc = uoPerRegDoc + "-" + lApplEstAccredOutBean.getDenominazioneUoApplEst();
				}
				applEstAccredBean.setUoPerRegDoc(uoPerRegDoc);
				
				//applEstAccredBean.setIdUoApplEst(lApplEstAccredOutBean.getIdUoApplEst());
				applEstAccredBean.setIdUoCollegataUtente(lApplEstAccredOutBean.getIdUoApplEst());
				applEstAccredBean.setDescrizioneUoCollegataUtente(uoPerRegDoc);
				
				listaApplEstAccredBean.add(applEstAccredBean);
			
				//applEstAccredBean.setPasswordUtenteApplEst(lApplEstAccredOutBean.get);				
			}
		}
		result.setListaApplEstAccreditate(listaApplEstAccredBean);	
		
		
		// Clienti associati all'utente
		List<SoggettoGruppoBean> listaClientiUtente = new ArrayList<SoggettoGruppoBean>();
		List<ClienteAssociatoUtenteXmlBean> listaClienteAssociatoUtente = new ArrayList<ClienteAssociatoUtenteXmlBean>();
		listaClienteAssociatoUtente = getClientiAssociatiUtente(output.getResultBean().getDaticlientiout());
		if (listaClienteAssociatoUtente != null && listaClienteAssociatoUtente.size() > 0) {
			for (ClienteAssociatoUtenteXmlBean lClienteAssociatoUtenteXmlBean : listaClienteAssociatoUtente) {
				SoggettoGruppoBean lSoggettoGruppoBean = new SoggettoGruppoBean();
				// lSoggettoGruppoBean.setBillingAccount(lClienteAssociatoUtenteXmlBean.getBillingAccount());
				lSoggettoGruppoBean.setCid(lClienteAssociatoUtenteXmlBean.getCid());
				lSoggettoGruppoBean.setCodfiscaleSoggetto(lClienteAssociatoUtenteXmlBean.getCodFiscalePiva());
				lSoggettoGruppoBean.setDenominazioneSoggetto(lClienteAssociatoUtenteXmlBean.getDenominazioneSoggetto());
				lSoggettoGruppoBean.setDenominazioneSocieta(lClienteAssociatoUtenteXmlBean.getDenominazioneSocieta());
				lSoggettoGruppoBean.setIdSoggettoGruppo(lClienteAssociatoUtenteXmlBean.getIdCliente());
				lSoggettoGruppoBean.setGruppoDiRiferimento(lClienteAssociatoUtenteXmlBean.getGruppoDiRiferimento());
				listaClientiUtente.add(lSoggettoGruppoBean);
			}
		}
		result.setListaClientiUtente(listaClientiUtente);
		
		// Leggo gli attributi custom
		List<AttributoBean> listaAttributi = new ArrayList<AttributoBean>();
		String xmlListaAttributi = output.getResultBean().getAttributiaddout();
		try {
			listaAttributi = XmlListaUtility.recuperaLista(xmlListaAttributi, AttributoBean.class);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		if (listaAttributi != null && listaAttributi.size() > 0) {
			List<SocietaUtenteBean> listaSocietaUtente = new ArrayList<SocietaUtenteBean>();
			List<GruppoClientiBean> listaGruppoClientiUtenti = new ArrayList<GruppoClientiBean>();
			

			for (AttributoBean lAttributoBean : listaAttributi) {
				String nomeAttributo = lAttributoBean.getNome();
				String valoreAttributo = lAttributoBean.getValore();
				String rowId = output.getResultBean().getRowidout();

				LoadAttrDinamicoListaOutputBean lLoadAttrDinamicoListaOutputBean = new LoadAttrDinamicoListaOutputBean();

				// Per ogni attributo chiamo il servizo che mi restituisce la
				// lista dei valori
				lLoadAttrDinamicoListaOutputBean = leggiListaValoriAttributi("DMT_USERS", nomeAttributo, rowId);
				List<HashMap<String, String>> valoriLista = new ArrayList<HashMap<String, String>>();
				valoriLista = lLoadAttrDinamicoListaOutputBean.getValoriLista();

				// Leggo i valori degli attributi
				for (HashMap<String, String> valori : valoriLista) {
					for (String key : valori.keySet()) {
						nomeAttributo = key;
						valoreAttributo = valori.get(key);
						// Se e' una SOCIETA'
						if (key.equalsIgnoreCase("CID_APPL_SOCIETA")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								SocietaUtenteBean lSocietaUtenteBean = new SocietaUtenteBean();
								lSocietaUtenteBean.setIdSocieta(valori.get(key));
								listaSocietaUtente.add(lSocietaUtenteBean);
							}
						}

						// Se e' un GRUPPO DI CLEINTI
						else if (key.equalsIgnoreCase("COD_GRUPPO_RIF")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								GruppoClientiBean lGruppoClientiBean = new GruppoClientiBean();
								lGruppoClientiBean.setIdGruppoClienti(valori.get(key));
								listaGruppoClientiUtenti.add(lGruppoClientiBean);
							}
						}

						// Se e' il NOME LOGO
						else if (key.equalsIgnoreCase("LOGO")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								result.setNomeLogo(valoreAttributo);
								result.setIdLogo(valoreAttributo);
							}
						}

						// Se e' il NOME LINGUA
						else if (key.equalsIgnoreCase("LINGUA")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								// result.setNomeLingua(valoreAttributo);
								result.setIdLingua(valoreAttributo);
							}
						}
						
						// Se e' il FLG UTENTE INTERNO/ESTERNO
						else if (key.equalsIgnoreCase("UTENTE_INTERNO_ESTERNO")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								result.setFlgUtenteInternoEsterno(valoreAttributo);
							}
						}
						
						//ORD_FIRMATARIO_FF_DG
						else if (key.equalsIgnoreCase("ORD_FIRMATARIO_FF_DG")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								result.setOrdFirmatariFFDG(valoreAttributo);
							}
						}
						
						// Se e' il FLG_UTENZA_APPLICATIVA
						else if (key.equalsIgnoreCase("FLG_UTENZA_APPLICATIVA")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								if (valoreAttributo.equalsIgnoreCase("1"))
									result.setFlgUtenzaApplicativa(true);
								else
									result.setFlgUtenzaApplicativa(false);
							}
						}
						
						// Se e' il FLG_DISATTIVA_NOTIF_X_DOC_DA_PRENDERE_IN_CARICO
						else if (key.equalsIgnoreCase("FLG_DISATTIVA_NOTIF_X_DOC_DA_PRENDERE_IN_CARICO")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								if (valoreAttributo.equalsIgnoreCase("1"))
									result.setFlgDisattivaNotifDocDaPrendereInCarico(true);
								else
									result.setFlgDisattivaNotifDocDaPrendereInCarico(false);
							}
						}
						
					}
				}
			}
			result.setListaSocietaUtenti(listaSocietaUtente);
			result.setListaGruppoClientiUtenti(listaGruppoClientiUtenti);
			
		}
	
		return result;
	}

	// Chiama il servizio standard
	public GestioneUtentiBean getLoaddettuser(GestioneUtentiBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityLoaddettuserBean input = new DmpkDefSecurityLoaddettuserBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIduserio(new BigDecimal(bean.getIdUser()));

		DmpkDefSecurityLoaddettuser loaddettuser = new DmpkDefSecurityLoaddettuser();
		StoreResultBean<DmpkDefSecurityLoaddettuserBean> output = loaddettuser.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error(output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		GestioneUtentiBean result = new GestioneUtentiBean();
		result.setIdUser(output.getResultBean().getIduserio() != null ? String.valueOf(output.getResultBean().getIduserio()) : null);
		result.setFlgDiSistema(output.getResultBean().getFlglockedout() != null ? output.getResultBean().getFlglockedout().intValue() : null);
		result.setDesUser(output.getResultBean().getDesuserio());

		StringSplitterServer st = new StringSplitterServer(result.getDesUser(), "|");
		String cognome = st.hasMoreTokens() ? st.nextToken() : null;
		String nome = st.hasMoreTokens() ? st.nextToken() : null;
		result.setCognome(cognome);
		result.setNome(nome);
		
		result.setCodFiscale(output.getResultBean().getCodfiscaleout());				
		result.setTitolo(output.getResultBean().getTitoloout());
		result.setNroMatricola(output.getResultBean().getNromatricolaout());
		
		if (output.getResultBean().getQualificaout() != null) {
			String par_QUALIFICHE_UTENTE_DA_DIZIONARIO = ParametriDBUtil.getParametroDB(getSession(), "QUALIFICHE_UTENTE_DA_DIZIONARIO");		
			if (par_QUALIFICHE_UTENTE_DA_DIZIONARIO != null && "true".equalsIgnoreCase(par_QUALIFICHE_UTENTE_DA_DIZIONARIO)) {			
				String[] values = output.getResultBean().getQualificaout().split(";");				
				if (values!=null && values.length>0){
					List<String> listaIdQualifiche = new ArrayList<String>();
					for (String item : values) {
						listaIdQualifiche.add(item);
					}
					result.setListaQualifiche(listaIdQualifiche);					
				}	
			}		
			else{
				result.setQualifica(output.getResultBean().getQualificaout());	
			}
		}
		
		result.setEmail(output.getResultBean().getEmailout());
		result.setDtIniVld(output.getResultBean().getDtiniziovldout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtiniziovldout()) : null);
		result.setDtFineVld(output.getResultBean().getDtfinevldout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtfinevldout()) : null);
		result.setUsername(output.getResultBean().getUsernameio());
		result.setIdProfilo(output.getResultBean().getIdprofiloout() != null ? String.valueOf(output.getResultBean().getIdprofiloout()) : null);
		result.setFlgValido(isValido(result.getDtIniVld(), result.getDtFineVld()) ? new Integer(1) : new Integer(0));
		result.setFlgIgnoreWarning(new Integer(0));
		result.setNomeProfilo(output.getResultBean().getNomeprofiloout());
		result.setIdSoggRubrica(output.getResultBean().getIdsoggrubricaout() != null ? String.valueOf(output.getResultBean().getIdsoggrubricaout()) : null);

		result.setAccountDefLocked(output.getResultBean().getAccountdeflockedout() != null ? String.valueOf(output.getResultBean().getAccountdeflockedout()) : "0");
		
		List<String> listaIdSubProfilo = new ArrayList<String>();
		if (output.getResultBean().getXmlgruppiprivout() != null) {
			for (SimpleKeyValueBean lIdSubProfilo : XmlUtility.recuperaListaSemplice(output.getResultBean().getXmlgruppiprivout())) {
				listaIdSubProfilo.add(lIdSubProfilo.getKey());
			}
		}
		result.setIdSubProfilo(listaIdSubProfilo);

		// Leggo gli indirizzi
		List<IndirizzoSoggettoBean> listaIndirizzi = new ArrayList<IndirizzoSoggettoBean>();
		if (output.getResultBean().getIndirizziout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getIndirizziout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					IndirizzoSoggettoBean indirizzoBean = new IndirizzoSoggettoBean();
					indirizzoBean.setRowId(v.get(0)); // colonna 1 dell'xml : Identificativo dell'indirizzo/luogo (rowid)
					indirizzoBean.setTipo(v.get(1)); // colonna 2 dell'xml : Codice del tipo di indirizzo/luogo
					indirizzoBean.setDataValidoDal(v.get(6) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(6)) : null); // colonna 7 dell'xml : Data
																																	// di inzio validita'
																																	// dell'indirizzo/luogo (nel
																																	// formato dato dal
																																	// parametro di conf.
																																	// FMT_STD_DATA)
					indirizzoBean.setDataValidoFinoAl(v.get(7) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(7)) : null); // colonna 8 dell'xml :
																																		// Data di fine
																																		// validita'
																																		// dell'indirizzo/luogo
																																		// (nel formato dato dal
																																		// parametro di conf.
																																		// FMT_STD_DATA)
					indirizzoBean.setCivico(v.get(10)); // colonna 11 dell'xml : N. civico (senza eventuali appendici)
					indirizzoBean.setInterno(v.get(11)); // colonna 12 dell'xml : Interno
					String toponimoIndirizzo = v.get(9); // colonna 10 dell'xml : Indirizzo (senza civico) (alternativo o ridondante rispetto al campo
															// precedente) e senza tipo toponimo se questo è separato in colonna 24 (che quindi è valorizzata)
					if (StringUtils.isBlank(indirizzoBean.getStato()) || "200".equals(indirizzoBean.getStato())) {
						indirizzoBean.setToponimo(toponimoIndirizzo);
					} else {
						indirizzoBean.setIndirizzo(toponimoIndirizzo);
					}

					indirizzoBean.setCap(v.get(14)); // colonna 15 dell'xml : CAP
					indirizzoBean.setFrazione(v.get(15)); // colonna 16 dell'xml : Frazione
					if (StringUtils.isBlank(indirizzoBean.getStato()) || "200".equals(indirizzoBean.getStato())) {
						indirizzoBean.setComune(v.get(16)); // colonna 17 dell'xml : Codice ISTAT del comune italiano (viene considerato solo se è valorizzata
															// anche la colonna successiva)
						indirizzoBean.setNomeComune(v.get(17)); // colonna 18 dell'xml : Nome del comune italiano o della città (se estera)
					} else {
						indirizzoBean.setCitta(v.get(17)); // colonna 18 dell'xml : Nome del comune italiano o della città (se estera)
					}
					indirizzoBean.setStato(v.get(18)); // colonna 19 dell'xml : Codice ISTAT dello stato (viene considerato solo se è valorizzata anche la
														// colonna successiva)
					indirizzoBean.setProvincia(v.get(20)); // colonna 21 dell'xml : Targa provincia
					indirizzoBean.setZona(v.get(21)); // colonna 22 dell'xml : Zona dell'indirizzo
					indirizzoBean.setComplementoIndirizzo(v.get(22)); // colonna 23 dell'xml : Complemento dell'indirizzo
					indirizzoBean.setTipoToponimo(v.get(23)); // colonna 24 dell'xml : Tipo toponimo (i.e. via, piazza ecc) dell'indirizzo
					indirizzoBean.setAppendici(v.get(24)); // colonna 25 dell'xml : Appendici del civico

					listaIndirizzi.add(indirizzoBean);
				}
			}
		}
		result.setListaIndirizzi(listaIndirizzi);

		
		// Leggo le UO associate all'utente
		List<UoAssociateUtenteBean> listaUoAssociateUtente = new ArrayList<UoAssociateUtenteBean>();
		if (output.getResultBean().getXmlrelconuoout() != null) {
			listaUoAssociateUtente = XmlListaUtility.recuperaLista(output.getResultBean().getXmlrelconuoout(), UoAssociateUtenteBean.class);
		}
		
		result.setListaUoAssociateUtente(listaUoAssociateUtente);
		
		// Visualizzazione di documenti e fascicoli assegnati/inviati in copia alla struttura
		List<DocAssAUOBean> listaVisualizzaDocumentiFascicoliStruttura = new ArrayList<DocAssAUOBean>();
		if (output.getResultBean().getVisdocassinvauoxmlout() != null) {
			listaVisualizzaDocumentiFascicoliStruttura = XmlListaUtility.recuperaLista(output.getResultBean().getVisdocassinvauoxmlout(), DocAssAUOBean.class);
		}
		result.setListaVisualizzaDocumentiFascicoliStruttura(listaVisualizzaDocumentiFascicoliStruttura);
				
		// Modifica di documenti e fascicoli assegnati alla struttura
		List<DocAssAUOBean> listaModificaDocumentiFascicoliStruttura = new ArrayList<DocAssAUOBean>();
		if (output.getResultBean().getModdocassauoxmlout() != null) {
			listaModificaDocumentiFascicoliStruttura = XmlListaUtility.recuperaLista(output.getResultBean().getModdocassauoxmlout(), DocAssAUOBean.class);
		}
		result.setListaModificaDocumentiFascicoliStruttura(listaModificaDocumentiFascicoliStruttura);
		
		// Leggo la lista con le applicazioni esterne o loro istanze in cui e' accreditato l'utente	
		List<ApplEstAccredBean> listaApplEstAccredBean = new ArrayList<ApplEstAccredBean>();
		if (output.getResultBean().getXmlapplestaccredout() != null) {
			String xmlLista = output.getResultBean().getXmlapplestaccredout();			
			List<ApplEstAccredOutBean> lListResult = new ArrayList<ApplEstAccredOutBean>();			
			try {
				lListResult = XmlListaUtility.recuperaLista(xmlLista, ApplEstAccredOutBean.class);	
			} catch (Exception e) {
				throw new StoreException(e.getMessage());						
			}
			for (ApplEstAccredOutBean lApplEstAccredOutBean : lListResult){	
				ApplEstAccredBean applEstAccredBean = new ApplEstAccredBean();
				applEstAccredBean.setCodiceApplEst(lApplEstAccredOutBean.getCodiceApplEst());
				applEstAccredBean.setCodiceIstAppl(lApplEstAccredOutBean.getCodiceIstAppl());
				applEstAccredBean.setDenominazioneApplEst(lApplEstAccredOutBean.getDenominazioneApplEst());
				applEstAccredBean.setDenominazioneUoApplEst(lApplEstAccredOutBean.getDenominazioneUoApplEst());
				applEstAccredBean.setIdUtenteApplEst(lApplEstAccredOutBean.getIdUtenteApplEst());
				applEstAccredBean.setNriLivelliApplEst(lApplEstAccredOutBean.getNriLivelliApplEst());
				applEstAccredBean.setUsernameUtenteApplEst(lApplEstAccredOutBean.getUsernameUtenteApplEst());
				applEstAccredBean.setPasswordUtenteApplEst(lApplEstAccredOutBean.getPasswordUtenteApplEst());
				
				 // col. 9 : (flag 1/0) Se 1 indica se è un applicazione che usa credenziali diverse da quelle usate da Auriga
                if (lApplEstAccredOutBean.getFlgUsaCredenzialiDiverseAuriga()!=null && lApplEstAccredOutBean.getFlgUsaCredenzialiDiverseAuriga().equalsIgnoreCase("1"))
                	applEstAccredBean.setFlgUsaCredenzialiDiverseAuriga(true);
                else
                	applEstAccredBean.setFlgUsaCredenzialiDiverseAuriga(false);
                
                
               // Concatenazione di codice + “-“ + codice istanza
                String codiceApplIstEst = lApplEstAccredOutBean.getCodiceApplEst();
				if ( lApplEstAccredOutBean.getCodiceIstAppl() !=null && !lApplEstAccredOutBean.getCodiceIstAppl().equalsIgnoreCase("")){
					codiceApplIstEst = codiceApplIstEst + "-" + lApplEstAccredOutBean.getCodiceIstAppl();
				}
				applEstAccredBean.setCodiceApplIstEst(codiceApplIstEst); 
				
				// concatenazione di nri livelli + “-“ + denominazione UO
				String uoPerRegDoc = lApplEstAccredOutBean.getNriLivelliApplEst();   
				if ( lApplEstAccredOutBean.getDenominazioneUoApplEst() !=null && !lApplEstAccredOutBean.getDenominazioneUoApplEst().equalsIgnoreCase("")){
					uoPerRegDoc = uoPerRegDoc + "-" + lApplEstAccredOutBean.getDenominazioneUoApplEst();
				}
				applEstAccredBean.setUoPerRegDoc(uoPerRegDoc);
				
				//applEstAccredBean.setIdUoApplEst(lApplEstAccredOutBean.getIdUoApplEst());
				applEstAccredBean.setIdUoCollegataUtente(lApplEstAccredOutBean.getIdUoApplEst());
				applEstAccredBean.setDescrizioneUoCollegataUtente(uoPerRegDoc);
				
				listaApplEstAccredBean.add(applEstAccredBean);
				
			}
		}		
		
		result.setListaApplEstAccreditate(listaApplEstAccredBean);

		// Leggo la Lista Visibilita' Email Transitate dalle Caselle Associate
		List<VisibEmailCaselleUoBean> listaVisibEmailCaselleUo = new ArrayList<VisibEmailCaselleUoBean>();
		if (output.getResultBean().getXmluocaselleemailvisbout() != null) {
			listaVisibEmailCaselleUo = XmlListaUtility.recuperaLista(output.getResultBean().getXmluocaselleemailvisbout(), VisibEmailCaselleUoBean.class);
		}
		result.setListaVisibEmailTransCaselleAssociateUO(listaVisibEmailCaselleUo);
		
		// Leggo gli attributi custom
		List<AttributoBean> listaAttributi = new ArrayList<AttributoBean>();
		String xmlListaAttributi = output.getResultBean().getAttributiaddout();
		try {
			listaAttributi = XmlListaUtility.recuperaLista(xmlListaAttributi, AttributoBean.class);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		if (listaAttributi != null && listaAttributi.size() > 0) {
			List<SocietaUtenteBean> listaSocietaUtente = new ArrayList<SocietaUtenteBean>();
			List<GruppoClientiBean> listaGruppoClientiUtenti = new ArrayList<GruppoClientiBean>();

			for (AttributoBean lAttributoBean : listaAttributi) {
				String nomeAttributo = lAttributoBean.getNome();
				String valoreAttributo = lAttributoBean.getValore();
				String rowId = output.getResultBean().getRowidout();

				LoadAttrDinamicoListaOutputBean lLoadAttrDinamicoListaOutputBean = new LoadAttrDinamicoListaOutputBean();

				// Per ogni attributo chiamo il servizo che mi restituisce la
				// lista dei valori
				lLoadAttrDinamicoListaOutputBean = leggiListaValoriAttributi("DMT_USERS", nomeAttributo, rowId);
				List<HashMap<String, String>> valoriLista = new ArrayList<HashMap<String, String>>();
				valoriLista = lLoadAttrDinamicoListaOutputBean.getValoriLista();

				// Leggo i valori degli attributi
				for (HashMap<String, String> valori : valoriLista) {
					for (String key : valori.keySet()) {
						nomeAttributo = key;
						valoreAttributo = valori.get(key);
						// Se e' una SOCIETA'
						if (key.equalsIgnoreCase("CID_APPL_SOCIETA")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								SocietaUtenteBean lSocietaUtenteBean = new SocietaUtenteBean();
								lSocietaUtenteBean.setIdSocieta(valori.get(key));
								listaSocietaUtente.add(lSocietaUtenteBean);
							}
						}

						// Se e' un GRUPPO DI CLEINTI
						else if (key.equalsIgnoreCase("COD_GRUPPO_RIF")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								GruppoClientiBean lGruppoClientiBean = new GruppoClientiBean();
								lGruppoClientiBean.setIdGruppoClienti(valori.get(key));
								listaGruppoClientiUtenti.add(lGruppoClientiBean);
							}
						}

						// Se e' il NOME LOGO
						else if (key.equalsIgnoreCase("LOGO")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								result.setNomeLogo(valoreAttributo);
								result.setIdLogo(valoreAttributo);
							}
						}

						// Se e' il NOME LINGUA
						else if (key.equalsIgnoreCase("LINGUA")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								// result.setNomeLingua(valoreAttributo);
								result.setIdLingua(valoreAttributo);
							}
						}
						
						// Se e' il FLG UTENTE INTERNO/ESTERNO
						else if (key.equalsIgnoreCase("UTENTE_INTERNO_ESTERNO")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								result.setFlgUtenteInternoEsterno(valoreAttributo);
							}
						}
						
						//ORD_FIRMATARIO_FF_DG
						else if (key.equalsIgnoreCase("ORD_FIRMATARIO_FF_DG")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								result.setOrdFirmatariFFDG(valoreAttributo);
							}
						}
						
						// Se e' il FLG_UTENZA_APPLICATIVA
						else if (key.equalsIgnoreCase("FLG_UTENZA_APPLICATIVA")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								if (valoreAttributo.equalsIgnoreCase("1"))
									result.setFlgUtenzaApplicativa(true);
								else
									result.setFlgUtenzaApplicativa(false);
							}
						}
						
						// Se e' il FLG_DISATTIVA_NOTIF_X_DOC_DA_PRENDERE_IN_CARICO
						else if (key.equalsIgnoreCase("FLG_DISATTIVA_NOTIF_X_DOC_DA_PRENDERE_IN_CARICO")) {
							if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
								if (valoreAttributo.equalsIgnoreCase("1"))
									result.setFlgDisattivaNotifDocDaPrendereInCarico(true);
								else
									result.setFlgDisattivaNotifDocDaPrendereInCarico(false);
							}
						}
					}
				}
			}
			result.setListaSocietaUtenti(listaSocietaUtente);
			result.setListaGruppoClientiUtenti(listaGruppoClientiUtenti);
		}
		
		
		return result;
	}

	@Override
	public GestioneUtentiBean get(GestioneUtentiBean bean) throws Exception {

		boolean isGestioneClienti = Boolean.parseBoolean(getExtraparams().get("isGestioneClienti"));

		GestioneUtentiBean result = new GestioneUtentiBean();

		if (isGestioneClienti) {
			result = getLoaddettuserestesa(bean);
		} else {
			result = getLoaddettuser(bean);
		}

		return result;
	}

	// Creo la lista dei clineti associati all'utente dal DB
	protected List<ClienteAssociatoUtenteXmlBean> getClientiAssociatiUtente(String clientiAssociatiUtentiXml) throws Exception {
		List<ClienteAssociatoUtenteXmlBean> lList = new ArrayList<ClienteAssociatoUtenteXmlBean>();
		if (clientiAssociatiUtentiXml != null && !clientiAssociatiUtentiXml.equalsIgnoreCase("")) {
			lList = XmlListaUtility.recuperaLista(clientiAssociatiUtentiXml, ClienteAssociatoUtenteXmlBean.class);
		}
		return lList;
	}

	private boolean isValido(Date dtIniVld, Date dtFineVld) {
		Date today = new Date();
		if (dtIniVld != null) {
			GregorianCalendar calIniVld = new GregorianCalendar();
			calIniVld.setTime(dtIniVld);
			calIniVld.set(Calendar.HOUR_OF_DAY, 0);
			calIniVld.set(Calendar.MINUTE, 0);
			calIniVld.set(Calendar.SECOND, 0);
			calIniVld.set(Calendar.MILLISECOND, 0);
			if (calIniVld.getTime().compareTo(today) > 0) {
				return false;
			}
		}
		if (dtFineVld != null) {
			GregorianCalendar calFineVld = new GregorianCalendar();
			calFineVld.setTime(dtFineVld);
			calFineVld.set(Calendar.HOUR_OF_DAY, 23);
			calFineVld.set(Calendar.MINUTE, 59);
			calFineVld.set(Calendar.SECOND, 59);
			calFineVld.set(Calendar.MILLISECOND, 999);
			if (calFineVld.getTime().compareTo(today) < 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public GestioneUtentiBean add(GestioneUtentiBean bean) throws Exception {

		boolean isGestioneLingua = Boolean.parseBoolean(getExtraparams().get("isGestioneLingua"));
		boolean isGestioneLogo = Boolean.parseBoolean(getExtraparams().get("isGestioneLogo"));
		boolean isGestioneUtenteInterno = Boolean.parseBoolean(getExtraparams().get("isGestioneUtenteInterno"));
		boolean isGestioneClienti = Boolean.parseBoolean(getExtraparams().get("isGestioneClienti"));
		boolean isGestioneSocieta = Boolean.parseBoolean(getExtraparams().get("isGestioneSocieta"));
		boolean isVisibilitaEmailCaselleAssociateUO  = Boolean.parseBoolean(getExtraparams().get("isVisibilitaEmailCaselleAssociateUO"));
		boolean isDisattivaNotifDocDaPrendereInCarico = Boolean.parseBoolean(getExtraparams().get("isDisattivaNotifDocDaPrendereInCarico"));
		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkDefSecurityIuuserBean input = new DmpkDefSecurityIuuserBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(bean.getFlgIgnoreWarning());
		input.setIduserio(StringUtils.isNotBlank(bean.getIdUser()) ? new BigDecimal(bean.getIdUser()) : null);
		input.setDesuserin(bean.getCognome() + "|" + bean.getNome());
		input.setUsernamein(bean.getUsername());
		input.setPasswordin(bean.getPassword());
		input.setConfermapasswordin(bean.getConfermaPassword());
		input.setCodfiscalein(bean.getCodFiscale());
		input.setTitoloin(bean.getTitolo());
		input.setNromatricolain(bean.getNroMatricola());
		
		String par_QUALIFICHE_UTENTE_DA_DIZIONARIO = ParametriDBUtil.getParametroDB(getSession(), "QUALIFICHE_UTENTE_DA_DIZIONARIO");		
		if (par_QUALIFICHE_UTENTE_DA_DIZIONARIO != null && "true".equalsIgnoreCase(par_QUALIFICHE_UTENTE_DA_DIZIONARIO)) {
			if (bean.getListaQualifiche()!=null && bean.getListaQualifiche().size()>0){
				StringBuffer listaQualifiche = new StringBuffer();
				for (String lIdQualifica : bean.getListaQualifiche()) {
					listaQualifiche.append(lIdQualifica);
					listaQualifiche.append(";");
				}
				input.setQualificain(listaQualifiche.toString());
			}				
		}
		else{
			input.setQualificain(bean.getQualifica());	
		}
		
		input.setEmailin(bean.getEmail());
		input.setDtiniziovldin(bean.getDtIniVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtIniVld()) : null);
		input.setDtfinevldin(bean.getDtFineVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtFineVld()) : null);
		input.setIdprofiloin(StringUtils.isNotBlank(bean.getIdProfilo()) ? new BigDecimal(bean.getIdProfilo()) : null);
		input.setIdsoggrubricain(StringUtils.isNotBlank(bean.getIdSoggRubrica()) ? new BigDecimal(bean.getIdSoggRubrica()) : null);
		
		List<SimpleValueBean> listaSubProfili = new ArrayList<SimpleValueBean>();
		if (bean.getIdSubProfilo()!=null) {
			for (String lIdSubProfilo : bean.getIdSubProfilo()) {
				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
				lSimpleValueBean.setValue(lIdSubProfilo);
				listaSubProfili.add(lSimpleValueBean);
			}
		}
		input.setFlgmodgruppiprivin("C");
		input.setXmlgruppiprivin(new XmlUtilitySerializer().bindXmlList(listaSubProfili));

	    // ****************************************
		// popolo gli attributi dinamici 
		// ****************************************
		Map<String, Object> valoriAttributiDinamici = new HashMap<String, Object>();
		Map<String, String> tipiValoriAttributiDinamici = new HashMap<String, String>();
		popolaAttributiDinamici(isGestioneLingua, isGestioneLogo, isGestioneUtenteInterno,  isGestioneClienti, isGestioneSocieta,  isVisibilitaEmailCaselleAssociateUO, isDisattivaNotifDocDaPrendereInCarico, bean, valoriAttributiDinamici, tipiValoriAttributiDinamici);
		bean.setValori(valoriAttributiDinamici);
		bean.setTipiValori(tipiValoriAttributiDinamici);
		
		// ****************************************
		// Salvo gli attributi custom
		// ****************************************
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		salvaAttributiCustomSemplici(bean, sezioneCacheAttributiDinamici);
		salvaAttributiCustomLista(bean, sezioneCacheAttributiDinamici);
		AttributiDinamiciXmlBean lAttributiDinamiciUtentiXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciUtentiXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		
		String xmlAttributiDinamici = null;
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlAttributiDinamici = lXmlUtilitySerializer.bindXml(lAttributiDinamiciUtentiXmlBean);
		input.setAttributiaddin(xmlAttributiDinamici);

		// Salvo gli indirizzi
		String xmlIndirizzi = null;
		if (bean.getListaIndirizzi() != null && bean.getListaIndirizzi().size() > 0) {
			xmlIndirizzi = getXmlIndirizzi(bean);
			input.setFlgmodindirizziin("C");
		}
		input.setXmlindirizziin(xmlIndirizzi);

		// Salvo le relazioni UO-UTENTE
		String xmlUoAssociateUtente = "";
		if (bean.getListaUoAssociateUtente() != null && bean.getListaUoAssociateUtente().size() > 0) {
			xmlUoAssociateUtente = getXmlUoAssociateUtente(bean, "add");
		}
		input.setXmlrelconuoin(xmlUoAssociateUtente);
		input.setFlgmodrelconuoin("C"); // C = Indica che i valori sono fornite in modo completo,vale a dire che dovranno soppiantare tutti quelle già specificate.
		
		// Salvo VisDocAssInvAUOXmlOut
		String visDocAssInvAUOXmlOut = "";
		if (bean.getListaVisualizzaDocumentiFascicoliStruttura() != null && !bean.getListaVisualizzaDocumentiFascicoliStruttura().isEmpty()) {
			visDocAssInvAUOXmlOut = getXmlDocAssAUOBean(bean.getListaVisualizzaDocumentiFascicoliStruttura(), "add");
		}
		input.setVisdocassinvauoxmlin(visDocAssInvAUOXmlOut);
		input.setFlgmoduovisdocassinvin("C"); // (valori I/C) Indica se le UO indicate nell'argomento successivo sono fornite in modo incrementale (=I) (solo quelle da aggiungere o cancellare) oppure completo (=C). 
		
		// Salvo ModDocAssAUOXmlIn 
		String modDocAssAUOXmlIn  = "";
		if (bean.getListaModificaDocumentiFascicoliStruttura() != null && !bean.getListaModificaDocumentiFascicoliStruttura().isEmpty()) {
			visDocAssInvAUOXmlOut = getXmlDocAssAUOBean(bean.getListaModificaDocumentiFascicoliStruttura(), "add");
		}
		input.setModdocassauoxmlin(modDocAssAUOXmlIn);
		input.setFlgmoduomoddocassin("C"); // (valori I/C) Indica se le UO indicate nell'argomento successivo sono fornite in modo incrementale (=I) (solo quelle da aggiungere o cancellare) oppure completo (=C). 
				
		// Salvo le relazioni UTENTE-APPLICAZIONI 
		String xmlApplEstAccred = "";
		if (bean.getListaApplEstAccreditate() != null && bean.getListaApplEstAccreditate().size() > 0) {
			xmlApplEstAccred = getXmlApplEstAccred(bean);
		}
		input.setXmlapplestaccredin(xmlApplEstAccred);
		input.setFlgmodapplestaccredin("C"); // C = Indica che i valori sono fornite in modo completo,vale a dire che dovranno soppiantare tutti quelle già specificate.
		
		DmpkDefSecurityIuuser dmpkDefSecurityIuuser = new DmpkDefSecurityIuuser();
		StoreResultBean<DmpkDefSecurityIuuserBean> output = dmpkDefSecurityIuuser.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error(output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
			bean.setFlgIgnoreWarning(1);
		} else {
			bean.setIdUser((String.valueOf(output.getResultBean().getIduserio())));
			bean.setFlgIgnoreWarning(0);
		}
		return bean;
	}

	// associa/rimuovi utente dal dominio
	public GestioneUtentiBean associaRimuoviDominio(GestioneUtentiBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String flgAccreditatiInDomIo = bean.getFlgAccreditatiInDomIo();
		DmpkDefSecurityAccreditauserindominiologonBean inputBean = null;
		inputBean = new DmpkDefSecurityAccreditauserindominiologonBean();
		inputBean.setDesuserin(bean.getDesUser());
		inputBean.setUsernamein(bean.getUsername());
		inputBean.setIduserin(StringUtils.isNotBlank(bean.getIdUser()) ? new BigDecimal(bean.getIdUser()) : null);
		if (flgAccreditatiInDomIo.equals("0") || (("").equals(flgAccreditatiInDomIo))) {
			inputBean.setDtfineaccredindomin("");
			inputBean.setDtinizioaccredindomin("");
		} else if (flgAccreditatiInDomIo.equals("1")) {
			Calendar cal = new GregorianCalendar();
			inputBean.setDtfineaccredindomin(new SimpleDateFormat(FMT_STD_DATA).format(cal.getTime()));
		}
		DmpkDefSecurityAccreditauserindominiologon dmpkdefsecurityaccreditauserindominiologon = new DmpkDefSecurityAccreditauserindominiologon();
		StoreResultBean<DmpkDefSecurityAccreditauserindominiologonBean> output = dmpkdefsecurityaccreditauserindominiologon.execute(getLocale(), loginBean,
				inputBean);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		if (StringUtils.isNotBlank(output.getResultBean().getErrmsgout())) {
			addMessage(output.getResultBean().getErrmsgout(), "", MessageType.WARNING);
			bean.setFlgIgnoreWarning(new Integer(1));
		} else {
			bean.setFlgIgnoreWarning(new Integer(0));
		}

		return bean;
	}

	@Override
	public GestioneUtentiBean update(GestioneUtentiBean bean, GestioneUtentiBean oldvalue) throws Exception {

		boolean isGestioneLingua = Boolean.parseBoolean(getExtraparams().get("isGestioneLingua"));
		boolean isGestioneLogo = Boolean.parseBoolean(getExtraparams().get("isGestioneLogo"));
		boolean isGestioneUtenteInterno = Boolean.parseBoolean(getExtraparams().get("isGestioneUtenteInterno"));
		boolean isGestioneClienti = Boolean.parseBoolean(getExtraparams().get("isGestioneClienti"));
		boolean isGestioneSocieta = Boolean.parseBoolean(getExtraparams().get("isGestioneSocieta"));
		boolean isVisibilitaEmailCaselleAssociateUO = Boolean.parseBoolean(getExtraparams().get("isVisibilitaEmailCaselleAssociateUO"));
		boolean isDisattivaNotifDocDaPrendereInCarico = Boolean.parseBoolean(getExtraparams().get("isDisattivaNotifDocDaPrendereInCarico"));
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityIuuserBean input = new DmpkDefSecurityIuuserBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(bean.getFlgIgnoreWarning());
		input.setIduserio(StringUtils.isNotBlank(bean.getIdUser()) ? new BigDecimal(bean.getIdUser()) : null);
		input.setDesuserin(bean.getCognome() + "|" + bean.getNome());
		input.setUsernamein(bean.getUsername());
		input.setCodfiscalein(bean.getCodFiscale());
		input.setTitoloin(bean.getTitolo());
		input.setNromatricolain(bean.getNroMatricola());
		
		String par_QUALIFICHE_UTENTE_DA_DIZIONARIO = ParametriDBUtil.getParametroDB(getSession(), "QUALIFICHE_UTENTE_DA_DIZIONARIO");		
		if (par_QUALIFICHE_UTENTE_DA_DIZIONARIO != null && "true".equalsIgnoreCase(par_QUALIFICHE_UTENTE_DA_DIZIONARIO)) {
			if (bean.getListaQualifiche()!=null && bean.getListaQualifiche().size()>0){
				StringBuffer listaQualifiche = new StringBuffer();
				for (String lIdQualifica : bean.getListaQualifiche()) {
					listaQualifiche.append(lIdQualifica);
					listaQualifiche.append(";");
				}
				input.setQualificain(listaQualifiche.toString());
			}				
		}
		else{
			input.setQualificain(bean.getQualifica());	
		}
			
		input.setEmailin(bean.getEmail());
		input.setDtiniziovldin(bean.getDtIniVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtIniVld()) : null);
		input.setDtfinevldin(bean.getDtFineVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtFineVld()) : null);
		input.setIdprofiloin(StringUtils.isNotBlank(bean.getIdProfilo()) ? new BigDecimal(bean.getIdProfilo()) : null);
		input.setIdsoggrubricain(StringUtils.isNotBlank(bean.getIdSoggRubrica()) ? new BigDecimal(bean.getIdSoggRubrica()) : null);
		
		List<SimpleValueBean> listaSubProfili = new ArrayList<SimpleValueBean>();
		if (bean.getIdSubProfilo()!=null) {
			for (String lIdSubProfilo : bean.getIdSubProfilo()) {
				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
				lSimpleValueBean.setValue(lIdSubProfilo);
				listaSubProfili.add(lSimpleValueBean);
				
			}
		}
		input.setFlgmodgruppiprivin("C");
		input.setXmlgruppiprivin(new XmlUtilitySerializer().bindXmlList(listaSubProfili));
		
		
		// ****************************************
		// popolo gli attributi dinamici 
		// ****************************************
		Map<String, Object> valoriAttributiDinamici = new HashMap<String, Object>();
		Map<String, String> tipiValoriAttributiDinamici = new HashMap<String, String>();
		popolaAttributiDinamici(isGestioneLingua, isGestioneLogo, isGestioneUtenteInterno,  isGestioneClienti, isGestioneSocieta,  isVisibilitaEmailCaselleAssociateUO,  isDisattivaNotifDocDaPrendereInCarico, bean, valoriAttributiDinamici, tipiValoriAttributiDinamici);
		bean.setValori(valoriAttributiDinamici);
		bean.setTipiValori(tipiValoriAttributiDinamici);
		
		// ****************************************
		// Salvo gli attributi custom
		// ****************************************
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		salvaAttributiCustomSemplici(bean, sezioneCacheAttributiDinamici);
		salvaAttributiCustomLista(bean, sezioneCacheAttributiDinamici);
		AttributiDinamiciXmlBean lAttributiDinamiciUtentiXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciUtentiXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		
		String xmlAttributiDinamici = null;
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlAttributiDinamici = lXmlUtilitySerializer.bindXmlCompleta(lAttributiDinamiciUtentiXmlBean);
		input.setAttributiaddin(xmlAttributiDinamici);

		// Salvo gli indirizzi
		String xmlIndirizzi = getXmlIndirizzi(bean);
		input.setFlgmodindirizziin("C");
		input.setXmlindirizziin(xmlIndirizzi);
		
		// Salvo le relazioni UO-UTENTE
		String xmlUoAssociateUtente = getXmlUoAssociateUtente(bean, "upd");
		input.setXmlrelconuoin(xmlUoAssociateUtente);
		input.setFlgmodrelconuoin("I"); // I = Indica che i valori sono forniti in modo incrementale
		
		
		// Salvo VisDocAssInvAUOXmlOut
		String visDocAssInvAUOXmlOut = "";
		if (bean.getListaVisualizzaDocumentiFascicoliStruttura() != null && !bean.getListaVisualizzaDocumentiFascicoliStruttura().isEmpty()) {
			visDocAssInvAUOXmlOut = getXmlDocAssAUOBean(bean.getListaVisualizzaDocumentiFascicoliStruttura(), "upd");
		}
		input.setVisdocassinvauoxmlin(visDocAssInvAUOXmlOut);
		input.setFlgmoduovisdocassinvin("C"); // (valori I/C) Indica se le UO indicate nell'argomento successivo sono fornite in modo incrementale (=I) (solo quelle da aggiungere o cancellare) oppure completo (=C). 
		
		// Salvo ModDocAssAUOXmlIn 
		String modDocAssAUOXmlIn  = "";
		if (bean.getListaModificaDocumentiFascicoliStruttura() != null && !bean.getListaModificaDocumentiFascicoliStruttura().isEmpty()) {
			modDocAssAUOXmlIn = getXmlDocAssAUOBean(bean.getListaModificaDocumentiFascicoliStruttura(), "upd");
		}
		input.setModdocassauoxmlin(modDocAssAUOXmlIn);
		input.setFlgmoduomoddocassin("C"); // (valori I/C) Indica se le UO indicate nell'argomento successivo sono fornite in modo incrementale (=I) (solo quelle da aggiungere o cancellare) oppure completo (=C). 
		
		// Salvo le relazioni UTENTE-APPLICAZIONI 
		String xmlApplEstAccred = getXmlApplEstAccred(bean);
		input.setFlgmodapplestaccredin("C"); // C = Indica che i valori sono fornite in modo completo,vale a dire che dovranno soppiantare tutti quelle già specificate.
		input.setXmlapplestaccredin(xmlApplEstAccred);
		
		// Eseguo il servizio
		DmpkDefSecurityIuuser dmpkDefSecurityIuuser = new DmpkDefSecurityIuuser();
		StoreResultBean<DmpkDefSecurityIuuserBean> output = dmpkDefSecurityIuuser.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error(output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
			bean.setFlgIgnoreWarning(new Integer(1));
		} else {
			bean.setFlgIgnoreWarning(new Integer(0));
		}
		return bean;
	}

	@Override
	public GestioneUtentiBean remove(GestioneUtentiBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityDuserBean input = new DmpkDefSecurityDuserBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIduserin(new BigDecimal(bean.getIdUser()));
		input.setMotiviin(null);
		input.setFlgignorewarningin(new Integer(1));

		DmpkDefSecurityDuser duser = new DmpkDefSecurityDuser();
		StoreResultBean<DmpkDefSecurityDuserBean> output = duser.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				logger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;
	}

	private LoadAttrDinamicoListaOutputBean leggiListaValoriAttributi(String nomeTabella, String nomeAttrLista, String rowId) throws Exception {
		LoadAttrDinamicoListaOutputBean result = new LoadAttrDinamicoListaOutputBean();
		if (nomeTabella != null && !nomeTabella.equalsIgnoreCase("") && nomeAttrLista != null && !nomeAttrLista.equalsIgnoreCase("") && rowId != null && !rowId.equalsIgnoreCase("")) {
			// Leggo la lista degli attributi
			LoadAttrDinamicoListaInputBean inputDataSourceBean = new LoadAttrDinamicoListaInputBean();

			inputDataSourceBean.setNomeAttrLista(nomeAttrLista);
			inputDataSourceBean.setNomeTabella(nomeTabella);
			inputDataSourceBean.setRowId(rowId);

			LoadAttrDinamicoListaDatasource lDataSource = new LoadAttrDinamicoListaDatasource();
			try {
				lDataSource.setSession(getSession());
				result = lDataSource.call(inputDataSourceBean);
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
		}
		return result;
	}

	protected String getXmlDocAssAUOBean(List<DocAssAUOBean> bean, String callFrom) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String xmXmlDocAssAUOBean = "";
		List<DocAssAUOXmlBean> lListlDocAssAUOBeanXml = new ArrayList<DocAssAUOXmlBean>();
		
		for (DocAssAUOBean lDocAssAUOBean : bean) {
			 DocAssAUOXmlBean lDocAssAUOBeanXmlBean = new DocAssAUOXmlBean();
			
			lDocAssAUOBeanXmlBean.setIdUo(lDocAssAUOBean.getIdUo() != null && lDocAssAUOBean.getIdUo().startsWith("UO") ? lDocAssAUOBean.getIdUo().substring(2) : lDocAssAUOBean.getIdUo());
			lDocAssAUOBeanXmlBean.setDenominazioneUo(lDocAssAUOBean.getDenominazioneUo());
			lDocAssAUOBeanXmlBean.setCodRapido(lDocAssAUOBean.getCodRapido());
			lDocAssAUOBeanXmlBean.setFlgIncluseSottoUo(lDocAssAUOBean.getFlgIncluseSottoUo() != null && lDocAssAUOBean.getFlgIncluseSottoUo() ? "1" : "0");
			lDocAssAUOBeanXmlBean.setFlgIncluseScrivanie( lDocAssAUOBean.getFlgIncluseSottoUo() != null  &&
					(lDocAssAUOBean.getFlgIncluseScrivanie() != null && lDocAssAUOBean.getFlgIncluseScrivanie()) ? "1" : "0");
			lDocAssAUOBeanXmlBean.setFlgVisDocFascRis(lDocAssAUOBean.getFlgVisDocFascRis() != null && lDocAssAUOBean.getFlgVisDocFascRis() ? "1" : "0");
			lListlDocAssAUOBeanXml.add(lDocAssAUOBeanXmlBean);			
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmXmlDocAssAUOBean = lXmlUtilitySerializer.bindXmlList(lListlDocAssAUOBeanXml);
		
		return xmXmlDocAssAUOBean;
	}
	
	protected String getXmlUoAssociateUtente(GestioneUtentiBean bean, String callFrom) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String xmlUoAssociateUtente = "";
		List<UoAssociateUtenteXmlBean> lListlUoAssociateUtenteXml = new ArrayList<UoAssociateUtenteXmlBean>();
					
		
		if(callFrom!=null && callFrom.equalsIgnoreCase("upd")){
			// Se devo fare un UPDATE
			for (UoAssociateUtenteBean lUoAssociateUtenteBean : bean.getListaUoAssociateUtente()) {
				// Se ho modificato i dati 
				if ( lUoAssociateUtenteBean.getFlgModificato()!=null && lUoAssociateUtenteBean.getFlgModificato().equalsIgnoreCase("1")){
					UoAssociateUtenteXmlBean lUoAssociateUtenteXmlBean = new UoAssociateUtenteXmlBean();
					lUoAssociateUtenteXmlBean.setRowId(lUoAssociateUtenteBean.getRowId());					
					lUoAssociateUtenteXmlBean.setTipoRelazione(lUoAssociateUtenteBean.getTipoRelazione());
					lUoAssociateUtenteXmlBean.setIdUo(lUoAssociateUtenteBean.getIdUo() != null && lUoAssociateUtenteBean.getIdUo().startsWith("UO") ? lUoAssociateUtenteBean.getIdUo().substring(2) : lUoAssociateUtenteBean.getIdUo());
					lUoAssociateUtenteXmlBean.setDtInizioVld(lUoAssociateUtenteBean.getDtInizioVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(lUoAssociateUtenteBean.getDtInizioVld()) : null);
					lUoAssociateUtenteXmlBean.setDtFineVld(lUoAssociateUtenteBean.getDtFineVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(lUoAssociateUtenteBean.getDtFineVld()) : null);
					lUoAssociateUtenteXmlBean.setDescrizioneRuolo(lUoAssociateUtenteBean.getDescrizioneRuolo());
					lUoAssociateUtenteXmlBean.setDenominazioneScrivaniaUtente(lUoAssociateUtenteBean.getDenominazioneScrivaniaUtente());
					lUoAssociateUtenteXmlBean.setFlgIncluseSottoUo((lUoAssociateUtenteBean.isFlgIncluseSottoUo() != null && lUoAssociateUtenteBean.isFlgIncluseSottoUo()) ? "1" : "0");
					lUoAssociateUtenteXmlBean.setFlgIncluseScrivanie((lUoAssociateUtenteBean.isFlgIncluseScrivanie() != null && lUoAssociateUtenteBean.isFlgIncluseScrivanie()) ? "1" : "0");
					lUoAssociateUtenteXmlBean.setFlgAccessoDocLimSV((lUoAssociateUtenteBean.isFlgAccessoDocLimSV() != null && lUoAssociateUtenteBean.isFlgAccessoDocLimSV()) ? "1" : "0");
					lUoAssociateUtenteXmlBean.setFlgRegistrazioneE((lUoAssociateUtenteBean.isFlgRegistrazioneE() != null && lUoAssociateUtenteBean.isFlgRegistrazioneE()) ? "1" : "0");
					lUoAssociateUtenteXmlBean.setFlgRegistrazioneUI((lUoAssociateUtenteBean.isFlgRegistrazioneUI() != null && lUoAssociateUtenteBean.isFlgRegistrazioneUI()) ? "1" : "0");
					lUoAssociateUtenteXmlBean.setFlgGestAtti((lUoAssociateUtenteBean.isFlgGestAtti() != null && lUoAssociateUtenteBean.isFlgGestAtti()) ? "1" : "0");
					lUoAssociateUtenteXmlBean.setFlgVisPropAttiInIter((lUoAssociateUtenteBean.isFlgVisPropAttiInIter() != null && lUoAssociateUtenteBean.isFlgVisPropAttiInIter()) ? "1" : "0");
					lUoAssociateUtenteXmlBean.setFlgRiservatezzaRelUserUo((lUoAssociateUtenteBean.isFlgRiservatezzaRelUserUo() != null && lUoAssociateUtenteBean.isFlgRiservatezzaRelUserUo()) ? "1" : "0");
					lUoAssociateUtenteXmlBean.setListaUOPuntoProtocolloEscluse(lUoAssociateUtenteBean.getListaUOPuntoProtocolloEscluse());
					lUoAssociateUtenteXmlBean.setListaUOPuntoProtocolloEreditarietaAbilitata(lUoAssociateUtenteBean.getListaUOPuntoProtocolloEreditarietaAbilitata());
					lUoAssociateUtenteXmlBean.setFlgTipoDestDoc(lUoAssociateUtenteBean.getFlgTipoDestDoc());
					lUoAssociateUtenteXmlBean.setIdUODestDocfasc(lUoAssociateUtenteBean.getIdUODestDocfasc());
					lUoAssociateUtenteXmlBean.setFlgGestAttiTutti((lUoAssociateUtenteBean.isFlgGestAttiTutti() != null && lUoAssociateUtenteBean.isFlgGestAttiTutti()) ? "1" : "0");
					if (lUoAssociateUtenteBean.isFlgGestAttiTutti() == null || !lUoAssociateUtenteBean.isFlgGestAttiTutti()) {
						lUoAssociateUtenteXmlBean.setListaTipiGestAtti(lUoAssociateUtenteBean.getListaTipiGestAttiSelezionati());
					}
					lUoAssociateUtenteXmlBean.setFlgVisPropAttiInIterTutti((lUoAssociateUtenteBean.isFlgVisPropAttiInIterTutti() != null && lUoAssociateUtenteBean.isFlgVisPropAttiInIterTutti()) ? "1" : "0");
					if (lUoAssociateUtenteBean.isFlgVisPropAttiInIterTutti() == null || !lUoAssociateUtenteBean.isFlgVisPropAttiInIterTutti()) {
						lUoAssociateUtenteXmlBean.setListaTipiVisPropAttiInIter(lUoAssociateUtenteBean.getListaTipiVisPropAttiInIterSelezionati());
					}
					lListlUoAssociateUtenteXml.add(lUoAssociateUtenteXmlBean);		
				}
			}
			
			// Aggiungo quelli eliminati
			if (bean.getListaUoAssociateUtenteEliminati() != null && !bean.getListaUoAssociateUtenteEliminati().equalsIgnoreCase("")) {	
				
				String[] listaUoAssociateUtenteEliminati = bean.getListaUoAssociateUtenteEliminati().split(";");
				
				if (listaUoAssociateUtenteEliminati != null && listaUoAssociateUtenteEliminati.length > 0){
					
					for (String rowId : listaUoAssociateUtenteEliminati) {
						// Ignoro le associazioni che sono state inserite e poi tolte senza fare il salvataggio dopo l'inserimento
						UoAssociateUtenteXmlBean lUoAssociateUtenteXmlBean = new UoAssociateUtenteXmlBean();
						if (StringUtils.isNotBlank(rowId) && !"null".equalsIgnoreCase(rowId)) {
							lUoAssociateUtenteXmlBean.setRowId(rowId);
							lUoAssociateUtenteXmlBean.setFlgRecDaEliminare("1");
							lListlUoAssociateUtenteXml.add(lUoAssociateUtenteXmlBean);
						}
					}
					
				}
			}
		} else {
			// Se devo fare un ADD
			for (UoAssociateUtenteBean lUoAssociateUtenteBean : bean.getListaUoAssociateUtente()) {
				UoAssociateUtenteXmlBean lUoAssociateUtenteXmlBean = new UoAssociateUtenteXmlBean();
				lUoAssociateUtenteXmlBean.setTipoRelazione(lUoAssociateUtenteBean.getTipoRelazione());
				lUoAssociateUtenteXmlBean.setIdUo(lUoAssociateUtenteBean.getIdUo() != null && lUoAssociateUtenteBean.getIdUo().startsWith("UO") ? lUoAssociateUtenteBean.getIdUo().substring(2) : lUoAssociateUtenteBean.getIdUo());
				lUoAssociateUtenteXmlBean.setDtInizioVld(lUoAssociateUtenteBean.getDtInizioVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(lUoAssociateUtenteBean.getDtInizioVld()) : null);
				lUoAssociateUtenteXmlBean.setDtFineVld(lUoAssociateUtenteBean.getDtFineVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(lUoAssociateUtenteBean.getDtFineVld()) : null);
				lUoAssociateUtenteXmlBean.setDescrizioneRuolo(lUoAssociateUtenteBean.getDescrizioneRuolo());
				lUoAssociateUtenteXmlBean.setDenominazioneScrivaniaUtente(lUoAssociateUtenteBean.getDenominazioneScrivaniaUtente());
				lUoAssociateUtenteXmlBean.setFlgIncluseSottoUo((lUoAssociateUtenteBean.isFlgIncluseSottoUo() != null && lUoAssociateUtenteBean.isFlgIncluseSottoUo()) ? "1" : "0");
				lUoAssociateUtenteXmlBean.setFlgIncluseScrivanie((lUoAssociateUtenteBean.isFlgIncluseScrivanie() != null && lUoAssociateUtenteBean.isFlgIncluseScrivanie()) ? "1" : "0");
				lUoAssociateUtenteXmlBean.setFlgAccessoDocLimSV((lUoAssociateUtenteBean.isFlgAccessoDocLimSV() != null && lUoAssociateUtenteBean.isFlgAccessoDocLimSV()) ? "1" : "0");
				lUoAssociateUtenteXmlBean.setFlgRegistrazioneE((lUoAssociateUtenteBean.isFlgRegistrazioneE() != null && lUoAssociateUtenteBean.isFlgRegistrazioneE()) ? "1" : "0");
				lUoAssociateUtenteXmlBean.setFlgRegistrazioneUI((lUoAssociateUtenteBean.isFlgRegistrazioneUI() != null && lUoAssociateUtenteBean.isFlgRegistrazioneUI()) ? "1" : "0");
				lUoAssociateUtenteXmlBean.setFlgGestAtti((lUoAssociateUtenteBean.isFlgGestAtti() != null && lUoAssociateUtenteBean.isFlgGestAtti()) ? "1" : "0");
				lUoAssociateUtenteXmlBean.setFlgVisPropAttiInIter((lUoAssociateUtenteBean.isFlgVisPropAttiInIter() != null && lUoAssociateUtenteBean.isFlgVisPropAttiInIter()) ? "1" : "0");
				lUoAssociateUtenteXmlBean.setFlgRiservatezzaRelUserUo((lUoAssociateUtenteBean.isFlgRiservatezzaRelUserUo() != null && lUoAssociateUtenteBean.isFlgRiservatezzaRelUserUo()) ? "1" : "0");
				lUoAssociateUtenteXmlBean.setListaUOPuntoProtocolloEscluse(lUoAssociateUtenteBean.getListaUOPuntoProtocolloEscluse());
				lUoAssociateUtenteXmlBean.setListaUOPuntoProtocolloEreditarietaAbilitata(lUoAssociateUtenteBean.getListaUOPuntoProtocolloEreditarietaAbilitata());
				lUoAssociateUtenteXmlBean.setFlgTipoDestDoc(lUoAssociateUtenteBean.getFlgTipoDestDoc());
				lUoAssociateUtenteXmlBean.setIdUODestDocfasc(lUoAssociateUtenteBean.getIdUODestDocfasc());
				lUoAssociateUtenteXmlBean.setFlgGestAttiTutti((lUoAssociateUtenteBean.isFlgGestAttiTutti() != null && lUoAssociateUtenteBean.isFlgGestAttiTutti()) ? "1" : "0");
				if (lUoAssociateUtenteBean.isFlgGestAttiTutti() != null && !lUoAssociateUtenteBean.isFlgGestAttiTutti()) {
					lUoAssociateUtenteXmlBean.setListaTipiGestAtti(lUoAssociateUtenteBean.getListaTipiGestAttiSelezionati());
				}
				lUoAssociateUtenteXmlBean.setFlgVisPropAttiInIterTutti((lUoAssociateUtenteBean.isFlgVisPropAttiInIterTutti() != null && lUoAssociateUtenteBean.isFlgVisPropAttiInIterTutti()) ? "1" : "0");
				if (lUoAssociateUtenteBean.isFlgVisPropAttiInIterTutti() != null && !lUoAssociateUtenteBean.isFlgVisPropAttiInIterTutti()) {
					lUoAssociateUtenteXmlBean.setListaTipiVisPropAttiInIter(lUoAssociateUtenteBean.getListaTipiVisPropAttiInIterSelezionati());
				}
				lListlUoAssociateUtenteXml.add(lUoAssociateUtenteXmlBean);	
			}
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlUoAssociateUtente = lXmlUtilitySerializer.bindXmlList(lListlUoAssociateUtenteXml);
		
		return xmlUoAssociateUtente;
	}
	
	protected String getXmlApplEstAccred(GestioneUtentiBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		String xmlApplEstAccred = "";
		List<ApplEstAccredXmlBean> lListlApplEstAccredXml = new ArrayList<ApplEstAccredXmlBean>();
		for (ApplEstAccredBean lApplEstAccredBean : bean.getListaApplEstAccreditate()) {
			ApplEstAccredXmlBean lApplEstAccredXmlBean = new ApplEstAccredXmlBean();
			lApplEstAccredXmlBean.setCodiceApplEst(lApplEstAccredBean.getCodiceApplEst());
			lApplEstAccredXmlBean.setCodiceIstAppl(lApplEstAccredBean.getCodiceIstAppl());
			lApplEstAccredXmlBean.setIdUtenteApplEst(lApplEstAccredBean.getIdUtenteApplEst());
			lApplEstAccredXmlBean.setUsernameUtenteApplEst(lApplEstAccredBean.getUsernameUtenteApplEst());
			lApplEstAccredXmlBean.setPasswordUtenteApplEst(lApplEstAccredBean.getPasswordUtenteApplEst());
			lApplEstAccredXmlBean.setFlgRecDaEliminare("0");
			lApplEstAccredXmlBean.setIdUoApplEst(lApplEstAccredBean.getIdUoCollegataUtente() != null && lApplEstAccredBean.getIdUoCollegataUtente().startsWith("UO") ? lApplEstAccredBean.getIdUoCollegataUtente().substring(2) : lApplEstAccredBean.getIdUoCollegataUtente());
			lListlApplEstAccredXml.add(lApplEstAccredXmlBean);
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlApplEstAccred = lXmlUtilitySerializer.bindXmlList(lListlApplEstAccredXml);
		return xmlApplEstAccred;
	}
	
	protected String getXmlIndirizzi(GestioneUtentiBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		String xmlIndirizzi;
		List<IndirizzoSoggettoXmlBean> lListIndirizzi = new ArrayList<IndirizzoSoggettoXmlBean>();

		for (IndirizzoSoggettoBean lIndirizzoSoggettoBean : bean.getListaIndirizzi()) {
			IndirizzoSoggettoXmlBean lIndirizzoSoggettoXmlBean = new IndirizzoSoggettoXmlBean();
			lIndirizzoSoggettoXmlBean.setRowId(lIndirizzoSoggettoBean.getRowId());
			lIndirizzoSoggettoXmlBean.setTipo(lIndirizzoSoggettoBean.getTipo());
			lIndirizzoSoggettoXmlBean.setDataValidoDal(lIndirizzoSoggettoBean.getDataValidoDal());
			lIndirizzoSoggettoXmlBean.setDataValidoFinoAl(lIndirizzoSoggettoBean.getDataValidoFinoAl());
			lIndirizzoSoggettoXmlBean.setStato(lIndirizzoSoggettoBean.getStato());

			if (StringUtils.isBlank(lIndirizzoSoggettoBean.getStato()) || "200".equals(lIndirizzoSoggettoBean.getStato())) {
				lIndirizzoSoggettoXmlBean.setTipoToponimo(lIndirizzoSoggettoBean.getTipoToponimo());
				lIndirizzoSoggettoXmlBean.setToponimoIndirizzo(lIndirizzoSoggettoBean.getToponimo());
				lIndirizzoSoggettoXmlBean.setComune(lIndirizzoSoggettoBean.getComune());
				lIndirizzoSoggettoXmlBean.setProvincia(lIndirizzoSoggettoBean.getProvincia());
				lIndirizzoSoggettoXmlBean.setCap(lIndirizzoSoggettoBean.getCap());
			} else {
				lIndirizzoSoggettoXmlBean.setToponimoIndirizzo(lIndirizzoSoggettoBean.getIndirizzo());
				lIndirizzoSoggettoXmlBean.setNomeComuneCitta(lIndirizzoSoggettoBean.getCitta());
			}

			lIndirizzoSoggettoXmlBean.setCivico(lIndirizzoSoggettoBean.getCivico());
			lIndirizzoSoggettoXmlBean.setInterno(lIndirizzoSoggettoBean.getInterno());
			lIndirizzoSoggettoXmlBean.setFrazione(lIndirizzoSoggettoBean.getFrazione());
			lIndirizzoSoggettoXmlBean.setZona(lIndirizzoSoggettoBean.getZona());
			lIndirizzoSoggettoXmlBean.setComplementoIndirizzo(lIndirizzoSoggettoBean.getComplementoIndirizzo());
			lIndirizzoSoggettoXmlBean.setAppendici(lIndirizzoSoggettoBean.getAppendici());
			lListIndirizzi.add(lIndirizzoSoggettoXmlBean);
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlIndirizzi = lXmlUtilitySerializer.bindXmlList(lListIndirizzi);
		return xmlIndirizzi;
	}
	
	public LoadAttrDinamicoListaOutputBean settaAttributiDinamici(AnagraficaSoggettiBean bean) throws JAXBException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<DettColonnaAttributoListaBean> datiDettLista = new ArrayList<DettColonnaAttributoListaBean>();
		DettColonnaAttributoListaBean lDettColonnaAttributoListaBean1 = new DettColonnaAttributoListaBean();
		lDettColonnaAttributoListaBean1.setNome("RS_VENT_CORR");
		lDettColonnaAttributoListaBean1.setValoreDefault(bean.getRsVentCorr());
		datiDettLista.add(lDettColonnaAttributoListaBean1);
		LoadAttrDinamicoListaOutputBean result = new LoadAttrDinamicoListaOutputBean();
		result.setDatiDettLista(datiDettLista);
		return result;
	}
	
	
	public GestioneUtentiBean leggiInfoLDAPemail(GestioneUtentiBean bean) throws Exception {
		GestioneUtentiBean result = new GestioneUtentiBean();
		String email = bean.getEmail();
		if(email !=null && !email.equalsIgnoreCase("")){
			LdapAuth ldap = (LdapAuth)SpringAppContext.getContext().getBean("ldapAuth");
			
			// Leggo le informazioni chiedero' all'LDAP
			LdapAuthUserPropertiesBean ldapAuthUserProperties = (LdapAuthUserPropertiesBean)SpringAppContext.getContext().getBean("ldapAuthUserProperties");
			
			String cognomeProperties = ldapAuthUserProperties.getCognome();
			String emailProperties = ldapAuthUserProperties.getEmail();
			String matricolaProperties = ldapAuthUserProperties.getNroMatricola();
			String nomeProperties = ldapAuthUserProperties.getNome();
			String qualificaProperties = ldapAuthUserProperties.getQualifica();
			String titoloProperties = ldapAuthUserProperties.getTitolo();
			String usernameProperties = ldapAuthUserProperties.getUsername();
			
			// Creo la lista di attributi da chiedere all'LDAP
			List<String>  returnedAttsList = new ArrayList<String>();			
			
			if (cognomeProperties!=null && !cognomeProperties.equalsIgnoreCase("")){
				returnedAttsList.add(cognomeProperties);
			}
			if (emailProperties!=null && !emailProperties.equalsIgnoreCase("")){
				returnedAttsList.add(emailProperties);
			}				
			if (matricolaProperties!=null && !matricolaProperties.equalsIgnoreCase("")){
				returnedAttsList.add(matricolaProperties);
			}			
			if (nomeProperties!=null && !nomeProperties.equalsIgnoreCase("")){
				returnedAttsList.add(nomeProperties);
			}			
			if (qualificaProperties!=null && !qualificaProperties.equalsIgnoreCase("")){
				returnedAttsList.add(qualificaProperties);
			}
			if (titoloProperties!=null && !titoloProperties.equalsIgnoreCase("")){
				returnedAttsList.add(titoloProperties);
			}
			if (usernameProperties!=null && !usernameProperties.equalsIgnoreCase("")){
				returnedAttsList.add(usernameProperties);
			}
						
			Map<String, String> attrValueMap = new HashMap<String, String>();
			attrValueMap = ldap.searchUserPropertiesByEmail(email, returnedAttsList);
			if (attrValueMap!=null && attrValueMap.size()> 0){
				result = popolaInfoUtenteLdap(attrValueMap);
			}
			else{
				throw new StoreException(MessageUtil.getValue(getLocale().getLanguage(), getSession(), "gestioneutenti_importaDaLDAPError_message"));
			}
		}
		else{
			throw new StoreException(MessageUtil.getValue(getLocale().getLanguage(), getSession(), "gestioneutenti_importaDaLDAPemailError_message"));
		}
		return result;	
	}
	
	public GestioneUtentiBean leggiInfoLDAPusername(GestioneUtentiBean bean) throws Exception {
		GestioneUtentiBean result = new GestioneUtentiBean();
		String username = bean.getUsername();
		if(username !=null && !username.equalsIgnoreCase("")){			
			// Leggo le credeziali LDAP
			LdapAuth ldap = (LdapAuth)SpringAppContext.getContext().getBean("ldapAuth");
			
			// Leggo le informazioni chiedero' all'LDAP
			LdapAuthUserPropertiesBean ldapAuthUserProperties = (LdapAuthUserPropertiesBean)SpringAppContext.getContext().getBean("ldapAuthUserProperties");
			
			String cognomeProperties = ldapAuthUserProperties.getCognome();
			String emailProperties = ldapAuthUserProperties.getEmail();
			String matricolaProperties = ldapAuthUserProperties.getNroMatricola();
			String nomeProperties = ldapAuthUserProperties.getNome();
			String qualificaProperties = ldapAuthUserProperties.getQualifica();
			String titoloProperties = ldapAuthUserProperties.getTitolo();
			String usernameProperties = ldapAuthUserProperties.getUsername();
			
			// Creo la lista di attributi da chiedere all'LDAP
			List<String>  returnedAttsList = new ArrayList<String>();			
			
			if (cognomeProperties!=null && !cognomeProperties.equalsIgnoreCase("")){
				returnedAttsList.add(cognomeProperties);
			}
			if (emailProperties!=null && !emailProperties.equalsIgnoreCase("")){
				returnedAttsList.add(emailProperties);
			}				
			if (matricolaProperties!=null && !matricolaProperties.equalsIgnoreCase("")){
				returnedAttsList.add(matricolaProperties);
			}			
			if (nomeProperties!=null && !nomeProperties.equalsIgnoreCase("")){
				returnedAttsList.add(nomeProperties);
			}			
			if (qualificaProperties!=null && !qualificaProperties.equalsIgnoreCase("")){
				returnedAttsList.add(qualificaProperties);
			}
			if (titoloProperties!=null && !titoloProperties.equalsIgnoreCase("")){
				returnedAttsList.add(titoloProperties);
			}
			if (usernameProperties!=null && !usernameProperties.equalsIgnoreCase("")){
				returnedAttsList.add(usernameProperties);
			}
			
			Map<String, String> attrValueMap = new HashMap<String, String>();
			attrValueMap = ldap.searchUserPropertiesByUsername(username, returnedAttsList);
			if (attrValueMap!=null && attrValueMap.size()> 0){
				result = popolaInfoUtenteLdap(attrValueMap);
			}
			else{
				throw new StoreException(MessageUtil.getValue(getLocale().getLanguage(), getSession(), "gestioneutenti_importaDaLDAPError_message"));
			}
		}
		else{
			throw new StoreException(MessageUtil.getValue(getLocale().getLanguage(), getSession(), "gestioneutenti_importaDaLDAPusernameError_message"));
		}
		return result;
	}
	
	private GestioneUtentiBean popolaInfoUtenteLdap(Map<String, String> attrValueMap ){
		GestioneUtentiBean result = new GestioneUtentiBean();
		LdapAuthUserPropertiesBean ldapAuthUserProperties = (LdapAuthUserPropertiesBean) SpringAppContext.getContext().getBean("ldapAuthUserProperties");
		String cognomeProperties = ldapAuthUserProperties.getCognome();
		String nomeProperties = ldapAuthUserProperties.getNome();
		String usernameProperties = ldapAuthUserProperties.getUsername();
		String codFiscaleProperties = ldapAuthUserProperties.getCodFiscale();
		String titoloProperties = ldapAuthUserProperties.getTitolo();
		String matricolaProperties = ldapAuthUserProperties.getNroMatricola();
		String qualificaProperties = ldapAuthUserProperties.getQualifica();
		String emailProperties = ldapAuthUserProperties.getEmail();
		String[] listAttr = attrValueMap.keySet().toArray(new String[]{});
		for (String attrKey : listAttr){
			if (attrKey != null && !attrKey.equalsIgnoreCase("")){
				String attrVal = attrValueMap.get(attrKey);				
				if (attrKey.equalsIgnoreCase(usernameProperties)){   // Username
					result.setUsername(attrVal);
				}
				if (attrKey.equalsIgnoreCase(cognomeProperties)){    // cognome
					result.setCognome(attrVal);
				}
				if (attrKey.equalsIgnoreCase(nomeProperties)){       // Nome
					result.setNome(attrVal);					
				}
				if (attrKey.equalsIgnoreCase(codFiscaleProperties)){ // Cod. fiscale
					result.setCodFiscale(attrVal);					
				}							
				if (attrKey.equalsIgnoreCase(titoloProperties)){     // Titolo
					result.setTitolo(attrVal);
				}
				if (attrKey.equalsIgnoreCase(matricolaProperties)){  // Matricola
					result.setNroMatricola(attrVal);
				}																				
				if (attrKey.equalsIgnoreCase(qualificaProperties)){  // Qualifica
					result.setQualifica(attrVal);
				}								
				if (attrKey.equalsIgnoreCase(emailProperties)){      // Email
					result.setEmail(attrVal);
				}
			}
		}		
		return result;
	}
	
	
	/*
	 * Cancella una relazione UTENTE-APPLICAZIONE
	 */
	
	public TogliAccredUsersInApplEstBean TogliAccredUsersInApplEst(TogliAccredUsersInApplEstBean bean) throws Exception {
		
    	AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkDefSecurityTogliaccredusersinapplestBean input = new DmpkDefSecurityTogliaccredusersinapplestBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodapplicazioneesternain(bean.getCodiceApplEst());
		input.setCodistapplicazioneesternain(bean.getCodiceIstAppl());
		
		String xmlUtenti = null;
		if (bean.getListaUtentiXml() != null && bean.getListaUtentiXml().size() > 0) {
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			xmlUtenti = lXmlUtilitySerializer.bindXmlList(bean.getListaUtentiXml());
		}
		input.setListautentixmlin(xmlUtenti);

		DmpkDefSecurityTogliaccredusersinapplest service = new DmpkDefSecurityTogliaccredusersinapplest();
		StoreResultBean<DmpkDefSecurityTogliaccredusersinapplestBean> output = service.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;
	}
	
	/*
	 * Cancella una relazione UO-UTENTE
	 */
    public DRelUOUserBean DRelUOUser(DRelUOUserBean bean) throws Exception {
		
    	AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkDefSecurityDreluouserBean input = new DmpkDefSecurityDreluouserBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		input.setIduoin(StringUtils.isNotBlank(bean.getIdUo()) ? new BigDecimal(bean.getIdUo()) : null);
		input.setNrilivelliuoin(bean.getNriLivelliUO());
		input.setDenominazioneuoin(bean.getDenominazioneUO());
		input.setIduserin(StringUtils.isNotBlank(bean.getIdUser()) ? new BigDecimal(bean.getIdUser()) : null);
		input.setDesuserin(bean.getDesUser());
		input.setUsernamein(bean.getUsername());
		input.setFlgtiporelin(bean.getFlgTipoRel());
		input.setDtiniziovldin(bean.getDtInizioVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtInizioVld()) : null);
		input.setIdruoloammin(bean.getIdRuoloAmm());
		input.setDesruoloammin(bean.getDesRuoloAmm());
		input.setMotiviin(bean.getMotivi());
		
		input.setFlgignorewarningin(new Integer(1));
		
		DmpkDefSecurityDreluouser service = new DmpkDefSecurityDreluouser();
		StoreResultBean<DmpkDefSecurityDreluouserBean> output = service.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}

    

    protected void popolaAttributiDinamici(boolean isGestioneLingua, boolean isGestioneLogo, boolean isGestioneUtenteInterno , boolean isGestioneClienti, boolean isGestioneSocieta, boolean isVisibilitaEmailCaselleAssociateUO, boolean isDisattivaNotifDocDaPrendereInCarico, GestioneUtentiBean bean , Map<String, Object> valoriAttributiDinamici,  Map<String, String> tipiValoriAttributiDinamici) throws Exception {
    	
    	// *****************************************
    	// Attributi semplici
    	// *****************************************
    	
    	// attributo custom NOME LINGUA
		if (isGestioneLingua) {
			valoriAttributiDinamici.put("LINGUA", (String)bean.getIdLingua());
			tipiValoriAttributiDinamici.put("LINGUA", (String)"");
		}
		
		// attributo custom NOME LOGO
		if (isGestioneLogo) {
			valoriAttributiDinamici.put("LOGO", (String)bean.getNomeLogo());
			tipiValoriAttributiDinamici.put("LOGO", (String)"");
			
		}
		
		// attributo custom FLG UTENTE INTERNO/ESTERNO
		if(isGestioneUtenteInterno){
			valoriAttributiDinamici.put("UTENTE_INTERNO_ESTERNO", (String)bean.getFlgUtenteInternoEsterno());
			tipiValoriAttributiDinamici.put("UTENTE_INTERNO_ESTERNO", (String)"");			
		}
		
		//ORD_FIRMATARIO_FF_DG
		valoriAttributiDinamici.put("ORD_FIRMATARIO_FF_DG", (String)bean.getOrdFirmatariFFDG());
		tipiValoriAttributiDinamici.put("ORD_FIRMATARIO_FF_DG", (String)"");
		
		// attributo custom FLG_UTENZA_APPLICATIVA
		valoriAttributiDinamici.put("FLG_UTENZA_APPLICATIVA", (bean.getFlgUtenzaApplicativa()!=null && bean.getFlgUtenzaApplicativa()) ?  (String)"1" : (String)"");
		tipiValoriAttributiDinamici.put("FLG_UTENZA_APPLICATIVA", (String)"");
		
		// attributo custom FLG_DISATTIVA_NOTIF_X_DOC_DA_PRENDERE_IN_CARICO
		if (isDisattivaNotifDocDaPrendereInCarico){
			valoriAttributiDinamici.put("FLG_DISATTIVA_NOTIF_X_DOC_DA_PRENDERE_IN_CARICO", (bean.getFlgDisattivaNotifDocDaPrendereInCarico()!=null && bean.getFlgDisattivaNotifDocDaPrendereInCarico()) ?  (String)"1" : (String)"");
			tipiValoriAttributiDinamici.put("FLG_DISATTIVA_NOTIF_X_DOC_DA_PRENDERE_IN_CARICO", (String)"");
		}
		
		// *****************************************
    	// Attributi lista
    	// *****************************************
		
		// Lista con i clienti
		if (isGestioneClienti) {
			List<HashMap<String, String>> valoriLista = new ArrayList<HashMap<String, String>>();
			if (bean.getListaClientiUtente() != null && bean.getListaClientiUtente().size() > 0) {
				for (SoggettoGruppoBean lSoggettoGruppoBean : bean.getListaClientiUtente()) {
					HashMap<String, String> lMap = new HashMap<String, String>();
					lMap.put("1", lSoggettoGruppoBean.getIdSoggettoGruppo());
					valoriLista.add(lMap);
				}
			}
			valoriAttributiDinamici.put("ID_CLIENTE", valoriLista);
			tipiValoriAttributiDinamici.put("ID_CLIENTE", (String)"LISTA");
		}
		
		// Lista con i gruppi clienti
		if (isGestioneClienti) {
			List<HashMap<String, String>> valoriLista = new ArrayList<HashMap<String, String>>();
			if (bean.getListaGruppoClientiUtenti() != null && bean.getListaGruppoClientiUtenti().size() > 0) {
				for (GruppoClientiBean lGruppoClientiBean : bean.getListaGruppoClientiUtenti()) {
					HashMap<String, String> lMap = new HashMap<String, String>();
					lMap.put("1", lGruppoClientiBean.getIdGruppoClienti());
					valoriLista.add(lMap);
				}
			}
			valoriAttributiDinamici.put("COD_GRUPPO_RIF", valoriLista);
			tipiValoriAttributiDinamici.put("COD_GRUPPO_RIF", (String)"LISTA");
		}
		
		// Lista con le societa'
		if (isGestioneSocieta) {	
			List<HashMap<String, String>> valoriLista = new ArrayList<HashMap<String, String>>();
			if (bean.getListaSocietaUtenti() != null && bean.getListaSocietaUtenti().size() > 0) {
				for (SocietaUtenteBean lSocietaUtenteBean : bean.getListaSocietaUtenti()) {
					HashMap<String, String> lMap = new HashMap<String, String>();
					lMap.put("1", lSocietaUtenteBean.getIdSocieta());
					valoriLista.add(lMap);
				}
			}
			valoriAttributiDinamici.put("CID_APPL_SOCIETA", valoriLista);
			tipiValoriAttributiDinamici.put("CID_APPL_SOCIETA", (String)"LISTA");
		}
		
		
		
		// Lista Visibilita' Email Transitate dalle Caselle Associate
		if (isVisibilitaEmailCaselleAssociateUO) {
			List<HashMap<String, String>> valoriLista = new ArrayList<HashMap<String, String>>();
			if (bean.getListaVisibEmailTransCaselleAssociateUO() != null && bean.getListaVisibEmailTransCaselleAssociateUO().size() > 0) {
				for (VisibEmailCaselleUoBean lVisibEmailCaselleUoBean : bean.getListaVisibEmailTransCaselleAssociateUO()) {
					if (lVisibEmailCaselleUoBean.getOrganigramma() != null && !lVisibEmailCaselleUoBean.getOrganigramma().equalsIgnoreCase("")){
						HashMap<String, String> lMap = new HashMap<String, String>();
						lMap.put("1", (lVisibEmailCaselleUoBean.getOrganigramma() != null && lVisibEmailCaselleUoBean.getOrganigramma().startsWith("UO") ? lVisibEmailCaselleUoBean.getOrganigramma().substring(2) : lVisibEmailCaselleUoBean.getOrganigramma()));
						lMap.put("2", (lVisibEmailCaselleUoBean.isFlgIncludiSottoUO() ? "1" : "0"));
						valoriLista.add(lMap);
					}
				}
			}
			valoriAttributiDinamici.put("VISIB_EMAIL_CASELLE_UO", valoriLista);
			tipiValoriAttributiDinamici.put("VISIB_EMAIL_CASELLE_UO", (String)"LISTA");
		}

    }
    	
    protected void salvaAttributiCustomSemplici(GestioneUtentiBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
	}
    
    protected void salvaAttributiCustomLista(GestioneUtentiBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
	}  
    
    public GestioneUtentiBean leggiUOCollegatePuntoProtocollo(GestioneUtentiBean bean) throws Exception {
    	
		String idUOPP = bean.getIdUOPP() != null ? bean.getIdUOPP() : "";
		String idUser = bean.getIdUser() != null ? bean.getIdUser() : ""; 
		String listaUOPuntoProtocolloEscluse = bean.getListaUOPuntoProtocolloEscluse() != null ? bean.getListaUOPuntoProtocolloEscluse() : "";
		String listaUOPuntoProtocolloEreditarietaAbilitata = bean.getListaUOPuntoProtocolloEreditarietaAbilitata() != null ? bean.getListaUOPuntoProtocolloEreditarietaAbilitata() : "";
			
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);		
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("UO_COLLEGATE_PP");
		
		String altriParametri = "ID_UO_PP|*|" + idUOPP +"|*|ID_USER|*|" +idUser;
		// FIXME Qua devo pasare l'elenco delle uo abilitate con ereditarietà e quello non abilitate con ereditarietà
		if (listaUOPuntoProtocolloEscluse != null && !listaUOPuntoProtocolloEscluse.equalsIgnoreCase(""))
			altriParametri = altriParametri + "|*|LISTA_UO_ESCLUSE|*|" + listaUOPuntoProtocolloEscluse;
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		 
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
		
		List<UOCollegatePuntoProtocolloBean> lista = new ArrayList<UOCollegatePuntoProtocolloBean>();
		if(lStoreResultBean.getDefaultMessage() == null) {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			// TODEL codice per test (Eliminare quando i test sull'ereditarietà saranno finiti)
			// xmlLista="<?xml version=\"1.0\" encoding=\"UTF-8\"?><Lista><Riga><Colonna Nro=\"1\">1668</Colonna><Colonna Nro=\"2\">2.F</Colonna><Colonna Nro=\"3\">Direzione Servizi Civici Partecipate e Sport</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">2522</Colonna><Colonna Nro=\"2\">2.F.#</Colonna><Colonna Nro=\"3\">Direzione Servizi Civici Partecipazione e Sport - Archivio</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1943</Colonna><Colonna Nro=\"2\">2.F.A</Colonna><Colonna Nro=\"3\">DIRETTORE</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">2523</Colonna><Colonna Nro=\"2\">2.F.ASS1</Colonna><Colonna Nro=\"3\">Assessore Servizi Civici</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">2524</Colonna><Colonna Nro=\"2\">2.F.ASS2</Colonna><Colonna Nro=\"3\">Assessore Partecipazione</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">2525</Colonna><Colonna Nro=\"2\">2.F.ASS3</Colonna><Colonna Nro=\"3\">Assessore Sport</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1689</Colonna><Colonna Nro=\"2\">2.F.A01</Colonna><Colonna Nro=\"3\">Direzione Servizi Civici, Partecipazione e Sport - Segreteria di Direzione</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1947</Colonna><Colonna Nro=\"2\">2.F.A02</Colonna><Colonna Nro=\"3\">Ufficio Partecipazione Attiva</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1938</Colonna><Colonna Nro=\"2\">2.F.B</Colonna><Colonna Nro=\"3\">Unità Funzioni Trasversali</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1940</Colonna><Colonna Nro=\"2\">2.F.B.01</Colonna><Colonna Nro=\"3\">Ufficio Personale</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1941</Colonna><Colonna Nro=\"2\">2.F.B.02</Colonna><Colonna Nro=\"3\">Ufficio Bilancio/Acquisti</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1942</Colonna><Colonna Nro=\"2\">2.F.B.03</Colonna><Colonna Nro=\"3\">Ufficio Sicurezza/81</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1939</Colonna><Colonna Nro=\"2\">2.F.C</Colonna><Colonna Nro=\"3\">Unità Supporto Tecnico Informatico</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1690</Colonna><Colonna Nro=\"2\">2.F.@01</Colonna><Colonna Nro=\"3\">DSC.Direzione@pec.comune.milano.it</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">2537</Colonna><Colonna Nro=\"2\">2.F.1.#</Colonna><Colonna Nro=\"3\">Area Servizi al Cittadino - Archivio</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1403</Colonna><Colonna Nro=\"2\">2.F.1.A01</Colonna><Colonna Nro=\"3\">Area Servizi al Cittadino - Segreteria di Direzione</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1407</Colonna><Colonna Nro=\"2\">2.F.1.A02</Colonna><Colonna Nro=\"3\">Ufficio Supporto Commissione Elettorale Circondariale</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1809</Colonna><Colonna Nro=\"2\">2.F.1.A03</Colonna><Colonna Nro=\"3\">Ufficio Dichiarazione Anticipata di Trattamento</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1408</Colonna><Colonna Nro=\"2\">2.F.1.A04</Colonna><Colonna Nro=\"3\">Settore Servizi al Cittadino - Ufficio Supporto Amministrativo</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1428</Colonna><Colonna Nro=\"2\">2.F.1.B</Colonna><Colonna Nro=\"3\">Unità Gestione Protocollo, Albo Pretorio On Line e Servizi Recapito</Colonna><Colonna Nro=\"4\">1</Colonna><Colonna Nro=\"5\">1</Colonna><Colonna Nro=\"6\">0</Colonna></Riga><Riga><Colonna Nro=\"1\">1438</Colonna><Colonna Nro=\"2\">2.F.1.B.02</Colonna><Colonna Nro=\"3\">Ufficio PEC</Colonna><Colonna Nro=\"4\">1</Colonna><Colonna Nro=\"5\">0</Colonna><Colonna Nro=\"6\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1437</Colonna><Colonna Nro=\"2\">2.F.1.B.03</Colonna><Colonna Nro=\"3\">Ufficio Albo Pretorio On Line</Colonna><Colonna Nro=\"4\">1</Colonna><Colonna Nro=\"5\">0</Colonna><Colonna Nro=\"6\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1429</Colonna><Colonna Nro=\"2\">2.F.1.B.04</Colonna><Colonna Nro=\"3\">Ufficio Servizi di Recapito</Colonna><Colonna Nro=\"4\">1</Colonna><Colonna Nro=\"5\">0</Colonna><Colonna Nro=\"6\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1435</Colonna><Colonna Nro=\"2\">2.F.1.C</Colonna><Colonna Nro=\"3\">Unità Gestione Archivi Cartacei Casa Comunale e Civiche Depositerie</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1436</Colonna><Colonna Nro=\"2\">2.F.1.C.01</Colonna><Colonna Nro=\"3\">Ufficio Cittadella degli Archivi</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1439</Colonna><Colonna Nro=\"2\">2.F.1.C.02</Colonna><Colonna Nro=\"3\">Ufficio Casa Comunale</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1841</Colonna><Colonna Nro=\"2\">2.F.1.C.03</Colonna><Colonna Nro=\"3\">Ufficio Civiche Depositerie</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1837</Colonna><Colonna Nro=\"2\">2.F.1.C.04</Colonna><Colonna Nro=\"3\">Ufficio Gestione PGWEB</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1404</Colonna><Colonna Nro=\"2\">2.F.1.D</Colonna><Colonna Nro=\"3\">Unità Anagrafe e Messi  Civici</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1416</Colonna><Colonna Nro=\"2\">2.F.1.D.01</Colonna><Colonna Nro=\"3\">Ufficio Messi</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1409</Colonna><Colonna Nro=\"2\">2.F.1.D.02</Colonna><Colonna Nro=\"3\">Ufficio AIRE</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1412</Colonna><Colonna Nro=\"2\">2.F.1.D.03</Colonna><Colonna Nro=\"3\">Ufficio Coordinamento</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1413</Colonna><Colonna Nro=\"2\">2.F.1.D.04</Colonna><Colonna Nro=\"3\">Ufficio Corrispondenza e Ricerche</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1414</Colonna><Colonna Nro=\"2\">2.F.1.D.05</Colonna><Colonna Nro=\"3\">Ufficio Depennazioni</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1422</Colonna><Colonna Nro=\"2\">2.F.1.D.06</Colonna><Colonna Nro=\"3\">Ufficio Residenze</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1423</Colonna><Colonna Nro=\"2\">2.F.1.D.07</Colonna><Colonna Nro=\"3\">Ufficio Stranieri</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1421</Colonna><Colonna Nro=\"2\">2.F.1.D.08</Colonna><Colonna Nro=\"3\">Ufficio Pass</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1405</Colonna><Colonna Nro=\"2\">2.F.1.E</Colonna><Colonna Nro=\"3\">Unità Leva, Elettorale e Oggetti Rinvenuti</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1425</Colonna><Colonna Nro=\"2\">2.F.1.E.01</Colonna><Colonna Nro=\"3\">Ufficio Leva</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1424</Colonna><Colonna Nro=\"2\">2.F.1.E.02</Colonna><Colonna Nro=\"3\">Ufficio Elettorale</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1820</Colonna><Colonna Nro=\"2\">2.F.1.E.03</Colonna><Colonna Nro=\"3\">Ufficio Oggetti Rinvenuti</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1815</Colonna><Colonna Nro=\"2\">2.F.1.F</Colonna><Colonna Nro=\"3\">Unità di Sportello FRONT OFFICE</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1828</Colonna><Colonna Nro=\"2\">2.F.1.F.01</Colonna><Colonna Nro=\"3\">Ufficio Cassa</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1816</Colonna><Colonna Nro=\"2\">2.F.1.G</Colonna><Colonna Nro=\"3\">Unità Stato Civile</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1420</Colonna><Colonna Nro=\"2\">2.F.1.G.01</Colonna><Colonna Nro=\"3\">Ufficio Nascite</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1415</Colonna><Colonna Nro=\"2\">2.F.1.G.02</Colonna><Colonna Nro=\"3\">Ufficio Matrimoni</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1419</Colonna><Colonna Nro=\"2\">2.F.1.G.03</Colonna><Colonna Nro=\"3\">Ufficio Morti</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1411</Colonna><Colonna Nro=\"2\">2.F.1.G.04</Colonna><Colonna Nro=\"3\">Ufficio Cittadinanze</Colonna><Colonna Nro=\"4\">1</Colonna></Riga><Riga><Colonna Nro=\"1\">1410</Colonna><Colonna Nro=\"2\">2.F.1.G.05</Colonna><Colonna Nro=\"3\">Ufficio Archivio Stato Civile</Colonna><Colonna Nro=\"4\">1</Colonna></Riga></Lista>";
			lista = XmlListaUtility.recuperaLista(xmlLista, UOCollegatePuntoProtocolloBean.class);
		} 
		bean.setListaUOCollegatePuntoProtocollo(lista);
		return bean;
		
    }
    
    public GestioneUtentiBean leggiTipologieAtti(GestioneUtentiBean bean) throws Exception {
    	
//    	String fromSelectTipoAtto = StringUtils.isNotBlank(getExtraparams().get("fromSelectTipoAtto")) ? getExtraparams().get("fromSelectTipoAtto") : "";
    	
    	// Inizializzo l'INPUT
    	DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
    	DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
    	lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
    	lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);		
    	lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ATTO_CON_FLUSSO_WF");
    	
    	StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
    	
    	List<SelezionaTipiAttoBean> lista = new ArrayList<SelezionaTipiAttoBean>();
    	if(lStoreResultBean.getDefaultMessage() == null) {
    		String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
    		lista = XmlListaUtility.recuperaLista(xmlLista, SelezionaTipiAttoBean.class);
    	} 
    	bean.setListaSelezionaTipiAtto(lista);
    	return bean;
    	
    }
    
    public GestioneUtentiBean sbloccaUtente(GestioneUtentiBean bean) throws Exception {
    	
    	AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo input
		DmpkDefSecurityUnlockuserBean input = new DmpkDefSecurityUnlockuserBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		input.setIduserin(StringUtils.isNotBlank(bean.getIdUser()) ? new BigDecimal(bean.getIdUser()) : null);
    	
		// eseguo il servizio
		DmpkDefSecurityUnlockuser service = new DmpkDefSecurityUnlockuser();
		StoreResultBean<DmpkDefSecurityUnlockuserBean> output = service.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
    }

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		
		AdvancedCriteria criteria = bean.getCriteria();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
	
		DmpkDefSecurityTrovausersBean input = createFetchInput(loginBean, criteria, token, idUserLavoro, -2);

		DmpkDefSecurityTrovausers lservice = new DmpkDefSecurityTrovausers();
		StoreResultBean<DmpkDefSecurityTrovausersBean> output = lservice.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			}
		}

		//imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);
		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), GestioneUtentiXmlBean.class.getName());

		// salvo il deserializzatore di base perchè interessa l'esportazione dei soli campi originali
		saveRemapInformations(loginBean, jobId, createRemapConditions(), GestioneUtentiXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString() + " . Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return bean;
	}

	private Map<String, String> createRemapConditions() {
		return new HashMap<String, String>();
	}
	
	
	protected DmpkDefSecurityTrovausersBean createFetchInput(AurigaLoginBean loginBean, AdvancedCriteria criteria, String token, String idUserLavoro,Integer overflowLimit) throws Exception, JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1; // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		String desUser = null;
		String username = null;
		String email = null;
		String idProfiloUtente = null;
		BigDecimal flgSoloVld = null;
		Integer flgAccreditatiInDomIo = null;
		String idUOCollegata = null;
		String codRapidoUOCollegata = null;
		Boolean flgIncludiSottoUO = null;
		String flgTipoRelConUO = null;	

		

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("desUser".equals(crit.getFieldName())) {
					desUser = getTextFilterValue(crit);
				} else if ("username".equals(crit.getFieldName())) {
					username = getTextFilterValue(crit);
				} else if ("flgSoloVld".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgSoloVld = new Boolean(String.valueOf(crit.getValue())) ? new BigDecimal(1) : new BigDecimal(0);
					}
				} else if ("flgAccreditatiInDomIo".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgAccreditatiInDomIo = (Boolean) crit.getValue() ? new Integer(1) : null;
					}
				} else if ("email".equals(crit.getFieldName())) {
					email = getTextFilterValue(crit);
				} else if ("profiloUtente".equals(crit.getFieldName())) {
					idProfiloUtente = getTextFilterValue(crit);
				} else if ("uoCollegata".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						idUOCollegata = (String) map.get("idUo");
						codRapidoUOCollegata = (String) map.get("codRapido");
						flgIncludiSottoUO = map.get("flgIncludiSottoUO") != null && (Boolean) map.get("flgIncludiSottoUO");						
						if(map.get("tipoRelazione") != null) {
							for(String tipoRel : (ArrayList<String>) map.get("tipoRelazione")) {
								if(flgTipoRelConUO == null) {
									flgTipoRelConUO = tipoRel;
								} else {
									flgTipoRelConUO += ";" + tipoRel;
								}
							}
						} 					
					}
				}
			}
		}

		if (!loginBean.getSpecializzazioneBean().getPrivilegi().contains("SIC/UTE")) {
			flgAccreditatiInDomIo = new Integer(1);
		}

		// Inizializzo l'INPUT	
		DmpkDefSecurityTrovausersBean input = new DmpkDefSecurityTrovausersBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(overflowLimit);
		input.setFlgsenzatotin(null);
		input.setDescrizioneio(desUser);
		input.setUsernameio(username);
		input.setFlgsolovldio(flgSoloVld);
		input.setFlgaccreditatiindomio(flgAccreditatiInDomIo);
		input.setEmailin(email);
		input.setIdprofiloin(StringUtils.isNotBlank(idProfiloUtente) ? new Integer(idProfiloUtente) : null);	
		input.setIduoconrelvsuserio(StringUtils.isNotBlank(idUOCollegata) ? new BigDecimal(idUOCollegata) : null);
		input.setLivelliuoconrelvsuserio(codRapidoUOCollegata);
		input.setFlginclsottouoio(flgIncludiSottoUO != null && flgIncludiSottoUO ? new Integer(1) : null);
		input.setFlgtiporelconuoio(flgTipoRelConUO);
		
		return input;
		
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		AdvancedCriteria criteria = bean.getCriteria();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityTrovausersBean input = createFetchInput(loginBean, criteria, token, idUserLavoro, -1);

		DmpkDefSecurityTrovausers lservice = new DmpkDefSecurityTrovausers();
		StoreResultBean<DmpkDefSecurityTrovausersBean> output = lservice.execute(getLocale(), loginBean, input);
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		bean.setNroRecordTot(output.getResultBean().getNrototrecout());
		return bean;
	}	
	
}