// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.filter;

import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.MultiplyAlphaRed;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import org.apache.batik.ext.awt.image.rendered.RenderedImageCachableRed;
import org.apache.batik.ext.awt.image.renderable.FilterAsAlphaRable;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.util.Map;
import org.apache.batik.ext.awt.image.renderable.Filter;
import java.awt.geom.Rectangle2D;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.ext.awt.image.renderable.AbstractRable;

public class MaskRable8Bit extends AbstractRable implements Mask
{
    protected GraphicsNode mask;
    protected Rectangle2D filterRegion;
    
    public MaskRable8Bit(final Filter filter, final GraphicsNode maskNode, final Rectangle2D filterRegion) {
        super(filter, null);
        this.setMaskNode(maskNode);
        this.setFilterRegion(filterRegion);
    }
    
    public void setSource(final Filter filter) {
        this.init(filter, null);
    }
    
    public Filter getSource() {
        return this.getSources().get(0);
    }
    
    public Rectangle2D getFilterRegion() {
        return (Rectangle2D)this.filterRegion.clone();
    }
    
    public void setFilterRegion(final Rectangle2D filterRegion) {
        if (filterRegion == null) {
            throw new IllegalArgumentException();
        }
        this.filterRegion = filterRegion;
    }
    
    public void setMaskNode(final GraphicsNode mask) {
        this.touch();
        this.mask = mask;
    }
    
    public GraphicsNode getMaskNode() {
        return this.mask;
    }
    
    public Rectangle2D getBounds2D() {
        return (Rectangle2D)this.filterRegion.clone();
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        final RenderedImage rendering = new FilterAsAlphaRable(new PadRable8Bit(this.getMaskNode().getGraphicsNodeRable(true), this.getBounds2D(), PadMode.ZERO_PAD)).createRendering(renderContext);
        if (rendering == null) {
            return null;
        }
        final CachableRed wrap = RenderedImageCachableRed.wrap(rendering);
        final RenderedImage rendering2 = new PadRable8Bit(this.getSource(), this.getBounds2D(), PadMode.ZERO_PAD).createRendering(renderContext);
        if (rendering2 == null) {
            return null;
        }
        return new MultiplyAlphaRed(GraphicsUtil.convertToLsRGB(GraphicsUtil.wrap(rendering2)), wrap);
    }
}
