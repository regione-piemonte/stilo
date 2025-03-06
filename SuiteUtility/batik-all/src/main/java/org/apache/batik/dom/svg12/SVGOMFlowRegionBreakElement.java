// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg.SVGOMTextPositioningElement;

public class SVGOMFlowRegionBreakElement extends SVGOMTextPositioningElement
{
    protected SVGOMFlowRegionBreakElement() {
    }
    
    public SVGOMFlowRegionBreakElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "flowRegionBreak";
    }
    
    protected Node newNode() {
        return new SVGOMFlowRegionBreakElement();
    }
}
