// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableLengthListValue extends AnimatableValue
{
    protected short[] lengthTypes;
    protected float[] lengthValues;
    protected short percentageInterpretation;
    
    protected AnimatableLengthListValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableLengthListValue(final AnimationTarget animationTarget, final short[] lengthTypes, final float[] lengthValues, final short percentageInterpretation) {
        super(animationTarget);
        this.lengthTypes = lengthTypes;
        this.lengthValues = lengthValues;
        this.percentageInterpretation = percentageInterpretation;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        final AnimatableLengthListValue animatableLengthListValue = (AnimatableLengthListValue)animatableValue2;
        final AnimatableLengthListValue animatableLengthListValue2 = (AnimatableLengthListValue)animatableValue3;
        final boolean b = animatableValue2 != null;
        final boolean b2 = animatableValue3 != null;
        final boolean b3 = (!b || animatableLengthListValue.lengthTypes.length == this.lengthTypes.length) && (!b2 || animatableLengthListValue2.lengthTypes.length == this.lengthTypes.length);
        short[] array;
        float[] array2;
        if (!b3 && b && n >= 0.5) {
            array = animatableLengthListValue.lengthTypes;
            array2 = animatableLengthListValue.lengthValues;
        }
        else {
            array = this.lengthTypes;
            array2 = this.lengthValues;
        }
        final int length = array.length;
        AnimatableLengthListValue animatableLengthListValue3;
        if (animatableValue == null) {
            animatableLengthListValue3 = new AnimatableLengthListValue(this.target);
            animatableLengthListValue3.lengthTypes = new short[length];
            animatableLengthListValue3.lengthValues = new float[length];
        }
        else {
            animatableLengthListValue3 = (AnimatableLengthListValue)animatableValue;
            if (animatableLengthListValue3.lengthTypes == null || animatableLengthListValue3.lengthTypes.length != length) {
                animatableLengthListValue3.lengthTypes = new short[length];
                animatableLengthListValue3.lengthValues = new float[length];
            }
        }
        animatableLengthListValue3.hasChanged = (this.percentageInterpretation != animatableLengthListValue3.percentageInterpretation);
        animatableLengthListValue3.percentageInterpretation = this.percentageInterpretation;
        for (int i = 0; i < length; ++i) {
            float svgToUserSpace = 0.0f;
            float svgToUserSpace2 = 0.0f;
            short n3 = array[i];
            float svgToUserSpace3 = array2[i];
            if (b3) {
                if ((b && !AnimatableLengthValue.compatibleTypes(n3, this.percentageInterpretation, animatableLengthListValue.lengthTypes[i], animatableLengthListValue.percentageInterpretation)) || (b2 && !AnimatableLengthValue.compatibleTypes(n3, this.percentageInterpretation, animatableLengthListValue2.lengthTypes[i], animatableLengthListValue2.percentageInterpretation))) {
                    svgToUserSpace3 = this.target.svgToUserSpace(svgToUserSpace3, n3, this.percentageInterpretation);
                    n3 = 1;
                    if (b) {
                        svgToUserSpace = animatableValue2.target.svgToUserSpace(animatableLengthListValue.lengthValues[i], animatableLengthListValue.lengthTypes[i], animatableLengthListValue.percentageInterpretation);
                    }
                    if (b2) {
                        svgToUserSpace2 = animatableValue3.target.svgToUserSpace(animatableLengthListValue2.lengthValues[i], animatableLengthListValue2.lengthTypes[i], animatableLengthListValue2.percentageInterpretation);
                    }
                }
                else {
                    if (b) {
                        svgToUserSpace = animatableLengthListValue.lengthValues[i];
                    }
                    if (b2) {
                        svgToUserSpace2 = animatableLengthListValue2.lengthValues[i];
                    }
                }
                svgToUserSpace3 += n * (svgToUserSpace - svgToUserSpace3) + n2 * svgToUserSpace2;
            }
            if (!animatableLengthListValue3.hasChanged) {
                animatableLengthListValue3.hasChanged = (n3 != animatableLengthListValue3.lengthTypes[i] || svgToUserSpace3 != animatableLengthListValue3.lengthValues[i]);
            }
            animatableLengthListValue3.lengthTypes[i] = n3;
            animatableLengthListValue3.lengthValues[i] = svgToUserSpace3;
        }
        return animatableLengthListValue3;
    }
    
    public short[] getLengthTypes() {
        return this.lengthTypes;
    }
    
    public float[] getLengthValues() {
        return this.lengthValues;
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableLengthListValue(this.target, this.lengthTypes, new float[this.lengthValues.length], this.percentageInterpretation);
    }
    
    public String getCssText() {
        final StringBuffer sb = new StringBuffer();
        if (this.lengthValues.length > 0) {
            sb.append(AnimatableValue.formatNumber(this.lengthValues[0]));
            sb.append(AnimatableLengthValue.UNITS[this.lengthTypes[0] - 1]);
        }
        for (int i = 1; i < this.lengthValues.length; ++i) {
            sb.append(',');
            sb.append(AnimatableValue.formatNumber(this.lengthValues[i]));
            sb.append(AnimatableLengthValue.UNITS[this.lengthTypes[i] - 1]);
        }
        return sb.toString();
    }
}
