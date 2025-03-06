// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.photometricinterpreters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.color.ColorConversions;
import org.apache.commons.imaging.common.ImageBuilder;

public class PhotometricInterpreterCmyk extends PhotometricInterpreter
{
    public PhotometricInterpreterCmyk(final int samplesPerPixel, final int[] bitsPerSample, final int predictor, final int width, final int height) {
        super(samplesPerPixel, bitsPerSample, predictor, width, height);
    }
    
    @Override
    public void interpretPixel(final ImageBuilder imageBuilder, final int[] samples, final int x, final int y) throws ImageReadException, IOException {
        final int sc = samples[0];
        final int sm = samples[1];
        final int sy = samples[2];
        final int sk = samples[3];
        final int rgb = ColorConversions.convertCMYKtoRGB(sc, sm, sy, sk);
        imageBuilder.setRGB(x, y, rgb);
    }
}
