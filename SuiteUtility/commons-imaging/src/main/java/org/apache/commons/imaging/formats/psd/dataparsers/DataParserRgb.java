// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.psd.dataparsers;

import org.apache.commons.imaging.formats.psd.PsdImageContents;

public class DataParserRgb extends DataParser
{
    @Override
    protected int getRGB(final int[][][] data, final int x, final int y, final PsdImageContents imageContents) {
        final int red = 0xFF & data[0][y][x];
        final int green = 0xFF & data[1][y][x];
        final int blue = 0xFF & data[2][y][x];
        final int alpha = 255;
        return 0xFF000000 | (0xFF & red) << 16 | (0xFF & green) << 8 | (0xFF & blue) << 0;
    }
    
    @Override
    public int getBasicChannelsCount() {
        return 3;
    }
}
