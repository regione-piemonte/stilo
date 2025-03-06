// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import org.apache.pdfbox.jbig2.segments.RegionSegmentInformation;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import org.apache.pdfbox.jbig2.err.IntegerMaxValueException;
import java.io.IOException;

public interface Region extends SegmentData
{
    Bitmap getRegionBitmap() throws IOException, IntegerMaxValueException, InvalidHeaderValueException;
    
    RegionSegmentInformation getRegionInfo();
}
