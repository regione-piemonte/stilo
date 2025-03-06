// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.apache.batik.dom.events.EventSupport;
import org.w3c.dom.events.Event;
import org.apache.batik.dom.xbl.NodeXBL;
import org.apache.batik.dom.svg12.XBLOMShadowTreeElement;
import org.w3c.dom.Element;
import org.apache.batik.bridge.BridgeUpdateHandler;
import org.apache.batik.bridge.ScriptingEnvironment;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.dom.xbl.XBLManager;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.events.NodeEventTarget;
import java.util.Iterator;
import org.apache.batik.script.Interpreter;
import org.apache.batik.dom.svg12.XBLEventSupport;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.Document;
import org.apache.batik.bridge.URIResolver;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.script.InterpreterPool;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.UserAgent;
import org.w3c.dom.events.EventTarget;
import org.apache.batik.bridge.BridgeContext;

public class SVG12BridgeContext extends BridgeContext
{
    protected XBLBindingListener bindingListener;
    protected XBLContentListener contentListener;
    protected EventTarget mouseCaptureTarget;
    protected boolean mouseCaptureSendAll;
    protected boolean mouseCaptureAutoRelease;
    
    public SVG12BridgeContext(final UserAgent userAgent) {
        super(userAgent);
    }
    
    public SVG12BridgeContext(final UserAgent userAgent, final DocumentLoader documentLoader) {
        super(userAgent, documentLoader);
    }
    
    public SVG12BridgeContext(final UserAgent userAgent, final InterpreterPool interpreterPool, final DocumentLoader documentLoader) {
        super(userAgent, interpreterPool, documentLoader);
    }
    
    public URIResolver createURIResolver(final SVGDocument svgDocument, final DocumentLoader documentLoader) {
        return new SVG12URIResolver(svgDocument, documentLoader);
    }
    
    public void addGVTListener(final Document document) {
        SVG12BridgeEventSupport.addGVTListener(this, document);
    }
    
    public void dispose() {
        this.clearChildContexts();
        synchronized (this.eventListenerSet) {
            for (final EventListenerMememto eventListenerMememto : this.eventListenerSet) {
                final NodeEventTarget target = eventListenerMememto.getTarget();
                final EventListener listener = eventListenerMememto.getListener();
                final boolean useCapture = eventListenerMememto.getUseCapture();
                final String eventType = eventListenerMememto.getEventType();
                final boolean namespaced = eventListenerMememto.getNamespaced();
                if (target != null && listener != null) {
                    if (eventType == null) {
                        continue;
                    }
                    if (eventListenerMememto instanceof ImplementationEventListenerMememto) {
                        final String namespaceURI = eventListenerMememto.getNamespaceURI();
                        final AbstractNode abstractNode = (AbstractNode)((Node)target).getOwnerDocument();
                        if (abstractNode == null) {
                            continue;
                        }
                        ((XBLEventSupport)abstractNode.initializeEventSupport()).removeImplementationEventListenerNS(namespaceURI, eventType, listener, useCapture);
                    }
                    else if (namespaced) {
                        target.removeEventListenerNS(eventListenerMememto.getNamespaceURI(), eventType, listener, useCapture);
                    }
                    else {
                        target.removeEventListener(eventType, listener, useCapture);
                    }
                }
            }
        }
        if (this.document != null) {
            this.removeDOMListeners();
            this.removeBindingListener();
        }
        if (this.animationEngine != null) {
            this.animationEngine.dispose();
            this.animationEngine = null;
        }
        for (final Interpreter interpreter : this.interpreterMap.values()) {
            if (interpreter != null) {
                interpreter.dispose();
            }
        }
        this.interpreterMap.clear();
        if (this.focusManager != null) {
            this.focusManager.dispose();
        }
    }
    
    public void addBindingListener() {
        final DefaultXBLManager defaultXBLManager = (DefaultXBLManager)((AbstractDocument)this.document).getXBLManager();
        if (defaultXBLManager != null) {
            defaultXBLManager.addBindingListener(this.bindingListener = new XBLBindingListener());
            defaultXBLManager.addContentSelectionChangedListener(this.contentListener = new XBLContentListener());
        }
    }
    
    public void removeBindingListener() {
        final XBLManager xblManager = ((AbstractDocument)this.document).getXBLManager();
        if (xblManager instanceof DefaultXBLManager) {
            final DefaultXBLManager defaultXBLManager = (DefaultXBLManager)xblManager;
            defaultXBLManager.removeBindingListener(this.bindingListener);
            defaultXBLManager.removeContentSelectionChangedListener(this.contentListener);
        }
    }
    
    public void addDOMListeners() {
        final SVGOMDocument svgomDocument = (SVGOMDocument)this.document;
        final XBLEventSupport xblEventSupport = (XBLEventSupport)svgomDocument.initializeEventSupport();
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.domAttrModifiedEventListener = new EventListenerWrapper(new DOMAttrModifiedEventListener()), true);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.domNodeInsertedEventListener = new EventListenerWrapper(new DOMNodeInsertedEventListener()), true);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.domNodeRemovedEventListener = new EventListenerWrapper(new DOMNodeRemovedEventListener()), true);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", this.domCharacterDataModifiedEventListener = new EventListenerWrapper(new DOMCharacterDataModifiedEventListener()), true);
        svgomDocument.addAnimatedAttributeListener(this.animatedAttributeListener = new AnimatedAttrListener());
        this.focusManager = new SVG12FocusManager(this.document);
        svgomDocument.getCSSEngine().addCSSEngineListener(this.cssPropertiesChangedListener = new CSSPropertiesChangedListener());
    }
    
    public void addUIEventListeners(final Document document) {
        final EventTarget eventTarget = (EventTarget)document.getDocumentElement();
        final XBLEventSupport xblEventSupport = (XBLEventSupport)((AbstractNode)eventTarget).initializeEventSupport();
        final EventListenerWrapper eventListenerWrapper = new EventListenerWrapper(new DOMMouseOverEventListener());
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", eventListenerWrapper, true);
        this.storeImplementationEventListenerNS(eventTarget, "http://www.w3.org/2001/xml-events", "mouseover", eventListenerWrapper, true);
        final EventListenerWrapper eventListenerWrapper2 = new EventListenerWrapper(new DOMMouseOutEventListener());
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", eventListenerWrapper2, true);
        this.storeImplementationEventListenerNS(eventTarget, "http://www.w3.org/2001/xml-events", "mouseout", eventListenerWrapper2, true);
    }
    
    public void removeUIEventListeners(final Document document) {
        final EventTarget eventTarget = (EventTarget)document.getDocumentElement();
        final XBLEventSupport xblEventSupport = (XBLEventSupport)((AbstractNode)eventTarget).initializeEventSupport();
        synchronized (this.eventListenerSet) {
            for (final EventListenerMememto eventListenerMememto : this.eventListenerSet) {
                final NodeEventTarget target = eventListenerMememto.getTarget();
                if (target == eventTarget) {
                    final EventListener listener = eventListenerMememto.getListener();
                    final boolean useCapture = eventListenerMememto.getUseCapture();
                    final String eventType = eventListenerMememto.getEventType();
                    final boolean namespaced = eventListenerMememto.getNamespaced();
                    if (target == null || listener == null) {
                        continue;
                    }
                    if (eventType == null) {
                        continue;
                    }
                    if (eventListenerMememto instanceof ImplementationEventListenerMememto) {
                        xblEventSupport.removeImplementationEventListenerNS(eventListenerMememto.getNamespaceURI(), eventType, listener, useCapture);
                    }
                    else if (namespaced) {
                        target.removeEventListenerNS(eventListenerMememto.getNamespaceURI(), eventType, listener, useCapture);
                    }
                    else {
                        target.removeEventListener(eventType, listener, useCapture);
                    }
                }
            }
        }
    }
    
    protected void removeDOMListeners() {
        final SVGOMDocument svgomDocument = (SVGOMDocument)this.document;
        svgomDocument.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMAttrModified", this.domAttrModifiedEventListener, true);
        svgomDocument.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeInserted", this.domNodeInsertedEventListener, true);
        svgomDocument.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.domNodeRemovedEventListener, true);
        svgomDocument.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", this.domCharacterDataModifiedEventListener, true);
        svgomDocument.removeAnimatedAttributeListener(this.animatedAttributeListener);
        final CSSEngine cssEngine = svgomDocument.getCSSEngine();
        if (cssEngine != null) {
            cssEngine.removeCSSEngineListener(this.cssPropertiesChangedListener);
            cssEngine.dispose();
            svgomDocument.setCSSEngine(null);
        }
    }
    
    protected void storeImplementationEventListenerNS(final EventTarget eventTarget, final String s, final String s2, final EventListener eventListener, final boolean b) {
        synchronized (this.eventListenerSet) {
            this.eventListenerSet.add(new ImplementationEventListenerMememto(eventTarget, s, s2, eventListener, b, this));
        }
    }
    
    public BridgeContext createSubBridgeContext(final SVGOMDocument svgomDocument) {
        if (svgomDocument.getCSSEngine() != null) {
            return (BridgeContext)svgomDocument.getCSSEngine().getCSSContext();
        }
        final BridgeContext subBridgeContext = super.createSubBridgeContext(svgomDocument);
        if (this.isDynamic() && subBridgeContext.isDynamic()) {
            this.setUpdateManager(subBridgeContext, this.updateManager);
            if (this.updateManager != null) {
                ScriptingEnvironment scriptingEnvironment;
                if (svgomDocument.isSVG12()) {
                    scriptingEnvironment = new SVG12ScriptingEnvironment(subBridgeContext);
                }
                else {
                    scriptingEnvironment = new ScriptingEnvironment(subBridgeContext);
                }
                scriptingEnvironment.loadScripts();
                scriptingEnvironment.dispatchSVGLoadEvent();
                if (svgomDocument.isSVG12()) {
                    final DefaultXBLManager xblManager = new DefaultXBLManager(svgomDocument, subBridgeContext);
                    this.setXBLManager(subBridgeContext, xblManager);
                    svgomDocument.setXBLManager(xblManager);
                    xblManager.startProcessing();
                }
            }
        }
        return subBridgeContext;
    }
    
    public void startMouseCapture(final EventTarget mouseCaptureTarget, final boolean mouseCaptureSendAll, final boolean mouseCaptureAutoRelease) {
        this.mouseCaptureTarget = mouseCaptureTarget;
        this.mouseCaptureSendAll = mouseCaptureSendAll;
        this.mouseCaptureAutoRelease = mouseCaptureAutoRelease;
    }
    
    public void stopMouseCapture() {
        this.mouseCaptureTarget = null;
    }
    
    protected class XBLContentListener implements ContentSelectionChangedListener
    {
        public void contentSelectionChanged(final ContentSelectionChangedEvent contentSelectionChangedEvent) {
            Element xblBoundElement = (Element)contentSelectionChangedEvent.getContentElement().getParentNode();
            if (xblBoundElement instanceof XBLOMShadowTreeElement) {
                xblBoundElement = ((NodeXBL)xblBoundElement).getXblBoundElement();
            }
            final BridgeUpdateHandler access$200 = BridgeContext.getBridgeUpdateHandler(xblBoundElement);
            if (access$200 instanceof SVG12BridgeUpdateHandler) {
                final SVG12BridgeUpdateHandler svg12BridgeUpdateHandler = (SVG12BridgeUpdateHandler)access$200;
                try {
                    svg12BridgeUpdateHandler.handleContentSelectionChangedEvent(contentSelectionChangedEvent);
                }
                catch (Exception ex) {
                    SVG12BridgeContext.this.userAgent.displayError(ex);
                }
            }
        }
    }
    
    protected class XBLBindingListener implements BindingListener
    {
        public void bindingChanged(final Element element, final Element element2) {
            final BridgeUpdateHandler access$000 = BridgeContext.getBridgeUpdateHandler(element);
            if (access$000 instanceof SVG12BridgeUpdateHandler) {
                final SVG12BridgeUpdateHandler svg12BridgeUpdateHandler = (SVG12BridgeUpdateHandler)access$000;
                try {
                    svg12BridgeUpdateHandler.handleBindingEvent(element, element2);
                }
                catch (Exception ex) {
                    SVG12BridgeContext.this.userAgent.displayError(ex);
                }
            }
        }
    }
    
    protected class EventListenerWrapper implements EventListener
    {
        protected EventListener listener;
        
        public EventListenerWrapper(final EventListener listener) {
            this.listener = listener;
        }
        
        public void handleEvent(final Event event) {
            this.listener.handleEvent(EventSupport.getUltimateOriginalEvent(event));
        }
        
        public String toString() {
            return super.toString() + " [wrapping " + this.listener.toString() + "]";
        }
    }
    
    protected static class ImplementationEventListenerMememto extends EventListenerMememto
    {
        public ImplementationEventListenerMememto(final EventTarget eventTarget, final String s, final EventListener eventListener, final boolean b, final BridgeContext bridgeContext) {
            super(eventTarget, s, eventListener, b, bridgeContext);
        }
        
        public ImplementationEventListenerMememto(final EventTarget eventTarget, final String s, final String s2, final EventListener eventListener, final boolean b, final BridgeContext bridgeContext) {
            super(eventTarget, s, s2, eventListener, b, bridgeContext);
        }
    }
}
