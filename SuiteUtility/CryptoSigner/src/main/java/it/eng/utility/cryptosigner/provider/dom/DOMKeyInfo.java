/*
 * Copyright 2005-2009 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
/*
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 */
/*
 * $Id: DOMKeyInfo.java 793943 2009-07-14 15:33:19Z coheigea $
 */
package it.eng.utility.cryptosigner.provider.dom;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * DOM-based implementation of KeyInfo.
 *
 * @author Sean Mullan
 */
public final class DOMKeyInfo extends DOMStructure implements KeyInfo {

    private final String id;
    private final List keyInfoTypes;

    /**
     * Creates a <code>DOMKeyInfo</code>.
     *
     * @param content a list of one or more {@link XMLStructure}s representing
     *    key information types. The list is defensively copied to protect
     *    against subsequent modification.
     * @param id an ID attribute
     */
    public DOMKeyInfo(List content, String id) {
        if (content == null) {
            throw new NullPointerException("content cannot be null");
	}
	List typesCopy = new ArrayList(content);
	if (typesCopy.isEmpty()) {
	    throw new IllegalArgumentException("content cannot be empty");
	}
	for (int i = 0, size = typesCopy.size(); i < size; i++) {
	    if (!(typesCopy.get(i) instanceof XMLStructure)) {
		throw new ClassCastException
		    ("content["+i+"] is not a valid KeyInfo type");
	    }
	}
	this.keyInfoTypes = Collections.unmodifiableList(typesCopy);
        this.id = id;
    }

    /**
     * Creates a <code>DOMKeyInfo</code> from XML.
     *
     * @param kiElem KeyInfo element
     */
    public DOMKeyInfo(Element kiElem, XMLCryptoContext context,
	Provider provider) throws MarshalException {
	// get Id attribute, if specified
	id = DOMUtils.getAttributeValue(kiElem, "Id");

        // get all children nodes
        NodeList nl = kiElem.getChildNodes();
	int length = nl.getLength();
	if (length < 1) {
	    throw new MarshalException
		("KeyInfo must contain at least one type");
	}
	List content = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            Node child = nl.item(i);
            // ignore all non-Element nodes
            if (child.getNodeType() != Node.ELEMENT_NODE) {
                continue;
	    }
            Element childElem = (Element) child;
	    String localName = childElem.getLocalName();
            if (localName.equals("X509Data")) {
	        content.add(new DOMX509Data(childElem));
            } else if (localName.equals("KeyName")) {
	        content.add(new DOMKeyName(childElem));
            } else if (localName.equals("KeyValue")) {
	        content.add(new DOMKeyValue(childElem));
            } else if (localName.equals("RetrievalMethod")) {
	        content.add
		    (new DOMRetrievalMethod(childElem, context, provider));
            } else if (localName.equals("PGPData")) {
	        content.add(new DOMPGPData(childElem));
	    } else { //may be MgmtData, SPKIData or element from other namespace
	        content.add(new javax.xml.crypto.dom.DOMStructure((childElem)));
	    }
        }
	keyInfoTypes = Collections.unmodifiableList(content);
    }

    public String getId() {
	return id;
    }

    public List getContent() {
	return keyInfoTypes;
    }

    public void marshal(XMLStructure parent, XMLCryptoContext context)
        throws MarshalException {
        if (parent == null) {
            throw new NullPointerException("parent is null");
        }

        Node pNode = ((javax.xml.crypto.dom.DOMStructure) parent).getNode();
        String dsPrefix = DOMUtils.getSignaturePrefix(context);
        Element kiElem = DOMUtils.createElement
            (DOMUtils.getOwnerDocument(pNode), "KeyInfo",
             XMLSignature.XMLNS, dsPrefix);
        if (dsPrefix == null || dsPrefix.length() == 0) {
            kiElem.setAttributeNS
                ("http://www.w3.org/2000/xmlns/", "xmlns", XMLSignature.XMLNS);
        } else {
            kiElem.setAttributeNS
                ("http://www.w3.org/2000/xmlns/", "xmlns:" + dsPrefix,
                 XMLSignature.XMLNS);
        }
        marshal(pNode, kiElem, null, dsPrefix, (DOMCryptoContext) context);
    }

    public void marshal(Node parent, String dsPrefix, 
	DOMCryptoContext context) throws MarshalException {
	marshal(parent, null, dsPrefix, context);
    }

    public void marshal(Node parent, Node nextSibling, String dsPrefix,
	DOMCryptoContext context) throws MarshalException {
        Document ownerDoc = DOMUtils.getOwnerDocument(parent);

        Element kiElem = DOMUtils.createElement
	    (ownerDoc, "KeyInfo", XMLSignature.XMLNS, dsPrefix);
        marshal(parent, kiElem, nextSibling, dsPrefix, context);
    }

    private void marshal(Node parent, Element kiElem, Node nextSibling,
        String dsPrefix, DOMCryptoContext context) throws MarshalException {
        // create and append KeyInfoType elements
	for (int i = 0, size = keyInfoTypes.size(); i < size; i++) {
	    XMLStructure kiType = (XMLStructure) keyInfoTypes.get(i);
	    if (kiType instanceof DOMStructure) {
		((DOMStructure) kiType).marshal(kiElem, dsPrefix, context);
	    } else {
		DOMUtils.appendChild(kiElem,
		    ((javax.xml.crypto.dom.DOMStructure) kiType).getNode());
	    }
        }

        // append id attribute
        DOMUtils.setAttributeID(kiElem, "Id", id);

	parent.insertBefore(kiElem, nextSibling);
    }

    public boolean equals(Object o) {
	if (this == o) {
            return true;
	}

        if (!(o instanceof KeyInfo)) {
            return false;
	}
        KeyInfo oki = (KeyInfo) o;

	boolean idsEqual = (id == null ? oki.getId() == null :
	    id.equals(oki.getId()));

	return (keyInfoTypes.equals(oki.getContent()) && idsEqual);
    }

    public int hashCode() {
	assert false : "hashCode not designed";
	return 43;
    }
}
