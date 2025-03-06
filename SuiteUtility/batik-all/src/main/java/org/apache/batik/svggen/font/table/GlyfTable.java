// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class GlyfTable implements Table
{
    private byte[] buf;
    private GlyfDescript[] descript;
    
    protected GlyfTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        this.buf = null;
        randomAccessFile.seek(directoryEntry.getOffset());
        randomAccessFile.read(this.buf = new byte[directoryEntry.getLength()]);
    }
    
    public void init(final int n, final LocaTable locaTable) {
        if (this.buf == null) {
            return;
        }
        this.descript = new GlyfDescript[n];
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.buf);
        for (int i = 0; i < n; ++i) {
            if (locaTable.getOffset(i + 1) - locaTable.getOffset(i) > 0) {
                byteArrayInputStream.reset();
                byteArrayInputStream.skip(locaTable.getOffset(i));
                final short n2 = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
                if (n2 >= 0) {
                    this.descript[i] = new GlyfSimpleDescript(this, n2, byteArrayInputStream);
                }
                else {
                    this.descript[i] = new GlyfCompositeDescript(this, byteArrayInputStream);
                }
            }
        }
        this.buf = null;
        for (int j = 0; j < n; ++j) {
            if (this.descript[j] != null) {
                this.descript[j].resolve();
            }
        }
    }
    
    public GlyfDescript getDescription(final int n) {
        return this.descript[n];
    }
    
    public int getType() {
        return 1735162214;
    }
}
