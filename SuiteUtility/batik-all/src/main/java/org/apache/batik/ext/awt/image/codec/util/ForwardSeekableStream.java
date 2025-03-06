// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.util;

import java.io.IOException;
import java.io.InputStream;

public class ForwardSeekableStream extends SeekableStream
{
    private InputStream src;
    long pointer;
    
    public ForwardSeekableStream(final InputStream src) {
        this.pointer = 0L;
        this.src = src;
    }
    
    public final int read() throws IOException {
        final int read = this.src.read();
        if (read != -1) {
            ++this.pointer;
        }
        return read;
    }
    
    public final int read(final byte[] b, final int off, final int len) throws IOException {
        final int read = this.src.read(b, off, len);
        if (read != -1) {
            this.pointer += read;
        }
        return read;
    }
    
    public final long skip(final long n) throws IOException {
        final long skip = this.src.skip(n);
        this.pointer += skip;
        return skip;
    }
    
    public final int available() throws IOException {
        return this.src.available();
    }
    
    public final void close() throws IOException {
        this.src.close();
    }
    
    public final synchronized void mark(final int readlimit) {
        this.markPos = this.pointer;
        this.src.mark(readlimit);
    }
    
    public final synchronized void reset() throws IOException {
        if (this.markPos != -1L) {
            this.pointer = this.markPos;
        }
        this.src.reset();
    }
    
    public boolean markSupported() {
        return this.src.markSupported();
    }
    
    public final boolean canSeekBackwards() {
        return false;
    }
    
    public final long getFilePointer() {
        return this.pointer;
    }
    
    public final void seek(final long n) throws IOException {
        while (n - this.pointer > 0L) {
            this.pointer += this.src.skip(n - this.pointer);
        }
    }
}
