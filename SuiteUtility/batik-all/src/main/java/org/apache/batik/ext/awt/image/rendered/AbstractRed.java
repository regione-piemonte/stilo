// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.WritableRaster;
import java.awt.Point;
import java.awt.image.Raster;
import java.awt.Shape;
import java.util.Set;
import java.util.Iterator;
import java.awt.image.RenderedImage;
import java.util.Collection;
import java.util.List;
import java.awt.image.ComponentColorModel;
import java.awt.color.ColorSpace;
import java.util.HashMap;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import java.util.Map;
import java.util.Vector;
import java.awt.Rectangle;

public abstract class AbstractRed implements CachableRed
{
    protected Rectangle bounds;
    protected Vector srcs;
    protected Map props;
    protected SampleModel sm;
    protected ColorModel cm;
    protected int tileGridXOff;
    protected int tileGridYOff;
    protected int tileWidth;
    protected int tileHeight;
    protected int minTileX;
    protected int minTileY;
    protected int numXTiles;
    protected int numYTiles;
    
    protected AbstractRed() {
    }
    
    protected AbstractRed(final Rectangle rectangle, final Map map) {
        this.init((CachableRed)null, rectangle, null, null, rectangle.x, rectangle.y, map);
    }
    
    protected AbstractRed(final CachableRed cachableRed, final Map map) {
        this.init(cachableRed, cachableRed.getBounds(), cachableRed.getColorModel(), cachableRed.getSampleModel(), cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), map);
    }
    
    protected AbstractRed(final CachableRed cachableRed, final Rectangle rectangle, final Map map) {
        this.init(cachableRed, rectangle, cachableRed.getColorModel(), cachableRed.getSampleModel(), cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), map);
    }
    
    protected AbstractRed(final CachableRed cachableRed, final Rectangle rectangle, final ColorModel colorModel, final SampleModel sampleModel, final Map map) {
        this.init(cachableRed, rectangle, colorModel, sampleModel, (cachableRed == null) ? 0 : cachableRed.getTileGridXOffset(), (cachableRed == null) ? 0 : cachableRed.getTileGridYOffset(), map);
    }
    
    protected AbstractRed(final CachableRed cachableRed, final Rectangle rectangle, final ColorModel colorModel, final SampleModel sampleModel, final int n, final int n2, final Map map) {
        this.init(cachableRed, rectangle, colorModel, sampleModel, n, n2, map);
    }
    
    protected void init(final CachableRed e, Rectangle bounds, ColorModel colorModel, SampleModel sm, final int tileGridXOff, final int tileGridYOff, final Map map) {
        this.srcs = new Vector(1);
        if (e != null) {
            this.srcs.add(e);
            if (bounds == null) {
                bounds = e.getBounds();
            }
            if (colorModel == null) {
                colorModel = e.getColorModel();
            }
            if (sm == null) {
                sm = e.getSampleModel();
            }
        }
        this.bounds = bounds;
        this.tileGridXOff = tileGridXOff;
        this.tileGridYOff = tileGridYOff;
        this.props = new HashMap();
        if (map != null) {
            this.props.putAll(map);
        }
        if (colorModel == null) {
            colorModel = new ComponentColorModel(ColorSpace.getInstance(1003), new int[] { 8 }, false, false, 1, 0);
        }
        this.cm = colorModel;
        if (sm == null) {
            sm = colorModel.createCompatibleSampleModel(bounds.width, bounds.height);
        }
        this.sm = sm;
        this.updateTileGridInfo();
    }
    
    protected AbstractRed(final List list, final Rectangle rectangle, final Map map) {
        this.init(list, rectangle, null, null, rectangle.x, rectangle.y, map);
    }
    
    protected AbstractRed(final List list, final Rectangle rectangle, final ColorModel colorModel, final SampleModel sampleModel, final Map map) {
        this.init(list, rectangle, colorModel, sampleModel, rectangle.x, rectangle.y, map);
    }
    
    protected AbstractRed(final List list, final Rectangle rectangle, final ColorModel colorModel, final SampleModel sampleModel, final int n, final int n2, final Map map) {
        this.init(list, rectangle, colorModel, sampleModel, n, n2, map);
    }
    
    protected void init(final List c, Rectangle bounds, ColorModel colorModel, SampleModel sm, final int tileGridXOff, final int tileGridYOff, final Map map) {
        this.srcs = new Vector();
        if (c != null) {
            this.srcs.addAll(c);
        }
        if (c.size() != 0) {
            final CachableRed cachableRed = c.get(0);
            if (bounds == null) {
                bounds = cachableRed.getBounds();
            }
            if (colorModel == null) {
                colorModel = cachableRed.getColorModel();
            }
            if (sm == null) {
                sm = cachableRed.getSampleModel();
            }
        }
        this.bounds = bounds;
        this.tileGridXOff = tileGridXOff;
        this.tileGridYOff = tileGridYOff;
        this.props = new HashMap();
        if (map != null) {
            this.props.putAll(map);
        }
        if (colorModel == null) {
            colorModel = new ComponentColorModel(ColorSpace.getInstance(1003), new int[] { 8 }, false, false, 1, 0);
        }
        this.cm = colorModel;
        if (sm == null) {
            sm = colorModel.createCompatibleSampleModel(bounds.width, bounds.height);
        }
        this.sm = sm;
        this.updateTileGridInfo();
    }
    
    protected void updateTileGridInfo() {
        this.tileWidth = this.sm.getWidth();
        this.tileHeight = this.sm.getHeight();
        this.minTileX = this.getXTile(this.bounds.x);
        this.minTileY = this.getYTile(this.bounds.y);
        this.numXTiles = this.getXTile(this.bounds.x + this.bounds.width - 1) - this.minTileX + 1;
        this.numYTiles = this.getYTile(this.bounds.y + this.bounds.height - 1) - this.minTileY + 1;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(this.getMinX(), this.getMinY(), this.getWidth(), this.getHeight());
    }
    
    public Vector getSources() {
        return this.srcs;
    }
    
    public ColorModel getColorModel() {
        return this.cm;
    }
    
    public SampleModel getSampleModel() {
        return this.sm;
    }
    
    public int getMinX() {
        return this.bounds.x;
    }
    
    public int getMinY() {
        return this.bounds.y;
    }
    
    public int getWidth() {
        return this.bounds.width;
    }
    
    public int getHeight() {
        return this.bounds.height;
    }
    
    public int getTileWidth() {
        return this.tileWidth;
    }
    
    public int getTileHeight() {
        return this.tileHeight;
    }
    
    public int getTileGridXOffset() {
        return this.tileGridXOff;
    }
    
    public int getTileGridYOffset() {
        return this.tileGridYOff;
    }
    
    public int getMinTileX() {
        return this.minTileX;
    }
    
    public int getMinTileY() {
        return this.minTileY;
    }
    
    public int getNumXTiles() {
        return this.numXTiles;
    }
    
    public int getNumYTiles() {
        return this.numYTiles;
    }
    
    public Object getProperty(final String s) {
        final Object value = this.props.get(s);
        if (value != null) {
            return value;
        }
        final Iterator<RenderedImage> iterator = this.srcs.iterator();
        while (iterator.hasNext()) {
            final Object property = iterator.next().getProperty(s);
            if (property != null) {
                return property;
            }
        }
        return null;
    }
    
    public String[] getPropertyNames() {
        final Set keySet = this.props.keySet();
        String[] array = new String[keySet.size()];
        keySet.toArray(array);
        final Iterator<RenderedImage> iterator = this.srcs.iterator();
        while (iterator.hasNext()) {
            final String[] propertyNames = iterator.next().getPropertyNames();
            if (propertyNames.length != 0) {
                final String[] array2 = new String[array.length + propertyNames.length];
                System.arraycopy(array, 0, array2, 0, array.length);
                System.arraycopy(propertyNames, 0, array2, array.length, propertyNames.length);
                array = array2;
            }
        }
        return array;
    }
    
    public Shape getDependencyRegion(final int n, final Rectangle rectangle) {
        if (n < 0 || n > this.srcs.size()) {
            throw new IndexOutOfBoundsException("Nonexistant source requested.");
        }
        if (!rectangle.intersects(this.bounds)) {
            return new Rectangle();
        }
        return rectangle.intersection(this.bounds);
    }
    
    public Shape getDirtyRegion(final int n, final Rectangle rectangle) {
        if (n != 0) {
            throw new IndexOutOfBoundsException("Nonexistant source requested.");
        }
        if (!rectangle.intersects(this.bounds)) {
            return new Rectangle();
        }
        return rectangle.intersection(this.bounds);
    }
    
    public Raster getTile(final int n, final int n2) {
        return this.copyData(this.makeTile(n, n2));
    }
    
    public Raster getData() {
        return this.getData(this.bounds);
    }
    
    public Raster getData(final Rectangle rectangle) {
        return this.copyData(Raster.createWritableRaster(this.sm.createCompatibleSampleModel(rectangle.width, rectangle.height), new Point(rectangle.x, rectangle.y)));
    }
    
    public final int getXTile(final int n) {
        final int n2 = n - this.tileGridXOff;
        if (n2 >= 0) {
            return n2 / this.tileWidth;
        }
        return (n2 - this.tileWidth + 1) / this.tileWidth;
    }
    
    public final int getYTile(final int n) {
        final int n2 = n - this.tileGridYOff;
        if (n2 >= 0) {
            return n2 / this.tileHeight;
        }
        return (n2 - this.tileHeight + 1) / this.tileHeight;
    }
    
    public void copyToRaster(final WritableRaster writableRaster) {
        int n = this.getXTile(writableRaster.getMinX());
        int n2 = this.getYTile(writableRaster.getMinY());
        int xTile = this.getXTile(writableRaster.getMinX() + writableRaster.getWidth() - 1);
        int yTile = this.getYTile(writableRaster.getMinY() + writableRaster.getHeight() - 1);
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
        for (int i = n2; i <= yTile; ++i) {
            for (int j = n; j <= xTile; ++j) {
                final Raster tile = this.getTile(j, i);
                if (is_INT_PACK_Data) {
                    GraphicsUtil.copyData_INT_PACK(tile, writableRaster);
                }
                else {
                    GraphicsUtil.copyData_FALLBACK(tile, writableRaster);
                }
            }
        }
    }
    
    public WritableRaster makeTile(final int i, final int j) {
        if (i < this.minTileX || i >= this.minTileX + this.numXTiles || j < this.minTileY || j >= this.minTileY + this.numYTiles) {
            throw new IndexOutOfBoundsException("Requested Tile (" + i + ',' + j + ") lies outside the bounds of image");
        }
        WritableRaster writableRaster = Raster.createWritableRaster(this.sm, new Point(this.tileGridXOff + i * this.tileWidth, this.tileGridYOff + j * this.tileHeight));
        int n = writableRaster.getMinX();
        int n2 = writableRaster.getMinY();
        int n3 = n + writableRaster.getWidth() - 1;
        int n4 = n2 + writableRaster.getHeight() - 1;
        if (n < this.bounds.x || n3 >= this.bounds.x + this.bounds.width || n2 < this.bounds.y || n4 >= this.bounds.y + this.bounds.height) {
            if (n < this.bounds.x) {
                n = this.bounds.x;
            }
            if (n2 < this.bounds.y) {
                n2 = this.bounds.y;
            }
            if (n3 >= this.bounds.x + this.bounds.width) {
                n3 = this.bounds.x + this.bounds.width - 1;
            }
            if (n4 >= this.bounds.y + this.bounds.height) {
                n4 = this.bounds.y + this.bounds.height - 1;
            }
            writableRaster = writableRaster.createWritableChild(n, n2, n3 - n + 1, n4 - n2 + 1, n, n2, null);
        }
        return writableRaster;
    }
    
    public static void copyBand(final Raster raster, final int b, final WritableRaster writableRaster, final int b2) {
        final Rectangle intersection = new Rectangle(raster.getMinX(), raster.getMinY(), raster.getWidth(), raster.getHeight()).intersection(new Rectangle(writableRaster.getMinX(), writableRaster.getMinY(), writableRaster.getWidth(), writableRaster.getHeight()));
        int[] samples = null;
        for (int i = intersection.y; i < intersection.y + intersection.height; ++i) {
            samples = raster.getSamples(intersection.x, i, intersection.width, 1, b, samples);
            writableRaster.setSamples(intersection.x, i, intersection.width, 1, b2, samples);
        }
    }
}
