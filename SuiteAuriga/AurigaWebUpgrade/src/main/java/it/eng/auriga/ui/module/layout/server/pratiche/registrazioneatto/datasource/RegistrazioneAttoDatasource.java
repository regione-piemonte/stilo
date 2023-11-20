/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIuassociazioneudvsprocBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.registrazioneatto.datasource.bean.RegistrazioneAttoBean;
import it.eng.auriga.ui.module.layout.server.task.bean.FileDaUnireBean;
import it.eng.client.DmpkProcessesIuassociazioneudvsproc;
import it.eng.client.GestioneDocumenti;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

@Datasource(id = "RegistrazioneAttoDatasource")
public class RegistrazioneAttoDatasource extends AbstractServiceDataSource<RegistrazioneAttoBean, RegistrazioneAttoBean> {

	private static Logger mLogger = Logger.getLogger(RegistrazioneAttoDatasource.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource #call(java.lang.Object)
	 * 
	 * Al salvataggio definitivo di questo task va creata un UD con il pdf unione passando alla AddDoc: #IdDocType = #IdDocType restituito dalla Call_ExecAtt
	 * #@NumerazioniDaDare una lista con una riga con: colonna 1 = variabile #SiglaRegistroAtto data dalla Call_execAtt; colonna 3 = R ; colonna 4 = Anno a
	 * maschera Dopo la AddDoc va fatto quello che facciamo di solito: IUEventoCustom pasando l’UD Collegamento dell’UD al processo -
	 * dmpk_processes.IUAssociazioneUDVsProc - passando CodRuoloDocInProcIn = AFIN Avanzamento del flusso (dmpk_wf.ExecAttFlusso ecc)
	 */
	@Override
	public RegistrazioneAttoBean call(RegistrazioneAttoBean pInBean) throws Exception {

		String tipoDocumento = pInBean.getIdTipoDoc();
		String siglaRegistroAtto = pInBean.getSiglaRegistroAtto();

		FilePrimarioBean lFilePrimarioBean = buildPrimario(pInBean.getUriPdf());

		List<File> fileAllegati = new ArrayList<File>();
		List<String> descrizione = new ArrayList<String>();
		List<Integer> docType = new ArrayList<Integer>();
		List<String> displayFilename = new ArrayList<String>();
		List<BigDecimal> idDocumento = new ArrayList<BigDecimal>();
		List<Boolean> isNull = new ArrayList<Boolean>();
		List<Boolean> isNewOrChanged = new ArrayList<Boolean>();
		List<FileInfoBean> info = new ArrayList<FileInfoBean>();

		AllegatiBean lAllegatiBean = new AllegatiBean();
		lAllegatiBean.setDescrizione(descrizione);
		lAllegatiBean.setDisplayFilename(displayFilename);
		lAllegatiBean.setDocType(docType);
		lAllegatiBean.setFileAllegati(fileAllegati);
		lAllegatiBean.setIdDocumento(idDocumento);
		lAllegatiBean.setInfo(info);
		lAllegatiBean.setIsNewOrChanged(isNewOrChanged);
		lAllegatiBean.setIsNull(isNull);

		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
		lCreaModDocumentoInBean.setTipoDocumento(tipoDocumento);
		TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
		lTipoNumerazioneBean.setSigla(siglaRegistroAtto);
		lTipoNumerazioneBean.setCategoria("R");
		lTipoNumerazioneBean.setAnno(pInBean.getAnnoRegistrazione() + "");
		lCreaModDocumentoInBean.setTipoNumerazioni(Arrays.asList(new TipoNumerazioneBean[] { lTipoNumerazioneBean }));
		CreaModDocumentoOutBean lCreaModDocumentoOutBean = lGestioneDocumenti.creadocumento(getLocale(), loginBean, lCreaModDocumentoInBean, lFilePrimarioBean,
				lAllegatiBean);

		if (lCreaModDocumentoOutBean.getDefaultMessage() != null) {
			throw new StoreException(lCreaModDocumentoOutBean);
		}

		pInBean.setEstremiRegAtto(lCreaModDocumentoOutBean.getSigla() + " " + lCreaModDocumentoOutBean.getNumero() + "/" + lCreaModDocumentoOutBean.getAnno());

		DmpkProcessesIuassociazioneudvsprocBean dmpkProcessesIuassociazioneudvsprocBean = new DmpkProcessesIuassociazioneudvsprocBean();
		dmpkProcessesIuassociazioneudvsprocBean.setIdprocessin(new BigDecimal(pInBean.getIdProcess()));
		dmpkProcessesIuassociazioneudvsprocBean.setIdudin(lCreaModDocumentoOutBean.getIdUd());
		dmpkProcessesIuassociazioneudvsprocBean.setNroordinevsprocin(null);
		dmpkProcessesIuassociazioneudvsprocBean.setFlgacqprodin("A");
		dmpkProcessesIuassociazioneudvsprocBean.setCodruolodocinprocin("AFIN");

		DmpkProcessesIuassociazioneudvsproc dmpkProcessesIuassociazioneudvsproc = new DmpkProcessesIuassociazioneudvsproc();
		StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> result = dmpkProcessesIuassociazioneudvsproc.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), dmpkProcessesIuassociazioneudvsprocBean);

		if (result.isInError()) {
			throw new StoreException(result);
		}

		return pInBean;
	}

	private FilePrimarioBean buildPrimario(String uriPdf) throws Exception {
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		InfoFileUtility lFileUtility = new InfoFileUtility();
		String name = "RegistrazioneAtto.pdf";

		lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriPdf).toURI().toString(), name, false, null);
		String displayFileName = name;
		FilePrimarioBean lFilePrimarioBean = new FilePrimarioBean();
		lFilePrimarioBean.setIsNewOrChanged(true);
		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);

		GenericFile lGenericFile = new GenericFile();
		setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
		lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
		lGenericFile.setDisplayFilename(displayFileName);
		lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
		lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
		lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
		lGenericFile.setDaScansione(Flag.NOT_SETTED);
		lGenericFile.setFirmato(Flag.SETTED);
		List<Firmatario> lListFirmatario = new ArrayList<Firmatario>();
		// for (String lFirmatari : lMimeTypeFirmaBean.getFirmatari()){
		// Firmatario lFirmatario = new Firmatario();
		// lFirmatario.setCommonName(lFirmatari);
		// lListFirmatario.add(lFirmatario);
		// }
		lGenericFile.setFirmatari(lListFirmatario);

		lFileInfoBean.setAllegatoRiferimento(lGenericFile);
		lFilePrimarioBean.setInfo(lFileInfoBean);

		lFilePrimarioBean.setFile(StorageImplementation.getStorage().extractFile(uriPdf));
		return lFilePrimarioBean;

	}

	public RegistrazioneAttoBean loadDati(RegistrazioneAttoBean pInBean) throws Exception {
		String uriPdf = null;
		// if (StringUtils.isNotEmpty(pInBean.getIdUd())) {
		// mLogger.debug("Recupero il documento dalla idUd");
		// uriPdf = recuperaDaUd(pInBean.getIdUd(), AurigaUserUtil.getLoginInfo(getSession()));
		// } else {
		mLogger.debug("Lo devo costruire");
		uriPdf = buildPdf(pInBean);
		// }
		pInBean.setUriPdf(uriPdf);
		return pInBean;
	}

	private String buildPdf(RegistrazioneAttoBean pInBean) throws Exception {
		// Se il file in colonna 1 ha estensione p7m (controllo
		// case-insensitive) va sbustato ricorsivamente prima di unirlo.
		// Inoltre se i file non sono già pdf (si vede il mimietype) vanno
		// convertiti in pdf prima di unirli
		//
		// Se colonna 2 è vuota si prende il modello odt in colonna 4, vi si
		// iniettano i dati e lo si pdfizza.
		if (pInBean.getFileDaUnire() != null) {
			List<InputStream> lListInputStreams = new ArrayList<InputStream>(pInBean.getFileDaUnire().size());
			for (FileDaUnireBean lFileDaUnireBean : pInBean.getFileDaUnire()) {
				InputStream lInputStreamPdf = null;
				if (StringUtils.isNotEmpty(lFileDaUnireBean.getNomeFile()) && lFileDaUnireBean.getNomeFile().toLowerCase().endsWith(".p7m")) {
					mLogger.debug("Ho un file firmato");
					InputStream lInputStream = sbusta(lFileDaUnireBean.getUriFile(), lFileDaUnireBean.getNomeFile());
					if (lFileDaUnireBean.getMimetype().equals("application/pdf")) {
						mLogger.debug("Il file sbustato è un pdf");
						lInputStreamPdf = lInputStream;
					} else {
						mLogger.debug("Il file sbustato non è un pdf: lo converto");
						mLogger.debug("mimetype: " + lFileDaUnireBean.getMimetype());
						lInputStreamPdf = convertiFileToPdf(lInputStream);
					}
				} else {
					if (StringUtils.isEmpty(lFileDaUnireBean.getUriFile())) {
						mLogger.debug("Ho un modello: lo converto in pdf");
						mLogger.debug("uri modello: " + lFileDaUnireBean.getUriModello());
						AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
						File lFilePdf = ModelliUtil.fillTemplateAndConvertToPdf(pInBean.getIdProcess(), lFileDaUnireBean.getUriModello(), lFileDaUnireBean.getTipoModello(), null, getSession());
						lInputStreamPdf = new FileInputStream(lFilePdf);
					} else {
						if (lFileDaUnireBean.getMimetype().equals("application/pdf")) {
							mLogger.debug("Il file è un pdf");
							lInputStreamPdf = StorageImplementation.getStorage().extract(lFileDaUnireBean.getUriFile());
						} else {
							mLogger.debug("Il file non è un pdf: lo converto");
							mLogger.debug("mimetype: " + lFileDaUnireBean.getMimetype());
							lInputStreamPdf = convertiFileToPdf(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUriFile()));
						}
					}
				}
				lListInputStreams.add(lInputStreamPdf);
			}
			File lFile = unisciPdf(lListInputStreams);
			return StorageImplementation.getStorage().store(lFile, new String[] {});
		}
		return null;

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

}
