// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import org.apache.batik.util.HaltingThread;
import java.awt.image.Raster;

public class TileGrid implements TileStore
{
    private static final boolean DEBUG = false;
    private static final boolean COUNT = false;
    private int xSz;
    private int ySz;
    private int minTileX;
    private int minTileY;
    private TileLRUMember[][] rasters;
    private TileGenerator source;
    private LRUCache cache;
    static int requests;
    static int misses;
    
    public TileGrid(final int minTileX, final int minTileY, final int xSz, final int ySz, final TileGenerator source, final LRUCache cache) {
        this.rasters = null;
        this.source = null;
        this.cache = null;
        this.cache = cache;
        this.source = source;
        this.minTileX = minTileX;
        this.minTileY = minTileY;
        this.xSz = xSz;
        this.ySz = ySz;
        this.rasters = new TileLRUMember[ySz][];
    }
    
    public void setTile(int n, int n2, final Raster raster) {
        n -= this.minTileX;
        n2 -= this.minTileY;
        if (n < 0 || n >= this.xSz) {
            return;
        }
        if (n2 < 0 || n2 >= this.ySz) {
            return;
        }
        final TileLRUMember[] array = this.rasters[n2];
        if (raster != null) {
            TileLRUMember tileLRUMember;
            if (array != null) {
                tileLRUMember = array[n];
                if (tileLRUMember == null) {
                    tileLRUMember = new TileLRUMember();
                    array[n] = tileLRUMember;
                }
            }
            else {
                final TileLRUMember[] array2 = new TileLRUMember[this.xSz];
                tileLRUMember = new TileLRUMember();
                array2[n] = tileLRUMember;
                this.rasters[n2] = array2;
            }
            tileLRUMember.setRaster(raster);
            this.cache.add(tileLRUMember);
            return;
        }
        if (array == null) {
            return;
        }
        final TileLRUMember tileLRUMember2 = array[n];
        if (tileLRUMember2 == null) {
            return;
        }
        array[n] = null;
        this.cache.remove(tileLRUMember2);
    }
    
    public Raster getTileNoCompute(int n, int n2) {
        n -= this.minTileX;
        n2 -= this.minTileY;
        if (n < 0 || n >= this.xSz) {
            return null;
        }
        if (n2 < 0 || n2 >= this.ySz) {
            return null;
        }
        final TileLRUMember[] array = this.rasters[n2];
        if (array == null) {
            return null;
        }
        final TileLRUMember tileLRUMember = array[n];
        if (tileLRUMember == null) {
            return null;
        }
        final Raster retrieveRaster = tileLRUMember.retrieveRaster();
        if (retrieveRaster != null) {
            this.cache.add(tileLRUMember);
        }
        return retrieveRaster;
    }
    
    public Raster getTile(int n, int n2) {
        n -= this.minTileX;
        n2 -= this.minTileY;
        if (n < 0 || n >= this.xSz) {
            return null;
        }
        if (n2 < 0 || n2 >= this.ySz) {
            return null;
        }
        Raster raster = null;
        final TileLRUMember[] array = this.rasters[n2];
        TileLRUMember tileLRUMember;
        if (array != null) {
            tileLRUMember = array[n];
            if (tileLRUMember != null) {
                raster = tileLRUMember.retrieveRaster();
            }
            else {
                tileLRUMember = new TileLRUMember();
                array[n] = tileLRUMember;
            }
        }
        else {
            final TileLRUMember[] array2 = new TileLRUMember[this.xSz];
            this.rasters[n2] = array2;
            tileLRUMember = new TileLRUMember();
            array2[n] = tileLRUMember;
        }
        if (raster == null) {
            raster = this.source.genTile(n + this.minTileX, n2 + this.minTileY);
            if (HaltingThread.hasBeenHalted()) {
                return raster;
            }
            tileLRUMember.setRaster(raster);
        }
        this.cache.add(tileLRUMember);
        return raster;
    }
}
