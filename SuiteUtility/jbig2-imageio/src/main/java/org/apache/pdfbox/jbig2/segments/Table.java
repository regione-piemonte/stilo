// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.segments;

import org.apache.pdfbox.jbig2.SegmentHeader;
import org.apache.pdfbox.jbig2.err.IntegerMaxValueException;
import java.io.IOException;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import org.apache.pdfbox.jbig2.io.SubInputStream;
import org.apache.pdfbox.jbig2.SegmentData;

public class Table implements SegmentData
{
    private SubInputStream subInputStream;
    private int htOutOfBand;
    private int htPS;
    private int htRS;
    private int htLow;
    private int htHigh;
    
    private void parseHeader() throws IOException, InvalidHeaderValueException, IntegerMaxValueException {
        final int bit;
        if ((bit = this.subInputStream.readBit()) == 1) {
            throw new InvalidHeaderValueException("B.2.1 Code table flags: Bit 7 must be zero, but was " + bit);
        }
        this.htRS = (int)(this.subInputStream.readBits(3) + 1L & 0xFL);
        this.htPS = (int)(this.subInputStream.readBits(3) + 1L & 0xFL);
        this.htOutOfBand = this.subInputStream.readBit();
        this.htLow = (int)this.subInputStream.readBits(32);
        this.htHigh = (int)this.subInputStream.readBits(32);
    }
    
    @Override
    public void init(final SegmentHeader segmentHeader, final SubInputStream subInputStream) throws InvalidHeaderValueException, IOException, IntegerMaxValueException {
        this.subInputStream = subInputStream;
        this.parseHeader();
    }
    
    public int getHtOOB() {
        return this.htOutOfBand;
    }
    
    public int getHtPS() {
        return this.htPS;
    }
    
    public int getHtRS() {
        return this.htRS;
    }
    
    public int getHtLow() {
        return this.htLow;
    }
    
    public int getHtHigh() {
        return this.htHigh;
    }
    
    public SubInputStream getSubInputStream() {
        return this.subInputStream;
    }
}
