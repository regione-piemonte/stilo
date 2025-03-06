// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.events.EventListener;
import org.apache.batik.css.engine.CSSNavigableDocumentListener;
import org.w3c.dom.Node;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;
import org.apache.batik.dom.svg.SVGOMDocument;

public class SVG12OMDocument extends SVGOMDocument
{
    protected SVG12OMDocument() {
    }
    
    public SVG12OMDocument(final DocumentType documentType, final DOMImplementation domImplementation) {
        super(documentType, domImplementation);
    }
    
    protected Node newNode() {
        return new SVG12OMDocument();
    }
    
    public void addCSSNavigableDocumentListener(final CSSNavigableDocumentListener cssNavigableDocumentListener) {
        if (this.cssNavigableDocumentListeners.containsKey(cssNavigableDocumentListener)) {
            return;
        }
        final DOMNodeInsertedListenerWrapper domNodeInsertedListenerWrapper = new DOMNodeInsertedListenerWrapper(cssNavigableDocumentListener);
        final DOMNodeRemovedListenerWrapper domNodeRemovedListenerWrapper = new DOMNodeRemovedListenerWrapper(cssNavigableDocumentListener);
        final DOMSubtreeModifiedListenerWrapper domSubtreeModifiedListenerWrapper = new DOMSubtreeModifiedListenerWrapper(cssNavigableDocumentListener);
        final DOMCharacterDataModifiedListenerWrapper domCharacterDataModifiedListenerWrapper = new DOMCharacterDataModifiedListenerWrapper(cssNavigableDocumentListener);
        final DOMAttrModifiedListenerWrapper domAttrModifiedListenerWrapper = new DOMAttrModifiedListenerWrapper(cssNavigableDocumentListener);
        this.cssNavigableDocumentListeners.put(cssNavigableDocumentListener, new EventListener[] { domNodeInsertedListenerWrapper, domNodeRemovedListenerWrapper, domSubtreeModifiedListenerWrapper, domCharacterDataModifiedListenerWrapper, domAttrModifiedListenerWrapper });
        final XBLEventSupport xblEventSupport = (XBLEventSupport)this.initializeEventSupport();
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", domNodeInsertedListenerWrapper, false);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", domNodeRemovedListenerWrapper, false);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", domSubtreeModifiedListenerWrapper, false);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", domCharacterDataModifiedListenerWrapper, false);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", domAttrModifiedListenerWrapper, false);
    }
    
    public void removeCSSNavigableDocumentListener(final CSSNavigableDocumentListener cssNavigableDocumentListener) {
        final EventListener[] array = this.cssNavigableDocumentListeners.get(cssNavigableDocumentListener);
        if (array == null) {
            return;
        }
        final XBLEventSupport xblEventSupport = (XBLEventSupport)this.initializeEventSupport();
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", array[0], false);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", array[1], false);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", array[2], false);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", array[3], false);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", array[4], false);
        this.cssNavigableDocumentListeners.remove(cssNavigableDocumentListener);
    }
}
