// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CoverageFormat1 extends Coverage
{
    private int glyphCount;
    private int[] glyphIds;
    
    protected CoverageFormat1(final RandomAccessFile randomAccessFile) throws IOException {
        this.glyphCount = randomAccessFile.readUnsignedShort();
        this.glyphIds = new int[this.glyphCount];
        for (int i = 0; i < this.glyphCount; ++i) {
            this.glyphIds[i] = randomAccessFile.readUnsignedShort();
        }
    }
    
    public int getFormat() {
        return 1;
    }
    
    public int findGlyph(final int n) {
        for (int i = 0; i < this.glyphCount; ++i) {
            if (this.glyphIds[i] == n) {
                return i;
            }
        }
        return -1;
    }
}
