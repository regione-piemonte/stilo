// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.w3c.dom.events.MutationEvent;
import org.w3c.dom.Attr;
import org.w3c.dom.events.Event;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.event.EventListenerList;
import org.apache.batik.dom.svg12.XBLOMContentElement;
import java.util.Iterator;
import org.apache.batik.dom.events.NodeEventTarget;
import java.util.Map;
import org.w3c.dom.NodeList;
import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.svg12.XBLEventSupport;
import org.apache.batik.dom.xbl.XBLManager;
import org.w3c.dom.Node;
import java.util.LinkedList;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.apache.batik.dom.svg12.XBLOMShadowTreeElement;

public class ContentManager
{
    protected XBLOMShadowTreeElement shadowTree;
    protected Element boundElement;
    protected DefaultXBLManager xblManager;
    protected HashMap selectors;
    protected HashMap selectedNodes;
    protected LinkedList contentElementList;
    protected Node removedNode;
    protected HashMap listeners;
    protected ContentElementDOMAttrModifiedEventListener contentElementDomAttrModifiedEventListener;
    protected DOMAttrModifiedEventListener domAttrModifiedEventListener;
    protected DOMNodeInsertedEventListener domNodeInsertedEventListener;
    protected DOMNodeRemovedEventListener domNodeRemovedEventListener;
    protected DOMSubtreeModifiedEventListener domSubtreeModifiedEventListener;
    protected ShadowTreeNodeInsertedListener shadowTreeNodeInsertedListener;
    protected ShadowTreeNodeRemovedListener shadowTreeNodeRemovedListener;
    protected ShadowTreeSubtreeModifiedListener shadowTreeSubtreeModifiedListener;
    
    public ContentManager(final XBLOMShadowTreeElement shadowTree, final XBLManager xblManager) {
        this.selectors = new HashMap();
        this.selectedNodes = new HashMap();
        this.contentElementList = new LinkedList();
        this.listeners = new HashMap();
        this.shadowTree = shadowTree;
        (this.xblManager = (DefaultXBLManager)xblManager).setContentManager(shadowTree, this);
        this.boundElement = this.xblManager.getXblBoundElement(shadowTree);
        this.contentElementDomAttrModifiedEventListener = new ContentElementDOMAttrModifiedEventListener();
        final XBLEventSupport xblEventSupport = (XBLEventSupport)this.shadowTree.initializeEventSupport();
        this.shadowTreeNodeInsertedListener = new ShadowTreeNodeInsertedListener();
        this.shadowTreeNodeRemovedListener = new ShadowTreeNodeRemovedListener();
        this.shadowTreeSubtreeModifiedListener = new ShadowTreeSubtreeModifiedListener();
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.shadowTreeNodeInsertedListener, true);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.shadowTreeNodeRemovedListener, true);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.shadowTreeSubtreeModifiedListener, true);
        final XBLEventSupport xblEventSupport2 = (XBLEventSupport)((AbstractNode)this.boundElement).initializeEventSupport();
        this.domAttrModifiedEventListener = new DOMAttrModifiedEventListener();
        this.domNodeInsertedEventListener = new DOMNodeInsertedEventListener();
        this.domNodeRemovedEventListener = new DOMNodeRemovedEventListener();
        this.domSubtreeModifiedEventListener = new DOMSubtreeModifiedEventListener();
        xblEventSupport2.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.domAttrModifiedEventListener, true);
        xblEventSupport2.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.domNodeInsertedEventListener, true);
        xblEventSupport2.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.domNodeRemovedEventListener, true);
        xblEventSupport2.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.domSubtreeModifiedEventListener, false);
        this.update(true);
    }
    
    public void dispose() {
        this.xblManager.setContentManager(this.shadowTree, null);
        final Iterator<Map.Entry<K, NodeList>> iterator = this.selectedNodes.entrySet().iterator();
        while (iterator.hasNext()) {
            final NodeList list = iterator.next().getValue();
            for (int i = 0; i < list.getLength(); ++i) {
                this.xblManager.getRecord(list.item(i)).contentElement = null;
            }
        }
        final Iterator iterator2 = this.contentElementList.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.contentElementDomAttrModifiedEventListener, false);
        }
        this.contentElementList.clear();
        this.selectedNodes.clear();
        final XBLEventSupport xblEventSupport = (XBLEventSupport)((AbstractNode)this.boundElement).getEventSupport();
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.domAttrModifiedEventListener, true);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.domNodeInsertedEventListener, true);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.domNodeRemovedEventListener, true);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.domSubtreeModifiedEventListener, false);
    }
    
    public NodeList getSelectedContent(final XBLOMContentElement key) {
        return this.selectedNodes.get(key);
    }
    
    protected XBLOMContentElement getContentElement(final Node node) {
        return this.xblManager.getXblContentElement(node);
    }
    
    public void addContentSelectionChangedListener(final XBLOMContentElement xblomContentElement, final ContentSelectionChangedListener l) {
        EventListenerList value = this.listeners.get(xblomContentElement);
        if (value == null) {
            value = new EventListenerList();
            this.listeners.put(xblomContentElement, value);
        }
        value.add(ContentSelectionChangedListener.class, l);
    }
    
    public void removeContentSelectionChangedListener(final XBLOMContentElement key, final ContentSelectionChangedListener l) {
        final EventListenerList list = this.listeners.get(key);
        if (list != null) {
            list.remove(ContentSelectionChangedListener.class, l);
        }
    }
    
    protected void dispatchContentSelectionChangedEvent(final XBLOMContentElement key) {
        this.xblManager.invalidateChildNodes(key.getXblParentNode());
        final ContentSelectionChangedEvent contentSelectionChangedEvent = new ContentSelectionChangedEvent(key);
        final EventListenerList list = this.listeners.get(key);
        if (list != null) {
            final Object[] listenerList = list.getListenerList();
            for (int i = listenerList.length - 2; i >= 0; i -= 2) {
                ((ContentSelectionChangedListener)listenerList[i + 1]).contentSelectionChanged(contentSelectionChangedEvent);
            }
        }
        final Object[] contentSelectionChangedListeners = this.xblManager.getContentSelectionChangedListeners();
        for (int j = contentSelectionChangedListeners.length - 2; j >= 0; j -= 2) {
            ((ContentSelectionChangedListener)contentSelectionChangedListeners[j + 1]).contentSelectionChanged(contentSelectionChangedEvent);
        }
    }
    
    protected void update(final boolean b) {
        final HashSet<Node> set = new HashSet<Node>();
        final Iterator<Map.Entry<K, NodeList>> iterator = this.selectedNodes.entrySet().iterator();
        while (iterator.hasNext()) {
            final NodeList list = iterator.next().getValue();
            for (int i = 0; i < list.getLength(); ++i) {
                final Node item = list.item(i);
                this.xblManager.getRecord(item).contentElement = null;
                set.add(item);
            }
        }
        final Iterator iterator2 = this.contentElementList.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.contentElementDomAttrModifiedEventListener, false);
        }
        this.contentElementList.clear();
        this.selectedNodes.clear();
        boolean b2 = false;
        for (Node node = this.shadowTree.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (this.update(b, node)) {
                b2 = true;
            }
        }
        if (b2) {
            final HashSet<Node> set2 = new HashSet<Node>();
            final Iterator<Map.Entry<K, NodeList>> iterator3 = this.selectedNodes.entrySet().iterator();
            while (iterator3.hasNext()) {
                final NodeList list2 = iterator3.next().getValue();
                for (int j = 0; j < list2.getLength(); ++j) {
                    set2.add(list2.item(j));
                }
            }
            final HashSet set3 = new HashSet();
            set3.addAll(set);
            set3.removeAll(set2);
            final HashSet set4 = new HashSet();
            set4.addAll(set2);
            set4.removeAll(set);
            if (!b) {
                this.xblManager.shadowTreeSelectedContentChanged(set3, set4);
            }
        }
    }
    
    protected boolean update(final boolean b, final Node node) {
        boolean b2 = false;
        for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            if (this.update(b, node2)) {
                b2 = true;
            }
        }
        if (node instanceof XBLOMContentElement) {
            this.contentElementList.add(node);
            final XBLOMContentElement contentElement = (XBLOMContentElement)node;
            contentElement.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.contentElementDomAttrModifiedEventListener, false, null);
            AbstractContentSelector selector = this.selectors.get(node);
            boolean update;
            if (selector == null) {
                if (contentElement.hasAttributeNS(null, "includes")) {
                    selector = AbstractContentSelector.createSelector(this.getContentSelectorLanguage(contentElement), this, contentElement, this.boundElement, contentElement.getAttributeNS(null, "includes"));
                }
                else {
                    selector = new DefaultContentSelector(this, contentElement, this.boundElement);
                }
                this.selectors.put(node, selector);
                update = true;
            }
            else {
                update = selector.update();
            }
            final NodeList selectedContent = selector.getSelectedContent();
            this.selectedNodes.put(node, selectedContent);
            for (int i = 0; i < selectedContent.getLength(); ++i) {
                this.xblManager.getRecord(selectedContent.item(i)).contentElement = contentElement;
            }
            if (update) {
                b2 = true;
                this.dispatchContentSelectionChangedEvent(contentElement);
            }
        }
        return b2;
    }
    
    protected String getContentSelectorLanguage(final Element element) {
        final String attributeNS = element.getAttributeNS("http://xml.apache.org/batik/ext", "selectorLanguage");
        if (attributeNS.length() != 0) {
            return attributeNS;
        }
        final String attributeNS2 = element.getOwnerDocument().getDocumentElement().getAttributeNS("http://xml.apache.org/batik/ext", "selectorLanguage");
        if (attributeNS2.length() != 0) {
            return attributeNS2;
        }
        return null;
    }
    
    protected class ShadowTreeSubtreeModifiedListener implements EventListener
    {
        public void handleEvent(final Event event) {
            if (ContentManager.this.removedNode != null) {
                ContentManager.this.removedNode = null;
                ContentManager.this.update(false);
            }
        }
    }
    
    protected class ShadowTreeNodeRemovedListener implements EventListener
    {
        public void handleEvent(final Event event) {
            if (event.getTarget() instanceof XBLOMContentElement) {
                ContentManager.this.removedNode = (Node)event.getTarget();
            }
        }
    }
    
    protected class ShadowTreeNodeInsertedListener implements EventListener
    {
        public void handleEvent(final Event event) {
            if (event.getTarget() instanceof XBLOMContentElement) {
                ContentManager.this.update(false);
            }
        }
    }
    
    protected class DOMSubtreeModifiedEventListener implements EventListener
    {
        public void handleEvent(final Event event) {
            if (ContentManager.this.removedNode != null) {
                ContentManager.this.removedNode = null;
                ContentManager.this.update(false);
            }
        }
    }
    
    protected class DOMNodeRemovedEventListener implements EventListener
    {
        public void handleEvent(final Event event) {
            ContentManager.this.removedNode = (Node)event.getTarget();
        }
    }
    
    protected class DOMNodeInsertedEventListener implements EventListener
    {
        public void handleEvent(final Event event) {
            ContentManager.this.update(false);
        }
    }
    
    protected class DOMAttrModifiedEventListener implements EventListener
    {
        public void handleEvent(final Event event) {
            if (event.getTarget() != ContentManager.this.boundElement) {
                ContentManager.this.update(false);
            }
        }
    }
    
    protected class ContentElementDOMAttrModifiedEventListener implements EventListener
    {
        public void handleEvent(final Event event) {
            final Attr attr = (Attr)((MutationEvent)event).getRelatedNode();
            final Element key = (Element)event.getTarget();
            if (key instanceof XBLOMContentElement) {
                final String namespaceURI = attr.getNamespaceURI();
                String s = attr.getLocalName();
                if (s == null) {
                    s = attr.getNodeName();
                }
                if ((namespaceURI == null && "includes".equals(s)) || ("http://xml.apache.org/batik/ext".equals(namespaceURI) && "selectorLanguage".equals(s))) {
                    ContentManager.this.selectors.remove(key);
                    ContentManager.this.update(false);
                }
            }
        }
    }
}
