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
 * $Id: DOMExcC14NMethod.java 656799 2008-05-15 19:23:15Z mullan $
 */
package it.eng.utility.cryptosigner.provider.dom;

import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.w3c.dom.Element;

/**
 * DOM-based implementation of CanonicalizationMethod for Exclusive
 * Canonical XML algorithm (with or without comments).
 * Uses Apache XML-Sec Canonicalizer.
 *
 * @author Sean Mullan
 */
public final class DOMExcC14NMethod extends ApacheCanonicalizer {

    public void init(TransformParameterSpec params)
        throws InvalidAlgorithmParameterException {
        if (params != null) {
            if (!(params instanceof ExcC14NParameterSpec)) {
                throw new InvalidAlgorithmParameterException
                    ("params must be of type ExcC14NParameterSpec");
            }
	    this.params = (C14NMethodParameterSpec) params;
        }
    }

    public void init(XMLStructure parent, XMLCryptoContext context)
        throws InvalidAlgorithmParameterException {
	super.init(parent, context);
	Element paramsElem = DOMUtils.getFirstChildElement(transformElem);
	if (paramsElem == null) {
	    this.params = null;
	    this.inclusiveNamespaces = null;
	    return;
	}
	unmarshalParams(paramsElem);
    }

    private void unmarshalParams(Element paramsElem) {
	String prefixListAttr = paramsElem.getAttributeNS(null, "PrefixList");
	this.inclusiveNamespaces = prefixListAttr;
	int begin = 0;
	int end = prefixListAttr.indexOf(' ');
	List prefixList = new ArrayList();
	while (end != -1) {
	    prefixList.add(prefixListAttr.substring(begin, end));
	    begin = end + 1;
	    end = prefixListAttr.indexOf(' ', begin);
	}
	if (begin <= prefixListAttr.length()) {
            prefixList.add(prefixListAttr.substring(begin));
        }
	this.params = new ExcC14NParameterSpec(prefixList);
    }

    public void marshalParams(XMLStructure parent, XMLCryptoContext context)
        throws MarshalException {

	super.marshalParams(parent, context);
	AlgorithmParameterSpec spec = getParameterSpec();
	if (spec == null) {
	    return;
	}

	String prefix = 
	    DOMUtils.getNSPrefix(context, CanonicalizationMethod.EXCLUSIVE);
        Element excElem = DOMUtils.createElement
	    (ownerDoc, "InclusiveNamespaces", 
	     CanonicalizationMethod.EXCLUSIVE, prefix);
	if (prefix == null || prefix.length() == 0) {
	    excElem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns",
	        CanonicalizationMethod.EXCLUSIVE);
	} else {
	    excElem.setAttributeNS("http://www.w3.org/2000/xmlns/", 
		"xmlns:" + prefix, CanonicalizationMethod.EXCLUSIVE);
	}

	ExcC14NParameterSpec params = (ExcC14NParameterSpec) spec;
	StringBuffer prefixListAttr = new StringBuffer("");
	List prefixList = params.getPrefixList();
	for (int i = 0, size = prefixList.size(); i < size; i++) {
	    prefixListAttr.append((String) prefixList.get(i));
	    if (i < size - 1) {
		prefixListAttr.append(" ");
	    }
	}
	DOMUtils.setAttribute(excElem, "PrefixList", prefixListAttr.toString());
	this.inclusiveNamespaces = prefixListAttr.toString();
	transformElem.appendChild(excElem);
    }

    public String getParamsNSURI() {
	return CanonicalizationMethod.EXCLUSIVE;
    }

    public Data transform(Data data, XMLCryptoContext xc)
	throws TransformException {

	// ignore comments if dereferencing same-document URI that require
	// you to omit comments, even if the Transform says otherwise -
	// this is to be compliant with section 4.3.3.3 of W3C Rec.
	if (data instanceof DOMSubTreeData) {
	    DOMSubTreeData subTree = (DOMSubTreeData) data;
	    if (subTree.excludeComments()) {
		try {
                    apacheCanonicalizer = Canonicalizer.getInstance
			(CanonicalizationMethod.EXCLUSIVE);
		} catch (InvalidCanonicalizerException ice) {
		    throw new TransformException
			("Couldn't find Canonicalizer for: " +
			 CanonicalizationMethod.EXCLUSIVE + ": " +
			 ice.getMessage(), ice);
		}
            }
	}

	return canonicalize(data, xc);
    }
}
