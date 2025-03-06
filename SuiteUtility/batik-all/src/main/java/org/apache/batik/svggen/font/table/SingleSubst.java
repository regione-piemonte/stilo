// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class SingleSubst extends LookupSubtable
{
    public abstract int getFormat();
    
    public abstract int substitute(final int p0);
    
    public static SingleSubst read(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        SingleSubst singleSubst = null;
        randomAccessFile.seek(n);
        final int unsignedShort = randomAccessFile.readUnsignedShort();
        if (unsignedShort == 1) {
            singleSubst = new SingleSubstFormat1(randomAccessFile, n);
        }
        else if (unsignedShort == 2) {
            singleSubst = new SingleSubstFormat2(randomAccessFile, n);
        }
        return singleSubst;
    }
}
