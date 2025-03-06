// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.transparencyfilters;

import org.apache.commons.imaging.ImageReadException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.ByteArrayInputStream;

public class TransparencyFilterTrueColor extends TransparencyFilter
{
    private final int transparentColor;
    
    public TransparencyFilterTrueColor(final byte[] bytes) throws IOException {
        super(bytes);
        final ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        final int transparentRed = BinaryFunctions.read2Bytes("transparentRed", is, "tRNS: Missing transparentColor", this.getByteOrder());
        final int transparentGreen = BinaryFunctions.read2Bytes("transparentGreen", is, "tRNS: Missing transparentColor", this.getByteOrder());
        final int transparentBlue = BinaryFunctions.read2Bytes("transparentBlue", is, "tRNS: Missing transparentColor", this.getByteOrder());
        this.transparentColor = ((0xFF & transparentRed) << 16 | (0xFF & transparentGreen) << 8 | (0xFF & transparentBlue) << 0);
    }
    
    @Override
    public int filter(final int rgb, final int sample) throws ImageReadException, IOException {
        if ((0xFFFFFF & rgb) == this.transparentColor) {
            return 0;
        }
        return rgb;
    }
}
