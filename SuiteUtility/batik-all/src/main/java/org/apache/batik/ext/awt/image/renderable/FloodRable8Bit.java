// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.PadRed;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.ext.awt.image.rendered.FloodRed;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.Paint;

public class FloodRable8Bit extends AbstractRable implements FloodRable
{
    Paint floodPaint;
    Rectangle2D floodRegion;
    
    public FloodRable8Bit(final Rectangle2D floodRegion, final Paint floodPaint) {
        this.setFloodPaint(floodPaint);
        this.setFloodRegion(floodRegion);
    }
    
    public void setFloodPaint(final Paint floodPaint) {
        this.touch();
        if (floodPaint == null) {
            this.floodPaint = new Color(0, 0, 0, 0);
        }
        else {
            this.floodPaint = floodPaint;
        }
    }
    
    public Paint getFloodPaint() {
        return this.floodPaint;
    }
    
    public Rectangle2D getBounds2D() {
        return (Rectangle2D)this.floodRegion.clone();
    }
    
    public Rectangle2D getFloodRegion() {
        return (Rectangle2D)this.floodRegion.clone();
    }
    
    public void setFloodRegion(final Rectangle2D floodRegion) {
        if (floodRegion == null) {
            throw new IllegalArgumentException();
        }
        this.touch();
        this.floodRegion = floodRegion;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        AffineTransform transform = renderContext.getTransform();
        if (transform == null) {
            transform = new AffineTransform();
        }
        final Rectangle2D bounds2D = this.getBounds2D();
        final Shape areaOfInterest = renderContext.getAreaOfInterest();
        Rectangle2D bounds2D2;
        if (areaOfInterest == null) {
            bounds2D2 = bounds2D;
        }
        else {
            bounds2D2 = areaOfInterest.getBounds2D();
            if (!bounds2D.intersects(bounds2D2)) {
                return null;
            }
            Rectangle2D.intersect(bounds2D, bounds2D2, bounds2D2);
        }
        final Rectangle bounds = transform.createTransformedShape(bounds2D2).getBounds();
        if (bounds.width <= 0 || bounds.height <= 0) {
            return null;
        }
        return new PadRed(new FloodRed(bounds, this.getFloodPaint()), bounds, PadMode.ZERO_PAD, null);
    }
}
