// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MaxpTable implements Table
{
    private int versionNumber;
    private int numGlyphs;
    private int maxPoints;
    private int maxContours;
    private int maxCompositePoints;
    private int maxCompositeContours;
    private int maxZones;
    private int maxTwilightPoints;
    private int maxStorage;
    private int maxFunctionDefs;
    private int maxInstructionDefs;
    private int maxStackElements;
    private int maxSizeOfInstructions;
    private int maxComponentElements;
    private int maxComponentDepth;
    
    protected MaxpTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(directoryEntry.getOffset());
        this.versionNumber = randomAccessFile.readInt();
        this.numGlyphs = randomAccessFile.readUnsignedShort();
        this.maxPoints = randomAccessFile.readUnsignedShort();
        this.maxContours = randomAccessFile.readUnsignedShort();
        this.maxCompositePoints = randomAccessFile.readUnsignedShort();
        this.maxCompositeContours = randomAccessFile.readUnsignedShort();
        this.maxZones = randomAccessFile.readUnsignedShort();
        this.maxTwilightPoints = randomAccessFile.readUnsignedShort();
        this.maxStorage = randomAccessFile.readUnsignedShort();
        this.maxFunctionDefs = randomAccessFile.readUnsignedShort();
        this.maxInstructionDefs = randomAccessFile.readUnsignedShort();
        this.maxStackElements = randomAccessFile.readUnsignedShort();
        this.maxSizeOfInstructions = randomAccessFile.readUnsignedShort();
        this.maxComponentElements = randomAccessFile.readUnsignedShort();
        this.maxComponentDepth = randomAccessFile.readUnsignedShort();
    }
    
    public int getMaxComponentDepth() {
        return this.maxComponentDepth;
    }
    
    public int getMaxComponentElements() {
        return this.maxComponentElements;
    }
    
    public int getMaxCompositeContours() {
        return this.maxCompositeContours;
    }
    
    public int getMaxCompositePoints() {
        return this.maxCompositePoints;
    }
    
    public int getMaxContours() {
        return this.maxContours;
    }
    
    public int getMaxFunctionDefs() {
        return this.maxFunctionDefs;
    }
    
    public int getMaxInstructionDefs() {
        return this.maxInstructionDefs;
    }
    
    public int getMaxPoints() {
        return this.maxPoints;
    }
    
    public int getMaxSizeOfInstructions() {
        return this.maxSizeOfInstructions;
    }
    
    public int getMaxStackElements() {
        return this.maxStackElements;
    }
    
    public int getMaxStorage() {
        return this.maxStorage;
    }
    
    public int getMaxTwilightPoints() {
        return this.maxTwilightPoints;
    }
    
    public int getMaxZones() {
        return this.maxZones;
    }
    
    public int getNumGlyphs() {
        return this.numGlyphs;
    }
    
    public int getType() {
        return 1835104368;
    }
}
