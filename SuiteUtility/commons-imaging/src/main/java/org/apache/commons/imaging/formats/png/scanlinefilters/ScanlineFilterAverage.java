// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.scanlinefilters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;

public class ScanlineFilterAverage implements ScanlineFilter
{
    private final int bytesPerPixel;
    
    public ScanlineFilterAverage(final int bytesPerPixel) {
        this.bytesPerPixel = bytesPerPixel;
    }
    
    @Override
    public void unfilter(final byte[] src, final byte[] dst, final byte[] up) throws ImageReadException, IOException {
        for (int i = 0; i < src.length; ++i) {
            int raw = 0;
            final int prevIndex = i - this.bytesPerPixel;
            if (prevIndex >= 0) {
                raw = dst[prevIndex];
            }
            int prior = 0;
            if (up != null) {
                prior = up[i];
            }
            final int average = ((0xFF & raw) + (0xFF & prior)) / 2;
            dst[i] = (byte)((src[i] + average) % 256);
        }
    }
}
