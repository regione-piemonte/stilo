// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;

public abstract class AbstractChildNode extends AbstractNode
{
    protected Node parentNode;
    protected Node previousSibling;
    protected Node nextSibling;
    
    public Node getParentNode() {
        return this.parentNode;
    }
    
    public void setParentNode(final Node parentNode) {
        this.parentNode = parentNode;
    }
    
    public void setPreviousSibling(final Node previousSibling) {
        this.previousSibling = previousSibling;
    }
    
    public Node getPreviousSibling() {
        return this.previousSibling;
    }
    
    public void setNextSibling(final Node nextSibling) {
        this.nextSibling = nextSibling;
    }
    
    public Node getNextSibling() {
        return this.nextSibling;
    }
}
