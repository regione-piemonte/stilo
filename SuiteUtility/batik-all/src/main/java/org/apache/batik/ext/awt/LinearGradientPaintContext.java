// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt;

import java.awt.geom.NoninvertibleTransformException;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.image.ColorModel;

final class LinearGradientPaintContext extends MultipleGradientPaintContext
{
    private float dgdX;
    private float dgdY;
    private float gc;
    private float pixSz;
    private static final int DEFAULT_IMPL = 1;
    private static final int ANTI_ALIAS_IMPL = 3;
    private int fillMethod;
    
    public LinearGradientPaintContext(final ColorModel colorModel, final Rectangle rectangle, final Rectangle2D rectangle2D, final AffineTransform affineTransform, final RenderingHints renderingHints, final Point2D point2D, final Point2D point2D2, final float[] array, final Color[] array2, final MultipleGradientPaint.CycleMethodEnum cycleMethodEnum, final MultipleGradientPaint.ColorSpaceEnum colorSpaceEnum) throws NoninvertibleTransformException {
        super(colorModel, rectangle, rectangle2D, affineTransform, renderingHints, array, array2, cycleMethodEnum, colorSpaceEnum);
        final Point2D.Float float1 = new Point2D.Float((float)point2D.getX(), (float)point2D.getY());
        final Point2D.Float float2 = new Point2D.Float((float)point2D2.getX(), (float)point2D2.getY());
        final float n = float2.x - float1.x;
        final float n2 = float2.y - float1.y;
        final float n3 = n * n + n2 * n2;
        final float n4 = n / n3;
        final float n5 = n2 / n3;
        this.dgdX = this.a00 * n4 + this.a10 * n5;
        this.dgdY = this.a01 * n4 + this.a11 * n5;
        final float abs = Math.abs(this.dgdX);
        final float abs2 = Math.abs(this.dgdY);
        if (abs > abs2) {
            this.pixSz = abs;
        }
        else {
            this.pixSz = abs2;
        }
        this.gc = (this.a02 - float1.x) * n4 + (this.a12 - float1.y) * n5;
        final Object value = renderingHints.get(RenderingHints.KEY_COLOR_RENDERING);
        final Object value2 = renderingHints.get(RenderingHints.KEY_RENDERING);
        this.fillMethod = 1;
        if (cycleMethodEnum == MultipleGradientPaint.REPEAT || this.hasDiscontinuity) {
            if (value2 == RenderingHints.VALUE_RENDER_QUALITY) {
                this.fillMethod = 3;
            }
            if (value == RenderingHints.VALUE_COLOR_RENDER_SPEED) {
                this.fillMethod = 1;
            }
            else if (value == RenderingHints.VALUE_COLOR_RENDER_QUALITY) {
                this.fillMethod = 3;
            }
        }
    }
    
    protected void fillHardNoCycle(final int[] array, int i, final int n, final int n2, final int n3, final int n4, final int n5) {
        final float n6 = this.dgdX * n2 + this.gc;
        for (int j = 0; j < n5; ++j) {
            float n7 = n6 + this.dgdY * (n3 + j);
            final int n8 = i + n4;
            if (this.dgdX == 0.0f) {
                int n9;
                if (n7 <= 0.0f) {
                    n9 = this.gradientUnderflow;
                }
                else if (n7 >= 1.0f) {
                    n9 = this.gradientOverflow;
                }
                else {
                    int n10;
                    for (n10 = 0; n10 < this.gradientsLength - 1 && n7 >= this.fractions[n10 + 1]; ++n10) {}
                    n9 = this.gradients[n10][(int)((n7 - this.fractions[n10]) * 255.0f / this.normalizedIntervals[n10] + 0.5f)];
                }
                while (i < n8) {
                    array[i++] = n9;
                }
            }
            else {
                float n11;
                float n12;
                int n13;
                int n14;
                if (this.dgdX >= 0.0f) {
                    n11 = (1.0f - n7) / this.dgdX;
                    n12 = (float)Math.ceil((0.0f - n7) / this.dgdX);
                    n13 = this.gradientUnderflow;
                    n14 = this.gradientOverflow;
                }
                else {
                    n11 = (0.0f - n7) / this.dgdX;
                    n12 = (float)Math.ceil((1.0f - n7) / this.dgdX);
                    n13 = this.gradientOverflow;
                    n14 = this.gradientUnderflow;
                }
                int n15;
                if (n11 > n4) {
                    n15 = n4;
                }
                else {
                    n15 = (int)n11;
                }
                int n16;
                if (n12 > n4) {
                    n16 = n4;
                }
                else {
                    n16 = (int)n12;
                }
                final int n17 = i + n15;
                if (n16 > 0) {
                    while (i < i + n16) {
                        array[i++] = n13;
                    }
                    n7 += this.dgdX * n16;
                }
                if (this.dgdX > 0.0f) {
                    int k;
                    for (k = 0; k < this.gradientsLength - 1; ++k) {
                        if (n7 < this.fractions[k + 1]) {
                            break;
                        }
                    }
                    while (i < n17) {
                        final float n18 = n7 - this.fractions[k];
                        final int[] array2 = this.gradients[k];
                        final double ceil = Math.ceil((this.fractions[k + 1] - n7) / this.dgdX);
                        int n19;
                        if (ceil > n4) {
                            n19 = n4;
                        }
                        else {
                            n19 = (int)ceil;
                        }
                        int n20 = i + n19;
                        if (n20 > n17) {
                            n20 = n17;
                        }
                        for (int n21 = (int)(n18 * 255.0f / this.normalizedIntervals[k] * 65536.0f) + 32768, n22 = (int)(this.dgdX * 255.0f / this.normalizedIntervals[k] * 65536.0f); i < n20; array[i++] = array2[n21 >> 16], n21 += n22) {}
                        n7 += (float)(this.dgdX * ceil);
                        ++k;
                    }
                }
                else {
                    int l;
                    for (l = this.gradientsLength - 1; l > 0; --l) {
                        if (n7 > this.fractions[l]) {
                            break;
                        }
                    }
                    while (i < n17) {
                        final float n23 = n7 - this.fractions[l];
                        final int[] array3 = this.gradients[l];
                        final double ceil2 = Math.ceil(n23 / -this.dgdX);
                        int n24;
                        if (ceil2 > n4) {
                            n24 = n4;
                        }
                        else {
                            n24 = (int)ceil2;
                        }
                        int n25 = i + n24;
                        if (n25 > n17) {
                            n25 = n17;
                        }
                        for (int n26 = (int)(n23 * 255.0f / this.normalizedIntervals[l] * 65536.0f) + 32768, n27 = (int)(this.dgdX * 255.0f / this.normalizedIntervals[l] * 65536.0f); i < n25; array[i++] = array3[n26 >> 16], n26 += n27) {}
                        n7 += (float)(this.dgdX * ceil2);
                        --l;
                    }
                }
                while (i < n8) {
                    array[i++] = n14;
                }
            }
            i += n;
        }
    }
    
    protected void fillSimpleNoCycle(final int[] array, int i, final int n, final int n2, final int n3, final int n4, final int n5) {
        final float n6 = this.dgdX * n2 + this.gc;
        final float n7 = this.dgdX * this.fastGradientArraySize;
        final int n8 = (int)(n7 * 65536.0f);
        final int[] gradient = this.gradient;
        for (int j = 0; j < n5; ++j) {
            float n9 = (float)((n6 + this.dgdY * (n3 + j)) * this.fastGradientArraySize + 0.5);
            final int n10 = i + n4;
            float n11 = this.dgdX * this.fastGradientArraySize * n4;
            if (n11 < 0.0f) {
                n11 = -n11;
            }
            if (n11 < 0.3) {
                int n12;
                if (n9 <= 0.0f) {
                    n12 = this.gradientUnderflow;
                }
                else if (n9 >= this.fastGradientArraySize) {
                    n12 = this.gradientOverflow;
                }
                else {
                    n12 = gradient[(int)n9];
                }
                while (i < n10) {
                    array[i++] = n12;
                }
            }
            else {
                int n13;
                int n14;
                int n15;
                int n16;
                if (this.dgdX > 0.0f) {
                    n13 = (int)((this.fastGradientArraySize - n9) / n7);
                    n14 = (int)Math.ceil(0.0f - n9 / n7);
                    n15 = this.gradientUnderflow;
                    n16 = this.gradientOverflow;
                }
                else {
                    n13 = (int)((0.0f - n9) / n7);
                    n14 = (int)Math.ceil((this.fastGradientArraySize - n9) / n7);
                    n15 = this.gradientOverflow;
                    n16 = this.gradientUnderflow;
                }
                if (n13 > n4) {
                    n13 = n4;
                }
                final int n17 = i + n13;
                if (n14 > 0) {
                    if (n14 > n4) {
                        n14 = n4;
                    }
                    while (i < i + n14) {
                        array[i++] = n15;
                    }
                    n9 += n7 * n14;
                }
                for (int n18 = (int)(n9 * 65536.0f); i < n17; array[i++] = gradient[n18 >> 16], n18 += n8) {}
                while (i < n10) {
                    array[i++] = n16;
                }
            }
            i += n;
        }
    }
    
    protected void fillSimpleRepeat(final int[] array, int i, final int n, final int n2, final int n3, final int n4, final int n5) {
        final float n6 = this.dgdX * n2 + this.gc;
        float n7 = (this.dgdX - (int)this.dgdX) * this.fastGradientArraySize;
        if (n7 < 0.0f) {
            n7 += this.fastGradientArraySize;
        }
        final int[] gradient = this.gradient;
        for (int j = 0; j < n5; ++j) {
            final float n8 = n6 + this.dgdY * (n3 + j);
            float n9 = n8 - (int)n8;
            if (n9 < 0.0f) {
                ++n9;
            }
            int n11;
            for (float n10 = (float)(n9 * this.fastGradientArraySize + 0.5); i < i + n4; array[i++] = gradient[n11], n10 += n7) {
                n11 = (int)n10;
                if (n11 >= this.fastGradientArraySize) {
                    n10 -= this.fastGradientArraySize;
                    n11 -= this.fastGradientArraySize;
                }
            }
            i += n;
        }
    }
    
    protected void fillSimpleReflect(final int[] array, int i, final int n, final int n2, final int n3, final int n4, final int n5) {
        final float n6 = this.dgdX * n2 + this.gc;
        final int[] gradient = this.gradient;
        for (int j = 0; j < n5; ++j) {
            final float n7 = n6 + this.dgdY * (n3 + j);
            float n8 = n7 - 2 * (int)(n7 / 2.0f);
            float dgdX = this.dgdX;
            if (n8 < 0.0f) {
                n8 = -n8;
                dgdX = -dgdX;
            }
            float n9 = dgdX - 2.0f * ((int)dgdX / 2.0f);
            if (n9 < 0.0f) {
                n9 += 2.0;
            }
            final int n10 = 2 * this.fastGradientArraySize;
            float n11 = (float)(n8 * this.fastGradientArraySize + 0.5);
            final float n12 = n9 * this.fastGradientArraySize;
            while (i < i + n4) {
                int n13 = (int)n11;
                if (n13 >= n10) {
                    n11 -= n10;
                    n13 -= n10;
                }
                if (n13 <= this.fastGradientArraySize) {
                    array[i++] = gradient[n13];
                }
                else {
                    array[i++] = gradient[n10 - n13];
                }
                n11 += n12;
            }
            i += n;
        }
    }
    
    protected void fillRaster(final int[] array, int i, final int n, final int n2, final int n3, final int n4, final int n5) {
        final float n6 = this.dgdX * n2 + this.gc;
        if (this.fillMethod == 3) {
            for (int j = 0; j < n5; ++j) {
                for (float n7 = n6 + this.dgdY * (n3 + j); i < i + n4; array[i++] = this.indexGradientAntiAlias(n7, this.pixSz), n7 += this.dgdX) {}
                i += n;
            }
        }
        else if (!this.isSimpleLookup) {
            if (this.cycleMethod == MultipleGradientPaint.NO_CYCLE) {
                this.fillHardNoCycle(array, i, n, n2, n3, n4, n5);
            }
            else {
                for (int k = 0; k < n5; ++k) {
                    for (float n8 = n6 + this.dgdY * (n3 + k); i < i + n4; array[i++] = this.indexIntoGradientsArrays(n8), n8 += this.dgdX) {}
                    i += n;
                }
            }
        }
        else if (this.cycleMethod == MultipleGradientPaint.NO_CYCLE) {
            this.fillSimpleNoCycle(array, i, n, n2, n3, n4, n5);
        }
        else if (this.cycleMethod == MultipleGradientPaint.REPEAT) {
            this.fillSimpleRepeat(array, i, n, n2, n3, n4, n5);
        }
        else {
            this.fillSimpleReflect(array, i, n, n2, n3, n4, n5);
        }
    }
}
