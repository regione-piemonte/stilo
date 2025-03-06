// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common.mylzw;

import java.io.IOException;
import java.io.InputStream;

public class BitsToByteInputStream extends InputStream
{
    private final MyBitInputStream is;
    private final int desiredDepth;
    
    public BitsToByteInputStream(final MyBitInputStream is, final int desiredDepth) {
        this.is = is;
        this.desiredDepth = desiredDepth;
    }
    
    @Override
    public int read() throws IOException {
        return this.readBits(8);
    }
    
    public int readBits(final int bitCount) throws IOException {
        int i = this.is.readBits(bitCount);
        if (bitCount < this.desiredDepth) {
            i <<= this.desiredDepth - bitCount;
        }
        else if (bitCount > this.desiredDepth) {
            i >>= bitCount - this.desiredDepth;
        }
        return i;
    }
    
    public int[] readBitsArray(final int sampleBits, final int length) throws IOException {
        final int[] result = new int[length];
        for (int i = 0; i < length; ++i) {
            result[i] = this.readBits(sampleBits);
        }
        return result;
    }
}
