// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

public class IndefiniteTimingSpecifier extends TimingSpecifier
{
    public IndefiniteTimingSpecifier(final TimedElement timedElement, final boolean b) {
        super(timedElement, b);
    }
    
    public String toString() {
        return "indefinite";
    }
    
    public void initialize() {
        if (!this.isBegin) {
            this.owner.addInstanceTime(new InstanceTime(this, Float.POSITIVE_INFINITY, false), this.isBegin);
        }
    }
    
    public boolean isEventCondition() {
        return false;
    }
}
