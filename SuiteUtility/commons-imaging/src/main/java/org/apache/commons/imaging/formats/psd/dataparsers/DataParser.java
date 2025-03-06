// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.psd.dataparsers;

import org.apache.commons.imaging.formats.psd.PsdHeaderInfo;
import java.awt.image.DataBuffer;
import org.apache.commons.imaging.formats.psd.PsdImageContents;
import java.awt.image.BufferedImage;

public abstract class DataParser
{
    public final void parseData(final int[][][] data, final BufferedImage bi, final PsdImageContents imageContents) {
        final DataBuffer buffer = bi.getRaster().getDataBuffer();
        final PsdHeaderInfo header = imageContents.header;
        final int width = header.columns;
        for (int height = header.rows, y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int rgb = this.getRGB(data, x, y, imageContents);
                buffer.setElem(y * width + x, rgb);
            }
        }
    }
    
    protected abstract int getRGB(final int[][][] p0, final int p1, final int p2, final PsdImageContents p3);
    
    public abstract int getBasicChannelsCount();
}
