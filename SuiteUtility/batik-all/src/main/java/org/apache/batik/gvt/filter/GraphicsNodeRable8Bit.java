// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.filter;

import org.apache.batik.ext.awt.image.rendered.TranslateRed;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.awt.color.ColorSpace;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import org.apache.batik.ext.awt.image.SVGComposite;
import java.awt.Graphics2D;
import java.awt.Shape;
import org.apache.batik.ext.awt.image.renderable.Filter;
import java.util.Map;
import org.apache.batik.gvt.GraphicsNode;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.image.renderable.PaintRable;
import org.apache.batik.ext.awt.image.renderable.AbstractRable;

public class GraphicsNodeRable8Bit extends AbstractRable implements GraphicsNodeRable, PaintRable
{
    private AffineTransform cachedGn2dev;
    private AffineTransform cachedUsr2dev;
    private CachableRed cachedRed;
    private Rectangle2D cachedBounds;
    private boolean usePrimitivePaint;
    private GraphicsNode node;
    
    public boolean getUsePrimitivePaint() {
        return this.usePrimitivePaint;
    }
    
    public void setUsePrimitivePaint(final boolean usePrimitivePaint) {
        this.usePrimitivePaint = usePrimitivePaint;
    }
    
    public GraphicsNode getGraphicsNode() {
        return this.node;
    }
    
    public void setGraphicsNode(final GraphicsNode node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        this.node = node;
    }
    
    public void clearCache() {
        this.cachedRed = null;
        this.cachedUsr2dev = null;
        this.cachedGn2dev = null;
        this.cachedBounds = null;
    }
    
    public GraphicsNodeRable8Bit(final GraphicsNode node) {
        this.cachedGn2dev = null;
        this.cachedUsr2dev = null;
        this.cachedRed = null;
        this.cachedBounds = null;
        this.usePrimitivePaint = true;
        if (node == null) {
            throw new IllegalArgumentException();
        }
        this.node = node;
        this.usePrimitivePaint = true;
    }
    
    public GraphicsNodeRable8Bit(final GraphicsNode node, final Map map) {
        super((Filter)null, map);
        this.cachedGn2dev = null;
        this.cachedUsr2dev = null;
        this.cachedRed = null;
        this.cachedBounds = null;
        this.usePrimitivePaint = true;
        if (node == null) {
            throw new IllegalArgumentException();
        }
        this.node = node;
        this.usePrimitivePaint = true;
    }
    
    public GraphicsNodeRable8Bit(final GraphicsNode node, final boolean usePrimitivePaint) {
        this.cachedGn2dev = null;
        this.cachedUsr2dev = null;
        this.cachedRed = null;
        this.cachedBounds = null;
        this.usePrimitivePaint = true;
        if (node == null) {
            throw new IllegalArgumentException();
        }
        this.node = node;
        this.usePrimitivePaint = usePrimitivePaint;
    }
    
    public Rectangle2D getBounds2D() {
        if (this.usePrimitivePaint) {
            final Rectangle2D primitiveBounds = this.node.getPrimitiveBounds();
            if (primitiveBounds == null) {
                return new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);
            }
            return (Rectangle2D)primitiveBounds.clone();
        }
        else {
            Rectangle2D pSrc = this.node.getBounds();
            if (pSrc == null) {
                return new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);
            }
            final AffineTransform transform = this.node.getTransform();
            if (transform != null) {
                pSrc = transform.createTransformedShape(pSrc).getBounds2D();
            }
            return pSrc;
        }
    }
    
    public boolean isDynamic() {
        return false;
    }
    
    public boolean paintRable(final Graphics2D graphics2D) {
        if (!SVGComposite.OVER.equals(graphics2D.getComposite())) {
            return false;
        }
        final ColorSpace destinationColorSpace = GraphicsUtil.getDestinationColorSpace(graphics2D);
        if (destinationColorSpace == null || destinationColorSpace != ColorSpace.getInstance(1000)) {
            return false;
        }
        final GraphicsNode graphicsNode = this.getGraphicsNode();
        if (this.getUsePrimitivePaint()) {
            graphicsNode.primitivePaint(graphics2D);
        }
        else {
            graphicsNode.paint(graphics2D);
        }
        return true;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        AffineTransform transform = renderContext.getTransform();
        AffineTransform cachedGn2dev;
        if (transform == null) {
            transform = (cachedGn2dev = new AffineTransform());
        }
        else {
            cachedGn2dev = (AffineTransform)transform.clone();
        }
        final AffineTransform transform2 = this.node.getTransform();
        if (transform2 != null) {
            cachedGn2dev.concatenate(transform2);
        }
        final Rectangle2D bounds2D = this.getBounds2D();
        if (this.cachedBounds != null && this.cachedGn2dev != null && this.cachedBounds.equals(bounds2D) && cachedGn2dev.getScaleX() == this.cachedGn2dev.getScaleX() && cachedGn2dev.getScaleY() == this.cachedGn2dev.getScaleY() && cachedGn2dev.getShearX() == this.cachedGn2dev.getShearX() && cachedGn2dev.getShearY() == this.cachedGn2dev.getShearY()) {
            final double n = transform.getTranslateX() - this.cachedUsr2dev.getTranslateX();
            final double n2 = transform.getTranslateY() - this.cachedUsr2dev.getTranslateY();
            if (n == 0.0 && n2 == 0.0) {
                return this.cachedRed;
            }
            if (n == (int)n && n2 == (int)n2) {
                return new TranslateRed(this.cachedRed, (int)Math.round(this.cachedRed.getMinX() + n), (int)Math.round(this.cachedRed.getMinY() + n2));
            }
        }
        if (bounds2D.getWidth() > 0.0 && bounds2D.getHeight() > 0.0) {
            this.cachedUsr2dev = (AffineTransform)transform.clone();
            this.cachedGn2dev = cachedGn2dev;
            this.cachedBounds = bounds2D;
            return this.cachedRed = new GraphicsNodeRed8Bit(this.node, transform, this.usePrimitivePaint, renderContext.getRenderingHints());
        }
        this.cachedUsr2dev = null;
        this.cachedGn2dev = null;
        this.cachedBounds = null;
        this.cachedRed = null;
        return null;
    }
}
