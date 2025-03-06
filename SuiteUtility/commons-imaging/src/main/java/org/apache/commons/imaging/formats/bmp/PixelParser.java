// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.bmp;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageBuilder;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

abstract class PixelParser
{
    final BmpHeaderInfo bhi;
    final byte[] colorTable;
    final byte[] imageData;
    final InputStream is;
    
    PixelParser(final BmpHeaderInfo bhi, final byte[] colorTable, final byte[] imageData) {
        this.bhi = bhi;
        this.colorTable = colorTable;
        this.imageData = imageData;
        this.is = new ByteArrayInputStream(imageData);
    }
    
    public abstract void processImage(final ImageBuilder p0) throws ImageReadException, IOException;
    
    int getColorTableRGB(int index) {
        index *= 4;
        final int blue = 0xFF & this.colorTable[index + 0];
        final int green = 0xFF & this.colorTable[index + 1];
        final int red = 0xFF & this.colorTable[index + 2];
        final int alpha = 255;
        return 0xFF000000 | red << 16 | green << 8 | blue << 0;
    }
}
