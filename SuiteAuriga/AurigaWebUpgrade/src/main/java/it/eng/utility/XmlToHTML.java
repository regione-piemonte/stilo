/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

public class XmlToHTML {
	
	private static Logger mLogger = Logger.getLogger(XmlToHTML.class);
	
	public static String convert(InputStream fileXml, File fileXsl) {
		
		StringWriter sw = new StringWriter();
		
//		TransformerFactory factory = TransformerFactory.newInstance("org.apache.xalan.processor.TransformerFactoryImpl", null);
		TransformerFactory factory = TransformerFactory.newInstance();
		
		try {
			
			Transformer transformer = factory.newTransformer(new StreamSource(fileXsl)); 
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new StreamSource(fileXml), new StreamResult(sw));   
			return sw.toString();
			
		} catch(Exception e) {
			mLogger.warn(e);
		}
		
		return null;
	}
	
}
