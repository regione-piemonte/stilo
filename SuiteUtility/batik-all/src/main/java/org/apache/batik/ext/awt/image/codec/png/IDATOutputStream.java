// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.png;

import java.io.IOException;
import java.io.OutputStream;
import java.io.FilterOutputStream;

class IDATOutputStream extends FilterOutputStream
{
    private static final byte[] typeSignature;
    private int bytesWritten;
    private int segmentLength;
    byte[] buffer;
    
    public IDATOutputStream(final OutputStream out, final int segmentLength) {
        super(out);
        this.bytesWritten = 0;
        this.segmentLength = segmentLength;
        this.buffer = new byte[segmentLength];
    }
    
    public void close() throws IOException {
        this.flush();
    }
    
    private void writeInt(final int n) throws IOException {
        this.out.write(n >> 24);
        this.out.write(n >> 16 & 0xFF);
        this.out.write(n >> 8 & 0xFF);
        this.out.write(n & 0xFF);
    }
    
    public void flush() throws IOException {
        this.writeInt(this.bytesWritten);
        this.out.write(IDATOutputStream.typeSignature);
        this.out.write(this.buffer, 0, this.bytesWritten);
        this.writeInt(~CRC.updateCRC(CRC.updateCRC(-1, IDATOutputStream.typeSignature, 0, 4), this.buffer, 0, this.bytesWritten));
        this.bytesWritten = 0;
    }
    
    public void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    public void write(final byte[] array, int n, int i) throws IOException {
        while (i > 0) {
            final int min = Math.min(this.segmentLength - this.bytesWritten, i);
            System.arraycopy(array, n, this.buffer, this.bytesWritten, min);
            n += min;
            i -= min;
            this.bytesWritten += min;
            if (this.bytesWritten == this.segmentLength) {
                this.flush();
            }
        }
    }
    
    public void write(final int n) throws IOException {
        this.buffer[this.bytesWritten++] = (byte)n;
        if (this.bytesWritten == this.segmentLength) {
            this.flush();
        }
    }
    
    static {
        typeSignature = new byte[] { 73, 68, 65, 84 };
    }
}
