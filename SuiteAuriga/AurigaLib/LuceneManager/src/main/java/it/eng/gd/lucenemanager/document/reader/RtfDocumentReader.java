/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import javax.persistence.Entity;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.document.AbstractDocumentReader;
import it.eng.gd.lucenemanager.exception.DocumentReaderException;

@Entity
public class RtfDocumentReader extends AbstractDocumentReader {

	static Logger aLogger = Logger.getLogger(RtfDocumentReader.class);

	public RtfDocumentReader() {
		super();
	}

	public Reader getContent(File file) throws DocumentReaderException {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(FileUtils.openInputStream(file)));
			RTFEditorKit kit = new RTFEditorKit();
			Document doc = kit.createDefaultDocument();
			kit.read(input, doc, 0);

			String str = doc.getText(0, doc.getLength());

			return new StringReader(str);

		} catch (IOException ex) {
			aLogger.error(ex.getMessage(), ex);
			throw new DocumentReaderException("Errore nella lettura delle righe dal documento Rtf", ex);
		} catch (Exception ex1) {
			aLogger.error(ex1.getMessage(), ex1);
			throw new DocumentReaderException("Errore nell'estrazione del testo dal documento Rtf", ex1);
		}

	}

	@Override
	public FileVO[] getContents(File file, String mimetype) throws Exception {
		return null;
	}

}
