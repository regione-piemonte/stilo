// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.RenderedImage;

public class TileCache
{
    private static LRUCache cache;
    
    public static void setSize(final int size) {
        TileCache.cache.setSize(size);
    }
    
    public static TileStore getTileGrid(final int n, final int n2, final int n3, final int n4, final TileGenerator tileGenerator) {
        return new TileGrid(n, n2, n3, n4, tileGenerator, TileCache.cache);
    }
    
    public static TileStore getTileGrid(final RenderedImage renderedImage, final TileGenerator tileGenerator) {
        return new TileGrid(renderedImage.getMinTileX(), renderedImage.getMinTileY(), renderedImage.getNumXTiles(), renderedImage.getNumYTiles(), tileGenerator, TileCache.cache);
    }
    
    public static TileStore getTileMap(final TileGenerator tileGenerator) {
        return new TileMap(tileGenerator, TileCache.cache);
    }
    
    static {
        TileCache.cache = new LRUCache(50);
    }
}
