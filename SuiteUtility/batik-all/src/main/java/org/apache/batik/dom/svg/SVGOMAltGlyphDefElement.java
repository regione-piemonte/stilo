// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGAltGlyphDefElement;

public class SVGOMAltGlyphDefElement extends SVGOMElement implements SVGAltGlyphDefElement
{
    protected SVGOMAltGlyphDefElement() {
    }
    
    public SVGOMAltGlyphDefElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "altGlyphDef";
    }
    
    protected Node newNode() {
        return new SVGOMAltGlyphDefElement();
    }
}
