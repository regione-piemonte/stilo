// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GammaCorrection
{
    private static final Logger LOGGER;
    private final int[] lookupTable;
    
    public GammaCorrection(final double srcGamma, final double dstGamma) {
        if (GammaCorrection.LOGGER.isLoggable(Level.FINEST)) {
            GammaCorrection.LOGGER.finest("src_gamma: " + srcGamma);
            GammaCorrection.LOGGER.finest("dst_gamma: " + dstGamma);
        }
        this.lookupTable = new int[256];
        for (int i = 0; i < 256; ++i) {
            this.lookupTable[i] = this.correctSample(i, srcGamma, dstGamma);
            if (GammaCorrection.LOGGER.isLoggable(Level.FINEST)) {
                GammaCorrection.LOGGER.finest("lookup_table[" + i + "]: " + this.lookupTable[i]);
            }
        }
    }
    
    public int correctSample(final int sample) {
        return this.lookupTable[sample];
    }
    
    public int correctARGB(final int pixel) {
        final int alpha = 0xFF000000 & pixel;
        int red = pixel >> 16 & 0xFF;
        int green = pixel >> 8 & 0xFF;
        int blue = pixel >> 0 & 0xFF;
        red = this.correctSample(red);
        green = this.correctSample(green);
        blue = this.correctSample(blue);
        return alpha | (0xFF & red) << 16 | (0xFF & green) << 8 | (0xFF & blue) << 0;
    }
    
    private int correctSample(final int sample, final double srcGamma, final double dstGamma) {
        return (int)Math.round(255.0 * Math.pow(sample / 255.0, srcGamma / dstGamma));
    }
    
    static {
        LOGGER = Logger.getLogger(GammaCorrection.class.getName());
    }
}
