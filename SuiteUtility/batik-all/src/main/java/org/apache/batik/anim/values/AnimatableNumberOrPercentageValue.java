// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableNumberOrPercentageValue extends AnimatableNumberValue
{
    protected boolean isPercentage;
    
    protected AnimatableNumberOrPercentageValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableNumberOrPercentageValue(final AnimationTarget animationTarget, final float n) {
        super(animationTarget, n);
    }
    
    public AnimatableNumberOrPercentageValue(final AnimationTarget animationTarget, final float n, final boolean isPercentage) {
        super(animationTarget, n);
        this.isPercentage = isPercentage;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableNumberOrPercentageValue animatableNumberOrPercentageValue;
        if (animatableValue == null) {
            animatableNumberOrPercentageValue = new AnimatableNumberOrPercentageValue(this.target);
        }
        else {
            animatableNumberOrPercentageValue = (AnimatableNumberOrPercentageValue)animatableValue;
        }
        final AnimatableNumberOrPercentageValue animatableNumberOrPercentageValue2 = (AnimatableNumberOrPercentageValue)animatableValue2;
        final AnimatableNumberOrPercentageValue animatableNumberOrPercentageValue3 = (AnimatableNumberOrPercentageValue)animatableValue3;
        float value;
        boolean isPercentage;
        if (animatableValue2 != null) {
            if (animatableNumberOrPercentageValue2.isPercentage == this.isPercentage) {
                value = this.value + n * (animatableNumberOrPercentageValue2.value - this.value);
                isPercentage = this.isPercentage;
            }
            else if (n >= 0.5) {
                value = animatableNumberOrPercentageValue2.value;
                isPercentage = animatableNumberOrPercentageValue2.isPercentage;
            }
            else {
                value = this.value;
                isPercentage = this.isPercentage;
            }
        }
        else {
            value = this.value;
            isPercentage = this.isPercentage;
        }
        if (animatableValue3 != null && animatableNumberOrPercentageValue3.isPercentage == isPercentage) {
            value += n2 * animatableNumberOrPercentageValue3.value;
        }
        if (animatableNumberOrPercentageValue.value != value || animatableNumberOrPercentageValue.isPercentage != isPercentage) {
            animatableNumberOrPercentageValue.value = value;
            animatableNumberOrPercentageValue.isPercentage = isPercentage;
            animatableNumberOrPercentageValue.hasChanged = true;
        }
        return animatableNumberOrPercentageValue;
    }
    
    public boolean isPercentage() {
        return this.isPercentage;
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableNumberOrPercentageValue(this.target, 0.0f, this.isPercentage);
    }
    
    public String getCssText() {
        final StringBuffer sb = new StringBuffer();
        sb.append(AnimatableValue.formatNumber(this.value));
        if (this.isPercentage) {
            sb.append('%');
        }
        return sb.toString();
    }
}
