// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;

public class GenericComment extends AbstractComment
{
    protected boolean readonly;
    
    public GenericComment() {
    }
    
    public GenericComment(final String nodeValue, final AbstractDocument ownerDocument) {
        this.ownerDocument = ownerDocument;
        this.setNodeValue(nodeValue);
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    protected Node newNode() {
        return new GenericComment();
    }
}
