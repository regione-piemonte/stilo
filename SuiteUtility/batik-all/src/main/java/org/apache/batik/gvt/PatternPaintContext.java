// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.awt.image.Raster;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import org.apache.batik.ext.awt.image.rendered.TileCacheRed;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Shape;
import java.awt.image.renderable.RenderContext;
import java.awt.color.ColorSpace;
import org.apache.batik.ext.awt.image.renderable.TileRable8Bit;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.awt.PaintContext;

public class PatternPaintContext implements PaintContext
{
    private ColorModel rasterCM;
    private WritableRaster raster;
    private RenderedImage tiled;
    protected AffineTransform usr2dev;
    private static Rectangle EVERYTHING;
    
    public AffineTransform getUsr2Dev() {
        return this.usr2dev;
    }
    
    public PatternPaintContext(final ColorModel colorModel, final AffineTransform affineTransform, RenderingHints hints, final Filter filter, final Rectangle2D pSrc, final boolean b) {
        if (affineTransform == null) {
            throw new IllegalArgumentException();
        }
        if (hints == null) {
            hints = new RenderingHints(null);
        }
        if (filter == null) {
            throw new IllegalArgumentException();
        }
        this.usr2dev = affineTransform;
        final TileRable8Bit tileRable8Bit = new TileRable8Bit(filter, PatternPaintContext.EVERYTHING, pSrc, b);
        final ColorSpace colorSpace = colorModel.getColorSpace();
        if (colorSpace == ColorSpace.getInstance(1000)) {
            tileRable8Bit.setColorSpaceLinear(false);
        }
        else if (colorSpace == ColorSpace.getInstance(1004)) {
            tileRable8Bit.setColorSpaceLinear(true);
        }
        this.tiled = tileRable8Bit.createRendering(new RenderContext(affineTransform, PatternPaintContext.EVERYTHING, hints));
        if (this.tiled != null) {
            final Rectangle bounds = affineTransform.createTransformedShape(pSrc).getBounds();
            if (bounds.getWidth() > 128.0 || bounds.getHeight() > 128.0) {
                this.tiled = new TileCacheRed(GraphicsUtil.wrap(this.tiled), 256, 64);
            }
            this.rasterCM = this.tiled.getColorModel();
            if (this.rasterCM.hasAlpha()) {
                if (colorModel.hasAlpha()) {
                    this.rasterCM = GraphicsUtil.coerceColorModel(this.rasterCM, colorModel.isAlphaPremultiplied());
                }
                else {
                    this.rasterCM = GraphicsUtil.coerceColorModel(this.rasterCM, false);
                }
            }
            return;
        }
        this.rasterCM = ColorModel.getRGBdefault();
        this.tiled = GraphicsUtil.wrap(new BufferedImage(this.rasterCM, this.rasterCM.createCompatibleWritableRaster(32, 32), false, null));
    }
    
    public void dispose() {
        this.raster = null;
    }
    
    public ColorModel getColorModel() {
        return this.rasterCM;
    }
    
    public Raster getRaster(final int childMinX, final int childMinY, final int n, final int n2) {
        if (this.raster == null || this.raster.getWidth() < n || this.raster.getHeight() < n2) {
            this.raster = this.rasterCM.createCompatibleWritableRaster(n, n2);
        }
        final WritableRaster writableChild = this.raster.createWritableChild(0, 0, n, n2, childMinX, childMinY, null);
        this.tiled.copyData(writableChild);
        GraphicsUtil.coerceData(writableChild, this.tiled.getColorModel(), this.rasterCM.isAlphaPremultiplied());
        if (this.raster.getWidth() == n && this.raster.getHeight() == n2) {
            return this.raster;
        }
        return writableChild.createTranslatedChild(0, 0);
    }
    
    static {
        PatternPaintContext.EVERYTHING = new Rectangle(-536870912, -536870912, 1073741823, 1073741823);
    }
}
