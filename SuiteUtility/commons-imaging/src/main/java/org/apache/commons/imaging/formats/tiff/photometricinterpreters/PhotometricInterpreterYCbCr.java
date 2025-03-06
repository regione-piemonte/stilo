// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.photometricinterpreters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageBuilder;

public class PhotometricInterpreterYCbCr extends PhotometricInterpreter
{
    public PhotometricInterpreterYCbCr(final int samplesPerPixel, final int[] bitsPerSample, final int predictor, final int width, final int height) {
        super(samplesPerPixel, bitsPerSample, predictor, width, height);
    }
    
    public static int limit(final int value, final int min, final int max) {
        return Math.min(max, Math.max(min, value));
    }
    
    public static int convertYCbCrtoRGB(final int Y, final int Cb, final int Cr) {
        final double r1 = 1.164 * (Y - 16.0) + 1.596 * (Cr - 128.0);
        final double g1 = 1.164 * (Y - 16.0) - 0.813 * (Cr - 128.0) - 0.392 * (Cb - 128.0);
        final double b1 = 1.164 * (Y - 16.0) + 2.017 * (Cb - 128.0);
        final int r2 = limit((int)r1, 0, 255);
        final int g2 = limit((int)g1, 0, 255);
        final int b2 = limit((int)b1, 0, 255);
        final int alpha = 255;
        final int rgb = 0xFF000000 | r2 << 16 | g2 << 8 | b2 << 0;
        return rgb;
    }
    
    @Override
    public void interpretPixel(final ImageBuilder imageBuilder, final int[] samples, final int x, final int y) throws ImageReadException, IOException {
        final int Y = samples[0];
        final int Cb = samples[1];
        final int Cr = samples[2];
        final double R = Y + 1.402 * (Cr - 128.0);
        final double G = Y - 0.34414 * (Cb - 128.0) - 0.71414 * (Cr - 128.0);
        final double B = Y + 1.772 * (Cb - 128.0);
        final int red = limit((int)R, 0, 255);
        final int green = limit((int)G, 0, 255);
        final int blue = limit((int)B, 0, 255);
        final int alpha = 255;
        final int rgb = 0xFF000000 | red << 16 | green << 8 | blue << 0;
        imageBuilder.setRGB(x, y, rgb);
    }
}
