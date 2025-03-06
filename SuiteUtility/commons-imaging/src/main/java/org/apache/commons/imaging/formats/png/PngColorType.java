// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png;

import java.util.Arrays;

public enum PngColorType
{
    GREYSCALE(0, true, false, 1, new int[] { 1, 2, 4, 8, 16 }), 
    TRUE_COLOR(2, false, false, 3, new int[] { 8, 16 }), 
    INDEXED_COLOR(3, false, false, 1, new int[] { 1, 2, 4, 8 }), 
    GREYSCALE_WITH_ALPHA(4, true, true, 2, new int[] { 8, 16 }), 
    TRUE_COLOR_WITH_ALPHA(6, false, true, 4, new int[] { 8, 16 });
    
    private final int value;
    private final boolean greyscale;
    private final boolean alpha;
    private final int samplesPerPixel;
    private final int[] allowedBitDepths;
    
    private PngColorType(final int value, final boolean greyscale, final boolean alpha, final int samplesPerPixel, final int[] allowedBitDepths) {
        this.value = value;
        this.greyscale = greyscale;
        this.alpha = alpha;
        this.samplesPerPixel = samplesPerPixel;
        this.allowedBitDepths = allowedBitDepths;
    }
    
    int getValue() {
        return this.value;
    }
    
    boolean isGreyscale() {
        return this.greyscale;
    }
    
    boolean hasAlpha() {
        return this.alpha;
    }
    
    int getSamplesPerPixel() {
        return this.samplesPerPixel;
    }
    
    boolean isBitDepthAllowed(final int bitDepth) {
        return Arrays.binarySearch(this.allowedBitDepths, bitDepth) >= 0;
    }
    
    public static PngColorType getColorType(final int value) {
        for (final PngColorType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }
    
    static PngColorType getColorType(final boolean alpha, final boolean grayscale) {
        if (grayscale) {
            if (alpha) {
                return PngColorType.GREYSCALE_WITH_ALPHA;
            }
            return PngColorType.GREYSCALE;
        }
        else {
            if (alpha) {
                return PngColorType.TRUE_COLOR_WITH_ALPHA;
            }
            return PngColorType.TRUE_COLOR;
        }
    }
}
