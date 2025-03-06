// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableNumberOrIdentValue extends AnimatableNumberValue
{
    protected boolean isIdent;
    protected String ident;
    protected boolean numericIdent;
    
    protected AnimatableNumberOrIdentValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableNumberOrIdentValue(final AnimationTarget animationTarget, final float n, final boolean numericIdent) {
        super(animationTarget, n);
        this.numericIdent = numericIdent;
    }
    
    public AnimatableNumberOrIdentValue(final AnimationTarget animationTarget, final String ident) {
        super(animationTarget);
        this.ident = ident;
        this.isIdent = true;
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableNumberOrIdentValue(this.target, 0.0f, this.numericIdent);
    }
    
    public String getCssText() {
        if (this.isIdent) {
            return this.ident;
        }
        if (this.numericIdent) {
            return Integer.toString((int)this.value);
        }
        return super.getCssText();
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableNumberOrIdentValue animatableNumberOrIdentValue;
        if (animatableValue == null) {
            animatableNumberOrIdentValue = new AnimatableNumberOrIdentValue(this.target);
        }
        else {
            animatableNumberOrIdentValue = (AnimatableNumberOrIdentValue)animatableValue;
        }
        if (animatableValue2 == null) {
            if (this.isIdent) {
                animatableNumberOrIdentValue.hasChanged = (!animatableNumberOrIdentValue.isIdent || !animatableNumberOrIdentValue.ident.equals(this.ident));
                animatableNumberOrIdentValue.ident = this.ident;
                animatableNumberOrIdentValue.isIdent = true;
            }
            else if (this.numericIdent) {
                animatableNumberOrIdentValue.hasChanged = (animatableNumberOrIdentValue.value != this.value || animatableNumberOrIdentValue.isIdent);
                animatableNumberOrIdentValue.value = this.value;
                animatableNumberOrIdentValue.isIdent = false;
                animatableNumberOrIdentValue.hasChanged = true;
                animatableNumberOrIdentValue.numericIdent = true;
            }
            else {
                final float value = animatableNumberOrIdentValue.value;
                super.interpolate(animatableNumberOrIdentValue, animatableValue2, n, animatableValue3, n2);
                animatableNumberOrIdentValue.numericIdent = false;
                if (animatableNumberOrIdentValue.value != value) {
                    animatableNumberOrIdentValue.hasChanged = true;
                }
            }
        }
        else {
            final AnimatableNumberOrIdentValue animatableNumberOrIdentValue2 = (AnimatableNumberOrIdentValue)animatableValue2;
            if (this.isIdent || animatableNumberOrIdentValue2.isIdent || this.numericIdent) {
                if (n >= 0.5) {
                    if (animatableNumberOrIdentValue.isIdent != animatableNumberOrIdentValue2.isIdent || animatableNumberOrIdentValue.value != animatableNumberOrIdentValue2.value || (animatableNumberOrIdentValue.isIdent && animatableNumberOrIdentValue2.isIdent && !animatableNumberOrIdentValue2.ident.equals(this.ident))) {
                        animatableNumberOrIdentValue.isIdent = animatableNumberOrIdentValue2.isIdent;
                        animatableNumberOrIdentValue.ident = animatableNumberOrIdentValue2.ident;
                        animatableNumberOrIdentValue.value = animatableNumberOrIdentValue2.value;
                        animatableNumberOrIdentValue.numericIdent = animatableNumberOrIdentValue2.numericIdent;
                        animatableNumberOrIdentValue.hasChanged = true;
                    }
                }
                else if (animatableNumberOrIdentValue.isIdent != this.isIdent || animatableNumberOrIdentValue.value != this.value || (animatableNumberOrIdentValue.isIdent && this.isIdent && !animatableNumberOrIdentValue.ident.equals(this.ident))) {
                    animatableNumberOrIdentValue.isIdent = this.isIdent;
                    animatableNumberOrIdentValue.ident = this.ident;
                    animatableNumberOrIdentValue.value = this.value;
                    animatableNumberOrIdentValue.numericIdent = this.numericIdent;
                    animatableNumberOrIdentValue.hasChanged = true;
                }
            }
            else {
                super.interpolate(animatableNumberOrIdentValue, animatableValue2, n, animatableValue3, n2);
                animatableNumberOrIdentValue.numericIdent = false;
            }
        }
        return animatableNumberOrIdentValue;
    }
}
