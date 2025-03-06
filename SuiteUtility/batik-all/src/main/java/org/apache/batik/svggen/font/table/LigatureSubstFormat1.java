// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class LigatureSubstFormat1 extends LigatureSubst
{
    private int coverageOffset;
    private int ligSetCount;
    private int[] ligatureSetOffsets;
    private Coverage coverage;
    private LigatureSet[] ligatureSets;
    
    protected LigatureSubstFormat1(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        this.coverageOffset = randomAccessFile.readUnsignedShort();
        this.ligSetCount = randomAccessFile.readUnsignedShort();
        this.ligatureSetOffsets = new int[this.ligSetCount];
        this.ligatureSets = new LigatureSet[this.ligSetCount];
        for (int i = 0; i < this.ligSetCount; ++i) {
            this.ligatureSetOffsets[i] = randomAccessFile.readUnsignedShort();
        }
        randomAccessFile.seek(n + this.coverageOffset);
        this.coverage = Coverage.read(randomAccessFile);
        for (int j = 0; j < this.ligSetCount; ++j) {
            this.ligatureSets[j] = new LigatureSet(randomAccessFile, n + this.ligatureSetOffsets[j]);
        }
    }
    
    public int getFormat() {
        return 1;
    }
}
