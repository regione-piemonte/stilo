// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGAnimateColorElement;

public class SVGOMAnimateColorElement extends SVGOMAnimationElement implements SVGAnimateColorElement
{
    protected SVGOMAnimateColorElement() {
    }
    
    public SVGOMAnimateColorElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "animateColor";
    }
    
    protected Node newNode() {
        return new SVGOMAnimateColorElement();
    }
}
