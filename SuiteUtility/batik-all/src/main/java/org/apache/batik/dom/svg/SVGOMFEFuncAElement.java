// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGFEFuncAElement;

public class SVGOMFEFuncAElement extends SVGOMComponentTransferFunctionElement implements SVGFEFuncAElement
{
    protected SVGOMFEFuncAElement() {
    }
    
    public SVGOMFEFuncAElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "feFuncA";
    }
    
    protected Node newNode() {
        return new SVGOMFEFuncAElement();
    }
}
