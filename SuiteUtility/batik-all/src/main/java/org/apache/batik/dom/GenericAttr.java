// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;

public class GenericAttr extends AbstractAttr
{
    protected boolean readonly;
    
    protected GenericAttr() {
    }
    
    public GenericAttr(final String nodeName, final AbstractDocument abstractDocument) throws DOMException {
        super(nodeName, abstractDocument);
        this.setNodeName(nodeName);
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    protected Node newNode() {
        return new GenericAttr();
    }
}
