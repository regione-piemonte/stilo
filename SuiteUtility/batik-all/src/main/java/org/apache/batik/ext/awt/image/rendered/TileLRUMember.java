// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.lang.ref.SoftReference;
import java.awt.image.Raster;
import java.lang.ref.Reference;

public class TileLRUMember implements LRUCache.LRUObj
{
    private static final boolean DEBUG = false;
    protected LRUCache.LRUNode myNode;
    protected Reference wRaster;
    protected Raster hRaster;
    
    public TileLRUMember() {
        this.myNode = null;
        this.wRaster = null;
        this.hRaster = null;
    }
    
    public TileLRUMember(final Raster raster) {
        this.myNode = null;
        this.wRaster = null;
        this.hRaster = null;
        this.setRaster(raster);
    }
    
    public void setRaster(final Raster raster) {
        this.hRaster = raster;
        this.wRaster = new SoftReference(raster);
    }
    
    public boolean checkRaster() {
        return this.hRaster != null || (this.wRaster != null && this.wRaster.get() != null);
    }
    
    public Raster retrieveRaster() {
        if (this.hRaster != null) {
            return this.hRaster;
        }
        if (this.wRaster == null) {
            return null;
        }
        this.hRaster = this.wRaster.get();
        if (this.hRaster == null) {
            this.wRaster = null;
        }
        return this.hRaster;
    }
    
    public LRUCache.LRUNode lruGet() {
        return this.myNode;
    }
    
    public void lruSet(final LRUCache.LRUNode myNode) {
        this.myNode = myNode;
    }
    
    public void lruRemove() {
        this.myNode = null;
        this.hRaster = null;
    }
}
