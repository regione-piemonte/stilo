// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableNumberListValue extends AnimatableValue
{
    protected float[] numbers;
    
    protected AnimatableNumberListValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableNumberListValue(final AnimationTarget animationTarget, final float[] numbers) {
        super(animationTarget);
        this.numbers = numbers;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        final AnimatableNumberListValue animatableNumberListValue = (AnimatableNumberListValue)animatableValue2;
        final AnimatableNumberListValue animatableNumberListValue2 = (AnimatableNumberListValue)animatableValue3;
        final boolean b = animatableValue2 != null;
        final boolean b2 = animatableValue3 != null;
        final boolean b3 = (!b || animatableNumberListValue.numbers.length == this.numbers.length) && (!b2 || animatableNumberListValue2.numbers.length == this.numbers.length);
        float[] array;
        if (!b3 && b && n >= 0.5) {
            array = animatableNumberListValue.numbers;
        }
        else {
            array = this.numbers;
        }
        final int length = array.length;
        AnimatableNumberListValue animatableNumberListValue3;
        if (animatableValue == null) {
            animatableNumberListValue3 = new AnimatableNumberListValue(this.target);
            animatableNumberListValue3.numbers = new float[length];
        }
        else {
            animatableNumberListValue3 = (AnimatableNumberListValue)animatableValue;
            if (animatableNumberListValue3.numbers == null || animatableNumberListValue3.numbers.length != length) {
                animatableNumberListValue3.numbers = new float[length];
            }
        }
        for (int i = 0; i < length; ++i) {
            float n3 = array[i];
            if (b3) {
                if (b) {
                    n3 += n * (animatableNumberListValue.numbers[i] - n3);
                }
                if (b2) {
                    n3 += n2 * animatableNumberListValue2.numbers[i];
                }
            }
            if (animatableNumberListValue3.numbers[i] != n3) {
                animatableNumberListValue3.numbers[i] = n3;
                animatableNumberListValue3.hasChanged = true;
            }
        }
        return animatableNumberListValue3;
    }
    
    public float[] getNumbers() {
        return this.numbers;
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableNumberListValue(this.target, new float[this.numbers.length]);
    }
    
    public String getCssText() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.numbers[0]);
        for (int i = 1; i < this.numbers.length; ++i) {
            sb.append(' ');
            sb.append(this.numbers[i]);
        }
        return sb.toString();
    }
}
