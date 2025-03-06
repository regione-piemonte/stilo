// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.decoder;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.io.InputStream;

class JpegInputStream
{
    private final InputStream is;
    private int cnt;
    private int b;
    
    JpegInputStream(final InputStream is) {
        this.is = is;
    }
    
    public int nextBit() throws IOException, ImageReadException {
        if (this.cnt == 0) {
            this.b = this.is.read();
            if (this.b < 0) {
                throw new ImageReadException("Premature End of File");
            }
            this.cnt = 8;
            if (this.b == 255) {
                final int b2 = this.is.read();
                if (b2 < 0) {
                    throw new ImageReadException("Premature End of File");
                }
                if (b2 != 0) {
                    if (b2 == 220) {
                        throw new ImageReadException("DNL not yet supported");
                    }
                    throw new ImageReadException("Invalid marker found in entropy data");
                }
            }
        }
        final int bit = this.b >> 7 & 0x1;
        --this.cnt;
        this.b <<= 1;
        return bit;
    }
}
