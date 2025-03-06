// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

import org.apache.commons.imaging.ImageWriteException;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import org.apache.commons.imaging.internal.Debug;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;

public class MedianCutQuantizer
{
    private final boolean ignoreAlpha;
    
    public MedianCutQuantizer(final boolean ignoreAlpha) {
        this.ignoreAlpha = ignoreAlpha;
    }
    
    private Map<Integer, ColorCount> groupColors1(final BufferedImage image, final int max, final int mask) {
        final Map<Integer, ColorCount> colorMap = new HashMap<Integer, ColorCount>();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final int[] row = new int[width];
        for (int y = 0; y < height; ++y) {
            image.getRGB(0, y, width, 1, row, 0, width);
            for (int argb : row) {
                if (this.ignoreAlpha) {
                    argb &= 0xFFFFFF;
                }
                argb &= mask;
                ColorCount color = colorMap.get(argb);
                if (color == null) {
                    color = new ColorCount(argb);
                    colorMap.put(argb, color);
                    if (colorMap.keySet().size() > max) {
                        return null;
                    }
                }
                final ColorCount colorCount = color;
                ++colorCount.count;
            }
        }
        return colorMap;
    }
    
    public Map<Integer, ColorCount> groupColors(final BufferedImage image, final int maxColors) {
        final int max = Integer.MAX_VALUE;
        for (int i = 0; i < 8; ++i) {
            int mask = 0xFF & 255 << i;
            mask = (mask | mask << 8 | mask << 16 | mask << 24);
            Debug.debug("mask(" + i + "): " + mask + " (" + Integer.toHexString(mask) + ")");
            final Map<Integer, ColorCount> result = this.groupColors1(image, Integer.MAX_VALUE, mask);
            if (result != null) {
                return result;
            }
        }
        throw new Error("");
    }
    
    public Palette process(final BufferedImage image, final int maxColors, final MedianCut medianCut) throws ImageWriteException {
        final Map<Integer, ColorCount> colorMap = this.groupColors(image, maxColors);
        final int discreteColors = colorMap.keySet().size();
        if (discreteColors <= maxColors) {
            Debug.debug("lossless palette: " + discreteColors);
            final int[] palette = new int[discreteColors];
            final List<ColorCount> colorCounts = new ArrayList<ColorCount>(colorMap.values());
            for (int i = 0; i < colorCounts.size(); ++i) {
                final ColorCount colorCount = colorCounts.get(i);
                palette[i] = colorCount.argb;
                if (this.ignoreAlpha) {
                    final int[] array = palette;
                    final int n = i;
                    array[n] |= 0xFF000000;
                }
            }
            return new SimplePalette(palette);
        }
        Debug.debug("discrete colors: " + discreteColors);
        final List<ColorGroup> colorGroups = new ArrayList<ColorGroup>();
        final ColorGroup root = new ColorGroup(new ArrayList<ColorCount>(colorMap.values()), this.ignoreAlpha);
        colorGroups.add(root);
        while (colorGroups.size() < maxColors && medianCut.performNextMedianCut(colorGroups, this.ignoreAlpha)) {}
        final int paletteSize = colorGroups.size();
        Debug.debug("palette size: " + paletteSize);
        final int[] palette2 = new int[paletteSize];
        for (int j = 0; j < colorGroups.size(); ++j) {
            final ColorGroup colorGroup = colorGroups.get(j);
            palette2[j] = colorGroup.getMedianValue();
            colorGroup.paletteIndex = j;
            if (colorGroup.colorCounts.size() < 1) {
                throw new ImageWriteException("empty color_group: " + colorGroup);
            }
        }
        if (paletteSize > discreteColors) {
            throw new ImageWriteException("palette_size > discrete_colors");
        }
        return new MedianCutPalette(root, palette2);
    }
}
