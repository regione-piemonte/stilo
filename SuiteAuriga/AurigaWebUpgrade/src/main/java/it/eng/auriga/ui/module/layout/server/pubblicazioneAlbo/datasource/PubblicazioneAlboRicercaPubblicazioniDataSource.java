/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.auriga.compiler.ModelliUtil;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdfolderBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailPreparaemailnotassinvioccBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocExtractvermodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;

import it.eng.auriga.ui.module.layout.server.archivio.datasource.CriteriPersonalizzatiUtil;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.CriteriPersonalizzatiUtil.TipoFiltro;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;

import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioXmlBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.AzioneSuccessivaBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriPersonalizzati;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.DestInvioNotifica;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.MezziTrasmissioneFilterBean;

import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.OperazioneMassivaArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Registrazione;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Stato;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.StatoRichAnnullamentoReg;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.SupportiOrigDoc;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.UoApertura;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.UoProponente;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.UoPubblicazione;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.UoRegistrazione;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Utente;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.common.TipoIdBean;
import it.eng.auriga.ui.module.layout.server.firmamultipla.bean.FirmaMassivaFilesBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.StampaFileBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.ProtocolloDataSource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AttestatoConformitaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatarioProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DownloadDocsZipBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FileScaricoZipBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.pubblicazioneAlbo.datasource.bean.PubblicazioneAlboRicercaPubblicazioniBean;
import it.eng.auriga.ui.module.layout.server.pubblicazioneAlbo.datasource.bean.PubblicazioneAlboRicercaPubblicazioniXmlBean;
import it.eng.auriga.ui.module.layout.server.pubblicazioneAlbo.datasource.bean.PubblicazioneAlboRicercaPubblicazioniXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.client.AurigaMailService;
import it.eng.client.AurigaService;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkCoreUpdfolder;
import it.eng.client.DmpkIntMgoEmailPreparaemailnotassinviocc;
import it.eng.client.DmpkModelliDocExtractvermodello;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.GestioneDocumenti;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.AssegnatariBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;

import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.TipoAssegnatario;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.document.function.bean.XmlFascicoloIn;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.FirmaUtility;
import it.eng.utility.MessageUtil;
import it.eng.utility.XmlUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractDataSourceUtilities;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "PubblicazioneAlboRicercaPubblicazioniDataSource")
public class PubblicazioneAlboRicercaPubblicazioniDataSource extends AurigaAbstractFetchDatasource<PubblicazioneAlboRicercaPubblicazioniBean> {

	private static Logger mLogger = Logger.getLogger(PubblicazioneAlboRicercaPubblicazioniDataSource.class);
	
	/**
	 * CODICI PER GESTIRE L'ESITO DI ELABORAZIONE DEI FILE DA SCARICARE COME ZIP
	 * */
	public static int OK_CON_WARNING_TIMBRO = 3;
	public static int OK_CON_WARNING_SBUSTA= 4;

	@Override
	public PaginatorBean<PubblicazioneAlboRicercaPubblicazioniBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		List<PubblicazioneAlboRicercaPubblicazioniBean> data = new ArrayList<PubblicazioneAlboRicercaPubblicazioniBean>();

		boolean overflow = false;

		// creo il bean per la ricerca, ma inizializzo anche le variabili che mi servono per determinare se effettivamente posso eseguire il recupero dei dati
		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(criteria, loginBean);

		if (StringUtils.isNotBlank(lFindRepositoryObjectBean.getFiltroFullText())
				&& (lFindRepositoryObjectBean.getCheckAttributes() == null || lFindRepositoryObjectBean.getCheckAttributes().length == 0)) {
			addMessage("Specificare almeno un attributo su cui effettuare la ricerca full-text", "", MessageType.ERROR);
		} else {

			List<Object> resFinder = null;
			try {
				resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
			String xmlResultSetOut = (String) resFinder.get(0);
			String numTotRecOut = (String) resFinder.get(1);
			String numRecInPagOut = (String) resFinder.get(2);
			String xmlPercorsiOut = null;
			String dettagliCercaInFolderOut = null;
			String errorMessageOut = null;
			if (resFinder.size() > 3) {
				xmlPercorsiOut = (String) resFinder.get(3);
			}
			if (resFinder.size() > 4) {
				dettagliCercaInFolderOut = (String) resFinder.get(4);
			}
			if (resFinder.size() > 5) {
				errorMessageOut = (String) resFinder.get(5);
			}

			overflow = manageOverflow(errorMessageOut);

			// Conversione ListaRisultati ==> EngResultSet
			if (xmlResultSetOut != null) {
				data = XmlListaUtility.recuperaLista(xmlResultSetOut, PubblicazioneAlboRicercaPubblicazioniBean.class, new PubblicazioneAlboRicercaPubblicazioniXmlBeanDeserializationHelper(createRemapConditionsMap()));
			}
		}

		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);

		PaginatorBean<PubblicazioneAlboRicercaPubblicazioniBean> lPaginatorBean = new PaginatorBean<PubblicazioneAlboRicercaPubblicazioniBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);

		return lPaginatorBean;
	}

	private Map<String, String> createRemapConditionsMap() {

		Map<String, String> retValue = new LinkedHashMap<String, String>();

		String idNode = getExtraparams().get("idNode");
		retValue.put("idNode", idNode);

		return retValue;
	}

	@Override
	public PubblicazioneAlboRicercaPubblicazioniBean get(PubblicazioneAlboRicercaPubblicazioniBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		
		PubblicazioneAlboRicercaPubblicazioniBean result = null;


		return result;
	}

	protected void recuperaDocumento(List<AllegatoProtocolloBean> listaDocumenti, AllegatiOutBean documento) throws Exception {
		recuperaDocumento(listaDocumenti, documento, null);
	}
	
	protected void recuperaDocumento(List<AllegatoProtocolloBean> listaDocumenti, AllegatiOutBean documento, String dictionaryEntrySezione) throws Exception {
		AllegatoProtocolloBean lDocumentoBean = new AllegatoProtocolloBean();
		BigDecimal idDocAllegato = StringUtils.isNotBlank(documento.getIdDoc()) ? new BigDecimal(documento.getIdDoc()) : null;
		lDocumentoBean.setIdUdAppartenenza(documento.getIdUd());
		lDocumentoBean.setIdDocAllegato(idDocAllegato);
		lDocumentoBean.setDescrizioneFileAllegato(documento.getDescrizioneOggetto());
		lDocumentoBean.setIdTipoFileAllegato(documento.getIdDocType());
		lDocumentoBean.setDescTipoFileAllegato(documento.getNomeDocType());
		lDocumentoBean.setListaTipiFileAllegato(documento.getIdDocType());
		if(StringUtils.isNotBlank(dictionaryEntrySezione)) {
			lDocumentoBean.setDictionaryEntrySezione(dictionaryEntrySezione);
		}
		lDocumentoBean.setNomeFileAllegato(documento.getDisplayFileName());
		lDocumentoBean.setFlgParteDispositivo(documento.getFlgParteDispositivo() == Flag.SETTED);
		lDocumentoBean.setFlgParteDispositivoSalvato(documento.getFlgParteDispositivo() == Flag.SETTED);
		lDocumentoBean.setIdTask(documento.getIdTask());
		lDocumentoBean.setFlgNoPubblAllegato(documento.getFlgNoPubbl() == Flag.SETTED); 
		lDocumentoBean.setIsPubblicato(documento.getFlgPubblicato() != null && "1".equals(documento.getFlgPubblicato()) ? true : false);
		lDocumentoBean.setFlgOriginaleCartaceo(documento.getFlgOriginaleCartaceo() == Flag.SETTED);
		lDocumentoBean.setFlgCopiaSostitutiva(documento.getFlgCopiaSostitutiva() == Flag.SETTED);
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lDocumentoBean.setUriFileAllegato(documento.getUri());
		lDocumentoBean.setRemoteUri(true);
		if (StringUtils.isNotBlank(documento.getImpronta())) {
			lMimeTypeFirmaBean.setImpronta(documento.getImpronta());
		}
		lMimeTypeFirmaBean.setCorrectFileName(lDocumentoBean.getNomeFileAllegato());
		lMimeTypeFirmaBean.setFirmato(documento.getFlgFileFirmato() == Flag.SETTED);
		lMimeTypeFirmaBean.setFirmaValida(documento.getFlgFileFirmato() == Flag.SETTED);
		lMimeTypeFirmaBean.setConvertibile(documento.getFlgConvertibilePdf() == Flag.SETTED);
		lMimeTypeFirmaBean.setDaScansione(false);
		lMimeTypeFirmaBean.setMimetype(documento.getMimetype());
		lMimeTypeFirmaBean.setBytes(documento.getDimensione() != null ? documento.getDimensione().longValue() : 0);
		if (lMimeTypeFirmaBean.isFirmato()) {
			lMimeTypeFirmaBean.setTipoFirma(documento.getDisplayFileName().toUpperCase().endsWith("P7M")
					|| documento.getDisplayFileName().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
			lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(documento.getFirmatari()) ? documento.getFirmatari().split(",") : null);
		}
		lDocumentoBean.setInfoFile(lMimeTypeFirmaBean);
		lDocumentoBean.setIdAttachEmail(documento.getIdAttachEmail());
		lDocumentoBean.setMimetypeVerPreFirma(documento.getMimetypeVerPreFirma());
		lDocumentoBean.setUriFileVerPreFirma(documento.getUriVerPreFirma());
		lDocumentoBean.setNomeFileVerPreFirma(documento.getDisplayFilenameVerPreFirma());
		lDocumentoBean.setFlgConvertibilePdfVerPreFirma(documento.getFlgConvertibilePdfVerPreFirma());
		lDocumentoBean.setImprontaVerPreFirma(documento.getImprontaVerPreFirma());		
		lDocumentoBean.setNroUltimaVersione(documento.getNroUltimaVersione());
		lDocumentoBean.setIdEmail(documento.getIdEmail());
		lDocumentoBean.setEstremiProtUd(documento.getEstremiProtUd());
		lDocumentoBean.setDataRicezione(documento.getDataRicezione());
		listaDocumenti.add(lDocumentoBean);
	}

	@Override
	public PubblicazioneAlboRicercaPubblicazioniBean update(PubblicazioneAlboRicercaPubblicazioniBean bean, PubblicazioneAlboRicercaPubblicazioniBean oldvalue) throws Exception {
		return bean;
	}
	

	@Override
	public PubblicazioneAlboRicercaPubblicazioniBean add(PubblicazioneAlboRicercaPubblicazioniBean bean) throws Exception {
		return bean;
	}

	

	private TipoAssegnatario getTipoAssegnatario(DestInvioBean lDestInvioBean) {
		TipoAssegnatario[] lTipoAssegnatarios = TipoAssegnatario.values();
		for (TipoAssegnatario lTipoAssegnatario : lTipoAssegnatarios) {
			if (lTipoAssegnatario.toString().equals(lDestInvioBean.getTypeNodo()))
				return lTipoAssegnatario;
		}
		return null;
	}

	
	@Override
	public PubblicazioneAlboRicercaPubblicazioniBean remove(PubblicazioneAlboRicercaPubblicazioniBean bean) throws Exception {
		return bean;
	}

	// Federico Cacco 14.01.2016
	// Commento questo metodo perchè ne creo uno analogo che aggiunge gli allegati del primario ai file da firmare
	public FirmaMassivaFilesBean getListaPerFirmaMassiva(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<FileDaFirmareBean> lListFiles = new ArrayList<FileDaFirmareBean>();
		List<String> documentiFirmati = new ArrayList<String>();
		for (int i = 0; i < bean.getListaRecord().size(); i++) {
			String flgSoloAbilAzioni = getExtraparams().get("flgSoloAbilAzioni");
			BigDecimal idUd = new BigDecimal(bean.getListaRecord().get(i).getIdUdFolder());
			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(idUd);
			lRecuperaDocumentoInBean.setFlgSoloAbilAzioni(flgSoloAbilAzioni);
			lRecuperaDocumentoInBean.setTsAnnDati(getExtraparams().get("tsAnnDati"));
			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
			if(lRecuperaDocumentoOutBean.isInError()) {
				throw new StoreException(lRecuperaDocumentoOutBean);
			}
			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
			ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
			ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);
			documento.setIdUd(idUd);
			documento.setIdEmailArrivo(lDocumentoXmlOutBean.getIdEmailArrivo());
			documento.setEmailArrivoInteroperabile(lDocumentoXmlOutBean.getEmailArrivoInteroperabile());
			documento.setEmailInviataFlgPEC(lDocumentoXmlOutBean.getEmailInviataFlgPEC());
			documento.setEmailInviataFlgPEO(lDocumentoXmlOutBean.getEmailInviataFlgPEO());
			if (documento.getInfoFile() != null && documento.getInfoFile().isFirmato() && documento.getInfoFile().getTipoFirma().startsWith("CAdES")) {
				documentiFirmati.add(bean.getListaRecord().get(i).getSegnatura());
			} else {
				FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
				lFileDaFirmareBean.setIdFile(String.valueOf(documento.getIdDocPrimario()));
				lFileDaFirmareBean.setNomeFile(documento.getNomeFilePrimario());
				lFileDaFirmareBean.setUri(documento.getUriFilePrimario());
				lFileDaFirmareBean.setIdUd(String.valueOf(documento.getIdUd()));
				lFileDaFirmareBean.setInfoFile(documento.getInfoFile());
				lListFiles.add(lFileDaFirmareBean);
			}
		}
		if (documentiFirmati.size() > 0) {
			if (documentiFirmati.size() == bean.getListaRecord().size()) {
				if (documentiFirmati.size() == 1) {
					addMessage("Il documento selezionato risulta già firmato", "", MessageType.ERROR);
				} else {
					addMessage("Tutti i documenti selezionati risultano già firmati", "", MessageType.ERROR);
				}
			} else {
				String message = null;
				if (documentiFirmati.size() == 1) {
					message = "Il documento " + documentiFirmati.get(0) + " risulta già firmato";
				} else {
					message = "I documenti ";
					boolean first = true;
					for (String lStr : documentiFirmati) {
						if (first)
							first = false;
						else
							message += ", ";
						message += lStr;
					}
					message += " risultano già firmati";
				}
				addMessage(message, "", MessageType.WARNING);
			}
		}
		lFirmaMassivaFilesBean.setFiles(lListFiles);
		return lFirmaMassivaFilesBean;
	}

	// Federico Cacco 18.02.2016
	// Restituisce i file da firmare relativi alle unità doumentali ricevute in ingresso
	// Restituisce sia primario che allegati
	public FirmaMassivaFilesBean getListaPerFirmaMassivaConAllegati(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<FileDaFirmareBean> lListaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		List<String> documentiDaNonFirmare = new ArrayList<String>();
		for (int i = 0; i < bean.getListaRecord().size(); i++) {
			boolean udDaFirmare = true;
			String idUd = bean.getListaRecord().get(i).getIdUdFolder();
			// Recupero il file primario e gli allegati dell'unità documentale
			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(new BigDecimal(idUd));
			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
			if(lRecuperaDocumentoOutBean.isInError()) {
				throw new StoreException(lRecuperaDocumentoOutBean);
			}
			List<FileDaFirmareBean> listaAllegatiDaFirmareUd = new ArrayList<FileDaFirmareBean>();
			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
			ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());			
			//la variabile documento ha al suo interno tutti i valori come nomeDocumento, allegati,....
			ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);
			if (StringUtils.isNotBlank(documento.getUriFilePrimario()) || (documento.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(documento.getFilePrimarioOmissis().getUriFile()))) {				
				if (StringUtils.isNotBlank(documento.getUriFilePrimario())) {
					FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
					lFileDaFirmareBean.setIdFile(documento.getIdDocPrimario() != null ? String.valueOf(documento.getIdDocPrimario()) : null);
					lFileDaFirmareBean.setNomeFile(documento.getNomeFilePrimario());
					lFileDaFirmareBean.setUri(documento.getUriFilePrimario());
					lFileDaFirmareBean.setIdUd(idUd);
					lFileDaFirmareBean.setInfoFile(documento.getInfoFile());
					lFileDaFirmareBean.setCodiceTipoRelazione("P");
					listaAllegatiDaFirmareUd.add(lFileDaFirmareBean);
				}				
				if(documento.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(documento.getFilePrimarioOmissis().getUriFile())) {
					FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
					lFileDaFirmareBeanOmissis.setIdFile(documento.getFilePrimarioOmissis().getIdDoc() != null ? String.valueOf(documento.getFilePrimarioOmissis().getIdDoc()) : null);
					lFileDaFirmareBeanOmissis.setNomeFile(documento.getFilePrimarioOmissis().getNomeFile());
					lFileDaFirmareBeanOmissis.setUri(documento.getFilePrimarioOmissis().getUriFile());
					lFileDaFirmareBeanOmissis.setIdUd(idUd);
					lFileDaFirmareBeanOmissis.setInfoFile(documento.getFilePrimarioOmissis().getInfoFile());
					lFileDaFirmareBeanOmissis.setCodiceTipoRelazione("P");
					listaAllegatiDaFirmareUd.add(lFileDaFirmareBeanOmissis);
				}				
			} else {
				documentiDaNonFirmare.add(bean.getListaRecord().get(i).getSegnatura());
				udDaFirmare = false;
			}
			if (udDaFirmare && documento.getListaAllegati() != null) {
				for (AllegatoProtocolloBean allegatoUdBean : documento.getListaAllegati()) {
					if ((StringUtils.isNotBlank(allegatoUdBean.getUriFileAllegato()) ||  StringUtils.isNotBlank(allegatoUdBean.getUriFileOmissis())) && 
							allegatoUdBean.getFlgParteDispositivo() != null && allegatoUdBean.getFlgParteDispositivo()) {						
						if (StringUtils.isNotBlank(allegatoUdBean.getUriFileAllegato())) {
							FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
							lFileDaFirmareBean.setIdFile(allegatoUdBean.getIdDocAllegato() != null ? String.valueOf(allegatoUdBean.getIdDocAllegato()) : null);
							lFileDaFirmareBean.setNomeFile(allegatoUdBean.getNomeFileAllegato());
							lFileDaFirmareBean.setUri(allegatoUdBean.getUriFileAllegato());
							lFileDaFirmareBean.setIdUd(idUd);
							lFileDaFirmareBean.setInfoFile(allegatoUdBean.getInfoFile());
							lFileDaFirmareBean.setCodiceTipoRelazione("ALL");
							listaAllegatiDaFirmareUd.add(lFileDaFirmareBean);	
						}
						if (StringUtils.isNotBlank(allegatoUdBean.getUriFileOmissis())) {
							FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
							lFileDaFirmareBeanOmissis.setIdFile(allegatoUdBean.getIdDocOmissis() != null ? String.valueOf(allegatoUdBean.getIdDocOmissis()) : null);
							lFileDaFirmareBeanOmissis.setNomeFile(allegatoUdBean.getNomeFileOmissis());
							lFileDaFirmareBeanOmissis.setUri(allegatoUdBean.getUriFileOmissis());
							lFileDaFirmareBeanOmissis.setIdUd(idUd);
							lFileDaFirmareBeanOmissis.setInfoFile(allegatoUdBean.getInfoFileOmissis());
							lFileDaFirmareBeanOmissis.setCodiceTipoRelazione("ALL");
							listaAllegatiDaFirmareUd.add(lFileDaFirmareBeanOmissis);
						}
					}
				}
			}
			// Verifico se l'unità documentale è da firmare
			if (udDaFirmare) {
				// L'unità documentale deve essere firmata, aggiunto allegati e
				// primario alla lista file da firmare
				for (FileDaFirmareBean allegatoUd : listaAllegatiDaFirmareUd) {
					lListaFileDaFirmare.add(allegatoUd);
				}
			}
		}
		if (documentiDaNonFirmare.size() > 0) {
			if (documentiDaNonFirmare.size() == bean.getListaRecord().size()) {
				if (documentiDaNonFirmare.size() == 1) {
					addMessage("Il documento selezionato risulta già firmato o senza file primario", "", MessageType.ERROR);
				} else {
					addMessage("Tutti i documenti selezionati risultano già firmati o senza file primario", "", MessageType.ERROR);
				}
			} else {
				String message = null;
				if (documentiDaNonFirmare.size() == 1) {
					message = "Il documento " + documentiDaNonFirmare.get(0) + " risulta già firmato o senza file primario";
				} else {
					message = "I documenti ";
					boolean first = true;
					for (String lStr : documentiDaNonFirmare) {
						if (first)
							first = false;
						else
							message += ", ";
						message += lStr;
					}
					message += " risultano già firmati o senza file primario";
				}
				addMessage(message, "", MessageType.WARNING);
			}
		}
		lFirmaMassivaFilesBean.setFiles(lListaFileDaFirmare);		
		GenericConfigBean config = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		String prefTipoFirma = getExtraparams().get("prefTipoFirma");
		if(prefTipoFirma != null && !"".equals(prefTipoFirma)) {
			if("C".equalsIgnoreCase(prefTipoFirma)) {
				if(config.getMaxNroDocFirmaMassivaClient() >= 1) {
					if( lListaFileDaFirmare.size() > config.getMaxNroDocFirmaMassivaClient()){
						lListaFileDaFirmare.clear();
						addMessage("Selezionato per firma un n.ro di documenti superiore al massimo consentito di "
								+ config.getMaxNroDocFirmaMassivaClient(), "", MessageType.ERROR);
					}
				}
			} else if("R".equalsIgnoreCase(prefTipoFirma)) {
				if(config.getMaxNroDocFirmaMassivaRemotaNonAuto() >= 1) {
					if(lListaFileDaFirmare.size() > config.getMaxNroDocFirmaMassivaRemotaNonAuto()){
						lListaFileDaFirmare.clear();
						addMessage("Selezionato per firma un n.ro di documenti superiore al massimo consentito di "
								+ config.getMaxNroDocFirmaMassivaRemotaNonAuto(), "", MessageType.ERROR);
					}
				}
			} 
//			else  if("A".equalsIgnoreCase(prefTipoFirma)) {
//				
//			}
		}		
		return lFirmaMassivaFilesBean;
	}
	
	public FirmaMassivaFilesBean getListaPerFirmaSingolaConAllegati(ProtocollazioneBean documento) throws Exception {
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<String> documentiDaNonFirmare = new ArrayList<String>();
		List<FileDaFirmareBean> listaAllegatiDaFirmareUd = new ArrayList<FileDaFirmareBean>();
		List<FileDaFirmareBean> lListaFileDaFirmare = new ArrayList<FileDaFirmareBean>();		
		boolean udDaFirmare = true;
		String idUd = documento.getIdUd() != null ? String.valueOf(documento.getIdUd()): null;
		//la variabile documento ha al suo interno tutti i valori come nomeDocumento, allegati,....
		if (StringUtils.isNotBlank(documento.getUriFilePrimario()) || (documento.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(documento.getFilePrimarioOmissis().getUriFile()))) {			
			if (StringUtils.isNotBlank(documento.getUriFilePrimario())) {
				FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
				lFileDaFirmareBean.setIdFile(documento.getIdDocPrimario() != null ? String.valueOf(documento.getIdDocPrimario()) : null);
				lFileDaFirmareBean.setNomeFile(documento.getNomeFilePrimario());
				lFileDaFirmareBean.setUri(documento.getUriFilePrimario());
				lFileDaFirmareBean.setIdUd(idUd);
				lFileDaFirmareBean.setInfoFile(documento.getInfoFile());
				lFileDaFirmareBean.setCodiceTipoRelazione("P");
				listaAllegatiDaFirmareUd.add(lFileDaFirmareBean);
			}			
			if(documento.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(documento.getFilePrimarioOmissis().getUriFile())) {
				FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
				lFileDaFirmareBeanOmissis.setIdFile(documento.getFilePrimarioOmissis().getIdDoc() != null ? String.valueOf(documento.getFilePrimarioOmissis().getIdDoc()) : null);
				lFileDaFirmareBeanOmissis.setNomeFile(documento.getFilePrimarioOmissis().getNomeFile());
				lFileDaFirmareBeanOmissis.setUri(documento.getFilePrimarioOmissis().getUriFile());
				lFileDaFirmareBeanOmissis.setIdUd(idUd);
				lFileDaFirmareBeanOmissis.setInfoFile(documento.getFilePrimarioOmissis().getInfoFile());
				lFileDaFirmareBeanOmissis.setCodiceTipoRelazione("P");
				listaAllegatiDaFirmareUd.add(lFileDaFirmareBeanOmissis);
			}			
		} else {
			documentiDaNonFirmare.add(documento.getSegnatura());
			udDaFirmare = false;			
		}		
		if (udDaFirmare && documento.getListaAllegati() != null) {
			for (AllegatoProtocolloBean allegatoUdBean : documento.getListaAllegati()) {
				if ((StringUtils.isNotBlank(allegatoUdBean.getUriFileAllegato()) ||  StringUtils.isNotBlank(allegatoUdBean.getUriFileOmissis())) && 
						allegatoUdBean.getFlgParteDispositivo() != null && allegatoUdBean.getFlgParteDispositivo()) {	
					if (StringUtils.isNotBlank(allegatoUdBean.getUriFileAllegato())) {
						FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
						lFileDaFirmareBean.setIdFile(allegatoUdBean.getIdDocAllegato() != null ? String.valueOf(allegatoUdBean.getIdDocAllegato()) : null);
						lFileDaFirmareBean.setNomeFile(allegatoUdBean.getNomeFileAllegato());
						lFileDaFirmareBean.setUri(allegatoUdBean.getUriFileAllegato());
						lFileDaFirmareBean.setIdUd(idUd);
						lFileDaFirmareBean.setInfoFile(allegatoUdBean.getInfoFile());
						lFileDaFirmareBean.setCodiceTipoRelazione("ALL");
						listaAllegatiDaFirmareUd.add(lFileDaFirmareBean);	
					}
					if (StringUtils.isNotBlank(allegatoUdBean.getUriFileOmissis())) {
						FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
						lFileDaFirmareBeanOmissis.setIdFile(allegatoUdBean.getIdDocOmissis() != null ? String.valueOf(allegatoUdBean.getIdDocOmissis()) : null);
						lFileDaFirmareBeanOmissis.setNomeFile(allegatoUdBean.getNomeFileOmissis());
						lFileDaFirmareBeanOmissis.setUri(allegatoUdBean.getUriFileOmissis());
						lFileDaFirmareBeanOmissis.setIdUd(idUd);
						lFileDaFirmareBeanOmissis.setInfoFile(allegatoUdBean.getInfoFileOmissis());
						lFileDaFirmareBeanOmissis.setCodiceTipoRelazione("ALL");
						listaAllegatiDaFirmareUd.add(lFileDaFirmareBeanOmissis);
					}
				}
			}
		}
		// Verifico se l'unità documentale è da firmare
		if (udDaFirmare) {
			// L'unità documentale deve essere firmata, aggiunto allegati e
			// primario alla lista file da firmare
			for (FileDaFirmareBean allegatoUd : listaAllegatiDaFirmareUd) {
				lListaFileDaFirmare.add(allegatoUd);
			}
		}
		lFirmaMassivaFilesBean.setFiles(lListaFileDaFirmare);
		return lFirmaMassivaFilesBean;
	}

	public FirmaMassivaFilesBean aggiornaDocumentoFirmato(FirmaMassivaFilesBean pFirmaMassivaFilesBean) throws Exception {
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<FileDaFirmareBean> filesInError = new ArrayList<FileDaFirmareBean>();
		for (FileDaFirmareBean lFileDaFirmareBean : pFirmaMassivaFilesBean.getFiles()) {
			try {
				String modalitaFirma = ParametriDBUtil.getParametroDB(getSession(), "MODALITA_FIRMA");
				String appletFirmaSpec = ParametriDBUtil.getParametroDB(getSession(), "APPLET_FIRMA_SPEC");
				if ((StringUtils.isBlank(modalitaFirma) || modalitaFirma.equalsIgnoreCase("APPLET")) && StringUtils.isBlank(appletFirmaSpec)) {
					// aggiungo l'estensione p7m al nome del file firmato perchè l'applet di firma multipla non lo fa
					boolean isCAdES = lFileDaFirmareBean.getInfoFile() != null && lFileDaFirmareBean.getInfoFile().isFirmato() && lFileDaFirmareBean.getInfoFile().getTipoFirma().startsWith("CAdES");
					if (isCAdES && !lFileDaFirmareBean.getNomeFile().toLowerCase().endsWith(".p7m")) {
						lFileDaFirmareBean.setNomeFile(lFileDaFirmareBean.getNomeFile() + ".p7m");
					}
				}
				boolean firmaEseguita = lFileDaFirmareBean.getFirmaEseguita() != null && lFileDaFirmareBean.getFirmaEseguita();
				if (!firmaEseguita || lFileDaFirmareBean.getInfoFile() == null || !lFileDaFirmareBean.getInfoFile().isFirmato() || !lFileDaFirmareBean.getInfoFile().isFirmaValida()) {
					throw new Exception("Firma non presente o non valida");
				}
				versionaDocumento(lFileDaFirmareBean);				
			} catch (Exception e) {
				filesInError.add(lFileDaFirmareBean);
			}
		}
		lFirmaMassivaFilesBean.setFiles(filesInError);
		return lFirmaMassivaFilesBean;
	}

	public void versionaDocumento(IdFileBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento(new BigDecimal(bean.getIdFile()));
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(StorageImplementation.getStorage().store(StorageImplementation.getStorage().extractFile(bean.getUri()))));
		
		MimeTypeFirmaBean lMimeTypeFirmaBean;
		if(bean.getInfoFile() == null) {
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
		} else {
			lMimeTypeFirmaBean = bean.getInfoFile();
		}
		
		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);
		GenericFile lGenericFile = new GenericFile();
		setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
		lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
		lGenericFile.setDisplayFilename(bean.getNomeFile());
		lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
		lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
		lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
		if (lMimeTypeFirmaBean.isDaScansione()) {
			lGenericFile.setDaScansione(Flag.SETTED);
			lGenericFile.setDataScansione(new Date());
			lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
		}
		lFileInfoBean.setAllegatoRiferimento(lGenericFile);
		lRebuildedFile.setInfo(lFileInfoBean);

		VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);

		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean);
		if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
			mLogger.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
			throw new StoreException(lVersionaDocumentoOutBean);
		}			
	}

	public ExportBean stampaAttestato(AttestatoConformitaBean attestatoConformitaBean) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String oggi = sdf.format(new Date());

		MimeTypeFirmaBean infoFileRecord = attestatoConformitaBean.getInfoFileAttach();

		ExportBean exportBean = new ExportBean();

		File file = File.createTempFile("attestato", "");
		Font font_10 = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

		document.setMargins(20, 20, 20, 20);

		document.newPage();
		document.open();

		String testo = "Si attesta che la presente copia del documento ";
		testo += "N° " + (attestatoConformitaBean.getSiglaProtocollo() == null ? "" : attestatoConformitaBean.getSiglaProtocollo()) + " "
				+ attestatoConformitaBean.getNroProtocollo();
		testo += " del " + attestatoConformitaBean.getDataProtocollo() + " ";
		// testo += " redatto da " + attestatoConformitaBean.getDesUserProtocollo();
		// testo += " U.O proponente " + attestatoConformitaBean.getDesUOProtocollo() + " ";

		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(attestatoConformitaBean.getUriAttach());
		RecuperoFile lRecuperoFile = new RecuperoFile();
		FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
		File lFile = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
	
		InputStream lInputStream = null;

		InfoFileUtility lInfoFileUtility = new InfoFileUtility();

		if (infoFileRecord.isFirmato() && infoFileRecord.getTipoFirma().startsWith("CAdES")) {
			lInputStream = lInfoFileUtility.sbusta(lFile.toURI().toString(), "");
		} else {
			lInputStream = new FileInputStream(lFile);
		}

		String uri = StorageImplementation.getStorage().storeStream(lInputStream);

		File realFile = StorageImplementation.getStorage().getRealFile(uri);
		byte[] bytesRealFile = getFileBytes(realFile);

		if (infoFileRecord.getMimetype().equals("application/pdf")) {

			PdfReader reader = new PdfReader(bytesRealFile);
			int numPagine = reader.getNumberOfPages();
			testo += "composta da N° " + numPagine + " facciate, è copia ";

		} else
			testo += ", è ";

		testo += "conforme all'originale digitale, depositato presso " + ParametriDBUtil.getParametroDB(getSession(), "DES_SOGG_PROD_X_ATT_CONF");
		testo += ", la cui impronta è quella riportata nel timbro seguente:\n\n";

		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		InfoFileUtility lFileUtility = new InfoFileUtility();
		lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null);

		// PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(lMimeTypeFirmaBean.getCorrectFileName()));
		HeaderFooterPageEvent header = new HeaderFooterPageEvent();
		writer.setPageEvent(header);

		// document.setMargins(20, 20, 20, 20);
		//
		// document.newPage();
		// document.open();

		document.add(new Paragraph(testo, font_10));

		// TIMBRO
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		BarcodePDF417 pdf417 = new BarcodePDF417();
		pdf417.setText(lDocumentConfiguration.getAlgoritmo().value() + " " + lDocumentConfiguration.getEncoding().value() + " "
				+ lMimeTypeFirmaBean.getImpronta());
		Image img = pdf417.getImage();

		document.add(img);

		testo = "\n\n";
		testo += ParametriDBUtil.getParametroDB(getSession(), "SEDE_SOGG_PROD_AOO");

		testo += ", li " + oggi + "\n\n";

		testo += "Firma " + AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getDesUserOut();

		document.add(new Paragraph(testo, font_10));

		document.close();

		StorageService storageService = StorageImplementation.getStorage();
		exportBean.setTempFileOut(storageService.store(file));

		lMimeTypeFirmaBean.setCorrectFileName("Attestazione.pdf");

		lMimeTypeFirmaBean.setFirmato(false);
		lMimeTypeFirmaBean.setFirmaValida(false);
		lMimeTypeFirmaBean.setConvertibile(true);
		lMimeTypeFirmaBean.setDaScansione(false);
		lMimeTypeFirmaBean.setMimetype("application/pdf");
		lMimeTypeFirmaBean.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(file));
		exportBean.setInfoFileOut(lMimeTypeFirmaBean);

		return exportBean;

	}

	protected static byte[] getFileBytes(File file) {
		byte[] bFile = new byte[(int) file.length()];

		try {
			// convert file into array of bytes
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

		} catch (Exception e) {
			mLogger.warn(e);
		}
		return bFile;
	}

	private class HeaderFooterPageEvent extends PdfPageEventHelper {

		public void onStartPage(PdfWriter writer, Document document) {

			GenericConfigBean lGenericPropertyConfigurator = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");

			String logoDominioImage = null;
			String logoUtente = null;

			if (lGenericPropertyConfigurator.getLogoDominioFolder() != null && !"".equals(lGenericPropertyConfigurator.getLogoDominioFolder())) {
				logoDominioImage = lGenericPropertyConfigurator.getLogoDominioFolder();
				if (!lGenericPropertyConfigurator.getLogoDominioFolder().endsWith("/")) {
					logoDominioImage += "/";
				}

				String idDominio = null;
				if (AurigaUserUtil.getLoginInfo(getSession()).getDominio() != null && !"".equals(AurigaUserUtil.getLoginInfo(getSession()).getDominio())) {
					if (AurigaUserUtil.getLoginInfo(getSession()).getDominio().split(":").length == 2) {
						idDominio = AurigaUserUtil.getLoginInfo(getSession()).getDominio().split(":")[1];
					}
				}
				// se l'utente ha un suo logo mostra quello altrimenti mostra il logo di default del dominio
				if (AurigaUserUtil.getLoginInfo(getSession()).getLogoUtente() != null && !"".equals(AurigaUserUtil.getLoginInfo(getSession()).getLogoUtente())) {
					logoUtente = (AurigaUserUtil.getLoginInfo(getSession()).getLogoUtente()).toString();
					logoDominioImage += logoUtente;
				} else if (idDominio != null && !"".equals(idDominio)) {
					logoDominioImage += "Logo_" + idDominio + ".png";
				} else {
					logoDominioImage = "images/blank.png";
				}

				try {

					Image logo = Image.getInstance(getExtraparams().get("urlContext") + logoDominioImage);
					logo.setAlignment(Image.ALIGN_CENTER);
					// logo.setWidthPercentage(50);
					document.add(logo);

					String footerLogo = ParametriDBUtil.getParametroDB(getSession(), "FOOTER_LOGO_SOGG_PROD_AOO").replaceAll("<br/>", "\n");

					Font font_10 = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
					Paragraph footerLogoParagraph = new Paragraph(footerLogo + "\n\n", font_10);
					footerLogoParagraph.setAlignment(Element.ALIGN_CENTER);
					document.add(footerLogoParagraph);

				} catch (Exception e) {
					mLogger.warn(e);
				}

			}

		}

		public void onEndPage(PdfWriter writer, Document document) {
		}

	}

	protected FindRepositoryObjectBean createFindRepositoryObjectBean(AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {

		/*
		 *  col.  1 : flgUdFolder
		 *  col.  2 : idUdFolder
		 *  col.  4 : segnatura
		 *  col.  6 : segnaturaXOrd
		 *  col. 14 : nroPubblicazione
		 *  col. 15 : tsPubblicazione
		 *  col. 18 : oggetto
		 *  col. 32 : tipologia documentale
		 *  col. 33 : idDocPrimario
		 *  col. 35 : tsInvio
		 *  col. 36 : tsEliminazione
		 *  col. 37 : eliminatoDa
		 *  col. 38 : destinatariInvio
		 *  col. 39 : flgSelXFinalita
		 *  col. 42 : assegnatari
		 *  col. 60 : tsRicezione
		 *  col. 61 : prioritaInvioNotifiche
		 *  col. 62 : estremiInvioNotifiche
		 *  col. 72 : dtEsecutivita
		 *  col. 89 : score
		 *  col. 91 : richiedentePubblicazione
		 *  col. 93 : nroDocConFile
		 *  col. 94 : nroDocConFileFirmati
		 *  col. 95 : nroDocConFileDaScanner
		 *  col. 96 : flgRicevutaViaEmail
		 *  col. 97 : flgInviataViaEmail
		 *  col.101 : flgImmediatamenteEseg (FLG_IMMEDIATAMENTE_ESEGUIBILE)
		 *  col.201 : dataAtto
		 *  col.206 : fascicoliApp
		 *  col.207 : livelloRiservatezza
		 *  col.209 : flgNotificatoAMe
		 *  col.214 : utenteProtocollante
		 *  col.215 : uoProtocollante
		 *  col.226 : uoProponente
		 *  col.227 : statoTrasmissioneEmail
		 *  col.261 : attoAutAnnullamento
		 *  col.263 : tsPresaInCarico
		 *  col.270 : dataInizioPubblicazione
		 *  col.271 : dataFinePubblicazione
		 *  col.273 : formaPubblicazione
		 *  col.274 : statoPubblicazione
		 *  col.276 : motivoAnnullamento
		 *  col.281 : centroDiCosto
		 *  col.282 : dataScadenza
		 *  col.283 : idRichPubbl
		 */
		String colsToReturn = "1,2,4,6,14,15,18,32,33,35,36,37,38,39,42,60,61,62,72,89,91,93,94,95,96,97,201,206,207,209,214,215,226,227,261,263,270,271,273,274,276,281,282,283,FLG_IMMEDIATAMENTE_ESEGUIBILE";
		
		String filtroFullText = null;
		String[] checkAttributes = null;
		String formatoEstremiReg = null;
		Integer searchAllTerms = null;
		String idNode = null;
		Long idFolder = null;
		String criteriAvanzati = null;
		// in modalita' di esplora il valore di default e' 0
		String includiSottoCartelle = "0";
		String advancedFilters = null;
		String customFilters = null;
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = new Integer(1);
		Integer numPagina = null;
		Integer numRighePagina = null;
		Integer online = null;
		String percorsoRicerca = null;
		String flgTipoRicerca = null;
		String finalita = null;
		String annoBozzaDa = null;
		String annoBozzaA = null;
		String nroBozzaDa = null;
		String nroBozzaA = null;
		Date tsBozzaDa = null;
		Date tsBozzaA = null;
		Date dtRaccomandataDal = null;
		Date dtRaccomandataAl = null;
		String nroRaccomandata = null;
		String annoStampaDa = null;
		String annoStampaA = null;
		String nroStampaDa = null;
		String nroStampaA = null;
		Date tsStampaDa = null;
		Date tsStampaA = null;
		String annoProtDa = null;
		String annoProtA = null;
		String nroProtDa = null;
		String nroProtA = null;
		String tipoProt = null;
		Date tsRegistrazioneDa = null;
		Date tsRegistrazioneA = null;
		Date tsAperturaFascicoloDa = null;
		Date tsAperturaFascicoloA = null;
		Date presaInCaricoDal = null;
		Date presaInCaricoAl = null;
		Date tsChiusuraFascicoloDa = null;
		Date tsChiusuraFascicoloA = null;
		String flgSoloDaLeggere = null;
		Date tsInvioDa = null;
		Date tsInvioA = null;
		List<DestInvioNotifica> inviatiA = null;
		List<DestInvioNotifica> assegnatiA = null;
		List<DestInvioNotifica> inConoscenzaA = null;
		List<DestInvioNotifica> conNotificheAutoA = null;
		Date tsArchiviazioneDa = null;
		Date tsArchiviazioneA = null;
		Date tsEliminazioneDa = null;
		Date tsEliminazioneA = null;
		String sezioneEliminazione = null;
		String restringiSottofascInserti = null;
		String flgUdFolder = "";
		String soloDocRicevutiViaEmail = null;
		String inviatiViaEmail = null;
		String statoPresaInCarico = null;
		String statoRichAnnullamento = null;
		String statoAutorizzazione = null;
		Date tsAssegnazioneDa = null;
		Date tsAssegnazioneA = null;
		Date tsInvioNotificaDa = null;
		Date tsInvioNotificaA = null;
		String soloRegAnnullate = null;
		String mittente = null;
		String operMittente = null;
		String idRubricaMitt = null;
		String destinatario = null;
		String operDestinatario = null;
		String idRubricaDest = null;
		String esibente = null;
		String operEsibente = null;
		String idRubricaEsib = null;
		String oggetto = null;
		String nomeFascicolo = null;
		String regEffettuataDa = null;
		String fileAssociati = null;
		String mezzoTrasmissioneFilter = null;
		// FILTRI @Registrazioni, visibili in tutte le sezioni della scrivania e ricerca documenti tranne bozze e stampe
		String altraNumerazioneSigla = null;
		String altraNumerazioneAnnoDa = null;
		String altraNumerazioneAnnoA = null;
		Date altraNumerazioneDataDa = null;
		Date altraNumerazioneDataA = null;
		String altraNumerazioneNrDa = null;
		String altraNumerazioneNrA = null;
		// FILTRI MODULO ATTI
		List<DestInvioNotifica> uoProponente = null;
		List<DestInvioNotifica> uoRegistrazione = null;
		List<DestInvioNotifica> uoApertura = null;
		String utentiAvvioAtto = null;
		String utentiAdozioneAtto = null;
		String noteUd = null;
		String statiDoc = null;
		String statiFolder = null;
		Date dataFirmaAttoDa = null;
		Date dataFirmaAttoA = null;
		String tipoDoc = null;
		String supportoProt = null;
		AdvancedCriteria criteriaAttributiCustomDoc = null;
		Map<String, String> mappaTipiAttributiCustomDoc = null;
		String tipoFolder = null;
		AdvancedCriteria criteriaAttributiCustomFolder = null;
		Map<String, String> mappaTipiAttributiCustomFolder = null;
		String nroProtRicevuto = null;
		Date dataProtRicevutoDa = null;
		Date dataProtRicevutoA = null;
		String statoTrasmissioneEmail = null;
		String tipoDocStampa = null;
		String nroRichiestaStampaExpDa = null;
		String nroRichiestaStampaExpA = null;
		Date tsRichiestaStampaExpDa = null;
		Date tsRichiestaStampaExpA = null;
		String capoFilaFasc = null;
		String protocolloCapofila = null;
		String docCollegatiProt = null;
		String idIndirizzoInVario = null;
		String indirizzo = null;
		String flgAppostoTimbro = null;
		String subDa = null;
		String subA = null;
		String sigla = null;
		String idClassificazione = null;
		String livelliClassificazione =  null;
		String flgIncludiSottoClassifiche = null;
		
		String annoFascicoloDa = null;
		String annoFascicoloA = null;
		String nroFascicoloDa = null;
		String nroFascicoloA = null;
		String nroSottoFascicoloDa = null;
		String nroSottoFascicoloA = null;
		String codiceFascicolo = null;
		String attoAutAnnullamento = null;
		Date dtStesuraDal = null;
		Date dtStesuraAl = null;
		String nominativoUD   = null;
		String operNominativoUD  = null;
		String tipiNominativoUD = null; 
		
		String centroDiCosto = null;
		Date dataScadenzaDa = null;
		Date dataScadenzaA = null;
		
		String cfNominativoCollegato = null;
		String flgRiservatezza = null;
		
		// FILTRI PUBBLICAZIONE
		String nroPubblicazioneDa = null;
		String nroPubblicazioneA = null;
				

		String annoPubblicazioneDa = null;
		String annoPubblicazioneA = null;

		Date dtInizioPubblicazioneDal = null;
		Date dtInizioPubblicazioneAl = null;
		
		Date dtTerminePubblicazioneDal = null;
		Date dtTerminePubblicazioneAl = null;
		
		String statoPubblicazione = null;
		String pubblicazioneRettificata = null;
		String pubblicazioneAnnullata = null;
		String pubblicazioneEffettuataDa = null;
		List<DestInvioNotifica> uoPubblicazione = null;
		
		String ggDurataPubblicazioneDa = null;
		String ggDurataPubblicazioneA = null;
		
		
		boolean setNullFlgSubfolderSearch = getExtraparams().get("setNullFlgSubfolderSearch") != null ? new Boolean(getExtraparams().get(
				"setNullFlgSubfolderSearch")) : false;
		String interesseCessato = getExtraparams().get("interesseCessato");

		List<CriteriPersonalizzati> listCustomFilters = new ArrayList<CriteriPersonalizzati>();

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("idNode".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						idNode = (String) crit.getValue();
					}
				} else if ("idFolder".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						idFolder = Long.parseLong((String) crit.getValue());
					}
				} else if ("flgUdFolder".equals(crit.getFieldName())) {
					// Se e' stato aggiunto il filtro "Restringi ricerca a" oppure il filtro "Restringi ricerca a ( su nodi con solo fascicoli )
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						if ("flgUdFolder".equals(crit.getFieldName())) {
							flgUdFolder = String.valueOf(crit.getValue());
						}
						if (flgUdFolder == null)
							flgUdFolder = "";
						// Se stato selezionato il valore "fascicoli e sotto-fascicoli"
						if (flgUdFolder.equalsIgnoreCase("FS")) {
							flgUdFolder = "F";
							restringiSottofascInserti = "F;S;I";
						}
						// Se e' stato selezionato il valore "fascicoli"
						else if (flgUdFolder.equalsIgnoreCase("F")) {
							flgUdFolder = null;
							restringiSottofascInserti = "F";
						}
						// Se stato selezionato il valore "sotto-fascicoli", setto RestringiSottofascInserti = S
						else if (flgUdFolder.equalsIgnoreCase("S")) {
							flgUdFolder = null;
							restringiSottofascInserti = "S";
						}
						// Se e' stato selezionato il valore "documenti"
						else if (flgUdFolder.equalsIgnoreCase("U")) {
							flgUdFolder = "U";
							restringiSottofascInserti = null;
						}
					}
				} else if ("soloDocRicevutiViaEmail".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						soloDocRicevutiViaEmail = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}
				} else if ("inviatiViaEmail".equals(crit.getFieldName())) {
					inviatiViaEmail = getTextFilterValue(crit);
				} else if ("statoPresaInCarico".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						statoPresaInCarico = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}
				} else if ("flgAppostoTimbro".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgAppostoTimbro = new Boolean(String.valueOf(crit.getValue())) ? "SI" : "NO";
					}
				} else if ("criteriAvanzati".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						criteriAvanzati = String.valueOf(crit.getValue());
					}
				} else if ("finalita".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						finalita = String.valueOf(crit.getValue());
					}
				} else if ("searchFulltext".equals(crit.getFieldName())) {
					// se sono entrato qui sono in modalita' di ricerca con i filtri quindi imposto il valore di default a 1
					includiSottoCartelle = "1";
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						filtroFullText = (String) map.get("parole");
						ArrayList<String> lArrayList = (ArrayList<String>) map.get("attributi");
						checkAttributes = lArrayList != null ? lArrayList.toArray(new String[] {}) : null;
						Boolean flgRicorsiva = (Boolean) map.get("flgRicorsiva");
						if (flgRicorsiva != null) {
							includiSottoCartelle = flgRicorsiva ? "1" : "0";
						}
						String operator = crit.getOperator();
						if (StringUtils.isNotBlank(operator)) {
							if ("allTheWords".equals(operator)) {
								searchAllTerms = new Integer("1");
							} else if ("oneOrMoreWords".equals(operator)) {
								searchAllTerms = new Integer("0");
							}
						}
					}
				} else if ("annoBozza".equals(crit.getFieldName())) {
					String[] estremiAnnoBozza = getNumberFilterValue(crit);
					annoBozzaDa = estremiAnnoBozza[0] != null ? estremiAnnoBozza[0] : null;
					annoBozzaA = estremiAnnoBozza[1] != null ? estremiAnnoBozza[1] : null;
				} else if ("nroBozza".equals(crit.getFieldName())) {
					String[] estremiNroBozza = getNumberFilterValue(crit);
					nroBozzaDa = estremiNroBozza[0] != null ? estremiNroBozza[0] : null;
					nroBozzaA = estremiNroBozza[1] != null ? estremiNroBozza[1] : null;
				} else if ("tsBozza".equals(crit.getFieldName())) {
					Date[] estremiDataBozza = getDateEstesaFilterValue(crit);
					if (tsBozzaDa != null) {
						tsBozzaDa = tsBozzaDa.compareTo(estremiDataBozza[0]) < 0 ? estremiDataBozza[0] : tsBozzaDa;
					} else {
						tsBozzaDa = estremiDataBozza[0];
					}
					if (tsBozzaA != null) {
						tsBozzaA = tsBozzaA.compareTo(estremiDataBozza[1]) > 0 ? estremiDataBozza[1] : tsBozzaA;
					} else {
						tsBozzaA = estremiDataBozza[1];
					}
				} else if ("dtRaccomandata".equals(crit.getFieldName())) {
					Date[] estremiDataRaccomandata = getDateFilterValue(crit);
					if (dtRaccomandataDal != null) {
						dtRaccomandataDal = dtRaccomandataDal.compareTo(estremiDataRaccomandata[0]) < 0 ? estremiDataRaccomandata[0] : dtRaccomandataDal;
					} else {
						dtRaccomandataDal = estremiDataRaccomandata[0];
					}
					if (dtRaccomandataAl != null) {
						dtRaccomandataAl = dtRaccomandataAl.compareTo(estremiDataRaccomandata[1]) > 0 ? estremiDataRaccomandata[1] : dtRaccomandataAl;
					} else {
						dtRaccomandataAl = estremiDataRaccomandata[1];
					}
				} else if ("nroRaccomandata".equals(crit.getFieldName())) {
					nroRaccomandata = getTextFilterValue(crit);
				} else if ("tipoDocStampa".equals(crit.getFieldName())) {
					tipoDocStampa = getTextFilterValue(crit);
				} else if ("annoStampa".equals(crit.getFieldName())) {
					String[] estremiAnnoStampa = getNumberFilterValue(crit);
					annoStampaDa = estremiAnnoStampa[0] != null ? estremiAnnoStampa[0] : null;
					annoStampaA = estremiAnnoStampa[1] != null ? estremiAnnoStampa[1] : null;
				} else if ("nroStampa".equals(crit.getFieldName())) {
					String[] estremiNroStampa = getNumberFilterValue(crit);
					nroStampaDa = estremiNroStampa[0] != null ? estremiNroStampa[0] : null;
					nroStampaA = estremiNroStampa[1] != null ? estremiNroStampa[1] : null;
				} else if ("tsStampa".equals(crit.getFieldName())) {
					Date[] estremiDataStampa = getDateFilterValue(crit);
					if (tsStampaDa != null) {
						tsStampaDa = tsStampaDa.compareTo(estremiDataStampa[0]) < 0 ? estremiDataStampa[0] : tsStampaDa;
					} else {
						tsStampaDa = estremiDataStampa[0];
					}
					if (tsStampaA != null) {
						tsStampaA = tsStampaA.compareTo(estremiDataStampa[1]) > 0 ? estremiDataStampa[1] : tsStampaA;
					} else {
						tsStampaA = estremiDataStampa[1];
					}
				} else if ("nroRichiestaStampaExp".equals(crit.getFieldName())) {
					String[] estremiNroRichiestaStampaExp = getNumberFilterValue(crit);
					nroRichiestaStampaExpDa = estremiNroRichiestaStampaExp[0] != null ? estremiNroRichiestaStampaExp[0] : null;
					nroRichiestaStampaExpA = estremiNroRichiestaStampaExp[1] != null ? estremiNroRichiestaStampaExp[1] : null;
				} else if ("tsRichiestaStampaExp".equals(crit.getFieldName())) {
					Date[] estremiDataRichiestaStampaExp = getDateFilterValue(crit);
					if (tsRichiestaStampaExpDa != null) {
						tsRichiestaStampaExpDa = tsRichiestaStampaExpDa.compareTo(estremiDataRichiestaStampaExp[0]) < 0 ? estremiDataRichiestaStampaExp[0]
								: tsRichiestaStampaExpDa;
					} else {
						tsRichiestaStampaExpDa = estremiDataRichiestaStampaExp[0];
					}
					if (tsRichiestaStampaExpA != null) {
						tsRichiestaStampaExpA = tsRichiestaStampaExpA.compareTo(estremiDataRichiestaStampaExp[1]) > 0 ? estremiDataRichiestaStampaExp[1]
								: tsRichiestaStampaExpA;
					} else {
						tsRichiestaStampaExpA = estremiDataRichiestaStampaExp[1];
					}
				} else if ("annoProt".equals(crit.getFieldName())) {
					String[] estremiAnnoProt = getNumberFilterValue(crit);
					annoProtDa = estremiAnnoProt[0] != null ? estremiAnnoProt[0] : null;
					annoProtA = estremiAnnoProt[1] != null ? estremiAnnoProt[1] : null;
				} else if ("nroProt".equals(crit.getFieldName())) {
					String[] estremiNroProt = getNumberFilterValue(crit);
					nroProtDa = estremiNroProt[0] != null ? estremiNroProt[0] : null;
					nroProtA = estremiNroProt[1] != null ? estremiNroProt[1] : null;
				} else if ("tipoProt".equals(crit.getFieldName())) {
					tipoProt = getTextFilterValue(crit);
				} else if ("tsRegistrazione".equals(crit.getFieldName())) {
					Date[] estremiDataReg = getDateEstesaFilterValue(crit);
					if (tsRegistrazioneDa != null) {
						tsRegistrazioneDa = tsRegistrazioneDa.compareTo(estremiDataReg[0]) < 0 ? estremiDataReg[0] : tsRegistrazioneDa;
					} else {
						tsRegistrazioneDa = estremiDataReg[0];
					}
					if (tsRegistrazioneA != null) {
						tsRegistrazioneA = tsRegistrazioneA.compareTo(estremiDataReg[1]) > 0 ? estremiDataReg[1] : tsRegistrazioneA;
					} else {
						tsRegistrazioneA = estremiDataReg[1];
					}
				}/* else if("flgSoloFolder".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
				 		flgUdFolder = new Boolean(String.valueOf(crit.getValue())) ? "F" : null;
				 	}
				}*/ else if ("tsAperturaFascicolo".equals(crit.getFieldName())) {
					Date[] estremiDataAperturaFascicolo = getDateEstesaFilterValue(crit);
					if (tsAperturaFascicoloDa != null) {
						tsAperturaFascicoloDa = tsAperturaFascicoloDa.compareTo(estremiDataAperturaFascicolo[0]) < 0 ? estremiDataAperturaFascicolo[0]
								: tsAperturaFascicoloDa;
					} else {
						tsAperturaFascicoloDa = estremiDataAperturaFascicolo[0];
					}
					if (tsAperturaFascicoloA != null) {
						tsAperturaFascicoloA = tsAperturaFascicoloA.compareTo(estremiDataAperturaFascicolo[1]) > 0 ? estremiDataAperturaFascicolo[1]
								: tsAperturaFascicoloA;
					} else {
						tsAperturaFascicoloA = estremiDataAperturaFascicolo[1];
					}
				} else if ("tsPresaInCarico".equals(crit.getFieldName())) {
					Date[] estremiDataPresaInCarico = getDateFilterValue(crit);
					if (presaInCaricoDal!= null) {
						presaInCaricoDal = presaInCaricoDal.compareTo(estremiDataPresaInCarico[0]) < 0 ? estremiDataPresaInCarico[0]
								: presaInCaricoDal;
					} else {
						presaInCaricoDal = estremiDataPresaInCarico[0];
					}
					if (presaInCaricoAl != null) {
						presaInCaricoAl = presaInCaricoAl.compareTo(estremiDataPresaInCarico[1]) > 0 ? estremiDataPresaInCarico[1]
								: presaInCaricoAl;
					} else {
						presaInCaricoAl = estremiDataPresaInCarico[1];
					}
				} else if ("tsChiusuraFascicolo".equals(crit.getFieldName())) {
					Date[] estremiDataChiusuraFascicolo = getDateFilterValue(crit);
					if (tsChiusuraFascicoloDa != null) {
						tsChiusuraFascicoloDa = tsChiusuraFascicoloDa.compareTo(estremiDataChiusuraFascicolo[0]) < 0 ? estremiDataChiusuraFascicolo[0]
								: tsChiusuraFascicoloDa;
					} else {
						tsChiusuraFascicoloDa = estremiDataChiusuraFascicolo[0];
					}
					if (tsChiusuraFascicoloA != null) {
						tsChiusuraFascicoloA = tsChiusuraFascicoloA.compareTo(estremiDataChiusuraFascicolo[1]) > 0 ? estremiDataChiusuraFascicolo[1]
								: tsChiusuraFascicoloA;
					} else {
						tsChiusuraFascicoloA = estremiDataChiusuraFascicolo[1];
					}
				} else if ("flgSoloDaLeggere".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgSoloDaLeggere = new Boolean(String.valueOf(crit.getValue())) ? "1" : null;
					}
				} else if ("tsInvio".equals(crit.getFieldName())) {
					Date[] estremiDataInvio = getDateFilterValue(crit);
					if (tsInvioDa != null) {
						tsInvioDa = tsInvioDa.compareTo(estremiDataInvio[0]) < 0 ? estremiDataInvio[0] : tsInvioDa;
					} else {
						tsInvioDa = estremiDataInvio[0];
					}
					if (tsInvioA != null) {
						tsInvioA = tsInvioA.compareTo(estremiDataInvio[1]) > 0 ? estremiDataInvio[1] : tsInvioA;
					} else {
						tsInvioA = estremiDataInvio[1];
					}
				} else if ("destinatarioInvio".equals(crit.getFieldName())) {
					inviatiA = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("tsArchiviazione".equals(crit.getFieldName())) {
					Date[] estremiDataArchiviazione = getDateFilterValue(crit);
					if (tsArchiviazioneDa != null) {
						tsArchiviazioneDa = tsArchiviazioneDa.compareTo(estremiDataArchiviazione[0]) < 0 ? estremiDataArchiviazione[0] : tsArchiviazioneDa;
					} else {
						tsArchiviazioneDa = estremiDataArchiviazione[0];
					}
					if (tsArchiviazioneA != null) {
						tsArchiviazioneA = tsArchiviazioneA.compareTo(estremiDataArchiviazione[1]) > 0 ? estremiDataArchiviazione[1] : tsArchiviazioneA;
					} else {
						tsArchiviazioneA = estremiDataArchiviazione[1];
					}
				} else if ("tsEliminazione".equals(crit.getFieldName())) {
					Date[] estremiDataEliminazione = getDateFilterValue(crit);
					if (tsEliminazioneDa != null) {
						tsEliminazioneDa = tsEliminazioneDa.compareTo(estremiDataEliminazione[0]) < 0 ? estremiDataEliminazione[0] : tsEliminazioneDa;
					} else {
						tsEliminazioneDa = estremiDataEliminazione[0];
					}
					if (tsEliminazioneA != null) {
						tsEliminazioneA = tsEliminazioneA.compareTo(estremiDataEliminazione[1]) > 0 ? estremiDataEliminazione[1] : tsEliminazioneA;
					} else {
						tsEliminazioneA = estremiDataEliminazione[1];
					}
				} else if ("sezioneEliminazioneDoc".equals(crit.getFieldName()) || "sezioneEliminazioneFasc".equals(crit.getFieldName())) {
					sezioneEliminazione = getTextFilterValue(crit);
				} else if ("statoRichAnnullamento".equals(crit.getFieldName())) {
					statoRichAnnullamento = getTextFilterValue(crit);
				} else if ("statoAutorizzazione".equals(crit.getFieldName())) {
					statoAutorizzazione = getTextFilterValue(crit);
				} else if ("tsAssegnazione".equals(crit.getFieldName())) {
					Date[] estremiDataAssegnazione = getDateFilterValue(crit);
					if (tsAssegnazioneDa != null) {
						tsAssegnazioneDa = tsAssegnazioneDa.compareTo(estremiDataAssegnazione[0]) < 0 ? estremiDataAssegnazione[0] : tsAssegnazioneDa;
					} else {
						tsAssegnazioneDa = estremiDataAssegnazione[0];
					}
					if (tsAssegnazioneA != null) {
						tsAssegnazioneA = tsAssegnazioneA.compareTo(estremiDataAssegnazione[1]) > 0 ? estremiDataAssegnazione[1] : tsAssegnazioneA;
					} else {
						tsAssegnazioneA = estremiDataAssegnazione[1];
					}
				} else if ("assegnatoA".equals(crit.getFieldName())) {
					assegnatiA = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("tsNotificaCC".equals(crit.getFieldName()) || "tsNotificaNA".equals(crit.getFieldName())) {
					Date[] estremiDataNotifica = getDateFilterValue(crit);
					if (tsInvioNotificaDa != null) {
						tsInvioNotificaDa = tsInvioNotificaDa.compareTo(estremiDataNotifica[0]) < 0 ? estremiDataNotifica[0] : tsInvioNotificaDa;
					} else {
						tsInvioNotificaDa = estremiDataNotifica[0];
					}
					if (tsInvioNotificaA != null) {
						tsInvioNotificaA = tsInvioNotificaA.compareTo(estremiDataNotifica[1]) > 0 ? estremiDataNotifica[1] : tsInvioNotificaA;
					} else {
						tsInvioNotificaA = estremiDataNotifica[1];
					}
				} else if ("inviatoA".equals(crit.getFieldName())) {
					inConoscenzaA = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("notificatoA".equals(crit.getFieldName())) {
					conNotificheAutoA = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("soloRegAnnullate".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						soloRegAnnullate = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}
				} else if ("mittente".equals(crit.getFieldName())) {
					mittente = getValueStringaFullTextMista(crit);
					operMittente = getOperatorStringaFullTextMista(crit);
				} else if ("mittenteInRubrica".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						idRubricaMitt = (String) map.get("id");
					}
				} else if ("destinatario".equals(crit.getFieldName())) {
					destinatario = getValueStringaFullTextMista(crit);
					operDestinatario = getOperatorStringaFullTextMista(crit);
				} else if ("destinatarioInRubrica".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						idRubricaDest = (String) map.get("id");
					}
				} else if ("esibente".equals(crit.getFieldName())) {
					esibente = getValueStringaFullTextMista(crit);
					operEsibente = getOperatorStringaFullTextMista(crit);
				} else if ("esibenteInRubrica".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						idRubricaEsib = (String) map.get("id");
					}
				} else if ("oggetto".equals(crit.getFieldName())) {
					oggetto = getTextFilterValue(crit);
				} else if ("nomeFascicolo".equals(crit.getFieldName())) {
					nomeFascicolo = getTextFilterValue(crit);
				} else if ("regEffettuataDa".equals(crit.getFieldName())) {
					regEffettuataDa = getTextFilterValue(crit);
				} else if ("fileAssociati".equals(crit.getFieldName())) {
					fileAssociati = getTextFilterValue(crit);
				} else if ("altraNumerazioneSigla".equals(crit.getFieldName())) {
					altraNumerazioneSigla = getTextFilterValue(crit);
				} else if ("altraNumerazioneSiglaArchivioLista".equals(crit.getFieldName())) {
					altraNumerazioneSigla = getTextFilterValue(crit);
				} else if ("altraNumerazioneNr".equals(crit.getFieldName())) {
					String[] estremiAnnoStampa = getNumberFilterValue(crit);
					altraNumerazioneNrDa = estremiAnnoStampa[0] != null ? estremiAnnoStampa[0] : null;
					altraNumerazioneNrA = estremiAnnoStampa[1] != null ? estremiAnnoStampa[1] : null;
				} else if ("altraNumerazioneAnno".equals(crit.getFieldName())) {
					String[] estremiAnnoBozza = getNumberFilterValue(crit);
					altraNumerazioneAnnoDa = estremiAnnoBozza[0] != null ? estremiAnnoBozza[0] : null;
					altraNumerazioneAnnoA = estremiAnnoBozza[1] != null ? estremiAnnoBozza[1] : null;
				} else if ("altraNumerazioneData".equals(crit.getFieldName())) {
					Date[] estremiDataRegistrazione = getDateEstesaFilterValue(crit);
					if (altraNumerazioneDataDa != null) {
						altraNumerazioneDataDa = tsStampaDa.compareTo(estremiDataRegistrazione[0]) < 0 ? estremiDataRegistrazione[0] : tsStampaDa;
					} else {
						altraNumerazioneDataDa = estremiDataRegistrazione[0];
					}
					if (altraNumerazioneDataA != null) {
						altraNumerazioneDataA = tsStampaA.compareTo(estremiDataRegistrazione[1]) > 0 ? estremiDataRegistrazione[1] : tsStampaA;
					} else {
						altraNumerazioneDataA = estremiDataRegistrazione[1];
					}
				} else if ("mezzoTrasmissione".equals(crit.getFieldName())) {
					mezzoTrasmissioneFilter = getTextFilterValue(crit);
				}
				// FILTRI MODULO ATTI
				else if ("uoProponente".equals(crit.getFieldName())) {
					uoProponente = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("utentiAvvioAtto".equals(crit.getFieldName())) {
					utentiAvvioAtto = getTextFilterValue(crit);
				} else if ("utentiAdozioneAtto".equals(crit.getFieldName())) {
					utentiAdozioneAtto = getTextFilterValue(crit);
				} else if ("noteUd".equals(crit.getFieldName())) {
					noteUd = getTextFilterValue(crit);
				} else if ("statiDoc".equals(crit.getFieldName())) {
					statiDoc = getTextFilterValue(crit);
				} else if("statiFolder".equals(crit.getFieldName())) {
					statiFolder = getTextFilterValue(crit);
				} else if ("dataFirmaAtto".equals(crit.getFieldName())) {
					Date[] estremiDataFirmaAtto = getDateFilterValue(crit);
					if (dataFirmaAttoDa != null) {
						dataFirmaAttoDa = dataFirmaAttoDa.compareTo(estremiDataFirmaAtto[0]) < 0 ? estremiDataFirmaAtto[0] : dataFirmaAttoDa;
					} else {
						dataFirmaAttoDa = estremiDataFirmaAtto[0];
					}
					if (dataFirmaAttoA != null) {
						dataFirmaAttoA = dataFirmaAttoA.compareTo(estremiDataFirmaAtto[1]) > 0 ? estremiDataFirmaAtto[1] : dataFirmaAttoA;
					} else {
						dataFirmaAttoA = estremiDataFirmaAtto[1];
					}
				} else if ("tipoDoc".equals(crit.getFieldName())) {
					// tipoDoc = getTextFilterValue(crit);
					tipoDoc = getTipoAttributiCustomDelTipoFilter(crit);
					criteriaAttributiCustomDoc = getCriteriaAttributiCustomDelTipoFilter(crit);
					mappaTipiAttributiCustomDoc = getMappaTipiAttributiCustomDelTipoFilter(crit);
				} else if ("supportoProt".equals(crit.getFieldName())) {
					supportoProt = getTextFilterValue(crit);
				} else if("uoRegistrazione".equals(crit.getFieldName())) {
					uoRegistrazione = getListaSceltaOrganigrammaFilterValue(crit);
				} else if("uoApertura".equals(crit.getFieldName())) {
					uoApertura = getListaSceltaOrganigrammaFilterValue(crit);
				} else if ("tipoFolder".equals(crit.getFieldName())) {
					tipoFolder = getTipoAttributiCustomDelTipoFilter(crit);
					criteriaAttributiCustomFolder = getCriteriaAttributiCustomDelTipoFilter(crit);
					mappaTipiAttributiCustomFolder = getMappaTipiAttributiCustomDelTipoFilter(crit);
				} else if ("nroProtRicevuto".equals(crit.getFieldName())) {
					nroProtRicevuto = getTextFilterValue(crit);
				} else if ("dataProtRicevuto".equals(crit.getFieldName())) {
					Date[] estremiDataProtRicevuto = getDateFilterValue(crit);
					if (dataProtRicevutoDa != null) {
						dataProtRicevutoDa = dataProtRicevutoDa.compareTo(estremiDataProtRicevuto[0]) < 0 ? estremiDataProtRicevuto[0] : dataProtRicevutoDa;
					} else {
						dataProtRicevutoDa = estremiDataProtRicevuto[0];
					}
					if (dataProtRicevutoA != null) {
						dataProtRicevutoA = dataProtRicevutoA.compareTo(estremiDataProtRicevuto[1]) > 0 ? estremiDataProtRicevuto[1] : dataProtRicevutoA;
					} else {
						dataProtRicevutoA = estremiDataProtRicevuto[1];
					}
				} else if ("statoTrasmissioneEmail".equals(crit.getFieldName())) {
					statoTrasmissioneEmail = getTextFilterValue(crit);
				} else if ("capoFilaFasc".equals(crit.getFieldName())) {
					capoFilaFasc = getTextFilterValue(crit);
				} else if ("registroAltriRifTipo".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("REGISTRO_TIPO_RIF_UD", crit));
				} else if ("registroAltriRifNro".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("NRO_RIF_UD", crit));
				} else if ("registroAltriRifData".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("DATA_RIF_UD", crit, TipoFiltro.DATA_SENZA_ORA));
				} else if ("registroAltriRifAnno".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("ANNO_RIF_UD", crit, TipoFiltro.NUMERO));
				} else if ("protocolloCapofila".equals(crit.getFieldName())) {
					protocolloCapofila = getTextFilterValue(crit);
				} else if ("docCollegatiProt".equals(crit.getFieldName())) {
					docCollegatiProt = getTextFilterValue(crit);
				} else if ("idIndirizzoInVario".equals(crit.getFieldName())) {
					Map map = (Map) crit.getValue();
					idIndirizzoInVario = (String) map.get("id");
				} else if ("indirizzo".equals(crit.getFieldName())) {
					indirizzo = getTextFilterValue(crit);
				} else if ("sub".equals(crit.getFieldName())) {
					String[] estremiSub = getNumberFilterValue(crit);
					subDa = estremiSub[0] != null ? estremiSub[0] : null;
					subA = estremiSub[1] != null ? estremiSub[1] : null;
				} else if ("sigla".equals(crit.getFieldName())) {
					sigla = getTextFilterValue(crit);
				} else if ("classificazioneArchivio".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						idClassificazione = (String) map.get("classifiche");
						livelliClassificazione = (String) map.get("livelliClassificazione");
						flgIncludiSottoClassifiche = new Boolean(String.valueOf(map.get("flgIncludiSottoClassifiche"))) ? "1" : "0";
					}
				} else if ("annoFascicolo".equals(crit.getFieldName())) {
						String[] estremiAnnoFascicolo = getNumberFilterValue(crit);
						annoFascicoloDa = estremiAnnoFascicolo[0] != null ? estremiAnnoFascicolo[0] : null;
						annoFascicoloA  = estremiAnnoFascicolo[1] != null ? estremiAnnoFascicolo[1] : null;
				} else if ("nroFascicolo".equals(crit.getFieldName())) {
					String[] estremiNroFascicolo = getNumberFilterValue(crit);
					nroFascicoloDa = estremiNroFascicolo[0] != null ? estremiNroFascicolo[0] : null;
					nroFascicoloA  = estremiNroFascicolo[1] != null ? estremiNroFascicolo[1] : null;
				} else if ("nroSottoFascicolo".equals(crit.getFieldName())) {
					String[] estremiNroSottoFascicolo = getNumberFilterValue(crit);
					nroSottoFascicoloDa = estremiNroSottoFascicolo[0] != null ? estremiNroSottoFascicolo[0] : null;
					nroSottoFascicoloA  = estremiNroSottoFascicolo[1] != null ? estremiNroSottoFascicolo[1] : null;
				} else if ("codiceFascicolo".equals(crit.getFieldName())) {
					codiceFascicolo = getTextFilterValue(crit);
				} else if("attoAutAnnullamento".equals(crit.getFieldName())) {
					attoAutAnnullamento = getTextFilterValue(crit);
				} else if("dtStesura".equals(crit.getFieldName())) {
					Date[] estremiDataStesura = getDateFilterValue(crit);
					if (dtStesuraDal != null) {
						dtStesuraDal = dtStesuraDal.compareTo(estremiDataStesura[0]) < 0 ? estremiDataStesura[0] : dtStesuraDal;
					} else {
						dtStesuraDal = estremiDataStesura[0];
					}
					if (dtStesuraAl != null) {
						dtStesuraAl = dtStesuraAl.compareTo(estremiDataStesura[1]) > 0 ? estremiDataStesura[1] : dtStesuraAl;
					} else {
						dtStesuraAl = estremiDataStesura[1];
					}
					
				} else if("docCollegatiNominativo".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						if((String) map.get("nominativo") !=null ){
							nominativoUD = (String) map.get("nominativo");
							operNominativoUD = getOperatorStringaFullTextMista(crit);	
						}
						if (map.get("tipiNominativi") instanceof ArrayList) {
							ArrayList<String>  lArrayList = (ArrayList<String>) map.get("tipiNominativi");
							tipiNominativoUD = StringUtils.join(lArrayList, ";");
						}
					}	
				} else if ("cig".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("CIG", crit));
				} else if ("perizia".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("COD_PERIZIA_ADSP", crit));
				} else if("centroDiCosto".equals(crit.getFieldName())) {
					centroDiCosto = getTextFilterValue(crit);
				} else if("dataScadenza".equals(crit.getFieldName())) {
					Date[] estremiDataScadenza = getDateFilterValue(crit);
					dataScadenzaDa = estremiDataScadenza[0];
					dataScadenzaA = estremiDataScadenza[1];
				} else if("cfNominativoCollegato".equals(crit.getFieldName())) {
					cfNominativoCollegato = getTextFilterValue(crit);			  
				} else if("flgRiservatezza".equals(crit.getFieldName())) {
					if(StringUtils.isNotBlank((String) crit.getValue())) {
						flgRiservatezza = String.valueOf(crit.getValue());
					}
				}					
				// PUBBLICAZIONE : data inizio
				else if ("dtInizioPubblicazione".equals(crit.getFieldName())) {
					Date[] estremiDataPubblicazioneInzio = getDateFilterValue(crit);
					if (dtInizioPubblicazioneDal != null) {
						dtInizioPubblicazioneDal = dtInizioPubblicazioneDal.compareTo(estremiDataPubblicazioneInzio[0]) < 0 ? estremiDataPubblicazioneInzio[0] : dtInizioPubblicazioneDal;
					} else {
						dtInizioPubblicazioneDal = estremiDataPubblicazioneInzio[0];
					}
					if (dtInizioPubblicazioneAl != null) {
						dtInizioPubblicazioneAl = dtInizioPubblicazioneAl.compareTo(estremiDataPubblicazioneInzio[1]) > 0 ? estremiDataPubblicazioneInzio[1] : dtInizioPubblicazioneAl;
					} else {
						dtInizioPubblicazioneAl = estremiDataPubblicazioneInzio[1];
					}
				}				
				// PUBBLICAZIONE : data termine
				else if ("dtTerminePubblicazione".equals(crit.getFieldName())) {
					Date[] estremiDataPubblicazioneTermine = getDateFilterValue(crit);
					if (dtTerminePubblicazioneDal != null) {
						dtTerminePubblicazioneDal = dtTerminePubblicazioneDal.compareTo(estremiDataPubblicazioneTermine[0]) < 0 ? estremiDataPubblicazioneTermine[0] : dtTerminePubblicazioneDal;
					} else {
						dtTerminePubblicazioneDal = estremiDataPubblicazioneTermine[0];
					}
					if (dtTerminePubblicazioneAl != null) {
						dtTerminePubblicazioneAl = dtTerminePubblicazioneAl.compareTo(estremiDataPubblicazioneTermine[1]) > 0 ? estremiDataPubblicazioneTermine[1] : dtTerminePubblicazioneAl;
					} else {
						dtTerminePubblicazioneAl = estremiDataPubblicazioneTermine[1];
					}
				}				
				// PUBBLICAZIONE : numero
				else if ("nroPubblicazione".equals(crit.getFieldName())) {
						String[] estremiNroPubblicazione = getNumberFilterValue(crit);
						nroPubblicazioneDa = estremiNroPubblicazione[0] != null ? estremiNroPubblicazione[0] : null;
						nroPubblicazioneA = estremiNroPubblicazione[1] != null ? estremiNroPubblicazione[1] : null;
				}
				// PUBBLICAZIONE : anno 
				else if ("annoPubblicazione".equals(crit.getFieldName())) {
					String[] estremiAnnoPubblicazione = getNumberFilterValue(crit);
					annoPubblicazioneDa = estremiAnnoPubblicazione[0] != null ? estremiAnnoPubblicazione[0] : null;
					annoPubblicazioneA = estremiAnnoPubblicazione[1] != null ? estremiAnnoPubblicazione[1] : null;
				}				
				// PUBBLICAZIONE : gg durata
				else if ("ggDurataPubblicazione".equals(crit.getFieldName())) {
					String[] estremiGGDurataPubblicazione = getNumberFilterValue(crit);
					ggDurataPubblicazioneDa = estremiGGDurataPubblicazione[0] != null ? estremiGGDurataPubblicazione[0] : null;
					ggDurataPubblicazioneA = estremiGGDurataPubblicazione[1] != null ? estremiGGDurataPubblicazione[1] : null;
				} 
				// PUBBLICAZIONE : UO richiesta
				else if("uoPubblicazione".equals(crit.getFieldName())) {
					uoPubblicazione = getListaSceltaOrganigrammaFilterValue(crit);
				} 							
				// PUBBLICAZIONE : effettuata da
				else if ("pubblicazioneEffettuataDa".equals(crit.getFieldName())) {
					pubblicazioneEffettuataDa = getTextFilterValue(crit);
				}				
				// PUBBLICAZIONE : annullata (si/no/null)
				else if ("pubblicazioneAnnullata".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						pubblicazioneAnnullata = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}
				}
				// PUBBLICAZIONE : rettificata (si/no/null)
				else if ("pubblicazioneRettificata".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						pubblicazioneRettificata = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
					}
				}				
				// PUBBLICAZIONE : stato
				else if ("statoPubblicazione".equals(crit.getFieldName())) {
					statoPubblicazione = getTextFilterValue(crit);
				}				
			}
		}

		if (criteriaAttributiCustomDoc != null && criteriaAttributiCustomDoc.getCriteria() != null) {
			for (Criterion crit : criteriaAttributiCustomDoc.getCriteria()) {
				String tipo = mappaTipiAttributiCustomDoc != null ? mappaTipiAttributiCustomDoc.get(crit.getFieldName()) : null;
				TipoFiltro tipoFiltro = CriteriPersonalizzatiUtil.getTipoFiltroFromTipoAttributoCustom(tipo);
				CriteriPersonalizzati criterioPersonalizzato = CriteriPersonalizzatiUtil.getCriterioPersonalizzato(crit.getFieldName(), crit, tipoFiltro);
				if(criterioPersonalizzato != null) {
					listCustomFilters.add(criterioPersonalizzato);
				}
			}
		}

		if (criteriaAttributiCustomFolder != null && criteriaAttributiCustomFolder.getCriteria() != null) {
			for (Criterion crit : criteriaAttributiCustomFolder.getCriteria()) {
				String tipo = mappaTipiAttributiCustomFolder != null ? mappaTipiAttributiCustomFolder.get(crit.getFieldName()) : null;
				TipoFiltro tipoFiltro = CriteriPersonalizzatiUtil.getTipoFiltroFromTipoAttributoCustom(tipo);
				CriteriPersonalizzati criterioPersonalizzato = CriteriPersonalizzatiUtil.getCriterioPersonalizzato(crit.getFieldName(), crit, tipoFiltro);
				if(criterioPersonalizzato != null) {
					listCustomFilters.add(criterioPersonalizzato);
				}
			}
		}

		boolean daProtocollare = false;
		CriteriAvanzati scCriteriAvanzati = new CriteriAvanzati();
		if (criteriAvanzati != null) {
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			scCriteriAvanzati = lXmlUtilityDeserializer.unbindXml(criteriAvanzati, CriteriAvanzati.class);
			if (scCriteriAvanzati.getNomeWorkspaceApp() != null && "<BOZZE>".equals(scCriteriAvanzati.getNomeWorkspaceApp())) {
				daProtocollare = true;
			}
			if (scCriteriAvanzati.getStatiDettDoc() != null && scCriteriAvanzati.getStatiDettDoc().size() > 0) {
				for (Stato stato : scCriteriAvanzati.getStatiDettDoc()) {
					if ("DP".equals(stato.getCodice())) {
						daProtocollare = true;
					}
				}
			}
		}
		
		scCriteriAvanzati.setIdNode(idNode);		

		// su tutti i filtri che sono variabili lista (es. @Registrazioni) il filtro caricato da DB deve andare in append con quello
		// eventuale a GUI relativo alla stessa variabile lista

		List<Registrazione> listaRegistrazioni = new ArrayList<Registrazione>();
		if (scCriteriAvanzati.getRegistrazioni() != null && scCriteriAvanzati.getRegistrazioni().size() > 0) {
			listaRegistrazioni = scCriteriAvanzati.getRegistrazioni();
		}

		if (!daProtocollare
				&& ((StringUtils.isNotBlank(annoProtDa)) || (StringUtils.isNotBlank(annoProtA)) || (StringUtils.isNotBlank(nroProtDa))
						|| (StringUtils.isNotBlank(nroProtA)) || (tsRegistrazioneDa != null) || (tsRegistrazioneA != null)
						|| (StringUtils.isNotBlank(subDa)) 
						|| (StringUtils.isNotBlank(subA)) || (StringUtils.isNotBlank(sigla)) )) {
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria("PG");
			registrazione.setSigla(sigla);
			registrazione.setAnno(null);
			registrazione.setNumeroDa(nroProtDa);
			registrazione.setNumeroA(nroProtA);
			registrazione.setDataDa(tsRegistrazioneDa);
			registrazione.setDataA(tsRegistrazioneA);
			registrazione.setAnnoDa(annoProtDa);
			registrazione.setAnnoA(annoProtA);
			registrazione.setEffettuataDa(regEffettuataDa);
			registrazione.setSubDa(subDa);
			registrazione.setSubA(subA);
			listaRegistrazioni.add(registrazione);
		}

		if ((StringUtils.isNotBlank(annoBozzaDa)) || (StringUtils.isNotBlank(annoBozzaA)) || (StringUtils.isNotBlank(nroBozzaDa))
				|| (StringUtils.isNotBlank(nroBozzaA)) || (tsBozzaDa != null) || (tsBozzaA != null)) {
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria("I");
			registrazione.setSigla("N.I.");
			registrazione.setAnno(null);
			registrazione.setNumeroDa(nroBozzaDa);
			registrazione.setNumeroA(nroBozzaA);
			registrazione.setDataDa(tsBozzaDa);
			registrazione.setDataA(tsBozzaA);
			registrazione.setAnnoDa(annoBozzaDa);
			registrazione.setAnnoA(annoBozzaA);
			registrazione.setEffettuataDa(regEffettuataDa);
			listaRegistrazioni.add(registrazione);
		}

		if ((StringUtils.isNotBlank(tipoDocStampa)) || (StringUtils.isNotBlank(annoStampaDa)) || (StringUtils.isNotBlank(annoStampaA))
				|| (StringUtils.isNotBlank(nroStampaDa)) || (StringUtils.isNotBlank(nroStampaA)) || (tsStampaDa != null) || (tsStampaA != null)) {
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria("I");
			registrazione.setSigla(tipoDocStampa);
			registrazione.setAnno(null);
			registrazione.setNumeroDa(nroStampaDa);
			registrazione.setNumeroA(nroStampaA);
			registrazione.setDataDa(tsStampaDa);
			registrazione.setDataA(tsStampaA);
			registrazione.setAnnoDa(annoStampaDa);
			registrazione.setAnnoA(annoStampaA);
			listaRegistrazioni.add(registrazione);
		}

		if ((StringUtils.isNotBlank(altraNumerazioneAnnoDa)) || (StringUtils.isNotBlank(altraNumerazioneSigla))
				|| (StringUtils.isNotBlank(altraNumerazioneAnnoA)) || (StringUtils.isNotBlank(altraNumerazioneNrDa))
				|| (StringUtils.isNotBlank(altraNumerazioneNrA)) || (altraNumerazioneDataDa != null) || (altraNumerazioneDataA != null)) {
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria("R;I;A");
			registrazione.setSigla(altraNumerazioneSigla);
			registrazione.setAnno(null);
			registrazione.setNumeroDa(altraNumerazioneNrDa);
			registrazione.setNumeroA(altraNumerazioneNrA);
			registrazione.setDataDa(altraNumerazioneDataDa);
			registrazione.setDataA(altraNumerazioneDataA);
			registrazione.setAnnoDa(altraNumerazioneAnnoDa);
			registrazione.setAnnoA(altraNumerazioneAnnoA);
			registrazione.setEffettuataDa(regEffettuataDa);
			listaRegistrazioni.add(registrazione);
		}
				
		
		
		
		
		// Se il filtro "Registrazione effettuata da" e' valorizzato verifico se ' presente nell'ArrayList
		if(regEffettuataDa!=null){
			boolean regEffettuataDaExist = false;
			if(listaRegistrazioni!=null && listaRegistrazioni.size()>0){
				for (int i = 0; i < listaRegistrazioni.size(); i++) {
					if (listaRegistrazioni.get(i) != null && 
						listaRegistrazioni.get(i).getEffettuataDa() !=null && 
						!listaRegistrazioni.get(i).getEffettuataDa().equalsIgnoreCase("")
							) {
						regEffettuataDaExist = true;
					}
				}
			}
			
			// Se non e' presente lo aggiugo
			if(!regEffettuataDaExist){
				Registrazione registrazione = new Registrazione();
				registrazione.setEffettuataDa(regEffettuataDa);
				listaRegistrazioni.add(registrazione);
			}				
		}
		
		
		scCriteriAvanzati.setRegistrazioni(listaRegistrazioni);
		
		List<SupportiOrigDoc> listaSupportiOrigDoc = new ArrayList<SupportiOrigDoc>();
		if ((StringUtils.isNotBlank(supportoProt))) {
			StringSplitterServer supportoProtST = new StringSplitterServer(supportoProt, ";");
			while (supportoProtST.hasMoreElements()) {
				SupportiOrigDoc supportoOrigDoc = new SupportiOrigDoc();
				supportoOrigDoc.setCodSupporto(supportoProtST.nextToken());
				listaSupportiOrigDoc.add(supportoOrigDoc);
			} 
			scCriteriAvanzati.setSupportiOrigDoc(listaSupportiOrigDoc);
		}

		// su tutti i filtri che sono variabili semplici (es. DataAperturaDa e DataAperturaA) il filtro caricato da DB dovrebbe essere settato
		// a meno che il filtro corrispondente nella GUI non sia impostato, nel qual caso predomina quello e sovrascrive il filtro da DB

		if (tsAperturaFascicoloDa != null) {
			scCriteriAvanzati.setDataAperturaDa(tsAperturaFascicoloDa);
		}

		if (tsAperturaFascicoloA != null) {
			scCriteriAvanzati.setDataAperturaA(tsAperturaFascicoloA);
		}

		if (tsChiusuraFascicoloDa != null) {
			scCriteriAvanzati.setDataChiusuraDa(tsChiusuraFascicoloDa);
		}

		if (tsChiusuraFascicoloA != null) {
			scCriteriAvanzati.setDataChiusuraA(tsChiusuraFascicoloA);
		}

		if (StringUtils.isNotBlank(flgSoloDaLeggere)) {
			scCriteriAvanzati.setSoloDaLeggere(flgSoloDaLeggere);
		}

		if (tsInvioDa != null) {
			scCriteriAvanzati.setDataInvioDa(tsInvioDa);
		}

		if (tsInvioA != null) {
			scCriteriAvanzati.setDataInvioA(tsInvioA);
		}

		if (inviatiA != null) {
			scCriteriAvanzati.setInviatiA(inviatiA);
		}

		if (tsArchiviazioneDa != null) {
			scCriteriAvanzati.setDataArchiviazioneDa(tsArchiviazioneDa);
		}

		if (tsArchiviazioneA != null) {
			scCriteriAvanzati.setDataArchiviazioneA(tsArchiviazioneA);
		}

		if (tsEliminazioneDa != null) {
			scCriteriAvanzati.setDataEliminazioneDa(tsEliminazioneDa);
		}

		if (tsEliminazioneA != null) {
			scCriteriAvanzati.setDataEliminazioneA(tsEliminazioneA);
		}

		if (StringUtils.isNotBlank(sezioneEliminazione)) {
			scCriteriAvanzati.setSezioneEliminazione(sezioneEliminazione);
		}

		if (StringUtils.isNotBlank(soloDocRicevutiViaEmail)) {
			scCriteriAvanzati.setSoloDocRicevutiViaEmail(soloDocRicevutiViaEmail);
		}

		if (StringUtils.isNotBlank(inviatiViaEmail)) {
			scCriteriAvanzati.setInviatiViaEmail(inviatiViaEmail);
		}

		if (StringUtils.isNotBlank(statoPresaInCarico)) {
			scCriteriAvanzati.setStatoPresaInCarico(statoPresaInCarico);
		}
		
		if (StringUtils.isNotBlank(flgAppostoTimbro)) {
			scCriteriAvanzati.setArchiviatoFileTimbrato(flgAppostoTimbro);
		}

		if (StringUtils.isNotBlank(statoRichAnnullamento)) {
			List<StatoRichAnnullamentoReg> statiRichiestaAnnullamentoReg = new ArrayList<StatoRichAnnullamentoReg>();
			StringSplitterServer st = new StringSplitterServer(statoRichAnnullamento, ";");
			while (st.hasMoreTokens()) {
				StatoRichAnnullamentoReg statoRichAnnullamentoReg = new StatoRichAnnullamentoReg();
				statoRichAnnullamentoReg.setStato(st.nextToken());
				statiRichiestaAnnullamentoReg.add(statoRichAnnullamentoReg);
			}
			scCriteriAvanzati.setStatiRichiestaAnnullamentoReg(statiRichiestaAnnullamentoReg);
		}

		if (StringUtils.isNotBlank(statoAutorizzazione)) {
			List<StatoRichAnnullamentoReg> statiRichiestaAnnullamentoReg = new ArrayList<StatoRichAnnullamentoReg>();
			StatoRichAnnullamentoReg statoRichAnnullamentoReg = new StatoRichAnnullamentoReg();
			statoRichAnnullamentoReg.setStato(statoAutorizzazione);
			statiRichiestaAnnullamentoReg.add(statoRichAnnullamentoReg);
			scCriteriAvanzati.setStatiRichiestaAnnullamentoReg(statiRichiestaAnnullamentoReg);
		}

		if (tsAssegnazioneDa != null) {
			scCriteriAvanzati.setDataAssegnSmistDa(tsAssegnazioneDa);
		}

		if (tsAssegnazioneA != null) {
			scCriteriAvanzati.setDataAssegnSmistA(tsAssegnazioneA);
		}

		if (assegnatiA != null) {
			scCriteriAvanzati.setAssegnatiA(assegnatiA);
		}

		if (tsInvioNotificaDa != null) {
			scCriteriAvanzati.setDataNotificaDa(tsInvioNotificaDa);
		}

		if (tsInvioNotificaA != null) {
			scCriteriAvanzati.setDataNotificaA(tsInvioNotificaA);
		}

		if (inConoscenzaA != null) {
			scCriteriAvanzati.setInConoscenzaA(inConoscenzaA);
		}

		if (conNotificheAutoA != null) {
			scCriteriAvanzati.setConNotificheAutoA(conNotificheAutoA);
		}

		if (StringUtils.isNotBlank(soloRegAnnullate)) {
			scCriteriAvanzati.setSoloRegAnnullate(soloRegAnnullate);
		}

		if (StringUtils.isNotBlank(tipoProt)) {
			scCriteriAvanzati.setFlagTipoProvDoc(tipoProt);
		}

		if (StringUtils.isNotBlank(restringiSottofascInserti)) {
			scCriteriAvanzati.setRestringiSottofascInserti(restringiSottofascInserti);
		}

		if (StringUtils.isNotBlank(interesseCessato)) {
			scCriteriAvanzati.setInteresseCessato(interesseCessato);
		}

		if (StringUtils.isNotBlank(mittente)) {
			scCriteriAvanzati.setMittenteUD(mittente);
		}

		if (StringUtils.isNotBlank(operMittente)) {
			scCriteriAvanzati.setOperMittenteUD(operMittente);
		}

		if (StringUtils.isNotBlank(idRubricaMitt)) {
			scCriteriAvanzati.setIdMittenteUDInRubrica(idRubricaMitt);
		}

		if (StringUtils.isNotBlank(destinatario)) {
			scCriteriAvanzati.setDestinatarioUD(destinatario);
		}

		if (StringUtils.isNotBlank(operDestinatario)) {
			scCriteriAvanzati.setOperDestinatarioUD(operDestinatario);
		}

		if (StringUtils.isNotBlank(idRubricaDest)) {
			scCriteriAvanzati.setIdDestinatarioUDInRubrica(idRubricaDest);
		}
		
		if (StringUtils.isNotBlank(esibente)) {
			scCriteriAvanzati.setEsibenteUD(esibente);
		}
		
		if (StringUtils.isNotBlank(operEsibente)) {
			scCriteriAvanzati.setOperEsibenteUD(operEsibente);
		}
		
		if (StringUtils.isNotBlank(idRubricaEsib)) {
			scCriteriAvanzati.setIdEsibenteUDInRubrica(idRubricaEsib);
		}

		if (StringUtils.isNotBlank(oggetto)) {
			scCriteriAvanzati.setOggettoUD(oggetto);
		}

		if (StringUtils.isNotBlank(nomeFascicolo)) {
			scCriteriAvanzati.setNomeFolder(nomeFascicolo);
		}

		if (StringUtils.isNotBlank(fileAssociati)) {
			scCriteriAvanzati.setPresenzaFile(fileAssociati);
		}

		if (StringUtils.isNotBlank(mezzoTrasmissioneFilter)) {
			List<MezziTrasmissioneFilterBean> listMezzoTrasmissione = new ArrayList<MezziTrasmissioneFilterBean>();
			StringSplitterServer st = new StringSplitterServer(mezzoTrasmissioneFilter, ";");
			while (st.hasMoreTokens()) {
				MezziTrasmissioneFilterBean lMezziTrasmissioneFilter = new MezziTrasmissioneFilterBean();
				String chiave = st.nextToken();
				lMezziTrasmissioneFilter.setIdMezzoTrasmissione(chiave);
				listMezzoTrasmissione.add(lMezziTrasmissioneFilter);
			}
			scCriteriAvanzati.setMezziTrasmissione(listMezzoTrasmissione);
		}

		if (uoProponente != null) {
			List<UoProponente> listaUoProponente = new ArrayList<UoProponente>();
			for (DestInvioNotifica item : uoProponente) {
				UoProponente lUoProponente = new UoProponente();
				lUoProponente.setIdUo(item.getId());
				lUoProponente.setFlgIncludiSottoUo(item.getFlgIncludiSottoUO() == Flag.SETTED ? Flag.SETTED : Flag.NOT_SETTED);
				listaUoProponente.add(lUoProponente);
			}
			scCriteriAvanzati.setUoProponente(listaUoProponente);
		}
		
		if (uoRegistrazione != null) { 
			List<UoRegistrazione> listaUoRegistrazione = new ArrayList<UoRegistrazione>();
			for (DestInvioNotifica item : uoRegistrazione) {
				UoRegistrazione lUoRegistrazione = new UoRegistrazione();
				lUoRegistrazione.setIdUo(item.getId());
				lUoRegistrazione.setFlgIncludiSottoUo(item.getFlgIncludiSottoUO() == Flag.SETTED ? Flag.SETTED : Flag.NOT_SETTED);
				listaUoRegistrazione.add(lUoRegistrazione);
			}
			scCriteriAvanzati.setUoRegistrazione(listaUoRegistrazione);
		}
		
		if (uoApertura != null) { 
			List<UoApertura> listaUoApertura = new ArrayList<UoApertura>();
			for (DestInvioNotifica item : uoApertura) {
				UoApertura lUoApertura = new UoApertura();
				lUoApertura.setIdUo(item.getId());
				lUoApertura.setFlgIncludiSottoUo(item.getFlgIncludiSottoUO() == Flag.SETTED ? Flag.SETTED : Flag.NOT_SETTED);
				listaUoApertura.add(lUoApertura);
			}
			scCriteriAvanzati.setUoApertura(listaUoApertura);
		}

		if (StringUtils.isNotBlank(utentiAvvioAtto)) {
			List<Utente> listaUtentiAvvioAtto = new ArrayList<Utente>();
			StringSplitterServer st = new StringSplitterServer(utentiAvvioAtto, ";");
			while (st.hasMoreTokens()) {
				Utente lUtente = new Utente();
				lUtente.setIdUser(st.nextToken());
				listaUtentiAvvioAtto.add(lUtente);
			}
			scCriteriAvanzati.setUtentiAvvioAtto(listaUtentiAvvioAtto);
		}

		if (StringUtils.isNotBlank(utentiAdozioneAtto)) {
			List<Utente> listaUtentiAdozioneAtto = new ArrayList<Utente>();
			StringSplitterServer st = new StringSplitterServer(utentiAdozioneAtto, ";");
			while (st.hasMoreTokens()) {
				Utente lUtente = new Utente();
				lUtente.setIdUser(st.nextToken());
				listaUtentiAdozioneAtto.add(lUtente);
			}
			scCriteriAvanzati.setUtentiAdozioneAtto(listaUtentiAdozioneAtto);
		}

		if (StringUtils.isNotBlank(noteUd)) {
			scCriteriAvanzati.setNoteUd(noteUd);
		}

		if (StringUtils.isNotBlank(statiDoc)) {
			List<Stato> listaStatiDoc = new ArrayList<Stato>();
			StringSplitterServer st = new StringSplitterServer(statiDoc, ";");
			while (st.hasMoreTokens()) {
				Stato lStato = new Stato();
				lStato.setCodice(st.nextToken());
				listaStatiDoc.add(lStato);
			}
			scCriteriAvanzati.setStatiDettDoc(listaStatiDoc);
		}
		
		if (StringUtils.isNotBlank(statiFolder)) {
			List<Stato> listaStatiFolder = new ArrayList<Stato>();
			StringSplitterServer st = new StringSplitterServer(statiFolder, ";");
			while (st.hasMoreTokens()) {
				Stato lStato = new Stato();
				lStato.setCodice(st.nextToken());
				listaStatiFolder.add(lStato);
			}
			scCriteriAvanzati.setStatiDettFolder(listaStatiFolder);
		}

		if (dataFirmaAttoDa != null) {
			scCriteriAvanzati.setDataFirmaAttoDa(dataFirmaAttoDa);
		}

		if (dataFirmaAttoA != null) {
			scCriteriAvanzati.setDataFirmaAttoA(dataFirmaAttoA);
		}
		
		if (presaInCaricoDal != null) {
			scCriteriAvanzati.setPresaInCaricoDal(presaInCaricoDal);
		}
		
		if (presaInCaricoAl != null) {
			scCriteriAvanzati.setPresaInCaricoAl(presaInCaricoAl);
		}

		if (StringUtils.isNotBlank(tipoDoc)) {
			scCriteriAvanzati.setIdDocType(tipoDoc);
		}

		if (StringUtils.isNotBlank(tipoFolder)) {
			scCriteriAvanzati.setIdFolderType(tipoFolder);
		}

		if (StringUtils.isNotBlank(nroProtRicevuto)) {
			scCriteriAvanzati.setNroProtRicevuto(nroProtRicevuto);
		}

		if (dataProtRicevutoDa != null) {
			scCriteriAvanzati.setDataProtRicevutoDa(dataProtRicevutoDa);
		}

		if (statoTrasmissioneEmail != null) {
			scCriteriAvanzati.setStatoTrasmissioneEmail(statoTrasmissioneEmail);
		}

		if (StringUtils.isNotBlank(nroRichiestaStampaExpDa)) {
			scCriteriAvanzati.setNroRichiestaStampaExpDa(nroRichiestaStampaExpDa);
		}

		if (StringUtils.isNotBlank(nroRichiestaStampaExpA)) {
			scCriteriAvanzati.setNroRichiestaStampaExpA(nroRichiestaStampaExpA);
		}

		if (tsRichiestaStampaExpDa != null) {
			scCriteriAvanzati.setTsRichiestaStampaExpDa(tsRichiestaStampaExpDa);
		}

		if (tsRichiestaStampaExpA != null) {
			scCriteriAvanzati.setTsRichiestaStampaExpA(tsRichiestaStampaExpA);
		}

		if (capoFilaFasc != null) {
			scCriteriAvanzati.setCapoFilaFasc(capoFilaFasc);
		}
		if (protocolloCapofila != null) {
			scCriteriAvanzati.setProtocolloCapofila(protocolloCapofila);
		}
		if (docCollegatiProt != null) {
			scCriteriAvanzati.setProtocolloCollegato(docCollegatiProt);
		}		
		if (idIndirizzoInVario != null) {
			scCriteriAvanzati.setIdIndirizzoInVario(idIndirizzoInVario);
		}
		if (indirizzo != null) {
			scCriteriAvanzati.setIndirizzo(indirizzo);
		}
		
		if (dtRaccomandataAl != null) {
			scCriteriAvanzati.setDtRaccomandataAl(dtRaccomandataAl);
		}

		if (dtRaccomandataDal != null) {
			scCriteriAvanzati.setDtRaccomandataDal(dtRaccomandataDal);
		}
	
		if (StringUtils.isNotBlank(nroRaccomandata)) {
			scCriteriAvanzati.setNroRaccomandata(nroRaccomandata);
		}
		
		if (StringUtils.isNotBlank(idClassificazione)) {
			scCriteriAvanzati.setIdClassificazione(idClassificazione);
		}
		if (StringUtils.isNotBlank(livelliClassificazione)) {
			scCriteriAvanzati.setLivelliClassificazione(livelliClassificazione);
		}
		if (StringUtils.isNotBlank(flgIncludiSottoClassifiche)) {
			scCriteriAvanzati.setFlgIncludiSottoClassifiche(flgIncludiSottoClassifiche);
		}
		if (StringUtils.isNotBlank(annoFascicoloDa)) {
			scCriteriAvanzati.setAnnoFascicoloDa(annoFascicoloDa);
		}
		if (StringUtils.isNotBlank(annoFascicoloA)) {
			scCriteriAvanzati.setAnnoFascicoloA(annoFascicoloA);
		}
		if (StringUtils.isNotBlank(nroFascicoloDa)) {
			scCriteriAvanzati.setNroFascicoloDa(nroFascicoloDa);
		}
		if (StringUtils.isNotBlank(nroFascicoloA)) {
			scCriteriAvanzati.setNroFascicoloA(nroFascicoloA);
		}
		if (StringUtils.isNotBlank(nroSottoFascicoloDa)) {
			scCriteriAvanzati.setNroSottoFascicoloDa(nroSottoFascicoloDa);
		}
		if (StringUtils.isNotBlank(nroSottoFascicoloA)) {
			scCriteriAvanzati.setNroSottoFascicoloA(nroSottoFascicoloA);
		}
		if (StringUtils.isNotBlank(codiceFascicolo)) {
			scCriteriAvanzati.setCodiceFascicolo(codiceFascicolo);
		}
		if (StringUtils.isNotBlank(attoAutAnnullamento)) {
			scCriteriAvanzati.setEstremiAttoAutAnnullamento(attoAutAnnullamento);
		}
		
		if (dtStesuraDal != null) {
			scCriteriAvanzati.setDtStesuraDal(dtStesuraDal);
		}
	
		if (dtStesuraAl != null) {
			scCriteriAvanzati.setDtStesuraAl(dtStesuraAl);
		}

		if (StringUtils.isNotBlank(nominativoUD)) {
			scCriteriAvanzati.setNominativoUD(nominativoUD);
		}

		if (StringUtils.isNotBlank(operNominativoUD)) {
			scCriteriAvanzati.setOperNominativoUD(operNominativoUD);
		}
		
		if (StringUtils.isNotBlank(tipiNominativoUD)) {
			scCriteriAvanzati.setTipiNominativoUD(tipiNominativoUD);
		}
		
		if (StringUtils.isNotBlank(centroDiCosto)) {
			scCriteriAvanzati.setCentroDiCosto(centroDiCosto);
		}
		
		if (dataScadenzaDa != null) {
			scCriteriAvanzati.setDataScadenzaDa(dataScadenzaDa);
		}
	
		if (dataScadenzaA != null) {
			scCriteriAvanzati.setDataScadenzaA(dataScadenzaA);
		}
		
		if (cfNominativoCollegato != null) {
			scCriteriAvanzati.setCfNominativoCollegato(cfNominativoCollegato);
		}
		
		if (flgRiservatezza != null) {
			scCriteriAvanzati.setFlgRiservatezza(flgRiservatezza);
		}
				
		// N.B. metto sempre una riga con CATEGORIA = "PUBBL" cosi' la store capisce che e' chiamata dalla lista delle pubblicazioni.
		
			
			// PUBBLICAZIONE : anno,numero, effettuata da
			if (scCriteriAvanzati.getRegistrazioni() != null && scCriteriAvanzati.getRegistrazioni().size() > 0) {
				listaRegistrazioni = scCriteriAvanzati.getRegistrazioni();
			}
				
			
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria("PUBBL");
			
			if ( (StringUtils.isNotBlank(annoPubblicazioneDa))      || 
					 (StringUtils.isNotBlank(annoPubblicazioneA))       ||
						 
					 (StringUtils.isNotBlank(nroPubblicazioneDa))       || 
					 (StringUtils.isNotBlank(nroPubblicazioneA))        || 
						 
					 (StringUtils.isNotBlank(pubblicazioneEffettuataDa))
				   ) 
					{			
						registrazione.setSigla(sigla);
						registrazione.setAnno(null);
						
						registrazione.setNumeroDa(nroPubblicazioneDa);
						registrazione.setNumeroA(nroPubblicazioneA);
						
						registrazione.setAnnoDa(annoPubblicazioneDa);
						registrazione.setAnnoA(annoPubblicazioneA);

						registrazione.setEffettuataDa(pubblicazioneEffettuataDa);
						
				}
				listaRegistrazioni.add(registrazione);
		
		
		// PUBBLICAZIONE : data inizio
		if (dtInizioPubblicazioneDal != null) {
			scCriteriAvanzati.setDtInizioPubblicazioneDal(dtInizioPubblicazioneDal);
		}
		if (dtInizioPubblicazioneAl != null) {
			scCriteriAvanzati.setDtInizioPubblicazioneAl(dtInizioPubblicazioneAl);
		}
		
		// PUBBLICAZIONE : data termine
		if (dtTerminePubblicazioneDal != null) {
			scCriteriAvanzati.setDtTerminePubblicazioneDal(dtTerminePubblicazioneDal);
		}
		if (dtTerminePubblicazioneAl != null) {
			scCriteriAvanzati.setDtTerminePubblicazioneAl(dtTerminePubblicazioneAl);
		}
		
		// PUBBLICAZIONE : gg durata
		if (StringUtils.isNotBlank(ggDurataPubblicazioneDa)) {
			scCriteriAvanzati.setGgDurataPubblicazioneDa(ggDurataPubblicazioneDa);
		}

		if (StringUtils.isNotBlank(ggDurataPubblicazioneA)) {
			scCriteriAvanzati.setGgDurataPubblicazioneA(ggDurataPubblicazioneA);
		}
				
		// PUBBLICAZIONE : UO richiesta
		if (uoPubblicazione != null) { 
			List<UoPubblicazione> listaUoPubblicazione = new ArrayList<UoPubblicazione>();
			for (DestInvioNotifica item : uoPubblicazione) {
				UoPubblicazione lUoPubblicazione = new UoPubblicazione();
				lUoPubblicazione.setIdUo(item.getId());
				lUoPubblicazione.setFlgIncludiSottoUo(item.getFlgIncludiSottoUO() == Flag.SETTED ? Flag.SETTED : Flag.NOT_SETTED);
				listaUoPubblicazione.add(lUoPubblicazione);
			}
			scCriteriAvanzati.setUoPubblicazione(listaUoPubblicazione);
		}
		
		// PUBBLICAZIONE : annullata (si/no/null)
		if (pubblicazioneAnnullata != null) {
			scCriteriAvanzati.setPubblicazioneAnnullata(pubblicazioneAnnullata);
		}
				
		// PUBBLICAZIONE : rettificata (si/no/null)
		if (pubblicazioneRettificata != null) {
			scCriteriAvanzati.setPubblicazioneRettificata(pubblicazioneRettificata);
		}
				
		// PUBBLICAZIONE : stato
		if (statoPubblicazione != null) {
			scCriteriAvanzati.setStatoPubblicazione(statoPubblicazione);
		}
		
		
				
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		advancedFilters = lXmlUtilitySerializer.bindXml(scCriteriAvanzati);

		List<CriteriPersonalizzati> listCustomFiltersSkipNullValues = new ArrayList<CriteriPersonalizzati>();
		for (int i = 0; i < listCustomFilters.size(); i++) {
			if (listCustomFilters.get(i) != null) {
				listCustomFiltersSkipNullValues.add(listCustomFilters.get(i));
			}
		}
		customFilters = lXmlUtilitySerializer.bindXmlList(listCustomFiltersSkipNullValues);

		// if(parametri != null) {
		// try {
		// HashMap <String, String> parameters = new HashMap<String, String>();
		// StringTokenizer st = new StringTokenizer(parametri, "|*|");
		// if(st.countTokens() > 0 && st.countTokens()%2 == 0) {
		// while(st.hasMoreTokens()) {
		// String key = st.nextToken();
		// String value = st.nextToken();
		// parameters.put(key, value);
		// }
		// }
		// idFolder = parameters.get("CercaInFolderIO") != null ? Integer.parseInt(parameters.get("CercaInFolderIO")) : null;
		// if(flgUdFolder == null) {
		// flgUdFolder = parameters.get("FlgUDFolderIO");
		// }
		// } catch (Exception e) {
		// }
		// }

		if (idFolder != null) {
			// Se sto cercando nelle NEWS
			if (idFolder.equals(new Long(-9))) {
				colsToReturn += ",60,61,62";
			}
			// Se sto cercando tra gli ELIMINATI
			else if (idFolder.equals(new Long(-99999))) {
				colsToReturn += ",36,37";
			}
			// Se sto cercando tra gli INVIATI
			else if (idFolder.equals(new Long(-9999)) || idFolder.equals(new Long(-99991))) {
				colsToReturn += ",35,38";
			}
		}

		/**
		 * Commentato controllo poichè la colonna 217 se non valorizzata impedisce la creazione del menù contestuale
		 * di Fascicoli/Folder senza percorsoNome e/o nroFascicolo
		 */
		//if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_FOLDER_CUSTOM")) {
			colsToReturn += ",217";
		//}

		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVATO_MODULO_ATTI")) {
			//colsToReturn += ",14,15,FLG_ATTO_PREGRESSO,226";
			colsToReturn += ",14,15,226";
		}

		/**
		 * Aggiunta colonne per parametro di db CLIENTE N.B le colonne partono da 103 fino a 108
		 */
//		String CMMIVar = ParametriDBUtil.getParametroDB(getSession(), "CLIENTE");
//		if (CMMIVar != null && !"".equals(CMMIVar) && "CMMI".equals(CMMIVar)) {
//			colsToReturn += ",DATI_CONTABILI_ARTICOLO_CMMI,DATI_CONTABILI_CAPITOLO_CMMI,DATI_CONTABILI_DDN_CMMI,DATI_CONTABILI_ESERCIZIO_CMMI,DATI_CONTABILI_IMPORTO_CMMI,DATI_CONTABILI_NUMERO_CMMI";
//		}

		FindRepositoryObjectBean lFindRepositoryObjectBean = new FindRepositoryObjectBean();

		lFindRepositoryObjectBean.setFiltroFullText(filtroFullText);
		lFindRepositoryObjectBean.setCheckAttributes(checkAttributes);
		lFindRepositoryObjectBean.setFormatoEstremiReg(formatoEstremiReg);
		lFindRepositoryObjectBean.setSearchAllTerms(searchAllTerms);
		lFindRepositoryObjectBean.setFlgUdFolder(flgUdFolder);
		lFindRepositoryObjectBean.setIdFolderSearchIn(idFolder);
		if (!setNullFlgSubfolderSearch) {
			lFindRepositoryObjectBean.setFlgSubfoderSearchIn(includiSottoCartelle);
		} else {
			lFindRepositoryObjectBean.setFlgSubfoderSearchIn(null);
		}

		lFindRepositoryObjectBean.setAdvancedFilters(advancedFilters);
		lFindRepositoryObjectBean.setCustomFilters(customFilters);
		lFindRepositoryObjectBean.setColsOrderBy(colsOrderBy);
		lFindRepositoryObjectBean.setFlgDescOrderBy(flgDescOrderBy);
		lFindRepositoryObjectBean.setFlgSenzaPaginazione(flgSenzaPaginazione);
		lFindRepositoryObjectBean.setNumPagina(numPagina);
		lFindRepositoryObjectBean.setNumRighePagina(numRighePagina);
		lFindRepositoryObjectBean.setOnline(online);
		lFindRepositoryObjectBean.setColsToReturn(colsToReturn);
		lFindRepositoryObjectBean.setPercorsoRicerca(percorsoRicerca);
		lFindRepositoryObjectBean.setFlagTipoRicerca(flgTipoRicerca);
		lFindRepositoryObjectBean.setFinalita(finalita);
		lFindRepositoryObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());

		return lFindRepositoryObjectBean;
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

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), PubblicazioneAlboRicercaPubblicazioniXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), PubblicazioneAlboRicercaPubblicazioniXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Richiesta di esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return null;
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

	public DownloadDocsZipBean generateDocsZip(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String limiteMaxZipErrorMessage = MessageUtil.getValue(getLocale().getLanguage(), null, "alert_archivio_list_limiteMaxZipError");

		String errorMessage = getExtraparams().get("messageError");
		String operazione = getExtraparams().get("operazione");
		String posizioneTimbro = getExtraparams().get("posizioneTimbro");
		String rotazioneTimbro = getExtraparams().get("rotazioneTimbro");
		String tipoPagina = getExtraparams().get("tipoPagina");
		
		OpzioniTimbroBean opzioniTimbroScelteUtente = new OpzioniTimbroBean();
		opzioniTimbroScelteUtente.setPosizioneTimbro(posizioneTimbro);
		opzioniTimbroScelteUtente.setRotazioneTimbro(rotazioneTimbro);
		opzioniTimbroScelteUtente.setTipoPagina(tipoPagina);

		String maxSize = ParametriDBUtil.getParametroDB(getSession(), "MAX_SIZE_ZIP");

		long MAX_SIZE = (maxSize != null && maxSize.length() > 0 ? Long.decode(maxSize) : 104857600);
		long lengthZip = 0;

		DownloadDocsZipBean retValue = new DownloadDocsZipBean();
		
		String tempPath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");
		
		Date date = new Date();
	    long timeMilli = date.getTime();

	    String prefissoNomeFileZip = AurigaAbstractDataSourceUtilities.getPrefissoNomeFileZip (operazione, true);
		File zipFile = new File(tempPath + prefissoNomeFileZip +  timeMilli + ".zip");

		Map<String, Integer> zipFileNames = new HashMap<String, Integer>();
		List<FileScaricoZipBean> listaFileDaScaricare = new ArrayList<>();

		for (int i = 0; i < bean.getListaRecord().size(); i++) {
			String idUd = bean.getListaRecord().get(i).getIdUdFolder();
			String segnatura = getSegnature(bean.getListaRecord().get(i).getSegnatura());

			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(new BigDecimal(idUd));
			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();

			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);

			if (StringUtils.isNotEmpty(lRecuperaDocumentoOutBean.getDefaultMessage())) {
				String message = lRecuperaDocumentoOutBean.getDefaultMessage();

				throw new StoreException(errorMessage != null ? errorMessage : message);
			}

			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
			ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
			ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);

			String attachmentFileName = null;
			String attachmentFileNameOmissis = null;
			
			
			if (documento.getNomeFilePrimario() != null && documento.getFilePrimarioOmissis().getNomeFile() == null) {
				// il file primario ha solo versione integrale
				if (documento.getInfoFile() != null) {
					lengthZip += documento.getInfoFile().getBytes();

					if (lengthZip > MAX_SIZE) {
						throw new StoreException(limiteMaxZipErrorMessage);
					}

					attachmentFileName = segnatura + " - File primario - " + documento.getNomeFilePrimario();
					attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);

					FileScaricoZipBean filePrimarioDaScaricare = new FileScaricoZipBean();
					filePrimarioDaScaricare.setUriFile(documento.getUriFilePrimario());
					filePrimarioDaScaricare.setNomeFile(attachmentFileName);
					filePrimarioDaScaricare.setFirmato(documento.getInfoFile().isFirmato());
					filePrimarioDaScaricare.setIdUd(new BigDecimal(idUd));
					filePrimarioDaScaricare.setMimeType(documento.getInfoFile().getMimetype());
					filePrimarioDaScaricare.setIdDoc(documento.getIdDocPrimario());
					
					listaFileDaScaricare.add(filePrimarioDaScaricare);
				}
			} else if (documento.getNomeFilePrimario() != null && documento.getFilePrimarioOmissis().getNomeFile() != null) {
				// il file primario ha versione integrale e versione con omissis
				if (documento.getInfoFile() != null) {
					lengthZip += documento.getInfoFile().getBytes();

					if (lengthZip > MAX_SIZE) {
						throw new StoreException(limiteMaxZipErrorMessage);
					}

					attachmentFileName = segnatura + " - File primario (vers. integrale) - " + documento.getNomeFilePrimario();
					attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);
					
					FileScaricoZipBean filePrimarioDaScaricare = new FileScaricoZipBean();
					filePrimarioDaScaricare.setUriFile(documento.getUriFilePrimario());
					filePrimarioDaScaricare.setNomeFile(attachmentFileName);
					filePrimarioDaScaricare.setFirmato(documento.getInfoFile().isFirmato());
					filePrimarioDaScaricare.setIdUd(new BigDecimal(idUd));
					filePrimarioDaScaricare.setMimeType(documento.getInfoFile().getMimetype());
					filePrimarioDaScaricare.setIdDoc(documento.getIdDocPrimario());
					
					listaFileDaScaricare.add(filePrimarioDaScaricare);

				}

				if (documento.getFilePrimarioOmissis().getInfoFile() != null) {
					lengthZip += documento.getFilePrimarioOmissis().getInfoFile().getBytes();

					if (lengthZip > MAX_SIZE) {
						throw new StoreException(limiteMaxZipErrorMessage);
					}

					attachmentFileNameOmissis = segnatura + " - File primario (vers. con omissis) - " + documento.getFilePrimarioOmissis().getNomeFile();
					attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);
					
					FileScaricoZipBean filePrimarioDaScaricare = new FileScaricoZipBean();
					filePrimarioDaScaricare.setUriFile(documento.getFilePrimarioOmissis().getUriFile());
					filePrimarioDaScaricare.setNomeFile(attachmentFileNameOmissis);
					filePrimarioDaScaricare.setFirmato(documento.getFilePrimarioOmissis().getInfoFile().isFirmato());
					filePrimarioDaScaricare.setIdUd(new BigDecimal(idUd));
					filePrimarioDaScaricare.setMimeType(documento.getFilePrimarioOmissis().getInfoFile().getMimetype());
					filePrimarioDaScaricare.setIdDoc(new BigDecimal(documento.getFilePrimarioOmissis().getIdDoc()));
					
					listaFileDaScaricare.add(filePrimarioDaScaricare);
				
				}
			} else if (documento.getNomeFilePrimario() == null && documento.getFilePrimarioOmissis().getNomeFile() != null) {
				// il file primario ha solo versione con omissis
				if (documento.getFilePrimarioOmissis().getInfoFile() != null) {
					lengthZip += documento.getFilePrimarioOmissis().getInfoFile().getBytes();

					if (lengthZip > MAX_SIZE) {
						throw new StoreException(limiteMaxZipErrorMessage);
					}

					attachmentFileNameOmissis = segnatura + " - File primario (vers. con omissis) - " + documento.getFilePrimarioOmissis().getNomeFile();
					attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);

					FileScaricoZipBean filePrimarioDaScaricare = new FileScaricoZipBean();
					filePrimarioDaScaricare.setUriFile(documento.getFilePrimarioOmissis().getUriFile());
					filePrimarioDaScaricare.setNomeFile(attachmentFileNameOmissis);
					filePrimarioDaScaricare.setFirmato(documento.getFilePrimarioOmissis().getInfoFile().isFirmato());
					filePrimarioDaScaricare.setIdUd(new BigDecimal(idUd));
					filePrimarioDaScaricare.setMimeType(documento.getFilePrimarioOmissis().getInfoFile().getMimetype());
					filePrimarioDaScaricare.setIdDoc(new BigDecimal(documento.getFilePrimarioOmissis().getIdDoc()));
					
					listaFileDaScaricare.add(filePrimarioDaScaricare);				
					
				}
			}

			if (documento.getListaAllegati() != null) {
				for (AllegatoProtocolloBean allegatoUdBean : documento.getListaAllegati()) {

					if (allegatoUdBean.getNomeFileAllegato() != null && allegatoUdBean.getNomeFileOmissis() != null) {
						// il file allegato ha versione integrale e versione con omissis
						if (allegatoUdBean.getInfoFileOmissis() != null ) {
							lengthZip += allegatoUdBean.getInfoFileOmissis().getBytes();

							if (lengthZip > MAX_SIZE) {
								throw new StoreException(limiteMaxZipErrorMessage);
							}

							attachmentFileNameOmissis = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. con omissis) - " + allegatoUdBean.getNomeFileOmissis();
							attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);
						
							FileScaricoZipBean fileAllegatoDaScaricare = new FileScaricoZipBean();
							fileAllegatoDaScaricare.setNomeFile(attachmentFileNameOmissis);
							fileAllegatoDaScaricare.setUriFile(allegatoUdBean.getUriFileOmissis());
							fileAllegatoDaScaricare.setFirmato(allegatoUdBean.getInfoFileOmissis().isFirmato());
							fileAllegatoDaScaricare.setIdUd(new BigDecimal(idUd));
							fileAllegatoDaScaricare.setMimeType(allegatoUdBean.getInfoFileOmissis().getMimetype());
							fileAllegatoDaScaricare.setIdDoc(allegatoUdBean.getIdDocOmissis());
							
							listaFileDaScaricare.add(fileAllegatoDaScaricare);
						
						}

						if (allegatoUdBean.getInfoFile() != null) {
							lengthZip += allegatoUdBean.getInfoFile().getBytes();

							if (lengthZip > MAX_SIZE) {
								throw new StoreException(limiteMaxZipErrorMessage);
							}

							attachmentFileName = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. integrale) - " + allegatoUdBean.getNomeFileAllegato();
							attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);
							
							FileScaricoZipBean fileAllegatoDaScaricare = new FileScaricoZipBean();
							fileAllegatoDaScaricare.setNomeFile(attachmentFileName);
							fileAllegatoDaScaricare.setUriFile(allegatoUdBean.getUriFileAllegato());
							fileAllegatoDaScaricare.setFirmato(allegatoUdBean.getInfoFile().isFirmato());
							fileAllegatoDaScaricare.setIdUd(new BigDecimal(idUd));
							fileAllegatoDaScaricare.setMimeType(allegatoUdBean.getInfoFile().getMimetype());
							fileAllegatoDaScaricare.setIdDoc(allegatoUdBean.getIdDocAllegato());
							
							listaFileDaScaricare.add(fileAllegatoDaScaricare);
							
						
						}
					} else if (allegatoUdBean.getNomeFileAllegato() == null && allegatoUdBean.getNomeFileOmissis() != null) {
						// il file allegato ha solo versione con omissis
						if (allegatoUdBean.getInfoFileOmissis() != null ) {
							lengthZip += allegatoUdBean.getInfoFileOmissis().getBytes();

							if (lengthZip > MAX_SIZE) {
								throw new StoreException(limiteMaxZipErrorMessage);
							}
							attachmentFileNameOmissis = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. con omissis) - " + allegatoUdBean.getNomeFileOmissis();
							attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);
							
							FileScaricoZipBean fileAllegatoDaScaricare = new FileScaricoZipBean();
							fileAllegatoDaScaricare.setNomeFile(attachmentFileNameOmissis);
							fileAllegatoDaScaricare.setUriFile(allegatoUdBean.getUriFileOmissis());
							fileAllegatoDaScaricare.setFirmato(allegatoUdBean.getInfoFileOmissis().isFirmato());
							fileAllegatoDaScaricare.setIdUd(new BigDecimal(idUd));
							fileAllegatoDaScaricare.setMimeType(allegatoUdBean.getInfoFileOmissis().getMimetype());
							fileAllegatoDaScaricare.setIdDoc(allegatoUdBean.getIdDocOmissis());
							
							listaFileDaScaricare.add(fileAllegatoDaScaricare);
						}
					} else if (allegatoUdBean.getNomeFileAllegato() != null && allegatoUdBean.getNomeFileOmissis() == null) {
						// il file allegato ha solo versione integrale
						if (allegatoUdBean.getInfoFile() != null) {
							lengthZip += allegatoUdBean.getInfoFile().getBytes();

							if (lengthZip > MAX_SIZE) {
								throw new StoreException(limiteMaxZipErrorMessage);
							}
							attachmentFileName = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" - " + allegatoUdBean.getNomeFileAllegato();
							attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);

							FileScaricoZipBean fileAllegatoDaScaricare = new FileScaricoZipBean();
							fileAllegatoDaScaricare.setNomeFile(attachmentFileName);
							fileAllegatoDaScaricare.setUriFile(allegatoUdBean.getUriFileAllegato());
							fileAllegatoDaScaricare.setFirmato(allegatoUdBean.getInfoFile().isFirmato());
							fileAllegatoDaScaricare.setIdUd(new BigDecimal(idUd));
							fileAllegatoDaScaricare.setMimeType(allegatoUdBean.getInfoFile().getMimetype());
							fileAllegatoDaScaricare.setIdDoc(allegatoUdBean.getIdDocAllegato());
							
							listaFileDaScaricare.add(fileAllegatoDaScaricare);
						}
					}
				}
			}
		}
				
		Integer warning = AurigaAbstractDataSourceUtilities.addFileToZip(zipFileNames, zipFile, listaFileDaScaricare, operazione, opzioniTimbroScelteUtente, getSession());

		try {
			if (!zipFileNames.isEmpty()) {
				String zipUri = StorageImplementation.getStorage().store(zipFile);

				retValue.setStorageZipRemoteUri(zipUri);
				retValue.setZipName(zipFile.getName());
				MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
				infoFile.setBytes(zipFile.length());
				retValue.setInfoFile(infoFile);
				if(warning!=null) {
					if(warning == OK_CON_WARNING_TIMBRO) {
						retValue.setWarningTimbro("Per alcuni file NON è stato possibile apporre il timbro; sono presenti nello zip nella forma originale");
					}
					else if(warning == OK_CON_WARNING_SBUSTA) {
						retValue.setWarningSbusta("Per alcuni file NON è stato possibile estrarre il file contenuto della busta firma p7m; sono presenti nello zip nella forma originale di p7m");
					}
				}
			} else {
				retValue.setMessage("Le unità documentarie selezionate non hanno nessun file");
			}

		} finally {

			// una volta salvato in storage lo posso eliminare localmente
			FileDeleteStrategy.FORCE.delete(zipFile);

		}

		return retValue;

	}

	
	public DownloadDocsZipBean generateDocsPubblicatiZip(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String limiteMaxZipErrorMessage = MessageUtil.getValue(getLocale().getLanguage(), null, "alert_archivio_list_limiteMaxZipError");

		String errorMessage = getExtraparams().get("messageError");

		String maxSize = ParametriDBUtil.getParametroDB(getSession(), "MAX_SIZE_ZIP");

		long MAX_SIZE = (maxSize != null && maxSize.length() > 0 ? Long.decode(maxSize) : 104857600);
		long lengthZip = 0;

		DownloadDocsZipBean retValue = new DownloadDocsZipBean();

		String tempPath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");
		
		Date date = new Date();
	    long timeMilli = date.getTime();

		File zipFile = new File(tempPath + "FileSelezioneDocumenti_" +  timeMilli + ".zip");

		Map<String, Integer> zipFileNames = new HashMap<String, Integer>();

		for (int i = 0; i < bean.getListaRecord().size(); i++) {
			String idUd = bean.getListaRecord().get(i).getIdUdFolder();
			String segnatura = getSegnature(bean.getListaRecord().get(i).getSegnatura());

			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(new BigDecimal(idUd));
			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();

			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);

			if (StringUtils.isNotEmpty(lRecuperaDocumentoOutBean.getDefaultMessage())) {
				String message = lRecuperaDocumentoOutBean.getDefaultMessage();

				throw new StoreException(errorMessage != null ? errorMessage : message);
			}

			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
			
			ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
			ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);

			String attachmentFileName = null;
			String attachmentFileNameOmissis = null;

			// Prendo solo i file PUBBLICATI 
			if (documento.getIsFilePrimarioPubblicato()!=null && documento.getIsFilePrimarioPubblicato()){

				if (documento.getNomeFilePrimario() != null /*&& documento.getFilePrimarioOmissis().getNomeFile() == null*/) {
					// il file primario ha solo versione integrale
					if (documento.getInfoFile() != null) {
						lengthZip += documento.getInfoFile().getBytes();

						if (lengthZip > MAX_SIZE) {
							throw new StoreException(limiteMaxZipErrorMessage);
						}

						attachmentFileName = segnatura + " - File primario - " + documento.getNomeFilePrimario();
						attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);

						File currentAttachmentPrimario = File.createTempFile("temp",  attachmentFileName);

						addFileZip( documento.getInfoFile(), documento.getUriFilePrimario(), attachmentFileName, currentAttachmentPrimario, zipFile);
					}
				} 
				
				/*COMMENTATO PER SEGNALAZIONE DA AOSTA: NELLO ZIP NON VOLEVANO LA VERSIONE OMISSIS MA SOLO IL FILE PRIMARIO CHE (AD AOSTA) E' QUELLO CHE VA IN PUBBLICAZIONE
				else if (documento.getNomeFilePrimario() != null && documento.getFilePrimarioOmissis().getNomeFile() != null) {
					// il file allegato ha versione integrale e versione con omissis
					if (documento.getInfoFile() != null) {
						lengthZip += documento.getInfoFile().getBytes();

						if (lengthZip > MAX_SIZE) {
							throw new StoreException(limiteMaxZipErrorMessage);
						}

						attachmentFileName = segnatura + " - File primario - " + documento.getNomeFilePrimario();
						attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);

						File currentAttachmentPrimarioIntegrale = new File(tempPath + attachmentFileName);
						addFileZip(tempPath, documento.getInfoFile(), documento.getUriFilePrimario(), attachmentFileName, currentAttachmentPrimarioIntegrale, zipFile);
					}
					
					if (documento.getFilePrimarioOmissis().getInfoFile() != null) {
						lengthZip += documento.getFilePrimarioOmissis().getInfoFile().getBytes();

						if (lengthZip > MAX_SIZE) {
							throw new StoreException(limiteMaxZipErrorMessage);
						}
						
						attachmentFileNameOmissis = segnatura + " - File primario (vers. con omissis) - " + documento.getFilePrimarioOmissis().getNomeFile();
						attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);

						File currentAttachmentPrimarioOmissis = new File (tempPath + attachmentFileNameOmissis);

						addFileZip(tempPath, documento.getFilePrimarioOmissis().getInfoFile(),  documento.getFilePrimarioOmissis().getUriFile(), attachmentFileNameOmissis, currentAttachmentPrimarioOmissis, zipFile);
					}
				
				} 
				/* COMMENTATO PERCHE IN TEORIA IN FASE DI PUBBLICAZIONE NON AVRO MAI L'OMISSIS SENZA PRIMARIO CHE AD AOSTA E' QUELLO CHE VA IN PUBBLICAZIONE
				else if (documento.getNomeFilePrimario() == null && documento.getFilePrimarioOmissis().getNomeFile() != null) {
					// il file primario ha solo versione con omissis
					if (documento.getFilePrimarioOmissis().getInfoFile() != null) {
						lengthZip += documento.getFilePrimarioOmissis().getInfoFile().getBytes();

						if (lengthZip > MAX_SIZE) {
							throw new StoreException(limiteMaxZipErrorMessage);
						}
						
						attachmentFileNameOmissis = segnatura + " - File primario (vers. con omissis) - " + documento.getFilePrimarioOmissis().getNomeFile();
						attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);
	
						File currentAttachmentPrimarioOmissis = new File (tempPath + attachmentFileNameOmissis);
	
						addFileZip(tempPath, documento.getFilePrimarioOmissis().getInfoFile(),  documento.getFilePrimarioOmissis().getUriFile(), attachmentFileNameOmissis, currentAttachmentPrimarioOmissis, zipFile);
					}
				}
				*/
			}
			
			if (documento.getListaAllegati() != null) {
				for (AllegatoProtocolloBean allegatoUdBean : documento.getListaAllegati()) {

					// Prendo solo i file PUBBLICATI
					if(allegatoUdBean.getIsPubblicato() !=null && allegatoUdBean.getIsPubblicato()){

						if (allegatoUdBean.getNomeFileAllegato() != null && allegatoUdBean.getNomeFileOmissis() != null) {
							// il file allegato ha versione integrale e versione con omissis
							if (allegatoUdBean.getInfoFileOmissis() != null ) {
								lengthZip += allegatoUdBean.getInfoFileOmissis().getBytes();

								if (lengthZip > MAX_SIZE) {
									throw new StoreException(limiteMaxZipErrorMessage);
								}

								attachmentFileNameOmissis = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. con omissis) - " + allegatoUdBean.getNomeFileOmissis();
								attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);
								File currentAttachmentOmissis = File.createTempFile("temp", attachmentFileNameOmissis);

								addFileZip(allegatoUdBean.getInfoFileOmissis(), allegatoUdBean.getUriFileOmissis(), attachmentFileNameOmissis, currentAttachmentOmissis, zipFile);
							}

							if (allegatoUdBean.getInfoFile() != null) {
								lengthZip += allegatoUdBean.getInfoFile().getBytes();

								if (lengthZip > MAX_SIZE) {
									throw new StoreException(limiteMaxZipErrorMessage);
								}

								attachmentFileName = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. integrale) - " + allegatoUdBean.getNomeFileAllegato();
								attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);
								File currentAttachment = File.createTempFile("temp", attachmentFileName);

								addFileZip(allegatoUdBean.getInfoFile(), allegatoUdBean.getUriFileAllegato(), attachmentFileName, currentAttachment, zipFile);
							}
						} else if (allegatoUdBean.getNomeFileAllegato() == null && allegatoUdBean.getNomeFileOmissis() != null) {
							// il file allegato ha solo versione con omissis
							if (allegatoUdBean.getInfoFileOmissis() != null ) {
								lengthZip += allegatoUdBean.getInfoFileOmissis().getBytes();

								if (lengthZip > MAX_SIZE) {
									throw new StoreException(limiteMaxZipErrorMessage);
								}
								attachmentFileNameOmissis = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. con omissis) - " + allegatoUdBean.getNomeFileOmissis();
								attachmentFileNameOmissis = checkAndRenameDuplicate(zipFileNames, attachmentFileNameOmissis);
								File currentAttachmentOmissis = File.createTempFile("temp", attachmentFileNameOmissis);

								addFileZip(allegatoUdBean.getInfoFileOmissis(), allegatoUdBean.getUriFileOmissis(), attachmentFileNameOmissis, currentAttachmentOmissis, zipFile);
							}
						} else if (allegatoUdBean.getNomeFileAllegato() != null && allegatoUdBean.getNomeFileOmissis() == null) {
							// il file allegato ha solo versione integrale
							if (allegatoUdBean.getInfoFile() != null) {
								lengthZip += allegatoUdBean.getInfoFile().getBytes();

								if (lengthZip > MAX_SIZE) {
									throw new StoreException(limiteMaxZipErrorMessage);
								}
								attachmentFileName = segnatura + "- Allegato N° "+ allegatoUdBean.getNumeroProgrAllegato() +" (vers. integrale) - " + allegatoUdBean.getNomeFileAllegato();
								attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);
								File currentAttachment = File.createTempFile("temp", attachmentFileName);

								addFileZip(allegatoUdBean.getInfoFile(), allegatoUdBean.getUriFileAllegato(), attachmentFileName, currentAttachment, zipFile);
							}
						}
					}
				}
			}
		}

		try {
			if (!zipFileNames.isEmpty()) {
				String zipUri = StorageImplementation.getStorage().store(zipFile);

				retValue.setStorageZipRemoteUri(zipUri);
				retValue.setZipName(zipFile.getName());
				MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
				infoFile.setBytes(zipFile.length());
				retValue.setInfoFile(infoFile);
			} else {
				retValue.setMessage("Le unità documentarie selezionate non hanno nessun file");
			}

		} finally {

			// una volta salvato in storage lo posso eliminare localmente
			FileDeleteStrategy.FORCE.delete(zipFile);

		}

		return retValue;

	}
	
	private String checkAndRenameDuplicate(Map<String, Integer> zipFileNames, String attachmentFileName) {

		boolean duplicated = zipFileNames.containsKey(attachmentFileName);

		Integer index = 0;

		if (duplicated) {

			int x = zipFileNames.get(attachmentFileName);
			String attachmentFileNameoriginale = attachmentFileName;
			while (zipFileNames.containsKey(attachmentFileName)) {
				attachmentFileName = normalizeDuplicate(attachmentFileNameoriginale, x);
				x++;
			}

			zipFileNames.put(attachmentFileName, index);

		} else {

			zipFileNames.put(attachmentFileName, index);

		}

		return attachmentFileName;
	}

	public String getSegnature(String segnatura) {
		if (segnatura != null) {
			segnatura = segnatura.replace(" ", "_");
			segnatura = segnatura.replace(".", "_");
			segnatura = segnatura.replace("\\", "_");
			segnatura = segnatura.replace("/", "_");
		} else {

			Calendar currentDate = Calendar.getInstance();

			segnatura = Integer.toString(currentDate.get(Calendar.DAY_OF_WEEK)) + "_" + Integer.toString(currentDate.get(Calendar.HOUR))
					+ Integer.toString(currentDate.get(Calendar.MINUTE)) + Integer.toString(currentDate.get(Calendar.SECOND))
					+ Integer.toString(currentDate.get(Calendar.MILLISECOND));
		}

		return segnatura;
	}

	private String normalizeDuplicate(String value, Integer index) {

		String[] tokens = value.split("\\.");
		String attachmentFileName = "";
		if (tokens.length >= 2) {
			for (int x = 0; x < tokens.length - 1; x++)
				attachmentFileName += "." + tokens[x];

			attachmentFileName += "_" + index + "." + tokens[tokens.length - 1];
			// tolgo il primo punto
			attachmentFileName = attachmentFileName.substring(1, attachmentFileName.length());
		} else
			attachmentFileName = tokens[0] + "_" + index + (tokens.length > 1 ? "." + tokens[1] : "");

		return attachmentFileName;
	}

	



	public File unisciPdf(List<InputStream> lListPdf) throws Exception {
		Document document = new Document();
		// Istanzio una copia nell'output
		File lFile = File.createTempFile("pdf", ".pdf");
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(lFile));
		// Apro per la modifica il nuovo documento
		document.open();
		// Istanzio un nuovo reader che ci servirà per leggere i singoli file
		PdfReader reader;

		// Per ogni file passato
		for (InputStream lInputStream : lListPdf) {
			// Leggo il file
			reader = new PdfReader(lInputStream);
			// Prendo il numero di pagine
			int n = reader.getNumberOfPages();
			// e per ogni pagina
			for (int page = 0; page < n;) {
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			// con questo metodo faccio il flush del contenuto e libero il rader
			copy.freeReader(reader);
		}
		// Chiudo il documento
		document.close();
		return lFile;
	}

	private InputStream convertiFileToPdf(InputStream lInputStream) throws StoreException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().storeStream(lInputStream, new String[] {}))
					.toURI().toString();
			return lInfoFileUtility.converti(fileUrl, "");
		} catch (Exception e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			throw new StoreException("Non è stato possibile convertire il file");
		}
	}

	private InputStream sbusta(String uriFile, String displayFilename) throws IOException, StorageException, StoreException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(uriFile).toURI().toString();
			return lInfoFileUtility.sbusta(fileUrl, "");
		} catch (Exception e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			throw new StoreException("Non è stato possibile sbustare il file");
		}
	}

	public boolean isTaskDettFascGen() {
		boolean isTaskDettFascGen = getExtraparams().get("isTaskDettFascGen") != null && "true".equals(getExtraparams().get("isTaskDettFascGen"));
		return isTaskDettFascGen;
	}

	public boolean isTaskDettFasc() {
		boolean isTaskDettFasc = getExtraparams().get("isTaskDettFasc") != null && "true".equals(getExtraparams().get("isTaskDettFasc"));
		return isTaskDettFasc;
	}
	
	/**
	 * ************************************************************************************************************************************************
	 */
	
	private void invioNotificheAssegnazioneInvioCC(PubblicazioneAlboRicercaPubblicazioniBean bean, XmlFascicoloIn xmlFascicolo,Boolean isUpdate) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		List<TipoIdBean> listaAssegnatari = getListaAssegnatari(xmlFascicolo);
		List<TipoIdBean> listaInvioCC =	getListaAssegnatariPerConoscenza(xmlFascicolo);
		
		/**
		 * Operazioni per la presenza di utenti assegnatari
		 */
		if(listaAssegnatari != null && !listaAssegnatari.isEmpty()){
			manageAssegnazioni(bean, isUpdate, lAurigaLoginBean, lXmlUtilitySerializer, listaAssegnatari);
		}
		/**
		 * Operazioni per la presenza di utenti per invio in conoscenza
		 */
		if(listaInvioCC != null && !listaInvioCC.isEmpty()){
			manageInvioCC(bean, isUpdate, lAurigaLoginBean, lXmlUtilitySerializer, listaInvioCC);
		}
	}
	
	private void manageAssegnazioni(PubblicazioneAlboRicercaPubblicazioniBean bean, Boolean isUpdate, AurigaLoginBean lAurigaLoginBean, XmlUtilitySerializer lXmlUtilitySerializer,
			List<TipoIdBean> listaAssegnatari) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {

		if (StringUtils.isBlank(bean.getFlgUdFolder()) || bean.getFlgUdFolder().equalsIgnoreCase("F")) {
			/**
			 * Assegnazione per conoscenza di un FOLDER, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_ASSEGN_FLD
			 */
			if (attivaNotEmailAssegnFLD(listaAssegnatari)) {
				if (listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
					sendNotificaAssegnazioneInvioCC(bean,lAurigaLoginBean,lXmlUtilitySerializer,listaAssegnatari,"ASSEGNAZIONE",isUpdate);
				} else {
					logger.warn("Non è stata inviata alcuna notifica e-mail assegnazione" + "lista destinatari listaAssegnatari: " + listaAssegnatari.size()
							+ ", verificare parametro ATTIVA_NOT_EMAIL_ASSEGN_FLD");
					//addMessage("Non è stata inviata alcuna notifica e-mail dell'assegnazione", "", MessageType.WARNING);
				}
			}
		} else if (bean.getFlgUdFolder().equalsIgnoreCase("U")) {
			/**
			 * Assegnazione per conoscenza di un DOCUMENTO, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_ASSEGN_UD
			 */
			if (attivaNotEmailAssegnUD(listaAssegnatari)) {
				if (listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
					sendNotificaAssegnazioneInvioCC(bean,lAurigaLoginBean,lXmlUtilitySerializer,listaAssegnatari,"ASSEGNAZIONE",isUpdate);
				} else {
					logger.warn("Non è stata inviata alcuna notifica e-mail assegnazione" + "lista destinatari listaAssegnatari: " + listaAssegnatari.size()
							+ ", verificare parametro ATTIVA_NOT_EMAIL_ASSEGN_UD");
					//addMessage("Non è stata inviata alcuna notifica e-mail dell'assegnazione", "", MessageType.WARNING);
				}
			}	
		}
	}
	
	private void manageInvioCC(PubblicazioneAlboRicercaPubblicazioniBean bean, Boolean isUpdate, AurigaLoginBean lAurigaLoginBean, XmlUtilitySerializer lXmlUtilitySerializer,
			List<TipoIdBean> listaAssegnatari) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {
		
		if (StringUtils.isBlank(bean.getFlgUdFolder()) || bean.getFlgUdFolder().equalsIgnoreCase("F")) {
			if (attivaNotEmailInvioCCFLD(listaAssegnatari)) {
				if (listaAssegnatari != null && listaAssegnatari.size() > 0) {
					sendNotificaAssegnazioneInvioCC(bean,lAurigaLoginBean,lXmlUtilitySerializer,listaAssegnatari,"INVIOCC",isUpdate);
				} else {
					logger.warn("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza" + "lista destinatari listaInvioDestCC: "
							+ listaAssegnatari.size() + ", verificare parametro ATTIVA_NOT_EMAIL_INVIO_CC_FLD");
					//addMessage("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza", "", MessageType.WARNING);
				}
			}
		} else if (bean.getFlgUdFolder().equalsIgnoreCase("U")) {
			/**
			 * Invio per conoscenza di un DOCUMENTO, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_ASSEGN_UD
			 */
			if (attivaNotEmailInvioCCUD(listaAssegnatari)) {
				if (listaAssegnatari != null && listaAssegnatari.size() > 0) {
					sendNotificaAssegnazioneInvioCC(bean,lAurigaLoginBean,lXmlUtilitySerializer,listaAssegnatari,"INVIOCC",isUpdate);
				} else {
					logger.warn("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza" + "lista destinatari listaInvioDestCC: "
							+ listaAssegnatari.size() + ", verificare parametro ATTIVA_NOT_EMAIL_INVIO_CC_UD");
					//addMessage("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza", "", MessageType.WARNING);
				}
			}
		}
	}
	
	private void sendNotificaAssegnazioneInvioCC(PubblicazioneAlboRicercaPubblicazioniBean bean, AurigaLoginBean lAurigaLoginBean,XmlUtilitySerializer lXmlUtilitySerializer, 
			List<TipoIdBean> listaAssegnatari,String tipoOperazione,Boolean isUpdate) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {
		
		String token = lAurigaLoginBean.getToken();
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro();
		
		DmpkIntMgoEmailPreparaemailnotassinvioccBean lInputBean = new DmpkIntMgoEmailPreparaemailnotassinvioccBean();
		lInputBean.setCodidconnectiontokenin(token);
		lInputBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		if(isUpdate){
			/**
			 * OPERAZIONE IN MODIFICA
			 */
			if("ASSEGNAZIONE".equals(tipoOperazione)){
				lInputBean.setAssinvioccin("assegnazione_competenza");
			}else{
				lInputBean.setAssinvioccin("invio_conoscenza");
			}
		} else {
			/**
			 * OPERAZIONE IN INSERIMENTO
			 */
			if("ASSEGNAZIONE".equals(tipoOperazione)){
				lInputBean.setAssinvioccin("assegnazione_competenza");
			}else{
				lInputBean.setAssinvioccin("invio_conoscenza");
			}
		}

		List<TipoIdBean> listaUdFolder = new ArrayList<TipoIdBean>();
		TipoIdBean udFolder = new TipoIdBean();

		udFolder.setTipo(StringUtils.isNotBlank(bean.getFlgUdFolder()) ? bean.getFlgUdFolder() : "F");
		udFolder.setId(bean.getIdUdFolder());
		listaUdFolder.add(udFolder);
		lInputBean.setUdfolderxmlin(lXmlUtilitySerializer.bindXmlList(listaUdFolder));
		lInputBean.setDestassinvioccxmlin(lXmlUtilitySerializer.bindXmlList(listaAssegnatari));
		lInputBean.setCodmotivoinvioin(null);
		lInputBean.setMotivoinvioin(null);
		lInputBean.setMessaggioinvioin(null);
		lInputBean.setLivelloprioritain(null);
		lInputBean.setTsdecorrassinvioccin(null);

		DmpkIntMgoEmailPreparaemailnotassinviocc preparaemailnotassinviocc = new DmpkIntMgoEmailPreparaemailnotassinviocc();
		StoreResultBean<DmpkIntMgoEmailPreparaemailnotassinvioccBean> lStoreResultBean = preparaemailnotassinviocc.execute(getLocale(), lAurigaLoginBean,
				lInputBean);

		if (lStoreResultBean.isInError()) {
			String message = "Fallito invio notifica e-mail dell'assegnazione";
			if (StringUtils.isNotBlank(lStoreResultBean.getDefaultMessage())) {
				logger.error("Fallito invio notifica e-mail dell'assegnazionem per il seguente motivo: " + lStoreResultBean.getDefaultMessage());
				message += " per il seguente motivo: " + lStoreResultBean.getDefaultMessage();
			}
			addMessage(message, "", MessageType.WARNING);
		} else {
			if (lStoreResultBean.getResultBean().getFlgemailtosendout() != null && lStoreResultBean.getResultBean().getFlgemailtosendout().intValue() == 1) {
				SenderBean sender = new SenderBean();
				sender.setIdUtenteModPec(lAurigaLoginBean.getSpecializzazioneBean().getIdUtenteModPec());
				sender.setAccount(lStoreResultBean.getResultBean().getIndirizzomittemailout());
				sender.setAddressFrom(lStoreResultBean.getResultBean().getIndirizzomittemailout());
				List<String> addressTo = new ArrayList<String>();
				StringReader sr = new StringReader(lStoreResultBean.getResultBean().getIndirizzidestemailout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
						addressTo.add(v.get(0));
					}
				}
				sender.setAddressTo(addressTo);
				sender.setSubject(lStoreResultBean.getResultBean().getOggemailout());
				sender.setBody(lStoreResultBean.getResultBean().getBodyemailout());
				sender.setIsHtml(true);
				sender.setIsPec(false);
				try {
					EmailSentReferenceBean lEmailSentReferenceBean = AurigaMailService.getMailSenderService().send(getLocale(), sender);
					String notSentMails = null;
					for (SenderBean lSenderBean : lEmailSentReferenceBean.getSentMails()) {
						if (!lSenderBean.getIsSent()) {
							if (notSentMails == null) {
								notSentMails = lSenderBean.getAddressTo().get(0);
							} else {
								notSentMails += ", " + lSenderBean.getAddressTo().get(0);
							}
						}
					}
					if (notSentMails != null) {
						if("ASSEGNAZIONE".equals(tipoOperazione)){
							logger.warn("Fallito invio notifica e-mail dell'assegnazione per i seguenti indirizzi: " + notSentMails);
							addMessage("Fallito invio notifica e-mail dell'assegnazione per i seguenti indirizzi: " + notSentMails, "", MessageType.WARNING);
						}else{
							logger.warn("Warning, Fallito invio notifica e-mail dell'invio per conoscenza per i seguenti indirizzi: " + notSentMails);
							addMessage("Fallito invio notifica e-mail dell'invio per conoscenza per i seguenti indirizzi: " + notSentMails, "", MessageType.WARNING);
						}
					}
				} catch (Exception e) {
					if("ASSEGNAZIONE".equals(tipoOperazione)){
						logger.error("Fallito invio notifica e-mail dell'assegnazione:" + e.getMessage(), e);
						addMessage("Fallito invio notifica e-mail dell'assegnazione: " + e.getMessage(), "", MessageType.WARNING);
					}else{
						logger.error("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza" + e.getMessage(), e);
						addMessage("Fallito invio notifica e-mail dell'invio per conoscenza: " + e.getMessage(), "", MessageType.WARNING);
					}
				}
			} else {
				if("ASSEGNAZIONE".equals(tipoOperazione)){
					logger.warn("Non è stata inviata alcuna notifica e-mail dell'assegnazione");
				}else{
					logger.warn("Warning, Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza,getFlgemailtosendout: "
							+ lStoreResultBean.getResultBean().getFlgemailtosendout());
				}
			}
		}
	}
	
	private List<TipoIdBean> getListaAssegnatari(XmlFascicoloIn xmlFascicolo) throws Exception {
		List<TipoIdBean> listaDestAssCC = new ArrayList<TipoIdBean>();
		if (xmlFascicolo.getAssegnatari() != null && !xmlFascicolo.getAssegnatari().isEmpty()) {
			for (AssegnatariBean assegnatarioBean : xmlFascicolo.getAssegnatari()) {
				
				TipoIdBean tipoIdBean = new TipoIdBean();
				tipoIdBean.setTipo(getTipoAssegnatarioInvioCC(assegnatarioBean));
				tipoIdBean.setId(assegnatarioBean.getIdSettato());
				tipoIdBean.setFlgMandaNotificaMail(assegnatarioBean.getFlgMandaNotificaMail() != null && assegnatarioBean.getFlgMandaNotificaMail() == Flag.SETTED ?
						true : false );
				listaDestAssCC.add(tipoIdBean);
			}
		}
		return listaDestAssCC;
	}
	
	private List<TipoIdBean> getListaAssegnatariPerConoscenza(XmlFascicoloIn xmlFascicolo) {
		List<TipoIdBean> listaInvioCC = new ArrayList<TipoIdBean>();
		if (xmlFascicolo.getInvioDestCC() != null && !xmlFascicolo.getInvioDestCC().isEmpty()) {
			for (AssegnatariBean assegnatarioBean : xmlFascicolo.getInvioDestCC()) {
				
				TipoIdBean tipoIdBean = new TipoIdBean();
				tipoIdBean.setTipo(getTipoAssegnatarioInvioCC(assegnatarioBean));
				tipoIdBean.setId(assegnatarioBean.getIdSettato());
				tipoIdBean.setFlgMandaNotificaMail(assegnatarioBean.getFlgMandaNotificaMail() != null && assegnatarioBean.getFlgMandaNotificaMail() == Flag.SETTED ?
						true : false );
				listaInvioCC.add(tipoIdBean);
			}
		}
		return listaInvioCC;
	}
	
	private String getTipoAssegnatarioInvioCC(AssegnatariBean lAssegnazioneBean) {
		TipoAssegnatario[] lTipoAssegnatarios = TipoAssegnatario.values();
		for (TipoAssegnatario lTipoAssegnatario : lTipoAssegnatarios) {
			if (lTipoAssegnatario.toString().equals(lAssegnazioneBean.getTipo().toString()))
				return lTipoAssegnatario.toString();
		}
		return null;
	}
	
	
	public String getXmlAssegnatariFirmaUD(AzioneSuccessivaBean bean,String tipo) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(getAssegnatariFirmaUD(bean,tipo));
	}

	private List<AssegnatariBean> getAssegnatariFirmaUD(AzioneSuccessivaBean bean,String tipo) throws Exception {
		List<AssegnatariBean> listaAssegnatari = new ArrayList<AssegnatariBean>();
		
		if("PP".equals(tipo)){
			for (AssegnazioneBean assegnazioneBean : bean.getProtRegAssegnazione()) {
					populateAssegnatariFirmaUD(bean, listaAssegnatari, assegnazioneBean);
			}
		} else if("PAF".equals(tipo)){
			for (AssegnazioneBean assegnazioneBean : bean.getInvioFirmaDigitaleAssegnazione()) {
					populateAssegnatariFirmaUD(bean, listaAssegnatari, assegnazioneBean);
			}
		} else if("PAV".equals(tipo)){
			for (AssegnazioneBean assegnazioneBean : bean.getInvioVistoElettronicoAssegnazione()) {
					populateAssegnatariFirmaUD(bean, listaAssegnatari, assegnazioneBean);
			}
		}
		
		return listaAssegnatari;
	}

	private void populateAssegnatariFirmaUD(AzioneSuccessivaBean bean, List<AssegnatariBean> listaAssegnatari, AssegnazioneBean assegnazioneBean)
			throws StoreException {
		AssegnatariBean lAssegnatariBean = new AssegnatariBean();
		lAssegnatariBean.setTipo(getTipoAssegnatarioFirmaUD(assegnazioneBean.getTypeNodo()));
		if(lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.GRUPPO)) {
			lAssegnatariBean.setIdSettato(assegnazioneBean.getGruppo());
		} else {
			lAssegnatariBean.setIdSettato(assegnazioneBean.getIdUo());
		}	
		if (bean.getMotivo() != null ) {							
			if ("PAF".equals(bean.getMotivo())) {
				if (lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.UNITA_ORGANIZZATIVA)) {
					throw new StoreException("L'assegnazione per firma/approvazione deve essere fatta a persona/e e non ad una U.O.");
				}	
			}
			if ("PAV".equals(bean.getMotivo())) {
				if (lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.UNITA_ORGANIZZATIVA)) {
					throw new StoreException("L'assegnazione per apposizione visto elettronico deve essere fatta a persona/e e non ad una U.O.");
				}	
			}							
		}
		lAssegnatariBean.setPermessiAccesso("FC");
		//TODO ASSEGNAZIONE SAVE OK
		if(assegnazioneBean.getOpzioniInvio() != null) {			
			lAssegnatariBean.setMotivoInvio(assegnazioneBean.getOpzioniInvio().getMotivoInvio());
			lAssegnatariBean.setLivelloPriorita(assegnazioneBean.getOpzioniInvio().getLivelloPriorita());
			lAssegnatariBean.setMessaggioInvio(assegnazioneBean.getOpzioniInvio().getMessaggioInvio());
			if (assegnazioneBean.getOpzioniInvio().getFlgPresaInCarico() != null && assegnazioneBean.getOpzioniInvio().getFlgPresaInCarico()) {
				lAssegnatariBean.setFeedback(Flag.SETTED);
			}
			if (assegnazioneBean.getOpzioniInvio().getFlgMancataPresaInCarico() != null && assegnazioneBean.getOpzioniInvio().getFlgMancataPresaInCarico()) {
				lAssegnatariBean.setNumeroGiorniFeedback(assegnazioneBean.getOpzioniInvio().getGiorniTrascorsi());
			}
		}
		if(StringUtils.isNotBlank(lAssegnatariBean.getIdSettato())) {
			listaAssegnatari.add(lAssegnatariBean);
		}
	}

	private TipoAssegnatario getTipoAssegnatarioFirmaUD(String typeNodo) {
		TipoAssegnatario[] tipiAssegnatari = TipoAssegnatario.values();
		for (TipoAssegnatario tipoAssegnatario : tipiAssegnatari) {
			if (tipoAssegnatario.toString().equals(typeNodo)) {
				return tipoAssegnatario;
			}
		}
		return null;
	}

	
	
	
	
	protected void salvaAttributiCustomLista(PubblicazioneAlboRicercaPubblicazioniBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
		
	}
	
	public StampaFileBean creaStampa(PubblicazioneAlboRicercaPubblicazioniBean bean) throws Exception {		
		
		try {		
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			
			String nomeModello = getExtraparams().get("nomeModello");
			String nomeFileStampa = getExtraparams().get("nomeFileStampa");
			
			DmpkModelliDocExtractvermodello lExtractvermodello = new DmpkModelliDocExtractvermodello();
			DmpkModelliDocExtractvermodelloBean lExtractvermodelloInput = new DmpkModelliDocExtractvermodelloBean();
			lExtractvermodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lExtractvermodelloInput.setNomemodelloin(nomeModello);
			
			StoreResultBean<DmpkModelliDocExtractvermodelloBean> lExtractvermodelloOutput = lExtractvermodello.execute(getLocale(), loginBean, lExtractvermodelloInput);
			
			if(lExtractvermodelloOutput.isInError()) {
				throw new StoreException(lExtractvermodelloOutput);
			}
			
			String uriModello = lExtractvermodelloOutput.getResultBean().getUriverout();
			
			DmpkModelliDocGetdatixgendamodello lGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
			DmpkModelliDocGetdatixgendamodelloBean lGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
			lGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lGetdatixgendamodelloInput.setIdobjrifin(bean.getIdUdFolder());
			lGetdatixgendamodelloInput.setFlgtpobjrifin("F");
			lGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
			
			StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = lGetdatixgendamodello.execute(getLocale(), loginBean,
					lGetdatixgendamodelloInput);
			
			if(lGetdatixgendamodelloOutput.isInError()) {
				throw new StoreException(lGetdatixgendamodelloOutput);
			}
			
			String templateValues = lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();					
			File fileModelloPdf = null;
			if (nomeModello != null &&
					nomeModello.equalsIgnoreCase("LISTA_CONTENUTI_FASC")) {
				fileModelloPdf = ModelliUtil.fillTemplateAndConvertToPdf(null, uriModello, null, templateValues, getSession());
			} else
				fileModelloPdf = ModelliUtil.fillTemplateAndConvertToPdf(null, uriModello, "DOCX_FORM", templateValues, getSession());
			
			StampaFileBean output = new StampaFileBean();
			output.setNomeFile(nomeFileStampa);
			output.setUri(StorageImplementation.getStorage().store(fileModelloPdf));
			
			MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
			infoFile.setMimetype("application/pdf");
			infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(fileModelloPdf));
			infoFile.setDaScansione(false);
			infoFile.setFirmato(false);
			infoFile.setFirmaValida(false);
			infoFile.setBytes(fileModelloPdf.length());
			infoFile.setCorrectFileName(output.getNomeFile());
			File realFile = StorageImplementation.getStorage().getRealFile(output.getUri());
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), output.getNomeFile()));
			output.setInfoFile(infoFile);
			
			return output;
			
		} catch(Exception e) {
			mLogger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante la generazione della stampa");
		}		
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_ASSEGN_UD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailAssegnUD(List<TipoIdBean> listaAssegnatari) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD"))) {
			if(listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
				for(int i=0; i < listaAssegnatari.size(); i++) {
					if(listaAssegnatari.get(i) != null &&
					   listaAssegnatari.get(i).getFlgMandaNotificaMail() != null && listaAssegnatari.get(i).getFlgMandaNotificaMail()) {
						attivo = true;
						break;
					}
				}
			}
		}
		return attivo;
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_ASSEGN_FLD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailAssegnFLD(List<TipoIdBean> listaAssegnatari) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_FLD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_FLD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_FLD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_FLD"))) {
			if(listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
				for(int i=0; i < listaAssegnatari.size(); i++) {
					if(listaAssegnatari.get(i) != null &&
					   listaAssegnatari.get(i).getFlgMandaNotificaMail() != null && listaAssegnatari.get(i).getFlgMandaNotificaMail()) {
						attivo = true;
						break;
					}
				}
			}
		}
		return attivo;
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_INVIO_CC_UD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailInvioCCUD(List<TipoIdBean> listaAssegnatari) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD"))) {
			if(listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
				for(int i=0; i < listaAssegnatari.size(); i++) {
					if(listaAssegnatari.get(i) != null &&
					   listaAssegnatari.get(i).getFlgMandaNotificaMail() != null && listaAssegnatari.get(i).getFlgMandaNotificaMail() ) {
							attivo = true;
							break;
					}
				}
			}
		}
		return attivo;
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_INVIO_CC_FLD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailInvioCCFLD(List<TipoIdBean> listaAssegnatari) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_FLD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_FLD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_FLD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_FLD"))) {
			if(listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
				for(int i=0; i < listaAssegnatari.size(); i++) {
					if(listaAssegnatari.get(i) != null &&
					   listaAssegnatari.get(i).getFlgMandaNotificaMail() != null && listaAssegnatari.get(i).getFlgMandaNotificaMail() ) {
							attivo = true;
							break;
					}
				}
			}
		}
		return attivo;
	}
	
	public PubblicazioneAlboRicercaPubblicazioniBean modificaTipologia(PubblicazioneAlboRicercaPubblicazioniBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		//se il bean è un unità documentale
		if ("U".equals(bean.getFlgUdFolder())) {
			DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setFlgtipotargetin("D");
			input.setIduddocin(new BigDecimal(bean.getIdDocPrimario()));

			CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
			creaModDocumentoInBean.setTipoDocumento(getExtraparams().get("tipoDocumento"));
			input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));

			DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
			StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
			if (output.isInError()) {
				throw new StoreException(output);
			}
		} else {
			DmpkCoreUpdfolderBean input = new DmpkCoreUpdfolderBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdfolderin(new BigDecimal(bean.getIdUdFolder()));

			XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
			xmlFascicoloIn.setIdFolderType(new BigDecimal(getExtraparams().get("idFolderType")));

			lXmlUtilitySerializer = new XmlUtilitySerializer();
			input.setAttributixmlin(lXmlUtilitySerializer.bindXml((Object) xmlFascicoloIn));
			
			DmpkCoreUpdfolder dmpkCoreUpdfolder = new DmpkCoreUpdfolder();
			StoreResultBean<DmpkCoreUpdfolderBean> output = dmpkCoreUpdfolder.execute(this.getLocale(), loginBean,
					(DmpkCoreUpdfolderBean) input);
			if (output.isInError()) {
				throw new StoreException(output);
			}
		}
		
		return bean;

	}
	
	public OperazioneMassivaArchivioBean modificaTipologiaMassiva(OperazioneMassivaArchivioBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		HashMap<String, String> errorMessages = new HashMap<String, String>();

		for (ArchivioBean archivioBean : bean.getListaRecord()) {
			
			//se il bean è un unità documentale
			if ("U".equals(archivioBean.getFlgUdFolder())) {
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(
						StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setFlgtipotargetin("D");
				input.setIduddocin(new BigDecimal(archivioBean.getIdDocPrimario()));

				CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
				lCreaModDocumentoInBean.setTipoDocumento(getExtraparams().get("tipoDocumento"));
				
				lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml((Object) lCreaModDocumentoInBean));

				DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean,
						(DmpkCoreUpddocudBean) input);

				if (output.getDefaultMessage() != null) {
					if (errorMessages == null)
						errorMessages = new HashMap<String, String>();
					errorMessages.put(archivioBean.getIdUdFolder(), output.getDefaultMessage());
				}
			//se il bean è una folder
			} else {
				DmpkCoreUpdfolderBean input = new DmpkCoreUpdfolderBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(
						StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setIdfolderin(new BigDecimal(archivioBean.getIdUdFolder()));

				XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
				xmlFascicoloIn.setIdFolderType(new BigDecimal(getExtraparams().get("idFolderType")));

				lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributixmlin(lXmlUtilitySerializer.bindXml((Object) xmlFascicoloIn));

				DmpkCoreUpdfolder dmpkCoreUpdfolder = new DmpkCoreUpdfolder();
				StoreResultBean<DmpkCoreUpdfolderBean> output = dmpkCoreUpdfolder.execute(this.getLocale(), loginBean,
						(DmpkCoreUpdfolderBean) input);

				if (output.getDefaultMessage() != null) {
					if (errorMessages == null)
						errorMessages = new HashMap<String, String>();
					errorMessages.put(archivioBean.getIdUdFolder(), output.getDefaultMessage());
				}
			}
		}
		if (errorMessages != null && !errorMessages.isEmpty()) {
			bean.setErrorMessages(errorMessages);
		}
		
		return bean;
	}
	
	//FIXME Ricavare dati protocollazione
	public ProtocollazioneBean protocollazioneDocumentiIstruttoria(ArchivioBean bean) throws Exception {
		ProtocollazioneBean lProtocollazioneBeanInput = new ProtocollazioneBean();
		String idUdDaProtocollare = bean.getIdUdDaProtocollare();
		String uoProcollante = bean.getDatiProtDocIstrUOProtocollante();
		String codUOProtocollante = uoProcollante != null && uoProcollante.startsWith("UO") ? uoProcollante.substring(2) : uoProcollante;
		lProtocollazioneBeanInput.setIdUd(StringUtils.isNotBlank(idUdDaProtocollare) ? new BigDecimal(idUdDaProtocollare) : null);
		lProtocollazioneBeanInput = getProtocolloDataSource().get(lProtocollazioneBeanInput);
				
		// Setto i mittenti
		List<MittenteProtBean> listaMittenti = new ArrayList<>();
		MittenteProtBean mittente = new MittenteProtBean();
		mittente.setIdSoggetto(bean.getDatiProtDocIstrMittenteId());
		listaMittenti.add(mittente);
		lProtocollazioneBeanInput.setListaMittenti(listaMittenti);
		
		// Setto i destinatari
		List<DestinatarioProtBean> listaDestinatari = new ArrayList<>();
		DestinatarioProtBean destinatario = new DestinatarioProtBean();
		destinatario.setTipoDestinatario("1".equalsIgnoreCase(bean.getDatiProtDocIstrDestinatarioFlgPersonaFisica()) ? "PF" : "PG");
		destinatario.setDenominazioneDestinatario(bean.getDatiProtDocIstrDestinatarioDenomCognome());
		destinatario.setCognomeDestinatario(bean.getDatiProtDocIstrDestinatarioDenomCognome());
		destinatario.setNomeDestinatario(bean.getDatiProtDocIstrDestinatarioNome());
		destinatario.setCodfiscaleDestinatario(bean.getDatiProtDocIstrCodFiscale());
		listaDestinatari.add(destinatario);
		lProtocollazioneBeanInput.setListaDestinatari(listaDestinatari);
		
		// Setto gli assegnatari
		List<AssegnazioneBean> lListAssegnatari = new ArrayList<AssegnazioneBean>();
		AssegnazioneBean lAssegnatariBean = new AssegnazioneBean();
		lAssegnatariBean.setTypeNodo("UO");
		lAssegnatariBean.setIdUo(codUOProtocollante);
		lListAssegnatari.add(lAssegnatariBean);
		lProtocollazioneBeanInput.setListaAssegnazioni(lListAssegnatari);
		
		// Setto il tipo di protocollo
		lProtocollazioneBeanInput.setFlgTipoProv("U");
		
		// Setto la UO protoollante
		// lProtocollazioneBeanInput.setUoProtocollante("UO100024");
		lProtocollazioneBeanInput.setUoProtocollante(uoProcollante);
		
		// Setto l'oggetto
		lProtocollazioneBeanInput.setOggetto(bean.getDescFolderType() + " - " + bean.getNome() + " - " + bean.getDesAllegatoDaProtocollare());
		
		// Setto la numerazione
		List<TipoNumerazioneBean> listaTipiNumerazioneDaDare = new ArrayList<TipoNumerazioneBean>();
		TipoNumerazioneBean lTipoProtocolloGenerale = new TipoNumerazioneBean();		
		lTipoProtocolloGenerale.setCategoria("PG");
		lTipoProtocolloGenerale.setIdUo(StringUtils.isNotBlank(uoProcollante) ? uoProcollante.substring(2) : null);
		listaTipiNumerazioneDaDare.add(lTipoProtocolloGenerale);
		lProtocollazioneBeanInput.setListaTipiNumerazioneDaDare(listaTipiNumerazioneDaDare);
	
		ProtocollazioneBean output = new ProtocollazioneBean();
		try {
			output = getProtocolloDataSource().update(lProtocollazioneBeanInput, null);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		return output;
	}
	
    private ProtocolloDataSource getProtocolloDataSource() {	
		
		ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource() {
		};		
		lProtocolloDataSource.setSession(getSession());
		lProtocolloDataSource.setExtraparams(getExtraparams());	
		// devo settare in ProtocolloDataSource i messages di ArchivioDataSource per mostrare a video gli errori in salvataggio dei file
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lProtocolloDataSource.setMessages(getMessages()); 
		
		return lProtocolloDataSource;
	}

	private String buildListaIdUdXml(ArchivioXmlBean bean) throws Exception {
		List<SimpleValueBean> listaIdUd = new ArrayList<SimpleValueBean>();

		SimpleValueBean lSimpleValueBean = new SimpleValueBean();
		lSimpleValueBean.setValue(bean.getIdUdFolder());
		listaIdUd.add(lSimpleValueBean);
		
		return new XmlUtilitySerializer().bindXmlList(listaIdUd);
	}	
}