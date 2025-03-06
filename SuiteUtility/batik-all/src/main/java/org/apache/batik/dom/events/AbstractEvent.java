// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.events;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.events.EventTarget;
import org.apache.batik.dom.xbl.OriginalEvent;
import org.w3c.dom.events.Event;

public abstract class AbstractEvent implements Event, OriginalEvent, Cloneable
{
    protected String type;
    protected boolean isBubbling;
    protected boolean cancelable;
    protected EventTarget currentTarget;
    protected EventTarget target;
    protected short eventPhase;
    protected long timeStamp;
    protected boolean stopPropagation;
    protected boolean stopImmediatePropagation;
    protected boolean preventDefault;
    protected String namespaceURI;
    protected Event originalEvent;
    protected List defaultActions;
    protected int bubbleLimit;
    
    public AbstractEvent() {
        this.timeStamp = System.currentTimeMillis();
        this.stopPropagation = false;
        this.stopImmediatePropagation = false;
        this.preventDefault = false;
        this.bubbleLimit = 0;
    }
    
    public String getType() {
        return this.type;
    }
    
    public EventTarget getCurrentTarget() {
        return this.currentTarget;
    }
    
    public EventTarget getTarget() {
        return this.target;
    }
    
    public short getEventPhase() {
        return this.eventPhase;
    }
    
    public boolean getBubbles() {
        return this.isBubbling;
    }
    
    public boolean getCancelable() {
        return this.cancelable;
    }
    
    public long getTimeStamp() {
        return this.timeStamp;
    }
    
    public String getNamespaceURI() {
        return this.namespaceURI;
    }
    
    public Event getOriginalEvent() {
        return this.originalEvent;
    }
    
    public void stopPropagation() {
        this.stopPropagation = true;
    }
    
    public void preventDefault() {
        this.preventDefault = true;
    }
    
    public boolean getDefaultPrevented() {
        return this.preventDefault;
    }
    
    public List getDefaultActions() {
        return this.defaultActions;
    }
    
    public void addDefaultAction(final Runnable runnable) {
        if (this.defaultActions == null) {
            this.defaultActions = new ArrayList();
        }
        this.defaultActions.add(runnable);
    }
    
    public void stopImmediatePropagation() {
        this.stopImmediatePropagation = true;
    }
    
    public void initEvent(final String type, final boolean isBubbling, final boolean cancelable) {
        this.type = type;
        this.isBubbling = isBubbling;
        this.cancelable = cancelable;
    }
    
    public void initEventNS(final String namespaceURI, final String type, final boolean isBubbling, final boolean cancelable) {
        if (this.namespaceURI != null && this.namespaceURI.length() == 0) {
            this.namespaceURI = null;
        }
        this.namespaceURI = namespaceURI;
        this.type = type;
        this.isBubbling = isBubbling;
        this.cancelable = cancelable;
    }
    
    boolean getStopPropagation() {
        return this.stopPropagation;
    }
    
    boolean getStopImmediatePropagation() {
        return this.stopImmediatePropagation;
    }
    
    void setEventPhase(final short eventPhase) {
        this.eventPhase = eventPhase;
    }
    
    void stopPropagation(final boolean stopPropagation) {
        this.stopPropagation = stopPropagation;
    }
    
    void stopImmediatePropagation(final boolean stopImmediatePropagation) {
        this.stopImmediatePropagation = stopImmediatePropagation;
    }
    
    void preventDefault(final boolean preventDefault) {
        this.preventDefault = preventDefault;
    }
    
    void setCurrentTarget(final EventTarget currentTarget) {
        this.currentTarget = currentTarget;
    }
    
    void setTarget(final EventTarget target) {
        this.target = target;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final AbstractEvent abstractEvent = (AbstractEvent)super.clone();
        abstractEvent.timeStamp = System.currentTimeMillis();
        return abstractEvent;
    }
    
    public AbstractEvent cloneEvent() {
        try {
            final AbstractEvent abstractEvent = (AbstractEvent)this.clone();
            abstractEvent.originalEvent = this;
            return abstractEvent;
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public int getBubbleLimit() {
        return this.bubbleLimit;
    }
    
    public void setBubbleLimit(final int bubbleLimit) {
        this.bubbleLimit = bubbleLimit;
    }
}
