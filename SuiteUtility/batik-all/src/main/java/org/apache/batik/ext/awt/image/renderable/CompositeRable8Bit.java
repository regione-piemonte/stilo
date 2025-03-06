// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.image.rendered.CompositeRed;
import org.apache.batik.ext.awt.image.rendered.FloodRed;
import java.util.ArrayList;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.util.Iterator;
import java.awt.color.ColorSpace;
import java.awt.image.renderable.RenderableImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import org.apache.batik.ext.awt.image.SVGComposite;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.List;
import org.apache.batik.ext.awt.image.CompositeRule;

public class CompositeRable8Bit extends AbstractColorInterpolationRable implements CompositeRable, PaintRable
{
    protected CompositeRule rule;
    
    public CompositeRable8Bit(final List list, final CompositeRule rule, final boolean colorSpaceLinear) {
        super(list);
        this.setColorSpaceLinear(colorSpaceLinear);
        this.rule = rule;
    }
    
    public void setSources(final List list) {
        this.init(list, null);
    }
    
    public void setCompositeRule(final CompositeRule rule) {
        this.touch();
        this.rule = rule;
    }
    
    public CompositeRule getCompositeRule() {
        return this.rule;
    }
    
    public boolean paintRable(final Graphics2D graphics2D) {
        if (!SVGComposite.OVER.equals(graphics2D.getComposite())) {
            return false;
        }
        if (this.getCompositeRule() != CompositeRule.OVER) {
            return false;
        }
        final ColorSpace operationColorSpace = this.getOperationColorSpace();
        final ColorSpace destinationColorSpace = GraphicsUtil.getDestinationColorSpace(graphics2D);
        if (destinationColorSpace == null || destinationColorSpace != operationColorSpace) {
            return false;
        }
        final Iterator<Filter> iterator = this.getSources().iterator();
        while (iterator.hasNext()) {
            GraphicsUtil.drawImage(graphics2D, iterator.next());
        }
        return true;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        if (this.srcs.size() == 0) {
            return null;
        }
        RenderingHints renderingHints = renderContext.getRenderingHints();
        if (renderingHints == null) {
            renderingHints = new RenderingHints(null);
        }
        final AffineTransform transform = renderContext.getTransform();
        final Shape areaOfInterest = renderContext.getAreaOfInterest();
        Rectangle2D rectangle2D;
        if (areaOfInterest == null) {
            rectangle2D = this.getBounds2D();
        }
        else {
            rectangle2D = areaOfInterest.getBounds2D();
            final Rectangle2D bounds2D = this.getBounds2D();
            if (!bounds2D.intersects(rectangle2D)) {
                return null;
            }
            Rectangle2D.intersect(rectangle2D, bounds2D, rectangle2D);
        }
        final Rectangle bounds = transform.createTransformedShape(rectangle2D).getBounds();
        final RenderContext renderContext2 = new RenderContext(transform, rectangle2D, renderingHints);
        final ArrayList list = new ArrayList<FloodRed>();
        final Iterator<Filter> iterator = this.getSources().iterator();
        while (iterator.hasNext()) {
            final RenderedImage rendering = iterator.next().createRendering(renderContext2);
            if (rendering != null) {
                list.add((FloodRed)this.convertSourceCS(rendering));
            }
            else {
                switch (this.rule.getRule()) {
                    case 2: {
                        return null;
                    }
                    case 3: {
                        list.clear();
                        continue;
                    }
                    case 6: {
                        list.add(new FloodRed(bounds));
                        continue;
                    }
                    default: {
                        continue;
                    }
                }
            }
        }
        if (list.size() == 0) {
            return null;
        }
        return new CompositeRed(list, this.rule);
    }
}
