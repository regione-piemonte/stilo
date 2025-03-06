// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RangeRecord
{
    private int start;
    private int end;
    private int startCoverageIndex;
    
    public RangeRecord(final RandomAccessFile randomAccessFile) throws IOException {
        this.start = randomAccessFile.readUnsignedShort();
        this.end = randomAccessFile.readUnsignedShort();
        this.startCoverageIndex = randomAccessFile.readUnsignedShort();
    }
    
    public boolean isInRange(final int n) {
        return this.start <= n && n <= this.end;
    }
    
    public int getCoverageIndex(final int n) {
        if (this.isInRange(n)) {
            return this.startCoverageIndex + n - this.start;
        }
        return -1;
    }
}
