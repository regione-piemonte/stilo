// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableMotionPointValue extends AnimatableValue
{
    protected float x;
    protected float y;
    protected float angle;
    
    protected AnimatableMotionPointValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableMotionPointValue(final AnimationTarget animationTarget, final float x, final float y, final float angle) {
        super(animationTarget);
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableMotionPointValue animatableMotionPointValue;
        if (animatableValue == null) {
            animatableMotionPointValue = new AnimatableMotionPointValue(this.target);
        }
        else {
            animatableMotionPointValue = (AnimatableMotionPointValue)animatableValue;
        }
        float x = this.x;
        float y = this.y;
        float angle = this.angle;
        int n3 = 1;
        if (animatableValue2 != null) {
            final AnimatableMotionPointValue animatableMotionPointValue2 = (AnimatableMotionPointValue)animatableValue2;
            x += n * (animatableMotionPointValue2.x - this.x);
            y += n * (animatableMotionPointValue2.y - this.y);
            angle += animatableMotionPointValue2.angle;
            ++n3;
        }
        if (animatableValue3 != null && n2 != 0) {
            final AnimatableMotionPointValue animatableMotionPointValue3 = (AnimatableMotionPointValue)animatableValue3;
            x += n2 * animatableMotionPointValue3.x;
            y += n2 * animatableMotionPointValue3.y;
            angle += animatableMotionPointValue3.angle;
            ++n3;
        }
        final float angle2 = angle / n3;
        if (animatableMotionPointValue.x != x || animatableMotionPointValue.y != y || animatableMotionPointValue.angle != angle2) {
            animatableMotionPointValue.x = x;
            animatableMotionPointValue.y = y;
            animatableMotionPointValue.angle = angle2;
            animatableMotionPointValue.hasChanged = true;
        }
        return animatableMotionPointValue;
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public float getAngle() {
        return this.angle;
    }
    
    public boolean canPace() {
        return true;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        final AnimatableMotionPointValue animatableMotionPointValue = (AnimatableMotionPointValue)animatableValue;
        final float n = this.x - animatableMotionPointValue.x;
        final float n2 = this.y - animatableMotionPointValue.y;
        return (float)Math.sqrt(n * n + n2 * n2);
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableMotionPointValue(this.target, 0.0f, 0.0f, 0.0f);
    }
    
    public String toStringRep() {
        final StringBuffer sb = new StringBuffer();
        sb.append(AnimatableValue.formatNumber(this.x));
        sb.append(',');
        sb.append(AnimatableValue.formatNumber(this.y));
        sb.append(',');
        sb.append(AnimatableValue.formatNumber(this.angle));
        sb.append("rad");
        return sb.toString();
    }
}
