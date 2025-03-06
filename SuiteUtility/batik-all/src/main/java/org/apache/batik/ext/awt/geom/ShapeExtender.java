// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.geom;

import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.awt.Shape;

public class ShapeExtender implements ExtendedShape
{
    Shape shape;
    
    public ShapeExtender(final Shape shape) {
        this.shape = shape;
    }
    
    public boolean contains(final double n, final double n2) {
        return this.shape.contains(n, n2);
    }
    
    public boolean contains(final double n, final double n2, final double n3, final double n4) {
        return this.shape.contains(n, n2, n3, n4);
    }
    
    public boolean contains(final Point2D point2D) {
        return this.shape.contains(point2D);
    }
    
    public boolean contains(final Rectangle2D rectangle2D) {
        return this.shape.contains(rectangle2D);
    }
    
    public Rectangle getBounds() {
        return this.shape.getBounds();
    }
    
    public Rectangle2D getBounds2D() {
        return this.shape.getBounds2D();
    }
    
    public PathIterator getPathIterator(final AffineTransform affineTransform) {
        return this.shape.getPathIterator(affineTransform);
    }
    
    public PathIterator getPathIterator(final AffineTransform affineTransform, final double n) {
        return this.shape.getPathIterator(affineTransform, n);
    }
    
    public ExtendedPathIterator getExtendedPathIterator() {
        return new EPIWrap(this.shape.getPathIterator(null));
    }
    
    public boolean intersects(final double n, final double n2, final double n3, final double n4) {
        return this.shape.intersects(n, n2, n3, n4);
    }
    
    public boolean intersects(final Rectangle2D rectangle2D) {
        return this.shape.intersects(rectangle2D);
    }
    
    public static class EPIWrap implements ExtendedPathIterator
    {
        PathIterator pi;
        
        public EPIWrap(final PathIterator pi) {
            this.pi = null;
            this.pi = pi;
        }
        
        public int currentSegment() {
            return this.pi.currentSegment(new float[6]);
        }
        
        public int currentSegment(final double[] array) {
            return this.pi.currentSegment(array);
        }
        
        public int currentSegment(final float[] array) {
            return this.pi.currentSegment(array);
        }
        
        public int getWindingRule() {
            return this.pi.getWindingRule();
        }
        
        public boolean isDone() {
            return this.pi.isDone();
        }
        
        public void next() {
            this.pi.next();
        }
    }
}
