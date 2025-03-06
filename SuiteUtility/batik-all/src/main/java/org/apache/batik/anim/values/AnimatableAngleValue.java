// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableAngleValue extends AnimatableNumberValue
{
    protected static final String[] UNITS;
    protected short unit;
    
    public AnimatableAngleValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableAngleValue(final AnimationTarget animationTarget, final float n, final short unit) {
        super(animationTarget, n);
        this.unit = unit;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableAngleValue animatableAngleValue;
        if (animatableValue == null) {
            animatableAngleValue = new AnimatableAngleValue(this.target);
        }
        else {
            animatableAngleValue = (AnimatableAngleValue)animatableValue;
        }
        float value = this.value;
        short unit = this.unit;
        if (animatableValue2 != null) {
            final AnimatableAngleValue animatableAngleValue2 = (AnimatableAngleValue)animatableValue2;
            if (animatableAngleValue2.unit != unit) {
                final float rad = rad(value, unit);
                value = rad + n * (rad(animatableAngleValue2.value, animatableAngleValue2.unit) - rad);
                unit = 3;
            }
            else {
                value += n * (animatableAngleValue2.value - value);
            }
        }
        if (animatableValue3 != null) {
            final AnimatableAngleValue animatableAngleValue3 = (AnimatableAngleValue)animatableValue3;
            if (animatableAngleValue3.unit != unit) {
                value += n2 * rad(animatableAngleValue3.value, animatableAngleValue3.unit);
                unit = 3;
            }
            else {
                value += n2 * animatableAngleValue3.value;
            }
        }
        if (animatableAngleValue.value != value || animatableAngleValue.unit != unit) {
            animatableAngleValue.value = value;
            animatableAngleValue.unit = unit;
            animatableAngleValue.hasChanged = true;
        }
        return animatableAngleValue;
    }
    
    public short getUnit() {
        return this.unit;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        final AnimatableAngleValue animatableAngleValue = (AnimatableAngleValue)animatableValue;
        return Math.abs(rad(this.value, this.unit) - rad(animatableAngleValue.value, animatableAngleValue.unit));
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableAngleValue(this.target, 0.0f, (short)1);
    }
    
    public String getCssText() {
        return super.getCssText() + AnimatableAngleValue.UNITS[this.unit];
    }
    
    public static float rad(final float n, final short n2) {
        switch (n2) {
            case 3: {
                return n;
            }
            case 4: {
                return 3.1415927f * n / 200.0f;
            }
            default: {
                return 3.1415927f * n / 180.0f;
            }
        }
    }
    
    static {
        UNITS = new String[] { "", "", "deg", "rad", "grad" };
    }
}
