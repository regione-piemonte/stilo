// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.decoder;

final class Dct
{
    private static final float[] DCT_SCALING_FACTORS;
    private static final float[] IDCT_SCALING_FACTORS;
    private static final float A1;
    private static final float A2;
    private static final float A3;
    private static final float A4;
    private static final float A5;
    private static final float C2;
    private static final float C4;
    private static final float C6;
    private static final float Q;
    private static final float R;
    
    private Dct() {
    }
    
    public static void scaleQuantizationVector(final float[] vector) {
        for (int x = 0; x < 8; ++x) {
            final int n = x;
            vector[n] *= Dct.DCT_SCALING_FACTORS[x];
        }
    }
    
    public static void scaleDequantizationVector(final float[] vector) {
        for (int x = 0; x < 8; ++x) {
            final int n = x;
            vector[n] *= Dct.IDCT_SCALING_FACTORS[x];
        }
    }
    
    public static void scaleQuantizationMatrix(final float[] matrix) {
        for (int y = 0; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                final int n = 8 * y + x;
                matrix[n] *= Dct.DCT_SCALING_FACTORS[y] * Dct.DCT_SCALING_FACTORS[x];
            }
        }
    }
    
    public static void scaleDequantizationMatrix(final float[] matrix) {
        for (int y = 0; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                final int n = 8 * y + x;
                matrix[n] *= Dct.IDCT_SCALING_FACTORS[y] * Dct.IDCT_SCALING_FACTORS[x];
            }
        }
    }
    
    public static void forwardDCT8(final float[] vector) {
        final float a00 = vector[0] + vector[7];
        final float a2 = vector[1] + vector[6];
        final float a3 = vector[2] + vector[5];
        final float a4 = vector[3] + vector[4];
        final float a5 = vector[3] - vector[4];
        final float a6 = vector[2] - vector[5];
        final float a7 = vector[1] - vector[6];
        final float a8 = vector[0] - vector[7];
        final float a9 = a00 + a4;
        final float a10 = a2 + a3;
        final float a11 = a2 - a3;
        final float a12 = a00 - a4;
        final float neg_a41 = a5 + a6;
        final float a13 = a6 + a7;
        final float a14 = a7 + a8;
        final float a15 = a11 + a12;
        final float a16 = a15 * Dct.A1;
        final float mul5 = (a14 - neg_a41) * Dct.A5;
        final float a17 = neg_a41 * Dct.A2 - mul5;
        final float a18 = a13 * Dct.A3;
        final float a19 = a14 * Dct.A4 - mul5;
        final float a20 = a8 + a18;
        final float a21 = a8 - a18;
        vector[0] = a9 + a10;
        vector[4] = a9 - a10;
        vector[2] = a12 + a16;
        vector[6] = a12 - a16;
        vector[5] = a21 + a17;
        vector[1] = a20 + a19;
        vector[7] = a20 - a19;
        vector[3] = a21 - a17;
    }
    
    public static void forwardDCT8x8(final float[] matrix) {
        for (int i = 0; i < 8; ++i) {
            final float a00 = matrix[8 * i] + matrix[8 * i + 7];
            final float a2 = matrix[8 * i + 1] + matrix[8 * i + 6];
            final float a3 = matrix[8 * i + 2] + matrix[8 * i + 5];
            final float a4 = matrix[8 * i + 3] + matrix[8 * i + 4];
            final float a5 = matrix[8 * i + 3] - matrix[8 * i + 4];
            final float a6 = matrix[8 * i + 2] - matrix[8 * i + 5];
            final float a7 = matrix[8 * i + 1] - matrix[8 * i + 6];
            final float a8 = matrix[8 * i] - matrix[8 * i + 7];
            final float a9 = a00 + a4;
            final float a10 = a2 + a3;
            final float a11 = a2 - a3;
            final float a12 = a00 - a4;
            final float neg_a41 = a5 + a6;
            final float a13 = a6 + a7;
            final float a14 = a7 + a8;
            final float a15 = a11 + a12;
            final float a16 = a15 * Dct.A1;
            final float mul5 = (a14 - neg_a41) * Dct.A5;
            final float a17 = neg_a41 * Dct.A2 - mul5;
            final float a18 = a13 * Dct.A3;
            final float a19 = a14 * Dct.A4 - mul5;
            final float a20 = a8 + a18;
            final float a21 = a8 - a18;
            matrix[8 * i] = a9 + a10;
            matrix[8 * i + 4] = a9 - a10;
            matrix[8 * i + 2] = a12 + a16;
            matrix[8 * i + 6] = a12 - a16;
            matrix[8 * i + 5] = a21 + a17;
            matrix[8 * i + 1] = a20 + a19;
            matrix[8 * i + 7] = a20 - a19;
            matrix[8 * i + 3] = a21 - a17;
        }
        for (int i = 0; i < 8; ++i) {
            final float a00 = matrix[i] + matrix[56 + i];
            final float a2 = matrix[8 + i] + matrix[48 + i];
            final float a3 = matrix[16 + i] + matrix[40 + i];
            final float a4 = matrix[24 + i] + matrix[32 + i];
            final float a5 = matrix[24 + i] - matrix[32 + i];
            final float a6 = matrix[16 + i] - matrix[40 + i];
            final float a7 = matrix[8 + i] - matrix[48 + i];
            final float a8 = matrix[i] - matrix[56 + i];
            final float a9 = a00 + a4;
            final float a10 = a2 + a3;
            final float a11 = a2 - a3;
            final float a12 = a00 - a4;
            final float neg_a41 = a5 + a6;
            final float a13 = a6 + a7;
            final float a14 = a7 + a8;
            final float a15 = a11 + a12;
            final float a16 = a15 * Dct.A1;
            final float mul5 = (a14 - neg_a41) * Dct.A5;
            final float a17 = neg_a41 * Dct.A2 - mul5;
            final float a18 = a13 * Dct.A3;
            final float a19 = a14 * Dct.A4 - mul5;
            final float a20 = a8 + a18;
            final float a21 = a8 - a18;
            matrix[i] = a9 + a10;
            matrix[32 + i] = a9 - a10;
            matrix[16 + i] = a12 + a16;
            matrix[48 + i] = a12 - a16;
            matrix[40 + i] = a21 + a17;
            matrix[8 + i] = a20 + a19;
            matrix[56 + i] = a20 - a19;
            matrix[24 + i] = a21 - a17;
        }
    }
    
    public static void inverseDCT8(final float[] vector) {
        final float a2 = vector[2] - vector[6];
        final float a3 = vector[2] + vector[6];
        final float a4 = vector[5] - vector[3];
        final float tmp1 = vector[1] + vector[7];
        final float tmp2 = vector[3] + vector[5];
        final float a5 = tmp1 - tmp2;
        final float a6 = vector[1] - vector[7];
        final float a7 = tmp1 + tmp2;
        final float tmp3 = Dct.C6 * (a4 + a6);
        final float neg_b4 = Dct.Q * a4 + tmp3;
        final float b6 = Dct.R * a6 - tmp3;
        final float b7 = a2 * Dct.C4;
        final float b8 = a5 * Dct.C4;
        final float tmp4 = b6 - a7;
        final float n0 = tmp4 - b8;
        final float n2 = vector[0] - vector[4];
        final float n3 = b7 - a3;
        final float n4 = vector[0] + vector[4];
        final float neg_n5 = neg_b4;
        final float m3 = n2 + n3;
        final float m4 = n4 + a3;
        final float m5 = n2 - n3;
        final float m6 = n4 - a3;
        final float neg_m7 = neg_n5 + n0;
        vector[0] = m4 + a7;
        vector[1] = m3 + tmp4;
        vector[2] = m5 - n0;
        vector[3] = m6 + neg_m7;
        vector[4] = m6 - neg_m7;
        vector[5] = m5 + n0;
        vector[6] = m3 - tmp4;
        vector[7] = m4 - a7;
    }
    
    public static void inverseDCT8x8(final float[] matrix) {
        for (int i = 0; i < 8; ++i) {
            final float a2 = matrix[8 * i + 2] - matrix[8 * i + 6];
            final float a3 = matrix[8 * i + 2] + matrix[8 * i + 6];
            final float a4 = matrix[8 * i + 5] - matrix[8 * i + 3];
            final float tmp1 = matrix[8 * i + 1] + matrix[8 * i + 7];
            final float tmp2 = matrix[8 * i + 3] + matrix[8 * i + 5];
            final float a5 = tmp1 - tmp2;
            final float a6 = matrix[8 * i + 1] - matrix[8 * i + 7];
            final float a7 = tmp1 + tmp2;
            final float tmp3 = Dct.C6 * (a4 + a6);
            final float neg_b4 = Dct.Q * a4 + tmp3;
            final float b6 = Dct.R * a6 - tmp3;
            final float b7 = a2 * Dct.C4;
            final float b8 = a5 * Dct.C4;
            final float tmp4 = b6 - a7;
            final float n0 = tmp4 - b8;
            final float n2 = matrix[8 * i] - matrix[8 * i + 4];
            final float n3 = b7 - a3;
            final float n4 = matrix[8 * i] + matrix[8 * i + 4];
            final float neg_n5 = neg_b4;
            final float m3 = n2 + n3;
            final float m4 = n4 + a3;
            final float m5 = n2 - n3;
            final float m6 = n4 - a3;
            final float neg_m7 = neg_n5 + n0;
            matrix[8 * i] = m4 + a7;
            matrix[8 * i + 1] = m3 + tmp4;
            matrix[8 * i + 2] = m5 - n0;
            matrix[8 * i + 3] = m6 + neg_m7;
            matrix[8 * i + 4] = m6 - neg_m7;
            matrix[8 * i + 5] = m5 + n0;
            matrix[8 * i + 6] = m3 - tmp4;
            matrix[8 * i + 7] = m4 - a7;
        }
        for (int i = 0; i < 8; ++i) {
            final float a2 = matrix[16 + i] - matrix[48 + i];
            final float a3 = matrix[16 + i] + matrix[48 + i];
            final float a4 = matrix[40 + i] - matrix[24 + i];
            final float tmp1 = matrix[8 + i] + matrix[56 + i];
            final float tmp2 = matrix[24 + i] + matrix[40 + i];
            final float a5 = tmp1 - tmp2;
            final float a6 = matrix[8 + i] - matrix[56 + i];
            final float a7 = tmp1 + tmp2;
            final float tmp3 = Dct.C6 * (a4 + a6);
            final float neg_b4 = Dct.Q * a4 + tmp3;
            final float b6 = Dct.R * a6 - tmp3;
            final float b7 = a2 * Dct.C4;
            final float b8 = a5 * Dct.C4;
            final float tmp4 = b6 - a7;
            final float n0 = tmp4 - b8;
            final float n2 = matrix[i] - matrix[32 + i];
            final float n3 = b7 - a3;
            final float n4 = matrix[i] + matrix[32 + i];
            final float neg_n5 = neg_b4;
            final float m3 = n2 + n3;
            final float m4 = n4 + a3;
            final float m5 = n2 - n3;
            final float m6 = n4 - a3;
            final float neg_m7 = neg_n5 + n0;
            matrix[i] = m4 + a7;
            matrix[8 + i] = m3 + tmp4;
            matrix[16 + i] = m5 - n0;
            matrix[24 + i] = m6 + neg_m7;
            matrix[32 + i] = m6 - neg_m7;
            matrix[40 + i] = m5 + n0;
            matrix[48 + i] = m3 - tmp4;
            matrix[56 + i] = m4 - a7;
        }
    }
    
    static {
        DCT_SCALING_FACTORS = new float[] { (float)(0.5 / Math.sqrt(2.0)), (float)(0.25 / Math.cos(0.19634954084936207)), (float)(0.25 / Math.cos(0.39269908169872414)), (float)(0.25 / Math.cos(0.5890486225480862)), (float)(0.25 / Math.cos(0.7853981633974483)), (float)(0.25 / Math.cos(0.9817477042468103)), (float)(0.25 / Math.cos(1.1780972450961724)), (float)(0.25 / Math.cos(1.3744467859455345)) };
        IDCT_SCALING_FACTORS = new float[] { (float)(8.0 / Math.sqrt(2.0) * 0.0625), (float)(4.0 * Math.cos(0.19634954084936207) * 0.125), (float)(4.0 * Math.cos(0.39269908169872414) * 0.125), (float)(4.0 * Math.cos(0.5890486225480862) * 0.125), (float)(4.0 * Math.cos(0.7853981633974483) * 0.125), (float)(4.0 * Math.cos(0.9817477042468103) * 0.125), (float)(4.0 * Math.cos(1.1780972450961724) * 0.125), (float)(4.0 * Math.cos(1.3744467859455345) * 0.125) };
        A1 = (float)Math.cos(0.7853981633974483);
        A2 = (float)(Math.cos(0.39269908169872414) - Math.cos(1.1780972450961724));
        A3 = Dct.A1;
        A4 = (float)(Math.cos(0.39269908169872414) + Math.cos(1.1780972450961724));
        A5 = (float)Math.cos(1.1780972450961724);
        C2 = (float)(2.0 * Math.cos(0.39269908169872414));
        C4 = (float)(2.0 * Math.cos(0.7853981633974483));
        C6 = (float)(2.0 * Math.cos(1.1780972450961724));
        Q = Dct.C2 - Dct.C6;
        R = Dct.C2 + Dct.C6;
    }
}
