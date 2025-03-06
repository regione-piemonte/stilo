// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt;

import java.awt.RenderingHints;

public final class ColorSpaceHintKey extends RenderingHints.Key
{
    public static Object VALUE_COLORSPACE_ARGB;
    public static Object VALUE_COLORSPACE_RGB;
    public static Object VALUE_COLORSPACE_GREY;
    public static Object VALUE_COLORSPACE_AGREY;
    public static Object VALUE_COLORSPACE_ALPHA;
    public static Object VALUE_COLORSPACE_ALPHA_CONVERT;
    public static final String PROPERTY_COLORSPACE = "org.apache.batik.gvt.filter.Colorspace";
    
    ColorSpaceHintKey(final int privatekey) {
        super(privatekey);
    }
    
    public boolean isCompatibleValue(final Object o) {
        return o == ColorSpaceHintKey.VALUE_COLORSPACE_ARGB || o == ColorSpaceHintKey.VALUE_COLORSPACE_RGB || o == ColorSpaceHintKey.VALUE_COLORSPACE_GREY || o == ColorSpaceHintKey.VALUE_COLORSPACE_AGREY || o == ColorSpaceHintKey.VALUE_COLORSPACE_ALPHA || o == ColorSpaceHintKey.VALUE_COLORSPACE_ALPHA_CONVERT;
    }
    
    static {
        ColorSpaceHintKey.VALUE_COLORSPACE_ARGB = new Object();
        ColorSpaceHintKey.VALUE_COLORSPACE_RGB = new Object();
        ColorSpaceHintKey.VALUE_COLORSPACE_GREY = new Object();
        ColorSpaceHintKey.VALUE_COLORSPACE_AGREY = new Object();
        ColorSpaceHintKey.VALUE_COLORSPACE_ALPHA = new Object();
        ColorSpaceHintKey.VALUE_COLORSPACE_ALPHA_CONVERT = new Object();
    }
}
