// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CvtTable implements Table
{
    private short[] values;
    
    protected CvtTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(directoryEntry.getOffset());
        final int n = directoryEntry.getLength() / 2;
        this.values = new short[n];
        for (int i = 0; i < n; ++i) {
            this.values[i] = randomAccessFile.readShort();
        }
    }
    
    public int getType() {
        return 1668707360;
    }
    
    public short[] getValues() {
        return this.values;
    }
}
