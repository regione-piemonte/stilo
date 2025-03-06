// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGAltGlyphElement;

public class SVGOMAltGlyphElement extends SVGURIReferenceTextPositioningElement implements SVGAltGlyphElement
{
    protected static final AttributeInitializer attributeInitializer;
    
    protected SVGOMAltGlyphElement() {
    }
    
    public SVGOMAltGlyphElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "altGlyph";
    }
    
    public String getGlyphRef() {
        return this.getAttributeNS(null, "glyphRef");
    }
    
    public void setGlyphRef(final String s) throws DOMException {
        this.setAttributeNS(null, "glyphRef", s);
    }
    
    public String getFormat() {
        return this.getAttributeNS(null, "format");
    }
    
    public void setFormat(final String s) throws DOMException {
        this.setAttributeNS(null, "format", s);
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMAltGlyphElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMAltGlyphElement();
    }
    
    static {
        (attributeInitializer = new AttributeInitializer(4)).addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns:xlink", "http://www.w3.org/1999/xlink");
        SVGOMAltGlyphElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "type", "simple");
        SVGOMAltGlyphElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "show", "other");
        SVGOMAltGlyphElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "actuate", "onLoad");
    }
}
