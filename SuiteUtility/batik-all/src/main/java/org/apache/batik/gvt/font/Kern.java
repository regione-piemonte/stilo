// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

import java.util.Arrays;

public class Kern
{
    private int[] firstGlyphCodes;
    private int[] secondGlyphCodes;
    private UnicodeRange[] firstUnicodeRanges;
    private UnicodeRange[] secondUnicodeRanges;
    private float kerningAdjust;
    
    public Kern(final int[] firstGlyphCodes, final int[] secondGlyphCodes, final UnicodeRange[] firstUnicodeRanges, final UnicodeRange[] secondUnicodeRanges, final float kerningAdjust) {
        this.firstGlyphCodes = firstGlyphCodes;
        this.secondGlyphCodes = secondGlyphCodes;
        this.firstUnicodeRanges = firstUnicodeRanges;
        this.secondUnicodeRanges = secondUnicodeRanges;
        this.kerningAdjust = kerningAdjust;
        if (firstGlyphCodes != null) {
            Arrays.sort(this.firstGlyphCodes);
        }
        if (secondGlyphCodes != null) {
            Arrays.sort(this.secondGlyphCodes);
        }
    }
    
    public boolean matchesFirstGlyph(final int key, final String s) {
        if (this.firstGlyphCodes != null && Arrays.binarySearch(this.firstGlyphCodes, key) >= 0) {
            return true;
        }
        if (s.length() < 1) {
            return false;
        }
        final char char1 = s.charAt(0);
        for (int i = 0; i < this.firstUnicodeRanges.length; ++i) {
            if (this.firstUnicodeRanges[i].contains(char1)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean matchesFirstGlyph(final int key, final char c) {
        if (this.firstGlyphCodes != null && Arrays.binarySearch(this.firstGlyphCodes, key) >= 0) {
            return true;
        }
        for (int i = 0; i < this.firstUnicodeRanges.length; ++i) {
            if (this.firstUnicodeRanges[i].contains(c)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean matchesSecondGlyph(final int key, final String s) {
        if (this.secondGlyphCodes != null && Arrays.binarySearch(this.secondGlyphCodes, key) >= 0) {
            return true;
        }
        if (s.length() < 1) {
            return false;
        }
        final char char1 = s.charAt(0);
        for (int i = 0; i < this.secondUnicodeRanges.length; ++i) {
            if (this.secondUnicodeRanges[i].contains(char1)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean matchesSecondGlyph(final int key, final char c) {
        if (this.secondGlyphCodes != null && Arrays.binarySearch(this.secondGlyphCodes, key) >= 0) {
            return true;
        }
        for (int i = 0; i < this.secondUnicodeRanges.length; ++i) {
            if (this.secondUnicodeRanges[i].contains(c)) {
                return true;
            }
        }
        return false;
    }
    
    public float getAdjustValue() {
        return this.kerningAdjust;
    }
}
