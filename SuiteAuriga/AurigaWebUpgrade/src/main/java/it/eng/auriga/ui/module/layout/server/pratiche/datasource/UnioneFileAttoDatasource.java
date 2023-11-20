/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.UnioneFileAttoBean;
import it.eng.auriga.ui.module.layout.server.task.bean.FileDaUnireBean;
import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraResultBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraUtility;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "UnioneFileAttoDatasource")
public class UnioneFileAttoDatasource extends AbstractServiceDataSource<UnioneFileAttoBean, UnioneFileAttoBean> {

	private static Logger mLogger = Logger.getLogger(UnioneFileAttoDatasource.class);

	@Override
	public UnioneFileAttoBean call(UnioneFileAttoBean bean) throws Exception {
		
		String uriFileUnione = buildFileUnione(bean, false);
		mLogger.debug("Ho creato il file di unione " + uriFileUnione);
		bean.setUri(uriFileUnione);
		bean.setNomeFile(bean.getNomeFileUnione());		
		bean.setInfoFile(new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFileUnione).toURI().toString(), bean.getNomeFileUnione(), false, null));
		
		if(StringUtils.isNotBlank(bean.getNomeFileUnioneVersIntegrale())) {
			String uriFileUnioneVersIntegrale = buildFileUnione(bean, true);
			mLogger.debug("Ho creato il file di unione vers.integrale " + uriFileUnioneVersIntegrale);
			bean.setUriVersIntegrale(uriFileUnioneVersIntegrale);
			bean.setNomeFileVersIntegrale(bean.getNomeFileUnioneVersIntegrale());
			bean.setInfoFileVersIntegrale(new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFileUnioneVersIntegrale).toURI().toString(), bean.getNomeFileUnioneVersIntegrale(), false, null));
		}
		
		return bean;
	}

	private String buildFileUnione(UnioneFileAttoBean bean, boolean isVersIntegrale) throws Exception {
		mLogger.debug("Entro in buildFileUnione di UnioneFileAttoDatasource");
		// Se il nome file in colonna 1 ha estensione p7m (controllo case-insensitive) va sbustato ricorsivamente prima di unirlo.
		// Inoltre se i file non sono già pdf (si vede il mimietype) vanno convertiti in pdf prima di unirli		
		// Se colonna 2 è vuota si prende il modello in colonna 4, vi si iniettano i dati e lo si pdfizza.
		List<FileDaUnireBean> fileDaUnire = isVersIntegrale ? bean.getFileDaUnireVersIntegrale() : bean.getFileDaUnire();
		if (fileDaUnire != null) {
			List<InputStream> lListInputStreams = new ArrayList<InputStream>(fileDaUnire.size());
			for (FileDaUnireBean lFileDaUnireBean : fileDaUnire) {
				mLogger.debug("Path del file da unire: " + lFileDaUnireBean.getUriFile());
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
						
						SezioneCache lSezioneCache = null;
						if (bean.getValori() != null && bean.getValori().size() > 0 && bean.getTipiValori() != null && bean.getTipiValori().size() > 0) {
							lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, bean.getValori(), bean.getTipiValori(), getSession());
						} else {
							lSezioneCache = new SezioneCache();
						}

						String xmlSezioneCacheAttributiAdd = null;
						if (lSezioneCache != null) {
							StringWriter lStringWriter = new StringWriter();
							Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
							lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
							lMarshaller.marshal(lSezioneCache, lStringWriter);
							xmlSezioneCacheAttributiAdd = lStringWriter.toString();
						}
						
						DmpkModelliDocGetdatixgendamodello lGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
						DmpkModelliDocGetdatixgendamodelloBean lGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
						lGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
						lGetdatixgendamodelloInput.setIdobjrifin(bean.getIdUd());
						lGetdatixgendamodelloInput.setFlgtpobjrifin("U");
						lGetdatixgendamodelloInput.setNomemodelloin(lFileDaUnireBean.getNomeModello());
						lGetdatixgendamodelloInput.setAttributiaddin(xmlSezioneCacheAttributiAdd);

						StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = lGetdatixgendamodello.execute(getLocale(), loginBean,
								lGetdatixgendamodelloInput);
						
						String templateValues = lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();					
						
						File lFilePdf = ModelliUtil.fillTemplateAndConvertToPdf(bean.getIdProcess(), lFileDaUnireBean.getUriModello(), lFileDaUnireBean.getTipoModello(), templateValues, getSession());
						mLogger.debug("Path del file generato iniettando il modello: " + lFilePdf.getAbsolutePath());
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
	
	public UnioneFileAttoBean timbraFileUnione(UnioneFileAttoBean bean) throws Exception {

		String idUd = getExtraparams().get("idUd");

		String uriFileTimbrato = timbraFile(idUd, bean.getUri(), bean.getNomeFileUnione());
		
		bean.setUri(uriFileTimbrato);

		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uriFileTimbrato));

		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		InfoFileUtility lFileUtility = new InfoFileUtility();
		lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), bean.getNomeFile(), false, null);

		bean.setInfoFile(lMimeTypeFirmaBean);
		
		if(StringUtils.isNotBlank(bean.getNomeFileUnioneVersIntegrale())) {
			String uriFileTimbratoVersIntegrale = timbraFile(idUd, bean.getUriVersIntegrale(), bean.getNomeFileUnioneVersIntegrale());
			
			bean.setUriVersIntegrale(uriFileTimbratoVersIntegrale);
			
			RebuildedFile lRebuildedFileVersIntegrale = new RebuildedFile();
			lRebuildedFileVersIntegrale.setFile(StorageImplementation.getStorage().extractFile(uriFileTimbratoVersIntegrale));

			MimeTypeFirmaBean lMimeTypeFirmaBeanVersIntegrale = new MimeTypeFirmaBean();
			InfoFileUtility lFileUtilityVersIntegrale = new InfoFileUtility();
			lMimeTypeFirmaBeanVersIntegrale = lFileUtilityVersIntegrale.getInfoFromFile(lRebuildedFileVersIntegrale.getFile().toURI().toString(), bean.getNomeFileVersIntegrale(), false, null);

			bean.setInfoFileVersIntegrale(lMimeTypeFirmaBeanVersIntegrale);
		}

		return bean;
	}

	private String timbraFile(String idUd, String uri, String nomeFile)
			throws Exception, StoreException, StorageException, FileNotFoundException {
		// timbra
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
		lOpzioniTimbroBean.setMimetype("application/pdf");
		lOpzioniTimbroBean.setUri(uri);
		lOpzioniTimbroBean.setNomeFile(nomeFile);
		lOpzioniTimbroBean.setIdUd(idUd);
		TimbraUtility timbraUtility = new TimbraUtility();
		lOpzioniTimbroBean = timbraUtility.loadSegnatureRegistrazioneDefault(lOpzioniTimbroBean, getSession(), getLocale());

		// Setto i parametri del timbro utilizzando dal config.xml il bean OpzioniTimbroAutoDocRegBean
		try{
			OpzioniTimbroAttachEmail lOpzTimbroAutoDocRegBean = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAutoDocRegBean");
			if(lOpzTimbroAutoDocRegBean != null){
				lOpzioniTimbroBean.setPosizioneTimbro(lOpzTimbroAutoDocRegBean.getPosizioneTimbro() != null &&
						!"".equals(lOpzTimbroAutoDocRegBean.getPosizioneTimbro()) ? lOpzTimbroAutoDocRegBean.getPosizioneTimbro().value() : "altoSn");
				lOpzioniTimbroBean.setRotazioneTimbro(lOpzTimbroAutoDocRegBean.getRotazioneTimbro() != null &&
						!"".equals(lOpzTimbroAutoDocRegBean.getRotazioneTimbro()) ? lOpzTimbroAutoDocRegBean.getRotazioneTimbro().value() : "verticale");
				if (lOpzTimbroAutoDocRegBean.getPaginaTimbro() != null) {
					if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina() != null) {
						lOpzioniTimbroBean.setTipoPagina(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina().value());
					} else if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine() != null) {
						lOpzioniTimbroBean.setTipoPagina("intervallo");
						if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa() != null) {
							lOpzioniTimbroBean.setPaginaDa(String.valueOf(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa()));
						}
						if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa() != null) {
							lOpzioniTimbroBean.setPaginaA(String.valueOf(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaA()));
						}
					}
				}
				lOpzioniTimbroBean.setTimbroSingolo(lOpzTimbroAutoDocRegBean.isTimbroSingolo());
				lOpzioniTimbroBean.setMoreLines(lOpzTimbroAutoDocRegBean.isRigheMultiple());
			}
		} catch (NoSuchBeanDefinitionException e) {
			/**
			 * Se il Bean OpzioniTimbroAutoDocRegBean non è correttamente configurato vengono utilizzare le preference del 
			 * bean OpzioniTimbroAttachEmail affinchè la timbratura vada a buon fine.
			 */
			mLogger.warn("OpzioniTimbroAutoDocRegBean non definito nel file di configurazione");
		}

		String uriFileTimbrato = null;

		// Timbro il file
		TimbraResultBean lTimbraResultBean = timbraUtility.timbra(lOpzioniTimbroBean, getSession());
		// Verifico se la timbratura è andata a buon fine
		if (lTimbraResultBean.isResult()) {
			// Aggiungo il file timbrato nella lista dei file da pubblicare
			uriFileTimbrato = lTimbraResultBean.getUri();
		} else {
			// // La timbratura è fallita, pubblico il file sbustato
			// files.add(StorageImplementation.getStorage().extractFile(uriFileSbustato));
			String errorMessage = "Si è verificato un errore durante la timbratura del file di risposta";
			if (StringUtils.isNotBlank(lTimbraResultBean.getError())) {
				errorMessage += ": " + lTimbraResultBean.getError();
			}
			throw new Exception(errorMessage);
		}
		return uriFileTimbrato;
	}

	public File unisciPdf(List<InputStream> lListPdf) throws Exception {
		mLogger.info("Entro in unisciPdf di UnioneFileAttoDatasource");
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
