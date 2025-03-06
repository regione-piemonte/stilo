// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt;

import java.awt.geom.NoninvertibleTransformException;
import java.awt.PaintContext;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.image.ColorModel;
import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.awt.geom.Point2D;

public final class LinearGradientPaint extends MultipleGradientPaint
{
    private Point2D start;
    private Point2D end;
    
    public LinearGradientPaint(final float x, final float y, final float x2, final float y2, final float[] array, final Color[] array2) {
        this(new Point2D.Float(x, y), new Point2D.Float(x2, y2), array, array2, LinearGradientPaint.NO_CYCLE, LinearGradientPaint.SRGB);
    }
    
    public LinearGradientPaint(final float x, final float y, final float x2, final float y2, final float[] array, final Color[] array2, final CycleMethodEnum cycleMethodEnum) {
        this(new Point2D.Float(x, y), new Point2D.Float(x2, y2), array, array2, cycleMethodEnum, LinearGradientPaint.SRGB);
    }
    
    public LinearGradientPaint(final Point2D point2D, final Point2D point2D2, final float[] array, final Color[] array2) {
        this(point2D, point2D2, array, array2, LinearGradientPaint.NO_CYCLE, LinearGradientPaint.SRGB);
    }
    
    public LinearGradientPaint(final Point2D point2D, final Point2D point2D2, final float[] array, final Color[] array2, final CycleMethodEnum cycleMethodEnum, final ColorSpaceEnum colorSpaceEnum) {
        this(point2D, point2D2, array, array2, cycleMethodEnum, colorSpaceEnum, new AffineTransform());
    }
    
    public LinearGradientPaint(final Point2D point2D, final Point2D obj, final float[] array, final Color[] array2, final CycleMethodEnum cycleMethodEnum, final ColorSpaceEnum colorSpaceEnum, final AffineTransform affineTransform) {
        super(array, array2, cycleMethodEnum, colorSpaceEnum, affineTransform);
        if (point2D == null || obj == null) {
            throw new NullPointerException("Start and end points must benon-null");
        }
        if (point2D.equals(obj)) {
            throw new IllegalArgumentException("Start point cannot equalendpoint");
        }
        this.start = (Point2D)point2D.clone();
        this.end = (Point2D)obj.clone();
    }
    
    public PaintContext createContext(final ColorModel colorModel, final Rectangle rectangle, final Rectangle2D rectangle2D, AffineTransform tx, final RenderingHints renderingHints) {
        tx = new AffineTransform(tx);
        tx.concatenate(this.gradientTransform);
        try {
            return new LinearGradientPaintContext(colorModel, rectangle, rectangle2D, tx, renderingHints, this.start, this.end, this.fractions, this.getColors(), this.cycleMethod, this.colorSpace);
        }
        catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("transform should beinvertible");
        }
    }
    
    public Point2D getStartPoint() {
        return new Point2D.Double(this.start.getX(), this.start.getY());
    }
    
    public Point2D getEndPoint() {
        return new Point2D.Double(this.end.getX(), this.end.getY());
    }
}
