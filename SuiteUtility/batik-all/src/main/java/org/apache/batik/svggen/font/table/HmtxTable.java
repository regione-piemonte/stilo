// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class HmtxTable implements Table
{
    private byte[] buf;
    private int[] hMetrics;
    private short[] leftSideBearing;
    
    protected HmtxTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        this.buf = null;
        this.hMetrics = null;
        this.leftSideBearing = null;
        randomAccessFile.seek(directoryEntry.getOffset());
        randomAccessFile.read(this.buf = new byte[directoryEntry.getLength()]);
    }
    
    public void init(final int n, final int n2) {
        if (this.buf == null) {
            return;
        }
        this.hMetrics = new int[n];
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.buf);
        for (int i = 0; i < n; ++i) {
            this.hMetrics[i] = (byteArrayInputStream.read() << 24 | byteArrayInputStream.read() << 16 | byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
        }
        if (n2 > 0) {
            this.leftSideBearing = new short[n2];
            for (int j = 0; j < n2; ++j) {
                this.leftSideBearing[j] = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
            }
        }
        this.buf = null;
    }
    
    public int getAdvanceWidth(final int n) {
        if (this.hMetrics == null) {
            return 0;
        }
        if (n < this.hMetrics.length) {
            return this.hMetrics[n] >> 16;
        }
        return this.hMetrics[this.hMetrics.length - 1] >> 16;
    }
    
    public short getLeftSideBearing(final int n) {
        if (this.hMetrics == null) {
            return 0;
        }
        if (n < this.hMetrics.length) {
            return (short)(this.hMetrics[n] & 0xFFFF);
        }
        return this.leftSideBearing[n - this.hMetrics.length];
    }
    
    public int getType() {
        return 1752003704;
    }
}
