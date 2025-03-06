// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common.bytesource;

import java.io.IOException;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.InputStream;

public abstract class ByteSource
{
    private final String filename;
    
    public ByteSource(final String filename) {
        this.filename = filename;
    }
    
    public final InputStream getInputStream(final long start) throws IOException {
        InputStream is = null;
        boolean succeeded = false;
        try {
            is = this.getInputStream();
            BinaryFunctions.skipBytes(is, start);
            succeeded = true;
        }
        finally {
            if (!succeeded && is != null) {
                is.close();
            }
        }
        return is;
    }
    
    public abstract InputStream getInputStream() throws IOException;
    
    public byte[] getBlock(final int start, final int length) throws IOException {
        return this.getBlock(0xFFFFFFFFL & (long)start, length);
    }
    
    public abstract byte[] getBlock(final long p0, final int p1) throws IOException;
    
    public abstract byte[] getAll() throws IOException;
    
    public abstract long getLength() throws IOException;
    
    public abstract String getDescription();
    
    public final String getFilename() {
        return this.filename;
    }
}
