/*
 * Copyright 2005 The Apache Software Foundation.
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
 * $Id: DOMSignatureProperty.java 647272 2008-04-11 19:22:21Z mullan $
 */
package it.eng.utility.cryptosigner.provider.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.SignatureProperty;
import javax.xml.crypto.dsig.XMLSignature;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * DOM-based implementation of SignatureProperty.
 *
 * @author Sean Mullan
 */
public final class DOMSignatureProperty extends DOMStructure 
    implements SignatureProperty {
 
    private final String id;
    private final String target;
    private final List content;

    /**
     * Creates a <code>SignatureProperty</code> from the specified parameters.
     *
     * @param content a list of one or more {@link XMLStructure}s. The list
     *    is defensively copied to protect against subsequent modification.
     * @param target the target URI
     * @param id the Id (may be <code>null</code>)
     */
    public DOMSignatureProperty(List content, String target, String id) {
	if (target == null) {
	    throw new NullPointerException("target cannot be null");
	} else if (content == null) {
	    throw new NullPointerException("content cannot be null");
	} else if (content.isEmpty()) {
	    throw new IllegalArgumentException("content cannot be empty");
        } else {
            List contentCopy = new ArrayList(content);
            for (int i = 0, size = contentCopy.size(); i < size; i++) {
                if (!(contentCopy.get(i) instanceof XMLStructure)) {
                    throw new ClassCastException
                        ("content["+i+"] is not a valid type");
                }
            }
            this.content = Collections.unmodifiableList(contentCopy);
        }
	this.target = target;
	this.id = id;
    }

    /**
     * Creates a <code>DOMSignatureProperty</code> from an element.
     *
     * @param propElem a SignatureProperty element
     */
    public DOMSignatureProperty(Element propElem) throws MarshalException {
	// unmarshal attributes
        target = DOMUtils.getAttributeValue(propElem, "Target");
	if (target == null) {
	    throw new MarshalException("target cannot be null");
	}
        id = DOMUtils.getAttributeValue(propElem, "Id");

	NodeList nodes = propElem.getChildNodes();
	int length = nodes.getLength();
	List content = new ArrayList(length);
	for (int i = 0; i < length; i++) {
	    content.add(new javax.xml.crypto.dom.DOMStructure(nodes.item(i)));
	}
        if (content.isEmpty()) {
	    throw new MarshalException("content cannot be empty");
        } else {
            this.content = Collections.unmodifiableList(content);
        }
    }

    public List getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getTarget() {
        return target;
    }

    public void marshal(Node parent, String dsPrefix, DOMCryptoContext context)
	throws MarshalException {
        Document ownerDoc = DOMUtils.getOwnerDocument(parent);

        Element propElem = DOMUtils.createElement
            (ownerDoc, "SignatureProperty", XMLSignature.XMLNS, dsPrefix);

	// set attributes
	DOMUtils.setAttributeID(propElem, "Id", id);
        DOMUtils.setAttribute(propElem, "Target", target);

        // create and append any elements and mixed content
	for (int i = 0, size = content.size(); i < size; i++) {
	    javax.xml.crypto.dom.DOMStructure property = 
		(javax.xml.crypto.dom.DOMStructure) content.get(i);
	    DOMUtils.appendChild(propElem, property.getNode());
        }
	    
	parent.appendChild(propElem);
    }

    public boolean equals(Object o) {
	if (this == o) {
            return true;
	}

	if (!(o instanceof SignatureProperty)) {
            return false;
	}
        SignatureProperty osp = (SignatureProperty) o;

	boolean idsEqual = (id == null ? osp.getId() == null :
            id.equals(osp.getId()));

	return (equalsContent(osp.getContent()) && 
	    target.equals(osp.getTarget()) && idsEqual);
    }

    public int hashCode() {
	assert false : "hashCode not designed";
	return 50;
    }

    private boolean equalsContent(List otherContent) {
	int osize = otherContent.size();
	if (content.size() != osize) {
            return false;
	}
        for (int i = 0; i < osize; i++) {
            XMLStructure oxs = (XMLStructure) otherContent.get(i);
            XMLStructure xs = (XMLStructure) content.get(i);
            if (oxs instanceof javax.xml.crypto.dom.DOMStructure) {
		if (!(xs instanceof javax.xml.crypto.dom.DOMStructure)) {
                    return false;
		}
                Node onode =
                    ((javax.xml.crypto.dom.DOMStructure) oxs).getNode();
		Node node =
                    ((javax.xml.crypto.dom.DOMStructure) xs).getNode();
		if (!DOMUtils.nodesEqual(node, onode)) {
		    return false;
		}
            } else {
		if (!(xs.equals(oxs))) {
                    return false;
		}
            }
	}

	return true;
    }
}
