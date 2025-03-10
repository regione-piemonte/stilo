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
 * $Id: DOMSignedInfo.java 793943 2009-07-14 15:33:19Z coheigea $
 */
package it.eng.utility.cryptosigner.provider.dom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;

import org.apache.xml.security.utils.Base64;
import org.apache.xml.security.utils.UnsyncBufferedOutputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * DOM-based implementation of SignedInfo.
 *
 * @author Sean Mullan
 */
public final class DOMSignedInfo extends DOMStructure implements SignedInfo {

    private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
    private List references;
    private CanonicalizationMethod canonicalizationMethod;
    private SignatureMethod signatureMethod;
    private String id;
    private Document ownerDoc;
    private Element localSiElem;
    private InputStream canonData;

    /**
     * Creates a <code>DOMSignedInfo</code> from the specified parameters. Use
     * this constructor when the <code>Id</code> is not specified.
     *
     * @param cm the canonicalization method
     * @param sm the signature method
     * @param references the list of references. The list is copied.
     */
    public DOMSignedInfo(CanonicalizationMethod cm, SignatureMethod sm, 
	List references) {
        if (cm == null || sm == null || references == null) {
            throw new NullPointerException();
        }
        this.canonicalizationMethod = cm;
        this.signatureMethod = sm;
	this.references = Collections.unmodifiableList
	    (new ArrayList(references));
	if (this.references.isEmpty()) {
	    throw new IllegalArgumentException("list of references must " +
	        "contain at least one entry");
	}
	for (int i = 0, size = this.references.size(); i < size; i++) {
	    Object obj = this.references.get(i);
	    if (!(obj instanceof Reference)) {
		throw new ClassCastException("list of references contains " +
		    "an illegal type");
	    }
	}
    }

    /**
     * Creates a <code>DOMSignedInfo</code> from the specified parameters.
     *
     * @param cm the canonicalization method
     * @param sm the signature method
     * @param references the list of references. The list is copied.
     * @param id an optional identifer that will allow this
     *    <code>SignedInfo</code> to be referenced by other signatures and
     *    objects
     */
    public DOMSignedInfo(CanonicalizationMethod cm, SignatureMethod sm, 
	List references, String id) {
        this(cm, sm, references);
        this.id = id;
    }

    /**
     * Creates a <code>DOMSignedInfo</code> from an element.
     *
     * @param siElem a SignedInfo element
     */
    public DOMSignedInfo(Element siElem, XMLCryptoContext context,
	Provider provider) throws MarshalException {
	localSiElem = siElem;
	ownerDoc = siElem.getOwnerDocument();

        // get Id attribute, if specified
        id = DOMUtils.getAttributeValue(siElem, "Id");

        // unmarshal CanonicalizationMethod
	Element cmElem = DOMUtils.getFirstChildElement(siElem);
	canonicalizationMethod = new DOMCanonicalizationMethod
	    (cmElem, context, provider);

        // unmarshal SignatureMethod
	Element smElem = DOMUtils.getNextSiblingElement(cmElem);
	signatureMethod = DOMSignatureMethod.unmarshal(smElem);

	// unmarshal References
	ArrayList refList = new ArrayList(5);
	Element refElem = DOMUtils.getNextSiblingElement(smElem);
	while (refElem != null) {
	    refList.add(new DOMReference(refElem, context, provider));
	    refElem = DOMUtils.getNextSiblingElement(refElem);
	}
	references = Collections.unmodifiableList(refList);
    }

    public CanonicalizationMethod getCanonicalizationMethod() {
        return canonicalizationMethod;
    }

    public SignatureMethod getSignatureMethod() {
        return signatureMethod;
    }

    public String getId() {
        return id;
    }

    public List getReferences() {
	return references;
    }

    public InputStream getCanonicalizedData() {
	return canonData;
    }

    public void canonicalize(XMLCryptoContext context,ByteArrayOutputStream bos)
	throws XMLSignatureException {

	if (context == null) {
            throw new NullPointerException("context cannot be null");
	}

	OutputStream os = new UnsyncBufferedOutputStream(bos);
        try {
            os.close();
        } catch (IOException e) {
            // Impossible
        }

	DOMSubTreeData subTree = new DOMSubTreeData(localSiElem, true);

	try {
	    ((DOMCanonicalizationMethod) 
		canonicalizationMethod).canonicalize(subTree, context, os);
	} catch (TransformException te) {
	    throw new XMLSignatureException(te);
	}

	byte[] signedInfoBytes = bos.toByteArray();

        // this whole block should only be done if logging is enabled
	if (log.isLoggable(Level.FINE)) {
            log.log(Level.FINE, "Canonicalized SignedInfo:"); 
            StringBuffer sb = new StringBuffer(signedInfoBytes.length);
            for (int i = 0; i < signedInfoBytes.length; i++) {
                sb.append((char) signedInfoBytes[i]);
            }
            log.log(Level.FINE, sb.toString());
	    log.log(Level.FINE, "Data to be signed/verified:"
                + Base64.encode(signedInfoBytes));
	}

	this.canonData = new ByteArrayInputStream(signedInfoBytes);
    }

    public void marshal(Node parent, String dsPrefix, DOMCryptoContext context)
	throws MarshalException {
        ownerDoc = DOMUtils.getOwnerDocument(parent);

	Element siElem = DOMUtils.createElement
	    (ownerDoc, "SignedInfo", XMLSignature.XMLNS, dsPrefix);

	// create and append CanonicalizationMethod element
	DOMCanonicalizationMethod dcm = 
	    (DOMCanonicalizationMethod) canonicalizationMethod;
	dcm.marshal(siElem, dsPrefix, context); 

	// create and append SignatureMethod element
	((DOMSignatureMethod) signatureMethod).marshal
	    (siElem, dsPrefix, context);

	// create and append Reference elements
	for (int i = 0, size = references.size(); i < size; i++) {
	    DOMReference reference = (DOMReference) references.get(i);
	    reference.marshal(siElem, dsPrefix, context);
	}

	// append Id attribute
        DOMUtils.setAttributeID(siElem, "Id", id);
	    
	parent.appendChild(siElem);
	localSiElem = siElem;
    }

    public boolean equals(Object o) {
	if (this == o) {
	    return true;
	}

	if (!(o instanceof SignedInfo)) {
	    return false;
	}
	SignedInfo osi = (SignedInfo) o;

	boolean idEqual = (id == null ? osi.getId() == null : 
	    id.equals(osi.getId()));

	return (canonicalizationMethod.equals(osi.getCanonicalizationMethod()) 
	    && signatureMethod.equals(osi.getSignatureMethod()) && 
	    references.equals(osi.getReferences()) && idEqual);
    }

    public int hashCode() {
	assert false : "hashCode not designed";
	return 59;
    }
}
