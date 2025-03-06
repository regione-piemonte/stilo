// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.pnm;

import java.io.IOException;
import java.io.InputStream;

class WhiteSpaceReader
{
    private final InputStream is;
    
    WhiteSpaceReader(final InputStream is) {
        this.is = is;
    }
    
    private char read() throws IOException {
        final int result = this.is.read();
        if (result < 0) {
            throw new IOException("PNM: Unexpected EOF");
        }
        return (char)result;
    }
    
    public char nextChar() throws IOException {
        char c = this.read();
        if (c == '#') {
            while (c != '\n' && c != '\r') {
                c = this.read();
            }
        }
        return c;
    }
    
    public String readtoWhiteSpace() throws IOException {
        char c;
        for (c = this.nextChar(); Character.isWhitespace(c); c = this.nextChar()) {}
        final StringBuilder buffer = new StringBuilder();
        while (!Character.isWhitespace(c)) {
            buffer.append(c);
            c = this.nextChar();
        }
        return buffer.toString();
    }
    
    public String readLine() throws IOException {
        final StringBuilder buffer = new StringBuilder();
        for (char c = this.read(); c != '\n' && c != '\r'; c = this.read()) {
            buffer.append(c);
        }
        return (buffer.length() > 0) ? buffer.toString() : null;
    }
}
