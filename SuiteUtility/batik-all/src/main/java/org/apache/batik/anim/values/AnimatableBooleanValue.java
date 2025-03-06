// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableBooleanValue extends AnimatableValue
{
    protected boolean value;
    
    protected AnimatableBooleanValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableBooleanValue(final AnimationTarget animationTarget, final boolean value) {
        super(animationTarget);
        this.value = value;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableBooleanValue animatableBooleanValue;
        if (animatableValue == null) {
            animatableBooleanValue = new AnimatableBooleanValue(this.target);
        }
        else {
            animatableBooleanValue = (AnimatableBooleanValue)animatableValue;
        }
        boolean value;
        if (animatableValue2 != null && n >= 0.5) {
            value = ((AnimatableBooleanValue)animatableValue2).value;
        }
        else {
            value = this.value;
        }
        if (animatableBooleanValue.value != value) {
            animatableBooleanValue.value = value;
            animatableBooleanValue.hasChanged = true;
        }
        return animatableBooleanValue;
    }
    
    public boolean getValue() {
        return this.value;
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableBooleanValue(this.target, false);
    }
    
    public String getCssText() {
        return this.value ? "true" : "false";
    }
}
