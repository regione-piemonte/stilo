// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGDefsElement;

public class SVGOMDefsElement extends SVGGraphicsElement implements SVGDefsElement
{
    protected SVGOMDefsElement() {
    }
    
    public SVGOMDefsElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "defs";
    }
    
    protected Node newNode() {
        return new SVGOMDefsElement();
    }
}
