/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.gd.lucenemanager.LuceneSpringAppContext;
import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.document.AbstractDocumentReader;
import it.eng.gd.lucenemanager.document.reader.util.ExtractTextAsWordlist;
import it.eng.gd.lucenemanager.exception.DocumentReaderException;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import javax.persistence.Entity;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

@Entity
public class PdfDocumentReader extends AbstractDocumentReader {

	private static final String FORMAT_SUFFIX = "pdf";
	
	private Logger log = Logger.getLogger(PdfDocumentReader.class);


	public Reader getContent(File file) throws DocumentReaderException {
		InputStream from = null;
		log.debug("#% chiamata pdfReader per  file: "+file.getName());
		try {
			// utilizzo di jpedal per la conversione a testo
			ExtractTextAsWordlist extractor = (ExtractTextAsWordlist) LuceneSpringAppContext.getContext().getBean("pdfExtractor");
			String text = extractor.extractTextFromPdf(file.getAbsolutePath());
			if (isOcrOperation() && StringUtils.isBlank(text)) {
				log.debug("#% incomincio l'elaborazione OCR");
				OcrDocumentReader reader = new OcrDocumentReader();
				reader.setEstensione(FORMAT_SUFFIX);
				log.debug("#% chiamo il reader relativo all'OCR");
				Reader r = reader.getContent(file);
				text = IOUtils.toString(r);
			}
			// creo un nuovo file temporaneo
			//File out = File.createTempFile("tmp_", FORMAT_SUFFIX);
			//FileUtils.writeStringToFile(out, text);
			// ritorno il reader (di tipo temporaneo)
			log.debug("#% ho estratto il seguente testo dal file: "+text);
			StringReader stringReader = new StringReader(text);
			return stringReader;
		} catch (Exception e) {
			log.error("ERRORE durante l'estrazione del testo dal documento pdf: " + e.getMessage(), e);
			throw new DocumentReaderException("Errore durante l'estrazione del testo dal documento pdf: " + e.getMessage(), e);
		} catch (Throwable t) {
			log.error("ERRORE durante l'estrazione del testo dal documento pdf: " + t.getMessage(), t);
			throw new DocumentReaderException("Errore durante l'estrazione del testo dal documento pdf: " + t.getMessage());
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (Exception e) {
				}
		}
	}


	@Override
	public FileVO[] getContents(File file, String mimetype) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		//File file = new File("C:/Users/TESAURO/Downloads/2._CTR0001047_LABORAEE_v0_Allegati_0-1-3 (2).pdf");
		File file = new File("C:/Users/TESAURO/Downloads/Modulo_SEPA_0.pdf");
		ExtractTextAsWordlist extractor = new ExtractTextAsWordlist();
		extractor.setTimeout("1000");
		
		try {
			String text = extractor.extractTextFromPdf(file.getAbsolutePath());
			System.out.println(text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
