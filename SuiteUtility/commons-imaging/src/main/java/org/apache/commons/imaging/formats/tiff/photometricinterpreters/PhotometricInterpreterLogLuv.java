// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.photometricinterpreters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageBuilder;

public class PhotometricInterpreterLogLuv extends PhotometricInterpreter
{
    public PhotometricInterpreterLogLuv(final int samplesPerPixel, final int[] bitsPerSample, final int predictor, final int width, final int height) {
        super(samplesPerPixel, bitsPerSample, predictor, width, height);
    }
    
    private float cube(final float f) {
        return f * f * f;
    }
    
    @Override
    public void interpretPixel(final ImageBuilder imageBuilder, final int[] samples, final int x, final int y) throws ImageReadException, IOException {
        final int cieL = samples[0];
        final int cieA = (byte)samples[1];
        final int cieB = (byte)samples[2];
        float var_Y = (cieL * 100.0f / 255.0f + 16.0f) / 116.0f;
        float var_X = cieA / 500.0f + var_Y;
        float var_Z = var_Y - cieB / 200.0f;
        final float var_x_cube = this.cube(var_X);
        final float var_y_cube = this.cube(var_Y);
        final float var_z_cube = this.cube(var_Z);
        if (var_y_cube > 0.008856f) {
            var_Y = var_y_cube;
        }
        else {
            var_Y = (var_Y - 0.13793103f) / 7.787f;
        }
        if (var_x_cube > 0.008856f) {
            var_X = var_x_cube;
        }
        else {
            var_X = (var_X - 0.13793103f) / 7.787f;
        }
        if (var_z_cube > 0.008856f) {
            var_Z = var_z_cube;
        }
        else {
            var_Z = (var_Z - 0.13793103f) / 7.787f;
        }
        final float ref_X = 95.047f;
        final float ref_Y = 100.0f;
        final float ref_Z = 108.883f;
        final float X = 95.047f * var_X;
        final float Y = 100.0f * var_Y;
        final float Z = 108.883f * var_Z;
        final float var_X2 = X / 100.0f;
        final float var_Y2 = Y / 100.0f;
        final float var_Z2 = Z / 100.0f;
        float var_R = var_X2 * 3.2406f + var_Y2 * -1.5372f + var_Z2 * -0.4986f;
        float var_G = var_X2 * -0.9689f + var_Y2 * 1.8758f + var_Z2 * 0.0415f;
        float var_B = var_X2 * 0.0557f + var_Y2 * -0.204f + var_Z2 * 1.057f;
        if (var_R > 0.0031308) {
            var_R = 1.055f * (float)Math.pow(var_R, 0.4166666666666667) - 0.055f;
        }
        else {
            var_R *= 12.92f;
        }
        if (var_G > 0.0031308) {
            var_G = 1.055f * (float)Math.pow(var_G, 0.4166666666666667) - 0.055f;
        }
        else {
            var_G *= 12.92f;
        }
        if (var_B > 0.0031308) {
            var_B = 1.055f * (float)Math.pow(var_B, 0.4166666666666667) - 0.055f;
        }
        else {
            var_B *= 12.92f;
        }
        final int R = (int)(var_R * 255.0f);
        final int G = (int)(var_G * 255.0f);
        final int B = (int)(var_B * 255.0f);
        int red = R;
        int green = G;
        int blue = B;
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));
        final int alpha = 255;
        final int rgb = 0xFF000000 | red << 16 | green << 8 | blue << 0;
        imageBuilder.setRGB(x, y, rgb);
    }
}
