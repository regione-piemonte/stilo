// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.psd.dataparsers;

import org.apache.commons.imaging.formats.psd.PsdImageContents;

public class DataParserIndexed extends DataParser
{
    private final int[] colorTable;
    
    public DataParserIndexed(final byte[] colorModeData) {
        this.colorTable = new int[256];
        for (int i = 0; i < 256; ++i) {
            final int red = 0xFF & colorModeData[0 + i];
            final int green = 0xFF & colorModeData[256 + i];
            final int blue = 0xFF & colorModeData[512 + i];
            final int alpha = 255;
            final int rgb = 0xFF000000 | (0xFF & red) << 16 | (0xFF & green) << 8 | (0xFF & blue) << 0;
            this.colorTable[i] = rgb;
        }
    }
    
    @Override
    protected int getRGB(final int[][][] data, final int x, final int y, final PsdImageContents imageContents) {
        final int sample = 0xFF & data[0][y][x];
        return this.colorTable[sample];
    }
    
    @Override
    public int getBasicChannelsCount() {
        return 1;
    }
}
