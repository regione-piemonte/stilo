// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.scanlinefilters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;

public class ScanlineFilterPaeth implements ScanlineFilter
{
    private final int bytesPerPixel;
    
    public ScanlineFilterPaeth(final int bytesPerPixel) {
        this.bytesPerPixel = bytesPerPixel;
    }
    
    private int paethPredictor(final int a, final int b, final int c) {
        final int p = a + b - c;
        final int pa = Math.abs(p - a);
        final int pb = Math.abs(p - b);
        final int pc = Math.abs(p - c);
        if (pa <= pb && pa <= pc) {
            return a;
        }
        if (pb <= pc) {
            return b;
        }
        return c;
    }
    
    @Override
    public void unfilter(final byte[] src, final byte[] dst, final byte[] up) throws ImageReadException, IOException {
        for (int i = 0; i < src.length; ++i) {
            int left = 0;
            final int prevIndex = i - this.bytesPerPixel;
            if (prevIndex >= 0) {
                left = dst[prevIndex];
            }
            int above = 0;
            if (up != null) {
                above = up[i];
            }
            int upperleft = 0;
            if (prevIndex >= 0 && up != null) {
                upperleft = up[prevIndex];
            }
            final int paethPredictor = this.paethPredictor(0xFF & left, 0xFF & above, 0xFF & upperleft);
            dst[i] = (byte)((src[i] + paethPredictor) % 256);
        }
    }
}
