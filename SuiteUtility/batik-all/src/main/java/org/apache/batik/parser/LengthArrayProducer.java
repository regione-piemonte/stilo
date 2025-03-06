// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.util.Iterator;
import java.util.LinkedList;

public class LengthArrayProducer extends DefaultLengthListHandler
{
    protected LinkedList vs;
    protected float[] v;
    protected LinkedList us;
    protected short[] u;
    protected int index;
    protected int count;
    protected short currentUnit;
    
    public short[] getLengthTypeArray() {
        return this.u;
    }
    
    public float[] getLengthValueArray() {
        return this.v;
    }
    
    public void startLengthList() throws ParseException {
        this.us = new LinkedList();
        this.u = new short[11];
        this.vs = new LinkedList();
        this.v = new float[11];
        this.count = 0;
        this.index = 0;
    }
    
    public void numberValue(final float n) throws ParseException {
    }
    
    public void lengthValue(final float n) throws ParseException {
        if (this.index == this.v.length) {
            this.vs.add(this.v);
            this.v = new float[this.v.length * 2 + 1];
            this.us.add(this.u);
            this.u = new short[this.u.length * 2 + 1];
            this.index = 0;
        }
        this.v[this.index] = n;
    }
    
    public void startLength() throws ParseException {
        this.currentUnit = 1;
    }
    
    public void endLength() throws ParseException {
        this.u[this.index++] = this.currentUnit;
        ++this.count;
    }
    
    public void em() throws ParseException {
        this.currentUnit = 3;
    }
    
    public void ex() throws ParseException {
        this.currentUnit = 4;
    }
    
    public void in() throws ParseException {
        this.currentUnit = 8;
    }
    
    public void cm() throws ParseException {
        this.currentUnit = 6;
    }
    
    public void mm() throws ParseException {
        this.currentUnit = 7;
    }
    
    public void pc() throws ParseException {
        this.currentUnit = 10;
    }
    
    public void pt() throws ParseException {
        this.currentUnit = 9;
    }
    
    public void px() throws ParseException {
        this.currentUnit = 5;
    }
    
    public void percentage() throws ParseException {
        this.currentUnit = 2;
    }
    
    public void endLengthList() throws ParseException {
        final float[] v = new float[this.count];
        int n = 0;
        for (final float[] array : this.vs) {
            System.arraycopy(array, 0, v, n, array.length);
            n += array.length;
        }
        System.arraycopy(this.v, 0, v, n, this.index);
        this.vs.clear();
        this.v = v;
        final short[] u = new short[this.count];
        int n2 = 0;
        for (final short[] array2 : this.us) {
            System.arraycopy(array2, 0, u, n2, array2.length);
            n2 += array2.length;
        }
        System.arraycopy(this.u, 0, u, n2, this.index);
        this.us.clear();
        this.u = u;
    }
}
