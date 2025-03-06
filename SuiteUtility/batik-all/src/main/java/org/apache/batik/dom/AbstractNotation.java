// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.w3c.dom.Notation;

public abstract class AbstractNotation extends AbstractNode implements Notation
{
    protected String nodeName;
    protected String publicId;
    protected String systemId;
    
    public short getNodeType() {
        return 12;
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
    
    public void setTextContent(final String s) throws DOMException {
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        final AbstractNotation abstractNotation = (AbstractNotation)node;
        abstractNotation.nodeName = this.nodeName;
        abstractNotation.publicId = this.publicId;
        abstractNotation.systemId = this.systemId;
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        final AbstractNotation abstractNotation = (AbstractNotation)node;
        abstractNotation.nodeName = this.nodeName;
        abstractNotation.publicId = this.publicId;
        abstractNotation.systemId = this.systemId;
        return node;
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        final AbstractNotation abstractNotation = (AbstractNotation)node;
        abstractNotation.nodeName = this.nodeName;
        abstractNotation.publicId = this.publicId;
        abstractNotation.systemId = this.systemId;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        final AbstractNotation abstractNotation = (AbstractNotation)node;
        abstractNotation.nodeName = this.nodeName;
        abstractNotation.publicId = this.publicId;
        abstractNotation.systemId = this.systemId;
        return node;
    }
}
