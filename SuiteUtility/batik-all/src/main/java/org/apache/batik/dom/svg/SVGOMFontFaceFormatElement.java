// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGFontFaceFormatElement;

public class SVGOMFontFaceFormatElement extends SVGOMElement implements SVGFontFaceFormatElement
{
    protected SVGOMFontFaceFormatElement() {
    }
    
    public SVGOMFontFaceFormatElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "font-face-format";
    }
    
    protected Node newNode() {
        return new SVGOMFontFaceFormatElement();
    }
}
