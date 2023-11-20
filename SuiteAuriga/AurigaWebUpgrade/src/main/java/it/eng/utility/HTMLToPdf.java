/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.exception.StoreException;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.servlet.fileExtractor.FileToExtractBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

// INPUT  
// htmlIn : E' il file html da convertire

// OUTPUT 
// pdfFileOut  : E' il file pdf convertito

public class HTMLToPdf {
	
	private static Logger mLogger = Logger.getLogger(HTMLToPdf.class);

	public static FileToExtractBean convert(FileToExtractBean fileToExtractHtmlIn)  throws Exception {
		
		Document document = new Document();
		PdfWriter writer;
		FileOutputStream os = null;
		InputStream is = null;
		try {		
			// Estraggo dallo storage il file
			File fileHtmlIn = StorageImplementation.getStorage().extractFile(fileToExtractHtmlIn.getUri());

			// Apro lo stream di out nella cartella temp
			File pdfFileOut = File.createTempFile("temp_file_pdf_", ".pdf");
			os = new FileOutputStream(pdfFileOut);
			writer = PdfWriter.getInstance(document,os);
			document.open();
			is = new FileInputStream(fileHtmlIn);
	        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
	        document.close();
	        
	        mLogger.debug("Creato il file " + pdfFileOut);
	        
			// Lo copio nello storage
			FileToExtractBean lFileToExtractBean = new FileToExtractBean();
			String displayFilename  = fileToExtractHtmlIn.getDisplayFilename();

			lFileToExtractBean.setDisplayFileName(displayFilename);
			lFileToExtractBean.setUri(StorageImplementation.getStorage().storeStream(new FileInputStream(pdfFileOut)));
			FileUtils.forceDelete(pdfFileOut);
			lFileToExtractBean.setRemoteUri(false);
			lFileToExtractBean.setSbustato(false);	

			return lFileToExtractBean;
			
		} catch (FileNotFoundException e) {
			mLogger.warn(e);
			throw new StoreException(e.getMessage());	
		} catch (DocumentException e) {
			mLogger.warn(e);
			throw new StoreException(e.getMessage());	
		} catch (IOException e) {
			mLogger.warn(e);
			throw new StoreException(e.getMessage());	
		} catch (Exception e) {
			mLogger.warn(e);
			throw new StoreException(e.getMessage());	
		} finally {
			if(is != null) {
				try {
					is.close(); 
				} catch (Exception e) {}
			}
			if(os != null) {
				try {
				    os.close();
				} catch (Exception e) {}
			}
		}
    }
}