// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import org.apache.batik.util.CleanerThread;
import java.lang.ref.SoftReference;
import org.apache.batik.util.HaltingThread;
import java.awt.Point;
import java.awt.image.Raster;
import java.util.HashMap;

public class TileMap implements TileStore
{
    private static final boolean DEBUG = false;
    private static final boolean COUNT = false;
    private HashMap rasters;
    private TileGenerator source;
    private LRUCache cache;
    static int requests;
    static int misses;
    
    public TileMap(final TileGenerator source, final LRUCache cache) {
        this.rasters = new HashMap();
        this.source = null;
        this.cache = null;
        this.cache = cache;
        this.source = source;
    }
    
    public void setTile(final int x, final int y, final Raster raster) {
        final Point key = new Point(x, y);
        if (raster == null) {
            final Object remove = this.rasters.remove(key);
            if (remove != null) {
                this.cache.remove((LRUCache.LRUObj)remove);
            }
            return;
        }
        final Object value = this.rasters.get(key);
        TileMapLRUMember value2;
        if (value == null) {
            value2 = new TileMapLRUMember(this, key, raster);
            this.rasters.put(key, value2);
        }
        else {
            value2 = (TileMapLRUMember)value;
            value2.setRaster(raster);
        }
        this.cache.add(value2);
    }
    
    public Raster getTileNoCompute(final int x, final int y) {
        final TileMapLRUMember value = this.rasters.get(new Point(x, y));
        if (value == null) {
            return null;
        }
        final TileMapLRUMember tileMapLRUMember = value;
        final Raster retrieveRaster = tileMapLRUMember.retrieveRaster();
        if (retrieveRaster != null) {
            this.cache.add(tileMapLRUMember);
        }
        return retrieveRaster;
    }
    
    public Raster getTile(final int x, final int y) {
        Raster raster = null;
        final Point point = new Point(x, y);
        final Object value = this.rasters.get(point);
        TileMapLRUMember value2 = null;
        if (value != null) {
            value2 = (TileMapLRUMember)value;
            raster = value2.retrieveRaster();
        }
        if (raster == null) {
            raster = this.source.genTile(x, y);
            if (HaltingThread.hasBeenHalted()) {
                return raster;
            }
            if (value2 != null) {
                value2.setRaster(raster);
            }
            else {
                value2 = new TileMapLRUMember(this, point, raster);
                this.rasters.put(point, value2);
            }
        }
        this.cache.add(value2);
        return raster;
    }
    
    static class TileMapLRUMember extends TileLRUMember
    {
        public Point pt;
        public SoftReference parent;
        
        TileMapLRUMember(final TileMap referent, final Point pt, final Raster raster) {
            super(raster);
            this.parent = new SoftReference((T)referent);
            this.pt = pt;
        }
        
        public void setRaster(final Raster hRaster) {
            this.hRaster = hRaster;
            this.wRaster = new RasterSoftRef(hRaster);
        }
        
        class RasterSoftRef extends CleanerThread.SoftReferenceCleared
        {
            RasterSoftRef(final Object o) {
                super(o);
            }
            
            public void cleared() {
                final TileMap tileMap = TileMapLRUMember.this.parent.get();
                if (tileMap != null) {
                    tileMap.rasters.remove(TileMapLRUMember.this.pt);
                }
            }
        }
    }
}
