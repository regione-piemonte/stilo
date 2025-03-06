// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGFEMergeElement;

public class SVGOMFEMergeElement extends SVGOMFilterPrimitiveStandardAttributes implements SVGFEMergeElement
{
    protected SVGOMFEMergeElement() {
    }
    
    public SVGOMFEMergeElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "feMerge";
    }
    
    protected Node newNode() {
        return new SVGOMFEMergeElement();
    }
}
