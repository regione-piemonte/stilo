// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.Shape;

public class StrokeShapePainter implements ShapePainter
{
    protected Shape shape;
    protected Shape strokedShape;
    protected Stroke stroke;
    protected Paint paint;
    
    public StrokeShapePainter(final Shape shape) {
        if (shape == null) {
            throw new IllegalArgumentException();
        }
        this.shape = shape;
    }
    
    public void setStroke(final Stroke stroke) {
        this.stroke = stroke;
        this.strokedShape = null;
    }
    
    public Stroke getStroke() {
        return this.stroke;
    }
    
    public void setPaint(final Paint paint) {
        this.paint = paint;
    }
    
    public Paint getPaint() {
        return this.paint;
    }
    
    public void paint(final Graphics2D graphics2D) {
        if (this.stroke != null && this.paint != null) {
            graphics2D.setPaint(this.paint);
            graphics2D.setStroke(this.stroke);
            graphics2D.draw(this.shape);
        }
    }
    
    public Shape getPaintedArea() {
        if (this.paint == null || this.stroke == null) {
            return null;
        }
        if (this.strokedShape == null) {
            this.strokedShape = this.stroke.createStrokedShape(this.shape);
        }
        return this.strokedShape;
    }
    
    public Rectangle2D getPaintedBounds2D() {
        final Shape paintedArea = this.getPaintedArea();
        if (paintedArea == null) {
            return null;
        }
        return paintedArea.getBounds2D();
    }
    
    public boolean inPaintedArea(final Point2D point2D) {
        final Shape paintedArea = this.getPaintedArea();
        return paintedArea != null && paintedArea.contains(point2D);
    }
    
    public Shape getSensitiveArea() {
        if (this.stroke == null) {
            return null;
        }
        if (this.strokedShape == null) {
            this.strokedShape = this.stroke.createStrokedShape(this.shape);
        }
        return this.strokedShape;
    }
    
    public Rectangle2D getSensitiveBounds2D() {
        final Shape sensitiveArea = this.getSensitiveArea();
        if (sensitiveArea == null) {
            return null;
        }
        return sensitiveArea.getBounds2D();
    }
    
    public boolean inSensitiveArea(final Point2D point2D) {
        final Shape sensitiveArea = this.getSensitiveArea();
        return sensitiveArea != null && sensitiveArea.contains(point2D);
    }
    
    public void setShape(final Shape shape) {
        if (shape == null) {
            throw new IllegalArgumentException();
        }
        this.shape = shape;
        this.strokedShape = null;
    }
    
    public Shape getShape() {
        return this.shape;
    }
}
