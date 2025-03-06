// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import java.io.Serializable;
import org.w3c.dom.events.Event;
import org.apache.batik.dom.events.DOMMutationEvent;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Attr;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.ElementTraversal;
import org.w3c.dom.Element;

public abstract class AbstractElement extends AbstractParentChildNode implements Element, ElementTraversal
{
    protected NamedNodeMap attributes;
    protected TypeInfo typeInfo;
    
    protected AbstractElement() {
    }
    
    protected AbstractElement(final String s, final AbstractDocument ownerDocument) {
        this.ownerDocument = ownerDocument;
        if (ownerDocument.getStrictErrorChecking() && !DOMUtilities.isValidName(s)) {
            throw this.createDOMException((short)5, "xml.name", new Object[] { s });
        }
    }
    
    public short getNodeType() {
        return 1;
    }
    
    public boolean hasAttributes() {
        return this.attributes != null && this.attributes.getLength() != 0;
    }
    
    public NamedNodeMap getAttributes() {
        return (this.attributes == null) ? (this.attributes = this.createAttributes()) : this.attributes;
    }
    
    public String getTagName() {
        return this.getNodeName();
    }
    
    public boolean hasAttribute(final String s) {
        return this.attributes != null && this.attributes.getNamedItem(s) != null;
    }
    
    public String getAttribute(final String s) {
        if (this.attributes == null) {
            return "";
        }
        final Attr attr = (Attr)this.attributes.getNamedItem(s);
        return (attr == null) ? "" : attr.getValue();
    }
    
    public void setAttribute(final String s, final String s2) throws DOMException {
        if (this.attributes == null) {
            this.attributes = this.createAttributes();
        }
        final Attr attributeNode = this.getAttributeNode(s);
        if (attributeNode == null) {
            final Attr attribute = this.getOwnerDocument().createAttribute(s);
            attribute.setValue(s2);
            this.attributes.setNamedItem(attribute);
        }
        else {
            attributeNode.setValue(s2);
        }
    }
    
    public void removeAttribute(final String s) throws DOMException {
        if (!this.hasAttribute(s)) {
            return;
        }
        this.attributes.removeNamedItem(s);
    }
    
    public Attr getAttributeNode(final String s) {
        if (this.attributes == null) {
            return null;
        }
        return (Attr)this.attributes.getNamedItem(s);
    }
    
    public Attr setAttributeNode(final Attr namedItemNS) throws DOMException {
        if (namedItemNS == null) {
            return null;
        }
        if (this.attributes == null) {
            this.attributes = this.createAttributes();
        }
        return (Attr)this.attributes.setNamedItemNS(namedItemNS);
    }
    
    public Attr removeAttributeNode(final Attr attr) throws DOMException {
        if (attr == null) {
            return null;
        }
        if (this.attributes == null) {
            throw this.createDOMException((short)8, "attribute.missing", new Object[] { attr.getName() });
        }
        final String namespaceURI = attr.getNamespaceURI();
        return (Attr)this.attributes.removeNamedItemNS(namespaceURI, (namespaceURI == null) ? attr.getNodeName() : attr.getLocalName());
    }
    
    public void normalize() {
        super.normalize();
        if (this.attributes != null) {
            final NamedNodeMap attributes = this.getAttributes();
            for (int i = attributes.getLength() - 1; i >= 0; --i) {
                attributes.item(i).normalize();
            }
        }
    }
    
    public boolean hasAttributeNS(String s, final String s2) {
        if (s != null && s.length() == 0) {
            s = null;
        }
        return this.attributes != null && this.attributes.getNamedItemNS(s, s2) != null;
    }
    
    public String getAttributeNS(String s, final String s2) {
        if (this.attributes == null) {
            return "";
        }
        if (s != null && s.length() == 0) {
            s = null;
        }
        final Attr attr = (Attr)this.attributes.getNamedItemNS(s, s2);
        return (attr == null) ? "" : attr.getValue();
    }
    
    public void setAttributeNS(String s, final String s2, final String s3) throws DOMException {
        if (this.attributes == null) {
            this.attributes = this.createAttributes();
        }
        if (s != null && s.length() == 0) {
            s = null;
        }
        final Attr attributeNodeNS = this.getAttributeNodeNS(s, s2);
        if (attributeNodeNS == null) {
            final Attr attributeNS = this.getOwnerDocument().createAttributeNS(s, s2);
            attributeNS.setValue(s3);
            this.attributes.setNamedItemNS(attributeNS);
        }
        else {
            attributeNodeNS.setValue(s3);
        }
    }
    
    public void removeAttributeNS(String s, final String s2) throws DOMException {
        if (s != null && s.length() == 0) {
            s = null;
        }
        if (!this.hasAttributeNS(s, s2)) {
            return;
        }
        this.attributes.removeNamedItemNS(s, s2);
    }
    
    public Attr getAttributeNodeNS(String s, final String s2) {
        if (s != null && s.length() == 0) {
            s = null;
        }
        if (this.attributes == null) {
            return null;
        }
        return (Attr)this.attributes.getNamedItemNS(s, s2);
    }
    
    public Attr setAttributeNodeNS(final Attr namedItemNS) throws DOMException {
        if (namedItemNS == null) {
            return null;
        }
        if (this.attributes == null) {
            this.attributes = this.createAttributes();
        }
        return (Attr)this.attributes.setNamedItemNS(namedItemNS);
    }
    
    public TypeInfo getSchemaTypeInfo() {
        if (this.typeInfo == null) {
            this.typeInfo = new ElementTypeInfo();
        }
        return this.typeInfo;
    }
    
    public void setIdAttribute(final String s, final boolean isIdAttr) throws DOMException {
        final AbstractAttr abstractAttr = (AbstractAttr)this.getAttributeNode(s);
        if (abstractAttr == null) {
            throw this.createDOMException((short)8, "attribute.missing", new Object[] { s });
        }
        if (abstractAttr.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { s });
        }
        abstractAttr.isIdAttr = isIdAttr;
    }
    
    public void setIdAttributeNS(String s, final String s2, final boolean isIdAttr) throws DOMException {
        if (s != null && s.length() == 0) {
            s = null;
        }
        final AbstractAttr abstractAttr = (AbstractAttr)this.getAttributeNodeNS(s, s2);
        if (abstractAttr == null) {
            throw this.createDOMException((short)8, "attribute.missing", new Object[] { s, s2 });
        }
        if (abstractAttr.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { abstractAttr.getNodeName() });
        }
        abstractAttr.isIdAttr = isIdAttr;
    }
    
    public void setIdAttributeNode(final Attr attr, final boolean isIdAttr) throws DOMException {
        final AbstractAttr abstractAttr = (AbstractAttr)attr;
        if (abstractAttr.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { abstractAttr.getNodeName() });
        }
        abstractAttr.isIdAttr = isIdAttr;
    }
    
    protected Attr getIdAttribute() {
        final NamedNodeMap attributes = this.getAttributes();
        if (attributes == null) {
            return null;
        }
        for (int length = attributes.getLength(), i = 0; i < length; ++i) {
            final AbstractAttr abstractAttr = (AbstractAttr)attributes.item(i);
            if (abstractAttr.isId()) {
                return abstractAttr;
            }
        }
        return null;
    }
    
    protected String getId() {
        final Attr idAttribute = this.getIdAttribute();
        if (idAttribute != null) {
            final String nodeValue = idAttribute.getNodeValue();
            if (nodeValue.length() > 0) {
                return nodeValue;
            }
        }
        return null;
    }
    
    protected void nodeAdded(final Node node) {
        this.invalidateElementsByTagName(node);
    }
    
    protected void nodeToBeRemoved(final Node node) {
        this.invalidateElementsByTagName(node);
    }
    
    private void invalidateElementsByTagName(final Node node) {
        if (node.getNodeType() != 1) {
            return;
        }
        final AbstractDocument currentDocument = this.getCurrentDocument();
        final String namespaceURI = node.getNamespaceURI();
        final String nodeName = node.getNodeName();
        final String s = (namespaceURI == null) ? node.getNodeName() : node.getLocalName();
        for (Node parentNode = this; parentNode != null; parentNode = parentNode.getParentNode()) {
            switch (parentNode.getNodeType()) {
                case 1:
                case 9: {
                    final ElementsByTagName elementsByTagName = currentDocument.getElementsByTagName(parentNode, nodeName);
                    if (elementsByTagName != null) {
                        elementsByTagName.invalidate();
                    }
                    final ElementsByTagName elementsByTagName2 = currentDocument.getElementsByTagName(parentNode, "*");
                    if (elementsByTagName2 != null) {
                        elementsByTagName2.invalidate();
                    }
                    final ElementsByTagNameNS elementsByTagNameNS = currentDocument.getElementsByTagNameNS(parentNode, namespaceURI, s);
                    if (elementsByTagNameNS != null) {
                        elementsByTagNameNS.invalidate();
                    }
                    final ElementsByTagNameNS elementsByTagNameNS2 = currentDocument.getElementsByTagNameNS(parentNode, "*", s);
                    if (elementsByTagNameNS2 != null) {
                        elementsByTagNameNS2.invalidate();
                    }
                    final ElementsByTagNameNS elementsByTagNameNS3 = currentDocument.getElementsByTagNameNS(parentNode, namespaceURI, "*");
                    if (elementsByTagNameNS3 != null) {
                        elementsByTagNameNS3.invalidate();
                    }
                    final ElementsByTagNameNS elementsByTagNameNS4 = currentDocument.getElementsByTagNameNS(parentNode, "*", "*");
                    if (elementsByTagNameNS4 != null) {
                        elementsByTagNameNS4.invalidate();
                        break;
                    }
                    break;
                }
            }
        }
        for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            this.invalidateElementsByTagName(node2);
        }
    }
    
    protected NamedNodeMap createAttributes() {
        return new NamedNodeHashMap();
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        final AbstractElement abstractElement = (AbstractElement)node;
        if (this.attributes != null) {
            final NamedNodeMap attributes = this.attributes;
            for (int i = attributes.getLength() - 1; i >= 0; --i) {
                final AbstractAttr abstractAttr = (AbstractAttr)attributes.item(i);
                if (abstractAttr.getSpecified()) {
                    final Attr attr = (Attr)abstractAttr.deepExport(abstractAttr.cloneNode(false), abstractDocument);
                    if (abstractAttr instanceof AbstractAttrNS) {
                        abstractElement.setAttributeNodeNS(attr);
                    }
                    else {
                        abstractElement.setAttributeNode(attr);
                    }
                }
            }
        }
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        final AbstractElement abstractElement = (AbstractElement)node;
        if (this.attributes != null) {
            final NamedNodeMap attributes = this.attributes;
            for (int i = attributes.getLength() - 1; i >= 0; --i) {
                final AbstractAttr abstractAttr = (AbstractAttr)attributes.item(i);
                if (abstractAttr.getSpecified()) {
                    final Attr attr = (Attr)abstractAttr.deepExport(abstractAttr.cloneNode(false), abstractDocument);
                    if (abstractAttr instanceof AbstractAttrNS) {
                        abstractElement.setAttributeNodeNS(attr);
                    }
                    else {
                        abstractElement.setAttributeNode(attr);
                    }
                }
            }
        }
        return node;
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        final AbstractElement abstractElement = (AbstractElement)node;
        if (this.attributes != null) {
            final NamedNodeMap attributes = this.attributes;
            for (int i = attributes.getLength() - 1; i >= 0; --i) {
                final AbstractAttr abstractAttr = (AbstractAttr)attributes.item(i).cloneNode(true);
                if (abstractAttr instanceof AbstractAttrNS) {
                    abstractElement.setAttributeNodeNS(abstractAttr);
                }
                else {
                    abstractElement.setAttributeNode(abstractAttr);
                }
            }
        }
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        final AbstractElement abstractElement = (AbstractElement)node;
        if (this.attributes != null) {
            final NamedNodeMap attributes = this.attributes;
            for (int i = attributes.getLength() - 1; i >= 0; --i) {
                final AbstractAttr abstractAttr = (AbstractAttr)attributes.item(i).cloneNode(true);
                if (abstractAttr instanceof AbstractAttrNS) {
                    abstractElement.setAttributeNodeNS(abstractAttr);
                }
                else {
                    abstractElement.setAttributeNode(abstractAttr);
                }
            }
        }
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
    
    public void fireDOMAttrModifiedEvent(final String s, final Attr attr, final String s2, final String anObject, final short n) {
        switch (n) {
            case 2: {
                if (((AbstractAttr)attr).isId()) {
                    this.ownerDocument.addIdEntry(this, anObject);
                }
                this.attrAdded(attr, anObject);
                break;
            }
            case 1: {
                if (((AbstractAttr)attr).isId()) {
                    this.ownerDocument.updateIdEntry(this, s2, anObject);
                }
                this.attrModified(attr, s2, anObject);
                break;
            }
            default: {
                if (((AbstractAttr)attr).isId()) {
                    this.ownerDocument.removeIdEntry(this, s2);
                }
                this.attrRemoved(attr, s2);
                break;
            }
        }
        final AbstractDocument currentDocument = this.getCurrentDocument();
        if (currentDocument.getEventsEnabled() && !s2.equals(anObject)) {
            final DOMMutationEvent domMutationEvent = (DOMMutationEvent)currentDocument.createEvent("MutationEvents");
            domMutationEvent.initMutationEventNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", true, false, attr, s2, anObject, s, n);
            this.dispatchEvent(domMutationEvent);
        }
    }
    
    protected void attrAdded(final Attr attr, final String s) {
    }
    
    protected void attrModified(final Attr attr, final String s, final String s2) {
    }
    
    protected void attrRemoved(final Attr attr, final String s) {
    }
    
    public Element getFirstElementChild() {
        for (Node node = this.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                return (Element)node;
            }
        }
        return null;
    }
    
    public Element getLastElementChild() {
        for (Node node = this.getLastChild(); node != null; node = node.getPreviousSibling()) {
            if (node.getNodeType() == 1) {
                return (Element)node;
            }
        }
        return null;
    }
    
    public Element getNextElementSibling() {
        for (Node node = this.getNextSibling(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                return (Element)node;
            }
        }
        return null;
    }
    
    public Element getPreviousElementSibling() {
        Node node;
        for (node = this.getPreviousSibling(); node != null; node = node.getPreviousSibling()) {
            if (node.getNodeType() == 1) {
                return (Element)node;
            }
        }
        return (Element)node;
    }
    
    public int getChildElementCount() {
        this.getChildNodes();
        return this.childNodes.elementChildren;
    }
    
    public class ElementTypeInfo implements TypeInfo
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
    
    protected static class Entry implements Serializable
    {
        public int hash;
        public String namespaceURI;
        public String name;
        public Node value;
        public Entry next;
        
        public Entry(final int hash, final String namespaceURI, final String name, final Node value, final Entry next) {
            this.hash = hash;
            this.namespaceURI = namespaceURI;
            this.name = name;
            this.value = value;
            this.next = next;
        }
        
        public boolean match(final String anObject, final String anObject2) {
            if (this.namespaceURI != null) {
                if (!this.namespaceURI.equals(anObject)) {
                    return false;
                }
            }
            else if (anObject != null) {
                return false;
            }
            return this.name.equals(anObject2);
        }
    }
    
    public class NamedNodeHashMap implements NamedNodeMap, Serializable
    {
        protected static final int INITIAL_CAPACITY = 3;
        protected Entry[] table;
        protected int count;
        
        public NamedNodeHashMap() {
            this.table = new Entry[3];
        }
        
        public Node getNamedItem(final String s) {
            if (s == null) {
                return null;
            }
            return this.get(null, s);
        }
        
        public Node setNamedItem(final Node node) throws DOMException {
            if (node == null) {
                return null;
            }
            this.checkNode(node);
            return this.setNamedItem(null, node.getNodeName(), node);
        }
        
        public Node removeNamedItem(final String s) throws DOMException {
            return this.removeNamedItemNS(null, s);
        }
        
        public Node item(final int n) {
            if (n < 0 || n >= this.count) {
                return null;
            }
            int n2 = 0;
        Label_0069:
            for (int i = 0; i < this.table.length; ++i) {
                Entry next = this.table[i];
                if (next != null) {
                    while (n2++ != n) {
                        next = next.next;
                        if (next == null) {
                            continue Label_0069;
                        }
                    }
                    return next.value;
                }
            }
            return null;
        }
        
        public int getLength() {
            return this.count;
        }
        
        public Node getNamedItemNS(String s, final String s2) {
            if (s != null && s.length() == 0) {
                s = null;
            }
            return this.get(s, s2);
        }
        
        public Node setNamedItemNS(final Node node) throws DOMException {
            if (node == null) {
                return null;
            }
            final String namespaceURI = node.getNamespaceURI();
            return this.setNamedItem(namespaceURI, (namespaceURI == null) ? node.getNodeName() : node.getLocalName(), node);
        }
        
        public Node removeNamedItemNS(String s, final String s2) throws DOMException {
            if (AbstractElement.this.isReadonly()) {
                throw AbstractElement.this.createDOMException((short)7, "readonly.node.map", new Object[0]);
            }
            if (s2 == null) {
                throw AbstractElement.this.createDOMException((short)8, "attribute.missing", new Object[] { "" });
            }
            if (s != null && s.length() == 0) {
                s = null;
            }
            final AbstractAttr abstractAttr = (AbstractAttr)this.remove(s, s2);
            if (abstractAttr == null) {
                throw AbstractElement.this.createDOMException((short)8, "attribute.missing", new Object[] { s2 });
            }
            abstractAttr.setOwnerElement(null);
            AbstractElement.this.fireDOMAttrModifiedEvent(abstractAttr.getNodeName(), abstractAttr, abstractAttr.getNodeValue(), "", (short)3);
            return abstractAttr;
        }
        
        public Node setNamedItem(String s, final String s2, final Node node) throws DOMException {
            if (s != null && s.length() == 0) {
                s = null;
            }
            ((AbstractAttr)node).setOwnerElement(AbstractElement.this);
            final AbstractAttr abstractAttr = (AbstractAttr)this.put(s, s2, node);
            if (abstractAttr != null) {
                abstractAttr.setOwnerElement(null);
                AbstractElement.this.fireDOMAttrModifiedEvent(s2, abstractAttr, abstractAttr.getNodeValue(), "", (short)3);
            }
            AbstractElement.this.fireDOMAttrModifiedEvent(s2, (Attr)node, "", node.getNodeValue(), (short)2);
            return abstractAttr;
        }
        
        protected void checkNode(final Node node) {
            if (AbstractElement.this.isReadonly()) {
                throw AbstractElement.this.createDOMException((short)7, "readonly.node.map", new Object[0]);
            }
            if (AbstractElement.this.getOwnerDocument() != node.getOwnerDocument()) {
                throw AbstractElement.this.createDOMException((short)4, "node.from.wrong.document", new Object[] { new Integer(node.getNodeType()), node.getNodeName() });
            }
            if (node.getNodeType() == 2 && ((Attr)node).getOwnerElement() != null) {
                throw AbstractElement.this.createDOMException((short)4, "inuse.attribute", new Object[] { node.getNodeName() });
            }
        }
        
        protected Node get(final String s, final String s2) {
            final int n = this.hashCode(s, s2) & Integer.MAX_VALUE;
            for (Entry next = this.table[n % this.table.length]; next != null; next = next.next) {
                if (next.hash == n && next.match(s, s2)) {
                    return next.value;
                }
            }
            return null;
        }
        
        protected Node put(final String s, final String s2, final Node value) {
            final int n = this.hashCode(s, s2) & Integer.MAX_VALUE;
            int n2 = n % this.table.length;
            for (Entry next = this.table[n2]; next != null; next = next.next) {
                if (next.hash == n && next.match(s, s2)) {
                    final Node value2 = next.value;
                    next.value = value;
                    return value2;
                }
            }
            final int length = this.table.length;
            if (this.count++ >= length - (length >> 2)) {
                this.rehash();
                n2 = n % this.table.length;
            }
            this.table[n2] = new Entry(n, s, s2, value, this.table[n2]);
            return null;
        }
        
        protected Node remove(final String s, final String s2) {
            final int n = this.hashCode(s, s2) & Integer.MAX_VALUE;
            final int n2 = n % this.table.length;
            Entry entry = null;
            for (Entry next = this.table[n2]; next != null; next = next.next) {
                if (next.hash == n && next.match(s, s2)) {
                    final Node value = next.value;
                    if (entry == null) {
                        this.table[n2] = next.next;
                    }
                    else {
                        entry.next = next.next;
                    }
                    --this.count;
                    return value;
                }
                entry = next;
            }
            return null;
        }
        
        protected void rehash() {
            final Entry[] table = this.table;
            this.table = new Entry[table.length * 2 + 1];
            for (int i = table.length - 1; i >= 0; --i) {
                Entry entry;
                int n;
                for (Entry next = table[i]; next != null; next = next.next, n = entry.hash % this.table.length, entry.next = this.table[n], this.table[n] = entry) {
                    entry = next;
                }
            }
        }
        
        protected int hashCode(final String s, final String s2) {
            return ((s == null) ? 0 : s.hashCode()) ^ s2.hashCode();
        }
    }
}
