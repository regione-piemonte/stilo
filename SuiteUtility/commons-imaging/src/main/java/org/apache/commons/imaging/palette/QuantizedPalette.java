// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

import org.apache.commons.imaging.ImageWriteException;
import java.util.List;

public class QuantizedPalette implements Palette
{
    private final int precision;
    private final List<ColorSpaceSubset> subsets;
    private final ColorSpaceSubset[] straight;
    
    public QuantizedPalette(final List<ColorSpaceSubset> subsets, final int precision) {
        this.subsets = subsets;
        this.precision = precision;
        this.straight = new ColorSpaceSubset[1 << precision * 3];
        for (int i = 0; i < subsets.size(); ++i) {
            final ColorSpaceSubset subset = subsets.get(i);
            subset.setIndex(i);
            for (int u = subset.mins[0]; u <= subset.maxs[0]; ++u) {
                for (int j = subset.mins[1]; j <= subset.maxs[1]; ++j) {
                    for (int k = subset.mins[2]; k <= subset.maxs[2]; ++k) {
                        final int index = u << precision * 2 | j << precision * 1 | k << precision * 0;
                        this.straight[index] = subset;
                    }
                }
            }
        }
    }
    
    @Override
    public int getPaletteIndex(final int rgb) throws ImageWriteException {
        final int precisionMask = (1 << this.precision) - 1;
        final int index = (rgb >> 24 - 3 * this.precision & precisionMask << (this.precision << 1)) | (rgb >> 16 - 2 * this.precision & precisionMask << this.precision) | (rgb >> 8 - this.precision & precisionMask);
        return this.straight[index].getIndex();
    }
    
    @Override
    public int getEntry(final int index) {
        final ColorSpaceSubset subset = this.subsets.get(index);
        return subset.rgb;
    }
    
    @Override
    public int length() {
        return this.subsets.size();
    }
}
