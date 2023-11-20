/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.XfaForm;

import it.eng.module.foutility.beans.CheckEditableFileBean;
import it.eng.module.foutility.beans.VerificaPdfBean;

public class PdfEditabiliUtil {

	public static final Logger log = LogManager.getLogger(PdfEditabiliUtil.class);
	
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
	
	public static CheckEditableFileBean isPdfEditable(String file) {
		log.debug("Verifico se il file " + file + " e' editabile");
		PdfReader pdfReader = null;
		CheckEditableFileBean checkEditableFileBean = new CheckEditableFileBean();
		try {
			pdfReader = new PdfReader(file);
			checkEditableFileBean = checkEditableFile(pdfReader);
		} catch (Exception e1) {
			log.error(e1);
		} finally {
			if(pdfReader != null) {
				pdfReader.close();
			}
		}

		return checkEditableFileBean;
	}
	
	private static CheckEditableFileBean checkEditableFile(PdfReader reader) throws Exception {
		CheckEditableFileBean checkEditableFileBean = new CheckEditableFileBean();

		AcroFields fields = reader.getAcroFields();
		
		List<String> listaNomiSignature = fields.getSignatureNames();

		if (fields != null && fields.getFields() != null && fields.getFields().size() > 0) {
			for (String key : fields.getFields().keySet()) {
				if (!listaNomiSignature.contains(key)) {
					checkEditableFileBean.setFlgEditable(true);
				}
			}
		}

		if (checkEditableFileWithXfaForm(reader)) {
			checkEditableFileBean.setFlgEditable(true);
			checkEditableFileBean.setFlgContainsXfaForm(true);
		}

		return checkEditableFileBean;
	}

	private static boolean checkEditableFileWithXfaForm(PdfReader reader) throws Exception {
	
		XfaForm xfaForm;
		AcroFields acroFileds = reader.getAcroFields();
		if (acroFileds != null) {
			xfaForm = acroFileds.getXfa();
			if (xfaForm != null && xfaForm.getDomDocument() != null) {
				return true;
			}
		}

		return false;
	}
	
	public static File staticizzaPdf(File file ) throws Exception {
		log.debug("Tento di staticizzare il file " + file);
		File tempFlatteFile = File.createTempFile("tempFlatten", ".pdf", file.getParentFile());

		PdfReader reader = new PdfReader(file.getAbsolutePath());
		reader.unethicalreading = true;
		
		FileOutputStream fos = new FileOutputStream(tempFlatteFile);
		PdfStamper stamper = new PdfStamper(reader, fos);
		AcroFields form = stamper.getAcroFields();
		form.setGenerateAppearances(true);

		stamper.setFormFlattening(true);
		stamper.close();
		reader.close();
		fos.close();
		
		return tempFlatteFile;
	}
}
