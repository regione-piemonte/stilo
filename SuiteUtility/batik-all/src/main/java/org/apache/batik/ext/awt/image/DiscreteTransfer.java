// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

public class DiscreteTransfer implements TransferFunction
{
    public byte[] lutData;
    public int[] tableValues;
    private int n;
    
    public DiscreteTransfer(final int[] tableValues) {
        this.tableValues = tableValues;
        this.n = tableValues.length;
    }
    
    private void buildLutData() {
        this.lutData = new byte[256];
        for (int i = 0; i <= 255; ++i) {
            int n = (int)Math.floor(i * this.n / 255.0f);
            if (n == this.n) {
                n = this.n - 1;
            }
            this.lutData[i] = (byte)(this.tableValues[n] & 0xFF);
        }
    }
    
    public byte[] getLookupTable() {
        this.buildLutData();
        return this.lutData;
    }
}
