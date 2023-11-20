/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspAddrecconttabellaBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspDcontenutosezBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspDrecconttabellaBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspGetcontenutisezBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspGetdaticonttabellaBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspGetdatireportBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspGetflgheaderrifnormativisezBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspIucontenutosezBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspLoaddettcontenutosezBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspRiordinacontenutisezBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspSpostacontenutosezBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspUpdrecconttabellaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.AttributiDinamiciDatasource;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.contenutiAmministrazioneTrasparente.datasource.bean.ContenutiAmmTraspXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.ContenutiAmmTraspBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.ContenutiDaRiordinareXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.ContenutoRigaTabellaBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.DatiFileDettContenutoSezXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.DocumentListBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.FileDaSelezionareBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.InfoDocumentoBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.InfoStrutturaTabellaBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.InfoStrutturaTabellaXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.RiordinaContenutiSezioneBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.report.bean.ReportTrasparenzaAmministrativaBean;
import it.eng.auriga.ui.module.layout.server.report.bean.ReportTrasparenzaAmministrativaFiltriXmlBean;
import it.eng.client.DmpkAmmTraspAddrecconttabella;
import it.eng.client.DmpkAmmTraspDcontenutosez;
import it.eng.client.DmpkAmmTraspDrecconttabella;
import it.eng.client.DmpkAmmTraspGetcontenutisez;
import it.eng.client.DmpkAmmTraspGetdaticonttabella;
import it.eng.client.DmpkAmmTraspGetdatireport;
import it.eng.client.DmpkAmmTraspGetflgheaderrifnormativisez;
import it.eng.client.DmpkAmmTraspIucontenutosez;
import it.eng.client.DmpkAmmTraspLoaddettcontenutosez;
import it.eng.client.DmpkAmmTraspRiordinacontenutisez;
import it.eng.client.DmpkAmmTraspSpostacontenutosez;
import it.eng.client.DmpkAmmTraspUpdrecconttabella;
import it.eng.client.GestioneDocumenti;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.client.SalvataggioFile;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.DestinatariBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.FileSavedIn;
import it.eng.document.function.bean.FileSavedOut;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.MittentiDocumentoBean;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.TipoProvenienza;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.XmlUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;


@Datasource(id = "ContenutiAmmTraspDatasource")
public class ContenutiAmmTraspDatasource extends AurigaAbstractFetchDatasource<ContenutiAmmTraspBean> {
	
	static Logger mLogger = Logger.getLogger(ContenutiAmmTraspDatasource.class);
	
	private static final String TIPO_CONTENUTO_FINE_SEZIONE = "fine_sezione";
	private static final String TIPO_CONTENUTO_PARAGRAFO = "paragrafo";
	private static final String TIPO_CONTENUTO_FILE_SEMPLICE = "file_semplice";
	private static final String TIPO_CONTENUTO_DOCUMENTO_COMPLESSO = "documento_complesso";
	private static final String TIPO_CONTENUTO_TABELLA = "tabella";
	private static final String TIPO_CONTENUTO_TITOLO_SEZIONE = "titolo_sezione";

	@Override
	public PaginatorBean<ContenutiAmmTraspBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT		
		DmpkAmmTraspGetcontenutisezBean input =  createFetchInput(criteria);
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		if (input.getIdsezionein()==null)
			return null;
		
		
		// Inizializzo l'OUTPUT
		DmpkAmmTraspGetcontenutisez lservice = new DmpkAmmTraspGetcontenutisez();
		StoreResultBean<DmpkAmmTraspGetcontenutisezBean> output = lservice.execute(getLocale(), loginBean, input);
		
		boolean overflow = false;
		
		// Leggo il result
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
			if(output.isInError()) {
				throw new StoreException(defaultMessage);		
			} else {
				overflow = manageOverflow(defaultMessage);
			}
		}
		
		// SETTO L'OUTPUT
		List<ContenutiAmmTraspBean> data = new ArrayList<ContenutiAmmTraspBean>();
		String idSezione = String.valueOf(input.getIdsezionein());
		
		if (output.getResultBean().getNrocontenutiout() != null){		
			data = XmlListaUtility.recuperaLista(output.getResultBean().getListacontenutiout(), ContenutiAmmTraspBean.class, new ContenutiAmmTraspXmlBeanDeserializationHelper(createRemapConditions(idSezione)));
		}
		
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);
		
		PaginatorBean<ContenutiAmmTraspBean> lPaginatorBean = new PaginatorBean<ContenutiAmmTraspBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}
	
	@Override
	public ContenutiAmmTraspBean get(ContenutiAmmTraspBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT
		DmpkAmmTraspLoaddettcontenutosezBean input = new DmpkAmmTraspLoaddettcontenutosezBean();
		
		input.setIdsezioneio(StringUtils.isNotBlank(bean.getIdSezione()) ? new BigDecimal(bean.getIdSezione()) : null);                   // Id. della sezione
		input.setIdcontenutosezin(StringUtils.isNotBlank(bean.getIdContenuto()) ? new BigDecimal(bean.getIdContenuto()) : null);          // Id. del contenuto
		input.setTipocontenutoio(bean.getTipoContenuto());                                                                                // Tipo di contenuto
		
		// Eseguo il servizio		
		DmpkAmmTraspLoaddettcontenutosez lservice = new DmpkAmmTraspLoaddettcontenutosez();
		StoreResultBean<DmpkAmmTraspLoaddettcontenutosezBean> result = lservice.execute(null, loginBean, input);
		
		// Leggo l'esito
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result.getDefaultMessage());
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		} 
		
		// Restituisco i dati
		bean.setTipoContenuto(result.getResultBean().getTipocontenutoio());                                                                                                       // Nro d'ordine nella sezione
		bean.setNroOrdineInSez(result.getResultBean().getNroordineinsezout());                                                                                                    // Nro d'ordine nella sezione
		bean.setTitoloContenuto(result.getResultBean().getTitoloout());                                                                                                           // Titolo. 
		bean.setHtmlContenuto(result.getResultBean().getTestohtmlsezout());                                                                                                       // Html con i dati di dettaglio da mostrare nella lista contenuti della sezione		
		bean.setTestoHtmlDettaglio(result.getResultBean().getTestohtmlindettaglioout());                                                                                          // Html con i dati di dettaglio da mostrare dopo il titolo nella maschera di dettaglio del contenuto
		
		/*Tale parametro ha la logica invertita se è true passare false e se false passare true*/
		bean.setFlgTestiHtmlUguali(((result.getResultBean().getFlgtestihtmlugualiout()!=null && result.getResultBean().getFlgtestihtmlugualiout() == 1) ? false : true));         // Se 1 indica che i due html precedenti sono uguali		
		bean.setFlgMostraDatiAggiornamento(((result.getResultBean().getFlgmostradatiaggout()!=null && result.getResultBean().getFlgmostradatiaggout() == 1) ? true : false));     // Se 1 significa che per il contenuto vanno mostrati dati di ultimo aggiornamento
		bean.setDatiAggHtml(result.getResultBean().getDatiaggout());                                                                                                              // Html con i dati di ultimo aggiornamento
		bean.setFlgMostraTastoExport(((result.getResultBean().getFlgexportopendataout()!=null && result.getResultBean().getFlgexportopendataout() == 1) ? true : false));         // Se 1 significa che per la sezione Ã¨ previsto export in open data, altrimenti no
		bean.setIdUd(result.getResultBean().getIdudout() !=null ? String.valueOf (result.getResultBean().getIdudout()) : null);                                                   // ID_UD dell'unitÃ  documentaria cui appartiene il contenuto documento
		bean.setTipoRegNum(result.getResultBean().getCodcategoriaregout());                                                                                                       // Codice della categoria di registrazione del documento: PG = Prot. geneerale, R = Repertorio, PP = Protocollo particolare, A = Altro
		bean.setSiglaRegNum(result.getResultBean().getSiglaregistroregout());                                                                                                     // Sigla del registro di registrazione del documento (valorizzato se categoria diversa da PG)
		bean.setAnnoRegNum(result.getResultBean().getAnnoregout() !=null ? String.valueOf (result.getResultBean().getAnnoregout()) : null);                                                        // Anno di registrazione del documento
		bean.setDataRegNum(result.getResultBean().getDataregout());                                                                                                               // Data di registrazione del documento (nel formato FMT_STD_DATA)
        bean.setNroRegNum(result.getResultBean().getNroregout() !=null ? String.valueOf (result.getResultBean().getNroregout()) : null);                                                           // NÂ° di registrazione del documento		
        bean.setIdDocFile(result.getResultBean().getIddocprimarioout() !=null ? String.valueOf (result.getResultBean().getIddocprimarioout()) : null);                            // ID_DOC del documento primario.
        bean.setNroVersioneFile(result.getResultBean().getNroverfileprimarioout() !=null ? String.valueOf (result.getResultBean().getNroverfileprimarioout()) : null);            // N.ro di versione del file del documento primario.
        bean.setFlgExportOpenData(((result.getResultBean().getFlgexportopendataout()!=null && result.getResultBean().getFlgexportopendataout() == 1) ? true : false));
        bean.setFlgPaginaDedicata(((result.getResultBean().getFlgpaginadedicataout()!=null && result.getResultBean().getFlgpaginadedicataout() == 1) ? true : false));
        
		// Leggo File		
		if (result.getResultBean().getDatifileprimarioout() != null && !result.getResultBean().getDatifileprimarioout().equalsIgnoreCase("")){
			List<DatiFileDettContenutoSezXmlBean> lListFilePrimario    = new ArrayList<DatiFileDettContenutoSezXmlBean>();
			lListFilePrimario = XmlListaUtility.recuperaLista(result.getResultBean().getDatifileprimarioout(), DatiFileDettContenutoSezXmlBean.class);
			if(lListFilePrimario.size()>0) {
				bean.setUriFile(lListFilePrimario.get(0).getUriFile());
				bean.setDisplayFile(lListFilePrimario.get(0).getDisplayFile());
				bean.setIdUd(lListFilePrimario.get(0).getIdUd());
				bean.setNroAllegato(lListFilePrimario.get(0).getNroAllegato());
				bean.setNroAllegatoHidden(lListFilePrimario.get(0).getNroAllegato());
				bean.setNroAllegato(lListFilePrimario.get(0).getNroAllegato());
				bean.setNroAllegatoHidden(lListFilePrimario.get(0).getNroAllegato());
			}
		}
		
		// Leggo DatiFileAllegatiOut
		if (result.getResultBean().getDatifileallegatiout() != null && !result.getResultBean().getDatifileallegatiout().equalsIgnoreCase("")){
			List<DatiFileDettContenutoSezXmlBean> lListFileAllegati    = new ArrayList<DatiFileDettContenutoSezXmlBean>();
			lListFileAllegati = XmlListaUtility.recuperaLista(result.getResultBean().getDatifileallegatiout(), DatiFileDettContenutoSezXmlBean.class);
			bean.setDatiFileAllegati(lListFileAllegati);	
		}
		
		bean.setNroRecPubblInTabella(result.getResultBean().getNrorecpubblintabellaout() !=null ? result.getResultBean().getNrorecpubblintabellaout() : null);                           // Nro di record attualmente pubblicati che ci sono nella tabella
		bean.setNroRecDaPubblicare(result.getResultBean().getNrorecdapubblicareout() !=null ? result.getResultBean().getNrorecdapubblicareout() : null);                                 // Nro di record ancora da pubblicare che ci sono nella tabella   
		bean.setDtInizioPubblicazione(result.getResultBean().getDtpubbldalout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(result.getResultBean().getDtpubbldalout()) : null);   // Data di inizio pubblicazione (nel formato del parametro FMT_STD_DATA)
		bean.setDtFinePubblicazione(result.getResultBean().getDtpubblalout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(result.getResultBean().getDtpubblalout()) : null);       // Data di fine pubblicazione (nel formato del parametro FMT_STD_DATA) 
		
		// Leggo InfoStrutturaTabOut
		List<InfoStrutturaTabellaBean> lListInfoStrutturaTabella = new ArrayList<InfoStrutturaTabellaBean>();
		if(StringUtils.isNotBlank(result.getResultBean().getInfostrutturatabout())) {
			StringReader sr = new StringReader(result.getResultBean().getInfostrutturatabout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					InfoStrutturaTabellaBean infoStrutturaTabellaBean = new InfoStrutturaTabellaBean();
					infoStrutturaTabellaBean.setIntestazione(v.get(0));                                                        //  1: Intestazione colonna
					infoStrutturaTabellaBean.setTipo(v.get(1));                                                                //  2: Tipo di colonna
					infoStrutturaTabellaBean.setLarghezza(StringUtils.isNotBlank(v.get(2)) ? new Integer(v.get(2)) : null );   //  3: larghezza percentuale della colonna (da 1 a 100)
					infoStrutturaTabellaBean.setNrPosizColonnaOrdinamento(v.get(3));                                           //  4: Indica se la colonna è quella o una di quelle per cui ordinare i dati
					infoStrutturaTabellaBean.setVersoOrdinamento(v.get(4));                                                    //  5: Indica il verso di ordinamento sulla colonna
					infoStrutturaTabellaBean.setFlgValoreObbligatorio(v.get(5) != null && "1".equals(v.get(5)));               //  6: (flag 1/0) Indica se campo obbligatorio (1) o meno (0)
					infoStrutturaTabellaBean.setFlgDettRiga(v.size() >6 && v.get(6) != null && "1".equals(v.get(6)));          //  7: (flag 1/0) Indica se campo obbligatorio (1) o meno (0)
					infoStrutturaTabellaBean.setValoriAmmessi(v.get(7));                                                       //  8: Valori ammessi
					lListInfoStrutturaTabella.add(infoStrutturaTabellaBean);
				}
			}
		}
		bean.setInfoStrutturaTabella(lListInfoStrutturaTabella);
		
		bean.setFlgAbilAuotorizzRich(result.getResultBean().getFlgabilauotorizzrichout() != null ? String.valueOf(result.getResultBean().getFlgabilauotorizzrichout()): null);
		bean.setStatoRichPubbl(result.getResultBean().getStatorichpubblout());
		bean.setMotivoRigetto(result.getResultBean().getMotivorigettoout());
				
		String statoAuotorizzRich = StringUtils.isNotBlank(getExtraparams().get("statoAuotorizzRich")) ? getExtraparams().get("statoAuotorizzRich") : "";
		bean.setStatoAuotorizzRich(statoAuotorizzRich);		
				
		return bean;
	}

	public ContenutiAmmTraspBean leggiFlgAbilAutorizzRich(ContenutiAmmTraspBean bean) throws Exception {
	
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT
		DmpkAmmTraspLoaddettcontenutosezBean input = new DmpkAmmTraspLoaddettcontenutosezBean();
		
		input.setIdsezioneio(StringUtils.isNotBlank(bean.getIdSezione()) ? new BigDecimal(bean.getIdSezione()) : null);                   // Id. della sezione
		input.setIdcontenutosezin(StringUtils.isNotBlank(bean.getIdContenuto()) ? new BigDecimal(bean.getIdContenuto()) : null);          // Id. del contenuto
		input.setTipocontenutoio(bean.getTipoContenuto());                                                                                // Tipo di contenuto
		
		// Eseguo il servizio		
		DmpkAmmTraspLoaddettcontenutosez lservice = new DmpkAmmTraspLoaddettcontenutosez();
		StoreResultBean<DmpkAmmTraspLoaddettcontenutosezBean> result = lservice.execute(null, loginBean, input);
		
		// Leggo l'esito
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result.getDefaultMessage());
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		} 
		
		// Restituisco i dati
		bean.setFlgAbilAuotorizzRich(result.getResultBean().getFlgabilauotorizzrichout() != null ? String.valueOf(result.getResultBean().getFlgabilauotorizzrichout()): "0");
				
		return bean;
	}
	
	@Override
	public ContenutiAmmTraspBean add(ContenutiAmmTraspBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
				
		// Inizializzo l'INPUT		
		DmpkAmmTraspIucontenutosezBean input = new DmpkAmmTraspIucontenutosezBean();
		
		input = inizializzaInput(bean);
		
		// Eseguo il servizio
		DmpkAmmTraspIucontenutosez lservice = new DmpkAmmTraspIucontenutosez();
		StoreResultBean<DmpkAmmTraspIucontenutosezBean> result = lservice.execute(getLocale(), loginBean, input);
		// Leggo l'esito
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result.getDefaultMessage());
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		// Restituisco il nuovo id contenuto
		bean.setIdContenuto(result.getResultBean().getIdcontenutosezio() !=null ? String.valueOf (result.getResultBean().getIdcontenutosezio()) : null);
		
		return bean;
	}

	@Override
	public ContenutiAmmTraspBean update(ContenutiAmmTraspBean bean, ContenutiAmmTraspBean oldvalue) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT		
		DmpkAmmTraspIucontenutosezBean input = new DmpkAmmTraspIucontenutosezBean();
		
		input = inizializzaInput(bean);
		
		// Eseguo il servizio
		DmpkAmmTraspIucontenutosez lservice = new DmpkAmmTraspIucontenutosez();
		StoreResultBean<DmpkAmmTraspIucontenutosezBean> result = lservice.execute(getLocale(), loginBean, input);
		
		// Leggo l'esito
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result.getDefaultMessage());
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		// Restituisco il nuovo id contenuto
		bean.setIdContenuto(result.getResultBean().getIdcontenutosezio() !=null ? String.valueOf (result.getResultBean().getIdcontenutosezio()) : null);
		
		return bean;
	}
		
	private FileStoreBean getBeanInfoFile(String uriFile, String displayFile) throws StorageException, Exception {
		FileStoreBean infoFileBean = new FileStoreBean();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		InfoFileUtility lFileUtility = new InfoFileUtility();
		
		File lFileToSave = StorageImplementation.getStorage().extractFile(uriFile);
		FileSavedIn lFileSavedIn = new FileSavedIn();
		lFileSavedIn.setSaved(lFileToSave);
		SalvataggioFile lSalvataggioFile = new SalvataggioFile();
		FileSavedOut out = lSalvataggioFile.savefile(getLocale(), loginBean, lFileSavedIn);
		
		MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(StorageImplementation.getStorage().getRealFile(out.getUri()).toURI().toString(), displayFile, false, null);

		if (lMimeTypeFirmaBean.getFirmatari() != null) {
			Firmatario firmatarioObject = new Firmatario();
			firmatarioObject.setTipoFirma(lMimeTypeFirmaBean.getTipoFirma());
			firmatarioObject.setInfoFirma(lMimeTypeFirmaBean.getInfoFirma());
			
			List<Firmatario> firmatari = new ArrayList<>();
			infoFileBean.setFirmatari(firmatari);
			
		} 
		infoFileBean.setFirmato(lMimeTypeFirmaBean.isFirmato() ? Flag.SETTED : Flag.NOT_SETTED);				
		
		if(lMimeTypeFirmaBean.getInfoFirmaMarca()!=null) {
			infoFileBean.setInfoVerificaMarcaTemporaleDocEl(lMimeTypeFirmaBean.getInfoFirmaMarca().getInfoMarcaTemporale());
			infoFileBean.setTipoMarcaTemporaleDocEl(lMimeTypeFirmaBean.getInfoFirmaMarca().getTipoMarcaTemporale());
			infoFileBean.setTsVerificaMarcaTempDocEl(lMimeTypeFirmaBean.getInfoFirmaMarca().getDataOraVerificaMarcaTemporale());
			infoFileBean.setTsMarcaTemporale(lMimeTypeFirmaBean.getInfoFirmaMarca().getDataOraMarcaTemporale());
		}
		infoFileBean.setDisplayFilename(displayFile);
		infoFileBean.setMimetype(lMimeTypeFirmaBean.getMimetype());
		infoFileBean.setImpronta(lMimeTypeFirmaBean.getImpronta());
		infoFileBean.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
		infoFileBean.setEncoding(lMimeTypeFirmaBean.getEncoding());
		infoFileBean.setDimensione(lMimeTypeFirmaBean.getBytes());
		infoFileBean.setUri(out.getUri());
		
		return infoFileBean;	
	}

	@Override
	public ContenutiAmmTraspBean remove(ContenutiAmmTraspBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT		
		DmpkAmmTraspDcontenutosezBean input = new DmpkAmmTraspDcontenutosezBean();
		input.setIdcontenutosezin(StringUtils.isNotBlank(bean.getIdContenuto()) ? new BigDecimal(bean.getIdContenuto()) : null);                               // Id. del contenuto
		input.setMotivoin(bean.getMotivoCancellazione());                                                                                                     // Tipo di contenuto
		
		// Eseguo il servizio
		DmpkAmmTraspDcontenutosez lservice = new DmpkAmmTraspDcontenutosez();
		StoreResultBean<DmpkAmmTraspDcontenutosezBean> result = lservice.execute(getLocale(), loginBean, input);
		
		// Leggo l'esito
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result.getDefaultMessage());
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
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
		
		// Inizializzo l'INPUT	
		DmpkAmmTraspGetcontenutisezBean input =  createFetchInput(criteria);
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		// Inizializzo l'OUTPUT
		DmpkAmmTraspGetcontenutisez lservice = new DmpkAmmTraspGetcontenutisez();
		StoreResultBean<DmpkAmmTraspGetcontenutisezBean> output = lservice.execute(getLocale(), loginBean, input);
				
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
			if(output.isInError()) {
				throw new StoreException(defaultMessage);		
			} 
		}		
		
		//imposto l'id del job creato
		//Integer jobId = output.getResultBean().getBachsizeio();
		
		//bean.setIdAsyncJob(jobId);
		//saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), ContenutiAmmTraspBean.class.getName());
		
		// salvo il deserializzatore di base perchÃ¨ interessa l'esportazione dei soli campi originali
		//saveRemapInformations(loginBean, jobId, createRemapConditions(), ContenutiAmmTraspXmlBeanDeserializationHelper.class);

		//updateJob(loginBean, bean, jobId, loginBean.getIdUser());
			
		/*
		if(jobId!=null && jobId > 0){
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString() + " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}
		*/
		return bean;
	}
	
	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		AdvancedCriteria criteria = bean.getCriteria();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT	
		DmpkAmmTraspGetcontenutisezBean input =  createFetchInput(criteria);
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		//non voglio overflow
		//input.setOverflowlimitin(-1);
				
		// Inizializzo l'OUTPUT		
		DmpkAmmTraspGetcontenutisez lservice = new DmpkAmmTraspGetcontenutisez();
		StoreResultBean<DmpkAmmTraspGetcontenutisezBean> output = lservice.execute(getLocale(), loginBean, input);
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		//bean.setNroRecordTot(output.getResultBean().getNrototrecout());
		return bean;
	}
	
	protected DmpkAmmTraspGetcontenutisezBean createFetchInput(AdvancedCriteria criteria) throws Exception, JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		// Inizializzo l'INPUT		
		DmpkAmmTraspGetcontenutisezBean input = new DmpkAmmTraspGetcontenutisezBean();
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {				
				if ("idNode".equals(crit.getFieldName())) {					
					String idSezione = getTextFilterValue(crit);
					input.setIdsezionein(idSezione != null ? new BigDecimal(idSezione) : null); 
				} else if ("dataPubblStoricheDa".equals(crit.getFieldName())) {					
					Date[] dataPubblStoricheDa = getDateFilterValue(crit);
					input.setDatapubblstorichedain(dataPubblStoricheDa != null ? new SimpleDateFormat(FMT_STD_DATA).format(dataPubblStoricheDa[0]) : null); 					
				} else if ("dataPubblStoricheA".equals(crit.getFieldName())) {					
					Date[] dataPubblStoricheA = getDateFilterValue(crit);
					input.setDatapubblstoricheain(dataPubblStoricheA != null ? new SimpleDateFormat(FMT_STD_DATA).format(dataPubblStoricheA[0]) : null);
				} else if ("cercaNelTitoloContenuto".equals(crit.getFieldName())) {					
					String cercaNelTitoloContenuto = getTextFilterValue(crit);
					input.setMatchwordlistin(cercaNelTitoloContenuto);
				} else if ("includiContenutiEliminati".equals(crit.getFieldName())) {
					Integer includiContenutiEliminati = new Boolean(String.valueOf(crit.getValue())) ? new Integer("1") : new Integer("0");
					input.setFlgincludipubbleliminatein(includiContenutiEliminati);
				}
			}
		}
		return input;
	}

	private Map<String, String> createRemapConditions(String idSezione) {
		Map<String, String> retValue = new LinkedHashMap<String, String>();
		retValue.put("idSezione", idSezione);
		return retValue;
	}		
	
	public void ordinamentoSezioneContenuti(RiordinaContenutiSezioneBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		
		DmpkAmmTraspRiordinacontenutisezBean input = new DmpkAmmTraspRiordinacontenutisezBean(); 
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		
		input.setIdsezionein(bean.getIdSezione() != null && !"".equalsIgnoreCase(bean.getIdSezione()) ? new BigDecimal(bean.getIdSezione()) : null);
		input.setContenutidariordinarein(getContenutiDaRiordinare(bean.getListContenuti()));
		
		DmpkAmmTraspRiordinacontenutisez service = new DmpkAmmTraspRiordinacontenutisez();
		StoreResultBean<DmpkAmmTraspRiordinacontenutisezBean> output = service.execute(getLocale(), loginBean, input);
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}		
	}
	
	public String getContenutiDaRiordinare(List<ContenutiDaRiordinareXmlBean> listContenuti) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, JAXBException {	
		String xmlResult = new XmlUtilitySerializer().bindXmlList(listContenuti);
		return xmlResult;
	}
	
	public InfoDocumentoBean caricaInfoDocumento(ContenutiAmmTraspBean bean) throws Exception {			
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());		
		
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
		lRecuperaDocumentoInBean.setIdProcess(null);
		lRecuperaDocumentoInBean.setTaskName("#NONE");
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		
		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		ProtocollazioneBean lProtocollazioneBean = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean, getExtraparams());
		
		InfoDocumentoBean infoDocumentoBean = new InfoDocumentoBean();
				
		// Dati atto
		infoDocumentoBean.setIdUd(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
		infoDocumentoBean.setSegnatura(lDocumentoXmlOutBean.getSegnatura());
		if(lDocumentoXmlOutBean.getEstremiRegistrazione()!=null) {
			infoDocumentoBean.setTipoRegNum(lDocumentoXmlOutBean.getEstremiRegistrazione().getTipo());
			infoDocumentoBean.setSiglaRegNum(lDocumentoXmlOutBean.getEstremiRegistrazione().getSigla());
			infoDocumentoBean.setAnnoRegNum(lDocumentoXmlOutBean.getEstremiRegistrazione().getAnno());
			infoDocumentoBean.setNroRegNum(lDocumentoXmlOutBean.getEstremiRegistrazione().getNro());
		}		
		infoDocumentoBean.setTipo(lDocumentoXmlOutBean.getTipoDocumento());	
		infoDocumentoBean.setNomeTipo(lDocumentoXmlOutBean.getNomeTipoDocumento());
		infoDocumentoBean.setIdDoc(lDocumentoXmlOutBean.getIdDocPrimario());	
		
		if(lProtocollazioneBean.getListaAllegati()!=null && lProtocollazioneBean.getListaAllegati().size()>0) {
					
			List<FileDaSelezionareBean> listaFile = creaListaFileDaSelezionare(lProtocollazioneBean);
			
			infoDocumentoBean.setListaAllegati(listaFile);
		}
		
		return infoDocumentoBean;	
	}
	
	private List<FileDaSelezionareBean> creaListaFileDaSelezionare(ProtocollazioneBean lProtocollazioneBean) {
		
		List<FileDaSelezionareBean> listaFile = new ArrayList<>();
		DocumentBean filePrimarioOmissis = lProtocollazioneBean.getFilePrimarioOmissis();
		//File primario senza omissis
		if ((lProtocollazioneBean.getUriFilePrimario() != null
				&& !"".equals(lProtocollazioneBean.getUriFilePrimario())) && 
				((filePrimarioOmissis.getUriFile() == null) || "".equals(filePrimarioOmissis.getUriFile()))) {
			
			FileDaSelezionareBean filePrimarioIntegrale = new FileDaSelezionareBean();
			filePrimarioIntegrale.setNomeFileAllegato("File primario - " + lProtocollazioneBean.getNomeFilePrimario());
			filePrimarioIntegrale.setIdDocAllegato(lProtocollazioneBean.getIdDocPrimario());
			filePrimarioIntegrale.setRemoteUri(lProtocollazioneBean.getRemoteUriFilePrimario());
			filePrimarioIntegrale.setInfoFile(lProtocollazioneBean.getInfoFile());
			filePrimarioIntegrale.setUriFileAllegato(lProtocollazioneBean.getUriFilePrimario());
			filePrimarioIntegrale.setNroAllegato("");
			
			listaFile.add(filePrimarioIntegrale);
		}
		
		//File primario con versione omissis
		else if((lProtocollazioneBean.getUriFilePrimario() != null
				&& !"".equals(lProtocollazioneBean.getUriFilePrimario())) && 
				((filePrimarioOmissis.getUriFile() != null) && !"".equals(filePrimarioOmissis.getUriFile()))) {
			
			//File primario integrale
			FileDaSelezionareBean filePrimarioIntegrale = new FileDaSelezionareBean();
			filePrimarioIntegrale.setNomeFileAllegato("File primario (vers. integrale) - " + lProtocollazioneBean.getNomeFilePrimario());
			filePrimarioIntegrale.setIdDocAllegato(lProtocollazioneBean.getIdDocPrimario());
			filePrimarioIntegrale.setRemoteUri(lProtocollazioneBean.getRemoteUriFilePrimario());
			filePrimarioIntegrale.setInfoFile(lProtocollazioneBean.getInfoFile());
			filePrimarioIntegrale.setUriFileAllegato(lProtocollazioneBean.getUriFilePrimario());
			filePrimarioIntegrale.setNroAllegato("");
			
			listaFile.add(filePrimarioIntegrale);
			
			//File primario omissis
			FileDaSelezionareBean filePrimarioOmissisDaSelezionare = new FileDaSelezionareBean();
			filePrimarioOmissisDaSelezionare.setNomeFileAllegato("File primario (vers. con omissis) - " + filePrimarioOmissis.getNomeFile());
			filePrimarioOmissisDaSelezionare.setIdDocAllegato(new BigDecimal(filePrimarioOmissis.getIdDoc()));
			filePrimarioOmissisDaSelezionare.setRemoteUri(filePrimarioOmissis.getRemoteUri());
			filePrimarioOmissisDaSelezionare.setInfoFile(filePrimarioOmissis.getInfoFile());
			filePrimarioOmissisDaSelezionare.setUriFileAllegato(filePrimarioOmissis.getUriFile());
			filePrimarioOmissisDaSelezionare.setNroAllegato("");
			
			listaFile.add(filePrimarioOmissisDaSelezionare);
		}
		
		//File primario solo omissis
		else if((lProtocollazioneBean.getUriFilePrimario() == null
				|| "".equals(lProtocollazioneBean.getUriFilePrimario())) && 
				((filePrimarioOmissis.getUriFile() != null) && !"".equals(filePrimarioOmissis.getUriFile()))) {
			
			FileDaSelezionareBean filePrimarioOmissisDaSelezionare = new FileDaSelezionareBean();
			filePrimarioOmissisDaSelezionare.setNomeFileAllegato("File primario (vers. con omissis) - " + filePrimarioOmissis.getNomeFile());
			filePrimarioOmissisDaSelezionare.setIdDocAllegato(new BigDecimal(filePrimarioOmissis.getIdDoc()));
			filePrimarioOmissisDaSelezionare.setRemoteUri(filePrimarioOmissis.getRemoteUri());
			filePrimarioOmissisDaSelezionare.setInfoFile(filePrimarioOmissis.getInfoFile());
			filePrimarioOmissisDaSelezionare.setUriFileAllegato(filePrimarioOmissis.getUriFile());
			filePrimarioOmissisDaSelezionare.setNroAllegato("");
			
			listaFile.add(filePrimarioOmissisDaSelezionare);
		}
		
		//Aggiungo gli allegati
		List<AllegatoProtocolloBean> listaAllegati = lProtocollazioneBean.getListaAllegati();
		
		for (AllegatoProtocolloBean allegatoProtBean : listaAllegati) {
			//Allegato senza omissis
			if((allegatoProtBean.getUriFileAllegato()!=null && !"".equals(allegatoProtBean.getUriFileAllegato())) 
					&& (allegatoProtBean.getUriFileOmissis()==null || "".equals(allegatoProtBean.getUriFileOmissis()))) {
				
				FileDaSelezionareBean allegatoDaSelezionare = new FileDaSelezionareBean();
				allegatoDaSelezionare.setNomeFileAllegato("Allegato N°" + allegatoProtBean.getNumeroProgrAllegato() + " - " + allegatoProtBean.getNomeFileAllegato());
				allegatoDaSelezionare.setIdDocAllegato(allegatoProtBean.getIdDocAllegato());
				allegatoDaSelezionare.setRemoteUri(allegatoProtBean.getRemoteUri());
				allegatoDaSelezionare.setInfoFile(allegatoProtBean.getInfoFile());
				allegatoDaSelezionare.setUriFileAllegato(allegatoProtBean.getUriFileAllegato());
				allegatoDaSelezionare.setNroAllegato(allegatoProtBean.getNroAllegato());
				
				listaFile.add(allegatoDaSelezionare);
			}
			
			//Entrambi versioni di allegati
			else if((allegatoProtBean.getUriFileAllegato()!=null && !"".equals(allegatoProtBean.getUriFileAllegato())) 
					&& (allegatoProtBean.getUriFileOmissis()!=null && !"".equals(allegatoProtBean.getUriFileOmissis()))) {
				
				//Allegato integrale
				FileDaSelezionareBean allegatoDaSelezionare = new FileDaSelezionareBean();
				allegatoDaSelezionare.setNomeFileAllegato("Allegato N°" + allegatoProtBean.getNumeroProgrAllegato() + " (vers. integrale) - " + allegatoProtBean.getNomeFileAllegato());
				allegatoDaSelezionare.setIdDocAllegato(allegatoProtBean.getIdDocAllegato());
				allegatoDaSelezionare.setRemoteUri(allegatoProtBean.getRemoteUri());
				allegatoDaSelezionare.setInfoFile(allegatoProtBean.getInfoFile());
				allegatoDaSelezionare.setUriFileAllegato(allegatoProtBean.getUriFileAllegato());
				allegatoDaSelezionare.setNroAllegato(allegatoProtBean.getNroAllegato());
				
				listaFile.add(allegatoDaSelezionare);
				
				//Alegato omissis
				FileDaSelezionareBean allegatoOmissisDaSelezionare = new FileDaSelezionareBean();
				allegatoOmissisDaSelezionare.setNomeFileAllegato("Allegato N°" + allegatoProtBean.getNumeroProgrAllegato() + " (vers. con omissis) - " + allegatoProtBean.getNomeFileOmissis());
				allegatoOmissisDaSelezionare.setIdDocAllegato(allegatoProtBean.getIdDocOmissis());
				allegatoOmissisDaSelezionare.setRemoteUri(allegatoProtBean.getRemoteUriOmissis());
				allegatoOmissisDaSelezionare.setInfoFile(allegatoProtBean.getInfoFileOmissis());
				allegatoOmissisDaSelezionare.setUriFileAllegato(allegatoProtBean.getUriFileOmissis());
				allegatoOmissisDaSelezionare.setNroAllegato(allegatoProtBean.getNroAllegato());
				
				listaFile.add(allegatoOmissisDaSelezionare);
			}
			
			//Allegato solo omissis
			else if((allegatoProtBean.getUriFileAllegato()==null || "".equals(allegatoProtBean.getUriFileAllegato())) 
					&& (allegatoProtBean.getUriFileOmissis()!=null && !"".equals(allegatoProtBean.getUriFileOmissis()))) {
				
				FileDaSelezionareBean allegatoOmissisDaSelezionare = new FileDaSelezionareBean();
				allegatoOmissisDaSelezionare.setNomeFileAllegato("Allegato N°" + allegatoProtBean.getNumeroProgrAllegato() + " (vers. con omissis) - " + allegatoProtBean.getNomeFileOmissis());
				allegatoOmissisDaSelezionare.setIdDocAllegato(allegatoProtBean.getIdDocOmissis());
				allegatoOmissisDaSelezionare.setRemoteUri(allegatoProtBean.getRemoteUriOmissis());
				allegatoOmissisDaSelezionare.setInfoFile(allegatoProtBean.getInfoFileOmissis());
				allegatoOmissisDaSelezionare.setUriFileAllegato(allegatoProtBean.getUriFileOmissis());
				allegatoOmissisDaSelezionare.setNroAllegato(allegatoProtBean.getNroAllegato());
				
				listaFile.add(allegatoOmissisDaSelezionare);
			}
		}	
		
		return listaFile;
	}

	public ContenutiAmmTraspBean leggiFlgHeaderRifNormativi(ContenutiAmmTraspBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT
		DmpkAmmTraspGetflgheaderrifnormativisezBean input = new DmpkAmmTraspGetflgheaderrifnormativisezBean();
		input.setIdsezionein(StringUtils.isNotBlank(bean.getIdSezione()) ? new BigDecimal(bean.getIdSezione()) : null);                   // Id. della sezione
		
		// Eseguo il servizio		
		DmpkAmmTraspGetflgheaderrifnormativisez lservice = new DmpkAmmTraspGetflgheaderrifnormativisez();
		StoreResultBean<DmpkAmmTraspGetflgheaderrifnormativisezBean> result = lservice.execute(null, loginBean, input);
				
		// Leggo l'esito
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result.getDefaultMessage());
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		} 
		
		// Restituisco i dati
		bean.setFlgPresenteHeader(result.getResultBean().getFlgpresenteheaderout()!=null ? String.valueOf(result.getResultBean().getFlgpresenteheaderout())  : "0");      
		bean.setFlgPresenteRifNormativi(result.getResultBean().getFlgpresentirifnormout()!=null    ? String.valueOf(result.getResultBean().getFlgpresentirifnormout()) : "0");
		
		return bean;
	}
	
	private DmpkAmmTraspIucontenutosezBean inizializzaInput(ContenutiAmmTraspBean bean)throws Exception {
		
		DmpkAmmTraspIucontenutosezBean input  = new DmpkAmmTraspIucontenutosezBean();
		
		input.setIdcontenutosezio(StringUtils.isNotBlank(bean.getIdContenuto()) ? new BigDecimal(bean.getIdContenuto()) : null);                               // Id. del contenuto
		input.setIdsezionein(StringUtils.isNotBlank(bean.getIdSezione()) ? new BigDecimal(bean.getIdSezione()) : null);                                        // Id. della sezione
		
		input.setTipocontenutoin(bean.getTipoContenuto());                                                                                                     // Tipo di contenuto
		input.setNroordineinsezin(bean.getNroOrdineInSez());                                                                                                   // Nro d'ordine nella sezione
		input.setTitoloin(bean.getTitoloContenuto());                                                                                                          // Titolo
		input.setTestohtmlsezin(bean.getTestoHtmlSezione());                                                                                                   // Html con i dati di dettaglio da mostrare nella lista contenuti della sezione.
		input.setTestohtmlindettaglioin(bean.getTestoHtmlDettaglio());                                                                                         // Html con i dati di dettaglio da mostrare dopo il titolo nella maschera di dettaglio del contenuto.		
		
		/*Tale parametro ha la logica invertita se Ã¨ true passare false e se false passare true*/
		input.setFlgtestihtmlugualiin((bean.getFlgTestiHtmlUguali() !=null && bean.getFlgTestiHtmlUguali()) ? new Integer(0) : new Integer(1));                // Se 1 indica che i due html precedenti sono uguali  				
		
		input.setFlgmostradatiaggin((bean.getFlgMostraDatiAggiornamento() !=null && bean.getFlgMostraDatiAggiornamento()) ? new Integer(1) : new Integer(0));  // Se 1 significa che per il contenuto vanno mostrati dati di ultimo aggiornamento  
		input.setFlgexportopendatain(new Boolean(String.valueOf(bean.getFlgExportOpenData())) ? new BigDecimal(1) : new BigDecimal(0));                        // Se 1 significa che per la sezione Ã¨ previsto export in open data, altrimenti no
		input.setFlgpaginadedicatain(new Boolean(String.valueOf(bean.getFlgPaginaDedicata())) ? new BigDecimal(1) : new BigDecimal(0));                        // Se 1 significa che per la sezione Ã¨ previsto export in open data, altrimenti no
		input.setIddocprimarioin(StringUtils.isNotBlank(bean.getIdDocFile()) ? new BigDecimal(bean.getIdDocFile()) : null);                                    // ID_DOC del documento primario.  		
		input.setNroverfileprimarioin(bean.getNroVersioneFile() != null ? new BigDecimal(bean.getNroVersioneFile()) : null);                                   // N.ro di versione del file del documento primario.
		input.setDtpubbldalin(bean.getDtInizioPubblicazione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtInizioPubblicazione()) : null);    // Data di inizio pubblicazione (nel formato del parametro FMT_STD_DATA)
		input.setDtpubblalin(bean.getDtFinePubblicazione() != null    ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtFinePubblicazione()) : null);      // Data di fine pubblicazione (nel formato del parametro FMT_STD_DATA)
		
		if(bean.getTipoContenuto().equalsIgnoreCase(TIPO_CONTENUTO_FILE_SEMPLICE) && StringUtils.isBlank(bean.getIdDocFile())) {
			FileStoreBean infoFileBean = getBeanInfoFile(bean.getUriFile(), bean.getDisplayFile());
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			String xmlDatiFile = lXmlUtilitySerializer.bindXml(infoFileBean);
			input.setDatifilesemplicedasalvarein(xmlDatiFile);
		} else if(bean.getTipoContenuto().equalsIgnoreCase(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO)){
			input.setIddocprimarioin(StringUtils.isNotBlank(bean.getIdDocFile()) ? new BigDecimal(bean.getIdDocFile()) : null);
		}
		
		// Salvo InfoStrutturaTab
		String xmlInfoStrutturaTabella = null;
		if (bean.getInfoStrutturaTabella() != null && bean.getInfoStrutturaTabella().size() > 0) {
			xmlInfoStrutturaTabella = getXmlInfoStrutturaTabella(bean);
		}
		input.setInfostrutturatabin(xmlInfoStrutturaTabella);
		
		if(bean.getFlgAbilAuotorizzRich() != null && "1".equals(bean.getFlgAbilAuotorizzRich())) {
			input.setStatorichpubblin(bean.getStatoAuotorizzRich());
			input.setMotivorigettoin(bean.getMotivoRigettoAuotorizzRich());
		} else {
			input.setStatorichpubblin(bean.getStatoRichPubbl());
			input.setMotivorigettoin(bean.getMotivoRigetto());
		}
				
		return input;
	}
	
	protected String getXmlInfoStrutturaTabella(ContenutiAmmTraspBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {		
		List<InfoStrutturaTabellaXmlBean> lList = new ArrayList<InfoStrutturaTabellaXmlBean>();
		for (InfoStrutturaTabellaBean lInfoStrutturaTabellaBean : bean.getInfoStrutturaTabella()) {
			InfoStrutturaTabellaXmlBean lInfoStrutturaTabellaXmlBean = new InfoStrutturaTabellaXmlBean();
			lInfoStrutturaTabellaXmlBean.setIntestazione(lInfoStrutturaTabellaBean.getIntestazione());                                                                                                           // 1: Intestazione colonna
			lInfoStrutturaTabellaXmlBean.setTipo(lInfoStrutturaTabellaBean.getTipo());                                                                                                                           // 2: Tipo di colonna:
			lInfoStrutturaTabellaXmlBean.setLarghezza(lInfoStrutturaTabellaBean.getLarghezza() != null  ? Integer.toString(lInfoStrutturaTabellaBean.getLarghezza()) : "0");                                     // 3: larghezza percentuale della colonna (da 1 a 100)	                        
			lInfoStrutturaTabellaXmlBean.setNrPosizColonnaOrdinamento(lInfoStrutturaTabellaBean.getNrPosizColonnaOrdinamento());                                                                                 // 4: Indica se la colonna Ã¨ quella o una di quelle per cui ordinare i dati
			lInfoStrutturaTabellaXmlBean.setVersoOrdinamento(lInfoStrutturaTabellaBean.getVersoOrdinamento());                                                                                                   // 5: Indica il verso di ordinamento sulla colonna
			lInfoStrutturaTabellaXmlBean.setFlgValoreObbligatorio(lInfoStrutturaTabellaBean.getFlgValoreObbligatorio() != null && lInfoStrutturaTabellaBean.getFlgValoreObbligatorio() ? "1" : "0");             // 6: (flag 1/0) Indica se campo obbligatorio (1) o meno (0 o NULL)
			lInfoStrutturaTabellaXmlBean.setFlgDettRiga(lInfoStrutturaTabellaBean.getFlgDettRiga() != null && lInfoStrutturaTabellaBean.getFlgDettRiga() ? "1" : "0");             								 // 7: (flag 1/0) Indica se campo obbligatorio (1) o meno (0 o NULL)
			lInfoStrutturaTabellaXmlBean.setValoriAmmessi(lInfoStrutturaTabellaBean.getValoriAmmessi());                                                                                                         // 8: Valori ammessi 
			lList.add(lInfoStrutturaTabellaXmlBean);
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlInfoStrutturaTabella = lXmlUtilitySerializer.bindXmlList(lList);
		return xmlInfoStrutturaTabella;
	}
	
	public ContenutiAmmTraspBean leggiDatiContTabella(ContenutiAmmTraspBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlFiltriTabella = lXmlUtilitySerializer.bindXmlList(bean.getListaFiltriContenutiTabella());
		
		// Inizializzo l'INPUT
		DmpkAmmTraspGetdaticonttabellaBean input = new DmpkAmmTraspGetdaticonttabellaBean();
		input.setIdcontenutosezin(StringUtils.isNotBlank(bean.getIdContenuto()) ? new BigDecimal(bean.getIdContenuto()) : null);          // Id. del contenuto
		input.setFlgrecordfuoripubblin(StringUtils.isNotBlank(bean.getFlgRecordFuoriPubbl()) ? new Integer(bean.getFlgRecordFuoriPubbl()) : null);
		input.setFiltriin(xmlFiltriTabella);
		
		// Eseguo il servizio		
		DmpkAmmTraspGetdaticonttabella lservice = new DmpkAmmTraspGetdaticonttabella();
		StoreResultBean<DmpkAmmTraspGetdaticonttabellaBean> result = lservice.execute(null, loginBean, input);
				
		// Leggo l'esito
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result.getDefaultMessage());
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		} 
				
		// Leggo la struttura della tabella da InfoStrutturaTabOut
		List<InfoStrutturaTabellaBean> lListInfoStrutturaTabella = new ArrayList<InfoStrutturaTabellaBean>();
		List<DettColonnaAttributoListaBean> lListDettColonnaAttributoLista = new ArrayList<DettColonnaAttributoListaBean>();
		HashMap<String, DocumentBean> mappaDocumenti = new HashMap<String, DocumentBean>();

		DettColonnaAttributoListaBean dettColonnaAttributoLista1 = new DettColonnaAttributoListaBean();
		DettColonnaAttributoListaBean dettColonnaAttributoLista2 = new DettColonnaAttributoListaBean();
		DettColonnaAttributoListaBean dettColonnaAttributoLista3 = new DettColonnaAttributoListaBean();
		
		if(StringUtils.isNotBlank(result.getResultBean().getInfostrutturatabout())) {
			StringReader sr = new StringReader(result.getResultBean().getInfostrutturatabout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				
				//  Aggiungo l'attributo data inizio pubblicazione e lo nascondo nel grid
				dettColonnaAttributoLista1.setNumero(lista.getRiga().size() + "");
				dettColonnaAttributoLista1.setRiga(0 + "");
				dettColonnaAttributoLista1.setNome("#DataPubblicazioneDa_hidden#");
				dettColonnaAttributoLista1.setLabel("Data inizio pubblicazione");
				dettColonnaAttributoLista1.setLarghezzaListGridField("0%");     
				dettColonnaAttributoLista1.setTipo("DATE");
				dettColonnaAttributoLista1.setModificabile("1");
				dettColonnaAttributoLista1.setNascondiSoloSuGrid("1");
				dettColonnaAttributoLista1.setFlgSkipImport("1");
				
				String defaultDecorPubblAmmTrasp = ParametriDBUtil.getParametroDB(getSession(), "DEFAULT_DECOR_PUBBL_AMM_TRASP");
				GregorianCalendar calInizioPubbl = new GregorianCalendar();
				calInizioPubbl.setTime(new Date());
				if(defaultDecorPubblAmmTrasp == null || defaultDecorPubblAmmTrasp.equals("") || defaultDecorPubblAmmTrasp.equalsIgnoreCase("oggi")) {
		    		// tengo la data di oggi;
		    	} else if(defaultDecorPubblAmmTrasp.equalsIgnoreCase("domani")) {
		    		// aggiungo 1 giorno
		    		calInizioPubbl.add(Calendar.DAY_OF_YEAR, 1);		    		
		    	} else {
		    		// aggiungo <GG_DELAY_INIZIO_PUBBL_ATTO> giorni
		    		String giorniDelayInizioPubblAtto = ParametriDBUtil.getParametroDB(getSession(), "GG_DELAY_INIZIO_PUBBL_ATTO");	    	
		    		calInizioPubbl.add(Calendar.DAY_OF_YEAR, Integer.parseInt(giorniDelayInizioPubblAtto != null ? giorniDelayInizioPubblAtto : "1"));
		    	}
				dettColonnaAttributoLista1.setValoreDefault(new SimpleDateFormat(FMT_STD_DATA).format(calInizioPubbl.getTime()));
				dettColonnaAttributoLista1.setObbligatorio("1");
				
				// Aggiungo l'attributo data fine pubblicazione e lo nascondo nel grid
				dettColonnaAttributoLista2.setNumero((lista.getRiga().size() + 1) + "");
				dettColonnaAttributoLista2.setRiga(1 + "");
				dettColonnaAttributoLista2.setNome("#DataPubblicazioneA_hidden#");
				dettColonnaAttributoLista2.setLabel("Data fine pubblicazione");         
				dettColonnaAttributoLista2.setLarghezzaListGridField("0%");            
				dettColonnaAttributoLista2.setTipo("DATE");
				dettColonnaAttributoLista2.setModificabile("1");
				dettColonnaAttributoLista2.setNascondiSoloSuGrid("1");
				dettColonnaAttributoLista2.setFlgSkipImport("1");
				
				// Aggiungo l'attributo nroRiga e lo nascondo nel grid
				dettColonnaAttributoLista3.setNumero((lista.getRiga().size() + 2) + "");
				dettColonnaAttributoLista3.setRiga(2 + "");
				dettColonnaAttributoLista3.setNome("#NroRiga_hidden#");
				dettColonnaAttributoLista3.setLabel("Nro riga");         
				dettColonnaAttributoLista3.setLarghezzaListGridField("0%");            
				dettColonnaAttributoLista3.setTipo("TEXT");
				dettColonnaAttributoLista3.setModificabile("1");
				dettColonnaAttributoLista3.setNascondiSoloSuGrid("1");
				dettColonnaAttributoLista3.setFlgSkipImport("1");
				
				lListDettColonnaAttributoLista.add(dettColonnaAttributoLista1);
				lListDettColonnaAttributoLista.add(dettColonnaAttributoLista2);
				lListDettColonnaAttributoLista.add(dettColonnaAttributoLista3);
				
				// Aggiungo gli altri attributi definiti dall'utente
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					InfoStrutturaTabellaBean infoStrutturaTabellaBean = new InfoStrutturaTabellaBean();
					infoStrutturaTabellaBean.setIntestazione(v.get(0));                                                        //  1: Intestazione colonna
					infoStrutturaTabellaBean.setTipo(v.get(1));                                                                //  2: Tipo di colonna
					infoStrutturaTabellaBean.setLarghezza(StringUtils.isNotBlank(v.get(2)) ? new Integer(v.get(2)) : null );   //  3: larghezza percentuale della colonna (da 1 a 100)
					infoStrutturaTabellaBean.setNrPosizColonnaOrdinamento(v.get(3));                                           //  4: Indica se la colonna Ã¨ quella o una di quelle per cui ordinare i dati
					infoStrutturaTabellaBean.setVersoOrdinamento(v.get(4));                                                    //  5: Indica il verso di ordinamento sulla colonna
					infoStrutturaTabellaBean.setFlgValoreObbligatorio(v.get(5) != null && "1".equals(v.get(5)));               //  6: (flag 1/0) Indica se campo obbligatorio (1) o meno (0)
					infoStrutturaTabellaBean.setFlgDettRiga(v.size() >6 && v.get(6) != null && "1".equals(v.get(6)));          //  7: (flag 1/0) Indica se campo obbligatorio (1) o meno (0)v
					infoStrutturaTabellaBean.setValoriAmmessi(v.get(7));                                                       //  8: Valori ammessi
					lListInfoStrutturaTabella.add(infoStrutturaTabellaBean);	
					DettColonnaAttributoListaBean dettColonnaAttributoLista = buildDettColonnaAttributoListaBeanFromVector(v, i, i + 3);
					lListDettColonnaAttributoLista.add(dettColonnaAttributoLista);				
				}
			}
		}
		bean.setInfoStrutturaTabella(lListInfoStrutturaTabella);
		bean.setListaDettColonnaAttributo(lListDettColonnaAttributoLista);
		
		// Leggo i dati della tabella da Valorirectabout
		if (StringUtils.isNotBlank(result.getResultBean().getValorirectabout())) {
			StringReader srValoriLista = new StringReader(result.getResultBean().getValorirectabout());
			Lista listaValoriLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srValoriLista);
			List<HashMap<String, Object>> valoriLista = recuperaListaValori(listaValoriLista, null, lListDettColonnaAttributoLista, mappaDocumenti);
			for (int i = 0; i < valoriLista.size(); i++) {
				Map<String, Object> valore = valoriLista.get(i);
				int nroRiga = Integer.valueOf((String) valore.get("#NroRiga_hidden#"));
				valoriLista.get(i).put("#NroRiga_hidden#", (nroRiga) + "");
			}
			bean.setValoriAttrLista(valoriLista);
		}
		
		// Tolgo la colonna #NroRiga_hidden#
		if (bean.getListaDettColonnaAttributo()!=null && bean.getListaDettColonnaAttributo().size()>0 && dettColonnaAttributoLista3!=null){
			lListDettColonnaAttributoLista = bean.getListaDettColonnaAttributo();
			lListDettColonnaAttributoLista.remove(dettColonnaAttributoLista3);		
			bean.setListaDettColonnaAttributo(lListDettColonnaAttributoLista);
		}
		
		Integer nrorectotaliout = result.getResultBean().getNrorectotaliout();
		bean.setNroRecTotali(nrorectotaliout);
		return bean;
	}
	private static DettColonnaAttributoListaBean buildDettColonnaAttributoListaBeanFromVector(Vector<String> v, int pos , int riga) {
		
		DettColonnaAttributoListaBean dettColonnaAttributoLista = new DettColonnaAttributoListaBean();
				
		dettColonnaAttributoLista.setNumero(pos + "");
		dettColonnaAttributoLista.setRiga(riga + "");
		dettColonnaAttributoLista.setNome(StringUtils.stripAccents(v.get(0)).replace(" ", "_").replace(".", "").replace("€", "E").replace("/", "_").replace("\\", "_").replace("'", "")); //  1: Intestazione colonna
		dettColonnaAttributoLista.setLabel(v.get(0));                                                                                          //  1: Intestazione colonna
		dettColonnaAttributoLista.setLarghezzaListGridField(v.get(2) + "%");                                                                   //  3: larghezza percentuale della colonna (da 1 a 100)
		dettColonnaAttributoLista.setNrPosizColonnaOrdinamento(v.get(3));                                                                      //  4: Indica se la colonna è quella o una di quelle per cui ordinare i dati
		dettColonnaAttributoLista.setVersoOrdinamento(v.get(4));                                                                               //  5: Indica il verso di ordinamento sulla colonna
		dettColonnaAttributoLista.setObbligatorio(v.get(5));                                                                                   //  6: (flag 1/0) Indica se campo obbligatorio (1) o meno (0)
		dettColonnaAttributoLista.setModificabile("1");
		dettColonnaAttributoLista.setNascondiSoloSuGrid(v.get(6) != null && "1".equals(v.get(6)) ? "1" : "0");
		dettColonnaAttributoLista.setValoriAmmessi(v.get(7) != null && !"".equals(v.get(7)) ? v.get(7) : null);                                //  8: Valori ammessi		
                                                  
		// Parte custom per i vari tipi
		String tipo = v.get(1);                                                                                                                //  2: Tipo di colonna
		if ("S".equalsIgnoreCase(tipo)){
			dettColonnaAttributoLista.setTipo("TEXT");
			dettColonnaAttributoLista.setLarghezza("450");
		} else if ("N".equalsIgnoreCase(tipo)) {
			dettColonnaAttributoLista.setTipo("INTEGER");
			dettColonnaAttributoLista.setLarghezza("300");
		} else if ("E".equalsIgnoreCase(tipo)) {
			dettColonnaAttributoLista.setTipo("EURO");
			dettColonnaAttributoLista.setLarghezza("300");
			dettColonnaAttributoLista.setNumCifreDecimali("2");
		} else if ("D".equalsIgnoreCase(tipo)) {
			dettColonnaAttributoLista.setTipo("DATE");
		} else if ("T".equalsIgnoreCase(tipo)) {
			dettColonnaAttributoLista.setTipo("DATETIME");
		} else if ("L".equalsIgnoreCase(tipo)) {
			setDettColonnaAttributoListaOfDocumentList(dettColonnaAttributoLista);
		} else if ("C".equalsIgnoreCase(tipo)) {
			dettColonnaAttributoLista.setTipo("CKEDITOR");
			dettColonnaAttributoLista.setAltezza("4");
			dettColonnaAttributoLista.setSottotipo("TRASPARENZA");
			dettColonnaAttributoLista.setLarghezza("400");
		} else {
			dettColonnaAttributoLista.setTipo("TEXT");
		}	
		
		return dettColonnaAttributoLista;
	}
	
	private static void setDettColonnaAttributoListaOfDocumentList(DettColonnaAttributoListaBean dettColonnaAttributoLista) {
		dettColonnaAttributoLista.setTipo("DOCUMENTLIST");
		dettColonnaAttributoLista.setHideNewButtonInDocumentList("1");
		dettColonnaAttributoLista.setShowImportaFileArchivioButtonInDocumentList("1");
		dettColonnaAttributoLista.setShowUploadFileButtonInDocumentList("1");		
		dettColonnaAttributoLista.setShowAltreOpInDocumentListItem("0");
		dettColonnaAttributoLista.setShowUploadItemInDocumentListItem("0");
		dettColonnaAttributoLista.setShowPreviewButtonInDocumentListItem("1");		
		dettColonnaAttributoLista.setShowPreviewEditButtonInDocumentListItem("0");		
		dettColonnaAttributoLista.setCanBeEditedByAppletInDocumentListItem("0");		
		dettColonnaAttributoLista.setShowEditButtonInDocumentListItem("0");					
		dettColonnaAttributoLista.setShowGeneraDaModelloButtonInDocumentListItem("0");		
		dettColonnaAttributoLista.setShowVisualizzaVersioniMenuItemInDocumentListItem("0");		
		dettColonnaAttributoLista.setShowDownloadMenuItemInDocumentListItem("1");	
		dettColonnaAttributoLista.setShowDownloadButtomOutsideAltreOpMenuInDocumentListItem("1");
		dettColonnaAttributoLista.setShowAcquisisciDaScannerMenuItemInDocumentListItem("0");		
		dettColonnaAttributoLista.setShowFirmaMenuItemInDocumentListItem("0");
		dettColonnaAttributoLista.setShowTimbraBarcodeMenuItemsInDocumentListItem("0");
		dettColonnaAttributoLista.setHideTimbraMenuItemsInDocumentListItem("0");
		dettColonnaAttributoLista.setShowEliminaMenuItemInDocumentListItem("0");
		dettColonnaAttributoLista.setShowFileFirmatoDigButtonInDocumentListItem("1");
		dettColonnaAttributoLista.setIsAttivaTimbraturaFilePostRegInDocumentListItem("0");
		dettColonnaAttributoLista.setShowFlgSostituisciVerPrecItemInDocumentListItem("0");
	}
	
	// TODO Allineare con il metodo  recuperaListaValori della classe AttributiDinamiciDatasource 
	private static List<HashMap<String, Object>> recuperaListaValori(Lista lLista, Lista lListaIdValori, List<DettColonnaAttributoListaBean> dettAttrLista, HashMap<String, DocumentBean> mappaDocumenti) throws Exception {
		List<HashMap<String, Object>> lList = new ArrayList<HashMap<String, Object>>();
		HashMap<Integer, String> mappa = new HashMap<Integer, String>();
		HashMap<Integer, String> mappaTipi = new HashMap<Integer, String>();
		// HashMap<Integer,String> mappaValoriCatasti = new HashMap<Integer, String>();
		for (int i = 0; i < dettAttrLista.size(); i++) {
			mappa.put(new Integer(dettAttrLista.get(i).getNumero()), dettAttrLista.get(i).getNome());
			mappaTipi.put(new Integer(dettAttrLista.get(i).getNumero()), dettAttrLista.get(i).getTipo());
			// if(StringUtils.isNotBlank(dettAttrLista.get(i).getPropertyCatasti()) && caratterizzazioneOst != null) {
			// String valoreCatasti = BeanUtilsBean2.getInstance().getProperty(caratterizzazioneOst, dettAttrLista.get(i).getPropertyCatasti());
			// if(StringUtils.isNotBlank(valoreCatasti)) {
			// mappaValoriCatasti.put(new Integer(dettAttrLista.get(i).getNumero()), valoreCatasti);
			// }
			// }
		}
		List<String> lListIdValori = new ArrayList<String>();
		if (lListaIdValori != null) {
			List<Riga> righe = lLista.getRiga();
			for (Riga lRiga : righe) {
				String idValoreLista = lRiga.getColonna().get(0).getContent();
				lListIdValori.add(idValoreLista);
			}
		}
		if (lLista != null) {
			List<Riga> righe = lLista.getRiga();
			for (Riga lRiga : righe) {
				HashMap<String, Object> lMap = new HashMap<String, Object>();
				int cont =  0;
				for (Colonna lColonna : lRiga.getColonna()) {
					// incremento contatore per recuperare l'idRiga che è l'ultima colonna della riga
					cont++;
					int nroColonna = lColonna.getNro().intValue();
					// Le colonne partono da 1, nelle store della tabella dinamica di amministrazione trasparente è stata usata questa convenzione
					// Però tutta la logica degli attributi dinamici parte da 0, quandi devo shiftare
					nroColonna -= 1;
					String tipoColonna = mappaTipi.get(nroColonna);
					if ("DOCUMENT".equals(tipoColonna)) {
						DocumentBean document = AttributiDinamiciDatasource.buildDocumentBean(lColonna.getContent());
						lMap.put(mappa.get(nroColonna), document.getIdDoc());
						mappaDocumenti.put(document.getIdDoc(), document);
					} else if ("EURO".equals(tipoColonna) || "DECIMAL".equals(tipoColonna)) {
						String content = lColonna.getContent();
						// if (StringUtils.isNotBlank(content)) {
						// content = content.replaceAll("\\.", "");
						// }
						lMap.put(mappa.get(nroColonna), content);
					} else if ("DOCUMENTLIST".equals(tipoColonna)) {
						String content = lColonna.getContent();
						List<DocumentListBean> listaDocumenti = new ArrayList<DocumentListBean>();
						if (StringUtils.isNotBlank(content)) {
							Lista listaDoc = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(content));
							if (listaDoc != null && listaDoc.getRiga() != null) {
								for (int i = 0; i < listaDoc.getRiga().size(); i++) {
									Vector<String> v = new XmlUtility().getValoriRiga(listaDoc.getRiga().get(i));
									DocumentListBean lDocumentListBean = new DocumentListBean();
									DocumentBean lDocumentBean = new DocumentBean();
									lDocumentBean.setUriFile(v.get(7));                                                        
									lDocumentBean.setNomeFile(v.get(8));
									lDocumentBean.setIdDoc(v.get(16));
									lDocumentBean.setNroVersione(v.get(17));
									lDocumentBean.setIdUd(v.get(18));
									MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
									infoFile.setFirmato("1".equalsIgnoreCase(v.get(9)) ? true : false);
									if (v.size() >= 20) {
										infoFile.setFirmaValida("1".equalsIgnoreCase(v.get(19)) ? true : false);
									}
									if ("1".equalsIgnoreCase(v.get(9)) && "1".equalsIgnoreCase(v.get(10))) {
										infoFile.setTipoFirma("CAdES");
									} else if ("1".equalsIgnoreCase(v.get(9))) {
										infoFile.setTipoFirma("PAdES");
									}
									infoFile.setConvertibile("1".equalsIgnoreCase(v.get(11)) ? true : false);
									infoFile.setCorrectFileName(v.get(8));
									infoFile.setMimetype(v.get(12));
									infoFile.setImpronta(v.get(13));
									infoFile.setAlgoritmo(v.get(14));
									infoFile.setEncoding(v.get(15));
									lDocumentBean.setInfoFile(infoFile);
									lDocumentListBean.setDocumento(lDocumentBean);
									listaDocumenti.add(lDocumentListBean);
								}
							}							
						}
						lMap.put(mappa.get(nroColonna), listaDocumenti);
					} else if (cont == lRiga.getColonna().size()) {
						// se sto iterando l'ultima colonna della riga allora questa corrisponde all'idRiga
						lMap.put("idRiga",lColonna.getContent());
					}
					// else if(mappaValoriCatasti.get(nroColonna) != null) {
					// lMap.put(mappa.get(nroColonna), mappaValoriCatasti.get(nroColonna));
					// }
					else {
						lMap.put(mappa.get(nroColonna), lColonna.getContent());
					}
				}
				try {
					lMap.put("idValoreLista", lListIdValori.get(lList.size()));
				} catch (Exception e) {
				}
				lList.add(lMap);
			}
		}
 		return lList;
	}
	
	public ContenutiAmmTraspBean aggiungiDatoContTabella(ContenutoRigaTabellaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkAmmTraspAddrecconttabellaBean input = new DmpkAmmTraspAddrecconttabellaBean();
		
		// Leggo dai contenuti la data di pubblicazione, la setto nel bean e la rimuovo dai contenuti 
		setAndRemoveDataPubblicazione(bean);
		
		String contenutoRiga = buildStringContentoRiga(bean);
		
		input.setValorirectoaddin(contenutoRiga);
		input.setIdcontenutosezin(StringUtils.isNotBlank(bean.getIdContenuto()) ? new BigDecimal(bean.getIdContenuto()) : null);
		input.setTspubbldalin(bean.getTsPubblDal() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getTsPubblDal()) : null);
		input.setTspubblalin(bean.getTsPubblAl() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getTsPubblAl()) : null);
		
		DmpkAmmTraspAddrecconttabella lservice = new DmpkAmmTraspAddrecconttabella();
		StoreResultBean<DmpkAmmTraspAddrecconttabellaBean> result = lservice.execute(null, loginBean, input);
		
		if (result.isInError()) {
			mLogger.error("Errore nell'inserimento della riga nella tabella: " + result.getDefaultMessage());
			mLogger.error("idContenuto:" + bean.getIdContenuto());
			mLogger.error("contenutoRiga:" + contenutoRiga);
			throw new StoreException("Errore nell'inserimento della riga nella tabella: " + result.getDefaultMessage());
		}
		
		return new ContenutiAmmTraspBean();
	}
	
	public ContenutiAmmTraspBean modificaDatoContTabella(ContenutoRigaTabellaBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkAmmTraspUpdrecconttabellaBean input = new DmpkAmmTraspUpdrecconttabellaBean();
		
		// Leggo dai contenuti la data di pubblicazione, la setto nel bean e la rimuovo dai contenuti 
		setAndRemoveDataPubblicazione(bean);
				
		String contenutoRiga = buildStringContentoRiga(bean);
		
		input.setValorirectoupdin(contenutoRiga);
		input.setIdcontenutosezin(StringUtils.isNotBlank(bean.getIdContenuto()) ? new BigDecimal(bean.getIdContenuto()) : null);
		input.setIdrigain((String) bean.getContenuto().get("idRiga"));
		input.setTspubbldalin(bean.getTsPubblDal() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getTsPubblDal()) : null);
		input.setTspubblalin(bean.getTsPubblAl() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getTsPubblAl()) : null);
		
		DmpkAmmTraspUpdrecconttabella lservice = new DmpkAmmTraspUpdrecconttabella();
		StoreResultBean<DmpkAmmTraspUpdrecconttabellaBean> result = lservice.execute(null, loginBean, input);
		if (result.isInError()) {
			mLogger.error("Errore nell'aggiornamento del contenuto della tabella: " + result.getDefaultMessage());
			mLogger.error("Nrorigain:" + bean.getNumeroRiga());
			mLogger.error("idContenuto:" + bean.getIdContenuto());
			mLogger.error("contenutoRiga:" + contenutoRiga);
		
			throw new StoreException("Errore nell'aggiornamento della riga della tabella: " + result.getDefaultMessage());
		}
		
		return new ContenutiAmmTraspBean();
	}
	
	public ContenutiAmmTraspBean eliminaDatoContTabella(ContenutoRigaTabellaBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkAmmTraspDrecconttabellaBean input = new DmpkAmmTraspDrecconttabellaBean();
		
		Lista listaValoriRiga = new Lista();
		Riga riga = new Riga();
		Colonna col = new Colonna();
		col.setNro(new BigInteger("1"));
		col.setContent(bean.getIdRiga());
		riga.getColonna().add(col);
		listaValoriRiga.getRiga().add(riga);

		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(listaValoriRiga, sw);
		
		input.setIdcontenutosezin(StringUtils.isNotBlank(bean.getIdContenuto()) ? new BigDecimal(bean.getIdContenuto()) : null);
		input.setRectodeletein(sw.toString());
		input.setIduserlavoroin(loginBean.getIdUser());
		
		DmpkAmmTraspDrecconttabella lservice = new DmpkAmmTraspDrecconttabella();
		StoreResultBean<DmpkAmmTraspDrecconttabellaBean> result = lservice.execute(null, loginBean, input);
		
		if (result.isInError()) {
			mLogger.error("Errore nell'eliminazione della riga dalla tabella: " + result.getDefaultMessage());
			mLogger.error("Nrorigain:" + bean.getNumeroRiga());
			mLogger.error("idContenuto:" + bean.getIdContenuto());
			throw new StoreException("Errore nell'eliminazione della riga dalla tabella " + result.getDefaultMessage());
		}
		
		return new ContenutiAmmTraspBean();
	}
	
	private void setAndRemoveDataPubblicazione(ContenutoRigaTabellaBean bean) throws Exception {
		// Tolgo gli attributi data inizio pubblicazione e fine pubblicazione
		if (bean.getContenuto()!=null && !bean.getContenuto().isEmpty() && bean.getContenuto().size() > 0 ){
			if (bean.getContenuto().containsKey("#DataPubblicazioneDa_hidden#")){
				Object objectDataPubblicazioneDa_hidden = bean.getContenuto().get("#DataPubblicazioneDa_hidden#");
				Date dateDataPubblicazioneDa_hidden = getDateValueFromObject(objectDataPubblicazioneDa_hidden);
				if (dateDataPubblicazioneDa_hidden != null) {
					bean.setTsPubblDal(dateDataPubblicazioneDa_hidden);					
				}
				bean.getContenuto().remove("#DataPubblicazioneDa_hidden#");
			}
			if (bean.getContenuto().containsKey("#DataPubblicazioneA_hidden#")){
				Object objectDataPubblicazioneA_hidden = bean.getContenuto().get("#DataPubblicazioneA_hidden#");
				Date dateDataPubblicazioneA_hidden = getDateValueFromObject(objectDataPubblicazioneA_hidden);
				if (dateDataPubblicazioneA_hidden != null) {
					bean.setTsPubblAl(dateDataPubblicazioneA_hidden);					
				}
				bean.getContenuto().remove("#DataPubblicazioneA_hidden#");
			}
		}
	}
	
	private Riga getRiga(HashMap<String, Object> contenuto,  List<Integer> colonne, Map<Integer, DettColonnaAttributoListaBean> mappaColonne, String uoLavoro) throws JAXBException , Exception {
		
		Riga riga = new Riga();
		
		for (int i = 0; i < colonne.size(); i++) {
			Integer nroColonna = colonne.get(i);
			DettColonnaAttributoListaBean colonna =  mappaColonne.get(nroColonna);
			String nomeColonna = colonna.getNome();
			Object valoreColonna = contenuto.get(nomeColonna);
			String strValoreColonna= "";
			Colonna col = new Colonna();
			// Le colonne partono da 1, nelle store della tabella dinamica di amministrazione trasparente è stata usata questa convenzione
			// Però tutta la logica degli attributi dinamici parte da 0, quandi devo shiftare
			col.setNro(new BigInteger((nroColonna.intValue() + 1) + ""));
			if ("DATE".equalsIgnoreCase(colonna.getTipo())) {
				Date data = getDateValueFromObject(valoreColonna);
				if (data != null) {
					strValoreColonna = new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).format(data);
				}
			} else if ("DATETIME".equalsIgnoreCase(colonna.getTipo())) {
				Date data = getDateValueFromObject(valoreColonna);
				if (data != null) {
					strValoreColonna = new SimpleDateFormat(AbstractDataSource.FMT_STD_TIMESTAMP).format(data);
				}
			} else if ("DECIMAL".equalsIgnoreCase(colonna.getTipo())) {
				if (valoreColonna != null) {
					strValoreColonna = ((String)valoreColonna).replace(".", "");
				}
			}  else if ("CKEDITOR".equalsIgnoreCase(colonna.getTipo())) {
				if (valoreColonna != null) {
					strValoreColonna = ((String)valoreColonna);
				}
			} else if ("DOCUMENTLIST".equalsIgnoreCase(colonna.getTipo())) {
				if (valoreColonna != null) {
					if (valoreColonna instanceof ArrayList) {
						ArrayList elencoDocumenti = (ArrayList) valoreColonna;
						Lista listaDocumenti = new Lista();
						for (Object documento : elencoDocumenti) {
							if (documento instanceof Map) {
								Map attributiDocumento = (Map) documento;
																
								String idDoc       = (attributiDocumento.get("idDoc")       != null ? attributiDocumento.get("idDoc").toString()       : "");
								String nroVersione = (attributiDocumento.get("nroVersione") != null ? attributiDocumento.get("nroVersione").toString() : "");
								String uriFile = (attributiDocumento.get("uriFile") != null ? attributiDocumento.get("uriFile").toString() : "");
								
								// Se l'iddoc non e' presente allora vuol dire che il file e' stato uplodato e devo protocollarlo 
								if (idDoc.equalsIgnoreCase("") && !uriFile.equalsIgnoreCase("")){
									
									String displayFileName = (attributiDocumento.get("nomeFile") != null ? attributiDocumento.get("nomeFile").toString() : "");
									
									ArchivioBean archivioBean = new ArchivioBean();
									
									// Setto la uo protocollante
									archivioBean.setUoProtocollante(uoLavoro);
																        
									// eseguo la protocollazione
									ProtocollazioneBean output = new ProtocollazioneBean();
									output = protocollaFile(uriFile, displayFileName, archivioBean);
									
									// Prendo l'iddoc 
									idDoc = (output.getIdDocPrimario() != null ? output.getIdDocPrimario().toString() : "");
									nroVersione = "1";									
								}
								
								if (!idDoc.equalsIgnoreCase("")){
									Riga rigaDocumento = new Riga();
									Colonna colonna1 = new Colonna();
									colonna1.setNro(new BigInteger("1"));
									colonna1.setContent(idDoc);
									rigaDocumento.getColonna().add(colonna1);
									Colonna colonna2 = new Colonna();
									colonna2.setNro(new BigInteger("2"));
									colonna2.setContent(nroVersione);
									rigaDocumento.getColonna().add(colonna2);
									listaDocumenti.getRiga().add(rigaDocumento);
								}
							}
						}
						StringWriter sw = new StringWriter();
						SingletonJAXBContext.getInstance().createMarshaller().marshal(listaDocumenti, sw);
						strValoreColonna = sw.toString();
					}
					if (valoreColonna instanceof String) {
						strValoreColonna = ((String)valoreColonna);
					}
				}
			} else {
				if (valoreColonna instanceof String) {
					strValoreColonna = ((String)valoreColonna);
				}
			}
			col.setContent(strValoreColonna);
			riga.getColonna().add(col);
		}
		
		return riga;
	}
	
	private String buildStringContentoRiga(ContenutoRigaTabellaBean bean) throws JAXBException, Exception  {
				
		List<DettColonnaAttributoListaBean> datiDettLista = bean.getListDettColonnaAttributoLista();
		Map<Integer, DettColonnaAttributoListaBean> mappaColonne = new HashMap<Integer, DettColonnaAttributoListaBean>(datiDettLista.size());
		for (int i = 0; i < datiDettLista.size(); i++) {
			DettColonnaAttributoListaBean dett = datiDettLista.get(i);
			
			boolean aggiungi = true;
			// Prendo solo gli attributi che non sono nascosti nel grid (es.#DataPubblicazioneDa_hidden#, #DataPubblicazioneA_hidden#)
			if(dett.getFlgSkipImport() !=null && !dett.getFlgSkipImport().equalsIgnoreCase("")){
				if(dett.getFlgSkipImport().equalsIgnoreCase("1")){
					aggiungi = false;
				}
			}
			if(aggiungi){
				mappaColonne.put(new Integer(dett.getNumero()), dett);
			}
		}
		List<Integer> colonne = new ArrayList<Integer>(mappaColonne.keySet());
		Collections.sort(colonne);
		
		Lista listaValoriRiga = new Lista();
		
		String uoLavoro = bean.getUoLavoro();
		
		// Creo 1 riga
		if (bean.getContenutoList().size() == 0){
			Riga riga = new Riga();
			riga = getRiga(bean.getContenuto(), colonne, mappaColonne , uoLavoro);
			listaValoriRiga.getRiga().add(riga);
		}
		// Creo n righe
		else{			
			// Leggo la lista dei valori
			for (HashMap<String, Object> contenuto : bean.getContenutoList()) {
				Riga riga = new Riga();
				riga = getRiga(contenuto, colonne, mappaColonne, uoLavoro);
				listaValoriRiga.getRiga().add(riga);
			}
			
		}
		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(listaValoriRiga, sw);
		
		return sw.toString();
	}
		
	private static Date getDateValueFromObject(Object value) {
		if (value != null) {
			if (value instanceof Date) {
				return (Date) value;
			} else {				
				String valueStr = String.valueOf(value);
				if (StringUtils.isNotBlank(valueStr)) {
					try {
						return new SimpleDateFormat(AbstractDataSource.DATETIME_ATTR_FORMAT).parse(valueStr);
					}catch (Exception e1) {
						try {
							return new SimpleDateFormat(AbstractDataSource.DATE_ATTR_FORMAT).parse(valueStr);
						}catch (Exception e2) {
							try {
								return new SimpleDateFormat(AbstractDataSource.FMT_STD_TIMESTAMP_WITH_SEC).parse(valueStr);
							}catch (Exception e3) {
								try {
									return new SimpleDateFormat(AbstractDataSource.FMT_STD_TIMESTAMP).parse(valueStr);
								}catch (Exception e4) {
									try {
										return new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).parse(valueStr);
									}catch (Exception e5) {
									}
								}
							}						
						}
					}
				}
			}
		} 
		return null;
	}
	
	public ExportBean generaReport(ReportTrasparenzaAmministrativaBean pInBean)  throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkAmmTraspGetdatireportBean input = new DmpkAmmTraspGetdatireportBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		// Tipo di report
		input.setTiporeportin(pInBean.getTipoReport());

		// Xml filtri
		ReportTrasparenzaAmministrativaFiltriXmlBean lFiltriXmlBean = new ReportTrasparenzaAmministrativaFiltriXmlBean();
				
		// Periodo da/a		
		lFiltriXmlBean.setDataDa(pInBean.getDa()!=null ? lSimpleDateFormat.format(pInBean.getDa()) : null);		
		lFiltriXmlBean.setDataA(pInBean.getA()!=null   ? lSimpleDateFormat.format(pInBean.getA())  : null);
		
		// Data riferimento
		lFiltriXmlBean.setDataRif(pInBean.getDataRif()!=null ? lSimpleDateFormat.format(pInBean.getDataRif()) : null);
		
		// Sezione
		lFiltriXmlBean.setIdSezione(pInBean.getIdSezione());

		
		String filtriXml = lXmlUtilitySerializer.bindXml(lFiltriXmlBean);
		input.setParametrireportin(filtriXml);
		
		// Eseguo il servizio
		DmpkAmmTraspGetdatireport servizio =  new DmpkAmmTraspGetdatireport();
		StoreResultBean<DmpkAmmTraspGetdatireportBean> lservizio = servizio.execute(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), input);
		
		DmpkAmmTraspGetdatireportBean lresultBean = new DmpkAmmTraspGetdatireportBean();
		
		// Leggo esito
		if (lservizio.isInError()){
			throw new Exception(lservizio.getDefaultMessage());
		} else {
			lresultBean = lservizio.getResultBean();
		}
		
		/*
		int nrRecordOut = (lresultBean.getNrorecordout()!=null ? lresultBean.getNrorecordout().intValue() : 0);
		
		if (nrRecordOut == 0){
			throw new Exception("Il report non contiene dati.");
		}
		*/
		// leggo la riga con le intestazioni delle colonne
		String[] headers = getHeaders(lresultBean.getIntestazionireportout());
		
		if (headers == null || headers.length == 0 ){
			throw new Exception("L''intestazione del report è mancante.");
		}
		
		// setto i nomi delle colonne
		String[] fields = getFields(headers);
		
		// Leggo le righe xml
		Map[] righeXls = getRigheXml(lresultBean.getValorireportout(), headers, fields);
		
		if (righeXls == null || righeXls.length == 0){
			throw new Exception("Il report non contiene dati.");
		}
		
		// Creo l'xls
		ExportBean result = creaXls(headers, fields, righeXls);
		
		return result;
	}
	
	private ExportBean creaXls(String[] headers, String[] fields, Map[]  records)  throws Exception {
	
		ExportBean lExportBeanIn = new ExportBean();
		lExportBeanIn.setNomeEntita("contenuti_amm_trasparente_crea_xls");
		lExportBeanIn.setFormatoExport("xls");
		lExportBeanIn.setCriteria(null);
		lExportBeanIn.setFields(fields);
		lExportBeanIn.setHeaders(headers);
		lExportBeanIn.setOverflow(false);
		lExportBeanIn.setRecords(records);
		ExportBean result = this.export(lExportBeanIn);
		return result;
	}
	
	private Map[] getRigheXml(String xml, String[] headers, String[] fields) throws Exception {
		
		if (StringUtils.isBlank(xml)) {
			return null;
		}
		
		// Calcolo il size
		int size = 0;
    	StringReader srValoriLista = new StringReader(xml);
    	Lista listaValoriLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srValoriLista);	
    	if(listaValoriLista != null && listaValoriLista.getRiga().size()>0) {
    		for (Riga lRiga : listaValoriLista.getRiga()){
    			size++;
    		}
    	}

    	if (size == 0){
			return null;
		}
		
		// Estraggo le righe
		Map[] result = new Map[size];
		
		int i = 0;
		// Ciclo le righe 
		for (Riga lRiga : listaValoriLista.getRiga()){
			Map<String,String> record = new HashMap<String,String>();
			// Ciclo le colonne
			int j = 0;
			for (Colonna lColonna : lRiga.getColonna()){
				// salvo il nome della cella e il valore
				int nroColonna = lColonna.getNro().intValue() - 1;
				String nomeColonna = fields[nroColonna];
				record.put(nomeColonna, lColonna.getContent());
				j++;
			}
			// Aggiungo la riga alla lista
			result[i]=record;  
			i++;
		}
		return result;
	}
	
	private String[]  getHeaders(String xml) throws Exception {
		
		if (StringUtils.isBlank(xml)) {
			return null;
		}
		
		// Calcolo il size
		int size = 0;
    	StringReader srValoriLista = new StringReader(xml);
    	Lista listaValoriLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srValoriLista);	
    	if(listaValoriLista != null && listaValoriLista.getRiga().size()>0) {
    		Riga lRiga  = listaValoriLista.getRiga().get(0);
    		for (Colonna lColonna : lRiga.getColonna()){
    			size++;
    		}
    	}
       
		if (size == 0){
			return null;
		}
		
		// Estraggo le colonne
		String[] result = new String[size];
		
		Riga lRiga  = listaValoriLista.getRiga().get(0);
		int i=0;
		for (Colonna lColonna : lRiga.getColonna()){
			String content = lColonna.getContent();
			result[i] = content;
			i++;
		}
		return result;
	}
	
	private String[] getFields(String[]  headers) throws Exception {
		
		// Calcolo il size
		
		if (headers==null)
			return null;
		
		int size = headers.length;
		if (size == 0){
			return null;
		}
		
		// Estraggo le colonne
		String[] result = new String[size];
		for (int i = 0; i < size; i++) {
			result[i] = "colonna_" + (i + 1);
		}
		return result;
	}
	
	// Sposta il contenuto da una sezione ad un'altra
	public ContenutiAmmTraspBean spostaContenutoSez(ContenutiAmmTraspBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT		
		DmpkAmmTraspSpostacontenutosezBean input = new DmpkAmmTraspSpostacontenutosezBean();
		input.setIdsezionenewin(StringUtils.isNotBlank(bean.getIdSezione()) ? new BigDecimal(bean.getIdSezione()) : null);                                     // Id sezione ricevente
		input.setIdcontenutosezin(StringUtils.isNotBlank(bean.getIdContenuto()) ? new BigDecimal(bean.getIdContenuto()) : null);                               // Id contenuto di provenienza
		
		// Eseguo il servizio
		StoreResultBean<DmpkAmmTraspSpostacontenutosezBean> result;
		try {
			DmpkAmmTraspSpostacontenutosez lservice = new DmpkAmmTraspSpostacontenutosez();
			result = lservice.execute(getLocale(), loginBean, input);
			
			// Leggo l'esito
			if (StringUtils.isNotBlank(result.getDefaultMessage())) {
				if (result.isInError()) {
					throw new StoreException(result.getDefaultMessage());
				} else {
					addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		} 		
		return bean;	
	}
	
	
	private ProtocollazioneBean protocollaFile(String uriFileIn , String displayFileNameIn, ArchivioBean archivioBeanIn) throws Exception {
				
		if (StringUtils.isBlank(uriFileIn)){
			throw new Exception("L'uri del file non e' indicato.Impossibile effettuare la protocollazione del file.");
		}
		
		ProtocollazioneBean inputBean  = new ProtocollazioneBean();
		ProtocollazioneBean outputBean = new ProtocollazioneBean();
		CreaModDocumentoInBean lCreaDocumentoInBean = new CreaModDocumentoInBean();
		
		// Login
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		
		// Setto la UO protocollante
		String uoProcollante = archivioBeanIn.getUoProtocollante();
		inputBean.setUoProtocollante(uoProcollante);
        String idUoProtocollante = (StringUtils.isNotBlank(uoProcollante) ? uoProcollante.substring(2) : null);
        
        // Setto il tipo di protocollo
		lCreaDocumentoInBean.setFlgTipoProv(TipoProvenienza.INTERNA);
		
		// Setto la tipologia di numerazione (SIGLA/CATEGORIA/ANNO)
		inputBean.setIsRepertorio(true);
		TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
		lTipoNumerazioneBean.setSigla("TRASP");
		int annoCorrente = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));		
		lTipoNumerazioneBean.setAnno(String.valueOf(annoCorrente));
		lTipoNumerazioneBean.setCategoria("R");
		lTipoNumerazioneBean.setIdUo(idUoProtocollante);
		List<TipoNumerazioneBean> listaTipiNumerazioni = new ArrayList<TipoNumerazioneBean>();
		listaTipiNumerazioni.add(lTipoNumerazioneBean);		
		lCreaDocumentoInBean.setTipoNumerazioni(listaTipiNumerazioni);
				
        // Setto il mittente interno
     	List<MittentiDocumentoBean> lListMittenti = new ArrayList<MittentiDocumentoBean>();
     	MittentiDocumentoBean lMittentiBean = new MittentiDocumentoBean();
     	lMittentiBean.setCodProvenienza("[AUR.UO]" + idUoProtocollante);
     	lListMittenti.add(lMittentiBean);
     	lCreaDocumentoInBean.setMittenti(lListMittenti);
     	     		
		// Setto il destinatario interno
     	List<DestinatariBean> lListDestinatari = new ArrayList<DestinatariBean>();
     	DestinatariBean lDestinatariBean = new DestinatariBean();
     	lDestinatariBean.setCodProvenienza("[AUR.UO]" + idUoProtocollante);
     	lListDestinatari.add(lDestinatariBean);
     	lDestinatariBean.setAssegna(Flag.NULL);
     	lDestinatariBean.setPerConoscenza(Flag.NULL);
     	lCreaDocumentoInBean.setDestinatari(lListDestinatari);
     	     	
		// Setto l'oggetto
     	lCreaDocumentoInBean.setOggetto(archivioBeanIn.getNome());
     	
		// Salvo il file PRIMARIO
		inputBean.setUriFilePrimario(uriFileIn);
		inputBean.setNomeFilePrimario(displayFileNameIn);
		inputBean.setRemoteUriFilePrimario(true);

		FilePrimarioBean lFilePrimarioBean = retrieveFilePrimario(inputBean, lAurigaLoginBean);
		if (lFilePrimarioBean != null) {
			if(lFilePrimarioBean.getFile() != null) {				
				lFilePrimarioBean.setFlgSostituisciVerPrec(inputBean.getFlgSostituisciVerPrec());
				MimeTypeFirmaBean lMimeTypeFirmaBean = inputBean.getInfoFile();
				if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
					File lFile = StorageImplementation.getStorage().extractFile(inputBean.getUriFilePrimario());
					lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), inputBean.getNomeFilePrimario(), false, null);
					if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
						throw new Exception("Si è verificato un errore durante il controllo del file primario " + inputBean.getNomeFilePrimario());
					}
				}

				FileInfoBean lFileInfoBean = new FileInfoBean();
				lFileInfoBean.setTipo(TipoFile.PRIMARIO);
				GenericFile lGenericFile = new GenericFile();
				setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
				lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
				lGenericFile.setDisplayFilename(inputBean.getNomeFilePrimario());
				lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
				lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
				lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
				lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
				if (lMimeTypeFirmaBean.isDaScansione()) {
					lGenericFile.setDaScansione(Flag.SETTED);
					lGenericFile.setDataScansione(new Date());
					lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
				}
				if (StringUtils.isNotBlank(inputBean.getIdUDScansione())) {
					lGenericFile.setIdUdScansioneProv(inputBean.getIdUDScansione());
				}
				lFileInfoBean.setAllegatoRiferimento(lGenericFile);
				lFilePrimarioBean.setIdDocumento(inputBean.getIdDocPrimario());
				lFilePrimarioBean.setIsNewOrChanged(inputBean.getIdDocPrimario() == null || (inputBean.getIsDocPrimarioChanged() != null && inputBean.getIsDocPrimarioChanged()));
				lFilePrimarioBean.setInfo(lFileInfoBean);				
			}			
		}
		
		AllegatiBean lAllegatiBean = new AllegatiBean();
		
		try {
			
			GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
			CreaModDocumentoOutBean lCreaDocumentoOutBean = null;
			lCreaDocumentoOutBean = lGestioneDocumenti.creadocumento(getLocale(), lAurigaLoginBean, lCreaDocumentoInBean, lFilePrimarioBean, lAllegatiBean);
			
			if (lCreaDocumentoOutBean.getDefaultMessage() != null) {
				throw new StoreException(lCreaDocumentoOutBean);
			}

			// Leggo i dati della registrazione
			outputBean.setIdDocPrimario(lCreaDocumentoOutBean.getIdDoc());
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		return outputBean;
	}
	
	private FilePrimarioBean retrieveFilePrimario(ProtocollazioneBean bean, AurigaLoginBean lAurigaLoginBean) throws StorageException {

		FilePrimarioBean filePrimarioBean = new FilePrimarioBean();
		if (StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())) {
			if (bean.getRemoteUriFilePrimario()) {
				// Il file è esterno
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(bean.getUriFilePrimario());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
				filePrimarioBean.setFile(out.getExtracted());
			} else {
				// File locale
				filePrimarioBean.setFile(StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario()));
			}
		}
		
		if(filePrimarioBean.getFile() != null) {
			return filePrimarioBean;
		}
		return null;
	}
}