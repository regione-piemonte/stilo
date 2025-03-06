// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Feature
{
    private int featureParams;
    private int lookupCount;
    private int[] lookupListIndex;
    
    protected Feature(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        randomAccessFile.seek(n);
        this.featureParams = randomAccessFile.readUnsignedShort();
        this.lookupCount = randomAccessFile.readUnsignedShort();
        this.lookupListIndex = new int[this.lookupCount];
        for (int i = 0; i < this.lookupCount; ++i) {
            this.lookupListIndex[i] = randomAccessFile.readUnsignedShort();
        }
    }
    
    public int getLookupCount() {
        return this.lookupCount;
    }
    
    public int getLookupListIndex(final int n) {
        return this.lookupListIndex[n];
    }
}
