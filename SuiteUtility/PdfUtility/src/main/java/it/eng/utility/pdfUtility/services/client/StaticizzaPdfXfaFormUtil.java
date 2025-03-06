package it.eng.utility.pdfUtility.services.client;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class StaticizzaPdfXfaFormUtil {


	public static String getPathDownload(String xml){
		XmlResponseBean response = parseResponse(xml);
		String pathDownload = response.getUrlDocumentoPdf();
		
		return pathDownload;
	}
	
	private static XmlResponseBean parseResponse(String xml){
		XmlResponseBean response = new XmlResponseBean();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
			Document document = builder.parse(is);
			
			NodeList nList = document.getElementsByTagName("CodiceOutput");
			String nodeVal = null;
			for (int i = 0, len = nList.getLength(); i < len; i++) {
			    Element elm = (Element)nList.item(i);
			    nodeVal = elm.getFirstChild().getNodeValue();
			    response.setCodiceOutput(nodeVal);
			}
			
			if(nodeVal!=null && nodeVal.equalsIgnoreCase("0")){
				nList = document.getElementsByTagName("out_document_pdf");
				for (int i = 0, len = nList.getLength(); i < len; i++) {
				    Element elm = (Element)nList.item(i);
				    String pathDownload = elm.getFirstChild().getNodeValue();
				    response.setUrlDocumentoPdf(pathDownload);
				}
				
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return response;
	}
}
