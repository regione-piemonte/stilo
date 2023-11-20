/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.persistence.Entity;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;

import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.document.AbstractDocumentReader;
import it.eng.gd.lucenemanager.exception.DocumentReaderException;

@Entity
public class OpenOfficeDocumentReader extends AbstractDocumentReader {

	static Logger aLogger = Logger.getLogger(OpenOfficeDocumentReader.class);

	public OpenOfficeDocumentReader() {
		super();
	}

	public Reader getContent(File file) throws DocumentReaderException {
		Writer wr = null;
		String fileName = null;
		ZipFile zipFile = null;
		StringWriter out = null;
		try {

			// serializzo l'input stream su un file temporaneo
			out = new StringWriter();

			// Unzip the openOffice Document
			zipFile = new ZipFile(file);
			Enumeration<?> entries = zipFile.entries();
			ZipEntry entry = null;

			while (entries.hasMoreElements()) {
				entry = (ZipEntry) entries.nextElement();

				if (entry.getName().equals("content.xml")) {

					SAXBuilder sax = new SAXBuilder();
					Document doc = sax.build(zipFile.getInputStream(entry));
					Element rootElement = doc.getRootElement();
					processElement(rootElement, out);
					break;
				}
			}
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
				}

			return new StringReader(out.toString());
		} catch (Exception e) {
			aLogger.error(e.getMessage(), e);
			throw new DocumentReaderException("Errore nell'estrazione del testo dal documento OpenOffice", e);
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
				}
			if (wr != null)
				try {
					wr.close();
				} catch (Exception e) {
				}
			if (zipFile != null)
				try {
					zipFile.close();
				} catch (Exception e) {
				}
			try {
				new File(fileName).delete();
			} catch (Exception e) {
			}
		}

	}

	// Process text elements recursively
	public void processElement(Object o, Writer fw) throws Exception {

		if (o instanceof Element) {

			Element e = (Element) o;
			String elementName = e.getQualifiedName();

			if (elementName.startsWith("text")) {

				if (elementName.equals("text:tab")) // add tab for text:tab
					fw.write("\t");
				else if (elementName.equals("text:s")) // add space for text:s
					fw.write(" ");
				else {
					List<?> children = e.getContent();
					Iterator<?> iterator = children.iterator();

					while (iterator.hasNext()) {

						Object child = iterator.next();
						// If Child is a Text Node, then append the text
						if (child instanceof Text) {
							Text t = (Text) child;
							String str = t.getValue();
							fw.write(str);
						} else
							processElement(child, fw); // Recursively process the child element
					}
				}
				if (elementName.equals("text:p"))
					fw.write("\n");
			} else {
				List<?> non_text_list = e.getContent();
				Iterator<?> it = non_text_list.iterator();
				while (it.hasNext()) {
					Object non_text_child = it.next();
					processElement(non_text_child, fw);
				}
			}
		}
	}

	@Override
	public FileVO[] getContents(File file, String mimetype) throws Exception {
		return null;
	}

}
