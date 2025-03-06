// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.png.transparencyfilters.TransparencyFilter;
import org.apache.commons.imaging.formats.png.chunks.PngChunkPlte;
import java.awt.image.BufferedImage;
import java.io.InputStream;

class ScanExpediterSimple extends ScanExpediter
{
    ScanExpediterSimple(final int width, final int height, final InputStream is, final BufferedImage bi, final PngColorType pngColorType, final int bitDepth, final int bitsPerPixel, final PngChunkPlte pngChunkPLTE, final GammaCorrection gammaCorrection, final TransparencyFilter transparencyFilter) {
        super(width, height, is, bi, pngColorType, bitDepth, bitsPerPixel, pngChunkPLTE, gammaCorrection, transparencyFilter);
    }
    
    @Override
    public void drive() throws ImageReadException, IOException {
        final int bitsPerScanLine = this.bitsPerPixel * this.width;
        final int pixelBytesPerScanLine = this.getBitsToBytesRoundingUp(bitsPerScanLine);
        byte[] prev = null;
        for (int y = 0; y < this.height; ++y) {
            final byte[] unfiltered = prev = this.getNextScanline(this.is, pixelBytesPerScanLine, prev, this.bytesPerPixel);
            final BitParser bitParser = new BitParser(unfiltered, this.bitsPerPixel, this.bitDepth);
            for (int x = 0; x < this.width; ++x) {
                final int rgb = this.getRGB(bitParser, x);
                this.bi.setRGB(x, y, rgb);
            }
        }
    }
}
