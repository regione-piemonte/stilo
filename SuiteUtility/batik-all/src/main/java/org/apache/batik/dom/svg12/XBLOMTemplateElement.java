// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;

public class XBLOMTemplateElement extends XBLOMElement
{
    protected XBLOMTemplateElement() {
    }
    
    public XBLOMTemplateElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "template";
    }
    
    protected Node newNode() {
        return new XBLOMTemplateElement();
    }
}
