// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DirectoryEntry
{
    private int tag;
    private int checksum;
    private int offset;
    private int length;
    private Table table;
    
    protected DirectoryEntry(final RandomAccessFile randomAccessFile) throws IOException {
        this.table = null;
        this.tag = randomAccessFile.readInt();
        this.checksum = randomAccessFile.readInt();
        this.offset = randomAccessFile.readInt();
        this.length = randomAccessFile.readInt();
    }
    
    public int getChecksum() {
        return this.checksum;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public int getOffset() {
        return this.offset;
    }
    
    public int getTag() {
        return this.tag;
    }
    
    public String toString() {
        return new StringBuffer().append((char)(this.tag >> 24 & 0xFF)).append((char)(this.tag >> 16 & 0xFF)).append((char)(this.tag >> 8 & 0xFF)).append((char)(this.tag & 0xFF)).append(", offset: ").append(this.offset).append(", length: ").append(this.length).append(", checksum: 0x").append(Integer.toHexString(this.checksum)).toString();
    }
}
