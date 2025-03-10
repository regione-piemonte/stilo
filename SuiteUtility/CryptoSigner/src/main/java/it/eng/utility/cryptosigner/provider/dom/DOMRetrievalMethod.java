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
 * Portions copyright 2005 Sun Microsystems, Inc. All rights reserved.
 */
/*
 * =========================================================================== 
 *
 * (C) Copyright IBM Corp. 2003 All Rights Reserved.
 *
 * ===========================================================================
 */
/*
 * $Id: DOMRetrievalMethod.java 793943 2009-07-14 15:33:19Z coheigea $
 */
package it.eng.utility.cryptosigner.provider.dom;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dom.DOMURIReference;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * DOM-based implementation of RetrievalMethod.
 *
 * @author Sean Mullan
 * @author Joyce Leung
 */
public final class DOMRetrievalMethod extends DOMStructure
    implements RetrievalMethod, DOMURIReference {

    private final List transforms;
    private String uri;
    private String type;
    private Attr here;

    /**
     * Creates a <code>DOMRetrievalMethod</code> containing the specified 
     * URIReference and List of Transforms.
     *
     * @param uri the URI
     * @param type the type
     * @param transforms a list of {@link Transform}s. The list is defensively
     *    copied to prevent subsequent modification. May be <code>null</code>
     *    or empty.
     */
    public DOMRetrievalMethod(String uri, String type, List transforms) {
	if (uri == null) {
	    throw new NullPointerException("uri cannot be null");
	}
        if (transforms == null || transforms.isEmpty()) {
            this.transforms = Collections.EMPTY_LIST;
        } else {
            List transformsCopy = new ArrayList(transforms);
            for (int i = 0, size = transformsCopy.size(); i < size; i++) {
                if (!(transformsCopy.get(i) instanceof Transform)) {
                    throw new ClassCastException
                        ("transforms["+i+"] is not a valid type");
                }
            }
            this.transforms = Collections.unmodifiableList(transformsCopy);
        }
	this.uri = uri;
        if ((uri != null) && (!uri.equals(""))) {
            try {
                new URI(uri);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

	this.type = type;
    }
	
    /**
     * Creates a <code>DOMRetrievalMethod</code> from an element.
     *
     * @param rmElem a RetrievalMethod element
     */
    public DOMRetrievalMethod(Element rmElem, XMLCryptoContext context,
	Provider provider) throws MarshalException {
        // get URI and Type attributes
        uri = DOMUtils.getAttributeValue(rmElem, "URI");
        type = DOMUtils.getAttributeValue(rmElem, "Type");

	// get here node
	here = rmElem.getAttributeNodeNS(null, "URI");

        // get Transforms, if specified
        List transforms = new ArrayList();
        Element transformsElem = DOMUtils.getFirstChildElement(rmElem);
        if (transformsElem != null) {
            Element transformElem = 
		DOMUtils.getFirstChildElement(transformsElem);
            while (transformElem != null) {
                transforms.add
		    (new DOMTransform(transformElem, context, provider));
                transformElem = DOMUtils.getNextSiblingElement(transformElem);
	    }
        }
	if (transforms.isEmpty()) {
            this.transforms = Collections.EMPTY_LIST;
	} else {
            this.transforms = Collections.unmodifiableList(transforms);
	}
    }

    public String getURI() {
	return uri;
    }

    public String getType() {
	return type;
    }

    public List getTransforms() {
	return transforms;
    }

    public void marshal(Node parent, String dsPrefix, DOMCryptoContext context)
	throws MarshalException {
        Document ownerDoc = DOMUtils.getOwnerDocument(parent);

        Element rmElem = DOMUtils.createElement
            (ownerDoc, "RetrievalMethod", XMLSignature.XMLNS, dsPrefix);

        // add URI and Type attributes
        DOMUtils.setAttribute(rmElem, "URI", uri);
        DOMUtils.setAttribute(rmElem, "Type", type);

        // add Transforms elements
	if (!transforms.isEmpty()) {
	    Element transformsElem = DOMUtils.createElement
                (ownerDoc, "Transforms", XMLSignature.XMLNS, dsPrefix);
	    rmElem.appendChild(transformsElem);
	    for (int i = 0, size = transforms.size(); i < size; i++) {
	        ((DOMTransform) transforms.get(i)).marshal
		    (transformsElem, dsPrefix, context);
            }
	}

        parent.appendChild(rmElem);

	// save here node
	here = rmElem.getAttributeNodeNS(null, "URI");
    }

    public Node getHere() {
	return here;
    }

    public Data dereference(XMLCryptoContext context)
        throws URIReferenceException {

	if (context == null) {
	    throw new NullPointerException("context cannot be null");
	}

	/*
         * If URIDereferencer is specified in context; use it, otherwise use 
	 * built-in.
	 */
        URIDereferencer deref = context.getURIDereferencer();
        if (deref == null) {
	    deref = DOMURIDereferencer.INSTANCE;
	}

	Data data = deref.dereference(this, context);

        // pass dereferenced data through Transforms
	try {
	    for (int i = 0, size = transforms.size(); i < size; i++) {
                Transform transform = (Transform) transforms.get(i);
                data = ((DOMTransform) transform).transform(data, context);
            }
	} catch (Exception e) {
	    throw new URIReferenceException(e);
	}
	return data;
    }

    public XMLStructure dereferenceAsXMLStructure(XMLCryptoContext context)
	throws URIReferenceException {

	try {
	    ApacheData data = (ApacheData) dereference(context);
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setNamespaceAware(true);
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document doc = db.parse(new ByteArrayInputStream
		(data.getXMLSignatureInput().getBytes()));
	    Element kiElem = doc.getDocumentElement();
            if (kiElem.getLocalName().equals("X509Data")) {
		return new DOMX509Data(kiElem);
	    } else {
		return null; // unsupported
	    }
	} catch (Exception e) {
	    throw new URIReferenceException(e);
	}
    }

    public boolean equals(Object obj) {
	if (this == obj) {
            return true;
	}
        if (!(obj instanceof RetrievalMethod)) {
            return false;
	}
        RetrievalMethod orm = (RetrievalMethod) obj;

	boolean typesEqual = (type == null ? orm.getType() == null :
            type.equals(orm.getType()));

	return (uri.equals(orm.getURI()) && 
	    transforms.equals(orm.getTransforms()) && typesEqual);
    }

    public int hashCode() {
	assert false : "hashCode not designed";
	return 48;
    }
}
