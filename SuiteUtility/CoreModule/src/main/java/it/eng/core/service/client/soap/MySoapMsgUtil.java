package it.eng.core.service.client.soap;
import java.io.StringWriter;
 

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class MySoapMsgUtil {
	private Logger log =  Logger.getLogger(getClass());

	public String parseSOAPBodyToString(SOAPBody body){
		String strBody=null;
		try {
			Document doc = body.extractContentAsDocument();

			Source source = new DOMSource(doc);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);
			strBody=stringWriter.getBuffer().toString();

		} catch (SOAPException e) {
			log.error("SOAPException while parsing soap body to string: " + e.getMessage());
			log.debug("Stack Trace: ", e);
		} catch (TransformerConfigurationException e) {
			log.error("TransformerConfigurationException while parsing soap body to string: " + e.getMessage());
			log.debug("Stack Trace: ", e);
		} catch (TransformerException e) {
			log.error("TransformerException while parsing soap body to string: " + e.getMessage());
			log.debug("Stack Trace: ", e);
		}

		return strBody;
	}


}