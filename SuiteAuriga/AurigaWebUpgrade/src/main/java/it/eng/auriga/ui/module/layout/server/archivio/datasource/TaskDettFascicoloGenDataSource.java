/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDel_ud_doc_verBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core_2.bean.DmpkCore2RollbacknumerazioneudBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetestremiregnumud_jBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CompilaListaModelliArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.TaskFascicoloFileFirmatiBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.RettangoloFirmaPadesBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.FileDaFirmare;
import it.eng.auriga.ui.module.layout.server.firmaHsm.datasource.FirmaHsmDataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.CompilaModelloAttivitaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskFileDaFirmareBean;
import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraResultBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraUtility;
import it.eng.client.DmpkCore2Rollbacknumerazioneud;
import it.eng.client.DmpkCoreAdddoc;
import it.eng.client.DmpkCoreDel_ud_doc_ver;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkUtilityGetestremiregnumud_j;
import it.eng.client.GestioneDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FolderCustom;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "TaskDettFascicoloGenDataSource")
public class TaskDettFascicoloGenDataSource extends AbstractServiceDataSource<ArchivioBean, TaskFileDaFirmareBean>{

	private static Logger mLogger = Logger.getLogger(TaskDettFascicoloGenDataSource.class);

	public TaskFileDaFirmareBean getFileDaFirmare(ArchivioBean pArchivioBean) throws StorageException, Exception{
		boolean fileDaTimbrare = getExtraparams().get("fileDaTimbrare") != null ? new Boolean(getExtraparams().get("fileDaTimbrare")) : false;
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		lTaskFileDaFirmareBean.setFiles(getListaFileDaFirmare(pArchivioBean, fileDaTimbrare));		
		return lTaskFileDaFirmareBean;
	}
	
	public ArrayList<FileDaFirmareBean> getListaFileDaFirmare(ArchivioBean pArchivioBean, boolean fileDaTimbrare) throws StorageException, Exception{
		ArrayList<FileDaFirmareBean> listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Per ogni documento devo recuperare solo quello che mi serve
		if (pArchivioBean.getListaDocumentiIstruttoria()!=null && pArchivioBean.getListaDocumentiIstruttoria().size()>0){
			for (AllegatoProtocolloBean lDocumentoIstruttoriaBean : pArchivioBean.getListaDocumentiIstruttoria()){
				// se è la risposta (che è quella con ruolo AFIN)
				if (lDocumentoIstruttoriaBean.getRuoloUd()!=null && "AFIN".equals(lDocumentoIstruttoriaBean.getRuoloUd())){
					if (StringUtils.isNotBlank(lDocumentoIstruttoriaBean.getUriFileAllegato())){
						aggiungiDocumento(listaFileDaFirmare, lDocumentoIstruttoriaBean, fileDaTimbrare);
					}
				}
			}
		}
		return listaFileDaFirmare;
	}
	
	public FileDaFirmareBean creaDocumentoUnioneFile(ArchivioBean pArchivioBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		File lFileUnione = buildUnioneFilePdf(pArchivioBean);				
		String idTipoDocFileUnione = StringUtils.isNotBlank(getExtraparams().get("idTipoDocFileUnione")) ? getExtraparams().get("idTipoDocFileUnione") : null;
		String nomeTipoDocFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeTipoDocFileUnione")) ? getExtraparams().get("nomeTipoDocFileUnione") : null;
		String nomeFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeFileUnione")) ? getExtraparams().get("nomeFileUnione") : "RispostaCompleta.pdf";
		String uriFileUnione = StorageImplementation.getStorage().store(lFileUnione, new String[] {});
		FileDaFirmareBean lFileUnioneBean = new FileDaFirmareBean();
		lFileUnioneBean.setUri(uriFileUnione);
		lFileUnioneBean.setIsFilePrincipaleAtto(true);
		lFileUnioneBean.setNomeFile(nomeFileUnione);		
		lFileUnioneBean.setInfoFile(new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFileUnione).toURI().toString(), nomeFileUnione, false, null));		
		AllegatoProtocolloBean lRispostaCompletaBean = null;
		if(StringUtils.isNotBlank(idTipoDocFileUnione)) {
			if (pArchivioBean.getListaDocumentiIstruttoria()!=null && pArchivioBean.getListaDocumentiIstruttoria().size()>0){
				for (AllegatoProtocolloBean lDocumentoIstruttoriaBean : pArchivioBean.getListaDocumentiIstruttoria()){
					if (lDocumentoIstruttoriaBean.getIdTipoFileAllegato()!=null && lDocumentoIstruttoriaBean.getIdTipoFileAllegato().equals(idTipoDocFileUnione)){
						lRispostaCompletaBean = new AllegatoProtocolloBean();
						BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lRispostaCompletaBean, lDocumentoIstruttoriaBean); 						
					}
				}
			}
		}
		if(lRispostaCompletaBean == null) {
			// Creo il documento relativo al file unione da aggiungere al fascicolo
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
			lCreaModDocumentoInBean.setTipoDocumento(idTipoDocFileUnione);
			lCreaModDocumentoInBean.setNomeDocType(nomeTipoDocFileUnione);
			lCreaModDocumentoInBean.setOggetto("Risposta completa (con allegati integrati)");
			List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
			FolderCustom folderCustom = new FolderCustom();
			folderCustom.setId(String.valueOf(pArchivioBean.getIdUdFolder()));
			listaFolderCustom.add(folderCustom);
			lCreaModDocumentoInBean.setFolderCustom(listaFolderCustom);
			CreaModDocumentoOutBean lCreaModDocumentoOutBean = lGestioneDocumenti.creadocumento(getLocale(), loginBean, lCreaModDocumentoInBean, null, null);
			if (lCreaModDocumentoOutBean.getDefaultMessage() != null) {
				throw new StoreException(lCreaModDocumentoOutBean);
			}
			lRispostaCompletaBean = new AllegatoProtocolloBean();
			lRispostaCompletaBean.setIdUdAppartenenza(String.valueOf(lCreaModDocumentoOutBean.getIdUd().longValue()));
			lRispostaCompletaBean.setIdDocAllegato(lCreaModDocumentoOutBean.getIdDoc());
		}
		boolean fileDaTimbrare = getExtraparams().get("fileDaTimbrare") != null ? new Boolean(getExtraparams().get("fileDaTimbrare")) : false;
		String idUd = lRispostaCompletaBean.getIdUdAppartenenza();
		String idDoc = String.valueOf(lRispostaCompletaBean.getIdDocAllegato().longValue());
		if (fileDaTimbrare) {	
			FileDaFirmareBean lFileUnioneTimbratoBean = timbraFile(lFileUnioneBean, idUd);
			lFileUnioneTimbratoBean.setIdUd(idUd);
			lFileUnioneTimbratoBean.setIdDoc(idDoc);						
			return lFileUnioneTimbratoBean;
		} else {
			lFileUnioneBean.setIdUd(idUd);
			lFileUnioneBean.setIdDoc(idDoc);			
			return lFileUnioneBean;
		}
	}
	
	public FileDaFirmareBean versionaDocumentoUnioneFile(FileDaFirmareBean lFileUnioneBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		lVersionaDocumentoInBean.setIdDocumento(new BigDecimal(lFileUnioneBean.getIdDoc()));
		lVersionaDocumentoInBean.setFile(StorageImplementation.getStorage().extractFile(lFileUnioneBean.getUri()));
		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);
		GenericFile lGenericFile = new GenericFile();
		setProprietaGenericFile(lGenericFile, lFileUnioneBean.getInfoFile());
		lGenericFile.setMimetype(lFileUnioneBean.getInfoFile().getMimetype());
		lGenericFile.setDisplayFilename(lFileUnioneBean.getNomeFile());
		lGenericFile.setImpronta(lFileUnioneBean.getInfoFile().getImpronta());
		lGenericFile.setImprontaFilePreFirma(lFileUnioneBean.getInfoFile().getImprontaFilePreFirma());
		lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
		lFileInfoBean.setAllegatoRiferimento(lGenericFile);
		lVersionaDocumentoInBean.setInfo(lFileInfoBean);
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), loginBean, lVersionaDocumentoInBean);
		if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
			mLogger.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
			throw new StoreException(lVersionaDocumentoOutBean);
		}	
		return lFileUnioneBean;
	}
	
	public FileDaFirmareBean cancellaDocumentoUnioneFile(FileDaFirmareBean lFileUnioneBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		DmpkCoreDel_ud_doc_verBean delUdDocVerInput = new DmpkCoreDel_ud_doc_verBean();
		delUdDocVerInput.setFlgtipotargetin("U");
		delUdDocVerInput.setIduddocin(new BigDecimal(lFileUnioneBean.getIdUd()));
		delUdDocVerInput.setFlgcancfisicain(new Integer(1));
		DmpkCoreDel_ud_doc_ver delUdDocVer = new DmpkCoreDel_ud_doc_ver();
		StoreResultBean<DmpkCoreDel_ud_doc_verBean> output = delUdDocVer.execute(getLocale(), loginBean, delUdDocVerInput);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return lFileUnioneBean;		
	}
	
	private File buildUnioneFilePdf(ArchivioBean pArchivioBean) throws Exception {
		try {
			mLogger.debug("UNIONE DEI FILE");
			List<FileDaFirmareBean> lListFileDaUnireBean = getListaFileDaFirmare(pArchivioBean, false);
			if (lListFileDaUnireBean != null && !lListFileDaUnireBean.isEmpty()) {
				List<InputStream> lListInputStream = new ArrayList<InputStream>(lListFileDaUnireBean.size());
				for (FileDaFirmareBean lFileDaUnireBean : lListFileDaUnireBean) {
					mLogger.debug("File " + lFileDaUnireBean.getNomeFile() + ": " + lFileDaUnireBean.getUri());
					if (lFileDaUnireBean.getInfoFile().isFirmato() && lFileDaUnireBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
						mLogger.debug("Il file è firmato in CAdES");
						FileDaFirmareBean lFileSbustatoBean = sbustaFile(lFileDaUnireBean);
						if (lFileDaUnireBean.getInfoFile().getMimetype().equals("application/pdf")) {
							mLogger.debug("Il file sbustato è un pdf, quindi lo aggiungo");
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileSbustatoBean.getUri()));
						} else if (lFileDaUnireBean.getInfoFile().isConvertibile()) {
							mLogger.debug("Il file sbustato non è un pdf ed è convertibile, quindi provo a convertirlo e lo aggiungo");
							mLogger.debug("mimetype: " + lFileDaUnireBean.getInfoFile().getMimetype());							
							try {
								FileDaFirmareBean lFileSbustatoConvertitoBean = convertiFile(lFileSbustatoBean);
								lListInputStream.add(StorageImplementation.getStorage().extract(lFileSbustatoConvertitoBean.getUri()));	
							} catch (Exception e) {
								String errorMessage = "Errore durante la conversione del file sbustato";
								if (lFileSbustatoBean != null && StringUtils.isNotBlank(lFileSbustatoBean.getNomeFile())) {
									errorMessage = "Errore durante la conversione del file sbustato " + lFileSbustatoBean.getNomeFile();
								}
								mLogger.error(errorMessage + " : " + e.getMessage(), e);
								throw new StoreException(errorMessage);
							}
						} else {
							String errorMessage = "Il file sbustato non è un pdf e non è convertibile.";
							if (lFileSbustatoBean != null && StringUtils.isNotBlank(lFileSbustatoBean.getNomeFile())) {
								errorMessage = "Il file sbustato" + lFileSbustatoBean.getNomeFile() + " non è un pdf e non è convertibile.";
							}
							mLogger.error(errorMessage);
							throw new StoreException(errorMessage);
						}
					} else if (lFileDaUnireBean.getInfoFile().getMimetype().equals("application/pdf")) {
						if (lFileDaUnireBean.getInfoFile().isFirmato() && lFileDaUnireBean.getInfoFile().getTipoFirma().equalsIgnoreCase("PADES")) {
							mLogger.debug("Il file è firmato in PAdES quindi devo prendere la versione precedente la firma");
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUriVerPreFirma()));
						} else {
							mLogger.debug("Il file è un pdf, quindi lo aggiungo");
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUri()));
						}
					} else if (lFileDaUnireBean.getInfoFile().isConvertibile()) {
						mLogger.debug("Il file non è un pdf ed è convertibile, quindi provo a convertirlo e lo aggiungo");
						try {
							FileDaFirmareBean lFileConvertitoBean = convertiFile(lFileDaUnireBean);
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileConvertitoBean.getUri()));	
						} catch (Exception e) {
							String errorMessage = "Errore durante la conversione del file";
							if (lFileDaUnireBean != null && StringUtils.isNotBlank(lFileDaUnireBean.getNomeFile())) {
								errorMessage = "Errore durante la conversione del file " + lFileDaUnireBean.getNomeFile();
							}
							mLogger.error(errorMessage + " : " + e.getMessage(), e);
							throw new StoreException(errorMessage);
							
						}
					} else {
						String errorMessage = "Il file non è un pdf e non è convertibile.";
						if (lFileDaUnireBean != null && StringUtils.isNotBlank(lFileDaUnireBean.getNomeFile())) {
							errorMessage = "Il file " + lFileDaUnireBean.getNomeFile() + " non è un pdf e non è convertibile.";
						}
						mLogger.error(errorMessage);
						throw new StoreException(errorMessage);
					
					}					
				}
				return unioneFilePdf(lListInputStream);				
			} else {
				String errorMessage = "E' obbligatorio inserire almeno un file per procedere.";
				mLogger.error(errorMessage);
				throw new StoreException(errorMessage);
			}	
		} catch(StoreException e) {
			mLogger.error("Si è verificato un errore durante l'unione dei file. " + e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante l'unione dei file. " + e.getMessage());
		} catch (Exception e1) {
			mLogger.error("Si è verificato un errore durante l'unione dei file. " + e1.getMessage(), e1);
			throw new StoreException("Si è verificato un errore durante l'unione dei file.");
		}
	}
	
	public File unioneFilePdf(List<InputStream> lListInputStream) throws Exception {
		Document document = new Document();
		// Istanzio una copia nell'output
		File lFile = File.createTempFile("pdf", ".pdf");
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(lFile));
		// Apro per la modifica il nuovo documento
		document.open();
		// Istanzio un nuovo reader che ci servirà per leggere i singoli file
		PdfReader reader;
		// Per ogni file passato
		for (InputStream lInputStream : lListInputStream) {
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

	private void aggiungiDocumento(ArrayList<FileDaFirmareBean> listaFileDaFirmare, AllegatoProtocolloBean lDocumentoIstruttoriaBean, boolean fileDaTimbrare) throws Exception {
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		lFileDaFirmareBean.setIdFile(lDocumentoIstruttoriaBean.getUriFileAllegato());
		lFileDaFirmareBean.setInfoFile(lDocumentoIstruttoriaBean.getInfoFile());
		lFileDaFirmareBean.setNomeFile(lDocumentoIstruttoriaBean.getNomeFileAllegato());
		lFileDaFirmareBean.setUri(lDocumentoIstruttoriaBean.getUriFileAllegato());
		lFileDaFirmareBean.setUriVerPreFirma(lDocumentoIstruttoriaBean.getUriFileVerPreFirma());
		lFileDaFirmareBean.setIdUd(lDocumentoIstruttoriaBean.getIdUdAppartenenza());
		lFileDaFirmareBean.setIdDoc(lDocumentoIstruttoriaBean.getIdDocAllegato() != null ? String.valueOf(lDocumentoIstruttoriaBean.getIdDocAllegato().longValue()) : null);
		if(listaFileDaFirmare == null) {
			listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		}
		if (fileDaTimbrare && StringUtils.isNotBlank(lDocumentoIstruttoriaBean.getIdUdAppartenenza())) {			
			listaFileDaFirmare.add(timbraFile(lFileDaFirmareBean, lDocumentoIstruttoriaBean.getIdUdAppartenenza()));
		} else {
			listaFileDaFirmare.add(lFileDaFirmareBean);
		}		
	}
	
	public ArchivioBean aggiornaFileFirmati(TaskFascicoloFileFirmatiBean pTaskFascicoloFileFirmatiBean) throws Exception{
		ArchivioBean lArchivioBean = pTaskFascicoloFileFirmatiBean.getFascicoloOriginale();
		boolean firmaNonValida = false;
		for (FileDaFirmareBean lFileDaFirmareBean : pTaskFascicoloFileFirmatiBean.getFileFirmati().getFiles()){
			if(lFileDaFirmareBean.getInfoFile().isFirmato() && !lFileDaFirmareBean.getInfoFile().isFirmaValida()) {
				if (lFileDaFirmareBean.getIdFile().startsWith("fileUnione")) {
					mLogger.error("La firma del file unione " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: " + lFileDaFirmareBean.getUri());
				} else if (lFileDaFirmareBean.getIdFile().startsWith("fileGenerato")) {
					mLogger.error("La firma del file generato " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: "	+ lFileDaFirmareBean.getUri());
				} else {
					mLogger.error("La firma del file " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: " + lFileDaFirmareBean.getUri());
				}
				firmaNonValida = true;
			}
			if (lFileDaFirmareBean.getIdFile().startsWith("fileUnione")) {
				aggiornaFileUnione(lArchivioBean, lFileDaFirmareBean);
			} else if (lFileDaFirmareBean.getIdFile().startsWith("fileGenerato")) {
				aggiornaFileGenerato(lArchivioBean, lFileDaFirmareBean);
			} else {
				aggiornaDocumento(lArchivioBean, lFileDaFirmareBean);
			}
		}
		if(firmaNonValida) {
			throw new StoreException("La firma di uno o più file risulta essere non valida");
		}
		return lArchivioBean;
	}
	
	public ArchivioBean aggiornaFile(TaskFascicoloFileFirmatiBean pTaskFascicoloFileFirmatiBean) throws Exception{
		ArchivioBean lArchivioBean = pTaskFascicoloFileFirmatiBean.getFascicoloOriginale();
		for (FileDaFirmareBean lFileDaFirmareBean : pTaskFascicoloFileFirmatiBean.getFileFirmati().getFiles()){			
			aggiornaDocumento(lArchivioBean, lFileDaFirmareBean);			
		}		
		return lArchivioBean;
	}

	private void aggiornaDocumento(ArchivioBean lArchivioBean,	FileDaFirmareBean lFileDaFirmareBean) {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile();
		for (AllegatoProtocolloBean lDocumentoIstruttoriaBean : lArchivioBean.getListaDocumentiIstruttoria()){
			if (lDocumentoIstruttoriaBean.getUriFileAllegato() != null && lDocumentoIstruttoriaBean.getUriFileAllegato().equals(uriFileOriginale)){										
				lDocumentoIstruttoriaBean.setUriFileAllegato(lFileDaFirmareBean.getUri());
				lDocumentoIstruttoriaBean.setNomeFileAllegato(lFileDaFirmareBean.getNomeFile());
				lDocumentoIstruttoriaBean.setRemoteUri(false);
				lDocumentoIstruttoriaBean.setInfoFile(lFileDaFirmareBean.getInfoFile());
				lDocumentoIstruttoriaBean.setIdTaskVer(getExtraparams().get("idTaskCorrente"));
				lDocumentoIstruttoriaBean.setIsChanged(true);
				break;
			}
		}
	}
	
	private void aggiornaFileUnione(ArchivioBean lArchivioBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("fileUnione".length());		
		if(lArchivioBean.getListaDocumentiIstruttoria() != null) {
			for (AllegatoProtocolloBean lDocumentoIstruttoriaBean : lArchivioBean.getListaDocumentiIstruttoria()){
				if (lDocumentoIstruttoriaBean.getUriFileAllegato() != null && lDocumentoIstruttoriaBean.getUriFileAllegato().equals(uriFileOriginale)) {									
					lDocumentoIstruttoriaBean.setUriFileAllegato(lFileDaFirmareBean.getUri());
					lDocumentoIstruttoriaBean.setNomeFileAllegato(lFileDaFirmareBean.getNomeFile());
					lDocumentoIstruttoriaBean.setRemoteUri(false);
					String precImpronta = lDocumentoIstruttoriaBean.getInfoFile() != null ? lDocumentoIstruttoriaBean.getInfoFile().getImpronta() : null;
					lDocumentoIstruttoriaBean.setInfoFile(lFileDaFirmareBean.getInfoFile());
					lDocumentoIstruttoriaBean.setIdTaskVer(getExtraparams().get("idTaskCorrente"));
					if (precImpronta == null || !precImpronta.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
						lDocumentoIstruttoriaBean.setIsChanged(true);
					}
					break;
				}
			}
		}
	}
	
	private void aggiornaFileGenerato(ArchivioBean lArchivioBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("fileGenerato".length());		
		if(lArchivioBean.getListaDocumentiIstruttoria() != null) {
			for (AllegatoProtocolloBean lDocumentoIstruttoriaBean : lArchivioBean.getListaDocumentiIstruttoria()){
				if (lDocumentoIstruttoriaBean.getUriFileAllegato() != null && lDocumentoIstruttoriaBean.getUriFileAllegato().equals(uriFileOriginale)) {									
					lDocumentoIstruttoriaBean.setUriFileAllegato(lFileDaFirmareBean.getUri());
					lDocumentoIstruttoriaBean.setNomeFileAllegato(lFileDaFirmareBean.getNomeFile());
					lDocumentoIstruttoriaBean.setRemoteUri(false);
					String precImpronta = lDocumentoIstruttoriaBean.getInfoFile() != null ? lDocumentoIstruttoriaBean.getInfoFile().getImpronta() : null;
					lDocumentoIstruttoriaBean.setInfoFile(lFileDaFirmareBean.getInfoFile());
					lDocumentoIstruttoriaBean.setIdTaskVer(getExtraparams().get("idTaskCorrente"));
					if (precImpronta == null || !precImpronta.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
						lDocumentoIstruttoriaBean.setIsChanged(true);
					}
					break;
				}
			}
		}
	}

	private FileDaFirmareBean timbraFile(FileDaFirmareBean lFileDaTimbrareBean, String idUd) throws Exception {
		mLogger.debug("TIMBRO FILE");		
		mLogger.debug("File " + lFileDaTimbrareBean.getNomeFile() + ": " + lFileDaTimbrareBean.getUri());
		if (StringUtils.isNotBlank(idUd)) {			
			mLogger.debug("idUd: " + idUd);
			if (lFileDaTimbrareBean.getInfoFile().isFirmato() && lFileDaTimbrareBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
				mLogger.debug("Il file è firmato in CAdES");
				FileDaFirmareBean lFileSbustatoBean = sbustaFile(lFileDaTimbrareBean);
				if (lFileDaTimbrareBean.getInfoFile().getMimetype().equals("application/pdf")) {
					mLogger.debug("Il file sbustato è un pdf, quindi lo timbro");
					return timbraFilePdf(lFileSbustatoBean, idUd);						
				} else if (lFileDaTimbrareBean.getInfoFile().isConvertibile()) {
					mLogger.debug("Il file sbustato non è un pdf ed è convertibile, quindi provo a convertirlo e lo timbro");
					mLogger.debug("mimetype: " + lFileDaTimbrareBean.getInfoFile().getMimetype());							
					try {
						FileDaFirmareBean lFileSbustatoConvertitoBean = convertiFile(lFileSbustatoBean);
						return timbraFilePdf(lFileSbustatoConvertitoBean, idUd);	
					} catch (Exception e) {
						mLogger.debug("Errore durante la conversione del file sbustato: " + e.getMessage(), e);
					}
				} else {
					mLogger.debug("Il file sbustato non è un pdf e non è convertibile, quindi non lo timbro");
				}
			} else if (lFileDaTimbrareBean.getInfoFile().getMimetype().equals("application/pdf")) {
				mLogger.debug("Il file è un pdf, quindi lo timbro");
				return timbraFilePdf(lFileDaTimbrareBean, idUd);		
			} else if (lFileDaTimbrareBean.getInfoFile().isConvertibile()) {
				mLogger.debug("Il file non è un pdf ed è convertibile, quindi provo a convertirlo e lo timbro");
				try {
					FileDaFirmareBean lFileConvertitoBean = convertiFile(lFileDaTimbrareBean);
					return timbraFilePdf(lFileConvertitoBean, idUd);	
				} catch (Exception e) {
					mLogger.debug("Errore durante la conversione del file: " + e.getMessage(), e);
				}
			} else {
				mLogger.debug("Il file non è un pdf e non è convertibile, quindi non lo timbro");
			}							
		} else {
			mLogger.debug("idUd non valorizzata, quindi non lo timbro");
		}
		return lFileDaTimbrareBean;
	}
	
	private FileDaFirmareBean convertiFile(FileDaFirmareBean lFileDaConvertireBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());		
		RecuperoFile lRecuperoFile = new RecuperoFile();
		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(lFileDaConvertireBean.getUri());
		FileExtractedOut out = lRecuperoFile.extractfile(getLocale(), loginBean, lFileExtractedIn);
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		File lFile = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));				
		String nomeFile = lFileDaConvertireBean.getInfoFile().getCorrectFileName() != null ? lFileDaConvertireBean.getInfoFile().getCorrectFileName() : "";
		String nomeFilePdf = FilenameUtils.getBaseName(nomeFile) + ".pdf";
		String uriPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(lFile.toURI().toString(), nomeFile));
		lFileDaConvertireBean.setNomeFile(nomeFilePdf);
		lFileDaConvertireBean.setUri(uriPdf);
		lFileDaConvertireBean.setInfoFile(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null));
		return lFileDaConvertireBean;			
	}
	
	private FileDaFirmareBean sbustaFile(FileDaFirmareBean lFileDaSbustareBean) throws Exception {
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		File lFile = StorageImplementation.getStorage().getRealFile(lFileDaSbustareBean.getUri());				
		String nomeFile = lFileDaSbustareBean.getInfoFile().getCorrectFileName() != null ? lFileDaSbustareBean.getInfoFile().getCorrectFileName() : "";		
		String nomeFileSbustato = (nomeFile != null && nomeFile.toLowerCase().endsWith(".p7m")) ? nomeFile.substring(0, nomeFile.length() - 4) : nomeFile;		
		String uriSbustato = StorageImplementation.getStorage().storeStream(lInfoFileUtility.sbusta(lFile.toURI().toString(), nomeFile));		
		lFileDaSbustareBean.setNomeFile(nomeFileSbustato);
		lFileDaSbustareBean.setUri(uriSbustato);
		lFileDaSbustareBean.setInfoFile(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null));
		return lFileDaSbustareBean;				
	}
	
	private FileDaFirmareBean timbraFilePdf(FileDaFirmareBean lFileDaTimbrareBean, String idUd) throws Exception {
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
		lOpzioniTimbroBean.setMimetype("application/pdf");
		lOpzioniTimbroBean.setUri(lFileDaTimbrareBean.getUri());
		lOpzioniTimbroBean.setNomeFile(lFileDaTimbrareBean.getNomeFile());
		lOpzioniTimbroBean.setIdUd(idUd);
		lOpzioniTimbroBean.setRemote(true);
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
			
		// Timbro il file
		TimbraResultBean lTimbraResultBean = timbraUtility.timbra(lOpzioniTimbroBean, getSession());
		// Verifico se la timbratura è andata a buon fine
		if (lTimbraResultBean.isResult()) {
			// Aggiungo il file timbrato nella lista dei file da pubblicare
			lFileDaTimbrareBean.setUri(lTimbraResultBean.getUri());
		} else {
			// // La timbratura è fallita, pubblico il file sbustato
			// files.add(StorageImplementation.getStorage().extractFile(uriFileSbustato));
			String errorMessage = "Si è verificato un errore durante la timbratura del file";
			if (StringUtils.isNotBlank(lTimbraResultBean.getError())) {
				errorMessage += ": " + lTimbraResultBean.getError();
			}
			throw new Exception(errorMessage);
		}		
		File lFileTimbrato = StorageImplementation.getStorage().extractFile(lFileDaTimbrareBean.getUri());		
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		InfoFileUtility lFileUtility = new InfoFileUtility();
		lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lFileTimbrato.toURI().toString(), lFileDaTimbrareBean.getNomeFile(), false, null);
		lMimeTypeFirmaBean.setFirmato(false);
		lMimeTypeFirmaBean.setFirmaValida(false);
		lMimeTypeFirmaBean.setConvertibile(false);
		lMimeTypeFirmaBean.setDaScansione(false);
		lFileDaTimbrareBean.setInfoFile(lMimeTypeFirmaBean);				
		return lFileDaTimbrareBean;
	}
	
	public CompilaListaModelliArchivioBean compilazioneAutomaticaListaModelliPdf(CompilaListaModelliArchivioBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		if(bean.getListaRecordModelli() != null) {
			
			for(int i = 0; i < bean.getListaRecordModelli().size(); i++) {
				CompilaModelloAttivitaBean modelloBean = bean.getListaRecordModelli().get(i);
				// Verifico che ci sia già un documento di istruttoria di quel tipo senza file
				AllegatoProtocolloBean lDocumentoIstruttoriaBean = null;
				if(StringUtils.isNotBlank(modelloBean.getIdTipoDoc())) {
					if(bean.getDettaglioBean().getListaDocumentiIstruttoria() != null) {
						for(int j = 0; j < bean.getDettaglioBean().getListaDocumentiIstruttoria().size(); j++) {
							if(bean.getDettaglioBean().getListaDocumentiIstruttoria().get(j).getListaTipiFileAllegato() != null && modelloBean.getIdTipoDoc().equals(bean.getDettaglioBean().getListaDocumentiIstruttoria().get(j).getListaTipiFileAllegato())) {
								// se è una UD e il file non è presente 
								if(StringUtils.isNotBlank(bean.getDettaglioBean().getListaDocumentiIstruttoria().get(j).getIdUdAppartenenza()) && StringUtils.isBlank(bean.getDettaglioBean().getListaDocumentiIstruttoria().get(j).getUriFileAllegato())) {
									lDocumentoIstruttoriaBean = bean.getDettaglioBean().getListaDocumentiIstruttoria().get(j);
									break;
								}
							}
						}
					}
				}
				// Se ho una numerazione da dare
				if(StringUtils.isNotBlank(modelloBean.getCategoriaNumDaDare())) {
					
					// Se ho già un doc. di istruttoria di quel tipo senza file
					if(lDocumentoIstruttoriaBean != null) {
						// se non è già numerato
						if(StringUtils.isBlank(lDocumentoIstruttoriaBean.getEstremiProtUd())) {	
							
							DmpkCoreUpddocudBean lDmpkCoreUpddocudInput = new DmpkCoreUpddocudBean();
							lDmpkCoreUpddocudInput.setCodidconnectiontokenin(loginBean.getToken());
							lDmpkCoreUpddocudInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
							lDmpkCoreUpddocudInput.setIduddocin(new BigDecimal(lDocumentoIstruttoriaBean.getIdUdAppartenenza()));
							lDmpkCoreUpddocudInput.setFlgtipotargetin("U");
							
							CreaModDocumentoInBean lModDocumentoInBean = new CreaModDocumentoInBean();
							
							List<TipoNumerazioneBean> listaTipiNumerazioneDaDare = new ArrayList<TipoNumerazioneBean>();
							TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
							lTipoNumerazioneBean.setCategoria(modelloBean.getCategoriaNumDaDare());
							lTipoNumerazioneBean.setSigla(modelloBean.getSiglaNumDaDare());
							listaTipiNumerazioneDaDare.add(lTipoNumerazioneBean);
							lModDocumentoInBean.setTipoNumerazioni(listaTipiNumerazioneDaDare);
							
							XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
							lDmpkCoreUpddocudInput.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lModDocumentoInBean));
		
							DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
							StoreResultBean<DmpkCoreUpddocudBean> lDmpkCoreUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean, lDmpkCoreUpddocudInput);
							if (lDmpkCoreUpddocudOutput.isInError()) {
								throw new StoreException(lDmpkCoreUpddocudOutput);
							}
							
							lDocumentoIstruttoriaBean.setEstremiProtUd(recuperaEstremiUd(lDocumentoIstruttoriaBean.getIdUdAppartenenza(), modelloBean.getCategoriaNumDaDare(), modelloBean.getSiglaNumDaDare()));
						}
					} else {
						
						DmpkCoreAdddocBean lDmpkCoreAdddocInput = new DmpkCoreAdddocBean();
						lDmpkCoreAdddocInput.setCodidconnectiontokenin(loginBean.getToken());
						lDmpkCoreAdddocInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
						
						CreaModDocumentoInBean lCreaDocumentoInBean = new CreaModDocumentoInBean();
						lCreaDocumentoInBean.setOggetto(modelloBean.getDescrizione());
						lCreaDocumentoInBean.setTipoDocumento(modelloBean.getIdTipoDoc());
						lCreaDocumentoInBean.setNomeDocType(modelloBean.getNomeTipoDoc());
						
						List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
						FolderCustom folderCustom = new FolderCustom();
						folderCustom.setId(String.valueOf(bean.getIdFolder()));
						listaFolderCustom.add(folderCustom);
						lCreaDocumentoInBean.setFolderCustom(listaFolderCustom);
											
						List<TipoNumerazioneBean> listaTipiNumerazioneDaDare = new ArrayList<TipoNumerazioneBean>();					
						TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();							
						lTipoNumerazioneBean.setCategoria(modelloBean.getCategoriaNumDaDare());
						lTipoNumerazioneBean.setSigla(modelloBean.getSiglaNumDaDare());
						listaTipiNumerazioneDaDare.add(lTipoNumerazioneBean);		
						lCreaDocumentoInBean.setTipoNumerazioni(listaTipiNumerazioneDaDare);
						
						XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
						lDmpkCoreAdddocInput.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaDocumentoInBean));
	
						DmpkCoreAdddoc lDmpkCoreAdddoc = new DmpkCoreAdddoc();
						StoreResultBean<DmpkCoreAdddocBean> lDmpkCoreAdddocOutput = lDmpkCoreAdddoc.execute(getLocale(), loginBean, lDmpkCoreAdddocInput);
						if (lDmpkCoreAdddocOutput.isInError()) {
							throw new StoreException(lDmpkCoreAdddocOutput);
						} 	
						
						lDocumentoIstruttoriaBean = new AllegatoProtocolloBean();
						lDocumentoIstruttoriaBean.setIdUdAppartenenza(String.valueOf(lDmpkCoreAdddocOutput.getResultBean().getIdudout().longValue()));
						lDocumentoIstruttoriaBean.setIdDocAllegato(lDmpkCoreAdddocOutput.getResultBean().getIddocout());							
						lDocumentoIstruttoriaBean.setDescrizioneFileAllegato(modelloBean.getDescrizione());						
						lDocumentoIstruttoriaBean.setListaTipiFileAllegato(modelloBean.getIdTipoDoc());
						lDocumentoIstruttoriaBean.setIdTipoFileAllegato(modelloBean.getIdTipoDoc());
						lDocumentoIstruttoriaBean.setDescTipoFileAllegato(modelloBean.getNomeTipoDoc());	
						lDocumentoIstruttoriaBean.setEstremiProtUd(recuperaEstremiUd(lDocumentoIstruttoriaBean.getIdUdAppartenenza(), modelloBean.getCategoriaNumDaDare(), modelloBean.getSiglaNumDaDare()));
						
						if(bean.getDettaglioBean().getListaDocumentiIstruttoria() == null) {
							bean.getDettaglioBean().setListaDocumentiIstruttoria(new ArrayList<AllegatoProtocolloBean>());
						}
						bean.getDettaglioBean().getListaDocumentiIstruttoria().add(lDocumentoIstruttoriaBean);	
					}
				}
				
				//TODO devo salvare i documenti istruttoria altrimenti se va in errore la generazione da modello non riporta a maschera la ud che ho appena aggiunto e numerato e me ne rifà ogni volta una nuova
				
				// Faccio la generazione da modello passando idFolder (se ho idUd passo anche quello)
				
				modelloBean.setIdUd(lDocumentoIstruttoriaBean != null ? lDocumentoIstruttoriaBean.getIdUdAppartenenza() : null);
				modelloBean.setEstremiProtUd(lDocumentoIstruttoriaBean != null ? lDocumentoIstruttoriaBean.getEstremiProtUd() : null);
				
				String templateValues = getDatiXGenDaModello(bean.getDettaglioBean(), modelloBean.getNomeModello(), modelloBean.getIdUd(), null);
				if(StringUtils.isNotBlank(modelloBean.getIdModello())) {
					FileDaFirmareBean lFileDaFirmareBean = ModelliUtil.fillTemplate(bean.getProcessId(), modelloBean.getIdModello(), templateValues, true, getSession());			
					modelloBean.setUriFileGenerato(lFileDaFirmareBean.getUri());
					modelloBean.setInfoFileGenerato(lFileDaFirmareBean.getInfoFile());			
				} else {
					File fileModelloPdf = ModelliUtil.fillTemplateAndConvertToPdf(bean.getProcessId(), modelloBean.getUri(), modelloBean.getTipoModello(), templateValues, getSession());
					String storageUri = StorageImplementation.getStorage().store(fileModelloPdf);
					modelloBean.setUriFileGenerato(storageUri);
					MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
					infoFile.setMimetype("application/pdf");
					infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(fileModelloPdf));
					infoFile.setDaScansione(false);
					infoFile.setFirmato(false);
					infoFile.setFirmaValida(false);
					infoFile.setBytes(fileModelloPdf.length());
					infoFile.setCorrectFileName(modelloBean.getNomeFile());
					File realFile = StorageImplementation.getStorage().getRealFile(modelloBean.getUri());
					InfoFileUtility lInfoFileUtility = new InfoFileUtility();
					infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), modelloBean.getNomeFile()));
					modelloBean.setInfoFileGenerato(infoFile);	
				}	
				if(modelloBean.getFlgFirmaAuto() != null && modelloBean.getFlgFirmaAuto()) {					
					// Firma automatica	con i seguenti parametri		
					String providerFirmaAuto = modelloBean.getProviderFirmaAuto();
					String userIdFirmaAuto = modelloBean.getUserIdFirmaAuto();
					String firmaInDelegaFirmaAuto = modelloBean.getFirmaInDelegaFirmaAuto();
					String passwordFirmaAuto = modelloBean.getPasswordFirmaAuto();					
					FirmaHsmBean lFirmaHsmBean = new FirmaHsmBean();					
					List<FileDaFirmare> listaFileDaFirmare = new ArrayList<>();
					// Settare i parametri del file da firmare
					FileDaFirmare fileDaFirmare = lFirmaHsmBean.new FileDaFirmare();
					fileDaFirmare.setUri(modelloBean.getUriFileGenerato());
					fileDaFirmare.setInfoFile(modelloBean.getInfoFileGenerato());
					String nomeFile = modelloBean.getNomeFile();
					boolean estensioneforzata = false;
					if (modelloBean.getInfoFileGenerato() != null && "application/pdf".equalsIgnoreCase(modelloBean.getInfoFileGenerato().getMimetype()) && modelloBean.getNomeFile() != null && !modelloBean.getNomeFile().toLowerCase(getLocale()).endsWith(".pdf")) {
						estensioneforzata = true;
						nomeFile += ".pdf";
					}
					fileDaFirmare.setNomeFile(nomeFile);					
					listaFileDaFirmare.add(fileDaFirmare);				
					lFirmaHsmBean.setListaFileDaFirmare(listaFileDaFirmare);
					String tipoMarca = "";
					if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "HSM_MARCA_SIGNATURE")) {
						tipoMarca = "HSM";
					} else {
						tipoMarca = ParametriDBUtil.getParametroDB(getSession(), "HSM_MARCA_SIGNATURE");
					}
					lFirmaHsmBean.setFileDaMarcare(tipoMarca);
					// Setto firmatario ed eventuale delegante
					if (StringUtils.isNotBlank(firmaInDelegaFirmaAuto)) {
						lFirmaHsmBean.setUsername(firmaInDelegaFirmaAuto);
						lFirmaHsmBean.setUsernameDelegante(userIdFirmaAuto);
					} else {
						lFirmaHsmBean.setUsername(userIdFirmaAuto);
						lFirmaHsmBean.setUsernameDelegante("");
					}
					lFirmaHsmBean.setPassword(passwordFirmaAuto);
					// Parametri per eventuale firma Medas
					// lFirmaHsmBean.setCodiceOtp(codiceOtp);
					// lFirmaHsmBean.setCertId(certId);
					// lFirmaHsmBean.setPotereDiFirma(potereDiFirma);
					// lFirmaHsmBean.setParametriHSMFromGui(parametriHSMFromGui);
					lFirmaHsmBean.setProviderHsmFromPreference(providerFirmaAuto);
					lFirmaHsmBean.setSkipControlloCoerenzaCertificatoFirma(true);					
					String parametriRettangoloFirmaJson =  ParametriDBUtil.getParametroDB(getSession(), "POSITION_GRAPHIC_SIGNATURE_IN_PADES");
					if ((parametriRettangoloFirmaJson != null) && (!"".equalsIgnoreCase(parametriRettangoloFirmaJson.trim()))) {
						RettangoloFirmaPadesBean rettangoloFirmaPades = new Gson().fromJson(parametriRettangoloFirmaJson, RettangoloFirmaPadesBean.class);
						lFirmaHsmBean.setRettangoloFirmaPades(rettangoloFirmaPades);
					}					
					lFirmaHsmBean.setParametriHSMFromGui(true);					
					FirmaHsmDataSource dataSource = new FirmaHsmDataSource();
					dataSource.setSession(getSession());					
					String hsmTipoFirmaAtti = ParametriDBUtil.getParametroDB(getSession(), "HSM_TIPO_FIRMA_ATTI");
					// Inserisco il parametro di forzatura dato dagli atti
					Map<String, String> extraParams = new LinkedHashMap<String, String>();
					extraParams.put("HSM_TIPO_FIRMA_FORZATO", hsmTipoFirmaAtti);
					dataSource.setExtraparams(extraParams);	
					// Chiamata del metodo del datasource
					FirmaHsmBean result = dataSource.firmaHsmMultiplaFileGeneratodaModello(lFirmaHsmBean);							
					if (result.getFileFirmati() != null && !result.getFileFirmati().isEmpty()) {
						FileDaFirmare fileFirmato = result.getFileFirmati().get(0);
						modelloBean.setNomeFile(fileFirmato.getNomeFile());
						if (estensioneforzata && fileFirmato.getNomeFile() != null && fileFirmato.getNomeFile().length() > 4) {
							modelloBean.setNomeFile(fileFirmato.getNomeFile().substring(0, fileFirmato.getNomeFile().length() - 4));
						}
						modelloBean.setUriFileGenerato(fileFirmato.getUri());
						modelloBean.setInfoFileGenerato(fileFirmato.getInfoFile());
					} else {
						// Nessun file firmato, in questo caso è stato deciso di retituire il file non firmato
						if(StringUtils.isNotBlank(result.getErrorMessage())) {
							mLogger.error("Errore nella firma del file generato da modello: " + result.getErrorMessage());
						} else {
							mLogger.error("Errore nella firma del file generato da modello");
						}
//						throw new StoreException("Errore nella firma file");
					}
				}
			}
		}			
		return bean;
	}	
	
	public String recuperaEstremiUd(String idUd, String categoria, String sigla) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		if(StringUtils.isNotBlank(idUd)) {
		
			DmpkUtilityGetestremiregnumud_jBean lDmpkUtilityGetestremiregnumud_jInput = new DmpkUtilityGetestremiregnumud_jBean();
			lDmpkUtilityGetestremiregnumud_jInput.setIdudin(new BigDecimal(idUd));
			lDmpkUtilityGetestremiregnumud_jInput.setCodcategoriaregio(categoria);
			lDmpkUtilityGetestremiregnumud_jInput.setSiglaregio(sigla);
		
			DmpkUtilityGetestremiregnumud_j lDmpkUtilityGetestremiregnumud_j = new DmpkUtilityGetestremiregnumud_j();
			
			SchemaBean lSchemaBean = new SchemaBean();
			lSchemaBean.setSchema(loginBean.getSchema());
		
			StoreResultBean<DmpkUtilityGetestremiregnumud_jBean> lDmpkUtilityGetestremiregnumud_jOutput = lDmpkUtilityGetestremiregnumud_j.execute(getLocale(), lSchemaBean, lDmpkUtilityGetestremiregnumud_jInput);
			if (lDmpkUtilityGetestremiregnumud_jOutput.isInError()) {
				throw new StoreException(lDmpkUtilityGetestremiregnumud_jOutput);
			}
			
			String estremiUd = "";
			if(StringUtils.isNotBlank(lDmpkUtilityGetestremiregnumud_jOutput.getResultBean().getCodcategoriaregio()) && "PG".equals(lDmpkUtilityGetestremiregnumud_jOutput.getResultBean().getCodcategoriaregio())) {
				estremiUd += "Prot. "; 
			} else if(StringUtils.isNotBlank(lDmpkUtilityGetestremiregnumud_jOutput.getResultBean().getSiglaregio())) {
				estremiUd += lDmpkUtilityGetestremiregnumud_jOutput.getResultBean().getSiglaregio() + " "; 
			}
			estremiUd += new SimpleDateFormat(FMT_STD_DATA).format(lDmpkUtilityGetestremiregnumud_jOutput.getResultBean().getTsregout()) + "." + lDmpkUtilityGetestremiregnumud_jOutput.getResultBean().getNumregout();		
			return estremiUd; 
		}
		
		return null;
	}
	
	public String getDatiXGenDaModello(ArchivioBean bean, String nomeModello, String idUd, Boolean flgVersOmissis) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		if(StringUtils.isNotBlank(idUd)) {
			lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(bean.getIdUdFolder() + "+" + idUd);
			lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("F+U");
		} else {
			lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(bean.getIdUdFolder());
			lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("F");
		}
		lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
		
		// Creo gli attributi addizionali
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lDmpkModelliDocGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean,
				lDmpkModelliDocGetdatixgendamodelloInput);
		if (lDmpkModelliDocGetdatixgendamodelloOutput.isInError()) {
			throw new StoreException(lDmpkModelliDocGetdatixgendamodelloOutput);
		}
		
		return lDmpkModelliDocGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();				
	}
	
	public CompilaModelloAttivitaBean rollbackNumerazioneDefAtti(CompilaModelloAttivitaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkCore2RollbacknumerazioneudBean input = new DmpkCore2RollbacknumerazioneudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);

		input.setIdudin(new BigDecimal(bean.getIdUd()));
		
		DmpkCore2Rollbacknumerazioneud store = new DmpkCore2Rollbacknumerazioneud();
		StoreResultBean<DmpkCore2RollbacknumerazioneudBean> output = store.execute(getLocale(), loginBean,input);

		if (output.isInError()) {
			bean.setEsitoRollbackNumDefAtti("KO");
			mLogger.error("Errore rollback numerazione documento con idUd " + bean.getIdUd() + ": " + output.getDefaultMessage());
//			throw new StoreException(output);
		} else {			
			bean.setEsitoRollbackNumDefAtti("OK");
		}
		
		return bean;
	}

	@Override
	public TaskFileDaFirmareBean call(ArchivioBean pInBean)
			throws Exception {
		return null;
	}

}
