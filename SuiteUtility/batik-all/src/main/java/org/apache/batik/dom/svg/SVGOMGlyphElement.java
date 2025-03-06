// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGGlyphElement;

public class SVGOMGlyphElement extends SVGStylableElement implements SVGGlyphElement
{
    protected SVGOMGlyphElement() {
    }
    
    public SVGOMGlyphElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "glyph";
    }
    
    protected Node newNode() {
        return new SVGOMGlyphElement();
    }
}
