// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableNumberValue extends AnimatableValue
{
    protected float value;
    
    protected AnimatableNumberValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableNumberValue(final AnimationTarget animationTarget, final float value) {
        super(animationTarget);
        this.value = value;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableNumberValue animatableNumberValue;
        if (animatableValue == null) {
            animatableNumberValue = new AnimatableNumberValue(this.target);
        }
        else {
            animatableNumberValue = (AnimatableNumberValue)animatableValue;
        }
        float value = this.value;
        if (animatableValue2 != null) {
            value += n * (((AnimatableNumberValue)animatableValue2).value - this.value);
        }
        if (animatableValue3 != null) {
            value += n2 * ((AnimatableNumberValue)animatableValue3).value;
        }
        if (animatableNumberValue.value != value) {
            animatableNumberValue.value = value;
            animatableNumberValue.hasChanged = true;
        }
        return animatableNumberValue;
    }
    
    public float getValue() {
        return this.value;
    }
    
    public boolean canPace() {
        return true;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return Math.abs(this.value - ((AnimatableNumberValue)animatableValue).value);
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableNumberValue(this.target, 0.0f);
    }
    
    public String getCssText() {
        return AnimatableValue.formatNumber(this.value);
    }
}
