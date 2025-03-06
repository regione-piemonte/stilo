// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGVKernElement;

public class SVGOMVKernElement extends SVGOMElement implements SVGVKernElement
{
    protected SVGOMVKernElement() {
    }
    
    public SVGOMVKernElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "vkern";
    }
    
    protected Node newNode() {
        return new SVGOMVKernElement();
    }
}
