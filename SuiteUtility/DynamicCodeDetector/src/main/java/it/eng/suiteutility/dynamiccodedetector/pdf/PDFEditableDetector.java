package it.eng.suiteutility.dynamiccodedetector.pdf;

import java.io.File;

import javax.activation.MimeType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetector;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorException;
import it.eng.suiteutility.dynamiccodedetector.ValidationInfos;

public class PDFEditableDetector implements DynamicCodeDetector {

	private static Logger log = LogManager.getLogger(PDFEditableDetector.class);

	public PDFEditableDetector() {

		
	}

	public ValidationInfos detect(File file, MimeType mimeType) throws DynamicCodeDetectorException {
		log.info("File " + file);
		ValidationInfos validationInfos = new ValidationInfos();
		
//		CheckEditableFileBean checkPdfEditabili = PdfEditabiliUtil.isPdfEditable( file.getPath() );
//		if(checkPdfEditabili.getFlgEditable() && checkPdfEditabili.getFlgContainsXfaForm() ) {
//			validationInfos.addError("Il PDF e' editabile");
//		}
		
		//throw new DynamicCodeDetectorException(e.getMessage(), e);
		
		return validationInfos;
	}

	

}
