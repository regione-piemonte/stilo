// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.CDATASection;

public class GenericCDATASection extends AbstractText implements CDATASection
{
    protected boolean readonly;
    
    protected GenericCDATASection() {
    }
    
    public GenericCDATASection(final String nodeValue, final AbstractDocument ownerDocument) {
        this.ownerDocument = ownerDocument;
        this.setNodeValue(nodeValue);
    }
    
    public String getNodeName() {
        return "#cdata-section";
    }
    
    public short getNodeType() {
        return 4;
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    protected Text createTextNode(final String s) {
        return this.getOwnerDocument().createCDATASection(s);
    }
    
    protected Node newNode() {
        return new GenericCDATASection();
    }
}
