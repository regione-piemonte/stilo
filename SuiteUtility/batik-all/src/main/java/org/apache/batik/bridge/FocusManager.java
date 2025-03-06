// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.events.Event;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.Element;
import org.w3c.dom.events.DocumentEvent;
import org.apache.batik.dom.events.DOMUIEvent;
import org.apache.batik.dom.events.NodeEventTarget;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.Document;
import org.w3c.dom.events.EventTarget;

public class FocusManager
{
    protected EventTarget lastFocusEventTarget;
    protected Document document;
    protected EventListener mouseclickListener;
    protected EventListener domFocusInListener;
    protected EventListener domFocusOutListener;
    protected EventListener mouseoverListener;
    protected EventListener mouseoutListener;
    
    public FocusManager(final Document document) {
        this.addEventListeners(this.document = document);
    }
    
    protected void addEventListeners(final Document document) {
        final NodeEventTarget nodeEventTarget = (NodeEventTarget)document;
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "click", this.mouseclickListener = new MouseClickTracker(), true, null);
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", this.mouseoverListener = new MouseOverTracker(), true, null);
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", this.mouseoutListener = new MouseOutTracker(), true, null);
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMFocusIn", this.domFocusInListener = new DOMFocusInTracker(), true, null);
        nodeEventTarget.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMFocusOut", this.domFocusOutListener = new DOMFocusOutTracker(), true, null);
    }
    
    protected void removeEventListeners(final Document document) {
        final NodeEventTarget nodeEventTarget = (NodeEventTarget)document;
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "click", this.mouseclickListener, true);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseover", this.mouseoverListener, true);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "mouseout", this.mouseoutListener, true);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMFocusIn", this.domFocusInListener, true);
        nodeEventTarget.removeEventListenerNS("http://www.w3.org/2001/xml-events", "DOMFocusOut", this.domFocusOutListener, true);
    }
    
    public EventTarget getCurrentEventTarget() {
        return this.lastFocusEventTarget;
    }
    
    public void dispose() {
        if (this.document == null) {
            return;
        }
        this.removeEventListeners(this.document);
        this.lastFocusEventTarget = null;
        this.document = null;
    }
    
    protected void fireDOMFocusInEvent(final EventTarget eventTarget, final EventTarget eventTarget2) {
        final DOMUIEvent domuiEvent = (DOMUIEvent)((DocumentEvent)((Element)eventTarget).getOwnerDocument()).createEvent("UIEvents");
        domuiEvent.initUIEventNS("http://www.w3.org/2001/xml-events", "DOMFocusIn", true, false, null, 0);
        eventTarget.dispatchEvent(domuiEvent);
    }
    
    protected void fireDOMFocusOutEvent(final EventTarget eventTarget, final EventTarget eventTarget2) {
        final DOMUIEvent domuiEvent = (DOMUIEvent)((DocumentEvent)((Element)eventTarget).getOwnerDocument()).createEvent("UIEvents");
        domuiEvent.initUIEventNS("http://www.w3.org/2001/xml-events", "DOMFocusOut", true, false, null, 0);
        eventTarget.dispatchEvent(domuiEvent);
    }
    
    protected void fireDOMActivateEvent(final EventTarget eventTarget, final int n) {
        final DOMUIEvent domuiEvent = (DOMUIEvent)((DocumentEvent)((Element)eventTarget).getOwnerDocument()).createEvent("UIEvents");
        domuiEvent.initUIEventNS("http://www.w3.org/2001/xml-events", "DOMActivate", true, true, null, 0);
        eventTarget.dispatchEvent(domuiEvent);
    }
    
    protected class MouseOutTracker implements EventListener
    {
        public void handleEvent(final Event event) {
            FocusManager.this.fireDOMFocusOutEvent(event.getTarget(), ((MouseEvent)event).getRelatedTarget());
        }
    }
    
    protected class MouseOverTracker implements EventListener
    {
        public void handleEvent(final Event event) {
            FocusManager.this.fireDOMFocusInEvent(event.getTarget(), ((MouseEvent)event).getRelatedTarget());
        }
    }
    
    protected class DOMFocusOutTracker implements EventListener
    {
        public DOMFocusOutTracker() {
        }
        
        public void handleEvent(final Event event) {
            FocusManager.this.lastFocusEventTarget = null;
        }
    }
    
    protected class DOMFocusInTracker implements EventListener
    {
        public void handleEvent(final Event event) {
            final EventTarget target = event.getTarget();
            if (FocusManager.this.lastFocusEventTarget != null && FocusManager.this.lastFocusEventTarget != target) {
                FocusManager.this.fireDOMFocusOutEvent(FocusManager.this.lastFocusEventTarget, target);
            }
            FocusManager.this.lastFocusEventTarget = event.getTarget();
        }
    }
    
    protected class MouseClickTracker implements EventListener
    {
        public void handleEvent(final Event event) {
            FocusManager.this.fireDOMActivateEvent(event.getTarget(), ((MouseEvent)event).getDetail());
        }
    }
}
