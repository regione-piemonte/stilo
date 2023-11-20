/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import it.eng.module.foutility.beans.CheckEditableFileBean;
import it.eng.module.foutility.beans.VerificaPdfBean;
import it.eng.utility.pdfUtility.bean.PdfBean;
import it.eng.utility.pdfUtility.editabili.PdfEditabiliUtil;

public class CheckPdfEditabili {

	public static final Logger log = LogManager.getLogger(CheckPdfEditabili.class);
	
	public static boolean isAttivaVerificaPdfEditable() {
		try {
			ApplicationContext context = FileoperationContextProvider.getApplicationContext();
			VerificaPdfBean formatoBean = (VerificaPdfBean) context.getBean("VerificaPdfBean");
			
			// VERIFICA FORMATO EDITABILE = ATTIVA
			if("true".equalsIgnoreCase(formatoBean.getAttivaVerificaEditabili())) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchBeanDefinitionException e) {
			//log.error(e);
			log.warn("Verifica pdf editabili non attiva, bean di configurazione non presente");
		} catch (Exception e1) {
			log.error(e1);
		} finally {
			
		}

		return false;
	}
	
	
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
