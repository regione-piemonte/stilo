// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

import org.w3c.dom.events.Event;

public abstract class EventLikeTimingSpecifier extends OffsetTimingSpecifier
{
    public EventLikeTimingSpecifier(final TimedElement timedElement, final boolean b, final float n) {
        super(timedElement, b, n);
    }
    
    public boolean isEventCondition() {
        return true;
    }
    
    public abstract void resolve(final Event p0);
}
