// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

import java.util.Calendar;

public class WallclockTimingSpecifier extends TimingSpecifier
{
    protected Calendar time;
    protected InstanceTime instance;
    
    public WallclockTimingSpecifier(final TimedElement timedElement, final boolean b, final Calendar time) {
        super(timedElement, b);
        this.time = time;
    }
    
    public String toString() {
        return "wallclock(" + this.time.toString() + ")";
    }
    
    public void initialize() {
        this.instance = new InstanceTime(this, this.owner.getRoot().convertWallclockTime(this.time), false);
        this.owner.addInstanceTime(this.instance, this.isBegin);
    }
    
    public boolean isEventCondition() {
        return false;
    }
}
