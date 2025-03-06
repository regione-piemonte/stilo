// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;

public class XBLOMHandlerGroupElement extends XBLOMElement
{
    protected XBLOMHandlerGroupElement() {
    }
    
    public XBLOMHandlerGroupElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "handlerGroup";
    }
    
    protected Node newNode() {
        return new XBLOMHandlerGroupElement();
    }
}
