// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import org.apache.batik.ext.awt.image.rendered.CachableRed;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.image.rendered.FilterAsAlphaRed;
import org.apache.batik.ext.awt.image.rendered.RenderedImageCachableRed;
import org.apache.batik.ext.awt.ColorSpaceHintKey;
import org.apache.batik.ext.awt.RenderingHintsKeyExt;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Map;

public class FilterAsAlphaRable extends AbstractRable
{
    public FilterAsAlphaRable(final Filter filter) {
        super(filter, null);
    }
    
    public Filter getSource() {
        return this.getSources().get(0);
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
        Shape aoi = renderContext.getAreaOfInterest();
        if (aoi == null) {
            aoi = this.getBounds2D();
        }
        renderingHints.put(RenderingHintsKeyExt.KEY_COLORSPACE, ColorSpaceHintKey.VALUE_COLORSPACE_ALPHA_CONVERT);
        final RenderedImage rendering = this.getSource().createRendering(new RenderContext(transform, aoi, renderingHints));
        if (rendering == null) {
            return null;
        }
        final CachableRed wrap = RenderedImageCachableRed.wrap(rendering);
        if (wrap.getProperty("org.apache.batik.gvt.filter.Colorspace") == ColorSpaceHintKey.VALUE_COLORSPACE_ALPHA_CONVERT) {
            return wrap;
        }
        return new FilterAsAlphaRed(wrap);
    }
}
