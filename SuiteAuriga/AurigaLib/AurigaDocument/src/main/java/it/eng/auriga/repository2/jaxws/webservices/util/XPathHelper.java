/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathHelper {

   public XPathHelper() {
   }

   public NodeList processXPath(String xpath, Node target) throws SAXException {
	   XPathFactory xpf = XPathFactory.newInstance();
       XPath xp = xpf.newXPath();
       NodeList nodes;
	try {
		nodes = (NodeList)xp.evaluate(xpath, target, XPathConstants.NODESET);
	} catch (XPathExpressionException e) {
		throw new SAXException(e);
	}
      // return the resulting node
      return nodes;
   }

   public String processXPathStringValue(String xpath, Node target) throws SAXException {
	   XPathFactory xpf = XPathFactory.newInstance();
       XPath xp = xpf.newXPath();
       String node;
	try {
		node = (String)xp.evaluate(xpath, target, XPathConstants.STRING);
	} catch (XPathExpressionException e) {
		throw new SAXException(e);
	}
       return node;
   }

   public double processXPathNumValue(String xpath, Node target) throws SAXException {
	   XPathFactory xpf = XPathFactory.newInstance();
       XPath xp = xpf.newXPath();
       Double node;
	try {
		node = (Double)xp.evaluate(xpath, target, XPathConstants.NUMBER);
	} catch (XPathExpressionException e) {
		throw new SAXException(e);
	}
       return node;
   }
}

