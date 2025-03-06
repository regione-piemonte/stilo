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

class PamWriter implements PnmWriter
{
    @Override
    public void writeImage(final BufferedImage src, final OutputStream os, final Map<String, Object> params) throws ImageWriteException, IOException {
        os.write(80);
        os.write(55);
        os.write(10);
        final int width = src.getWidth();
        final int height = src.getHeight();
        os.write(("WIDTH " + width).getBytes(StandardCharsets.US_ASCII));
        os.write(10);
        os.write(("HEIGHT " + height).getBytes(StandardCharsets.US_ASCII));
        os.write(10);
        os.write("DEPTH 4".getBytes(StandardCharsets.US_ASCII));
        os.write(10);
        os.write("MAXVAL 255".getBytes(StandardCharsets.US_ASCII));
        os.write(10);
        os.write("TUPLTYPE RGB_ALPHA".getBytes(StandardCharsets.US_ASCII));
        os.write(10);
        os.write("ENDHDR".getBytes(StandardCharsets.US_ASCII));
        os.write(10);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int argb = src.getRGB(x, y);
                final int alpha = 0xFF & argb >> 24;
                final int red = 0xFF & argb >> 16;
                final int green = 0xFF & argb >> 8;
                final int blue = 0xFF & argb >> 0;
                os.write((byte)red);
                os.write((byte)green);
                os.write((byte)blue);
                os.write((byte)alpha);
            }
        }
    }
}
