// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt;

import java.awt.geom.NoninvertibleTransformException;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.image.ColorModel;

final class RadialGradientPaintContext extends MultipleGradientPaintContext
{
    private boolean isSimpleFocus;
    private boolean isNonCyclic;
    private float radius;
    private float centerX;
    private float centerY;
    private float focusX;
    private float focusY;
    private float radiusSq;
    private float constA;
    private float constB;
    private float trivial;
    private static final int FIXED_POINT_IMPL = 1;
    private static final int DEFAULT_IMPL = 2;
    private static final int ANTI_ALIAS_IMPL = 3;
    private int fillMethod;
    private static final float SCALEBACK = 0.999f;
    private float invSqStepFloat;
    private static final int MAX_PRECISION = 256;
    private int[] sqrtLutFixed;
    
    public RadialGradientPaintContext(final ColorModel colorModel, final Rectangle rectangle, final Rectangle2D rectangle2D, final AffineTransform affineTransform, final RenderingHints renderingHints, final float centerX, final float centerY, final float radius, final float focusX, final float focusY, final float[] array, final Color[] array2, final MultipleGradientPaint.CycleMethodEnum cycleMethodEnum, final MultipleGradientPaint.ColorSpaceEnum colorSpaceEnum) throws NoninvertibleTransformException {
        super(colorModel, rectangle, rectangle2D, affineTransform, renderingHints, array, array2, cycleMethodEnum, colorSpaceEnum);
        this.isSimpleFocus = false;
        this.isNonCyclic = false;
        this.sqrtLutFixed = new int[256];
        this.centerX = centerX;
        this.centerY = centerY;
        this.focusX = focusX;
        this.focusY = focusY;
        this.radius = radius;
        this.isSimpleFocus = (this.focusX == this.centerX && this.focusY == this.centerY);
        this.isNonCyclic = (cycleMethodEnum == RadialGradientPaint.NO_CYCLE);
        this.radiusSq = this.radius * this.radius;
        final float n = this.focusX - this.centerX;
        final float n2 = this.focusY - this.centerY;
        if (Math.sqrt(n * n + n2 * n2) > this.radius * 0.999f) {
            final double atan2 = Math.atan2(n2, n);
            this.focusX = (float)(0.999f * this.radius * Math.cos(atan2)) + this.centerX;
            this.focusY = (float)(0.999f * this.radius * Math.sin(atan2)) + this.centerY;
        }
        final float n3 = this.focusX - this.centerX;
        this.trivial = (float)Math.sqrt(this.radiusSq - n3 * n3);
        this.constA = this.a02 - this.centerX;
        this.constB = this.a12 - this.centerY;
        final Object value = renderingHints.get(RenderingHints.KEY_COLOR_RENDERING);
        final Object value2 = renderingHints.get(RenderingHints.KEY_RENDERING);
        this.fillMethod = 0;
        if (value2 == RenderingHints.VALUE_RENDER_QUALITY || value == RenderingHints.VALUE_COLOR_RENDER_QUALITY) {
            this.fillMethod = 3;
        }
        if (value2 == RenderingHints.VALUE_RENDER_SPEED || value == RenderingHints.VALUE_COLOR_RENDER_SPEED) {
            this.fillMethod = 2;
        }
        if (this.fillMethod == 0) {
            this.fillMethod = 2;
        }
        if (this.fillMethod == 2 && this.isSimpleFocus && this.isNonCyclic && this.isSimpleLookup) {
            this.calculateFixedPointSqrtLookupTable();
            this.fillMethod = 1;
        }
    }
    
    protected void fillRaster(final int[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        switch (this.fillMethod) {
            case 1: {
                this.fixedPointSimplestCaseNonCyclicFillRaster(array, n, n2, n3, n4, n5, n6);
                break;
            }
            case 3: {
                this.antiAliasFillRaster(array, n, n2, n3, n4, n5, n6);
                break;
            }
            default: {
                this.cyclicCircularGradientFillRaster(array, n, n2, n3, n4, n5, n6);
                break;
            }
        }
    }
    
    private void fixedPointSimplestCaseNonCyclicFillRaster(final int[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = this.fastGradientArraySize / this.radius;
        final float n8 = this.a00 * n3 + this.a01 * n4 + this.constA;
        final float n9 = this.a10 * n3 + this.a11 * n4 + this.constB;
        final float n10 = n7 * this.a00;
        final float n11 = n7 * this.a10;
        final int n12 = this.fastGradientArraySize * this.fastGradientArraySize;
        int i = n;
        final float n13 = n10 * n10 + n11 * n11;
        final float n14 = n13 * 2.0f;
        if (n13 > n12) {
            final int gradientOverflow = this.gradientOverflow;
            for (int j = 0; j < n6; ++j) {
                while (i < i + n5) {
                    array[i] = gradientOverflow;
                    ++i;
                }
                i += n2;
            }
            return;
        }
        for (int k = 0; k < n6; ++k) {
            final float n15 = n7 * (this.a01 * k + n8);
            final float n16 = n7 * (this.a11 * k + n9);
            float n17 = n16 * n16 + n15 * n15;
            float n18 = (n11 * n16 + n10 * n15) * 2.0f + n13;
            while (i < i + n5) {
                if (n17 >= n12) {
                    array[i] = this.gradientOverflow;
                }
                else {
                    final float n19 = n17 * this.invSqStepFloat;
                    final int n20 = (int)n19;
                    final float n21 = n19 - n20;
                    final int n22 = this.sqrtLutFixed[n20];
                    array[i] = this.gradient[n22 + (int)(n21 * (this.sqrtLutFixed[n20 + 1] - n22))];
                }
                n17 += n18;
                n18 += n14;
                ++i;
            }
            i += n2;
        }
    }
    
    private void calculateFixedPointSqrtLookupTable() {
        final float n = this.fastGradientArraySize * this.fastGradientArraySize / 254.0f;
        final int[] sqrtLutFixed = this.sqrtLutFixed;
        int i;
        for (i = 0; i < 255; ++i) {
            sqrtLutFixed[i] = (int)Math.sqrt(i * n);
        }
        sqrtLutFixed[i] = sqrtLutFixed[i - 1];
        this.invSqStepFloat = 1.0f / n;
    }
    
    private void cyclicCircularGradientFillRaster(final int[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final double n7 = -this.radiusSq + this.centerX * this.centerX + this.centerY * this.centerY;
        final float n8 = this.a00 * n3 + this.a01 * n4 + this.a02;
        final float n9 = this.a10 * n3 + this.a11 * n4 + this.a12;
        final float n10 = 2.0f * this.centerY;
        final float n11 = -2.0f * this.centerX;
        int n12 = n;
        final int n13 = n5 + n2;
        for (int i = 0; i < n6; ++i) {
            float n14 = this.a01 * i + n8;
            float n15 = this.a11 * i + n9;
            for (int j = 0; j < n5; ++j) {
                double n16;
                double n17;
                if (n14 - this.focusX > -1.0E-6f && n14 - this.focusX < 1.0E-6f) {
                    n16 = this.focusX;
                    n17 = this.centerY + ((n15 > this.focusY) ? this.trivial : ((double)(-this.trivial)));
                }
                else {
                    final double n18 = (n15 - this.focusY) / (n14 - this.focusX);
                    final double n19 = n15 - n18 * n14;
                    final double n20 = n18 * n18 + 1.0;
                    final double n21 = n11 + -2.0 * n18 * (this.centerY - n19);
                    final float n22 = (float)Math.sqrt(n21 * n21 - 4.0 * n20 * (n7 + n19 * (n19 - n10)));
                    n16 = (-n21 + ((n14 < this.focusX) ? (-n22) : ((double)n22))) / (2.0 * n20);
                    n17 = n18 * n16 + n19;
                }
                final float n23 = (float)n16 - this.focusX;
                final float n24 = n23 * n23;
                final float n25 = (float)n17 - this.focusY;
                final float n26 = n24 + n25 * n25;
                final float n27 = n14 - this.focusX;
                final float n28 = n27 * n27;
                final float n29 = n15 - this.focusY;
                array[n12 + j] = this.indexIntoGradientsArrays((float)Math.sqrt((n28 + n29 * n29) / n26));
                n14 += this.a00;
                n15 += this.a10;
            }
            n12 += n13;
        }
    }
    
    private void antiAliasFillRaster(final int[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final double n7 = -this.radiusSq + this.centerX * this.centerX + this.centerY * this.centerY;
        final float n8 = 2.0f * this.centerY;
        final float n9 = -2.0f * this.centerX;
        final float n10 = this.a00 * (n3 - 0.5f) + this.a01 * (n4 + 0.5f) + this.a02;
        final float n11 = this.a10 * (n3 - 0.5f) + this.a11 * (n4 + 0.5f) + this.a12;
        int n12 = n - 1;
        final double[] array2 = new double[n5 + 1];
        float n13 = n10 - this.a01;
        float n14 = n11 - this.a11;
        for (int i = 0; i <= n5; ++i) {
            final float n15 = n13 - this.focusX;
            double n16;
            double n17;
            if (n15 > -1.0E-6f && n15 < 1.0E-6f) {
                n16 = this.focusX;
                n17 = this.centerY + ((n14 > this.focusY) ? this.trivial : ((double)(-this.trivial)));
            }
            else {
                final double n18 = (n14 - this.focusY) / (n13 - this.focusX);
                final double n19 = n14 - n18 * n13;
                final double n20 = n18 * n18 + 1.0;
                final double n21 = n9 + -2.0 * n18 * (this.centerY - n19);
                final double sqrt = Math.sqrt(n21 * n21 - 4.0 * n20 * (n7 + n19 * (n19 - n8)));
                n16 = (-n21 + ((n13 < this.focusX) ? (-sqrt) : sqrt)) / (2.0 * n20);
                n17 = n18 * n16 + n19;
            }
            final double n22 = n16 - this.focusX;
            final double n23 = n22 * n22;
            final double n24 = n17 - this.focusY;
            final double n25 = n23 + n24 * n24;
            final double n26 = n13 - this.focusX;
            final double n27 = n26 * n26;
            final double n28 = n14 - this.focusY;
            array2[i] = Math.sqrt((n27 + n28 * n28) / n25);
            n13 += this.a00;
            n14 += this.a10;
        }
        for (int j = 0; j < n6; ++j) {
            final float n29 = this.a01 * j + n10;
            final float n30 = this.a11 * j + n11;
            double n31 = array2[0];
            final float n32 = n29 - this.focusX;
            double n33;
            double n34;
            if (n32 > -1.0E-6f && n32 < 1.0E-6f) {
                n33 = this.focusX;
                n34 = this.centerY + ((n30 > this.focusY) ? this.trivial : ((double)(-this.trivial)));
            }
            else {
                final double n35 = (n30 - this.focusY) / (n29 - this.focusX);
                final double n36 = n30 - n35 * n29;
                final double n37 = n35 * n35 + 1.0;
                final double n38 = n9 + -2.0 * n35 * (this.centerY - n36);
                final double sqrt2 = Math.sqrt(n38 * n38 - 4.0 * n37 * (n7 + n36 * (n36 - n8)));
                n33 = (-n38 + ((n29 < this.focusX) ? (-sqrt2) : sqrt2)) / (2.0 * n37);
                n34 = n35 * n33 + n36;
            }
            final double n39 = n33 - this.focusX;
            final double n40 = n39 * n39;
            final double n41 = n34 - this.focusY;
            final double n42 = n40 + n41 * n41;
            final double n43 = n29 - this.focusX;
            final double n44 = n43 * n43;
            final double n45 = n30 - this.focusY;
            double n46 = Math.sqrt((n44 + n45 * n45) / n42);
            array2[0] = n46;
            float n47 = n29 + this.a00;
            float n48 = n30 + this.a10;
            for (int k = 1; k <= n5; ++k) {
                final double n49 = n31;
                final double n50 = n46;
                n31 = array2[k];
                final float n51 = n47 - this.focusX;
                double n52;
                double n53;
                if (n51 > -1.0E-6f && n51 < 1.0E-6f) {
                    n52 = this.focusX;
                    n53 = this.centerY + ((n48 > this.focusY) ? this.trivial : ((double)(-this.trivial)));
                }
                else {
                    final double n54 = (n48 - this.focusY) / (n47 - this.focusX);
                    final double n55 = n48 - n54 * n47;
                    final double n56 = n54 * n54 + 1.0;
                    final double n57 = n9 + -2.0 * n54 * (this.centerY - n55);
                    final double sqrt3 = Math.sqrt(n57 * n57 - 4.0 * n56 * (n7 + n55 * (n55 - n8)));
                    n52 = (-n57 + ((n47 < this.focusX) ? (-sqrt3) : sqrt3)) / (2.0 * n56);
                    n53 = n54 * n52 + n55;
                }
                final double n58 = n52 - this.focusX;
                final double n59 = n58 * n58;
                final double n60 = n53 - this.focusY;
                final double n61 = n59 + n60 * n60;
                final double n62 = n47 - this.focusX;
                final double n63 = n62 * n62;
                final double n64 = n48 - this.focusY;
                n46 = Math.sqrt((n63 + n64 * n64) / n61);
                array2[k] = n46;
                array[n12 + k] = this.indexGradientAntiAlias((float)((n49 + n50 + n31 + n46) / 4.0), (float)Math.max(Math.abs(n46 - n49), Math.abs(n31 - n50)));
                n47 += this.a00;
                n48 += this.a10;
            }
            n12 += n5 + n2;
        }
    }
}
