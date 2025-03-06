// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CoverageFormat2 extends Coverage
{
    private int rangeCount;
    private RangeRecord[] rangeRecords;
    
    protected CoverageFormat2(final RandomAccessFile randomAccessFile) throws IOException {
        this.rangeCount = randomAccessFile.readUnsignedShort();
        this.rangeRecords = new RangeRecord[this.rangeCount];
        for (int i = 0; i < this.rangeCount; ++i) {
            this.rangeRecords[i] = new RangeRecord(randomAccessFile);
        }
    }
    
    public int getFormat() {
        return 2;
    }
    
    public int findGlyph(final int n) {
        for (int i = 0; i < this.rangeCount; ++i) {
            final int coverageIndex = this.rangeRecords[i].getCoverageIndex(n);
            if (coverageIndex > -1) {
                return coverageIndex;
            }
        }
        return -1;
    }
}
