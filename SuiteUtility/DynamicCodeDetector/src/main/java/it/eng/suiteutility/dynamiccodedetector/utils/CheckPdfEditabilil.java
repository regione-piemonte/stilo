package it.eng.suiteutility.dynamiccodedetector.utils;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.pdfUtility.bean.PdfBean;
import it.eng.utility.pdfUtility.editabili.PdfEditabiliUtil;

public class CheckPdfEditabilil {

	public static final Logger log = LogManager.getLogger(CheckPdfEditabilil.class);
	
	public static CheckEditableFileBean isPdfEditable(File file) {
		log.debug("Verifico se il file " + file + " e' editabile");
		CheckEditableFileBean checkEditableFileBean = new CheckEditableFileBean();
		try {
			PdfBean pdfBean = PdfEditabiliUtil.isPdfEditable( file );
			if( pdfBean.getEditable()!=null  ){
				checkEditableFileBean.setFlgEditable( pdfBean.getEditable() );
			} 
			if( pdfBean.getContainXForm()!=null ){
				checkEditableFileBean.setFlgContainsXfaForm( pdfBean.getContainXForm() );
			} 
		} catch (Exception e1) {
			log.error(e1);
		} finally {
			
		}

		return checkEditableFileBean;
	}
}
