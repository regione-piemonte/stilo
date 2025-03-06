// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class LigatureSubst extends LookupSubtable
{
    public static LigatureSubst read(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        LigatureSubst ligatureSubst = null;
        randomAccessFile.seek(n);
        if (randomAccessFile.readUnsignedShort() == 1) {
            ligatureSubst = new LigatureSubstFormat1(randomAccessFile, n);
        }
        return ligatureSubst;
    }
}
