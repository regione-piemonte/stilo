// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.apache.batik.dom.util.DOMUtilities;

public abstract class AbstractAttrNS extends AbstractAttr
{
    protected String namespaceURI;
    
    protected AbstractAttrNS() {
    }
    
    protected AbstractAttrNS(String s, final String anObject, final AbstractDocument abstractDocument) throws DOMException {
        super(anObject, abstractDocument);
        if (s != null && s.length() == 0) {
            s = null;
        }
        this.namespaceURI = s;
        final String prefix = DOMUtilities.getPrefix(anObject);
        if (!abstractDocument.getStrictErrorChecking()) {
            return;
        }
        if (prefix != null) {
            if (s == null || ("xml".equals(prefix) && !"http://www.w3.org/XML/1998/namespace".equals(s)) || ("xmlns".equals(prefix) && !"http://www.w3.org/2000/xmlns/".equals(s))) {
                throw this.createDOMException((short)14, "namespace.uri", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), s });
            }
        }
        else if ("xmlns".equals(anObject) && !"http://www.w3.org/2000/xmlns/".equals(s)) {
            throw this.createDOMException((short)14, "namespace.uri", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), s });
        }
    }
    
    public String getNamespaceURI() {
        return this.namespaceURI;
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        ((AbstractAttrNS)node).namespaceURI = this.namespaceURI;
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        ((AbstractAttrNS)node).namespaceURI = this.namespaceURI;
        return node;
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        ((AbstractAttrNS)node).namespaceURI = this.namespaceURI;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        ((AbstractAttrNS)node).namespaceURI = this.namespaceURI;
        return node;
    }
}
