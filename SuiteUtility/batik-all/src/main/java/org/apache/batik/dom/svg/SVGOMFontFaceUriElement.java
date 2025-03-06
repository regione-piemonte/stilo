// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGFontFaceUriElement;

public class SVGOMFontFaceUriElement extends SVGOMElement implements SVGFontFaceUriElement
{
    protected SVGOMFontFaceUriElement() {
    }
    
    public SVGOMFontFaceUriElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "font-face-uri";
    }
    
    protected Node newNode() {
        return new SVGOMFontFaceUriElement();
    }
}
