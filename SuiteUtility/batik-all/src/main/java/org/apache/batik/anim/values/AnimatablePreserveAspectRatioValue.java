// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatablePreserveAspectRatioValue extends AnimatableValue
{
    protected static final String[] ALIGN_VALUES;
    protected static final String[] MEET_OR_SLICE_VALUES;
    protected short align;
    protected short meetOrSlice;
    
    protected AnimatablePreserveAspectRatioValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatablePreserveAspectRatioValue(final AnimationTarget animationTarget, final short align, final short meetOrSlice) {
        super(animationTarget);
        this.align = align;
        this.meetOrSlice = meetOrSlice;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatablePreserveAspectRatioValue animatablePreserveAspectRatioValue;
        if (animatableValue == null) {
            animatablePreserveAspectRatioValue = new AnimatablePreserveAspectRatioValue(this.target);
        }
        else {
            animatablePreserveAspectRatioValue = (AnimatablePreserveAspectRatioValue)animatableValue;
        }
        short n3;
        short n4;
        if (animatableValue2 != null && n >= 0.5) {
            final AnimatablePreserveAspectRatioValue animatablePreserveAspectRatioValue2 = (AnimatablePreserveAspectRatioValue)animatableValue2;
            n3 = animatablePreserveAspectRatioValue2.align;
            n4 = animatablePreserveAspectRatioValue2.meetOrSlice;
        }
        else {
            n3 = this.align;
            n4 = this.meetOrSlice;
        }
        if (animatablePreserveAspectRatioValue.align != n3 || animatablePreserveAspectRatioValue.meetOrSlice != n4) {
            animatablePreserveAspectRatioValue.align = this.align;
            animatablePreserveAspectRatioValue.meetOrSlice = this.meetOrSlice;
            animatablePreserveAspectRatioValue.hasChanged = true;
        }
        return animatablePreserveAspectRatioValue;
    }
    
    public short getAlign() {
        return this.align;
    }
    
    public short getMeetOrSlice() {
        return this.meetOrSlice;
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatablePreserveAspectRatioValue(this.target, (short)1, (short)1);
    }
    
    public String toStringRep() {
        if (this.align < 1 || this.align > 10) {
            return null;
        }
        final String str = AnimatablePreserveAspectRatioValue.ALIGN_VALUES[this.align];
        if (this.align == 1) {
            return str;
        }
        if (this.meetOrSlice < 1 || this.meetOrSlice > 2) {
            return null;
        }
        return str + ' ' + AnimatablePreserveAspectRatioValue.MEET_OR_SLICE_VALUES[this.meetOrSlice];
    }
    
    static {
        ALIGN_VALUES = new String[] { null, "none", "xMinYMin", "xMidYMin", "xMaxYMin", "xMinYMid", "xMidYMid", "xMaxYMid", "xMinYMax", "xMidYMax", "xMaxYMax" };
        MEET_OR_SLICE_VALUES = new String[] { null, "meet", "slice" };
    }
}
