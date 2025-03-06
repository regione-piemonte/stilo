// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;

public final class MemoryCacheSeekableStream extends SeekableStream
{
    private InputStream src;
    private long pointer;
    private static final int SECTOR_SHIFT = 9;
    private static final int SECTOR_SIZE = 512;
    private static final int SECTOR_MASK = 511;
    private List data;
    int sectors;
    int length;
    boolean foundEOS;
    
    public MemoryCacheSeekableStream(final InputStream src) {
        this.pointer = 0L;
        this.data = new ArrayList();
        this.sectors = 0;
        this.length = 0;
        this.foundEOS = false;
        this.src = src;
    }
    
    private long readUntil(final long n) throws IOException {
        if (n < this.length) {
            return n;
        }
        if (this.foundEOS) {
            return this.length;
        }
        for (int n2 = (int)(n >> 9), i = this.length >> 9; i <= n2; ++i) {
            final byte[] b = new byte[512];
            this.data.add(b);
            int j = 512;
            int off = 0;
            while (j > 0) {
                final int read = this.src.read(b, off, j);
                if (read == -1) {
                    this.foundEOS = true;
                    return this.length;
                }
                off += read;
                j -= read;
                this.length += read;
            }
        }
        return this.length;
    }
    
    public boolean canSeekBackwards() {
        return true;
    }
    
    public long getFilePointer() {
        return this.pointer;
    }
    
    public void seek(final long pointer) throws IOException {
        if (pointer < 0L) {
            throw new IOException(PropertyUtil.getString("MemoryCacheSeekableStream0"));
        }
        this.pointer = pointer;
    }
    
    public int read() throws IOException {
        final long n = this.pointer + 1L;
        if (this.readUntil(n) >= n) {
            return ((byte[])this.data.get((int)(this.pointer >> 9)))[(int)(this.pointer++ & 0x1FFL)] & 0xFF;
        }
        return -1;
    }
    
    public int read(final byte[] array, final int n, final int a) throws IOException {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || a < 0 || n + a > array.length) {
            throw new IndexOutOfBoundsException();
        }
        if (a == 0) {
            return 0;
        }
        if (this.readUntil(this.pointer + a) <= this.pointer) {
            return -1;
        }
        final byte[] array2 = this.data.get((int)(this.pointer >> 9));
        final int min = Math.min(a, 512 - (int)(this.pointer & 0x1FFL));
        System.arraycopy(array2, (int)(this.pointer & 0x1FFL), array, n, min);
        this.pointer += min;
        return min;
    }
}
