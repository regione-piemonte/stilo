// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.io;

import java.io.IOException;
import java.io.InputStream;

public class ASCIIDecoder extends AbstractCharDecoder
{
    public ASCIIDecoder(final InputStream inputStream) {
        super(inputStream);
    }
    
    public int readChar() throws IOException {
        if (this.position == this.count) {
            this.fillBuffer();
        }
        if (this.count == -1) {
            return -1;
        }
        final byte b = this.buffer[this.position++];
        if (b < 0) {
            this.charError("ASCII");
        }
        return b;
    }
}
