// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt;

import java.awt.image.DirectColorModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.color.ColorSpace;
import java.awt.geom.NoninvertibleTransformException;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.image.WritableRaster;
import java.lang.ref.WeakReference;
import java.awt.image.ColorModel;
import java.awt.PaintContext;

abstract class MultipleGradientPaintContext implements PaintContext
{
    protected static final boolean DEBUG = false;
    protected ColorModel dataModel;
    protected ColorModel model;
    private static ColorModel lrgbmodel_NA;
    private static ColorModel srgbmodel_NA;
    private static ColorModel lrgbmodel_A;
    private static ColorModel srgbmodel_A;
    protected static ColorModel cachedModel;
    protected static WeakReference cached;
    protected WritableRaster saved;
    protected MultipleGradientPaint.CycleMethodEnum cycleMethod;
    protected MultipleGradientPaint.ColorSpaceEnum colorSpace;
    protected float a00;
    protected float a01;
    protected float a10;
    protected float a11;
    protected float a02;
    protected float a12;
    protected boolean isSimpleLookup;
    protected boolean hasDiscontinuity;
    protected int fastGradientArraySize;
    protected int[] gradient;
    protected int[][] gradients;
    protected int gradientAverage;
    protected int gradientUnderflow;
    protected int gradientOverflow;
    protected int gradientsLength;
    protected float[] normalizedIntervals;
    protected float[] fractions;
    private int transparencyTest;
    private static final int[] SRGBtoLinearRGB;
    private static final int[] LinearRGBtoSRGB;
    protected static final int GRADIENT_SIZE = 256;
    protected static final int GRADIENT_SIZE_INDEX = 255;
    private static final int MAX_GRADIENT_ARRAY_SIZE = 5000;
    
    protected MultipleGradientPaintContext(final ColorModel colorModel, final Rectangle rectangle, final Rectangle2D rectangle2D, final AffineTransform affineTransform, final RenderingHints renderingHints, final float[] array, final Color[] array2, final MultipleGradientPaint.CycleMethodEnum cycleMethod, final MultipleGradientPaint.ColorSpaceEnum colorSpace) throws NoninvertibleTransformException {
        this.isSimpleLookup = true;
        this.hasDiscontinuity = false;
        boolean b = false;
        boolean b2 = false;
        int length = array.length;
        if (array[0] != 0.0f) {
            b = true;
            ++length;
        }
        if (array[array.length - 1] != 1.0f) {
            b2 = true;
            ++length;
        }
        for (int i = 0; i < array.length - 1; ++i) {
            if (array[i] == array[i + 1]) {
                --length;
            }
        }
        this.fractions = new float[length];
        final Color[] array3 = new Color[length - 1];
        final Color[] array4 = new Color[length - 1];
        this.normalizedIntervals = new float[length - 1];
        this.gradientUnderflow = array2[0].getRGB();
        this.gradientOverflow = array2[array2.length - 1].getRGB();
        int n = 0;
        if (b) {
            this.fractions[0] = 0.0f;
            array3[0] = array2[0];
            array4[0] = array2[0];
            this.normalizedIntervals[0] = array[0];
            ++n;
        }
        for (int j = 0; j < array.length - 1; ++j) {
            if (array[j] == array[j + 1]) {
                if (!array2[j].equals(array2[j + 1])) {
                    this.hasDiscontinuity = true;
                }
            }
            else {
                this.fractions[n] = array[j];
                array3[n] = array2[j];
                array4[n] = array2[j + 1];
                this.normalizedIntervals[n] = array[j + 1] - array[j];
                ++n;
            }
        }
        this.fractions[n] = array[array.length - 1];
        if (b2) {
            array3[n] = (array4[n] = array2[array2.length - 1]);
            this.normalizedIntervals[n] = 1.0f - array[array.length - 1];
            ++n;
            this.fractions[n] = 1.0f;
        }
        final AffineTransform inverse = affineTransform.createInverse();
        final double[] flatmatrix = new double[6];
        inverse.getMatrix(flatmatrix);
        this.a00 = (float)flatmatrix[0];
        this.a10 = (float)flatmatrix[1];
        this.a01 = (float)flatmatrix[2];
        this.a11 = (float)flatmatrix[3];
        this.a02 = (float)flatmatrix[4];
        this.a12 = (float)flatmatrix[5];
        this.cycleMethod = cycleMethod;
        this.colorSpace = colorSpace;
        if (colorModel.getColorSpace() == MultipleGradientPaintContext.lrgbmodel_A.getColorSpace()) {
            this.dataModel = MultipleGradientPaintContext.lrgbmodel_A;
        }
        else {
            if (colorModel.getColorSpace() != MultipleGradientPaintContext.srgbmodel_A.getColorSpace()) {
                throw new IllegalArgumentException("Unsupported ColorSpace for interpolation");
            }
            this.dataModel = MultipleGradientPaintContext.srgbmodel_A;
        }
        this.calculateGradientFractions(array3, array4);
        this.model = GraphicsUtil.coerceColorModel(this.dataModel, colorModel.isAlphaPremultiplied());
    }
    
    protected final void calculateGradientFractions(final Color[] array, final Color[] array2) {
        if (this.colorSpace == LinearGradientPaint.LINEAR_RGB) {
            final int[] srgBtoLinearRGB = MultipleGradientPaintContext.SRGBtoLinearRGB;
            for (int i = 0; i < array.length; ++i) {
                array[i] = interpolateColor(srgBtoLinearRGB, array[i]);
                array2[i] = interpolateColor(srgBtoLinearRGB, array2[i]);
            }
        }
        this.transparencyTest = -16777216;
        if (this.cycleMethod == MultipleGradientPaint.NO_CYCLE) {
            this.transparencyTest &= this.gradientUnderflow;
            this.transparencyTest &= this.gradientOverflow;
        }
        this.gradients = new int[this.fractions.length - 1][];
        this.gradientsLength = this.gradients.length;
        final int length = this.normalizedIntervals.length;
        float n = 1.0f;
        final float[] normalizedIntervals = this.normalizedIntervals;
        for (int j = 0; j < length; ++j) {
            n = ((n > normalizedIntervals[j]) ? normalizedIntervals[j] : n);
        }
        int n2 = 0;
        if (n == 0.0f) {
            n2 = Integer.MAX_VALUE;
            this.hasDiscontinuity = true;
        }
        else {
            for (int k = 0; k < normalizedIntervals.length; ++k) {
                n2 += (int)(normalizedIntervals[k] / n * 256.0f);
            }
        }
        if (n2 > 5000) {
            this.calculateMultipleArrayGradient(array, array2);
            if (this.cycleMethod == MultipleGradientPaint.REPEAT && this.gradients[0][0] != this.gradients[this.gradients.length - 1][255]) {
                this.hasDiscontinuity = true;
            }
        }
        else {
            this.calculateSingleArrayGradient(array, array2, n);
            if (this.cycleMethod == MultipleGradientPaint.REPEAT && this.gradient[0] != this.gradient[this.fastGradientArraySize]) {
                this.hasDiscontinuity = true;
            }
        }
        if (this.transparencyTest >>> 24 == 255) {
            if (this.dataModel.getColorSpace() == MultipleGradientPaintContext.lrgbmodel_NA.getColorSpace()) {
                this.dataModel = MultipleGradientPaintContext.lrgbmodel_NA;
            }
            else if (this.dataModel.getColorSpace() == MultipleGradientPaintContext.srgbmodel_NA.getColorSpace()) {
                this.dataModel = MultipleGradientPaintContext.srgbmodel_NA;
            }
            this.model = this.dataModel;
        }
    }
    
    private static Color interpolateColor(final int[] array, final Color color) {
        final int rgb = color.getRGB();
        return new Color((array[rgb >> 24 & 0xFF] & 0xFF) << 24 | (array[rgb >> 16 & 0xFF] & 0xFF) << 16 | (array[rgb >> 8 & 0xFF] & 0xFF) << 8 | (array[rgb & 0xFF] & 0xFF), true);
    }
    
    private void calculateSingleArrayGradient(final Color[] array, final Color[] array2, final float n) {
        this.isSimpleLookup = true;
        int n2 = 1;
        int n3 = 32768;
        int n4 = 32768;
        int n5 = 32768;
        int n6 = 32768;
        for (int i = 0; i < this.gradients.length; ++i) {
            final int n7 = (int)(this.normalizedIntervals[i] / n * 255.0f);
            n2 += n7;
            this.gradients[i] = new int[n7];
            final int rgb = array[i].getRGB();
            final int rgb2 = array2[i].getRGB();
            this.interpolate(rgb, rgb2, this.gradients[i]);
            final int n8 = this.gradients[i][128];
            final float n9 = this.normalizedIntervals[i];
            n3 += (int)((n8 >> 8 & 0xFF0000) * n9);
            n4 += (int)((n8 & 0xFF0000) * n9);
            n5 += (int)((n8 << 8 & 0xFF0000) * n9);
            n6 += (int)((n8 << 16 & 0xFF0000) * n9);
            this.transparencyTest &= (rgb & rgb2);
        }
        this.gradientAverage = ((n3 & 0xFF0000) << 8 | (n4 & 0xFF0000) | (n5 & 0xFF0000) >> 8 | (n6 & 0xFF0000) >> 16);
        this.gradient = new int[n2];
        int n10 = 0;
        for (int j = 0; j < this.gradients.length; ++j) {
            System.arraycopy(this.gradients[j], 0, this.gradient, n10, this.gradients[j].length);
            n10 += this.gradients[j].length;
        }
        this.gradient[this.gradient.length - 1] = array2[array2.length - 1].getRGB();
        if (this.colorSpace == LinearGradientPaint.LINEAR_RGB) {
            if (this.dataModel.getColorSpace() == ColorSpace.getInstance(1000)) {
                for (int k = 0; k < this.gradient.length; ++k) {
                    this.gradient[k] = convertEntireColorLinearRGBtoSRGB(this.gradient[k]);
                }
                this.gradientAverage = convertEntireColorLinearRGBtoSRGB(this.gradientAverage);
            }
        }
        else if (this.dataModel.getColorSpace() == ColorSpace.getInstance(1004)) {
            for (int l = 0; l < this.gradient.length; ++l) {
                this.gradient[l] = convertEntireColorSRGBtoLinearRGB(this.gradient[l]);
            }
            this.gradientAverage = convertEntireColorSRGBtoLinearRGB(this.gradientAverage);
        }
        this.fastGradientArraySize = this.gradient.length - 1;
    }
    
    private void calculateMultipleArrayGradient(final Color[] array, final Color[] array2) {
        this.isSimpleLookup = false;
        int n = 32768;
        int n2 = 32768;
        int n3 = 32768;
        int n4 = 32768;
        for (int i = 0; i < this.gradients.length; ++i) {
            if (this.normalizedIntervals[i] != 0.0f) {
                this.gradients[i] = new int[256];
                final int rgb = array[i].getRGB();
                final int rgb2 = array2[i].getRGB();
                this.interpolate(rgb, rgb2, this.gradients[i]);
                final int n5 = this.gradients[i][128];
                final float n6 = this.normalizedIntervals[i];
                n += (int)((n5 >> 8 & 0xFF0000) * n6);
                n2 += (int)((n5 & 0xFF0000) * n6);
                n3 += (int)((n5 << 8 & 0xFF0000) * n6);
                n4 += (int)((n5 << 16 & 0xFF0000) * n6);
                this.transparencyTest &= rgb;
                this.transparencyTest &= rgb2;
            }
        }
        this.gradientAverage = ((n & 0xFF0000) << 8 | (n2 & 0xFF0000) | (n3 & 0xFF0000) >> 8 | (n4 & 0xFF0000) >> 16);
        if (this.colorSpace == LinearGradientPaint.LINEAR_RGB) {
            if (this.dataModel.getColorSpace() == ColorSpace.getInstance(1000)) {
                for (int j = 0; j < this.gradients.length; ++j) {
                    for (int k = 0; k < this.gradients[j].length; ++k) {
                        this.gradients[j][k] = convertEntireColorLinearRGBtoSRGB(this.gradients[j][k]);
                    }
                }
                this.gradientAverage = convertEntireColorLinearRGBtoSRGB(this.gradientAverage);
            }
        }
        else if (this.dataModel.getColorSpace() == ColorSpace.getInstance(1004)) {
            for (int l = 0; l < this.gradients.length; ++l) {
                for (int n7 = 0; n7 < this.gradients[l].length; ++n7) {
                    this.gradients[l][n7] = convertEntireColorSRGBtoLinearRGB(this.gradients[l][n7]);
                }
            }
            this.gradientAverage = convertEntireColorSRGBtoLinearRGB(this.gradientAverage);
        }
    }
    
    private void interpolate(final int n, final int n2, final int[] array) {
        int length = array.length;
        final float n3 = 1.0f / length;
        final int n4 = n >> 24 & 0xFF;
        final int n5 = n >> 16 & 0xFF;
        final int n6 = n >> 8 & 0xFF;
        final int n7 = n & 0xFF;
        final int n8 = (n2 >> 24 & 0xFF) - n4;
        final int n9 = (n2 >> 16 & 0xFF) - n5;
        final int n10 = (n2 >> 8 & 0xFF) - n6;
        final int n11 = (n2 & 0xFF) - n7;
        final float n12 = 2.0f * n8 * n3;
        final float n13 = 2.0f * n9 * n3;
        final float n14 = 2.0f * n10 * n3;
        final float n15 = 2.0f * n11 * n3;
        array[0] = n;
        --length;
        array[length] = n2;
        for (int i = 1; i < length; ++i) {
            final float n16 = (float)i;
            array[i] = ((n4 + ((int)(n16 * n12) + 1 >> 1) & 0xFF) << 24 | (n5 + ((int)(n16 * n13) + 1 >> 1) & 0xFF) << 16 | (n6 + ((int)(n16 * n14) + 1 >> 1) & 0xFF) << 8 | (n7 + ((int)(n16 * n15) + 1 >> 1) & 0xFF));
        }
    }
    
    private static int convertEntireColorLinearRGBtoSRGB(final int n) {
        final int n2 = n >> 24 & 0xFF;
        final int n3 = n >> 16 & 0xFF;
        final int n4 = n >> 8 & 0xFF;
        final int n5 = n & 0xFF;
        final int[] linearRGBtoSRGB = MultipleGradientPaintContext.LinearRGBtoSRGB;
        return n2 << 24 | linearRGBtoSRGB[n3] << 16 | linearRGBtoSRGB[n4] << 8 | linearRGBtoSRGB[n5];
    }
    
    private static int convertEntireColorSRGBtoLinearRGB(final int n) {
        final int n2 = n >> 24 & 0xFF;
        final int n3 = n >> 16 & 0xFF;
        final int n4 = n >> 8 & 0xFF;
        final int n5 = n & 0xFF;
        final int[] srgBtoLinearRGB = MultipleGradientPaintContext.SRGBtoLinearRGB;
        return n2 << 24 | srgBtoLinearRGB[n3] << 16 | srgBtoLinearRGB[n4] << 8 | srgBtoLinearRGB[n5];
    }
    
    protected final int indexIntoGradientsArrays(float n) {
        if (this.cycleMethod == MultipleGradientPaint.NO_CYCLE) {
            if (n >= 1.0f) {
                return this.gradientOverflow;
            }
            if (n <= 0.0f) {
                return this.gradientUnderflow;
            }
        }
        else {
            if (this.cycleMethod == MultipleGradientPaint.REPEAT) {
                n -= (int)n;
                if (n < 0.0f) {
                    ++n;
                }
                int n2 = 0;
                int n3 = 0;
                int n4 = 0;
                if (this.isSimpleLookup) {
                    n *= this.gradient.length;
                    final int n5 = (int)n;
                    if (n5 + 1 < this.gradient.length) {
                        return this.gradient[n5];
                    }
                    n2 = (int)((n - n5) * 65536.0f);
                    n3 = this.gradient[n5];
                    n4 = this.gradient[0];
                }
                else {
                    int i = 0;
                    while (i < this.gradientsLength) {
                        if (n < this.fractions[i + 1]) {
                            final float n6 = (n - this.fractions[i]) / this.normalizedIntervals[i] * 256.0f;
                            final int n7 = (int)n6;
                            if (n7 + 1 < this.gradients[i].length || i + 1 < this.gradientsLength) {
                                return this.gradients[i][n7];
                            }
                            n2 = (int)((n6 - n7) * 65536.0f);
                            n3 = this.gradients[i][n7];
                            n4 = this.gradients[0][0];
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                }
                return ((n3 >> 8 & 0xFF0000) + ((n4 >>> 24) - (n3 >>> 24)) * n2 & 0xFF0000) << 8 | ((n3 & 0xFF0000) + ((n4 >> 16 & 0xFF) - (n3 >> 16 & 0xFF)) * n2 & 0xFF0000) | ((n3 << 8 & 0xFF0000) + ((n4 >> 8 & 0xFF) - (n3 >> 8 & 0xFF)) * n2 & 0xFF0000) >> 8 | ((n3 << 16 & 0xFF0000) + ((n4 & 0xFF) - (n3 & 0xFF)) * n2 & 0xFF0000) >> 16;
            }
            if (n < 0.0f) {
                n = -n;
            }
            final int n8 = (int)n;
            n -= n8;
            if ((n8 & 0x1) == 0x1) {
                n = 1.0f - n;
            }
        }
        if (this.isSimpleLookup) {
            return this.gradient[(int)(n * this.fastGradientArraySize)];
        }
        for (int j = 0; j < this.gradientsLength; ++j) {
            if (n < this.fractions[j + 1]) {
                return this.gradients[j][(int)((n - this.fractions[j]) / this.normalizedIntervals[j] * 255.0f)];
            }
        }
        return this.gradientOverflow;
    }
    
    protected final int indexGradientAntiAlias(final float n, float n2) {
        if (this.cycleMethod == MultipleGradientPaint.NO_CYCLE) {
            final float n3 = n - n2 / 2.0f;
            final float n4 = n + n2 / 2.0f;
            if (n3 >= 1.0f) {
                return this.gradientOverflow;
            }
            if (n4 <= 0.0f) {
                return this.gradientUnderflow;
            }
            float n5 = 0.0f;
            float n6 = 0.0f;
            float n7;
            int n8;
            if (n4 >= 1.0f) {
                n5 = (n4 - 1.0f) / n2;
                if (n3 <= 0.0f) {
                    n6 = -n3 / n2;
                    n7 = 1.0f;
                    n8 = this.gradientAverage;
                }
                else {
                    n7 = 1.0f - n3;
                    n8 = this.getAntiAlias(n3, true, 1.0f, false, 1.0f - n3, 1.0f);
                }
            }
            else {
                if (n3 > 0.0f) {
                    return this.getAntiAlias(n3, true, n4, false, n2, 1.0f);
                }
                n6 = -n3 / n2;
                n7 = n4;
                n8 = this.getAntiAlias(0.0f, true, n4, false, n4, 1.0f);
            }
            final int n9 = (int)(65536.0f * n7 / n2);
            int n10 = (n8 >>> 20 & 0xFF0) * n9 >> 16;
            int n11 = (n8 >> 12 & 0xFF0) * n9 >> 16;
            int n12 = (n8 >> 4 & 0xFF0) * n9 >> 16;
            int n13 = (n8 << 4 & 0xFF0) * n9 >> 16;
            if (n6 != 0.0f) {
                final int gradientUnderflow = this.gradientUnderflow;
                final int n14 = (int)(65536.0f * n6);
                n10 += (gradientUnderflow >>> 20 & 0xFF0) * n14 >> 16;
                n11 += (gradientUnderflow >> 12 & 0xFF0) * n14 >> 16;
                n12 += (gradientUnderflow >> 4 & 0xFF0) * n14 >> 16;
                n13 += (gradientUnderflow << 4 & 0xFF0) * n14 >> 16;
            }
            if (n5 != 0.0f) {
                final int gradientOverflow = this.gradientOverflow;
                final int n15 = (int)(65536.0f * n5);
                n10 += (gradientOverflow >>> 20 & 0xFF0) * n15 >> 16;
                n11 += (gradientOverflow >> 12 & 0xFF0) * n15 >> 16;
                n12 += (gradientOverflow >> 4 & 0xFF0) * n15 >> 16;
                n13 += (gradientOverflow << 4 & 0xFF0) * n15 >> 16;
            }
            return (n10 & 0xFF0) << 20 | (n11 & 0xFF0) << 12 | (n12 & 0xFF0) << 4 | (n13 & 0xFF0) >> 4;
        }
        else {
            final int n16 = (int)n2;
            float n17 = 1.0f;
            if (n16 != 0) {
                n2 -= n16;
                n17 = n2 / (n16 + n2);
                if (n17 < 0.1) {
                    return this.gradientAverage;
                }
            }
            if (n2 > 0.99) {
                return this.gradientAverage;
            }
            float n18 = n - n2 / 2.0f;
            float n19 = n + n2 / 2.0f;
            boolean b = true;
            boolean b2 = false;
            float n20;
            float n21;
            if (this.cycleMethod == MultipleGradientPaint.REPEAT) {
                n20 = n18 - (int)n18;
                n21 = n19 - (int)n19;
                if (n20 < 0.0f) {
                    ++n20;
                }
                if (n21 < 0.0f) {
                    ++n21;
                }
            }
            else {
                if (n19 < 0.0f) {
                    n18 = -n18;
                    b = !b;
                    n19 = -n19;
                    b2 = !b2;
                }
                else if (n18 < 0.0f) {
                    n18 = -n18;
                    b = !b;
                }
                final int n22 = (int)n18;
                n20 = n18 - n22;
                final int n23 = (int)n19;
                n21 = n19 - n23;
                if ((n22 & 0x1) == 0x1) {
                    n20 = 1.0f - n20;
                    b = !b;
                }
                if ((n23 & 0x1) == 0x1) {
                    n21 = 1.0f - n21;
                    b2 = !b2;
                }
                if (n20 > n21 && !b && b2) {
                    final float n24 = n20;
                    n20 = n21;
                    n21 = n24;
                    b = true;
                    b2 = false;
                }
            }
            return this.getAntiAlias(n20, b, n21, b2, n2, n17);
        }
    }
    
    private final int getAntiAlias(float n, final boolean b, float n2, final boolean b2, final float n3, final float n4) {
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n33;
        int n34;
        int n35;
        int n36;
        if (this.isSimpleLookup) {
            n *= this.fastGradientArraySize;
            n2 *= this.fastGradientArraySize;
            final int n9 = (int)n;
            final int n10 = (int)n2;
            if (b && !b2 && n9 <= n10) {
                if (n9 == n10) {
                    return this.gradient[n9];
                }
                for (int i = n9 + 1; i < n10; ++i) {
                    final int n11 = this.gradient[i];
                    n5 += (n11 >>> 20 & 0xFF0);
                    n6 += (n11 >>> 12 & 0xFF0);
                    n7 += (n11 >>> 4 & 0xFF0);
                    n8 += (n11 << 4 & 0xFF0);
                }
            }
            else {
                int n12;
                int fastGradientArraySize;
                if (b) {
                    n12 = n9 + 1;
                    fastGradientArraySize = this.fastGradientArraySize;
                }
                else {
                    n12 = 0;
                    fastGradientArraySize = n9;
                }
                for (int j = n12; j < fastGradientArraySize; ++j) {
                    final int n13 = this.gradient[j];
                    n5 += (n13 >>> 20 & 0xFF0);
                    n6 += (n13 >>> 12 & 0xFF0);
                    n7 += (n13 >>> 4 & 0xFF0);
                    n8 += (n13 << 4 & 0xFF0);
                }
                int n14;
                int fastGradientArraySize2;
                if (b2) {
                    n14 = n10 + 1;
                    fastGradientArraySize2 = this.fastGradientArraySize;
                }
                else {
                    n14 = 0;
                    fastGradientArraySize2 = n10;
                }
                for (int k = n14; k < fastGradientArraySize2; ++k) {
                    final int n15 = this.gradient[k];
                    n5 += (n15 >>> 20 & 0xFF0);
                    n6 += (n15 >>> 12 & 0xFF0);
                    n7 += (n15 >>> 4 & 0xFF0);
                    n8 += (n15 << 4 & 0xFF0);
                }
            }
            final int n16 = (int)(65536.0f / (n3 * this.fastGradientArraySize));
            final int n17 = n5 * n16 >> 16;
            final int n18 = n6 * n16 >> 16;
            final int n19 = n7 * n16 >> 16;
            final int n20 = n8 * n16 >> 16;
            int n21;
            if (b) {
                n21 = (int)((1.0f - (n - n9)) * n16);
            }
            else {
                n21 = (int)((n - n9) * n16);
            }
            final int n22 = this.gradient[n9];
            final int n23 = n17 + ((n22 >>> 20 & 0xFF0) * n21 >> 16);
            final int n24 = n18 + ((n22 >>> 12 & 0xFF0) * n21 >> 16);
            final int n25 = n19 + ((n22 >>> 4 & 0xFF0) * n21 >> 16);
            final int n26 = n20 + ((n22 << 4 & 0xFF0) * n21 >> 16);
            int n27;
            if (b2) {
                n27 = (int)((1.0f - (n2 - n10)) * n16);
            }
            else {
                n27 = (int)((n2 - n10) * n16);
            }
            final int n28 = this.gradient[n10];
            final int n29 = n23 + ((n28 >>> 20 & 0xFF0) * n27 >> 16);
            final int n30 = n24 + ((n28 >>> 12 & 0xFF0) * n27 >> 16);
            final int n31 = n25 + ((n28 >>> 4 & 0xFF0) * n27 >> 16);
            final int n32 = n26 + ((n28 << 4 & 0xFF0) * n27 >> 16);
            n33 = n29 + 8 >> 4;
            n34 = n30 + 8 >> 4;
            n35 = n31 + 8 >> 4;
            n36 = n32 + 8 >> 4;
        }
        else {
            int n37 = 0;
            int n38 = 0;
            int n39 = -1;
            int n40 = -1;
            float n41 = 0.0f;
            float n42 = 0.0f;
            for (int l = 0; l < this.gradientsLength; ++l) {
                if (n < this.fractions[l + 1] && n39 == -1) {
                    n39 = l;
                    n41 = (n - this.fractions[l]) / this.normalizedIntervals[l] * 255.0f;
                    n37 = (int)n41;
                    if (n40 != -1) {
                        break;
                    }
                }
                if (n2 < this.fractions[l + 1] && n40 == -1) {
                    n40 = l;
                    n42 = (n2 - this.fractions[l]) / this.normalizedIntervals[l] * 255.0f;
                    n38 = (int)n42;
                    if (n39 != -1) {
                        break;
                    }
                }
            }
            if (n39 == -1) {
                n39 = this.gradients.length - 1;
                n41 = (float)(n37 = 255);
            }
            if (n40 == -1) {
                n40 = this.gradients.length - 1;
                n42 = (float)(n38 = 255);
            }
            if (n39 == n40 && n37 <= n38 && b && !b2) {
                return this.gradients[n39][n37 + n38 + 1 >> 1];
            }
            final int n43 = (int)(65536.0f / n3);
            int n55;
            int n56;
            int n57;
            int n58;
            if (n39 < n40 && b && !b2) {
                final int n44 = (int)(n43 * this.normalizedIntervals[n39] * (255.0f - n41) / 255.0f);
                final int n45 = this.gradients[n39][n37 + 256 >> 1];
                int n46 = n5 + ((n45 >>> 20 & 0xFF0) * n44 >> 16);
                int n47 = n6 + ((n45 >>> 12 & 0xFF0) * n44 >> 16);
                int n48 = n7 + ((n45 >>> 4 & 0xFF0) * n44 >> 16);
                int n49 = n8 + ((n45 << 4 & 0xFF0) * n44 >> 16);
                for (int n50 = n39 + 1; n50 < n40; ++n50) {
                    final int n51 = (int)(n43 * this.normalizedIntervals[n50]);
                    final int n52 = this.gradients[n50][128];
                    n46 += (n52 >>> 20 & 0xFF0) * n51 >> 16;
                    n47 += (n52 >>> 12 & 0xFF0) * n51 >> 16;
                    n48 += (n52 >>> 4 & 0xFF0) * n51 >> 16;
                    n49 += (n52 << 4 & 0xFF0) * n51 >> 16;
                }
                final int n53 = (int)(n43 * this.normalizedIntervals[n40] * n42 / 255.0f);
                final int n54 = this.gradients[n40][n38 + 1 >> 1];
                n55 = n46 + ((n54 >>> 20 & 0xFF0) * n53 >> 16);
                n56 = n47 + ((n54 >>> 12 & 0xFF0) * n53 >> 16);
                n57 = n48 + ((n54 >>> 4 & 0xFF0) * n53 >> 16);
                n58 = n49 + ((n54 << 4 & 0xFF0) * n53 >> 16);
            }
            else {
                int n59;
                int n60;
                if (b) {
                    n59 = (int)(n43 * this.normalizedIntervals[n39] * (255.0f - n41) / 255.0f);
                    n60 = this.gradients[n39][n37 + 256 >> 1];
                }
                else {
                    n59 = (int)(n43 * this.normalizedIntervals[n39] * n41 / 255.0f);
                    n60 = this.gradients[n39][n37 + 1 >> 1];
                }
                final int n61 = n5 + ((n60 >>> 20 & 0xFF0) * n59 >> 16);
                final int n62 = n6 + ((n60 >>> 12 & 0xFF0) * n59 >> 16);
                final int n63 = n7 + ((n60 >>> 4 & 0xFF0) * n59 >> 16);
                final int n64 = n8 + ((n60 << 4 & 0xFF0) * n59 >> 16);
                int n65;
                int n66;
                if (b2) {
                    n65 = (int)(n43 * this.normalizedIntervals[n40] * (255.0f - n42) / 255.0f);
                    n66 = this.gradients[n40][n38 + 256 >> 1];
                }
                else {
                    n65 = (int)(n43 * this.normalizedIntervals[n40] * n42 / 255.0f);
                    n66 = this.gradients[n40][n38 + 1 >> 1];
                }
                n55 = n61 + ((n66 >>> 20 & 0xFF0) * n65 >> 16);
                n56 = n62 + ((n66 >>> 12 & 0xFF0) * n65 >> 16);
                n57 = n63 + ((n66 >>> 4 & 0xFF0) * n65 >> 16);
                n58 = n64 + ((n66 << 4 & 0xFF0) * n65 >> 16);
                int n67;
                int gradientsLength;
                if (b) {
                    n67 = n39 + 1;
                    gradientsLength = this.gradientsLength;
                }
                else {
                    n67 = 0;
                    gradientsLength = n39;
                }
                for (int n68 = n67; n68 < gradientsLength; ++n68) {
                    final int n69 = (int)(n43 * this.normalizedIntervals[n68]);
                    final int n70 = this.gradients[n68][128];
                    n55 += (n70 >>> 20 & 0xFF0) * n69 >> 16;
                    n56 += (n70 >>> 12 & 0xFF0) * n69 >> 16;
                    n57 += (n70 >>> 4 & 0xFF0) * n69 >> 16;
                    n58 += (n70 << 4 & 0xFF0) * n69 >> 16;
                }
                int n71;
                int gradientsLength2;
                if (b2) {
                    n71 = n40 + 1;
                    gradientsLength2 = this.gradientsLength;
                }
                else {
                    n71 = 0;
                    gradientsLength2 = n40;
                }
                for (int n72 = n71; n72 < gradientsLength2; ++n72) {
                    final int n73 = (int)(n43 * this.normalizedIntervals[n72]);
                    final int n74 = this.gradients[n72][128];
                    n55 += (n74 >>> 20 & 0xFF0) * n73 >> 16;
                    n56 += (n74 >>> 12 & 0xFF0) * n73 >> 16;
                    n57 += (n74 >>> 4 & 0xFF0) * n73 >> 16;
                    n58 += (n74 << 4 & 0xFF0) * n73 >> 16;
                }
            }
            n33 = n55 + 8 >> 4;
            n34 = n56 + 8 >> 4;
            n35 = n57 + 8 >> 4;
            n36 = n58 + 8 >> 4;
        }
        if (n4 != 1.0f) {
            final int n75 = (int)(65536.0f * (1.0f - n4));
            final int n76 = (this.gradientAverage >>> 24 & 0xFF) * n75;
            final int n77 = (this.gradientAverage >> 16 & 0xFF) * n75;
            final int n78 = (this.gradientAverage >> 8 & 0xFF) * n75;
            final int n79 = (this.gradientAverage & 0xFF) * n75;
            final int n80 = (int)(n4 * 65536.0f);
            n33 = n33 * n80 + n76 >> 16;
            n34 = n34 * n80 + n77 >> 16;
            n35 = n35 * n80 + n78 >> 16;
            n36 = n36 * n80 + n79 >> 16;
        }
        return n33 << 24 | n34 << 16 | n35 << 8 | n36;
    }
    
    private static int convertSRGBtoLinearRGB(final int n) {
        final float n2 = n / 255.0f;
        float n3;
        if (n2 <= 0.04045f) {
            n3 = n2 / 12.92f;
        }
        else {
            n3 = (float)Math.pow((n2 + 0.055) / 1.055, 2.4);
        }
        return Math.round(n3 * 255.0f);
    }
    
    private static int convertLinearRGBtoSRGB(final int n) {
        final float n2 = n / 255.0f;
        float n3;
        if (n2 <= 0.0031308f) {
            n3 = n2 * 12.92f;
        }
        else {
            n3 = 1.055f * (float)Math.pow(n2, 0.4166666666666667) - 0.055f;
        }
        return Math.round(n3 * 255.0f);
    }
    
    public final Raster getRaster(final int n, final int n2, final int w, final int h) {
        if (w == 0 || h == 0) {
            return null;
        }
        WritableRaster writableRaster = this.saved;
        if (writableRaster == null || writableRaster.getWidth() < w || writableRaster.getHeight() < h) {
            final WritableRaster cachedRaster = getCachedRaster(this.dataModel, w, h);
            this.saved = cachedRaster;
            writableRaster = cachedRaster.createWritableChild(cachedRaster.getMinX(), cachedRaster.getMinY(), w, h, 0, 0, null);
        }
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        this.fillRaster(dataBufferInt.getBankData()[0], dataBufferInt.getOffset(), ((SinglePixelPackedSampleModel)writableRaster.getSampleModel()).getScanlineStride() - w, n, n2, w, h);
        GraphicsUtil.coerceData(writableRaster, this.dataModel, this.model.isAlphaPremultiplied());
        return writableRaster;
    }
    
    protected abstract void fillRaster(final int[] p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    protected static final synchronized WritableRaster getCachedRaster(final ColorModel colorModel, int w, int h) {
        if (colorModel == MultipleGradientPaintContext.cachedModel && MultipleGradientPaintContext.cached != null) {
            final WritableRaster writableRaster = (WritableRaster)MultipleGradientPaintContext.cached.get();
            if (writableRaster != null && writableRaster.getWidth() >= w && writableRaster.getHeight() >= h) {
                MultipleGradientPaintContext.cached = null;
                return writableRaster;
            }
        }
        if (w < 32) {
            w = 32;
        }
        if (h < 32) {
            h = 32;
        }
        return colorModel.createCompatibleWritableRaster(w, h);
    }
    
    protected static final synchronized void putCachedRaster(final ColorModel cachedModel, final WritableRaster referent) {
        if (MultipleGradientPaintContext.cached != null) {
            final WritableRaster writableRaster = (WritableRaster)MultipleGradientPaintContext.cached.get();
            if (writableRaster != null) {
                final int width = writableRaster.getWidth();
                final int height = writableRaster.getHeight();
                final int width2 = referent.getWidth();
                final int height2 = referent.getHeight();
                if (width >= width2 && height >= height2) {
                    return;
                }
                if (width * height >= width2 * height2) {
                    return;
                }
            }
        }
        MultipleGradientPaintContext.cachedModel = cachedModel;
        MultipleGradientPaintContext.cached = new WeakReference((T)referent);
    }
    
    public final void dispose() {
        if (this.saved != null) {
            putCachedRaster(this.model, this.saved);
            this.saved = null;
        }
    }
    
    public final ColorModel getColorModel() {
        return this.model;
    }
    
    static {
        MultipleGradientPaintContext.lrgbmodel_NA = new DirectColorModel(ColorSpace.getInstance(1004), 24, 16711680, 65280, 255, 0, false, 3);
        MultipleGradientPaintContext.srgbmodel_NA = new DirectColorModel(ColorSpace.getInstance(1000), 24, 16711680, 65280, 255, 0, false, 3);
        MultipleGradientPaintContext.lrgbmodel_A = new DirectColorModel(ColorSpace.getInstance(1004), 32, 16711680, 65280, 255, -16777216, false, 3);
        MultipleGradientPaintContext.srgbmodel_A = new DirectColorModel(ColorSpace.getInstance(1000), 32, 16711680, 65280, 255, -16777216, false, 3);
        SRGBtoLinearRGB = new int[256];
        LinearRGBtoSRGB = new int[256];
        for (int i = 0; i < 256; ++i) {
            MultipleGradientPaintContext.SRGBtoLinearRGB[i] = convertSRGBtoLinearRGB(i);
            MultipleGradientPaintContext.LinearRGBtoSRGB[i] = convertLinearRGBtoSRGB(i);
        }
    }
}
