// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt;

import java.awt.geom.NoninvertibleTransformException;
import java.awt.PaintContext;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.image.ColorModel;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.awt.geom.Point2D;

public final class RadialGradientPaint extends MultipleGradientPaint
{
    private Point2D focus;
    private Point2D center;
    private float radius;
    
    public RadialGradientPaint(final float n, final float n2, final float n3, final float[] array, final Color[] array2) {
        this(n, n2, n3, n, n2, array, array2);
    }
    
    public RadialGradientPaint(final Point2D point2D, final float n, final float[] array, final Color[] array2) {
        this(point2D, n, point2D, array, array2);
    }
    
    public RadialGradientPaint(final float x, final float y, final float n, final float x2, final float y2, final float[] array, final Color[] array2) {
        this(new Point2D.Float(x, y), n, new Point2D.Float(x2, y2), array, array2, RadialGradientPaint.NO_CYCLE, RadialGradientPaint.SRGB);
    }
    
    public RadialGradientPaint(final Point2D point2D, final float n, final Point2D point2D2, final float[] array, final Color[] array2) {
        this(point2D, n, point2D2, array, array2, RadialGradientPaint.NO_CYCLE, RadialGradientPaint.SRGB);
    }
    
    public RadialGradientPaint(final Point2D point2D, final float n, final Point2D point2D2, final float[] array, final Color[] array2, final CycleMethodEnum cycleMethodEnum, final ColorSpaceEnum colorSpaceEnum) {
        this(point2D, n, point2D2, array, array2, cycleMethodEnum, colorSpaceEnum, new AffineTransform());
    }
    
    public RadialGradientPaint(final Point2D point2D, final float radius, final Point2D point2D2, final float[] array, final Color[] array2, final CycleMethodEnum cycleMethodEnum, final ColorSpaceEnum colorSpaceEnum, final AffineTransform affineTransform) {
        super(array, array2, cycleMethodEnum, colorSpaceEnum, affineTransform);
        if (point2D == null) {
            throw new NullPointerException("Center point should not be null.");
        }
        if (point2D2 == null) {
            throw new NullPointerException("Focus point should not be null.");
        }
        if (radius <= 0.0f) {
            throw new IllegalArgumentException("radius should be greater than zero");
        }
        this.center = (Point2D)point2D.clone();
        this.focus = (Point2D)point2D2.clone();
        this.radius = radius;
    }
    
    public RadialGradientPaint(final Rectangle2D rectangle2D, final float[] array, final Color[] array2) {
        this((float)rectangle2D.getX() + (float)rectangle2D.getWidth() / 2.0f, (float)rectangle2D.getY() + (float)rectangle2D.getWidth() / 2.0f, (float)rectangle2D.getWidth() / 2.0f, array, array2);
    }
    
    public PaintContext createContext(final ColorModel colorModel, final Rectangle rectangle, final Rectangle2D rectangle2D, AffineTransform tx, final RenderingHints renderingHints) {
        tx = new AffineTransform(tx);
        tx.concatenate(this.gradientTransform);
        try {
            return new RadialGradientPaintContext(colorModel, rectangle, rectangle2D, tx, renderingHints, (float)this.center.getX(), (float)this.center.getY(), this.radius, (float)this.focus.getX(), (float)this.focus.getY(), this.fractions, this.colors, this.cycleMethod, this.colorSpace);
        }
        catch (NoninvertibleTransformException ex) {
            throw new IllegalArgumentException("transform should be invertible");
        }
    }
    
    public Point2D getCenterPoint() {
        return new Point2D.Double(this.center.getX(), this.center.getY());
    }
    
    public Point2D getFocusPoint() {
        return new Point2D.Double(this.focus.getX(), this.focus.getY());
    }
    
    public float getRadius() {
        return this.radius;
    }
}
