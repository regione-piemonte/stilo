// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.rgbe;

import java.io.IOException;
import java.io.InputStream;

class InfoHeaderReader
{
    private final InputStream is;
    
    InfoHeaderReader(final InputStream is) {
        this.is = is;
    }
    
    private char read() throws IOException {
        final int result = this.is.read();
        if (result < 0) {
            throw new IOException("HDR: Unexpected EOF");
        }
        return (char)result;
    }
    
    public String readNextLine() throws IOException {
        final StringBuilder buffer = new StringBuilder();
        char c;
        while ((c = this.read()) != '\n') {
            buffer.append(c);
        }
        return buffer.toString();
    }
}
