package it.eng.suiteutility.dynamiccodedetector.pdf;

import java.io.File;

import javax.activation.MimeType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetector;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorException;
import it.eng.suiteutility.dynamiccodedetector.ValidationInfos;
import it.eng.suiteutility.dynamiccodedetector.utils.CheckPdfCommenti;
import it.eng.suiteutility.dynamiccodedetector.utils.CheckPdfCommentiFileBean;

public class PDFCommentiDetector implements DynamicCodeDetector {

	private static Logger log = LogManager.getLogger(PDFCommentiDetector.class);

	public PDFCommentiDetector() {

		
	}

	public ValidationInfos detect(File file, MimeType mimeType) throws DynamicCodeDetectorException {
		log.info("File " + file);
		ValidationInfos validationInfos = new ValidationInfos();
		
		CheckPdfCommentiFileBean checkPdfCommenti = CheckPdfCommenti.isPdfConCommenti( file );
		if(checkPdfCommenti.getFlgContieneCommenti()!=null && checkPdfCommenti.getFlgContieneCommenti() ) {
			validationInfos.addError("Il PDF contiene commenti");
		}
		
		//throw new DynamicCodeDetectorException(e.getMessage(), e);
		
		return validationInfos;
	}

	

}
