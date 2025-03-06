// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

import java.util.Iterator;
import java.util.LinkedList;
import org.apache.batik.util.DoublyIndexedSet;
import java.util.Calendar;

public abstract class TimedDocumentRoot extends TimeContainer
{
    protected Calendar documentBeginTime;
    protected boolean useSVG11AccessKeys;
    protected boolean useSVG12AccessKeys;
    protected DoublyIndexedSet propagationFlags;
    protected LinkedList listeners;
    protected boolean isSampling;
    protected boolean isHyperlinking;
    
    public TimedDocumentRoot(final boolean useSVG11AccessKeys, final boolean useSVG12AccessKeys) {
        this.propagationFlags = new DoublyIndexedSet();
        this.listeners = new LinkedList();
        this.root = this;
        this.useSVG11AccessKeys = useSVG11AccessKeys;
        this.useSVG12AccessKeys = useSVG12AccessKeys;
    }
    
    protected float getImplicitDur() {
        return Float.POSITIVE_INFINITY;
    }
    
    public float getDefaultBegin(final TimedElement timedElement) {
        return 0.0f;
    }
    
    public float getCurrentTime() {
        return this.lastSampleTime;
    }
    
    public boolean isSampling() {
        return this.isSampling;
    }
    
    public boolean isHyperlinking() {
        return this.isHyperlinking;
    }
    
    public float seekTo(final float lastSampleTime, final boolean isHyperlinking) {
        this.isSampling = true;
        this.lastSampleTime = lastSampleTime;
        this.isHyperlinking = isHyperlinking;
        this.propagationFlags.clear();
        float n = Float.POSITIVE_INFINITY;
        final TimedElement[] children = this.getChildren();
        for (int i = 0; i < children.length; ++i) {
            final float sample = children[i].sampleAt(lastSampleTime, isHyperlinking);
            if (sample < n) {
                n = sample;
            }
        }
        boolean b;
        do {
            b = false;
            for (int j = 0; j < children.length; ++j) {
                if (children[j].shouldUpdateCurrentInterval) {
                    b = true;
                    final float sample2 = children[j].sampleAt(lastSampleTime, isHyperlinking);
                    if (sample2 < n) {
                        n = sample2;
                    }
                }
            }
        } while (b);
        this.isSampling = false;
        if (isHyperlinking) {
            this.root.currentIntervalWillUpdate();
        }
        return n;
    }
    
    public void resetDocument(final Calendar documentBeginTime) {
        if (documentBeginTime == null) {
            this.documentBeginTime = Calendar.getInstance();
        }
        else {
            this.documentBeginTime = documentBeginTime;
        }
        this.reset(true);
    }
    
    public Calendar getDocumentBeginTime() {
        return this.documentBeginTime;
    }
    
    public float convertEpochTime(final long n) {
        return (n - this.documentBeginTime.getTime().getTime()) / 1000.0f;
    }
    
    public float convertWallclockTime(final Calendar calendar) {
        return (calendar.getTime().getTime() - this.documentBeginTime.getTime().getTime()) / 1000.0f;
    }
    
    public void addTimegraphListener(final TimegraphListener e) {
        this.listeners.add(e);
    }
    
    public void removeTimegraphListener(final TimegraphListener o) {
        this.listeners.remove(o);
    }
    
    void fireElementAdded(final TimedElement timedElement) {
        final Iterator iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().elementAdded(timedElement);
        }
    }
    
    void fireElementRemoved(final TimedElement timedElement) {
        final Iterator iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().elementRemoved(timedElement);
        }
    }
    
    boolean shouldPropagate(final Interval interval, final TimingSpecifier timingSpecifier, final boolean b) {
        final InstanceTime instanceTime = b ? interval.getBeginInstanceTime() : interval.getEndInstanceTime();
        if (this.propagationFlags.contains(instanceTime, timingSpecifier)) {
            return false;
        }
        this.propagationFlags.add(instanceTime, timingSpecifier);
        return true;
    }
    
    protected void currentIntervalWillUpdate() {
    }
    
    protected abstract String getEventNamespaceURI(final String p0);
    
    protected abstract String getEventType(final String p0);
    
    protected abstract String getRepeatEventName();
}
