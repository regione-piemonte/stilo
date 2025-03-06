// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGMissingGlyphElement;

public class SVGOMMissingGlyphElement extends SVGStylableElement implements SVGMissingGlyphElement
{
    protected SVGOMMissingGlyphElement() {
    }
    
    public SVGOMMissingGlyphElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "missing-glyph";
    }
    
    protected Node newNode() {
        return new SVGOMMissingGlyphElement();
    }
}
