// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.pnm;

import java.io.IOException;
import org.apache.commons.imaging.ImageWriteException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.io.OutputStream;
import java.awt.image.BufferedImage;

class PbmWriter implements PnmWriter
{
    private final boolean rawbits;
    
    PbmWriter(final boolean rawbits) {
        this.rawbits = rawbits;
    }
    
    @Override
    public void writeImage(final BufferedImage src, final OutputStream os, final Map<String, Object> params) throws ImageWriteException, IOException {
        os.write(80);
        os.write(this.rawbits ? 52 : 49);
        os.write(32);
        final int width = src.getWidth();
        final int height = src.getHeight();
        os.write(Integer.toString(width).getBytes(StandardCharsets.US_ASCII));
        os.write(32);
        os.write(Integer.toString(height).getBytes(StandardCharsets.US_ASCII));
        os.write(10);
        int bitcache = 0;
        int bitsInCache = 0;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int argb = src.getRGB(x, y);
                final int red = 0xFF & argb >> 16;
                final int green = 0xFF & argb >> 8;
                final int blue = 0xFF & argb >> 0;
                int sample = (red + green + blue) / 3;
                if (sample > 127) {
                    sample = 0;
                }
                else {
                    sample = 1;
                }
                if (this.rawbits) {
                    bitcache = (bitcache << 1 | (0x1 & sample));
                    if (++bitsInCache >= 8) {
                        os.write((byte)bitcache);
                        bitcache = 0;
                        bitsInCache = 0;
                    }
                }
                else {
                    os.write(Integer.toString(sample).getBytes(StandardCharsets.US_ASCII));
                    os.write(32);
                }
            }
            if (this.rawbits && bitsInCache > 0) {
                bitcache <<= 8 - bitsInCache;
                os.write((byte)bitcache);
                bitcache = 0;
                bitsInCache = 0;
            }
        }
    }
}
