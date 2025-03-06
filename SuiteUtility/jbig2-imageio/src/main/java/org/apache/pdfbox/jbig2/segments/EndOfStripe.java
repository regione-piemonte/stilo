// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.segments;

import org.apache.pdfbox.jbig2.SegmentHeader;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import org.apache.pdfbox.jbig2.err.IntegerMaxValueException;
import java.io.IOException;
import org.apache.pdfbox.jbig2.io.SubInputStream;
import org.apache.pdfbox.jbig2.SegmentData;

public class EndOfStripe implements SegmentData
{
    private SubInputStream subInputStream;
    private int lineNumber;
    
    private void parseHeader() throws IOException, IntegerMaxValueException, InvalidHeaderValueException {
        this.lineNumber = (int)(this.subInputStream.readBits(32) & -1L);
    }
    
    @Override
    public void init(final SegmentHeader segmentHeader, final SubInputStream subInputStream) throws IntegerMaxValueException, InvalidHeaderValueException, IOException {
        this.subInputStream = subInputStream;
        this.parseHeader();
    }
    
    public int getLineNumber() {
        return this.lineNumber;
    }
}
