// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;

public class FillShapePainter implements ShapePainter
{
    protected Shape shape;
    protected Paint paint;
    
    public FillShapePainter(final Shape shape) {
        if (shape == null) {
            throw new IllegalArgumentException("Shape can not be null!");
        }
        this.shape = shape;
    }
    
    public void setPaint(final Paint paint) {
        this.paint = paint;
    }
    
    public Paint getPaint() {
        return this.paint;
    }
    
    public void paint(final Graphics2D graphics2D) {
        if (this.paint != null) {
            graphics2D.setPaint(this.paint);
            graphics2D.fill(this.shape);
        }
    }
    
    public Shape getPaintedArea() {
        if (this.paint == null) {
            return null;
        }
        return this.shape;
    }
    
    public Rectangle2D getPaintedBounds2D() {
        if (this.paint == null || this.shape == null) {
            return null;
        }
        return this.shape.getBounds2D();
    }
    
    public boolean inPaintedArea(final Point2D point2D) {
        return this.paint != null && this.shape != null && this.shape.contains(point2D);
    }
    
    public Shape getSensitiveArea() {
        return this.shape;
    }
    
    public Rectangle2D getSensitiveBounds2D() {
        if (this.shape == null) {
            return null;
        }
        return this.shape.getBounds2D();
    }
    
    public boolean inSensitiveArea(final Point2D point2D) {
        return this.shape != null && this.shape.contains(point2D);
    }
    
    public void setShape(final Shape shape) {
        if (shape == null) {
            throw new IllegalArgumentException();
        }
        this.shape = shape;
    }
    
    public Shape getShape() {
        return this.shape;
    }
}
