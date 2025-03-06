// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.Rectangle;
import org.apache.batik.ext.awt.image.rendered.AffineRed;
import java.awt.geom.Rectangle2D;
import java.lang.ref.SoftReference;
import org.apache.batik.ext.awt.image.rendered.TileCacheRed;
import java.awt.image.renderable.RenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;
import java.awt.RenderingHints;
import org.apache.batik.ext.awt.image.SVGComposite;
import java.util.ListIterator;
import java.util.List;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import org.apache.batik.ext.awt.image.CompositeRule;
import java.awt.Shape;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.Vector;
import java.awt.image.renderable.RenderableImage;
import java.util.Map;
import java.lang.ref.Reference;

public class FilterResRable8Bit extends AbstractRable implements FilterResRable, PaintRable
{
    private int filterResolutionX;
    private int filterResolutionY;
    Reference resRed;
    float resScale;
    
    public FilterResRable8Bit() {
        this.filterResolutionX = -1;
        this.filterResolutionY = -1;
        this.resRed = null;
        this.resScale = 0.0f;
    }
    
    public FilterResRable8Bit(final Filter filter, final int filterResolutionX, final int filterResolutionY) {
        this.filterResolutionX = -1;
        this.filterResolutionY = -1;
        this.resRed = null;
        this.resScale = 0.0f;
        this.init(filter, null);
        this.setFilterResolutionX(filterResolutionX);
        this.setFilterResolutionY(filterResolutionY);
    }
    
    public Filter getSource() {
        return this.srcs.get(0);
    }
    
    public void setSource(final Filter filter) {
        this.init(filter, null);
    }
    
    public int getFilterResolutionX() {
        return this.filterResolutionX;
    }
    
    public void setFilterResolutionX(final int filterResolutionX) {
        if (filterResolutionX < 0) {
            throw new IllegalArgumentException();
        }
        this.touch();
        this.filterResolutionX = filterResolutionX;
    }
    
    public int getFilterResolutionY() {
        return this.filterResolutionY;
    }
    
    public void setFilterResolutionY(final int filterResolutionY) {
        this.touch();
        this.filterResolutionY = filterResolutionY;
    }
    
    public boolean allPaintRable(final RenderableImage renderableImage) {
        if (!(renderableImage instanceof PaintRable)) {
            return false;
        }
        final Vector<RenderableImage> sources = renderableImage.getSources();
        if (sources == null) {
            return true;
        }
        final Iterator<Object> iterator = sources.iterator();
        while (iterator.hasNext()) {
            if (!this.allPaintRable(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean distributeAcross(final RenderableImage renderableImage, final Graphics2D graphics2D) {
        if (renderableImage instanceof PadRable) {
            final PadRable padRable = (PadRable)renderableImage;
            final Shape clip = graphics2D.getClip();
            graphics2D.clip(padRable.getPadRect());
            final boolean distributeAcross = this.distributeAcross(padRable.getSource(), graphics2D);
            graphics2D.setClip(clip);
            return distributeAcross;
        }
        if (!(renderableImage instanceof CompositeRable)) {
            return false;
        }
        final CompositeRable compositeRable = (CompositeRable)renderableImage;
        if (compositeRable.getCompositeRule() != CompositeRule.OVER) {
            return false;
        }
        final Vector<RenderableImage> sources = compositeRable.getSources();
        if (sources == null) {
            return true;
        }
        final ListIterator<Object> listIterator = sources.listIterator(sources.size());
        while (listIterator.hasPrevious()) {
            if (!this.allPaintRable(listIterator.previous())) {
                listIterator.next();
                break;
            }
        }
        if (!listIterator.hasPrevious()) {
            GraphicsUtil.drawImage(graphics2D, compositeRable);
            return true;
        }
        if (!listIterator.hasNext()) {
            return false;
        }
        GraphicsUtil.drawImage(graphics2D, new FilterResRable8Bit(new CompositeRable8Bit(sources.subList(0, listIterator.nextIndex()), compositeRable.getCompositeRule(), compositeRable.isColorSpaceLinear()), this.getFilterResolutionX(), this.getFilterResolutionY()));
        while (listIterator.hasNext()) {
            final PaintRable paintRable = listIterator.next();
            if (!paintRable.paintRable(graphics2D)) {
                GraphicsUtil.drawImage(graphics2D, new FilterResRable8Bit((Filter)paintRable, this.getFilterResolutionX(), this.getFilterResolutionY()));
            }
        }
        return true;
    }
    
    public boolean paintRable(final Graphics2D graphics2D) {
        return SVGComposite.OVER.equals(graphics2D.getComposite()) && this.distributeAcross(this.getSource(), graphics2D);
    }
    
    private float getResScale() {
        return this.resScale;
    }
    
    private RenderedImage getResRed(final RenderingHints hints) {
        final Rectangle2D bounds2D = this.getBounds2D();
        final float resScale = (float)Math.min(this.getFilterResolutionX() / bounds2D.getWidth(), this.getFilterResolutionY() / bounds2D.getHeight());
        if (resScale == this.resScale) {
            final RenderedImage renderedImage = this.resRed.get();
            if (renderedImage != null) {
                return renderedImage;
            }
        }
        final TileCacheRed referent = new TileCacheRed(GraphicsUtil.wrap(this.getSource().createRendering(new RenderContext(AffineTransform.getScaleInstance(resScale, resScale), null, hints))));
        this.resScale = resScale;
        this.resRed = new SoftReference(referent);
        return referent;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        AffineTransform transform = renderContext.getTransform();
        if (transform == null) {
            transform = new AffineTransform();
        }
        final RenderingHints renderingHints = renderContext.getRenderingHints();
        final int filterResolutionX = this.getFilterResolutionX();
        final int filterResolutionY = this.getFilterResolutionY();
        if (filterResolutionX <= 0 || filterResolutionY == 0) {
            return null;
        }
        final Rectangle bounds = transform.createTransformedShape(this.getBounds2D()).getBounds();
        float n = 1.0f;
        if (filterResolutionX < bounds.width) {
            n = filterResolutionX / (float)bounds.width;
        }
        float n2 = 1.0f;
        if (filterResolutionY < 0) {
            n2 = n;
        }
        else if (filterResolutionY < bounds.height) {
            n2 = filterResolutionY / (float)bounds.height;
        }
        if (n >= 1.0f && n2 >= 1.0f) {
            return this.getSource().createRendering(renderContext);
        }
        final RenderedImage resRed = this.getResRed(renderingHints);
        final float resScale = this.getResScale();
        return new AffineRed(GraphicsUtil.wrap(resRed), new AffineTransform(transform.getScaleX() / resScale, transform.getShearY() / resScale, transform.getShearX() / resScale, transform.getScaleY() / resScale, transform.getTranslateX(), transform.getTranslateY()), renderingHints);
    }
}
