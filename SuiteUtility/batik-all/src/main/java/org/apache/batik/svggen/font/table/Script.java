// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Script
{
    private int defaultLangSysOffset;
    private int langSysCount;
    private LangSysRecord[] langSysRecords;
    private LangSys defaultLangSys;
    private LangSys[] langSys;
    
    protected Script(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        randomAccessFile.seek(n);
        this.defaultLangSysOffset = randomAccessFile.readUnsignedShort();
        this.langSysCount = randomAccessFile.readUnsignedShort();
        if (this.langSysCount > 0) {
            this.langSysRecords = new LangSysRecord[this.langSysCount];
            for (int i = 0; i < this.langSysCount; ++i) {
                this.langSysRecords[i] = new LangSysRecord(randomAccessFile);
            }
        }
        if (this.langSysCount > 0) {
            this.langSys = new LangSys[this.langSysCount];
            for (int j = 0; j < this.langSysCount; ++j) {
                randomAccessFile.seek(n + this.langSysRecords[j].getOffset());
                this.langSys[j] = new LangSys(randomAccessFile);
            }
        }
        if (this.defaultLangSysOffset > 0) {
            randomAccessFile.seek(n + this.defaultLangSysOffset);
            this.defaultLangSys = new LangSys(randomAccessFile);
        }
    }
    
    public LangSys getDefaultLangSys() {
        return this.defaultLangSys;
    }
}
