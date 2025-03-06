// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableLengthOrIdentValue extends AnimatableLengthValue
{
    protected boolean isIdent;
    protected String ident;
    
    protected AnimatableLengthOrIdentValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableLengthOrIdentValue(final AnimationTarget animationTarget, final short n, final float n2, final short n3) {
        super(animationTarget, n, n2, n3);
    }
    
    public AnimatableLengthOrIdentValue(final AnimationTarget animationTarget, final String ident) {
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
        return new AnimatableLengthOrIdentValue(this.target, (short)1, 0.0f, this.percentageInterpretation);
    }
    
    public String getCssText() {
        if (this.isIdent) {
            return this.ident;
        }
        return super.getCssText();
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableLengthOrIdentValue animatableLengthOrIdentValue;
        if (animatableValue == null) {
            animatableLengthOrIdentValue = new AnimatableLengthOrIdentValue(this.target);
        }
        else {
            animatableLengthOrIdentValue = (AnimatableLengthOrIdentValue)animatableValue;
        }
        if (animatableValue2 == null) {
            if (this.isIdent) {
                animatableLengthOrIdentValue.hasChanged = (!animatableLengthOrIdentValue.isIdent || !animatableLengthOrIdentValue.ident.equals(this.ident));
                animatableLengthOrIdentValue.ident = this.ident;
                animatableLengthOrIdentValue.isIdent = true;
            }
            else {
                final short lengthType = animatableLengthOrIdentValue.lengthType;
                final float lengthValue = animatableLengthOrIdentValue.lengthValue;
                final short percentageInterpretation = animatableLengthOrIdentValue.percentageInterpretation;
                super.interpolate(animatableLengthOrIdentValue, animatableValue2, n, animatableValue3, n2);
                if (animatableLengthOrIdentValue.lengthType != lengthType || animatableLengthOrIdentValue.lengthValue != lengthValue || animatableLengthOrIdentValue.percentageInterpretation != percentageInterpretation) {
                    animatableLengthOrIdentValue.hasChanged = true;
                }
            }
        }
        else {
            final AnimatableLengthOrIdentValue animatableLengthOrIdentValue2 = (AnimatableLengthOrIdentValue)animatableValue2;
            if (this.isIdent || animatableLengthOrIdentValue2.isIdent) {
                if (n >= 0.5) {
                    if (animatableLengthOrIdentValue.isIdent != animatableLengthOrIdentValue2.isIdent || animatableLengthOrIdentValue.lengthType != animatableLengthOrIdentValue2.lengthType || animatableLengthOrIdentValue.lengthValue != animatableLengthOrIdentValue2.lengthValue || (animatableLengthOrIdentValue.isIdent && animatableLengthOrIdentValue2.isIdent && !animatableLengthOrIdentValue2.ident.equals(this.ident))) {
                        animatableLengthOrIdentValue.isIdent = animatableLengthOrIdentValue2.isIdent;
                        animatableLengthOrIdentValue.ident = animatableLengthOrIdentValue2.ident;
                        animatableLengthOrIdentValue.lengthType = animatableLengthOrIdentValue2.lengthType;
                        animatableLengthOrIdentValue.lengthValue = animatableLengthOrIdentValue2.lengthValue;
                        animatableLengthOrIdentValue.hasChanged = true;
                    }
                }
                else if (animatableLengthOrIdentValue.isIdent != this.isIdent || animatableLengthOrIdentValue.lengthType != this.lengthType || animatableLengthOrIdentValue.lengthValue != this.lengthValue || (animatableLengthOrIdentValue.isIdent && this.isIdent && !animatableLengthOrIdentValue.ident.equals(this.ident))) {
                    animatableLengthOrIdentValue.isIdent = this.isIdent;
                    animatableLengthOrIdentValue.ident = this.ident;
                    animatableLengthOrIdentValue.ident = this.ident;
                    animatableLengthOrIdentValue.lengthType = this.lengthType;
                    animatableLengthOrIdentValue.hasChanged = true;
                }
            }
            else {
                super.interpolate(animatableLengthOrIdentValue, animatableValue2, n, animatableValue3, n2);
            }
        }
        return animatableLengthOrIdentValue;
    }
}
