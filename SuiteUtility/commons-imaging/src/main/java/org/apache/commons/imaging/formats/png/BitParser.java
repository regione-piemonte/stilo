// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png;

import org.apache.commons.imaging.ImageReadException;

class BitParser
{
    private final byte[] bytes;
    private final int bitsPerPixel;
    private final int bitDepth;
    
    BitParser(final byte[] bytes, final int bitsPerPixel, final int bitDepth) {
        this.bytes = bytes;
        this.bitsPerPixel = bitsPerPixel;
        this.bitDepth = bitDepth;
    }
    
    public int getSample(final int pixelIndexInScanline, final int sampleIndex) throws ImageReadException {
        final int pixelIndexBits = this.bitsPerPixel * pixelIndexInScanline;
        final int sampleIndexBits = pixelIndexBits + sampleIndex * this.bitDepth;
        final int sampleIndexBytes = sampleIndexBits >> 3;
        if (this.bitDepth == 8) {
            return 0xFF & this.bytes[sampleIndexBytes];
        }
        if (this.bitDepth < 8) {
            int b = 0xFF & this.bytes[sampleIndexBytes];
            final int bitsToShift = 8 - ((pixelIndexBits & 0x7) + this.bitDepth);
            b >>= bitsToShift;
            final int bitmask = (1 << this.bitDepth) - 1;
            return b & bitmask;
        }
        if (this.bitDepth == 16) {
            return (0xFF & this.bytes[sampleIndexBytes]) << 8 | (0xFF & this.bytes[sampleIndexBytes + 1]);
        }
        throw new ImageReadException("PNG: bad BitDepth: " + this.bitDepth);
    }
    
    public int getSampleAsByte(final int pixelIndexInScanline, final int sampleIndex) throws ImageReadException {
        int sample = this.getSample(pixelIndexInScanline, sampleIndex);
        final int rot = 8 - this.bitDepth;
        if (rot > 0) {
            sample = sample * 255 / ((1 << this.bitDepth) - 1);
        }
        else if (rot < 0) {
            sample >>= -rot;
        }
        return 0xFF & sample;
    }
}
