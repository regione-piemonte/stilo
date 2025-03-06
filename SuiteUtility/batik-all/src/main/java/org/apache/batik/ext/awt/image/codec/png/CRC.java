// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.png;

class CRC
{
    private static int[] crcTable;
    
    public static int updateCRC(final int n, final byte[] array, final int n2, final int n3) {
        int n4 = n;
        for (int i = 0; i < n3; ++i) {
            n4 = (CRC.crcTable[(n4 ^ array[n2 + i]) & 0xFF] ^ n4 >>> 8);
        }
        return n4;
    }
    
    static {
        CRC.crcTable = new int[256];
        for (int i = 0; i < 256; ++i) {
            int n = i;
            for (int j = 0; j < 8; ++j) {
                if ((n & 0x1) == 0x1) {
                    n = (0xEDB88320 ^ n >>> 1);
                }
                else {
                    n >>>= 1;
                }
                CRC.crcTable[i] = n;
            }
        }
    }
}
