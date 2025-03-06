// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.datareaders;

import java.io.IOException;
import java.nio.ByteOrder;
import java.io.InputStream;

class BitInputStream extends InputStream
{
    private final InputStream is;
    private final ByteOrder byteOrder;
    private int cache;
    private int cacheBitsRemaining;
    private long bytesRead;
    
    BitInputStream(final InputStream is, final ByteOrder byteOrder) {
        this.is = is;
        this.byteOrder = byteOrder;
    }
    
    @Override
    public int read() throws IOException {
        if (this.cacheBitsRemaining > 0) {
            throw new IOException("BitInputStream: incomplete bit read");
        }
        return this.is.read();
    }
    
    public final int readBits(final int count) throws IOException {
        if (count < 8) {
            if (this.cacheBitsRemaining == 0) {
                this.cache = this.is.read();
                this.cacheBitsRemaining = 8;
                ++this.bytesRead;
            }
            if (count > this.cacheBitsRemaining) {
                throw new IOException("BitInputStream: can't read bit fields across bytes");
            }
            this.cacheBitsRemaining -= count;
            final int bits = this.cache >> this.cacheBitsRemaining;
            switch (count) {
                case 1: {
                    return bits & 0x1;
                }
                case 2: {
                    return bits & 0x3;
                }
                case 3: {
                    return bits & 0x7;
                }
                case 4: {
                    return bits & 0xF;
                }
                case 5: {
                    return bits & 0x1F;
                }
                case 6: {
                    return bits & 0x3F;
                }
                case 7: {
                    return bits & 0x7F;
                }
            }
        }
        if (this.cacheBitsRemaining > 0) {
            throw new IOException("BitInputStream: incomplete bit read");
        }
        if (count == 8) {
            ++this.bytesRead;
            return this.is.read();
        }
        if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
            if (count == 16) {
                this.bytesRead += 2L;
                return this.is.read() << 8 | this.is.read() << 0;
            }
            if (count == 24) {
                this.bytesRead += 3L;
                return this.is.read() << 16 | this.is.read() << 8 | this.is.read() << 0;
            }
            if (count == 32) {
                this.bytesRead += 4L;
                return this.is.read() << 24 | this.is.read() << 16 | this.is.read() << 8 | this.is.read() << 0;
            }
        }
        else {
            if (count == 16) {
                this.bytesRead += 2L;
                return this.is.read() << 0 | this.is.read() << 8;
            }
            if (count == 24) {
                this.bytesRead += 3L;
                return this.is.read() << 0 | this.is.read() << 8 | this.is.read() << 16;
            }
            if (count == 32) {
                this.bytesRead += 4L;
                return this.is.read() << 0 | this.is.read() << 8 | this.is.read() << 16 | this.is.read() << 24;
            }
        }
        throw new IOException("BitInputStream: unknown error");
    }
    
    public void flushCache() {
        this.cacheBitsRemaining = 0;
    }
    
    public long getBytesRead() {
        return this.bytesRead;
    }
}
