// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.util.Map;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.awt.image.renderable.RenderableImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Graphics2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

public class AffineRable8Bit extends AbstractRable implements AffineRable, PaintRable
{
    AffineTransform affine;
    AffineTransform invAffine;
    
    public AffineRable8Bit(final Filter filter, final AffineTransform affine) {
        this.init(filter);
        this.setAffine(affine);
    }
    
    public Rectangle2D getBounds2D() {
        return this.affine.createTransformedShape(this.getSource().getBounds2D()).getBounds2D();
    }
    
    public Filter getSource() {
        return this.srcs.get(0);
    }
    
    public void setSource(final Filter filter) {
        this.init(filter);
    }
    
    public void setAffine(final AffineTransform affine) {
        this.touch();
        this.affine = affine;
        try {
            this.invAffine = affine.createInverse();
        }
        catch (NoninvertibleTransformException ex) {
            this.invAffine = null;
        }
    }
    
    public AffineTransform getAffine() {
        return (AffineTransform)this.affine.clone();
    }
    
    public boolean paintRable(final Graphics2D graphics2D) {
        final AffineTransform transform = graphics2D.getTransform();
        graphics2D.transform(this.getAffine());
        GraphicsUtil.drawImage(graphics2D, this.getSource());
        graphics2D.setTransform(transform);
        return true;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        if (this.invAffine == null) {
            return null;
        }
        RenderingHints renderingHints = renderContext.getRenderingHints();
        if (renderingHints == null) {
            renderingHints = new RenderingHints(null);
        }
        Shape shape = renderContext.getAreaOfInterest();
        if (shape != null) {
            shape = this.invAffine.createTransformedShape(shape);
        }
        final AffineTransform transform = renderContext.getTransform();
        transform.concatenate(this.affine);
        return this.getSource().createRendering(new RenderContext(transform, shape, renderingHints));
    }
    
    public Shape getDependencyRegion(final int n, final Rectangle2D pSrc) {
        if (n != 0) {
            throw new IndexOutOfBoundsException("Affine only has one input");
        }
        if (this.invAffine == null) {
            return null;
        }
        return this.invAffine.createTransformedShape(pSrc);
    }
    
    public Shape getDirtyRegion(final int n, final Rectangle2D pSrc) {
        if (n != 0) {
            throw new IndexOutOfBoundsException("Affine only has one input");
        }
        return this.affine.createTransformedShape(pSrc);
    }
}
