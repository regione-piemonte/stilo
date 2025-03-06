// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.segments;

import java.io.IOException;
import java.io.InputStream;

public class AppnSegment extends GenericSegment
{
    public AppnSegment(final int marker, final int markerLength, final InputStream is) throws IOException {
        super(marker, markerLength, is);
    }
    
    @Override
    public String getDescription() {
        return "APPN (APP" + (this.marker - 65504) + ") (" + this.getSegmentType() + ")";
    }
}
