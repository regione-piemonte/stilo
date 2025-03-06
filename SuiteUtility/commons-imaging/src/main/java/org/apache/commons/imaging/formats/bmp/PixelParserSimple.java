// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.bmp;

import org.apache.commons.imaging.common.ImageBuilder;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;

abstract class PixelParserSimple extends PixelParser
{
    PixelParserSimple(final BmpHeaderInfo bhi, final byte[] colorTable, final byte[] imageData) {
        super(bhi, colorTable, imageData);
    }
    
    public abstract int getNextRGB() throws ImageReadException, IOException;
    
    public abstract void newline() throws ImageReadException, IOException;
    
    @Override
    public void processImage(final ImageBuilder imageBuilder) throws ImageReadException, IOException {
        for (int y = this.bhi.height - 1; y >= 0; --y) {
            for (int x = 0; x < this.bhi.width; ++x) {
                final int rgb = this.getNextRGB();
                imageBuilder.setRGB(x, y, rgb);
            }
            this.newline();
        }
    }
}
