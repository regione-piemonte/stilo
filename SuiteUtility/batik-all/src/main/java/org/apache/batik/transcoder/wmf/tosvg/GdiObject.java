// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder.wmf.tosvg;

public class GdiObject
{
    int id;
    boolean used;
    Object obj;
    int type;
    
    GdiObject(final int id, final boolean used) {
        this.type = 0;
        this.id = id;
        this.used = used;
        this.type = 0;
    }
    
    public void clear() {
        this.used = false;
        this.type = 0;
    }
    
    public void Setup(final int type, final Object obj) {
        this.obj = obj;
        this.type = type;
        this.used = true;
    }
    
    public boolean isUsed() {
        return this.used;
    }
    
    public int getType() {
        return this.type;
    }
    
    public Object getObject() {
        return this.obj;
    }
    
    public int getID() {
        return this.id;
    }
}
