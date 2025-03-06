// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.w3c.dom.ProcessingInstruction;

public abstract class AbstractProcessingInstruction extends AbstractChildNode implements ProcessingInstruction
{
    protected String data;
    
    public String getNodeName() {
        return this.getTarget();
    }
    
    public short getNodeType() {
        return 7;
    }
    
    public String getNodeValue() throws DOMException {
        return this.getData();
    }
    
    public void setNodeValue(final String data) throws DOMException {
        this.setData(data);
    }
    
    public String getData() {
        return this.data;
    }
    
    public void setData(final String data) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        this.fireDOMCharacterDataModifiedEvent(this.data, this.data = data);
        if (this.getParentNode() != null) {
            ((AbstractParentNode)this.getParentNode()).fireDOMSubtreeModifiedEvent();
        }
    }
    
    public String getTextContent() {
        return this.getNodeValue();
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        final AbstractProcessingInstruction abstractProcessingInstruction = (AbstractProcessingInstruction)super.export(node, abstractDocument);
        abstractProcessingInstruction.data = this.data;
        return abstractProcessingInstruction;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        final AbstractProcessingInstruction abstractProcessingInstruction = (AbstractProcessingInstruction)super.deepExport(node, abstractDocument);
        abstractProcessingInstruction.data = this.data;
        return abstractProcessingInstruction;
    }
    
    protected Node copyInto(final Node node) {
        final AbstractProcessingInstruction abstractProcessingInstruction = (AbstractProcessingInstruction)super.copyInto(node);
        abstractProcessingInstruction.data = this.data;
        return abstractProcessingInstruction;
    }
    
    protected Node deepCopyInto(final Node node) {
        final AbstractProcessingInstruction abstractProcessingInstruction = (AbstractProcessingInstruction)super.deepCopyInto(node);
        abstractProcessingInstruction.data = this.data;
        return abstractProcessingInstruction;
    }
}
