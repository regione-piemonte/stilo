// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.icns;

final class Rle24Compression
{
    private Rle24Compression() {
    }
    
    public static byte[] decompress(final int width, final int height, final byte[] data) {
        final int pixelCount = width * height;
        final byte[] result = new byte[4 * pixelCount];
        int dataPos = 0;
        if (width >= 128 && height >= 128) {
            dataPos = 4;
        }
        for (int band = 1; band <= 3; ++band) {
            int remaining = pixelCount;
            int resultPos = 0;
            while (remaining > 0) {
                if ((data[dataPos] & 0x80) != 0x0) {
                    final int count = (0xFF & data[dataPos]) - 125;
                    for (int i = 0; i < count; ++i) {
                        result[band + 4 * resultPos++] = data[dataPos + 1];
                    }
                    dataPos += 2;
                    remaining -= count;
                }
                else {
                    final int count = (0xFF & data[dataPos]) + 1;
                    ++dataPos;
                    for (int i = 0; i < count; ++i) {
                        result[band + 4 * resultPos++] = data[dataPos++];
                    }
                    remaining -= count;
                }
            }
        }
        return result;
    }
}
