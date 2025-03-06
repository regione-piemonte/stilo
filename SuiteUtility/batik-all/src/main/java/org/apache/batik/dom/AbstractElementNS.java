// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.apache.batik.dom.util.DOMUtilities;

public abstract class AbstractElementNS extends AbstractElement
{
    protected String namespaceURI;
    
    protected AbstractElementNS() {
    }
    
    protected AbstractElementNS(String s, final String s2, final AbstractDocument abstractDocument) throws DOMException {
        super(s2, abstractDocument);
        if (s != null && s.length() == 0) {
            s = null;
        }
        this.namespaceURI = s;
        final String prefix = DOMUtilities.getPrefix(s2);
        if (prefix != null && (s == null || ("xml".equals(prefix) && !"http://www.w3.org/XML/1998/namespace".equals(s)))) {
            throw this.createDOMException((short)14, "namespace.uri", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), s });
        }
    }
    
    public String getNamespaceURI() {
        return this.namespaceURI;
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        ((AbstractElementNS)node).namespaceURI = this.namespaceURI;
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        ((AbstractElementNS)node).namespaceURI = this.namespaceURI;
        return node;
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        ((AbstractElementNS)node).namespaceURI = this.namespaceURI;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        ((AbstractElementNS)node).namespaceURI = this.namespaceURI;
        return node;
    }
}
