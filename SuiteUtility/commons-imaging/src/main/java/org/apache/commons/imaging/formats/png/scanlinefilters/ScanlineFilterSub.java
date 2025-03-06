// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.scanlinefilters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;

public class ScanlineFilterSub implements ScanlineFilter
{
    private final int bytesPerPixel;
    
    public ScanlineFilterSub(final int bytesPerPixel) {
        this.bytesPerPixel = bytesPerPixel;
    }
    
    @Override
    public void unfilter(final byte[] src, final byte[] dst, final byte[] up) throws ImageReadException, IOException {
        for (int i = 0; i < src.length; ++i) {
            final int prevIndex = i - this.bytesPerPixel;
            if (prevIndex >= 0) {
                dst[i] = (byte)((src[i] + dst[prevIndex]) % 256);
            }
            else {
                dst[i] = src[i];
            }
        }
    }
}
