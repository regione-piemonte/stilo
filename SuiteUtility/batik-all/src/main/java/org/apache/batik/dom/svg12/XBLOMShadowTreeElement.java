// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg.IdContainer;
import org.apache.batik.dom.xbl.XBLShadowTreeElement;

public class XBLOMShadowTreeElement extends XBLOMElement implements XBLShadowTreeElement, IdContainer
{
    protected XBLOMShadowTreeElement() {
    }
    
    public XBLOMShadowTreeElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "shadowTree";
    }
    
    protected Node newNode() {
        return new XBLOMShadowTreeElement();
    }
    
    public Element getElementById(final String s) {
        return this.getElementById(s, this);
    }
    
    protected Element getElementById(final String anObject, final Node node) {
        if (node.getNodeType() == 1 && ((Element)node).getAttributeNS(null, "id").equals(anObject)) {
            return (Element)node;
        }
        for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            final Element elementById = this.getElementById(anObject, node2);
            if (elementById != null) {
                return elementById;
            }
        }
        return null;
    }
    
    public Node getCSSParentNode() {
        return this.ownerDocument.getXBLManager().getXblBoundElement(this);
    }
}
