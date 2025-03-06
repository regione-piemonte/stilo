// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.flow;

import org.apache.batik.gvt.font.GVTGlyphVector;

class GlyphGroupInfo
{
    int start;
    int end;
    int glyphCount;
    int lastGlyphCount;
    boolean hideLast;
    float advance;
    float lastAdvance;
    int range;
    GVTGlyphVector gv;
    boolean[] hide;
    
    public GlyphGroupInfo(final GVTGlyphVector gv, final int start, final int end, final boolean[] array, final boolean hideLast, final float[] array2, final float[] array3, final float[] array4, final boolean[] array5) {
        this.gv = gv;
        this.start = start;
        this.end = end;
        this.hide = new boolean[this.end - this.start + 1];
        this.hideLast = hideLast;
        System.arraycopy(array, this.start, this.hide, 0, this.hide.length);
        float lastAdvance;
        final float advance = (lastAdvance = array2[2 * end + 2] - array2[2 * start]) + array3[end];
        int glyphCount = end - start + 1;
        for (int i = start; i < end; ++i) {
            if (array[i]) {
                --glyphCount;
            }
        }
        int lastGlyphCount = glyphCount;
        for (int j = end; j >= start; --j) {
            lastAdvance += array4[j];
            if (!array5[j]) {
                break;
            }
            --lastGlyphCount;
        }
        if (this.hideLast) {
            --lastGlyphCount;
        }
        this.glyphCount = glyphCount;
        this.lastGlyphCount = lastGlyphCount;
        this.advance = advance;
        this.lastAdvance = lastAdvance;
    }
    
    public GVTGlyphVector getGlyphVector() {
        return this.gv;
    }
    
    public int getStart() {
        return this.start;
    }
    
    public int getEnd() {
        return this.end;
    }
    
    public int getGlyphCount() {
        return this.glyphCount;
    }
    
    public int getLastGlyphCount() {
        return this.lastGlyphCount;
    }
    
    public boolean[] getHide() {
        return this.hide;
    }
    
    public boolean getHideLast() {
        return this.hideLast;
    }
    
    public float getAdvance() {
        return this.advance;
    }
    
    public float getLastAdvance() {
        return this.lastAdvance;
    }
    
    public void setRange(final int range) {
        this.range = range;
    }
    
    public int getRange() {
        return this.range;
    }
}
