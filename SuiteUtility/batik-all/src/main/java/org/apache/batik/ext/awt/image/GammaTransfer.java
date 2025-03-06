// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

public class GammaTransfer implements TransferFunction
{
    public byte[] lutData;
    public float amplitude;
    public float exponent;
    public float offset;
    
    public GammaTransfer(final float amplitude, final float exponent, final float offset) {
        this.amplitude = amplitude;
        this.exponent = exponent;
        this.offset = offset;
    }
    
    private void buildLutData() {
        this.lutData = new byte[256];
        for (int i = 0; i <= 255; ++i) {
            int n = (int)Math.round(255.0 * (this.amplitude * Math.pow(i / 255.0f, this.exponent) + this.offset));
            if (n > 255) {
                n = -1;
            }
            else if (n < 0) {
                n = 0;
            }
            this.lutData[i] = (byte)(n & 0xFF);
        }
    }
    
    public byte[] getLookupTable() {
        this.buildLutData();
        return this.lutData;
    }
}
