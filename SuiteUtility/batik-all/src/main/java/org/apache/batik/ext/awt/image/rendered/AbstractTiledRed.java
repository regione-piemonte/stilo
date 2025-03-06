// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.DataBuffer;
import java.awt.Point;
import java.awt.image.DataBufferInt;
import org.apache.batik.util.HaltingThread;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.List;
import java.awt.image.SampleModel;
import java.awt.image.ColorModel;
import java.util.Map;
import java.awt.Rectangle;

public abstract class AbstractTiledRed extends AbstractRed implements TileGenerator
{
    private TileStore tiles;
    private static int defaultTileSize;
    
    public static int getDefaultTileSize() {
        return AbstractTiledRed.defaultTileSize;
    }
    
    protected AbstractTiledRed() {
    }
    
    protected AbstractTiledRed(final Rectangle rectangle, final Map map) {
        super(rectangle, map);
    }
    
    protected AbstractTiledRed(final CachableRed cachableRed, final Map map) {
        super(cachableRed, map);
    }
    
    protected AbstractTiledRed(final CachableRed cachableRed, final Rectangle rectangle, final Map map) {
        super(cachableRed, rectangle, map);
    }
    
    protected AbstractTiledRed(final CachableRed cachableRed, final Rectangle rectangle, final ColorModel colorModel, final SampleModel sampleModel, final Map map) {
        super(cachableRed, rectangle, colorModel, sampleModel, map);
    }
    
    protected AbstractTiledRed(final CachableRed cachableRed, final Rectangle rectangle, final ColorModel colorModel, final SampleModel sampleModel, final int n, final int n2, final Map map) {
        super(cachableRed, rectangle, colorModel, sampleModel, n, n2, map);
    }
    
    protected void init(final CachableRed cachableRed, final Rectangle rectangle, final ColorModel colorModel, final SampleModel sampleModel, final int n, final int n2, final Map map) {
        this.init(cachableRed, rectangle, colorModel, sampleModel, n, n2, null, map);
    }
    
    protected void init(final CachableRed cachableRed, final Rectangle rectangle, final ColorModel colorModel, final SampleModel sampleModel, final int n, final int n2, final TileStore tiles, final Map map) {
        super.init(cachableRed, rectangle, colorModel, sampleModel, n, n2, map);
        this.tiles = tiles;
        if (this.tiles == null) {
            this.tiles = this.createTileStore();
        }
    }
    
    protected AbstractTiledRed(final List list, final Rectangle rectangle, final Map map) {
        super(list, rectangle, map);
    }
    
    protected AbstractTiledRed(final List list, final Rectangle rectangle, final ColorModel colorModel, final SampleModel sampleModel, final Map map) {
        super(list, rectangle, colorModel, sampleModel, map);
    }
    
    protected AbstractTiledRed(final List list, final Rectangle rectangle, final ColorModel colorModel, final SampleModel sampleModel, final int n, final int n2, final Map map) {
        super(list, rectangle, colorModel, sampleModel, n, n2, map);
    }
    
    protected void init(final List list, final Rectangle rectangle, final ColorModel colorModel, final SampleModel sampleModel, final int n, final int n2, final Map map) {
        super.init(list, rectangle, colorModel, sampleModel, n, n2, map);
        this.tiles = this.createTileStore();
    }
    
    public TileStore getTileStore() {
        return this.tiles;
    }
    
    protected void setTileStore(final TileStore tiles) {
        this.tiles = tiles;
    }
    
    protected TileStore createTileStore() {
        return TileCache.getTileMap(this);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        this.copyToRasterByBlocks(writableRaster);
        return writableRaster;
    }
    
    public Raster getData(final Rectangle rectangle) {
        final int xTile = this.getXTile(rectangle.x);
        final int xTile2 = this.getXTile(rectangle.x + rectangle.width - 1);
        final int yTile = this.getYTile(rectangle.y);
        final int yTile2 = this.getYTile(rectangle.y + rectangle.height - 1);
        if (xTile == xTile2 && yTile == yTile2) {
            return this.getTile(xTile, yTile).createChild(rectangle.x, rectangle.y, rectangle.width, rectangle.height, rectangle.x, rectangle.y, null);
        }
        return super.getData(rectangle);
    }
    
    public Raster getTile(final int n, final int n2) {
        return this.tiles.getTile(n, n2);
    }
    
    public Raster genTile(final int n, final int n2) {
        final WritableRaster tile = this.makeTile(n, n2);
        this.genRect(tile);
        return tile;
    }
    
    public abstract void genRect(final WritableRaster p0);
    
    public void setTile(final int n, final int n2, final Raster raster) {
        this.tiles.setTile(n, n2, raster);
    }
    
    public void copyToRasterByBlocks(final WritableRaster writableRaster) {
        final boolean is_INT_PACK_Data = GraphicsUtil.is_INT_PACK_Data(this.getSampleModel(), false);
        final Rectangle bounds = this.getBounds();
        final Rectangle bounds2 = writableRaster.getBounds();
        int n = this.getXTile(bounds2.x);
        int n2 = this.getYTile(bounds2.y);
        int xTile = this.getXTile(bounds2.x + bounds2.width - 1);
        int yTile = this.getYTile(bounds2.y + bounds2.height - 1);
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
        int n3 = n;
        int n4 = xTile;
        int n5 = n2;
        int n6 = yTile;
        if (n * this.tileWidth + this.tileGridXOff < bounds2.x && bounds.x != bounds2.x) {
            ++n3;
        }
        if (n2 * this.tileHeight + this.tileGridYOff < bounds2.y && bounds.y != bounds2.y) {
            ++n5;
        }
        if ((xTile + 1) * this.tileWidth + this.tileGridXOff - 1 >= bounds2.x + bounds2.width && bounds.x + bounds.width != bounds2.x + bounds2.width) {
            --n4;
        }
        if ((yTile + 1) * this.tileHeight + this.tileGridYOff - 1 >= bounds2.y + bounds2.height && bounds.y + bounds.height != bounds2.y + bounds2.height) {
            --n6;
        }
        final int n7 = n4 - n3 + 1;
        final int n8 = n6 - n5 + 1;
        boolean[] array = null;
        if (n7 > 0 && n8 > 0) {
            array = new boolean[n7 * n8];
        }
        final boolean[] array2 = new boolean[2 * (xTile - n + 1) + 2 * (yTile - n2 + 1)];
        int n9 = 0;
        int n10 = 0;
        for (int i = n2; i <= yTile; ++i) {
            for (int j = n; j <= xTile; ++j) {
                final Raster tileNoCompute = this.tiles.getTileNoCompute(j, i);
                final boolean b = tileNoCompute != null;
                if (i >= n5 && i <= n6 && j >= n3 && j <= n4) {
                    array[j - n3 + (i - n5) * n7] = b;
                }
                else {
                    array2[n9++] = b;
                }
                if (b) {
                    ++n10;
                    if (is_INT_PACK_Data) {
                        GraphicsUtil.copyData_INT_PACK(tileNoCompute, writableRaster);
                    }
                    else {
                        GraphicsUtil.copyData_FALLBACK(tileNoCompute, writableRaster);
                    }
                }
            }
        }
        if (n7 > 0 && n8 > 0) {
            this.drawBlock(new TileBlock(n3, n5, n7, n8, array, 0, 0, n7, n8), writableRaster);
        }
        final Thread currentThread = Thread.currentThread();
        if (HaltingThread.hasBeenHalted()) {
            return;
        }
        int n11 = 0;
        for (int k = n2; k <= yTile; ++k) {
            for (int l = n; l <= xTile; ++l) {
                final Raster tileNoCompute2 = this.tiles.getTileNoCompute(l, k);
                if (k >= n5 && k <= n6 && l >= n3 && l <= n4) {
                    if (tileNoCompute2 == null) {
                        final WritableRaster tile = this.makeTile(l, k);
                        if (is_INT_PACK_Data) {
                            GraphicsUtil.copyData_INT_PACK(writableRaster, tile);
                        }
                        else {
                            GraphicsUtil.copyData_FALLBACK(writableRaster, tile);
                        }
                        this.tiles.setTile(l, k, tile);
                    }
                }
                else if (!array2[n11++]) {
                    final Raster tile2 = this.getTile(l, k);
                    if (HaltingThread.hasBeenHalted(currentThread)) {
                        return;
                    }
                    if (is_INT_PACK_Data) {
                        GraphicsUtil.copyData_INT_PACK(tile2, writableRaster);
                    }
                    else {
                        GraphicsUtil.copyData_FALLBACK(tile2, writableRaster);
                    }
                }
            }
        }
    }
    
    public void copyToRaster(final WritableRaster writableRaster) {
        final Rectangle bounds = writableRaster.getBounds();
        int n = this.getXTile(bounds.x);
        int n2 = this.getYTile(bounds.y);
        int xTile = this.getXTile(bounds.x + bounds.width - 1);
        int yTile = this.getYTile(bounds.y + bounds.height - 1);
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
        final boolean is_INT_PACK_Data = GraphicsUtil.is_INT_PACK_Data(this.getSampleModel(), false);
        final int n3 = xTile - n + 1;
        final boolean[] array = new boolean[n3 * (yTile - n2 + 1)];
        for (int i = n2; i <= yTile; ++i) {
            for (int j = n; j <= xTile; ++j) {
                final Raster tileNoCompute = this.tiles.getTileNoCompute(j, i);
                if (tileNoCompute != null) {
                    array[j - n + (i - n2) * n3] = true;
                    if (is_INT_PACK_Data) {
                        GraphicsUtil.copyData_INT_PACK(tileNoCompute, writableRaster);
                    }
                    else {
                        GraphicsUtil.copyData_FALLBACK(tileNoCompute, writableRaster);
                    }
                }
            }
        }
        for (int k = n2; k <= yTile; ++k) {
            for (int l = n; l <= xTile; ++l) {
                if (!array[l - n + (k - n2) * n3]) {
                    final Raster tile = this.getTile(l, k);
                    if (is_INT_PACK_Data) {
                        GraphicsUtil.copyData_INT_PACK(tile, writableRaster);
                    }
                    else {
                        GraphicsUtil.copyData_FALLBACK(tile, writableRaster);
                    }
                }
            }
        }
    }
    
    protected void drawBlock(final TileBlock tileBlock, final WritableRaster writableRaster) {
        final TileBlock[] bestSplit = tileBlock.getBestSplit();
        if (bestSplit == null) {
            return;
        }
        this.drawBlockInPlace(bestSplit, writableRaster);
    }
    
    protected void drawBlockAndCopy(final TileBlock[] array, final WritableRaster writableRaster) {
        if (array.length == 1) {
            final TileBlock tileBlock = array[0];
            final int n = tileBlock.getXLoc() * this.tileWidth + this.tileGridXOff;
            final int n2 = tileBlock.getYLoc() * this.tileHeight + this.tileGridYOff;
            if (n == writableRaster.getMinX() && n2 == writableRaster.getMinY()) {
                this.drawBlockInPlace(array, writableRaster);
                return;
            }
        }
        final int tileWidth = this.tileWidth;
        final int tileHeight = this.tileHeight;
        int size = 0;
        for (int i = 0; i < array.length; ++i) {
            final TileBlock tileBlock2 = array[i];
            final int n3 = tileBlock2.getWidth() * tileWidth * (tileBlock2.getHeight() * tileHeight);
            if (n3 > size) {
                size = n3;
            }
        }
        final DataBufferInt dataBuffer = new DataBufferInt(size);
        final int[] bandMasks = { 16711680, 65280, 255, -16777216 };
        final boolean is_INT_PACK_Data = GraphicsUtil.is_INT_PACK_Data(writableRaster.getSampleModel(), false);
        final Thread currentThread = Thread.currentThread();
        for (int j = 0; j < array.length; ++j) {
            final TileBlock tileBlock3 = array[j];
            final Rectangle intersection = new Rectangle(tileBlock3.getXLoc() * tileWidth + this.tileGridXOff, tileBlock3.getYLoc() * tileHeight + this.tileGridYOff, tileBlock3.getWidth() * tileWidth, tileBlock3.getHeight() * tileHeight).intersection(this.bounds);
            final WritableRaster packedRaster = Raster.createPackedRaster(dataBuffer, intersection.width, intersection.height, intersection.width, bandMasks, new Point(intersection.x, intersection.y));
            this.genRect(packedRaster);
            if (is_INT_PACK_Data) {
                GraphicsUtil.copyData_INT_PACK(packedRaster, writableRaster);
            }
            else {
                GraphicsUtil.copyData_FALLBACK(packedRaster, writableRaster);
            }
            if (HaltingThread.hasBeenHalted(currentThread)) {
                return;
            }
        }
    }
    
    protected void drawBlockInPlace(final TileBlock[] array, final WritableRaster writableRaster) {
        final Thread currentThread = Thread.currentThread();
        final int tileWidth = this.tileWidth;
        final int tileHeight = this.tileHeight;
        for (int i = 0; i < array.length; ++i) {
            final TileBlock tileBlock = array[i];
            final Rectangle intersection = new Rectangle(tileBlock.getXLoc() * tileWidth + this.tileGridXOff, tileBlock.getYLoc() * tileHeight + this.tileGridYOff, tileBlock.getWidth() * tileWidth, tileBlock.getHeight() * tileHeight).intersection(this.bounds);
            this.genRect(writableRaster.createWritableChild(intersection.x, intersection.y, intersection.width, intersection.height, intersection.x, intersection.y, null));
            if (HaltingThread.hasBeenHalted(currentThread)) {
                return;
            }
        }
    }
    
    static {
        AbstractTiledRed.defaultTileSize = 128;
    }
}
