// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.apache.batik.dom.events.EventSupport;
import org.apache.batik.dom.svg12.XBLOMImportElement;
import java.util.LinkedList;
import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MutationEvent;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import org.apache.batik.dom.xbl.NodeXBL;
import java.util.ArrayList;
import org.apache.batik.dom.xbl.XBLManagerData;
import org.apache.batik.dom.svg12.XBLOMContentElement;
import org.w3c.dom.NamedNodeMap;
import org.apache.batik.dom.AbstractAttrNS;
import org.w3c.dom.Attr;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.DocumentEvent;
import org.apache.batik.dom.xbl.ShadowTreeEvent;
import org.apache.batik.dom.xbl.XBLShadowTreeElement;
import org.apache.batik.dom.svg12.XBLOMShadowTreeElement;
import org.apache.batik.dom.svg12.BindableElement;
import org.apache.batik.dom.svg12.XBLOMTemplateElement;
import org.apache.batik.dom.events.NodeEventTarget;
import org.apache.batik.bridge.BridgeException;
import java.util.TreeSet;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg12.XBLEventSupport;
import org.apache.batik.dom.svg12.XBLOMDefinitionElement;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.HashMap;
import javax.swing.event.EventListenerList;
import java.util.Map;
import org.apache.batik.util.DoublyIndexedTable;
import org.apache.batik.bridge.BridgeContext;
import org.w3c.dom.Document;
import org.apache.batik.util.XBLConstants;
import org.apache.batik.dom.xbl.XBLManager;

public class DefaultXBLManager implements XBLManager, XBLConstants
{
    protected boolean isProcessing;
    protected Document document;
    protected BridgeContext ctx;
    protected DoublyIndexedTable definitionLists;
    protected DoublyIndexedTable definitions;
    protected Map contentManagers;
    protected Map imports;
    protected DocInsertedListener docInsertedListener;
    protected DocRemovedListener docRemovedListener;
    protected DocSubtreeListener docSubtreeListener;
    protected ImportAttrListener importAttrListener;
    protected RefAttrListener refAttrListener;
    protected EventListenerList bindingListenerList;
    protected EventListenerList contentSelectionChangedListenerList;
    
    public DefaultXBLManager(final Document document, final BridgeContext ctx) {
        this.definitionLists = new DoublyIndexedTable();
        this.definitions = new DoublyIndexedTable();
        this.contentManagers = new HashMap();
        this.imports = new HashMap();
        this.docInsertedListener = new DocInsertedListener();
        this.docRemovedListener = new DocRemovedListener();
        this.docSubtreeListener = new DocSubtreeListener();
        this.importAttrListener = new ImportAttrListener();
        this.refAttrListener = new RefAttrListener();
        this.bindingListenerList = new EventListenerList();
        this.contentSelectionChangedListenerList = new EventListenerList();
        this.document = document;
        this.ctx = ctx;
        this.imports.put(null, new ImportRecord(null, null));
    }
    
    public void startProcessing() {
        if (this.isProcessing) {
            return;
        }
        final NodeList elementsByTagNameNS = this.document.getElementsByTagNameNS("http://www.w3.org/2004/xbl", "definition");
        final XBLOMDefinitionElement[] array = new XBLOMDefinitionElement[elementsByTagNameNS.getLength()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (XBLOMDefinitionElement)elementsByTagNameNS.item(i);
        }
        final NodeList elementsByTagNameNS2 = this.document.getElementsByTagNameNS("http://www.w3.org/2004/xbl", "import");
        final Element[] array2 = new Element[elementsByTagNameNS2.getLength()];
        for (int j = 0; j < array2.length; ++j) {
            array2[j] = (Element)elementsByTagNameNS2.item(j);
        }
        final XBLEventSupport xblEventSupport = (XBLEventSupport)((AbstractDocument)this.document).initializeEventSupport();
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.docRemovedListener, true);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.docInsertedListener, true);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.docSubtreeListener, true);
        for (int k = 0; k < array.length; ++k) {
            if (array[k].getAttributeNS(null, "ref").length() != 0) {
                this.addDefinitionRef(array[k]);
            }
            else {
                this.addDefinition(array[k].getElementNamespaceURI(), array[k].getElementLocalName(), array[k], null);
            }
        }
        for (int l = 0; l < array2.length; ++l) {
            this.addImport(array2[l]);
        }
        this.isProcessing = true;
        this.bind(this.document.getDocumentElement());
    }
    
    public void stopProcessing() {
        if (!this.isProcessing) {
            return;
        }
        this.isProcessing = false;
        final XBLEventSupport xblEventSupport = (XBLEventSupport)((AbstractDocument)this.document).initializeEventSupport();
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.docRemovedListener, true);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.docInsertedListener, true);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.docSubtreeListener, true);
        final ImportRecord[] array = new ImportRecord[this.imports.values().size()];
        this.imports.values().toArray(array);
        for (int i = 0; i < array.length; ++i) {
            final ImportRecord importRecord = array[i];
            if (importRecord.importElement.getLocalName().equals("definition")) {
                this.removeDefinitionRef(importRecord.importElement);
            }
            else {
                this.removeImport(importRecord.importElement);
            }
        }
        final Object[] valuesArray = this.definitions.getValuesArray();
        this.definitions.clear();
        for (int j = 0; j < valuesArray.length; ++j) {
            DefinitionRecord o = (DefinitionRecord)valuesArray[j];
            final TreeSet set = (TreeSet)this.definitionLists.get(o.namespaceURI, o.localName);
            if (set != null) {
                while (!set.isEmpty()) {
                    o = set.first();
                    set.remove(o);
                    this.removeDefinition(o);
                }
                this.definitionLists.put(o.namespaceURI, o.localName, null);
            }
        }
        this.definitionLists = new DoublyIndexedTable();
        this.contentManagers.clear();
    }
    
    public boolean isProcessing() {
        return this.isProcessing;
    }
    
    protected void addDefinitionRef(final Element element) {
        final String attributeNS = element.getAttributeNS(null, "ref");
        final Element referencedElement = this.ctx.getReferencedElement(element, attributeNS);
        if (!"http://www.w3.org/2004/xbl".equals(referencedElement.getNamespaceURI()) || !"definition".equals(referencedElement.getLocalName())) {
            throw new BridgeException(this.ctx, element, "uri.badTarget", new Object[] { attributeNS });
        }
        this.imports.put(element, new ImportRecord(element, referencedElement));
        ((NodeEventTarget)element).addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.refAttrListener, false, null);
        final XBLOMDefinitionElement xblomDefinitionElement = (XBLOMDefinitionElement)element;
        this.addDefinition(xblomDefinitionElement.getElementNamespaceURI(), xblomDefinitionElement.getElementLocalName(), (XBLOMDefinitionElement)referencedElement, element);
    }
    
    protected void removeDefinitionRef(final Element element) {
        final ImportRecord importRecord = this.imports.get(element);
        ((NodeEventTarget)element).removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.refAttrListener, false);
        this.removeDefinition((DefinitionRecord)this.definitions.get(importRecord.node, element));
        this.imports.remove(element);
    }
    
    protected void addImport(final Element element) {
        final Node referencedNode = this.ctx.getReferencedNode(element, element.getAttributeNS(null, "bindings"));
        if (referencedNode.getNodeType() == 1 && (!"http://www.w3.org/2004/xbl".equals(referencedNode.getNamespaceURI()) || !"xbl".equals(referencedNode.getLocalName()))) {
            throw new BridgeException(this.ctx, element, "uri.badTarget", new Object[] { referencedNode });
        }
        final ImportRecord importRecord = new ImportRecord(element, referencedNode);
        this.imports.put(element, importRecord);
        ((NodeEventTarget)element).addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.importAttrListener, false, null);
        final NodeEventTarget nodeEventTarget = (NodeEventTarget)referencedNode;
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", importRecord.importInsertedListener, false, null);
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", importRecord.importRemovedListener, false, null);
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", importRecord.importSubtreeListener, false, null);
        this.addImportedDefinitions(element, referencedNode);
    }
    
    protected void addImportedDefinitions(final Element element, Node node) {
        if (node instanceof XBLOMDefinitionElement) {
            final XBLOMDefinitionElement xblomDefinitionElement = (XBLOMDefinitionElement)node;
            this.addDefinition(xblomDefinitionElement.getElementNamespaceURI(), xblomDefinitionElement.getElementLocalName(), xblomDefinitionElement, element);
        }
        else {
            for (node = node.getFirstChild(); node != null; node = node.getNextSibling()) {
                this.addImportedDefinitions(element, node);
            }
        }
    }
    
    protected void removeImport(final Element element) {
        final ImportRecord importRecord = this.imports.get(element);
        final NodeEventTarget nodeEventTarget = (NodeEventTarget)importRecord.node;
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", importRecord.importInsertedListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", importRecord.importRemovedListener, false);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", importRecord.importSubtreeListener, false);
        ((NodeEventTarget)element).removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.importAttrListener, false);
        final Object[] valuesArray = this.definitions.getValuesArray();
        for (int i = 0; i < valuesArray.length; ++i) {
            final DefinitionRecord definitionRecord = (DefinitionRecord)valuesArray[i];
            if (definitionRecord.importElement == element) {
                this.removeDefinition(definitionRecord);
            }
        }
        this.imports.remove(element);
    }
    
    protected void addDefinition(final String s, final String s2, final XBLOMDefinitionElement xblomDefinitionElement, final Element element) {
        final ImportRecord importRecord = this.imports.get(element);
        DefinitionRecord definitionRecord = null;
        TreeSet set = (TreeSet)this.definitionLists.get(s, s2);
        if (set == null) {
            set = new TreeSet<Object>();
            this.definitionLists.put(s, s2, set);
        }
        else if (set.size() > 0) {
            definitionRecord = set.first();
        }
        XBLOMTemplateElement xblomTemplateElement = null;
        for (Node node = xblomDefinitionElement.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node instanceof XBLOMTemplateElement) {
                xblomTemplateElement = (XBLOMTemplateElement)node;
                break;
            }
        }
        final DefinitionRecord e = new DefinitionRecord(s, s2, xblomDefinitionElement, xblomTemplateElement, element);
        set.add(e);
        this.definitions.put(xblomDefinitionElement, element, e);
        this.addDefinitionElementListeners(xblomDefinitionElement, importRecord);
        if (set.first() != e) {
            return;
        }
        if (definitionRecord != null) {
            final XBLOMDefinitionElement definition = definitionRecord.definition;
            final XBLOMTemplateElement template = definitionRecord.template;
            if (template != null) {
                this.removeTemplateElementListeners(template, importRecord);
            }
            this.removeDefinitionElementListeners(definition, importRecord);
        }
        if (xblomTemplateElement != null) {
            this.addTemplateElementListeners(xblomTemplateElement, importRecord);
        }
        if (this.isProcessing) {
            this.rebind(s, s2, this.document.getDocumentElement());
        }
    }
    
    protected void addDefinitionElementListeners(final XBLOMDefinitionElement xblomDefinitionElement, final ImportRecord importRecord) {
        final XBLEventSupport xblEventSupport = (XBLEventSupport)xblomDefinitionElement.initializeEventSupport();
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", importRecord.defAttrListener, false);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", importRecord.defNodeInsertedListener, false);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", importRecord.defNodeRemovedListener, false);
    }
    
    protected void addTemplateElementListeners(final XBLOMTemplateElement xblomTemplateElement, final ImportRecord importRecord) {
        final XBLEventSupport xblEventSupport = (XBLEventSupport)xblomTemplateElement.initializeEventSupport();
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", importRecord.templateMutationListener, false);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", importRecord.templateMutationListener, false);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", importRecord.templateMutationListener, false);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", importRecord.templateMutationListener, false);
    }
    
    protected void removeDefinition(final DefinitionRecord o) {
        final TreeSet set = (TreeSet)this.definitionLists.get(o.namespaceURI, o.localName);
        if (set == null) {
            return;
        }
        final Element importElement = o.importElement;
        final ImportRecord importRecord = this.imports.get(importElement);
        final DefinitionRecord definitionRecord = set.first();
        set.remove(o);
        this.definitions.remove(o.definition, importElement);
        this.removeDefinitionElementListeners(o.definition, importRecord);
        if (o != definitionRecord) {
            return;
        }
        if (o.template != null) {
            this.removeTemplateElementListeners(o.template, importRecord);
        }
        this.rebind(o.namespaceURI, o.localName, this.document.getDocumentElement());
    }
    
    protected void removeDefinitionElementListeners(final XBLOMDefinitionElement xblomDefinitionElement, final ImportRecord importRecord) {
        final XBLEventSupport xblEventSupport = (XBLEventSupport)xblomDefinitionElement.initializeEventSupport();
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", importRecord.defAttrListener, false);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", importRecord.defNodeInsertedListener, false);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", importRecord.defNodeRemovedListener, false);
    }
    
    protected void removeTemplateElementListeners(final XBLOMTemplateElement xblomTemplateElement, final ImportRecord importRecord) {
        final XBLEventSupport xblEventSupport = (XBLEventSupport)xblomTemplateElement.initializeEventSupport();
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", importRecord.templateMutationListener, false);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", importRecord.templateMutationListener, false);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", importRecord.templateMutationListener, false);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", importRecord.templateMutationListener, false);
    }
    
    protected DefinitionRecord getActiveDefinition(final String s, final String s2) {
        final TreeSet set = (TreeSet)this.definitionLists.get(s, s2);
        if (set == null || set.size() == 0) {
            return null;
        }
        return set.first();
    }
    
    protected void unbind(final Element element) {
        if (element instanceof BindableElement) {
            this.setActiveDefinition((BindableElement)element, null);
        }
        else {
            final NodeList xblScopedChildNodes = this.getXblScopedChildNodes(element);
            for (int i = 0; i < xblScopedChildNodes.getLength(); ++i) {
                final Node item = xblScopedChildNodes.item(i);
                if (item.getNodeType() == 1) {
                    this.unbind((Element)item);
                }
            }
        }
    }
    
    protected void bind(final Element element) {
        final AbstractDocument abstractDocument = (AbstractDocument)element.getOwnerDocument();
        if (abstractDocument != this.document) {
            final XBLManager xblManager = abstractDocument.getXBLManager();
            if (xblManager instanceof DefaultXBLManager) {
                ((DefaultXBLManager)xblManager).bind(element);
                return;
            }
        }
        if (element instanceof BindableElement) {
            this.setActiveDefinition((BindableElement)element, this.getActiveDefinition(element.getNamespaceURI(), element.getLocalName()));
        }
        else {
            final NodeList xblScopedChildNodes = this.getXblScopedChildNodes(element);
            for (int i = 0; i < xblScopedChildNodes.getLength(); ++i) {
                final Node item = xblScopedChildNodes.item(i);
                if (item.getNodeType() == 1) {
                    this.bind((Element)item);
                }
            }
        }
    }
    
    protected void rebind(final String s, final String s2, final Element element) {
        final AbstractDocument abstractDocument = (AbstractDocument)element.getOwnerDocument();
        if (abstractDocument != this.document) {
            final XBLManager xblManager = abstractDocument.getXBLManager();
            if (xblManager instanceof DefaultXBLManager) {
                ((DefaultXBLManager)xblManager).rebind(s, s2, element);
                return;
            }
        }
        if (element instanceof BindableElement && s.equals(element.getNamespaceURI()) && s2.equals(element.getLocalName())) {
            this.setActiveDefinition((BindableElement)element, this.getActiveDefinition(element.getNamespaceURI(), element.getLocalName()));
        }
        else {
            final NodeList xblScopedChildNodes = this.getXblScopedChildNodes(element);
            for (int i = 0; i < xblScopedChildNodes.getLength(); ++i) {
                final Node item = xblScopedChildNodes.item(i);
                if (item.getNodeType() == 1) {
                    this.rebind(s, s2, (Element)item);
                }
            }
        }
    }
    
    protected void setActiveDefinition(final BindableElement bindableElement, final DefinitionRecord definitionRecord) {
        this.getRecord(bindableElement).definitionElement = ((definitionRecord == null) ? null : definitionRecord.definition);
        if (definitionRecord != null && definitionRecord.definition != null && definitionRecord.template != null) {
            this.setXblShadowTree(bindableElement, this.cloneTemplate(definitionRecord.template));
        }
        else {
            this.setXblShadowTree(bindableElement, null);
        }
    }
    
    protected void setXblShadowTree(final BindableElement boundElement, final XBLOMShadowTreeElement shadowTree) {
        final XBLOMShadowTreeElement xblomShadowTreeElement = (XBLOMShadowTreeElement)this.getXblShadowTree(boundElement);
        if (xblomShadowTreeElement != null) {
            this.fireShadowTreeEvent(boundElement, "unbinding", xblomShadowTreeElement);
            final ContentManager contentManager = this.getContentManager(xblomShadowTreeElement);
            if (contentManager != null) {
                contentManager.dispose();
            }
            boundElement.setShadowTree(null);
            this.getRecord(xblomShadowTreeElement).boundElement = null;
            xblomShadowTreeElement.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.docSubtreeListener, false);
        }
        if (shadowTree != null) {
            shadowTree.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.docSubtreeListener, false, null);
            this.fireShadowTreeEvent(boundElement, "prebind", shadowTree);
            boundElement.setShadowTree(shadowTree);
            this.getRecord(shadowTree).boundElement = boundElement;
            this.setContentManager(shadowTree, new ContentManager(shadowTree, ((AbstractDocument)boundElement.getOwnerDocument()).getXBLManager()));
        }
        this.invalidateChildNodes(boundElement);
        if (shadowTree != null) {
            final NodeList xblScopedChildNodes = this.getXblScopedChildNodes(boundElement);
            for (int i = 0; i < xblScopedChildNodes.getLength(); ++i) {
                final Node item = xblScopedChildNodes.item(i);
                if (item.getNodeType() == 1) {
                    this.bind((Element)item);
                }
            }
            this.dispatchBindingChangedEvent(boundElement, shadowTree);
            this.fireShadowTreeEvent(boundElement, "bound", shadowTree);
        }
        else {
            this.dispatchBindingChangedEvent(boundElement, shadowTree);
        }
    }
    
    protected void fireShadowTreeEvent(final BindableElement bindableElement, final String s, final XBLShadowTreeElement xblShadowTreeElement) {
        final ShadowTreeEvent shadowTreeEvent = (ShadowTreeEvent)((DocumentEvent)bindableElement.getOwnerDocument()).createEvent("ShadowTreeEvent");
        shadowTreeEvent.initShadowTreeEventNS("http://www.w3.org/2004/xbl", s, true, false, xblShadowTreeElement);
        bindableElement.dispatchEvent(shadowTreeEvent);
    }
    
    protected XBLOMShadowTreeElement cloneTemplate(final XBLOMTemplateElement xblomTemplateElement) {
        final XBLOMShadowTreeElement xblomShadowTreeElement = (XBLOMShadowTreeElement)xblomTemplateElement.getOwnerDocument().createElementNS("http://www.w3.org/2004/xbl", "shadowTree");
        final NamedNodeMap attributes = xblomTemplateElement.getAttributes();
        for (int i = 0; i < attributes.getLength(); ++i) {
            final Attr attr = (Attr)attributes.item(i);
            if (attr instanceof AbstractAttrNS) {
                xblomShadowTreeElement.setAttributeNodeNS(attr);
            }
            else {
                xblomShadowTreeElement.setAttributeNode(attr);
            }
        }
        for (Node node = xblomTemplateElement.getFirstChild(); node != null; node = node.getNextSibling()) {
            xblomShadowTreeElement.appendChild(node.cloneNode(true));
        }
        return xblomShadowTreeElement;
    }
    
    public Node getXblParentNode(final Node node) {
        final XBLOMContentElement xblContentElement = this.getXblContentElement(node);
        Node node2 = (xblContentElement == null) ? node.getParentNode() : xblContentElement.getParentNode();
        if (node2 instanceof XBLOMContentElement) {
            node2 = node2.getParentNode();
        }
        if (node2 instanceof XBLOMShadowTreeElement) {
            node2 = this.getXblBoundElement(node2);
        }
        return node2;
    }
    
    public NodeList getXblChildNodes(final Node node) {
        final XBLRecord record = this.getRecord(node);
        if (record.childNodes == null) {
            record.childNodes = new XblChildNodes(record);
        }
        return record.childNodes;
    }
    
    public NodeList getXblScopedChildNodes(final Node node) {
        final XBLRecord record = this.getRecord(node);
        if (record.scopedChildNodes == null) {
            record.scopedChildNodes = new XblScopedChildNodes(record);
        }
        return record.scopedChildNodes;
    }
    
    public Node getXblFirstChild(final Node node) {
        return this.getXblChildNodes(node).item(0);
    }
    
    public Node getXblLastChild(final Node node) {
        final NodeList xblChildNodes = this.getXblChildNodes(node);
        return xblChildNodes.item(xblChildNodes.getLength() - 1);
    }
    
    public Node getXblPreviousSibling(final Node node) {
        final Node xblParentNode = this.getXblParentNode(node);
        if (xblParentNode == null || this.getRecord(xblParentNode).childNodes == null) {
            return node.getPreviousSibling();
        }
        final XBLRecord record = this.getRecord(node);
        if (!record.linksValid) {
            this.updateLinks(node);
        }
        return record.previousSibling;
    }
    
    public Node getXblNextSibling(final Node node) {
        final Node xblParentNode = this.getXblParentNode(node);
        if (xblParentNode == null || this.getRecord(xblParentNode).childNodes == null) {
            return node.getNextSibling();
        }
        final XBLRecord record = this.getRecord(node);
        if (!record.linksValid) {
            this.updateLinks(node);
        }
        return record.nextSibling;
    }
    
    public Element getXblFirstElementChild(Node node) {
        for (node = this.getXblFirstChild(node); node != null && node.getNodeType() != 1; node = this.getXblNextSibling(node)) {}
        return (Element)node;
    }
    
    public Element getXblLastElementChild(Node node) {
        for (node = this.getXblLastChild(node); node != null && node.getNodeType() != 1; node = this.getXblPreviousSibling(node)) {}
        return (Element)node;
    }
    
    public Element getXblPreviousElementSibling(Node xblPreviousSibling) {
        do {
            xblPreviousSibling = this.getXblPreviousSibling(xblPreviousSibling);
        } while (xblPreviousSibling != null && xblPreviousSibling.getNodeType() != 1);
        return (Element)xblPreviousSibling;
    }
    
    public Element getXblNextElementSibling(Node xblNextSibling) {
        do {
            xblNextSibling = this.getXblNextSibling(xblNextSibling);
        } while (xblNextSibling != null && xblNextSibling.getNodeType() != 1);
        return (Element)xblNextSibling;
    }
    
    public Element getXblBoundElement(Node parentNode) {
        while (parentNode != null && !(parentNode instanceof XBLShadowTreeElement)) {
            final XBLOMContentElement xblContentElement = this.getXblContentElement(parentNode);
            if (xblContentElement != null) {
                parentNode = xblContentElement;
            }
            parentNode = parentNode.getParentNode();
        }
        if (parentNode == null) {
            return null;
        }
        return this.getRecord(parentNode).boundElement;
    }
    
    public Element getXblShadowTree(final Node node) {
        if (node instanceof BindableElement) {
            return ((BindableElement)node).getShadowTree();
        }
        return null;
    }
    
    public NodeList getXblDefinitions(final Node node) {
        return new NodeList() {
            private final /* synthetic */ String val$namespaceURI = node.getNamespaceURI();
            private final /* synthetic */ String val$localName = node.getLocalName();
            
            public Node item(final int n) {
                final TreeSet set = (TreeSet)DefaultXBLManager.this.definitionLists.get(this.val$namespaceURI, this.val$localName);
                if (set != null && set.size() != 0 && n == 0) {
                    return set.first().definition;
                }
                return null;
            }
            
            public int getLength() {
                final TreeSet set = (TreeSet)DefaultXBLManager.this.definitionLists.get(this.val$namespaceURI, this.val$localName);
                return (set != null && set.size() != 0) ? 1 : 0;
            }
        };
    }
    
    protected XBLRecord getRecord(final Node node) {
        final XBLManagerData xblManagerData = (XBLManagerData)node;
        XBLRecord managerData = (XBLRecord)xblManagerData.getManagerData();
        if (managerData == null) {
            managerData = new XBLRecord();
            managerData.node = node;
            xblManagerData.setManagerData(managerData);
        }
        return managerData;
    }
    
    protected void updateLinks(final Node node) {
        final XBLRecord record = this.getRecord(node);
        record.previousSibling = null;
        record.nextSibling = null;
        record.linksValid = true;
        final Node xblParentNode = this.getXblParentNode(node);
        if (xblParentNode != null) {
            final NodeList xblChildNodes = this.getXblChildNodes(xblParentNode);
            if (xblChildNodes instanceof XblChildNodes) {
                ((XblChildNodes)xblChildNodes).update();
            }
        }
    }
    
    public XBLOMContentElement getXblContentElement(final Node node) {
        return this.getRecord(node).contentElement;
    }
    
    public static int computeBubbleLimit(Node xblParentNode, Node xblParentNode2) {
        final ArrayList<Element> list = new ArrayList<Element>(10);
        final ArrayList list2 = new ArrayList<Node>(10);
        while (xblParentNode != null) {
            list.add((Element)xblParentNode);
            xblParentNode = ((NodeXBL)xblParentNode).getXblParentNode();
        }
        while (xblParentNode2 != null) {
            list2.add(xblParentNode2);
            xblParentNode2 = ((NodeXBL)xblParentNode2).getXblParentNode();
        }
        for (int size = list.size(), size2 = list2.size(), n = 0; n < size && n < size2; ++n) {
            final Element element = list.get(size - n - 1);
            if (element != list2.get(size2 - n - 1)) {
                for (Element xblBoundElement = ((NodeXBL)element).getXblBoundElement(); n > 0 && xblBoundElement != list.get(size - n - 1); --n) {}
                return size - n - 1;
            }
        }
        return 1;
    }
    
    public ContentManager getContentManager(final Node node) {
        final Element xblBoundElement = this.getXblBoundElement(node);
        if (xblBoundElement != null) {
            final Element xblShadowTree = this.getXblShadowTree(xblBoundElement);
            if (xblShadowTree != null) {
                final Document ownerDocument = xblBoundElement.getOwnerDocument();
                ContentManager contentManager;
                if (ownerDocument != this.document) {
                    contentManager = ((DefaultXBLManager)((AbstractDocument)ownerDocument).getXBLManager()).contentManagers.get(xblShadowTree);
                }
                else {
                    contentManager = this.contentManagers.get(xblShadowTree);
                }
                return contentManager;
            }
        }
        return null;
    }
    
    void setContentManager(final Element element, final ContentManager contentManager) {
        if (contentManager == null) {
            this.contentManagers.remove(element);
        }
        else {
            this.contentManagers.put(element, contentManager);
        }
    }
    
    public void invalidateChildNodes(final Node node) {
        final XBLRecord record = this.getRecord(node);
        if (record.childNodes != null) {
            record.childNodes.invalidate();
        }
        if (record.scopedChildNodes != null) {
            record.scopedChildNodes.invalidate();
        }
    }
    
    public void addContentSelectionChangedListener(final ContentSelectionChangedListener l) {
        this.contentSelectionChangedListenerList.add(ContentSelectionChangedListener.class, l);
    }
    
    public void removeContentSelectionChangedListener(final ContentSelectionChangedListener l) {
        this.contentSelectionChangedListenerList.remove(ContentSelectionChangedListener.class, l);
    }
    
    protected Object[] getContentSelectionChangedListeners() {
        return this.contentSelectionChangedListenerList.getListenerList();
    }
    
    void shadowTreeSelectedContentChanged(final Set set, final Set set2) {
        for (final Node node : set) {
            if (node.getNodeType() == 1) {
                this.unbind((Element)node);
            }
        }
        for (final Node node2 : set2) {
            if (node2.getNodeType() == 1) {
                this.bind((Element)node2);
            }
        }
    }
    
    public void addBindingListener(final BindingListener l) {
        this.bindingListenerList.add(BindingListener.class, l);
    }
    
    public void removeBindingListener(final BindingListener l) {
        this.bindingListenerList.remove(BindingListener.class, l);
    }
    
    protected void dispatchBindingChangedEvent(final Element element, final Element element2) {
        final Object[] listenerList = this.bindingListenerList.getListenerList();
        for (int i = listenerList.length - 2; i >= 0; i -= 2) {
            ((BindingListener)listenerList[i + 1]).bindingChanged(element, element2);
        }
    }
    
    protected boolean isActiveDefinition(final XBLOMDefinitionElement xblomDefinitionElement, final Element element) {
        final DefinitionRecord definitionRecord = (DefinitionRecord)this.definitions.get(xblomDefinitionElement, element);
        return definitionRecord != null && definitionRecord == this.getActiveDefinition(definitionRecord.namespaceURI, definitionRecord.localName);
    }
    
    protected class XblScopedChildNodes extends XblChildNodes
    {
        public XblScopedChildNodes(final XBLRecord xblRecord) {
            super(xblRecord);
        }
        
        protected void update() {
            this.size = 0;
            final Element xblShadowTree = DefaultXBLManager.this.getXblShadowTree(this.record.node);
            for (Node nextSibling = (xblShadowTree == null) ? this.record.node.getFirstChild() : xblShadowTree.getFirstChild(); nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
                this.collectXblScopedChildNodes(nextSibling);
            }
        }
        
        protected void collectXblScopedChildNodes(final Node node) {
            boolean b = false;
            if (node.getNodeType() == 1) {
                if (!node.getNamespaceURI().equals("http://www.w3.org/2004/xbl")) {
                    b = true;
                }
                else if (node instanceof XBLOMContentElement) {
                    final ContentManager contentManager = DefaultXBLManager.this.getContentManager(node);
                    if (contentManager != null) {
                        final NodeList selectedContent = contentManager.getSelectedContent((XBLOMContentElement)node);
                        for (int i = 0; i < selectedContent.getLength(); ++i) {
                            this.collectXblScopedChildNodes(selectedContent.item(i));
                        }
                    }
                }
            }
            else {
                b = true;
            }
            if (b) {
                this.nodes.add(node);
                ++this.size;
            }
        }
    }
    
    protected class XBLRecord
    {
        public Node node;
        public XblChildNodes childNodes;
        public XblScopedChildNodes scopedChildNodes;
        public XBLOMContentElement contentElement;
        public XBLOMDefinitionElement definitionElement;
        public BindableElement boundElement;
        public boolean linksValid;
        public Node nextSibling;
        public Node previousSibling;
    }
    
    protected class XblChildNodes implements NodeList
    {
        protected XBLRecord record;
        protected List nodes;
        protected int size;
        
        public XblChildNodes(final XBLRecord record) {
            this.record = record;
            this.nodes = new ArrayList();
            this.size = -1;
        }
        
        protected void update() {
            this.size = 0;
            final Element xblShadowTree = DefaultXBLManager.this.getXblShadowTree(this.record.node);
            Node collectXblChildNodes = null;
            for (Node nextSibling = (xblShadowTree == null) ? this.record.node.getFirstChild() : xblShadowTree.getFirstChild(); nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
                collectXblChildNodes = this.collectXblChildNodes(nextSibling, collectXblChildNodes);
            }
            if (collectXblChildNodes != null) {
                final XBLRecord record = DefaultXBLManager.this.getRecord(collectXblChildNodes);
                record.nextSibling = null;
                record.linksValid = true;
            }
        }
        
        protected Node collectXblChildNodes(final Node nextSibling, Node collectXblChildNodes) {
            boolean b = false;
            if (nextSibling.getNodeType() == 1) {
                if (!"http://www.w3.org/2004/xbl".equals(nextSibling.getNamespaceURI())) {
                    b = true;
                }
                else if (nextSibling instanceof XBLOMContentElement) {
                    final ContentManager contentManager = DefaultXBLManager.this.getContentManager(nextSibling);
                    if (contentManager != null) {
                        final NodeList selectedContent = contentManager.getSelectedContent((XBLOMContentElement)nextSibling);
                        for (int i = 0; i < selectedContent.getLength(); ++i) {
                            collectXblChildNodes = this.collectXblChildNodes(selectedContent.item(i), collectXblChildNodes);
                        }
                    }
                }
            }
            else {
                b = true;
            }
            if (b) {
                this.nodes.add(nextSibling);
                ++this.size;
                if (collectXblChildNodes != null) {
                    final XBLRecord record = DefaultXBLManager.this.getRecord(collectXblChildNodes);
                    record.nextSibling = nextSibling;
                    record.linksValid = true;
                }
                final XBLRecord record2 = DefaultXBLManager.this.getRecord(nextSibling);
                record2.previousSibling = collectXblChildNodes;
                record2.linksValid = true;
                collectXblChildNodes = nextSibling;
            }
            return collectXblChildNodes;
        }
        
        public void invalidate() {
            for (int i = 0; i < this.size; ++i) {
                final XBLRecord record = DefaultXBLManager.this.getRecord(this.nodes.get(i));
                record.previousSibling = null;
                record.nextSibling = null;
                record.linksValid = false;
            }
            this.nodes.clear();
            this.size = -1;
        }
        
        public Node getFirstNode() {
            if (this.size == -1) {
                this.update();
            }
            return (this.size == 0) ? null : this.nodes.get(0);
        }
        
        public Node getLastNode() {
            if (this.size == -1) {
                this.update();
            }
            return (this.size == 0) ? null : this.nodes.get(this.nodes.size() - 1);
        }
        
        public Node item(final int n) {
            if (this.size == -1) {
                this.update();
            }
            if (n < 0 || n >= this.size) {
                return null;
            }
            return this.nodes.get(n);
        }
        
        public int getLength() {
            if (this.size == -1) {
                this.update();
            }
            return this.size;
        }
    }
    
    protected class RefAttrListener implements EventListener
    {
        public void handleEvent(final Event event) {
            final EventTarget target = event.getTarget();
            if (target != event.getCurrentTarget()) {
                return;
            }
            final MutationEvent mutationEvent = (MutationEvent)event;
            if (mutationEvent.getAttrName().equals("ref")) {
                final Element element = (Element)target;
                DefaultXBLManager.this.removeDefinitionRef(element);
                if (mutationEvent.getNewValue().length() == 0) {
                    final XBLOMDefinitionElement xblomDefinitionElement = (XBLOMDefinitionElement)element;
                    DefaultXBLManager.this.addDefinition(xblomDefinitionElement.getElementNamespaceURI(), xblomDefinitionElement.getElementLocalName(), (XBLOMDefinitionElement)element, null);
                }
                else {
                    DefaultXBLManager.this.addDefinitionRef(element);
                }
            }
        }
    }
    
    protected class ImportAttrListener implements EventListener
    {
        public void handleEvent(final Event event) {
            final EventTarget target = event.getTarget();
            if (target != event.getCurrentTarget()) {
                return;
            }
            if (((MutationEvent)event).getAttrName().equals("bindings")) {
                final Element element = (Element)target;
                DefaultXBLManager.this.removeImport(element);
                DefaultXBLManager.this.addImport(element);
            }
        }
    }
    
    protected class DefNodeRemovedListener implements EventListener
    {
        protected Element importElement;
        
        public DefNodeRemovedListener(final Element importElement) {
            this.importElement = importElement;
        }
        
        public void handleEvent(final Event event) {
            final Node relatedNode = ((MutationEvent)event).getRelatedNode();
            if (!(relatedNode instanceof XBLOMDefinitionElement)) {
                return;
            }
            final EventTarget target = event.getTarget();
            if (!(target instanceof XBLOMTemplateElement)) {
                return;
            }
            final XBLOMTemplateElement xblomTemplateElement = (XBLOMTemplateElement)target;
            final DefinitionRecord definitionRecord = (DefinitionRecord)DefaultXBLManager.this.definitions.get(relatedNode, this.importElement);
            if (definitionRecord == null || definitionRecord.template != xblomTemplateElement) {
                return;
            }
            final ImportRecord importRecord = DefaultXBLManager.this.imports.get(this.importElement);
            DefaultXBLManager.this.removeTemplateElementListeners(xblomTemplateElement, importRecord);
            definitionRecord.template = null;
            for (Node node = xblomTemplateElement.getNextSibling(); node != null; node = node.getNextSibling()) {
                if (node instanceof XBLOMTemplateElement) {
                    definitionRecord.template = (XBLOMTemplateElement)node;
                    break;
                }
            }
            DefaultXBLManager.this.addTemplateElementListeners(definitionRecord.template, importRecord);
            DefaultXBLManager.this.rebind(definitionRecord.namespaceURI, definitionRecord.localName, DefaultXBLManager.this.document.getDocumentElement());
        }
    }
    
    protected class DefinitionRecord implements Comparable
    {
        public String namespaceURI;
        public String localName;
        public XBLOMDefinitionElement definition;
        public XBLOMTemplateElement template;
        public Element importElement;
        
        public DefinitionRecord(final String namespaceURI, final String localName, final XBLOMDefinitionElement definition, final XBLOMTemplateElement template, final Element importElement) {
            this.namespaceURI = namespaceURI;
            this.localName = localName;
            this.definition = definition;
            this.template = template;
            this.importElement = importElement;
        }
        
        public boolean equals(final Object o) {
            return this.compareTo(o) == 0;
        }
        
        public int compareTo(final Object o) {
            final DefinitionRecord definitionRecord = (DefinitionRecord)o;
            AbstractNode abstractNode;
            AbstractNode abstractNode2;
            if (this.importElement == null) {
                abstractNode = this.definition;
                if (definitionRecord.importElement == null) {
                    abstractNode2 = definitionRecord.definition;
                }
                else {
                    abstractNode2 = (AbstractNode)definitionRecord.importElement;
                }
            }
            else if (definitionRecord.importElement == null) {
                abstractNode = (AbstractNode)this.importElement;
                abstractNode2 = definitionRecord.definition;
            }
            else if (this.definition.getOwnerDocument() == definitionRecord.definition.getOwnerDocument()) {
                abstractNode = this.definition;
                abstractNode2 = definitionRecord.definition;
            }
            else {
                abstractNode = (AbstractNode)this.importElement;
                abstractNode2 = (AbstractNode)definitionRecord.importElement;
            }
            final short compareDocumentPosition = abstractNode.compareDocumentPosition(abstractNode2);
            if ((compareDocumentPosition & 0x2) != 0x0) {
                return -1;
            }
            if ((compareDocumentPosition & 0x4) != 0x0) {
                return 1;
            }
            return 0;
        }
    }
    
    protected class ImportRecord
    {
        public Element importElement;
        public Node node;
        public DefNodeInsertedListener defNodeInsertedListener;
        public DefNodeRemovedListener defNodeRemovedListener;
        public DefAttrListener defAttrListener;
        public ImportInsertedListener importInsertedListener;
        public ImportRemovedListener importRemovedListener;
        public ImportSubtreeListener importSubtreeListener;
        public TemplateMutationListener templateMutationListener;
        
        public ImportRecord(final Element importElement, final Node node) {
            this.importElement = importElement;
            this.node = node;
            this.defNodeInsertedListener = new DefNodeInsertedListener(importElement);
            this.defNodeRemovedListener = new DefNodeRemovedListener(importElement);
            this.defAttrListener = new DefAttrListener(importElement);
            this.importInsertedListener = new ImportInsertedListener(importElement);
            this.importRemovedListener = new ImportRemovedListener();
            this.importSubtreeListener = new ImportSubtreeListener(importElement, this.importRemovedListener);
            this.templateMutationListener = new TemplateMutationListener(importElement);
        }
    }
    
    protected class DefNodeInsertedListener implements EventListener
    {
        protected Element importElement;
        
        public DefNodeInsertedListener(final Element importElement) {
            this.importElement = importElement;
        }
        
        public void handleEvent(final Event event) {
            final Node relatedNode = ((MutationEvent)event).getRelatedNode();
            if (!(relatedNode instanceof XBLOMDefinitionElement)) {
                return;
            }
            final EventTarget target = event.getTarget();
            if (!(target instanceof XBLOMTemplateElement)) {
                return;
            }
            final XBLOMTemplateElement xblomTemplateElement = (XBLOMTemplateElement)target;
            final DefinitionRecord definitionRecord = (DefinitionRecord)DefaultXBLManager.this.definitions.get(relatedNode, this.importElement);
            if (definitionRecord == null) {
                return;
            }
            final ImportRecord importRecord = DefaultXBLManager.this.imports.get(this.importElement);
            if (definitionRecord.template != null) {
                for (Node node = relatedNode.getFirstChild(); node != null; node = node.getNextSibling()) {
                    if (node == xblomTemplateElement) {
                        DefaultXBLManager.this.removeTemplateElementListeners(definitionRecord.template, importRecord);
                        definitionRecord.template = xblomTemplateElement;
                        break;
                    }
                    if (node == definitionRecord.template) {
                        return;
                    }
                }
            }
            else {
                definitionRecord.template = xblomTemplateElement;
            }
            DefaultXBLManager.this.addTemplateElementListeners(xblomTemplateElement, importRecord);
            DefaultXBLManager.this.rebind(definitionRecord.namespaceURI, definitionRecord.localName, DefaultXBLManager.this.document.getDocumentElement());
        }
    }
    
    protected class DefAttrListener implements EventListener
    {
        protected Element importElement;
        
        public DefAttrListener(final Element importElement) {
            this.importElement = importElement;
        }
        
        public void handleEvent(final Event event) {
            final EventTarget target = event.getTarget();
            if (!(target instanceof XBLOMDefinitionElement)) {
                return;
            }
            final XBLOMDefinitionElement xblomDefinitionElement = (XBLOMDefinitionElement)target;
            if (!DefaultXBLManager.this.isActiveDefinition(xblomDefinitionElement, this.importElement)) {
                return;
            }
            final MutationEvent mutationEvent = (MutationEvent)event;
            final String attrName = mutationEvent.getAttrName();
            if (attrName.equals("element")) {
                DefaultXBLManager.this.removeDefinition((DefinitionRecord)DefaultXBLManager.this.definitions.get(xblomDefinitionElement, this.importElement));
                DefaultXBLManager.this.addDefinition(xblomDefinitionElement.getElementNamespaceURI(), xblomDefinitionElement.getElementLocalName(), xblomDefinitionElement, this.importElement);
            }
            else if (attrName.equals("ref") && mutationEvent.getNewValue().length() != 0) {
                DefaultXBLManager.this.removeDefinition((DefinitionRecord)DefaultXBLManager.this.definitions.get(xblomDefinitionElement, this.importElement));
                DefaultXBLManager.this.addDefinitionRef(xblomDefinitionElement);
            }
        }
    }
    
    protected class ImportInsertedListener implements EventListener
    {
        protected Element importElement;
        
        public ImportInsertedListener(final Element importElement) {
            this.importElement = importElement;
        }
        
        public void handleEvent(final Event event) {
            final EventTarget target = event.getTarget();
            if (target instanceof XBLOMDefinitionElement) {
                final XBLOMDefinitionElement xblomDefinitionElement = (XBLOMDefinitionElement)target;
                DefaultXBLManager.this.addDefinition(xblomDefinitionElement.getElementNamespaceURI(), xblomDefinitionElement.getElementLocalName(), xblomDefinitionElement, this.importElement);
            }
        }
    }
    
    protected class ImportRemovedListener implements EventListener
    {
        protected LinkedList toBeRemoved;
        
        protected ImportRemovedListener() {
            this.toBeRemoved = new LinkedList();
        }
        
        public void handleEvent(final Event event) {
            this.toBeRemoved.add(event.getTarget());
        }
    }
    
    protected class ImportSubtreeListener implements EventListener
    {
        protected Element importElement;
        protected ImportRemovedListener importRemovedListener;
        
        public ImportSubtreeListener(final Element importElement, final ImportRemovedListener importRemovedListener) {
            this.importElement = importElement;
            this.importRemovedListener = importRemovedListener;
        }
        
        public void handleEvent(final Event event) {
            final Object[] array = this.importRemovedListener.toBeRemoved.toArray();
            this.importRemovedListener.toBeRemoved.clear();
            for (int i = 0; i < array.length; ++i) {
                DefaultXBLManager.this.removeDefinition((DefinitionRecord)DefaultXBLManager.this.definitions.get(array[i], this.importElement));
            }
        }
    }
    
    protected class TemplateMutationListener implements EventListener
    {
        protected Element importElement;
        
        public TemplateMutationListener(final Element importElement) {
            this.importElement = importElement;
        }
        
        public void handleEvent(final Event event) {
            Node parentNode;
            for (parentNode = (Node)event.getTarget(); parentNode != null && !(parentNode instanceof XBLOMDefinitionElement); parentNode = parentNode.getParentNode()) {}
            final DefinitionRecord definitionRecord = (DefinitionRecord)DefaultXBLManager.this.definitions.get(parentNode, this.importElement);
            if (definitionRecord == null) {
                return;
            }
            DefaultXBLManager.this.rebind(definitionRecord.namespaceURI, definitionRecord.localName, DefaultXBLManager.this.document.getDocumentElement());
        }
    }
    
    protected class DocSubtreeListener implements EventListener
    {
        public void handleEvent(final Event event) {
            final Object[] array = DefaultXBLManager.this.docRemovedListener.defsToBeRemoved.toArray();
            DefaultXBLManager.this.docRemovedListener.defsToBeRemoved.clear();
            for (int i = 0; i < array.length; ++i) {
                final XBLOMDefinitionElement xblomDefinitionElement = (XBLOMDefinitionElement)array[i];
                if (xblomDefinitionElement.getAttributeNS(null, "ref").length() == 0) {
                    DefaultXBLManager.this.removeDefinition((DefinitionRecord)DefaultXBLManager.this.definitions.get(xblomDefinitionElement, null));
                }
                else {
                    DefaultXBLManager.this.removeDefinitionRef(xblomDefinitionElement);
                }
            }
            final Object[] array2 = DefaultXBLManager.this.docRemovedListener.importsToBeRemoved.toArray();
            DefaultXBLManager.this.docRemovedListener.importsToBeRemoved.clear();
            for (int j = 0; j < array2.length; ++j) {
                DefaultXBLManager.this.removeImport((Element)array2[j]);
            }
            final Object[] array3 = DefaultXBLManager.this.docRemovedListener.nodesToBeInvalidated.toArray();
            DefaultXBLManager.this.docRemovedListener.nodesToBeInvalidated.clear();
            for (int k = 0; k < array3.length; ++k) {
                DefaultXBLManager.this.invalidateChildNodes((Node)array3[k]);
            }
        }
    }
    
    protected class DocRemovedListener implements EventListener
    {
        protected LinkedList defsToBeRemoved;
        protected LinkedList importsToBeRemoved;
        protected LinkedList nodesToBeInvalidated;
        
        protected DocRemovedListener() {
            this.defsToBeRemoved = new LinkedList();
            this.importsToBeRemoved = new LinkedList();
            this.nodesToBeInvalidated = new LinkedList();
        }
        
        public void handleEvent(final Event event) {
            final EventTarget target = event.getTarget();
            if (target instanceof XBLOMDefinitionElement) {
                if (DefaultXBLManager.this.getXblBoundElement((Node)target) == null) {
                    this.defsToBeRemoved.add(target);
                }
            }
            else if (target instanceof XBLOMImportElement && DefaultXBLManager.this.getXblBoundElement((Node)target) == null) {
                this.importsToBeRemoved.add(target);
            }
            final Node xblParentNode = DefaultXBLManager.this.getXblParentNode((Node)target);
            if (xblParentNode != null) {
                this.nodesToBeInvalidated.add(xblParentNode);
            }
        }
    }
    
    protected class DocInsertedListener implements EventListener
    {
        public void handleEvent(Event ultimateOriginalEvent) {
            final EventTarget target = ultimateOriginalEvent.getTarget();
            if (target instanceof XBLOMDefinitionElement) {
                if (DefaultXBLManager.this.getXblBoundElement((Node)target) == null) {
                    final XBLOMDefinitionElement xblomDefinitionElement = (XBLOMDefinitionElement)target;
                    if (xblomDefinitionElement.getAttributeNS(null, "ref").length() == 0) {
                        DefaultXBLManager.this.addDefinition(xblomDefinitionElement.getElementNamespaceURI(), xblomDefinitionElement.getElementLocalName(), xblomDefinitionElement, null);
                    }
                    else {
                        DefaultXBLManager.this.addDefinitionRef(xblomDefinitionElement);
                    }
                }
            }
            else if (target instanceof XBLOMImportElement) {
                if (DefaultXBLManager.this.getXblBoundElement((Node)target) == null) {
                    DefaultXBLManager.this.addImport((Element)target);
                }
            }
            else {
                ultimateOriginalEvent = EventSupport.getUltimateOriginalEvent(ultimateOriginalEvent);
                final EventTarget target2 = ultimateOriginalEvent.getTarget();
                final Node xblParentNode = DefaultXBLManager.this.getXblParentNode((Node)target2);
                if (xblParentNode != null) {
                    DefaultXBLManager.this.invalidateChildNodes(xblParentNode);
                }
                if (target2 instanceof BindableElement) {
                    for (Node node = ((Element)target2).getParentNode(); node != null; node = node.getParentNode()) {
                        if (node instanceof BindableElement && DefaultXBLManager.this.getRecord(node).definitionElement != null) {
                            return;
                        }
                    }
                    DefaultXBLManager.this.bind((Element)target2);
                }
            }
        }
    }
}
