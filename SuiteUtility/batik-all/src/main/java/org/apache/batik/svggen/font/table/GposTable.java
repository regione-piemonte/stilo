// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class GposTable implements Table
{
    protected GposTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(directoryEntry.getOffset());
        randomAccessFile.readInt();
        randomAccessFile.readInt();
        randomAccessFile.readInt();
        randomAccessFile.readInt();
    }
    
    public int getType() {
        return 1196445523;
    }
    
    public String toString() {
        return "GPOS";
    }
}
