// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

import org.w3c.dom.smil.TimeEvent;
import org.w3c.dom.events.Event;

public class RepeatTimingSpecifier extends EventbaseTimingSpecifier
{
    protected int repeatIteration;
    protected boolean repeatIterationSpecified;
    
    public RepeatTimingSpecifier(final TimedElement timedElement, final boolean b, final float n, final String s) {
        super(timedElement, b, n, s, timedElement.getRoot().getRepeatEventName());
    }
    
    public RepeatTimingSpecifier(final TimedElement timedElement, final boolean b, final float n, final String s, final int repeatIteration) {
        super(timedElement, b, n, s, timedElement.getRoot().getRepeatEventName());
        this.repeatIteration = repeatIteration;
        this.repeatIterationSpecified = true;
    }
    
    public String toString() {
        return ((this.eventbaseID == null) ? "" : (this.eventbaseID + ".")) + "repeat" + (this.repeatIterationSpecified ? ("(" + this.repeatIteration + ")") : "") + ((this.offset != 0.0f) ? super.toString() : "");
    }
    
    public void handleEvent(final Event event) {
        final TimeEvent timeEvent = (TimeEvent)event;
        if (!this.repeatIterationSpecified || timeEvent.getDetail() == this.repeatIteration) {
            super.handleEvent(event);
        }
    }
}
