// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatablePercentageValue extends AnimatableNumberValue
{
    protected AnimatablePercentageValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatablePercentageValue(final AnimationTarget animationTarget, final float n) {
        super(animationTarget, n);
    }
    
    public AnimatableValue interpolate(AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        if (animatableValue == null) {
            animatableValue = new AnimatablePercentageValue(this.target);
        }
        return super.interpolate(animatableValue, animatableValue2, n, animatableValue3, n2);
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatablePercentageValue(this.target, 0.0f);
    }
    
    public String getCssText() {
        return super.getCssText() + "%";
    }
}
