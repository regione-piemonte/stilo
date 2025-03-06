// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.bmp;

import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.commons.imaging.common.BinaryOutputStream;

class BmpWriterRgb implements BmpWriter
{
    @Override
    public int getPaletteSize() {
        return 0;
    }
    
    @Override
    public int getBitsPerPixel() {
        return 24;
    }
    
    @Override
    public void writePalette(final BinaryOutputStream bos) throws IOException {
    }
    
    @Override
    public byte[] getImageData(final BufferedImage src) {
        final int width = src.getWidth();
        final int height = src.getHeight();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bytecount = 0;
        for (int y = height - 1; y >= 0; --y) {
            for (int x = 0; x < width; ++x) {
                final int argb = src.getRGB(x, y);
                final int rgb = 0xFFFFFF & argb;
                final int red = 0xFF & rgb >> 16;
                final int green = 0xFF & rgb >> 8;
                final int blue = 0xFF & rgb >> 0;
                baos.write(blue);
                baos.write(green);
                baos.write(red);
                bytecount += 3;
            }
            while (bytecount % 4 != 0) {
                baos.write(0);
                ++bytecount;
            }
        }
        return baos.toByteArray();
    }
}
