// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TableDirectory
{
    private int version;
    private short numTables;
    private short searchRange;
    private short entrySelector;
    private short rangeShift;
    private DirectoryEntry[] entries;
    
    public TableDirectory(final RandomAccessFile randomAccessFile) throws IOException {
        this.version = 0;
        this.numTables = 0;
        this.searchRange = 0;
        this.entrySelector = 0;
        this.rangeShift = 0;
        this.version = randomAccessFile.readInt();
        this.numTables = randomAccessFile.readShort();
        this.searchRange = randomAccessFile.readShort();
        this.entrySelector = randomAccessFile.readShort();
        this.rangeShift = randomAccessFile.readShort();
        this.entries = new DirectoryEntry[this.numTables];
        for (short n = 0; n < this.numTables; ++n) {
            this.entries[n] = new DirectoryEntry(randomAccessFile);
        }
        int i = 1;
        while (i != 0) {
            i = 0;
            for (int j = 0; j < this.numTables - 1; ++j) {
                if (this.entries[j].getOffset() > this.entries[j + 1].getOffset()) {
                    final DirectoryEntry directoryEntry = this.entries[j];
                    this.entries[j] = this.entries[j + 1];
                    this.entries[j + 1] = directoryEntry;
                    i = 1;
                }
            }
        }
    }
    
    public DirectoryEntry getEntry(final int n) {
        return this.entries[n];
    }
    
    public DirectoryEntry getEntryByTag(final int n) {
        for (short n2 = 0; n2 < this.numTables; ++n2) {
            if (this.entries[n2].getTag() == n) {
                return this.entries[n2];
            }
        }
        return null;
    }
    
    public short getEntrySelector() {
        return this.entrySelector;
    }
    
    public short getNumTables() {
        return this.numTables;
    }
    
    public short getRangeShift() {
        return this.rangeShift;
    }
    
    public short getSearchRange() {
        return this.searchRange;
    }
    
    public int getVersion() {
        return this.version;
    }
}
