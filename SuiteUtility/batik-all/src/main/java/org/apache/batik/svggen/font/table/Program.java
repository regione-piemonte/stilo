// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class Program
{
    private short[] instructions;
    
    public short[] getInstructions() {
        return this.instructions;
    }
    
    protected void readInstructions(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        this.instructions = new short[n];
        for (int i = 0; i < n; ++i) {
            this.instructions[i] = (short)randomAccessFile.readUnsignedByte();
        }
    }
    
    protected void readInstructions(final ByteArrayInputStream byteArrayInputStream, final int n) {
        this.instructions = new short[n];
        for (int i = 0; i < n; ++i) {
            this.instructions[i] = (short)byteArrayInputStream.read();
        }
    }
}
