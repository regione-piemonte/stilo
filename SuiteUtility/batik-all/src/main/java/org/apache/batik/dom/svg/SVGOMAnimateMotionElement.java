// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGAnimateMotionElement;

public class SVGOMAnimateMotionElement extends SVGOMAnimationElement implements SVGAnimateMotionElement
{
    protected static final AttributeInitializer attributeInitializer;
    
    protected SVGOMAnimateMotionElement() {
    }
    
    public SVGOMAnimateMotionElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "animateMotion";
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMAnimateMotionElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMAnimateMotionElement();
    }
    
    static {
        (attributeInitializer = new AttributeInitializer(1)).addAttribute(null, null, "calcMode", "paced");
    }
}
