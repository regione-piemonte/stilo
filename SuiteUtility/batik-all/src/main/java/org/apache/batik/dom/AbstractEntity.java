// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.Entity;

public abstract class AbstractEntity extends AbstractParentNode implements Entity
{
    protected String nodeName;
    protected String publicId;
    protected String systemId;
    
    public short getNodeType() {
        return 6;
    }
    
    public void setNodeName(final String nodeName) {
        this.nodeName = nodeName;
    }
    
    public String getNodeName() {
        return this.nodeName;
    }
    
    public String getPublicId() {
        return this.publicId;
    }
    
    public void setPublicId(final String publicId) {
        this.publicId = publicId;
    }
    
    public String getSystemId() {
        return this.systemId;
    }
    
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }
    
    public String getNotationName() {
        return this.getNodeName();
    }
    
    public void setNotationName(final String nodeName) {
        this.setNodeName(nodeName);
    }
    
    public String getInputEncoding() {
        return null;
    }
    
    public String getXmlEncoding() {
        return null;
    }
    
    public String getXmlVersion() {
        return null;
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        final AbstractEntity abstractEntity = (AbstractEntity)node;
        abstractEntity.nodeName = this.nodeName;
        abstractEntity.publicId = this.publicId;
        abstractEntity.systemId = this.systemId;
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        final AbstractEntity abstractEntity = (AbstractEntity)node;
        abstractEntity.nodeName = this.nodeName;
        abstractEntity.publicId = this.publicId;
        abstractEntity.systemId = this.systemId;
        return node;
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        final AbstractEntity abstractEntity = (AbstractEntity)node;
        abstractEntity.nodeName = this.nodeName;
        abstractEntity.publicId = this.publicId;
        abstractEntity.systemId = this.systemId;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        final AbstractEntity abstractEntity = (AbstractEntity)node;
        abstractEntity.nodeName = this.nodeName;
        abstractEntity.publicId = this.publicId;
        abstractEntity.systemId = this.systemId;
        return node;
    }
    
    protected void checkChildType(final Node node, final boolean b) {
        switch (node.getNodeType()) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 11: {}
            default: {
                throw this.createDOMException((short)3, "child.type", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), new Integer(node.getNodeType()), node.getNodeName() });
            }
        }
    }
}
