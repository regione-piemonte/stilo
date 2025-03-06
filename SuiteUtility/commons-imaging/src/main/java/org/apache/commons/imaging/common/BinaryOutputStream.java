// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.io.IOException;
import java.nio.ByteOrder;
import java.io.OutputStream;

public class BinaryOutputStream extends OutputStream
{
    private final OutputStream os;
    private ByteOrder byteOrder;
    private int count;
    
    public BinaryOutputStream(final OutputStream os, final ByteOrder byteOrder) {
        this.byteOrder = ByteOrder.BIG_ENDIAN;
        this.byteOrder = byteOrder;
        this.os = os;
    }
    
    public BinaryOutputStream(final OutputStream os) {
        this.byteOrder = ByteOrder.BIG_ENDIAN;
        this.os = os;
    }
    
    protected void setByteOrder(final ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }
    
    public ByteOrder getByteOrder() {
        return this.byteOrder;
    }
    
    @Override
    public void write(final int i) throws IOException {
        this.os.write(i);
        ++this.count;
    }
    
    @Override
    public final void write(final byte[] bytes) throws IOException {
        this.os.write(bytes, 0, bytes.length);
        this.count += bytes.length;
    }
    
    @Override
    public final void write(final byte[] bytes, final int offset, final int length) throws IOException {
        this.os.write(bytes, offset, length);
        this.count += length;
    }
    
    @Override
    public void flush() throws IOException {
        this.os.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.os.close();
    }
    
    public int getByteCount() {
        return this.count;
    }
    
    public final void write4Bytes(final int value) throws IOException {
        if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
            this.write(0xFF & value >> 24);
            this.write(0xFF & value >> 16);
            this.write(0xFF & value >> 8);
            this.write(0xFF & value);
        }
        else {
            this.write(0xFF & value);
            this.write(0xFF & value >> 8);
            this.write(0xFF & value >> 16);
            this.write(0xFF & value >> 24);
        }
    }
    
    public final void write3Bytes(final int value) throws IOException {
        if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
            this.write(0xFF & value >> 16);
            this.write(0xFF & value >> 8);
            this.write(0xFF & value);
        }
        else {
            this.write(0xFF & value);
            this.write(0xFF & value >> 8);
            this.write(0xFF & value >> 16);
        }
    }
    
    public final void write2Bytes(final int value) throws IOException {
        if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
            this.write(0xFF & value >> 8);
            this.write(0xFF & value);
        }
        else {
            this.write(0xFF & value);
            this.write(0xFF & value >> 8);
        }
    }
}
