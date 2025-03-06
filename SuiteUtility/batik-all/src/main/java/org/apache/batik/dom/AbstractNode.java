// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.apache.batik.dom.events.DOMMutationEvent;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.Event;
import org.apache.batik.dom.events.NodeEventTarget;
import org.w3c.dom.events.EventListener;
import java.util.Iterator;
import java.util.Map;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.DocumentType;
import java.util.ArrayList;
import org.w3c.dom.Attr;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Element;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import java.util.HashMap;
import org.apache.batik.dom.events.EventSupport;
import org.w3c.dom.NodeList;
import java.io.Serializable;
import org.apache.batik.dom.xbl.XBLManagerData;
import org.apache.batik.dom.xbl.NodeXBL;

public abstract class AbstractNode implements ExtendedNode, NodeXBL, XBLManagerData, Serializable
{
    public static final NodeList EMPTY_NODE_LIST;
    protected AbstractDocument ownerDocument;
    protected transient EventSupport eventSupport;
    protected HashMap userData;
    protected HashMap userDataHandlers;
    protected Object managerData;
    public static final short DOCUMENT_POSITION_DISCONNECTED = 1;
    public static final short DOCUMENT_POSITION_PRECEDING = 2;
    public static final short DOCUMENT_POSITION_FOLLOWING = 4;
    public static final short DOCUMENT_POSITION_CONTAINS = 8;
    public static final short DOCUMENT_POSITION_CONTAINED_BY = 16;
    public static final short DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;
    
    public void setNodeName(final String s) {
    }
    
    public void setOwnerDocument(final Document document) {
        this.ownerDocument = (AbstractDocument)document;
    }
    
    public void setSpecified(final boolean b) {
        throw this.createDOMException((short)11, "node.type", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
    }
    
    public String getNodeValue() throws DOMException {
        return null;
    }
    
    public void setNodeValue(final String s) throws DOMException {
    }
    
    public Node getParentNode() {
        return null;
    }
    
    public void setParentNode(final Node node) {
        throw this.createDOMException((short)3, "parent.not.allowed", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
    }
    
    public NodeList getChildNodes() {
        return AbstractNode.EMPTY_NODE_LIST;
    }
    
    public Node getFirstChild() {
        return null;
    }
    
    public Node getLastChild() {
        return null;
    }
    
    public void setPreviousSibling(final Node node) {
        throw this.createDOMException((short)3, "sibling.not.allowed", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
    }
    
    public Node getPreviousSibling() {
        return null;
    }
    
    public void setNextSibling(final Node node) {
        throw this.createDOMException((short)3, "sibling.not.allowed", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
    }
    
    public Node getNextSibling() {
        return null;
    }
    
    public boolean hasAttributes() {
        return false;
    }
    
    public NamedNodeMap getAttributes() {
        return null;
    }
    
    public Document getOwnerDocument() {
        return this.ownerDocument;
    }
    
    public String getNamespaceURI() {
        return null;
    }
    
    public Node insertBefore(final Node node, final Node node2) throws DOMException {
        throw this.createDOMException((short)3, "children.not.allowed", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
    }
    
    public Node replaceChild(final Node node, final Node node2) throws DOMException {
        throw this.createDOMException((short)3, "children.not.allowed", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
    }
    
    public Node removeChild(final Node node) throws DOMException {
        throw this.createDOMException((short)3, "children.not.allowed", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
    }
    
    public Node appendChild(final Node node) throws DOMException {
        throw this.createDOMException((short)3, "children.not.allowed", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
    }
    
    public boolean hasChildNodes() {
        return false;
    }
    
    public Node cloneNode(final boolean b) {
        final Node node = b ? this.deepCopyInto(this.newNode()) : this.copyInto(this.newNode());
        this.fireUserDataHandlers((short)1, this, node);
        return node;
    }
    
    public void normalize() {
    }
    
    public boolean isSupported(final String s, final String s2) {
        return this.getCurrentDocument().getImplementation().hasFeature(s, s2);
    }
    
    public String getPrefix() {
        return (this.getNamespaceURI() == null) ? null : DOMUtilities.getPrefix(this.getNodeName());
    }
    
    public void setPrefix(final String str) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        final String namespaceURI = this.getNamespaceURI();
        if (namespaceURI == null) {
            throw this.createDOMException((short)14, "namespace", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        final String localName = this.getLocalName();
        if (str == null) {
            this.setNodeName(localName);
            return;
        }
        if (!str.equals("") && !DOMUtilities.isValidName(str)) {
            throw this.createDOMException((short)5, "prefix", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), str });
        }
        if (!DOMUtilities.isValidPrefix(str)) {
            throw this.createDOMException((short)14, "prefix", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), str });
        }
        if ((str.equals("xml") && !"http://www.w3.org/XML/1998/namespace".equals(namespaceURI)) || (str.equals("xmlns") && !"http://www.w3.org/2000/xmlns/".equals(namespaceURI))) {
            throw this.createDOMException((short)14, "namespace.uri", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), namespaceURI });
        }
        this.setNodeName(str + ':' + localName);
    }
    
    public String getLocalName() {
        return (this.getNamespaceURI() == null) ? null : DOMUtilities.getLocalName(this.getNodeName());
    }
    
    public DOMException createDOMException(final short n, final String message, final Object[] array) {
        try {
            return new DOMException(n, this.getCurrentDocument().formatMessage(message, array));
        }
        catch (Exception ex) {
            return new DOMException(n, message);
        }
    }
    
    protected String getCascadedXMLBase(Node parentNode) {
        String s = null;
        for (Node node = parentNode.getParentNode(); node != null; node = node.getParentNode()) {
            if (node.getNodeType() == 1) {
                s = this.getCascadedXMLBase(node);
                break;
            }
        }
        if (s == null) {
            AbstractDocument abstractDocument;
            if (parentNode.getNodeType() == 9) {
                abstractDocument = (AbstractDocument)parentNode;
            }
            else {
                abstractDocument = (AbstractDocument)parentNode.getOwnerDocument();
            }
            s = abstractDocument.getDocumentURI();
        }
        while (parentNode != null && parentNode.getNodeType() != 1) {
            parentNode = parentNode.getParentNode();
        }
        if (parentNode == null) {
            return s;
        }
        final Attr attributeNodeNS = ((Element)parentNode).getAttributeNodeNS("http://www.w3.org/XML/1998/namespace", "base");
        if (attributeNodeNS != null) {
            if (s == null) {
                s = attributeNodeNS.getNodeValue();
            }
            else {
                s = new ParsedURL(s, attributeNodeNS.getNodeValue()).toString();
            }
        }
        return s;
    }
    
    public String getBaseURI() {
        return this.getCascadedXMLBase(this);
    }
    
    public static String getBaseURI(final Node node) {
        return ((AbstractNode)node).getBaseURI();
    }
    
    public short compareDocumentPosition(final Node e) throws DOMException {
        if (this == e) {
            return 0;
        }
        final ArrayList<Node> list = (ArrayList<Node>)new ArrayList<Element>(10);
        final ArrayList<AbstractNode> list2 = new ArrayList<AbstractNode>(10);
        int n = 0;
        int n2 = 0;
        Object e2;
        if (this.getNodeType() == 2) {
            list.add(this);
            ++n;
            e2 = ((Attr)this).getOwnerElement();
            if (e.getNodeType() == 2 && e2 == ((Attr)e).getOwnerElement()) {
                if (this.hashCode() < ((Attr)e).hashCode()) {
                    return 34;
                }
                return 36;
            }
        }
        else {
            e2 = this;
        }
        while (e2 != null) {
            if (e2 == e) {
                return 20;
            }
            list.add((Element)e2);
            ++n;
            e2 = ((Node)e2).getParentNode();
        }
        Node e3;
        if (e.getNodeType() == 2) {
            list2.add((AbstractNode)e);
            ++n2;
            e3 = ((Attr)e).getOwnerElement();
        }
        else {
            e3 = e;
        }
        while (e3 != null) {
            if (e3 == this) {
                return 10;
            }
            list2.add((AbstractNode)e3);
            ++n2;
            e3 = e3.getParentNode();
        }
        int n3 = n - 1;
        int n4 = n2 - 1;
        if (list.get(n3) == list2.get(n4)) {
            Node node;
            AbstractNode abstractNode;
            for (node = list.get(n3), abstractNode = list2.get(n4); node == abstractNode; node = list.get(--n3), abstractNode = list2.get(--n4)) {
                e3 = node;
            }
            for (Node node2 = e3.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                if (node2 == node) {
                    return 2;
                }
                if (node2 == abstractNode) {
                    return 4;
                }
            }
            return 1;
        }
        if (this.hashCode() < e.hashCode()) {
            return 35;
        }
        return 37;
    }
    
    public String getTextContent() {
        return null;
    }
    
    public void setTextContent(final String s) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        if (this.getNodeType() != 10) {
            while (this.getFirstChild() != null) {
                this.removeChild(this.getFirstChild());
            }
            this.appendChild(this.getOwnerDocument().createTextNode(s));
        }
    }
    
    public boolean isSameNode(final Node node) {
        return this == node;
    }
    
    public String lookupPrefix(final String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        switch (this.getNodeType()) {
            case 1: {
                return this.lookupNamespacePrefix(s, (Element)this);
            }
            case 9: {
                return ((AbstractNode)((Document)this).getDocumentElement()).lookupPrefix(s);
            }
            case 6:
            case 10:
            case 11:
            case 12: {
                return null;
            }
            case 2: {
                final AbstractNode abstractNode = (AbstractNode)((Attr)this).getOwnerElement();
                if (abstractNode != null) {
                    return abstractNode.lookupPrefix(s);
                }
                return null;
            }
            default: {
                for (Node node = this.getParentNode(); node != null; node = node.getParentNode()) {
                    if (node.getNodeType() == 1) {
                        return ((AbstractNode)node).lookupPrefix(s);
                    }
                }
                return null;
            }
        }
    }
    
    protected String lookupNamespacePrefix(final String s, final Element element) {
        final String namespaceURI = element.getNamespaceURI();
        final String prefix = element.getPrefix();
        if (namespaceURI != null && namespaceURI.equals(s) && prefix != null) {
            final String lookupNamespaceURI = ((AbstractNode)element).lookupNamespaceURI(prefix);
            if (lookupNamespaceURI != null && lookupNamespaceURI.equals(s)) {
                return prefix;
            }
        }
        final NamedNodeMap attributes = element.getAttributes();
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); ++i) {
                final Node item = attributes.item(i);
                if ("xmlns".equals(item.getPrefix()) && item.getNodeValue().equals(s)) {
                    final String localName = item.getLocalName();
                    final String lookupNamespaceURI2 = ((AbstractNode)element).lookupNamespaceURI(localName);
                    if (lookupNamespaceURI2 != null && lookupNamespaceURI2.equals(s)) {
                        return localName;
                    }
                }
            }
        }
        for (Node node = this.getParentNode(); node != null; node = node.getParentNode()) {
            if (node.getNodeType() == 1) {
                return ((AbstractNode)node).lookupNamespacePrefix(s, element);
            }
        }
        return null;
    }
    
    public boolean isDefaultNamespace(final String s) {
        switch (this.getNodeType()) {
            case 9: {
                return ((AbstractNode)((Document)this).getDocumentElement()).isDefaultNamespace(s);
            }
            case 6:
            case 10:
            case 11:
            case 12: {
                return false;
            }
            case 2: {
                final AbstractNode abstractNode = (AbstractNode)((Attr)this).getOwnerElement();
                return abstractNode != null && abstractNode.isDefaultNamespace(s);
            }
            case 1: {
                if (this.getPrefix() == null) {
                    final String namespaceURI = this.getNamespaceURI();
                    return (namespaceURI == null && s == null) || (namespaceURI != null && namespaceURI.equals(s));
                }
                final NamedNodeMap attributes = this.getAttributes();
                if (attributes != null) {
                    for (int i = 0; i < attributes.getLength(); ++i) {
                        final Node item = attributes.item(i);
                        if ("xmlns".equals(item.getLocalName())) {
                            return item.getNodeValue().equals(s);
                        }
                    }
                    break;
                }
                break;
            }
        }
        for (Node parentNode = this; parentNode != null; parentNode = parentNode.getParentNode()) {
            if (parentNode.getNodeType() == 1) {
                return ((AbstractNode)parentNode).isDefaultNamespace(s);
            }
        }
        return false;
    }
    
    public String lookupNamespaceURI(final String s) {
        switch (this.getNodeType()) {
            case 9: {
                return ((AbstractNode)((Document)this).getDocumentElement()).lookupNamespaceURI(s);
            }
            case 6:
            case 10:
            case 11:
            case 12: {
                return null;
            }
            case 2: {
                final AbstractNode abstractNode = (AbstractNode)((Attr)this).getOwnerElement();
                if (abstractNode != null) {
                    return abstractNode.lookupNamespaceURI(s);
                }
                return null;
            }
            case 1: {
                final NamedNodeMap attributes = this.getAttributes();
                if (attributes != null) {
                    int i = 0;
                    while (i < attributes.getLength()) {
                        final Node item = attributes.item(i);
                        final String prefix = item.getPrefix();
                        String anObject = item.getLocalName();
                        if (anObject == null) {
                            anObject = item.getNodeName();
                        }
                        if (("xmlns".equals(prefix) && this.compareStrings(anObject, s)) || ("xmlns".equals(anObject) && s == null)) {
                            final String nodeValue = item.getNodeValue();
                            if (nodeValue.length() > 0) {
                                return nodeValue;
                            }
                            return null;
                        }
                        else {
                            ++i;
                        }
                    }
                    break;
                }
                break;
            }
        }
        for (Node node = this.getParentNode(); node != null; node = node.getParentNode()) {
            if (node.getNodeType() == 1) {
                return ((AbstractNode)node).lookupNamespaceURI(s);
            }
        }
        return null;
    }
    
    public boolean isEqualNode(final Node node) {
        if (node == null) {
            return false;
        }
        final short nodeType = node.getNodeType();
        if (nodeType != this.getNodeType() || !this.compareStrings(this.getNodeName(), node.getNodeName()) || !this.compareStrings(this.getLocalName(), node.getLocalName()) || !this.compareStrings(this.getPrefix(), node.getPrefix()) || !this.compareStrings(this.getNodeValue(), node.getNodeValue()) || !this.compareStrings(this.getNodeValue(), node.getNodeValue()) || !this.compareNamedNodeMaps(this.getAttributes(), node.getAttributes())) {
            return false;
        }
        if (nodeType == 10) {
            final DocumentType documentType = (DocumentType)this;
            final DocumentType documentType2 = (DocumentType)node;
            if (!this.compareStrings(documentType.getPublicId(), documentType2.getPublicId()) || !this.compareStrings(documentType.getSystemId(), documentType2.getSystemId()) || !this.compareStrings(documentType.getInternalSubset(), documentType2.getInternalSubset()) || !this.compareNamedNodeMaps(documentType.getEntities(), documentType2.getEntities()) || !this.compareNamedNodeMaps(documentType.getNotations(), documentType2.getNotations())) {
                return false;
            }
        }
        final Node firstChild = this.getFirstChild();
        final Node firstChild2 = node.getFirstChild();
        return (firstChild == null || firstChild2 == null || ((AbstractNode)firstChild).isEqualNode(firstChild2)) && firstChild == firstChild2;
    }
    
    protected boolean compareStrings(final String s, final String anObject) {
        return (s != null && s.equals(anObject)) || (s == null && anObject == null);
    }
    
    protected boolean compareNamedNodeMaps(final NamedNodeMap namedNodeMap, final NamedNodeMap namedNodeMap2) {
        if ((namedNodeMap == null && namedNodeMap2 != null) || (namedNodeMap != null && namedNodeMap2 == null)) {
            return false;
        }
        if (namedNodeMap != null) {
            final int length = namedNodeMap.getLength();
            if (length != namedNodeMap2.getLength()) {
                return false;
            }
            for (int i = 0; i < length; ++i) {
                final Node item = namedNodeMap.item(i);
                final String localName = item.getLocalName();
                Node node;
                if (localName != null) {
                    node = namedNodeMap2.getNamedItemNS(item.getNamespaceURI(), localName);
                }
                else {
                    node = namedNodeMap2.getNamedItem(item.getNodeName());
                }
                if (!((AbstractNode)item).isEqualNode(node)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Object getFeature(final String s, final String s2) {
        return null;
    }
    
    public Object getUserData(final String key) {
        if (this.userData == null) {
            return null;
        }
        return this.userData.get(key);
    }
    
    public Object setUserData(final String s, final Object value, final UserDataHandler value2) {
        if (this.userData == null) {
            this.userData = new HashMap();
            this.userDataHandlers = new HashMap();
        }
        if (value == null) {
            this.userData.remove(s);
            return this.userDataHandlers.remove(s);
        }
        this.userDataHandlers.put(s, value2);
        return this.userData.put(s, value);
    }
    
    protected void fireUserDataHandlers(final short n, final Node node, final Node node2) {
        final AbstractNode abstractNode = (AbstractNode)node;
        if (abstractNode.userData != null) {
            for (final Map.Entry<Object, V> entry : abstractNode.userData.entrySet()) {
                final UserDataHandler userDataHandler = abstractNode.userDataHandlers.get(entry.getKey());
                if (userDataHandler != null) {
                    userDataHandler.handle(n, entry.getKey(), entry.getValue(), node, node2);
                }
            }
        }
    }
    
    public void addEventListener(final String s, final EventListener eventListener, final boolean b) {
        if (this.eventSupport == null) {
            this.initializeEventSupport();
        }
        this.eventSupport.addEventListener(s, eventListener, b);
    }
    
    public void addEventListenerNS(String s, final String s2, final EventListener eventListener, final boolean b, final Object o) {
        if (this.eventSupport == null) {
            this.initializeEventSupport();
        }
        if (s != null && s.length() == 0) {
            s = null;
        }
        this.eventSupport.addEventListenerNS(s, s2, eventListener, b, o);
    }
    
    public void removeEventListener(final String s, final EventListener eventListener, final boolean b) {
        if (this.eventSupport != null) {
            this.eventSupport.removeEventListener(s, eventListener, b);
        }
    }
    
    public void removeEventListenerNS(String s, final String s2, final EventListener eventListener, final boolean b) {
        if (this.eventSupport != null) {
            if (s != null && s.length() == 0) {
                s = null;
            }
            this.eventSupport.removeEventListenerNS(s, s2, eventListener, b);
        }
    }
    
    public NodeEventTarget getParentNodeEventTarget() {
        return (NodeEventTarget)this.getXblParentNode();
    }
    
    public boolean dispatchEvent(final Event event) throws EventException {
        if (this.eventSupport == null) {
            this.initializeEventSupport();
        }
        return this.eventSupport.dispatchEvent(this, event);
    }
    
    public boolean willTriggerNS(final String s, final String s2) {
        return true;
    }
    
    public boolean hasEventListenerNS(String s, final String s2) {
        if (this.eventSupport == null) {
            return false;
        }
        if (s != null && s.length() == 0) {
            s = null;
        }
        return this.eventSupport.hasEventListenerNS(s, s2);
    }
    
    public EventSupport getEventSupport() {
        return this.eventSupport;
    }
    
    public EventSupport initializeEventSupport() {
        if (this.eventSupport == null) {
            final AbstractDocument currentDocument = this.getCurrentDocument();
            this.eventSupport = ((AbstractDOMImplementation)currentDocument.getImplementation()).createEventSupport(this);
            currentDocument.setEventsEnabled(true);
        }
        return this.eventSupport;
    }
    
    public void fireDOMNodeInsertedIntoDocumentEvent() {
        final AbstractDocument currentDocument = this.getCurrentDocument();
        if (currentDocument.getEventsEnabled()) {
            final DOMMutationEvent domMutationEvent = (DOMMutationEvent)currentDocument.createEvent("MutationEvents");
            domMutationEvent.initMutationEventNS("http://www.w3.org/2001/xml-events", "DOMNodeInsertedIntoDocument", true, false, null, null, null, null, (short)2);
            this.dispatchEvent(domMutationEvent);
        }
    }
    
    public void fireDOMNodeRemovedFromDocumentEvent() {
        final AbstractDocument currentDocument = this.getCurrentDocument();
        if (currentDocument.getEventsEnabled()) {
            final DOMMutationEvent domMutationEvent = (DOMMutationEvent)currentDocument.createEvent("MutationEvents");
            domMutationEvent.initMutationEventNS("http://www.w3.org/2001/xml-events", "DOMNodeRemovedFromDocument", true, false, null, null, null, null, (short)3);
            this.dispatchEvent(domMutationEvent);
        }
    }
    
    protected void fireDOMCharacterDataModifiedEvent(final String s, final String s2) {
        final AbstractDocument currentDocument = this.getCurrentDocument();
        if (currentDocument.getEventsEnabled()) {
            final DOMMutationEvent domMutationEvent = (DOMMutationEvent)currentDocument.createEvent("MutationEvents");
            domMutationEvent.initMutationEventNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", true, false, null, s, s2, null, (short)1);
            this.dispatchEvent(domMutationEvent);
        }
    }
    
    protected AbstractDocument getCurrentDocument() {
        return this.ownerDocument;
    }
    
    protected abstract Node newNode();
    
    protected Node export(final Node node, final AbstractDocument ownerDocument) {
        final AbstractNode abstractNode = (AbstractNode)node;
        abstractNode.ownerDocument = ownerDocument;
        abstractNode.setReadonly(false);
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument ownerDocument) {
        final AbstractNode abstractNode = (AbstractNode)node;
        abstractNode.ownerDocument = ownerDocument;
        abstractNode.setReadonly(false);
        return node;
    }
    
    protected Node copyInto(final Node node) {
        ((AbstractNode)node).ownerDocument = this.ownerDocument;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        ((AbstractNode)node).ownerDocument = this.ownerDocument;
        return node;
    }
    
    protected void checkChildType(final Node node, final boolean b) {
        throw this.createDOMException((short)3, "children.not.allowed", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
    }
    
    public Node getXblParentNode() {
        return this.ownerDocument.getXBLManager().getXblParentNode(this);
    }
    
    public NodeList getXblChildNodes() {
        return this.ownerDocument.getXBLManager().getXblChildNodes(this);
    }
    
    public NodeList getXblScopedChildNodes() {
        return this.ownerDocument.getXBLManager().getXblScopedChildNodes(this);
    }
    
    public Node getXblFirstChild() {
        return this.ownerDocument.getXBLManager().getXblFirstChild(this);
    }
    
    public Node getXblLastChild() {
        return this.ownerDocument.getXBLManager().getXblLastChild(this);
    }
    
    public Node getXblPreviousSibling() {
        return this.ownerDocument.getXBLManager().getXblPreviousSibling(this);
    }
    
    public Node getXblNextSibling() {
        return this.ownerDocument.getXBLManager().getXblNextSibling(this);
    }
    
    public Element getXblFirstElementChild() {
        return this.ownerDocument.getXBLManager().getXblFirstElementChild(this);
    }
    
    public Element getXblLastElementChild() {
        return this.ownerDocument.getXBLManager().getXblLastElementChild(this);
    }
    
    public Element getXblPreviousElementSibling() {
        return this.ownerDocument.getXBLManager().getXblPreviousElementSibling(this);
    }
    
    public Element getXblNextElementSibling() {
        return this.ownerDocument.getXBLManager().getXblNextElementSibling(this);
    }
    
    public Element getXblBoundElement() {
        return this.ownerDocument.getXBLManager().getXblBoundElement(this);
    }
    
    public Element getXblShadowTree() {
        return this.ownerDocument.getXBLManager().getXblShadowTree(this);
    }
    
    public NodeList getXblDefinitions() {
        return this.ownerDocument.getXBLManager().getXblDefinitions(this);
    }
    
    public Object getManagerData() {
        return this.managerData;
    }
    
    public void setManagerData(final Object managerData) {
        this.managerData = managerData;
    }
    
    static {
        EMPTY_NODE_LIST = new NodeList() {
            public Node item(final int n) {
                return null;
            }
            
            public int getLength() {
                return 0;
            }
        };
    }
}
