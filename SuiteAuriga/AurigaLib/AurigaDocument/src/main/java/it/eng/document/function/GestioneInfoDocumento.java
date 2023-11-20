/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddverdocBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddverdocImpl;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrodigregBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.client.AurigaMailService;
import it.eng.client.DmpkRegistrazionedocGettimbrodigreg;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.FileToSendBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.InviaInfoDocumentoInputBean;
import it.eng.document.function.bean.InviaInfoDocumentoOutputBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.storage.DocumentStorage;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;
import it.eng.utility.TimbraUtil;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.xml.XmlUtilitySerializer;

@Service(name = "GestioneInfoDocumento")
public class GestioneInfoDocumento {

	private static Logger mLogger = Logger.getLogger(GestioneInfoDocumento.class);

	@Operation(name = "inviaInfo")
	public InviaInfoDocumentoOutputBean inviaInfo(AurigaLoginBean pAurigaLoginBean, InviaInfoDocumentoInputBean input) {
		InviaInfoDocumentoOutputBean output = new InviaInfoDocumentoOutputBean();

		try {
			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);

			File modelloPdf;
			if (StringUtils.isNotBlank(input.getUriFileModello())) {
				modelloPdf = DocumentStorage.extract(input.getUriFileModello(), null);

				File modelloPdfTimbrato = null;
				if (input.isFlgTimbraFileModello()) {
					modelloPdfTimbrato = timbraFile(modelloPdf, input.getNomeFileModello(), input.getTestoInChiaroBarCode(), input.getContenutoBarCode());
				}

				if (input.isFlgVersionaModello()) {
					/** PREPARO IL FILE DA VERSIONARE */
					RebuildedFile lRebuildedFile = new RebuildedFile();
					lRebuildedFile.setIdDocumento(StringUtils.isNotBlank(input.getIdDocFileModello()) ? new BigDecimal(input.getIdDocFileModello()) : null);

					// Se il file e stato timbrato correttamente lo versiono
					if (modelloPdfTimbrato != null) {
						lRebuildedFile.setFile(modelloPdfTimbrato);
						lRebuildedFile.setInfo(getFileInfoBean(modelloPdfTimbrato));
					}
					// Se c'e stato un errore durante la timbratura versiono il file non timbrato
					else {
						lRebuildedFile.setFile(modelloPdf);
						lRebuildedFile.setInfo(getFileInfoBean(modelloPdf));
					}

					String uriPdfTimbratoVersionato = versionaDocumento(getVersionaDocumentoInBean(lRebuildedFile), pAurigaLoginBean);

				}

				List<FileToSendBean> filesDaInviare = new ArrayList<>();
				if (input.isFlgInviaAllegati()) {
					if (input.isFlgTimbraAllegati()) {
						timbraAllegatiCopiaConforme(input.getAllegatiMail(), pAurigaLoginBean);
					}

					filesDaInviare.addAll(input.getAllegatiMail());
				}

				/* AGGIUNGO IL MODELLO AI FILE DA INVIARE */
				FileToSendBean fileModello = new FileToSendBean();
				fileModello.setUri(modelloPdfTimbrato != null ? DocumentStorage.store(modelloPdfTimbrato, null) : input.getUriFileModello());
				fileModello.setNomeFile(input.getNomeFileModello());
				fileModello.setDimensione(modelloPdfTimbrato != null ? new BigDecimal(modelloPdfTimbrato.length()) : new BigDecimal(modelloPdf.length()));
				fileModello.setMimetype("application\\pdf");

				filesDaInviare.add(fileModello);

				inviaModello(input, filesDaInviare);

				
			} else {
				mLogger.error("Uri Modello non presente");
				throw new Exception("Uri Modello non presente");
			}

		} catch (Exception e) {
			String errorMessage = "Errore durante l'operazione di invio delle operazioni: " + e.getMessage();
			mLogger.error(errorMessage);
			output.setInError(true);
			output.setErrorMessage(errorMessage);
		}

		return output;

	}


	private void inviaModello(InviaInfoDocumentoInputBean input, List<FileToSendBean> filesToSend) throws Exception {

		SenderBean senderBean = new SenderBean();
		ResultBean<EmailSentReferenceBean> output = null;

		senderBean.setFlgInvioSeparato(false);
		senderBean.setIsPec(true);

		// Mittente
		// testeng@serviziopec.eng.it
		senderBean.setAccount(input.getMittente());
		senderBean.setAddressFrom(input.getMittente());

		// Destinatari principali
		if(input.getDestinatari() != null) {		
			senderBean.setAddressTo(input.getDestinatari());
		} else {
			 throw new Exception("Destinatari non presenti");
		}		

		// Oggetto
		senderBean.setSubject(input.getOggetto());

		// CORPO
		senderBean.setBody(input.getCorpoMail());
		senderBean.setIsHtml(true);

		// Conferma di lettura
		senderBean.setReturnReceipt(false);

		// Attach
		List<SenderAttachmentsBean> listaSenderAttachmentsBean = new ArrayList<SenderAttachmentsBean>();
		for (FileToSendBean file : filesToSend) {

			String uriFile = file.getUri();

			File fileOut; 
			try {
				fileOut = DocumentStorage.extract(uriFile, null);
			} catch (StorageException e) {
				mLogger.error("Errore durante il recupero del file per l'invio mail : " + e.getMessage(),e);
				throw new StorageException(e.getMessage(), e);
			}
			// Se il servizio e' andato in errore restituisco il messaggio di errore
			if (fileOut == null) {
				mLogger.error("Errore durante invio mail: Errore nel recupero del file");
				throw new Exception("Errore durante invio mail: Errore nel recupero del file");
			}

			SenderAttachmentsBean senderAttachmentsBean = new SenderAttachmentsBean();
			senderAttachmentsBean.setFile(fileOut);
			senderAttachmentsBean.setFirmato(file.isFirmato());
			senderAttachmentsBean.setFirmaValida(file.isFirmaValida());
			senderAttachmentsBean.setMimetype(file.getMimetype());
			senderAttachmentsBean.setOriginalName(file.getNomeFile());
			senderAttachmentsBean.setDimensione(file.getDimensione());
			senderAttachmentsBean.setAlgoritmo(file.getAlgoritmo());
			senderAttachmentsBean.setEncoding(file.getEncoding());
			senderAttachmentsBean.setImpronta(file.getImpronta());
			senderAttachmentsBean.setFilename(file.getNomeFile());
			
			listaSenderAttachmentsBean.add(senderAttachmentsBean);
		}
		senderBean.setAttachments(listaSenderAttachmentsBean);

		try {
			output = AurigaMailService.getMailSenderService().sendandsave(new Locale("it"), senderBean);
			
			if(StringUtils.isBlank(output.getResultBean().getIdEmails().get(0)) || output.isInError() ) {
				throw new Exception(output.getDefaultMessage());
			}
		} catch (Exception e) {
			mLogger.error("Errore durante invio mail: " +e.getMessage(),e);
			throw new Exception(e.getMessage(), e);
		}	
	}


	private void timbraAllegatiCopiaConforme(List<FileToSendBean> allegatiMail, AurigaLoginBean pAurigaLoginBean) {
		
		for (FileToSendBean fileDaTimbrare : allegatiMail) {
			if (fileDaTimbrare.getIdUD() != null) {
				try {
					//recupero la segnatura del timbro da apporre sul file
					DmpkRegistrazionedocGettimbrodigregBean result = getSegnaturaStore(fileDaTimbrare.getIdUD(), pAurigaLoginBean);
					if (result != null) {
						String contenutoBarcode = result.getContenutobarcodeout();
						String testoInChiaroBarcode = result.getTestoinchiaroout();
						String nomeFile = fileDaTimbrare.getNomeFile();

						File fileExtractDaTimbrare = DocumentStorage.extract(fileDaTimbrare.getUri(), null);

						File fileTimbrato = timbraFile(fileExtractDaTimbrare, nomeFile, testoInChiaroBarcode, contenutoBarcode);

						String uriFileTimbrato = DocumentStorage.store(fileTimbrato, null);

						fileDaTimbrare.setUri(uriFileTimbrato);
						
						/*sistemare nome file timbrato*/
						fileDaTimbrare.setNomeFile(nomeFile);
					}
				} catch (Exception ex) {
					mLogger.error("Errore durante l'apposizione del timbro al file: " + fileDaTimbrare.getNomeFile() + " error: " + ex.getMessage() + "\n" + ex);
				}
			}
		}			
	}
	
	private DmpkRegistrazionedocGettimbrodigregBean getSegnaturaStore(String idUD, AurigaLoginBean pAurigaLoginBean) throws Exception {
		DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
		input.setIdudio(new BigDecimal(idUD));
		input.setFinalitain("CONFORMITA_ORIG_CARTACEO");

		DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
		StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> result = store.execute(null, pAurigaLoginBean, input);

		if (result.isInError()) {
			mLogger.error("Errore durante il recupero della segnatura del timbro: " + result.getDefaultMessage());
			throw new Exception("Errore durante il recupero della segnatura del timbro: " + result.getDefaultMessage());
		}	
		
		return result.getResultBean();
	}


	private File timbraFile(File fileDaTimbrare, String nomeFile, String testoInChiaroBarCode, String contenutoBarCode) throws Exception {
		try {
			OpzioniCopertinaTimbroBean opzioniTimbro = (OpzioniCopertinaTimbroBean) SpringAppContext.getContext()
					.getBean("OpzioniTimbro");
			
			TimbraUtil.impostaTestoOpzioniTimbro(opzioniTimbro, testoInChiaroBarCode, contenutoBarCode);
			InputStream fileTimbratoStream = TimbraUtil.timbra(fileDaTimbrare, nomeFile, opzioniTimbro,
					false);
			File fileTimbrato = File.createTempFile("tmp", ".pdf");
			FileUtils.copyInputStreamToFile(fileTimbratoStream, fileTimbrato);
			return fileTimbrato;
		} catch (Exception e) {
			mLogger.error("Errore durante la timbratura del file: " + nomeFile + "\n" + e.getMessage(), e);
//			throw new Exception("Errore durante la timbratura del file: " + modelloPdf.getName() + "\n" + e.getMessage(), e);
			
			return null;
		}
		
	}
	

	protected VersionaDocumentoInBean getVersionaDocumentoInBean(RebuildedFile lRebuildedFile)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
		return lVersionaDocumentoInBean;
	}

	
	
	private String versionaDocumento(VersionaDocumentoInBean lVersionaDocumentoInBean, AurigaLoginBean pAurigaLoginBean) throws Exception {
		
		String uriVer = null;
		FileStoreBean lFileStoreBean = new FileStoreBean();
		
		Session session = null;
		
		try {
			
			uriVer = DocumentStorage.store(lVersionaDocumentoInBean.getFile(),
					pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
			mLogger.debug("Salvato " + uriVer);
			
			lFileStoreBean.setUri(uriVer);
			lFileStoreBean.setDimensione(lVersionaDocumentoInBean.getFile().length());
			
			try {
				BeanUtilsBean2.getInstance().copyProperties(lFileStoreBean,
						lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento());
			} catch (Exception e) {
				mLogger.warn(e);
			}
			
			String lStringXml = "";
			try {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
				mLogger.debug("attributiVerXml " + lStringXml);
			} catch (Exception e) {
				mLogger.warn(e);
			}
			
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
				
			DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
			lAddverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lAddverdocBean.setIduserlavoroin(null);
			lAddverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
			lAddverdocBean.setAttributiverxmlin(lStringXml);
			
			final AddverdocImpl store = new AddverdocImpl();
			store.setBean(lAddverdocBean);
			mLogger.debug("Chiamo la addVerdoc " + lAddverdocBean);
			
			session.doWork(new Work() {
				
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					store.execute(paramConnection);
				}
			});
			
			StoreResultBean<DmpkCoreAddverdocBean> lAddverdocStoreResultBean = new StoreResultBean<DmpkCoreAddverdocBean>();
			AnalyzeResult.analyze(lAddverdocBean, lAddverdocStoreResultBean);
			lAddverdocStoreResultBean.setResultBean(lAddverdocBean);
			
			session.flush();
			lTransaction.commit();
			
			if (lAddverdocStoreResultBean.isInError()) {
				mLogger.error("Errore durante il versionamento del file: " + lFileStoreBean.getDisplayFilename()  + "\n" + lAddverdocStoreResultBean.getDefaultMessage());
				throw new Exception("Errore durante il versionamento del file: " + lFileStoreBean.getDisplayFilename() + "\n" + lAddverdocStoreResultBean.getDefaultMessage());
			}
		} catch (Throwable e) {
			mLogger.error("Errore durante il versionamento del file: " + lFileStoreBean.getDisplayFilename() + "\n" + e.getMessage(), e);
			throw new Exception("Errore durante il versionamento del file: " + lFileStoreBean.getDisplayFilename() + "\n" + e.getMessage(), e);
		} finally {
			HibernateUtil.release(session);
		}
		
		return uriVer;
	}
	
	public FileInfoBean getFileInfoBean(File file) throws Exception {
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		try {
		lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(file.toURI().toString(), file.getName(), false,
				null);
		} catch (Exception e) {
			mLogger.error("Errore durante il recupero delle informazioni del file " + file.getName() + " - path : " + file.getPath());
			throw new Exception(e.getMessage(), e);
		}
		GenericFile allegatoRiferimento = new GenericFile();
		allegatoRiferimento.setDisplayFilename(file.getName());
		allegatoRiferimento.setImpronta(lMimeTypeFirmaBean.getImpronta());
		allegatoRiferimento.setMimetype(lMimeTypeFirmaBean.getMimetype());
		allegatoRiferimento.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
		allegatoRiferimento.setEncoding(lMimeTypeFirmaBean.getEncoding());
		allegatoRiferimento.setIdFormato(lMimeTypeFirmaBean.getIdFormato());
		allegatoRiferimento.setFirmato(lMimeTypeFirmaBean.isFirmato() ? Flag.SETTED : Flag.NOT_SETTED);
		
		FileInfoBean info = new FileInfoBean();
		info.setTipo(TipoFile.ALLEGATO);
		info.setAllegatoRiferimento(allegatoRiferimento);
		return info;
	}


	public Locale getLocale(){
		return new Locale("it", "IT");
	}	
}