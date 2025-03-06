// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGTextContentElement;
import org.apache.batik.dom.svg.SVGOMTextContentElement;

public class SVGOMFlowDivElement extends SVGOMTextContentElement implements SVGTextContentElement
{
    protected SVGOMFlowDivElement() {
    }
    
    public SVGOMFlowDivElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "flowDiv";
    }
    
    protected Node newNode() {
        return new SVGOMFlowDivElement();
    }
}
