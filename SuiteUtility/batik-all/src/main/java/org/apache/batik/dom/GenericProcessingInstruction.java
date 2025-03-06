// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;

public class GenericProcessingInstruction extends AbstractProcessingInstruction
{
    protected String target;
    protected boolean readonly;
    
    protected GenericProcessingInstruction() {
    }
    
    public GenericProcessingInstruction(final String target, final String data, final AbstractDocument ownerDocument) {
        this.ownerDocument = ownerDocument;
        this.setTarget(target);
        this.setData(data);
    }
    
    public void setNodeName(final String target) {
        this.setTarget(target);
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    public String getTarget() {
        return this.target;
    }
    
    public void setTarget(final String target) {
        this.target = target;
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        final GenericProcessingInstruction genericProcessingInstruction = (GenericProcessingInstruction)super.export(node, abstractDocument);
        genericProcessingInstruction.setTarget(this.getTarget());
        return genericProcessingInstruction;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        final GenericProcessingInstruction genericProcessingInstruction = (GenericProcessingInstruction)super.deepExport(node, abstractDocument);
        genericProcessingInstruction.setTarget(this.getTarget());
        return genericProcessingInstruction;
    }
    
    protected Node copyInto(final Node node) {
        final GenericProcessingInstruction genericProcessingInstruction = (GenericProcessingInstruction)super.copyInto(node);
        genericProcessingInstruction.setTarget(this.getTarget());
        return genericProcessingInstruction;
    }
    
    protected Node deepCopyInto(final Node node) {
        final GenericProcessingInstruction genericProcessingInstruction = (GenericProcessingInstruction)super.deepCopyInto(node);
        genericProcessingInstruction.setTarget(this.getTarget());
        return genericProcessingInstruction;
    }
    
    protected Node newNode() {
        return new GenericProcessingInstruction();
    }
}
