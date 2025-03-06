// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGHKernElement;

public class SVGOMHKernElement extends SVGOMElement implements SVGHKernElement
{
    protected SVGOMHKernElement() {
    }
    
    public SVGOMHKernElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "hkern";
    }
    
    protected Node newNode() {
        return new SVGOMHKernElement();
    }
}
