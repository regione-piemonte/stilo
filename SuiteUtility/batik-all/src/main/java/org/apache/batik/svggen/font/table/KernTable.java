// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class KernTable implements Table
{
    private int version;
    private int nTables;
    private KernSubtable[] tables;
    
    protected KernTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(directoryEntry.getOffset());
        this.version = randomAccessFile.readUnsignedShort();
        this.nTables = randomAccessFile.readUnsignedShort();
        this.tables = new KernSubtable[this.nTables];
        for (int i = 0; i < this.nTables; ++i) {
            this.tables[i] = KernSubtable.read(randomAccessFile);
        }
    }
    
    public int getSubtableCount() {
        return this.nTables;
    }
    
    public KernSubtable getSubtable(final int n) {
        return this.tables[n];
    }
    
    public int getType() {
        return 1801810542;
    }
}
