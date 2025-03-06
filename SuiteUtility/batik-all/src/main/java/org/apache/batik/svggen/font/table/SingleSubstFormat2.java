// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class SingleSubstFormat2 extends SingleSubst
{
    private int coverageOffset;
    private int glyphCount;
    private int[] substitutes;
    private Coverage coverage;
    
    protected SingleSubstFormat2(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        this.coverageOffset = randomAccessFile.readUnsignedShort();
        this.glyphCount = randomAccessFile.readUnsignedShort();
        this.substitutes = new int[this.glyphCount];
        for (int i = 0; i < this.glyphCount; ++i) {
            this.substitutes[i] = randomAccessFile.readUnsignedShort();
        }
        randomAccessFile.seek(n + this.coverageOffset);
        this.coverage = Coverage.read(randomAccessFile);
    }
    
    public int getFormat() {
        return 2;
    }
    
    public int substitute(final int n) {
        final int glyph = this.coverage.findGlyph(n);
        if (glyph > -1) {
            return this.substitutes[glyph];
        }
        return n;
    }
}
