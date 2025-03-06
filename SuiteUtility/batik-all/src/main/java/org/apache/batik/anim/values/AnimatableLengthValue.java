// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableLengthValue extends AnimatableValue
{
    protected static final String[] UNITS;
    protected short lengthType;
    protected float lengthValue;
    protected short percentageInterpretation;
    
    protected AnimatableLengthValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableLengthValue(final AnimationTarget animationTarget, final short lengthType, final float lengthValue, final short percentageInterpretation) {
        super(animationTarget);
        this.lengthType = lengthType;
        this.lengthValue = lengthValue;
        this.percentageInterpretation = percentageInterpretation;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableLengthValue animatableLengthValue;
        if (animatableValue == null) {
            animatableLengthValue = new AnimatableLengthValue(this.target);
        }
        else {
            animatableLengthValue = (AnimatableLengthValue)animatableValue;
        }
        final short lengthType = animatableLengthValue.lengthType;
        final float lengthValue = animatableLengthValue.lengthValue;
        final short percentageInterpretation = animatableLengthValue.percentageInterpretation;
        animatableLengthValue.lengthType = this.lengthType;
        animatableLengthValue.lengthValue = this.lengthValue;
        animatableLengthValue.percentageInterpretation = this.percentageInterpretation;
        if (animatableValue2 != null) {
            final AnimatableLengthValue animatableLengthValue2 = (AnimatableLengthValue)animatableValue2;
            float n3;
            if (!compatibleTypes(animatableLengthValue.lengthType, animatableLengthValue.percentageInterpretation, animatableLengthValue2.lengthType, animatableLengthValue2.percentageInterpretation)) {
                animatableLengthValue.lengthValue = this.target.svgToUserSpace(animatableLengthValue.lengthValue, animatableLengthValue.lengthType, animatableLengthValue.percentageInterpretation);
                animatableLengthValue.lengthType = 1;
                n3 = animatableLengthValue2.target.svgToUserSpace(animatableLengthValue2.lengthValue, animatableLengthValue2.lengthType, animatableLengthValue2.percentageInterpretation);
            }
            else {
                n3 = animatableLengthValue2.lengthValue;
            }
            final AnimatableLengthValue animatableLengthValue3 = animatableLengthValue;
            animatableLengthValue3.lengthValue += n * (n3 - animatableLengthValue.lengthValue);
        }
        if (animatableValue3 != null) {
            final AnimatableLengthValue animatableLengthValue4 = (AnimatableLengthValue)animatableValue3;
            float n4;
            if (!compatibleTypes(animatableLengthValue.lengthType, animatableLengthValue.percentageInterpretation, animatableLengthValue4.lengthType, animatableLengthValue4.percentageInterpretation)) {
                animatableLengthValue.lengthValue = this.target.svgToUserSpace(animatableLengthValue.lengthValue, animatableLengthValue.lengthType, animatableLengthValue.percentageInterpretation);
                animatableLengthValue.lengthType = 1;
                n4 = animatableLengthValue4.target.svgToUserSpace(animatableLengthValue4.lengthValue, animatableLengthValue4.lengthType, animatableLengthValue4.percentageInterpretation);
            }
            else {
                n4 = animatableLengthValue4.lengthValue;
            }
            final AnimatableLengthValue animatableLengthValue5 = animatableLengthValue;
            animatableLengthValue5.lengthValue += n2 * n4;
        }
        if (percentageInterpretation != animatableLengthValue.percentageInterpretation || lengthType != animatableLengthValue.lengthType || lengthValue != animatableLengthValue.lengthValue) {
            animatableLengthValue.hasChanged = true;
        }
        return animatableLengthValue;
    }
    
    public static boolean compatibleTypes(final short n, final short n2, final short n3, final short n4) {
        return (n == n3 && (n != 2 || n2 == n4)) || (n == 1 && n3 == 5) || (n == 5 && n3 == 1);
    }
    
    public int getLengthType() {
        return this.lengthType;
    }
    
    public float getLengthValue() {
        return this.lengthValue;
    }
    
    public boolean canPace() {
        return true;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        final AnimatableLengthValue animatableLengthValue = (AnimatableLengthValue)animatableValue;
        return Math.abs(this.target.svgToUserSpace(this.lengthValue, this.lengthType, this.percentageInterpretation) - this.target.svgToUserSpace(animatableLengthValue.lengthValue, animatableLengthValue.lengthType, animatableLengthValue.percentageInterpretation));
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableLengthValue(this.target, (short)1, 0.0f, this.percentageInterpretation);
    }
    
    public String getCssText() {
        return AnimatableValue.formatNumber(this.lengthValue) + AnimatableLengthValue.UNITS[this.lengthType - 1];
    }
    
    static {
        UNITS = new String[] { "", "%", "em", "ex", "px", "cm", "mm", "in", "pt", "pc" };
    }
}
