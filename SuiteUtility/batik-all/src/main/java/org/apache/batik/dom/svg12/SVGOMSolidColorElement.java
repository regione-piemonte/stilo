// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg.SVGStylableElement;

public class SVGOMSolidColorElement extends SVGStylableElement
{
    protected SVGOMSolidColorElement() {
    }
    
    public SVGOMSolidColorElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "solidColor";
    }
    
    protected Node newNode() {
        return new SVGOMSolidColorElement();
    }
}
