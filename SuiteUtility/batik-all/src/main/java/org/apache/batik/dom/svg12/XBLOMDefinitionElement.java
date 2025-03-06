// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;

public class XBLOMDefinitionElement extends XBLOMElement
{
    protected XBLOMDefinitionElement() {
    }
    
    public XBLOMDefinitionElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "definition";
    }
    
    protected Node newNode() {
        return new XBLOMDefinitionElement();
    }
    
    public String getElementNamespaceURI() {
        final String prefix = DOMUtilities.getPrefix(this.getAttributeNS(null, "element"));
        final String lookupNamespaceURI = this.lookupNamespaceURI(prefix);
        if (lookupNamespaceURI == null) {
            throw this.createDOMException((short)14, "prefix", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), prefix });
        }
        return lookupNamespaceURI;
    }
    
    public String getElementLocalName() {
        return DOMUtilities.getLocalName(this.getAttributeNS(null, "element"));
    }
}
