// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;
import java.awt.Graphics2D;
import java.awt.Shape;

public class CompositeShapePainter implements ShapePainter
{
    protected Shape shape;
    protected ShapePainter[] painters;
    protected int count;
    
    public CompositeShapePainter(final Shape shape) {
        if (shape == null) {
            throw new IllegalArgumentException();
        }
        this.shape = shape;
    }
    
    public void addShapePainter(final ShapePainter shapePainter) {
        if (shapePainter == null) {
            return;
        }
        if (this.shape != shapePainter.getShape()) {
            shapePainter.setShape(this.shape);
        }
        if (this.painters == null) {
            this.painters = new ShapePainter[2];
        }
        if (this.count == this.painters.length) {
            final ShapePainter[] painters = new ShapePainter[this.count + this.count / 2 + 1];
            System.arraycopy(this.painters, 0, painters, 0, this.count);
            this.painters = painters;
        }
        this.painters[this.count++] = shapePainter;
    }
    
    public ShapePainter getShapePainter(final int n) {
        return this.painters[n];
    }
    
    public int getShapePainterCount() {
        return this.count;
    }
    
    public void paint(final Graphics2D graphics2D) {
        if (this.painters != null) {
            for (int i = 0; i < this.count; ++i) {
                this.painters[i].paint(graphics2D);
            }
        }
    }
    
    public Shape getPaintedArea() {
        if (this.painters == null) {
            return null;
        }
        final Area area = new Area();
        for (int i = 0; i < this.count; ++i) {
            final Shape paintedArea = this.painters[i].getPaintedArea();
            if (paintedArea != null) {
                area.add(new Area(paintedArea));
            }
        }
        return area;
    }
    
    public Rectangle2D getPaintedBounds2D() {
        if (this.painters == null) {
            return null;
        }
        Rectangle2D rectangle2D = null;
        for (int i = 0; i < this.count; ++i) {
            final Rectangle2D paintedBounds2D = this.painters[i].getPaintedBounds2D();
            if (paintedBounds2D != null) {
                if (rectangle2D == null) {
                    rectangle2D = (Rectangle2D)paintedBounds2D.clone();
                }
                else {
                    rectangle2D.add(paintedBounds2D);
                }
            }
        }
        return rectangle2D;
    }
    
    public boolean inPaintedArea(final Point2D point2D) {
        if (this.painters == null) {
            return false;
        }
        for (int i = 0; i < this.count; ++i) {
            if (this.painters[i].inPaintedArea(point2D)) {
                return true;
            }
        }
        return false;
    }
    
    public Shape getSensitiveArea() {
        if (this.painters == null) {
            return null;
        }
        final Area area = new Area();
        for (int i = 0; i < this.count; ++i) {
            final Shape sensitiveArea = this.painters[i].getSensitiveArea();
            if (sensitiveArea != null) {
                area.add(new Area(sensitiveArea));
            }
        }
        return area;
    }
    
    public Rectangle2D getSensitiveBounds2D() {
        if (this.painters == null) {
            return null;
        }
        Rectangle2D rectangle2D = null;
        for (int i = 0; i < this.count; ++i) {
            final Rectangle2D sensitiveBounds2D = this.painters[i].getSensitiveBounds2D();
            if (rectangle2D == null) {
                rectangle2D = (Rectangle2D)sensitiveBounds2D.clone();
            }
            else {
                rectangle2D.add(sensitiveBounds2D);
            }
        }
        return rectangle2D;
    }
    
    public boolean inSensitiveArea(final Point2D point2D) {
        if (this.painters == null) {
            return false;
        }
        for (int i = 0; i < this.count; ++i) {
            if (this.painters[i].inSensitiveArea(point2D)) {
                return true;
            }
        }
        return false;
    }
    
    public void setShape(final Shape shape) {
        if (shape == null) {
            throw new IllegalArgumentException();
        }
        if (this.painters != null) {
            for (int i = 0; i < this.count; ++i) {
                this.painters[i].setShape(shape);
            }
        }
        this.shape = shape;
    }
    
    public Shape getShape() {
        return this.shape;
    }
}
