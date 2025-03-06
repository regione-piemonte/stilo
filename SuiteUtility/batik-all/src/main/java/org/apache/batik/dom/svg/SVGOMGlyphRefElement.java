// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGAnimatedString;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGGlyphRefElement;

public class SVGOMGlyphRefElement extends SVGStylableElement implements SVGGlyphRefElement
{
    protected static final AttributeInitializer attributeInitializer;
    protected SVGOMAnimatedString href;
    
    protected SVGOMGlyphRefElement() {
    }
    
    public SVGOMGlyphRefElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.initializeLiveAttributes();
    }
    
    protected void initializeAllLiveAttributes() {
        super.initializeAllLiveAttributes();
        this.initializeLiveAttributes();
    }
    
    private void initializeLiveAttributes() {
        this.href = this.createLiveAnimatedString("http://www.w3.org/1999/xlink", "href");
    }
    
    public String getLocalName() {
        return "glyphRef";
    }
    
    public SVGAnimatedString getHref() {
        return (SVGAnimatedString)this.href;
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
    
    public float getX() {
        return Float.parseFloat(this.getAttributeNS(null, "x"));
    }
    
    public void setX(final float f) throws DOMException {
        this.setAttributeNS(null, "x", String.valueOf(f));
    }
    
    public float getY() {
        return Float.parseFloat(this.getAttributeNS(null, "y"));
    }
    
    public void setY(final float f) throws DOMException {
        this.setAttributeNS(null, "y", String.valueOf(f));
    }
    
    public float getDx() {
        return Float.parseFloat(this.getAttributeNS(null, "dx"));
    }
    
    public void setDx(final float f) throws DOMException {
        this.setAttributeNS(null, "dx", String.valueOf(f));
    }
    
    public float getDy() {
        return Float.parseFloat(this.getAttributeNS(null, "dy"));
    }
    
    public void setDy(final float f) throws DOMException {
        this.setAttributeNS(null, "dy", String.valueOf(f));
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMGlyphRefElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMGlyphRefElement();
    }
    
    static {
        (attributeInitializer = new AttributeInitializer(4)).addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns:xlink", "http://www.w3.org/1999/xlink");
        SVGOMGlyphRefElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "type", "simple");
        SVGOMGlyphRefElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "show", "other");
        SVGOMGlyphRefElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "actuate", "onLoad");
    }
}
