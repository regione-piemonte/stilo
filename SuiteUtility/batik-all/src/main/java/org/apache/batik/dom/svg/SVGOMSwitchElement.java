// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGSwitchElement;

public class SVGOMSwitchElement extends SVGGraphicsElement implements SVGSwitchElement
{
    protected SVGOMSwitchElement() {
    }
    
    public SVGOMSwitchElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "switch";
    }
    
    protected Node newNode() {
        return new SVGOMSwitchElement();
    }
}
