// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.OutputStream;

public class SeekableOutputStream extends OutputStream
{
    private RandomAccessFile file;
    
    public SeekableOutputStream(final RandomAccessFile file) {
        if (file == null) {
            throw new IllegalArgumentException("SeekableOutputStream0");
        }
        this.file = file;
    }
    
    public void write(final int b) throws IOException {
        this.file.write(b);
    }
    
    public void write(final byte[] b) throws IOException {
        this.file.write(b);
    }
    
    public void write(final byte[] b, final int off, final int len) throws IOException {
        this.file.write(b, off, len);
    }
    
    public void flush() throws IOException {
        this.file.getFD().sync();
    }
    
    public void close() throws IOException {
        this.file.close();
    }
    
    public long getFilePointer() throws IOException {
        return this.file.getFilePointer();
    }
    
    public void seek(final long pos) throws IOException {
        this.file.seek(pos);
    }
}
