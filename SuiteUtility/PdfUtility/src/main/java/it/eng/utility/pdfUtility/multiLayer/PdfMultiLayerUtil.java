package it.eng.utility.pdfUtility.multiLayer;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.pdfUtility.bean.PdfBean;
import it.eng.utility.pdfUtility.bean.PdfConstant;

public class PdfMultiLayerUtil {

	public static final Logger logger = LogManager.getLogger(PdfMultiLayerUtil.class);
	
	
	public static File manageMultiLayerPdf(File pdfFile, String mimeTypeFile) {
		
		File fileWithoutLayer = pdfFile;
		try {
			PdfBean pdfBean = checkPdfMultiLayer(pdfFile);
			if (PdfConstant.pdfMimeType.equalsIgnoreCase(mimeTypeFile) && 
					(pdfBean.getMultiLayer()!=null && pdfBean.getMultiLayer() )) {
				logger.debug("Il pdf e' multiLayer");
				logger.debug("--- GESTIONE PDF MULTILAYER ---");
				fileWithoutLayer = PdfUtil.rewriteFile(pdfFile);
				
			}
		} catch (Exception e) {
			logger.error("Errore durante la gestione dei pdf multiLayer: " + e.getMessage(), e);
		}
		
		return fileWithoutLayer;
		
	}
	
	public static PdfBean checkPdfMultiLayer(File pdfFile) {
		PdfReader reader = null;
		PdfBean pdfBean = new PdfBean();
		try {
			reader = new PdfReader( pdfFile.getAbsolutePath() );
			PdfDictionary catalog = reader.getCatalog();
			
			if(catalog.get(PdfName.OCPROPERTIES)!=null){
				pdfBean.setMultiLayer(true);
			}else {
				pdfBean.setMultiLayer(false);
			}
		}catch(Exception ex) {
			logger.error("Errore durante il controllo dei pdf MultiLayer: " + ex.getMessage());
		} finally {
			if( reader!=null)
				reader.close();
		}
		return pdfBean;
	}
}
