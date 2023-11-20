/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskFileDaFirmareBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskProtocolloFileFirmatiBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "FirmaDirigenteDatasource")
public class FirmaDirigenteDatasource extends AbstractServiceDataSource<ProtocollazioneBean, TaskFileDaFirmareBean> {

	private static Logger mLogger = Logger.getLogger(FirmaDirigenteDatasource.class);

	public TaskFileDaFirmareBean getFileDaFirmare(ProtocollazioneBean pProtocollazioneBean) throws StorageException, Exception {
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		lTaskFileDaFirmareBean.setFiles(new ArrayList<FileDaFirmareBean>());
		//Aggiungo il primario
		if (StringUtils.isNotBlank(pProtocollazioneBean.getUriFilePrimario())) {
			aggiungiPrimario(lTaskFileDaFirmareBean, pProtocollazioneBean);
		}
		if (pProtocollazioneBean.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(pProtocollazioneBean.getFilePrimarioOmissis().getUriFile())){
			aggiungiPrimarioOmissis(lTaskFileDaFirmareBean, pProtocollazioneBean);
		}
		// Per ogni allegato devo recuperare solo quello che mi serve
		if (pProtocollazioneBean.getListaAllegati() != null && pProtocollazioneBean.getListaAllegati().size() > 0) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pProtocollazioneBean.getListaAllegati()) {
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) {
					if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
						aggiungiAllegato(lTaskFileDaFirmareBean, lAllegatoProtocolloBean);
					}
					if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili() && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())){
						aggiungiAllegatoOmissis(lTaskFileDaFirmareBean, lAllegatoProtocolloBean);
					}
				}
			}
		}
		return lTaskFileDaFirmareBean;
	}
	
	private void aggiungiPrimario(TaskFileDaFirmareBean lTaskFileDaFirmareBean, ProtocollazioneBean pProtocollazioneBean) throws StorageException, Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		FileDaFirmareBean lFileDaFirmareBean = FilePrimarioUtility.generateFilePrimario(pProtocollazioneBean, pProtocollazioneBean.getIdProcess(), loginBean, getLocale(), getSession());
		// FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		// lFileDaFirmareBean.setIdFile("primario");
		// lFileDaFirmareBean.setNomeFile(pProtocollazioneBean.getNomeFilePrimario());
		// lFileDaFirmareBean.setUri(pProtocollazioneBean.getUriFilePrimario());
		// lFileDaFirmareBean.setInfoFile(pProtocollazioneBean.getInfoFile());
		lTaskFileDaFirmareBean.getFiles().add(lFileDaFirmareBean);
	}
	
	private void aggiungiPrimarioOmissis(TaskFileDaFirmareBean lTaskFileDaFirmareBean, ProtocollazioneBean pProtocollazioneBean) throws Exception {
		if(pProtocollazioneBean.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(pProtocollazioneBean.getFilePrimarioOmissis().getUriFile())) {
			FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
			lFileDaFirmareBeanOmissis.setIdFile("primarioOmissis" + pProtocollazioneBean.getFilePrimarioOmissis().getUriFile());
			lFileDaFirmareBeanOmissis.setInfoFile(pProtocollazioneBean.getFilePrimarioOmissis().getInfoFile());
			lFileDaFirmareBeanOmissis.setNomeFile(pProtocollazioneBean.getFilePrimarioOmissis().getNomeFile());
			lFileDaFirmareBeanOmissis.setUri(pProtocollazioneBean.getFilePrimarioOmissis().getUriFile());
			lFileDaFirmareBeanOmissis.setIsFilePrincipaleAtto(true);
			lTaskFileDaFirmareBean.getFiles().add(lFileDaFirmareBeanOmissis);
		}
	}

	private void aggiungiAllegato(TaskFileDaFirmareBean lTaskFileDaFirmareBean, AllegatoProtocolloBean lAllegatoProtocolloBean) {
		if(StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
			FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
			lFileDaFirmareBean.setIdFile("allegato" + lAllegatoProtocolloBean.getUriFileAllegato());
			lFileDaFirmareBean.setInfoFile(lAllegatoProtocolloBean.getInfoFile());
			lFileDaFirmareBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
			lFileDaFirmareBean.setUri(lAllegatoProtocolloBean.getUriFileAllegato());
			lTaskFileDaFirmareBean.getFiles().add(lFileDaFirmareBean);
		}
	}
	
	private void aggiungiAllegatoOmissis(TaskFileDaFirmareBean lTaskFileDaFirmareBean, AllegatoProtocolloBean lAllegatoProtocolloBean) throws Exception {
		if(StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())) {
			FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
			lFileDaFirmareBeanOmissis.setIdFile("allegatoOmissis" + lAllegatoProtocolloBean.getUriFileOmissis());
			lFileDaFirmareBeanOmissis.setInfoFile(lAllegatoProtocolloBean.getInfoFileOmissis());
			lFileDaFirmareBeanOmissis.setNomeFile(lAllegatoProtocolloBean.getNomeFileOmissis());
			lFileDaFirmareBeanOmissis.setUri(lAllegatoProtocolloBean.getUriFileOmissis());
			lFileDaFirmareBeanOmissis.setUriVerPreFirma(lAllegatoProtocolloBean.getUriFileVerPreFirmaOmissis());
			lTaskFileDaFirmareBean.getFiles().add(lFileDaFirmareBeanOmissis);		
		}
	}

	//Prima di aggiornaFileFirmati (controllarli tutti i punti, non solo questo) bisogna salvare in bozza per aggiornare le versioni pre firma di 
	//primario e allegati (vers. integrale e con omissis) altrimenti poi nel file unione che va in pubblicazione mettiamo le versioni vecchie
	public ProtocollazioneBean aggiornaFileFirmati(TaskProtocolloFileFirmatiBean pTaskProtocolloFileFirmatiBean) throws Exception {
		ProtocollazioneBean lProtocollazioneBean = pTaskProtocolloFileFirmatiBean.getProtocolloOriginale();
		if (pTaskProtocolloFileFirmatiBean.getFileFirmati() != null && pTaskProtocolloFileFirmatiBean.getFileFirmati().getFiles() != null) {
			boolean firmaNonValida = false;
			for (FileDaFirmareBean lFileDaFirmareBean : pTaskProtocolloFileFirmatiBean.getFileFirmati().getFiles()) {
				String idFile = lFileDaFirmareBean.getIdFile();
				if (lFileDaFirmareBean.getInfoFile().isFirmato() && !lFileDaFirmareBean.getInfoFile().isFirmaValida()) {
					if (idFile.startsWith("primarioOmissis")) {
						mLogger.error("La firma del file primario " + lFileDaFirmareBean.getNomeFile() + " (vers. con omissis) risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					} else if (idFile.startsWith("primario")) {
						mLogger.error("La firma del file primario " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					} else if (idFile.startsWith("allegatoOmissis")) {
						mLogger.error("La firma del file allegato " + lFileDaFirmareBean.getNomeFile() + " (vers. con omissis) risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					} else if (idFile.startsWith("allegato")) {
						mLogger.error("La firma del file allegato " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					}
					firmaNonValida = true;
				}
				if (idFile.startsWith("primarioOmissis")) {
					aggiornaPrimarioOmissisFirmato(lProtocollazioneBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("primario")) {
					aggiornaPrimarioFirmato(lProtocollazioneBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("allegatoOmissis")) {
					aggiornaAllegatoOmissisFirmato(lProtocollazioneBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("allegato")) {
					aggiornaAllegatoFirmato(lProtocollazioneBean, lFileDaFirmareBean);
				} 				
			}
			if (firmaNonValida) {
				throw new StoreException("La firma di uno o più file risulta essere non valida");
			}
		}
		return lProtocollazioneBean;
	}
	
	public ProtocolloDataSource getProtocolloDataSource() {	
		ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource();
		lProtocolloDataSource.setSession(getSession());
		Map<String, String> extraparams = new LinkedHashMap<String, String>();
		extraparams.put("isPropostaAtto", "true");
		lProtocolloDataSource.setExtraparams(extraparams);	
		return lProtocolloDataSource;
	}
	
	private void aggiornaPrimarioFirmato(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		if(lProtocollazioneBean.getIsDocPrimarioChanged() != null && lProtocollazioneBean.getIsDocPrimarioChanged()) {
			// Prima salvo la versione pre firma se è stata modificata
			getProtocolloDataSource().aggiornaFilePrimario(lProtocollazioneBean);
		}
		aggiornaPrimario(lProtocollazioneBean, lFileDaFirmareBean);
	}
	
	private void aggiornaPrimarioOmissisFirmato(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) throws Exception {
		if(lProtocollazioneBean.getFilePrimarioOmissis() != null && lProtocollazioneBean.getFilePrimarioOmissis().getIsChanged() != null && lProtocollazioneBean.getFilePrimarioOmissis().getIsChanged()) {
			// Prima salvo la versione pre firma se è stata modificata
			getProtocolloDataSource().aggiornaFilePrimarioOmissis(lProtocollazioneBean);
		}
		aggiornaPrimarioOmissis(lProtocollazioneBean, lFileDaFirmareBeanOmissis);
	}
	
	private void aggiornaAllegatoFirmato(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource();
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegato".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lProtocollazioneBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileAllegato() != null && lAllegatoProtocolloBean.getUriFileAllegato().equals(uriFileOriginale)) {
				if(lAllegatoProtocolloBean.getIsChanged() != null && lAllegatoProtocolloBean.getIsChanged()) {
					// Prima salvo la versione pre firma se è stata modificata
					lProtocolloDataSource.aggiornaFileAllegato(lAllegatoProtocolloBean);
				}				
				break;
			}
		}
		aggiornaAllegato(lProtocollazioneBean, lFileDaFirmareBean);
	}
	
	private void aggiornaAllegatoOmissisFirmato(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) throws Exception {
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource();
		String uriFileOriginaleOmissis = lFileDaFirmareBeanOmissis.getIdFile().substring("allegatoOmissis".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lProtocollazioneBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileOmissis() != null && lAllegatoProtocolloBean.getUriFileOmissis().equals(uriFileOriginaleOmissis)) {
				if(lAllegatoProtocolloBean.getIsChangedOmissis() != null && lAllegatoProtocolloBean.getIsChangedOmissis()) {
					// Prima salvo la versione pre firma se è stata modificata
					lProtocolloDataSource.aggiornaFileAllegatoOmissis(lAllegatoProtocolloBean);
				}				
				break;
			}
		}
		aggiornaAllegatoOmissis(lProtocollazioneBean, lFileDaFirmareBeanOmissis);
	}

	private void aggiornaPrimario(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBean) {
		lProtocollazioneBean.setUriFilePrimario(lFileDaFirmareBean.getUri());
		lProtocollazioneBean.setNomeFilePrimario(lFileDaFirmareBean.getNomeFile());
		lProtocollazioneBean.setInfoFile(lFileDaFirmareBean.getInfoFile());
		lProtocollazioneBean.setIsDocPrimarioChanged(true);
	}
	
	private void aggiornaPrimarioOmissis(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) {
		if(lProtocollazioneBean.getFilePrimarioOmissis() == null) {
			lProtocollazioneBean.setFilePrimarioOmissis(new DocumentBean());
		}
		lProtocollazioneBean.getFilePrimarioOmissis().setUriFile(lFileDaFirmareBeanOmissis.getUri());
		lProtocollazioneBean.getFilePrimarioOmissis().setNomeFile(lFileDaFirmareBeanOmissis.getNomeFile());
		lProtocollazioneBean.getFilePrimarioOmissis().setInfoFile(lFileDaFirmareBeanOmissis.getInfoFile());
		lProtocollazioneBean.getFilePrimarioOmissis().setIsChanged(true);
	}

	private void aggiornaAllegato(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBean) {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegato".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lProtocollazioneBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileAllegato() != null && lAllegatoProtocolloBean.getUriFileAllegato().equals(uriFileOriginale)) {
				lAllegatoProtocolloBean.setUriFileAllegato(lFileDaFirmareBean.getUri());
				lAllegatoProtocolloBean.setNomeFileAllegato(lFileDaFirmareBean.getNomeFile());
				lAllegatoProtocolloBean.setInfoFile(lFileDaFirmareBean.getInfoFile());
				lAllegatoProtocolloBean.setIsChanged(true);
				break;
			}
		}
	}
	
	private void aggiornaAllegatoOmissis(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) {
		String uriFileOriginaleOmissis = lFileDaFirmareBeanOmissis.getIdFile().substring("allegatoOmissis".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lProtocollazioneBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileOmissis() != null && lAllegatoProtocolloBean.getUriFileOmissis().equals(uriFileOriginaleOmissis)) {
				lAllegatoProtocolloBean.setUriFileOmissis(lFileDaFirmareBeanOmissis.getUri());
				lAllegatoProtocolloBean.setNomeFileOmissis(lFileDaFirmareBeanOmissis.getNomeFile());
				lAllegatoProtocolloBean.setInfoFileOmissis(lFileDaFirmareBeanOmissis.getInfoFile());
				lAllegatoProtocolloBean.setIsChangedOmissis(true);
				break;
			}
		}
	}

	public ProtocollazioneBean unisciAllegatoAPrimario(TaskProtocolloFileFirmatiBean pTaskProtocolloFileFirmatiBean) throws StorageException, Exception {
		ProtocollazioneBean lProtocollazioneBean = pTaskProtocolloFileFirmatiBean.getProtocolloOriginale();
		if (pTaskProtocolloFileFirmatiBean.getFileFirmati() != null && pTaskProtocolloFileFirmatiBean.getFileFirmati().getFiles() != null
				&& pTaskProtocolloFileFirmatiBean.getFileFirmati().getFiles().size() > 0) {
			FileDaFirmareBean lFileAllegatoBean = pTaskProtocolloFileFirmatiBean.getFileFirmati().getFiles().get(0);
			if (lFileAllegatoBean.getInfoFile().isFirmato() && !lFileAllegatoBean.getInfoFile().isFirmaValida()) {
				throw new StoreException("La firma del file allegato risulta essere non valida");
			}
			List<InputStream> llFileDaUnireStreams = new ArrayList<InputStream>();
			if (StringUtils.isNotEmpty(lProtocollazioneBean.getNomeFilePrimario()) && lProtocollazioneBean.getNomeFilePrimario().toLowerCase().endsWith(".p7m")) {
				InputStream lFilePrimarioSbustatoPdf = null;
				if (StringUtils.isNotEmpty(lProtocollazioneBean.getNomeFilePrimario())
						&& lProtocollazioneBean.getNomeFilePrimario().toLowerCase().endsWith(".p7m")) {
					mLogger.debug("Ho un file firmato");
					InputStream lInputStream = sbusta(lProtocollazioneBean.getUriFilePrimario(), lProtocollazioneBean.getNomeFilePrimario());
					if (lProtocollazioneBean.getInfoFile().getMimetype().equals("application/pdf")) {
						mLogger.debug("Il file sbustato è un pdf");
						lFilePrimarioSbustatoPdf = lInputStream;
					} else {
						mLogger.debug("Il file sbustato non è un pdf: lo converto");
						mLogger.debug("mimetype: " + lProtocollazioneBean.getInfoFile().getMimetype());
						lFilePrimarioSbustatoPdf = convertiFileToPdf(lInputStream);
					}
				} else {
					if (lProtocollazioneBean.getInfoFile().getMimetype().equals("application/pdf")) {
						mLogger.debug("Il file è un pdf");
						lFilePrimarioSbustatoPdf = StorageImplementation.getStorage().extract(lProtocollazioneBean.getUriFilePrimario());
					} else {
						mLogger.debug("Il file non è un pdf: lo converto");
						mLogger.debug("mimetype: " + lProtocollazioneBean.getInfoFile().getMimetype());
						lFilePrimarioSbustatoPdf = convertiFileToPdf(StorageImplementation.getStorage().extract(lProtocollazioneBean.getUriFilePrimario()));
					}
				}
				llFileDaUnireStreams.add(lFilePrimarioSbustatoPdf);
			}
			if (StringUtils.isNotEmpty(lFileAllegatoBean.getNomeFile()) && lFileAllegatoBean.getNomeFile().toLowerCase().endsWith(".p7m")) {
				InputStream lFileAllegatoSbustatoPdf = null;
				if (StringUtils.isNotEmpty(lFileAllegatoBean.getNomeFile()) && lFileAllegatoBean.getNomeFile().toLowerCase().endsWith(".p7m")) {
					mLogger.debug("Ho un file firmato");
					InputStream lInputStream = sbusta(lFileAllegatoBean.getUri(), lFileAllegatoBean.getNomeFile());
					if (lFileAllegatoBean.getInfoFile().getMimetype().equals("application/pdf")) {
						mLogger.debug("Il file sbustato è un pdf");
						lFileAllegatoSbustatoPdf = lInputStream;
					} else {
						mLogger.debug("Il file sbustato non è un pdf: lo converto");
						mLogger.debug("mimetype: " + lFileAllegatoBean.getInfoFile().getMimetype());
						lFileAllegatoSbustatoPdf = convertiFileToPdf(lInputStream);
					}
				} else {
					if (lFileAllegatoBean.getInfoFile().getMimetype().equals("application/pdf")) {
						mLogger.debug("Il file è un pdf");
						lFileAllegatoSbustatoPdf = StorageImplementation.getStorage().extract(lFileAllegatoBean.getUri());
					} else {
						mLogger.debug("Il file non è un pdf: lo converto");
						mLogger.debug("mimetype: " + lFileAllegatoBean.getInfoFile().getMimetype());
						lFileAllegatoSbustatoPdf = convertiFileToPdf(StorageImplementation.getStorage().extract(lFileAllegatoBean.getUri()));
					}
				}
				llFileDaUnireStreams.add(lFileAllegatoSbustatoPdf);
			}
			File lFileUnione = unisciPdf(llFileDaUnireStreams);
			mLogger.debug("Il file unione ha path " + lFileUnione.getPath());
			String storageUri = StorageImplementation.getStorage().store(lFileUnione, new String[] {});
			FileDaFirmareBean lFilePrimarioBean = new FileDaFirmareBean();
			lFilePrimarioBean.setUri(storageUri);
			lFilePrimarioBean.setNomeFile(lProtocollazioneBean.getNomeFilePrimario());
			MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
			infoFile.setMimetype("application/pdf");
			infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(lFileUnione));
			infoFile.setDaScansione(false);
			infoFile.setFirmato(false);
			infoFile.setFirmaValida(false);
			infoFile.setBytes(lFileUnione.length());
			infoFile.setCorrectFileName(lProtocollazioneBean.getNomeFilePrimario());
			File realFile = StorageImplementation.getStorage().getRealFile(storageUri);
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), lProtocollazioneBean.getNomeFilePrimario()));
			lFilePrimarioBean.setInfoFile(infoFile);
			aggiornaPrimario(lProtocollazioneBean, lFilePrimarioBean);
		}
		return lProtocollazioneBean;
	}

	public File unisciPdf(List<InputStream> lListPdf) throws Exception {
		mLogger.info("Entro in unisciPdf di FirmaDirigenteDatasource");
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

	@Override
	public TaskFileDaFirmareBean call(ProtocollazioneBean pInBean) throws Exception {
		return null;
	}
}
