// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class Coverage
{
    public abstract int getFormat();
    
    public abstract int findGlyph(final int p0);
    
    protected static Coverage read(final RandomAccessFile randomAccessFile) throws IOException {
        Coverage coverage = null;
        final int unsignedShort = randomAccessFile.readUnsignedShort();
        if (unsignedShort == 1) {
            coverage = new CoverageFormat1(randomAccessFile);
        }
        else if (unsignedShort == 2) {
            coverage = new CoverageFormat2(randomAccessFile);
        }
        return coverage;
    }
}
