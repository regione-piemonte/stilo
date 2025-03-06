// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

public class LinearTransfer implements TransferFunction
{
    public byte[] lutData;
    public float slope;
    public float intercept;
    
    public LinearTransfer(final float slope, final float intercept) {
        this.slope = slope;
        this.intercept = intercept;
    }
    
    private void buildLutData() {
        this.lutData = new byte[256];
        final float n = this.intercept * 255.0f + 0.5f;
        for (int i = 0; i <= 255; ++i) {
            int n2 = (int)(this.slope * i + n);
            if (n2 < 0) {
                n2 = 0;
            }
            else if (n2 > 255) {
                n2 = 255;
            }
            this.lutData[i] = (byte)(0xFF & n2);
        }
    }
    
    public byte[] getLookupTable() {
        this.buildLutData();
        return this.lutData;
    }
}
