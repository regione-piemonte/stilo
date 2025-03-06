// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGFontFaceSrcElement;

public class SVGOMFontFaceSrcElement extends SVGOMElement implements SVGFontFaceSrcElement
{
    protected SVGOMFontFaceSrcElement() {
    }
    
    public SVGOMFontFaceSrcElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "font-face-src";
    }
    
    protected Node newNode() {
        return new SVGOMFontFaceSrcElement();
    }
}
