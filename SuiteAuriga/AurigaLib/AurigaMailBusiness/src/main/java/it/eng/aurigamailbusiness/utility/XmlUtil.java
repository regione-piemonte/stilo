/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * Classe che contiene i metodi di utilità per effettuare il <br>
 * Marshaller e Unmarshaller
 * 
 * @author michele
 *
 */
public class XmlUtil {

	private static final String EXTERNAL_DTD_LOADING_FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

	private static Logger logger = LogManager.getLogger(XmlUtil.class);

	private static JAXBContext context;

	private static SAXParserFactory mParserFactory = null;

	public static void setContext(JAXBContext context) {
		XmlUtil.context = context;
		mParserFactory = SAXParserFactory.newInstance();
		mParserFactory.setNamespaceAware(true);
		mParserFactory.setValidating(false);
	}

	public static Object xmlToObject(String xml) throws JAXBException {

		SAXSource lSource = new SAXSource(getXmlReader(), new InputSource(new StringReader(xml)));

		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(null);

		return unmarshaller.unmarshal(lSource);
	}

	public static Object xmlToObject(InputStream xml) throws JAXBException {
		try {
			String ret = IOUtils.toString(xml);
			return xmlToObject(ret);
		} catch (Exception e) {
			logger.error("Eccezione xmlToObject", e);
		}
		return null;
	}

	public static String objectToXml(Object bean) throws JAXBException {
		if (bean != null) {
			StringWriter writer = new StringWriter();
			Marshaller marshall = context.createMarshaller();
			marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshall.marshal(bean, writer);
			return writer.toString();
		} else {
			return null;
		}
	}

	public static String objectToXml(Object bean, String encoding) throws JAXBException {
		if (bean != null) {
			StringWriter writer = new StringWriter();
			Marshaller marshall = context.createMarshaller();
			marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshall.setProperty("jaxb.encoding", encoding);
			marshall.marshal(bean, writer);

			return writer.toString();
		} else {
			return null;
		}
	}

	private static synchronized XMLReader getXmlReader() throws JAXBException {
		try {
			SAXParser lSaxParser = mParserFactory.newSAXParser();
			XMLReader lReader = lSaxParser.getXMLReader();
			lReader.setFeature(EXTERNAL_DTD_LOADING_FEATURE, false);
			return lReader;
		} catch (Exception e) {
			throw new JAXBException(e);
		}
	}

	public static JAXBContext getContext() {
		return context;
	}

}