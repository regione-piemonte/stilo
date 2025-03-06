// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.EntityReference;

public abstract class AbstractEntityReference extends AbstractParentChildNode implements EntityReference
{
    protected String nodeName;
    
    protected AbstractEntityReference() {
    }
    
    protected AbstractEntityReference(final String nodeName, final AbstractDocument ownerDocument) throws DOMException {
        this.ownerDocument = ownerDocument;
        if (ownerDocument.getStrictErrorChecking() && !DOMUtilities.isValidName(nodeName)) {
            throw this.createDOMException((short)5, "xml.name", new Object[] { nodeName });
        }
        this.nodeName = nodeName;
    }
    
    public short getNodeType() {
        return 5;
    }
    
    public void setNodeName(final String nodeName) {
        this.nodeName = nodeName;
    }
    
    public String getNodeName() {
        return this.nodeName;
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        ((AbstractEntityReference)node).nodeName = this.nodeName;
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        ((AbstractEntityReference)node).nodeName = this.nodeName;
        return node;
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        ((AbstractEntityReference)node).nodeName = this.nodeName;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        ((AbstractEntityReference)node).nodeName = this.nodeName;
        return node;
    }
    
    protected void checkChildType(final Node node, final boolean b) {
        switch (node.getNodeType()) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 11: {}
            default: {
                throw this.createDOMException((short)3, "child.type", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), new Integer(node.getNodeType()), node.getNodeName() });
            }
        }
    }
}
