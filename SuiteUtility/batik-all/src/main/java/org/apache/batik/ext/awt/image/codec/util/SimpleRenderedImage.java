// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.util;

import java.awt.Point;
import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.util.Vector;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import java.awt.image.RenderedImage;

public abstract class SimpleRenderedImage implements RenderedImage
{
    protected int minX;
    protected int minY;
    protected int width;
    protected int height;
    protected int tileWidth;
    protected int tileHeight;
    protected int tileGridXOffset;
    protected int tileGridYOffset;
    protected SampleModel sampleModel;
    protected ColorModel colorModel;
    protected List sources;
    protected Map properties;
    
    public SimpleRenderedImage() {
        this.tileGridXOffset = 0;
        this.tileGridYOffset = 0;
        this.sampleModel = null;
        this.colorModel = null;
        this.sources = new ArrayList();
        this.properties = new HashMap();
    }
    
    public int getMinX() {
        return this.minX;
    }
    
    public final int getMaxX() {
        return this.getMinX() + this.getWidth();
    }
    
    public int getMinY() {
        return this.minY;
    }
    
    public final int getMaxY() {
        return this.getMinY() + this.getHeight();
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(this.getMinX(), this.getMinY(), this.getWidth(), this.getHeight());
    }
    
    public int getTileWidth() {
        return this.tileWidth;
    }
    
    public int getTileHeight() {
        return this.tileHeight;
    }
    
    public int getTileGridXOffset() {
        return this.tileGridXOffset;
    }
    
    public int getTileGridYOffset() {
        return this.tileGridYOffset;
    }
    
    public int getMinTileX() {
        return this.XToTileX(this.getMinX());
    }
    
    public int getMaxTileX() {
        return this.XToTileX(this.getMaxX() - 1);
    }
    
    public int getNumXTiles() {
        return this.getMaxTileX() - this.getMinTileX() + 1;
    }
    
    public int getMinTileY() {
        return this.YToTileY(this.getMinY());
    }
    
    public int getMaxTileY() {
        return this.YToTileY(this.getMaxY() - 1);
    }
    
    public int getNumYTiles() {
        return this.getMaxTileY() - this.getMinTileY() + 1;
    }
    
    public SampleModel getSampleModel() {
        return this.sampleModel;
    }
    
    public ColorModel getColorModel() {
        return this.colorModel;
    }
    
    public Object getProperty(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        return this.properties.get(lowerCase);
    }
    
    public String[] getPropertyNames() {
        final String[] array = new String[this.properties.size()];
        this.properties.keySet().toArray(array);
        return array;
    }
    
    public String[] getPropertyNames(String lowerCase) {
        final String[] propertyNames = this.getPropertyNames();
        if (propertyNames == null) {
            return null;
        }
        lowerCase = lowerCase.toLowerCase();
        final ArrayList list = new ArrayList<String>();
        for (int i = 0; i < propertyNames.length; ++i) {
            if (propertyNames[i].startsWith(lowerCase)) {
                list.add(propertyNames[i]);
            }
        }
        if (list.size() == 0) {
            return null;
        }
        final String[] array = new String[list.size()];
        list.toArray(array);
        return array;
    }
    
    public static int XToTileX(int n, final int n2, final int n3) {
        n -= n2;
        if (n < 0) {
            n += 1 - n3;
        }
        return n / n3;
    }
    
    public static int YToTileY(int n, final int n2, final int n3) {
        n -= n2;
        if (n < 0) {
            n += 1 - n3;
        }
        return n / n3;
    }
    
    public int XToTileX(final int n) {
        return XToTileX(n, this.getTileGridXOffset(), this.getTileWidth());
    }
    
    public int YToTileY(final int n) {
        return YToTileY(n, this.getTileGridYOffset(), this.getTileHeight());
    }
    
    public static int tileXToX(final int n, final int n2, final int n3) {
        return n * n3 + n2;
    }
    
    public static int tileYToY(final int n, final int n2, final int n3) {
        return n * n3 + n2;
    }
    
    public int tileXToX(final int n) {
        return n * this.tileWidth + this.tileGridXOffset;
    }
    
    public int tileYToY(final int n) {
        return n * this.tileHeight + this.tileGridYOffset;
    }
    
    public Vector getSources() {
        return null;
    }
    
    public Raster getData() {
        return this.getData(new Rectangle(this.getMinX(), this.getMinY(), this.getWidth(), this.getHeight()));
    }
    
    public Raster getData(final Rectangle rectangle) {
        final int xToTileX = this.XToTileX(rectangle.x);
        final int yToTileY = this.YToTileY(rectangle.y);
        final int xToTileX2 = this.XToTileX(rectangle.x + rectangle.width - 1);
        final int yToTileY2 = this.YToTileY(rectangle.y + rectangle.height - 1);
        if (xToTileX == xToTileX2 && yToTileY == yToTileY2) {
            return this.getTile(xToTileX, yToTileY).createChild(rectangle.x, rectangle.y, rectangle.width, rectangle.height, rectangle.x, rectangle.y, null);
        }
        final WritableRaster writableRaster = Raster.createWritableRaster(this.sampleModel.createCompatibleSampleModel(rectangle.width, rectangle.height), rectangle.getLocation());
        for (int i = yToTileY; i <= yToTileY2; ++i) {
            for (int j = xToTileX; j <= xToTileX2; ++j) {
                final Raster tile = this.getTile(j, i);
                final Rectangle intersection = rectangle.intersection(tile.getBounds());
                writableRaster.setDataElements(0, 0, tile.createChild(intersection.x, intersection.y, intersection.width, intersection.height, intersection.x, intersection.y, null));
            }
        }
        return writableRaster;
    }
    
    public WritableRaster copyData(WritableRaster writableRaster) {
        Rectangle rectangle;
        if (writableRaster == null) {
            rectangle = this.getBounds();
            writableRaster = Raster.createWritableRaster(this.sampleModel.createCompatibleSampleModel(this.width, this.height), new Point(this.minX, this.minY));
        }
        else {
            rectangle = writableRaster.getBounds();
        }
        final int xToTileX = this.XToTileX(rectangle.x);
        final int yToTileY = this.YToTileY(rectangle.y);
        final int xToTileX2 = this.XToTileX(rectangle.x + rectangle.width - 1);
        for (int yToTileY2 = this.YToTileY(rectangle.y + rectangle.height - 1), i = yToTileY; i <= yToTileY2; ++i) {
            for (int j = xToTileX; j <= xToTileX2; ++j) {
                final Raster tile = this.getTile(j, i);
                final Rectangle intersection = rectangle.intersection(tile.getBounds());
                writableRaster.setDataElements(0, 0, tile.createChild(intersection.x, intersection.y, intersection.width, intersection.height, intersection.x, intersection.y, null));
            }
        }
        return writableRaster;
    }
}
