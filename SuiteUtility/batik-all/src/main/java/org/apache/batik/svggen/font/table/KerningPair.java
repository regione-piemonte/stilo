// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class KerningPair
{
    private int left;
    private int right;
    private short value;
    
    protected KerningPair(final RandomAccessFile randomAccessFile) throws IOException {
        this.left = randomAccessFile.readUnsignedShort();
        this.right = randomAccessFile.readUnsignedShort();
        this.value = randomAccessFile.readShort();
    }
    
    public int getLeft() {
        return this.left;
    }
    
    public int getRight() {
        return this.right;
    }
    
    public short getValue() {
        return this.value;
    }
}
