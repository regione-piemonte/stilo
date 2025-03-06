// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ClassDefFormat1 extends ClassDef
{
    private int startGlyph;
    private int glyphCount;
    private int[] classValues;
    
    public ClassDefFormat1(final RandomAccessFile randomAccessFile) throws IOException {
        this.startGlyph = randomAccessFile.readUnsignedShort();
        this.glyphCount = randomAccessFile.readUnsignedShort();
        this.classValues = new int[this.glyphCount];
        for (int i = 0; i < this.glyphCount; ++i) {
            this.classValues[i] = randomAccessFile.readUnsignedShort();
        }
    }
    
    public int getFormat() {
        return 1;
    }
}
