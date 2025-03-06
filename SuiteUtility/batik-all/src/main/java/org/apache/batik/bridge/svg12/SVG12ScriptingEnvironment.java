// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.apache.batik.dom.events.EventSupport;
import org.apache.batik.bridge.SVGUtilities;
import org.apache.batik.bridge.Messages;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;
import org.apache.batik.dom.svg12.SVGGlobal;
import org.apache.batik.script.Window;
import org.apache.batik.script.Interpreter;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.dom.AbstractElement;
import org.w3c.dom.Element;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg12.XBLEventSupport;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.dom.util.TriplyIndexedTable;
import org.apache.batik.bridge.ScriptingEnvironment;

public class SVG12ScriptingEnvironment extends ScriptingEnvironment
{
    public static final String HANDLER_SCRIPT_DESCRIPTION = "SVG12ScriptingEnvironment.constant.handler.script.description";
    protected TriplyIndexedTable handlerScriptingListeners;
    
    public SVG12ScriptingEnvironment(final BridgeContext bridgeContext) {
        super(bridgeContext);
    }
    
    protected void addDocumentListeners() {
        this.domNodeInsertedListener = new DOMNodeInsertedListener();
        this.domNodeRemovedListener = new DOMNodeRemovedListener();
        this.domAttrModifiedListener = new DOMAttrModifiedListener();
        final XBLEventSupport xblEventSupport = (XBLEventSupport)((AbstractDocument)this.document).initializeEventSupport();
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.domNodeInsertedListener, false);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.domNodeRemovedListener, false);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.domAttrModifiedListener, false);
    }
    
    protected void removeDocumentListeners() {
        final XBLEventSupport xblEventSupport = (XBLEventSupport)((AbstractDocument)this.document).initializeEventSupport();
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.domNodeInsertedListener, false);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.domNodeRemovedListener, false);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.domAttrModifiedListener, false);
    }
    
    protected void addScriptingListenersOn(final Element element) {
        final String namespaceURI = element.getNamespaceURI();
        final String localName = element.getLocalName();
        if ("http://www.w3.org/2000/svg".equals(namespaceURI) && "handler".equals(localName)) {
            final AbstractElement abstractElement = (AbstractElement)element.getParentNode();
            String s = element.getAttributeNS("http://www.w3.org/2001/xml-events", "event");
            String lookupNamespaceURI = "http://www.w3.org/2001/xml-events";
            if (s.indexOf(58) != -1) {
                final String prefix = DOMUtilities.getPrefix(s);
                s = DOMUtilities.getLocalName(s);
                lookupNamespaceURI = ((AbstractElement)element).lookupNamespaceURI(prefix);
            }
            final HandlerScriptingEventListener handlerScriptingEventListener = new HandlerScriptingEventListener(lookupNamespaceURI, s, (AbstractElement)element);
            abstractElement.addEventListenerNS(lookupNamespaceURI, s, handlerScriptingEventListener, false, null);
            if (this.handlerScriptingListeners == null) {
                this.handlerScriptingListeners = new TriplyIndexedTable();
            }
            this.handlerScriptingListeners.put(lookupNamespaceURI, s, element, handlerScriptingEventListener);
        }
        super.addScriptingListenersOn(element);
    }
    
    protected void removeScriptingListenersOn(final Element element) {
        final String namespaceURI = element.getNamespaceURI();
        final String localName = element.getLocalName();
        if ("http://www.w3.org/2000/svg".equals(namespaceURI) && "handler".equals(localName)) {
            final AbstractElement abstractElement = (AbstractElement)element.getParentNode();
            String s = element.getAttributeNS("http://www.w3.org/2001/xml-events", "event");
            String lookupNamespaceURI = "http://www.w3.org/2001/xml-events";
            if (s.indexOf(58) != -1) {
                final String prefix = DOMUtilities.getPrefix(s);
                s = DOMUtilities.getLocalName(s);
                lookupNamespaceURI = ((AbstractElement)element).lookupNamespaceURI(prefix);
            }
            abstractElement.removeEventListenerNS(lookupNamespaceURI, s, (EventListener)this.handlerScriptingListeners.put(lookupNamespaceURI, s, element, null), false);
        }
        super.removeScriptingListenersOn(element);
    }
    
    public org.apache.batik.script.Window createWindow(final Interpreter interpreter, final String s) {
        return new Global(interpreter, s);
    }
    
    protected class Global extends Window implements SVGGlobal
    {
        public Global(final Interpreter interpreter, final String s) {
            super(interpreter, s);
        }
        
        public void startMouseCapture(final EventTarget eventTarget, final boolean b, final boolean b2) {
            ((SVG12BridgeContext)SVG12ScriptingEnvironment.this.bridgeContext.getPrimaryBridgeContext()).startMouseCapture(eventTarget, b, b2);
        }
        
        public void stopMouseCapture() {
            ((SVG12BridgeContext)SVG12ScriptingEnvironment.this.bridgeContext.getPrimaryBridgeContext()).stopMouseCapture();
        }
    }
    
    protected class HandlerScriptingEventListener implements EventListener
    {
        protected String eventNamespaceURI;
        protected String eventType;
        protected AbstractElement handlerElement;
        
        public HandlerScriptingEventListener(final String eventNamespaceURI, final String eventType, final AbstractElement handlerElement) {
            this.eventNamespaceURI = eventNamespaceURI;
            this.eventType = eventType;
            this.handlerElement = handlerElement;
        }
        
        public void handleEvent(final Event event) {
            final Element element = (Element)event.getCurrentTarget();
            final String textContent = this.handlerElement.getTextContent();
            if (textContent.length() == 0) {
                return;
            }
            final String formatMessage = Messages.formatMessage("SVG12ScriptingEnvironment.constant.handler.script.description", new Object[] { ((AbstractDocument)this.handlerElement.getOwnerDocument()).getDocumentURI(), this.eventNamespaceURI, this.eventType, new Integer(SVG12ScriptingEnvironment.this.bridgeContext.getDocumentLoader().getLineNumber(this.handlerElement)) });
            String s = this.handlerElement.getAttributeNS(null, "contentScriptType");
            if (s.length() == 0) {
                Element parentElement;
                for (parentElement = element; parentElement != null && (!"http://www.w3.org/2000/svg".equals(parentElement.getNamespaceURI()) || !"svg".equals(parentElement.getLocalName())); parentElement = SVGUtilities.getParentElement(parentElement)) {}
                if (parentElement == null) {
                    return;
                }
                s = parentElement.getAttributeNS(null, "contentScriptType");
            }
            SVG12ScriptingEnvironment.this.runEventHandler(textContent, event, s, formatMessage);
        }
    }
    
    protected class DOMAttrModifiedListener extends ScriptingEnvironment.DOMAttrModifiedListener
    {
        public void handleEvent(final Event event) {
            super.handleEvent(EventSupport.getUltimateOriginalEvent(event));
        }
    }
    
    protected class DOMNodeRemovedListener extends ScriptingEnvironment.DOMNodeRemovedListener
    {
        public void handleEvent(final Event event) {
            super.handleEvent(EventSupport.getUltimateOriginalEvent(event));
        }
    }
    
    protected class DOMNodeInsertedListener extends ScriptingEnvironment.DOMNodeInsertedListener
    {
        public void handleEvent(final Event event) {
            super.handleEvent(EventSupport.getUltimateOriginalEvent(event));
        }
    }
}
