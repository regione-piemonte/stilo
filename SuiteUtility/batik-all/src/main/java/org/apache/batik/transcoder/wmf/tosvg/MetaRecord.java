// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.wmf.tosvg;

import java.util.ArrayList;
import java.util.List;

public class MetaRecord
{
    public int functionId;
    public int numPoints;
    private final List ptVector;
    
    public MetaRecord() {
        this.ptVector = new ArrayList();
    }
    
    public void EnsureCapacity(final int n) {
    }
    
    public void AddElement(final Object o) {
        this.ptVector.add(o);
    }
    
    public final void addElement(final int value) {
        this.ptVector.add(new Integer(value));
    }
    
    public Integer ElementAt(final int n) {
        return this.ptVector.get(n);
    }
    
    public final int elementAt(final int n) {
        return this.ptVector.get(n);
    }
    
    public static class StringRecord extends MetaRecord
    {
        public final String text;
        
        public StringRecord(final String text) {
            this.text = text;
        }
    }
    
    public static class ByteRecord extends MetaRecord
    {
        public final byte[] bstr;
        
        public ByteRecord(final byte[] bstr) {
            this.bstr = bstr;
        }
    }
}
