package it.eng.utility;

import java.util.Collections;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

public class NoNamespaceContext implements NamespaceContext {

	@Override
	public String getNamespaceURI(String prefix) {
		return XMLConstants.NULL_NS_URI;
	}

	@Override
	public String getPrefix(String namespaceURI) {
		return "";
	}

	@Override
	public Iterator getPrefixes(String namespaceURI) {
		return Collections.EMPTY_SET.iterator();
	}
}