/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_statistiche.bean.DmpkStatisticheReportdocavanzatiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.report.bean.DatasetReportDocAvazatiBean;
import it.eng.auriga.ui.module.layout.server.report.bean.ReportDocAvanzatiBean;
import it.eng.auriga.ui.module.layout.server.report.bean.ReportDocAvanzatiFiltriXmlBean;
import it.eng.auriga.ui.module.layout.server.report.bean.ReportDocAvanzatiRaggruppamentiXmlBean;
import it.eng.auriga.ui.module.layout.server.report.bean.VerificaAbilFiltroInBean;
import it.eng.auriga.ui.module.layout.server.report.bean.VerificaAbilFiltroResultBean;
import it.eng.auriga.ui.module.layout.server.servlet.piechart.ReportDocAvanzatiResultBean;
import it.eng.auriga.ui.module.layout.server.statisticheDocumenti.datasource.LoadComboEnteAooDataSource;
import it.eng.auriga.ui.module.layout.server.statisticheDocumenti.datasource.bean.StatisticheDocumentiEnteAooBean;
import it.eng.client.DmpkStatisticheReportdocavanzati;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "ReportDocAvanzatiDatasource")
public class ReportDocAvanzatiDatasource extends AbstractServiceDataSource<ReportDocAvanzatiBean, DatasetReportDocAvazatiBean>{

	private static Logger logger = Logger.getLogger(ReportDocAvanzatiDatasource.class);
	
	@Override
	public DatasetReportDocAvazatiBean call(ReportDocAvanzatiBean pInBean) throws Exception {
				
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		// Inizializzo l'INPUT
		DmpkStatisticheReportdocavanzatiBean input = new DmpkStatisticheReportdocavanzatiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgbatchin(null);
		
		// Passo il TIPO DI REPORT
		input.setTiporeportin(pInBean.getTipoReport());
		
		// Passo xml FILTRI
		ReportDocAvanzatiFiltriXmlBean lReportDocAvanzatiFiltriXmlBean = new ReportDocAvanzatiFiltriXmlBean();
		
		// filtro APPLICAZIONI ESTERNE		
		String applicazioniEsterne = null;
		if(pInBean.getApplicazioniEsterne() != null) {
			for(int i = 0; i < pInBean.getApplicazioniEsterne().size(); i++) {
				if(applicazioniEsterne == null) {
					applicazioniEsterne = pInBean.getApplicazioniEsterne().get(i);
				} else {
					applicazioniEsterne += "," + pInBean.getApplicazioniEsterne().get(i);
				}
			}
		}
		lReportDocAvanzatiFiltriXmlBean.setApplicazioniEsterne(applicazioniEsterne);
		
		// filtro TIPO REGISTRAZIONE
		String tipoRegistrazione = null;
		if(pInBean.getTipoRegistrazione() != null) {
			for(int i = 0; i < pInBean.getTipoRegistrazione().size(); i++) {
				if(tipoRegistrazione == null) {
					tipoRegistrazione = pInBean.getTipoRegistrazione().get(i);
				} else {
					tipoRegistrazione += "," + pInBean.getTipoRegistrazione().get(i);
				}
			}
		}		
		lReportDocAvanzatiFiltriXmlBean.setTipoRegistrazione(tipoRegistrazione);
		
		// filtro CATEGORIA REGISTRAZIONE
		String categoriaRegistrazione = null;
		if(pInBean.getCategoriaRegistrazione() != null) {
			for(int i = 0; i < pInBean.getCategoriaRegistrazione().size(); i++) {
				if(categoriaRegistrazione == null) {
					categoriaRegistrazione = pInBean.getCategoriaRegistrazione().get(i);
				} else {
					categoriaRegistrazione += "," + pInBean.getCategoriaRegistrazione().get(i);
				}
			}
		}
		lReportDocAvanzatiFiltriXmlBean.setCategoriaRegistrazione(categoriaRegistrazione);
		
		// filtro MEZZO DI TRASMISSIONE
		String mezzoRegistrazione = null;
		if(pInBean.getMezzoTrasmissione() != null) {
			for(int i = 0; i < pInBean.getMezzoTrasmissione().size(); i++) {
				if(mezzoRegistrazione == null) {
					mezzoRegistrazione = pInBean.getMezzoTrasmissione().get(i);
				} else {
					mezzoRegistrazione += "," + pInBean.getMezzoTrasmissione().get(i);
				}
			}
		}
		lReportDocAvanzatiFiltriXmlBean.setMezzoTrasmissione(mezzoRegistrazione);
		
		// filtro LIVELLI RISERVATEZZA
		String livelliRiservatezza = null;
		if(pInBean.getLivelliRiservatezza() != null) {
			for(int i = 0; i < pInBean.getLivelliRiservatezza().size(); i++) {
				if(livelliRiservatezza == null) {
					livelliRiservatezza = pInBean.getLivelliRiservatezza().get(i);
				} else {
					livelliRiservatezza += "," + pInBean.getLivelliRiservatezza().get(i);
				}
			}
		}
		lReportDocAvanzatiFiltriXmlBean.setLivelliRiservatezza(livelliRiservatezza);
		
		// filtro REGISTRO DI NUMERAZIONE
		String registroNumerazione = null;
		if(pInBean.getRegistroNumerazione() != null) {
			for(int i = 0; i < pInBean.getRegistroNumerazione().size(); i++) {
				if(registroNumerazione == null) {
					registroNumerazione = pInBean.getRegistroNumerazione().get(i);
				} else {
					registroNumerazione += "," + pInBean.getRegistroNumerazione().get(i);
				}
			}
		}
		lReportDocAvanzatiFiltriXmlBean.setRegistroNumerazione(registroNumerazione);				
		
		// filtro PERIODO 
		lReportDocAvanzatiFiltriXmlBean.setDataA(lSimpleDateFormat.format(pInBean.getA()));   
		lReportDocAvanzatiFiltriXmlBean.setDataDa(lSimpleDateFormat.format(pInBean.getDa()));		
		
		// filtro ENTE/AOO
		lReportDocAvanzatiFiltriXmlBean.setIdEnteAoo(pInBean.getIdEnteAoo());				  
		
		// filtro OPERATORE
		lReportDocAvanzatiFiltriXmlBean.setIdUtente(pInBean.getIdUtente());                   
		
		// filtro PRESENZA FILE
		lReportDocAvanzatiFiltriXmlBean.setPresenzaFile(pInBean.getPresenzaFile());           
		
		// filtro SUPPORTO
		lReportDocAvanzatiFiltriXmlBean.setSupporto(pInBean.getSupporto());                   
		
		// filtro UO
		lReportDocAvanzatiFiltriXmlBean.setIdUO(pInBean.getIdUO());                           
		
		// filtro INCLUSE SOTTO UO
		lReportDocAvanzatiFiltriXmlBean.setFlgIncluseSottoUO(pInBean.isFlgIncluseSottoUO() ?       "1" : "0");
		
		String filtriXml = lXmlUtilitySerializer.bindXml(lReportDocAvanzatiFiltriXmlBean);
		input.setFiltriin(filtriXml);
		
		// Passo xml RAGGRUPPAMENTI
		ReportDocAvanzatiRaggruppamentiXmlBean lReportDocAvanzatiRaggruppamentiXmlBean = new ReportDocAvanzatiRaggruppamentiXmlBean();
				
		// Raggruppa PERIODO
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaPeriodo(pInBean.getRaggruppaPeriodo());
		
		// Raggruppa TIPO UO
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaUo(pInBean.getRaggruppaUo());
		
		// Raggruppa APPLICAZIONI ESTERNE
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaApplicazioniEsterne(pInBean.isRaggruppaApplicazioniEsterne() ?       "1" : "0");
		
		// Raggruppa CATEGORIA REGISTRAZIONE
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaCategoriaRegistrazione(pInBean.isRaggruppaCategoriaRegistrazione() ? "1" : "0");     
		
		// Raggruppa ENTE/AOO
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaEnteAoo(pInBean.isRaggruppaEnteAoo() ?                               "1" : "0");     
		
		// Raggruppa LIVELLI RISERVATEZZA
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaLivelliRiservatezza(pInBean.isRaggruppaLivelliRiservatezza() ?       "1" : "0");     
		
		// Raggruppa MEZZO DI TRASMISSIONE
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaMezzoTrasmissione(pInBean.isRaggruppaMezzoTrasmissione() ?           "1" : "0");     
		
		// Raggruppa PRESENZA FILE
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaPresenzaFile(pInBean.isRaggruppaPresenzaFile() ?                     "1" : "0");     
		
		// Raggruppa REGISTRO DI NUMERAZIONE
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaRegistroNumerazione(pInBean.isRaggruppaRegistroNumerazione() ?       "1" : "0");     
		
		// Raggruppa REGISTRAZIONI VALIDE / ANNULLATE
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaRegValideAnnullate(pInBean.isRaggruppaRegValideAnnullate() ?         "1" : "0");     
				
		// Raggruppa SUPPORTO
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaSupporto(pInBean.isRaggruppaSupporto() ?                             "1" : "0");     
		
		// Raggruppa TIPO REGISTRAZIONE
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaTipoRegistrazione(pInBean.isRaggruppaTipoRegistrazione() ?           "1" : "0");     
		
		// Raggruppa OPERATORE
		lReportDocAvanzatiRaggruppamentiXmlBean.setRaggruppaUtente(pInBean.isRaggruppaUtente() ?                                 "1" : "0");          
				
		String raggruppamentiXml = lXmlUtilitySerializer.bindXml(lReportDocAvanzatiRaggruppamentiXmlBean);
		input.setRaggruppamentiin(raggruppamentiXml);
		
		// Eseguo il servizio
		DmpkStatisticheReportdocavanzati servizio =  new DmpkStatisticheReportdocavanzati();
		StoreResultBean<DmpkStatisticheReportdocavanzatiBean> result = servizio.execute(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), input);
		
		DmpkStatisticheReportdocavanzatiBean lresultBean = new DmpkStatisticheReportdocavanzatiBean();
		
		if (result.isInError()){
			throw new Exception(result.getDefaultMessage());
		} else {
			lresultBean = result.getResultBean();
		}
		
		// Restituisco la lista XML con i dati del report ( Valorizzata solo se IdJobIO non è valorizzato in output )
		// -- Ogni record riporta dati e percentuali di un gruppo e corrisponde ad un tag Riga con le seguenti colonne:
		// -- La prima riga riporta i nomi delle colonne
		// -- 1: Id. del soggetto produttore/AOO cui si riferiscono dati e percentuali del record (valorizzato se si raggruppa per SpAOO)
		// -- 2: Nome del soggetto produttore/AOO cui si riferiscono dati e percentuali del record (valorizzato se si raggruppa per SpAOO)
		// -- 3: Codice della UO del raggruppamento (valorizzato se si raggruppa per UO)
		// -- 4: Nome della UO del raggruppamento (valorizzato se si raggruppa per UO)
		// -- 5: Cod. dell'applicazione del raggruppamento (valorizzato se si raggruppa per ApplicazioneReg)
		// -- 6: Nome dell'applicazione del raggruppamento (valorizzato se si raggruppa per ApplicazioneReg)
		// -- 7: Username dell'utente del raggruppamento (valorizzato se si raggruppa per User)
		// -- 8: Cognome e Nome dell'utente del raggruppamento (valorizzato se si raggruppa per User)
		// -- 9: Tipo di registrazione: E (Entrata), U (Uscita) e I (Interni) (valorizzato se si raggruppa per TipoRegistrazione)
		// -- 10: Categoria di registrazione : PG = Protocollo Generale, PP = Protocollo Particolare, R = Repertorio, A = Altro tipo di numerazione (valorizzato se si raggruppa per CategoriaRegistrazione)
		// -- 11: Sigla registro di registrazione (valorizzato se si raggruppa per SiglaRegistro)
		// -- 12: Supporto originale : digitale, analogico, misto (valorizzato se si raggruppa per SupportoOriginale)
		// -- 13: Indicazione 1/0 della presenza o meno di file sui documenti del gruppo (valorizzato se si raggruppa per PresenzaFile)
		// -- 14: Canale di ricezione/trasmissione (valorizzato se si raggruppa per Canale)
		// -- 15: Livello di riservatezza (valorizzato se si raggruppa per Riservatezza)
		// -- 16: Periodo (valorizzato se si raggruppa per Periodo): è sempre un numero
		// -- 17: N.ro di documenti del gruppo
		// -- 18: Percentuale che corrisponde al conteggio di col 17. In notazione italiana con la , come separatore dei decimali
		// -- 19: Percentuale arrotondata in modo tale che la somma delle varie percentuali sia 100. In notazione italiana con la , come separatore dei decimali
		DatasetReportDocAvazatiBean output = new DatasetReportDocAvazatiBean();
		
		output.setErrorCode(lresultBean.getErrcodeout());
		output.setErrorContext(lresultBean.getErrcontextout());
		output.setErrorMessage(lresultBean.getErrmsgout());
		output.setIdJob(lresultBean.getIdjobio());
		output.setNroRecord(lresultBean.getNrorecordout());
		output.setTitle(lresultBean.getReporttitleout());
		
		String xmlOut = lresultBean.getReportcontentsxmlout();		
		List<ReportDocAvanzatiResultBean> lList    = new ArrayList<ReportDocAvanzatiResultBean>();
		List<ReportDocAvanzatiResultBean> lListNew = new ArrayList<ReportDocAvanzatiResultBean>();
		
		if (xmlOut!= null && !xmlOut.equalsIgnoreCase("")){
			lList = XmlListaUtility.recuperaLista(xmlOut, ReportDocAvanzatiResultBean.class);			
			if(lList !=null && lList.size()>0){				
				for (ReportDocAvanzatiResultBean rec : lList){	
					if(rec.getPresenzaFile()!=null){
						if (rec.getPresenzaFile().equalsIgnoreCase("1")) {
							rec.setPresenzaFile("SI");
						} else if (rec.getPresenzaFile().equalsIgnoreCase("0")) {
							rec.setPresenzaFile("NO");
						}
					}	
					/*
					if (rec.getTipoRegistrazione()!=null){
						if (rec.getTipoRegistrazione().equalsIgnoreCase("E"))
							rec.setTipoRegistrazione("Entrata");
						else if (rec.getTipoRegistrazione().equalsIgnoreCase("U")) 
							rec.setTipoRegistrazione("Uscita");
						else if (rec.getTipoRegistrazione().equalsIgnoreCase("I")) 
							rec.setTipoRegistrazione("Interna");
					}
					*/
					lListNew.add(rec);
				}
			}
		}
		output.setDataset(lListNew);
		
		return output;
	}
	
	
	public VerificaAbilFiltroResultBean isAbilEnteAooFiltro(VerificaAbilFiltroInBean bean) throws Exception  {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		PaginatorBean<StatisticheDocumentiEnteAooBean> lPaginatorBean = new PaginatorBean<StatisticheDocumentiEnteAooBean>();
		LoadComboEnteAooDataSource lLoadComboEnteAooDataSource = new LoadComboEnteAooDataSource();
		
		VerificaAbilFiltroResultBean result =  new VerificaAbilFiltroResultBean();
	
		try {			
			lLoadComboEnteAooDataSource.setSession(getSession());			
			AdvancedCriteria lcriteria = new AdvancedCriteria();
			lPaginatorBean = lLoadComboEnteAooDataSource.fetch(lcriteria, null, null, null);
			int tipoDominio = loginBean.getSpecializzazioneBean().getTipoDominio();
			result.setResult(true);
			if(lPaginatorBean.getData()!=null && lPaginatorBean.getData().size()>0 && tipoDominio == 1
					){
				result.setEsito(true);
			}
			else{
				result.setEsito(false);
			}				
		}
		catch (Exception e) {
			result.setResult(false);
			result.setError(e.getMessage());
			result.setEsito(false);
			addMessage(e.getMessage(), "", MessageType.ERROR);
		}		
		return result;
	}
}