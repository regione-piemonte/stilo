// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableIntegerValue extends AnimatableValue
{
    protected int value;
    
    protected AnimatableIntegerValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableIntegerValue(final AnimationTarget animationTarget, final int value) {
        super(animationTarget);
        this.value = value;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableIntegerValue animatableIntegerValue;
        if (animatableValue == null) {
            animatableIntegerValue = new AnimatableIntegerValue(this.target);
        }
        else {
            animatableIntegerValue = (AnimatableIntegerValue)animatableValue;
        }
        int value = this.value;
        if (animatableValue2 != null) {
            value += (int)(this.value + n * (((AnimatableIntegerValue)animatableValue2).getValue() - this.value));
        }
        if (animatableValue3 != null) {
            value += n2 * ((AnimatableIntegerValue)animatableValue3).getValue();
        }
        if (animatableIntegerValue.value != value) {
            animatableIntegerValue.value = value;
            animatableIntegerValue.hasChanged = true;
        }
        return animatableIntegerValue;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public boolean canPace() {
        return true;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return (float)Math.abs(this.value - ((AnimatableIntegerValue)animatableValue).value);
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableIntegerValue(this.target, 0);
    }
    
    public String getCssText() {
        return Integer.toString(this.value);
    }
}
