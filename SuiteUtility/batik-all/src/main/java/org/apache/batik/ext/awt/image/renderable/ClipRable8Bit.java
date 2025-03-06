// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.image.rendered.PadRed;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.MultiplyAlphaRed;
import org.apache.batik.ext.awt.image.rendered.BufferedImageCachableRed;
import org.apache.batik.ext.awt.image.rendered.RenderedImageCachableRed;
import java.awt.Paint;
import java.awt.Color;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.awt.Shape;

public class ClipRable8Bit extends AbstractRable implements ClipRable
{
    protected boolean useAA;
    protected Shape clipPath;
    
    public ClipRable8Bit(final Filter filter, final Shape clipPath) {
        super(filter, null);
        this.setClipPath(clipPath);
        this.setUseAntialiasedClip(false);
    }
    
    public ClipRable8Bit(final Filter filter, final Shape clipPath, final boolean useAntialiasedClip) {
        super(filter, null);
        this.setClipPath(clipPath);
        this.setUseAntialiasedClip(useAntialiasedClip);
    }
    
    public void setSource(final Filter filter) {
        this.init(filter, null);
    }
    
    public Filter getSource() {
        return this.getSources().get(0);
    }
    
    public void setUseAntialiasedClip(final boolean useAA) {
        this.touch();
        this.useAA = useAA;
    }
    
    public boolean getUseAntialiasedClip() {
        return this.useAA;
    }
    
    public void setClipPath(final Shape clipPath) {
        this.touch();
        this.clipPath = clipPath;
    }
    
    public Shape getClipPath() {
        return this.clipPath;
    }
    
    public Rectangle2D getBounds2D() {
        return this.getSource().getBounds2D();
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        final AffineTransform transform = renderContext.getTransform();
        RenderingHints renderingHints = renderContext.getRenderingHints();
        if (renderingHints == null) {
            renderingHints = new RenderingHints(null);
        }
        Shape pSrc = renderContext.getAreaOfInterest();
        if (pSrc == null) {
            pSrc = this.getBounds2D();
        }
        final Rectangle2D bounds2D = this.getBounds2D();
        final Rectangle2D bounds2D2 = this.clipPath.getBounds2D();
        final Rectangle2D bounds2D3 = pSrc.getBounds2D();
        if (!bounds2D.intersects(bounds2D2)) {
            return null;
        }
        Rectangle2D.intersect(bounds2D, bounds2D2, bounds2D);
        if (!bounds2D.intersects(bounds2D3)) {
            return null;
        }
        Rectangle2D.intersect(bounds2D, pSrc.getBounds2D(), bounds2D);
        final Rectangle bounds = transform.createTransformedShape(bounds2D).getBounds();
        if (bounds.width == 0 || bounds.height == 0) {
            return null;
        }
        final BufferedImage bufferedImage = new BufferedImage(bounds.width, bounds.height, 10);
        final Shape transformedShape = transform.createTransformedShape(this.getClipPath());
        final Rectangle bounds2 = transform.createTransformedShape(pSrc).getBounds();
        final Graphics2D graphics = GraphicsUtil.createGraphics(bufferedImage, renderingHints);
        graphics.translate(-bounds.x, -bounds.y);
        graphics.setPaint(Color.white);
        graphics.fill(transformedShape);
        graphics.dispose();
        return new PadRed(new MultiplyAlphaRed(RenderedImageCachableRed.wrap(this.getSource().createRendering(new RenderContext(transform, bounds2D, renderingHints))), new BufferedImageCachableRed(bufferedImage, bounds.x, bounds.y)), bounds2, PadMode.ZERO_PAD, renderingHints);
    }
}
