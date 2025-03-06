// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGFontFaceNameElement;

public class SVGOMFontFaceNameElement extends SVGOMElement implements SVGFontFaceNameElement
{
    protected SVGOMFontFaceNameElement() {
    }
    
    public SVGOMFontFaceNameElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "font-face-name";
    }
    
    protected Node newNode() {
        return new SVGOMFontFaceNameElement();
    }
}
