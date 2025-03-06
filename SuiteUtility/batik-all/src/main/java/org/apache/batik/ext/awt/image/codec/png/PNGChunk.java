// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.png;

class PNGChunk
{
    int length;
    int type;
    byte[] data;
    int crc;
    final String typeString;
    
    PNGChunk(final int length, final int type, final byte[] data, final int crc) {
        this.length = length;
        this.type = type;
        this.data = data;
        this.crc = crc;
        this.typeString = "" + (char)(type >>> 24 & 0xFF) + (char)(type >>> 16 & 0xFF) + (char)(type >>> 8 & 0xFF) + (char)(type & 0xFF);
    }
    
    public int getLength() {
        return this.length;
    }
    
    public int getType() {
        return this.type;
    }
    
    public String getTypeString() {
        return this.typeString;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public byte getByte(final int n) {
        return this.data[n];
    }
    
    public int getInt1(final int n) {
        return this.data[n] & 0xFF;
    }
    
    public int getInt2(final int n) {
        return (this.data[n] & 0xFF) << 8 | (this.data[n + 1] & 0xFF);
    }
    
    public int getInt4(final int n) {
        return (this.data[n] & 0xFF) << 24 | (this.data[n + 1] & 0xFF) << 16 | (this.data[n + 2] & 0xFF) << 8 | (this.data[n + 3] & 0xFF);
    }
    
    public String getString4(final int n) {
        return "" + (char)this.data[n] + (char)this.data[n + 1] + (char)this.data[n + 2] + (char)this.data[n + 3];
    }
    
    public boolean isType(final String anObject) {
        return this.typeString.equals(anObject);
    }
}
