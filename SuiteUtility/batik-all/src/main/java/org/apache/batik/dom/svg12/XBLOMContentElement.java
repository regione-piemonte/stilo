// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;

public class XBLOMContentElement extends XBLOMElement
{
    protected XBLOMContentElement() {
    }
    
    public XBLOMContentElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "content";
    }
    
    protected Node newNode() {
        return new XBLOMContentElement();
    }
}
