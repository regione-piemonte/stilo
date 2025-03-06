// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;

public class GenericDocumentFragment extends AbstractDocumentFragment
{
    protected boolean readonly;
    
    protected GenericDocumentFragment() {
    }
    
    public GenericDocumentFragment(final AbstractDocument ownerDocument) {
        this.ownerDocument = ownerDocument;
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    protected Node newNode() {
        return new GenericDocumentFragment();
    }
}
