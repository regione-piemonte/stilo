// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.XBLConstants;
import org.apache.batik.dom.svg.SVGOMElement;

public abstract class XBLOMElement extends SVGOMElement implements XBLConstants
{
    protected String prefix;
    
    protected XBLOMElement() {
    }
    
    protected XBLOMElement(final String prefix, final AbstractDocument ownerDocument) {
        this.ownerDocument = ownerDocument;
        this.setPrefix(prefix);
    }
    
    public String getNodeName() {
        if (this.prefix == null || this.prefix.equals("")) {
            return this.getLocalName();
        }
        return this.prefix + ':' + this.getLocalName();
    }
    
    public String getNamespaceURI() {
        return "http://www.w3.org/2004/xbl";
    }
    
    public void setPrefix(final String prefix) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        if (prefix != null && !prefix.equals("") && !DOMUtilities.isValidName(prefix)) {
            throw this.createDOMException((short)5, "prefix", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), prefix });
        }
        this.prefix = prefix;
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        ((XBLOMElement)node).prefix = this.prefix;
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        ((XBLOMElement)node).prefix = this.prefix;
        return node;
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        ((XBLOMElement)node).prefix = this.prefix;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        ((XBLOMElement)node).prefix = this.prefix;
        return node;
    }
}
