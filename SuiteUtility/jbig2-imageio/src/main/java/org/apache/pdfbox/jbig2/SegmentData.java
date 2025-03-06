// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import java.io.IOException;
import org.apache.pdfbox.jbig2.err.IntegerMaxValueException;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import org.apache.pdfbox.jbig2.io.SubInputStream;

public interface SegmentData
{
    void init(final SegmentHeader p0, final SubInputStream p1) throws InvalidHeaderValueException, IntegerMaxValueException, IOException;
}
