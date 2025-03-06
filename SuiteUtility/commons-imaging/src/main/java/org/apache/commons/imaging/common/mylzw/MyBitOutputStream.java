// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common.mylzw;

import java.io.IOException;
import java.nio.ByteOrder;
import java.io.OutputStream;

public class MyBitOutputStream extends OutputStream
{
    private final OutputStream os;
    private final ByteOrder byteOrder;
    private int bitsInCache;
    private int bitCache;
    private int bytesWritten;
    
    public MyBitOutputStream(final OutputStream os, final ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
        this.os = os;
    }
    
    @Override
    public void write(final int value) throws IOException {
        this.writeBits(value, 8);
    }
    
    public void writeBits(int value, final int sampleBits) throws IOException {
        final int sampleMask = (1 << sampleBits) - 1;
        value &= sampleMask;
        if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
            this.bitCache = (this.bitCache << sampleBits | value);
        }
        else {
            this.bitCache |= value << this.bitsInCache;
        }
        this.bitsInCache += sampleBits;
        while (this.bitsInCache >= 8) {
            if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
                final int b = 0xFF & this.bitCache >> this.bitsInCache - 8;
                this.actualWrite(b);
                this.bitsInCache -= 8;
            }
            else {
                final int b = 0xFF & this.bitCache;
                this.actualWrite(b);
                this.bitCache >>= 8;
                this.bitsInCache -= 8;
            }
            final int remainderMask = (1 << this.bitsInCache) - 1;
            this.bitCache &= remainderMask;
        }
    }
    
    private void actualWrite(final int value) throws IOException {
        this.os.write(value);
        ++this.bytesWritten;
    }
    
    public void flushCache() throws IOException {
        if (this.bitsInCache > 0) {
            final int bitMask = (1 << this.bitsInCache) - 1;
            int b = bitMask & this.bitCache;
            if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
                b <<= 8 - this.bitsInCache;
                this.os.write(b);
            }
            else {
                this.os.write(b);
            }
        }
        this.bitsInCache = 0;
        this.bitCache = 0;
    }
    
    public int getBytesWritten() {
        return this.bytesWritten + ((this.bitsInCache > 0) ? 1 : 0);
    }
}
