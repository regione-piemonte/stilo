// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGAltGlyphItemElement;

public class SVGOMAltGlyphItemElement extends SVGOMElement implements SVGAltGlyphItemElement
{
    protected SVGOMAltGlyphItemElement() {
    }
    
    public SVGOMAltGlyphItemElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "altGlyphItem";
    }
    
    protected Node newNode() {
        return new SVGOMAltGlyphItemElement();
    }
}
