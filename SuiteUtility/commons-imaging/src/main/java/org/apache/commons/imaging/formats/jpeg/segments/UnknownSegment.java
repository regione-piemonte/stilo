// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.segments;

import java.io.IOException;
import java.io.InputStream;

public class UnknownSegment extends GenericSegment
{
    public UnknownSegment(final int marker, final int markerLength, final InputStream is) throws IOException {
        super(marker, markerLength, is);
    }
    
    public UnknownSegment(final int marker, final byte[] bytes) {
        super(marker, bytes);
    }
    
    @Override
    public String getDescription() {
        return "Unknown (" + this.getSegmentType() + ")";
    }
}
