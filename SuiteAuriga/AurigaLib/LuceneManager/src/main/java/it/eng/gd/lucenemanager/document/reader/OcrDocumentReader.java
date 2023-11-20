/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.document.AbstractDocumentReader;
import it.eng.gd.lucenemanager.exception.DocumentReaderException;
import it.eng.utility.FileUtil;
import it.eng.utility.ocr.OCRApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * reader che effettua l'OCR del file
 * 
 * @author jravagnan
 * 
 */
@Entity
public class OcrDocumentReader extends AbstractDocumentReader {

	static Logger aLogger = Logger.getLogger(OcrDocumentReader.class.getName());

	// estensione del file 'chiamante' l'OCR
	private String estensione;

	static Map<String, String> mappaEstensioni = new HashMap<String, String>();
	private static final String DOT=".";

	public OcrDocumentReader() {
		super();
		mappaEstensioni.put("image/tiff", "tiff");
		mappaEstensioni.put("image/jpeg", "jpeg");
		mappaEstensioni.put("image/gif", "gif");
		mappaEstensioni.put("image/png", "png");
	}

	public Reader getContent(File file) throws DocumentReaderException {
		InputStream from = null;
		InputStream inst = null;
		File totmp = null;
		try {
			from = new FileInputStream(file);
			aLogger.debug("#% inizio OCR reader");
			aLogger.debug("estensione " + estensione);
			totmp = File.createTempFile("tmpp_", DOT+estensione);
			//IOUtils.copy(inst, fost);
			FileUtil.writeStreamToFile(from, totmp);
			StringReader reader = null;
			OCRApi ocrapi = new OCRApi();
			aLogger.debug("#% provo ad estrarre il contenuto testuale del file: " + totmp.getName());
			inst = ocrapi.getText(totmp);
			reader = new StringReader(IOUtils.toString(inst));
			// ritorno il reader
			return reader;
		} catch (Exception ex1) {
			aLogger.error(ex1.getMessage(), ex1);
			throw new DocumentReaderException("Errore nell'estrazione del testo dal documento: " + ex1.getMessage(), ex1);
		} catch (Throwable ex1) {
			aLogger.error(ex1.getMessage(), ex1);
			throw new DocumentReaderException("Errore nell'estrazione del testo dal documento: " + ex1.getMessage());
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (Exception e) {
				}
			if (inst != null)
				try {
					inst.close();
				} catch (Exception e) {
				}
			if (totmp != null)
				try {
					totmp.delete();
				} catch (Exception e) {
				}
		}
	}

	public String getEstensione() {
		return estensione;
	}

	public void setEstensione(String estensione) {
		this.estensione = estensione;
	}

	public String getEstensioneByFormato(String formato){
		return mappaEstensioni.get(formato);
	}
	
	@Override
	public FileVO[] getContents(File file, String mimetype) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
