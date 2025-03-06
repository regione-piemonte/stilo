// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.util.Map;
import java.awt.Rectangle;

public class TranslateRed extends AbstractRed
{
    protected int deltaX;
    protected int deltaY;
    
    public TranslateRed(final CachableRed cachableRed, final int x, final int y) {
        super(cachableRed, new Rectangle(x, y, cachableRed.getWidth(), cachableRed.getHeight()), cachableRed.getColorModel(), cachableRed.getSampleModel(), cachableRed.getTileGridXOffset() + x - cachableRed.getMinX(), cachableRed.getTileGridYOffset() + y - cachableRed.getMinY(), null);
        this.deltaX = x - cachableRed.getMinX();
        this.deltaY = y - cachableRed.getMinY();
    }
    
    public int getDeltaX() {
        return this.deltaX;
    }
    
    public int getDeltaY() {
        return this.deltaY;
    }
    
    public CachableRed getSource() {
        return this.getSources().get(0);
    }
    
    public Object getProperty(final String s) {
        return this.getSource().getProperty(s);
    }
    
    public String[] getPropertyNames() {
        return this.getSource().getPropertyNames();
    }
    
    public Raster getTile(final int n, final int n2) {
        final Raster tile = this.getSource().getTile(n, n2);
        return tile.createTranslatedChild(tile.getMinX() + this.deltaX, tile.getMinY() + this.deltaY);
    }
    
    public Raster getData() {
        final Raster data = this.getSource().getData();
        return data.createTranslatedChild(data.getMinX() + this.deltaX, data.getMinY() + this.deltaY);
    }
    
    public Raster getData(final Rectangle rectangle) {
        final Rectangle rectangle2 = (Rectangle)rectangle.clone();
        rectangle2.translate(-this.deltaX, -this.deltaY);
        final Raster data = this.getSource().getData(rectangle2);
        return data.createTranslatedChild(data.getMinX() + this.deltaX, data.getMinY() + this.deltaY);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        this.getSource().copyData(writableRaster.createWritableTranslatedChild(writableRaster.getMinX() - this.deltaX, writableRaster.getMinY() - this.deltaY));
        return writableRaster;
    }
}
