/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetdatiinviopubblviaftpBean;
import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfCall_execattBean;
import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfGetdatixinvionotifmailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.LoginDataSource;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.AttachmentUDBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttachmentInvioNotEmailBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.CompilaModelloAttivitaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.NuovaPropostaAtto2CompletaDataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.CompilaListaModelliNuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskBean;
import it.eng.auriga.ui.module.layout.server.task.bean.AttrCustomToShowOutBean;
import it.eng.auriga.ui.module.layout.server.task.bean.EventoBean;
import it.eng.auriga.ui.module.layout.server.task.bean.FileDaPubblicareBean;
import it.eng.auriga.ui.module.layout.server.task.bean.ValoriEsitoOutBean;
import it.eng.auriga.ui.module.layout.server.task.bean.XmlDatiEventoOutBean;
import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraResultBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraUtility;
import it.eng.auriga.util.FTPManager;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigasendmail.bean.AurigaAttachmentMailToSendBean;
import it.eng.aurigasendmail.bean.AurigaDummyMailToSendBean;
import it.eng.aurigasendmail.bean.AurigaResultSendMail;
import it.eng.client.AurigaMailService;
import it.eng.client.DmpkProcessesGetdatiinviopubblviaftp;
import it.eng.client.DmpkWfCall_execatt;
import it.eng.client.DmpkWfGetdatixinvionotifmail;
import it.eng.client.GestioneTask;
import it.eng.client.RecuperoFile;
import it.eng.client.SimpleSendMailService;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.EseguiTaskInBean;
import it.eng.document.function.bean.EseguiTaskOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.FilePrimarioOutBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.ProtocolloRicevuto;
import it.eng.document.function.bean.RegNumPrincipale;
import it.eng.document.function.bean.RegistrazioneData;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoProvenienza;
import it.eng.document.function.bean.XmlDocumentoEventoBean;
import it.eng.fileOperation.clientws.TestoTimbro;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.TimbraUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.servlet.bean.InfoFirmaMarca;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;

@Datasource(id = "AurigaTaskDataSource")
public class AurigaTaskDataSource extends AbstractServiceDataSource<TaskBean, EventoBean> {

	private static final Logger log = Logger.getLogger(AurigaTaskDataSource.class);

	@Override
	public EventoBean call(TaskBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkWfCall_execattBean dmpkWfCall_execattInput = new DmpkWfCall_execattBean();
		dmpkWfCall_execattInput.setCodidconnectiontokenin(token);
		dmpkWfCall_execattInput.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		dmpkWfCall_execattInput.setIdprocessin(new BigDecimal(bean.getIdProcess()));
		dmpkWfCall_execattInput.setActivitynamein(bean.getActivityName());

		DmpkWfCall_execatt dmpkWfCall_execatt = new DmpkWfCall_execatt();
		StoreResultBean<DmpkWfCall_execattBean> dmpkWfCall_execattOutput = dmpkWfCall_execatt.execute(getLocale(), loginBean, dmpkWfCall_execattInput);

		if (StringUtils.isNotBlank(dmpkWfCall_execattOutput.getDefaultMessage())) {
			if (dmpkWfCall_execattOutput.isInError()) {
				throw new StoreException(dmpkWfCall_execattOutput);
			} else {
				addMessage(dmpkWfCall_execattOutput.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		EventoBean result = new EventoBean();
		result.setInstanceId(bean.getInstanceId());
		result.setIdProcess(bean.getIdProcess());
		result.setActivityName(bean.getActivityName());
		result.setIdGUIEvento(dmpkWfCall_execattOutput.getResultBean().getUrltoredirecttoout());

		if (StringUtils.isNotBlank(dmpkWfCall_execattOutput.getResultBean().getTaskinfoxmlout())) {
			XmlDatiEventoOutBean scXmlDatiEvento = new XmlDatiEventoOutBean();
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			scXmlDatiEvento = lXmlUtilityDeserializer.unbindXml(dmpkWfCall_execattOutput.getResultBean().getTaskinfoxmlout(), XmlDatiEventoOutBean.class);
			if (scXmlDatiEvento != null) {
				result.setWarningMsg(scXmlDatiEvento.getWarningMsg());
				result.setIdEventType(scXmlDatiEvento.getIdEventType());
				result.setNomeEventType(scXmlDatiEvento.getNomeEventType());
				result.setRowIdEvento(scXmlDatiEvento.getRowidEvento());
				result.setIdEvento(scXmlDatiEvento.getIdEvento());
				result.setIdUd(scXmlDatiEvento.getIdUD());
				result.setIdDocPrimario(scXmlDatiEvento.getIdDocPrimario());
				result.setIdDocType(scXmlDatiEvento.getIdDocType());
				result.setNomeDocType(scXmlDatiEvento.getNomeDocType());
				result.setFlgEventoDurativo(scXmlDatiEvento.getFlgEventoDurativo() == Flag.SETTED);
				if (result.getFlgEventoDurativo()) {
					result.setDataInizioEvento(scXmlDatiEvento.getTsInizioEvento());
					if (result.getDataInizioEvento() == null) {
						result.setDataInizioEvento(new Date());
					}
					result.setDataFineEvento(scXmlDatiEvento.getTsFineEvento());
				} else {
					result.setDataEvento(scXmlDatiEvento.getTsFineEvento());
					if (result.getDataEvento() == null) {
						result.setDataEvento(new Date());
					}
				}
				result.setFlgDocEntrata(scXmlDatiEvento.getFlgDocEntrata() == Flag.SETTED);
				if (result.getFlgDocEntrata() != null && result.getFlgDocEntrata()) {
					result.setFlgTipoProv("E");
				}
				recuperaPrimario(scXmlDatiEvento, result);
				recuperaRegNumPrincipale(scXmlDatiEvento, result);
				result.setRifOrigineProtRicevuto(scXmlDatiEvento.getRifDocRicevuto());
				result.setNroProtRicevuto(scXmlDatiEvento.getEstremiRegDocRicevuto());
				result.setAnnoProtRicevuto(scXmlDatiEvento.getAnnoDocRicevuto());
				result.setDataProtRicevuto(scXmlDatiEvento.getDtDocRicevuto());
				recuperaAllegati(scXmlDatiEvento, result);
				result.setOggetto(scXmlDatiEvento.getDesOgg());
				result.setDataDocumento(scXmlDatiEvento.getDtStesura());
				result.setNote(scXmlDatiEvento.getNoteTask());
				result.setFlgPresenzaEsito(scXmlDatiEvento.getPresenzaEsito() == Flag.SETTED);
				result.setEsito(scXmlDatiEvento.getEsito());
				recuperaValoriEsito(scXmlDatiEvento, result);
				recuperaMappaAttrToShow(scXmlDatiEvento, result);
			}
		}

		return result;
	}

	private void recuperaRegNumPrincipale(XmlDatiEventoOutBean scXmlDatiEvento, EventoBean bean) throws Exception {
		RegNumPrincipale regNumPrincipale = scXmlDatiEvento.getRegNumPrincipale();
		if (regNumPrincipale != null) {
			bean.setTipoRegistrazione(regNumPrincipale.getTipo());
			bean.setSiglaRegistrazione(regNumPrincipale.getRegistro());
			bean.setAnnoRegistrazione(regNumPrincipale.getAnno());
			bean.setNroRegistrazione(regNumPrincipale.getNro());
			bean.setDataRegistrazione(scXmlDatiEvento.getDtRegistrazione());
		}
	}

	private void recuperaPrimario(XmlDatiEventoOutBean scXmlDatiEvento, EventoBean bean) throws Exception {
		FilePrimarioOutBean filePrimario = scXmlDatiEvento.getFilePrimario();
		if (filePrimario != null) {
			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			bean.setNomeFilePrimario(filePrimario.getDisplayFilename());
			bean.setUriFilePrimario(filePrimario.getUri());
			bean.setRemoteUriFilePrimario(true);
			if (StringUtils.isNotBlank(filePrimario.getImpronta())) {
				lMimeTypeFirmaBean.setImpronta(filePrimario.getImpronta());
			}
			lMimeTypeFirmaBean.setCorrectFileName(bean.getNomeFilePrimario());
			lMimeTypeFirmaBean.setFirmato(filePrimario.getFlgFirmato() == Flag.SETTED);
			lMimeTypeFirmaBean.setFirmaValida(filePrimario.getFlgFirmato() == Flag.SETTED);
			lMimeTypeFirmaBean.setConvertibile(filePrimario.getFlgConvertibilePdf() == Flag.SETTED);
			lMimeTypeFirmaBean.setDaScansione(false);
			lMimeTypeFirmaBean.setMimetype(filePrimario.getMimetype());
			if (lMimeTypeFirmaBean.isFirmato()) {
				lMimeTypeFirmaBean.setTipoFirma(filePrimario.getDisplayFilename().toUpperCase().endsWith("P7M")
						|| filePrimario.getDisplayFilename().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
				lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(filePrimario.getFirmatari()) ? filePrimario.getFirmatari().split(",") : null);
			}
			bean.setInfoFile(lMimeTypeFirmaBean);
		}
	}

	protected void recuperaAllegati(XmlDatiEventoOutBean scXmlDatiEvento, EventoBean bean) throws Exception {
		if (scXmlDatiEvento.getAllegati() != null && scXmlDatiEvento.getAllegati().size() > 0) {
			List<AllegatoProtocolloBean> listaAllegati = new ArrayList<AllegatoProtocolloBean>();
			for (AllegatiOutBean allegato : scXmlDatiEvento.getAllegati()) {
				recuperaAllegato(listaAllegati, allegato);
			}
			bean.setListaAllegati(listaAllegati);
		}
	}

	private void recuperaAllegato(List<AllegatoProtocolloBean> listaAllegati, AllegatiOutBean allegato) throws Exception {
		AllegatoProtocolloBean lAllegatoProtocolloBean = new AllegatoProtocolloBean();
		BigDecimal idDocAllegato = StringUtils.isNotBlank(allegato.getIdDoc()) ? new BigDecimal(allegato.getIdDoc()) : null;
		lAllegatoProtocolloBean.setIdDocAllegato(idDocAllegato);
		lAllegatoProtocolloBean.setDescrizioneFileAllegato(allegato.getDescrizioneOggetto());
		lAllegatoProtocolloBean.setIdTipoFileAllegato(allegato.getIdDocType());
		lAllegatoProtocolloBean.setListaTipiFileAllegato(allegato.getIdDocType());
		lAllegatoProtocolloBean.setNomeFileAllegato(allegato.getDisplayFileName());
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lAllegatoProtocolloBean.setUriFileAllegato(allegato.getUri());
		lAllegatoProtocolloBean.setRemoteUri(true);
		if (StringUtils.isNotBlank(allegato.getImpronta())) {
			lMimeTypeFirmaBean.setImpronta(allegato.getImpronta());
		}
		lMimeTypeFirmaBean.setFirmato(allegato.getFlgFileFirmato() == Flag.SETTED);

		lMimeTypeFirmaBean.setFirmaValida(!(allegato.getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED));

		InfoFirmaMarca lInfoFirmaMarca = new InfoFirmaMarca();
		lInfoFirmaMarca.setBustaCrittograficaFattaDaAuriga(allegato.getFlgBustaCrittograficaAuriga() == Flag.SETTED);
		lInfoFirmaMarca.setFirmeNonValideBustaCrittografica(allegato.getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED);
		lInfoFirmaMarca.setFirmeExtraAuriga(allegato.getFlgFirmeExtraAuriga() == Flag.SETTED);
		lInfoFirmaMarca.setMarcaTemporaleAppostaDaAuriga(allegato.getFlgMarcaTemporaleAuriga() == Flag.SETTED);
		lInfoFirmaMarca.setDataOraMarcaTemporale(allegato.getDataOraMarcaTemporale());
		lInfoFirmaMarca.setMarcaTemporaleNonValida(allegato.getFlgMarcaTemporaleNonValida() == Flag.SETTED);
		lMimeTypeFirmaBean.setInfoFirmaMarca(lInfoFirmaMarca);

		lMimeTypeFirmaBean.setPdfProtetto(allegato.getFlgPdfProtetto() == Flag.SETTED);
		lMimeTypeFirmaBean.setPdfEditabile(allegato.getFlgPdfEditabile() == Flag.SETTED);
		lMimeTypeFirmaBean.setPdfConCommenti(allegato.getFlgPdfConCommenti() == Flag.SETTED);

		lMimeTypeFirmaBean.setConvertibile(allegato.getFlgConvertibilePdf() == Flag.SETTED);
		lMimeTypeFirmaBean.setDaScansione(false);
		lMimeTypeFirmaBean.setCorrectFileName(lAllegatoProtocolloBean.getNomeFileAllegato());
		lMimeTypeFirmaBean.setMimetype(allegato.getMimetype());
		if (lMimeTypeFirmaBean.isFirmato()) {
			lMimeTypeFirmaBean.setTipoFirma(allegato.getDisplayFileName().toUpperCase().endsWith("P7M")
					|| allegato.getDisplayFileName().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
		}
		lAllegatoProtocolloBean.setInfoFile(lMimeTypeFirmaBean);
		lAllegatoProtocolloBean.setIdAttachEmail(allegato.getIdAttachEmail());
		listaAllegati.add(lAllegatoProtocolloBean);
	}

	private void recuperaValoriEsito(XmlDatiEventoOutBean scXmlDatiEvento, EventoBean bean) throws Exception {
		if (scXmlDatiEvento.getValoriEsito() != null && scXmlDatiEvento.getValoriEsito().size() > 0) {
			Map<String, String> valoriEsito = new HashMap<String, String>();
			for (ValoriEsitoOutBean valoreEsitoBean : scXmlDatiEvento.getValoriEsito()) {
				valoriEsito.put(valoreEsitoBean.getValore(), valoreEsitoBean.getValore());
			}
			bean.setValoriEsito(valoriEsito);
		}
	}

	private void recuperaMappaAttrToShow(XmlDatiEventoOutBean scXmlDatiEvento, EventoBean bean) throws Exception {
		if (scXmlDatiEvento.getAttrCustomToShow() != null && scXmlDatiEvento.getAttrCustomToShow().size() > 0) {
			Map<String, Map<String, String>> mappaAttrToShow = new HashMap<String, Map<String, String>>();
			for (AttrCustomToShowOutBean attrToShow : scXmlDatiEvento.getAttrCustomToShow()) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("idFolder", attrToShow.getIdFolder());
				if (StringUtils.isNotEmpty(attrToShow.getCampoNomeComune())) {
					Map<String, String> paramsComplex = new HashMap<String, String>();
					StringBuffer lStringBuffer = new StringBuffer();

					paramsComplex.put("campoCodIstatComune", StringUtils.isNotEmpty(attrToShow.getCampoCodIstatComune()) ? attrToShow.getCampoCodIstatComune()
							: "");
					paramsComplex.put("nomeComune", StringUtils.isNotEmpty(attrToShow.getCampoNomeComune()) ? attrToShow.getCampoNomeComune() : "");
					paramsComplex.put("campoFrazione", StringUtils.isNotEmpty(attrToShow.getCampoFrazione()) ? attrToShow.getCampoFrazione() : "");
					paramsComplex.put("campoIndirizzo", StringUtils.isNotEmpty(attrToShow.getCampoIndirizzo()) ? attrToShow.getCampoIndirizzo() : "");
					paramsComplex.put("campoCivico", StringUtils.isNotEmpty(attrToShow.getCampoCivico()) ? attrToShow.getCampoCivico() : "");
					paramsComplex.put("campoCap", StringUtils.isNotEmpty(attrToShow.getCampoCap()) ? attrToShow.getCampoCap() : "");
					paramsComplex.put("campoUbicazione", StringUtils.isNotEmpty(attrToShow.getCampoUbicazione()) ? attrToShow.getCampoUbicazione() : "");
					paramsComplex.put("campoIdLayer", StringUtils.isNotEmpty(attrToShow.getCampoIdLayer()) ? attrToShow.getCampoIdLayer() : "");
					for (String lStrKey : paramsComplex.keySet()) {
						lStringBuffer.append(lStrKey + "=" + paramsComplex.get(lStrKey) + ";");
					}
					params.put("mostraMappa", lStringBuffer.toString());
				}
				mappaAttrToShow.put(attrToShow.getAttrName(), params);
			}
			bean.setMappaAttrToShow(mappaAttrToShow);
		}
	}

	public EventoBean salvaTask(EventoBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		boolean isPubblicazioneAtto = bean.getActivityName() != null && bean.getActivityName().toUpperCase().equals("PUBBLICAZIONE_ATTO");

		//guardare nella CallExecAtt la variabile #DocActions.InvioFtpAlbo -> se è true faccio l'invio
		boolean flgInvioFtpAlbo = getExtraparams().get("flgInvioFtpAlbo") != null && getExtraparams().get("flgInvioFtpAlbo").equalsIgnoreCase("true");		
		boolean flgInvioNotEmail = getExtraparams().get("flgInvioNotEmail") != null && getExtraparams().get("flgInvioNotEmail").equalsIgnoreCase("true");		

		if ((isPubblicazioneAtto || flgInvioFtpAlbo) && !ParametriDBUtil.getParametroDBAsBoolean(getSession(), "DISATTIVA_INVIO_PUBBL_FTP")) {
			DmpkProcessesGetdatiinviopubblviaftpBean dmpkProcessesGetdatiinviopubblviaftpInput = new DmpkProcessesGetdatiinviopubblviaftpBean();
			dmpkProcessesGetdatiinviopubblviaftpInput.setIdprocessin(new BigDecimal(bean.getIdProcess()));

			SchemaBean schemaBean = new SchemaBean();
			schemaBean.setSchema(loginBean.getSchema());

			DmpkProcessesGetdatiinviopubblviaftp dmpkProcessesGetdatiinviopubblviaftp = new DmpkProcessesGetdatiinviopubblviaftp();
			StoreResultBean<DmpkProcessesGetdatiinviopubblviaftpBean> dmpkProcessesGetdatiinviopubblviaftpOutput = dmpkProcessesGetdatiinviopubblviaftp
					.execute(getLocale(), schemaBean, dmpkProcessesGetdatiinviopubblviaftpInput);

			if (StringUtils.isNotBlank(dmpkProcessesGetdatiinviopubblviaftpOutput.getDefaultMessage())) {
				if (dmpkProcessesGetdatiinviopubblviaftpOutput.isInError()) {
					throw new StoreException(dmpkProcessesGetdatiinviopubblviaftpOutput);
				} else {
					addMessage(dmpkProcessesGetdatiinviopubblviaftpOutput.getDefaultMessage(), "", MessageType.WARNING);
				}
			}

			boolean savedInFtp = false;
			// Pubblico i file
			try {
				final String EST_TO_REMOVE = ".p7m";
				// Carico la configurazione ftp per la pubblicazione
				FTPManager lFTPManager = new FTPManager("pubblicazioneAttoFtp.properties");
				if(StringUtils.isNotBlank(dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean().getUseridaccessoftpout())) {
					// Se la store mi restituisce l'utenza di accesso imposto quella, altrimenti tengo quella configurata nel file di properties
					lFTPManager.setFtpUser(dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean().getUseridaccessoftpout());					
				}
				if(StringUtils.isNotBlank(dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean().getPasswordaccessoftpout())) {
					// Se la store mi restituisce la password di accesso imposto quella, altrimenti tengo quella configurata nel file di properties
					lFTPManager.setFtpPassword(dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean().getPasswordaccessoftpout());					
				}
				if(StringUtils.isNotBlank(dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean().getNomediraccessoftpout())) {
					// Se la store mi restituisce la cartella di root imposto quella, altrimenti tengo quella configurata nel file di properties
					lFTPManager.setFtpDir(dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean().getNomediraccessoftpout());					
				}
				File fileXml = null;
				if(StringUtils.isNotBlank(dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean().getXmlpubblicazioneout())) {
					fileXml = File.createTempFile("pubblatto", ".xml");
					FileUtils.writeStringToFile(fileXml, dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean().getXmlpubblicazioneout(), "UTF-8");
				}				
				if (fileXml == null || lFTPManager.upload(Arrays.asList(new File[] { fileXml }), Arrays.asList(new String[] { dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean().getNomexmlout() }))) {
					if(fileXml == null) {
						log.debug("Nessun file xml da pubblicare");
					} else {
						log.debug("Ho pubblicato il file " + dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean().getNomexmlout());
					}
					// Recupero la lista dei file da pubblicare
					List<FileDaPubblicareBean> listaFileDaPubblicare = XmlListaUtility.recuperaLista(dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean()
							.getFiledapubblicareout(), FileDaPubblicareBean.class);
					List<File> files = new ArrayList<File>();
					List<String> fileNames = new ArrayList<String>();
					if (listaFileDaPubblicare != null && listaFileDaPubblicare.size() > 0) {
						// Ci sono file da pubblicare
						String listaFile = null;
						String listaFileTimbroNonPoss = null;
						for (FileDaPubblicareBean fileDaPubbl : listaFileDaPubblicare) {
							// Controllo se è un file da sbustare e timbrare
							if ((fileDaPubbl.getDaTimbrare().equalsIgnoreCase("1"))) {
								// Estraggo il file da sbustare e lo salvo nei temporanei per potervi accedere sempre tramite puntamento
								File fileDaSbustare = StorageImplementation.getStorage().extractFile(fileDaPubbl.getUri());
								File fileDaSbustareApp = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(fileDaSbustare));
								// Sbusto il file
								InfoFileUtility lInfoFileUtility = new InfoFileUtility();
								InputStream fileSbustatoStream = lInfoFileUtility.sbusta(fileDaSbustareApp.toURI().toString(), fileDaPubbl.getNomeFile());
								String uriFileSbustato = StorageImplementation.getStorage().storeStream(fileSbustatoStream);

								// Carico le impostazioni per la timbratura
								// String mimetype = "application/pdf";
								String mimetype = StringUtils.isNotBlank(fileDaPubbl.getMimetype()) ? fileDaPubbl.getMimetype() : "";
								OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
								lOpzioniTimbroBean.setMimetype(mimetype);
								lOpzioniTimbroBean.setUri(uriFileSbustato);
								lOpzioniTimbroBean.setNomeFile(fileDaPubbl.getNomeFile());
								lOpzioniTimbroBean.setIdUd(fileDaPubbl.getIdUd());
								TimbraUtility timbraUtility = new TimbraUtility();
								lOpzioniTimbroBean = timbraUtility.loadSegnatureRegistrazioneDefault(lOpzioniTimbroBean, getSession(), getLocale());

								// Setto i parametri del timbro non di default
								lOpzioniTimbroBean
								.setPosizioneTimbro((ParametriDBUtil.getParametroDB(getSession(), "TIMBRO_ATTO_DA_PUBBL_POSIZIONE")) != null ? ParametriDBUtil
										.getParametroDB(getSession(), "TIMBRO_ATTO_DA_PUBBL_POSIZIONE") : "altoSn");
								lOpzioniTimbroBean
								.setRotazioneTimbro((ParametriDBUtil.getParametroDB(getSession(), "TIMBRO_ATTO_DA_PUBBL_ROTAZIONE")) != null ? ParametriDBUtil
										.getParametroDB(getSession(), "TIMBRO_ATTO_DA_PUBBL_ROTAZIONE") : "verticale");
								lOpzioniTimbroBean
								.setTipoPagina((ParametriDBUtil.getParametroDB(getSession(), "TIMBRO_ATTO_DA_PUBBL_SEL_PAG")) != null ? ParametriDBUtil
										.getParametroDB(getSession(), "TIMBRO_ATTO_DA_PUBBL_SEL_PAG") : "tutte");
								lOpzioniTimbroBean
								.setPaginaDa((ParametriDBUtil.getParametroDB(getSession(), "TIMBRO_ATTO_DA_PUBBL_PAG_DA")) != null ? ParametriDBUtil
										.getParametroDB(getSession(), "TIMBRO_ATTO_DA_PUBBL_PAG_DA") : "1");
								lOpzioniTimbroBean
								.setPaginaA((ParametriDBUtil.getParametroDB(getSession(), "TIMBRO_ATTO_DA_PUBBL_PAG_A")) != null ? ParametriDBUtil
										.getParametroDB(getSession(), "TIMBRO_ATTO_DA_PUBBL_PAG_A") : "1");
								// Timbro il file
								TimbraResultBean lTimbraResultBean = timbraUtility.timbra(lOpzioniTimbroBean, getSession());
								// Verifico se la timbratura è andata a buon fine
								if (lTimbraResultBean.isResult()) {
									// Aggiungo il file timbrato nella lista dei file da pubblicare
									files.add(StorageImplementation.getStorage().extractFile(lTimbraResultBean.getUri()));
								} else {
									// // La timbratura è fallita, pubblico il file sbustato
									// files.add(StorageImplementation.getStorage().extractFile(uriFileSbustato));
									String errorMessage = "Si è verificato un errore durante la timbratura del file "
											+ StringUtils.remove(fileDaPubbl.getNomeFile(), EST_TO_REMOVE);
									if (StringUtils.isNotBlank(lTimbraResultBean.getError())) {
										errorMessage += ": " + lTimbraResultBean.getError();
									}
									throw new Exception(errorMessage);
								}
							} else {
								// Non devo sbustare il file, lo aggiungo direttamente alla lista dei file da pubblicare
								files.add(StorageImplementation.getStorage().extractFile(fileDaPubbl.getUri()));
								// Tengo traccia dei nomi dei file che andranno pubblicati senza timbro
								if (listaFileTimbroNonPoss == null) {
									listaFileTimbroNonPoss = StringUtils.remove(fileDaPubbl.getNomeFile(), EST_TO_REMOVE);
								} else {
									listaFileTimbroNonPoss += ", " + StringUtils.remove(fileDaPubbl.getNomeFile(), EST_TO_REMOVE);
								}
							}
							String nomeFileDaPubblicare = StringUtils.remove(fileDaPubbl.getNomeFile(), EST_TO_REMOVE);
							fileNames.add(nomeFileDaPubblicare);
							if (listaFile == null) {
								listaFile = nomeFileDaPubblicare;
							} else {
								listaFile += ", " + nomeFileDaPubblicare;
							}
						}
						if (lFTPManager.upload(dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean().getNomecartellaout(), files, fileNames)) {
							log.debug("Ho pubblicato i file " + listaFile + " dentro la cartella " + dmpkProcessesGetdatiinviopubblviaftpOutput.getResultBean().getNomecartellaout());
							savedInFtp = true;
							// if (StringUtils.isNotBlank(listaFileTimbroNonPoss)){
							// final String message =
							// "I seguenti file sono stati inviati in pubblicazione senza timbro dato che il formato del/i file non ne consente l’apposizione: ";
							// addMessage(message + listaFileTimbroNonPoss, "", MessageType.WARNING);
							// }
						}
					}

				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

			if (!savedInFtp) {
				throw new StoreException("Si è verificato un errore durante la pubblicazione dell'atto via FTP");
			}
		}

		// if(bean.getActivityName() != null && bean.getActivityName().toUpperCase().equals("VERIFICA_COLLEGIO_SINDACALE")) {
		// invioNotificaVerificaCollegioSindacale(bean);
		// }

		EseguiTaskInBean lEseguiTaskInBean = new EseguiTaskInBean();

		lEseguiTaskInBean.setInstanceId(bean.getInstanceId());
		lEseguiTaskInBean.setActivityName(bean.getActivityName());
		lEseguiTaskInBean.setIdProcess(bean.getIdProcess());

		lEseguiTaskInBean.setIdUd(bean.getIdUd());
		lEseguiTaskInBean.setIdDocPrimario(bean.getIdDocPrimario());

		if (StringUtils.isNotBlank(bean.getIdDocType())) {
			XmlDocumentoEventoBean documentoXml = new XmlDocumentoEventoBean();

			documentoXml.setTipoDocumento(bean.getIdDocType());

			if (bean.getFlgTipoProv() != null) {
				TipoProvenienza tipoProvenienza = null;
				if (bean.getFlgTipoProv().equalsIgnoreCase("E")) {
					tipoProvenienza = TipoProvenienza.ENTRATA;
				}
				documentoXml.setFlgTipoProv(tipoProvenienza);
			}

			documentoXml.setOggetto(bean.getOggetto());

			if (StringUtils.isNotBlank(bean.getNroProtRicevuto()) && (StringUtils.isNotBlank(bean.getAnnoProtRicevuto()) || bean.getDataProtRicevuto() != null)) {
				ProtocolloRicevuto protocolloRicevuto = new ProtocolloRicevuto();
				protocolloRicevuto.setOrigine(bean.getRifOrigineProtRicevuto());
				protocolloRicevuto.setNumero(bean.getNroProtRicevuto());
				protocolloRicevuto.setAnno(bean.getAnnoProtRicevuto());
				protocolloRicevuto.setData(bean.getDataProtRicevuto());
				documentoXml.setProtocolloRicevuto(protocolloRicevuto);
			}

			if (StringUtils.isNotBlank(bean.getNroRegistrazione())
					&& (StringUtils.isNotBlank(bean.getAnnoRegistrazione()) || bean.getDataRegistrazione() != null)) {
				List<RegistrazioneData> registrazioniDate = new ArrayList<RegistrazioneData>();
				RegistrazioneData regData = new RegistrazioneData();
				regData.setTipo(StringUtils.isNotBlank(bean.getTipoRegistrazione()) ? bean.getTipoRegistrazione() : "PG");
				regData.setRegistro(bean.getSiglaRegistrazione());
				regData.setNro(bean.getNroRegistrazione());
				regData.setAnno(bean.getAnnoRegistrazione());
				regData.setDataRegistrazione(bean.getDataRegistrazione());
				registrazioniDate.add(regData);
				documentoXml.setRegistrazioniDate(registrazioniDate);
			}

			documentoXml.setDataStesura(bean.getDataDocumento());
			documentoXml.setNomeDocType(bean.getNomeDocType());

			lEseguiTaskInBean.setDocumentoXml(documentoXml);

		}

		lEseguiTaskInBean.setIdEvento(bean.getIdEvento());
		lEseguiTaskInBean.setIdEventType(bean.getIdEventType());
		lEseguiTaskInBean.setFlgEventoDurativo(bean.getFlgEventoDurativo());
		lEseguiTaskInBean.setDataEvento(bean.getDataEvento());
		lEseguiTaskInBean.setDataFineEvento(bean.getDataFineEvento());
		lEseguiTaskInBean.setDataInizioEvento(bean.getDataInizioEvento());
		lEseguiTaskInBean.setNoteEvento(bean.getNote());

		lEseguiTaskInBean.setFlgPresenzaEsito(bean.getFlgPresenzaEsito());
		lEseguiTaskInBean.setEsito(bean.getEsito());

		if (bean.getAttributiAdd() != null && bean.getAttributiAdd().size() > 0) {
			SezioneCache scAttributi = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(bean.getIdProcess(), bean.getAttributiAdd(),
					bean.getTipiAttributiAdd(), getSession());
			lEseguiTaskInBean.setScAttributiAdd(scAttributi);
		}

		// Salvo il file PRIMARIO
		if (StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())) {
			FilePrimarioBean lFilePrimarioBean = new FilePrimarioBean();
			File lFile = null;
			// Il file è esterno
			if (bean.getRemoteUriFilePrimario()) {
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(bean.getUriFilePrimario());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), loginBean, lFileExtractedIn);
				lFile = out.getExtracted();
			} else {
				// File locale
				lFile = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
			}
			lFilePrimarioBean.setFile(lFile);
			MimeTypeFirmaBean lMimeTypeFirmaBean = bean.getInfoFile();
			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.PRIMARIO);
			GenericFile lGenericFile = new GenericFile();
			setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
			lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
			lGenericFile.setDisplayFilename(bean.getNomeFilePrimario());
			lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
			lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
			lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
			lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
			if (lMimeTypeFirmaBean.isDaScansione()) {
				lGenericFile.setDaScansione(Flag.SETTED);
				lGenericFile.setDataScansione(new Date());
				lGenericFile.setIdUserScansione(loginBean.getIdUser() + "");
			}
			lFileInfoBean.setAllegatoRiferimento(lGenericFile);
			lFilePrimarioBean.setIdDocumento(bean.getIdDocPrimario() != null ? new BigDecimal(bean.getIdDocPrimario()) : null);
			lFilePrimarioBean.setIsNewOrChanged(bean.getIdDocPrimario() == null || "".equals(bean.getIdDocPrimario()) || bean.getIsDocPrimarioChanged());
			lFilePrimarioBean.setInfo(lFileInfoBean);
			lEseguiTaskInBean.setFilePrimario(lFilePrimarioBean);
		}

		if (bean.getListaAllegati() != null) {
			List<BigDecimal> idDocumento = new ArrayList<BigDecimal>();
			List<String> descrizione = new ArrayList<String>();
			List<Integer> docType = new ArrayList<Integer>();
			List<File> fileAllegati = new ArrayList<File>();
			List<Boolean> isNull = new ArrayList<Boolean>();
			List<Boolean> isNewOrChanged = new ArrayList<Boolean>();
			List<String> displayFilename = new ArrayList<String>();
			List<FileInfoBean> infos = new ArrayList<FileInfoBean>();
			int i = 1;
			for (AllegatoProtocolloBean allegato : bean.getListaAllegati()) {
				idDocumento.add(allegato.getIdDocAllegato());
				descrizione.add(allegato.getDescrizioneFileAllegato());
				docType.add(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato()) ? Integer.valueOf(allegato.getListaTipiFileAllegato()) : null);
				displayFilename.add(allegato.getNomeFileAllegato());
				if (StringUtils.isNotBlank(allegato.getUriFileAllegato()) && StringUtils.isNotBlank(allegato.getNomeFileAllegato())) {
					isNull.add(false);
					isNewOrChanged.add(allegato.getIdDocAllegato() == null || allegato.getIsChanged());
					File lFile = null;
					if (allegato.getRemoteUri()) {
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileAllegato());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), loginBean, lFileExtractedIn);
						lFile = out.getExtracted();
					} else {
						// File locale
						lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
					}
					if (allegato.getIdDocAllegato() == null || allegato.getIsChanged())
						fileAllegati.add(lFile);
					MimeTypeFirmaBean lMimeTypeFirmaBean = allegato.getInfoFile();
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFile = new GenericFile();
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(allegato.getNomeFileAllegato());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());

					if (lMimeTypeFirmaBean.isDaScansione()) {
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(loginBean.getIdUser() + "");
					}
					lFileInfoBean.setPosizione(i);
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					infos.add(lFileInfoBean);
				} else {
					infos.add(new FileInfoBean());
					isNull.add(true);
					isNewOrChanged.add(null);
				}
				i++;
			}
			AllegatiBean lAllegatiBean = new AllegatiBean();
			lAllegatiBean.setIdDocumento(idDocumento);
			lAllegatiBean.setDescrizione(descrizione);
			lAllegatiBean.setDisplayFilename(displayFilename);
			lAllegatiBean.setDocType(docType);
			lAllegatiBean.setIsNull(isNull);
			lAllegatiBean.setIsNewOrChanged(isNewOrChanged);
			lAllegatiBean.setFileAllegati(fileAllegati);
			lAllegatiBean.setInfo(infos);
			lEseguiTaskInBean.setAllegati(lAllegatiBean);
		}
		
		if(flgInvioNotEmail) {
			invioNotificaEmail(bean);
		}
		
		GestioneTask lGestioneTask = new GestioneTask();
		EseguiTaskOutBean lEseguiTaskOutBean = lGestioneTask.salvatask(getLocale(), loginBean, lEseguiTaskInBean);

		if (StringUtils.isNotBlank(lEseguiTaskOutBean.getWarningMessage())) {
			addMessage(lEseguiTaskOutBean.getWarningMessage(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(lEseguiTaskOutBean.getDefaultMessage())) {
			throw new StoreException(lEseguiTaskOutBean);
		}

		StringBuffer lStringBuffer = new StringBuffer();
		if (lEseguiTaskOutBean.getFileInErrors() != null && lEseguiTaskOutBean.getFileInErrors().size() > 0) {
			for (String lStrFileInError : lEseguiTaskOutBean.getFileInErrors().values()) {
				lStringBuffer.append("; " + lStrFileInError);
			}
			addMessage(lStringBuffer.toString(), "", MessageType.WARNING);
		}
		
		if(bean.getListaRecordModelli() != null && bean.getListaRecordModelli().size() > 0) {
			NuovaPropostaAtto2CompletaBean dettaglioBean = bean.getDettaglioBean();			
			CompilaListaModelliNuovaPropostaAtto2CompletaBean lCompilaListaModelliNuovaPropostaAtto2CompletaBean = new CompilaListaModelliNuovaPropostaAtto2CompletaBean();
			lCompilaListaModelliNuovaPropostaAtto2CompletaBean.setIdUd(bean.getIdUd());
			lCompilaListaModelliNuovaPropostaAtto2CompletaBean.setProcessId(bean.getIdProcess());
			lCompilaListaModelliNuovaPropostaAtto2CompletaBean.setDettaglioBean(dettaglioBean);
			lCompilaListaModelliNuovaPropostaAtto2CompletaBean.setListaRecordModelli(bean.getListaRecordModelli());		
			lCompilaListaModelliNuovaPropostaAtto2CompletaBean = getNuovaPropostaAtto2CompletaDataSource().compilazioneAutomaticaListaModelliPdf(lCompilaListaModelliNuovaPropostaAtto2CompletaBean);
			if(lCompilaListaModelliNuovaPropostaAtto2CompletaBean.getListaRecordModelli() != null && lCompilaListaModelliNuovaPropostaAtto2CompletaBean.getListaRecordModelli().size() > 0) {
				for(int i = 0; i < lCompilaListaModelliNuovaPropostaAtto2CompletaBean.getListaRecordModelli().size(); i++) {
					CompilaModelloAttivitaBean lCompilaModelloAttivitaBean = lCompilaListaModelliNuovaPropostaAtto2CompletaBean.getListaRecordModelli().get(i);					
					String descrizioneFileAllegato = lCompilaModelloAttivitaBean.getDescrizione();
					String nomeFileAllegato = lCompilaModelloAttivitaBean.getNomeFile() + ".pdf";
					String uriFileAllegato = lCompilaModelloAttivitaBean.getUriFileGenerato();
					MimeTypeFirmaBean infoAllegato = lCompilaModelloAttivitaBean.getInfoFileGenerato();						
					String idTipoModello = lCompilaModelloAttivitaBean.getIdTipoDoc();
					String nomeTipoModello = lCompilaModelloAttivitaBean.getNomeTipoDoc();
					int posModello = -1;
					if (dettaglioBean.getListaAllegati() != null) {
						for (int j = 0; j < dettaglioBean.getListaAllegati().size(); j++) {
							if (dettaglioBean.getListaAllegati().get(j).getListaTipiFileAllegato() != null && dettaglioBean.getListaAllegati().get(j).getListaTipiFileAllegato().equalsIgnoreCase(lCompilaModelloAttivitaBean.getIdTipoDoc())) {
								posModello = j;
							}
						}
					}
					AllegatoProtocolloBean lAllegatoProtocolloBean = new AllegatoProtocolloBean();
					if (posModello != -1) {
						lAllegatoProtocolloBean = dettaglioBean.getListaAllegati().get(posModello);
					}
					boolean flgParere = lCompilaModelloAttivitaBean.getFlgParere() != null && lCompilaModelloAttivitaBean.getFlgParere();									
					boolean flgParteDispositivo = lCompilaModelloAttivitaBean.getFlgParteDispositivo() != null && lCompilaModelloAttivitaBean.getFlgParteDispositivo();									
					boolean flgNoPubbl = lCompilaModelloAttivitaBean.getFlgNoPubbl() != null && lCompilaModelloAttivitaBean.getFlgNoPubbl();									
					boolean flgPubblicaSeparato = lCompilaModelloAttivitaBean.getFlgPubblicaSeparato() != null && lCompilaModelloAttivitaBean.getFlgPubblicaSeparato();														
					lAllegatoProtocolloBean.setFlgParere(flgParere);
					if(flgParere) {
						lAllegatoProtocolloBean.setFlgParteDispositivo(false);
						lAllegatoProtocolloBean.setFlgNoPubblAllegato(flgNoPubbl);
						lAllegatoProtocolloBean.setFlgPubblicaSeparato(true);
					} else {
						lAllegatoProtocolloBean.setFlgParteDispositivo(flgParteDispositivo);
						if(!flgParteDispositivo) {
							lAllegatoProtocolloBean.setFlgNoPubblAllegato(true);
							lAllegatoProtocolloBean.setFlgPubblicaSeparato(false);
						} else {
							lAllegatoProtocolloBean.setFlgNoPubblAllegato(flgNoPubbl);	
							lAllegatoProtocolloBean.setFlgPubblicaSeparato(flgPubblicaSeparato);
						}
					}
					lAllegatoProtocolloBean.setNomeFileAllegato(nomeFileAllegato);
					lAllegatoProtocolloBean.setUriFileAllegato(uriFileAllegato);
					lAllegatoProtocolloBean.setDescrizioneFileAllegato(descrizioneFileAllegato);
					lAllegatoProtocolloBean.setListaTipiFileAllegato(idTipoModello);
					lAllegatoProtocolloBean.setIdTipoFileAllegato(idTipoModello);
					lAllegatoProtocolloBean.setDescTipoFileAllegato(nomeTipoModello);
					lAllegatoProtocolloBean.setRemoteUri(false);
					lAllegatoProtocolloBean.setIsChanged(true);
					lAllegatoProtocolloBean.setNomeFileVerPreFirma(nomeFileAllegato);
					lAllegatoProtocolloBean.setUriFileVerPreFirma(uriFileAllegato);
					lAllegatoProtocolloBean.setInfoFileVerPreFirma(infoAllegato);
					lAllegatoProtocolloBean.setImprontaVerPreFirma(infoAllegato.getImpronta());
					lAllegatoProtocolloBean.setInfoFile(infoAllegato);					
					if (posModello == -1) {
						if (dettaglioBean.getListaAllegati() == null || dettaglioBean.getListaAllegati().size() == 0) {
							dettaglioBean.setListaAllegati(new ArrayList<AllegatoProtocolloBean>());							
						}
						dettaglioBean.getListaAllegati().add(0, lAllegatoProtocolloBean);
					} else {
						dettaglioBean.getListaAllegati().set(posModello, lAllegatoProtocolloBean);
					}					
				}
				getNuovaPropostaAtto2CompletaDataSource().update(dettaglioBean, null);
			}
		}

		return bean;

	}
	
	private NuovaPropostaAtto2CompletaDataSource getNuovaPropostaAtto2CompletaDataSource() {
		NuovaPropostaAtto2CompletaDataSource lNuovaPropostaAtto2CompletaDataSource = new NuovaPropostaAtto2CompletaDataSource();
		lNuovaPropostaAtto2CompletaDataSource.setSession(getSession());
		lNuovaPropostaAtto2CompletaDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lNuovaPropostaAtto2CompletaDataSource.setMessages(getMessages()); 		
		return lNuovaPropostaAtto2CompletaDataSource;
	}

	private void invioNotificaEmail(EventoBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		List<String> fileDaTimbrareNonConvertiti = new ArrayList<String>();
		List<AttachmentUDBean> lListAttachments = new ArrayList<AttachmentUDBean>();

		if(StringUtils.isBlank(bean.getInvioNotEmailIdCasellaMittente())) {
			try {
				// *******************************************************
				// Invio la mail in modalita light (senza AurigaMail)
				// *******************************************************		
				AurigaDummyMailToSendBean lDummyMailToSendBean = new AurigaDummyMailToSendBean();				
				lDummyMailToSendBean.setSubject(bean.getInvioNotEmailSubject());
				lDummyMailToSendBean.setBody(bean.getInvioNotEmailBody());
				lDummyMailToSendBean.setHtml(true);
				lDummyMailToSendBean.setFrom(bean.getInvioNotEmailIndirizzoMittente());
				List<String> destinatari = new ArrayList<String>();
				if(bean.getInvioNotEmailDestinatari() != null) {
					for(SimpleValueBean lSimpleValueBean : bean.getInvioNotEmailDestinatari()) {
						destinatari.add(lSimpleValueBean.getValue());
					}
				}
				lDummyMailToSendBean.setAddressTo(destinatari);
				List<String> destinatariCC = new ArrayList<String>();
				if(bean.getInvioNotEmailDestinatariCC() != null) {
					for(SimpleValueBean lSimpleValueBean : bean.getInvioNotEmailDestinatariCC()) {
						destinatariCC.add(lSimpleValueBean.getValue());
					}
				}
				lDummyMailToSendBean.setAddressCc(destinatariCC);
				List<String> destinatariCCN = new ArrayList<String>();
				if(bean.getInvioNotEmailDestinatariCCN() != null) {
					for(SimpleValueBean lSimpleValueBean : bean.getInvioNotEmailDestinatariCCN()) {
						destinatariCCN.add(lSimpleValueBean.getValue());
					}
				}
				lDummyMailToSendBean.setAddressBcc(destinatariCCN);
				//FEDERICA BUONO se bean.getInvioNotEmailAttachment()
				if (bean.getInvioNotEmailAttachment() != null && !bean.getInvioNotEmailAttachment().isEmpty()) {
					List<SenderAttachmentsBean> lista = prepareAttachByteContent(bean.getInvioNotEmailAttachment(), fileDaTimbrareNonConvertiti,
							lListAttachments, loginBean, bean.getIdUd());
					
					List<AurigaAttachmentMailToSendBean> listaAurigaAttachmentMailToSend = new ArrayList<>();
					
					for (SenderAttachmentsBean senderAttachmentsBean : lista) {
						AurigaAttachmentMailToSendBean attach = new AurigaAttachmentMailToSendBean();
						attach.setFile(senderAttachmentsBean.getFile());
						attach.setFilename(senderAttachmentsBean.getFilename());
						listaAurigaAttachmentMailToSend.add(attach);
					}

					lDummyMailToSendBean.setAttachmentMailToSendBeans(listaAurigaAttachmentMailToSend);
				}

				List<String> errorMessages = new ArrayList<String>();
				AurigaResultSendMail lResultSendMail = new SimpleSendMailService().sendfromconfigured(Locale.ITALY, lDummyMailToSendBean, "helpdesk");
				if (!lResultSendMail.isInviata()) {
					errorMessages = lResultSendMail.getErrori();
				}
				if (errorMessages.size() > 0) {
					String listErrorMessages = "";
					for (int i = 0; i < errorMessages.size(); i++) {
						if (errorMessages.get(i) != null)
							listErrorMessages = listErrorMessages + errorMessages.get(i) + "\n";
					}
					throw new Exception(listErrorMessages);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				if(bean.getInvioNotEmailFlgInvioMailXComplTask()) {
					throw new StoreException("Fallito invio notifica e-mail: " + e.getMessage());
				} else {
					addMessage("Fallito invio notifica e-mail: " + e.getMessage(), "", MessageType.WARNING);
				}
			}
		} else {
			SenderBean sender = new SenderBean();
			sender.setIdUtenteModPec(loginBean.getSpecializzazioneBean().getIdUtenteModPec());
			sender.setSubject(bean.getInvioNotEmailSubject());
			sender.setBody(bean.getInvioNotEmailBody());
			sender.setIsHtml(true);
			sender.setAccount(bean.getInvioNotEmailIndirizzoMittente());
			sender.setAddressFrom(bean.getInvioNotEmailIndirizzoMittente());
			sender.setAliasAddressFrom(bean.getInvioNotEmailAliasIndirizzoMittente());
			
			// Setto anche il Reply-To
			if(StringUtils.isNotBlank(sender.getAliasAddressFrom()) && StringUtils.isNotBlank(loginBean.getUserid())) {
				sender.setReplyTo(getEmailReplyTo(loginBean.getUserid()));	
			}
			
			List<String> destinatari = new ArrayList<String>();
			if(bean.getInvioNotEmailDestinatari() != null) {
				for(SimpleValueBean lSimpleValueBean : bean.getInvioNotEmailDestinatari()) {
					destinatari.add(lSimpleValueBean.getValue());
				}
			}
			sender.setAddressTo(destinatari);
			List<String> destinatariCC = new ArrayList<String>();
			if(bean.getInvioNotEmailDestinatariCC() != null) {
				for(SimpleValueBean lSimpleValueBean : bean.getInvioNotEmailDestinatariCC()) {
					destinatariCC.add(lSimpleValueBean.getValue());
				}
			}
			sender.setAddressCc(destinatariCC);
			List<String> destinatariCCN = new ArrayList<String>();
			if(bean.getInvioNotEmailDestinatariCCN() != null) {
				for(SimpleValueBean lSimpleValueBean : bean.getInvioNotEmailDestinatariCCN()) {
					destinatariCCN.add(lSimpleValueBean.getValue());
				}
			}
			sender.setAddressBcc(destinatariCCN);
			sender.setIsPec(bean.getInvioNotEmailFlgPEC() != null && bean.getInvioNotEmailFlgPEC());
			
			if (bean.getInvioNotEmailAttachment() != null && !bean.getInvioNotEmailAttachment().isEmpty()) {
				List<SenderAttachmentsBean> lista = prepareAttachByteContent(bean.getInvioNotEmailAttachment(), fileDaTimbrareNonConvertiti,
						lListAttachments, loginBean, bean.getIdUd());
				sender.setAttachments(lista);
			}

			try {
				ResultBean<EmailSentReferenceBean> output = AurigaMailService.getMailSenderService().sendandsave(locale, sender);
				if(output.isInError()) {
					throw new Exception(output.getDefaultMessage());
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				if(bean.getInvioNotEmailFlgInvioMailXComplTask()) {
					throw new StoreException("Fallito invio notifica e-mail: " + e.getMessage());
				} else {
					addMessage("Fallito invio notifica e-mail: " + e.getMessage(), "", MessageType.WARNING);
				}
			}
		}

		
/**
 *  commentato perchè non vogliamo che dia questi warning se l'invio della notifica è silente
 */
//		if (fileDaTimbrareNonConvertiti != null && fileDaTimbrareNonConvertiti.size() > 0) {
//			boolean first = true;
//			StringBuffer lStringBuffer = new StringBuffer("Il formato del/i file ");
//			if (first)
//				first = false;
//			else
//				lStringBuffer.append(";");
//			for (String lString : fileDaTimbrareNonConvertiti) {
//				lStringBuffer.append(lString);
//			}
//			lStringBuffer.append(" non consente di apporvi il timbro con la segnatura");
//			addMessage(lStringBuffer.toString(), lStringBuffer.toString(), MessageType.WARNING);
//		}
	}

	protected List<SenderAttachmentsBean> prepareAttachByteContent(List<AttachmentInvioNotEmailBean> list, List<String> fileDaTimbrareNonConvertiti,
			List<AttachmentUDBean> lListAttachments, AurigaLoginBean loginBean, String idUd)
					throws Exception, StorageException, FileNotFoundException, IOException {
		
		prepareAttach(list, fileDaTimbrareNonConvertiti, lListAttachments, idUd);

		List<SenderAttachmentsBean> lista = new ArrayList<SenderAttachmentsBean>();
		// Preparo gli attachment
		for (AttachmentUDBean lAttachmentUDBean : lListAttachments) {
			SenderAttachmentsBean lAttach = new SenderAttachmentsBean();
			if (lAttachmentUDBean.getRemoteUri()) {
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(lAttachmentUDBean.getUriAttach());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), loginBean, lFileExtractedIn);
				lAttach.setFile(out.getExtracted());
			} else {
				lAttach.setFile(StorageImplementation.getStorage().extractFile(lAttachmentUDBean.getUriAttach()));
			}
			lAttach.setFilename(lAttachmentUDBean.getFileNameAttach());
			lAttach.setFirmato(lAttachmentUDBean.getFirmato());
			lAttach.setMimetype(lAttachmentUDBean.getMimetype());
			lAttach.setOriginalName(lAttachmentUDBean.getFileNameAttach());
			lista.add(lAttach);
		}
		return lista;
	}

	protected void prepareAttach(List<AttachmentInvioNotEmailBean> list, List<String> fileDaTimbrareNonConvertiti,
			List<AttachmentUDBean> lListAttachments, String idUd) throws Exception {
		
		for (AttachmentInvioNotEmailBean lAttach : list) {
			if (StringUtils.isNotBlank(lAttach.getFlgTimbrato()) &&  lAttach.getFlgTimbrato().equals("1")) {
				log.debug("L'attachment " + lAttach.getNomeFile() + " è da timbrare");
				generaFileTimbrato(lAttach, lListAttachments, fileDaTimbrareNonConvertiti, idUd);
			} else {
				AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lAttach);
				lListAttachments.add(lAttachmentUDBean);
			}
		}
	}

	private void generaFileTimbrato(AttachmentInvioNotEmailBean lAttach, List<AttachmentUDBean> lListAttachments,
			List<String> fileDaTimbrareNonConvertiti, String idUd) throws Exception {
		File fileDaTimbrare = null;
		String uriDaTimbrare = "";
		TimbraUtil lTimbraUtil = new TimbraUtil();
		// Se il file da timbrare non è pdf e non è convertibile genero alert
		String nomeFileDaTimbrare = lAttach.getNomeFile();
		if (lAttach.getFlgConvertibile() != null &&  lAttach.getFlgConvertibile().equals("0")  
				&& lAttach.getMimetype() != null && !lAttach.getMimetype().equalsIgnoreCase("application/pdf")) {
			log.debug("Mi risulta che non è PDF e non è convertibile, lo aggiungo alla lista dei non timbrati");
			fileDaTimbrareNonConvertiti.add(lAttach.getNomeFile());
			AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lAttach);
			lListAttachments.add(lAttachmentUDBean);
			return;
		} else {
			log.debug("Recupero il file");
			// Se è da convertire
			RecuperoFile lRecuperoFile = new RecuperoFile();
			AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(lAttach.getUri());
			FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);
			uriDaTimbrare = lAttach.getUri();
			if (lAttach.getMimetype() != null && !lAttach.getMimetype().equalsIgnoreCase("application/pdf") && 
					lAttach.getFlgConvertibile() != null &&  lAttach.getFlgConvertibile().equals("1")) {
				log.debug("Mi risulta non pdf e convertibile, provo a convertirlo");
				try {
					String uriFilePdf = StorageImplementation.getStorage().storeStream(
							lTimbraUtil.converti(out.getExtracted(), lAttach.getNomeFile()));
					uriDaTimbrare = uriFilePdf;
					fileDaTimbrare = StorageImplementation.getStorage().extractFile(uriFilePdf);
					if (!lAttach.getMimetype().equals("application/pdf")) {
						nomeFileDaTimbrare = lAttach.getNomeFile() + ".pdf";
					}
				} catch (Exception e) {
					log.error("Non sono riuscito a convertirlo per " + e.getMessage() + ", lo aggiungo alla lista dei non timbrati ", e);
					fileDaTimbrareNonConvertiti.add(lAttach.getNomeFile());
					AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lAttach);
					lListAttachments.add(lAttachmentUDBean);
					return;
				}
			} else {
				log.debug("Mi risulta pdf");
				fileDaTimbrare = out.getExtracted();
			}
		}
		OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAttachEmail");

		// timbra
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
		lOpzioniTimbroBean.setMimetype("application/pdf");
		lOpzioniTimbroBean.setUri(uriDaTimbrare);
		lOpzioniTimbroBean.setNomeFile(nomeFileDaTimbrare);
		lOpzioniTimbroBean.setIdUd(idUd);
		TimbraUtility timbraUtility = new TimbraUtility();
		lOpzioniTimbroBean = timbraUtility.loadSegnatureRegistrazioneDefault(lOpzioniTimbroBean, getSession(), getLocale());
		TestoTimbro lTestoTimbro = new TestoTimbro();
		lTestoTimbro.setTesto(StringUtils.isNotBlank(lOpzioniTimbroBean.getTesto()) ? lOpzioniTimbroBean.getTesto() : lOpzioniTimbroBean.getTestoIntestazione());
		TestoTimbro lTestoIntestazioneTimbro = new TestoTimbro();
		lTestoIntestazioneTimbro.setTesto(lOpzioniTimbroBean.getTestoIntestazione());
		lOpzioniTimbroAttachEmail.setIntestazioneTimbro(lTestoIntestazioneTimbro);
		lOpzioniTimbroAttachEmail.setTestoTimbro(lTestoTimbro);
		
		InputStream timbrato = null;
		log.debug("Provo a timbrarlo");
		try {
			boolean generaPdfA = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "TIMBRATURA_ABILITA_PDFA");
			timbrato = lTimbraUtil.timbra(fileDaTimbrare, nomeFileDaTimbrare, lOpzioniTimbroAttachEmail, generaPdfA);
		} catch (Exception e) {
			log.error("Non sono riuscito a timbrarlo per " + e.getMessage() + ", lo aggiungo alla lista dei non timbrati ", e);
			fileDaTimbrareNonConvertiti.add(lAttach.getNomeFile());
			AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lAttach);
			lListAttachments.add(lAttachmentUDBean);
			return;
		}
		// se il file che si timbra è firmato digitalmente
		// OR non è un pdf 
		// negli allegati mettiamo sia il file originale che quello timbrato con suffisso _con_ segnatura.pdf

		if (lAttach.getFlgFirmato() != null &&  lAttach.getFlgFirmato().equals("1") 
				|| lAttach.getMimetype() != null && !lAttach.getMimetype().equalsIgnoreCase("application/pdf")) {
			log.debug("Mi risulta firmato digitalmente oppure non pdf");
			AttachmentUDBean lAttachmentUDBean = getAttachmentOriginale(lAttach);
			lListAttachments.add(lAttachmentUDBean);
			log.debug("Aggiungo l'attachment timbrato oltre all'originale");
			AttachmentUDBean lAttachmentUDBeanTimbrato = getAttachmentTimbrato(lAttach, timbrato);
			lListAttachments.add(lAttachmentUDBeanTimbrato);
		} else {
			log.debug("Aggiungo l'attachment timbrato");
			AttachmentUDBean lAttachmentUDBeanTimbrato = getAttachmentTimbrato(lAttach, timbrato);
			lAttachmentUDBeanTimbrato.setFileNameAttach(lAttach.getNomeFile());
			lListAttachments.add(lAttachmentUDBeanTimbrato);
		}

	}

	protected AttachmentUDBean getAttachmentOriginale(AttachmentInvioNotEmailBean lAttach) {
		AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
		lAttachmentUDBean.setFileNameAttach(lAttach.getNomeFile());
		lAttachmentUDBean.setNroAttach(StringUtils.isNotEmpty(lAttach.getNroAllegato()) ? Integer.valueOf(lAttach
				.getNroAllegato()) : null);
		lAttachmentUDBean.setUriAttach(lAttach.getUri());
		lAttachmentUDBean.setRemoteUri(true);
		lAttachmentUDBean.setFirmato(lAttach.getFlgFirmato() != null && lAttach.getFlgFirmato().equals("1") ? true : false);
		lAttachmentUDBean.setMimetype(lAttach.getMimetype());
		lAttachmentUDBean.setFlgFirmaValida(lAttach.getFlgFirmaValida() != null && lAttach.getFlgFirmaValida().equals("1") ? true : false);
		return lAttachmentUDBean;
	}

	protected AttachmentUDBean getAttachmentTimbrato(AttachmentInvioNotEmailBean lAttach, InputStream timbrato) throws StorageException {
		String lStrUri = StorageImplementation.getStorage().storeStream(timbrato);
		String fileName = FilenameUtils.getBaseName(lAttach.getNomeFile()) + "_timbrato.pdf";
		AttachmentUDBean lAttachmentUDBean = new AttachmentUDBean();
		lAttachmentUDBean.setFileNameAttach(fileName);
		lAttachmentUDBean.setNroAttach(StringUtils.isNotEmpty(lAttach.getNroAllegato()) ? Integer.valueOf(lAttach
				.getNroAllegato()) : null);
		lAttachmentUDBean.setUriAttach(lStrUri);
		lAttachmentUDBean.setRemoteUri(false);
		lAttachmentUDBean.setMimetype("application/pdf");
		lAttachmentUDBean.setFirmato(false);
		lAttachmentUDBean.setFlgFirmaValida(false);
		return lAttachmentUDBean;
	}

	public AttProcBean getDatiXInvioNotifMail(AttProcBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		boolean prepareAttach = getExtraparams().get("prepareAttach") != null && getExtraparams().get("prepareAttach").equalsIgnoreCase("true");		
		
		List<String> fileDaTimbrareNonConvertiti = new ArrayList<String>();
		List<AttachmentUDBean> lListAttachments = new ArrayList<AttachmentUDBean>();

		if (prepareAttach) {
			if (bean.getInvioNotEmailAttachment() != null && !bean.getInvioNotEmailAttachment().isEmpty()) {
				List<AttachmentInvioNotEmailBean> preparedAttach = new ArrayList<>();
				prepareAttach(bean.getInvioNotEmailAttachment(), fileDaTimbrareNonConvertiti, lListAttachments, bean.getIdUd());
				addInfoFileToAttach(lListAttachments, preparedAttach);
				bean.setInvioNotEmailAttachment(preparedAttach);
			}
		} else {
			String token = loginBean.getToken();
			String idUserLavoro = loginBean.getIdUserLavoro();

			DmpkWfGetdatixinvionotifmailBean storeInput = new DmpkWfGetdatixinvionotifmailBean();
			storeInput.setCodidconnectiontokenin(token);
			storeInput.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			storeInput.setIdprocessin(new BigDecimal(bean.getIdProcess()));
			storeInput.setActivitynamein(bean.getActivityName());

			DmpkWfGetdatixinvionotifmail store = new DmpkWfGetdatixinvionotifmail();
			StoreResultBean<DmpkWfGetdatixinvionotifmailBean> storeResult = store.execute(getLocale(), loginBean, storeInput);

			if (StringUtils.isNotBlank(storeResult.getDefaultMessage())) {
				if (storeResult.isInError()) {
					throw new StoreException(storeResult);
				} else {
					addMessage(storeResult.getDefaultMessage(), "", MessageType.WARNING);
				}
			}

			if (storeResult.getResultBean() != null && StringUtils.isNotBlank(storeResult.getResultBean().getMailinfoxmlout())) {
				XmlDatiEventoOutBean scXmlDatiEvento = new XmlDatiEventoOutBean();
				XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
				scXmlDatiEvento = lXmlUtilityDeserializer.unbindXml(storeResult.getResultBean().getMailinfoxmlout(), XmlDatiEventoOutBean.class);
				bean.setFlgInvioNotEmail(scXmlDatiEvento.getFlgInvioNotEmail() != null && scXmlDatiEvento.getFlgInvioNotEmail().equalsIgnoreCase("true"));
				bean.setInvioNotEmailSubject(scXmlDatiEvento.getInvioNotEmailSubject());
				bean.setInvioNotEmailBody(scXmlDatiEvento.getInvioNotEmailBody());
				bean.setInvioNotEmailDestinatari(scXmlDatiEvento.getInvioNotEmailDestinatari());
				bean.setInvioNotEmailDestinatariCC(scXmlDatiEvento.getInvioNotEmailDestinatariCC());
				bean.setInvioNotEmailDestinatariCCN(scXmlDatiEvento.getInvioNotEmailDestinatariCCN());
				bean.setInvioNotEmailIdCasellaMittente(scXmlDatiEvento.getInvioNotEmailIdCasellaMittente());
				bean.setInvioNotEmailIndirizzoMittente(scXmlDatiEvento.getInvioNotEmailIndirizzoMittente());
				bean.setInvioNotEmailAliasIndirizzoMittente(scXmlDatiEvento.getInvioNotEmailAliasIndirizzoMittente());		
				bean.setInvioNotEmailFlgConfermaInvio(scXmlDatiEvento.getInvioNotEmailFlgConfermaInvio() != null && scXmlDatiEvento.getInvioNotEmailFlgConfermaInvio().equalsIgnoreCase("true"));		
				bean.setInvioNotEmailFlgInvioMailXComplTask(scXmlDatiEvento.getInvioNotEmailFlgInvioMailXComplTask() != null && scXmlDatiEvento.getInvioNotEmailFlgInvioMailXComplTask().equalsIgnoreCase("true"));		
				if (bean.getInvioNotEmailFlgConfermaInvio()) {
					List<AttachmentInvioNotEmailBean> preparedAttach = new ArrayList<>();

					if (scXmlDatiEvento.getInvioNotEmailAttachment() != null && !scXmlDatiEvento.getInvioNotEmailAttachment().isEmpty()) {
						prepareAttach(scXmlDatiEvento.getInvioNotEmailAttachment(), fileDaTimbrareNonConvertiti, lListAttachments, bean.getIdUd());
						addInfoFileToAttach(lListAttachments, preparedAttach);
					}
					bean.setInvioNotEmailAttachment(preparedAttach);
				} else {
					bean.setInvioNotEmailAttachment(scXmlDatiEvento.getInvioNotEmailAttachment() != null ? scXmlDatiEvento.getInvioNotEmailAttachment() : new ArrayList<AttachmentInvioNotEmailBean>());	
				}
						
			}
		}

		return bean;
	}

	protected void addInfoFileToAttach(List<AttachmentUDBean> lListAttachments,
			List<AttachmentInvioNotEmailBean> preparedAttach) {
		for (AttachmentUDBean attachmentUDBean : lListAttachments) {
			AttachmentInvioNotEmailBean lAttach = new AttachmentInvioNotEmailBean();
			lAttach.setFlgFirmato(attachmentUDBean.getFirmato() != null ? attachmentUDBean.getFirmato().toString() : "false");
			lAttach.setMimetype(attachmentUDBean.getMimetype());
			lAttach.setNomeFile(attachmentUDBean.getFileNameAttach());
			lAttach.setNroAllegato(attachmentUDBean.getNroAttach() !=null ? attachmentUDBean.getNroAttach().toString() : "");
			lAttach.setUri(attachmentUDBean.getUriAttach());
			
			MimeTypeFirmaBean infofile = new MimeTypeFirmaBean();
			infofile.setFirmato(attachmentUDBean.getFirmato() != null ? attachmentUDBean.getFirmato() : false);
			infofile.setFirmaValida(attachmentUDBean.getFlgFirmaValida() != null ? attachmentUDBean.getFlgFirmaValida() : false);
			infofile.setMimetype(attachmentUDBean.getMimetype());
			infofile.setCorrectFileName(attachmentUDBean.getFileNameAttach());
			lAttach.setInfoFile(infofile);
			preparedAttach.add(lAttach);
		}
	}

	/*
	private void invioNotificaVerificaCollegioSindacale(EventoBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		SenderBean sender = new SenderBean();
		sender.setIdUtenteModPec(loginBean.getSpecializzazioneBean().getIdUtenteModPec());
		sender.setSubject("Notifica automatica Verifica del Collegio Sindacale");
		sender.setBody("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		sender.setAccount(<casella mittente>);
		sender.setAddressFrom(<casella mittente>);
		List<String> indirizzoDest = new ArrayList<String>();
		indirizzoDest.add(<indirizzo destinatario>));
		sender.setAddressTo(indirizzoDest);
		sender.setIsHtml(false);
		sender.setIsPec(false);
		try {
			ResultBean<EmailSentReferenceBean> output = AurigaMailService.getMailSenderService().sendandsave(locale, sender);
			if(output.isInError()) {
				throw new Exception(output.getDefaultMessage());
			}
		} catch (Exception e) {
			addMessage("Fallito invio notifica e-mail relativa alla Verifica del Collegio Sindacale: " + e.getMessage(), "", MessageType.WARNING);;
		}
	}
	 */

	private String getEmailReplyTo(String usernameIn) throws Exception {
		String result = null;
		LoginDataSource lDataSource = new LoginDataSource();
		try {
			lDataSource.setSession(getSession());
			result = lDataSource.getUsernameMail(usernameIn);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		return result; 
	}
}