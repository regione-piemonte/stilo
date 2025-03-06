// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGTitleElement;

public class SVGOMTitleElement extends SVGDescriptiveElement implements SVGTitleElement
{
    protected SVGOMTitleElement() {
    }
    
    public SVGOMTitleElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "title";
    }
    
    protected Node newNode() {
        return new SVGOMTitleElement();
    }
}
