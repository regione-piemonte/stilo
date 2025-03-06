// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;

public class GenericElement extends AbstractElement
{
    protected String nodeName;
    protected boolean readonly;
    
    protected GenericElement() {
    }
    
    public GenericElement(final String nodeName, final AbstractDocument abstractDocument) throws DOMException {
        super(nodeName, abstractDocument);
        this.nodeName = nodeName;
    }
    
    public void setNodeName(final String nodeName) {
        this.nodeName = nodeName;
    }
    
    public String getNodeName() {
        return this.nodeName;
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        ((GenericElement)node).nodeName = this.nodeName;
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        ((GenericElement)node).nodeName = this.nodeName;
        return node;
    }
    
    protected Node copyInto(final Node node) {
        ((GenericElement)super.copyInto(node)).nodeName = this.nodeName;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        ((GenericElement)super.deepCopyInto(node)).nodeName = this.nodeName;
        return node;
    }
    
    protected Node newNode() {
        return new GenericElement();
    }
}
