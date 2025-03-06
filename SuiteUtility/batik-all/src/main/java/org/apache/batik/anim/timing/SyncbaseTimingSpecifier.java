// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

import java.util.HashMap;

public class SyncbaseTimingSpecifier extends OffsetTimingSpecifier
{
    protected String syncbaseID;
    protected TimedElement syncbaseElement;
    protected boolean syncBegin;
    protected HashMap instances;
    
    public SyncbaseTimingSpecifier(final TimedElement timedElement, final boolean b, final float n, final String syncbaseID, final boolean syncBegin) {
        super(timedElement, b, n);
        this.instances = new HashMap();
        this.syncbaseID = syncbaseID;
        this.syncBegin = syncBegin;
        (this.syncbaseElement = timedElement.getTimedElementById(syncbaseID)).addDependent(this, syncBegin);
    }
    
    public String toString() {
        return this.syncbaseID + "." + (this.syncBegin ? "begin" : "end") + ((this.offset != 0.0f) ? super.toString() : "");
    }
    
    public void initialize() {
    }
    
    public boolean isEventCondition() {
        return false;
    }
    
    float newInterval(final Interval key) {
        if (this.owner.hasPropagated) {
            return Float.POSITIVE_INFINITY;
        }
        final InstanceTime value = new InstanceTime(this, (this.syncBegin ? key.getBegin() : key.getEnd()) + this.offset, true);
        this.instances.put(key, value);
        key.addDependent(value, this.syncBegin);
        return this.owner.addInstanceTime(value, this.isBegin);
    }
    
    float removeInterval(final Interval key) {
        if (this.owner.hasPropagated) {
            return Float.POSITIVE_INFINITY;
        }
        final InstanceTime instanceTime = this.instances.get(key);
        key.removeDependent(instanceTime, this.syncBegin);
        return this.owner.removeInstanceTime(instanceTime, this.isBegin);
    }
    
    float handleTimebaseUpdate(final InstanceTime instanceTime, final float n) {
        if (this.owner.hasPropagated) {
            return Float.POSITIVE_INFINITY;
        }
        return this.owner.instanceTimeChanged(instanceTime, this.isBegin);
    }
}
