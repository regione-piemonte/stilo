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
 * $Id: DOMTransform.java 793943 2009-07-14 15:33:19Z coheigea $
 */
package it.eng.utility.cryptosigner.provider.dom;

import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;

import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.TransformService;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.dom.DOMSignContext;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * DOM-based abstract implementation of Transform.
 *
 * @author Sean Mullan
 */
public class DOMTransform extends DOMStructure implements Transform {

    protected TransformService spi;

    /**
     * Creates a <code>DOMTransform</code>.
     *
     * @param spi the TransformService
     */
    public DOMTransform(TransformService spi) {
	this.spi = spi;
    }

    /**
     * Creates a <code>DOMTransform</code> from an element. This constructor
     * invokes the abstract  method to
     * unmarshal any algorithm-specific input parameters.
     *
     * @param transElem a Transform element
     */
    public DOMTransform(Element transElem, XMLCryptoContext context,
	Provider provider) throws MarshalException {
        String algorithm = DOMUtils.getAttributeValue(transElem, "Algorithm");
	try {
	    spi = TransformService.getInstance(algorithm, "DOM");
	} catch (NoSuchAlgorithmException e1) {
	    try {
	        spi = TransformService.getInstance(algorithm, "DOM", provider);
	    } catch (NoSuchAlgorithmException e2) {
	        throw new MarshalException(e2);
	    }
	}
	try {
            spi.init(new javax.xml.crypto.dom.DOMStructure(transElem), context);
	} catch (InvalidAlgorithmParameterException iape) {
	    throw new MarshalException(iape);
	}
    }

    public final AlgorithmParameterSpec getParameterSpec() {
	return spi.getParameterSpec();
    }

    public final String getAlgorithm() {
	return spi.getAlgorithm();
    }

    /**
     * This method invokes the abstract
     * method to marshal any algorithm-specific parameters.
     */
    public void marshal(Node parent, String dsPrefix, DOMCryptoContext context)
	throws MarshalException {
        Document ownerDoc = DOMUtils.getOwnerDocument(parent);

	Element transformElem = null;
	if (parent.getLocalName().equals("Transforms")) {
            transformElem = DOMUtils.createElement
		(ownerDoc, "Transform", XMLSignature.XMLNS, dsPrefix);
	} else {
            transformElem = DOMUtils.createElement
            (ownerDoc, "CanonicalizationMethod", XMLSignature.XMLNS, dsPrefix);
	}
	DOMUtils.setAttribute(transformElem, "Algorithm", getAlgorithm());

        spi.marshalParams
	    (new javax.xml.crypto.dom.DOMStructure(transformElem), context);

        parent.appendChild(transformElem);
    }

    /**
     * Transforms the specified data using the underlying transform algorithm.
     *
     * @param data the data to be transformed
     * @param xc the <code>XMLCryptoContext</code> containing
     *    additional context (may be <code>null</code> if not applicable)
     * @return the transformed data
     * @throws TransformException if an unexpected error occurs while
     *    executing the transform
     */
    public Data transform(Data data, XMLCryptoContext xc) 
	throws TransformException {
	return spi.transform(data, xc);
    }

    /**
     * Transforms the specified data using the underlying transform algorithm.
     *
     * @param data the data to be transformed
     * @param xc the <code>XMLCryptoContext</code> containing
     *    additional context (may be <code>null</code> if not applicable)
     * @param os the <code>OutputStream</code> that should be used to write
     *    the transformed data to
     * @return the transformed data
     * @throws TransformException  if an unexpected error occurs while
     *    executing the transform
     */
    public Data transform(Data data, XMLCryptoContext xc, OutputStream os) 
	throws TransformException {
	return spi.transform(data, xc, os);
    }

    public boolean equals(Object o) {
	if (this == o) {
            return true;
	}

        if (!(o instanceof Transform)) {
            return false;
	}
        Transform otransform = (Transform) o;

	return (getAlgorithm().equals(otransform.getAlgorithm()) && 
	    DOMUtils.paramsEqual
		(getParameterSpec(), otransform.getParameterSpec()));
    }

    public int hashCode() {
	assert false : "hashCode not designed";
	return 58;
    }

    /**
     * Transforms the specified data using the underlying transform algorithm.
     * This method invokes the method and passes it
     * the specified <code>DOMSignContext</code> before transforming the data.
     *
     * @param data the data to be transformed
     * @param sc the <code>XMLCryptoContext</code> containing
     *    additional context (may be <code>null</code> if not applicable)
     * @param context the marshalling context
     * @return the transformed data
     * @throws MarshalException if an exception occurs while marshalling
     * @throws NullPointerException if <code>data</code> or <code>context</code> 
     *    is <code>null</code>
     * @throws XMLSignatureException if an unexpected error occurs while
     *    executing the transform
     */
    Data transform(Data data, XMLCryptoContext xc, DOMSignContext context) 
	throws MarshalException, TransformException {
        marshal(context.getParent(),
            DOMUtils.getSignaturePrefix(context), context);
	return transform(data, xc);
    }
}
