// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGAnimateTransformElement;

public class SVGOMAnimateTransformElement extends SVGOMAnimationElement implements SVGAnimateTransformElement
{
    protected static final AttributeInitializer attributeInitializer;
    
    protected SVGOMAnimateTransformElement() {
    }
    
    public SVGOMAnimateTransformElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "animateTransform";
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMAnimateTransformElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMAnimateTransformElement();
    }
    
    static {
        (attributeInitializer = new AttributeInitializer(1)).addAttribute(null, null, "type", "translate");
    }
}
