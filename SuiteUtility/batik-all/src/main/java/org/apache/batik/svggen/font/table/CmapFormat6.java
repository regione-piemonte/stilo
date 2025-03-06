// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CmapFormat6 extends CmapFormat
{
    private short format;
    private short length;
    private short version;
    private short firstCode;
    private short entryCount;
    private short[] glyphIdArray;
    
    protected CmapFormat6(final RandomAccessFile randomAccessFile) throws IOException {
        super(randomAccessFile);
        this.format = 6;
    }
    
    public int getFirst() {
        return 0;
    }
    
    public int getLast() {
        return 0;
    }
    
    public int mapCharCode(final int n) {
        return 0;
    }
}
