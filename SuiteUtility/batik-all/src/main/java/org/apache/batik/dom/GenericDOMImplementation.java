// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;
import org.w3c.dom.DOMImplementation;

public class GenericDOMImplementation extends AbstractDOMImplementation
{
    protected static final DOMImplementation DOM_IMPLEMENTATION;
    
    public static DOMImplementation getDOMImplementation() {
        return GenericDOMImplementation.DOM_IMPLEMENTATION;
    }
    
    public DocumentType createDocumentType(final String s, final String s2, final String s3) {
        throw new DOMException((short)9, "Doctype not supported");
    }
    
    public Document createDocument(final String s, final String s2, final DocumentType documentType) throws DOMException {
        final GenericDocument genericDocument = new GenericDocument(documentType, this);
        genericDocument.appendChild(genericDocument.createElementNS(s, s2));
        return genericDocument;
    }
    
    static {
        DOM_IMPLEMENTATION = new GenericDOMImplementation();
    }
}
