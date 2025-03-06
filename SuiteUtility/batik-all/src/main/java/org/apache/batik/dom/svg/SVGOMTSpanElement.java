// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGTSpanElement;

public class SVGOMTSpanElement extends SVGOMTextPositioningElement implements SVGTSpanElement
{
    protected SVGOMTSpanElement() {
    }
    
    public SVGOMTSpanElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "tspan";
    }
    
    protected Node newNode() {
        return new SVGOMTSpanElement();
    }
}
