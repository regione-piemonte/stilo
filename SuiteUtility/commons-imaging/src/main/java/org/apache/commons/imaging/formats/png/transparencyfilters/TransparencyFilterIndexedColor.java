// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.transparencyfilters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;

public class TransparencyFilterIndexedColor extends TransparencyFilter
{
    public TransparencyFilterIndexedColor(final byte[] bytes) {
        super(bytes);
    }
    
    @Override
    public int filter(final int rgb, final int index) throws ImageReadException, IOException {
        final int length = this.getLength();
        if (index >= length) {
            return rgb;
        }
        if (index < 0 || index > length) {
            throw new ImageReadException("TransparencyFilterIndexedColor index: " + index + ", bytes.length: " + length);
        }
        final int alpha = this.getByte(index);
        return (0xFF & alpha) << 24 | (0xFFFFFF & rgb);
    }
}
