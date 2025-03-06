// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.color.ColorSpace;
import java.awt.image.ComponentColorModel;
import java.awt.image.DirectColorModel;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.AffineTransformOp;
import java.awt.Point;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.SampleModel;
import java.awt.image.ColorModel;
import java.awt.Rectangle;
import java.util.Map;
import java.awt.geom.Point2D;
import java.awt.Shape;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.AffineTransform;
import java.awt.RenderingHints;

public class AffineRed extends AbstractRed
{
    RenderingHints hints;
    AffineTransform src2me;
    AffineTransform me2src;
    
    public AffineTransform getTransform() {
        return (AffineTransform)this.src2me.clone();
    }
    
    public CachableRed getSource() {
        return this.getSources().get(0);
    }
    
    public AffineRed(final CachableRed cachableRed, final AffineTransform src2me, final RenderingHints hints) {
        this.src2me = src2me;
        this.hints = hints;
        try {
            this.me2src = src2me.createInverse();
        }
        catch (NoninvertibleTransformException ex) {
            this.me2src = null;
        }
        final Rectangle bounds = src2me.createTransformedShape(cachableRed.getBounds()).getBounds();
        final ColorModel fixColorModel = fixColorModel(cachableRed);
        final SampleModel fixSampleModel = this.fixSampleModel(cachableRed, fixColorModel, bounds);
        final Point2D transform = src2me.transform(new Point2D.Float((float)cachableRed.getTileGridXOffset(), (float)cachableRed.getTileGridYOffset()), null);
        this.init(cachableRed, bounds, fixColorModel, fixSampleModel, (int)transform.getX(), (int)transform.getY(), null);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        PadRed.ZeroRecter.getZeroRecter(writableRaster).zeroRect(new Rectangle(writableRaster.getMinX(), writableRaster.getMinY(), writableRaster.getWidth(), writableRaster.getHeight()));
        this.genRect(writableRaster);
        return writableRaster;
    }
    
    public Raster getTile(final int n, final int n2) {
        if (this.me2src == null) {
            return null;
        }
        final WritableRaster writableRaster = Raster.createWritableRaster(this.sm, new Point(this.tileGridXOff + n * this.tileWidth, this.tileGridYOff + n2 * this.tileHeight));
        this.genRect(writableRaster);
        return writableRaster;
    }
    
    public void genRect(final WritableRaster writableRaster) {
        if (this.me2src == null) {
            return;
        }
        final Rectangle bounds = this.me2src.createTransformedShape(writableRaster.getBounds()).getBounds();
        bounds.setBounds(bounds.x - 1, bounds.y - 1, bounds.width + 2, bounds.height + 2);
        final CachableRed cachableRed = this.getSources().get(0);
        if (!bounds.intersects(cachableRed.getBounds())) {
            return;
        }
        final Raster data = cachableRed.getData(bounds.intersection(cachableRed.getBounds()));
        if (data == null) {
            return;
        }
        final AffineTransform xform = (AffineTransform)this.src2me.clone();
        xform.concatenate(AffineTransform.getTranslateInstance(data.getMinX(), data.getMinY()));
        final Point2D transform = this.me2src.transform(new Point2D.Float((float)writableRaster.getMinX(), (float)writableRaster.getMinY()), null);
        final Point2D transform2 = xform.transform(new Point2D.Double(transform.getX() - data.getMinX(), transform.getY() - data.getMinY()), null);
        xform.preConcatenate(AffineTransform.getTranslateInstance(-transform2.getX(), -transform2.getY()));
        final AffineTransformOp affineTransformOp = new AffineTransformOp(xform, this.hints);
        final ColorModel colorModel = cachableRed.getColorModel();
        final ColorModel colorModel2 = this.getColorModel();
        final WritableRaster writableRaster2 = (WritableRaster)data;
        final ColorModel coerceData = GraphicsUtil.coerceData(writableRaster2, colorModel, true);
        affineTransformOp.filter(new BufferedImage(coerceData, writableRaster2.createWritableTranslatedChild(0, 0), coerceData.isAlphaPremultiplied(), null), new BufferedImage(colorModel2, writableRaster.createWritableTranslatedChild(0, 0), colorModel2.isAlphaPremultiplied(), null));
    }
    
    protected static ColorModel fixColorModel(final CachableRed cachableRed) {
        ColorModel colorModel = cachableRed.getColorModel();
        if (colorModel.hasAlpha()) {
            if (!colorModel.isAlphaPremultiplied()) {
                colorModel = GraphicsUtil.coerceColorModel(colorModel, true);
            }
            return colorModel;
        }
        final ColorSpace colorSpace = colorModel.getColorSpace();
        final int n = cachableRed.getSampleModel().getNumBands() + 1;
        if (n == 4) {
            final int[] array = new int[4];
            for (int i = 0; i < n - 1; ++i) {
                array[i] = 16711680 >> 8 * i;
            }
            array[3] = 255 << 8 * (n - 1);
            return new DirectColorModel(colorSpace, 8 * n, array[0], array[1], array[2], array[3], true, 3);
        }
        final int[] bits = new int[n];
        for (int j = 0; j < n; ++j) {
            bits[j] = 8;
        }
        return new ComponentColorModel(colorSpace, bits, true, true, 3, 3);
    }
    
    protected SampleModel fixSampleModel(final CachableRed cachableRed, final ColorModel colorModel, final Rectangle rectangle) {
        final SampleModel sampleModel = cachableRed.getSampleModel();
        final int defaultTileSize = AbstractTiledRed.getDefaultTileSize();
        int w = sampleModel.getWidth();
        if (w < defaultTileSize) {
            w = defaultTileSize;
        }
        if (w > rectangle.width) {
            w = rectangle.width;
        }
        int h = sampleModel.getHeight();
        if (h < defaultTileSize) {
            h = defaultTileSize;
        }
        if (h > rectangle.height) {
            h = rectangle.height;
        }
        if (w <= 0 || h <= 0) {
            w = 1;
            h = 1;
        }
        return colorModel.createCompatibleSampleModel(w, h);
    }
}
