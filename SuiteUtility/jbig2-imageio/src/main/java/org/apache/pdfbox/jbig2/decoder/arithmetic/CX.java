// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.decoder.arithmetic;

public final class CX
{
    private int index;
    private final byte[] cx;
    private final byte[] mps;
    
    public CX(final int n, final int index) {
        this.index = index;
        this.cx = new byte[n];
        this.mps = new byte[n];
    }
    
    protected int cx() {
        return this.cx[this.index] & 0x7F;
    }
    
    protected void setCx(final int n) {
        this.cx[this.index] = (byte)(n & 0x7F);
    }
    
    protected byte mps() {
        return this.mps[this.index];
    }
    
    protected void toggleMps() {
        final byte[] mps = this.mps;
        final int index = this.index;
        mps[index] ^= 0x1;
    }
    
    protected int getIndex() {
        return this.index;
    }
    
    public void setIndex(final int index) {
        this.index = index;
    }
}
