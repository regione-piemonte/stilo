// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.io;

import java.io.IOException;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageInputStreamImpl;

public class SubInputStream extends ImageInputStreamImpl
{
    protected final ImageInputStream wrappedStream;
    protected final long offset;
    protected final long length;
    private final byte[] buffer;
    long bufferBase;
    long bufferTop;
    
    public SubInputStream(final ImageInputStream wrappedStream, final long offset, final long length) {
        this.buffer = new byte[4096];
        assert null != wrappedStream;
        assert length >= 0L;
        assert offset >= 0L;
        this.wrappedStream = wrappedStream;
        this.offset = offset;
        this.length = length;
    }
    
    @Override
    public int read() throws IOException {
        if (this.streamPos >= this.length) {
            return -1;
        }
        if ((this.streamPos >= this.bufferTop || this.streamPos < this.bufferBase) && !this.fillBuffer()) {
            return -1;
        }
        final int n = 0xFF & this.buffer[(int)(this.streamPos - this.bufferBase)];
        ++this.streamPos;
        return n;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.streamPos >= this.length) {
            return -1;
        }
        synchronized (this.wrappedStream) {
            if (this.wrappedStream.getStreamPosition() != this.streamPos + this.offset) {
                this.wrappedStream.seek(this.streamPos + this.offset);
            }
            final int read = this.wrappedStream.read(array, n, (int)Math.min(n2, this.length - this.streamPos));
            this.streamPos += read;
            return read;
        }
    }
    
    private boolean fillBuffer() throws IOException {
        synchronized (this.wrappedStream) {
            if (this.wrappedStream.getStreamPosition() != this.streamPos + this.offset) {
                this.wrappedStream.seek(this.streamPos + this.offset);
            }
            this.bufferBase = this.streamPos;
            final int read = this.wrappedStream.read(this.buffer, 0, (int)Math.min(this.buffer.length, this.length - this.streamPos));
            this.bufferTop = this.bufferBase + read;
            return read > 0;
        }
    }
    
    @Override
    public long length() {
        return this.length;
    }
    
    public void skipBits() {
        if (this.bitOffset != 0) {
            this.bitOffset = 0;
            ++this.streamPos;
        }
    }
}
