// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class LookupList
{
    private int lookupCount;
    private int[] lookupOffsets;
    private Lookup[] lookups;
    
    public LookupList(final RandomAccessFile randomAccessFile, final int n, final LookupSubtableFactory lookupSubtableFactory) throws IOException {
        randomAccessFile.seek(n);
        this.lookupCount = randomAccessFile.readUnsignedShort();
        this.lookupOffsets = new int[this.lookupCount];
        this.lookups = new Lookup[this.lookupCount];
        for (int i = 0; i < this.lookupCount; ++i) {
            this.lookupOffsets[i] = randomAccessFile.readUnsignedShort();
        }
        for (int j = 0; j < this.lookupCount; ++j) {
            this.lookups[j] = new Lookup(lookupSubtableFactory, randomAccessFile, n + this.lookupOffsets[j]);
        }
    }
    
    public Lookup getLookup(final Feature feature, final int n) {
        if (feature.getLookupCount() > n) {
            return this.lookups[feature.getLookupListIndex(n)];
        }
        return null;
    }
}
