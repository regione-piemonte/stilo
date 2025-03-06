// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;

public class GenericNotation extends AbstractNotation
{
    protected boolean readonly;
    
    protected GenericNotation() {
    }
    
    public GenericNotation(final String nodeName, final String publicId, final String systemId, final AbstractDocument ownerDocument) {
        this.ownerDocument = ownerDocument;
        this.setNodeName(nodeName);
        this.setPublicId(publicId);
        this.setSystemId(systemId);
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    protected Node newNode() {
        return new GenericNotation();
    }
}
