// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Ligature
{
    private int ligGlyph;
    private int compCount;
    private int[] components;
    
    public Ligature(final RandomAccessFile randomAccessFile) throws IOException {
        this.ligGlyph = randomAccessFile.readUnsignedShort();
        this.compCount = randomAccessFile.readUnsignedShort();
        this.components = new int[this.compCount - 1];
        for (int i = 0; i < this.compCount - 1; ++i) {
            this.components[i] = randomAccessFile.readUnsignedShort();
        }
    }
    
    public int getGlyphCount() {
        return this.compCount;
    }
    
    public int getGlyphId(final int n) {
        return (n == 0) ? this.ligGlyph : this.components[n - 1];
    }
}
