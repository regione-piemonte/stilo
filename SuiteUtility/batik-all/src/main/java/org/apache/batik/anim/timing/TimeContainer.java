// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class TimeContainer extends TimedElement
{
    protected List children;
    
    public TimeContainer() {
        this.children = new LinkedList();
    }
    
    public void addChild(final TimedElement timedElement) {
        if (timedElement == this) {
            throw new IllegalArgumentException("recursive datastructure not allowed here!");
        }
        this.children.add(timedElement);
        (timedElement.parent = this).setRoot(timedElement, this.root);
        this.root.fireElementAdded(timedElement);
        this.root.currentIntervalWillUpdate();
    }
    
    protected void setRoot(final TimedElement timedElement, final TimedDocumentRoot root) {
        timedElement.root = root;
        if (timedElement instanceof TimeContainer) {
            final Iterator<TimedElement> iterator = ((TimeContainer)timedElement).children.iterator();
            while (iterator.hasNext()) {
                this.setRoot(iterator.next(), root);
            }
        }
    }
    
    public void removeChild(final TimedElement timedElement) {
        this.children.remove(timedElement);
        timedElement.parent = null;
        this.setRoot(timedElement, null);
        this.root.fireElementRemoved(timedElement);
        this.root.currentIntervalWillUpdate();
    }
    
    public TimedElement[] getChildren() {
        return this.children.toArray(new TimedElement[0]);
    }
    
    protected float sampleAt(final float n, final boolean b) {
        super.sampleAt(n, b);
        return this.sampleChildren(n, b);
    }
    
    protected float sampleChildren(final float n, final boolean b) {
        float n2 = Float.POSITIVE_INFINITY;
        final Iterator<TimedElement> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            final float sample = iterator.next().sampleAt(n, b);
            if (sample < n2) {
                n2 = sample;
            }
        }
        return n2;
    }
    
    protected void reset(final boolean b) {
        super.reset(b);
        final Iterator<TimedElement> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            iterator.next().reset(b);
        }
    }
    
    protected boolean isConstantAnimation() {
        return false;
    }
    
    public abstract float getDefaultBegin(final TimedElement p0);
}
