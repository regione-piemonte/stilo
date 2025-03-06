// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.photometricinterpreters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.color.ColorConversions;
import org.apache.commons.imaging.common.ImageBuilder;

public class PhotometricInterpreterCieLab extends PhotometricInterpreter
{
    public PhotometricInterpreterCieLab(final int samplesPerPixel, final int[] bitsPerSample, final int predictor, final int width, final int height) {
        super(samplesPerPixel, bitsPerSample, predictor, width, height);
    }
    
    @Override
    public void interpretPixel(final ImageBuilder imageBuilder, final int[] samples, final int x, final int y) throws ImageReadException, IOException {
        final int cieL = samples[0];
        final int cieA = (byte)samples[1];
        final int cieB = (byte)samples[2];
        final int rgb = ColorConversions.convertCIELabtoARGBTest(cieL, cieA, cieB);
        imageBuilder.setRGB(x, y, rgb);
    }
}
