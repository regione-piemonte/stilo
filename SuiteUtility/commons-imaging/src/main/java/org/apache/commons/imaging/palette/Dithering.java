// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

import org.apache.commons.imaging.ImageWriteException;
import java.awt.image.BufferedImage;

public final class Dithering
{
    private Dithering() {
    }
    
    public static void applyFloydSteinbergDithering(final BufferedImage image, final Palette palette) throws ImageWriteException {
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                final int argb = image.getRGB(x, y);
                final int index = palette.getPaletteIndex(argb);
                final int nextArgb = palette.getEntry(index);
                image.setRGB(x, y, nextArgb);
                final int a = argb >> 24 & 0xFF;
                final int r = argb >> 16 & 0xFF;
                final int g = argb >> 8 & 0xFF;
                final int b = argb & 0xFF;
                final int na = nextArgb >> 24 & 0xFF;
                final int nr = nextArgb >> 16 & 0xFF;
                final int ng = nextArgb >> 8 & 0xFF;
                final int nb = nextArgb & 0xFF;
                final int errA = a - na;
                final int errR = r - nr;
                final int errG = g - ng;
                final int errB = b - nb;
                if (x + 1 < image.getWidth()) {
                    int update = adjustPixel(image.getRGB(x + 1, y), errA, errR, errG, errB, 7);
                    image.setRGB(x + 1, y, update);
                    if (y + 1 < image.getHeight()) {
                        update = adjustPixel(image.getRGB(x + 1, y + 1), errA, errR, errG, errB, 1);
                        image.setRGB(x + 1, y + 1, update);
                    }
                }
                if (y + 1 < image.getHeight()) {
                    int update = adjustPixel(image.getRGB(x, y + 1), errA, errR, errG, errB, 5);
                    image.setRGB(x, y + 1, update);
                    if (x - 1 >= 0) {
                        update = adjustPixel(image.getRGB(x - 1, y + 1), errA, errR, errG, errB, 3);
                        image.setRGB(x - 1, y + 1, update);
                    }
                }
            }
        }
    }
    
    private static int adjustPixel(final int argb, final int errA, final int errR, final int errG, final int errB, final int mul) {
        int a = argb >> 24 & 0xFF;
        int r = argb >> 16 & 0xFF;
        int g = argb >> 8 & 0xFF;
        int b = argb & 0xFF;
        a += errA * mul / 16;
        r += errR * mul / 16;
        g += errG * mul / 16;
        b += errB * mul / 16;
        if (a < 0) {
            a = 0;
        }
        else if (a > 255) {
            a = 255;
        }
        if (r < 0) {
            r = 0;
        }
        else if (r > 255) {
            r = 255;
        }
        if (g < 0) {
            g = 0;
        }
        else if (g > 255) {
            g = 255;
        }
        if (b < 0) {
            b = 0;
        }
        else if (b > 255) {
            b = 255;
        }
        return a << 24 | r << 16 | g << 8 | b;
    }
}
