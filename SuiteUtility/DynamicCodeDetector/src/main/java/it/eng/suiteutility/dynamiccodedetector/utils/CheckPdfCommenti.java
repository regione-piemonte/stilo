package it.eng.suiteutility.dynamiccodedetector.utils;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.pdfUtility.bean.PdfBean;
import it.eng.utility.pdfUtility.commenti.PdfCommentiUtil;

public class CheckPdfCommenti {

	public static final Logger logger = LogManager.getLogger(CheckPdfCommenti.class);
	
	public static CheckPdfCommentiFileBean isPdfConCommenti(File file) {
		logger.debug("Verifico se il file " + file + " contiene commenti");
		CheckPdfCommentiFileBean checkPdfCommentiFileBean = new CheckPdfCommentiFileBean();
		try {
			PdfBean pdfbean = PdfCommentiUtil.isPdfConCommenti( file );
			
			if( pdfbean!=null ){
				if( pdfbean.getWithComment()!=null )
					checkPdfCommentiFileBean.setFlgContieneCommenti( pdfbean.getWithComment() );
				if( pdfbean.getPagesWithComment()!=null )
					checkPdfCommentiFileBean.setPageWithCommentBox( pdfbean.getPagesWithComment() );
			}
		} catch (Exception e1) {
			logger.error(e1);
		} finally {
			
		}

		return checkPdfCommentiFileBean;
	}
	
}
