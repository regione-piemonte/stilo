// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.apache.batik.util.CleanerThread;
import org.w3c.dom.DOMLocator;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.xpath.XPathResult;
import org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;
import org.apache.xml.utils.PrefixResolver;
import javax.xml.transform.SourceLocator;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPath;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import org.w3c.dom.NodeList;
import org.w3c.dom.xpath.XPathException;
import org.w3c.dom.xpath.XPathExpression;
import org.w3c.dom.xpath.XPathNSResolver;
import org.w3c.dom.DOMError;
import org.apache.batik.xml.XMLUtilities;
import java.util.LinkedList;
import org.w3c.dom.DOMErrorHandler;
import org.apache.batik.dom.events.EventSupport;
import org.w3c.dom.events.MutationNameEvent;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.traversal.TreeWalker;
import org.w3c.dom.traversal.NodeIterator;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.events.Event;
import org.apache.batik.util.SoftDoublyIndexedTable;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import java.util.MissingResourceException;
import java.util.Locale;
import org.w3c.dom.Node;
import org.w3c.dom.DocumentType;
import org.apache.batik.dom.xbl.GenericXBLManager;
import java.util.Map;
import org.apache.batik.dom.xbl.XBLManager;
import java.util.WeakHashMap;
import org.apache.batik.dom.events.DocumentEventSupport;
import org.apache.batik.dom.traversal.TraversalSupport;
import org.w3c.dom.DOMImplementation;
import org.apache.batik.i18n.LocalizableSupport;
import org.w3c.dom.xpath.XPathEvaluator;
import org.apache.batik.i18n.Localizable;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.events.DocumentEvent;
import org.w3c.dom.Document;

public abstract class AbstractDocument extends AbstractParentNode implements Document, DocumentEvent, DocumentTraversal, Localizable, XPathEvaluator
{
    protected static final String RESOURCES = "org.apache.batik.dom.resources.Messages";
    protected transient LocalizableSupport localizableSupport;
    protected transient DOMImplementation implementation;
    protected transient TraversalSupport traversalSupport;
    protected transient DocumentEventSupport documentEventSupport;
    protected transient boolean eventsEnabled;
    protected transient WeakHashMap elementsByTagNames;
    protected transient WeakHashMap elementsByTagNamesNS;
    protected String inputEncoding;
    protected String xmlEncoding;
    protected String xmlVersion;
    protected boolean xmlStandalone;
    protected String documentURI;
    protected boolean strictErrorChecking;
    protected DocumentConfiguration domConfig;
    protected transient XBLManager xblManager;
    protected transient Map elementsById;
    
    protected AbstractDocument() {
        this.localizableSupport = new LocalizableSupport("org.apache.batik.dom.resources.Messages", this.getClass().getClassLoader());
        this.xmlVersion = "1.0";
        this.strictErrorChecking = true;
        this.xblManager = new GenericXBLManager();
    }
    
    public AbstractDocument(final DocumentType documentType, final DOMImplementation implementation) {
        this.localizableSupport = new LocalizableSupport("org.apache.batik.dom.resources.Messages", this.getClass().getClassLoader());
        this.xmlVersion = "1.0";
        this.strictErrorChecking = true;
        this.xblManager = new GenericXBLManager();
        this.implementation = implementation;
        if (documentType != null) {
            if (documentType instanceof GenericDocumentType) {
                final GenericDocumentType genericDocumentType = (GenericDocumentType)documentType;
                if (genericDocumentType.getOwnerDocument() == null) {
                    genericDocumentType.setOwnerDocument(this);
                }
            }
            this.appendChild(documentType);
        }
    }
    
    public void setDocumentInputEncoding(final String inputEncoding) {
        this.inputEncoding = inputEncoding;
    }
    
    public void setDocumentXmlEncoding(final String xmlEncoding) {
        this.xmlEncoding = xmlEncoding;
    }
    
    public void setLocale(final Locale locale) {
        this.localizableSupport.setLocale(locale);
    }
    
    public Locale getLocale() {
        return this.localizableSupport.getLocale();
    }
    
    public String formatMessage(final String s, final Object[] array) throws MissingResourceException {
        return this.localizableSupport.formatMessage(s, array);
    }
    
    public boolean getEventsEnabled() {
        return this.eventsEnabled;
    }
    
    public void setEventsEnabled(final boolean eventsEnabled) {
        this.eventsEnabled = eventsEnabled;
    }
    
    public String getNodeName() {
        return "#document";
    }
    
    public short getNodeType() {
        return 9;
    }
    
    public DocumentType getDoctype() {
        for (Node node = this.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 10) {
                return (DocumentType)node;
            }
        }
        return null;
    }
    
    public void setDoctype(final DocumentType documentType) {
        if (documentType != null) {
            this.appendChild(documentType);
            ((ExtendedNode)documentType).setReadonly(true);
        }
    }
    
    public DOMImplementation getImplementation() {
        return this.implementation;
    }
    
    public Element getDocumentElement() {
        for (Node node = this.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                return (Element)node;
            }
        }
        return null;
    }
    
    public Node importNode(final Node node, final boolean b) throws DOMException {
        return this.importNode(node, b, false);
    }
    
    public Node importNode(final Node node, boolean b, final boolean b2) {
        Object o = null;
        switch (node.getNodeType()) {
            case 1: {
                final Element element = (Element)(o = this.createElementNS(node.getNamespaceURI(), node.getNodeName()));
                if (node.hasAttributes()) {
                    final NamedNodeMap attributes = node.getAttributes();
                    for (int length = attributes.getLength(), i = 0; i < length; ++i) {
                        final Attr attr = (Attr)attributes.item(i);
                        if (attr.getSpecified()) {
                            final AbstractAttr attributeNodeNS = (AbstractAttr)this.importNode(attr, true);
                            if (b2 && attributeNodeNS.isId()) {
                                attributeNodeNS.setIsId(false);
                            }
                            element.setAttributeNodeNS(attributeNodeNS);
                        }
                    }
                    break;
                }
                break;
            }
            case 2: {
                o = this.createAttributeNS(node.getNamespaceURI(), node.getNodeName());
                break;
            }
            case 3: {
                o = this.createTextNode(node.getNodeValue());
                b = false;
                break;
            }
            case 4: {
                o = this.createCDATASection(node.getNodeValue());
                b = false;
                break;
            }
            case 5: {
                o = this.createEntityReference(node.getNodeName());
                break;
            }
            case 7: {
                o = this.createProcessingInstruction(node.getNodeName(), node.getNodeValue());
                b = false;
                break;
            }
            case 8: {
                o = this.createComment(node.getNodeValue());
                b = false;
                break;
            }
            case 11: {
                o = this.createDocumentFragment();
                break;
            }
            default: {
                throw this.createDOMException((short)9, "import.node", new Object[0]);
            }
        }
        if (node instanceof AbstractNode) {
            this.fireUserDataHandlers((short)2, node, (Node)o);
        }
        if (b) {
            for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                ((Node)o).appendChild(this.importNode(node2, true));
            }
        }
        return (Node)o;
    }
    
    public Node cloneNode(final boolean b) {
        final Document document = (Document)this.newNode();
        this.copyInto(document);
        this.fireUserDataHandlers((short)1, this, document);
        if (b) {
            for (Node node = this.getFirstChild(); node != null; node = node.getNextSibling()) {
                document.appendChild(document.importNode(node, b));
            }
        }
        return document;
    }
    
    public abstract boolean isId(final Attr p0);
    
    public Element getElementById(final String s) {
        return this.getChildElementById(this.getDocumentElement(), s);
    }
    
    public Element getChildElementById(final Node node, final String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        if (this.elementsById == null) {
            return null;
        }
        final Node root = this.getRoot(node);
        final IdSoftRef value = this.elementsById.get(s);
        if (value == null) {
            return null;
        }
        if (!(value instanceof IdSoftRef)) {
            final Iterator<IdSoftRef> iterator = ((List<IdSoftRef>)value).iterator();
            while (iterator.hasNext()) {
                final Object value2 = iterator.next().get();
                if (value2 == null) {
                    iterator.remove();
                }
                else {
                    final Element element = (Element)value2;
                    if (this.getRoot(element) == root) {
                        return element;
                    }
                    continue;
                }
            }
            return null;
        }
        final Object value3 = value.get();
        if (value3 == null) {
            this.elementsById.remove(s);
            return null;
        }
        final Element element2 = (Element)value3;
        if (this.getRoot(element2) == root) {
            return element2;
        }
        return null;
    }
    
    protected Node getRoot(Node parentNode) {
        Node node = parentNode;
        while (parentNode != null) {
            node = parentNode;
            parentNode = parentNode.getParentNode();
        }
        return node;
    }
    
    public void removeIdEntry(final Element element, final String s) {
        if (s == null) {
            return;
        }
        if (this.elementsById == null) {
            return;
        }
        synchronized (this.elementsById) {
            final List<IdSoftRef> value = this.elementsById.get(s);
            if (value == null) {
                return;
            }
            if (value instanceof IdSoftRef) {
                this.elementsById.remove(s);
                return;
            }
            final List<IdSoftRef> list = value;
            final Iterator<IdSoftRef> iterator = list.iterator();
            while (iterator.hasNext()) {
                final Object value2 = iterator.next().get();
                if (value2 == null) {
                    iterator.remove();
                }
                else {
                    if (element == value2) {
                        iterator.remove();
                        break;
                    }
                    continue;
                }
            }
            if (list.size() == 0) {
                this.elementsById.remove(s);
            }
        }
    }
    
    public void addIdEntry(final Element element, final String s) {
        if (s == null) {
            return;
        }
        if (this.elementsById == null) {
            final HashMap<String, IdSoftRef> elementsById = new HashMap<String, IdSoftRef>();
            elementsById.put(s, new IdSoftRef(element, s));
            this.elementsById = elementsById;
            return;
        }
        synchronized (this.elementsById) {
            final List<IdSoftRef> value = this.elementsById.get(s);
            if (value == null) {
                this.elementsById.put(s, new IdSoftRef(element, s));
                return;
            }
            if (value instanceof IdSoftRef) {
                final IdSoftRef idSoftRef = (IdSoftRef)value;
                if (idSoftRef.get() == null) {
                    this.elementsById.put(s, new IdSoftRef(element, s));
                    return;
                }
                final ArrayList<IdSoftRef> list = new ArrayList<IdSoftRef>(4);
                idSoftRef.setList(list);
                list.add(idSoftRef);
                list.add(new IdSoftRef(element, s, list));
                this.elementsById.put(s, list);
            }
            else {
                final List<IdSoftRef> list2 = value;
                list2.add(new IdSoftRef(element, s, list2));
            }
        }
    }
    
    public void updateIdEntry(final Element element, final String s, final String anObject) {
        if (s == anObject || (s != null && s.equals(anObject))) {
            return;
        }
        this.removeIdEntry(element, s);
        this.addIdEntry(element, anObject);
    }
    
    public ElementsByTagName getElementsByTagName(final Node key, final String s) {
        if (this.elementsByTagNames == null) {
            return null;
        }
        final SoftDoublyIndexedTable softDoublyIndexedTable = this.elementsByTagNames.get(key);
        if (softDoublyIndexedTable == null) {
            return null;
        }
        return (ElementsByTagName)softDoublyIndexedTable.get(null, s);
    }
    
    public void putElementsByTagName(final Node node, final String s, final ElementsByTagName elementsByTagName) {
        if (this.elementsByTagNames == null) {
            this.elementsByTagNames = new WeakHashMap(11);
        }
        SoftDoublyIndexedTable softDoublyIndexedTable = this.elementsByTagNames.get(node);
        if (softDoublyIndexedTable == null) {
            this.elementsByTagNames.put(node, softDoublyIndexedTable = new SoftDoublyIndexedTable());
        }
        softDoublyIndexedTable.put(null, s, elementsByTagName);
    }
    
    public ElementsByTagNameNS getElementsByTagNameNS(final Node key, final String s, final String s2) {
        if (this.elementsByTagNamesNS == null) {
            return null;
        }
        final SoftDoublyIndexedTable softDoublyIndexedTable = this.elementsByTagNamesNS.get(key);
        if (softDoublyIndexedTable == null) {
            return null;
        }
        return (ElementsByTagNameNS)softDoublyIndexedTable.get(s, s2);
    }
    
    public void putElementsByTagNameNS(final Node node, final String s, final String s2, final ElementsByTagNameNS elementsByTagNameNS) {
        if (this.elementsByTagNamesNS == null) {
            this.elementsByTagNamesNS = new WeakHashMap(11);
        }
        SoftDoublyIndexedTable softDoublyIndexedTable = this.elementsByTagNamesNS.get(node);
        if (softDoublyIndexedTable == null) {
            this.elementsByTagNamesNS.put(node, softDoublyIndexedTable = new SoftDoublyIndexedTable());
        }
        softDoublyIndexedTable.put(s, s2, elementsByTagNameNS);
    }
    
    public Event createEvent(final String s) throws DOMException {
        if (this.documentEventSupport == null) {
            this.documentEventSupport = ((AbstractDOMImplementation)this.implementation).createDocumentEventSupport();
        }
        return this.documentEventSupport.createEvent(s);
    }
    
    public boolean canDispatch(String s, final String s2) {
        if (s2 == null) {
            return false;
        }
        if (s != null && s.length() == 0) {
            s = null;
        }
        return (s == null || s.equals("http://www.w3.org/2001/xml-events")) && (s2.equals("Event") || s2.equals("MutationEvent") || s2.equals("MutationNameEvent") || s2.equals("UIEvent") || s2.equals("MouseEvent") || s2.equals("KeyEvent") || s2.equals("KeyboardEvent") || s2.equals("TextEvent") || s2.equals("CustomEvent"));
    }
    
    public NodeIterator createNodeIterator(final Node node, final int n, final NodeFilter nodeFilter, final boolean b) throws DOMException {
        if (this.traversalSupport == null) {
            this.traversalSupport = new TraversalSupport();
        }
        return this.traversalSupport.createNodeIterator(this, node, n, nodeFilter, b);
    }
    
    public TreeWalker createTreeWalker(final Node node, final int n, final NodeFilter nodeFilter, final boolean b) throws DOMException {
        return TraversalSupport.createTreeWalker(this, node, n, nodeFilter, b);
    }
    
    public void detachNodeIterator(final NodeIterator nodeIterator) {
        this.traversalSupport.detachNodeIterator(nodeIterator);
    }
    
    public void nodeToBeRemoved(final Node node) {
        if (this.traversalSupport != null) {
            this.traversalSupport.nodeToBeRemoved(node);
        }
    }
    
    protected AbstractDocument getCurrentDocument() {
        return this;
    }
    
    protected Node export(final Node node, final Document document) {
        throw this.createDOMException((short)9, "import.document", new Object[0]);
    }
    
    protected Node deepExport(final Node node, final Document document) {
        throw this.createDOMException((short)9, "import.document", new Object[0]);
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        final AbstractDocument abstractDocument = (AbstractDocument)node;
        abstractDocument.implementation = this.implementation;
        abstractDocument.localizableSupport = new LocalizableSupport("org.apache.batik.dom.resources.Messages", this.getClass().getClassLoader());
        abstractDocument.inputEncoding = this.inputEncoding;
        abstractDocument.xmlEncoding = this.xmlEncoding;
        abstractDocument.xmlVersion = this.xmlVersion;
        abstractDocument.xmlStandalone = this.xmlStandalone;
        abstractDocument.documentURI = this.documentURI;
        abstractDocument.strictErrorChecking = this.strictErrorChecking;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        final AbstractDocument abstractDocument = (AbstractDocument)node;
        abstractDocument.implementation = this.implementation;
        abstractDocument.localizableSupport = new LocalizableSupport("org.apache.batik.dom.resources.Messages", this.getClass().getClassLoader());
        return node;
    }
    
    protected void checkChildType(final Node node, final boolean b) {
        final short nodeType = node.getNodeType();
        switch (nodeType) {
            case 1:
            case 7:
            case 8:
            case 10:
            case 11: {
                if ((!b && nodeType == 1 && this.getDocumentElement() != null) || (nodeType == 10 && this.getDoctype() != null)) {
                    throw this.createDOMException((short)9, "document.child.already.exists", new Object[] { new Integer(nodeType), node.getNodeName() });
                }
            }
            default: {
                throw this.createDOMException((short)3, "child.type", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), new Integer(nodeType), node.getNodeName() });
            }
        }
    }
    
    public String getInputEncoding() {
        return this.inputEncoding;
    }
    
    public String getXmlEncoding() {
        return this.xmlEncoding;
    }
    
    public boolean getXmlStandalone() {
        return this.xmlStandalone;
    }
    
    public void setXmlStandalone(final boolean xmlStandalone) throws DOMException {
        this.xmlStandalone = xmlStandalone;
    }
    
    public String getXmlVersion() {
        return this.xmlVersion;
    }
    
    public void setXmlVersion(final String xmlVersion) throws DOMException {
        if (xmlVersion == null || (!xmlVersion.equals("1.0") && !xmlVersion.equals("1.1"))) {
            throw this.createDOMException((short)9, "xml.version", new Object[] { xmlVersion });
        }
        this.xmlVersion = xmlVersion;
    }
    
    public boolean getStrictErrorChecking() {
        return this.strictErrorChecking;
    }
    
    public void setStrictErrorChecking(final boolean strictErrorChecking) {
        this.strictErrorChecking = strictErrorChecking;
    }
    
    public String getDocumentURI() {
        return this.documentURI;
    }
    
    public void setDocumentURI(final String documentURI) {
        this.documentURI = documentURI;
    }
    
    public DOMConfiguration getDomConfig() {
        if (this.domConfig == null) {
            this.domConfig = new DocumentConfiguration();
        }
        return this.domConfig;
    }
    
    public Node adoptNode(final Node node) throws DOMException {
        if (!(node instanceof AbstractNode)) {
            return null;
        }
        switch (node.getNodeType()) {
            case 9: {
                throw this.createDOMException((short)9, "adopt.document", new Object[0]);
            }
            case 10: {
                throw this.createDOMException((short)9, "adopt.document.type", new Object[0]);
            }
            case 6:
            case 12: {
                return null;
            }
            default: {
                final AbstractNode abstractNode = (AbstractNode)node;
                if (abstractNode.isReadonly()) {
                    throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(abstractNode.getNodeType()), abstractNode.getNodeName() });
                }
                final Node parentNode = node.getParentNode();
                if (parentNode != null) {
                    parentNode.removeChild(node);
                }
                this.adoptNode1((AbstractNode)node);
                return node;
            }
        }
    }
    
    protected void adoptNode1(final AbstractNode abstractNode) {
        abstractNode.ownerDocument = this;
        switch (abstractNode.getNodeType()) {
            case 2: {
                final AbstractAttr abstractAttr = (AbstractAttr)abstractNode;
                abstractAttr.ownerElement = null;
                abstractAttr.unspecified = false;
                break;
            }
            case 1: {
                final NamedNodeMap attributes = abstractNode.getAttributes();
                for (int length = attributes.getLength(), i = 0; i < length; ++i) {
                    final AbstractAttr abstractAttr2 = (AbstractAttr)attributes.item(i);
                    if (abstractAttr2.getSpecified()) {
                        this.adoptNode1(abstractAttr2);
                    }
                }
                break;
            }
            case 5: {
                while (abstractNode.getFirstChild() != null) {
                    abstractNode.removeChild(abstractNode.getFirstChild());
                }
                break;
            }
        }
        this.fireUserDataHandlers((short)5, abstractNode, null);
        Node node = abstractNode.getFirstChild();
        while (node != null) {
            switch (node.getNodeType()) {
                case 6:
                case 10:
                case 12: {}
                default: {
                    this.adoptNode1((AbstractNode)node);
                    node = node.getNextSibling();
                    continue;
                }
            }
        }
    }
    
    public Node renameNode(final Node node, String namespaceURI, final String nodeName) {
        final AbstractNode abstractNode = (AbstractNode)node;
        if (abstractNode == this.getDocumentElement()) {
            throw this.createDOMException((short)9, "rename.document.element", new Object[0]);
        }
        final short nodeType = node.getNodeType();
        if (nodeType != 1 && nodeType != 2) {
            throw this.createDOMException((short)9, "rename.node", new Object[] { new Integer(nodeType), node.getNodeName() });
        }
        if ((this.xmlVersion.equals("1.1") && !DOMUtilities.isValidName11(nodeName)) || !DOMUtilities.isValidName(nodeName)) {
            throw this.createDOMException((short)9, "wf.invalid.name", new Object[] { nodeName });
        }
        if (node.getOwnerDocument() != this) {
            throw this.createDOMException((short)9, "node.from.wrong.document", new Object[] { new Integer(nodeType), node.getNodeName() });
        }
        final int index = nodeName.indexOf(58);
        if (index == 0 || index == nodeName.length() - 1) {
            throw this.createDOMException((short)14, "qname", new Object[] { new Integer(nodeType), node.getNodeName(), nodeName });
        }
        final String prefix = DOMUtilities.getPrefix(nodeName);
        if (namespaceURI != null && namespaceURI.length() == 0) {
            namespaceURI = null;
        }
        if (prefix != null && namespaceURI == null) {
            throw this.createDOMException((short)14, "prefix", new Object[] { new Integer(nodeType), node.getNodeName(), prefix });
        }
        if (this.strictErrorChecking && (("xml".equals(prefix) && !"http://www.w3.org/XML/1998/namespace".equals(namespaceURI)) || ("xmlns".equals(prefix) && !"http://www.w3.org/2000/xmlns/".equals(namespaceURI)))) {
            throw this.createDOMException((short)14, "namespace", new Object[] { new Integer(nodeType), node.getNodeName(), namespaceURI });
        }
        final String namespaceURI2 = node.getNamespaceURI();
        final String nodeName2 = node.getNodeName();
        if (nodeType == 1) {
            final Node parentNode = node.getParentNode();
            final AbstractElement abstractElement = (AbstractElement)this.createElementNS(namespaceURI, nodeName);
            final EventSupport eventSupport = abstractNode.getEventSupport();
            if (eventSupport != null) {
                if (abstractElement.getEventSupport() == null) {
                    final EventSupport eventSupport2 = ((AbstractDOMImplementation)this.implementation).createEventSupport(abstractElement);
                    this.setEventsEnabled(true);
                    abstractElement.eventSupport = eventSupport2;
                }
                eventSupport.moveEventListeners(abstractElement.getEventSupport());
            }
            abstractElement.userData = ((abstractElement.userData == null) ? null : ((HashMap)abstractNode.userData.clone()));
            abstractElement.userDataHandlers = ((abstractElement.userDataHandlers == null) ? null : ((HashMap)abstractNode.userDataHandlers.clone()));
            final Node node2 = null;
            if (parentNode != null) {
                node.getNextSibling();
                parentNode.removeChild(node);
            }
            while (node.getFirstChild() != null) {
                abstractElement.appendChild(node.getFirstChild());
            }
            final NamedNodeMap attributes = node.getAttributes();
            for (int i = 0; i < attributes.getLength(); ++i) {
                abstractElement.setAttributeNodeNS((Attr)attributes.item(i));
            }
            if (parentNode != null) {
                if (node2 == null) {
                    parentNode.appendChild(abstractElement);
                }
                else {
                    parentNode.insertBefore(node2, abstractElement);
                }
            }
            this.fireUserDataHandlers((short)4, node, abstractElement);
            if (this.getEventsEnabled()) {
                final MutationNameEvent mutationNameEvent = (MutationNameEvent)this.createEvent("MutationNameEvent");
                mutationNameEvent.initMutationNameEventNS("http://www.w3.org/2001/xml-events", "DOMElementNameChanged", true, false, null, namespaceURI2, nodeName2);
                this.dispatchEvent(mutationNameEvent);
            }
            return abstractElement;
        }
        if (node instanceof AbstractAttrNS) {
            final AbstractAttrNS attributeNodeNS = (AbstractAttrNS)node;
            final Element ownerElement = attributeNodeNS.getOwnerElement();
            if (ownerElement != null) {
                ownerElement.removeAttributeNode(attributeNodeNS);
            }
            attributeNodeNS.namespaceURI = namespaceURI;
            attributeNodeNS.nodeName = nodeName;
            if (ownerElement != null) {
                ownerElement.setAttributeNodeNS(attributeNodeNS);
            }
            this.fireUserDataHandlers((short)4, attributeNodeNS, null);
            if (this.getEventsEnabled()) {
                final MutationNameEvent mutationNameEvent2 = (MutationNameEvent)this.createEvent("MutationNameEvent");
                mutationNameEvent2.initMutationNameEventNS("http://www.w3.org/2001/xml-events", "DOMAttrNameChanged", true, false, attributeNodeNS, namespaceURI2, nodeName2);
                this.dispatchEvent(mutationNameEvent2);
            }
            return attributeNodeNS;
        }
        final AbstractAttr abstractAttr = (AbstractAttr)node;
        final Element ownerElement2 = abstractAttr.getOwnerElement();
        if (ownerElement2 != null) {
            ownerElement2.removeAttributeNode(abstractAttr);
        }
        final AbstractAttr attributeNodeNS2 = (AbstractAttr)this.createAttributeNS(namespaceURI, nodeName);
        attributeNodeNS2.setNodeValue(abstractAttr.getNodeValue());
        attributeNodeNS2.userData = ((abstractAttr.userData == null) ? null : ((HashMap)abstractAttr.userData.clone()));
        attributeNodeNS2.userDataHandlers = ((abstractAttr.userDataHandlers == null) ? null : ((HashMap)abstractAttr.userDataHandlers.clone()));
        if (ownerElement2 != null) {
            ownerElement2.setAttributeNodeNS(attributeNodeNS2);
        }
        this.fireUserDataHandlers((short)4, abstractAttr, attributeNodeNS2);
        if (this.getEventsEnabled()) {
            final MutationNameEvent mutationNameEvent3 = (MutationNameEvent)this.createEvent("MutationNameEvent");
            mutationNameEvent3.initMutationNameEventNS("http://www.w3.org/2001/xml-events", "DOMAttrNameChanged", true, false, attributeNodeNS2, namespaceURI2, nodeName2);
            this.dispatchEvent(mutationNameEvent3);
        }
        return attributeNodeNS2;
    }
    
    public void normalizeDocument() {
        if (this.domConfig == null) {
            this.domConfig = new DocumentConfiguration();
        }
        this.normalizeDocument(this.getDocumentElement(), this.domConfig.getBooleanParameter("cdata-sections"), this.domConfig.getBooleanParameter("comments"), this.domConfig.getBooleanParameter("element-content-whitespace"), this.domConfig.getBooleanParameter("namespace-declarations"), this.domConfig.getBooleanParameter("namespaces"), this.domConfig.getBooleanParameter("split-cdata-sections"), (DOMErrorHandler)this.domConfig.getParameter("error-handler"));
    }
    
    protected boolean normalizeDocument(final Element element, final boolean b, final boolean b2, final boolean b3, final boolean b4, final boolean b5, final boolean b6, final DOMErrorHandler domErrorHandler) {
        final AbstractElement abstractElement = (AbstractElement)element;
        Node node = element.getFirstChild();
        while (node != null) {
            short n = node.getNodeType();
            if (n == 3 || (!b && n == 4)) {
                final Node node2 = node;
                final StringBuffer sb = new StringBuffer();
                sb.append(node2.getNodeValue());
                Node nextSibling;
                Node nextSibling2;
                for (nextSibling = node.getNextSibling(); nextSibling != null && (nextSibling.getNodeType() == 3 || (!b && nextSibling.getNodeType() == 4)); nextSibling = nextSibling2) {
                    sb.append(nextSibling.getNodeValue());
                    nextSibling2 = nextSibling.getNextSibling();
                    element.removeChild(nextSibling);
                }
                final String string = sb.toString();
                if (string.length() == 0) {
                    final Node nextSibling3 = nextSibling.getNextSibling();
                    element.removeChild(nextSibling);
                    node = nextSibling3;
                    continue;
                }
                if (!string.equals(node2.getNodeValue())) {
                    if (!b && n == 3) {
                        node = this.createTextNode(string);
                        element.replaceChild(node, node2);
                    }
                    else {
                        node = node2;
                        node2.setNodeValue(string);
                    }
                }
                else {
                    node = node2;
                }
                if (!b3) {
                    n = node.getNodeType();
                    if (n == 3 && ((AbstractText)node).isElementContentWhitespace()) {
                        final Node nextSibling4 = node.getNextSibling();
                        element.removeChild(node);
                        node = nextSibling4;
                        continue;
                    }
                }
                if (n == 4 && b6 && !this.splitCdata(element, node, domErrorHandler)) {
                    return false;
                }
            }
            else if (n == 4 && b6) {
                if (!this.splitCdata(element, node, domErrorHandler)) {
                    return false;
                }
            }
            else if (n == 8 && !b2) {
                Node node3 = node.getPreviousSibling();
                if (node3 == null) {
                    node3 = node.getNextSibling();
                }
                element.removeChild(node);
                node = node3;
                continue;
            }
            node = node.getNextSibling();
        }
        final NamedNodeMap attributes = element.getAttributes();
        final LinkedList<Attr> list = new LinkedList<Attr>();
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        for (int i = 0; i < attributes.getLength(); ++i) {
            final Attr e = (Attr)attributes.item(i);
            final String prefix = e.getPrefix();
            if ((e != null && "xmlns".equals(prefix)) || e.getNodeName().equals("xmlns")) {
                if (!b4) {
                    list.add(e);
                }
                else {
                    final String nodeValue = e.getNodeValue();
                    if (!e.getNodeValue().equals("http://www.w3.org/2000/xmlns/")) {
                        if (nodeValue.equals("http://www.w3.org/2000/xmlns/")) {
                            hashMap.put(prefix, nodeValue);
                        }
                    }
                }
            }
        }
        if (!b4) {
            final Iterator<Object> iterator = list.iterator();
            while (iterator.hasNext()) {
                element.removeAttributeNode(iterator.next());
            }
        }
        else if (b5) {
            final String namespaceURI = element.getNamespaceURI();
            if (namespaceURI != null) {
                final String prefix2 = element.getPrefix();
                if (!this.compareStrings(abstractElement.lookupNamespaceURI(prefix2), namespaceURI)) {
                    element.setAttributeNS("http://www.w3.org/2000/xmlns/", (prefix2 == null) ? "xmlns" : ("xmlns:" + prefix2), namespaceURI);
                }
            }
            else if (element.getLocalName() != null) {
                if (abstractElement.lookupNamespaceURI(null) == null) {
                    element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "");
                }
            }
            final NamedNodeMap attributes2 = element.getAttributes();
            for (int j = 0; j < attributes2.getLength(); ++j) {
                final Attr attr = (Attr)attributes2.item(j);
                final String namespaceURI2 = attr.getNamespaceURI();
                if (namespaceURI2 != null) {
                    final String prefix3 = attr.getPrefix();
                    if (prefix3 == null || (!prefix3.equals("xml") && !prefix3.equals("xmlns"))) {
                        if (!namespaceURI2.equals("http://www.w3.org/2000/xmlns/")) {
                            final String s = (prefix3 == null) ? null : abstractElement.lookupNamespaceURI(prefix3);
                            if (prefix3 == null || s == null || !s.equals(namespaceURI2)) {
                                final String lookupPrefix = abstractElement.lookupPrefix(namespaceURI2);
                                if (lookupPrefix != null) {
                                    attr.setPrefix(lookupPrefix);
                                }
                                else if (prefix3 != null && abstractElement.lookupNamespaceURI(prefix3) == null) {
                                    element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix3, namespaceURI2);
                                }
                                else {
                                    final int k = 1;
                                    String string2;
                                    do {
                                        string2 = "NS" + k;
                                    } while (abstractElement.lookupPrefix(string2) != null);
                                    element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + string2, namespaceURI2);
                                    attr.setPrefix(string2);
                                }
                            }
                        }
                    }
                }
                else if (attr.getLocalName() == null) {}
            }
        }
        final NamedNodeMap attributes3 = element.getAttributes();
        for (int l = 0; l < attributes3.getLength(); ++l) {
            final Attr attr2 = (Attr)attributes3.item(l);
            if (!this.checkName(attr2.getNodeName()) && domErrorHandler != null && !domErrorHandler.handleError(this.createDOMError("wf-invalid-character-in-node-name", (short)2, "wf.invalid.name", new Object[] { attr2.getNodeName() }, attr2, null))) {
                return false;
            }
            if (!this.checkChars(attr2.getNodeValue()) && domErrorHandler != null && !domErrorHandler.handleError(this.createDOMError("wf-invalid-character", (short)2, "wf.invalid.character", new Object[] { new Integer(2), attr2.getNodeName(), attr2.getNodeValue() }, attr2, null))) {
                return false;
            }
        }
        for (Node node4 = element.getFirstChild(); node4 != null; node4 = node4.getNextSibling()) {
            switch (node4.getNodeType()) {
                case 3: {
                    final String nodeValue2 = node4.getNodeValue();
                    if (!this.checkChars(nodeValue2) && domErrorHandler != null && !domErrorHandler.handleError(this.createDOMError("wf-invalid-character", (short)2, "wf.invalid.character", new Object[] { new Integer(node4.getNodeType()), node4.getNodeName(), nodeValue2 }, node4, null))) {
                        return false;
                    }
                    break;
                }
                case 8: {
                    final String nodeValue3 = node4.getNodeValue();
                    if ((!this.checkChars(nodeValue3) || nodeValue3.indexOf("--") != -1 || nodeValue3.charAt(nodeValue3.length() - 1) == '-') && domErrorHandler != null && !domErrorHandler.handleError(this.createDOMError("wf-invalid-character", (short)2, "wf.invalid.character", new Object[] { new Integer(node4.getNodeType()), node4.getNodeName(), nodeValue3 }, node4, null))) {
                        return false;
                    }
                    break;
                }
                case 4: {
                    final String nodeValue4 = node4.getNodeValue();
                    if ((!this.checkChars(nodeValue4) || nodeValue4.indexOf("]]>") != -1) && domErrorHandler != null && !domErrorHandler.handleError(this.createDOMError("wf-invalid-character", (short)2, "wf.invalid.character", new Object[] { new Integer(node4.getNodeType()), node4.getNodeName(), nodeValue4 }, node4, null))) {
                        return false;
                    }
                    break;
                }
                case 7: {
                    if (node4.getNodeName().equalsIgnoreCase("xml") && domErrorHandler != null && !domErrorHandler.handleError(this.createDOMError("wf-invalid-character-in-node-name", (short)2, "wf.invalid.name", new Object[] { node4.getNodeName() }, node4, null))) {
                        return false;
                    }
                    final String nodeValue5 = node4.getNodeValue();
                    if ((!this.checkChars(nodeValue5) || nodeValue5.indexOf("?>") != -1) && domErrorHandler != null && !domErrorHandler.handleError(this.createDOMError("wf-invalid-character", (short)2, "wf.invalid.character", new Object[] { new Integer(node4.getNodeType()), node4.getNodeName(), nodeValue5 }, node4, null))) {
                        return false;
                    }
                    break;
                }
                case 1: {
                    if (!this.checkName(node4.getNodeName()) && domErrorHandler != null && !domErrorHandler.handleError(this.createDOMError("wf-invalid-character-in-node-name", (short)2, "wf.invalid.name", new Object[] { node4.getNodeName() }, node4, null))) {
                        return false;
                    }
                    if (!this.normalizeDocument((Element)node4, b, b2, b3, b4, b5, b6, domErrorHandler)) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }
    
    protected boolean splitCdata(final Element element, final Node node, final DOMErrorHandler domErrorHandler) {
        final String nodeValue = node.getNodeValue();
        final int index = nodeValue.indexOf("]]>");
        if (index != -1) {
            final String substring = nodeValue.substring(0, index + 2);
            final String substring2 = nodeValue.substring(index + 2);
            node.setNodeValue(substring);
            final Node nextSibling = node.getNextSibling();
            if (nextSibling == null) {
                element.appendChild(this.createCDATASection(substring2));
            }
            else {
                element.insertBefore(this.createCDATASection(substring2), nextSibling);
            }
            if (domErrorHandler != null && !domErrorHandler.handleError(this.createDOMError("cdata-sections-splitted", (short)1, "cdata.section.split", new Object[0], node, null))) {
                return false;
            }
        }
        return true;
    }
    
    protected boolean checkChars(final String s) {
        final int length = s.length();
        if (this.xmlVersion.equals("1.1")) {
            for (int i = 0; i < length; ++i) {
                if (!XMLUtilities.isXML11Character(s.charAt(i))) {
                    return false;
                }
            }
        }
        else {
            for (int j = 0; j < length; ++j) {
                if (!XMLUtilities.isXMLCharacter(s.charAt(j))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    protected boolean checkName(final String s) {
        if (this.xmlVersion.equals("1.1")) {
            return DOMUtilities.isValidName11(s);
        }
        return DOMUtilities.isValidName(s);
    }
    
    protected DOMError createDOMError(final String s, final short n, final String s2, final Object[] array, final Node node, final Exception ex) {
        try {
            return new DocumentError(s, n, this.getCurrentDocument().formatMessage(s2, array), node, ex);
        }
        catch (Exception ex2) {
            return new DocumentError(s, n, s2, node, ex);
        }
    }
    
    public void setTextContent(final String s) throws DOMException {
    }
    
    public void setXBLManager(XBLManager xblManager) {
        final boolean processing = this.xblManager.isProcessing();
        this.xblManager.stopProcessing();
        if (xblManager == null) {
            xblManager = new GenericXBLManager();
        }
        this.xblManager = xblManager;
        if (processing) {
            this.xblManager.startProcessing();
        }
    }
    
    public XBLManager getXBLManager() {
        return this.xblManager;
    }
    
    public XPathExpression createExpression(final String s, final XPathNSResolver xPathNSResolver) throws DOMException, XPathException {
        return new XPathExpr(s, xPathNSResolver);
    }
    
    public XPathNSResolver createNSResolver(final Node node) {
        return new XPathNodeNSResolver(node);
    }
    
    public Object evaluate(final String s, final Node node, final XPathNSResolver xPathNSResolver, final short n, final Object o) throws XPathException, DOMException {
        return this.createExpression(s, xPathNSResolver).evaluate(node, n, o);
    }
    
    public XPathException createXPathException(final short n, final String message, final Object[] array) {
        try {
            return new XPathException(n, this.formatMessage(message, array));
        }
        catch (Exception ex) {
            return new XPathException(n, message);
        }
    }
    
    public Node getXblParentNode() {
        return this.xblManager.getXblParentNode(this);
    }
    
    public NodeList getXblChildNodes() {
        return this.xblManager.getXblChildNodes(this);
    }
    
    public NodeList getXblScopedChildNodes() {
        return this.xblManager.getXblScopedChildNodes(this);
    }
    
    public Node getXblFirstChild() {
        return this.xblManager.getXblFirstChild(this);
    }
    
    public Node getXblLastChild() {
        return this.xblManager.getXblLastChild(this);
    }
    
    public Node getXblPreviousSibling() {
        return this.xblManager.getXblPreviousSibling(this);
    }
    
    public Node getXblNextSibling() {
        return this.xblManager.getXblNextSibling(this);
    }
    
    public Element getXblFirstElementChild() {
        return this.xblManager.getXblFirstElementChild(this);
    }
    
    public Element getXblLastElementChild() {
        return this.xblManager.getXblLastElementChild(this);
    }
    
    public Element getXblPreviousElementSibling() {
        return this.xblManager.getXblPreviousElementSibling(this);
    }
    
    public Element getXblNextElementSibling() {
        return this.xblManager.getXblNextElementSibling(this);
    }
    
    public Element getXblBoundElement() {
        return this.xblManager.getXblBoundElement(this);
    }
    
    public Element getXblShadowTree() {
        return this.xblManager.getXblShadowTree(this);
    }
    
    public NodeList getXblDefinitions() {
        return this.xblManager.getXblDefinitions(this);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.implementation.getClass().getName());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.localizableSupport = new LocalizableSupport("org.apache.batik.dom.resources.Messages", this.getClass().getClassLoader());
        final Class<?> forName = Class.forName((String)objectInputStream.readObject());
        try {
            this.implementation = (DOMImplementation)forName.getMethod("getDOMImplementation", (Class<?>[])null).invoke(null, (Object[])null);
        }
        catch (Exception ex) {
            try {
                this.implementation = forName.newInstance();
            }
            catch (Exception ex2) {}
        }
    }
    
    protected class XPathNodeNSResolver implements XPathNSResolver
    {
        protected Node contextNode;
        
        public XPathNodeNSResolver(final Node contextNode) {
            this.contextNode = contextNode;
        }
        
        public String lookupNamespaceURI(final String s) {
            return ((AbstractNode)this.contextNode).lookupNamespaceURI(s);
        }
    }
    
    protected class XPathExpr implements XPathExpression
    {
        protected XPath xpath;
        protected XPathNSResolver resolver;
        protected NSPrefixResolver prefixResolver;
        protected XPathContext context;
        
        public XPathExpr(final String s, final XPathNSResolver resolver) throws DOMException, XPathException {
            this.resolver = resolver;
            this.prefixResolver = new NSPrefixResolver();
            try {
                this.xpath = new XPath(s, (SourceLocator)null, (PrefixResolver)this.prefixResolver, 0);
                this.context = new XPathContext();
            }
            catch (TransformerException ex) {
                throw AbstractDocument.this.createXPathException((short)51, "xpath.invalid.expression", new Object[] { s, ex.getMessage() });
            }
        }
        
        public Object evaluate(final Node node, final short n, final Object o) throws XPathException, DOMException {
            if ((node.getNodeType() != 9 && node.getOwnerDocument() != AbstractDocument.this) || (node.getNodeType() == 9 && node != AbstractDocument.this)) {
                throw AbstractDocument.this.createDOMException((short)4, "node.from.wrong.document", new Object[] { new Integer(node.getNodeType()), node.getNodeName() });
            }
            if (n < 0 || n > 9) {
                throw AbstractDocument.this.createDOMException((short)9, "xpath.invalid.result.type", new Object[] { new Integer(n) });
            }
            switch (node.getNodeType()) {
                case 5:
                case 6:
                case 10:
                case 11:
                case 12: {
                    throw AbstractDocument.this.createDOMException((short)9, "xpath.invalid.context.node", new Object[] { new Integer(node.getNodeType()), node.getNodeName() });
                }
                default: {
                    this.context.reset();
                    XObject execute;
                    try {
                        execute = this.xpath.execute(this.context, node, (PrefixResolver)this.prefixResolver);
                    }
                    catch (TransformerException ex) {
                        throw AbstractDocument.this.createXPathException((short)51, "xpath.error", new Object[] { this.xpath.getPatternString(), ex.getMessage() });
                    }
                    try {
                        Label_0437: {
                            switch (n) {
                                case 8:
                                case 9: {
                                    return this.convertSingleNode(execute, n);
                                }
                                case 3: {
                                    return this.convertBoolean(execute);
                                }
                                case 1: {
                                    return this.convertNumber(execute);
                                }
                                case 4:
                                case 5:
                                case 6:
                                case 7: {
                                    return this.convertNodeIterator(execute, n);
                                }
                                case 2: {
                                    return this.convertString(execute);
                                }
                                case 0: {
                                    switch (execute.getType()) {
                                        case 1: {
                                            return this.convertBoolean(execute);
                                        }
                                        case 2: {
                                            return this.convertNumber(execute);
                                        }
                                        case 3: {
                                            return this.convertString(execute);
                                        }
                                        case 4: {
                                            return this.convertNodeIterator(execute, (short)4);
                                        }
                                        default: {
                                            break Label_0437;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    catch (TransformerException ex2) {
                        throw AbstractDocument.this.createXPathException((short)52, "xpath.cannot.convert.result", new Object[] { new Integer(n), ex2.getMessage() });
                    }
                    return null;
                }
            }
        }
        
        protected Result convertSingleNode(final XObject xObject, final short n) throws TransformerException {
            return new Result(xObject.nodelist().item(0), n);
        }
        
        protected Result convertBoolean(final XObject xObject) throws TransformerException {
            return new Result(xObject.bool());
        }
        
        protected Result convertNumber(final XObject xObject) throws TransformerException {
            return new Result(xObject.num());
        }
        
        protected Result convertString(final XObject xObject) {
            return new Result(xObject.str());
        }
        
        protected Result convertNodeIterator(final XObject xObject, final short n) throws TransformerException {
            return new Result(xObject.nodelist(), n);
        }
        
        protected class NSPrefixResolver implements PrefixResolver
        {
            public String getBaseIdentifier() {
                return null;
            }
            
            public String getNamespaceForPrefix(final String s) {
                if (XPathExpr.this.resolver == null) {
                    return null;
                }
                return XPathExpr.this.resolver.lookupNamespaceURI(s);
            }
            
            public String getNamespaceForPrefix(final String s, final Node node) {
                if (XPathExpr.this.resolver == null) {
                    return null;
                }
                return XPathExpr.this.resolver.lookupNamespaceURI(s);
            }
            
            public boolean handlesNullPrefixes() {
                return false;
            }
        }
        
        public class Result implements XPathResult
        {
            protected short resultType;
            protected double numberValue;
            protected String stringValue;
            protected boolean booleanValue;
            protected Node singleNodeValue;
            protected NodeList iterator;
            protected int iteratorPosition;
            
            public Result(final Node singleNodeValue, final short resultType) {
                this.resultType = resultType;
                this.singleNodeValue = singleNodeValue;
            }
            
            public Result(final boolean booleanValue) throws TransformerException {
                this.resultType = 3;
                this.booleanValue = booleanValue;
            }
            
            public Result(final double numberValue) throws TransformerException {
                this.resultType = 1;
                this.numberValue = numberValue;
            }
            
            public Result(final String stringValue) {
                this.resultType = 2;
                this.stringValue = stringValue;
            }
            
            public Result(final NodeList iterator, final short resultType) {
                this.resultType = resultType;
                this.iterator = iterator;
            }
            
            public short getResultType() {
                return this.resultType;
            }
            
            public boolean getBooleanValue() {
                if (this.resultType != 3) {
                    throw AbstractDocument.this.createXPathException((short)52, "xpath.invalid.result.type", new Object[] { new Integer(this.resultType) });
                }
                return this.booleanValue;
            }
            
            public double getNumberValue() {
                if (this.resultType != 1) {
                    throw AbstractDocument.this.createXPathException((short)52, "xpath.invalid.result.type", new Object[] { new Integer(this.resultType) });
                }
                return this.numberValue;
            }
            
            public String getStringValue() {
                if (this.resultType != 2) {
                    throw AbstractDocument.this.createXPathException((short)52, "xpath.invalid.result.type", new Object[] { new Integer(this.resultType) });
                }
                return this.stringValue;
            }
            
            public Node getSingleNodeValue() {
                if (this.resultType != 8 && this.resultType != 9) {
                    throw AbstractDocument.this.createXPathException((short)52, "xpath.invalid.result.type", new Object[] { new Integer(this.resultType) });
                }
                return this.singleNodeValue;
            }
            
            public boolean getInvalidIteratorState() {
                return false;
            }
            
            public int getSnapshotLength() {
                if (this.resultType != 6 && this.resultType != 7) {
                    throw AbstractDocument.this.createXPathException((short)52, "xpath.invalid.result.type", new Object[] { new Integer(this.resultType) });
                }
                return this.iterator.getLength();
            }
            
            public Node iterateNext() {
                if (this.resultType != 4 && this.resultType != 5) {
                    throw AbstractDocument.this.createXPathException((short)52, "xpath.invalid.result.type", new Object[] { new Integer(this.resultType) });
                }
                return this.iterator.item(this.iteratorPosition++);
            }
            
            public Node snapshotItem(final int n) {
                if (this.resultType != 6 && this.resultType != 7) {
                    throw AbstractDocument.this.createXPathException((short)52, "xpath.invalid.result.type", new Object[] { new Integer(this.resultType) });
                }
                return this.iterator.item(n);
            }
        }
    }
    
    protected class DocumentConfiguration implements DOMConfiguration
    {
        protected String[] booleanParamNames;
        protected boolean[] booleanParamValues;
        protected boolean[] booleanParamReadOnly;
        protected Map booleanParamIndexes;
        protected Object errorHandler;
        protected ParameterNameList paramNameList;
        
        protected DocumentConfiguration() {
            this.booleanParamNames = new String[] { "canonical-form", "cdata-sections", "check-character-normalization", "comments", "datatype-normalization", "element-content-whitespace", "entities", "infoset", "namespaces", "namespace-declarations", "normalize-characters", "split-cdata-sections", "validate", "validate-if-schema", "well-formed" };
            this.booleanParamValues = new boolean[] { false, true, false, true, false, false, true, false, true, true, false, true, false, false, true };
            this.booleanParamReadOnly = new boolean[] { true, false, true, false, true, false, false, false, false, false, true, false, true, true, false };
            this.booleanParamIndexes = new HashMap();
            for (int i = 0; i < this.booleanParamNames.length; ++i) {
                this.booleanParamIndexes.put(this.booleanParamNames[i], new Integer(i));
            }
        }
        
        public void setParameter(final String anObject, final Object errorHandler) {
            if ("error-handler".equals(anObject)) {
                if (errorHandler != null && !(errorHandler instanceof DOMErrorHandler)) {
                    throw AbstractDocument.this.createDOMException((short)17, "domconfig.param.type", new Object[] { anObject });
                }
                this.errorHandler = errorHandler;
            }
            else {
                final Integer n = this.booleanParamIndexes.get(anObject);
                if (n == null) {
                    throw AbstractDocument.this.createDOMException((short)8, "domconfig.param.not.found", new Object[] { anObject });
                }
                if (errorHandler == null) {
                    throw AbstractDocument.this.createDOMException((short)9, "domconfig.param.value", new Object[] { anObject });
                }
                if (!(errorHandler instanceof Boolean)) {
                    throw AbstractDocument.this.createDOMException((short)17, "domconfig.param.type", new Object[] { anObject });
                }
                final int intValue = n;
                final boolean booleanValue = (boolean)errorHandler;
                if (this.booleanParamReadOnly[intValue] && this.booleanParamValues[intValue] != booleanValue) {
                    throw AbstractDocument.this.createDOMException((short)9, "domconfig.param.value", new Object[] { anObject });
                }
                this.booleanParamValues[intValue] = booleanValue;
                if (anObject.equals("infoset")) {
                    this.setParameter("validate-if-schema", Boolean.FALSE);
                    this.setParameter("entities", Boolean.FALSE);
                    this.setParameter("datatype-normalization", Boolean.FALSE);
                    this.setParameter("cdata-sections", Boolean.FALSE);
                    this.setParameter("well-formed", Boolean.TRUE);
                    this.setParameter("element-content-whitespace", Boolean.TRUE);
                    this.setParameter("comments", Boolean.TRUE);
                    this.setParameter("namespaces", Boolean.TRUE);
                }
            }
        }
        
        public Object getParameter(final String anObject) {
            if ("error-handler".equals(anObject)) {
                return this.errorHandler;
            }
            final Integer n = this.booleanParamIndexes.get(anObject);
            if (n == null) {
                throw AbstractDocument.this.createDOMException((short)8, "domconfig.param.not.found", new Object[] { anObject });
            }
            return this.booleanParamValues[n] ? Boolean.TRUE : Boolean.FALSE;
        }
        
        public boolean getBooleanParameter(final String s) {
            return (boolean)this.getParameter(s);
        }
        
        public boolean canSetParameter(final String s, final Object o) {
            if (s.equals("error-handler")) {
                return o == null || o instanceof DOMErrorHandler;
            }
            final Integer n = this.booleanParamIndexes.get(s);
            if (n == null || o == null || !(o instanceof Boolean)) {
                return false;
            }
            final int intValue = n;
            final boolean booleanValue = (boolean)o;
            return !this.booleanParamReadOnly[intValue] || this.booleanParamValues[intValue] == booleanValue;
        }
        
        public DOMStringList getParameterNames() {
            if (this.paramNameList == null) {
                this.paramNameList = new ParameterNameList();
            }
            return this.paramNameList;
        }
        
        protected class ParameterNameList implements DOMStringList
        {
            public String item(final int n) {
                if (n < 0) {
                    return null;
                }
                if (n < DocumentConfiguration.this.booleanParamNames.length) {
                    return DocumentConfiguration.this.booleanParamNames[n];
                }
                if (n == DocumentConfiguration.this.booleanParamNames.length) {
                    return "error-handler";
                }
                return null;
            }
            
            public int getLength() {
                return DocumentConfiguration.this.booleanParamNames.length + 1;
            }
            
            public boolean contains(final String s) {
                if ("error-handler".equals(s)) {
                    return true;
                }
                for (int i = 0; i < DocumentConfiguration.this.booleanParamNames.length; ++i) {
                    if (DocumentConfiguration.this.booleanParamNames[i].equals(s)) {
                        return true;
                    }
                }
                return false;
            }
        }
    }
    
    protected class DocumentError implements DOMError
    {
        protected String type;
        protected short severity;
        protected String message;
        protected Node relatedNode;
        protected Object relatedException;
        protected DOMLocator domLocator;
        
        public DocumentError(final String type, final short severity, final String message, final Node relatedNode, final Exception relatedException) {
            this.type = type;
            this.severity = severity;
            this.message = message;
            this.relatedNode = relatedNode;
            this.relatedException = relatedException;
        }
        
        public String getType() {
            return this.type;
        }
        
        public short getSeverity() {
            return this.severity;
        }
        
        public String getMessage() {
            return this.message;
        }
        
        public Object getRelatedData() {
            return this.relatedNode;
        }
        
        public Object getRelatedException() {
            return this.relatedException;
        }
        
        public DOMLocator getLocation() {
            if (this.domLocator == null) {
                this.domLocator = new ErrorLocation(this.relatedNode);
            }
            return this.domLocator;
        }
        
        protected class ErrorLocation implements DOMLocator
        {
            protected Node node;
            
            public ErrorLocation(final Node node) {
                this.node = node;
            }
            
            public int getLineNumber() {
                return -1;
            }
            
            public int getColumnNumber() {
                return -1;
            }
            
            public int getByteOffset() {
                return -1;
            }
            
            public int getUtf16Offset() {
                return -1;
            }
            
            public Node getRelatedNode() {
                return this.node;
            }
            
            public String getUri() {
                return ((AbstractDocument)this.node.getOwnerDocument()).getDocumentURI();
            }
        }
    }
    
    protected class IdSoftRef extends CleanerThread.SoftReferenceCleared
    {
        String id;
        List list;
        
        IdSoftRef(final Object o, final String id) {
            super(o);
            this.id = id;
        }
        
        IdSoftRef(final Object o, final String id, final List list) {
            super(o);
            this.id = id;
            this.list = list;
        }
        
        public void setList(final List list) {
            this.list = list;
        }
        
        public void cleared() {
            if (AbstractDocument.this.elementsById == null) {
                return;
            }
            synchronized (AbstractDocument.this.elementsById) {
                if (this.list != null) {
                    this.list.remove(this);
                }
                else {
                    final IdSoftRef remove = AbstractDocument.this.elementsById.remove(this.id);
                    if (remove != this) {
                        AbstractDocument.this.elementsById.put(this.id, remove);
                    }
                }
            }
        }
    }
}
