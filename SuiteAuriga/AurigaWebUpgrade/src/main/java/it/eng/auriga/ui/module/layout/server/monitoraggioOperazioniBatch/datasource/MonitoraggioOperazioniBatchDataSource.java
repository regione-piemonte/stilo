/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerInsrichopmassiveBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerSetstatorichopmassiveBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerTrovarichopbatchmassiveBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioOperazioniBatch.datasource.bean.AssegnatarioOpBatchXmlBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioOperazioniBatch.datasource.bean.CasellaBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioOperazioniBatch.datasource.bean.FiltriMonitoraggioOperazioniBatchBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioOperazioniBatch.datasource.bean.MonitoraggioOperazioniBatchBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioOperazioniBatch.datasource.bean.RichiestaChiusuraMassivaBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioOperazioniBatch.datasource.bean.RichiestaRiassegnazioneVariazioneBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioOperazioniBatch.datasource.bean.StatoRichOpMassiveBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioOperazioniBatch.datasource.bean.XmlDatiDettRichiestaChiusuraBean;
import it.eng.client.DmpkBmanagerInsrichopmassive;
import it.eng.client.DmpkBmanagerSetstatorichopmassive;
import it.eng.client.DmpkBmanagerTrovarichopbatchmassive;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.utility.XmlUtility;
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

@Datasource(id = "MonitoraggioOperazioniBatchDataSource")
public class MonitoraggioOperazioniBatchDataSource extends AurigaAbstractFetchDatasource<MonitoraggioOperazioniBatchBean> {

	@Override
	public String getNomeEntita() {
		return "monitoraggio_operazioni_batch";
	}
	
	@Override
	public MonitoraggioOperazioniBatchBean get(MonitoraggioOperazioniBatchBean bean) throws Exception {		
		return bean;
	}
	
	@Override
	public PaginatorBean<MonitoraggioOperazioniBatchBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
				
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkBmanagerTrovarichopbatchmassiveBean input =  createFetchInput(criteria, token, idUserLavoro);
				
		// Inizializzo l'OUTPUT
		DmpkBmanagerTrovarichopbatchmassive lservice = new DmpkBmanagerTrovarichopbatchmassive();
		StoreResultBean<DmpkBmanagerTrovarichopbatchmassiveBean> output = lservice.execute(getLocale(), loginBean, input);
		
		boolean overflow = false;
		
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
			if(output.isInError()) {
				throw new StoreException(defaultMessage);		
			} else {
				overflow = manageOverflow(defaultMessage);
			}
		}

		List<MonitoraggioOperazioniBatchBean> data = new ArrayList<MonitoraggioOperazioniBatchBean>();
		
		if (output.getResultBean().getNrototrecout() != null){		
			data = XmlListaUtility.recuperaLista(output.getResultBean().getResultout(), MonitoraggioOperazioniBatchBean.class);
		}
		
		PaginatorBean<MonitoraggioOperazioniBatchBean> lPaginatorBean = new PaginatorBean<MonitoraggioOperazioniBatchBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		
		return lPaginatorBean;
	}
	
	@Override
	public MonitoraggioOperazioniBatchBean add(MonitoraggioOperazioniBatchBean bean) throws Exception {
		return bean;
	}
	
	@Override
	public MonitoraggioOperazioniBatchBean update(MonitoraggioOperazioniBatchBean bean, MonitoraggioOperazioniBatchBean oldvalue) throws Exception {	
		return bean;
	}	
	
	@Override
	public MonitoraggioOperazioniBatchBean remove(MonitoraggioOperazioniBatchBean bean) throws Exception {	
		return bean;
	}
	
	public RichiestaChiusuraMassivaBean nuovaRichiestaChiusura(RichiestaChiusuraMassivaBean beanIn) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String xmlDatiDettRichiestaIn = null;
		
		DmpkBmanagerInsrichopmassiveBean input = new DmpkBmanagerInsrichopmassiveBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		// Setto l'input
		
		// Tipo = "chiusura"
		input.setTipooperazionein("chiusura");   
		
		// Tipo di operazione M = e-mail, F = fascicoli
		input.setTipoobjoperonin(beanIn.getTipoOggettiDaChiudere());
		
		// Data e ora a cui effettuare l’operazione  
		input.setTsdecorrenzain(beanIn.getDtOperazione() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP_WITH_SEC).format(beanIn.getDtOperazione()) : null);
		
		// Motivazione
		input.setMotivazionein(beanIn.getMotivazioneRichiesta());

		// Note
		input.setNotein(beanIn.getNoteRichiesta());
		
		// Xml dati richiesta
		XmlDatiDettRichiestaChiusuraBean xmlDatiDettRichiestaChiusuraBean  = new XmlDatiDettRichiestaChiusuraBean();
		
		// Lista delle caselle 		
		if(beanIn.getCaselle()!=null && !beanIn.getCaselle().equalsIgnoreCase("")){
			List<CasellaBean> listCaselle = new ArrayList<CasellaBean>();
			String[] valuesCaselle = beanIn.getCaselle().split(",");
			for (String idCasella : valuesCaselle) {
				CasellaBean casellaBean = new CasellaBean();
				casellaBean.setIdCasella(idCasella);
				listCaselle.add(casellaBean);
			}
			xmlDatiDettRichiestaChiusuraBean.setCaselle(listCaselle);
		}
		
		// Lista assegnatari visualizzati in una POPOUP (DIM_ORGANIGRAMMA_NONSTD = true)
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "DIM_ORGANIGRAMMA_NONSTD") ) {
			if(beanIn.getStruttureNONSTD()!=null && !beanIn.getStruttureNONSTD().isEmpty()){				
				List<AssegnatarioOpBatchXmlBean> uOAssegnatarieXml = new ArrayList<AssegnatarioOpBatchXmlBean>();
				for(AssegnatarioOpBatchXmlBean uoItem : beanIn.getStruttureNONSTD()) {
					AssegnatarioOpBatchXmlBean lAssegnatarioOpBatchXmlBean = new AssegnatarioOpBatchXmlBean();
					lAssegnatarioOpBatchXmlBean.setTypeNodo(uoItem.getTypeNodo());
					lAssegnatarioOpBatchXmlBean.setIdUo(uoItem.getIdUo());
					if(uoItem.isFlgIncludiSottoUO()){
						lAssegnatarioOpBatchXmlBean.setFlgSottoUO("1");	
					}
					else{
						lAssegnatarioOpBatchXmlBean.setFlgSottoUO("0");
					}
					uOAssegnatarieXml.add(lAssegnatarioOpBatchXmlBean);
				}
				xmlDatiDettRichiestaChiusuraBean.setUosvAssegnatarie(uOAssegnatarieXml);
			}
		}
		// Lista assegnatari visualizzati in una SELECT (DIM_ORGANIGRAMMA_NONSTD = false)
		else{
			if(beanIn.getStruttureSTD()!=null && !beanIn.getStruttureSTD().equalsIgnoreCase("")){
				List<AssegnatarioOpBatchXmlBean> listUoAssegnazione = new ArrayList<AssegnatarioOpBatchXmlBean>();
				String[] valuesUOSVAssegnatarie = beanIn.getStruttureSTD().split(",");
				for (String UOSVAssegnataria : valuesUOSVAssegnatarie) {
					AssegnatarioOpBatchXmlBean AssegnatarioBean = new AssegnatarioOpBatchXmlBean();
					String flgTipo = UOSVAssegnataria.substring(0,2);						
					String idUo = UOSVAssegnataria.substring(2);
					AssegnatarioBean.setTypeNodo(flgTipo);
					AssegnatarioBean.setIdUo(idUo);		
					listUoAssegnazione.add(AssegnatarioBean);
				}
				xmlDatiDettRichiestaChiusuraBean.setUosvAssegnatarie(listUoAssegnazione);
			}
			
			// indica la UO o scvivania a cui spostare; prefisso UO/SV (per indicare UO o scrivania) seguito da id. UO/scrivania (es: UO1229; SV619 )
			xmlDatiDettRichiestaChiusuraBean.setUosvTarget(beanIn.getStruttureSTD());
		}
			
		// Data di invio delle mail (DA)
		xmlDatiDettRichiestaChiusuraBean.setDataInvioDa(beanIn.getDataInvioDa());

		// Data di invio delle mail (A)
		xmlDatiDettRichiestaChiusuraBean.setDataInvioA(beanIn.getDataInvioA());
		
		if(beanIn.getTipoPeriodoApertura()!=null && !beanIn.getTipoPeriodoApertura().equalsIgnoreCase("")){		
			// n.ro di giorni da cui mail/fascicoli devono essere aperti
			if(beanIn.getTipoPeriodoApertura().equalsIgnoreCase("G")){
				xmlDatiDettRichiestaChiusuraBean.setOggApertiDaMinGG(beanIn.getPeriodoApertura());
			}
			// n.ro di mesi da cui mail/fascicoli devono essere aperti
			else if (beanIn.getTipoPeriodoApertura().equalsIgnoreCase("M")){
				xmlDatiDettRichiestaChiusuraBean.setOggApertiDaMinMesi(beanIn.getPeriodoApertura());
			}
		}
		
		if(beanIn.getPeriodoSenzaOperazioni()!=null && !beanIn.getPeriodoSenzaOperazioni().equalsIgnoreCase("")){		
			// n.ro di giorni da cui gli oggetti su cui agire devono essere senza operazioni
			if(beanIn.getPeriodoSenzaOperazioni().equalsIgnoreCase("G")){
				xmlDatiDettRichiestaChiusuraBean.setOggSenzaOperDaMinGG(beanIn.getPeriodoSenzaOperazioni());
			}
			// n.ro di mesi da cui gli oggetti su cui agire devono essere senza operazioni
			else if (beanIn.getPeriodoSenzaOperazioni().equalsIgnoreCase("M")){
				xmlDatiDettRichiestaChiusuraBean.setOggSenzaOperDaMinMesi(beanIn.getPeriodoSenzaOperazioni());
			}
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlDatiDettRichiestaIn = lXmlUtilitySerializer.bindXml(xmlDatiDettRichiestaChiusuraBean);  
		input.setXmldatidettrichiestain(xmlDatiDettRichiestaIn);
				
        // Eseguo il servizio
		DmpkBmanagerInsrichopmassive lservizio = new DmpkBmanagerInsrichopmassive();
		StoreResultBean<DmpkBmanagerInsrichopmassiveBean> output = lservizio.execute(getLocale(), loginBean, input);

		HashMap<String, String> errorMessages = new HashMap<String, String>();
		
		RichiestaChiusuraMassivaBean beanOut = new RichiestaChiusuraMassivaBean();
		// Leggo l'esito
		if (output.isInError()) {
			beanOut.setStoreInError(true);
			String storeErrorMesssage = StringUtils.isNotBlank(output.getDefaultMessage()) ? output.getDefaultMessage() : "";
			beanOut.setStoreErrorMessage(storeErrorMesssage);
		} 
		
		beanOut.setIdJob(output.getResultBean().getIdrichiestaopmassivaout());
		
		return beanOut;
	}
	
	public RichiestaRiassegnazioneVariazioneBean nuovaRichiestaRiassegnazioneVariazione(RichiestaRiassegnazioneVariazioneBean richiestaRiassegnazioneBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String tipoOperazione = getExtraparams().get("tipoOperazione");
		
		DmpkBmanagerInsrichopmassiveBean input = new DmpkBmanagerInsrichopmassiveBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		//Motivazione
		input.setMotivazionein(richiestaRiassegnazioneBean.getMotivazioneRichiesta());
		//Note
		input.setNotein(richiestaRiassegnazioneBean.getNoteRichiesta());
		//Tipo oggetto da riassegnare
		input.setTipoobjoperonin(richiestaRiassegnazioneBean.getTipoOggettoRiassegnare());
		//Data e ora in cui effettuare l'operazione
		input.setTsdecorrenzain(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(richiestaRiassegnazioneBean.getDataeOraOperazione()));
		//Tipo di operazione
		input.setTipooperazionein(tipoOperazione);

		//Creazione della sezione cache da passare alla store
		XmlDatiDettRichiestaChiusuraBean xmlDatiDettRichiestaChiusuraBean = new XmlDatiDettRichiestaChiusuraBean();

		if (StringUtils.isNotBlank(richiestaRiassegnazioneBean.getOrganigrammaUoPostazioneDa())) {
			List<AssegnatarioOpBatchXmlBean> listUoAssegnazione = new ArrayList<AssegnatarioOpBatchXmlBean>();

			AssegnatarioOpBatchXmlBean AssegnatarioBean = new AssegnatarioOpBatchXmlBean();
			String flgTipo = richiestaRiassegnazioneBean.getOrganigrammaUoPostazioneDa().substring(0, 2);
			String idUo = richiestaRiassegnazioneBean.getOrganigrammaUoPostazioneDa().substring(2);
			AssegnatarioBean.setTypeNodo(flgTipo);
			AssegnatarioBean.setIdUo(idUo);
			listUoAssegnazione.add(AssegnatarioBean);

			xmlDatiDettRichiestaChiusuraBean.setUosvAssegnatarie(listUoAssegnazione);
		}

		// indica la UO o scvivania a cui spostare; prefisso UO/SV (per indicare UO o
		// scrivania) seguito da id. UO/scrivania (es: UO1229; SV619 )
		xmlDatiDettRichiestaChiusuraBean.setUosvTarget(richiestaRiassegnazioneBean.getOrganigrammaUoPostazioneVs());

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlDatiDettRichiestaIn = lXmlUtilitySerializer.bindXml(xmlDatiDettRichiestaChiusuraBean);
		input.setXmldatidettrichiestain(xmlDatiDettRichiestaIn);
		
//		String xmlSezioneCacheDatiDettRichiesta = createSezioneCache(richiestaRiassegnazioneBean);
//		input.setXmldatidettrichiestain(xmlSezioneCacheDatiDettRichiesta);

		DmpkBmanagerInsrichopmassive service = new DmpkBmanagerInsrichopmassive();
		StoreResultBean<DmpkBmanagerInsrichopmassiveBean> output = service.execute(getLocale(), loginBean, input);
		
		RichiestaRiassegnazioneVariazioneBean beanOut = new RichiestaRiassegnazioneVariazioneBean();
		
		// Leggo l'esito
		if (output.isInError()) {
			beanOut.setStoreInError(true);
			String storeErrorMesssage = StringUtils.isNotBlank(output.getDefaultMessage()) ? output.getDefaultMessage() : "";
			beanOut.setStoreErrorMessage(storeErrorMesssage);
		} 
		
		return beanOut;
	}
	
	public StatoRichOpMassiveBean mettiTogliRichiestaDaRiprocessare(StatoRichOpMassiveBean statoRichOpMassiveBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkBmanagerSetstatorichopmassiveBean input = new DmpkBmanagerSetstatorichopmassiveBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		// Inserisco la lista dei processi da riprocessare
		if (statoRichOpMassiveBean.getIdRichiesteList() != null) {
			StringWriter sw = new StringWriter();
			SingletonJAXBContext.getInstance().createMarshaller().marshal(getListaFormList(statoRichOpMassiveBean.getIdRichiesteList()), sw);
			input.setXmlrichopmassivein(sw.toString());
		} else {
			throw new Exception("La lista dei processi e'' vuota");
		}

		// Inserisco lo stato 
		input.setStatoin(statoRichOpMassiveBean.getStato());
		
		// Inserisco le note
		input.setNotein(statoRichOpMassiveBean.getNote());
				
        // Eseguo il servizio
		DmpkBmanagerSetstatorichopmassive lservizio = new DmpkBmanagerSetstatorichopmassive();
		StoreResultBean<DmpkBmanagerSetstatorichopmassiveBean> output = lservizio.execute(getLocale(), loginBean, input);

		HashMap<String, String> errorMessages = new HashMap<String, String>();
		
		// Leggo l'esito
		if (output.isInError()) {
			statoRichOpMassiveBean.setStoreInError(true);
			String storeErrorMesssage = StringUtils.isNotBlank(output.getDefaultMessage()) ? output.getDefaultMessage() : "";
			statoRichOpMassiveBean.setStoreErrorMessage(storeErrorMesssage);
		} else if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(output.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
				// -- 3: Esito operazione reset stato: OK, KO
				if (v.get(2).equalsIgnoreCase("ko")) {
					// -- 4: Messaggio di errore ( Concateno le col. 1 + "#" + col 3 ) 
					String errMsg = v.get(1) + "#" + v.get(3);
					errorMessages.put(v.get(0), errMsg);    
				}
			}
		}
		statoRichOpMassiveBean.setErrorMessages(errorMessages);
		return statoRichOpMassiveBean;
	}

	private Lista getListaFormList(List<String> listaStringhe) {
		Lista lista = new Lista();
		if (listaStringhe != null) {
			for (String s : listaStringhe) {
				Riga riga = new Riga();
				Colonna col1 = new Colonna();
				col1.setNro(new BigInteger("1"));
				col1.setContent(s);
				riga.getColonna().add(col1);
				lista.getRiga().add(riga);
			}
		}
		return lista;
	}
	
	/**
	 * @param criteria
	 * @param token
	 * @param idUserLavoro
	 * @return
	 * @throws Exception
	 * @throws JAXBException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	protected DmpkBmanagerTrovarichopbatchmassiveBean createFetchInput(AdvancedCriteria criteria, String token, String idUserLavoro) throws Exception, JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1;			// 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		
		FiltriMonitoraggioOperazioniBatchBean filtri = new FiltriMonitoraggioOperazioniBatchBean();		
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){						
				if("tipoOperazione".equals(crit.getFieldName())) {
					filtri.setTipoOperazione(getTextFilterValue(crit));	
				} 
				else if ("tipoOggettiDaProcessare".equals(crit.getFieldName())) {
					filtri.setTipoOggettiDaProcessare(getTextFilterValue(crit));	
				} 		
				else if ("dataRichiesta".equals(crit.getFieldName())) {
					Date[] estremiData = getDateFilterValue(crit);
					filtri.setDtRichiestaDa(estremiData[0]);
					filtri.setDtRichiestaA(estremiData[1]);					
				} 
				else if ("dataSchedulazione".equals(crit.getFieldName())) {
					Date[] estremiData = getDateFilterValue(crit);
					filtri.setDtSchedulazioneDa(estremiData[0]);
					filtri.setDtSchedulazioneA(estremiData[1]);					
				}
				else if ("statoRichiesta".equals(crit.getFieldName())) {
					filtri.setStatoRichiesta(getTextFilterValue(crit));	
				} 
				else if ("motivoRichiesta".equals(crit.getFieldName())) {
					filtri.setMotivoRichiesta(getTextFilterValue(crit));	
				} 
				else if ("utenteRichiestaSottomissione".equals(crit.getFieldName())) {
					filtri.setUtenteRichiestaSottomissione(getTextFilterValue(crit));	
				} 				
				else if ("tipoEventoScatenante".equals(crit.getFieldName())) {
					filtri.setTipoEventoScatenante(getTextFilterValue(crit));	
				} 
				else if ("eventoScatenanteSuTipoOggetto".equals(crit.getFieldName())) {
					filtri.setEventoScatenanteSuTipoOggetto(getTextFilterValue(crit));	
				}
				else if ("fineUltimaElaborazione".equals(crit.getFieldName())) {
					Date[] estremiData = getDateConOraFilterValue(crit);
					if (estremiData[0] != null) {
						filtri.setDtFineUltimaElaborazioneDa(estremiData[0]);
					}
					if (estremiData[1] != null) {
						filtri.setDtFineUltimaElaborazioneA(estremiData[1]);
					}
				}
			}
		}
		
		// Inizializzo l'INPUT		
		DmpkBmanagerTrovarichopbatchmassiveBean input = new DmpkBmanagerTrovarichopbatchmassiveBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
						
		// Setto i filtri all'input del servizio
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setFiltriio(lXmlUtilitySerializer.bindXml(filtri));
		
		return input;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		AdvancedCriteria criteria = bean.getCriteria();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT		
		DmpkBmanagerTrovarichopbatchmassiveBean input = createFetchInput(criteria, token, idUserLavoro);
		input.setOverflowlimitin(-2);
		
		// Inizializzo l'OUTPUT
		DmpkBmanagerTrovarichopbatchmassive lservice = new DmpkBmanagerTrovarichopbatchmassive();
		StoreResultBean<DmpkBmanagerTrovarichopbatchmassiveBean> output = lservice.execute(getLocale(), loginBean, input);
				
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
				if(output.isInError()) {
					throw new StoreException(defaultMessage);		
				} 
		}		
		
		//imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);
		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), MonitoraggioOperazioniBatchBean.class.getName());
		
		updateJob(loginBean, bean, jobId, loginBean.getIdUser());
		
	    if(jobId!=null && jobId > 0){
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString() + " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}
		return bean;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		AdvancedCriteria criteria = bean.getCriteria();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT	
		DmpkBmanagerTrovarichopbatchmassiveBean input = createFetchInput(criteria, token, idUserLavoro);
		
		//non voglio overflow
		input.setOverflowlimitin(-1);
		
		//non mi interessano le colonne ritornate, solo il numero dei record
		//input.setColtoreturnin("1");
		
		// Inizializzo l'OUTPUT		
		DmpkBmanagerTrovarichopbatchmassive lservice = new DmpkBmanagerTrovarichopbatchmassive();
		StoreResultBean<DmpkBmanagerTrovarichopbatchmassiveBean> output = lservice.execute(getLocale(), loginBean, input);
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
	
	
	public String createSezioneCache(RichiestaRiassegnazioneVariazioneBean richiestaRiassegnazioneBean) throws JAXBException {
		SezioneCache lSezioneCache = new SezioneCache();
		
		if (StringUtils.isNotBlank(richiestaRiassegnazioneBean.getOrganigrammaUoPostazioneVs())) {
			Variabile varUOSVTarget = new Variabile();
			varUOSVTarget.setNome("UOSVTarget");
			varUOSVTarget.setValoreSemplice(richiestaRiassegnazioneBean.getOrganigrammaUoPostazioneVs());
			lSezioneCache.getVariabile().add(varUOSVTarget);
		}
		
		Variabile varUOSVAssegnatarie = new Variabile();
		varUOSVAssegnatarie.setNome("@UOSVAssegnatarie");
		SezioneCache.Variabile.Lista listaUOSVAssegnatarie = new SezioneCache.Variabile.Lista();		

		it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga riga = new it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga();

		String flgUOSV = null;
		String idOrganigramma = null;
		if(StringUtils.isNotBlank(richiestaRiassegnazioneBean.getOrganigrammaUoPostazioneDa())) {
			flgUOSV = richiestaRiassegnazioneBean.getOrganigrammaUoPostazioneDa().substring(0, 2);
			idOrganigramma = richiestaRiassegnazioneBean.getOrganigrammaUoPostazioneDa().substring(2);
		}
		
		it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna col1 = new it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna();
		col1.setNro(new BigInteger("1"));
		col1.setContent(flgUOSV);
		riga.getColonna().add(col1);
		
		it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna col2 = new it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna();
		col2.setNro(new BigInteger("2"));
		col2.setContent(idOrganigramma);
		riga.getColonna().add(col2);
		
		listaUOSVAssegnatarie.getRiga().add(riga);
			
		
		varUOSVAssegnatarie.setLista(listaUOSVAssegnatarie);
		lSezioneCache.getVariabile().add(varUOSVAssegnatarie);

		String xmlSezioneCacheDatiDettRichiesta = null;
		if (lSezioneCache != null) {
			StringWriter lStringWriter = new StringWriter();
			Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
			lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			lMarshaller.marshal(lSezioneCache, lStringWriter);
			xmlSezioneCacheDatiDettRichiesta = lStringWriter.toString();
		}
		
		return xmlSezioneCacheDatiDettRichiesta;
	}
}