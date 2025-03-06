// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.segments;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.InputStream;

public class ComSegment extends GenericSegment
{
    public ComSegment(final int marker, final byte[] segmentData) {
        super(marker, segmentData);
    }
    
    public ComSegment(final int marker, final int markerLength, final InputStream is) throws IOException {
        super(marker, markerLength, is);
    }
    
    public byte[] getComment() {
        return this.getSegmentData();
    }
    
    @Override
    public String getDescription() {
        String commentString = "";
        try {
            commentString = this.getSegmentDataAsString("UTF-8");
        }
        catch (UnsupportedEncodingException ex) {}
        return "COM (" + commentString + ")";
    }
}
