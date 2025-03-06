// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim;

import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimatableElement;
import org.apache.batik.anim.timing.TimedElement;

public abstract class AbstractAnimation
{
    public static final short CALC_MODE_DISCRETE = 0;
    public static final short CALC_MODE_LINEAR = 1;
    public static final short CALC_MODE_PACED = 2;
    public static final short CALC_MODE_SPLINE = 3;
    protected TimedElement timedElement;
    protected AnimatableElement animatableElement;
    protected AbstractAnimation lowerAnimation;
    protected AbstractAnimation higherAnimation;
    protected boolean isDirty;
    protected boolean isActive;
    protected boolean isFrozen;
    protected float beginTime;
    protected AnimatableValue value;
    protected AnimatableValue composedValue;
    protected boolean usesUnderlyingValue;
    protected boolean toAnimation;
    
    protected AbstractAnimation(final TimedElement timedElement, final AnimatableElement animatableElement) {
        this.timedElement = timedElement;
        this.animatableElement = animatableElement;
    }
    
    public TimedElement getTimedElement() {
        return this.timedElement;
    }
    
    public AnimatableValue getValue() {
        if (!this.isActive && !this.isFrozen) {
            return null;
        }
        return this.value;
    }
    
    public AnimatableValue getComposedValue() {
        if (!this.isActive && !this.isFrozen) {
            return null;
        }
        if (this.isDirty) {
            AnimatableValue animatableValue = null;
            if (!this.willReplace()) {
                if (this.lowerAnimation == null) {
                    animatableValue = this.animatableElement.getUnderlyingValue();
                    this.usesUnderlyingValue = true;
                }
                else {
                    animatableValue = this.lowerAnimation.getComposedValue();
                    this.usesUnderlyingValue = false;
                }
            }
            this.composedValue = this.value.interpolate(this.composedValue, null, 0.0f, animatableValue, 1);
            this.isDirty = false;
        }
        return this.composedValue;
    }
    
    public String toString() {
        return this.timedElement.toString();
    }
    
    public boolean usesUnderlyingValue() {
        return this.usesUnderlyingValue || this.toAnimation;
    }
    
    protected boolean willReplace() {
        return true;
    }
    
    protected void markDirty() {
        this.isDirty = true;
        if (this.higherAnimation != null && !this.higherAnimation.willReplace() && !this.higherAnimation.isDirty) {
            this.higherAnimation.markDirty();
        }
    }
    
    protected void sampledLastValue(final int n) {
    }
    
    protected abstract void sampledAt(final float p0, final float p1, final int p2);
}
