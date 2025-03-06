// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg.SVGOMTextPositioningElement;

public class FlowRegionBreakElement extends SVGOMTextPositioningElement implements BatikExtConstants
{
    protected FlowRegionBreakElement() {
    }
    
    public FlowRegionBreakElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "flowRegionBreak";
    }
    
    public String getNamespaceURI() {
        return "http://xml.apache.org/batik/ext";
    }
    
    protected Node newNode() {
        return new FlowRegionBreakElement();
    }
}
