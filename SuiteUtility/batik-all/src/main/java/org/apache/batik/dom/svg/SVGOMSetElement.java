// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGSetElement;

public class SVGOMSetElement extends SVGOMAnimationElement implements SVGSetElement
{
    protected SVGOMSetElement() {
    }
    
    public SVGOMSetElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "set";
    }
    
    protected Node newNode() {
        return new SVGOMSetElement();
    }
}
