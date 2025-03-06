// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

import java.io.Serializable;
import java.util.Comparator;
import java.util.logging.Logger;

class ColorSpaceSubset
{
    private static final Logger LOGGER;
    final int[] mins;
    final int[] maxs;
    final int precision;
    final int precisionMask;
    final int total;
    int rgb;
    private int index;
    public static final RgbComparator RGB_COMPARATOR;
    
    ColorSpaceSubset(final int total, final int precision) {
        this.total = total;
        this.precision = precision;
        this.precisionMask = (1 << precision) - 1;
        this.mins = new int[3];
        this.maxs = new int[3];
        for (int i = 0; i < 3; ++i) {
            this.mins[i] = 0;
            this.maxs[i] = this.precisionMask;
        }
        this.rgb = -1;
    }
    
    ColorSpaceSubset(final int total, final int precision, final int[] mins, final int[] maxs) {
        this.total = total;
        this.precision = precision;
        this.mins = mins;
        this.maxs = maxs;
        this.precisionMask = (1 << precision) - 1;
        this.rgb = -1;
    }
    
    public final boolean contains(int red, int green, int blue) {
        red >>= 8 - this.precision;
        if (this.mins[0] > red) {
            return false;
        }
        if (this.maxs[0] < red) {
            return false;
        }
        green >>= 8 - this.precision;
        if (this.mins[1] > green) {
            return false;
        }
        if (this.maxs[1] < green) {
            return false;
        }
        blue >>= 8 - this.precision;
        return this.mins[2] <= blue && this.maxs[2] >= blue;
    }
    
    public void dump(final String prefix) {
        final int rdiff = this.maxs[0] - this.mins[0] + 1;
        final int gdiff = this.maxs[1] - this.mins[1] + 1;
        final int bdiff = this.maxs[2] - this.mins[2] + 1;
        final int colorArea = rdiff * gdiff * bdiff;
        ColorSpaceSubset.LOGGER.fine(prefix + ": [" + Integer.toHexString(this.rgb) + "] total : " + this.total);
        ColorSpaceSubset.LOGGER.fine("\trgb: " + Integer.toHexString(this.rgb) + ", red: " + Integer.toHexString(this.mins[0] << 8 - this.precision) + ", " + Integer.toHexString(this.maxs[0] << 8 - this.precision) + ", green: " + Integer.toHexString(this.mins[1] << 8 - this.precision) + ", " + Integer.toHexString(this.maxs[1] << 8 - this.precision) + ", blue: " + Integer.toHexString(this.mins[2] << 8 - this.precision) + ", " + Integer.toHexString(this.maxs[2] << 8 - this.precision));
        ColorSpaceSubset.LOGGER.fine("\tred: " + this.mins[0] + ", " + this.maxs[0] + ", green: " + this.mins[1] + ", " + this.maxs[1] + ", blue: " + this.mins[2] + ", " + this.maxs[2]);
        ColorSpaceSubset.LOGGER.fine("\trdiff: " + rdiff + ", gdiff: " + gdiff + ", bdiff: " + bdiff + ", colorArea: " + colorArea);
    }
    
    public void dumpJustRGB(final String prefix) {
        ColorSpaceSubset.LOGGER.fine("\trgb: " + Integer.toHexString(this.rgb) + ", red: " + Integer.toHexString(this.mins[0] << 8 - this.precision) + ", " + Integer.toHexString(this.maxs[0] << 8 - this.precision) + ", green: " + Integer.toHexString(this.mins[1] << 8 - this.precision) + ", " + Integer.toHexString(this.maxs[1] << 8 - this.precision) + ", blue: " + Integer.toHexString(this.mins[2] << 8 - this.precision) + ", " + Integer.toHexString(this.maxs[2] << 8 - this.precision));
    }
    
    public int getArea() {
        final int rdiff = this.maxs[0] - this.mins[0] + 1;
        final int gdiff = this.maxs[1] - this.mins[1] + 1;
        final int bdiff = this.maxs[2] - this.mins[2] + 1;
        final int colorArea = rdiff * gdiff * bdiff;
        return colorArea;
    }
    
    public void setAverageRGB(final int[] table) {
        long redsum = 0L;
        long greensum = 0L;
        long bluesum = 0L;
        for (int red = this.mins[0]; red <= this.maxs[0]; ++red) {
            for (int green = this.mins[1]; green <= this.maxs[1]; ++green) {
                for (int blue = this.mins[2]; blue <= this.maxs[2]; ++blue) {
                    final int idx = blue << 2 * this.precision | green << 1 * this.precision | red << 0 * this.precision;
                    final int count = table[idx];
                    redsum += count * (red << 8 - this.precision);
                    greensum += count * (green << 8 - this.precision);
                    bluesum += count * (blue << 8 - this.precision);
                }
            }
        }
        redsum /= this.total;
        greensum /= this.total;
        bluesum /= this.total;
        this.rgb = (int)((redsum & 0xFFL) << 16 | (greensum & 0xFFL) << 8 | (bluesum & 0xFFL) << 0);
    }
    
    public final int getIndex() {
        return this.index;
    }
    
    public final void setIndex(final int i) {
        this.index = i;
    }
    
    static {
        LOGGER = Logger.getLogger(ColorSpaceSubset.class.getName());
        RGB_COMPARATOR = new RgbComparator();
    }
    
    public static class RgbComparator implements Comparator<ColorSpaceSubset>, Serializable
    {
        private static final long serialVersionUID = 509214838111679029L;
        
        @Override
        public int compare(final ColorSpaceSubset c1, final ColorSpaceSubset c2) {
            return c1.rgb - c2.rgb;
        }
    }
}
