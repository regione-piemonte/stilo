// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.pcx;

import org.apache.commons.imaging.ImageWriteException;
import java.io.IOException;
import org.apache.commons.imaging.common.BinaryOutputStream;

class RleWriter
{
    private final boolean isCompressed;
    private int previousByte;
    private int repeatCount;
    
    RleWriter(final boolean isCompressed) {
        this.previousByte = -1;
        this.repeatCount = 0;
        this.isCompressed = isCompressed;
    }
    
    void write(final BinaryOutputStream bos, final byte[] samples) throws IOException, ImageWriteException {
        if (this.isCompressed) {
            for (final byte element : samples) {
                if ((element & 0xFF) == this.previousByte && this.repeatCount < 63) {
                    ++this.repeatCount;
                }
                else {
                    if (this.repeatCount > 0) {
                        if (this.repeatCount == 1 && (this.previousByte & 0xC0) != 0xC0) {
                            bos.write(this.previousByte);
                        }
                        else {
                            bos.write(0xC0 | this.repeatCount);
                            bos.write(this.previousByte);
                        }
                    }
                    this.previousByte = (0xFF & element);
                    this.repeatCount = 1;
                }
            }
        }
        else {
            bos.write(samples);
        }
    }
    
    void flush(final BinaryOutputStream bos) throws IOException {
        if (this.repeatCount > 0) {
            if (this.repeatCount == 1 && (this.previousByte & 0xC0) != 0xC0) {
                bos.write(this.previousByte);
            }
            else {
                bos.write(0xC0 | this.repeatCount);
                bos.write(this.previousByte);
            }
        }
    }
}
