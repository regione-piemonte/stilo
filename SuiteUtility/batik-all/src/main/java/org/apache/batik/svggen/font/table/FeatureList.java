// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FeatureList
{
    private int featureCount;
    private FeatureRecord[] featureRecords;
    private Feature[] features;
    
    public FeatureList(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        randomAccessFile.seek(n);
        this.featureCount = randomAccessFile.readUnsignedShort();
        this.featureRecords = new FeatureRecord[this.featureCount];
        this.features = new Feature[this.featureCount];
        for (int i = 0; i < this.featureCount; ++i) {
            this.featureRecords[i] = new FeatureRecord(randomAccessFile);
        }
        for (int j = 0; j < this.featureCount; ++j) {
            this.features[j] = new Feature(randomAccessFile, n + this.featureRecords[j].getOffset());
        }
    }
    
    public Feature findFeature(final LangSys langSys, final String s) {
        if (s.length() != 4) {
            return null;
        }
        final int n = s.charAt(0) << 24 | s.charAt(1) << 16 | s.charAt(2) << 8 | s.charAt(3);
        for (int i = 0; i < this.featureCount; ++i) {
            if (this.featureRecords[i].getTag() == n && langSys.isFeatureIndexed(i)) {
                return this.features[i];
            }
        }
        return null;
    }
}
