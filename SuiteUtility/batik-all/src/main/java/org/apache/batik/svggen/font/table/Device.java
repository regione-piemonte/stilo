// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Device
{
    private int startSize;
    private int endSize;
    private int deltaFormat;
    private int[] deltaValues;
    
    public Device(final RandomAccessFile randomAccessFile) throws IOException {
        this.startSize = randomAccessFile.readUnsignedShort();
        this.endSize = randomAccessFile.readUnsignedShort();
        this.deltaFormat = randomAccessFile.readUnsignedShort();
        int n = this.startSize - this.endSize;
        switch (this.deltaFormat) {
            case 1: {
                n = ((n % 8 == 0) ? (n / 8) : (n / 8 + 1));
                break;
            }
            case 2: {
                n = ((n % 4 == 0) ? (n / 4) : (n / 4 + 1));
                break;
            }
            case 3: {
                n = ((n % 2 == 0) ? (n / 2) : (n / 2 + 1));
                break;
            }
        }
        this.deltaValues = new int[n];
        for (int i = 0; i < n; ++i) {
            this.deltaValues[i] = randomAccessFile.readUnsignedShort();
        }
    }
}
