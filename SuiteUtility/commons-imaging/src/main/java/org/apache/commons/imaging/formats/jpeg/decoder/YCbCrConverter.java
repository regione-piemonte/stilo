// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.decoder;

final class YCbCrConverter
{
    private static final int[] REDS;
    private static final int[] BLUES;
    private static final int[] GREENS1;
    private static final int[] GREENS2;
    
    private YCbCrConverter() {
    }
    
    private static int fastRound(final float x) {
        return (int)(x + 0.5f);
    }
    
    public static int convertYCbCrToRGB(final int Y, final int Cb, final int Cr) {
        final int r = YCbCrConverter.REDS[Cr << 8 | Y];
        final int g1 = YCbCrConverter.GREENS1[Cb << 8 | Cr];
        final int g2 = YCbCrConverter.GREENS2[g1 << 8 | Y];
        final int b = YCbCrConverter.BLUES[Cb << 8 | Y];
        return r | g2 | b;
    }
    
    static {
        REDS = new int[65536];
        BLUES = new int[65536];
        GREENS1 = new int[65536];
        GREENS2 = new int[131072];
        for (int Y = 0; Y < 256; ++Y) {
            for (int Cr = 0; Cr < 256; ++Cr) {
                int r = Y + fastRound(1.402f * (Cr - 128));
                if (r < 0) {
                    r = 0;
                }
                if (r > 255) {
                    r = 255;
                }
                YCbCrConverter.REDS[Cr << 8 | Y] = r << 16;
            }
        }
        for (int Y = 0; Y < 256; ++Y) {
            for (int Cb = 0; Cb < 256; ++Cb) {
                int b = Y + fastRound(1.772f * (Cb - 128));
                if (b < 0) {
                    b = 0;
                }
                if (b > 255) {
                    b = 255;
                }
                YCbCrConverter.BLUES[Cb << 8 | Y] = b;
            }
        }
        for (int Cb2 = 0; Cb2 < 256; ++Cb2) {
            for (int Cr = 0; Cr < 256; ++Cr) {
                final int value = fastRound(0.34414f * (Cb2 - 128) + 0.71414f * (Cr - 128));
                YCbCrConverter.GREENS1[Cb2 << 8 | Cr] = value + 135;
            }
        }
        for (int Y = 0; Y < 256; ++Y) {
            for (int value2 = 0; value2 < 270; ++value2) {
                int green = Y - (value2 - 135);
                if (green < 0) {
                    green = 0;
                }
                else if (green > 255) {
                    green = 255;
                }
                YCbCrConverter.GREENS2[value2 << 8 | Y] = green << 8;
            }
        }
    }
}
