// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LocaTable implements Table
{
    private byte[] buf;
    private int[] offsets;
    private short factor;
    
    protected LocaTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        this.buf = null;
        this.offsets = null;
        this.factor = 0;
        randomAccessFile.seek(directoryEntry.getOffset());
        randomAccessFile.read(this.buf = new byte[directoryEntry.getLength()]);
    }
    
    public void init(final int n, final boolean b) {
        if (this.buf == null) {
            return;
        }
        this.offsets = new int[n + 1];
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.buf);
        if (b) {
            this.factor = 2;
            for (int i = 0; i <= n; ++i) {
                this.offsets[i] = (byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
            }
        }
        else {
            this.factor = 1;
            for (int j = 0; j <= n; ++j) {
                this.offsets[j] = (byteArrayInputStream.read() << 24 | byteArrayInputStream.read() << 16 | byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
            }
        }
        this.buf = null;
    }
    
    public int getOffset(final int n) {
        if (this.offsets == null) {
            return 0;
        }
        return this.offsets[n] * this.factor;
    }
    
    public int getType() {
        return 1819239265;
    }
}
