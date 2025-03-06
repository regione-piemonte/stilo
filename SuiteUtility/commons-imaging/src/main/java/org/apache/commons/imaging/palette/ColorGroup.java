// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

import java.util.Iterator;
import org.apache.commons.imaging.ImageWriteException;
import java.util.List;

class ColorGroup
{
    public ColorGroupCut cut;
    public int paletteIndex;
    public final List<ColorCount> colorCounts;
    public final boolean ignoreAlpha;
    public int minRed;
    public int maxRed;
    public int minGreen;
    public int maxGreen;
    public int minBlue;
    public int maxBlue;
    public int minAlpha;
    public int maxAlpha;
    public final int alphaDiff;
    public final int redDiff;
    public final int greenDiff;
    public final int blueDiff;
    public final int maxDiff;
    public final int diffTotal;
    public final int totalPoints;
    
    ColorGroup(final List<ColorCount> colorCounts, final boolean ignoreAlpha) throws ImageWriteException {
        this.paletteIndex = -1;
        this.minRed = Integer.MAX_VALUE;
        this.maxRed = Integer.MIN_VALUE;
        this.minGreen = Integer.MAX_VALUE;
        this.maxGreen = Integer.MIN_VALUE;
        this.minBlue = Integer.MAX_VALUE;
        this.maxBlue = Integer.MIN_VALUE;
        this.minAlpha = Integer.MAX_VALUE;
        this.maxAlpha = Integer.MIN_VALUE;
        this.colorCounts = colorCounts;
        this.ignoreAlpha = ignoreAlpha;
        if (colorCounts.size() < 1) {
            throw new ImageWriteException("empty color_group");
        }
        int total = 0;
        for (final ColorCount color : colorCounts) {
            total += color.count;
            this.minAlpha = Math.min(this.minAlpha, color.alpha);
            this.maxAlpha = Math.max(this.maxAlpha, color.alpha);
            this.minRed = Math.min(this.minRed, color.red);
            this.maxRed = Math.max(this.maxRed, color.red);
            this.minGreen = Math.min(this.minGreen, color.green);
            this.maxGreen = Math.max(this.maxGreen, color.green);
            this.minBlue = Math.min(this.minBlue, color.blue);
            this.maxBlue = Math.max(this.maxBlue, color.blue);
        }
        this.totalPoints = total;
        this.alphaDiff = this.maxAlpha - this.minAlpha;
        this.redDiff = this.maxRed - this.minRed;
        this.greenDiff = this.maxGreen - this.minGreen;
        this.blueDiff = this.maxBlue - this.minBlue;
        this.maxDiff = Math.max(ignoreAlpha ? this.redDiff : Math.max(this.alphaDiff, this.redDiff), Math.max(this.greenDiff, this.blueDiff));
        this.diffTotal = (ignoreAlpha ? 0 : this.alphaDiff) + this.redDiff + this.greenDiff + this.blueDiff;
    }
    
    public boolean contains(final int argb) {
        final int alpha = 0xFF & argb >> 24;
        final int red = 0xFF & argb >> 16;
        final int green = 0xFF & argb >> 8;
        final int blue = 0xFF & argb >> 0;
        return (this.ignoreAlpha || (alpha >= this.minAlpha && alpha <= this.maxAlpha)) && red >= this.minRed && red <= this.maxRed && green >= this.minGreen && green <= this.maxGreen && blue >= this.minBlue && blue <= this.maxBlue;
    }
    
    public int getMedianValue() {
        long countTotal = 0L;
        long alphaTotal = 0L;
        long redTotal = 0L;
        long greenTotal = 0L;
        long blueTotal = 0L;
        for (final ColorCount color : this.colorCounts) {
            countTotal += color.count;
            alphaTotal += color.count * color.alpha;
            redTotal += color.count * color.red;
            greenTotal += color.count * color.green;
            blueTotal += color.count * color.blue;
        }
        final int alpha = this.ignoreAlpha ? 255 : ((int)Math.round(alphaTotal / (double)countTotal));
        final int red = (int)Math.round(redTotal / (double)countTotal);
        final int green = (int)Math.round(greenTotal / (double)countTotal);
        final int blue = (int)Math.round(blueTotal / (double)countTotal);
        return alpha << 24 | red << 16 | green << 8 | blue;
    }
    
    @Override
    public String toString() {
        return "{ColorGroup. minRed: " + Integer.toHexString(this.minRed) + ", maxRed: " + Integer.toHexString(this.maxRed) + ", minGreen: " + Integer.toHexString(this.minGreen) + ", maxGreen: " + Integer.toHexString(this.maxGreen) + ", minBlue: " + Integer.toHexString(this.minBlue) + ", maxBlue: " + Integer.toHexString(this.maxBlue) + ", minAlpha: " + Integer.toHexString(this.minAlpha) + ", maxAlpha: " + Integer.toHexString(this.maxAlpha) + ", maxDiff: " + Integer.toHexString(this.maxDiff) + ", diffTotal: " + this.diffTotal + "}";
    }
}
