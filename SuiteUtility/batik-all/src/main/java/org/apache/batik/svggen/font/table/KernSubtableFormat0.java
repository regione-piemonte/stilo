// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class KernSubtableFormat0 extends KernSubtable
{
    private int nPairs;
    private int searchRange;
    private int entrySelector;
    private int rangeShift;
    private KerningPair[] kerningPairs;
    
    protected KernSubtableFormat0(final RandomAccessFile randomAccessFile) throws IOException {
        this.nPairs = randomAccessFile.readUnsignedShort();
        this.searchRange = randomAccessFile.readUnsignedShort();
        this.entrySelector = randomAccessFile.readUnsignedShort();
        this.rangeShift = randomAccessFile.readUnsignedShort();
        this.kerningPairs = new KerningPair[this.nPairs];
        for (int i = 0; i < this.nPairs; ++i) {
            this.kerningPairs[i] = new KerningPair(randomAccessFile);
        }
    }
    
    public int getKerningPairCount() {
        return this.nPairs;
    }
    
    public KerningPair getKerningPair(final int n) {
        return this.kerningPairs[n];
    }
}
