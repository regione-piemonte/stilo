// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import org.apache.batik.ext.awt.image.rendered.AbstractRed;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.AffineRed;
import org.apache.batik.ext.awt.image.rendered.DisplacementMapRed;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import java.util.List;
import org.apache.batik.ext.awt.image.ARGBChannel;

public class DisplacementMapRable8Bit extends AbstractColorInterpolationRable implements DisplacementMapRable
{
    private double scale;
    private ARGBChannel xChannelSelector;
    private ARGBChannel yChannelSelector;
    
    public DisplacementMapRable8Bit(final List sources, final double scale, final ARGBChannel xChannelSelector, final ARGBChannel yChannelSelector) {
        this.setSources(sources);
        this.setScale(scale);
        this.setXChannelSelector(xChannelSelector);
        this.setYChannelSelector(yChannelSelector);
    }
    
    public Rectangle2D getBounds2D() {
        return this.getSources().get(0).getBounds2D();
    }
    
    public void setScale(final double scale) {
        this.touch();
        this.scale = scale;
    }
    
    public double getScale() {
        return this.scale;
    }
    
    public void setSources(final List list) {
        if (list.size() != 2) {
            throw new IllegalArgumentException();
        }
        this.init(list, null);
    }
    
    public void setXChannelSelector(final ARGBChannel xChannelSelector) {
        if (xChannelSelector == null) {
            throw new IllegalArgumentException();
        }
        this.touch();
        this.xChannelSelector = xChannelSelector;
    }
    
    public ARGBChannel getXChannelSelector() {
        return this.xChannelSelector;
    }
    
    public void setYChannelSelector(final ARGBChannel yChannelSelector) {
        if (yChannelSelector == null) {
            throw new IllegalArgumentException();
        }
        this.touch();
        this.yChannelSelector = yChannelSelector;
    }
    
    public ARGBChannel getYChannelSelector() {
        return this.yChannelSelector;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        final Filter filter = this.getSources().get(0);
        final Filter filter2 = this.getSources().get(1);
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
        final float n = (float)(this.scale * sqrt);
        final float n2 = (float)(this.scale * sqrt2);
        if (n == 0.0f && n2 == 0.0f) {
            return filter.createRendering(renderContext);
        }
        final AffineTransform scaleInstance = AffineTransform.getScaleInstance(sqrt, sqrt2);
        Shape shape = renderContext.getAreaOfInterest();
        if (shape == null) {
            shape = this.getBounds2D();
        }
        final Rectangle2D bounds2D = shape.getBounds2D();
        final RenderedImage rendering = filter2.createRendering(new RenderContext(scaleInstance, bounds2D, renderingHints));
        if (rendering == null) {
            return null;
        }
        final Rectangle2D.Double double1 = new Rectangle2D.Double(bounds2D.getX() - this.scale / 2.0, bounds2D.getY() - this.scale / 2.0, bounds2D.getWidth() + this.scale, bounds2D.getHeight() + this.scale);
        final Rectangle2D bounds2D2 = filter.getBounds2D();
        if (!double1.intersects(bounds2D2)) {
            return null;
        }
        final RenderedImage rendering2 = filter.createRendering(new RenderContext(scaleInstance, double1.createIntersection(bounds2D2), renderingHints));
        if (rendering2 == null) {
            return null;
        }
        AbstractRed abstractRed = new DisplacementMapRed(GraphicsUtil.wrap(rendering2), GraphicsUtil.wrap(this.convertSourceCS(rendering)), this.xChannelSelector, this.yChannelSelector, n, n2, renderingHints);
        final AffineTransform affineTransform = new AffineTransform(scaleX / sqrt, shearY / sqrt, shearX / sqrt2, scaleY / sqrt2, translateX, translateY);
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
