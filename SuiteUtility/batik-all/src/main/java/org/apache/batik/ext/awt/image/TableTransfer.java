// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

public class TableTransfer implements TransferFunction
{
    public byte[] lutData;
    public int[] tableValues;
    private int n;
    
    public TableTransfer(final int[] tableValues) {
        this.tableValues = tableValues;
        this.n = tableValues.length;
    }
    
    private void buildLutData() {
        this.lutData = new byte[256];
        for (int i = 0; i <= 255; ++i) {
            final float n = i * (this.n - 1) / 255.0f;
            final int n2 = (int)Math.floor(n);
            this.lutData[i] = (byte)((int)(this.tableValues[n2] + (n - n2) * (this.tableValues[(n2 + 1 > this.n - 1) ? (this.n - 1) : (n2 + 1)] - this.tableValues[n2])) & 0xFF);
        }
    }
    
    public byte[] getLookupTable() {
        this.buildLutData();
        return this.lutData;
    }
}
