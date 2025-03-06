// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class NameTable implements Table
{
    private short formatSelector;
    private short numberOfNameRecords;
    private short stringStorageOffset;
    private NameRecord[] records;
    
    protected NameTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(directoryEntry.getOffset());
        this.formatSelector = randomAccessFile.readShort();
        this.numberOfNameRecords = randomAccessFile.readShort();
        this.stringStorageOffset = randomAccessFile.readShort();
        this.records = new NameRecord[this.numberOfNameRecords];
        for (short n = 0; n < this.numberOfNameRecords; ++n) {
            this.records[n] = new NameRecord(randomAccessFile);
        }
        for (short n2 = 0; n2 < this.numberOfNameRecords; ++n2) {
            this.records[n2].loadString(randomAccessFile, directoryEntry.getOffset() + this.stringStorageOffset);
        }
    }
    
    public String getRecord(final short n) {
        for (short n2 = 0; n2 < this.numberOfNameRecords; ++n2) {
            if (this.records[n2].getNameId() == n) {
                return this.records[n2].getRecordString();
            }
        }
        return "";
    }
    
    public int getType() {
        return 1851878757;
    }
}
