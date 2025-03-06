// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.psd.dataparsers;

import org.apache.commons.imaging.color.ColorConversions;
import org.apache.commons.imaging.formats.psd.PsdImageContents;

public class DataParserCmyk extends DataParser
{
    @Override
    protected int getRGB(final int[][][] data, final int x, final int y, final PsdImageContents imageContents) {
        int sc = 0xFF & data[0][y][x];
        int sm = 0xFF & data[1][y][x];
        int sy = 0xFF & data[2][y][x];
        int sk = 0xFF & data[3][y][x];
        sc = 255 - sc;
        sm = 255 - sm;
        sy = 255 - sy;
        sk = 255 - sk;
        return ColorConversions.convertCMYKtoRGB(sc, sm, sy, sk);
    }
    
    @Override
    public int getBasicChannelsCount() {
        return 4;
    }
}
