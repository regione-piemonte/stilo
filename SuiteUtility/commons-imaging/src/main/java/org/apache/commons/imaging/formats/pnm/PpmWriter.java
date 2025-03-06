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

class PpmWriter implements PnmWriter
{
    private final boolean rawbits;
    
    PpmWriter(final boolean rawbits) {
        this.rawbits = rawbits;
    }
    
    @Override
    public void writeImage(final BufferedImage src, final OutputStream os, final Map<String, Object> params) throws ImageWriteException, IOException {
        os.write(80);
        os.write(this.rawbits ? 54 : 51);
        os.write(32);
        final int width = src.getWidth();
        final int height = src.getHeight();
        os.write(Integer.toString(width).getBytes(StandardCharsets.US_ASCII));
        os.write(32);
        os.write(Integer.toString(height).getBytes(StandardCharsets.US_ASCII));
        os.write(32);
        os.write(Integer.toString(255).getBytes(StandardCharsets.US_ASCII));
        os.write(10);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int argb = src.getRGB(x, y);
                final int red = 0xFF & argb >> 16;
                final int green = 0xFF & argb >> 8;
                final int blue = 0xFF & argb >> 0;
                if (this.rawbits) {
                    os.write((byte)red);
                    os.write((byte)green);
                    os.write((byte)blue);
                }
                else {
                    os.write(Integer.toString(red).getBytes(StandardCharsets.US_ASCII));
                    os.write(32);
                    os.write(Integer.toString(green).getBytes(StandardCharsets.US_ASCII));
                    os.write(32);
                    os.write(Integer.toString(blue).getBytes(StandardCharsets.US_ASCII));
                    os.write(32);
                }
            }
        }
    }
}
