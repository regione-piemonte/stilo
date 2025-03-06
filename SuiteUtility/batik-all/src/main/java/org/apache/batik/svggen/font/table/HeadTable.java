// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class HeadTable implements Table
{
    private int versionNumber;
    private int fontRevision;
    private int checkSumAdjustment;
    private int magicNumber;
    private short flags;
    private short unitsPerEm;
    private long created;
    private long modified;
    private short xMin;
    private short yMin;
    private short xMax;
    private short yMax;
    private short macStyle;
    private short lowestRecPPEM;
    private short fontDirectionHint;
    private short indexToLocFormat;
    private short glyphDataFormat;
    
    protected HeadTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(directoryEntry.getOffset());
        this.versionNumber = randomAccessFile.readInt();
        this.fontRevision = randomAccessFile.readInt();
        this.checkSumAdjustment = randomAccessFile.readInt();
        this.magicNumber = randomAccessFile.readInt();
        this.flags = randomAccessFile.readShort();
        this.unitsPerEm = randomAccessFile.readShort();
        this.created = randomAccessFile.readLong();
        this.modified = randomAccessFile.readLong();
        this.xMin = randomAccessFile.readShort();
        this.yMin = randomAccessFile.readShort();
        this.xMax = randomAccessFile.readShort();
        this.yMax = randomAccessFile.readShort();
        this.macStyle = randomAccessFile.readShort();
        this.lowestRecPPEM = randomAccessFile.readShort();
        this.fontDirectionHint = randomAccessFile.readShort();
        this.indexToLocFormat = randomAccessFile.readShort();
        this.glyphDataFormat = randomAccessFile.readShort();
    }
    
    public int getCheckSumAdjustment() {
        return this.checkSumAdjustment;
    }
    
    public long getCreated() {
        return this.created;
    }
    
    public short getFlags() {
        return this.flags;
    }
    
    public short getFontDirectionHint() {
        return this.fontDirectionHint;
    }
    
    public int getFontRevision() {
        return this.fontRevision;
    }
    
    public short getGlyphDataFormat() {
        return this.glyphDataFormat;
    }
    
    public short getIndexToLocFormat() {
        return this.indexToLocFormat;
    }
    
    public short getLowestRecPPEM() {
        return this.lowestRecPPEM;
    }
    
    public short getMacStyle() {
        return this.macStyle;
    }
    
    public long getModified() {
        return this.modified;
    }
    
    public int getType() {
        return 1751474532;
    }
    
    public short getUnitsPerEm() {
        return this.unitsPerEm;
    }
    
    public int getVersionNumber() {
        return this.versionNumber;
    }
    
    public short getXMax() {
        return this.xMax;
    }
    
    public short getXMin() {
        return this.xMin;
    }
    
    public short getYMax() {
        return this.yMax;
    }
    
    public short getYMin() {
        return this.yMin;
    }
    
    public String toString() {
        return "head\n\tversionNumber: " + this.versionNumber + "\n\tfontRevision: " + this.fontRevision + "\n\tcheckSumAdjustment: " + this.checkSumAdjustment + "\n\tmagicNumber: " + this.magicNumber + "\n\tflags: " + this.flags + "\n\tunitsPerEm: " + this.unitsPerEm + "\n\tcreated: " + this.created + "\n\tmodified: " + this.modified + "\n\txMin: " + this.xMin + ", yMin: " + this.yMin + "\n\txMax: " + this.xMax + ", yMax: " + this.yMax + "\n\tmacStyle: " + this.macStyle + "\n\tlowestRecPPEM: " + this.lowestRecPPEM + "\n\tfontDirectionHint: " + this.fontDirectionHint + "\n\tindexToLocFormat: " + this.indexToLocFormat + "\n\tglyphDataFormat: " + this.glyphDataFormat;
    }
}
