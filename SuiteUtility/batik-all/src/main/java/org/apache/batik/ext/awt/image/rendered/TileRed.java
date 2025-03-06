// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.DataBufferInt;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.Graphics2D;
import org.apache.batik.util.HaltingThread;
import java.awt.Color;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import java.util.Map;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.Raster;
import java.awt.Point;
import java.awt.image.WritableRaster;
import java.awt.image.RenderedImage;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class TileRed extends AbstractRed implements TileGenerator
{
    static final AffineTransform IDENTITY;
    Rectangle tiledRegion;
    int xStep;
    int yStep;
    TileStore tiles;
    private RenderingHints hints;
    final boolean is_INT_PACK;
    RenderedImage tile;
    WritableRaster raster;
    
    public TileRed(final RenderedImage renderedImage, final Rectangle rectangle) {
        this(renderedImage, rectangle, renderedImage.getWidth(), renderedImage.getHeight(), null);
    }
    
    public TileRed(final RenderedImage renderedImage, final Rectangle rectangle, final RenderingHints renderingHints) {
        this(renderedImage, rectangle, renderedImage.getWidth(), renderedImage.getHeight(), renderingHints);
    }
    
    public TileRed(final RenderedImage renderedImage, final Rectangle rectangle, final int n, final int n2) {
        this(renderedImage, rectangle, n, n2, null);
    }
    
    public TileRed(final RenderedImage renderedImage, final Rectangle tiledRegion, final int n, final int n2, final RenderingHints hints) {
        this.tile = null;
        this.raster = null;
        if (tiledRegion == null) {
            throw new IllegalArgumentException();
        }
        if (renderedImage == null) {
            throw new IllegalArgumentException();
        }
        this.tiledRegion = tiledRegion;
        this.xStep = n;
        this.yStep = n2;
        this.hints = hints;
        SampleModel sm = fixSampleModel(renderedImage, n, n2, tiledRegion.width, tiledRegion.height);
        final ColorModel colorModel = renderedImage.getColorModel();
        final double n3 = AbstractTiledRed.getDefaultTileSize();
        final double n4 = n3 * n3;
        final double n5 = n * (double)n2;
        if (16.1 * n4 > n5) {
            int n6 = n;
            int n7 = n2;
            if (4.0 * n5 <= n4) {
                final int n8 = (int)Math.ceil(Math.sqrt(n4 / n5));
                n6 *= n8;
                n7 *= n8;
            }
            sm = sm.createCompatibleSampleModel(n6, n7);
            this.raster = Raster.createWritableRaster(sm, new Point(renderedImage.getMinX(), renderedImage.getMinY()));
        }
        this.is_INT_PACK = GraphicsUtil.is_INT_PACK_Data(sm, false);
        this.init((CachableRed)null, tiledRegion, colorModel, sm, renderedImage.getMinX(), renderedImage.getMinY(), null);
        if (this.raster != null) {
            this.fillRasterFrom(this.raster.createWritableChild(renderedImage.getMinX(), renderedImage.getMinY(), n, n2, renderedImage.getMinX(), renderedImage.getMinY(), null), renderedImage);
            this.fillOutRaster(this.raster);
        }
        else {
            this.tile = new TileCacheRed(GraphicsUtil.wrap(renderedImage));
        }
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        final int n = (int)Math.floor(writableRaster.getMinX() / this.xStep) * this.xStep;
        final int n2 = (int)Math.floor(writableRaster.getMinY() / this.yStep) * this.yStep;
        final int n3 = writableRaster.getMinX() - n;
        final int n4 = writableRaster.getMinY() - n2;
        final int xTile = this.getXTile(n3);
        final int yTile = this.getYTile(n4);
        final int xTile2 = this.getXTile(n3 + writableRaster.getWidth() - 1);
        for (int yTile2 = this.getYTile(n4 + writableRaster.getHeight() - 1), i = yTile; i <= yTile2; ++i) {
            for (int j = xTile; j <= xTile2; ++j) {
                final Raster tile = this.getTile(j, i);
                final Raster child = tile.createChild(tile.getMinX(), tile.getMinY(), tile.getWidth(), tile.getHeight(), tile.getMinX() + n, tile.getMinY() + n2, null);
                if (this.is_INT_PACK) {
                    GraphicsUtil.copyData_INT_PACK(child, writableRaster);
                }
                else {
                    GraphicsUtil.copyData_FALLBACK(child, writableRaster);
                }
            }
        }
        return writableRaster;
    }
    
    public Raster getTile(final int n, final int n2) {
        if (this.raster != null) {
            return this.raster.createTranslatedChild(this.tileGridXOff + n * this.tileWidth, this.tileGridYOff + n2 * this.tileHeight);
        }
        return this.genTile(n, n2);
    }
    
    public Raster genTile(final int n, final int n2) {
        final int n3 = this.tileGridXOff + n * this.tileWidth;
        final int n4 = this.tileGridYOff + n2 * this.tileHeight;
        if (this.raster != null) {
            return this.raster.createTranslatedChild(n3, n4);
        }
        final WritableRaster writableRaster = Raster.createWritableRaster(this.sm, new Point(n3, n4));
        this.fillRasterFrom(writableRaster, this.tile);
        return writableRaster;
    }
    
    public WritableRaster fillRasterFrom(final WritableRaster writableRaster, final RenderedImage renderedImage) {
        final ColorModel colorModel = this.getColorModel();
        final Graphics2D graphics = GraphicsUtil.createGraphics(new BufferedImage(colorModel, writableRaster.createWritableTranslatedChild(0, 0), colorModel.isAlphaPremultiplied(), null), this.hints);
        final int minX = writableRaster.getMinX();
        final int minY = writableRaster.getMinY();
        final int width = writableRaster.getWidth();
        final int height = writableRaster.getHeight();
        graphics.setComposite(AlphaComposite.Clear);
        graphics.setColor(new Color(0, 0, 0, 0));
        graphics.fillRect(0, 0, width, height);
        graphics.setComposite(AlphaComposite.SrcOver);
        graphics.translate(-minX, -minY);
        final int n = renderedImage.getMinX() + renderedImage.getWidth() - 1;
        final int n2 = renderedImage.getMinY() + renderedImage.getHeight() - 1;
        final int n3 = (int)Math.ceil((minX - n) / this.xStep) * this.xStep;
        final int n4 = (int)Math.ceil((minY - n2) / this.yStep) * this.yStep;
        graphics.translate(n3, n4);
        int i = n3 - writableRaster.getMinX() + renderedImage.getMinX();
        int j = n4 - writableRaster.getMinY() + renderedImage.getMinY();
        final int n5 = i;
        while (j < height) {
            if (HaltingThread.hasBeenHalted()) {
                return writableRaster;
            }
            while (i < width) {
                GraphicsUtil.drawImage(graphics, renderedImage);
                i += this.xStep;
                graphics.translate(this.xStep, 0);
            }
            j += this.yStep;
            graphics.translate(n5 - i, this.yStep);
            i = n5;
        }
        return writableRaster;
    }
    
    protected void fillOutRaster(final WritableRaster writableRaster) {
        if (this.is_INT_PACK) {
            this.fillOutRaster_INT_PACK(writableRaster);
        }
        else {
            this.fillOutRaster_FALLBACK(writableRaster);
        }
    }
    
    protected void fillOutRaster_INT_PACK(final WritableRaster writableRaster) {
        final int minX = writableRaster.getMinX();
        final int minY = writableRaster.getMinY();
        final int width = writableRaster.getWidth();
        final int height = writableRaster.getHeight();
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        final int[] array = dataBufferInt.getBankData()[0];
        final int n = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(minX - writableRaster.getSampleModelTranslateX(), minY - writableRaster.getSampleModelTranslateY());
        for (int xStep = this.xStep, i = this.xStep; i < width; i += xStep, xStep *= 2) {
            int n2 = xStep;
            if (i + n2 > width) {
                n2 = width - i;
            }
            if (n2 >= 128) {
                int n3 = n;
                int n4 = n + i;
                for (int j = 0; j < this.yStep; ++j) {
                    System.arraycopy(array, n3, array, n4, n2);
                    n3 += scanlineStride;
                    n4 += scanlineStride;
                }
            }
            else {
                int n5 = n;
                int n6 = n + i;
                for (int k = 0; k < this.yStep; ++k) {
                    int n7;
                    int l;
                    int n8;
                    for (n7 = n5, l = n5 + (n2 - 1), n8 = n6 + (n2 - 1); l >= n7; array[n8--] = array[l--]) {}
                    n5 = l + (scanlineStride + 1);
                    n6 = n8 + (scanlineStride + 1);
                }
            }
        }
        for (int yStep = this.yStep, yStep2 = this.yStep; yStep2 < height; yStep2 += yStep, yStep *= 2) {
            int n9 = yStep;
            if (yStep2 + n9 > height) {
                n9 = height - yStep2;
            }
            System.arraycopy(array, n, array, n + yStep2 * scanlineStride, n9 * scanlineStride);
        }
    }
    
    protected void fillOutRaster_FALLBACK(final WritableRaster writableRaster) {
        final int width = writableRaster.getWidth();
        final int height = writableRaster.getHeight();
        Object o = null;
        int x2;
        for (int xStep = this.xStep, i = this.xStep; i < width; i = x2 + xStep, xStep *= 4) {
            int n = xStep;
            if (i + n > width) {
                n = width - i;
            }
            o = writableRaster.getDataElements(0, 0, n, this.yStep, o);
            writableRaster.setDataElements(i, 0, n, this.yStep, o);
            final int x = i + n;
            if (x >= width) {
                break;
            }
            if (x + n > width) {
                n = width - x;
            }
            writableRaster.setDataElements(x, 0, n, this.yStep, o);
            x2 = x + n;
            if (x2 >= width) {
                break;
            }
            if (x2 + n > width) {
                n = width - x2;
            }
            writableRaster.setDataElements(x2, 0, n, this.yStep, o);
        }
        int n2;
        int y2;
        for (int yStep = this.yStep, j = this.yStep; j < height; j = y2 + n2 + yStep, yStep *= 4) {
            n2 = yStep;
            if (j + n2 > height) {
                n2 = height - j;
            }
            o = writableRaster.getDataElements(0, 0, width, n2, o);
            writableRaster.setDataElements(0, j, width, n2, o);
            final int y = j + n2;
            if (n2 >= height) {
                break;
            }
            if (y + n2 > height) {
                n2 = height - y;
            }
            writableRaster.setDataElements(0, y, width, n2, o);
            y2 = y + n2;
            if (n2 >= height) {
                break;
            }
            if (y2 + n2 > height) {
                n2 = height - y2;
            }
            writableRaster.setDataElements(0, y2, width, n2, o);
        }
    }
    
    protected static SampleModel fixSampleModel(final RenderedImage renderedImage, final int n, final int n2, final int n3, final int n4) {
        final int defaultTileSize = AbstractTiledRed.getDefaultTileSize();
        final SampleModel sampleModel = renderedImage.getSampleModel();
        int width = sampleModel.getWidth();
        if (width < defaultTileSize) {
            width = defaultTileSize;
        }
        if (width > n) {
            width = n;
        }
        int height = sampleModel.getHeight();
        if (height < defaultTileSize) {
            height = defaultTileSize;
        }
        if (height > n2) {
            height = n2;
        }
        return sampleModel.createCompatibleSampleModel(width, height);
    }
    
    static {
        IDENTITY = new AffineTransform();
    }
}
