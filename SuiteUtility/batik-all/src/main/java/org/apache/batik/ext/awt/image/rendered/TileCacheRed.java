// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.Rectangle;
import java.awt.image.ColorModel;
import java.util.Map;

public class TileCacheRed extends AbstractTiledRed
{
    public TileCacheRed(final CachableRed cachableRed) {
        super(cachableRed, null);
    }
    
    public TileCacheRed(final CachableRed cachableRed, int width, int height) {
        final ColorModel colorModel = cachableRed.getColorModel();
        final Rectangle bounds = cachableRed.getBounds();
        if (width > bounds.width) {
            width = bounds.width;
        }
        if (height > bounds.height) {
            height = bounds.height;
        }
        this.init(cachableRed, bounds, colorModel, colorModel.createCompatibleSampleModel(width, height), cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), null);
    }
    
    public void genRect(final WritableRaster writableRaster) {
        this.getSources().get(0).copyData(writableRaster);
    }
    
    public void flushCache(final Rectangle rectangle) {
        int n = this.getXTile(rectangle.x);
        int n2 = this.getYTile(rectangle.y);
        int xTile = this.getXTile(rectangle.x + rectangle.width - 1);
        int yTile = this.getYTile(rectangle.y + rectangle.height - 1);
        if (n < this.minTileX) {
            n = this.minTileX;
        }
        if (n2 < this.minTileY) {
            n2 = this.minTileY;
        }
        if (xTile >= this.minTileX + this.numXTiles) {
            xTile = this.minTileX + this.numXTiles - 1;
        }
        if (yTile >= this.minTileY + this.numYTiles) {
            yTile = this.minTileY + this.numYTiles - 1;
        }
        if (xTile < n || yTile < n2) {
            return;
        }
        final TileStore tileStore = this.getTileStore();
        for (int i = n2; i <= yTile; ++i) {
            for (int j = n; j <= xTile; ++j) {
                tileStore.setTile(j, i, null);
            }
        }
    }
}
