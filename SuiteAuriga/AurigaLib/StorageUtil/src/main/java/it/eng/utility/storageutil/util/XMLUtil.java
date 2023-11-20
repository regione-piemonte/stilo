/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Classe che espone i metodi per serializzare e deserializzare gli oggetti come xml
 * Gli oggetti in ingresso devono avere l'annotation javax.xml.bind.annotation.XmlRootElement
 * 
 * @author Mattia Zanin
 *
 */
public class XMLUtil{

	/**
     * Metodo di serializzazione di un oggetto come xml
     * 
     * @param obj    
	 * @throws Exception
     */
	public String serialize(Object obj) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		StringWriter writer = new StringWriter();
		context.createMarshaller().marshal(obj,writer);
		return writer.toString();
	}

	/**
     * Metodo di serializzazione di un oggetto come xml, formattato
     * 
     * @param obj    
	 * @throws JAXBException 
	 * @throws Exception
     */
	public String prettySerialize(Object obj) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		StringWriter writer = new StringWriter();
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(obj,writer);
		return writer.toString();
	}

	/**
     * Metodo di deserializzazione di un oggetto da xml
     * 
     * @param str
     * @param classe
	 * @throws Exception
     */
	public Serializable deserialize(String str, Class<?> classe) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(classe);
		return (Serializable)context.createUnmarshaller().unmarshal(new StringReader(str));
	}
	
	/**
     * Metodo di deserializzazione di un oggetto da xml
     * 
     * @param str
     * @param classe
	 * @throws ParserConfigurationException 
	 * @throws JAXBException 
	 * @throws SAXException 
	 * @throws Exception
     */
	public Serializable deserializeIgnoringDtd(String str, Class<?> classe) throws ParserConfigurationException, JAXBException, SAXException {
		JAXBContext jc = JAXBContext.newInstance(classe);
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setFeature("http://apache.org/xml/features/validation/schema", false);
        spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        // spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        spf.setNamespaceAware(true);
        XMLReader xmlReader = spf.newSAXParser().getXMLReader();
        InputSource inputSource = new InputSource(new StringReader(str));
        SAXSource source = new SAXSource(xmlReader, inputSource);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (Serializable) unmarshaller.unmarshal(source);
	}
	
	/**
     * Metodo statico che crea una nuova istanza della classe
     * 
     */
	public static XMLUtil newInstance() {		
		return new XMLUtil();
	}
	
}