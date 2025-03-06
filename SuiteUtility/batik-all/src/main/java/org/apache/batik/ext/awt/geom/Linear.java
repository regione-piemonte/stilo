// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.geom;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;

public class Linear implements Segment
{
    public Point2D.Double p1;
    public Point2D.Double p2;
    
    public Linear() {
        this.p1 = new Point2D.Double();
        this.p2 = new Point2D.Double();
    }
    
    public Linear(final double x, final double y, final double x2, final double y2) {
        this.p1 = new Point2D.Double(x, y);
        this.p2 = new Point2D.Double(x2, y2);
    }
    
    public Linear(final Point2D.Double p2, final Point2D.Double p3) {
        this.p1 = p2;
        this.p2 = p3;
    }
    
    public Object clone() {
        return new Linear(new Point2D.Double(this.p1.x, this.p1.y), new Point2D.Double(this.p2.x, this.p2.y));
    }
    
    public Segment reverse() {
        return new Linear(new Point2D.Double(this.p2.x, this.p2.y), new Point2D.Double(this.p1.x, this.p1.y));
    }
    
    public double minX() {
        if (this.p1.x < this.p2.x) {
            return this.p1.x;
        }
        return this.p2.x;
    }
    
    public double maxX() {
        if (this.p1.x > this.p2.x) {
            return this.p1.x;
        }
        return this.p2.x;
    }
    
    public double minY() {
        if (this.p1.y < this.p2.y) {
            return this.p1.y;
        }
        return this.p2.y;
    }
    
    public double maxY() {
        if (this.p1.y > this.p2.y) {
            return this.p2.y;
        }
        return this.p1.y;
    }
    
    public Rectangle2D getBounds2D() {
        double x;
        double w;
        if (this.p1.x < this.p2.x) {
            x = this.p1.x;
            w = this.p2.x - this.p1.x;
        }
        else {
            x = this.p2.x;
            w = this.p1.x - this.p2.x;
        }
        double y;
        double h;
        if (this.p1.y < this.p2.y) {
            y = this.p1.y;
            h = this.p2.y - this.p1.y;
        }
        else {
            y = this.p2.y;
            h = this.p1.y - this.p2.y;
        }
        return new Rectangle2D.Double(x, y, w, h);
    }
    
    public Point2D.Double evalDt(final double n) {
        return new Point2D.Double(this.p2.x - this.p1.x, this.p2.y - this.p1.y);
    }
    
    public Point2D.Double eval(final double n) {
        return new Point2D.Double(this.p1.x + n * (this.p2.x - this.p1.x), this.p1.y + n * (this.p2.y - this.p1.y));
    }
    
    public SplitResults split(final double n) {
        if (n == this.p1.y || n == this.p2.y) {
            return null;
        }
        if (n <= this.p1.y && n <= this.p2.y) {
            return null;
        }
        if (n >= this.p1.y && n >= this.p2.y) {
            return null;
        }
        final double n2 = (n - this.p1.y) / (this.p2.y - this.p1.y);
        final Segment[] array = { this.getSegment(0.0, n2) };
        final Segment[] array2 = { this.getSegment(n2, 1.0) };
        if (this.p2.y < n) {
            return new SplitResults(array, array2);
        }
        return new SplitResults(array2, array);
    }
    
    public Segment getSegment(final double n, final double n2) {
        return new Linear(this.eval(n), this.eval(n2));
    }
    
    public Segment splitBefore(final double n) {
        return new Linear(this.p1, this.eval(n));
    }
    
    public Segment splitAfter(final double n) {
        return new Linear(this.eval(n), this.p2);
    }
    
    public void subdivide(final Segment segment, final Segment segment2) {
        Linear linear = null;
        Linear linear2 = null;
        if (segment instanceof Linear) {
            linear = (Linear)segment;
        }
        if (segment2 instanceof Linear) {
            linear2 = (Linear)segment2;
        }
        this.subdivide(linear, linear2);
    }
    
    public void subdivide(final double n, final Segment segment, final Segment segment2) {
        Linear linear = null;
        Linear linear2 = null;
        if (segment instanceof Linear) {
            linear = (Linear)segment;
        }
        if (segment2 instanceof Linear) {
            linear2 = (Linear)segment2;
        }
        this.subdivide(n, linear, linear2);
    }
    
    public void subdivide(final Linear linear, final Linear linear2) {
        if (linear == null && linear2 == null) {
            return;
        }
        final double n = (this.p1.x + this.p2.x) * 0.5;
        final double n2 = (this.p1.y + this.p2.y) * 0.5;
        if (linear != null) {
            linear.p1.x = this.p1.x;
            linear.p1.y = this.p1.y;
            linear.p2.x = n;
            linear.p2.y = n2;
        }
        if (linear2 != null) {
            linear2.p1.x = n;
            linear2.p1.y = n2;
            linear2.p2.x = this.p2.x;
            linear2.p2.y = this.p2.y;
        }
    }
    
    public void subdivide(final double n, final Linear linear, final Linear linear2) {
        if (linear == null && linear2 == null) {
            return;
        }
        final double n2 = this.p1.x + n * (this.p2.x - this.p1.x);
        final double n3 = this.p1.y + n * (this.p2.y - this.p1.y);
        if (linear != null) {
            linear.p1.x = this.p1.x;
            linear.p1.y = this.p1.y;
            linear.p2.x = n2;
            linear.p2.y = n3;
        }
        if (linear2 != null) {
            linear2.p1.x = n2;
            linear2.p1.y = n3;
            linear2.p2.x = this.p2.x;
            linear2.p2.y = this.p2.y;
        }
    }
    
    public double getLength() {
        final double n = this.p2.x - this.p1.x;
        final double n2 = this.p2.y - this.p1.y;
        return Math.sqrt(n * n + n2 * n2);
    }
    
    public double getLength(final double n) {
        return this.getLength();
    }
    
    public String toString() {
        return "M" + this.p1.x + ',' + this.p1.y + 'L' + this.p2.x + ',' + this.p2.y;
    }
}
