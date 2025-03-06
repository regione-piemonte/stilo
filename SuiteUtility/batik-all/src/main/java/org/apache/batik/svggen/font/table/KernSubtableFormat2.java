// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class KernSubtableFormat2 extends KernSubtable
{
    private int rowWidth;
    private int leftClassTable;
    private int rightClassTable;
    private int array;
    
    protected KernSubtableFormat2(final RandomAccessFile randomAccessFile) throws IOException {
        this.rowWidth = randomAccessFile.readUnsignedShort();
        this.leftClassTable = randomAccessFile.readUnsignedShort();
        this.rightClassTable = randomAccessFile.readUnsignedShort();
        this.array = randomAccessFile.readUnsignedShort();
    }
    
    public int getKerningPairCount() {
        return 0;
    }
    
    public KerningPair getKerningPair(final int n) {
        return null;
    }
}
