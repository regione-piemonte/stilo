// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.color.ColorSpace;
import java.awt.Rectangle;
import org.apache.batik.ext.awt.image.rendered.TurbulencePatternRed;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.awt.geom.Rectangle2D;

public class TurbulenceRable8Bit extends AbstractColorInterpolationRable implements TurbulenceRable
{
    int seed;
    int numOctaves;
    double baseFreqX;
    double baseFreqY;
    boolean stitched;
    boolean fractalNoise;
    Rectangle2D region;
    
    public TurbulenceRable8Bit(final Rectangle2D region) {
        this.seed = 0;
        this.numOctaves = 1;
        this.baseFreqX = 0.0;
        this.baseFreqY = 0.0;
        this.stitched = false;
        this.fractalNoise = false;
        this.region = region;
    }
    
    public TurbulenceRable8Bit(final Rectangle2D region, final int seed, final int numOctaves, final double baseFreqX, final double baseFreqY, final boolean stitched, final boolean fractalNoise) {
        this.seed = 0;
        this.numOctaves = 1;
        this.baseFreqX = 0.0;
        this.baseFreqY = 0.0;
        this.stitched = false;
        this.fractalNoise = false;
        this.seed = seed;
        this.numOctaves = numOctaves;
        this.baseFreqX = baseFreqX;
        this.baseFreqY = baseFreqY;
        this.stitched = stitched;
        this.fractalNoise = fractalNoise;
        this.region = region;
    }
    
    public Rectangle2D getTurbulenceRegion() {
        return (Rectangle2D)this.region.clone();
    }
    
    public Rectangle2D getBounds2D() {
        return (Rectangle2D)this.region.clone();
    }
    
    public int getSeed() {
        return this.seed;
    }
    
    public int getNumOctaves() {
        return this.numOctaves;
    }
    
    public double getBaseFrequencyX() {
        return this.baseFreqX;
    }
    
    public double getBaseFrequencyY() {
        return this.baseFreqY;
    }
    
    public boolean isStitched() {
        return this.stitched;
    }
    
    public boolean isFractalNoise() {
        return this.fractalNoise;
    }
    
    public void setTurbulenceRegion(final Rectangle2D region) {
        this.touch();
        this.region = region;
    }
    
    public void setSeed(final int seed) {
        this.touch();
        this.seed = seed;
    }
    
    public void setNumOctaves(final int numOctaves) {
        this.touch();
        this.numOctaves = numOctaves;
    }
    
    public void setBaseFrequencyX(final double baseFreqX) {
        this.touch();
        this.baseFreqX = baseFreqX;
    }
    
    public void setBaseFrequencyY(final double baseFreqY) {
        this.touch();
        this.baseFreqY = baseFreqY;
    }
    
    public void setStitched(final boolean stitched) {
        this.touch();
        this.stitched = stitched;
    }
    
    public void setFractalNoise(final boolean fractalNoise) {
        this.touch();
        this.fractalNoise = fractalNoise;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        final Shape areaOfInterest = renderContext.getAreaOfInterest();
        Rectangle2D pSrc;
        if (areaOfInterest == null) {
            pSrc = this.getBounds2D();
        }
        else {
            final Rectangle2D bounds2D = this.getBounds2D();
            pSrc = areaOfInterest.getBounds2D();
            if (!pSrc.intersects(bounds2D)) {
                return null;
            }
            Rectangle2D.intersect(pSrc, bounds2D, pSrc);
        }
        final AffineTransform transform = renderContext.getTransform();
        final Rectangle bounds = transform.createTransformedShape(pSrc).getBounds();
        if (bounds.width <= 0 || bounds.height <= 0) {
            return null;
        }
        final ColorSpace operationColorSpace = this.getOperationColorSpace();
        Rectangle2D rectangle2D = null;
        if (this.stitched) {
            rectangle2D = (Rectangle2D)this.region.clone();
        }
        AffineTransform inverse = new AffineTransform();
        try {
            inverse = transform.createInverse();
        }
        catch (NoninvertibleTransformException ex) {}
        return new TurbulencePatternRed(this.baseFreqX, this.baseFreqY, this.numOctaves, this.seed, this.fractalNoise, rectangle2D, inverse, bounds, operationColorSpace, true);
    }
}
