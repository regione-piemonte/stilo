// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGDescElement;

public class SVGOMDescElement extends SVGDescriptiveElement implements SVGDescElement
{
    protected SVGOMDescElement() {
    }
    
    public SVGOMDescElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "desc";
    }
    
    protected Node newNode() {
        return new SVGOMDescElement();
    }
}
