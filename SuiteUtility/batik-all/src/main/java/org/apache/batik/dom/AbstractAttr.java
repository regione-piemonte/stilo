// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.Attr;

public abstract class AbstractAttr extends AbstractParentNode implements Attr
{
    protected String nodeName;
    protected boolean unspecified;
    protected boolean isIdAttr;
    protected AbstractElement ownerElement;
    protected TypeInfo typeInfo;
    
    protected AbstractAttr() {
    }
    
    protected AbstractAttr(final String s, final AbstractDocument ownerDocument) throws DOMException {
        this.ownerDocument = ownerDocument;
        if (ownerDocument.getStrictErrorChecking() && !DOMUtilities.isValidName(s)) {
            throw this.createDOMException((short)5, "xml.name", new Object[] { s });
        }
    }
    
    public void setNodeName(final String nodeName) {
        this.nodeName = nodeName;
        this.isIdAttr = this.ownerDocument.isId(this);
    }
    
    public String getNodeName() {
        return this.nodeName;
    }
    
    public short getNodeType() {
        return 2;
    }
    
    public String getNodeValue() throws DOMException {
        final Node firstChild = this.getFirstChild();
        if (firstChild == null) {
            return "";
        }
        Node node = firstChild.getNextSibling();
        if (node == null) {
            return firstChild.getNodeValue();
        }
        final StringBuffer sb = new StringBuffer(firstChild.getNodeValue());
        do {
            sb.append(node.getNodeValue());
            node = node.getNextSibling();
        } while (node != null);
        return sb.toString();
    }
    
    public void setNodeValue(final String s) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        final String nodeValue = this.getNodeValue();
        Node firstChild;
        while ((firstChild = this.getFirstChild()) != null) {
            this.removeChild(firstChild);
        }
        final String s2 = (s == null) ? "" : s;
        this.appendChild(this.getOwnerDocument().createTextNode(s2));
        if (this.ownerElement != null) {
            this.ownerElement.fireDOMAttrModifiedEvent(this.nodeName, this, nodeValue, s2, (short)1);
        }
    }
    
    public String getName() {
        return this.getNodeName();
    }
    
    public boolean getSpecified() {
        return !this.unspecified;
    }
    
    public void setSpecified(final boolean b) {
        this.unspecified = !b;
    }
    
    public String getValue() {
        return this.getNodeValue();
    }
    
    public void setValue(final String nodeValue) throws DOMException {
        this.setNodeValue(nodeValue);
    }
    
    public void setOwnerElement(final AbstractElement ownerElement) {
        this.ownerElement = ownerElement;
    }
    
    public Element getOwnerElement() {
        return this.ownerElement;
    }
    
    public TypeInfo getSchemaTypeInfo() {
        if (this.typeInfo == null) {
            this.typeInfo = new AttrTypeInfo();
        }
        return this.typeInfo;
    }
    
    public boolean isId() {
        return this.isIdAttr;
    }
    
    public void setIsId(final boolean isIdAttr) {
        this.isIdAttr = isIdAttr;
    }
    
    protected void nodeAdded(final Node node) {
        this.setSpecified(true);
    }
    
    protected void nodeToBeRemoved(final Node node) {
        this.setSpecified(true);
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        final AbstractAttr abstractAttr = (AbstractAttr)node;
        abstractAttr.nodeName = this.nodeName;
        abstractAttr.unspecified = false;
        abstractAttr.isIdAttr = abstractDocument.isId(abstractAttr);
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        final AbstractAttr abstractAttr = (AbstractAttr)node;
        abstractAttr.nodeName = this.nodeName;
        abstractAttr.unspecified = false;
        abstractAttr.isIdAttr = abstractDocument.isId(abstractAttr);
        return node;
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        final AbstractAttr abstractAttr = (AbstractAttr)node;
        abstractAttr.nodeName = this.nodeName;
        abstractAttr.unspecified = this.unspecified;
        abstractAttr.isIdAttr = this.isIdAttr;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        final AbstractAttr abstractAttr = (AbstractAttr)node;
        abstractAttr.nodeName = this.nodeName;
        abstractAttr.unspecified = this.unspecified;
        abstractAttr.isIdAttr = this.isIdAttr;
        return node;
    }
    
    protected void checkChildType(final Node node, final boolean b) {
        switch (node.getNodeType()) {
            case 3:
            case 5:
            case 11: {}
            default: {
                throw this.createDOMException((short)3, "child.type", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), new Integer(node.getNodeType()), node.getNodeName() });
            }
        }
    }
    
    protected void fireDOMSubtreeModifiedEvent() {
        if (this.getCurrentDocument().getEventsEnabled()) {
            super.fireDOMSubtreeModifiedEvent();
            if (this.getOwnerElement() != null) {
                ((AbstractElement)this.getOwnerElement()).fireDOMSubtreeModifiedEvent();
            }
        }
    }
    
    public class AttrTypeInfo implements TypeInfo
    {
        public String getTypeNamespace() {
            return null;
        }
        
        public String getTypeName() {
            return null;
        }
        
        public boolean isDerivedFrom(final String s, final String s2, final int n) {
            return false;
        }
    }
}
