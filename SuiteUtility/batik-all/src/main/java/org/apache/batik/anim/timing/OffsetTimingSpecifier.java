// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

public class OffsetTimingSpecifier extends TimingSpecifier
{
    protected float offset;
    
    public OffsetTimingSpecifier(final TimedElement timedElement, final boolean b, final float offset) {
        super(timedElement, b);
        this.offset = offset;
    }
    
    public String toString() {
        return ((this.offset >= 0.0f) ? "+" : "") + this.offset;
    }
    
    public void initialize() {
        this.owner.addInstanceTime(new InstanceTime(this, this.offset, false), this.isBegin);
    }
    
    public boolean isEventCondition() {
        return false;
    }
}
