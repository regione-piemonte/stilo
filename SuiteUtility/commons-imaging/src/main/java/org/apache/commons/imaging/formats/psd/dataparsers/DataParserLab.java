// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.psd.dataparsers;

import org.apache.commons.imaging.color.ColorConversions;
import org.apache.commons.imaging.formats.psd.PsdImageContents;

public class DataParserLab extends DataParser
{
    @Override
    protected int getRGB(final int[][][] data, final int x, final int y, final PsdImageContents imageContents) {
        final int cieL = 0xFF & data[0][y][x];
        int cieA = 0xFF & data[1][y][x];
        int cieB = 0xFF & data[2][y][x];
        cieA -= 128;
        cieB -= 128;
        return ColorConversions.convertCIELabtoARGBTest(cieL, cieA, cieB);
    }
    
    @Override
    public int getBasicChannelsCount() {
        return 3;
    }
}
