// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CmapTable implements Table
{
    private int version;
    private int numTables;
    private CmapIndexEntry[] entries;
    private CmapFormat[] formats;
    
    protected CmapTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(directoryEntry.getOffset());
        final long filePointer = randomAccessFile.getFilePointer();
        this.version = randomAccessFile.readUnsignedShort();
        this.numTables = randomAccessFile.readUnsignedShort();
        this.entries = new CmapIndexEntry[this.numTables];
        this.formats = new CmapFormat[this.numTables];
        for (int i = 0; i < this.numTables; ++i) {
            this.entries[i] = new CmapIndexEntry(randomAccessFile);
        }
        for (int j = 0; j < this.numTables; ++j) {
            randomAccessFile.seek(filePointer + this.entries[j].getOffset());
            this.formats[j] = CmapFormat.create(randomAccessFile.readUnsignedShort(), randomAccessFile);
        }
    }
    
    public CmapFormat getCmapFormat(final short n, final short n2) {
        for (int i = 0; i < this.numTables; ++i) {
            if (this.entries[i].getPlatformId() == n && this.entries[i].getEncodingId() == n2) {
                return this.formats[i];
            }
        }
        return null;
    }
    
    public int getType() {
        return 1668112752;
    }
    
    public String toString() {
        final StringBuffer append = new StringBuffer(this.numTables * 8).append("cmap\n");
        for (int i = 0; i < this.numTables; ++i) {
            append.append('\t').append(this.entries[i].toString()).append('\n');
        }
        for (int j = 0; j < this.numTables; ++j) {
            append.append('\t').append(this.formats[j].toString()).append('\n');
        }
        return append.toString();
    }
}
