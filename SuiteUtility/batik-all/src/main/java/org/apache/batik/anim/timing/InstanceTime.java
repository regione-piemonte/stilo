// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

public class InstanceTime implements Comparable
{
    protected float time;
    protected TimingSpecifier creator;
    protected boolean clearOnReset;
    
    public InstanceTime(final TimingSpecifier creator, final float time, final boolean clearOnReset) {
        this.creator = creator;
        this.time = time;
        this.clearOnReset = clearOnReset;
    }
    
    public boolean getClearOnReset() {
        return this.clearOnReset;
    }
    
    public float getTime() {
        return this.time;
    }
    
    float dependentUpdate(final float time) {
        this.time = time;
        if (this.creator != null) {
            return this.creator.handleTimebaseUpdate(this, this.time);
        }
        return Float.POSITIVE_INFINITY;
    }
    
    public String toString() {
        return Float.toString(this.time);
    }
    
    public int compareTo(final Object o) {
        final InstanceTime instanceTime = (InstanceTime)o;
        if (this.time == instanceTime.time) {
            return 0;
        }
        if (this.time > instanceTime.time) {
            return 1;
        }
        return -1;
    }
}
