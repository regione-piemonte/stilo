// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class NameRecord
{
    private short platformId;
    private short encodingId;
    private short languageId;
    private short nameId;
    private short stringLength;
    private short stringOffset;
    private String record;
    
    protected NameRecord(final RandomAccessFile randomAccessFile) throws IOException {
        this.platformId = randomAccessFile.readShort();
        this.encodingId = randomAccessFile.readShort();
        this.languageId = randomAccessFile.readShort();
        this.nameId = randomAccessFile.readShort();
        this.stringLength = randomAccessFile.readShort();
        this.stringOffset = randomAccessFile.readShort();
    }
    
    public short getEncodingId() {
        return this.encodingId;
    }
    
    public short getLanguageId() {
        return this.languageId;
    }
    
    public short getNameId() {
        return this.nameId;
    }
    
    public short getPlatformId() {
        return this.platformId;
    }
    
    public String getRecordString() {
        return this.record;
    }
    
    protected void loadString(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        final StringBuffer sb = new StringBuffer();
        randomAccessFile.seek(n + this.stringOffset);
        if (this.platformId == 0) {
            for (int i = 0; i < this.stringLength / 2; ++i) {
                sb.append(randomAccessFile.readChar());
            }
        }
        else if (this.platformId == 1) {
            for (short n2 = 0; n2 < this.stringLength; ++n2) {
                sb.append((char)randomAccessFile.readByte());
            }
        }
        else if (this.platformId == 2) {
            for (short n3 = 0; n3 < this.stringLength; ++n3) {
                sb.append((char)randomAccessFile.readByte());
            }
        }
        else if (this.platformId == 3) {
            for (int j = 0; j < this.stringLength / 2; ++j) {
                sb.append(randomAccessFile.readChar());
            }
        }
        this.record = sb.toString();
    }
}
