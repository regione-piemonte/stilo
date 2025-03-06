// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

import java.awt.image.ColorModel;
import java.util.Set;
import java.util.HashSet;
import org.apache.commons.imaging.ImageWriteException;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.logging.Level;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class PaletteFactory
{
    private static final Logger LOGGER;
    public static final int COMPONENTS = 3;
    
    public Palette makeExactRgbPaletteFancy(final BufferedImage src) {
        final byte[] rgbmap = new byte[2097152];
        final int width = src.getWidth();
        for (int height = src.getHeight(), y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int argb = src.getRGB(x, y);
                final int rggbb = 0x1FFFFF & argb;
                final int highred = 0x7 & argb >> 21;
                final int mask = 1 << highred;
                final byte[] array = rgbmap;
                final int n = rggbb;
                array[n] |= (byte)mask;
            }
        }
        int count = 0;
        for (final byte element : rgbmap) {
            final int eight = 0xFF & element;
            count += Integer.bitCount(eight);
        }
        if (PaletteFactory.LOGGER.isLoggable(Level.FINEST)) {
            PaletteFactory.LOGGER.finest("Used colors: " + count);
        }
        final int[] colormap = new int[count];
        int mapsize = 0;
        for (int i = 0; i < rgbmap.length; ++i) {
            final int eight2 = 0xFF & rgbmap[i];
            int mask = 128;
            for (int j = 0; j < 8; ++j) {
                final int bit = eight2 & mask;
                mask >>>= 1;
                if (bit > 0) {
                    final int rgb = i | 7 - j << 21;
                    colormap[mapsize++] = rgb;
                }
            }
        }
        Arrays.sort(colormap);
        return new SimplePalette(colormap);
    }
    
    private int pixelToQuantizationTableIndex(int argb, final int precision) {
        int result = 0;
        final int precisionMask = (1 << precision) - 1;
        for (int i = 0; i < 3; ++i) {
            int sample = argb & 0xFF;
            argb >>= 8;
            sample >>= 8 - precision;
            result = (result << precision | (sample & precisionMask));
        }
        return result;
    }
    
    private int getFrequencyTotal(final int[] table, final int[] mins, final int[] maxs, final int precision) {
        int sum = 0;
        for (int blue = mins[2]; blue <= maxs[2]; ++blue) {
            final int b = blue << 2 * precision;
            for (int green = mins[1]; green <= maxs[1]; ++green) {
                final int g = green << 1 * precision;
                for (int red = mins[0]; red <= maxs[0]; ++red) {
                    final int index = b | g | red;
                    sum += table[index];
                }
            }
        }
        return sum;
    }
    
    private DivisionCandidate finishDivision(final ColorSpaceSubset subset, final int component, final int precision, final int sum, final int slice) {
        if (PaletteFactory.LOGGER.isLoggable(Level.FINEST)) {
            subset.dump("trying (" + component + "): ");
        }
        final int total = subset.total;
        if (slice < subset.mins[component] || slice >= subset.maxs[component]) {
            return null;
        }
        if (sum < 1 || sum >= total) {
            return null;
        }
        final int remainder = total - sum;
        if (remainder < 1 || remainder >= total) {
            return null;
        }
        final int[] sliceMins = new int[subset.mins.length];
        System.arraycopy(subset.mins, 0, sliceMins, 0, subset.mins.length);
        final int[] sliceMaxs = new int[subset.maxs.length];
        System.arraycopy(subset.maxs, 0, sliceMaxs, 0, subset.maxs.length);
        sliceMins[component] = (sliceMaxs[component] = slice) + 1;
        if (PaletteFactory.LOGGER.isLoggable(Level.FINEST)) {
            PaletteFactory.LOGGER.finest("total: " + total);
            PaletteFactory.LOGGER.finest("first total: " + sum);
            PaletteFactory.LOGGER.finest("second total: " + (total - sum));
            PaletteFactory.LOGGER.finest("slice: " + slice);
        }
        final ColorSpaceSubset first = new ColorSpaceSubset(sum, precision, subset.mins, sliceMaxs);
        final ColorSpaceSubset second = new ColorSpaceSubset(total - sum, precision, sliceMins, subset.maxs);
        return new DivisionCandidate(first, second);
    }
    
    private List<DivisionCandidate> divideSubset2(final int[] table, final ColorSpaceSubset subset, final int component, final int precision) {
        if (PaletteFactory.LOGGER.isLoggable(Level.FINEST)) {
            subset.dump("trying (" + component + "): ");
        }
        final int total = subset.total;
        final int[] sliceMins = new int[subset.mins.length];
        System.arraycopy(subset.mins, 0, sliceMins, 0, subset.mins.length);
        final int[] sliceMaxs = new int[subset.maxs.length];
        System.arraycopy(subset.maxs, 0, sliceMaxs, 0, subset.maxs.length);
        int sum1 = 0;
        int last = 0;
        int slice1;
        for (slice1 = subset.mins[component]; slice1 != subset.maxs[component] + 1; ++slice1) {
            sliceMaxs[component] = (sliceMins[component] = slice1);
            last = this.getFrequencyTotal(table, sliceMins, sliceMaxs, precision);
            sum1 += last;
            if (sum1 >= total / 2) {
                break;
            }
        }
        final int sum2 = sum1 - last;
        final int slice2 = slice1 - 1;
        final DivisionCandidate dc1 = this.finishDivision(subset, component, precision, sum1, slice1);
        final DivisionCandidate dc2 = this.finishDivision(subset, component, precision, sum2, slice2);
        final List<DivisionCandidate> result = new ArrayList<DivisionCandidate>();
        if (dc1 != null) {
            result.add(dc1);
        }
        if (dc2 != null) {
            result.add(dc2);
        }
        return result;
    }
    
    private DivisionCandidate divideSubset2(final int[] table, final ColorSpaceSubset subset, final int precision) {
        final List<DivisionCandidate> dcs = new ArrayList<DivisionCandidate>();
        dcs.addAll(this.divideSubset2(table, subset, 0, precision));
        dcs.addAll(this.divideSubset2(table, subset, 1, precision));
        dcs.addAll(this.divideSubset2(table, subset, 2, precision));
        DivisionCandidate bestV = null;
        double bestScore = Double.MAX_VALUE;
        for (final DivisionCandidate dc : dcs) {
            final ColorSpaceSubset first = dc.dst_a;
            final ColorSpaceSubset second = dc.dst_b;
            final int area1 = first.total;
            final int area2 = second.total;
            final int diff = Math.abs(area1 - area2);
            final double score = diff / (double)Math.max(area1, area2);
            if (bestV == null) {
                bestV = dc;
                bestScore = score;
            }
            else {
                if (score >= bestScore) {
                    continue;
                }
                bestV = dc;
                bestScore = score;
            }
        }
        return bestV;
    }
    
    private List<ColorSpaceSubset> divide(final List<ColorSpaceSubset> v, final int desiredCount, final int[] table, final int precision) {
        final List<ColorSpaceSubset> ignore = new ArrayList<ColorSpaceSubset>();
        while (true) {
            int maxArea = -1;
            ColorSpaceSubset maxSubset = null;
            for (final ColorSpaceSubset subset : v) {
                if (ignore.contains(subset)) {
                    continue;
                }
                final int area = subset.total;
                if (maxSubset == null) {
                    maxSubset = subset;
                    maxArea = area;
                }
                else {
                    if (area <= maxArea) {
                        continue;
                    }
                    maxSubset = subset;
                    maxArea = area;
                }
            }
            if (maxSubset == null) {
                return v;
            }
            if (PaletteFactory.LOGGER.isLoggable(Level.FINEST)) {
                PaletteFactory.LOGGER.finest("\tarea: " + maxArea);
            }
            final DivisionCandidate dc = this.divideSubset2(table, maxSubset, precision);
            if (dc != null) {
                v.remove(maxSubset);
                v.add(dc.dst_a);
                v.add(dc.dst_b);
            }
            else {
                ignore.add(maxSubset);
            }
            if (v.size() == desiredCount) {
                return v;
            }
        }
    }
    
    public Palette makeQuantizedRgbPalette(final BufferedImage src, final int max) {
        final int precision = 6;
        final int tableScale = 18;
        final int tableSize = 262144;
        final int[] table = new int[262144];
        final int width = src.getWidth();
        final int height = src.getHeight();
        List<ColorSpaceSubset> subsets = new ArrayList<ColorSpaceSubset>();
        final ColorSpaceSubset all = new ColorSpaceSubset(width * height, 6);
        subsets.add(all);
        if (PaletteFactory.LOGGER.isLoggable(Level.FINEST)) {
            final int preTotal = this.getFrequencyTotal(table, all.mins, all.maxs, 6);
            PaletteFactory.LOGGER.finest("pre total: " + preTotal);
        }
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int argb = src.getRGB(x, y);
                final int index = this.pixelToQuantizationTableIndex(argb, 6);
                final int[] array = table;
                final int n = index;
                ++array[n];
            }
        }
        if (PaletteFactory.LOGGER.isLoggable(Level.FINEST)) {
            final int allTotal = this.getFrequencyTotal(table, all.mins, all.maxs, 6);
            PaletteFactory.LOGGER.finest("all total: " + allTotal);
            PaletteFactory.LOGGER.finest("width * height: " + width * height);
        }
        subsets = this.divide(subsets, max, table, 6);
        if (PaletteFactory.LOGGER.isLoggable(Level.FINEST)) {
            PaletteFactory.LOGGER.finest("subsets: " + subsets.size());
            PaletteFactory.LOGGER.finest("width*height: " + width * height);
        }
        for (int i = 0; i < subsets.size(); ++i) {
            final ColorSpaceSubset subset = subsets.get(i);
            subset.setAverageRGB(table);
            if (PaletteFactory.LOGGER.isLoggable(Level.FINEST)) {
                subset.dump(i + ": ");
            }
        }
        Collections.sort(subsets, ColorSpaceSubset.RGB_COMPARATOR);
        return new QuantizedPalette(subsets, 6);
    }
    
    public Palette makeQuantizedRgbaPalette(final BufferedImage src, final boolean transparent, final int max) throws ImageWriteException {
        return new MedianCutQuantizer(!transparent).process(src, max, new LongestAxisMedianCut());
    }
    
    public SimplePalette makeExactRgbPaletteSimple(final BufferedImage src, final int max) {
        final Set<Integer> rgbs = new HashSet<Integer>();
        final int width = src.getWidth();
        for (int height = src.getHeight(), y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int argb = src.getRGB(x, y);
                final int rgb = 0xFFFFFF & argb;
                if (rgbs.add(rgb) && rgbs.size() > max) {
                    return null;
                }
            }
        }
        final int[] result = new int[rgbs.size()];
        int next = 0;
        final Iterator<Integer> iterator = rgbs.iterator();
        while (iterator.hasNext()) {
            final int rgb = iterator.next();
            result[next++] = rgb;
        }
        Arrays.sort(result);
        return new SimplePalette(result);
    }
    
    public boolean isGrayscale(final BufferedImage src) {
        final int width = src.getWidth();
        final int height = src.getHeight();
        if (6 == src.getColorModel().getColorSpace().getType()) {
            return true;
        }
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int argb = src.getRGB(x, y);
                final int red = 0xFF & argb >> 16;
                final int green = 0xFF & argb >> 8;
                final int blue = 0xFF & argb >> 0;
                if (red != green || red != blue) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean hasTransparency(final BufferedImage src) {
        return this.hasTransparency(src, 255);
    }
    
    public boolean hasTransparency(final BufferedImage src, final int threshold) {
        final int width = src.getWidth();
        final int height = src.getHeight();
        if (!src.getColorModel().hasAlpha()) {
            return false;
        }
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int argb = src.getRGB(x, y);
                final int alpha = 0xFF & argb >> 24;
                if (alpha < threshold) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public int countTrasparentColors(final int[] rgbs) {
        int first = -1;
        for (final int rgb : rgbs) {
            final int alpha = 0xFF & rgb >> 24;
            if (alpha < 255) {
                if (first < 0) {
                    first = rgb;
                }
                else if (rgb != first) {
                    return 2;
                }
            }
        }
        if (first < 0) {
            return 0;
        }
        return 1;
    }
    
    public int countTransparentColors(final BufferedImage src) {
        final ColorModel cm = src.getColorModel();
        if (!cm.hasAlpha()) {
            return 0;
        }
        final int width = src.getWidth();
        final int height = src.getHeight();
        int first = -1;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int rgb = src.getRGB(x, y);
                final int alpha = 0xFF & rgb >> 24;
                if (alpha < 255) {
                    if (first < 0) {
                        first = rgb;
                    }
                    else if (rgb != first) {
                        return 2;
                    }
                }
            }
        }
        if (first < 0) {
            return 0;
        }
        return 1;
    }
    
    static {
        LOGGER = Logger.getLogger(PaletteFactory.class.getName());
    }
    
    private static class DivisionCandidate
    {
        private final ColorSpaceSubset dst_a;
        private final ColorSpaceSubset dst_b;
        
        DivisionCandidate(final ColorSpaceSubset dst_a, final ColorSpaceSubset dst_b) {
            this.dst_a = dst_a;
            this.dst_b = dst_b;
        }
    }
}
