// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

import org.w3c.dom.events.KeyboardEvent;
import org.apache.batik.dom.events.DOMKeyEvent;
import org.w3c.dom.events.Event;
import org.apache.batik.dom.events.NodeEventTarget;
import org.w3c.dom.events.EventListener;

public class AccesskeyTimingSpecifier extends EventLikeTimingSpecifier implements EventListener
{
    protected char accesskey;
    protected boolean isSVG12AccessKey;
    protected String keyName;
    
    public AccesskeyTimingSpecifier(final TimedElement timedElement, final boolean b, final float n, final char accesskey) {
        super(timedElement, b, n);
        this.accesskey = accesskey;
    }
    
    public AccesskeyTimingSpecifier(final TimedElement timedElement, final boolean b, final float n, final String keyName) {
        super(timedElement, b, n);
        this.isSVG12AccessKey = true;
        this.keyName = keyName;
    }
    
    public String toString() {
        if (this.isSVG12AccessKey) {
            return "accessKey(" + this.keyName + ")" + ((this.offset != 0.0f) ? super.toString() : "");
        }
        return "accesskey(" + this.accesskey + ")" + ((this.offset != 0.0f) ? super.toString() : "");
    }
    
    public void initialize() {
        if (this.isSVG12AccessKey) {
            ((NodeEventTarget)this.owner.getRootEventTarget()).addEventListenerNS("http://www.w3.org/2001/xml-events", "keydown", this, false, null);
        }
        else {
            this.owner.getRootEventTarget().addEventListener("keypress", this, false);
        }
    }
    
    public void deinitialize() {
        if (this.isSVG12AccessKey) {
            ((NodeEventTarget)this.owner.getRootEventTarget()).removeEventListenerNS("http://www.w3.org/2001/xml-events", "keydown", this, false);
        }
        else {
            this.owner.getRootEventTarget().removeEventListener("keypress", this, false);
        }
    }
    
    public void handleEvent(final Event event) {
        boolean equals;
        if (event.getType().charAt(3) == 'p') {
            equals = (((DOMKeyEvent)event).getCharCode() == this.accesskey);
        }
        else {
            equals = ((KeyboardEvent)event).getKeyIdentifier().equals(this.keyName);
        }
        if (equals) {
            this.owner.eventOccurred(this, event);
        }
    }
    
    public void resolve(final Event event) {
        this.owner.addInstanceTime(new InstanceTime(this, this.owner.getRoot().convertEpochTime(event.getTimeStamp()) + this.offset, true), this.isBegin);
    }
}
