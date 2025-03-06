// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.ByteArrayInputStream;

public class GlyfSimpleDescript extends GlyfDescript
{
    private int[] endPtsOfContours;
    private byte[] flags;
    private short[] xCoordinates;
    private short[] yCoordinates;
    private int count;
    
    public GlyfSimpleDescript(final GlyfTable glyfTable, final short n, final ByteArrayInputStream byteArrayInputStream) {
        super(glyfTable, n, byteArrayInputStream);
        this.endPtsOfContours = new int[n];
        for (short n2 = 0; n2 < n; ++n2) {
            this.endPtsOfContours[n2] = (byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
        }
        this.count = this.endPtsOfContours[n - 1] + 1;
        this.flags = new byte[this.count];
        this.xCoordinates = new short[this.count];
        this.yCoordinates = new short[this.count];
        this.readInstructions(byteArrayInputStream, byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
        this.readFlags(this.count, byteArrayInputStream);
        this.readCoords(this.count, byteArrayInputStream);
    }
    
    public int getEndPtOfContours(final int n) {
        return this.endPtsOfContours[n];
    }
    
    public byte getFlags(final int n) {
        return this.flags[n];
    }
    
    public short getXCoordinate(final int n) {
        return this.xCoordinates[n];
    }
    
    public short getYCoordinate(final int n) {
        return this.yCoordinates[n];
    }
    
    public boolean isComposite() {
        return false;
    }
    
    public int getPointCount() {
        return this.count;
    }
    
    public int getContourCount() {
        return this.getNumberOfContours();
    }
    
    private void readCoords(final int n, final ByteArrayInputStream byteArrayInputStream) {
        short n2 = 0;
        short n3 = 0;
        for (int i = 0; i < n; ++i) {
            if ((this.flags[i] & 0x10) != 0x0) {
                if ((this.flags[i] & 0x2) != 0x0) {
                    n2 += (short)byteArrayInputStream.read();
                }
            }
            else if ((this.flags[i] & 0x2) != 0x0) {
                n2 += (short)(-(short)byteArrayInputStream.read());
            }
            else {
                n2 += (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
            }
            this.xCoordinates[i] = n2;
        }
        for (int j = 0; j < n; ++j) {
            if ((this.flags[j] & 0x20) != 0x0) {
                if ((this.flags[j] & 0x4) != 0x0) {
                    n3 += (short)byteArrayInputStream.read();
                }
            }
            else if ((this.flags[j] & 0x4) != 0x0) {
                n3 += (short)(-(short)byteArrayInputStream.read());
            }
            else {
                n3 += (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
            }
            this.yCoordinates[j] = n3;
        }
    }
    
    private void readFlags(final int n, final ByteArrayInputStream byteArrayInputStream) {
        try {
            for (int i = 0; i < n; ++i) {
                this.flags[i] = (byte)byteArrayInputStream.read();
                if ((this.flags[i] & 0x8) != 0x0) {
                    final int read = byteArrayInputStream.read();
                    for (int j = 1; j <= read; ++j) {
                        this.flags[i + j] = this.flags[i];
                    }
                    i += read;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("error: array index out of bounds");
        }
    }
}
