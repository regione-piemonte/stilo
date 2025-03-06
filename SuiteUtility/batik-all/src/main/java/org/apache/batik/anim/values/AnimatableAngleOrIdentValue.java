// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableAngleOrIdentValue extends AnimatableAngleValue
{
    protected boolean isIdent;
    protected String ident;
    
    protected AnimatableAngleOrIdentValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableAngleOrIdentValue(final AnimationTarget animationTarget, final float n, final short n2) {
        super(animationTarget, n, n2);
    }
    
    public AnimatableAngleOrIdentValue(final AnimationTarget animationTarget, final String ident) {
        super(animationTarget);
        this.ident = ident;
        this.isIdent = true;
    }
    
    public boolean isIdent() {
        return this.isIdent;
    }
    
    public String getIdent() {
        return this.ident;
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableAngleOrIdentValue(this.target, 0.0f, (short)1);
    }
    
    public String getCssText() {
        if (this.isIdent) {
            return this.ident;
        }
        return super.getCssText();
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableAngleOrIdentValue animatableAngleOrIdentValue;
        if (animatableValue == null) {
            animatableAngleOrIdentValue = new AnimatableAngleOrIdentValue(this.target);
        }
        else {
            animatableAngleOrIdentValue = (AnimatableAngleOrIdentValue)animatableValue;
        }
        if (animatableValue2 == null) {
            if (this.isIdent) {
                animatableAngleOrIdentValue.hasChanged = (!animatableAngleOrIdentValue.isIdent || !animatableAngleOrIdentValue.ident.equals(this.ident));
                animatableAngleOrIdentValue.ident = this.ident;
                animatableAngleOrIdentValue.isIdent = true;
            }
            else {
                final short unit = animatableAngleOrIdentValue.unit;
                final float value = animatableAngleOrIdentValue.value;
                super.interpolate(animatableAngleOrIdentValue, animatableValue2, n, animatableValue3, n2);
                if (animatableAngleOrIdentValue.unit != unit || animatableAngleOrIdentValue.value != value) {
                    animatableAngleOrIdentValue.hasChanged = true;
                }
            }
        }
        else {
            final AnimatableAngleOrIdentValue animatableAngleOrIdentValue2 = (AnimatableAngleOrIdentValue)animatableValue2;
            if (this.isIdent || animatableAngleOrIdentValue2.isIdent) {
                if (n >= 0.5) {
                    if (animatableAngleOrIdentValue.isIdent != animatableAngleOrIdentValue2.isIdent || animatableAngleOrIdentValue.unit != animatableAngleOrIdentValue2.unit || animatableAngleOrIdentValue.value != animatableAngleOrIdentValue2.value || (animatableAngleOrIdentValue.isIdent && animatableAngleOrIdentValue2.isIdent && !animatableAngleOrIdentValue2.ident.equals(this.ident))) {
                        animatableAngleOrIdentValue.isIdent = animatableAngleOrIdentValue2.isIdent;
                        animatableAngleOrIdentValue.ident = animatableAngleOrIdentValue2.ident;
                        animatableAngleOrIdentValue.unit = animatableAngleOrIdentValue2.unit;
                        animatableAngleOrIdentValue.value = animatableAngleOrIdentValue2.value;
                        animatableAngleOrIdentValue.hasChanged = true;
                    }
                }
                else if (animatableAngleOrIdentValue.isIdent != this.isIdent || animatableAngleOrIdentValue.unit != this.unit || animatableAngleOrIdentValue.value != this.value || (animatableAngleOrIdentValue.isIdent && this.isIdent && !animatableAngleOrIdentValue.ident.equals(this.ident))) {
                    animatableAngleOrIdentValue.isIdent = this.isIdent;
                    animatableAngleOrIdentValue.ident = this.ident;
                    animatableAngleOrIdentValue.unit = this.unit;
                    animatableAngleOrIdentValue.value = this.value;
                    animatableAngleOrIdentValue.hasChanged = true;
                }
            }
            else {
                super.interpolate(animatableAngleOrIdentValue, animatableValue2, n, animatableValue3, n2);
            }
        }
        return animatableAngleOrIdentValue;
    }
}
