// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.apache.batik.dom.events.EventSupport;
import org.w3c.dom.events.Event;
import org.w3c.dom.Element;
import org.w3c.dom.events.MutationEvent;
import org.apache.batik.dom.xbl.NodeXBL;
import org.w3c.dom.Node;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.EventListener;
import org.apache.batik.dom.AbstractNode;
import org.apache.batik.dom.svg12.XBLEventSupport;
import org.apache.batik.dom.events.NodeEventTarget;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.Bridge;
import org.apache.batik.bridge.SVGTextElementBridge;

public class SVG12TextElementBridge extends SVGTextElementBridge implements SVG12BridgeUpdateHandler
{
    public Bridge getInstance() {
        return new SVG12TextElementBridge();
    }
    
    protected void addTextEventListeners(final BridgeContext bridgeContext, final NodeEventTarget nodeEventTarget) {
        if (this.childNodeRemovedEventListener == null) {
            this.childNodeRemovedEventListener = new DOMChildNodeRemovedEventListener();
        }
        if (this.subtreeModifiedEventListener == null) {
            this.subtreeModifiedEventListener = new DOMSubtreeModifiedEventListener();
        }
        final SVG12BridgeContext svg12BridgeContext = (SVG12BridgeContext)bridgeContext;
        final XBLEventSupport xblEventSupport = (XBLEventSupport)((AbstractNode)nodeEventTarget).initializeEventSupport();
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.childNodeRemovedEventListener, true);
        svg12BridgeContext.storeImplementationEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.childNodeRemovedEventListener, true);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.subtreeModifiedEventListener, false);
        svg12BridgeContext.storeImplementationEventListenerNS(nodeEventTarget, "http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.subtreeModifiedEventListener, false);
    }
    
    protected void removeTextEventListeners(final BridgeContext bridgeContext, final NodeEventTarget nodeEventTarget) {
        final XBLEventSupport xblEventSupport = (XBLEventSupport)((AbstractNode)nodeEventTarget).initializeEventSupport();
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMNodeRemoved", this.childNodeRemovedEventListener, true);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMSubtreeModified", this.subtreeModifiedEventListener, false);
    }
    
    protected Node getFirstChild(final Node node) {
        return ((NodeXBL)node).getXblFirstChild();
    }
    
    protected Node getNextSibling(final Node node) {
        return ((NodeXBL)node).getXblNextSibling();
    }
    
    protected Node getParentNode(final Node node) {
        return ((NodeXBL)node).getXblParentNode();
    }
    
    public void handleDOMCharacterDataModified(final MutationEvent mutationEvent) {
        final Node node = (Node)mutationEvent.getTarget();
        if (this.isParentDisplayed(node)) {
            if (this.getParentNode(node) != node.getParentNode()) {
                this.computeLaidoutText(this.ctx, this.e, this.node);
            }
            else {
                this.laidoutText = null;
            }
        }
    }
    
    public void handleBindingEvent(final Element element, final Element element2) {
    }
    
    public void handleContentSelectionChangedEvent(final ContentSelectionChangedEvent contentSelectionChangedEvent) {
        this.computeLaidoutText(this.ctx, this.e, this.node);
    }
    
    protected class DOMSubtreeModifiedEventListener extends SVGTextElementBridge.DOMSubtreeModifiedEventListener
    {
        public void handleEvent(final Event event) {
            super.handleEvent(EventSupport.getUltimateOriginalEvent(event));
        }
    }
    
    protected class DOMChildNodeRemovedEventListener extends SVGTextElementBridge.DOMChildNodeRemovedEventListener
    {
        public void handleEvent(final Event event) {
            super.handleEvent(EventSupport.getUltimateOriginalEvent(event));
        }
    }
}
