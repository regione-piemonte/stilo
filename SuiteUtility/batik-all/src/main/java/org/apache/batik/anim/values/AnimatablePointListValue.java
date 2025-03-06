// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatablePointListValue extends AnimatableNumberListValue
{
    protected AnimatablePointListValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatablePointListValue(final AnimationTarget animationTarget, final float[] array) {
        super(animationTarget, array);
    }
    
    public AnimatableValue interpolate(AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        if (animatableValue == null) {
            animatableValue = new AnimatablePointListValue(this.target);
        }
        return super.interpolate(animatableValue, animatableValue2, n, animatableValue3, n2);
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatablePointListValue(this.target, new float[this.numbers.length]);
    }
}
