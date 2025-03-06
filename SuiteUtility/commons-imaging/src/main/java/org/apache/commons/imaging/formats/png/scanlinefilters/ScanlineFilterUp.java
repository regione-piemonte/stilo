// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.scanlinefilters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;

public class ScanlineFilterUp implements ScanlineFilter
{
    @Override
    public void unfilter(final byte[] src, final byte[] dst, final byte[] up) throws ImageReadException, IOException {
        for (int i = 0; i < src.length; ++i) {
            if (up != null) {
                dst[i] = (byte)((src[i] + up[i]) % 256);
            }
            else {
                dst[i] = src[i];
            }
        }
    }
}
