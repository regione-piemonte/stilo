// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.apache.batik.dom.events.EventSupport;
import org.w3c.dom.events.Event;
import org.w3c.dom.Node;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.Element;
import org.w3c.dom.events.DocumentEvent;
import org.apache.batik.dom.events.DOMUIEvent;
import org.w3c.dom.events.EventTarget;
import org.apache.batik.dom.AbstractNode;
import org.apache.batik.dom.svg12.XBLEventSupport;
import org.w3c.dom.Document;
import org.apache.batik.bridge.FocusManager;

public class SVG12FocusManager extends FocusManager
{
    public SVG12FocusManager(final Document document) {
        super(document);
    }
    
    protected void addEventListeners(final Document document) {
        final XBLEventSupport xblEventSupport = (XBLEventSupport)((AbstractNode)document).initializeEventSupport();
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "click", this.mouseclickListener = new MouseClickTracker(), true);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", this.mouseoverListener = new MouseOverTracker(), true);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", this.mouseoutListener = new MouseOutTracker(), true);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMFocusIn", this.domFocusInListener = new DOMFocusInTracker(), true);
        xblEventSupport.addImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMFocusOut", this.domFocusOutListener = new DOMFocusOutTracker(), true);
    }
    
    protected void removeEventListeners(final Document document) {
        final XBLEventSupport xblEventSupport = (XBLEventSupport)((AbstractNode)document).getEventSupport();
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "click", this.mouseclickListener, true);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", this.mouseoverListener, true);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", this.mouseoutListener, true);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMFocusIn", this.domFocusInListener, true);
        xblEventSupport.removeImplementationEventListenerNS("http://www.w3.org/2001/xml-events", "DOMFocusOut", this.domFocusOutListener, true);
    }
    
    protected void fireDOMFocusInEvent(final EventTarget eventTarget, final EventTarget eventTarget2) {
        final DOMUIEvent domuiEvent = (DOMUIEvent)((DocumentEvent)((Element)eventTarget).getOwnerDocument()).createEvent("UIEvents");
        domuiEvent.initUIEventNS("http://www.w3.org/2001/xml-events", "DOMFocusIn", true, false, null, 0);
        domuiEvent.setBubbleLimit(DefaultXBLManager.computeBubbleLimit((Node)eventTarget2, (Node)eventTarget));
        eventTarget.dispatchEvent(domuiEvent);
    }
    
    protected void fireDOMFocusOutEvent(final EventTarget eventTarget, final EventTarget eventTarget2) {
        final DOMUIEvent domuiEvent = (DOMUIEvent)((DocumentEvent)((Element)eventTarget).getOwnerDocument()).createEvent("UIEvents");
        domuiEvent.initUIEventNS("http://www.w3.org/2001/xml-events", "DOMFocusOut", true, false, null, 0);
        domuiEvent.setBubbleLimit(DefaultXBLManager.computeBubbleLimit((Node)eventTarget, (Node)eventTarget2));
        eventTarget.dispatchEvent(domuiEvent);
    }
    
    protected class MouseOutTracker extends FocusManager.MouseOutTracker
    {
        public void handleEvent(final Event event) {
            super.handleEvent(EventSupport.getUltimateOriginalEvent(event));
        }
    }
    
    protected class MouseOverTracker extends FocusManager.MouseOverTracker
    {
        public void handleEvent(final Event event) {
            super.handleEvent(EventSupport.getUltimateOriginalEvent(event));
        }
    }
    
    protected class DOMFocusInTracker extends FocusManager.DOMFocusInTracker
    {
        public void handleEvent(final Event event) {
            super.handleEvent(EventSupport.getUltimateOriginalEvent(event));
        }
    }
    
    protected class MouseClickTracker extends FocusManager.MouseClickTracker
    {
        public void handleEvent(final Event event) {
            super.handleEvent(EventSupport.getUltimateOriginalEvent(event));
        }
    }
}
