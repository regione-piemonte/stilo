// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.photometricinterpreters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageBuilder;

public class PhotometricInterpreterBiLevel extends PhotometricInterpreter
{
    private final boolean invert;
    
    public PhotometricInterpreterBiLevel(final int samplesPerPixel, final int[] bitsPerSample, final int predictor, final int width, final int height, final boolean invert) {
        super(samplesPerPixel, bitsPerSample, predictor, width, height);
        this.invert = invert;
    }
    
    @Override
    public void interpretPixel(final ImageBuilder imageBuilder, final int[] samples, final int x, final int y) throws ImageReadException, IOException {
        int sample = samples[0];
        if (this.invert) {
            sample = 255 - sample;
        }
        final int red = sample;
        final int green = sample;
        final int blue = sample;
        final int alpha = 255;
        final int rgb = 0xFF000000 | red << 16 | green << 8 | blue << 0;
        imageBuilder.setRGB(x, y, rgb);
    }
}
