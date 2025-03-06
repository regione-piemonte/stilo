// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGFEFuncRElement;

public class SVGOMFEFuncRElement extends SVGOMComponentTransferFunctionElement implements SVGFEFuncRElement
{
    protected SVGOMFEFuncRElement() {
    }
    
    public SVGOMFEFuncRElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "feFuncR";
    }
    
    protected Node newNode() {
        return new SVGOMFEFuncRElement();
    }
}
