// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGFEFuncBElement;

public class SVGOMFEFuncBElement extends SVGOMComponentTransferFunctionElement implements SVGFEFuncBElement
{
    protected SVGOMFEFuncBElement() {
    }
    
    public SVGOMFEFuncBElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "feFuncB";
    }
    
    protected Node newNode() {
        return new SVGOMFEFuncBElement();
    }
}
