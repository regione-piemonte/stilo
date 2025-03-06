// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;
import java.io.InputStream;

public final class FileCacheSeekableStream extends SeekableStream
{
    private InputStream stream;
    private File cacheFile;
    private RandomAccessFile cache;
    private int bufLen;
    private byte[] buf;
    private long length;
    private long pointer;
    private boolean foundEOF;
    
    public FileCacheSeekableStream(final InputStream stream) throws IOException {
        this.bufLen = 1024;
        this.buf = new byte[this.bufLen];
        this.length = 0L;
        this.pointer = 0L;
        this.foundEOF = false;
        this.stream = stream;
        (this.cacheFile = File.createTempFile("jai-FCSS-", ".tmp")).deleteOnExit();
        this.cache = new RandomAccessFile(this.cacheFile, "rw");
    }
    
    private long readUntil(final long n) throws IOException {
        if (n < this.length) {
            return n;
        }
        if (this.foundEOF) {
            return this.length;
        }
        long a = n - this.length;
        this.cache.seek(this.length);
        while (a > 0L) {
            final int read = this.stream.read(this.buf, 0, (int)Math.min(a, this.bufLen));
            if (read == -1) {
                this.foundEOF = true;
                return this.length;
            }
            this.cache.setLength(this.cache.length() + read);
            this.cache.write(this.buf, 0, read);
            a -= read;
            this.length += read;
        }
        return n;
    }
    
    public boolean canSeekBackwards() {
        return true;
    }
    
    public long getFilePointer() {
        return this.pointer;
    }
    
    public void seek(final long pointer) throws IOException {
        if (pointer < 0L) {
            throw new IOException(PropertyUtil.getString("FileCacheSeekableStream0"));
        }
        this.pointer = pointer;
    }
    
    public int read() throws IOException {
        final long n = this.pointer + 1L;
        if (this.readUntil(n) >= n) {
            this.cache.seek(this.pointer++);
            return this.cache.read();
        }
        return -1;
    }
    
    public int read(final byte[] b, final int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        }
        if (off < 0 || len < 0 || off + len > b.length) {
            throw new IndexOutOfBoundsException();
        }
        if (len == 0) {
            return 0;
        }
        len = (int)Math.min(len, this.readUntil(this.pointer + len) - this.pointer);
        if (len > 0) {
            this.cache.seek(this.pointer);
            this.cache.readFully(b, off, len);
            this.pointer += len;
            return len;
        }
        return -1;
    }
    
    public void close() throws IOException {
        super.close();
        this.cache.close();
        this.cacheFile.delete();
    }
}
