// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGDefinitionSrcElement;

public class SVGOMDefinitionSrcElement extends SVGOMElement implements SVGDefinitionSrcElement
{
    protected SVGOMDefinitionSrcElement() {
    }
    
    public SVGOMDefinitionSrcElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "definition-src";
    }
    
    protected Node newNode() {
        return new SVGOMDefinitionSrcElement();
    }
}
