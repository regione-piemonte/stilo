// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.io;

import java.io.IOException;
import java.io.InputStream;

public class UTF16Decoder extends AbstractCharDecoder
{
    protected boolean bigEndian;
    
    public UTF16Decoder(final InputStream inputStream) throws IOException {
        super(inputStream);
        final int read = inputStream.read();
        if (read == -1) {
            this.endOfStreamError("UTF-16");
        }
        final int read2 = inputStream.read();
        if (read2 == -1) {
            this.endOfStreamError("UTF-16");
        }
        switch ((read & 0xFF) << 8 | (read2 & 0xFF)) {
            case 65279: {
                this.bigEndian = true;
                break;
            }
            case 65534: {
                break;
            }
            default: {
                this.charError("UTF-16");
                break;
            }
        }
    }
    
    public UTF16Decoder(final InputStream inputStream, final boolean bigEndian) {
        super(inputStream);
        this.bigEndian = bigEndian;
    }
    
    public int readChar() throws IOException {
        if (this.position == this.count) {
            this.fillBuffer();
        }
        if (this.count == -1) {
            return -1;
        }
        final byte b = this.buffer[this.position++];
        if (this.position == this.count) {
            this.fillBuffer();
        }
        if (this.count == -1) {
            this.endOfStreamError("UTF-16");
        }
        final byte b2 = this.buffer[this.position++];
        final int n = this.bigEndian ? ((b & 0xFF) << 8 | (b2 & 0xFF)) : ((b2 & 0xFF) << 8 | (b & 0xFF));
        if (n == 65534) {
            this.charError("UTF-16");
        }
        return n;
    }
}
