// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.ByteArrayInputStream;

public class GlyfCompositeComp
{
    public static final short ARG_1_AND_2_ARE_WORDS = 1;
    public static final short ARGS_ARE_XY_VALUES = 2;
    public static final short ROUND_XY_TO_GRID = 4;
    public static final short WE_HAVE_A_SCALE = 8;
    public static final short MORE_COMPONENTS = 32;
    public static final short WE_HAVE_AN_X_AND_Y_SCALE = 64;
    public static final short WE_HAVE_A_TWO_BY_TWO = 128;
    public static final short WE_HAVE_INSTRUCTIONS = 256;
    public static final short USE_MY_METRICS = 512;
    private int firstIndex;
    private int firstContour;
    private short argument1;
    private short argument2;
    private short flags;
    private int glyphIndex;
    private double xscale;
    private double yscale;
    private double scale01;
    private double scale10;
    private int xtranslate;
    private int ytranslate;
    private int point1;
    private int point2;
    
    protected GlyfCompositeComp(final ByteArrayInputStream byteArrayInputStream) {
        this.xscale = 1.0;
        this.yscale = 1.0;
        this.scale01 = 0.0;
        this.scale10 = 0.0;
        this.xtranslate = 0;
        this.ytranslate = 0;
        this.point1 = 0;
        this.point2 = 0;
        this.flags = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
        this.glyphIndex = (byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
        if ((this.flags & 0x1) != 0x0) {
            this.argument1 = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
            this.argument2 = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read());
        }
        else {
            this.argument1 = (short)byteArrayInputStream.read();
            this.argument2 = (short)byteArrayInputStream.read();
        }
        if ((this.flags & 0x2) != 0x0) {
            this.xtranslate = this.argument1;
            this.ytranslate = this.argument2;
        }
        else {
            this.point1 = this.argument1;
            this.point2 = this.argument2;
        }
        if ((this.flags & 0x8) != 0x0) {
            final double n = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read()) / 16384.0;
            this.yscale = n;
            this.xscale = n;
        }
        else if ((this.flags & 0x40) != 0x0) {
            this.xscale = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read()) / 16384.0;
            this.yscale = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read()) / 16384.0;
        }
        else if ((this.flags & 0x80) != 0x0) {
            this.xscale = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read()) / 16384.0;
            this.scale01 = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read()) / 16384.0;
            this.scale10 = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read()) / 16384.0;
            this.yscale = (short)(byteArrayInputStream.read() << 8 | byteArrayInputStream.read()) / 16384.0;
        }
    }
    
    public void setFirstIndex(final int firstIndex) {
        this.firstIndex = firstIndex;
    }
    
    public int getFirstIndex() {
        return this.firstIndex;
    }
    
    public void setFirstContour(final int firstContour) {
        this.firstContour = firstContour;
    }
    
    public int getFirstContour() {
        return this.firstContour;
    }
    
    public short getArgument1() {
        return this.argument1;
    }
    
    public short getArgument2() {
        return this.argument2;
    }
    
    public short getFlags() {
        return this.flags;
    }
    
    public int getGlyphIndex() {
        return this.glyphIndex;
    }
    
    public double getScale01() {
        return this.scale01;
    }
    
    public double getScale10() {
        return this.scale10;
    }
    
    public double getXScale() {
        return this.xscale;
    }
    
    public double getYScale() {
        return this.yscale;
    }
    
    public int getXTranslate() {
        return this.xtranslate;
    }
    
    public int getYTranslate() {
        return this.ytranslate;
    }
    
    public int scaleX(final int n, final int n2) {
        return Math.round((float)(n * this.xscale + n2 * this.scale10));
    }
    
    public int scaleY(final int n, final int n2) {
        return Math.round((float)(n * this.scale01 + n2 * this.yscale));
    }
}
