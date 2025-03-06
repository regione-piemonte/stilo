// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class PrepTable extends Program implements Table
{
    public PrepTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(directoryEntry.getOffset());
        this.readInstructions(randomAccessFile, directoryEntry.getLength());
    }
    
    public int getType() {
        return 1886545264;
    }
}
