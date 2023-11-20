/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIuassociazioneudvsprocBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIueventocustomBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.editorapplet.datasource.bean.EditorAppletBean;
import it.eng.client.DmpkProcessesIuassociazioneudvsproc;
import it.eng.client.DmpkProcessesIueventocustom;
import it.eng.client.GestioneDocumenti;
import it.eng.client.GestioneTask;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.EseguiTaskInBean;
import it.eng.document.function.bean.EseguiTaskOutBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "EditorAppletDataSource")
public class EditorAppletDataSource extends AbstractServiceDataSource<EditorAppletBean, EditorAppletBean> {

	private static Logger mLogger = Logger.getLogger(EditorAppletDataSource.class);

	public EditorAppletBean lockUd(EditorAppletBean pInBean) throws Exception {
		if (StringUtils.isNotEmpty(pInBean.getIdUd())) {
			callStoreLock(pInBean.getIdUd(), "I");
		}
		return pInBean;
	}

	public EditorAppletBean unlockUd(EditorAppletBean pInBean) throws Exception {
		if (StringUtils.isNotEmpty(pInBean.getIdUd())) {
			callStoreLock(pInBean.getIdUd(), "O");
		}
		return pInBean;
	}

	protected void callStoreLock(String idUd, String flag) throws Exception, StoreException {
		// DmpkCoreLockudBean lDmpkCoreLockudBean = new DmpkCoreLockudBean();
		// DmpkCoreLockud lDmpkCoreLockud = new DmpkCoreLockud();
		// lDmpkCoreLockudBean.setFlgtipolockin(flag);
		// lDmpkCoreLockudBean.setIdudin(new BigDecimal(idUd));
		// StoreResultBean<DmpkCoreLockudBean> lStoreResult = lDmpkCoreLockud.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
		// lDmpkCoreLockudBean);
		// if (lStoreResult.isInError() || lStoreResult.getDefaultMessage()!=null){
		// throw new StoreException(lStoreResult);
		// }
	}

	public EditorAppletBean salvaFirmatoAvanzaTask(EditorAppletBean pInBean) throws Exception {
		call(pInBean);
		EseguiTaskInBean lEseguiTaskInBean = new EseguiTaskInBean();
		lEseguiTaskInBean.setInstanceId(pInBean.getInstanceId());
		lEseguiTaskInBean.setActivityName(pInBean.getActivityName());
		lEseguiTaskInBean.setIdProcess(pInBean.getIdProcess());
		lEseguiTaskInBean.setIdEvento(pInBean.getIdEvento());
		lEseguiTaskInBean.setIdEventType(pInBean.getIdTipoEvento());

		GestioneTask lGestioneTask = new GestioneTask();
		EseguiTaskOutBean lEseguiTaskOutBean = lGestioneTask.salvatask(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lEseguiTaskInBean);

		if (StringUtils.isNotBlank(lEseguiTaskOutBean.getWarningMessage())) {
			addMessage(lEseguiTaskOutBean.getWarningMessage(), "", it.eng.utility.ui.module.core.shared.message.MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(lEseguiTaskOutBean.getDefaultMessage())) {
			throw new StoreException(lEseguiTaskOutBean);
		}
		return pInBean;
	}

	@Override
	public EditorAppletBean call(EditorAppletBean pInBean) throws Exception {
		mLogger.debug("Start call");
		String flgIgnoreObblig = getExtraparams().get("flgIgnoreObblig");

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		addOrUpdateDocument(loginBean, pInBean);

		DmpkProcessesIueventocustomBean lBean = new DmpkProcessesIueventocustomBean();
		lBean.setCodidconnectiontokenin(token);
		lBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		lBean.setIdprocessin(new BigDecimal(pInBean.getIdProcess()));
		lBean.setIdeventoio(StringUtils.isNotBlank(pInBean.getIdEvento()) ? new BigDecimal(pInBean.getIdEvento()) : null);
		lBean.setIdtipoeventoin(StringUtils.isNotBlank(pInBean.getIdTipoEvento()) ? new BigDecimal(pInBean.getIdTipoEvento()) : null);
		lBean.setIdudassociatain(StringUtils.isNotBlank(pInBean.getIdUd()) ? new BigDecimal(pInBean.getIdUd()) : null);

		lBean.setDeseventoin(pInBean.getDesEvento());
		lBean.setMessaggioin(pInBean.getMessaggio());

		if (StringUtils.isNotBlank(flgIgnoreObblig)) {
			lBean.setFlgignoraobbligin(new Integer(flgIgnoreObblig));
		}

		DmpkProcessesIueventocustom store = new DmpkProcessesIueventocustom();

		StoreResultBean<DmpkProcessesIueventocustomBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lBean);
		if (result.isInError()) {
			throw new StoreException(result);
		}

		addMessage("Salvataggio effettuato con successo", "", it.eng.utility.ui.module.core.shared.message.MessageType.INFO);

		pInBean.setIdEvento(String.valueOf(result.getResultBean().getIdeventoio()));
		return pInBean;
	}

	private void addOrUpdateDocument(AurigaLoginBean loginBean, EditorAppletBean pInBean) throws Exception {
		mLogger.debug("Start addOrUpdateDocument");

		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		String uri = null;
		String filename = null;
		MimeTypeFirmaBean lMimeTypeFirmaBean = null;
		if (StringUtils.isNotBlank(pInBean.getUriUltimaVersDocTask())) {
			uri = pInBean.getUriUltimaVersDocTask();
			mLogger.debug("uri: " + uri);
			filename = StringUtils.isNotBlank(pInBean.getNomeFileDocTipAssTask()) ? pInBean.getNomeFileDocTipAssTask() : (StringUtils.isNotBlank(pInBean.getNomeFile()) ? pInBean.getNomeFile() : null);			
			mLogger.debug("filename: " + filename);
			lMimeTypeFirmaBean = pInBean.getInfoFileUltimaVersDocTask();
		} else if (StringUtils.isNotBlank(pInBean.getUriModAssDocTask())) {
			mLogger.debug("ho un modello odt: lo converto in doc");
			mLogger.debug("uri modello odt: " + pInBean.getUriModAssDocTask());
			// uri = fillOdtAndConvertToDoc(pInBean.getUriModAssDocTask(), pInBean.getIdProcess(), loginBean);
			File filledTemplate = ModelliUtil.fillTemplateAndConvertToDoc(pInBean.getIdProcess(), pInBean.getUriModAssDocTask(), pInBean.getTipoModAssDocTask(), getSession());
			uri = StorageImplementation.getStorage().store(filledTemplate);
			filename = StringUtils.isNotBlank(pInBean.getNomeTipDocTask()) ? pInBean.getNomeTipDocTask() + ".doc" : null;
			mLogger.debug("uri: " + uri);
			mLogger.debug("filename: " + filename);
		} else {
			throw new Exception("Nessun documento associato");
		}

		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento(StringUtils.isNotBlank(pInBean.getIdDocAssTask()) ? new BigDecimal(pInBean.getIdDocAssTask()) : null);
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uri));
		
		if (lMimeTypeFirmaBean == null) {
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(),false, null);
		}

		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);

		GenericFile lGenericFile = new GenericFile();
		setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
		lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
		if(StringUtils.isNotBlank(filename)) {
			lGenericFile.setDisplayFilename(filename);
		} else {
			String estensione = null;
			if (lMimeTypeFirmaBean.getCorrectFileName() != null && !lMimeTypeFirmaBean.getCorrectFileName().trim().equals("")) {
				estensione = lMimeTypeFirmaBean.getCorrectFileName().substring(lMimeTypeFirmaBean.getCorrectFileName().lastIndexOf(".") + 1);
			} else if(lMimeTypeFirmaBean.getMimetype() != null && lMimeTypeFirmaBean.getMimetype().equals("application/pdf")) {
				estensione = "pdf";
			} else {
				estensione = "doc";
			}			
			lGenericFile.setDisplayFilename(StringUtils.isNotBlank(pInBean.getNomeTipDocTask()) ? pInBean.getNomeTipDocTask() + "." + estensione : pInBean.getDesEvento() + "." + estensione);
		}
		lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
		lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
		lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());

		lFileInfoBean.setAllegatoRiferimento(lGenericFile);

		lRebuildedFile.setInfo(lFileInfoBean);

		mLogger.debug("idDoc: " + pInBean.getIdDocAssTask());

		if (StringUtils.isNotBlank(pInBean.getIdDocAssTask())) {

			mLogger.debug("idDoc presente -> versiono il documento");
			VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);

			VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), loginBean, lVersionaDocumentoInBean);

			if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
				mLogger.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
				throw new StoreException(lVersionaDocumentoOutBean);
			}	

		} else {
			mLogger.debug("idDoc non presente -> creo un nuovo documento di tipo " + pInBean.getIdDocTipDocTask());

			FilePrimarioBean lFilePrimarioBean = new FilePrimarioBean();
			lFilePrimarioBean.setIdDocumento(StringUtils.isNotBlank(pInBean.getIdDocAssTask()) ? new BigDecimal(pInBean.getIdDocAssTask()) : null);
			lFilePrimarioBean.setIsNewOrChanged(true);
			lFilePrimarioBean.setInfo(lFileInfoBean);

			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
			lCreaModDocumentoInBean.setTipoDocumento(pInBean.getIdDocTipDocTask());
			lCreaModDocumentoInBean.setNomeDocType(null);

			lFilePrimarioBean.setFile(StorageImplementation.getStorage().extractFile(uri));

			CreaModDocumentoOutBean lCreaModDocumentoOutBean = lGestioneDocumenti.creadocumento(getLocale(), loginBean, lCreaModDocumentoInBean,
					lFilePrimarioBean, null);

			if (lCreaModDocumentoOutBean.getDefaultMessage() != null) {
				throw new StoreException(lCreaModDocumentoOutBean);
			}

			mLogger.debug("associo il documento con idUd " + lCreaModDocumentoOutBean.getIdUd() + " al procedimento " + pInBean.getIdProcess());
			DmpkProcessesIuassociazioneudvsprocBean dmpkProcessesIuassociazioneudvsprocBean = new DmpkProcessesIuassociazioneudvsprocBean();
			dmpkProcessesIuassociazioneudvsprocBean.setIdprocessin(new BigDecimal(pInBean.getIdProcess()));
			dmpkProcessesIuassociazioneudvsprocBean.setIdudin(lCreaModDocumentoOutBean.getIdUd());
			dmpkProcessesIuassociazioneudvsprocBean.setNroordinevsprocin(null);
			dmpkProcessesIuassociazioneudvsprocBean.setFlgacqprodin("A");
			dmpkProcessesIuassociazioneudvsprocBean.setCodruolodocinprocin("PARTEIST");

			DmpkProcessesIuassociazioneudvsproc dmpkProcessesIuassociazioneudvsproc = new DmpkProcessesIuassociazioneudvsproc();

			StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> result = dmpkProcessesIuassociazioneudvsproc.execute(getLocale(),
					AurigaUserUtil.getLoginInfo(getSession()), dmpkProcessesIuassociazioneudvsprocBean);
			if (result.isInError()) {
				throw new StoreException(result);
			}

			pInBean.setIdUd(lCreaModDocumentoOutBean.getIdUd() != null ? String.valueOf(lCreaModDocumentoOutBean.getIdUd().longValue()) : null);
		}
	}

	public EditorAppletBean loadDati(EditorAppletBean pInBean) throws Exception {
		mLogger.debug("Start loadDati");
		if (StringUtils.isNotBlank(pInBean.getUriUltimaVersDocTask())) {
			pInBean.setImpronta(calculateHash(pInBean.getUriUltimaVersDocTask()));
			pInBean.setUriPdfGenerato(convertPdf(pInBean.getUriUltimaVersDocTask()));
			String fileNamePdfGenerato = StringUtils.isNotBlank(pInBean.getNomeFileDocTipAssTask()) ? FilenameUtils.getBaseName(pInBean
					.getNomeFileDocTipAssTask()) + ".pdf" : pInBean.getNomeTipDocTask() + ".pdf";
			pInBean.setFilenamePdfGenerato(fileNamePdfGenerato);
			pInBean.setImprontaPdf(calculateHash(pInBean.getUriPdfGenerato()));
			pInBean.setUriDoc(pInBean.getUriUltimaVersDocTask());
		} else if (StringUtils.isNotBlank(pInBean.getUriModAssDocTask())) {
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			// Inserisco la lingua di default
			// loginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
			loginBean.setLinguaApplicazione(getLocale().getLanguage());
			// String uriDocModello = fillOdtAndConvertToDoc(pInBean.getUriModAssDocTask(), pInBean.getIdProcess(), loginBean);
			// pInBean.setImpronta(calculateHash(uriDocModello));
			// pInBean.setUriPdfGenerato(convertPdf(uriDocModello));
			// pInBean.setImprontaPdf(calculateHash(pInBean.getUriPdfGenerato()));
			// pInBean.setUriDoc(uriDocModello);
			File filledTemplate = ModelliUtil.fillTemplateAndConvertToDoc(pInBean.getIdProcess(), pInBean.getUriModAssDocTask(), pInBean.getTipoModAssDocTask(), getSession());
			String uriDocModello = StorageImplementation.getStorage().store(filledTemplate);
			pInBean.setImpronta(calculateHash(uriDocModello));
			pInBean.setUriPdfGenerato(convertPdf(uriDocModello));
			String fileNamePdfGenerato = StringUtils.isNotBlank(pInBean.getDisplayFilenameModAssDocTask()) ? pInBean.getDisplayFilenameModAssDocTask().replaceAll(" ", "_") + ".pdf"
					: pInBean.getNomeTipDocTask() + ".pdf";
			pInBean.setFilenamePdfGenerato(fileNamePdfGenerato);
			pInBean.setImprontaPdf(calculateHash(pInBean.getUriPdfGenerato()));
			pInBean.setUriDoc(uriDocModello);
		}
		return pInBean;
	}

	public String convertPdf(String uri) throws StoreException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(uri).toURI().toString();
			return StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(fileUrl, ""));
		} catch (Exception e) {
			mLogger.error("Errore durante la conversione in pdf: " + e.getMessage(), e);
			throw new StoreException("Non è stato possibile convertire il file");
		}
	}

	public String calculateHash(String uri) throws StoreException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(uri).toURI().toString();
			return lInfoFileUtility.calcolaImpronta(fileUrl, "");
		} catch (Exception e) {
			mLogger.error("Errore durante il calcolo dell'imporonta: " + e.getMessage(), e);
			throw new StoreException("Non è stato possibile calcolare l'impronta del file");
		}
	}

}
