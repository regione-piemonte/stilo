// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.bmp;

import java.io.IOException;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.ImageReadException;

class PixelParserRgb extends PixelParserSimple
{
    private int bytecount;
    private int cachedBitCount;
    private int cachedByte;
    
    PixelParserRgb(final BmpHeaderInfo bhi, final byte[] colorTable, final byte[] imageData) {
        super(bhi, colorTable, imageData);
    }
    
    @Override
    public int getNextRGB() throws ImageReadException, IOException {
        if (this.bhi.bitsPerPixel == 1 || this.bhi.bitsPerPixel == 4) {
            if (this.cachedBitCount < this.bhi.bitsPerPixel) {
                if (this.cachedBitCount != 0) {
                    throw new ImageReadException("Unexpected leftover bits: " + this.cachedBitCount + "/" + this.bhi.bitsPerPixel);
                }
                this.cachedBitCount += 8;
                this.cachedByte = (0xFF & this.imageData[this.bytecount]);
                ++this.bytecount;
            }
            final int cacheMask = (1 << this.bhi.bitsPerPixel) - 1;
            final int sample = cacheMask & this.cachedByte >> 8 - this.bhi.bitsPerPixel;
            this.cachedByte = (0xFF & this.cachedByte << this.bhi.bitsPerPixel);
            this.cachedBitCount -= this.bhi.bitsPerPixel;
            return this.getColorTableRGB(sample);
        }
        if (this.bhi.bitsPerPixel == 8) {
            final int sample2 = 0xFF & this.imageData[this.bytecount + 0];
            final int rgb = this.getColorTableRGB(sample2);
            ++this.bytecount;
            return rgb;
        }
        if (this.bhi.bitsPerPixel == 16) {
            final int data = BinaryFunctions.read2Bytes("Pixel", this.is, "BMP Image Data", ByteOrder.LITTLE_ENDIAN);
            final int blue = (0x1F & data >> 0) << 3;
            final int green = (0x1F & data >> 5) << 3;
            final int red = (0x1F & data >> 10) << 3;
            final int alpha = 255;
            final int rgb2 = 0xFF000000 | red << 16 | green << 8 | blue << 0;
            this.bytecount += 2;
            return rgb2;
        }
        if (this.bhi.bitsPerPixel == 24) {
            final int blue2 = 0xFF & this.imageData[this.bytecount + 0];
            final int green2 = 0xFF & this.imageData[this.bytecount + 1];
            final int red2 = 0xFF & this.imageData[this.bytecount + 2];
            final int alpha2 = 255;
            final int rgb3 = 0xFF000000 | red2 << 16 | green2 << 8 | blue2 << 0;
            this.bytecount += 3;
            return rgb3;
        }
        if (this.bhi.bitsPerPixel == 32) {
            final int blue2 = 0xFF & this.imageData[this.bytecount + 0];
            final int green2 = 0xFF & this.imageData[this.bytecount + 1];
            final int red2 = 0xFF & this.imageData[this.bytecount + 2];
            final int alpha2 = 255;
            final int rgb3 = 0xFF000000 | red2 << 16 | green2 << 8 | blue2 << 0;
            this.bytecount += 4;
            return rgb3;
        }
        throw new ImageReadException("Unknown BitsPerPixel: " + this.bhi.bitsPerPixel);
    }
    
    @Override
    public void newline() throws ImageReadException, IOException {
        this.cachedBitCount = 0;
        while (this.bytecount % 4 != 0) {
            BinaryFunctions.readByte("Pixel", this.is, "BMP Image Data");
            ++this.bytecount;
        }
    }
}
