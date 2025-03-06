// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.photometricinterpreters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageBuilder;

public class PhotometricInterpreterRgb extends PhotometricInterpreter
{
    public PhotometricInterpreterRgb(final int samplesPerPixel, final int[] bitsPerSample, final int predictor, final int width, final int height) {
        super(samplesPerPixel, bitsPerSample, predictor, width, height);
    }
    
    @Override
    public void interpretPixel(final ImageBuilder imageBuilder, final int[] samples, final int x, final int y) throws ImageReadException, IOException {
        final int red = samples[0];
        final int green = samples[1];
        final int blue = samples[2];
        final int alpha = 255;
        final int rgb = 0xFF000000 | red << 16 | green << 8 | blue << 0;
        imageBuilder.setRGB(x, y, rgb);
    }
}
