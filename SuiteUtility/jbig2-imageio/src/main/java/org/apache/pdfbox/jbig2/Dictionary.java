// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import org.apache.pdfbox.jbig2.err.IntegerMaxValueException;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import java.io.IOException;
import java.util.ArrayList;

public interface Dictionary extends SegmentData
{
    ArrayList<Bitmap> getDictionary() throws IOException, InvalidHeaderValueException, IntegerMaxValueException;
}
