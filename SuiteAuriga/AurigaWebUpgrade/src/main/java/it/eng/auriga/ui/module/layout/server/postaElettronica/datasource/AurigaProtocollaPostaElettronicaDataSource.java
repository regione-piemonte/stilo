/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.jsoup.nodes.Node;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailPrepararegemailricevutaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.CalcolaImpronteService;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.ItemLavorazioneMailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.LockUnlockMail;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioPostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.aurigamailbusiness.bean.EmailAttachsBean;
import it.eng.aurigamailbusiness.bean.EmailInfoBean;
import it.eng.aurigamailbusiness.bean.InfoProtocolloBean;
import it.eng.aurigamailbusiness.bean.MailLoginBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.client.DmpkIntMgoEmailPrepararegemailricevuta;
import it.eng.client.MailProcessorService;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FilePrimarioOutBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.TipoProvenienza;
import it.eng.module.archiveUtility.ArchiveType;
import it.eng.module.archiveUtility.ArchiveUtils;
import it.eng.module.archiveUtility.bean.DecompressioneArchiviBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.pdfproducer.PdfConverter;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilityDeserializer;

// Da attivare nel caso si utilizza il metodo recuperaFiles per ADR.
// TODEL: da eliminare nel caso di aggiornamento dell'ambiente ADR.
// import it.eng.utility.ui.user.UserUtil;
// import it.eng.document.function.bean.FileExtractedIn;
// import it.eng.document.function.bean.FileExtractedOut;
// import it.eng.client.RecuperoFile;

@Datasource(id = "AurigaProtocollaPostaElettronicaDataSource")
public class AurigaProtocollaPostaElettronicaDataSource extends AbstractServiceDataSource<DettaglioPostaElettronicaBean, ProtocollazioneBean> {

	private static Logger mLogger = Logger.getLogger(AurigaProtocollaPostaElettronicaDataSource.class);
	private AurigaLoginBean mLoginBean;
	private MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
	private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

	private PdfConverter getPdfConverter() {
		final Object pdfConverterObj = SpringAppContext.getContext().getBean("pdfConverter");
		return (PdfConverter) pdfConverterObj;
	}

	@Override
	public ProtocollazioneBean call(DettaglioPostaElettronicaBean bean) throws Exception {

		final boolean isProtocollaInteraEmail = getExtraparams().get("isProtocollaInteraEmail") != null &&
				"true".equals(getExtraparams().get("isProtocollaInteraEmail")) ? true : false;
		String isRegistroIstanza = getExtraparams().get("isRegistroIstanza") != null && "true".equals(getExtraparams().get("isRegistroIstanza")) ? "true" : "";
		LockUnlockMail lLockUnlockEmail = new LockUnlockMail(getSession());
		// Inizializzo lo storage
		// recupero loginbean
		mLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		// Verifico che lo stato di protocollazione della mail non sia già C
		String lStringStatoProtocollazione = recuperaStatoProtocollazione(bean);
		// Se lo stato è C si da errore
		if (lStringStatoProtocollazione != null && lStringStatoProtocollazione.equals("C")) {
			throw new StoreException("E-mail già protocollata");
		}
		// Verifico che la mail non sia locked provando a lockarla
		boolean isLocked = lLockUnlockEmail.recuperaIsLocked(bean.getIdEmail());
		if (isLocked)
			throw new StoreException("Operazione non consentita: sull'email sta lavorando un altro utente");
		// Va messo un lock sulla mail
		TEmailMgoBean lTEmailMgoBean = lLockUnlockEmail.lockMailFromProtEmail(bean);
		// Tipo di conversione da applicare al corpo della mail
		String tipoConversione = ParametriDBUtil.getParametroDB(getSession(), "FMT_CONVERSIONE_TESTO_EMAIL");
		
		// Se la classifica è interoparabile e trattabile
		String lFileSegnaturaXml = null;
		Map<String, Object> lFiles = null;
		Map<String, Object> lrecuperaFilesMap = null;
		
		// Recupero i files
		if(!isProtocollaInteraEmail) {
			
			lrecuperaFilesMap = recuperaFiles(lTEmailMgoBean, tipoConversione);

			lFiles = (Map<String, Object>) lrecuperaFilesMap.get("file");

			// if (classifica.startsWith("standard.arrivo.interoperabili") && !classifica.equals("standard.arrivo.interoperabili.non_conformi.non_trattabili")){
			if (lTEmailMgoBean.getCategoria() != null && lTEmailMgoBean.getCategoria().equals("INTEROP_SEGN")) {
				lFileSegnaturaXml = recuperaSegnatura(lFiles);
				if (lFileSegnaturaXml == null)
					throw new StoreException("Fallito recupero dell'allegato \"segnatura.xml\"");
			}
		}

		// Se tutto è andato correttamente
		DocumentoXmlOutBean lDocumentoXmlOutBean = preparaRegistrazione(bean, lFileSegnaturaXml, lFiles, isProtocollaInteraEmail);

		String idTipoDoc = getExtraparams().get("idTipoDoc") != null ? getExtraparams().get("idTipoDoc") : null;
		String nomeTipoDoc = getExtraparams().get("nomeTipoDoc") != null ? getExtraparams().get("nomeTipoDoc") : null;

		lDocumentoXmlOutBean.setTipoDocumento(idTipoDoc);
		lDocumentoXmlOutBean.setNomeTipoDocumento(nomeTipoDoc);

		// se la lista dei file degli appunti non è vuota, vengono aggiunti come allegati alla protocollazione
		if (!isProtocollaInteraEmail && (bean.getFileDaAppunti() != null && !bean.getFileDaAppunti().isEmpty())) {
			processFileDaAppunti(lFiles, lDocumentoXmlOutBean, bean.getFileDaAppunti());
		}

		List<String> erroriDecompressione = null;
		// è attivata la decompressione per le mail in entrata
		if (!isProtocollaInteraEmail && isAttivaDecompressionePerMailEntrata(lDocumentoXmlOutBean.getFlgTipoProv())) {
			if ("PEC".equals(lTEmailMgoBean.getCategoria())) {
				gestisciEmlAttach(lFiles, lDocumentoXmlOutBean);
			}
			erroriDecompressione = gestisciArchivi(lDocumentoXmlOutBean, lFiles);
		}

		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession(), lTEmailMgoBean.getTsInvio());
		ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();
		
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdUDFromEmail())) {
			lProtocollazioneBean.setIdUd(new BigDecimal(lDocumentoXmlOutBean.getIdUDFromEmail()));
		} else {
			lProtocollazioneBean = lProtocolloUtility.getProtocolloFromEmailDocumentoXml(lDocumentoXmlOutBean, lFiles);

			if (!"".equals(isRegistroIstanza) && "true".equals(isRegistroIstanza)) {
				if (lProtocollazioneBean.getListaAllegati() != null && !lProtocollazioneBean.getListaAllegati().isEmpty()
						&& lProtocollazioneBean.getListaAllegati().size() == 1
						&& !lProtocollazioneBean.getListaAllegati().get(0).getNomeFileAllegato().equals("TestoEmail.txt")) {
					lProtocollazioneBean = getSwitchAllegatoToFilePrimario(lProtocollazioneBean);
				}
			}
			
			if (erroriDecompressione != null && !erroriDecompressione.isEmpty()) {
				lProtocollazioneBean.setErroriArchivi(erroriDecompressione);
			}
			
			lProtocollazioneBean.setIdEmailArrivo(bean.getIdEmail());
			lProtocollazioneBean.setEmailArrivoInteroperabile(lDocumentoXmlOutBean.getEmailArrivoInteroperabile());
			lProtocollazioneBean.setCasellaIsPEC(lDocumentoXmlOutBean.getCasellaIsPEC());
			if (lProtocollazioneBean.getListaAllegati() != null && !lProtocollazioneBean.getListaAllegati().isEmpty()) {
				lProtocollazioneBean.setErroriEstensioniFile(checkEstensioneFile(lProtocollazioneBean.getListaAllegati()));
			}

			// Se ho convertito i corpo mail in PDF o html
			if (lrecuperaFilesMap != null && lrecuperaFilesMap.get("isConvSucces") != null && (Boolean) lrecuperaFilesMap.get("isConvSucces")) {

				String correctFileName = "TestoEmail";
				String mimeType = "";

				if ("pdf".equalsIgnoreCase(tipoConversione) || "pdf/A".equalsIgnoreCase(tipoConversione)) {
					// Corpo mail in pdf
					correctFileName = "TestoEmail.pdf";
					mimeType = "application/pdf";
				} else {
					// Corpo mail in html
					correctFileName = "TestoEmail.html";
					mimeType = "text/html";
				}

				MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
				File corpoMail = StorageImplementation.getStorage().getRealFile((String) lFiles.get("__TestoEmail.txt"));
				lMimeTypeFirmaBean.setImpronta(calcolaImpronta(corpoMail.toURI().toString(), correctFileName));
				lMimeTypeFirmaBean.setCorrectFileName(correctFileName);
				lMimeTypeFirmaBean.setFirmato(false);
				lMimeTypeFirmaBean.setConvertibile(true);
				lMimeTypeFirmaBean.setDaScansione(false);
				lMimeTypeFirmaBean.setMimetype(mimeType);
				lMimeTypeFirmaBean.setBytes(corpoMail.length());

				if ("TestoEmail.txt".equals(lProtocollazioneBean.getNomeFilePrimario())) {
					// il corpo della mail è il file primario.
					lProtocollazioneBean.setInfoFile(lMimeTypeFirmaBean);
					lProtocollazioneBean.setNomeFilePrimario(correctFileName);
				} else {
					// il corpo della mail è un allegato. Si procede a recuperare il bean relativo e aggiornare l'infoFile.
					for (AllegatoProtocolloBean allegatoBean : lProtocollazioneBean.getListaAllegati()) {
						if (allegatoBean.getNomeFileAllegato().equals("TestoEmail.txt")) {
							allegatoBean.setInfoFile(lMimeTypeFirmaBean);
							allegatoBean.setNomeFileAllegato(correctFileName);

						}
					}
				}
			}
		}
		lProtocollazioneBean.setWarningMsgDoppiaReg(lDocumentoXmlOutBean.getWarningMsgDoppiaReg());

		return lProtocollazioneBean;
	}

	private ProtocollazioneBean getSwitchAllegatoToFilePrimario(ProtocollazioneBean bean) throws Exception {
		int indice = 0;
		// Recupero se è presente un primario
		AllegatoProtocolloBean lAllegatoProtocolloBeanPrimario = null;
		if (bean.getInfoFile() != null && StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())) {
			lAllegatoProtocolloBeanPrimario = new AllegatoProtocolloBean();
			lAllegatoProtocolloBeanPrimario.setNomeFileAllegato(bean.getNomeFilePrimario());
			lAllegatoProtocolloBeanPrimario.setUriFileAllegato(bean.getUriFilePrimario());
			lAllegatoProtocolloBeanPrimario.setRemoteUri(bean.getRemoteUriFilePrimario());
			lAllegatoProtocolloBeanPrimario.setIdAttachEmail(bean.getIdAttachEmailPrimario());
			lAllegatoProtocolloBeanPrimario.setInfoFile(bean.getInfoFile());
			lAllegatoProtocolloBeanPrimario.setIsChanged(true);
		}
		AllegatoProtocolloBean lAllegatoProtocolloBean = bean.getListaAllegati().get(indice);
		bean.setNomeFilePrimario(lAllegatoProtocolloBean.getNomeFileAllegato());
		bean.setUriFilePrimario(lAllegatoProtocolloBean.getUriFileAllegato());
		bean.setRemoteUriFilePrimario(lAllegatoProtocolloBean.getRemoteUri());
		bean.setIdAttachEmailPrimario(lAllegatoProtocolloBean.getIdAttachEmail());
		bean.setInfoFile(lAllegatoProtocolloBean.getInfoFile());
		bean.setIsDocPrimarioChanged(true);
		List<AllegatoProtocolloBean> lListAllegati = bean.getListaAllegati();
		if (lAllegatoProtocolloBeanPrimario != null)
			lListAllegati.set(indice, lAllegatoProtocolloBeanPrimario);
		else
			lListAllegati.remove(indice);
		bean.setListaAllegati(lListAllegati);
		return bean;
	}

	private String deleteWhitespaceFromCorpoMail(String corpoMail) {
		if (corpoMail != null) {
			String corpoMailWithoutWhitespaces = StringUtils.deleteWhitespace(corpoMail);
			corpoMailWithoutWhitespaces = corpoMailWithoutWhitespaces.replaceAll("<br>", "");
			corpoMailWithoutWhitespaces = corpoMailWithoutWhitespaces.replaceAll("<br/>", "");
			corpoMailWithoutWhitespaces = corpoMailWithoutWhitespaces.replaceAll("&nbsp;", "");
			return corpoMailWithoutWhitespaces;
		}
		return null;
	}

	/**
	 * 
	 * @param lFiles
	 *            lista dei File in cui aggiungere i file estratti dal .eml
	 * @param lDocumentoXmlOutBean
	 *            Bean contenente i dati relativi alla protocollazione da modificare in base ai file esstratti dal .eml
	 */
	private void gestisciEmlAttach(Map<String, Object> lFiles, DocumentoXmlOutBean lDocumentoXmlOutBean) {

		String algoritmoImpronta = "";
		String encodingImpronta = "";
		Map<String, Object> attachEml = new HashMap<>(); // mappa degli attach contenente nome e Uri per StorageUtil
		List<AllegatiOutBean> listEmlAllegatiBean = new ArrayList<>(); // lista degli allegati presenti all'interno di un file .eml (il quale risulta essere
		// allegato di una mail).

		// Recupero delle impronte dal File primario o allegati
		if (lDocumentoXmlOutBean.getFilePrimario() != null) {
			algoritmoImpronta = lDocumentoXmlOutBean.getFilePrimario().getAlgoritmoImpronta();
			encodingImpronta = lDocumentoXmlOutBean.getFilePrimario().getEncodingImpronta();
		} else if (lDocumentoXmlOutBean.getAllegati() != null && !lDocumentoXmlOutBean.getAllegati().isEmpty()) {
			algoritmoImpronta = lDocumentoXmlOutBean.getAllegati().get(0).getAlgoritmoImpronta();
			encodingImpronta = lDocumentoXmlOutBean.getAllegati().get(0).getEncodingImpronta();
		}

		for (Entry<String, Object> lFileEntry : lFiles.entrySet()) {
			String fileName = lFileEntry.getKey().split(";")[0]; // recupero nome originale file
			if (fileName != null && fileName.contains("postacert.eml")) {
				try {
					File emlFile = StorageImplementation.getStorage().extractFile((String) lFileEntry.getValue());
					processEmlAttach(attachEml, emlFile, listEmlAllegatiBean, algoritmoImpronta, encodingImpronta);
				} catch (StorageException e) {
					mLogger.error("Errore nel recupero del file : " + fileName + " da storageUtil", e);
				} catch (Exception e) {
					mLogger.error("Eccezione: ", e);
				}
			}
		}
		if (!attachEml.isEmpty() && !listEmlAllegatiBean.isEmpty()) {
			// sono stati recuperati degli allegati da processEmlAttach
			lFiles.putAll(attachEml);
			List<AllegatiOutBean> allegatiOutBeanList = lDocumentoXmlOutBean.getAllegati();
			allegatiOutBeanList.addAll(listEmlAllegatiBean);
			lDocumentoXmlOutBean.setAllegati(allegatiOutBeanList);
		}

	}

	/**
	 * 
	 * @param attachEml
	 *            mappa da popolare contenente il nome del file e l'uri relativo dei file estrapolati dal .eml
	 * @param emlFile
	 *            file .eml da analizzare
	 * @param listEmlAllegatiBean
	 *            lista di AllegatiOutBean da popolare contenente i file estratti dal .eml
	 * @param algoritmoImpronta
	 *            impronta dell'algoritmo da utilizzare per calcolare l'impronta dei file
	 * @param encodingImpronta
	 *            encoding da utilizzare per calcolare l'impronta dei file
	 * @throws Exception
	 * @throws StorageException
	 */
	private void processEmlAttach(Map<String, Object> attachEml, File emlFile, List<AllegatiOutBean> listEmlAllegatiBean, String algoritmoImpronta,
			String encodingImpronta) throws Exception, StorageException {
		MailProcessorService lMailProcessorService = new MailProcessorService();
		// in questo caso uso il file eml per recuperare gli attach
		EmailAttachsBean lEmailAttachsBean = lMailProcessorService.getattachments(getLocale(), emlFile);
		if (lEmailAttachsBean.getMailAttachments() == null)
			return;
		int count = 0;
		if (lEmailAttachsBean.getFiles() != null && !lEmailAttachsBean.getFiles().isEmpty()) {
			String valuesMap = null;
			for (File lFile : lEmailAttachsBean.getFiles()) {

				String uriAllegato = StorageImplementation.getStorage().store(lFile);
				String filename = Normalizer.normalize(lEmailAttachsBean.getMailAttachments().get(count).getFilename(), Normalizer.Form.NFC);
				String size = lEmailAttachsBean.getMailAttachments().get(count).getSize().toString();
				String uriFile = StorageImplementation.getStorage().extractFile(uriAllegato).toURI().toString();

				lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(uriFile, filename, false, null);
				String mimeType = lMimeTypeFirmaBean.getMimetype();

				if (mimeType == null)
					mimeType = ""; // nel caso non si riesca a calcolare il mimetype del file, si setta un valore per non incorrere a eccezioni future durante i
				// controlli.

				if (filename != null) {

					// Funzione ricorsiva che permette di recuperare i contenuti degli eml all'interno del file emlFile. Attualmente disabilitata.
					// if(filename.contains(".eml")) {
					// processEmlAttach(attachEml, lFile, listEmlAllegatiBean, algoritmoImpronta, encodingImpronta);
					// }

					CalcolaImpronteService calcolaImpronteService = new CalcolaImpronteService();
					String impronta = calcolaImpronteService.calcolaImprontaWithoutFileOp(lFile, algoritmoImpronta, encodingImpronta);
					valuesMap = filename.concat(";").concat(size);
					attachEml.put(valuesMap, uriAllegato);
					listEmlAllegatiBean.add(createAllegato(algoritmoImpronta, encodingImpronta, new BigDecimal(size), filename, 
							mimeType, impronta, lMimeTypeFirmaBean));

				}
				count++;
			}
		}
	}

	private Map<String, Object> recuperaFiles(TEmailMgoBean mail, String tipoConversione) throws Exception {

		Map<String, Object> lFiles = new HashMap<String, Object>();
		Map<String, Object> recuperaFilesMap = new HashMap<>();

		boolean isConvSucces = false; // identifica se è stato convertito il TestoEmail.txt in pdf/html

		// Recupero il corpo della mail in HTML
		MailProcessorService lMailProcessorService = new MailProcessorService();
		EmailInfoBean emailInfoBean = lMailProcessorService.getbodyhtmlbyidemail(getLocale(), mail.getIdEmail());
		String htmlBody = emailInfoBean.getMessaggio();
		Pattern htmlPattern = Pattern.compile(".*\\<[^>]+>.*", Pattern.DOTALL);

		String uriFile = null; // uri del file convertito

		if (htmlBody != null && htmlPattern.matcher(htmlBody).matches()) {
			// Caso in cui il corpo mail è in HTML
			uriFile = createFileFromHtml(htmlBody, tipoConversione);
		} else {
			// Caso in cui il corpo mail non è HTML
			uriFile = createFileFromTxt(mail.getCorpo(), tipoConversione);
		}

		if (uriFile != null && !"".equalsIgnoreCase(uriFile)) {
			// Se non sono avvenuti errori durante la conversione
			lFiles.put("__TestoEmail.txt", uriFile);
			isConvSucces = true;
		} else {
			// In caso di errori durante la conversione oppure non bisognava convertire il corpo, gestisco il corpo mail come txt
			if (StringUtils.isNotEmpty(deleteWhitespaceFromCorpoMail(mail.getCorpo()))) {
				org.jsoup.nodes.Document emailBody = Jsoup.parse(mail.getCorpo());
				String uriTesto = StorageImplementation.getStorage().storeStream(IOUtils.toInputStream(emailBody.text()));
				lFiles.put("__TestoEmail.txt", uriTesto);
			}
		}
		EmailAttachsBean lEmailAttachsBean = lMailProcessorService.getattachmentsbyidemail(getLocale(), mail.getIdEmail());
		if (lEmailAttachsBean.getMailAttachments() == null) {
			recuperaFilesMap.put("file", lFiles);
			recuperaFilesMap.put("isConvSucces", isConvSucces);

			return recuperaFilesMap;
		}
		int count = 0;
		if (lEmailAttachsBean.getFiles() != null && !lEmailAttachsBean.getFiles().isEmpty()) {
			String valuesMap = null;
			for (File lFile : lEmailAttachsBean.getFiles()) {

				long start = new Date().getTime();
				mLogger.info("Tempo impiegato attach mail " + (new Date().getTime() - start));
				String uriAllegato = StorageImplementation.getStorage().store(lFile);
				String filename = Normalizer.normalize(lEmailAttachsBean.getMailAttachments().get(count).getFilename(), Normalizer.Form.NFC);
				String size = lEmailAttachsBean.getMailAttachments().get(count).getSize().toString();

				if (lFiles.size() > 0) {
					valuesMap = filename.concat(";").concat(size);
					lFiles.put(valuesMap, uriAllegato);
				} else {
					valuesMap = filename.concat(";").concat(size);
					lFiles.put(valuesMap, uriAllegato);
				}
				count++;
			}
		}

		recuperaFilesMap.put("file", lFiles);
		recuperaFilesMap.put("isConvSucces", isConvSucces);

		return recuperaFilesMap;
	}

	/**
	 * metodo che crea un file a partire dal txt di input e lo importa mediante StorageUtil. A seconda del parametro impostato a lato DB può salvare il file in
	 * pdf o pdf/A.
	 * 
	 * @param corpoMail
	 *            testo del corpo semplice
	 * @param tipoConversione
	 *            tipo di conversione da applicare. Può essere pdf o pdf/A
	 * @return l'uri del file convertito, altrimenti se non c'è stata una conversione (per errori o perchè non bisognava convertire) torna null.
	 */
	private String createFileFromTxt(String corpoMail, String tipoConversione) {
		if ("pdf".equalsIgnoreCase(tipoConversione) || "pdf/A".equalsIgnoreCase(tipoConversione)) {
			// il corpo mail è un testo txt semplice ma deve essere comunque convertito in pdf o pdf/A.
			FileWriter fw = null;
			BufferedWriter bw = null;
			InputStream bodyConvertStream = null;
			try {
				// creazione del file temporaneo dove copiare il corpo della mail.
				File fileTxt = File.createTempFile("TestoEmailTemp", ".txt");
				fileTxt.deleteOnExit();
				fw = new FileWriter(fileTxt);
				bw = new BufferedWriter(fw);
				bw.write(corpoMail);
				bw.close();
				fw.close();
				// Inizio conversione del file temporaneo.
				InfoFileUtility lFileUtility = new InfoFileUtility();
				if ("pdf/A".equalsIgnoreCase(tipoConversione)) {
					bodyConvertStream = lFileUtility.converti(fileTxt.toURI().toString(), fileTxt.getName(), false, true);
				} else {
					bodyConvertStream = lFileUtility.converti((fileTxt.toURI().toString()), fileTxt.getName());
				}
				File fileConvert = File.createTempFile("TestoEmailConvert", ".file");
				fileConvert.deleteOnExit();
				FileUtils.copyInputStreamToFile(bodyConvertStream, fileConvert);
				return StorageImplementation.getStorage().store(fileConvert);
			} catch (Exception e) {
				mLogger.error(e.getMessage(), e);
				return null;
			} finally {
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
					}
				}
				if (bw != null) {
					try {
						bw.close();
					} catch (IOException e) {
					}
				}
				if (bodyConvertStream != null) {
					try {
						bodyConvertStream.close();
					} catch (IOException e) {
					}
				}
			}

		} else {
			return null; // torna null perchè non deve eseguire nessun tipo di conversione per il tipo txt.
		}
	}

	/**
	 * metodo che crea un file a partire dall'html di input e lo importa mediante StorageUtil. A seconda del parametro impostato a lato DB può salvare un file
	 * html o convertirlo in pdf.
	 * 
	 * @param html
	 *            testo html da convertire salvare in un file (pdf o html)
	 * @param tipoConversione
	 *            tipo di conversione da utilizzare (supportato pdf, pdf/A)
	 * @return uri di StorageUtil del file, null in caso di errore
	 */
	private String createFileFromHtml(String html, String tipoConversione) {

		if (html.contains("&nbsp;")) {
			html = html.replace("&nbsp;", " ");
		}
		
		// Pulizia dei commenti nell'html altrimenti s'incorre in javaheapspace in fase di generazione pdf
		html = rimuoviCommentiInHtml(html);

		org.jsoup.nodes.Document emailBody = Jsoup.parse(html);
		// normalizza la sintassi del documento rispettando le direttive xml, la libreria di generazione del pdf si aspetta html valido
		emailBody.outputSettings().syntax(Syntax.xml);
		emailBody.select("img").remove();
		
		// Inizio controllo, nel caso in cui l'attributo publicid del nodo DOCTYPE sia presente e non contenga i doppi apici a inizio e fine del valore
		// poichè in questo caso non viene convertito il testo dell'email in pdf generando, quindi, un file vuoto 
		List<Node> childNodes = emailBody.childNodes();
		for (Node node : childNodes) {
			if(node.nodeName() != null && node.nodeName().equals("#doctype")) {
				Attributes attributes = node.attributes();
				String publicid = attributes.get("publicid");
				if(publicid != null && !publicid.contains("\"")) {
					String correctPublicid = "\"" + publicid + "\"";
//					attributes.remove("publicid");
					attributes.put("publicid", correctPublicid);
				}
			}
		}
		// Fine controllo
		
		String htmlValido = emailBody.outerHtml();

		BufferedWriter fileWriter = null; // utilizzato per scrivere il body in un file html
		OutputStream fileOut = null; // utilizzato per generare lo stream di uscita in un file html
		InputStream filePdfIn = null; // utilizzato per generare uno stream per il file da convertire da html a pdf.
		InputStream filePdfAIn = null; // utilizzato per generare il pdf/A
		OutputStream filePdfAOut = null; // utilizzato per generare il pdf/A

		try {
			// file del corpo mail da ritornare convertito in html, pdf o pdf/A.
			// In base alla conversione scelta il file subirà delle modifiche successive:
			// html: html
			// pdf: html -> pdf
			// pdf/A: html -> pdf -> pdf/A
			File fileConvert = File.createTempFile("TestoEmailConvert", ".html");
			fileConvert.deleteOnExit();

			fileOut = new FileOutputStream(fileConvert);
			fileWriter = new BufferedWriter(new OutputStreamWriter(fileOut, "UTF-16"));
			fileWriter.write(htmlValido);
			fileWriter.close();
			fileOut.close();

			if ("pdf".equalsIgnoreCase(tipoConversione) || "pdf/A".equalsIgnoreCase(tipoConversione)) {
				InfoFileUtility lFileUtility = new InfoFileUtility();
				File fileConvertPdf = File.createTempFile("TestoEmailConvert", ".pdf");
				fileConvertPdf.deleteOnExit();
				try {
					final PdfConverter pdfConverter = getPdfConverter();
					final Path baseDir = pdfConverter.getBaseDir();
					URL url = null;
					if (baseDir != null) {
						final String baseUrl = getSession().getServletContext().getRealPath(baseDir.toString());
						if (baseUrl != null) {
							url = new URL("file", "", "/" + baseUrl);
						}
					}
					getPdfConverter().generatePdf(htmlValido, fileConvertPdf, url, false);
					fileConvert = fileConvertPdf;
				} catch (Exception e) {
					mLogger.error("CONVERSIONE PRIORITARIA IN PDF NON AVVENUTA!", e);
				}
				if (fileConvert != fileConvertPdf) {
					filePdfIn = lFileUtility.converti(fileConvert.toURI().toString(), fileConvert.getName());
					FileUtils.copyInputStreamToFile(filePdfIn, fileConvertPdf);
					filePdfIn.close();
					fileConvert = fileConvertPdf; // sostituisco il file da ritornare con quello convertito in pdf
				}

				if ("pdf/A".equalsIgnoreCase(tipoConversione)) {
					File fileConvertPdfA = File.createTempFile("TestoEmailConvert", ".pdf");
					fileConvertPdfA.deleteOnExit();
					filePdfAIn = new FileInputStream(fileConvertPdf);
					filePdfAOut = new FileOutputStream(fileConvertPdfA);
					lFileUtility.convertiPdfToPdfA(filePdfAIn, filePdfAOut);
					filePdfAIn.close();
					filePdfAOut.close();
					fileConvert = fileConvertPdfA; // sostituisco il file principale convertito (in pdf) con quello in pdf/A
				}
			}
			return StorageImplementation.getStorage().store(fileConvert);

		} catch (Exception e) {
			mLogger.error(e.getMessage(), e);
			return null;
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
				}
			}
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
				}
			}
			if (filePdfIn != null) {
				try {
					filePdfIn.close();
				} catch (IOException e) {
				}
			}
			if (filePdfAIn != null) {
				try {
					filePdfAIn.close();
				} catch (IOException e) {
				}
			}
			if (filePdfAOut != null) {
				try {
					filePdfAOut.close();
				} catch (IOException e) {
				}
			}
		}
	}

	// Metodo da usare su ADR al posto di recuperaFiles perchè in quell'ambiente non è ancora aggiornato la mailbusiness con le modifiche relative alle mail.
	// TODEL: da eliminare nel caso di aggiornamento dell'ambiente ADR.
	// private Map<String, Object> recuperaFiles(PostaElettronicaBean bean, TEmailMgoBean mail) throws Exception {
	// Map<String, Object> lFiles = new HashMap<String, Object>();
	// // Se c'è un corpop della mail diventa un file
	// if (StringUtils.isNotEmpty(deleteWhitespaceFromCorpoMail(mail.getCorpo()))) {
	// long start = new Date().getTime();
	// String uriTesto = StorageImplementation.getStorage().storeStream(IOUtils.toInputStream(mail.getCorpo()));
	// mLogger.error("Tempo impiegato testo mail " + (new Date().getTime() - start));
	// lFiles.put("__TestoEmail.txt", uriTesto);
	// }
	// MailProcessorService lMailProcessorService = new MailProcessorService();
	// String uriEmail = mail.getUriEmail();
	// RecuperoFile lRecuperoFile = new RecuperoFile();
	// FileExtractedIn lFileExtractedIn = new FileExtractedIn();
	// lFileExtractedIn.setUri(uriEmail);
	// FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), mLoginBean, lFileExtractedIn);
	// File emlFile = out.getExtracted();
	// if (emlFile == null)
	// return lFiles;
	// EmailAttachsBean lEmailAttachsBean = lMailProcessorService.getattachments(getLocale(), emlFile);
	// if (lEmailAttachsBean.getMailAttachments() == null)
	// return lFiles;
	// int count = 0;
	// if (lEmailAttachsBean.getFiles() != null && lEmailAttachsBean.getFiles().size() > 0) {
	// String valuesMap = null;
	// for (File lFile : lEmailAttachsBean.getFiles()) {
	//
	// long start = new Date().getTime();
	// mLogger.error("Tempo impiegato attach mail " + (new Date().getTime() - start));
	// String uriAllegato = StorageImplementation.getStorage().store(lFile);
	// String filename = Normalizer.normalize(lEmailAttachsBean.getMailAttachments().get(count).getFilename(), Normalizer.Form.NFC);
	// String size = lEmailAttachsBean.getMailAttachments().get(count).getSize().toString();
	//
	// if (lFiles.size() > 0) {
	// valuesMap = filename.concat(";").concat(size);
	// lFiles.put(valuesMap, uriAllegato);
	// } else {
	// valuesMap = filename.concat(";").concat(size);
	// lFiles.put(valuesMap, uriAllegato);
	// }
	// count++;
	// }
	// }
	// return lFiles;
	// }

	protected DocumentoXmlOutBean preparaRegistrazione(DettaglioPostaElettronicaBean pInBean, String lFileSegnaturaXml, 
			Map<String, Object> lFiles, final boolean isProtocollaInteraEmail) throws Exception {

		String token = mLoginBean.getToken();
		String idUserLavoro = mLoginBean.getIdUserLavoro();

		DmpkIntMgoEmailPrepararegemailricevuta store = new DmpkIntMgoEmailPrepararegemailricevuta();
		DmpkIntMgoEmailPrepararegemailricevutaBean bean = new DmpkIntMgoEmailPrepararegemailricevutaBean();
		bean.setCodidconnectiontokenin(token);
		bean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		bean.setIdemailin(pInBean.getIdEmail());
		if (lFileSegnaturaXml != null) {
			bean.setXmlsegnaturain(IOUtils.toString(new FileInputStream(StorageImplementation.getStorage().extractFile(lFileSegnaturaXml))));
		}

		/**
		 * Viene verificato nel campo avvertimenti della tabella t_email_mgo la presenza della stringa 'DTD non valido' Se è presente viene eliminata la
		 * segnatura.dtd dalla validazione dei file da protocollare
		 */
		if (pInBean.getAvvertimenti() != null && pInBean.getAvvertimenti().contains("DTD non valido")) {
			bean.setXmlsegnaturain("");
		}
		
		if (isRichiestaAccessoAtti()) {
			 bean.setFinalitain("VISURA_SUE");
		}

		StoreResultBean<DmpkIntMgoEmailPrepararegemailricevutaBean> lResult = store.execute(getLocale(), mLoginBean, bean);
		if (StringUtils.isNotBlank(lResult.getDefaultMessage())) {
			if (lResult.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + lResult.getDefaultMessage());
				throw new StoreException(lResult);
			} else {
				addMessage(lResult.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		String lString = lResult.getResultBean().getXmldatiudout();
		XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
		DocumentoXmlOutBean lDocumentoXmlOutBean = lXmlUtilityDeserializer.unbindXml(lString, DocumentoXmlOutBean.class);
		
		if(!isProtocollaInteraEmail) {

			if (lDocumentoXmlOutBean.getFilePrimario() != null && lDocumentoXmlOutBean.getFilePrimario().getDimensione() != null) {
				int cmpDimPrimario = lDocumentoXmlOutBean.getFilePrimario().getDimensione().compareTo(BigDecimal.ZERO);
				if (cmpDimPrimario == 0) {
					for (String nomiAllegati : lFiles.keySet()) {
						String[] nomeAllegato = nomiAllegati.split(";");
						if (lDocumentoXmlOutBean.getFilePrimario().getDisplayFilename().equalsIgnoreCase(nomeAllegato[0])) {
							lDocumentoXmlOutBean.getFilePrimario().setDimensione(new BigDecimal(nomeAllegato[1]));
							break;
						}
					}
				}
			}
	
			// Gli allegati della mail con dimensione 0 vengono esclusi dalla protocollazione della stessa
			if (lDocumentoXmlOutBean.getAllegati() != null && !lDocumentoXmlOutBean.getAllegati().isEmpty()) {
				Iterator<AllegatiOutBean> iteAllegato = lDocumentoXmlOutBean.getAllegati().iterator();
				while(iteAllegato.hasNext()) {
					AllegatiOutBean allegatoItem = iteAllegato.next();
					if (allegatoItem.getDimensione() != null) {
						int cmpDimAllegato = allegatoItem.getDimensione().compareTo(BigDecimal.ZERO);
						if (cmpDimAllegato == 0) {
							Set<String> fileDaEliminareSet = new HashSet<String>();
							for (String nomiAllegati : lFiles.keySet()) {
								String[] nomeAllegato = nomiAllegati.split(";");
								if (allegatoItem.getNomeOriginale().equalsIgnoreCase(nomeAllegato[0])) {
									fileDaEliminareSet.add(allegatoItem.getNomeOriginale().concat(";").concat("0"));
								}
							}
							for(String itemFileToDel : fileDaEliminareSet) {
								//Rimuovo dalla mappa lFiles allegati con dimensione 0
								lFiles.remove(itemFileToDel);
							}
							
							// Rimuovo eventuali allegati processati da mailui con dimensione 0 da lDocumentoXmlOutBean.getAllegati()
							iteAllegato.remove();
						}
					}
				}
			}
		} else {
			
			/**
			 * Azione di Protocollazione/Repertoriazione intera email
			 */
			
			lDocumentoXmlOutBean.setFilePrimario(null);
			lDocumentoXmlOutBean.setAllegati(null);
			
			DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
			
			File fileTemp = StorageImplementation.getStorage().extractFile(pInBean.getUri());	
			String uriEml = StorageImplementation.getStorage().store(fileTemp);
			
			FilePrimarioOutBean lFilePrimarioOutBean = new FilePrimarioOutBean();
			lFilePrimarioOutBean.setAlgoritmoImpronta(lDocumentConfiguration.getAlgoritmo().value());
			lFilePrimarioOutBean.setDimensione(new BigDecimal(fileTemp.length()));
			lFilePrimarioOutBean.setDisplayFilename("E-mailRicevuta.eml");
			lFilePrimarioOutBean.setEncodingImpronta(lDocumentConfiguration.getEncoding().value());
			lFilePrimarioOutBean.setImpronta((new CalcolaImpronteService()).calcolaImprontaWithoutFileOp(fileTemp, lDocumentConfiguration.getAlgoritmo().value(),
					lDocumentConfiguration.getEncoding().value()));
			lFilePrimarioOutBean.setMimetype("multipart/mixed");
			lFilePrimarioOutBean.setUri(uriEml);
			
			lFilePrimarioOutBean.setFlgFirmato(Flag.NOT_SETTED);
			lFilePrimarioOutBean.setFlgPdfConCommenti(Flag.NOT_SETTED);
			lFilePrimarioOutBean.setFlgPdfEditabile(Flag.NOT_SETTED);
			lFilePrimarioOutBean.setFlgConvertibilePdf(Flag.NOT_SETTED);
			lFilePrimarioOutBean.setFlgPdfProtetto(Flag.NOT_SETTED);
			
			lDocumentoXmlOutBean.setFilePrimario(lFilePrimarioOutBean);
		}

		mLogger.debug(lString);

		return lDocumentoXmlOutBean;
	}

	protected String recuperaSegnatura(Map<String, Object> lFiles) throws Exception {
		for (String lStringFileName : lFiles.keySet()) {
			// il nome file contiene anche la size del file, quindi devo verificare che inizi per segnatura.xml
			if (lStringFileName.toLowerCase().startsWith("segnatura.xml")) {
				return (String) lFiles.get(lStringFileName);
			}
		}
		return null;
	}

	private String recuperaStatoProtocollazione(DettaglioPostaElettronicaBean bean) throws Exception {

		MailProcessorService lMailProcessorService = new MailProcessorService();
		InfoProtocolloBean lInfoProtocolloBean = new InfoProtocolloBean();
		lInfoProtocolloBean.setIdEmail(bean.getIdEmail());

		MailLoginBean lLoginBean = new MailLoginBean();
		lLoginBean.setToken(mLoginBean.getToken());
		lLoginBean.setUserId(mLoginBean.getIdUserLavoro());
		lLoginBean.setSchema(mLoginBean.getSchema());
		lInfoProtocolloBean.setLoginBean(lLoginBean);
		String lStringStatoProtocollazione = lMailProcessorService.getstatoprotocollazione(getLocale(), lInfoProtocolloBean).getFlagStatoProtocollazione();
		mLogger.debug("Stato protocollazione: " + lStringStatoProtocollazione);

		return lStringStatoProtocollazione;
	}

	/**
	 * 
	 * @param lDocumentoXmlOutBean
	 *            contiene le informazioni relative alla mail da protocollare.
	 * @param lFiles
	 *            mappa contenente gli allegati e relativi uri
	 * @return erroriDecompressione lista contenente gli errori di decompressione
	 * 
	 *         metodo che permette di recuperare i file dai vari archivi allegati ad una mail.
	 * @throws StorageException
	 */
	private List<String> gestisciArchivi(DocumentoXmlOutBean lDocumentoXmlOutBean, Map<String, Object> lFiles) throws StorageException {

		List<String> erroriDecompressione = new ArrayList<>(); // contiene i vari errori che possono incorrere durante la decompressione degli archivi

		// controlla se il file primario è un archivio.
		FilePrimarioOutBean filePrimario = lDocumentoXmlOutBean.getFilePrimario();
		if (filePrimario != null && checkArchive(filePrimario.getMimetype(), FilenameUtils.getExtension(filePrimario.getDisplayFilename()))) {
			// Recupera l'Uri del file in questione
			String fileUri = recuperaUriDaLista(filePrimario.getDisplayFilename(), lFiles);
			if (fileUri != null && !fileUri.equals("")) {
				// Creazione e recupero della cartella temporanea dove estrarre i file.
				File primaryArchive = StorageImplementation.getStorage().extractFile(fileUri);
				String outPath = TEMP_DIR + File.separator + primaryArchive.getName() + "_dir";
				new File(outPath).mkdirs();

				try {
					// La store in alcuni casi non ritorna il mimetype delll'archivio. Nel caso si prova a calcolare prima di decomprimere l'archivio
					String mimeTypeArchive;
					if (filePrimario.getMimetype() != null && !"".equals(filePrimario.getMimetype())) {
						mimeTypeArchive = filePrimario.getMimetype();
					} else {
						lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(primaryArchive.toURI().toString(), filePrimario.getDisplayFilename(), false,
								null);
						mimeTypeArchive = lMimeTypeFirmaBean.getMimetype();
					}


					// Decomprimo l'archivio se i file contenuti nello zip non superano la soglia consentita
					ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
					if (lProtocolloUtility.isAttivaDecompressioneSogliaConsentita(primaryArchive, mimeTypeArchive)){

						DecompressioneArchiviBean lDecompressioneArchiviBean = ArchiveUtils.getArchiveContent(primaryArchive, mimeTypeArchive, outPath);

						if (lDecompressioneArchiviBean.getErroriArchivi() == null || lDecompressioneArchiviBean.getErroriArchivi().isEmpty()) {
							if (lDecompressioneArchiviBean.getErroriFiles() != null && !lDecompressioneArchiviBean.getErroriFiles().isEmpty()) {
								// Ci sono stati degli errori di lettura dei file decompressi, probabilmente dovute ad una protezione.
								List<String> errori = checkErroriFile(lDecompressioneArchiviBean.getErroriFiles());
								if (errori != null && !errori.isEmpty())
									erroriDecompressione.addAll(errori);
							}
							Map<String, String> listFileUriExtract = storeFileDecompressi(lDecompressioneArchiviBean.getFilesEstratti());
							sistemaArchivioEstratto(lDocumentoXmlOutBean, lFiles, listFileUriExtract, filePrimario.getDisplayFilename(),
									filePrimario.getAlgoritmoImpronta(), filePrimario.getEncodingImpronta(), true);
						} else {
							// C'è stato un errore nella decompressione dell'archivo: non si esegue nessuna modifica al file primario.
							List<String> errori = checkErroriArchivi(filePrimario.getDisplayFilename(), lDecompressioneArchiviBean.getErroriArchivi());
							if (errori != null && !errori.isEmpty())
								erroriDecompressione.addAll(errori);
						}
					}


				} catch (Exception e) {
					mLogger.error("Errore nella decompressione dell'archivio file primario", e);
					erroriDecompressione.add("Errore nella decompressione dell'archivio " + filePrimario.getDisplayFilename());
				} finally {
					// Eliminazione della directory in cui sono salvati i file temporanei
					try {
						FileUtils.deleteDirectory(new File(outPath));
					} catch (IOException e) {
						mLogger.error("Errore nell'eliminazione della directory temporanea", e);
					}
				}
			}
		}

		// Controlla se ogni allegato è un archivio
		Iterator<AllegatiOutBean> iteAllegato = lDocumentoXmlOutBean.getAllegati().iterator();
		while (iteAllegato.hasNext()) {
			AllegatiOutBean allegato = iteAllegato.next();
			if (checkArchive(allegato.getMimetype(), FilenameUtils.getExtension(allegato.getNomeOriginale()))){
				// Recupera l'Uri del file in questione
				String fileUri = recuperaUriDaLista(allegato.getNomeOriginale(), lFiles);
				if (fileUri != null && !fileUri.equals("")) {
					// Creazione e recupero della cartella temporanea dove estrarre i file.
					File archive = StorageImplementation.getStorage().extractFile(fileUri);
					String outPath = archive.getPath() + "_dir";
					new File(outPath).mkdirs();

					try {
						// La store in alcuni casi non ritorna il mimetype delll'archivio. Nel caso si prova a calcolare prima di decomprimere l'archivio
						String mimeTypeArchive;
						if (allegato.getMimetype() != null) {
							mimeTypeArchive = allegato.getMimetype();
						} else {
							lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(archive.toURI().toString(), allegato.getNomeOriginale(), false, null);
							mimeTypeArchive = lMimeTypeFirmaBean.getMimetype();
						}

						// Decomprimo l'archivio se i file contenuti nello zip non superano la soglia consentita
						ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
						if (lProtocolloUtility.isAttivaDecompressioneSogliaConsentita(archive, mimeTypeArchive)){
							DecompressioneArchiviBean lDecompressioneArchiviBean = ArchiveUtils.getArchiveContent(archive, mimeTypeArchive, outPath);

							if (lDecompressioneArchiviBean.getErroriArchivi() == null || lDecompressioneArchiviBean.getErroriArchivi().isEmpty()) {
								if (lDecompressioneArchiviBean.getErroriFiles() != null && !lDecompressioneArchiviBean.getErroriFiles().isEmpty()) {
									// Ci sono stati degli errori di lettura dei file decompressi, probabilmente dovute ad una protezione.
									List<String> errori = checkErroriFile(lDecompressioneArchiviBean.getErroriFiles());
									if (errori != null && !errori.isEmpty())
										erroriDecompressione.addAll(errori);
								}
								Map<String, String> listFileUriExtract = storeFileDecompressi(lDecompressioneArchiviBean.getFilesEstratti());
								//System.out.println("Analizzo file: "+allegato.getNomeOriginale());
								sistemaArchivioEstratto(lDocumentoXmlOutBean, lFiles, listFileUriExtract, allegato.getNomeOriginale(),
										allegato.getAlgoritmoImpronta(), allegato.getEncodingImpronta(), false);
							} else {
								// C'è stato un errore nella decompressione dell'archivo: non si esegue nessuna modifica all'allegato.
								List<String> errori = checkErroriArchivi(allegato.getNomeOriginale(), lDecompressioneArchiviBean.getErroriArchivi());
								if (errori != null && !errori.isEmpty())
									erroriDecompressione.addAll(errori);
							}
						}
					} catch (Exception e) {
						mLogger.error("Errore nella decompressione dell'archivio allegato", e);
						erroriDecompressione.add("Errore nella decompressione dell'archivio " + allegato.getNomeOriginale());
					} finally {
						// Eliminazione della directory in cui sono salvati i file temporanei
						try {
							FileUtils.deleteDirectory(new File(outPath));
						} catch (IOException e) {
							mLogger.error("Errore nell'eliminazione della directory temporanea", e);
						}
					}
				} else {
					iteAllegato.remove();
				}
			} 
		}
		return erroriDecompressione;
	}

	/**
	 * @param pathFolder
	 *            percorso in cui sono salvati i file decompressi
	 * @return listUriExtract mappa contenente il nome del file e l'Uri relativo metodo che ritorna una Mappa contenente il nome del file e l'uri relativo.
	 */
	private Map<String, String> storeFileDecompressi(List<File> filesListExtr) {
		Map<String, String> listUriExtract = new HashMap<>();
		try {
			for (File lFile : filesListExtr) {
				String fileName = lFile.getName();
				long size = lFile.length();
				String uri = StorageImplementation.getStorage().store(lFile);
				listUriExtract.put(fileName.concat(";").concat(String.valueOf(size)), uri);
			}
		} catch (StorageException e) {
			mLogger.error("Errore nello storage del file decompressato", e);
		}
		return listUriExtract;
	}

	/**
	 * 
	 * @param lDocumentoXmlOutBean
	 *            bean contenente le informazioni della mail da protocollare
	 * @param lFiles
	 *            mappa contenente gli allegati e relativi uri
	 * @param listFileUriExtract
	 *            mappa contenente i file estratti dall'archivio e relativi uri
	 * @param nomeArchivio
	 *            nome dell'archivio estratto
	 * @param algoritmoImpronta
	 *            algorimo utilizzato per il calcolo dell'impronta dell'archivio
	 * @param encodingImpronta
	 *            encoding utilizzato per il calcolo dell'impronta dell'archivio
	 * @param isFilePrimario
	 *            indica se l'archivio in questione era il file primario o meno.
	 * 
	 *            metodo che aggiunge i nuovi file estratti ai bean relativi, eliminando allo stesso tempo l'archivio decompressato dai suddetti.
	 */
	private void sistemaArchivioEstratto(DocumentoXmlOutBean lDocumentoXmlOutBean, Map<String, Object> lFiles, Map<String, String> listFileUriExtract,
			String nomeArchivio, String algoritmoImpronta, String encodingImpronta, boolean isFilePrimario) {

		CalcolaImpronteService calcolaImpronteService = new CalcolaImpronteService();
		List<AllegatiOutBean> allegatiEstratti = new ArrayList<>();

		for (Entry<String, String> lFileEntry : listFileUriExtract.entrySet()) {
			try {
				File fileExt = StorageImplementation.getStorage().extractFile(lFileEntry.getValue());
				lFiles.put(lFileEntry.getKey(), lFileEntry.getValue());
				String fileName = lFileEntry.getKey().split(";")[0]; // recupero nome originale file

				lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(fileExt.toURI().toString(), fileName, false, null);
				String mimeType = lMimeTypeFirmaBean.getMimetype();

				if (mimeType == null) {
					mimeType = ""; // nel caso non si riesca a calcolare il mimetype del file, si setta un valore per non incorrere a eccezioni future durante i
					// controlli.
				}
				String impronta = calcolaImpronteService.calcolaImprontaWithoutFileOp(fileExt, algoritmoImpronta, encodingImpronta);

				if (listFileUriExtract.size() == 1 && isFilePrimario) {
					// caso particolare in cui il file primario è uno zip che contiene un solo file
					FilePrimarioOutBean filePrimarioNew = createFilePrimario(algoritmoImpronta, encodingImpronta, BigDecimal.valueOf(fileExt.length()),
							fileName, mimeType, impronta, lMimeTypeFirmaBean);
					lDocumentoXmlOutBean.setFilePrimario(filePrimarioNew);
				} else {
					// creazione bean per l'allegato estratto.
					AllegatiOutBean bean = createAllegato(algoritmoImpronta, encodingImpronta, BigDecimal.valueOf(fileExt.length()), fileName, mimeType,
							impronta, lMimeTypeFirmaBean);
					allegatiEstratti.add(bean);
				}
			} catch (StorageException e) {
				mLogger.error("Errore durante il recupero dallo store del file estratto", e);
			} catch (Exception e) {
				mLogger.error("Errore durante il calcolo dell'impronta per il file estratto", e);
			}
		}

		List<AllegatiOutBean> listaAllegatiNew = new ArrayList<>(lDocumentoXmlOutBean.getAllegati()); // Nuova lista di allegati per lDocumentoXmlOutBean

		// Eliminazione dell'archivio da DocumentoXmlOutBean
		if (isFilePrimario && listFileUriExtract.size() != 1) {
			lDocumentoXmlOutBean.setFilePrimario(null);
			// Aggiornamento lista allegati lDocumentoXmlOutBean con gli elementi estratti
			listaAllegatiNew.addAll(allegatiEstratti);
			lDocumentoXmlOutBean.setAllegati(listaAllegatiNew);
		} else if (!isFilePrimario) {
			// eliminazione dell'archivio allegato
			List<AllegatiOutBean> listaAllegatiNewToRemove = new ArrayList<AllegatiOutBean>();
			for (AllegatiOutBean allegato : listaAllegatiNew) {
				if (allegato.getNomeOriginale().equals(nomeArchivio)) {
					//listaAllegatiNew.remove(allegato);
					//break;
					listaAllegatiNewToRemove.add(allegato);
				}
			}
			// Gestita casistica di due file compressi di cui uno firmato e l'altro no avente gli stessi file
			for (AllegatiOutBean allegato2 : listaAllegatiNewToRemove) {
				if (allegato2.getNomeOriginale().equals(nomeArchivio)) {
					listaAllegatiNew.remove(allegato2);
				}
			}
			// Aggiornamento lista allegati lDocumentoXmlOutBean con gli elementi estratti
			listaAllegatiNew.addAll(allegatiEstratti);
			lDocumentoXmlOutBean.setAllegati(listaAllegatiNew);
		}

		// Eliminazione dell'archivio da lFiles
		for (Entry<String, Object> lFile : lFiles.entrySet()) {			
			String[] splits = lFile.getKey().split(";");
			if (splits[0].equals(nomeArchivio)) {
				lFiles.remove(lFile.getKey());
				break;
			}
		}

	}

	private AllegatiOutBean createAllegato(String algoritmoImpronta, String encodingImpronta, BigDecimal dimension, String fileName, String mimeType,
			String impronta, MimeTypeFirmaBean mimeTypeFirmaBean) {
		
		AllegatiOutBean bean = new AllegatiOutBean();
		bean.setAlgoritmoImpronta(algoritmoImpronta);
		bean.setEncodingImpronta(encodingImpronta);
		bean.setImpronta(impronta);
		bean.setDisplayFileName(fileName);
		bean.setNomeOriginale(fileName);
		bean.setMimetype(mimeType);
		bean.setDimensione(dimension);
		
		/**
		 * INFORMAZIONI SULLA FIRMA & MARCA
		 */
		bean.setFlgFileFirmato(mimeTypeFirmaBean != null && mimeTypeFirmaBean.isFirmato() ? Flag.SETTED : Flag.NOT_SETTED);
		if(mimeTypeFirmaBean != null && mimeTypeFirmaBean.getFirmatari() != null &&
				mimeTypeFirmaBean.getFirmatari().length > 0) {
			String firmatari = "";
			if(mimeTypeFirmaBean.getFirmatari().length == 1) {
				firmatari = mimeTypeFirmaBean.getFirmatari()[0];
			} else {
				for(String item : mimeTypeFirmaBean.getFirmatari()) {
					firmatari += item.concat(",");
				}
			}
			bean.setFirmatari(firmatari);
		}
		bean.setFlgPdfProtetto(mimeTypeFirmaBean != null && mimeTypeFirmaBean.isPdfProtetto() ? Flag.SETTED : Flag.NOT_SETTED);
		bean.setFlgPdfEditabile(mimeTypeFirmaBean != null && mimeTypeFirmaBean.isPdfEditabile() ? Flag.SETTED : Flag.NOT_SETTED);
		bean.setFlgPdfConCommenti(mimeTypeFirmaBean != null && mimeTypeFirmaBean.isPdfConCommenti() ? Flag.SETTED : Flag.NOT_SETTED);
		
		bean.setFlgConvertibilePdf(mimeTypeFirmaBean != null && mimeTypeFirmaBean.isConvertibile() ? Flag.SETTED : Flag.NOT_SETTED);
		
		bean.setFlgBustaCrittograficaAuriga(mimeTypeFirmaBean.getInfoFirmaMarca() != null && mimeTypeFirmaBean.getInfoFirmaMarca() != null
				&& mimeTypeFirmaBean.getInfoFirmaMarca().isBustaCrittograficaFattaDaAuriga() ? Flag.SETTED : Flag.NOT_SETTED);
		bean.setFlgFirmeNonValideBustaCrittografica(mimeTypeFirmaBean.getInfoFirmaMarca() != null && mimeTypeFirmaBean.getInfoFirmaMarca() != null
				&& mimeTypeFirmaBean.getInfoFirmaMarca().isFirmeNonValideBustaCrittografica() ? Flag.SETTED : Flag.NOT_SETTED);
		bean.setFlgFirmeExtraAuriga(mimeTypeFirmaBean.getInfoFirmaMarca() != null && mimeTypeFirmaBean.getInfoFirmaMarca() != null
				&& mimeTypeFirmaBean.getInfoFirmaMarca().isFirmeExtraAuriga() ? Flag.SETTED : Flag.NOT_SETTED);
		bean.setFlgMarcaTemporaleAuriga(mimeTypeFirmaBean.getInfoFirmaMarca() != null && mimeTypeFirmaBean.getInfoFirmaMarca() != null
				&& mimeTypeFirmaBean.getInfoFirmaMarca().isMarcaTemporaleAppostaDaAuriga() ? Flag.SETTED : Flag.NOT_SETTED);
		bean.setDataOraMarcaTemporale(mimeTypeFirmaBean.getInfoFirmaMarca() != null && mimeTypeFirmaBean.getInfoFirmaMarca() != null
				&& mimeTypeFirmaBean.getInfoFirmaMarca().getDataOraMarcaTemporale() != null ? 
						mimeTypeFirmaBean.getInfoFirmaMarca().getDataOraMarcaTemporale() : null);
		bean.setFlgMarcaTemporaleNonValida(mimeTypeFirmaBean.getInfoFirmaMarca() != null && mimeTypeFirmaBean.getInfoFirmaMarca() != null
				&& mimeTypeFirmaBean.getInfoFirmaMarca().isMarcaTemporaleNonValida() ? Flag.SETTED : Flag.NOT_SETTED);
		
		return bean;
	}

	private FilePrimarioOutBean createFilePrimario(String algoritmoImpronta, String encodingImpronta, BigDecimal dimension, String fileName, String mimeType,
			String impronta, MimeTypeFirmaBean mimeTypeFirmaBean) {
		
		FilePrimarioOutBean filePrimarioNew = new FilePrimarioOutBean();
		filePrimarioNew.setAlgoritmoImpronta(algoritmoImpronta);
		filePrimarioNew.setEncodingImpronta(encodingImpronta);
		filePrimarioNew.setImpronta(impronta);
		filePrimarioNew.setNomeOriginale(fileName);
		filePrimarioNew.setDisplayFilename(fileName);
		filePrimarioNew.setMimetype(mimeType);
		filePrimarioNew.setDimensione(dimension);
		
		/**
		 * INFORMAZIONI SULLA FIRMA & MARCA
		 */
		filePrimarioNew.setFlgFirmato(mimeTypeFirmaBean != null && mimeTypeFirmaBean.isFirmato() ? Flag.SETTED : Flag.NOT_SETTED);
		if(mimeTypeFirmaBean != null && mimeTypeFirmaBean.getFirmatari() != null &&
				mimeTypeFirmaBean.getFirmatari().length > 0) {
			String firmatari = "";
			if(mimeTypeFirmaBean.getFirmatari().length == 1) {
				firmatari = mimeTypeFirmaBean.getFirmatari()[0];
			} else {
				for(String item : mimeTypeFirmaBean.getFirmatari()) {
					firmatari += item.concat(",");
				}
			}
			filePrimarioNew.setFirmatari(firmatari);
		}
		filePrimarioNew.setFlgPdfProtetto(mimeTypeFirmaBean != null && mimeTypeFirmaBean.isPdfProtetto() ? Flag.SETTED : Flag.NOT_SETTED);
		filePrimarioNew.setFlgPdfEditabile(mimeTypeFirmaBean != null && mimeTypeFirmaBean.isPdfEditabile() ? Flag.SETTED : Flag.NOT_SETTED);
		filePrimarioNew.setFlgPdfConCommenti(mimeTypeFirmaBean != null && mimeTypeFirmaBean.isPdfConCommenti() ? Flag.SETTED : Flag.NOT_SETTED);

		filePrimarioNew.setFlgConvertibilePdf(mimeTypeFirmaBean != null && mimeTypeFirmaBean.isConvertibile() ? Flag.SETTED : Flag.NOT_SETTED);
		
		filePrimarioNew.setFlgBustaCrittograficaAuriga(mimeTypeFirmaBean.getInfoFirmaMarca() != null && mimeTypeFirmaBean.getInfoFirmaMarca() != null
				&& mimeTypeFirmaBean.getInfoFirmaMarca().isBustaCrittograficaFattaDaAuriga() ? Flag.SETTED : Flag.NOT_SETTED);
		filePrimarioNew.setFlgFirmeNonValideBustaCrittografica(mimeTypeFirmaBean.getInfoFirmaMarca() != null && mimeTypeFirmaBean.getInfoFirmaMarca() != null
				&& mimeTypeFirmaBean.getInfoFirmaMarca().isFirmeNonValideBustaCrittografica() ? Flag.SETTED : Flag.NOT_SETTED);
		filePrimarioNew.setFlgFirmeExtraAuriga(mimeTypeFirmaBean.getInfoFirmaMarca() != null && mimeTypeFirmaBean.getInfoFirmaMarca() != null
				&& mimeTypeFirmaBean.getInfoFirmaMarca().isFirmeExtraAuriga() ? Flag.SETTED : Flag.NOT_SETTED);
		filePrimarioNew.setFlgMarcaTemporaleAuriga(mimeTypeFirmaBean.getInfoFirmaMarca() != null && mimeTypeFirmaBean.getInfoFirmaMarca() != null
				&& mimeTypeFirmaBean.getInfoFirmaMarca().isMarcaTemporaleAppostaDaAuriga() ? Flag.SETTED : Flag.NOT_SETTED);
		filePrimarioNew.setDataOraMarcaTemporale(mimeTypeFirmaBean.getInfoFirmaMarca() != null && mimeTypeFirmaBean.getInfoFirmaMarca() != null
				&& mimeTypeFirmaBean.getInfoFirmaMarca().getDataOraMarcaTemporale() != null ? 
						mimeTypeFirmaBean.getInfoFirmaMarca().getDataOraMarcaTemporale() : null);
		filePrimarioNew.setFlgMarcaTemporaleNonValida(mimeTypeFirmaBean.getInfoFirmaMarca() != null && mimeTypeFirmaBean.getInfoFirmaMarca() != null
				&& mimeTypeFirmaBean.getInfoFirmaMarca().isMarcaTemporaleNonValida() ? Flag.SETTED : Flag.NOT_SETTED);
		
		
		return filePrimarioNew;
	}

	/**
	 * @param erroriFiles
	 *            mappa contenente gli eventuali errori dei file, <nome_file, tipo_erore> metodo che stampa un messaggio di warning.
	 */
	private List<String> checkErroriFile(Map<String, String> erroriFiles) {
		List<String> erroriFile = new ArrayList<>();
		for (Entry<String, String> lEntry : erroriFiles.entrySet()) {
			if (lEntry.getValue().contains("password")) {
				erroriFile.add("Il file decompresso " + lEntry.getKey() + " è protetto da password");
			}
		}
		return erroriFile;
	}

	/**
	 * @param nomeArchivo
	 *            nome dell'archivo non decompressato correttamente
	 * @param mappaErroriArchivi
	 *            mappa contenente gli eventuali errori dei file, <nome_archivio, tipo_erore> metodo che stampa un messaggio di warning.
	 */
	private List<String> checkErroriArchivi(String nomeArchivio, Map<String, String> mappaErroriArchivi) {
		List<String> erroriArchivi = new ArrayList<>();
		for (Entry<String, String> lEntry : mappaErroriArchivi.entrySet()) {
			if (lEntry.getValue().contains("password")) {
				erroriArchivi.add("Non è stato possibile decomprimere l'archivio " + nomeArchivio + " in quanto protetto da password");
			} else {
				erroriArchivi.add("Errore nella decompressione dell'archivio " + nomeArchivio);
			}
		}
		return erroriArchivi;
	}

	/**
	 * 
	 * @param name
	 *            nome del file da cercare nella lista
	 * @param lFiles
	 *            lista contenenente i nomi dei file e Uri relativo nello store
	 * @return l'uri del file ricercato. null altrimenti.
	 */
	private String recuperaUriDaLista(String name, Map<String, Object> lFiles) {
		for (Map.Entry<String, Object> entry : lFiles.entrySet()) {
			String[] splits = entry.getKey().split(";");
			if (splits[0].equals(name)) {
				return (String) entry.getValue();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param lTipoProvenienza
	 *            indica il tipo di mail (entrata, uscita, interna)
	 * @return se è attiva o meno la decompressione degli archivi per le mail in entrata durante la fase di protocollazione
	 */
	private boolean isAttivaDecompressionePerMailEntrata(TipoProvenienza lTipoProvenienza) {

		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		boolean ret = lProtocolloUtility.isAttivaDecompressione() && (lTipoProvenienza == TipoProvenienza.ENTRATA);
		return ret;		
	}

	/**
	 * 
	 * @param mime
	 *            mime del file
	 * @param ext
	 *            estensione del file
	 * @return se il file in ingresso è un archivio o meno
	 * 
	 */
	private boolean checkArchive(String mime, String ext) {
		for (ArchiveType lArchiveType : ArchiveType.values()) {
			if (lArchiveType.getMime().equals(mime) || lArchiveType.getType().equals(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param files
	 *            lista AllegatoBean da controllare l'estensione
	 * @return i file che attualmente non sono riconosciuti da Auriga
	 */
	private List<String> checkEstensioneFile(List<AllegatoProtocolloBean> files) {
		List<String> erroriEstensioni = new ArrayList<>();
		for (AllegatoProtocolloBean allegato : files) {
			if (allegato.getInfoFile().getMimetype() == null) {
				erroriEstensioni.add("Il formato dell'allegato " + allegato.getNomeFileAllegato()
				+ " non è stato riconosciuto al momento dello scarico della email." + "Contattare il supporto");
			}
		}
		return erroriEstensioni;
	}

	public String calcolaImpronta(String fileUrl, String displayFilename) throws Exception {
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		return lInfoFileUtility.calcolaImpronta(fileUrl, displayFilename);
	}

	/**
	 * Metodo che aggiunge i file recuperati dalla scheda note e appunti alla lista dei file principali lFiles e modifica il bean della protocollazione
	 * relativo.
	 * 
	 * @param lFiles
	 *            Lista attuale dei file.
	 * @param lDocumentoXmlOutBean
	 *            Bean contenente i dati relativi alla protocollazione
	 * @param fileDaAppunti
	 *            File recuperati dalla scheda note e appunti. Saranno aggiunti come allegati alla protocollazione
	 */
	private void processFileDaAppunti(Map<String, Object> lFiles, DocumentoXmlOutBean lDocumentoXmlOutBean, List<ItemLavorazioneMailBean> fileDaAppunti) {
		List<AllegatiOutBean> listAllegatiBean = new ArrayList<>(); // lista dove contenere i nuovi file recuperati dagli appunti sottoforma di AllegatiOutBean
		for (ItemLavorazioneMailBean itemLavorazioneBean : fileDaAppunti) {
			String filename = StringUtils.isNotBlank(itemLavorazioneBean.getItemLavNomeFile()) ? itemLavorazioneBean.getItemLavNomeFile()
					: itemLavorazioneBean.getItemLavInfoFile().getCorrectFileName();
			String uriAllegato = itemLavorazioneBean.getItemLavUriFile();
			String size = StringUtils.isNotBlank(itemLavorazioneBean.getItemLavDimensioneFile()) ? itemLavorazioneBean.getItemLavDimensioneFile()
					: String.valueOf(itemLavorazioneBean.getItemLavInfoFile().getBytes());
			String algoritmoImpronta = StringUtils.isNotBlank(itemLavorazioneBean.getItemLavAlgoritmoImprontaFile())
					? itemLavorazioneBean.getItemLavAlgoritmoImprontaFile() : itemLavorazioneBean.getItemLavInfoFile().getAlgoritmo();
					String encodingImpronta = StringUtils.isNotBlank(itemLavorazioneBean.getItemLavAlgoritmoEncodingImprontaFile())
							? itemLavorazioneBean.getItemLavAlgoritmoEncodingImprontaFile() : itemLavorazioneBean.getItemLavInfoFile().getEncoding();
							String mimeType = StringUtils.isNotBlank(itemLavorazioneBean.getItemLavMimeTypeFile()) ? itemLavorazioneBean.getItemLavMimeTypeFile()
									: itemLavorazioneBean.getItemLavInfoFile().getMimetype();
							String impronta = StringUtils.isNotBlank(itemLavorazioneBean.getItemLavImprontaFile()) ? itemLavorazioneBean.getItemLavImprontaFile()
									: itemLavorazioneBean.getItemLavInfoFile().getImpronta();

							lFiles.put(filename.concat(";").concat(size), (String) uriAllegato);
							AllegatiOutBean allegatoBean = createAllegato(algoritmoImpronta, encodingImpronta, new BigDecimal(size), filename, mimeType, 
									impronta,itemLavorazioneBean.getItemLavInfoFile());
							listAllegatiBean.add(allegatoBean);

		}
		List<AllegatiOutBean> allegatiOutBeanList = lDocumentoXmlOutBean.getAllegati();
		allegatiOutBeanList.addAll(listAllegatiBean);
		lDocumentoXmlOutBean.setAllegati(allegatiOutBeanList);
	}

	public ProtocollazioneBean sbloccaMail(ProtocollazioneBean pProtocollazioneBean) throws Exception {
		LockUnlockMail lLockUnlockEmail = new LockUnlockMail(getSession());
		try {
			lLockUnlockEmail.unlockMail(pProtocollazioneBean.getIdEmailArrivo());
		} catch (Exception e) {
		}
		return new ProtocollazioneBean();
	}
	
	public boolean isRichiestaAccessoAtti() { 
		// Verifico se sono in una richiesta di accesso atti
		return getExtraparams().get("isRichiestaAccessoAtti") != null && "true".equals(getExtraparams().get("isRichiestaAccessoAtti"));
	}
	
	private String rimuoviCommentiInHtml (String html) {
		String result = "";
        // Compile regular expression
        final Pattern pattern = Pattern.compile("<!--(.*?)-->", Pattern.DOTALL);
        final Matcher matcher = pattern.matcher(html);
        result = matcher.find() ? matcher.replaceAll("") : html;
		return result;
	}

}