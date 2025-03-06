// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import java.awt.Rectangle;

public class Bitmap
{
    private final int height;
    private final int width;
    private final int rowStride;
    private byte[] bitmap;
    
    public Bitmap(final int width, final int height) {
        this.height = height;
        this.width = width;
        this.rowStride = width + 7 >> 3;
        this.bitmap = new byte[this.height * this.rowStride];
    }
    
    public byte getPixel(final int n, final int n2) {
        return (byte)(this.getByte(this.getByteIndex(n, n2)) >> 7 - this.getBitOffset(n) & 0x1);
    }
    
    public void setPixel(final int n, final int n2, final byte b) {
        final int byteIndex = this.getByteIndex(n, n2);
        this.bitmap[byteIndex] |= (byte)(b << 7 - this.getBitOffset(n));
    }
    
    public int getByteIndex(final int n, final int n2) {
        return n2 * this.rowStride + (n >> 3);
    }
    
    public byte[] getByteArray() {
        return this.bitmap;
    }
    
    public byte getByte(final int n) {
        return this.bitmap[n];
    }
    
    public void setByte(final int n, final byte b) {
        this.bitmap[n] = b;
    }
    
    public int getByteAsInteger(final int n) {
        return this.bitmap[n] & 0xFF;
    }
    
    public int getBitOffset(final int n) {
        return n & 0x7;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getRowStride() {
        return this.rowStride;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(0, 0, this.width, this.height);
    }
    
    public int getMemorySize() {
        return this.bitmap.length;
    }
}
