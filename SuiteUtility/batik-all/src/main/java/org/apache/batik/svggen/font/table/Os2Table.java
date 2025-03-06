// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Os2Table implements Table
{
    private int version;
    private short xAvgCharWidth;
    private int usWeightClass;
    private int usWidthClass;
    private short fsType;
    private short ySubscriptXSize;
    private short ySubscriptYSize;
    private short ySubscriptXOffset;
    private short ySubscriptYOffset;
    private short ySuperscriptXSize;
    private short ySuperscriptYSize;
    private short ySuperscriptXOffset;
    private short ySuperscriptYOffset;
    private short yStrikeoutSize;
    private short yStrikeoutPosition;
    private short sFamilyClass;
    private Panose panose;
    private int ulUnicodeRange1;
    private int ulUnicodeRange2;
    private int ulUnicodeRange3;
    private int ulUnicodeRange4;
    private int achVendorID;
    private short fsSelection;
    private int usFirstCharIndex;
    private int usLastCharIndex;
    private short sTypoAscender;
    private short sTypoDescender;
    private short sTypoLineGap;
    private int usWinAscent;
    private int usWinDescent;
    private int ulCodePageRange1;
    private int ulCodePageRange2;
    
    protected Os2Table(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(directoryEntry.getOffset());
        this.version = randomAccessFile.readUnsignedShort();
        this.xAvgCharWidth = randomAccessFile.readShort();
        this.usWeightClass = randomAccessFile.readUnsignedShort();
        this.usWidthClass = randomAccessFile.readUnsignedShort();
        this.fsType = randomAccessFile.readShort();
        this.ySubscriptXSize = randomAccessFile.readShort();
        this.ySubscriptYSize = randomAccessFile.readShort();
        this.ySubscriptXOffset = randomAccessFile.readShort();
        this.ySubscriptYOffset = randomAccessFile.readShort();
        this.ySuperscriptXSize = randomAccessFile.readShort();
        this.ySuperscriptYSize = randomAccessFile.readShort();
        this.ySuperscriptXOffset = randomAccessFile.readShort();
        this.ySuperscriptYOffset = randomAccessFile.readShort();
        this.yStrikeoutSize = randomAccessFile.readShort();
        this.yStrikeoutPosition = randomAccessFile.readShort();
        this.sFamilyClass = randomAccessFile.readShort();
        final byte[] b = new byte[10];
        randomAccessFile.read(b);
        this.panose = new Panose(b);
        this.ulUnicodeRange1 = randomAccessFile.readInt();
        this.ulUnicodeRange2 = randomAccessFile.readInt();
        this.ulUnicodeRange3 = randomAccessFile.readInt();
        this.ulUnicodeRange4 = randomAccessFile.readInt();
        this.achVendorID = randomAccessFile.readInt();
        this.fsSelection = randomAccessFile.readShort();
        this.usFirstCharIndex = randomAccessFile.readUnsignedShort();
        this.usLastCharIndex = randomAccessFile.readUnsignedShort();
        this.sTypoAscender = randomAccessFile.readShort();
        this.sTypoDescender = randomAccessFile.readShort();
        this.sTypoLineGap = randomAccessFile.readShort();
        this.usWinAscent = randomAccessFile.readUnsignedShort();
        this.usWinDescent = randomAccessFile.readUnsignedShort();
        this.ulCodePageRange1 = randomAccessFile.readInt();
        this.ulCodePageRange2 = randomAccessFile.readInt();
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public short getAvgCharWidth() {
        return this.xAvgCharWidth;
    }
    
    public int getWeightClass() {
        return this.usWeightClass;
    }
    
    public int getWidthClass() {
        return this.usWidthClass;
    }
    
    public short getLicenseType() {
        return this.fsType;
    }
    
    public short getSubscriptXSize() {
        return this.ySubscriptXSize;
    }
    
    public short getSubscriptYSize() {
        return this.ySubscriptYSize;
    }
    
    public short getSubscriptXOffset() {
        return this.ySubscriptXOffset;
    }
    
    public short getSubscriptYOffset() {
        return this.ySubscriptYOffset;
    }
    
    public short getSuperscriptXSize() {
        return this.ySuperscriptXSize;
    }
    
    public short getSuperscriptYSize() {
        return this.ySuperscriptYSize;
    }
    
    public short getSuperscriptXOffset() {
        return this.ySuperscriptXOffset;
    }
    
    public short getSuperscriptYOffset() {
        return this.ySuperscriptYOffset;
    }
    
    public short getStrikeoutSize() {
        return this.yStrikeoutSize;
    }
    
    public short getStrikeoutPosition() {
        return this.yStrikeoutPosition;
    }
    
    public short getFamilyClass() {
        return this.sFamilyClass;
    }
    
    public Panose getPanose() {
        return this.panose;
    }
    
    public int getUnicodeRange1() {
        return this.ulUnicodeRange1;
    }
    
    public int getUnicodeRange2() {
        return this.ulUnicodeRange2;
    }
    
    public int getUnicodeRange3() {
        return this.ulUnicodeRange3;
    }
    
    public int getUnicodeRange4() {
        return this.ulUnicodeRange4;
    }
    
    public int getVendorID() {
        return this.achVendorID;
    }
    
    public short getSelection() {
        return this.fsSelection;
    }
    
    public int getFirstCharIndex() {
        return this.usFirstCharIndex;
    }
    
    public int getLastCharIndex() {
        return this.usLastCharIndex;
    }
    
    public short getTypoAscender() {
        return this.sTypoAscender;
    }
    
    public short getTypoDescender() {
        return this.sTypoDescender;
    }
    
    public short getTypoLineGap() {
        return this.sTypoLineGap;
    }
    
    public int getWinAscent() {
        return this.usWinAscent;
    }
    
    public int getWinDescent() {
        return this.usWinDescent;
    }
    
    public int getCodePageRange1() {
        return this.ulCodePageRange1;
    }
    
    public int getCodePageRange2() {
        return this.ulCodePageRange2;
    }
    
    public int getType() {
        return 1330851634;
    }
}
