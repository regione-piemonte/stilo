package it.eng.suiteutility.dynamiccodedetector.pdf;

import java.io.File;

import javax.activation.MimeType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetector;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorException;
import it.eng.suiteutility.dynamiccodedetector.ValidationInfos;
import it.eng.suiteutility.dynamiccodedetector.utils.CheckEditableFileBean;
import it.eng.suiteutility.dynamiccodedetector.utils.CheckPdfCommenti;
import it.eng.suiteutility.dynamiccodedetector.utils.CheckPdfCommentiFileBean;

public class PDFDetector implements DynamicCodeDetector {

	private static Logger log = LogManager.getLogger(PDFDetector.class);

	public PDFDetector() {

		
	}

	public ValidationInfos detect(File file, MimeType mimeType) throws DynamicCodeDetectorException {
		log.info("File " + file);
		ValidationInfos validationInfos = new ValidationInfos();
		
		CheckPdfCommentiFileBean checkPdfCommenti = CheckPdfCommenti.isPdfConCommenti( file );
		if(checkPdfCommenti.getFlgContieneCommenti()!=null && checkPdfCommenti.getFlgContieneCommenti() ) {
			validationInfos.addError("Il PDF contiene commenti");
		}
		
//		CheckEditableFileBean checkPdfEditabili = PdfEditabiliUtil.isPdfEditable( file.getPath() );
//		if(checkPdfEditabili.getFlgEditable() && checkPdfEditabili.getFlgContainsXfaForm() ) {
//			validationInfos.addError("Il PDF e' editabile");
//		}
		
		//throw new DynamicCodeDetectorException(e.getMessage(), e);
		
		return validationInfos;
	}

	

}
