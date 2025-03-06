// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import org.apache.batik.ext.awt.image.rendered.AbstractRed;
import java.awt.image.ColorModel;
import org.apache.batik.ext.awt.image.rendered.AffineRed;
import org.apache.batik.ext.awt.image.rendered.BufferedImageCachableRed;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.Point;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.PadRed;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.ext.awt.image.rendered.RenderedImageCachableRed;
import java.awt.Shape;
import org.apache.batik.ext.awt.image.rendered.MorphologyOp;
import java.awt.geom.AffineTransform;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Map;

public class MorphologyRable8Bit extends AbstractRable implements MorphologyRable
{
    private double radiusX;
    private double radiusY;
    private boolean doDilation;
    
    public MorphologyRable8Bit(final Filter filter, final double radiusX, final double radiusY, final boolean doDilation) {
        super(filter, null);
        this.setRadiusX(radiusX);
        this.setRadiusY(radiusY);
        this.setDoDilation(doDilation);
    }
    
    public Filter getSource() {
        return this.getSources().get(0);
    }
    
    public void setSource(final Filter filter) {
        this.init(filter, null);
    }
    
    public Rectangle2D getBounds2D() {
        return this.getSource().getBounds2D();
    }
    
    public void setRadiusX(final double radiusX) {
        if (radiusX <= 0.0) {
            throw new IllegalArgumentException();
        }
        this.touch();
        this.radiusX = radiusX;
    }
    
    public void setRadiusY(final double radiusY) {
        if (radiusY <= 0.0) {
            throw new IllegalArgumentException();
        }
        this.touch();
        this.radiusY = radiusY;
    }
    
    public void setDoDilation(final boolean doDilation) {
        this.touch();
        this.doDilation = doDilation;
    }
    
    public boolean getDoDilation() {
        return this.doDilation;
    }
    
    public double getRadiusX() {
        return this.radiusX;
    }
    
    public double getRadiusY() {
        return this.radiusY;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        RenderingHints renderingHints = renderContext.getRenderingHints();
        if (renderingHints == null) {
            renderingHints = new RenderingHints(null);
        }
        final AffineTransform transform = renderContext.getTransform();
        final double scaleX = transform.getScaleX();
        final double scaleY = transform.getScaleY();
        final double shearX = transform.getShearX();
        final double shearY = transform.getShearY();
        final double translateX = transform.getTranslateX();
        final double translateY = transform.getTranslateY();
        final double sqrt = Math.sqrt(scaleX * scaleX + shearY * shearY);
        final double sqrt2 = Math.sqrt(scaleY * scaleY + shearX * shearX);
        final AffineTransform scaleInstance = AffineTransform.getScaleInstance(sqrt, sqrt2);
        final int n = (int)Math.round(this.radiusX * sqrt);
        final int n2 = (int)Math.round(this.radiusY * sqrt2);
        MorphologyOp morphologyOp = null;
        if (n > 0 && n2 > 0) {
            morphologyOp = new MorphologyOp(n, n2, this.doDilation);
        }
        final AffineTransform affineTransform = new AffineTransform(scaleX / sqrt, shearY / sqrt, shearX / sqrt2, scaleY / sqrt2, translateX, translateY);
        Shape shape = renderContext.getAreaOfInterest();
        if (shape == null) {
            shape = this.getBounds2D();
        }
        final Rectangle2D bounds2D = shape.getBounds2D();
        final RenderedImage rendering = this.getSource().createRendering(new RenderContext(scaleInstance, new Rectangle2D.Double(bounds2D.getX() - n / sqrt, bounds2D.getY() - n2 / sqrt2, bounds2D.getWidth() + 2 * n / sqrt, bounds2D.getHeight() + 2 * n2 / sqrt2), renderingHints));
        if (rendering == null) {
            return null;
        }
        final RenderedImageCachableRed renderedImageCachableRed = new RenderedImageCachableRed(rendering);
        final Rectangle2D bounds2D2 = scaleInstance.createTransformedShape(shape.getBounds2D()).getBounds2D();
        final PadRed padRed = new PadRed(renderedImageCachableRed, new Rectangle2D.Double(bounds2D2.getX() - n, bounds2D2.getY() - n2, bounds2D2.getWidth() + 2 * n, bounds2D2.getHeight() + 2 * n2).getBounds(), PadMode.ZERO_PAD, renderingHints);
        final ColorModel colorModel = rendering.getColorModel();
        final Raster data = padRed.getData();
        final BufferedImage bufferedImage = new BufferedImage(colorModel, Raster.createWritableRaster(data.getSampleModel(), data.getDataBuffer(), new Point(0, 0)), colorModel.isAlphaPremultiplied(), null);
        BufferedImage filter;
        if (morphologyOp != null) {
            filter = morphologyOp.filter(bufferedImage, null);
        }
        else {
            filter = bufferedImage;
        }
        AbstractRed abstractRed = new BufferedImageCachableRed(filter, padRed.getMinX(), padRed.getMinY());
        if (!affineTransform.isIdentity()) {
            abstractRed = new AffineRed(abstractRed, affineTransform, renderingHints);
        }
        return abstractRed;
    }
    
    public Shape getDependencyRegion(final int n, final Rectangle2D rectangle2D) {
        return super.getDependencyRegion(n, rectangle2D);
    }
    
    public Shape getDirtyRegion(final int n, final Rectangle2D rectangle2D) {
        return super.getDirtyRegion(n, rectangle2D);
    }
}
