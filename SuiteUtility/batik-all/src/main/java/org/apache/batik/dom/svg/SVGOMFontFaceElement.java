// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGFontFaceElement;

public class SVGOMFontFaceElement extends SVGOMElement implements SVGFontFaceElement
{
    protected SVGOMFontFaceElement() {
    }
    
    public SVGOMFontFaceElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "font-face";
    }
    
    protected Node newNode() {
        return new SVGOMFontFaceElement();
    }
}
