// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.filter;

import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import org.apache.batik.ext.awt.image.renderable.AffineRable8Bit;
import java.util.List;
import org.apache.batik.ext.awt.image.renderable.CompositeRable8Bit;
import org.apache.batik.ext.awt.image.CompositeRule;
import java.util.ArrayList;
import org.apache.batik.ext.awt.image.renderable.Filter;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.AffineTransform;
import java.util.Iterator;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.ext.awt.image.renderable.AbstractRable;

public class BackgroundRable8Bit extends AbstractRable
{
    private GraphicsNode node;
    
    public GraphicsNode getGraphicsNode() {
        return this.node;
    }
    
    public void setGraphicsNode(final GraphicsNode node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        this.node = node;
    }
    
    public BackgroundRable8Bit(final GraphicsNode node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        this.node = node;
    }
    
    static Rectangle2D addBounds(final CompositeGraphicsNode compositeGraphicsNode, final GraphicsNode graphicsNode, final Rectangle2D rectangle2D) {
        final Iterator<GraphicsNode> iterator = compositeGraphicsNode.getChildren().iterator();
        Rectangle2D r = null;
        while (iterator.hasNext()) {
            final GraphicsNode graphicsNode2 = iterator.next();
            if (graphicsNode2 == graphicsNode) {
                break;
            }
            Rectangle2D rectangle2D2 = graphicsNode2.getBounds();
            final AffineTransform transform = graphicsNode2.getTransform();
            if (transform != null) {
                rectangle2D2 = transform.createTransformedShape(rectangle2D2).getBounds2D();
            }
            if (r == null) {
                r = (Rectangle2D)rectangle2D2.clone();
            }
            else {
                r.add(rectangle2D2);
            }
        }
        if (r == null) {
            if (rectangle2D == null) {
                return CompositeGraphicsNode.VIEWPORT;
            }
            return rectangle2D;
        }
        else {
            if (rectangle2D == null) {
                return r;
            }
            rectangle2D.add(r);
            return rectangle2D;
        }
    }
    
    static Rectangle2D getViewportBounds(final GraphicsNode graphicsNode, final GraphicsNode graphicsNode2) {
        Rectangle2D pSrc = null;
        if (graphicsNode instanceof CompositeGraphicsNode) {
            pSrc = ((CompositeGraphicsNode)graphicsNode).getBackgroundEnable();
        }
        if (pSrc == null) {
            pSrc = getViewportBounds(graphicsNode.getParent(), graphicsNode);
        }
        if (pSrc == null) {
            return null;
        }
        if (pSrc != CompositeGraphicsNode.VIEWPORT) {
            final AffineTransform transform = graphicsNode.getTransform();
            if (transform != null) {
                try {
                    pSrc = transform.createInverse().createTransformedShape(pSrc).getBounds2D();
                }
                catch (NoninvertibleTransformException ex) {
                    pSrc = null;
                }
            }
            if (graphicsNode2 != null) {
                pSrc = addBounds((CompositeGraphicsNode)graphicsNode, graphicsNode2, pSrc);
            }
            else {
                final Rectangle2D primitiveBounds = graphicsNode.getPrimitiveBounds();
                if (primitiveBounds != null) {
                    pSrc.add(primitiveBounds);
                }
            }
            return pSrc;
        }
        if (graphicsNode2 == null) {
            return (Rectangle2D)graphicsNode.getPrimitiveBounds().clone();
        }
        return addBounds((CompositeGraphicsNode)graphicsNode, graphicsNode2, null);
    }
    
    static Rectangle2D getBoundsRecursive(final GraphicsNode graphicsNode, final GraphicsNode graphicsNode2) {
        Rectangle2D backgroundEnable = null;
        if (graphicsNode == null) {
            return null;
        }
        if (graphicsNode instanceof CompositeGraphicsNode) {
            backgroundEnable = ((CompositeGraphicsNode)graphicsNode).getBackgroundEnable();
        }
        if (backgroundEnable != null) {
            return backgroundEnable;
        }
        Rectangle2D pSrc = getBoundsRecursive(graphicsNode.getParent(), graphicsNode);
        if (pSrc == null) {
            return new Rectangle2D.Float(0.0f, 0.0f, 0.0f, 0.0f);
        }
        if (pSrc == CompositeGraphicsNode.VIEWPORT) {
            return pSrc;
        }
        final AffineTransform transform = graphicsNode.getTransform();
        if (transform != null) {
            try {
                pSrc = transform.createInverse().createTransformedShape(pSrc).getBounds2D();
            }
            catch (NoninvertibleTransformException ex) {
                pSrc = null;
            }
        }
        return pSrc;
    }
    
    public Rectangle2D getBounds2D() {
        Rectangle2D rectangle2D = getBoundsRecursive(this.node, null);
        if (rectangle2D == CompositeGraphicsNode.VIEWPORT) {
            rectangle2D = getViewportBounds(this.node, null);
        }
        return rectangle2D;
    }
    
    public Filter getBackground(final GraphicsNode graphicsNode, final GraphicsNode graphicsNode2, final Rectangle2D rectangle2D) {
        if (graphicsNode == null) {
            throw new IllegalArgumentException("BackgroundImage requested yet no parent has 'enable-background:new'");
        }
        Rectangle2D backgroundEnable = null;
        if (graphicsNode instanceof CompositeGraphicsNode) {
            backgroundEnable = ((CompositeGraphicsNode)graphicsNode).getBackgroundEnable();
        }
        final ArrayList list = new ArrayList<Object>();
        if (backgroundEnable == null) {
            Rectangle2D bounds2D = rectangle2D;
            final AffineTransform transform = graphicsNode.getTransform();
            if (transform != null) {
                bounds2D = transform.createTransformedShape(rectangle2D).getBounds2D();
            }
            final Filter background = this.getBackground(graphicsNode.getParent(), graphicsNode, bounds2D);
            if (background != null && background.getBounds2D().intersects(rectangle2D)) {
                list.add(background);
            }
        }
        if (graphicsNode2 != null) {
            for (final GraphicsNode graphicsNode3 : ((CompositeGraphicsNode)graphicsNode).getChildren()) {
                if (graphicsNode3 == graphicsNode2) {
                    break;
                }
                Rectangle2D rectangle2D2 = graphicsNode3.getBounds();
                final AffineTransform transform2 = graphicsNode3.getTransform();
                if (transform2 != null) {
                    rectangle2D2 = transform2.createTransformedShape(rectangle2D2).getBounds2D();
                }
                if (!rectangle2D.intersects(rectangle2D2)) {
                    continue;
                }
                list.add(graphicsNode3.getEnableBackgroundGraphicsNodeRable(true));
            }
        }
        if (list.size() == 0) {
            return null;
        }
        Filter filter;
        if (list.size() == 1) {
            filter = list.get(0);
        }
        else {
            filter = new CompositeRable8Bit(list, CompositeRule.OVER, false);
        }
        if (graphicsNode2 != null) {
            final AffineTransform transform3 = graphicsNode2.getTransform();
            if (transform3 != null) {
                try {
                    filter = new AffineRable8Bit(filter, transform3.createInverse());
                }
                catch (NoninvertibleTransformException ex) {
                    filter = null;
                }
            }
        }
        return filter;
    }
    
    public boolean isDynamic() {
        return false;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        final Rectangle2D bounds2D = this.getBounds2D();
        final Shape areaOfInterest = renderContext.getAreaOfInterest();
        if (areaOfInterest != null) {
            final Rectangle2D bounds2D2 = areaOfInterest.getBounds2D();
            if (!bounds2D.intersects(bounds2D2)) {
                return null;
            }
            Rectangle2D.intersect(bounds2D, bounds2D2, bounds2D);
        }
        final Filter background = this.getBackground(this.node, null, bounds2D);
        if (background == null) {
            return null;
        }
        return new PadRable8Bit(background, bounds2D, PadMode.ZERO_PAD).createRendering(new RenderContext(renderContext.getTransform(), bounds2D, renderContext.getRenderingHints()));
    }
}
