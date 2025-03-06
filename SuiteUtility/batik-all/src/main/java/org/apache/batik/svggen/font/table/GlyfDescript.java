// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.ByteArrayInputStream;

public abstract class GlyfDescript extends Program implements GlyphDescription
{
    public static final byte onCurve = 1;
    public static final byte xShortVector = 2;
    public static final byte yShortVector = 4;
    public static final byte repeat = 8;
    public static final byte xDual = 16;
    public static final byte yDual = 32;
    protected GlyfTable parentTable;
    private int numberOfContours;
    private short xMin;
    private short yMin;
    private short xMax;
    private short yMax;
    
    protected GlyfDescript(final GlyfTable parentTable, final short numberOfContours, final ByteArrayInputStream byteArrayInputStream) {
        this.parentTable = parentTable;
        this.numberOfContours = numberOfContours;
        this.xMin = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
        this.yMin = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
        this.xMax = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
        this.yMax = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
    }
    
    public void resolve() {
    }
    
    public int getNumberOfContours() {
        return this.numberOfContours;
    }
    
    public short getXMaximum() {
        return this.xMax;
    }
    
    public short getXMinimum() {
        return this.xMin;
    }
    
    public short getYMaximum() {
        return this.yMax;
    }
    
    public short getYMinimum() {
        return this.yMin;
    }
}
