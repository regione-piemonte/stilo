package it.eng.utility.ocr;

import it.eng.utility.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import net.sourceforge.tess4j.CustomTesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.vietocr.PdfUtilities;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.PdfReader;

//import com.lowagie.text.pdf.PdfReader;

public class OCRApi {

	private static Logger aLogger = Logger.getLogger(OCRApi.class.getName());	

	public OCRApi() { 

	}

	public InputStream getText(File file) throws Exception {
		CustomTesseract tesseract = CustomTesseract.getInstance();
		tesseract.setLanguage("ita");	
		String ocrText = "";
		aLogger.debug("Inizio getText del file " + file.getAbsolutePath() + "");

		String estensioneFile = FilenameUtils.getExtension(file.getName());
		aLogger.debug("estensioneFile " + estensioneFile + "");

		if (estensioneFile.toLowerCase().equalsIgnoreCase("pdf")) {
			aLogger.debug("Il file e' un pdf lo elaboro suddividendolo nelle sue pagine ");
			PdfReader reader = null;
			try {    		     
				reader = new PdfReader(file.getAbsolutePath());
				int n = reader.getNumberOfPages();
				aLogger.debug("Number of pages : " + n);

				List<File> files = PdfUtilities.splitPdf( file );
				aLogger.debug("files delle singole pagine: " + files);
				if(files!=null ) {
					int i = 0;      		            
					while ( i < n ) {
						aLogger.debug(">>> OCR OF PAGE  " + i + "/" + n + " <<<");
						ocrText += "\n" + tesseract.doOCR(files.get(i));    
						FileUtil.deleteFile(files.get(i));
						i++;
					}
				} else {
					ocrText = tesseract.doOCR(file);
				}
			}  catch (Exception e) {
				aLogger.error("getText: " + e.getMessage(), e);
				throw e;
			}  finally {
				if(reader!=null){
					reader.close();
				}
			}  		            			
		} else {    			    	    	
			aLogger.debug("Il filenon e' un pdf lo elaboro direttamente ");
			ocrText = tesseract.doOCR(file);
		}	    	
		aLogger.debug("Tutto OK");
		System.out.println(ocrText);
		return IOUtils.toInputStream(ocrText);
	}

	public static void main(String[] args) throws Throwable {
		//File file = new File("C:/Users/TESAURO/Downloads/Modulo_SEPA_0.pdf");
		File file = new File("C:/Users/TESAURO/Pictures/imgpsh_fullsize_anim.png");
		OCRApi ocr = new OCRApi();
		InputStream text = ocr.getText(file);
	}
}
