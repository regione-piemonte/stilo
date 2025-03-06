package it.eng.utility.pdfUtility.editabili;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.XfaForm;

import it.eng.utility.pdfUtility.bean.PdfBean;


public class PdfEditabiliUtil {

	public static final Logger logger = LogManager.getLogger(PdfEditabiliUtil.class);
	
	public static PdfBean isPdfEditable(File file) {
		logger.debug("Verifico se il file " + file + " e' editabile");
		PdfReader pdfReader = null;
		PdfBean pdfBean = new PdfBean();
		try {
			pdfReader = new PdfReader(file.getAbsolutePath());

			AcroFields fields = pdfReader.getAcroFields();
			
			List<String> listaNomiSignature = fields.getSignatureNames();

			if (fields != null && fields.getFields() != null && fields.getFields().size() > 0) {
				for (String key : fields.getFields().keySet()) {
					if (!listaNomiSignature.contains(key)) {
						pdfBean.setEditable(true);
					}
				}
			}

			if (checkEditableFileWithXfaForm(file.getPath())) {
				pdfBean.setEditable(true);
				pdfBean.setContainXForm(true);
			}

			return pdfBean;
		
		} catch (Exception e1) {
			logger.error(e1);
		} finally {
			if(pdfReader != null) {
				pdfReader.close();
			}
		}

		return pdfBean;
	}

	
	public static boolean checkEditableFileWithXfaForm(String pathFile) throws Exception {
		logger.debug("Verifico se il pdf contiene xfa Form");
		PdfReader reader = null;
		
		try {
			reader = new PdfReader(pathFile);
//			reader.unethicalreading = true;

			XfaForm xfaForm;

			AcroFields acroFileds = reader.getAcroFields();
			if (acroFileds != null) {
				xfaForm = acroFileds.getXfa();
				if (xfaForm != null && xfaForm.getDomDocument() != null) {
					logger.debug("Il pdf contiene xfa Form");
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("Errore nel metodo checkEditableFileWithXfaForm", e);
			throw new Exception("Errore durante la verifica degli xfa form contenuti nel documento: " + e.getMessage(),e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		logger.debug("Il pdf non contiene xfa Form");
		return false;
	}
	
	public static File staticizzaPdfEditabile(File file ) throws Exception {
		logger.debug("Tento di staticizzare il file " + file);
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
	
	public static String staticizzaPdfConXfaForm(String filePath) throws Exception {
		logger.debug("Staticizzo documento con xfa form");
		PDDocument pdDoc = null;
		
		try {
			File tempFlattenFile = File.createTempFile("tempFlatten", ".pdf");
			pdDoc = PDDocument.load(new File(new URI(filePath).getPath()));
			
			if (pdDoc.isEncrypted()) {
				pdDoc.setAllSecurityToBeRemoved(true);
			}
			
			PDDocumentCatalog pdCatalog = pdDoc.getDocumentCatalog();
			PDAcroForm acroForm = pdCatalog.getAcroForm();

			if (acroForm != null) {

				@SuppressWarnings("unchecked")
				List<PDField> fields = acroForm.getFields();
				for (PDField field : fields) {
					if (field.getFullyQualifiedName().equals("formfield1")) {
						field.setReadOnly(true);
					}
				}
				pdDoc.save(tempFlattenFile.getPath());
				
				return tempFlattenFile.getPath();
			}
		} catch (Exception e) {
			logger.error("Errore nel metodo staticizzaPdfConXfaForm", e);
			throw new Exception("Errore durante la staticizzazione del documento editabile con xfaform: " + e.getMessage(),e);
		} finally {
			if (pdDoc != null) {
				pdDoc.close();
			}
		}
		
		return null;
	}
	
}
