// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.image.rendered.PadRed;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.awt.image.renderable.RenderableImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Shape;
import org.apache.batik.ext.awt.image.SVGComposite;
import java.awt.Graphics2D;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.PadMode;

public class PadRable8Bit extends AbstractRable implements PadRable, PaintRable
{
    PadMode padMode;
    Rectangle2D padRect;
    
    public PadRable8Bit(final Filter filter, final Rectangle2D padRect, final PadMode padMode) {
        super.init(filter, null);
        this.padRect = padRect;
        this.padMode = padMode;
    }
    
    public Filter getSource() {
        return this.srcs.get(0);
    }
    
    public void setSource(final Filter filter) {
        super.init(filter, null);
    }
    
    public Rectangle2D getBounds2D() {
        return (Rectangle2D)this.padRect.clone();
    }
    
    public void setPadRect(final Rectangle2D padRect) {
        this.touch();
        this.padRect = padRect;
    }
    
    public Rectangle2D getPadRect() {
        return (Rectangle2D)this.padRect.clone();
    }
    
    public void setPadMode(final PadMode padMode) {
        this.touch();
        this.padMode = padMode;
    }
    
    public PadMode getPadMode() {
        return this.padMode;
    }
    
    public boolean paintRable(final Graphics2D graphics2D) {
        if (!SVGComposite.OVER.equals(graphics2D.getComposite())) {
            return false;
        }
        if (this.getPadMode() != PadMode.ZERO_PAD) {
            return false;
        }
        final Rectangle2D padRect = this.getPadRect();
        final Shape clip = graphics2D.getClip();
        graphics2D.clip(padRect);
        GraphicsUtil.drawImage(graphics2D, this.getSource());
        graphics2D.setClip(clip);
        return true;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        RenderingHints renderingHints = renderContext.getRenderingHints();
        if (renderingHints == null) {
            renderingHints = new RenderingHints(null);
        }
        final Filter source = this.getSource();
        Shape shape = renderContext.getAreaOfInterest();
        if (shape == null) {
            shape = this.getBounds2D();
        }
        final AffineTransform transform = renderContext.getTransform();
        final Rectangle2D bounds2D = source.getBounds2D();
        final Rectangle2D bounds2D2 = this.getBounds2D();
        final Rectangle2D bounds2D3 = shape.getBounds2D();
        if (!bounds2D3.intersects(bounds2D2)) {
            return null;
        }
        Rectangle2D.intersect(bounds2D3, bounds2D2, bounds2D3);
        RenderedImage rendering = null;
        if (bounds2D3.intersects(bounds2D)) {
            final Rectangle2D aoi = (Rectangle2D)bounds2D.clone();
            Rectangle2D.intersect(aoi, bounds2D3, aoi);
            rendering = source.createRendering(new RenderContext(transform, aoi, renderingHints));
        }
        if (rendering == null) {
            rendering = new BufferedImage(1, 1, 2);
        }
        return new PadRed(GraphicsUtil.wrap(rendering), transform.createTransformedShape(bounds2D3).getBounds2D().getBounds(), this.padMode, renderingHints);
    }
    
    public Shape getDependencyRegion(final int n, final Rectangle2D rectangle2D) {
        if (n != 0) {
            throw new IndexOutOfBoundsException("Affine only has one input");
        }
        final Rectangle2D bounds2D = this.getSource().getBounds2D();
        if (!bounds2D.intersects(rectangle2D)) {
            return new Rectangle2D.Float();
        }
        Rectangle2D.intersect(bounds2D, rectangle2D, bounds2D);
        final Rectangle2D bounds2D2 = this.getBounds2D();
        if (!bounds2D.intersects(bounds2D2)) {
            return new Rectangle2D.Float();
        }
        Rectangle2D.intersect(bounds2D, bounds2D2, bounds2D);
        return bounds2D;
    }
    
    public Shape getDirtyRegion(final int n, Rectangle2D rectangle2D) {
        if (n != 0) {
            throw new IndexOutOfBoundsException("Affine only has one input");
        }
        rectangle2D = (Rectangle2D)rectangle2D.clone();
        final Rectangle2D bounds2D = this.getBounds2D();
        if (!rectangle2D.intersects(bounds2D)) {
            return new Rectangle2D.Float();
        }
        Rectangle2D.intersect(rectangle2D, bounds2D, rectangle2D);
        return rectangle2D;
    }
}
