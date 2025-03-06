// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.util.Map;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.color.ColorSpace;
import java.awt.Rectangle;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

public final class TurbulencePatternRed extends AbstractRed
{
    private StitchInfo stitchInfo;
    private static final AffineTransform IDENTITY;
    private double baseFrequencyX;
    private double baseFrequencyY;
    private int numOctaves;
    private int seed;
    private Rectangle2D tile;
    private AffineTransform txf;
    private boolean isFractalNoise;
    private int[] channels;
    double[] tx;
    double[] ty;
    private static final int RAND_m = Integer.MAX_VALUE;
    private static final int RAND_a = 16807;
    private static final int RAND_q = 127773;
    private static final int RAND_r = 2836;
    private static final int BSize = 256;
    private static final int BM = 255;
    private static final double PerlinN = 4096.0;
    private final int[] latticeSelector;
    private final double[] gradient;
    
    public double getBaseFrequencyX() {
        return this.baseFrequencyX;
    }
    
    public double getBaseFrequencyY() {
        return this.baseFrequencyY;
    }
    
    public int getNumOctaves() {
        return this.numOctaves;
    }
    
    public int getSeed() {
        return this.seed;
    }
    
    public Rectangle2D getTile() {
        return (Rectangle2D)this.tile.clone();
    }
    
    public boolean isFractalNoise() {
        return this.isFractalNoise;
    }
    
    public boolean[] getChannels() {
        final boolean[] array = new boolean[4];
        for (int i = 0; i < this.channels.length; ++i) {
            array[this.channels[i]] = true;
        }
        return array;
    }
    
    public final int setupSeed(int n) {
        if (n <= 0) {
            n = -(n % 2147483646) + 1;
        }
        if (n > 2147483646) {
            n = 2147483646;
        }
        return n;
    }
    
    public final int random(final int n) {
        int n2 = 16807 * (n % 127773) - 2836 * (n / 127773);
        if (n2 <= 0) {
            n2 += Integer.MAX_VALUE;
        }
        return n2;
    }
    
    private void initLattice(int n) {
        n = this.setupSeed(n);
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 256; ++j) {
                final double n2 = (n = this.random(n)) % 512 - 256;
                final double n3 = (n = this.random(n)) % 512 - 256;
                final double n4 = 1.0 / Math.sqrt(n2 * n2 + n3 * n3);
                this.gradient[j * 8 + i * 2] = n2 * n4;
                this.gradient[j * 8 + i * 2 + 1] = n3 * n4;
            }
        }
        int k;
        for (k = 0; k < 256; ++k) {
            this.latticeSelector[k] = k;
        }
        while (--k > 0) {
            final int n5 = this.latticeSelector[k];
            final int n6 = (n = this.random(n)) % 256;
            this.latticeSelector[k] = this.latticeSelector[n6];
            this.latticeSelector[n6] = n5;
            final int n7 = k << 3;
            final int n8 = n6 << 3;
            for (int l = 0; l < 8; ++l) {
                final double n9 = this.gradient[n7 + l];
                this.gradient[n7 + l] = this.gradient[n8 + l];
                this.gradient[n8 + l] = n9;
            }
        }
        this.latticeSelector[256] = this.latticeSelector[0];
        for (int n10 = 0; n10 < 8; ++n10) {
            this.gradient[2048 + n10] = this.gradient[n10];
        }
    }
    
    private static final double s_curve(final double n) {
        return n * n * (3.0 - 2.0 * n);
    }
    
    private static final double lerp(final double n, final double n2, final double n3) {
        return n2 + n * (n3 - n2);
    }
    
    private final void noise2(final double[] array, double n, double n2) {
        n += 4096.0;
        final int n3 = (int)n & 0xFF;
        final int n4 = this.latticeSelector[n3];
        final int n5 = this.latticeSelector[n3 + 1];
        final double n6 = n - (int)n;
        final double n7 = n6 - 1.0;
        final double s_curve = s_curve(n6);
        n2 += 4096.0;
        final int n8 = (int)n2;
        final int n9 = (n5 + n8 & 0xFF) << 3;
        final int n10 = (n4 + n8 & 0xFF) << 3;
        final double n11 = n2 - (int)n2;
        final double n12 = n11 - 1.0;
        final double s_curve2 = s_curve(n11);
        switch (this.channels.length) {
            case 4: {
                array[3] = lerp(s_curve2, lerp(s_curve, n6 * this.gradient[n10 + 6] + n11 * this.gradient[n10 + 7], n7 * this.gradient[n9 + 6] + n11 * this.gradient[n9 + 7]), lerp(s_curve, n6 * this.gradient[n10 + 8 + 6] + n12 * this.gradient[n10 + 8 + 7], n7 * this.gradient[n9 + 8 + 6] + n12 * this.gradient[n9 + 8 + 7]));
            }
            case 3: {
                array[2] = lerp(s_curve2, lerp(s_curve, n6 * this.gradient[n10 + 4] + n11 * this.gradient[n10 + 5], n7 * this.gradient[n9 + 4] + n11 * this.gradient[n9 + 5]), lerp(s_curve, n6 * this.gradient[n10 + 8 + 4] + n12 * this.gradient[n10 + 8 + 5], n7 * this.gradient[n9 + 8 + 4] + n12 * this.gradient[n9 + 8 + 5]));
            }
            case 2: {
                array[1] = lerp(s_curve2, lerp(s_curve, n6 * this.gradient[n10 + 2] + n11 * this.gradient[n10 + 3], n7 * this.gradient[n9 + 2] + n11 * this.gradient[n9 + 3]), lerp(s_curve, n6 * this.gradient[n10 + 8 + 2] + n12 * this.gradient[n10 + 8 + 3], n7 * this.gradient[n9 + 8 + 2] + n12 * this.gradient[n9 + 8 + 3]));
            }
            case 1: {
                array[0] = lerp(s_curve2, lerp(s_curve, n6 * this.gradient[n10 + 0] + n11 * this.gradient[n10 + 1], n7 * this.gradient[n9 + 0] + n11 * this.gradient[n9 + 1]), lerp(s_curve, n6 * this.gradient[n10 + 8 + 0] + n12 * this.gradient[n10 + 8 + 1], n7 * this.gradient[n9 + 8 + 0] + n12 * this.gradient[n9 + 8 + 1]));
                break;
            }
        }
    }
    
    private final void noise2Stitch(final double[] array, final double n, final double n2, final StitchInfo stitchInfo) {
        final double n3 = n + 4096.0;
        int n4 = (int)n3;
        int n5 = n4 + 1;
        if (n5 >= stitchInfo.wrapX) {
            if (n4 >= stitchInfo.wrapX) {
                n4 -= stitchInfo.width;
                n5 -= stitchInfo.width;
            }
            else {
                n5 -= stitchInfo.width;
            }
        }
        final int n6 = this.latticeSelector[n4 & 0xFF];
        final int n7 = this.latticeSelector[n5 & 0xFF];
        final double n8 = n3 - (int)n3;
        final double n9 = n8 - 1.0;
        final double s_curve = s_curve(n8);
        final double n10 = n2 + 4096.0;
        int n11 = (int)n10;
        int n12 = n11 + 1;
        if (n12 >= stitchInfo.wrapY) {
            if (n11 >= stitchInfo.wrapY) {
                n11 -= stitchInfo.height;
                n12 -= stitchInfo.height;
            }
            else {
                n12 -= stitchInfo.height;
            }
        }
        final int n13 = (n6 + n11 & 0xFF) << 3;
        final int n14 = (n7 + n11 & 0xFF) << 3;
        final int n15 = (n6 + n12 & 0xFF) << 3;
        final int n16 = (n7 + n12 & 0xFF) << 3;
        final double n17 = n10 - (int)n10;
        final double n18 = n17 - 1.0;
        final double s_curve2 = s_curve(n17);
        switch (this.channels.length) {
            case 4: {
                array[3] = lerp(s_curve2, lerp(s_curve, n8 * this.gradient[n13 + 6] + n17 * this.gradient[n13 + 7], n9 * this.gradient[n14 + 6] + n17 * this.gradient[n14 + 7]), lerp(s_curve, n8 * this.gradient[n15 + 6] + n18 * this.gradient[n15 + 7], n9 * this.gradient[n16 + 6] + n18 * this.gradient[n16 + 7]));
            }
            case 3: {
                array[2] = lerp(s_curve2, lerp(s_curve, n8 * this.gradient[n13 + 4] + n17 * this.gradient[n13 + 5], n9 * this.gradient[n14 + 4] + n17 * this.gradient[n14 + 5]), lerp(s_curve, n8 * this.gradient[n15 + 4] + n18 * this.gradient[n15 + 5], n9 * this.gradient[n16 + 4] + n18 * this.gradient[n16 + 5]));
            }
            case 2: {
                array[1] = lerp(s_curve2, lerp(s_curve, n8 * this.gradient[n13 + 2] + n17 * this.gradient[n13 + 3], n9 * this.gradient[n14 + 2] + n17 * this.gradient[n14 + 3]), lerp(s_curve, n8 * this.gradient[n15 + 2] + n18 * this.gradient[n15 + 3], n9 * this.gradient[n16 + 2] + n18 * this.gradient[n16 + 3]));
            }
            case 1: {
                array[0] = lerp(s_curve2, lerp(s_curve, n8 * this.gradient[n13 + 0] + n17 * this.gradient[n13 + 1], n9 * this.gradient[n14 + 0] + n17 * this.gradient[n14 + 1]), lerp(s_curve, n8 * this.gradient[n15 + 0] + n18 * this.gradient[n15 + 1], n9 * this.gradient[n16 + 0] + n18 * this.gradient[n16 + 1]));
                break;
            }
        }
    }
    
    private final int turbulence_4(double n, double n2, final double[] array) {
        double n3 = 255.0;
        n *= this.baseFrequencyX;
        n2 *= this.baseFrequencyY;
        final int n4 = 0;
        final int n5 = 1;
        final int n6 = 2;
        final int n7 = 3;
        final double n8 = 0.0;
        array[n6] = (array[n7] = n8);
        array[n4] = (array[n5] = n8);
        for (int i = this.numOctaves; i > 0; --i) {
            final double n9 = n + 4096.0;
            final int n10 = (int)n9 & 0xFF;
            final int n11 = this.latticeSelector[n10];
            final int n12 = this.latticeSelector[n10 + 1];
            final double n13 = n9 - (int)n9;
            final double n14 = n13 - 1.0;
            final double s_curve = s_curve(n13);
            final double n15 = n2 + 4096.0;
            final int n16 = (int)n15 & 0xFF;
            final int n17 = (n12 + n16 & 0xFF) << 3;
            final int n18 = (n11 + n16 & 0xFF) << 3;
            final double n19 = n15 - (int)n15;
            final double n20 = n19 - 1.0;
            final double s_curve2 = s_curve(n19);
            final double lerp = lerp(s_curve2, lerp(s_curve, n13 * this.gradient[n18 + 0] + n19 * this.gradient[n18 + 1], n14 * this.gradient[n17 + 0] + n19 * this.gradient[n17 + 1]), lerp(s_curve, n13 * this.gradient[n18 + 8 + 0] + n20 * this.gradient[n18 + 8 + 1], n14 * this.gradient[n17 + 8 + 0] + n20 * this.gradient[n17 + 8 + 1]));
            if (lerp < 0.0) {
                final int n21 = 0;
                array[n21] -= lerp * n3;
            }
            else {
                final int n22 = 0;
                array[n22] += lerp * n3;
            }
            final double lerp2 = lerp(s_curve2, lerp(s_curve, n13 * this.gradient[n18 + 2] + n19 * this.gradient[n18 + 3], n14 * this.gradient[n17 + 2] + n19 * this.gradient[n17 + 3]), lerp(s_curve, n13 * this.gradient[n18 + 8 + 2] + n20 * this.gradient[n18 + 8 + 3], n14 * this.gradient[n17 + 8 + 2] + n20 * this.gradient[n17 + 8 + 3]));
            if (lerp2 < 0.0) {
                final int n23 = 1;
                array[n23] -= lerp2 * n3;
            }
            else {
                final int n24 = 1;
                array[n24] += lerp2 * n3;
            }
            final double lerp3 = lerp(s_curve2, lerp(s_curve, n13 * this.gradient[n18 + 4] + n19 * this.gradient[n18 + 5], n14 * this.gradient[n17 + 4] + n19 * this.gradient[n17 + 5]), lerp(s_curve, n13 * this.gradient[n18 + 8 + 4] + n20 * this.gradient[n18 + 8 + 5], n14 * this.gradient[n17 + 8 + 4] + n20 * this.gradient[n17 + 8 + 5]));
            if (lerp3 < 0.0) {
                final int n25 = 2;
                array[n25] -= lerp3 * n3;
            }
            else {
                final int n26 = 2;
                array[n26] += lerp3 * n3;
            }
            final double lerp4 = lerp(s_curve2, lerp(s_curve, n13 * this.gradient[n18 + 6] + n19 * this.gradient[n18 + 7], n14 * this.gradient[n17 + 6] + n19 * this.gradient[n17 + 7]), lerp(s_curve, n13 * this.gradient[n18 + 8 + 6] + n20 * this.gradient[n18 + 8 + 7], n14 * this.gradient[n17 + 8 + 6] + n20 * this.gradient[n17 + 8 + 7]));
            if (lerp4 < 0.0) {
                final int n27 = 3;
                array[n27] -= lerp4 * n3;
            }
            else {
                final int n28 = 3;
                array[n28] += lerp4 * n3;
            }
            n3 *= 0.5;
            n *= 2.0;
            n2 *= 2.0;
        }
        final int n29 = (int)array[0];
        int n30;
        if ((n29 & 0xFFFFFF00) == 0x0) {
            n30 = n29 << 16;
        }
        else {
            n30 = (((n29 & Integer.MIN_VALUE) != 0x0) ? 0 : 16711680);
        }
        final int n31 = (int)array[1];
        int n32;
        if ((n31 & 0xFFFFFF00) == 0x0) {
            n32 = (n30 | n31 << 8);
        }
        else {
            n32 = (n30 | (((n31 & Integer.MIN_VALUE) != 0x0) ? 0 : 65280));
        }
        final int n33 = (int)array[2];
        int n34;
        if ((n33 & 0xFFFFFF00) == 0x0) {
            n34 = (n32 | n33);
        }
        else {
            n34 = (n32 | (((n33 & Integer.MIN_VALUE) != 0x0) ? 0 : 255));
        }
        final int n35 = (int)array[3];
        int n36;
        if ((n35 & 0xFFFFFF00) == 0x0) {
            n36 = (n34 | n35 << 24);
        }
        else {
            n36 = (n34 | (((n35 & Integer.MIN_VALUE) != 0x0) ? 0 : -16777216));
        }
        return n36;
    }
    
    private final void turbulence(final int[] array, double n, double n2, final double[] array2, final double[] array3) {
        final int n3 = 0;
        final int n4 = 1;
        final int n5 = 2;
        final int n6 = 3;
        final double n7 = 0.0;
        array2[n5] = (array2[n6] = n7);
        array2[n3] = (array2[n4] = n7);
        double n8 = 255.0;
        n *= this.baseFrequencyX;
        n2 *= this.baseFrequencyY;
        switch (this.channels.length) {
            case 4: {
                for (int i = 0; i < this.numOctaves; ++i) {
                    this.noise2(array3, n, n2);
                    if (array3[0] < 0.0) {
                        final int n9 = 0;
                        array2[n9] -= array3[0] * n8;
                    }
                    else {
                        final int n10 = 0;
                        array2[n10] += array3[0] * n8;
                    }
                    if (array3[1] < 0.0) {
                        final int n11 = 1;
                        array2[n11] -= array3[1] * n8;
                    }
                    else {
                        final int n12 = 1;
                        array2[n12] += array3[1] * n8;
                    }
                    if (array3[2] < 0.0) {
                        final int n13 = 2;
                        array2[n13] -= array3[2] * n8;
                    }
                    else {
                        final int n14 = 2;
                        array2[n14] += array3[2] * n8;
                    }
                    if (array3[3] < 0.0) {
                        final int n15 = 3;
                        array2[n15] -= array3[3] * n8;
                    }
                    else {
                        final int n16 = 3;
                        array2[n16] += array3[3] * n8;
                    }
                    n8 *= 0.5;
                    n *= 2.0;
                    n2 *= 2.0;
                }
                array[0] = (int)array2[0];
                if ((array[0] & 0xFFFFFF00) != 0x0) {
                    array[0] = (((array[0] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[1] = (int)array2[1];
                if ((array[1] & 0xFFFFFF00) != 0x0) {
                    array[1] = (((array[1] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[2] = (int)array2[2];
                if ((array[2] & 0xFFFFFF00) != 0x0) {
                    array[2] = (((array[2] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[3] = (int)array2[3];
                if ((array[3] & 0xFFFFFF00) != 0x0) {
                    array[3] = (((array[3] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    break;
                }
                break;
            }
            case 3: {
                for (int j = 0; j < this.numOctaves; ++j) {
                    this.noise2(array3, n, n2);
                    if (array3[2] < 0.0) {
                        final int n17 = 2;
                        array2[n17] -= array3[2] * n8;
                    }
                    else {
                        final int n18 = 2;
                        array2[n18] += array3[2] * n8;
                    }
                    if (array3[1] < 0.0) {
                        final int n19 = 1;
                        array2[n19] -= array3[1] * n8;
                    }
                    else {
                        final int n20 = 1;
                        array2[n20] += array3[1] * n8;
                    }
                    if (array3[0] < 0.0) {
                        final int n21 = 0;
                        array2[n21] -= array3[0] * n8;
                    }
                    else {
                        final int n22 = 0;
                        array2[n22] += array3[0] * n8;
                    }
                    n8 *= 0.5;
                    n *= 2.0;
                    n2 *= 2.0;
                }
                array[2] = (int)array2[2];
                if ((array[2] & 0xFFFFFF00) != 0x0) {
                    array[2] = (((array[2] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[1] = (int)array2[1];
                if ((array[1] & 0xFFFFFF00) != 0x0) {
                    array[1] = (((array[1] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[0] = (int)array2[0];
                if ((array[0] & 0xFFFFFF00) != 0x0) {
                    array[0] = (((array[0] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    break;
                }
                break;
            }
            case 2: {
                for (int k = 0; k < this.numOctaves; ++k) {
                    this.noise2(array3, n, n2);
                    if (array3[1] < 0.0) {
                        final int n23 = 1;
                        array2[n23] -= array3[1] * n8;
                    }
                    else {
                        final int n24 = 1;
                        array2[n24] += array3[1] * n8;
                    }
                    if (array3[0] < 0.0) {
                        final int n25 = 0;
                        array2[n25] -= array3[0] * n8;
                    }
                    else {
                        final int n26 = 0;
                        array2[n26] += array3[0] * n8;
                    }
                    n8 *= 0.5;
                    n *= 2.0;
                    n2 *= 2.0;
                }
                array[1] = (int)array2[1];
                if ((array[1] & 0xFFFFFF00) != 0x0) {
                    array[1] = (((array[1] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[0] = (int)array2[0];
                if ((array[0] & 0xFFFFFF00) != 0x0) {
                    array[0] = (((array[0] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    break;
                }
                break;
            }
            case 1: {
                for (int l = 0; l < this.numOctaves; ++l) {
                    this.noise2(array3, n, n2);
                    if (array3[0] < 0.0) {
                        final int n27 = 0;
                        array2[n27] -= array3[0] * n8;
                    }
                    else {
                        final int n28 = 0;
                        array2[n28] += array3[0] * n8;
                    }
                    n8 *= 0.5;
                    n *= 2.0;
                    n2 *= 2.0;
                }
                array[0] = (int)array2[0];
                if ((array[0] & 0xFFFFFF00) != 0x0) {
                    array[0] = (((array[0] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    break;
                }
                break;
            }
        }
    }
    
    private final void turbulenceStitch(final int[] array, double n, double n2, final double[] array2, final double[] array3, final StitchInfo stitchInfo) {
        double n3 = 1.0;
        n *= this.baseFrequencyX;
        n2 *= this.baseFrequencyY;
        final int n4 = 0;
        final int n5 = 1;
        final int n6 = 2;
        final int n7 = 3;
        final double n8 = 0.0;
        array2[n6] = (array2[n7] = n8);
        array2[n4] = (array2[n5] = n8);
        switch (this.channels.length) {
            case 4: {
                for (int i = 0; i < this.numOctaves; ++i) {
                    this.noise2Stitch(array3, n, n2, stitchInfo);
                    if (array3[3] < 0.0) {
                        final int n9 = 3;
                        array2[n9] -= array3[3] * n3;
                    }
                    else {
                        final int n10 = 3;
                        array2[n10] += array3[3] * n3;
                    }
                    if (array3[2] < 0.0) {
                        final int n11 = 2;
                        array2[n11] -= array3[2] * n3;
                    }
                    else {
                        final int n12 = 2;
                        array2[n12] += array3[2] * n3;
                    }
                    if (array3[1] < 0.0) {
                        final int n13 = 1;
                        array2[n13] -= array3[1] * n3;
                    }
                    else {
                        final int n14 = 1;
                        array2[n14] += array3[1] * n3;
                    }
                    if (array3[0] < 0.0) {
                        final int n15 = 0;
                        array2[n15] -= array3[0] * n3;
                    }
                    else {
                        final int n16 = 0;
                        array2[n16] += array3[0] * n3;
                    }
                    n3 *= 0.5;
                    n *= 2.0;
                    n2 *= 2.0;
                    stitchInfo.doubleFrequency();
                }
                array[3] = (int)(array2[3] * 255.0);
                if ((array[3] & 0xFFFFFF00) != 0x0) {
                    array[3] = (((array[3] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[2] = (int)(array2[2] * 255.0);
                if ((array[2] & 0xFFFFFF00) != 0x0) {
                    array[2] = (((array[2] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[1] = (int)(array2[1] * 255.0);
                if ((array[1] & 0xFFFFFF00) != 0x0) {
                    array[1] = (((array[1] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[0] = (int)(array2[0] * 255.0);
                if ((array[0] & 0xFFFFFF00) != 0x0) {
                    array[0] = (((array[0] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    break;
                }
                break;
            }
            case 3: {
                for (int j = 0; j < this.numOctaves; ++j) {
                    this.noise2Stitch(array3, n, n2, stitchInfo);
                    if (array3[2] < 0.0) {
                        final int n17 = 2;
                        array2[n17] -= array3[2] * n3;
                    }
                    else {
                        final int n18 = 2;
                        array2[n18] += array3[2] * n3;
                    }
                    if (array3[1] < 0.0) {
                        final int n19 = 1;
                        array2[n19] -= array3[1] * n3;
                    }
                    else {
                        final int n20 = 1;
                        array2[n20] += array3[1] * n3;
                    }
                    if (array3[0] < 0.0) {
                        final int n21 = 0;
                        array2[n21] -= array3[0] * n3;
                    }
                    else {
                        final int n22 = 0;
                        array2[n22] += array3[0] * n3;
                    }
                    n3 *= 0.5;
                    n *= 2.0;
                    n2 *= 2.0;
                    stitchInfo.doubleFrequency();
                }
                array[2] = (int)(array2[2] * 255.0);
                if ((array[2] & 0xFFFFFF00) != 0x0) {
                    array[2] = (((array[2] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[1] = (int)(array2[1] * 255.0);
                if ((array[1] & 0xFFFFFF00) != 0x0) {
                    array[1] = (((array[1] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[0] = (int)(array2[0] * 255.0);
                if ((array[0] & 0xFFFFFF00) != 0x0) {
                    array[0] = (((array[0] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    break;
                }
                break;
            }
            case 2: {
                for (int k = 0; k < this.numOctaves; ++k) {
                    this.noise2Stitch(array3, n, n2, stitchInfo);
                    if (array3[1] < 0.0) {
                        final int n23 = 1;
                        array2[n23] -= array3[1] * n3;
                    }
                    else {
                        final int n24 = 1;
                        array2[n24] += array3[1] * n3;
                    }
                    if (array3[0] < 0.0) {
                        final int n25 = 0;
                        array2[n25] -= array3[0] * n3;
                    }
                    else {
                        final int n26 = 0;
                        array2[n26] += array3[0] * n3;
                    }
                    n3 *= 0.5;
                    n *= 2.0;
                    n2 *= 2.0;
                    stitchInfo.doubleFrequency();
                }
                array[1] = (int)(array2[1] * 255.0);
                if ((array[1] & 0xFFFFFF00) != 0x0) {
                    array[1] = (((array[1] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[0] = (int)(array2[0] * 255.0);
                if ((array[0] & 0xFFFFFF00) != 0x0) {
                    array[0] = (((array[0] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    break;
                }
                break;
            }
            case 1: {
                for (int l = 0; l < this.numOctaves; ++l) {
                    this.noise2Stitch(array3, n, n2, stitchInfo);
                    if (array3[0] < 0.0) {
                        final int n27 = 0;
                        array2[n27] -= array3[0] * n3;
                    }
                    else {
                        final int n28 = 0;
                        array2[n28] += array3[0] * n3;
                    }
                    n3 *= 0.5;
                    n *= 2.0;
                    n2 *= 2.0;
                    stitchInfo.doubleFrequency();
                }
                array[0] = (int)(array2[0] * 255.0);
                if ((array[0] & 0xFFFFFF00) != 0x0) {
                    array[0] = (((array[0] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    break;
                }
                break;
            }
        }
    }
    
    private final int turbulenceFractal_4(double n, double n2, final double[] array) {
        double n3 = 127.5;
        n *= this.baseFrequencyX;
        n2 *= this.baseFrequencyY;
        final int n4 = 0;
        final int n5 = 1;
        final int n6 = 2;
        final int n7 = 3;
        final double n8 = 127.5;
        array[n6] = (array[n7] = n8);
        array[n4] = (array[n5] = n8);
        for (int i = this.numOctaves; i > 0; --i) {
            final double n9 = n + 4096.0;
            final int n10 = (int)n9 & 0xFF;
            final int n11 = this.latticeSelector[n10];
            final int n12 = this.latticeSelector[n10 + 1];
            final double n13 = n9 - (int)n9;
            final double n14 = n13 - 1.0;
            final double s_curve = s_curve(n13);
            final double n15 = n2 + 4096.0;
            final int n16 = (int)n15 & 0xFF;
            final int n17 = (n12 + n16 & 0xFF) << 3;
            final int n18 = (n11 + n16 & 0xFF) << 3;
            final double n19 = n15 - (int)n15;
            final double n20 = n19 - 1.0;
            final double s_curve2 = s_curve(n19);
            final int n21 = 0;
            array[n21] += lerp(s_curve2, lerp(s_curve, n13 * this.gradient[n18 + 0] + n19 * this.gradient[n18 + 1], n14 * this.gradient[n17 + 0] + n19 * this.gradient[n17 + 1]), lerp(s_curve, n13 * this.gradient[n18 + 8 + 0] + n20 * this.gradient[n18 + 8 + 1], n14 * this.gradient[n17 + 8 + 0] + n20 * this.gradient[n17 + 8 + 1])) * n3;
            final int n22 = 1;
            array[n22] += lerp(s_curve2, lerp(s_curve, n13 * this.gradient[n18 + 2] + n19 * this.gradient[n18 + 3], n14 * this.gradient[n17 + 2] + n19 * this.gradient[n17 + 3]), lerp(s_curve, n13 * this.gradient[n18 + 8 + 2] + n20 * this.gradient[n18 + 8 + 3], n14 * this.gradient[n17 + 8 + 2] + n20 * this.gradient[n17 + 8 + 3])) * n3;
            final int n23 = 2;
            array[n23] += lerp(s_curve2, lerp(s_curve, n13 * this.gradient[n18 + 4] + n19 * this.gradient[n18 + 5], n14 * this.gradient[n17 + 4] + n19 * this.gradient[n17 + 5]), lerp(s_curve, n13 * this.gradient[n18 + 8 + 4] + n20 * this.gradient[n18 + 8 + 5], n14 * this.gradient[n17 + 8 + 4] + n20 * this.gradient[n17 + 8 + 5])) * n3;
            final int n24 = 3;
            array[n24] += lerp(s_curve2, lerp(s_curve, n13 * this.gradient[n18 + 6] + n19 * this.gradient[n18 + 7], n14 * this.gradient[n17 + 6] + n19 * this.gradient[n17 + 7]), lerp(s_curve, n13 * this.gradient[n18 + 8 + 6] + n20 * this.gradient[n18 + 8 + 7], n14 * this.gradient[n17 + 8 + 6] + n20 * this.gradient[n17 + 8 + 7])) * n3;
            n3 *= 0.5;
            n *= 2.0;
            n2 *= 2.0;
        }
        final int n25 = (int)array[0];
        int n26;
        if ((n25 & 0xFFFFFF00) == 0x0) {
            n26 = n25 << 16;
        }
        else {
            n26 = (((n25 & Integer.MIN_VALUE) != 0x0) ? 0 : 16711680);
        }
        final int n27 = (int)array[1];
        int n28;
        if ((n27 & 0xFFFFFF00) == 0x0) {
            n28 = (n26 | n27 << 8);
        }
        else {
            n28 = (n26 | (((n27 & Integer.MIN_VALUE) != 0x0) ? 0 : 65280));
        }
        final int n29 = (int)array[2];
        int n30;
        if ((n29 & 0xFFFFFF00) == 0x0) {
            n30 = (n28 | n29);
        }
        else {
            n30 = (n28 | (((n29 & Integer.MIN_VALUE) != 0x0) ? 0 : 255));
        }
        final int n31 = (int)array[3];
        int n32;
        if ((n31 & 0xFFFFFF00) == 0x0) {
            n32 = (n30 | n31 << 24);
        }
        else {
            n32 = (n30 | (((n31 & Integer.MIN_VALUE) != 0x0) ? 0 : -16777216));
        }
        return n32;
    }
    
    private final void turbulenceFractal(final int[] array, double n, double n2, final double[] array2, final double[] array3) {
        double n3 = 127.5;
        final int n4 = 0;
        final int n5 = 1;
        final int n6 = 2;
        final int n7 = 3;
        final double n8 = 127.5;
        array2[n6] = (array2[n7] = n8);
        array2[n4] = (array2[n5] = n8);
        n *= this.baseFrequencyX;
        n2 *= this.baseFrequencyY;
        for (int i = this.numOctaves; i > 0; --i) {
            this.noise2(array3, n, n2);
            switch (this.channels.length) {
                case 4: {
                    final int n9 = 3;
                    array2[n9] += array3[3] * n3;
                }
                case 3: {
                    final int n10 = 2;
                    array2[n10] += array3[2] * n3;
                }
                case 2: {
                    final int n11 = 1;
                    array2[n11] += array3[1] * n3;
                }
                case 1: {
                    final int n12 = 0;
                    array2[n12] += array3[0] * n3;
                    break;
                }
            }
            n3 *= 0.5;
            n *= 2.0;
            n2 *= 2.0;
        }
        switch (this.channels.length) {
            case 4: {
                array[3] = (int)array2[3];
                if ((array[3] & 0xFFFFFF00) != 0x0) {
                    array[3] = (((array[3] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
            }
            case 3: {
                array[2] = (int)array2[2];
                if ((array[2] & 0xFFFFFF00) != 0x0) {
                    array[2] = (((array[2] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
            }
            case 2: {
                array[1] = (int)array2[1];
                if ((array[1] & 0xFFFFFF00) != 0x0) {
                    array[1] = (((array[1] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
            }
            case 1: {
                array[0] = (int)array2[0];
                if ((array[0] & 0xFFFFFF00) != 0x0) {
                    array[0] = (((array[0] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    break;
                }
                break;
            }
        }
    }
    
    private final void turbulenceFractalStitch(final int[] array, double n, double n2, final double[] array2, final double[] array3, final StitchInfo stitchInfo) {
        double n3 = 127.5;
        final int n4 = 0;
        final int n5 = 1;
        final int n6 = 2;
        final int n7 = 3;
        final double n8 = 127.5;
        array2[n6] = (array2[n7] = n8);
        array2[n4] = (array2[n5] = n8);
        n *= this.baseFrequencyX;
        n2 *= this.baseFrequencyY;
        for (int i = this.numOctaves; i > 0; --i) {
            this.noise2Stitch(array3, n, n2, stitchInfo);
            switch (this.channels.length) {
                case 4: {
                    final int n9 = 3;
                    array2[n9] += array3[3] * n3;
                }
                case 3: {
                    final int n10 = 2;
                    array2[n10] += array3[2] * n3;
                }
                case 2: {
                    final int n11 = 1;
                    array2[n11] += array3[1] * n3;
                }
                case 1: {
                    final int n12 = 0;
                    array2[n12] += array3[0] * n3;
                    break;
                }
            }
            n3 *= 0.5;
            n *= 2.0;
            n2 *= 2.0;
            stitchInfo.doubleFrequency();
        }
        switch (this.channels.length) {
            case 4: {
                array[3] = (int)array2[3];
                if ((array[3] & 0xFFFFFF00) != 0x0) {
                    array[3] = (((array[3] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
            }
            case 3: {
                array[2] = (int)array2[2];
                if ((array[2] & 0xFFFFFF00) != 0x0) {
                    array[2] = (((array[2] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
            }
            case 2: {
                array[1] = (int)array2[1];
                if ((array[1] & 0xFFFFFF00) != 0x0) {
                    array[1] = (((array[1] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
            }
            case 1: {
                array[0] = (int)array2[0];
                if ((array[0] & 0xFFFFFF00) != 0x0) {
                    array[0] = (((array[0] & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    break;
                }
                break;
            }
        }
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        if (writableRaster == null) {
            throw new IllegalArgumentException("Cannot generate a noise pattern into a null raster");
        }
        final int width = writableRaster.getWidth();
        final int height = writableRaster.getHeight();
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        final int minX = writableRaster.getMinX();
        final int minY = writableRaster.getMinY();
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int n = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(minX - writableRaster.getSampleModelTranslateX(), minY - writableRaster.getSampleModelTranslateY());
        final int[] array = dataBufferInt.getBankData()[0];
        final int n2 = singlePixelPackedSampleModel.getScanlineStride() - width;
        int i = n;
        final int[] array2 = new int[4];
        final double[] array3 = { 0.0, 0.0, 0.0, 0.0 };
        final double[] array4 = { 0.0, 0.0, 0.0, 0.0 };
        final double n3 = this.tx[0];
        final double n4 = this.tx[1];
        final double n5 = this.ty[0] - width * n3;
        final double n6 = this.ty[1] - width * n4;
        final double[] array5 = { minX, minY };
        this.txf.transform(array5, 0, array5, 0, 1);
        double n7 = array5[0];
        double n8 = array5[1];
        if (this.isFractalNoise) {
            if (this.stitchInfo == null) {
                if (this.channels.length == 4) {
                    for (int j = 0; j < height; ++j) {
                        while (i < i + width) {
                            array[i] = this.turbulenceFractal_4(n7, n8, array3);
                            n7 += n3;
                            n8 += n4;
                            ++i;
                        }
                        n7 += n5;
                        n8 += n6;
                        i += n2;
                    }
                }
                else {
                    for (int k = 0; k < height; ++k) {
                        while (i < i + width) {
                            this.turbulenceFractal(array2, n7, n8, array3, array4);
                            array[i] = (array2[3] << 24 | array2[0] << 16 | array2[1] << 8 | array2[2]);
                            n7 += n3;
                            n8 += n4;
                            ++i;
                        }
                        n7 += n5;
                        n8 += n6;
                        i += n2;
                    }
                }
            }
            else {
                final StitchInfo stitchInfo = new StitchInfo();
                for (int l = 0; l < height; ++l) {
                    while (i < i + width) {
                        stitchInfo.assign(this.stitchInfo);
                        this.turbulenceFractalStitch(array2, n7, n8, array3, array4, stitchInfo);
                        array[i] = (array2[3] << 24 | array2[0] << 16 | array2[1] << 8 | array2[2]);
                        n7 += n3;
                        n8 += n4;
                        ++i;
                    }
                    n7 += n5;
                    n8 += n6;
                    i += n2;
                }
            }
        }
        else if (this.stitchInfo == null) {
            if (this.channels.length == 4) {
                for (int n9 = 0; n9 < height; ++n9) {
                    while (i < i + width) {
                        array[i] = this.turbulence_4(n7, n8, array3);
                        n7 += n3;
                        n8 += n4;
                        ++i;
                    }
                    n7 += n5;
                    n8 += n6;
                    i += n2;
                }
            }
            else {
                for (int n10 = 0; n10 < height; ++n10) {
                    while (i < i + width) {
                        this.turbulence(array2, n7, n8, array3, array4);
                        array[i] = (array2[3] << 24 | array2[0] << 16 | array2[1] << 8 | array2[2]);
                        n7 += n3;
                        n8 += n4;
                        ++i;
                    }
                    n7 += n5;
                    n8 += n6;
                    i += n2;
                }
            }
        }
        else {
            final StitchInfo stitchInfo2 = new StitchInfo();
            for (int n11 = 0; n11 < height; ++n11) {
                while (i < i + width) {
                    stitchInfo2.assign(this.stitchInfo);
                    this.turbulenceStitch(array2, n7, n8, array3, array4, stitchInfo2);
                    array[i] = (array2[3] << 24 | array2[0] << 16 | array2[1] << 8 | array2[2]);
                    n7 += n3;
                    n8 += n4;
                    ++i;
                }
                n7 += n5;
                n8 += n6;
                i += n2;
            }
        }
        return writableRaster;
    }
    
    public TurbulencePatternRed(final double n, final double n2, final int n3, final int seed, final boolean isFractalNoise, final Rectangle2D tile, final AffineTransform txf, final Rectangle rectangle, final ColorSpace colorSpace, final boolean b) {
        this.stitchInfo = null;
        this.tx = new double[] { 1.0, 0.0 };
        this.ty = new double[] { 0.0, 1.0 };
        this.latticeSelector = new int[257];
        this.gradient = new double[2056];
        this.baseFrequencyX = n;
        this.baseFrequencyY = n2;
        this.seed = seed;
        this.isFractalNoise = isFractalNoise;
        this.tile = tile;
        this.txf = txf;
        if (this.txf == null) {
            this.txf = TurbulencePatternRed.IDENTITY;
        }
        int numComponents = colorSpace.getNumComponents();
        if (b) {
            ++numComponents;
        }
        this.channels = new int[numComponents];
        for (int i = 0; i < this.channels.length; ++i) {
            this.channels[i] = i;
        }
        txf.deltaTransform(this.tx, 0, this.tx, 0, 1);
        txf.deltaTransform(this.ty, 0, this.ty, 0, 1);
        final double[] array = { 0.5, 0.0 };
        final double[] array2 = { 0.0, 0.5 };
        txf.deltaTransform(array, 0, array, 0, 1);
        txf.deltaTransform(array2, 0, array2, 0, 1);
        final int n4 = -(int)Math.round((Math.log(Math.max(Math.abs(array[0]), Math.abs(array2[0]))) + Math.log(n)) / Math.log(2.0));
        final int n5 = -(int)Math.round((Math.log(Math.max(Math.abs(array[1]), Math.abs(array2[1]))) + Math.log(n2)) / Math.log(2.0));
        this.numOctaves = ((n3 > n4) ? n4 : n3);
        this.numOctaves = ((this.numOctaves > n5) ? n5 : this.numOctaves);
        if (this.numOctaves < 1 && n3 > 1) {
            this.numOctaves = 1;
        }
        if (this.numOctaves > 8) {
            this.numOctaves = 8;
        }
        if (tile != null) {
            final double baseFrequencyX = Math.floor(tile.getWidth() * n) / tile.getWidth();
            final double baseFrequencyX2 = Math.ceil(tile.getWidth() * n) / tile.getWidth();
            if (n / baseFrequencyX < baseFrequencyX2 / n) {
                this.baseFrequencyX = baseFrequencyX;
            }
            else {
                this.baseFrequencyX = baseFrequencyX2;
            }
            final double baseFrequencyY = Math.floor(tile.getHeight() * n2) / tile.getHeight();
            final double baseFrequencyY2 = Math.ceil(tile.getHeight() * n2) / tile.getHeight();
            if (n2 / baseFrequencyY < baseFrequencyY2 / n2) {
                this.baseFrequencyY = baseFrequencyY;
            }
            else {
                this.baseFrequencyY = baseFrequencyY2;
            }
            this.stitchInfo = new StitchInfo();
            this.stitchInfo.width = (int)(tile.getWidth() * this.baseFrequencyX);
            this.stitchInfo.height = (int)(tile.getHeight() * this.baseFrequencyY);
            this.stitchInfo.wrapX = (int)(tile.getX() * this.baseFrequencyX + 4096.0 + this.stitchInfo.width);
            this.stitchInfo.wrapY = (int)(tile.getY() * this.baseFrequencyY + 4096.0 + this.stitchInfo.height);
            if (this.stitchInfo.width == 0) {
                this.stitchInfo.width = 1;
            }
            if (this.stitchInfo.height == 0) {
                this.stitchInfo.height = 1;
            }
        }
        this.initLattice(seed);
        DirectColorModel directColorModel;
        if (b) {
            directColorModel = new DirectColorModel(colorSpace, 32, 16711680, 65280, 255, -16777216, false, 3);
        }
        else {
            directColorModel = new DirectColorModel(colorSpace, 24, 16711680, 65280, 255, 0, false, 3);
        }
        final int defaultTileSize = AbstractTiledRed.getDefaultTileSize();
        this.init((CachableRed)null, rectangle, directColorModel, directColorModel.createCompatibleSampleModel(defaultTileSize, defaultTileSize), 0, 0, null);
    }
    
    static {
        IDENTITY = new AffineTransform();
    }
    
    static final class StitchInfo
    {
        int width;
        int height;
        int wrapX;
        int wrapY;
        
        StitchInfo() {
        }
        
        StitchInfo(final StitchInfo stitchInfo) {
            this.width = stitchInfo.width;
            this.height = stitchInfo.height;
            this.wrapX = stitchInfo.wrapX;
            this.wrapY = stitchInfo.wrapY;
        }
        
        final void assign(final StitchInfo stitchInfo) {
            this.width = stitchInfo.width;
            this.height = stitchInfo.height;
            this.wrapX = stitchInfo.wrapX;
            this.wrapY = stitchInfo.wrapY;
        }
        
        final void doubleFrequency() {
            this.width *= 2;
            this.height *= 2;
            this.wrapX *= 2;
            this.wrapY *= 2;
            this.wrapX -= (int)4096.0;
            this.wrapY -= (int)4096.0;
        }
    }
}
