// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.DOMException;
import org.apache.batik.dom.AbstractAttr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.util.DoublyIndexedTable;
import org.apache.batik.util.SVGConstants;
import org.apache.batik.css.engine.CSSNavigableNode;
import org.apache.batik.dom.events.NodeEventTarget;

public abstract class AbstractElement extends org.apache.batik.dom.AbstractElement implements NodeEventTarget, CSSNavigableNode, SVGConstants
{
    protected transient DoublyIndexedTable liveAttributeValues;
    
    protected AbstractElement() {
        this.liveAttributeValues = new DoublyIndexedTable();
    }
    
    protected AbstractElement(final String prefix, final AbstractDocument ownerDocument) {
        this.liveAttributeValues = new DoublyIndexedTable();
        this.ownerDocument = ownerDocument;
        this.setPrefix(prefix);
        this.initializeAttributes();
    }
    
    public Node getCSSParentNode() {
        return this.getXblParentNode();
    }
    
    public Node getCSSPreviousSibling() {
        return this.getXblPreviousSibling();
    }
    
    public Node getCSSNextSibling() {
        return this.getXblNextSibling();
    }
    
    public Node getCSSFirstChild() {
        return this.getXblFirstChild();
    }
    
    public Node getCSSLastChild() {
        return this.getXblLastChild();
    }
    
    public boolean isHiddenFromSelectors() {
        return false;
    }
    
    public void fireDOMAttrModifiedEvent(final String s, final Attr attr, final String s2, final String s3, final short n) {
        super.fireDOMAttrModifiedEvent(s, attr, s2, s3, n);
        if (((SVGOMDocument)this.ownerDocument).isSVG12 && (n == 2 || n == 1)) {
            if (attr.getNamespaceURI() == null && attr.getNodeName().equals("id")) {
                final Attr attributeNodeNS = this.getAttributeNodeNS("http://www.w3.org/XML/1998/namespace", "id");
                if (attributeNodeNS == null) {
                    this.setAttributeNS("http://www.w3.org/XML/1998/namespace", "id", s3);
                }
                else if (!attributeNodeNS.getNodeValue().equals(s3)) {
                    attributeNodeNS.setNodeValue(s3);
                }
            }
            else if (attr.getNodeName().equals("xml:id")) {
                final Attr attributeNodeNS2 = this.getAttributeNodeNS(null, "id");
                if (attributeNodeNS2 == null) {
                    this.setAttributeNS(null, "id", s3);
                }
                else if (!attributeNodeNS2.getNodeValue().equals(s3)) {
                    attributeNodeNS2.setNodeValue(s3);
                }
            }
        }
    }
    
    public LiveAttributeValue getLiveAttributeValue(final String s, final String s2) {
        return (LiveAttributeValue)this.liveAttributeValues.get(s, s2);
    }
    
    public void putLiveAttributeValue(final String s, final String s2, final LiveAttributeValue liveAttributeValue) {
        this.liveAttributeValues.put(s, s2, liveAttributeValue);
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return null;
    }
    
    protected void initializeAttributes() {
        final AttributeInitializer attributeInitializer = this.getAttributeInitializer();
        if (attributeInitializer != null) {
            attributeInitializer.initializeAttributes(this);
        }
    }
    
    protected boolean resetAttribute(final String s, final String s2, final String s3) {
        final AttributeInitializer attributeInitializer = this.getAttributeInitializer();
        return attributeInitializer != null && attributeInitializer.resetAttribute(this, s, s2, s3);
    }
    
    protected NamedNodeMap createAttributes() {
        return new ExtendedNamedNodeHashMap();
    }
    
    public void setUnspecifiedAttribute(final String s, final String s2, final String s3) {
        if (this.attributes == null) {
            this.attributes = this.createAttributes();
        }
        ((ExtendedNamedNodeHashMap)this.attributes).setUnspecifiedAttribute(s, s2, s3);
    }
    
    protected void attrAdded(final Attr attr, final String s) {
        final LiveAttributeValue liveAttributeValue = this.getLiveAttributeValue(attr);
        if (liveAttributeValue != null) {
            liveAttributeValue.attrAdded(attr, s);
        }
    }
    
    protected void attrModified(final Attr attr, final String s, final String s2) {
        final LiveAttributeValue liveAttributeValue = this.getLiveAttributeValue(attr);
        if (liveAttributeValue != null) {
            liveAttributeValue.attrModified(attr, s, s2);
        }
    }
    
    protected void attrRemoved(final Attr attr, final String s) {
        final LiveAttributeValue liveAttributeValue = this.getLiveAttributeValue(attr);
        if (liveAttributeValue != null) {
            liveAttributeValue.attrRemoved(attr, s);
        }
    }
    
    private LiveAttributeValue getLiveAttributeValue(final Attr attr) {
        final String namespaceURI = attr.getNamespaceURI();
        return this.getLiveAttributeValue(namespaceURI, (namespaceURI == null) ? attr.getNodeName() : attr.getLocalName());
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        ((AbstractElement)node).initializeAttributes();
        super.export(node, abstractDocument);
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        ((AbstractElement)node).initializeAttributes();
        super.deepExport(node, abstractDocument);
        return node;
    }
    
    protected class ExtendedNamedNodeHashMap extends NamedNodeHashMap
    {
        public ExtendedNamedNodeHashMap() {
        }
        
        public void setUnspecifiedAttribute(final String s, final String s2, final String value) {
            final Attr attributeNS = AbstractElement.this.getOwnerDocument().createAttributeNS(s, s2);
            attributeNS.setValue(value);
            ((AbstractAttr)attributeNS).setSpecified(false);
            this.setNamedItemNS(attributeNS);
        }
        
        public Node removeNamedItemNS(final String s, final String s2) throws DOMException {
            if (AbstractElement.this.isReadonly()) {
                throw AbstractElement.this.createDOMException((short)7, "readonly.node.map", new Object[0]);
            }
            if (s2 == null) {
                throw AbstractElement.this.createDOMException((short)8, "attribute.missing", new Object[] { "" });
            }
            final AbstractAttr abstractAttr = (AbstractAttr)this.remove(s, s2);
            if (abstractAttr == null) {
                throw AbstractElement.this.createDOMException((short)8, "attribute.missing", new Object[] { s2 });
            }
            abstractAttr.setOwnerElement(null);
            if (!AbstractElement.this.resetAttribute(s, abstractAttr.getPrefix(), s2)) {
                AbstractElement.this.fireDOMAttrModifiedEvent(abstractAttr.getNodeName(), abstractAttr, abstractAttr.getNodeValue(), "", (short)3);
            }
            return abstractAttr;
        }
    }
}
