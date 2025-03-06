// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class ClassDef
{
    public abstract int getFormat();
    
    protected static ClassDef read(final RandomAccessFile randomAccessFile) throws IOException {
        ClassDef classDef = null;
        final int unsignedShort = randomAccessFile.readUnsignedShort();
        if (unsignedShort == 1) {
            classDef = new ClassDefFormat1(randomAccessFile);
        }
        else if (unsignedShort == 2) {
            classDef = new ClassDefFormat2(randomAccessFile);
        }
        return classDef;
    }
}
