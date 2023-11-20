/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class HtmlTransformer {

	public static String convertInputIntoParagraph(String pStrHtml) throws SAXException, 
	IOException, ParserConfigurationException, TransformerException, XPathExpressionException{
		pStrHtml = pStrHtml.replace("><style", "/><style");System.out.println(pStrHtml);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(false);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setValidating(false);
		dbf.setSchema(null);
		Document odtDoc =  dbf.newDocumentBuilder().parse(new InputSource(new StringReader(pStrHtml)));
		//NodeList lNodes = odtDoc.getElementsByTagName("input");
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList lNodes = (NodeList)xPath.evaluate("//*/input",
				odtDoc.getDocumentElement(), XPathConstants.NODESET);   
		for (int i = 0; i < lNodes.getLength(); i++){
			Node n = lNodes.item(i);
			((Element)n).removeAttribute("style");
			String id = ((Element)n).getAttribute("id");
			System.out.println(id);
			String value = ((Element)n).getAttribute("value");
			if (StringUtils.isNotEmpty(value));
			((Element)n).setTextContent(value);
			String style = ((Element)((Element)n).getParentNode()).getAttribute("style");
			((Element)n).setAttribute("style", style);
			odtDoc.renameNode(n, null, "p");
		}
		lNodes = odtDoc.getElementsByTagName("textarea");
		List<Element> lListToRemove = new ArrayList<Element>();
		List<Node> lListNodeToAdd = new ArrayList<Node>();
		for (int i = 0; i < lNodes.getLength(); i++){
			Node n = lNodes.item(i);
			System.out.println(n);
			Element lElement = (Element)n;
			lElement.removeAttribute("style");
			Node parent = lElement.getParentNode();
			((Element)parent).getAttribute("class");
			String style = ((Element)parent).getAttribute("style");
			Node newNode = odtDoc.createElement("p");
			newNode.setTextContent(((Element)n).getTextContent());
			((Element)newNode).setAttribute("style", style); 
			lListToRemove.add(lElement);
			lListNodeToAdd.add(newNode);
			//parent.appendChild(newNode);
			
			//((Element)n).getParentNode().replaceChild(newNode,n);
			//odtDoc.renameNode(n, null, "p");
//			String value = ((Element)n).getAttribute("value");
//			if (StringUtils.isNotEmpty(value));
//			((Element)n).setTextContent(value);
		}
		for (int k = 0; k<lListToRemove.size(); k++){
			Element lElement = lListToRemove.get(k);
			lElement.getParentNode().appendChild(lListNodeToAdd.get(k));
			lElement.getParentNode().removeChild(lElement);
		}
		DOMSource domSource = new DOMSource(odtDoc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty("method","html");
		transformer.transform(domSource, result);
		return writer.toString();
	}
}
