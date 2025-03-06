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
import java.io.ByteArrayInputStream;
import org.apache.commons.imaging.common.PackBits;
import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.common.BinaryFileParser;
import org.apache.commons.imaging.formats.psd.PsdImageContents;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import org.apache.commons.imaging.formats.psd.dataparsers.DataParser;

public class CompressedDataReader implements DataReader
{
    private final DataParser dataParser;
    
    public CompressedDataReader(final DataParser dataParser) {
        this.dataParser = dataParser;
    }
    
    @Override
    public void readData(final InputStream is, final BufferedImage bi, final PsdImageContents imageContents, final BinaryFileParser bfp) throws ImageReadException, IOException {
        final PsdHeaderInfo header = imageContents.header;
        final int width = header.columns;
        final int height = header.rows;
        final int scanlineCount = height * header.channels;
        final int[] scanlineBytecounts = new int[scanlineCount];
        for (int i = 0; i < scanlineCount; ++i) {
            scanlineBytecounts[i] = BinaryFunctions.read2Bytes("scanline_bytecount[" + i + "]", is, "PSD: bad Image Data", bfp.getByteOrder());
        }
        final int depth = header.depth;
        final int channelCount = this.dataParser.getBasicChannelsCount();
        final int[][][] data = new int[channelCount][height][];
        for (int channel = 0; channel < channelCount; ++channel) {
            for (int y = 0; y < height; ++y) {
                final int index = channel * height + y;
                final byte[] packed = BinaryFunctions.readBytes("scanline", is, scanlineBytecounts[index], "PSD: Missing Image Data");
                final byte[] unpacked = new PackBits().decompress(packed, width);
                final InputStream bais = new ByteArrayInputStream(unpacked);
                final MyBitInputStream mbis = new MyBitInputStream(bais, ByteOrder.BIG_ENDIAN);
                try (final BitsToByteInputStream bbis = new BitsToByteInputStream(mbis, 8)) {
                    final int[] scanline = bbis.readBitsArray(depth, width);
                    data[channel][y] = scanline;
                }
            }
        }
        this.dataParser.parseData(data, bi, imageContents);
    }
}
