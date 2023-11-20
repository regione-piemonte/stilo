/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_cogito.bean.DmpkCogitoReportcogitologBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.report.bean.DatasetReportCogitoLogBean;
import it.eng.auriga.ui.module.layout.server.report.bean.ReportCogitoLogBean;
import it.eng.auriga.ui.module.layout.server.report.bean.ReportCogitoLogFiltriXmlBean;
import it.eng.auriga.ui.module.layout.server.report.bean.ReportCogitoLogRaggruppamentiXmlBean;
import it.eng.auriga.ui.module.layout.server.report.bean.VerificaAbilFiltroInBean;
import it.eng.auriga.ui.module.layout.server.report.bean.VerificaAbilFiltroResultBean;
import it.eng.auriga.ui.module.layout.server.servlet.piechart.ReportCogitoLogResultBean;
import it.eng.auriga.ui.module.layout.server.statisticheDocumenti.datasource.LoadComboEnteAooDataSource;
import it.eng.auriga.ui.module.layout.server.statisticheDocumenti.datasource.bean.StatisticheDocumentiEnteAooBean;
import it.eng.client.DmpkCogitoReportcogitolog;
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

@Datasource(id = "ReportCogitoLogDatasource")
public class ReportCogitoLogDatasource extends AbstractServiceDataSource<ReportCogitoLogBean, DatasetReportCogitoLogBean>{

	private static Logger logger = Logger.getLogger(ReportCogitoLogDatasource.class);
	
	@Override
	public DatasetReportCogitoLogBean call(ReportCogitoLogBean pInBean) throws Exception {
				
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		// Inizializzo l'INPUT
		DmpkCogitoReportcogitologBean input = new DmpkCogitoReportcogitologBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgbatchin(null);
		
		// Passo il TIPO DI REPORT
		input.setTiporeportin(pInBean.getTipoReport());
		
		// Passo xml FILTRI
		ReportCogitoLogFiltriXmlBean lReportCogitoLogFiltriXmlBean = new ReportCogitoLogFiltriXmlBean();
		
		// filtro PERIODO 
		lReportCogitoLogFiltriXmlBean.setDataA(lSimpleDateFormat.format(pInBean.getA()));   
		lReportCogitoLogFiltriXmlBean.setDataDa(lSimpleDateFormat.format(pInBean.getDa()));		
		
		// filtro OPERATORE
		lReportCogitoLogFiltriXmlBean.setIdUtente(pInBean.getIdUtente());                   
		
		// filtro UO
		lReportCogitoLogFiltriXmlBean.setIdUO(pInBean.getIdUO());                           
		
		// filtro INCLUSE SOTTO UO
		lReportCogitoLogFiltriXmlBean.setFlgIncluseSottoUO(pInBean.isFlgIncluseSottoUO() ?       "1" : "0");
		
		// filtro CLASSIFICAZIONE SUGGERITA
		lReportCogitoLogFiltriXmlBean.setIdClassificazioneSuggerita(pInBean.getIdClassificazioneSuggerita()); 
		
		// filtro CLASSIFICAZIONE SCELTA
		lReportCogitoLogFiltriXmlBean.setIdClassificazioneScelta(pInBean.getIdClassificazioneScelta());
		
		// filtro ESITO
		lReportCogitoLogFiltriXmlBean.setIdEsito(pInBean.getIdEsito());
		
		// filtro ERRORE
		lReportCogitoLogFiltriXmlBean.setErrore(pInBean.isErrore() ?  "1" : "0");
				
		String filtriXml = lXmlUtilitySerializer.bindXml(lReportCogitoLogFiltriXmlBean);
		input.setFiltriin(filtriXml);
		
		// Passo xml RAGGRUPPAMENTI
		ReportCogitoLogRaggruppamentiXmlBean lReportCogitoLogRaggruppamentiXmlBean = new ReportCogitoLogRaggruppamentiXmlBean();
				
		// Raggruppa PERIODO
		lReportCogitoLogRaggruppamentiXmlBean.setRaggruppaPeriodo(pInBean.getRaggruppaPeriodo());
		
		// Raggruppa TIPO UO
		lReportCogitoLogRaggruppamentiXmlBean.setRaggruppaUo(pInBean.getRaggruppaUo());
		
		// Raggruppa OPERATORE
		lReportCogitoLogRaggruppamentiXmlBean.setRaggruppaUtente(pInBean.isRaggruppaUtente() ?  "1" : "0");
		
		// Raggruppa CLASSIFICAZIONE
		lReportCogitoLogRaggruppamentiXmlBean.setRaggruppaClassificazione(pInBean.isRaggruppaClassificazione() ? "1" : "0");
						
		// Raggruppa REGISTRAZIONE
		lReportCogitoLogRaggruppamentiXmlBean.setRaggruppaRegistrazione(pInBean.isRaggruppaRegistrazione() ? "1" : "0");
				
		String raggruppamentiXml = lXmlUtilitySerializer.bindXml(lReportCogitoLogRaggruppamentiXmlBean);
		input.setRaggruppamentiin(raggruppamentiXml);
		
		// Eseguo il servizio
		DmpkCogitoReportcogitolog servizio =  new DmpkCogitoReportcogitolog();
		StoreResultBean<DmpkCogitoReportcogitologBean> result = servizio.execute(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), input);
		
		DmpkCogitoReportcogitologBean lresultBean = new DmpkCogitoReportcogitologBean();
		
		if (result.isInError()){
			throw new Exception(result.getDefaultMessage());
		} else {
			lresultBean = result.getResultBean();
		}
				
		// Restituisco la lista XML con i dati del report ( Valorizzata solo se IdJobIO non è valorizzato in output )
		// -- Ogni record riporta dati e percentuali di un gruppo e corrisponde ad un tag Riga con le seguenti colonne:
		// -- La prima riga riporta i nomi delle colonne
		// -- 1: Oggetto Registrazione
		// -- 2: Codice della UO del raggruppamento (valorizzato se si raggruppa per UO)
		// -- 3: Nome della UO del raggruppamento (valorizzato se si raggruppa per UO)
		// -- 4: Username dell'utente del raggruppamento (valorizzato se si raggruppa per User)
		// -- 5: Cognome e Nome dell'utente del raggruppamento (valorizzato se si raggruppa per User)
		// -- 6: Numeri livelli classificazione
	    // -- 7: Descrizione classificazione'		
		// -- 8: Periodo (valorizzato se si raggruppa per Periodo): è sempre un numero
		// -- 9: N.ro di documenti del gruppo
		// --10: Percentuale che corrisponde al conteggio. In notazione italiana con la , come separatore dei decimali
		// --11: Percentuale arrotondata in modo tale che la somma delle varie percentuali sia 100. In notazione italiana con la , come separatore dei decimali
		
		DatasetReportCogitoLogBean output = new DatasetReportCogitoLogBean();
		
		output.setErrorCode(lresultBean.getErrcodeout());
		output.setErrorContext(lresultBean.getErrcontextout());
		output.setErrorMessage(lresultBean.getErrmsgout());
		output.setIdJob(lresultBean.getIdjobio());
		output.setNroRecord(lresultBean.getNrorecordout());
		output.setTitle(lresultBean.getReporttitleout());
		
		String xmlOut = lresultBean.getReportcontentsxmlout();		
		List<ReportCogitoLogResultBean> lList    = new ArrayList<ReportCogitoLogResultBean>();
		if (xmlOut!= null && !xmlOut.equalsIgnoreCase("")){
			lList = XmlListaUtility.recuperaLista(xmlOut, ReportCogitoLogResultBean.class);
		}
		output.setDataset(lList);
		
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