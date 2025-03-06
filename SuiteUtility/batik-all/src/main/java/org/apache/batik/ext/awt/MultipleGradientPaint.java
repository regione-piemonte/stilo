// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt;

import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.awt.Paint;

public abstract class MultipleGradientPaint implements Paint
{
    protected int transparency;
    protected float[] fractions;
    protected Color[] colors;
    protected AffineTransform gradientTransform;
    protected CycleMethodEnum cycleMethod;
    protected ColorSpaceEnum colorSpace;
    public static final CycleMethodEnum NO_CYCLE;
    public static final CycleMethodEnum REFLECT;
    public static final CycleMethodEnum REPEAT;
    public static final ColorSpaceEnum SRGB;
    public static final ColorSpaceEnum LINEAR_RGB;
    
    public MultipleGradientPaint(final float[] array, final Color[] array2, final CycleMethodEnum cycleMethod, final ColorSpaceEnum colorSpace, final AffineTransform affineTransform) {
        if (array == null) {
            throw new IllegalArgumentException("Fractions array cannot be null");
        }
        if (array2 == null) {
            throw new IllegalArgumentException("Colors array cannot be null");
        }
        if (array.length != array2.length) {
            throw new IllegalArgumentException("Colors and fractions must have equal size");
        }
        if (array2.length < 2) {
            throw new IllegalArgumentException("User must specify at least 2 colors");
        }
        if (colorSpace != MultipleGradientPaint.LINEAR_RGB && colorSpace != MultipleGradientPaint.SRGB) {
            throw new IllegalArgumentException("Invalid colorspace for interpolation.");
        }
        if (cycleMethod != MultipleGradientPaint.NO_CYCLE && cycleMethod != MultipleGradientPaint.REFLECT && cycleMethod != MultipleGradientPaint.REPEAT) {
            throw new IllegalArgumentException("Invalid cycle method.");
        }
        if (affineTransform == null) {
            throw new IllegalArgumentException("Gradient transform cannot be null.");
        }
        System.arraycopy(array, 0, this.fractions = new float[array.length], 0, array.length);
        System.arraycopy(array2, 0, this.colors = new Color[array2.length], 0, array2.length);
        this.colorSpace = colorSpace;
        this.cycleMethod = cycleMethod;
        this.gradientTransform = (AffineTransform)affineTransform.clone();
        boolean b = true;
        for (int i = 0; i < array2.length; ++i) {
            b = (b && array2[i].getAlpha() == 255);
        }
        if (b) {
            this.transparency = 1;
        }
        else {
            this.transparency = 3;
        }
    }
    
    public Color[] getColors() {
        final Color[] array = new Color[this.colors.length];
        System.arraycopy(this.colors, 0, array, 0, this.colors.length);
        return array;
    }
    
    public float[] getFractions() {
        final float[] array = new float[this.fractions.length];
        System.arraycopy(this.fractions, 0, array, 0, this.fractions.length);
        return array;
    }
    
    public int getTransparency() {
        return this.transparency;
    }
    
    public CycleMethodEnum getCycleMethod() {
        return this.cycleMethod;
    }
    
    public ColorSpaceEnum getColorSpace() {
        return this.colorSpace;
    }
    
    public AffineTransform getTransform() {
        return (AffineTransform)this.gradientTransform.clone();
    }
    
    static {
        NO_CYCLE = new CycleMethodEnum();
        REFLECT = new CycleMethodEnum();
        REPEAT = new CycleMethodEnum();
        SRGB = new ColorSpaceEnum();
        LINEAR_RGB = new ColorSpaceEnum();
    }
    
    public static class CycleMethodEnum
    {
    }
    
    public static class ColorSpaceEnum
    {
    }
}
