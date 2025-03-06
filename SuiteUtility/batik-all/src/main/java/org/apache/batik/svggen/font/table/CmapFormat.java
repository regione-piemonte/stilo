// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class CmapFormat
{
    protected int format;
    protected int length;
    protected int version;
    
    protected CmapFormat(final RandomAccessFile randomAccessFile) throws IOException {
        this.length = randomAccessFile.readUnsignedShort();
        this.version = randomAccessFile.readUnsignedShort();
    }
    
    protected static CmapFormat create(final int n, final RandomAccessFile randomAccessFile) throws IOException {
        switch (n) {
            case 0: {
                return new CmapFormat0(randomAccessFile);
            }
            case 2: {
                return new CmapFormat2(randomAccessFile);
            }
            case 4: {
                return new CmapFormat4(randomAccessFile);
            }
            case 6: {
                return new CmapFormat6(randomAccessFile);
            }
            default: {
                return null;
            }
        }
    }
    
    public int getFormat() {
        return this.format;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public abstract int mapCharCode(final int p0);
    
    public abstract int getFirst();
    
    public abstract int getLast();
    
    public String toString() {
        return "format: " + this.format + ", length: " + this.length + ", version: " + this.version;
    }
}
