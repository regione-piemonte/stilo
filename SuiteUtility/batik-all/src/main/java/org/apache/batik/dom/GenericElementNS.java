// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;

public class GenericElementNS extends AbstractElementNS
{
    protected String nodeName;
    protected boolean readonly;
    
    protected GenericElementNS() {
    }
    
    public GenericElementNS(final String s, final String nodeName, final AbstractDocument abstractDocument) {
        super(s, nodeName, abstractDocument);
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
        ((GenericElementNS)super.export(node, abstractDocument)).nodeName = this.nodeName;
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        ((GenericElementNS)super.deepExport(node, abstractDocument)).nodeName = this.nodeName;
        return node;
    }
    
    protected Node copyInto(final Node node) {
        ((GenericElementNS)super.copyInto(node)).nodeName = this.nodeName;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        ((GenericElementNS)super.deepCopyInto(node)).nodeName = this.nodeName;
        return node;
    }
    
    protected Node newNode() {
        return new GenericElementNS();
    }
}
