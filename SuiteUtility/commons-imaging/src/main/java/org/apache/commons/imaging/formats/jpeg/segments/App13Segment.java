// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.segments;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.jpeg.iptc.PhotoshopApp13Data;
import java.util.Map;
import org.apache.commons.imaging.formats.jpeg.iptc.IptcParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import org.apache.commons.imaging.formats.jpeg.JpegImageParser;

public class App13Segment extends AppnSegment
{
    public App13Segment(final JpegImageParser parser, final int marker, final byte[] segmentData) throws IOException {
        this(marker, segmentData.length, new ByteArrayInputStream(segmentData));
    }
    
    public App13Segment(final int marker, final int markerLength, final InputStream is) throws IOException {
        super(marker, markerLength, is);
    }
    
    public boolean isPhotoshopJpegSegment() {
        return new IptcParser().isPhotoshopJpegSegment(this.getSegmentData());
    }
    
    public PhotoshopApp13Data parsePhotoshopSegment(final Map<String, Object> params) throws ImageReadException, IOException {
        if (!this.isPhotoshopJpegSegment()) {
            return null;
        }
        return new IptcParser().parsePhotoshopSegment(this.getSegmentData(), params);
    }
}
