// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import org.apache.batik.dom.anim.AnimationTarget;

public class AnimatablePaintValue extends AnimatableColorValue
{
    public static final int PAINT_NONE = 0;
    public static final int PAINT_CURRENT_COLOR = 1;
    public static final int PAINT_COLOR = 2;
    public static final int PAINT_URI = 3;
    public static final int PAINT_URI_NONE = 4;
    public static final int PAINT_URI_CURRENT_COLOR = 5;
    public static final int PAINT_URI_COLOR = 6;
    public static final int PAINT_INHERIT = 7;
    protected int paintType;
    protected String uri;
    
    protected AnimatablePaintValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    protected AnimatablePaintValue(final AnimationTarget animationTarget, final float n, final float n2, final float n3) {
        super(animationTarget, n, n2, n3);
    }
    
    public static AnimatablePaintValue createNonePaintValue(final AnimationTarget animationTarget) {
        final AnimatablePaintValue animatablePaintValue = new AnimatablePaintValue(animationTarget);
        animatablePaintValue.paintType = 0;
        return animatablePaintValue;
    }
    
    public static AnimatablePaintValue createCurrentColorPaintValue(final AnimationTarget animationTarget) {
        final AnimatablePaintValue animatablePaintValue = new AnimatablePaintValue(animationTarget);
        animatablePaintValue.paintType = 1;
        return animatablePaintValue;
    }
    
    public static AnimatablePaintValue createColorPaintValue(final AnimationTarget animationTarget, final float n, final float n2, final float n3) {
        final AnimatablePaintValue animatablePaintValue = new AnimatablePaintValue(animationTarget, n, n2, n3);
        animatablePaintValue.paintType = 2;
        return animatablePaintValue;
    }
    
    public static AnimatablePaintValue createURIPaintValue(final AnimationTarget animationTarget, final String uri) {
        final AnimatablePaintValue animatablePaintValue = new AnimatablePaintValue(animationTarget);
        animatablePaintValue.uri = uri;
        animatablePaintValue.paintType = 3;
        return animatablePaintValue;
    }
    
    public static AnimatablePaintValue createURINonePaintValue(final AnimationTarget animationTarget, final String uri) {
        final AnimatablePaintValue animatablePaintValue = new AnimatablePaintValue(animationTarget);
        animatablePaintValue.uri = uri;
        animatablePaintValue.paintType = 4;
        return animatablePaintValue;
    }
    
    public static AnimatablePaintValue createURICurrentColorPaintValue(final AnimationTarget animationTarget, final String uri) {
        final AnimatablePaintValue animatablePaintValue = new AnimatablePaintValue(animationTarget);
        animatablePaintValue.uri = uri;
        animatablePaintValue.paintType = 5;
        return animatablePaintValue;
    }
    
    public static AnimatablePaintValue createURIColorPaintValue(final AnimationTarget animationTarget, final String uri, final float n, final float n2, final float n3) {
        final AnimatablePaintValue animatablePaintValue = new AnimatablePaintValue(animationTarget, n, n2, n3);
        animatablePaintValue.uri = uri;
        animatablePaintValue.paintType = 6;
        return animatablePaintValue;
    }
    
    public static AnimatablePaintValue createInheritPaintValue(final AnimationTarget animationTarget) {
        final AnimatablePaintValue animatablePaintValue = new AnimatablePaintValue(animationTarget);
        animatablePaintValue.paintType = 7;
        return animatablePaintValue;
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        AnimatablePaintValue animatablePaintValue;
        if (animatableValue == null) {
            animatablePaintValue = new AnimatablePaintValue(this.target);
        }
        else {
            animatablePaintValue = (AnimatablePaintValue)animatableValue;
        }
        if (this.paintType == 2) {
            boolean b = true;
            if (animatableValue2 != null) {
                b = (((AnimatablePaintValue)animatableValue2).paintType == 2);
            }
            if (animatableValue3 != null) {
                final AnimatablePaintValue animatablePaintValue2 = (AnimatablePaintValue)animatableValue3;
                b = (b && animatablePaintValue2.paintType == 2);
            }
            if (b) {
                animatablePaintValue.paintType = 2;
                return super.interpolate(animatablePaintValue, animatableValue2, n, animatableValue3, n2);
            }
        }
        int paintType;
        String s;
        float red;
        float green;
        float blue;
        if (animatableValue2 != null && n >= 0.5) {
            final AnimatablePaintValue animatablePaintValue3 = (AnimatablePaintValue)animatableValue2;
            paintType = animatablePaintValue3.paintType;
            s = animatablePaintValue3.uri;
            red = animatablePaintValue3.red;
            green = animatablePaintValue3.green;
            blue = animatablePaintValue3.blue;
        }
        else {
            paintType = this.paintType;
            s = this.uri;
            red = this.red;
            green = this.green;
            blue = this.blue;
        }
        if (animatablePaintValue.paintType != paintType || animatablePaintValue.uri == null || !animatablePaintValue.uri.equals(s) || animatablePaintValue.red != red || animatablePaintValue.green != green || animatablePaintValue.blue != blue) {
            animatablePaintValue.paintType = paintType;
            animatablePaintValue.uri = s;
            animatablePaintValue.red = red;
            animatablePaintValue.green = green;
            animatablePaintValue.blue = blue;
            animatablePaintValue.hasChanged = true;
        }
        return animatablePaintValue;
    }
    
    public int getPaintType() {
        return this.paintType;
    }
    
    public String getURI() {
        return this.uri;
    }
    
    public boolean canPace() {
        return false;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        return createColorPaintValue(this.target, 0.0f, 0.0f, 0.0f);
    }
    
    public String getCssText() {
        switch (this.paintType) {
            case 0: {
                return "none";
            }
            case 1: {
                return "currentColor";
            }
            case 2: {
                return super.getCssText();
            }
            case 3: {
                return "url(" + this.uri + ")";
            }
            case 4: {
                return "url(" + this.uri + ") none";
            }
            case 5: {
                return "url(" + this.uri + ") currentColor";
            }
            case 6: {
                return "url(" + this.uri + ") " + super.getCssText();
            }
            default: {
                return "inherit";
            }
        }
    }
}
