// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CmapFormat4 extends CmapFormat
{
    public int language;
    private int segCountX2;
    private int searchRange;
    private int entrySelector;
    private int rangeShift;
    private int[] endCode;
    private int[] startCode;
    private int[] idDelta;
    private int[] idRangeOffset;
    private int[] glyphIdArray;
    private int segCount;
    private int first;
    private int last;
    
    protected CmapFormat4(final RandomAccessFile randomAccessFile) throws IOException {
        super(randomAccessFile);
        this.format = 4;
        this.segCountX2 = randomAccessFile.readUnsignedShort();
        this.segCount = this.segCountX2 / 2;
        this.endCode = new int[this.segCount];
        this.startCode = new int[this.segCount];
        this.idDelta = new int[this.segCount];
        this.idRangeOffset = new int[this.segCount];
        this.searchRange = randomAccessFile.readUnsignedShort();
        this.entrySelector = randomAccessFile.readUnsignedShort();
        this.rangeShift = randomAccessFile.readUnsignedShort();
        this.last = -1;
        for (int i = 0; i < this.segCount; ++i) {
            this.endCode[i] = randomAccessFile.readUnsignedShort();
            if (this.endCode[i] > this.last) {
                this.last = this.endCode[i];
            }
        }
        randomAccessFile.readUnsignedShort();
        for (int j = 0; j < this.segCount; ++j) {
            this.startCode[j] = randomAccessFile.readUnsignedShort();
            if (j == 0 || this.startCode[j] < this.first) {
                this.first = this.startCode[j];
            }
        }
        for (int k = 0; k < this.segCount; ++k) {
            this.idDelta[k] = randomAccessFile.readUnsignedShort();
        }
        for (int l = 0; l < this.segCount; ++l) {
            this.idRangeOffset[l] = randomAccessFile.readUnsignedShort();
        }
        final int n = (this.length - 16 - this.segCount * 8) / 2;
        this.glyphIdArray = new int[n];
        for (int n2 = 0; n2 < n; ++n2) {
            this.glyphIdArray[n2] = randomAccessFile.readUnsignedShort();
        }
    }
    
    public int getFirst() {
        return this.first;
    }
    
    public int getLast() {
        return this.last;
    }
    
    public int mapCharCode(final int n) {
        try {
            if (n < 0 || n >= 65534) {
                return 0;
            }
            int i = 0;
            while (i < this.segCount) {
                if (this.endCode[i] >= n) {
                    if (this.startCode[i] > n) {
                        break;
                    }
                    if (this.idRangeOffset[i] > 0) {
                        return this.glyphIdArray[this.idRangeOffset[i] / 2 + (n - this.startCode[i]) - (this.segCount - i)];
                    }
                    return (this.idDelta[i] + n) % 65536;
                }
                else {
                    ++i;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("error: Array out of bounds - " + ex.getMessage());
        }
        return 0;
    }
    
    public String toString() {
        return new StringBuffer(80).append(super.toString()).append(", segCountX2: ").append(this.segCountX2).append(", searchRange: ").append(this.searchRange).append(", entrySelector: ").append(this.entrySelector).append(", rangeShift: ").append(this.rangeShift).append(", endCode: ").append(intToStr(this.endCode)).append(", startCode: ").append(intToStr(this.startCode)).append(", idDelta: ").append(intToStr(this.idDelta)).append(", idRangeOffset: ").append(intToStr(this.idRangeOffset)).toString();
    }
    
    private static String intToStr(final int[] array) {
        final int length = array.length;
        final StringBuffer sb = new StringBuffer(length * 8);
        sb.append('[');
        for (int i = 0; i < length; ++i) {
            sb.append(array[i]);
            if (i < length - 1) {
                sb.append(',');
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
