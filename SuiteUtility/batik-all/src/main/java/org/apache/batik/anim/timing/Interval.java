// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

import java.util.Iterator;
import java.util.LinkedList;

public class Interval
{
    protected float begin;
    protected float end;
    protected InstanceTime beginInstanceTime;
    protected InstanceTime endInstanceTime;
    protected LinkedList beginDependents;
    protected LinkedList endDependents;
    
    public Interval(final float begin, final float end, final InstanceTime beginInstanceTime, final InstanceTime endInstanceTime) {
        this.beginDependents = new LinkedList();
        this.endDependents = new LinkedList();
        this.begin = begin;
        this.end = end;
        this.beginInstanceTime = beginInstanceTime;
        this.endInstanceTime = endInstanceTime;
    }
    
    public String toString() {
        return TimedElement.toString(this.begin) + ".." + TimedElement.toString(this.end);
    }
    
    public float getBegin() {
        return this.begin;
    }
    
    public float getEnd() {
        return this.end;
    }
    
    public InstanceTime getBeginInstanceTime() {
        return this.beginInstanceTime;
    }
    
    public InstanceTime getEndInstanceTime() {
        return this.endInstanceTime;
    }
    
    void addDependent(final InstanceTime instanceTime, final boolean b) {
        if (b) {
            this.beginDependents.add(instanceTime);
        }
        else {
            this.endDependents.add(instanceTime);
        }
    }
    
    void removeDependent(final InstanceTime instanceTime, final boolean b) {
        if (b) {
            this.beginDependents.remove(instanceTime);
        }
        else {
            this.endDependents.remove(instanceTime);
        }
    }
    
    float setBegin(final float begin) {
        float n = Float.POSITIVE_INFINITY;
        this.begin = begin;
        final Iterator iterator = this.beginDependents.iterator();
        while (iterator.hasNext()) {
            final float dependentUpdate = iterator.next().dependentUpdate(begin);
            if (dependentUpdate < n) {
                n = dependentUpdate;
            }
        }
        return n;
    }
    
    float setEnd(final float end, final InstanceTime endInstanceTime) {
        float n = Float.POSITIVE_INFINITY;
        this.end = end;
        this.endInstanceTime = endInstanceTime;
        final Iterator iterator = this.endDependents.iterator();
        while (iterator.hasNext()) {
            final float dependentUpdate = iterator.next().dependentUpdate(end);
            if (dependentUpdate < n) {
                n = dependentUpdate;
            }
        }
        return n;
    }
}
