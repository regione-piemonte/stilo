// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class HheaTable implements Table
{
    private int version;
    private short ascender;
    private short descender;
    private short lineGap;
    private short advanceWidthMax;
    private short minLeftSideBearing;
    private short minRightSideBearing;
    private short xMaxExtent;
    private short caretSlopeRise;
    private short caretSlopeRun;
    private short metricDataFormat;
    private int numberOfHMetrics;
    
    protected HheaTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(directoryEntry.getOffset());
        this.version = randomAccessFile.readInt();
        this.ascender = randomAccessFile.readShort();
        this.descender = randomAccessFile.readShort();
        this.lineGap = randomAccessFile.readShort();
        this.advanceWidthMax = randomAccessFile.readShort();
        this.minLeftSideBearing = randomAccessFile.readShort();
        this.minRightSideBearing = randomAccessFile.readShort();
        this.xMaxExtent = randomAccessFile.readShort();
        this.caretSlopeRise = randomAccessFile.readShort();
        this.caretSlopeRun = randomAccessFile.readShort();
        for (int i = 0; i < 5; ++i) {
            randomAccessFile.readShort();
        }
        this.metricDataFormat = randomAccessFile.readShort();
        this.numberOfHMetrics = randomAccessFile.readUnsignedShort();
    }
    
    public short getAdvanceWidthMax() {
        return this.advanceWidthMax;
    }
    
    public short getAscender() {
        return this.ascender;
    }
    
    public short getCaretSlopeRise() {
        return this.caretSlopeRise;
    }
    
    public short getCaretSlopeRun() {
        return this.caretSlopeRun;
    }
    
    public short getDescender() {
        return this.descender;
    }
    
    public short getLineGap() {
        return this.lineGap;
    }
    
    public short getMetricDataFormat() {
        return this.metricDataFormat;
    }
    
    public short getMinLeftSideBearing() {
        return this.minLeftSideBearing;
    }
    
    public short getMinRightSideBearing() {
        return this.minRightSideBearing;
    }
    
    public int getNumberOfHMetrics() {
        return this.numberOfHMetrics;
    }
    
    public int getType() {
        return 1751672161;
    }
    
    public short getXMaxExtent() {
        return this.xMaxExtent;
    }
}
