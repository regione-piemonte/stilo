// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.psd.dataparsers;

import org.apache.commons.imaging.formats.psd.PsdImageContents;

public class DataParserBitmap extends DataParser
{
    @Override
    protected int getRGB(final int[][][] data, final int x, final int y, final PsdImageContents imageContents) {
        int sample = 0xFF & data[0][y][x];
        if (sample == 0) {
            sample = 255;
        }
        else {
            sample = 0;
        }
        final int alpha = 255;
        return 0xFF000000 | (0xFF & sample) << 16 | (0xFF & sample) << 8 | (0xFF & sample) << 0;
    }
    
    @Override
    public int getBasicChannelsCount() {
        return 1;
    }
}
