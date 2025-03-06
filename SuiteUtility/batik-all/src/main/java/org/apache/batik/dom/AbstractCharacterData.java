// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.w3c.dom.CharacterData;

public abstract class AbstractCharacterData extends AbstractChildNode implements CharacterData
{
    protected String nodeValue;
    
    public AbstractCharacterData() {
        this.nodeValue = "";
    }
    
    public String getNodeValue() throws DOMException {
        return this.nodeValue;
    }
    
    public void setNodeValue(final String s) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        this.fireDOMCharacterDataModifiedEvent(this.nodeValue, this.nodeValue = ((s == null) ? "" : s));
        if (this.getParentNode() != null) {
            ((AbstractParentNode)this.getParentNode()).fireDOMSubtreeModifiedEvent();
        }
    }
    
    public String getData() throws DOMException {
        return this.getNodeValue();
    }
    
    public void setData(final String nodeValue) throws DOMException {
        this.setNodeValue(nodeValue);
    }
    
    public int getLength() {
        return this.nodeValue.length();
    }
    
    public String substringData(final int beginIndex, final int n) throws DOMException {
        this.checkOffsetCount(beginIndex, n);
        final String nodeValue = this.getNodeValue();
        return nodeValue.substring(beginIndex, Math.min(nodeValue.length(), beginIndex + n));
    }
    
    public void appendData(final String s) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        this.setNodeValue(this.getNodeValue() + ((s == null) ? "" : s));
    }
    
    public void insertData(final int beginIndex, final String str) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        if (beginIndex < 0 || beginIndex > this.getLength()) {
            throw this.createDOMException((short)1, "offset", new Object[] { new Integer(beginIndex) });
        }
        final String nodeValue = this.getNodeValue();
        this.setNodeValue(nodeValue.substring(0, beginIndex) + str + nodeValue.substring(beginIndex, nodeValue.length()));
    }
    
    public void deleteData(final int endIndex, final int n) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        this.checkOffsetCount(endIndex, n);
        final String nodeValue = this.getNodeValue();
        this.setNodeValue(nodeValue.substring(0, endIndex) + nodeValue.substring(Math.min(nodeValue.length(), endIndex + n), nodeValue.length()));
    }
    
    public void replaceData(final int endIndex, final int n, final String str) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        this.checkOffsetCount(endIndex, n);
        final String nodeValue = this.getNodeValue();
        this.setNodeValue(nodeValue.substring(0, endIndex) + str + nodeValue.substring(Math.min(nodeValue.length(), endIndex + n), nodeValue.length()));
    }
    
    protected void checkOffsetCount(final int value, final int value2) throws DOMException {
        if (value < 0 || value >= this.getLength()) {
            throw this.createDOMException((short)1, "offset", new Object[] { new Integer(value) });
        }
        if (value2 < 0) {
            throw this.createDOMException((short)1, "negative.count", new Object[] { new Integer(value2) });
        }
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        ((AbstractCharacterData)node).nodeValue = this.nodeValue;
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        ((AbstractCharacterData)node).nodeValue = this.nodeValue;
        return node;
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        ((AbstractCharacterData)node).nodeValue = this.nodeValue;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        ((AbstractCharacterData)node).nodeValue = this.nodeValue;
        return node;
    }
}
