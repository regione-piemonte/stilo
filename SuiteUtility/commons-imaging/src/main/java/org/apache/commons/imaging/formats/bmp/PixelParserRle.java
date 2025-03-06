// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.bmp;

import java.io.IOException;
import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.common.ImageBuilder;
import org.apache.commons.imaging.ImageReadException;
import java.util.logging.Logger;

class PixelParserRle extends PixelParser
{
    private static final Logger LOGGER;
    
    PixelParserRle(final BmpHeaderInfo bhi, final byte[] colorTable, final byte[] imageData) {
        super(bhi, colorTable, imageData);
    }
    
    private int getSamplesPerByte() throws ImageReadException {
        if (this.bhi.bitsPerPixel == 8) {
            return 1;
        }
        if (this.bhi.bitsPerPixel == 4) {
            return 2;
        }
        throw new ImageReadException("BMP RLE: bad BitsPerPixel: " + this.bhi.bitsPerPixel);
    }
    
    private int[] convertDataToSamples(final int data) throws ImageReadException {
        int[] rgbs;
        if (this.bhi.bitsPerPixel == 8) {
            rgbs = new int[] { this.getColorTableRGB(data) };
        }
        else {
            if (this.bhi.bitsPerPixel != 4) {
                throw new ImageReadException("BMP RLE: bad BitsPerPixel: " + this.bhi.bitsPerPixel);
            }
            rgbs = new int[2];
            final int sample1 = data >> 4;
            final int sample2 = 0xF & data;
            rgbs[0] = this.getColorTableRGB(sample1);
            rgbs[1] = this.getColorTableRGB(sample2);
        }
        return rgbs;
    }
    
    private int processByteOfData(final int[] rgbs, final int repeat, int x, final int y, final int width, final int height, final ImageBuilder imageBuilder) {
        int pixelsWritten = 0;
        for (int i = 0; i < repeat; ++i) {
            if (x >= 0 && x < width && y >= 0 && y < height) {
                final int rgb = rgbs[i % rgbs.length];
                imageBuilder.setRGB(x, y, rgb);
            }
            else {
                PixelParserRle.LOGGER.fine("skipping bad pixel (" + x + "," + y + ")");
            }
            ++x;
            ++pixelsWritten;
        }
        return pixelsWritten;
    }
    
    @Override
    public void processImage(final ImageBuilder imageBuilder) throws ImageReadException, IOException {
        final int width = this.bhi.width;
        final int height = this.bhi.height;
        int x = 0;
        int y = height - 1;
        boolean done = false;
        while (!done) {
            final int a = 0xFF & BinaryFunctions.readByte("RLE (" + x + "," + y + ") a", this.is, "BMP: Bad RLE");
            final int b = 0xFF & BinaryFunctions.readByte("RLE (" + x + "," + y + ") b", this.is, "BMP: Bad RLE");
            if (a == 0) {
                switch (b) {
                    case 0: {
                        --y;
                        x = 0;
                        continue;
                    }
                    case 1: {
                        done = true;
                        continue;
                    }
                    case 2: {
                        final int deltaX = 0xFF & BinaryFunctions.readByte("RLE deltaX", this.is, "BMP: Bad RLE");
                        final int deltaY = 0xFF & BinaryFunctions.readByte("RLE deltaY", this.is, "BMP: Bad RLE");
                        x += deltaX;
                        y -= deltaY;
                        continue;
                    }
                    default: {
                        final int samplesPerByte = this.getSamplesPerByte();
                        int size = b / samplesPerByte;
                        if (b % samplesPerByte > 0) {
                            ++size;
                        }
                        if (size % 2 != 0) {
                            ++size;
                        }
                        final byte[] bytes = BinaryFunctions.readBytes("bytes", this.is, size, "RLE: Absolute Mode");
                        int written;
                        for (int remaining = b, i = 0; remaining > 0; remaining -= written, ++i) {
                            final int[] samples = this.convertDataToSamples(0xFF & bytes[i]);
                            final int towrite = Math.min(remaining, samplesPerByte);
                            written = this.processByteOfData(samples, towrite, x, y, width, height, imageBuilder);
                            x += written;
                        }
                        continue;
                    }
                }
            }
            else {
                final int[] rgbs = this.convertDataToSamples(b);
                x += this.processByteOfData(rgbs, a, x, y, width, height, imageBuilder);
            }
        }
    }
    
    static {
        LOGGER = Logger.getLogger(PixelParserRle.class.getName());
    }
}
