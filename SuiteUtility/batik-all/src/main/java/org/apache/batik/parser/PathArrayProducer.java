// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.util.Iterator;
import java.util.LinkedList;

public class PathArrayProducer implements PathHandler
{
    protected LinkedList ps;
    protected float[] p;
    protected LinkedList cs;
    protected short[] c;
    protected int cindex;
    protected int pindex;
    protected int ccount;
    protected int pcount;
    
    public short[] getPathCommands() {
        return this.c;
    }
    
    public float[] getPathParameters() {
        return this.p;
    }
    
    public void startPath() throws ParseException {
        this.cs = new LinkedList();
        this.c = new short[11];
        this.ps = new LinkedList();
        this.p = new float[11];
        this.ccount = 0;
        this.pcount = 0;
        this.cindex = 0;
        this.pindex = 0;
    }
    
    public void movetoRel(final float n, final float n2) throws ParseException {
        this.command((short)3);
        this.param(n);
        this.param(n2);
    }
    
    public void movetoAbs(final float n, final float n2) throws ParseException {
        this.command((short)2);
        this.param(n);
        this.param(n2);
    }
    
    public void closePath() throws ParseException {
        this.command((short)1);
    }
    
    public void linetoRel(final float n, final float n2) throws ParseException {
        this.command((short)5);
        this.param(n);
        this.param(n2);
    }
    
    public void linetoAbs(final float n, final float n2) throws ParseException {
        this.command((short)4);
        this.param(n);
        this.param(n2);
    }
    
    public void linetoHorizontalRel(final float n) throws ParseException {
        this.command((short)13);
        this.param(n);
    }
    
    public void linetoHorizontalAbs(final float n) throws ParseException {
        this.command((short)12);
        this.param(n);
    }
    
    public void linetoVerticalRel(final float n) throws ParseException {
        this.command((short)15);
        this.param(n);
    }
    
    public void linetoVerticalAbs(final float n) throws ParseException {
        this.command((short)14);
        this.param(n);
    }
    
    public void curvetoCubicRel(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws ParseException {
        this.command((short)7);
        this.param(n);
        this.param(n2);
        this.param(n3);
        this.param(n4);
        this.param(n5);
        this.param(n6);
    }
    
    public void curvetoCubicAbs(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws ParseException {
        this.command((short)6);
        this.param(n);
        this.param(n2);
        this.param(n3);
        this.param(n4);
        this.param(n5);
        this.param(n6);
    }
    
    public void curvetoCubicSmoothRel(final float n, final float n2, final float n3, final float n4) throws ParseException {
        this.command((short)17);
        this.param(n);
        this.param(n2);
        this.param(n3);
        this.param(n4);
    }
    
    public void curvetoCubicSmoothAbs(final float n, final float n2, final float n3, final float n4) throws ParseException {
        this.command((short)16);
        this.param(n);
        this.param(n2);
        this.param(n3);
        this.param(n4);
    }
    
    public void curvetoQuadraticRel(final float n, final float n2, final float n3, final float n4) throws ParseException {
        this.command((short)9);
        this.param(n);
        this.param(n2);
        this.param(n3);
        this.param(n4);
    }
    
    public void curvetoQuadraticAbs(final float n, final float n2, final float n3, final float n4) throws ParseException {
        this.command((short)8);
        this.param(n);
        this.param(n2);
        this.param(n3);
        this.param(n4);
    }
    
    public void curvetoQuadraticSmoothRel(final float n, final float n2) throws ParseException {
        this.command((short)19);
        this.param(n);
        this.param(n2);
    }
    
    public void curvetoQuadraticSmoothAbs(final float n, final float n2) throws ParseException {
        this.command((short)18);
        this.param(n);
        this.param(n2);
    }
    
    public void arcRel(final float n, final float n2, final float n3, final boolean b, final boolean b2, final float n4, final float n5) throws ParseException {
        this.command((short)11);
        this.param(n);
        this.param(n2);
        this.param(n3);
        this.param(b ? 1.0f : 0.0f);
        this.param(b2 ? 1.0f : 0.0f);
        this.param(n4);
        this.param(n5);
    }
    
    public void arcAbs(final float n, final float n2, final float n3, final boolean b, final boolean b2, final float n4, final float n5) throws ParseException {
        this.command((short)10);
        this.param(n);
        this.param(n2);
        this.param(n3);
        this.param(b ? 1.0f : 0.0f);
        this.param(b2 ? 1.0f : 0.0f);
        this.param(n4);
        this.param(n5);
    }
    
    protected void command(final short n) throws ParseException {
        if (this.cindex == this.c.length) {
            this.cs.add(this.c);
            this.c = new short[this.c.length * 2 + 1];
            this.cindex = 0;
        }
        this.c[this.cindex++] = n;
        ++this.ccount;
    }
    
    protected void param(final float n) throws ParseException {
        if (this.pindex == this.p.length) {
            this.ps.add(this.p);
            this.p = new float[this.p.length * 2 + 1];
            this.pindex = 0;
        }
        this.p[this.pindex++] = n;
        ++this.pcount;
    }
    
    public void endPath() throws ParseException {
        final short[] c = new short[this.ccount];
        int n = 0;
        for (final short[] array : this.cs) {
            System.arraycopy(array, 0, c, n, array.length);
            n += array.length;
        }
        System.arraycopy(this.c, 0, c, n, this.cindex);
        this.cs.clear();
        this.c = c;
        final float[] p = new float[this.pcount];
        int n2 = 0;
        for (final float[] array2 : this.ps) {
            System.arraycopy(array2, 0, p, n2, array2.length);
            n2 += array2.length;
        }
        System.arraycopy(this.p, 0, p, n2, this.pindex);
        this.ps.clear();
        this.p = p;
    }
}
