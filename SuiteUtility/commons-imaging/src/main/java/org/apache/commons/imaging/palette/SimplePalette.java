// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

public class SimplePalette implements Palette
{
    private final int[] palette;
    
    public SimplePalette(final int[] palette) {
        this.palette = palette;
    }
    
    @Override
    public int getPaletteIndex(final int rgb) {
        for (int i = 0; i < this.palette.length; ++i) {
            if (this.palette[i] == rgb) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public int getEntry(final int index) {
        return this.palette[index];
    }
    
    @Override
    public int length() {
        return this.palette.length;
    }
}
