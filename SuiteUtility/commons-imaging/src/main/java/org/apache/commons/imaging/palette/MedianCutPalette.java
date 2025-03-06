// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

class MedianCutPalette extends SimplePalette
{
    private final ColorGroup root;
    
    MedianCutPalette(final ColorGroup root, final int[] palette) {
        super(palette);
        this.root = root;
    }
    
    @Override
    public int getPaletteIndex(final int rgb) {
        ColorGroup cg;
        for (cg = this.root; cg.cut != null; cg = cg.cut.getColorGroup(rgb)) {}
        return cg.paletteIndex;
    }
}
