// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common.mylzw;

import java.io.IOException;
import java.nio.ByteOrder;
import java.io.InputStream;

public class MyBitInputStream extends InputStream
{
    private final InputStream is;
    private final ByteOrder byteOrder;
    private boolean tiffLZWMode;
    private long bytesRead;
    private int bitsInCache;
    private int bitCache;
    
    public MyBitInputStream(final InputStream is, final ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
        this.is = is;
    }
    
    @Override
    public int read() throws IOException {
        return this.readBits(8);
    }
    
    public void setTiffLZWMode() {
        this.tiffLZWMode = true;
    }
    
    public int readBits(final int sampleBits) throws IOException {
        while (this.bitsInCache < sampleBits) {
            final int next = this.is.read();
            if (next < 0) {
                if (this.tiffLZWMode) {
                    return 257;
                }
                return -1;
            }
            else {
                final int newByte = 0xFF & next;
                if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
                    this.bitCache = (this.bitCache << 8 | newByte);
                }
                else {
                    this.bitCache |= newByte << this.bitsInCache;
                }
                ++this.bytesRead;
                this.bitsInCache += 8;
            }
        }
        final int sampleMask = (1 << sampleBits) - 1;
        int sample;
        if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
            sample = (sampleMask & this.bitCache >> this.bitsInCache - sampleBits);
        }
        else {
            sample = (sampleMask & this.bitCache);
            this.bitCache >>= sampleBits;
        }
        final int result = sample;
        this.bitsInCache -= sampleBits;
        final int remainderMask = (1 << this.bitsInCache) - 1;
        this.bitCache &= remainderMask;
        return result;
    }
    
    public void flushCache() {
        this.bitsInCache = 0;
        this.bitCache = 0;
    }
    
    public long getBytesRead() {
        return this.bytesRead;
    }
}
