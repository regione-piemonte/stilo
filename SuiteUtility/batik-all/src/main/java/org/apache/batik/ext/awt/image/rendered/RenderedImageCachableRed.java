// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.Shape;
import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.ColorModel;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.awt.image.RenderedImage;

public class RenderedImageCachableRed implements CachableRed
{
    private RenderedImage src;
    private Vector srcs;
    
    public static CachableRed wrap(final RenderedImage renderedImage) {
        if (renderedImage instanceof CachableRed) {
            return (CachableRed)renderedImage;
        }
        if (renderedImage instanceof BufferedImage) {
            return new BufferedImageCachableRed((BufferedImage)renderedImage);
        }
        return new RenderedImageCachableRed(renderedImage);
    }
    
    public RenderedImageCachableRed(final RenderedImage src) {
        this.srcs = new Vector(0);
        if (src == null) {
            throw new IllegalArgumentException();
        }
        this.src = src;
    }
    
    public Vector getSources() {
        return this.srcs;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(this.getMinX(), this.getMinY(), this.getWidth(), this.getHeight());
    }
    
    public int getMinX() {
        return this.src.getMinX();
    }
    
    public int getMinY() {
        return this.src.getMinY();
    }
    
    public int getWidth() {
        return this.src.getWidth();
    }
    
    public int getHeight() {
        return this.src.getHeight();
    }
    
    public ColorModel getColorModel() {
        return this.src.getColorModel();
    }
    
    public SampleModel getSampleModel() {
        return this.src.getSampleModel();
    }
    
    public int getMinTileX() {
        return this.src.getMinTileX();
    }
    
    public int getMinTileY() {
        return this.src.getMinTileY();
    }
    
    public int getNumXTiles() {
        return this.src.getNumXTiles();
    }
    
    public int getNumYTiles() {
        return this.src.getNumYTiles();
    }
    
    public int getTileGridXOffset() {
        return this.src.getTileGridXOffset();
    }
    
    public int getTileGridYOffset() {
        return this.src.getTileGridYOffset();
    }
    
    public int getTileWidth() {
        return this.src.getTileWidth();
    }
    
    public int getTileHeight() {
        return this.src.getTileHeight();
    }
    
    public Object getProperty(final String s) {
        return this.src.getProperty(s);
    }
    
    public String[] getPropertyNames() {
        return this.src.getPropertyNames();
    }
    
    public Raster getTile(final int n, final int n2) {
        return this.src.getTile(n, n2);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        return this.src.copyData(writableRaster);
    }
    
    public Raster getData() {
        return this.src.getData();
    }
    
    public Raster getData(final Rectangle rectangle) {
        return this.src.getData(rectangle);
    }
    
    public Shape getDependencyRegion(final int n, final Rectangle rectangle) {
        throw new IndexOutOfBoundsException("Nonexistant source requested.");
    }
    
    public Shape getDirtyRegion(final int n, final Rectangle rectangle) {
        throw new IndexOutOfBoundsException("Nonexistant source requested.");
    }
}
