// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.bmp;

import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.commons.imaging.common.BinaryOutputStream;
import org.apache.commons.imaging.palette.SimplePalette;

class BmpWriterPalette implements BmpWriter
{
    private final SimplePalette palette;
    private final int bitsPerSample;
    
    BmpWriterPalette(final SimplePalette palette) {
        this.palette = palette;
        if (palette.length() <= 2) {
            this.bitsPerSample = 1;
        }
        else if (palette.length() <= 16) {
            this.bitsPerSample = 4;
        }
        else {
            this.bitsPerSample = 8;
        }
    }
    
    @Override
    public int getPaletteSize() {
        return this.palette.length();
    }
    
    @Override
    public int getBitsPerPixel() {
        return this.bitsPerSample;
    }
    
    @Override
    public void writePalette(final BinaryOutputStream bos) throws IOException {
        for (int i = 0; i < this.palette.length(); ++i) {
            final int rgb = this.palette.getEntry(i);
            final int red = 0xFF & rgb >> 16;
            final int green = 0xFF & rgb >> 8;
            final int blue = 0xFF & rgb >> 0;
            bos.write(blue);
            bos.write(green);
            bos.write(red);
            bos.write(0);
        }
    }
    
    @Override
    public byte[] getImageData(final BufferedImage src) {
        final int width = src.getWidth();
        final int height = src.getHeight();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bitCache = 0;
        int bitsInCache = 0;
        int bytecount = 0;
        for (int y = height - 1; y >= 0; --y) {
            for (int x = 0; x < width; ++x) {
                final int argb = src.getRGB(x, y);
                final int rgb = 0xFFFFFF & argb;
                final int index = this.palette.getPaletteIndex(rgb);
                if (this.bitsPerSample == 8) {
                    baos.write(0xFF & index);
                    ++bytecount;
                }
                else {
                    bitCache = (bitCache << this.bitsPerSample | index);
                    bitsInCache += this.bitsPerSample;
                    if (bitsInCache >= 8) {
                        baos.write(0xFF & bitCache);
                        ++bytecount;
                        bitCache = 0;
                        bitsInCache = 0;
                    }
                }
            }
            if (bitsInCache > 0) {
                bitCache <<= 8 - bitsInCache;
                baos.write(0xFF & bitCache);
                ++bytecount;
                bitCache = 0;
                bitsInCache = 0;
            }
            while (bytecount % 4 != 0) {
                baos.write(0);
                ++bytecount;
            }
        }
        return baos.toByteArray();
    }
}
