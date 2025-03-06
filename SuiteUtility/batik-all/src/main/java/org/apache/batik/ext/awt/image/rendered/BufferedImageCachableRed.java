// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.util.Map;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BufferedImageCachableRed extends AbstractRed
{
    BufferedImage bi;
    
    public BufferedImageCachableRed(final BufferedImage bi) {
        super((CachableRed)null, new Rectangle(bi.getMinX(), bi.getMinY(), bi.getWidth(), bi.getHeight()), bi.getColorModel(), bi.getSampleModel(), bi.getMinX(), bi.getMinY(), null);
        this.bi = bi;
    }
    
    public BufferedImageCachableRed(final BufferedImage bi, final int x, final int y) {
        super((CachableRed)null, new Rectangle(x, y, bi.getWidth(), bi.getHeight()), bi.getColorModel(), bi.getSampleModel(), x, y, null);
        this.bi = bi;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(this.getMinX(), this.getMinY(), this.getWidth(), this.getHeight());
    }
    
    public BufferedImage getBufferedImage() {
        return this.bi;
    }
    
    public Object getProperty(final String name) {
        return this.bi.getProperty(name);
    }
    
    public String[] getPropertyNames() {
        return this.bi.getPropertyNames();
    }
    
    public Raster getTile(final int tileX, final int tileY) {
        return this.bi.getTile(tileX, tileY);
    }
    
    public Raster getData() {
        return this.bi.getData().createTranslatedChild(this.getMinX(), this.getMinY());
    }
    
    public Raster getData(final Rectangle rectangle) {
        final Rectangle rectangle2 = (Rectangle)rectangle.clone();
        if (!rectangle2.intersects(this.getBounds())) {
            return null;
        }
        final Rectangle intersection = rectangle2.intersection(this.getBounds());
        intersection.translate(-this.getMinX(), -this.getMinY());
        final Raster data = this.bi.getData(intersection);
        return data.createTranslatedChild(data.getMinX() + this.getMinX(), data.getMinY() + this.getMinY());
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        GraphicsUtil.copyData(this.bi.getRaster(), writableRaster.createWritableTranslatedChild(writableRaster.getMinX() - this.getMinX(), writableRaster.getMinY() - this.getMinY()));
        return writableRaster;
    }
}
