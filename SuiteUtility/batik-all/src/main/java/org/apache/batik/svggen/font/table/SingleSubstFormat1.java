// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class SingleSubstFormat1 extends SingleSubst
{
    private int coverageOffset;
    private short deltaGlyphID;
    private Coverage coverage;
    
    protected SingleSubstFormat1(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        this.coverageOffset = randomAccessFile.readUnsignedShort();
        this.deltaGlyphID = randomAccessFile.readShort();
        randomAccessFile.seek(n + this.coverageOffset);
        this.coverage = Coverage.read(randomAccessFile);
    }
    
    public int getFormat() {
        return 1;
    }
    
    public int substitute(final int n) {
        if (this.coverage.findGlyph(n) > -1) {
            return n + this.deltaGlyphID;
        }
        return n;
    }
}
