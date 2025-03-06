// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg.SVGStylableElement;

public class SVGOMSubImageRefElement extends SVGStylableElement
{
    protected SVGOMSubImageRefElement() {
    }
    
    public SVGOMSubImageRefElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "subImageRef";
    }
    
    protected Node newNode() {
        return new SVGOMSubImageRefElement();
    }
}
