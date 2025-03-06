// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.photometricinterpreters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageBuilder;

public class PhotometricInterpreterPalette extends PhotometricInterpreter
{
    private final int[] indexColorMap;
    
    public PhotometricInterpreterPalette(final int samplesPerPixel, final int[] bitsPerSample, final int predictor, final int width, final int height, final int[] colorMap) {
        super(samplesPerPixel, bitsPerSample, predictor, width, height);
        final int bitsPerPixel = this.getBitsPerSample(0);
        final int colormapScale = 1 << bitsPerPixel;
        this.indexColorMap = new int[colormapScale];
        for (int index = 0; index < colormapScale; ++index) {
            final int red = colorMap[index] >> 8 & 0xFF;
            final int green = colorMap[index + colormapScale] >> 8 & 0xFF;
            final int blue = colorMap[index + 2 * colormapScale] >> 8 & 0xFF;
            this.indexColorMap[index] = (0xFF000000 | red << 16 | green << 8 | blue);
        }
    }
    
    @Override
    public void interpretPixel(final ImageBuilder imageBuilder, final int[] samples, final int x, final int y) throws ImageReadException, IOException {
        imageBuilder.setRGB(x, y, this.indexColorMap[samples[0]]);
    }
}
