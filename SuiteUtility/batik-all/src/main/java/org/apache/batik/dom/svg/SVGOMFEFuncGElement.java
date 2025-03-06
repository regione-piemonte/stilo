// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGFEFuncGElement;

public class SVGOMFEFuncGElement extends SVGOMComponentTransferFunctionElement implements SVGFEFuncGElement
{
    protected SVGOMFEFuncGElement() {
    }
    
    public SVGOMFEFuncGElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "feFuncG";
    }
    
    protected Node newNode() {
        return new SVGOMFEFuncGElement();
    }
}
