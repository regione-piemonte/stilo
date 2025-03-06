// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CmapIndexEntry
{
    private int platformId;
    private int encodingId;
    private int offset;
    
    protected CmapIndexEntry(final RandomAccessFile randomAccessFile) throws IOException {
        this.platformId = randomAccessFile.readUnsignedShort();
        this.encodingId = randomAccessFile.readUnsignedShort();
        this.offset = randomAccessFile.readInt();
    }
    
    public int getEncodingId() {
        return this.encodingId;
    }
    
    public int getOffset() {
        return this.offset;
    }
    
    public int getPlatformId() {
        return this.platformId;
    }
    
    public String toString() {
        String str = "";
        String str2 = null;
        switch (this.platformId) {
            case 1: {
                str2 = " (Macintosh)";
                break;
            }
            case 3: {
                str2 = " (Windows)";
                break;
            }
            default: {
                str2 = "";
                break;
            }
        }
        if (this.platformId == 3) {
            switch (this.encodingId) {
                case 0: {
                    str = " (Symbol)";
                    break;
                }
                case 1: {
                    str = " (Unicode)";
                    break;
                }
                case 2: {
                    str = " (ShiftJIS)";
                    break;
                }
                case 3: {
                    str = " (Big5)";
                    break;
                }
                case 4: {
                    str = " (PRC)";
                    break;
                }
                case 5: {
                    str = " (Wansung)";
                    break;
                }
                case 6: {
                    str = " (Johab)";
                    break;
                }
                default: {
                    str = "";
                    break;
                }
            }
        }
        return "platform id: " + this.platformId + str2 + ", encoding id: " + this.encodingId + str + ", offset: " + this.offset;
    }
}
