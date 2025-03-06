// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.color;

import java.awt.color.ICC_Profile;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;

public class ICCColorSpaceExt extends ICC_ColorSpace
{
    public static final int PERCEPTUAL = 0;
    public static final int RELATIVE_COLORIMETRIC = 1;
    public static final int ABSOLUTE_COLORIMETRIC = 2;
    public static final int SATURATION = 3;
    public static final int AUTO = 4;
    static final ColorSpace sRGB;
    int intent;
    
    public ICCColorSpaceExt(final ICC_Profile profile, final int intent) {
        super(profile);
        switch (this.intent = intent) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4: {
                if (intent != 4) {
                    profile.getData(1751474532)[64] = (byte)intent;
                }
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }
    
    public float[] intendedToRGB(final float[] array) {
        switch (this.intent) {
            case 2: {
                return this.absoluteColorimetricToRGB(array);
            }
            case 0:
            case 4: {
                return this.perceptualToRGB(array);
            }
            case 1: {
                return this.relativeColorimetricToRGB(array);
            }
            case 3: {
                return this.saturationToRGB(array);
            }
            default: {
                throw new Error("invalid intent:" + this.intent);
            }
        }
    }
    
    public float[] perceptualToRGB(final float[] colorvalue) {
        return this.toRGB(colorvalue);
    }
    
    public float[] relativeColorimetricToRGB(final float[] colorvalue) {
        return ICCColorSpaceExt.sRGB.fromCIEXYZ(this.toCIEXYZ(colorvalue));
    }
    
    public float[] absoluteColorimetricToRGB(final float[] array) {
        return this.perceptualToRGB(array);
    }
    
    public float[] saturationToRGB(final float[] array) {
        return this.perceptualToRGB(array);
    }
    
    static {
        sRGB = ColorSpace.getInstance(1000);
    }
}
