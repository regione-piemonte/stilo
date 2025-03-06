// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.transparencyfilters;

import org.apache.commons.imaging.ImageReadException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.ByteArrayInputStream;

public class TransparencyFilterGrayscale extends TransparencyFilter
{
    private final int transparentColor;
    
    public TransparencyFilterGrayscale(final byte[] bytes) throws IOException {
        super(bytes);
        final ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        this.transparentColor = BinaryFunctions.read2Bytes("transparentColor", is, "tRNS: Missing transparentColor", this.getByteOrder());
    }
    
    @Override
    public int filter(final int rgb, final int index) throws ImageReadException, IOException {
        if (index != this.transparentColor) {
            return rgb;
        }
        return 0;
    }
}
