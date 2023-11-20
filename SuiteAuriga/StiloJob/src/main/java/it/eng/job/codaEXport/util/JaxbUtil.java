/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;



public class JaxbUtil {

	static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private JAXBContext jc;
	private Unmarshaller unmarshaller;
	private Marshaller marshaller;

	public JaxbUtil(Class objectClass) throws JAXBException {
		jc = JAXBContext.newInstance(objectClass);
		unmarshaller = jc.createUnmarshaller();
		marshaller = jc.createMarshaller();
	}

	public Boolean validate(InputStream xmlSource, String pathXSD) /*throws Exception*/ {
		logger.debug("Validazione in corso... ");
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		URL xsdURL = JaxbUtil.class.getResource("/xsd/" + pathXSD);
		logger.debug("Schema: " + xsdURL);

		Schema schema;
		try {
			schema = schemaFactory.newSchema(xsdURL);
			Validator validator = schema.newValidator();
			//validator.validate(new StreamSource(xmlMessaggio));
			validator.validate(new StreamSource(xmlSource));
		} catch (SAXException e) {
			logger.error(e);
			return false;
		} catch (IOException e) {
			logger.error(e);
			return false;
		}
				
		return true;

	}

	// OK
	public Object unmarshal(InputStream xmlMessaggio, String pathXSD) throws Exception {
		logger.debug("unmarshal in corso... ");
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		URL xsdURL = JaxbUtil.class.getResource("/xsd/" + pathXSD);
		logger.debug("Schema: " + xsdURL);
		schemaFactory.setErrorHandler(new ErrorHandler() {

			@Override
			public void warning(SAXParseException paramSAXParseException) throws SAXException {
				logger.error("WARNING SAX " + paramSAXParseException.getLineNumber());
				logger.error("WARNING SAX " + paramSAXParseException.getColumnNumber());
				logger.error("WARNING SAX " + paramSAXParseException.getMessage());
				logger.error("WARNING StackTrace " + paramSAXParseException.getMessage(), paramSAXParseException.getCause());
			}

			@Override
			public void fatalError(SAXParseException paramSAXParseException) throws SAXException {
				logger.error("FATAL SAX " + paramSAXParseException.getLineNumber());
				logger.error("FATAL SAX " + paramSAXParseException.getColumnNumber());
				logger.error("FATAL SAX " + paramSAXParseException.getMessage());
				logger.error("FATAL StackTrace " + paramSAXParseException.getMessage(), paramSAXParseException.getCause());

			}

			@Override
			public void error(SAXParseException paramSAXParseException) throws SAXException {
				logger.error("ERROR SAX " + paramSAXParseException.getLineNumber());
				logger.error("ERROR SAX " + paramSAXParseException.getColumnNumber());
				logger.error("ERROR SAX " + paramSAXParseException.getMessage());
				logger.error("ERROR StackTrace " + paramSAXParseException.getMessage(), paramSAXParseException.getCause());

			}
		});

		Schema schema = schemaFactory.newSchema(xsdURL);
		unmarshaller.setSchema(schema);

		// StringReader reader = new StringReader(xmlMessaggio);
		unmarshaller.setEventHandler(new ValidationEventHandler() {

			@Override
			public boolean handleEvent(ValidationEvent paramValidationEvent) {
				try {
					logger.error("Messaggio " + paramValidationEvent.getMessage());
					logger.error("Severity " + paramValidationEvent.getSeverity());
					logger.error("Errore ", paramValidationEvent.getLinkedException());
					logger.error("LineNumber " + paramValidationEvent.getLocator().getLineNumber());
					logger.error("ColumnNumber " + paramValidationEvent.getLocator().getColumnNumber());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				return false;
			}
		});
		Object obj = unmarshaller.unmarshal(xmlMessaggio);
		
		if (obj instanceof JAXBElement) {
			@SuppressWarnings("unchecked")
			//JAXBElement<T> jaxbEle = (JAXBElement<T>) obj;
			JAXBElement jaxbEle = (JAXBElement) obj;
			return jaxbEle.getValue();
		} else
			return obj;// null;
	}
	
	public String marshal(Object bean) throws Exception {
		StringWriter sw = new StringWriter();
		// JaxbValidationResponse<T> lJaxbValidationResponse = new JaxbValidationResponse<T>();
		// SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		// URL xsdURL = JaxbUtil.class.getResource("/xsd/" + pathXSD);
		// Schema schema = schemaFactory.newSchema(xsdURL);
		// marshaller.setSchema(schema);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// marshaller.setEventHandler(lJaxbValidationResponse.getCustomValidationHandler());
		marshaller.marshal(bean, sw);
		return sw.toString();
	}
	
	public Object unmarshal(String xmlMessaggio) throws Exception {
		
		StringReader reader = new StringReader(xmlMessaggio);
		Object obj = unmarshaller.unmarshal(reader);
		
		if (obj instanceof JAXBElement) {
			@SuppressWarnings("unchecked")
			//JAXBElement<T> jaxbEle = (JAXBElement<T>) obj;
			JAXBElement jaxbEle = (JAXBElement) obj;
			return jaxbEle.getValue();
		} else
			return obj;// null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object unmarshal(File fileXml, Class classe) throws Exception {
		if (logger.isTraceEnabled()) logger.trace(String.format("file: %s, classe: %s", fileXml, classe));
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Source xmlSource = new StreamSource(fileXml);
		Object obj = unmarshaller.unmarshal(xmlSource, classe);
		if (obj instanceof JAXBElement) {
			JAXBElement jaxbEle = (JAXBElement) obj;
			return jaxbEle.getValue();
		} else
			return obj;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object unmarshal(Reader xmlMessaggio, Class classe) throws Exception {
		
		
		if (logger.isTraceEnabled()) logger.trace(String.format("classe: %s", classe));
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Source xmlSource = new StreamSource(xmlMessaggio);
		Object obj = unmarshaller.unmarshal(xmlSource, classe);
		if (obj instanceof JAXBElement) {
			JAXBElement jaxbEle = (JAXBElement) obj;
			return jaxbEle.getValue();
		} else
			return obj;
	    }
	
	
	public Object unmarshal(String xmlMessaggio,Class classe, String pathXSD)
			throws Exception {
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		URL xsdURL = JaxbUtil.class.getResource("/xsd/" + pathXSD);
		logger.debug(xsdURL);
		schemaFactory.setErrorHandler(new ErrorHandler() {

			@Override
			public void warning(SAXParseException paramSAXParseException)
					throws SAXException {
				logger.error("WARNING SAX "
						+ paramSAXParseException.getLineNumber());
				logger.error("WARNING SAX "
						+ paramSAXParseException.getColumnNumber());
				logger.error("WARNING SAX "
						+ paramSAXParseException.getMessage());
				logger.error(
						"WARNING StackTrace "
								+ paramSAXParseException.getMessage(),
						paramSAXParseException.getCause());
			}

			@Override
			public void fatalError(SAXParseException paramSAXParseException)
					throws SAXException {
				logger.error("FATAL SAX "
						+ paramSAXParseException.getLineNumber());
				logger.error("FATAL SAX "
						+ paramSAXParseException.getColumnNumber());
				logger.error("FATAL SAX "
						+ paramSAXParseException.getMessage());
				logger.error(
						"FATAL StackTrace "
								+ paramSAXParseException.getMessage(),
						paramSAXParseException.getCause());

			}

			@Override
			public void error(SAXParseException paramSAXParseException)
					throws SAXException {
				logger.error("ERROR SAX "
						+ paramSAXParseException.getLineNumber());
				logger.error("ERROR SAX "
						+ paramSAXParseException.getColumnNumber());
				logger.error("ERROR SAX "
						+ paramSAXParseException.getMessage());
				logger.error(
						"ERROR StackTrace "
								+ paramSAXParseException.getMessage(),
						paramSAXParseException.getCause());

			}
		});

		Schema schema = schemaFactory.newSchema(xsdURL);
		unmarshaller.setSchema(schema);

		StringReader reader = new StringReader(xmlMessaggio);
		unmarshaller.setEventHandler(new ValidationEventHandler() {

			@Override
			public boolean handleEvent(ValidationEvent paramValidationEvent) {
				try {
					logger.error(paramValidationEvent.getMessage());
					logger.error(paramValidationEvent.getSeverity());
					logger.error("Errore ",
							paramValidationEvent.getLinkedException());
					logger.error(paramValidationEvent.getLocator()
							.getLineNumber());
					logger.error(paramValidationEvent.getLocator()
							.getColumnNumber());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				return false;
			}
		});
		Object obj = unmarshaller.unmarshal(reader);
		return obj;
	}
}
