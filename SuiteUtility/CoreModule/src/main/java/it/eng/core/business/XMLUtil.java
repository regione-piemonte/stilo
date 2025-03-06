package it.eng.core.business;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * Classe contenente utility per la gestione/validazione degli xml
 * 
 * @author upescato
 *
 */

public class XMLUtil {

	private static final Logger log = Logger.getLogger(XMLUtil.class);

	private XMLUtil() {
	}

	/**
	 * Ritorna un'istanza della classe
	 * 
	 * @return
	 */
	public static XMLUtil getInstance() {
		return new XMLUtil();
	}

	/**
	 * Metodo di utility che verifica la validità di un file XML rispetto ad uno schema XSD
	 * 
	 * @param file
	 *            - File da verificare
	 * @param schemaFile
	 *            - Schema su cui fare verifica
	 * @return
	 */
	public boolean validateXMLagainstSchema(String xml, File schemaFile) {
		Source xmlFile = new StreamSource(new StringReader(xml));
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(schemaFile);
			Validator validator = schema.newValidator();
			validator.validate(xmlFile);
			return true;
		} catch (SAXException e) {
			log.error(xmlFile.getSystemId() + " non è valido.");
			log.error("Causa: " + e.getLocalizedMessage());
			return false;
		} catch (IOException e) {
			log.error("IOException validateXMLagainstSchema: " + e);
			return false;
		}

	}

}
