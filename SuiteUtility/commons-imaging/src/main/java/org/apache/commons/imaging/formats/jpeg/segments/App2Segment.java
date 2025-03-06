// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.segments;

import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.formats.jpeg.JpegConstants;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class App2Segment extends AppnSegment implements Comparable<App2Segment>
{
    private final byte[] iccBytes;
    public final int curMarker;
    public final int numMarkers;
    
    public App2Segment(final int marker, final byte[] segmentData) throws ImageReadException, IOException {
        this(marker, segmentData.length, new ByteArrayInputStream(segmentData));
    }
    
    public App2Segment(final int marker, int markerLength, final InputStream is2) throws ImageReadException, IOException {
        super(marker, markerLength, is2);
        if (BinaryFunctions.startsWith(this.getSegmentData(), JpegConstants.ICC_PROFILE_LABEL)) {
            final InputStream is3 = new ByteArrayInputStream(this.getSegmentData());
            BinaryFunctions.readAndVerifyBytes(is3, JpegConstants.ICC_PROFILE_LABEL, "Not a Valid App2 Segment: missing ICC Profile label");
            this.curMarker = BinaryFunctions.readByte("curMarker", is3, "Not a valid App2 Marker");
            this.numMarkers = BinaryFunctions.readByte("numMarkers", is3, "Not a valid App2 Marker");
            markerLength -= JpegConstants.ICC_PROFILE_LABEL.size();
            markerLength -= 2;
            this.iccBytes = BinaryFunctions.readBytes("App2 Data", is3, markerLength, "Invalid App2 Segment: insufficient data");
        }
        else {
            this.curMarker = -1;
            this.numMarkers = -1;
            this.iccBytes = null;
        }
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof App2Segment) {
            final App2Segment other = (App2Segment)obj;
            return this.curMarker == other.curMarker;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.curMarker;
    }
    
    @Override
    public int compareTo(final App2Segment other) {
        return this.curMarker - other.curMarker;
    }
    
    public byte[] getIccBytes() {
        return this.iccBytes;
    }
}
