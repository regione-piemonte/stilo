/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.persistence.Entity;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.document.AbstractDocumentReader;

@Entity
public class WordDocumentReader extends AbstractDocumentReader {

	static Logger aLogger = Logger.getLogger(WordDocumentReader.class.getName());

	public WordDocumentReader() {
		super();
	}

	public Reader getContent(File file) throws Exception {
		Writer wr = null;

		try {
			HWPFDocument doc = new HWPFDocument(new FileInputStream(file));
			// org.apache.poi.hwpf.extractor.WordExtractor te = new org.apache.poi.hwpf.extractor.WordExtractor(new FileInputStream(file));
			org.apache.poi.hwpf.extractor.WordExtractor te = new org.apache.poi.hwpf.extractor.WordExtractor(doc);
			String str = te.getText();

			return new StringReader(str); // FSTemporaryFileReader(new File(filename));
		} catch (OfficeXmlFileException e) {
			// si tratta di un docx e devo convertirlo come tale

			// XWPFDocument doc = new XWPFDocument(new FileInputStream(file));
			// XWPFWordExtractor ex = new XWPFWordExtractor(doc);
			// String str = ex.getText();
			String str = getContentDocx(file);
			return new StringReader(str);

		} catch (IOException e) {
			aLogger.error(e.getMessage(), e);
			if (e.getMessage() != null && e.getMessage().equalsIgnoreCase("The text piece table is corrupted")) {
				// riconverto l'eccezione in modo che il documento sia processato dal convertitore in pdf
				throw new IllegalStateException("The text piece table is corrupted");
			} else
				throw e;
		} catch (Exception e) {
			aLogger.error(e.getMessage(), e);
			throw e;// new DocumentReaderException( "Errore nell'estrazione del testo dal documento Microsoft Word", e);
		} finally {
			if (wr != null)
				try {
					wr.close();
				} catch (Exception e) {
				}
		}
	}

	private String getContentDocx(File file) throws Exception {
		List<String> lines = new ArrayList<String>();
		ZipFile docxFile = new ZipFile(file);
		try {
			ZipEntry documentXML = docxFile.getEntry("word/document.xml");
			InputStream documentXMLIS = docxFile.getInputStream(documentXML);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			Document doc = dbf.newDocumentBuilder().parse(documentXMLIS);
			Element tElement = doc.getDocumentElement();
			NodeList n = (NodeList) tElement.getElementsByTagName("w:p");
			for (int j = 0; j < n.getLength(); j++) {
				Node child = n.item(j);
				String line = child.getTextContent();
				if (line != null && !line.trim().isEmpty()) {
					lines.add(line.trim());
				}
			}
			String total = "";
			for (String line : lines) {
				total = total + line + " ";
			}
			return total;
		} finally {
			docxFile.close();
		}
	}

	@Override
	public FileVO[] getContents(File file, String mimetype) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/****************************************************************
	 * MAIN DI TEST
	 */
	// public static void main(String argv[]) throws Exception {
	// // File myFile = new File( "C:\\Users\\Ravagnan\\Desktop\\fileTest\\PIGD_Architettura_HW_sw.docx" );
	// File myFile = new File("D:\\doc lavoro\\BEST PRACTICE PER UN MIGLIORE UTILIZZO DEL LOG.doc");
	//
	//
	// WordDocumentReader re = new WordDocumentReader();
	// Reader reader = re.getContent(myFile);
	//
	// System.out.println(IOUtils.toString(reader));
	//
	// }

}
