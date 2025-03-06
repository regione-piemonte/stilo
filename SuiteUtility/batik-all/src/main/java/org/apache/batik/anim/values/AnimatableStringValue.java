// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableStringValue extends AnimatableValue
{
    protected String string;
    
    protected AnimatableStringValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableStringValue(final AnimationTarget animationTarget, final String string) {
        super(animationTarget);
        this.string = string;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableStringValue animatableStringValue;
        if (animatableValue == null) {
            animatableStringValue = new AnimatableStringValue(this.target);
        }
        else {
            animatableStringValue = (AnimatableStringValue)animatableValue;
        }
        String s;
        if (animatableValue2 != null && n >= 0.5) {
            s = ((AnimatableStringValue)animatableValue2).string;
        }
        else {
            s = this.string;
        }
        if (animatableStringValue.string == null || !animatableStringValue.string.equals(s)) {
            animatableStringValue.string = s;
            animatableStringValue.hasChanged = true;
        }
        return animatableStringValue;
    }
    
    public String getString() {
        return this.string;
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableStringValue(this.target, "");
    }
    
    public String getCssText() {
        return this.string;
    }
}
