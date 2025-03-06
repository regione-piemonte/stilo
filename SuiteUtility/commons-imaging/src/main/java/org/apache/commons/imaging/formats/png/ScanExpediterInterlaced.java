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

class ScanExpediterInterlaced extends ScanExpediter
{
    private static final int[] STARTING_ROW;
    private static final int[] STARTING_COL;
    private static final int[] ROW_INCREMENT;
    private static final int[] COL_INCREMENT;
    
    ScanExpediterInterlaced(final int width, final int height, final InputStream is, final BufferedImage bi, final PngColorType pngColorType, final int bitDepth, final int bitsPerPixel, final PngChunkPlte fPNGChunkPLTE, final GammaCorrection gammaCorrection, final TransparencyFilter transparencyFilter) {
        super(width, height, is, bi, pngColorType, bitDepth, bitsPerPixel, fPNGChunkPLTE, gammaCorrection, transparencyFilter);
    }
    
    private void visit(final int x, final int y, final BufferedImage bi, final BitParser fBitParser, final int pixelIndexInScanline) throws ImageReadException, IOException {
        final int rgb = this.getRGB(fBitParser, pixelIndexInScanline);
        bi.setRGB(x, y, rgb);
    }
    
    @Override
    public void drive() throws ImageReadException, IOException {
        for (int pass = 1; pass <= 7; ++pass) {
            byte[] prev = null;
            for (int y = ScanExpediterInterlaced.STARTING_ROW[pass - 1]; y < this.height; y += ScanExpediterInterlaced.ROW_INCREMENT[pass - 1]) {
                int x = ScanExpediterInterlaced.STARTING_COL[pass - 1];
                int pixelIndexInScanline = 0;
                if (x < this.width) {
                    final int columnsInRow = 1 + (this.width - ScanExpediterInterlaced.STARTING_COL[pass - 1] - 1) / ScanExpediterInterlaced.COL_INCREMENT[pass - 1];
                    final int bitsPerScanLine = this.bitsPerPixel * columnsInRow;
                    final int pixelBytesPerScanLine = this.getBitsToBytesRoundingUp(bitsPerScanLine);
                    final byte[] unfiltered = prev = this.getNextScanline(this.is, pixelBytesPerScanLine, prev, this.bytesPerPixel);
                    final BitParser fBitParser = new BitParser(unfiltered, this.bitsPerPixel, this.bitDepth);
                    while (x < this.width) {
                        this.visit(x, y, this.bi, fBitParser, pixelIndexInScanline);
                        x += ScanExpediterInterlaced.COL_INCREMENT[pass - 1];
                        ++pixelIndexInScanline;
                    }
                }
            }
        }
    }
    
    static {
        STARTING_ROW = new int[] { 0, 0, 4, 0, 2, 0, 1 };
        STARTING_COL = new int[] { 0, 4, 0, 2, 0, 1, 0 };
        ROW_INCREMENT = new int[] { 8, 8, 8, 4, 4, 2, 2 };
        COL_INCREMENT = new int[] { 8, 8, 4, 4, 2, 2, 1 };
    }
}
