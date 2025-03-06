// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;

public class GenericAttrNS extends AbstractAttrNS
{
    protected boolean readonly;
    
    protected GenericAttrNS() {
    }
    
    public GenericAttrNS(final String s, final String nodeName, final AbstractDocument abstractDocument) throws DOMException {
        super(s, nodeName, abstractDocument);
        this.setNodeName(nodeName);
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    protected Node newNode() {
        return new GenericAttrNS();
    }
}
