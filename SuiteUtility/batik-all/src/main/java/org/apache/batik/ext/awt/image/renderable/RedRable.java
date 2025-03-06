// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.geom.AffineTransform;
import java.awt.Rectangle;
import java.awt.Shape;
import org.apache.batik.ext.awt.image.rendered.AffineRed;
import org.apache.batik.ext.awt.image.rendered.TranslateRed;
import java.util.Map;
import java.awt.RenderingHints;
import java.awt.image.renderable.RenderContext;
import java.awt.image.RenderedImage;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.rendered.CachableRed;

public class RedRable extends AbstractRable
{
    CachableRed src;
    
    public RedRable(final CachableRed src) {
        super((Filter)null);
        this.src = src;
    }
    
    public CachableRed getSource() {
        return this.src;
    }
    
    public Object getProperty(final String s) {
        return this.src.getProperty(s);
    }
    
    public String[] getPropertyNames() {
        return this.src.getPropertyNames();
    }
    
    public Rectangle2D getBounds2D() {
        return this.getSource().getBounds();
    }
    
    public RenderedImage createDefaultRendering() {
        return this.getSource();
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        RenderingHints renderingHints = renderContext.getRenderingHints();
        if (renderingHints == null) {
            renderingHints = new RenderingHints(null);
        }
        final Shape areaOfInterest = renderContext.getAreaOfInterest();
        Rectangle rectangle;
        if (areaOfInterest != null) {
            rectangle = areaOfInterest.getBounds();
        }
        else {
            rectangle = this.getBounds2D().getBounds();
        }
        final AffineTransform transform = renderContext.getTransform();
        final CachableRed source = this.getSource();
        if (!rectangle.intersects(source.getBounds())) {
            return null;
        }
        if (transform.isIdentity()) {
            return source;
        }
        if (transform.getScaleX() == 1.0 && transform.getScaleY() == 1.0 && transform.getShearX() == 0.0 && transform.getShearY() == 0.0) {
            final int n = (int)(source.getMinX() + transform.getTranslateX());
            final int n2 = (int)(source.getMinY() + transform.getTranslateY());
            final double n3 = n - (source.getMinX() + transform.getTranslateX());
            final double n4 = n2 - (source.getMinY() + transform.getTranslateY());
            if (n3 > -1.0E-4 && n3 < 1.0E-4 && n4 > -1.0E-4 && n4 < 1.0E-4) {
                return new TranslateRed(source, n, n2);
            }
        }
        return new AffineRed(source, transform, renderingHints);
    }
}
