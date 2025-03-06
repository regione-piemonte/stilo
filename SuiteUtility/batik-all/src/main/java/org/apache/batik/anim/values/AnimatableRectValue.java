// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatableRectValue extends AnimatableValue
{
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    
    protected AnimatableRectValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableRectValue(final AnimationTarget animationTarget, final float x, final float y, final float width, final float height) {
        super(animationTarget);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatableRectValue animatableRectValue;
        if (animatableValue == null) {
            animatableRectValue = new AnimatableRectValue(this.target);
        }
        else {
            animatableRectValue = (AnimatableRectValue)animatableValue;
        }
        float x = this.x;
        float y = this.y;
        float width = this.width;
        float height = this.height;
        if (animatableValue2 != null) {
            final AnimatableRectValue animatableRectValue2 = (AnimatableRectValue)animatableValue2;
            x += n * (animatableRectValue2.x - this.x);
            y += n * (animatableRectValue2.y - this.y);
            width += n * (animatableRectValue2.width - this.width);
            height += n * (animatableRectValue2.height - this.height);
        }
        if (animatableValue3 != null && n2 != 0) {
            final AnimatableRectValue animatableRectValue3 = (AnimatableRectValue)animatableValue3;
            x += n2 * animatableRectValue3.x;
            y += n2 * animatableRectValue3.y;
            width += n2 * animatableRectValue3.width;
            height += n2 * animatableRectValue3.height;
        }
        if (animatableRectValue.x != x || animatableRectValue.y != y || animatableRectValue.width != width || animatableRectValue.height != height) {
            animatableRectValue.x = x;
            animatableRectValue.y = y;
            animatableRectValue.width = width;
            animatableRectValue.height = height;
            animatableRectValue.hasChanged = true;
        }
        return animatableRectValue;
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableRectValue(this.target, 0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public String toStringRep() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.x);
        sb.append(',');
        sb.append(this.y);
        sb.append(',');
        sb.append(this.width);
        sb.append(',');
        sb.append(this.height);
        return sb.toString();
    }
}
