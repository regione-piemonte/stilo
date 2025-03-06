// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGMetadataElement;

public class SVGOMMetadataElement extends SVGOMElement implements SVGMetadataElement
{
    protected SVGOMMetadataElement() {
    }
    
    public SVGOMMetadataElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "metadata";
    }
    
    protected Node newNode() {
        return new SVGOMMetadataElement();
    }
}
