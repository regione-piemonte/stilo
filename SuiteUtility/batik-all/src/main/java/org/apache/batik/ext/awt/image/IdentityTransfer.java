// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

public class IdentityTransfer implements TransferFunction
{
    public static byte[] lutData;
    
    public byte[] getLookupTable() {
        return IdentityTransfer.lutData;
    }
    
    static {
        IdentityTransfer.lutData = new byte[256];
        for (int i = 0; i <= 255; ++i) {
            IdentityTransfer.lutData[i] = (byte)i;
        }
    }
}
