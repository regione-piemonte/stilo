/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import org.apache.axis.types.URI;
import org.apache.log4j.Logger;

import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.pdfUtility.commenti.PdfCommentiUtil;
import it.eng.utility.pdfUtility.editabili.PdfEditabiliUtil;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class ManagePdfUtil {
	
	static Logger logger = Logger.getLogger(ManagePdfUtil.class);
		
	public static IdFileBean manageProtectedPdf(String pathFile, String mimetypeInput, String displayFileName, boolean fromScanner, Date dataRif){
		IdFileBean idFileBean = new IdFileBean();
		final StorageService storageService = StorageImplementation.getStorage();
		MimeTypeFirmaBean mimeTypeFirmaBean;
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();

		try {		
			if("application/pdf".equalsIgnoreCase(mimetypeInput)) {
				if(InfoFileUtility.checkProtectedFile(pathFile)) {
					logger.debug("Il pdf è protetto");
					logger.debug("--- GESTIONE PDF PROTETTI ---");
					String filePath = new URI(pathFile).getPath();
					File fileToConvert = new File(filePath);
					
					File fileWithPermissions = PdfUtil.rewriteFile(fileToConvert);
					String pathFileStorato = storageService.storeStream(new FileInputStream(fileWithPermissions));
					String realFilePath = storageService.getRealFile(pathFileStorato).toURI().toString();
					logger.debug("Il file protetto è stato copiato con tutti i permessi al seguente uri: " + realFilePath);
					logger.debug("Ricalcolo mimetype con fileop");
					
					//ricalcolo il mimetype con fileop
					mimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(realFilePath, displayFileName, fromScanner, dataRif);
					
					idFileBean.setInfoFile(mimeTypeFirmaBean);
					idFileBean.setUri(pathFileStorato);
				}
			}

			return idFileBean;
		} catch (Exception e) {
			logger.error("Errore durante la gestione dei pdf protetti: " + e.getMessage(), e);
		}
		
		return idFileBean;
	}

	
	public static IdFileBean manageEditablePdf(String fileUrl, String displayFileName, boolean fromScanner, Date dataRif, MimeTypeFirmaBean lMimeTypeFirmaBean) throws Exception {
		IdFileBean idFileBean = new IdFileBean();
		MimeTypeFirmaBean mimeTypeFirmaBean = lMimeTypeFirmaBean;
		String fileStaticizzatoUrl;
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		final StorageService storageService = StorageImplementation.getStorage();
		
		
		try {
			if(lMimeTypeFirmaBean.isPdfEditabile()) {
				logger.debug("Il pdf è editabile");
				logger.debug("--- GESTIONE PDF EDITABILI ---");
				
				//controllo se il file contiene xfa Form
				if(!PdfEditabiliUtil.checkEditableFileWithXfaForm(fileUrl)) {
					//staticizzo il file
					String pathFile = new URI(fileUrl).getPath();
					File fileToConvert = new File(pathFile);
					
					fileStaticizzatoUrl = PdfEditabiliUtil.staticizzaPdfEditabile(fileToConvert).getPath();
					String pathFileStorato = storageService.storeStream(new FileInputStream(new File(fileStaticizzatoUrl)));
					String realFilePath = storageService.getRealFile(pathFileStorato).toURI().toString();
					logger.debug("File staticizzato è stato salvato al seguente uri: " + realFilePath);
					logger.debug("Ricalcolo mimetype con fileop");
					
					//ricalcolo il mimetype con fileop
					mimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(realFilePath, displayFileName, fromScanner, dataRif);
					mimeTypeFirmaBean.setPdfEditabile(true);
					
					idFileBean.setInfoFile(mimeTypeFirmaBean);
					idFileBean.setUri(pathFileStorato);
				}else {
					//staticizzo il file con xfaform
					fileStaticizzatoUrl = PdfEditabiliUtil.staticizzaPdfConXfaForm(fileUrl);
					if(fileStaticizzatoUrl!=null) {
						String pathFileStorato = storageService.storeStream(new FileInputStream(new File(fileStaticizzatoUrl)));
						String realFilePath = storageService.getRealFile(pathFileStorato).toURI().toString();
						logger.debug("File staticizzato con xfa form è stato salvato al seguente uri: " + pathFileStorato);
						logger.debug("Ricalcolo mimetype con fileop");
						
						//ricalcolo il mimetype con fileop
						mimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(realFilePath, displayFileName, fromScanner, dataRif);
					
						idFileBean.setInfoFile(mimeTypeFirmaBean);
						idFileBean.setUri(pathFileStorato);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Errore durante la gestione dei pdf editabili: " + e.getMessage(), e);
		}
		
		return idFileBean;
		
	}
	
	public static IdFileBean managePdfCorrotti(String fileUrl, String displayFileName, boolean fromScanner, Date dataRif, MimeTypeFirmaBean lMimeTypeFirmaBean) throws Exception {
		IdFileBean idFileBean = new IdFileBean();
		MimeTypeFirmaBean mimeTypeFirmaBean = lMimeTypeFirmaBean;
		File fileConvertitoInImmagine;
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		final StorageService storageService = StorageImplementation.getStorage();

		logger.debug("Il pdf non puo essere letto da itext in quanto corrotto, lo converto in immagine");
		logger.debug("--- GESTIONE PDF CORROTTI ---");

		// converto il file in immagine
		try {
			if("application/pdf".equalsIgnoreCase(lMimeTypeFirmaBean.getMimetype())) {
				String pathFile = new URI(fileUrl).getPath();
				File fileToConvert = new File(pathFile);
				
				fileConvertitoInImmagine = PdfUtil.fromPdfToPdfImage(fileToConvert);
	
				String pathFileStorato = storageService.storeStream(new FileInputStream(fileConvertitoInImmagine));
				String realFilePath = storageService.getRealFile(pathFileStorato).toURI().toString();
				logger.debug("Il File convertito in immagine è stato salvato al seguente uri: " + realFilePath);
				logger.debug("Ricalcolo mimetype con fileop");
	
				// ricalcolo il mimetype con fileop
				mimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(realFilePath, displayFileName, fromScanner, dataRif);
	
				idFileBean.setInfoFile(mimeTypeFirmaBean);
				idFileBean.setUri(pathFileStorato);
			}

		} catch (Exception e) {
			logger.error("E' avvenuto un errore durante la converione in immagine del pdf corrotto: " + e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}

		return idFileBean;

	}


	public static IdFileBean managePdfConCommenti(String fileUrl, String displayFileName, boolean fromScanner, Date dataRif, MimeTypeFirmaBean lMimeTypeFirmaBean) {

		IdFileBean idFileBean = new IdFileBean();
		MimeTypeFirmaBean mimeTypeFirmaBean = lMimeTypeFirmaBean;
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		final StorageService storageService = StorageImplementation.getStorage();		
		
		try {
			if(lMimeTypeFirmaBean.isPdfConCommenti()) {
				logger.debug("Il pdf ha commenti");
				logger.debug("--- GESTIONE PDF CON COMMENTI ---");
				
				String pathFile = new URI(fileUrl).getPath();
				
				//controllo quali pagine hanno i commenti
				List<Integer> listaPagineConCommenti = PdfCommentiUtil.returnPagesWithComment(new File(pathFile));
				File fileConvertito = PdfCommentiUtil.staticizzaFileConCommenti(new File(pathFile), listaPagineConCommenti);
				
				String pathFileStorato = storageService.storeStream(new FileInputStream(fileConvertito));
				String realFilePath = storageService.getRealFile(pathFileStorato).toURI().toString();
				
				logger.debug("File convertito è stato salvato al seguente uri: " + realFilePath);
				logger.debug("Ricalcolo mimetype con fileop");
				
				//ricalcolo il mimetype con fileop
				mimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(realFilePath, displayFileName, fromScanner, dataRif);
				mimeTypeFirmaBean.setPdfConCommenti(true);
				
				idFileBean.setInfoFile(mimeTypeFirmaBean);
				idFileBean.setUri(pathFileStorato);
				
			}
		} catch (Exception e) {
			logger.error("Errore durante la gestione dei pdf con commenti: " + e.getMessage(), e);
		}
		
		return idFileBean;
		
	
	}


}
