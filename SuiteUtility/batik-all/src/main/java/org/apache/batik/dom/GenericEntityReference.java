// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;

public class GenericEntityReference extends AbstractEntityReference
{
    protected boolean readonly;
    
    protected GenericEntityReference() {
    }
    
    public GenericEntityReference(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    protected Node newNode() {
        return new GenericEntityReference();
    }
}
