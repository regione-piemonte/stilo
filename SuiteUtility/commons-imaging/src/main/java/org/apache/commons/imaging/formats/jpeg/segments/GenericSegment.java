// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.segments;

import java.io.UnsupportedEncodingException;
import java.io.PrintWriter;
import java.io.IOException;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.InputStream;

public abstract class GenericSegment extends Segment
{
    private final byte[] segmentData;
    
    public GenericSegment(final int marker, final int markerLength, final InputStream is) throws IOException {
        super(marker, markerLength);
        this.segmentData = BinaryFunctions.readBytes("Segment Data", is, markerLength, "Invalid Segment: insufficient data");
    }
    
    public GenericSegment(final int marker, final byte[] bytes) {
        super(marker, bytes.length);
        this.segmentData = bytes.clone();
    }
    
    @Override
    public void dump(final PrintWriter pw) {
        this.dump(pw, 0);
    }
    
    public void dump(final PrintWriter pw, final int start) {
        for (int i = 0; i < 50 && i + start < this.segmentData.length; ++i) {
            this.debugNumber(pw, "\t" + (i + start), this.segmentData[i + start], 1);
        }
    }
    
    public byte[] getSegmentData() {
        return this.segmentData.clone();
    }
    
    protected byte getSegmentData(final int offset) {
        return this.segmentData[offset];
    }
    
    public String getSegmentDataAsString(final String encoding) throws UnsupportedEncodingException {
        return new String(this.segmentData, encoding);
    }
}
