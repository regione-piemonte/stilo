package it.eng.core.business;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Classe di utility methods per le stringhe XPath
 * 
 * @author upescato
 *
 */

public class XPathUtil {

	private XPathUtil() {
	}

	/**
	 * Ritorna un'istanza della classe
	 * 
	 * @return
	 */
	public static XPathUtil getInstance() {
		return new XPathUtil();
	}

	/**
	 * Metodo per validare la sintassi XPath di una stringa in input
	 * 
	 * @param input
	 * @return true se la sintassi della stringa in input è valida, false altrimenti
	 */
	public boolean isXPathValidSilent(String input) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			XPathExpression expr = xpath.compile(input);
			if (expr != null) {
				return true;
			} else {
				return false;
			}
		} catch (XPathExpressionException e) {
			return false;
		}
	}

	/**
	 * Metodo per validare la sintassi XPath di una stringa in input. Lancia un'eccezione <code>XPathExpressionException</code> se la sintassi della stringa in
	 * input non è valida.
	 * 
	 * @param input
	 * @throws XPathExpressionException
	 */
	public void isXPathValid(String input) throws XPathExpressionException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		xpath.compile(input);
	}

	/**
	 * Metodo di utilità che, dati in input un'espressione Xpath e un file, verifica se l'espressione è valida rispetto al file.
	 * 
	 * @param xpathExpression
	 *            - Espressione xpath da valutare
	 * @param xmlFile
	 *            - File xml rispetto a cui valutare l'espressione xpath
	 * @return true se l'espressione xpath è valida rispetto al file in input, false altrimenti.
	 * @throws Exception
	 */
	public boolean validateXPathAgainstXML(String xpathExpression, String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document;
		try {
			// Costruisco il file come DOM
			builder = factory.newDocumentBuilder();
			document = builder.parse(IOUtils.toInputStream(xml));

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			// Valuto l'espressione xpath rispetto all'oggetto Document
			NodeList content = (NodeList) xpath.evaluate(xpathExpression, document.getDocumentElement(), XPathConstants.NODESET);
			// Se il numero di nodi tornati è pari a 0, allora l'espressione xpath non è corretta rispetto all'XML passato in input
			if (content.getLength() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (ParserConfigurationException e) {
			throw e;
		} catch (SAXException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (XPathExpressionException e) {
			throw e;
		}

	}
}
