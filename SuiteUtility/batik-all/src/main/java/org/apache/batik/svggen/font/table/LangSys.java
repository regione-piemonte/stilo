// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class LangSys
{
    private int lookupOrder;
    private int reqFeatureIndex;
    private int featureCount;
    private int[] featureIndex;
    
    protected LangSys(final RandomAccessFile randomAccessFile) throws IOException {
        this.lookupOrder = randomAccessFile.readUnsignedShort();
        this.reqFeatureIndex = randomAccessFile.readUnsignedShort();
        this.featureCount = randomAccessFile.readUnsignedShort();
        this.featureIndex = new int[this.featureCount];
        for (int i = 0; i < this.featureCount; ++i) {
            this.featureIndex[i] = randomAccessFile.readUnsignedShort();
        }
    }
    
    protected boolean isFeatureIndexed(final int n) {
        for (int i = 0; i < this.featureCount; ++i) {
            if (this.featureIndex[i] == n) {
                return true;
            }
        }
        return false;
    }
}
