// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common.bytesource;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ByteSourceArray extends ByteSource
{
    private final byte[] bytes;
    
    public ByteSourceArray(final String filename, final byte[] bytes) {
        super(filename);
        this.bytes = bytes;
    }
    
    public ByteSourceArray(final byte[] bytes) {
        this(null, bytes);
    }
    
    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.bytes);
    }
    
    @Override
    public byte[] getBlock(final long startLong, final int length) throws IOException {
        final int start = (int)startLong;
        if (start < 0 || length < 0 || start + length < 0 || start + length > this.bytes.length) {
            throw new IOException("Could not read block (block start: " + start + ", block length: " + length + ", data length: " + this.bytes.length + ").");
        }
        final byte[] result = new byte[length];
        System.arraycopy(this.bytes, start, result, 0, length);
        return result;
    }
    
    @Override
    public long getLength() {
        return this.bytes.length;
    }
    
    @Override
    public byte[] getAll() throws IOException {
        return this.bytes;
    }
    
    @Override
    public String getDescription() {
        return this.bytes.length + " byte array";
    }
}
