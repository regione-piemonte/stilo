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
 * $Id: DOMManifest.java 647272 2008-04-11 19:22:21Z mullan $
 */
package it.eng.utility.cryptosigner.provider.dom;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.Manifest;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * DOM-based implementation of Manifest.
 *
 * @author Sean Mullan
 */
public final class DOMManifest extends DOMStructure implements Manifest {

    private final List references;
    private final String id;

    /**
     * Creates a <code>DOMManifest</code> containing the specified
     * list of {@link Reference}s and optional id.
     *
     * @param references a list of one or more <code>Reference</code>s. The list
     *    is defensively copied to protect against subsequent modification.
     * @param id the id (may be <code>null</code>
     */
    public DOMManifest(List references, String id) {
	if (references == null) {
	    throw new NullPointerException("references cannot be null");
	}
	List refCopy = new ArrayList(references);
	if (refCopy.isEmpty()) {
	    throw new IllegalArgumentException("list of references must " +
	        "contain at least one entry");
	}
        for (int i = 0, size = refCopy.size(); i < size; i++) {
            if (!(refCopy.get(i) instanceof Reference)) {
                throw new ClassCastException
                    ("references["+i+"] is not a valid type");
            }
        }
	this.references = Collections.unmodifiableList(refCopy);
	this.id = id;
    }

    /**
     * Creates a <code>DOMManifest</code> from an element.
     *
     * @param manElem a Manifest element
     */
    public DOMManifest(Element manElem, XMLCryptoContext context,
	Provider provider) throws MarshalException {
        this.id = DOMUtils.getAttributeValue(manElem, "Id");
        Element refElem = DOMUtils.getFirstChildElement(manElem);
	List refs = new ArrayList();
	while (refElem != null) {
	    refs.add(new DOMReference(refElem, context, provider));
	    refElem = DOMUtils.getNextSiblingElement(refElem);
	}
	this.references = Collections.unmodifiableList(refs);
    }

    public String getId() {
	return id;
    }
    
    public List getReferences() {
        return references;
    }

    public void marshal(Node parent, String dsPrefix, DOMCryptoContext context)
	throws MarshalException {
        Document ownerDoc = DOMUtils.getOwnerDocument(parent);

        Element manElem = DOMUtils.createElement
            (ownerDoc, "Manifest", XMLSignature.XMLNS, dsPrefix);

        DOMUtils.setAttributeID(manElem, "Id", id);

	// add references
	for (int i = 0, size = references.size(); i < size; i++) {
	    DOMReference ref = (DOMReference) references.get(i);
	    ref.marshal(manElem, dsPrefix, context);
	}
        parent.appendChild(manElem);
    }

    public boolean equals(Object o) {
	if (this == o) {
            return true;
	}

	if (!(o instanceof Manifest)) {
            return false;
	}
        Manifest oman = (Manifest) o;

	boolean idsEqual = (id == null ? oman.getId() == null :
            id.equals(oman.getId()));

	return (idsEqual && references.equals(oman.getReferences()));
    }

    public int hashCode() {
	assert false : "hashCode not designed";
	return 46;
    }
}
