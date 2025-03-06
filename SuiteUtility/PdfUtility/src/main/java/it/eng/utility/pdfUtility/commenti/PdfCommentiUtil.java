package it.eng.utility.pdfUtility.commenti;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;

import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.pdfUtility.bean.PdfBean;

public class PdfCommentiUtil {

	public static final Logger logger = LogManager.getLogger(PdfCommentiUtil.class);
	
	public static PdfBean isPdfConCommenti(File file) {
		logger.debug("Verifico se il file " + file + " contiene commenti");
		PdfReader pdfReader = null;
		PdfBean pdfBean = new PdfBean();
		try {
			pdfReader = new PdfReader(file.getAbsolutePath());
			long t0 = System.currentTimeMillis();
			List<Integer> pagesWithComment = returnPagesWithComment( file );
			long t1 = System.currentTimeMillis();
			long timeOperation = t1 - t0;
			
			if(pagesWithComment.size()>0) {
				logger.debug("Il file " + file + " ha commenti su " + pagesWithComment.size() + " pagine ---   ha impiegato " + timeOperation + " millisecondi");
				pdfBean.setWithComment( true );
				pdfBean.setPagesWithComment(pagesWithComment);
			} else {
				logger.debug("Il file " + file + " NON ha commenti  ---   ha impiegato " + timeOperation + " millisecondi");
				pdfBean.setWithComment( false );
			}
		} catch (Exception e1) {
			logger.error(e1);
		} finally {
			if(pdfReader != null) {
				pdfReader.close();
			}
		}

		return pdfBean;
	}
	
	public static List<Integer> returnPagesWithComment(File file) {
		List<Integer> listPageWithComment = new ArrayList<>();
		PdfReader reader = null;
		try {
			reader = new PdfReader( file.getAbsolutePath() );
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				
				PdfDictionary page = reader.getPageN(i);
				PdfArray annotsArray = null;
				
				if(page.getAsArray(PdfName.ANNOTS)==null)
					continue;
				annotsArray = page.getAsArray(PdfName.ANNOTS);
				if(annotsArray != null && !annotsArray.isEmpty()) {
					ListIterator iter = annotsArray.listIterator(); 
					while (iter.hasNext()) {
						PdfDictionary annot = (PdfDictionary) PdfReader.getPdfObject((PdfObject) iter.next());
						
						PdfString content = (PdfString) PdfReader.getPdfObject(annot.get(PdfName.CONTENTS));
						if (content != null) {
							PdfString tagAutoCad = (PdfString) PdfReader.getPdfObject(annot.get(PdfName.T));
							if (tagAutoCad != null && StringUtils.isNotBlank(tagAutoCad.toString())) {
								if (!tagAutoCad.toString().toUpperCase().contains("AUTOCAD")) {
									listPageWithComment.add(i);
								}
							}else {
								listPageWithComment.add(i);
							}
							break;
						} else {
							if(PdfName.STAMP.equals(PdfReader.getPdfObject(annot.get(PdfName.SUBTYPE))) 
							|| PdfName.SQUARE.equals(PdfReader.getPdfObject(annot.get(PdfName.SUBTYPE)))
							|| PdfName.INK.equals(PdfReader.getPdfObject(annot.get(PdfName.SUBTYPE)))){
								listPageWithComment.add(i);
							}
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error("Errore durante il check dei commenti sulle pagine del pdf: " + e.getMessage(),e);
		} finally {
			if( reader!=null ){
				reader.close();
			}
		}
		
		return listPageWithComment;
	}
	
	
	/*
	 * Metodo che riscrive il pdf in input convertendo in immagine 
	 * le pagine con commenti
	 */
	public static File staticizzaFileConCommenti(File filePdf, List<Integer> listPagesToConvert) throws Exception {
		return PdfUtil.rewriteFile(filePdf, listPagesToConvert);
	}
	
	
}
