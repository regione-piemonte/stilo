// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.psd.datareaders;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.psd.PsdHeaderInfo;
import org.apache.commons.imaging.common.mylzw.BitsToByteInputStream;
import org.apache.commons.imaging.common.mylzw.MyBitInputStream;
import java.nio.ByteOrder;
import org.apache.commons.imaging.common.BinaryFileParser;
import org.apache.commons.imaging.formats.psd.PsdImageContents;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import org.apache.commons.imaging.formats.psd.dataparsers.DataParser;

public class UncompressedDataReader implements DataReader
{
    private final DataParser dataParser;
    
    public UncompressedDataReader(final DataParser dataParser) {
        this.dataParser = dataParser;
    }
    
    @Override
    public void readData(final InputStream is, final BufferedImage bi, final PsdImageContents imageContents, final BinaryFileParser bfp) throws ImageReadException, IOException {
        final PsdHeaderInfo header = imageContents.header;
        final int width = header.columns;
        final int height = header.rows;
        final int channelCount = this.dataParser.getBasicChannelsCount();
        final int depth = header.depth;
        final MyBitInputStream mbis = new MyBitInputStream(is, ByteOrder.BIG_ENDIAN);
        try (final BitsToByteInputStream bbis = new BitsToByteInputStream(mbis, 8)) {
            final int[][][] data = new int[channelCount][height][width];
            for (int channel = 0; channel < channelCount; ++channel) {
                for (int y = 0; y < height; ++y) {
                    for (int x = 0; x < width; ++x) {
                        final int b = bbis.readBits(depth);
                        data[channel][y][x] = (byte)b;
                    }
                }
            }
            this.dataParser.parseData(data, bi, imageContents);
        }
    }
}
