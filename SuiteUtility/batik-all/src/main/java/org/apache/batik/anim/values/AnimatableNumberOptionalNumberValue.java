// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableNumberOptionalNumberValue extends AnimatableValue
{
    protected float number;
    protected boolean hasOptionalNumber;
    protected float optionalNumber;
    
    protected AnimatableNumberOptionalNumberValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableNumberOptionalNumberValue(final AnimationTarget animationTarget, final float number) {
        super(animationTarget);
        this.number = number;
    }
    
    public AnimatableNumberOptionalNumberValue(final AnimationTarget animationTarget, final float number, final float optionalNumber) {
        super(animationTarget);
        this.number = number;
        this.optionalNumber = optionalNumber;
        this.hasOptionalNumber = true;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableNumberOptionalNumberValue animatableNumberOptionalNumberValue;
        if (animatableValue == null) {
            animatableNumberOptionalNumberValue = new AnimatableNumberOptionalNumberValue(this.target);
        }
        else {
            animatableNumberOptionalNumberValue = (AnimatableNumberOptionalNumberValue)animatableValue;
        }
        float n3;
        float n4;
        boolean b;
        if (animatableValue2 != null && n >= 0.5) {
            final AnimatableNumberOptionalNumberValue animatableNumberOptionalNumberValue2 = (AnimatableNumberOptionalNumberValue)animatableValue2;
            n3 = animatableNumberOptionalNumberValue2.number;
            n4 = animatableNumberOptionalNumberValue2.optionalNumber;
            b = animatableNumberOptionalNumberValue2.hasOptionalNumber;
        }
        else {
            n3 = this.number;
            n4 = this.optionalNumber;
            b = this.hasOptionalNumber;
        }
        if (animatableNumberOptionalNumberValue.number != n3 || animatableNumberOptionalNumberValue.hasOptionalNumber != b || animatableNumberOptionalNumberValue.optionalNumber != n4) {
            animatableNumberOptionalNumberValue.number = this.number;
            animatableNumberOptionalNumberValue.optionalNumber = this.optionalNumber;
            animatableNumberOptionalNumberValue.hasOptionalNumber = this.hasOptionalNumber;
            animatableNumberOptionalNumberValue.hasChanged = true;
        }
        return animatableNumberOptionalNumberValue;
    }
    
    public float getNumber() {
        return this.number;
    }
    
    public boolean hasOptionalNumber() {
        return this.hasOptionalNumber;
    }
    
    public float getOptionalNumber() {
        return this.optionalNumber;
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        if (this.hasOptionalNumber) {
            return new AnimatableNumberOptionalNumberValue(this.target, 0.0f, 0.0f);
        }
        return new AnimatableNumberOptionalNumberValue(this.target, 0.0f);
    }
    
    public String getCssText() {
        final StringBuffer sb = new StringBuffer();
        sb.append(AnimatableValue.formatNumber(this.number));
        if (this.hasOptionalNumber) {
            sb.append(' ');
            sb.append(AnimatableValue.formatNumber(this.optionalNumber));
        }
        return sb.toString();
    }
}
