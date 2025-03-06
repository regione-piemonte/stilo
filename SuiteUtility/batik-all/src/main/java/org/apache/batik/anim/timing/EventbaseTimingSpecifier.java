// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

import org.w3c.dom.events.Event;
import org.apache.batik.dom.events.NodeEventTarget;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.EventListener;

public class EventbaseTimingSpecifier extends EventLikeTimingSpecifier implements EventListener
{
    protected String eventbaseID;
    protected TimedElement eventbase;
    protected EventTarget eventTarget;
    protected String eventNamespaceURI;
    protected String eventType;
    protected String eventName;
    
    public EventbaseTimingSpecifier(final TimedElement timedElement, final boolean b, final float n, final String eventbaseID, final String eventName) {
        super(timedElement, b, n);
        this.eventbaseID = eventbaseID;
        this.eventName = eventName;
        final TimedDocumentRoot root = timedElement.getRoot();
        this.eventNamespaceURI = root.getEventNamespaceURI(eventName);
        this.eventType = root.getEventType(eventName);
        if (eventbaseID == null) {
            this.eventTarget = timedElement.getAnimationEventTarget();
        }
        else {
            this.eventTarget = timedElement.getEventTargetById(eventbaseID);
        }
    }
    
    public String toString() {
        return ((this.eventbaseID == null) ? "" : (this.eventbaseID + ".")) + this.eventName + ((this.offset != 0.0f) ? super.toString() : "");
    }
    
    public void initialize() {
        ((NodeEventTarget)this.eventTarget).addEventListenerNS(this.eventNamespaceURI, this.eventType, this, false, null);
    }
    
    public void deinitialize() {
        ((NodeEventTarget)this.eventTarget).removeEventListenerNS(this.eventNamespaceURI, this.eventType, this, false);
    }
    
    public void handleEvent(final Event event) {
        this.owner.eventOccurred(this, event);
    }
    
    public void resolve(final Event event) {
        this.owner.addInstanceTime(new InstanceTime(this, this.owner.getRoot().convertEpochTime(event.getTimeStamp()) + this.offset, true), this.isBegin);
    }
}
