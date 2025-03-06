// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.scanlinefilters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;

public class ScanlineFilterNone implements ScanlineFilter
{
    @Override
    public void unfilter(final byte[] src, final byte[] dst, final byte[] up) throws ImageReadException, IOException {
        System.arraycopy(src, 0, dst, 0, src.length);
    }
}
