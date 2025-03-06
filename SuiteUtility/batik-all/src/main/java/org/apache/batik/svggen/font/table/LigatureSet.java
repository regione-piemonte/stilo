// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class LigatureSet
{
    private int ligatureCount;
    private int[] ligatureOffsets;
    private Ligature[] ligatures;
    
    public LigatureSet(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        randomAccessFile.seek(n);
        this.ligatureCount = randomAccessFile.readUnsignedShort();
        this.ligatureOffsets = new int[this.ligatureCount];
        this.ligatures = new Ligature[this.ligatureCount];
        for (int i = 0; i < this.ligatureCount; ++i) {
            this.ligatureOffsets[i] = randomAccessFile.readUnsignedShort();
        }
        for (int j = 0; j < this.ligatureCount; ++j) {
            randomAccessFile.seek(n + this.ligatureOffsets[j]);
            this.ligatures[j] = new Ligature(randomAccessFile);
        }
    }
}
