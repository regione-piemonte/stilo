// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class GenericText extends AbstractText
{
    protected boolean readonly;
    
    protected GenericText() {
    }
    
    public GenericText(final String nodeValue, final AbstractDocument ownerDocument) {
        this.ownerDocument = ownerDocument;
        this.setNodeValue(nodeValue);
    }
    
    public String getNodeName() {
        return "#text";
    }
    
    public short getNodeType() {
        return 3;
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    protected Text createTextNode(final String s) {
        return this.getOwnerDocument().createTextNode(s);
    }
    
    protected Node newNode() {
        return new GenericText();
    }
}
