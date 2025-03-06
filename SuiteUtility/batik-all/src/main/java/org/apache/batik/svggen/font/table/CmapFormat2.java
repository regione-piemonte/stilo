// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CmapFormat2 extends CmapFormat
{
    private short[] subHeaderKeys;
    private int[] subHeaders1;
    private int[] subHeaders2;
    private short[] glyphIndexArray;
    
    protected CmapFormat2(final RandomAccessFile randomAccessFile) throws IOException {
        super(randomAccessFile);
        this.subHeaderKeys = new short[256];
        this.format = 2;
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
