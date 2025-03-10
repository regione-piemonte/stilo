// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.io;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractCharDecoder implements CharDecoder
{
    protected static final int BUFFER_SIZE = 8192;
    protected InputStream inputStream;
    protected byte[] buffer;
    protected int position;
    protected int count;
    
    protected AbstractCharDecoder(final InputStream inputStream) {
        this.buffer = new byte[8192];
        this.inputStream = inputStream;
    }
    
    public void dispose() throws IOException {
        this.inputStream.close();
        this.inputStream = null;
    }
    
    protected void fillBuffer() throws IOException {
        this.count = this.inputStream.read(this.buffer, 0, 8192);
        this.position = 0;
    }
    
    protected void charError(final String s) throws IOException {
        throw new IOException(Messages.formatMessage("invalid.char", new Object[] { s }));
    }
    
    protected void endOfStreamError(final String s) throws IOException {
        throw new IOException(Messages.formatMessage("end.of.stream", new Object[] { s }));
    }
}
