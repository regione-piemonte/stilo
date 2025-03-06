// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.png;

import java.io.IOException;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.OutputStream;

class ChunkStream extends OutputStream implements DataOutput
{
    private String type;
    private ByteArrayOutputStream baos;
    private DataOutputStream dos;
    
    ChunkStream(final String type) throws IOException {
        this.type = type;
        this.baos = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.baos);
    }
    
    public void write(final byte[] b) throws IOException {
        this.dos.write(b);
    }
    
    public void write(final byte[] b, final int off, final int len) throws IOException {
        this.dos.write(b, off, len);
    }
    
    public void write(final int b) throws IOException {
        this.dos.write(b);
    }
    
    public void writeBoolean(final boolean v) throws IOException {
        this.dos.writeBoolean(v);
    }
    
    public void writeByte(final int v) throws IOException {
        this.dos.writeByte(v);
    }
    
    public void writeBytes(final String s) throws IOException {
        this.dos.writeBytes(s);
    }
    
    public void writeChar(final int v) throws IOException {
        this.dos.writeChar(v);
    }
    
    public void writeChars(final String s) throws IOException {
        this.dos.writeChars(s);
    }
    
    public void writeDouble(final double v) throws IOException {
        this.dos.writeDouble(v);
    }
    
    public void writeFloat(final float v) throws IOException {
        this.dos.writeFloat(v);
    }
    
    public void writeInt(final int v) throws IOException {
        this.dos.writeInt(v);
    }
    
    public void writeLong(final long v) throws IOException {
        this.dos.writeLong(v);
    }
    
    public void writeShort(final int v) throws IOException {
        this.dos.writeShort(v);
    }
    
    public void writeUTF(final String str) throws IOException {
        this.dos.writeUTF(str);
    }
    
    public void writeToStream(final DataOutputStream dataOutputStream) throws IOException {
        final byte[] b = { (byte)this.type.charAt(0), (byte)this.type.charAt(1), (byte)this.type.charAt(2), (byte)this.type.charAt(3) };
        this.dos.flush();
        this.baos.flush();
        final byte[] byteArray = this.baos.toByteArray();
        final int length = byteArray.length;
        dataOutputStream.writeInt(length);
        dataOutputStream.write(b);
        dataOutputStream.write(byteArray, 0, length);
        dataOutputStream.writeInt(~CRC.updateCRC(CRC.updateCRC(-1, b, 0, 4), byteArray, 0, length));
    }
    
    public void close() throws IOException {
        if (this.baos != null) {
            this.baos.close();
            this.baos = null;
        }
        if (this.dos != null) {
            this.dos.close();
            this.dos = null;
        }
    }
}
