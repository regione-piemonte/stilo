// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.util.Iterator;
import java.util.LinkedList;

public class FloatArrayProducer extends DefaultNumberListHandler implements PointsHandler
{
    protected LinkedList as;
    protected float[] a;
    protected int index;
    protected int count;
    
    public float[] getFloatArray() {
        return this.a;
    }
    
    public void startNumberList() throws ParseException {
        this.as = new LinkedList();
        this.a = new float[11];
        this.count = 0;
        this.index = 0;
    }
    
    public void numberValue(final float n) throws ParseException {
        if (this.index == this.a.length) {
            this.as.add(this.a);
            this.a = new float[this.a.length * 2 + 1];
            this.index = 0;
        }
        this.a[this.index++] = n;
        ++this.count;
    }
    
    public void endNumberList() throws ParseException {
        final float[] a = new float[this.count];
        int n = 0;
        for (final float[] array : this.as) {
            System.arraycopy(array, 0, a, n, array.length);
            n += array.length;
        }
        System.arraycopy(this.a, 0, a, n, this.index);
        this.as.clear();
        this.a = a;
    }
    
    public void startPoints() throws ParseException {
        this.startNumberList();
    }
    
    public void point(final float n, final float n2) throws ParseException {
        this.numberValue(n);
        this.numberValue(n2);
    }
    
    public void endPoints() throws ParseException {
        this.endNumberList();
    }
}
