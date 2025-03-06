// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class KernSubtable
{
    protected KernSubtable() {
    }
    
    public abstract int getKerningPairCount();
    
    public abstract KerningPair getKerningPair(final int p0);
    
    public static KernSubtable read(final RandomAccessFile randomAccessFile) throws IOException {
        KernSubtable kernSubtable = null;
        randomAccessFile.readUnsignedShort();
        randomAccessFile.readUnsignedShort();
        switch (randomAccessFile.readUnsignedShort() >> 8) {
            case 0: {
                kernSubtable = new KernSubtableFormat0(randomAccessFile);
                break;
            }
            case 2: {
                kernSubtable = new KernSubtableFormat2(randomAccessFile);
                break;
            }
        }
        return kernSubtable;
    }
}
