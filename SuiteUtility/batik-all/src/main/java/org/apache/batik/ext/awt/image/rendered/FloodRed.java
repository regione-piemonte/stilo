// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.Graphics2D;
import java.awt.image.SampleModel;
import java.awt.image.ColorModel;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.Point;
import java.util.Map;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Paint;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.WritableRaster;

public class FloodRed extends AbstractRed
{
    private WritableRaster raster;
    
    public FloodRed(final Rectangle rectangle) {
        this(rectangle, new Color(0, 0, 0, 0));
    }
    
    public FloodRed(final Rectangle rectangle, final Paint paint) {
        final ColorModel srgb_Unpre = GraphicsUtil.sRGB_Unpre;
        final int defaultTileSize = AbstractTiledRed.getDefaultTileSize();
        int width = rectangle.width;
        if (width > defaultTileSize) {
            width = defaultTileSize;
        }
        int height = rectangle.height;
        if (height > defaultTileSize) {
            height = defaultTileSize;
        }
        final SampleModel compatibleSampleModel = srgb_Unpre.createCompatibleSampleModel(width, height);
        this.init((CachableRed)null, rectangle, srgb_Unpre, compatibleSampleModel, 0, 0, null);
        this.raster = Raster.createWritableRaster(compatibleSampleModel, new Point(0, 0));
        final Graphics2D graphics = GraphicsUtil.createGraphics(new BufferedImage(srgb_Unpre, this.raster, srgb_Unpre.isAlphaPremultiplied(), null));
        graphics.setPaint(paint);
        graphics.fillRect(0, 0, rectangle.width, rectangle.height);
        graphics.dispose();
    }
    
    public Raster getTile(final int n, final int n2) {
        return this.raster.createTranslatedChild(this.tileGridXOff + n * this.tileWidth, this.tileGridYOff + n2 * this.tileHeight);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        final int xTile = this.getXTile(writableRaster.getMinX());
        final int yTile = this.getYTile(writableRaster.getMinY());
        final int xTile2 = this.getXTile(writableRaster.getMinX() + writableRaster.getWidth() - 1);
        final int yTile2 = this.getYTile(writableRaster.getMinY() + writableRaster.getHeight() - 1);
        final boolean is_INT_PACK_Data = GraphicsUtil.is_INT_PACK_Data(this.getSampleModel(), false);
        for (int i = yTile; i <= yTile2; ++i) {
            for (int j = xTile; j <= xTile2; ++j) {
                final Raster tile = this.getTile(j, i);
                if (is_INT_PACK_Data) {
                    GraphicsUtil.copyData_INT_PACK(tile, writableRaster);
                }
                else {
                    GraphicsUtil.copyData_FALLBACK(tile, writableRaster);
                }
            }
        }
        return writableRaster;
    }
}
