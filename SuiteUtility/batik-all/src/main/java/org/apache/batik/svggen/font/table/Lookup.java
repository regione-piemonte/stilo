// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Lookup
{
    public static final int IGNORE_BASE_GLYPHS = 2;
    public static final int IGNORE_BASE_LIGATURES = 4;
    public static final int IGNORE_BASE_MARKS = 8;
    public static final int MARK_ATTACHMENT_TYPE = 65280;
    private int type;
    private int flag;
    private int subTableCount;
    private int[] subTableOffsets;
    private LookupSubtable[] subTables;
    
    public Lookup(final LookupSubtableFactory lookupSubtableFactory, final RandomAccessFile randomAccessFile, final int n) throws IOException {
        randomAccessFile.seek(n);
        this.type = randomAccessFile.readUnsignedShort();
        this.flag = randomAccessFile.readUnsignedShort();
        this.subTableCount = randomAccessFile.readUnsignedShort();
        this.subTableOffsets = new int[this.subTableCount];
        this.subTables = new LookupSubtable[this.subTableCount];
        for (int i = 0; i < this.subTableCount; ++i) {
            this.subTableOffsets[i] = randomAccessFile.readUnsignedShort();
        }
        for (int j = 0; j < this.subTableCount; ++j) {
            this.subTables[j] = lookupSubtableFactory.read(this.type, randomAccessFile, n + this.subTableOffsets[j]);
        }
    }
    
    public int getType() {
        return this.type;
    }
    
    public int getSubtableCount() {
        return this.subTableCount;
    }
    
    public LookupSubtable getSubtable(final int n) {
        return this.subTables[n];
    }
}
