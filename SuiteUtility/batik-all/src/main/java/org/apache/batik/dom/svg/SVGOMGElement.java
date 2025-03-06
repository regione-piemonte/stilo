// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGGElement;

public class SVGOMGElement extends SVGGraphicsElement implements SVGGElement
{
    protected SVGOMGElement() {
    }
    
    public SVGOMGElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "g";
    }
    
    protected Node newNode() {
        return new SVGOMGElement();
    }
}
