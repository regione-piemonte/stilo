// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.util.Map;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.AbstractRed;

public class HistogramRed extends AbstractRed
{
    boolean[] computed;
    int tallied;
    int[] bins;
    
    public HistogramRed(final CachableRed cachableRed) {
        super(cachableRed, null);
        this.tallied = 0;
        this.bins = new int[256];
        this.computed = new boolean[this.getNumXTiles() * this.getNumYTiles()];
    }
    
    public void tallyTile(final Raster raster) {
        final int minX = raster.getMinX();
        final int minY = raster.getMinY();
        final int width = raster.getWidth();
        final int height = raster.getHeight();
        int[] pixels = null;
        for (int i = minY; i < minY + height; ++i) {
            pixels = raster.getPixels(minX, i, width, 1, pixels);
            int n;
            int[] bins;
            int n2;
            for (int j = 0; j < 3 * width; n = pixels[j++] * 5 + pixels[j++] * 9 + pixels[j++] * 2, bins = this.bins, n2 = n >> 4, ++bins[n2], ++j) {}
        }
        ++this.tallied;
    }
    
    public int[] getHistogram() {
        if (this.tallied == this.computed.length) {
            return this.bins;
        }
        final CachableRed cachableRed = this.getSources().get(0);
        final int minTileY = cachableRed.getMinTileY();
        final int numXTiles = cachableRed.getNumXTiles();
        final int minTileX = cachableRed.getMinTileX();
        for (int i = 0; i < cachableRed.getNumYTiles(); ++i) {
            for (int j = 0; j < numXTiles; ++j) {
                final int n = j + minTileX + i * numXTiles;
                if (!this.computed[n]) {
                    this.tallyTile(cachableRed.getTile(j + minTileX, i + minTileY));
                    this.computed[n] = true;
                }
            }
        }
        return this.bins;
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        this.copyToRaster(writableRaster);
        return writableRaster;
    }
    
    public Raster getTile(final int n, final int n2) {
        final int n3 = n2 - this.getMinTileY();
        final int n4 = n - this.getMinTileX();
        final Raster tile = this.getSources().get(0).getTile(n, n2);
        final int n5 = n4 + n3 * this.getNumXTiles();
        if (this.computed[n5]) {
            return tile;
        }
        this.tallyTile(tile);
        this.computed[n5] = true;
        return tile;
    }
}
