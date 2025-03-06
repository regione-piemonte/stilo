// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableColorValue extends AnimatableValue
{
    protected float red;
    protected float green;
    protected float blue;
    
    protected AnimatableColorValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableColorValue(final AnimationTarget animationTarget, final float red, final float green, final float blue) {
        super(animationTarget);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableColorValue animatableColorValue;
        if (animatableValue == null) {
            animatableColorValue = new AnimatableColorValue(this.target);
        }
        else {
            animatableColorValue = (AnimatableColorValue)animatableValue;
        }
        final float red = animatableColorValue.red;
        final float green = animatableColorValue.green;
        final float blue = animatableColorValue.blue;
        animatableColorValue.red = this.red;
        animatableColorValue.green = this.green;
        animatableColorValue.blue = this.blue;
        final AnimatableColorValue animatableColorValue2 = (AnimatableColorValue)animatableValue2;
        final AnimatableColorValue animatableColorValue3 = (AnimatableColorValue)animatableValue3;
        if (animatableValue2 != null) {
            final AnimatableColorValue animatableColorValue4 = animatableColorValue;
            animatableColorValue4.red += n * (animatableColorValue2.red - animatableColorValue.red);
            final AnimatableColorValue animatableColorValue5 = animatableColorValue;
            animatableColorValue5.green += n * (animatableColorValue2.green - animatableColorValue.green);
            final AnimatableColorValue animatableColorValue6 = animatableColorValue;
            animatableColorValue6.blue += n * (animatableColorValue2.blue - animatableColorValue.blue);
        }
        if (animatableValue3 != null) {
            final AnimatableColorValue animatableColorValue7 = animatableColorValue;
            animatableColorValue7.red += n2 * animatableColorValue3.red;
            final AnimatableColorValue animatableColorValue8 = animatableColorValue;
            animatableColorValue8.green += n2 * animatableColorValue3.green;
            final AnimatableColorValue animatableColorValue9 = animatableColorValue;
            animatableColorValue9.blue += n2 * animatableColorValue3.blue;
        }
        if (animatableColorValue.red != red || animatableColorValue.green != green || animatableColorValue.blue != blue) {
            animatableColorValue.hasChanged = true;
        }
        return animatableColorValue;
    }
    
    public boolean canPace() {
        return true;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        final AnimatableColorValue animatableColorValue = (AnimatableColorValue)animatableValue;
        final float n = this.red - animatableColorValue.red;
        final float n2 = this.green - animatableColorValue.green;
        final float n3 = this.blue - animatableColorValue.blue;
        return (float)Math.sqrt(n * n + n2 * n2 + n3 * n3);
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableColorValue(this.target, 0.0f, 0.0f, 0.0f);
    }
    
    public String getCssText() {
        return "rgb(" + Math.round(this.red * 255.0f) + ',' + Math.round(this.green * 255.0f) + ',' + Math.round(this.blue * 255.0f) + ')';
    }
}
