// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGColorProfileElement;

public class SVGOMColorProfileElement extends SVGOMURIReferenceElement implements SVGColorProfileElement
{
    protected static final AttributeInitializer attributeInitializer;
    
    protected SVGOMColorProfileElement() {
    }
    
    public SVGOMColorProfileElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "color-profile";
    }
    
    public String getLocal() {
        return this.getAttributeNS(null, "local");
    }
    
    public void setLocal(final String s) throws DOMException {
        this.setAttributeNS(null, "local", s);
    }
    
    public String getName() {
        return this.getAttributeNS(null, "name");
    }
    
    public void setName(final String s) throws DOMException {
        this.setAttributeNS(null, "name", s);
    }
    
    public short getRenderingIntent() {
        final Attr attributeNodeNS = this.getAttributeNodeNS(null, "rendering-intent");
        if (attributeNodeNS == null) {
            return 1;
        }
        final String value = attributeNodeNS.getValue();
        switch (value.length()) {
            case 4: {
                if (value.equals("auto")) {
                    return 1;
                }
                break;
            }
            case 10: {
                if (value.equals("perceptual")) {
                    return 2;
                }
                if (value.equals("saturate")) {
                    return 4;
                }
                break;
            }
            case 21: {
                if (value.equals("absolute-colorimetric")) {
                    return 5;
                }
                if (value.equals("relative-colorimetric")) {
                    return 3;
                }
                break;
            }
        }
        return 0;
    }
    
    public void setRenderingIntent(final short n) throws DOMException {
        switch (n) {
            case 1: {
                this.setAttributeNS(null, "rendering-intent", "auto");
                break;
            }
            case 2: {
                this.setAttributeNS(null, "rendering-intent", "perceptual");
                break;
            }
            case 3: {
                this.setAttributeNS(null, "rendering-intent", "relative-colorimetric");
                break;
            }
            case 4: {
                this.setAttributeNS(null, "rendering-intent", "saturate");
                break;
            }
            case 5: {
                this.setAttributeNS(null, "rendering-intent", "absolute-colorimetric");
                break;
            }
        }
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMColorProfileElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMColorProfileElement();
    }
    
    static {
        (attributeInitializer = new AttributeInitializer(5)).addAttribute(null, null, "rendering-intent", "auto");
        SVGOMColorProfileElement.attributeInitializer.addAttribute("http://www.w3.org/2000/xmlns/", null, "xmlns:xlink", "http://www.w3.org/1999/xlink");
        SVGOMColorProfileElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "type", "simple");
        SVGOMColorProfileElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "show", "other");
        SVGOMColorProfileElement.attributeInitializer.addAttribute("http://www.w3.org/1999/xlink", "xlink", "actuate", "onLoad");
    }
}
