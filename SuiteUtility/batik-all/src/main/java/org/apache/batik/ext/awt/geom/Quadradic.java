// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.geom;

import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;

public class Quadradic extends AbstractSegment
{
    public Point2D.Double p1;
    public Point2D.Double p2;
    public Point2D.Double p3;
    static int count;
    
    public Quadradic() {
        this.p1 = new Point2D.Double();
        this.p2 = new Point2D.Double();
        this.p3 = new Point2D.Double();
    }
    
    public Quadradic(final double x, final double y, final double x2, final double y2, final double x3, final double y3) {
        this.p1 = new Point2D.Double(x, y);
        this.p2 = new Point2D.Double(x2, y2);
        this.p3 = new Point2D.Double(x3, y3);
    }
    
    public Quadradic(final Point2D.Double p3, final Point2D.Double p4, final Point2D.Double p5) {
        this.p1 = p3;
        this.p2 = p4;
        this.p3 = p5;
    }
    
    public Object clone() {
        return new Quadradic(new Point2D.Double(this.p1.x, this.p1.y), new Point2D.Double(this.p2.x, this.p2.y), new Point2D.Double(this.p3.x, this.p3.y));
    }
    
    public Segment reverse() {
        return new Quadradic(new Point2D.Double(this.p3.x, this.p3.y), new Point2D.Double(this.p2.x, this.p2.y), new Point2D.Double(this.p1.x, this.p1.y));
    }
    
    private void getMinMax(final double n, final double n2, final double n3, final double[] array) {
        if (n3 > n) {
            array[0] = n;
            array[1] = n3;
        }
        else {
            array[0] = n3;
            array[1] = n;
        }
        final double n4 = n - 2.0 * n2 + n3;
        final double n5 = n2 - n;
        if (n4 == 0.0) {
            return;
        }
        final double n6 = n5 / n4;
        if (n6 <= 0.0 || n6 >= 1.0) {
            return;
        }
        final double n7 = ((n - 2.0 * n2 + n3) * n6 + 2.0 * (n2 - n)) * n6 + n;
        if (n7 < array[0]) {
            array[0] = n7;
        }
        else if (n7 > array[1]) {
            array[1] = n7;
        }
    }
    
    public double minX() {
        final double[] array = { 0.0, 0.0 };
        this.getMinMax(this.p1.x, this.p2.x, this.p3.x, array);
        return array[0];
    }
    
    public double maxX() {
        final double[] array = { 0.0, 0.0 };
        this.getMinMax(this.p1.x, this.p2.x, this.p3.x, array);
        return array[1];
    }
    
    public double minY() {
        final double[] array = { 0.0, 0.0 };
        this.getMinMax(this.p1.y, this.p2.y, this.p3.y, array);
        return array[0];
    }
    
    public double maxY() {
        final double[] array = { 0.0, 0.0 };
        this.getMinMax(this.p1.y, this.p2.y, this.p3.y, array);
        return array[1];
    }
    
    public Rectangle2D getBounds2D() {
        final double[] array = { 0.0, 0.0 };
        this.getMinMax(this.p1.x, this.p2.x, this.p3.x, array);
        final double[] array2 = { 0.0, 0.0 };
        this.getMinMax(this.p1.y, this.p2.y, this.p3.y, array2);
        return new Rectangle2D.Double(array[0], array2[0], array[1] - array[0], array2[1] - array2[0]);
    }
    
    protected int findRoots(final double n, final double[] res) {
        return QuadCurve2D.solveQuadratic(new double[] { this.p1.y - n, 2.0 * (this.p2.y - this.p1.y), this.p1.y - 2.0 * this.p2.y + this.p3.y }, res);
    }
    
    public Point2D.Double evalDt(final double n) {
        return new Point2D.Double(2.0 * (this.p1.x - 2.0 * this.p2.x + this.p3.x) * n + 2.0 * (this.p2.x - this.p1.x), 2.0 * (this.p1.y - 2.0 * this.p2.y + this.p3.y) * n + 2.0 * (this.p2.y - this.p1.y));
    }
    
    public Point2D.Double eval(final double n) {
        return new Point2D.Double(((this.p1.x - 2.0 * this.p2.x + this.p3.x) * n + 2.0 * (this.p2.x - this.p1.x)) * n + this.p1.x, ((this.p1.y - 2.0 * this.p2.y + this.p3.y) * n + 2.0 * (this.p2.y - this.p1.y)) * n + this.p1.y);
    }
    
    public Segment getSegment(final double n, final double n2) {
        final double n3 = n2 - n;
        final Point2D.Double eval = this.eval(n);
        final Point2D.Double evalDt = this.evalDt(n);
        return new Quadradic(eval, new Point2D.Double(eval.x + 0.5 * n3 * evalDt.x, eval.y + 0.5 * n3 * evalDt.y), this.eval(n2));
    }
    
    public void subdivide(final Quadradic quadradic, final Quadradic quadradic2) {
        if (quadradic == null && quadradic2 == null) {
            return;
        }
        final double n = (this.p1.x - 2.0 * this.p2.x + this.p3.x) * 0.25 + (this.p2.x - this.p1.x) + this.p1.x;
        final double n2 = (this.p1.y - 2.0 * this.p2.y + this.p3.y) * 0.25 + (this.p2.y - this.p1.y) + this.p1.y;
        final double n3 = (this.p1.x - 2.0 * this.p2.x + this.p3.x) * 0.25 + (this.p2.x - this.p1.x) * 0.5;
        final double n4 = (this.p1.y - 2.0 * this.p2.y + this.p3.y) * 0.25 + (this.p2.y - this.p1.y) * 0.5;
        if (quadradic != null) {
            quadradic.p1.x = this.p1.x;
            quadradic.p1.y = this.p1.y;
            quadradic.p2.x = n - n3;
            quadradic.p2.y = n2 - n4;
            quadradic.p3.x = n;
            quadradic.p3.y = n2;
        }
        if (quadradic2 != null) {
            quadradic2.p1.x = n;
            quadradic2.p1.y = n2;
            quadradic2.p2.x = n + n3;
            quadradic2.p2.y = n2 + n4;
            quadradic2.p3.x = this.p3.x;
            quadradic2.p3.y = this.p3.y;
        }
    }
    
    public void subdivide(final double n, final Quadradic quadradic, final Quadradic quadradic2) {
        final Point2D.Double eval = this.eval(n);
        final Point2D.Double evalDt = this.evalDt(n);
        if (quadradic != null) {
            quadradic.p1.x = this.p1.x;
            quadradic.p1.y = this.p1.y;
            quadradic.p2.x = eval.x - evalDt.x * n * 0.5;
            quadradic.p2.y = eval.y - evalDt.y * n * 0.5;
            quadradic.p3.x = eval.x;
            quadradic.p3.y = eval.y;
        }
        if (quadradic2 != null) {
            quadradic2.p1.x = eval.x;
            quadradic2.p1.y = eval.y;
            quadradic2.p2.x = eval.x + evalDt.x * (1.0 - n) * 0.5;
            quadradic2.p2.y = eval.y + evalDt.y * (1.0 - n) * 0.5;
            quadradic2.p3.x = this.p3.x;
            quadradic2.p3.y = this.p3.y;
        }
    }
    
    public void subdivide(final Segment segment, final Segment segment2) {
        Quadradic quadradic = null;
        Quadradic quadradic2 = null;
        if (segment instanceof Quadradic) {
            quadradic = (Quadradic)segment;
        }
        if (segment2 instanceof Quadradic) {
            quadradic2 = (Quadradic)segment2;
        }
        this.subdivide(quadradic, quadradic2);
    }
    
    public void subdivide(final double n, final Segment segment, final Segment segment2) {
        Quadradic quadradic = null;
        Quadradic quadradic2 = null;
        if (segment instanceof Quadradic) {
            quadradic = (Quadradic)segment;
        }
        if (segment2 instanceof Quadradic) {
            quadradic2 = (Quadradic)segment2;
        }
        this.subdivide(n, quadradic, quadradic2);
    }
    
    protected double subLength(final double n, final double n2, final double n3) {
        ++Quadradic.count;
        final double n4 = this.p3.x - this.p1.x;
        final double n5 = this.p3.y - this.p1.y;
        final double sqrt = Math.sqrt(n4 * n4 + n5 * n5);
        final double n6 = n + n2;
        if (n6 < n3) {
            return (n6 + sqrt) * 0.5;
        }
        if (n6 - sqrt < n3) {
            return (n6 + sqrt) * 0.5;
        }
        final Quadradic quadradic = new Quadradic();
        final double n7 = (this.p1.x + 2.0 * this.p2.x + this.p3.x) * 0.25;
        final double n8 = (this.p1.y + 2.0 * this.p2.y + this.p3.y) * 0.25;
        final double n9 = 0.25 * n4;
        final double n10 = 0.25 * n5;
        quadradic.p1.x = this.p1.x;
        quadradic.p1.y = this.p1.y;
        quadradic.p2.x = n7 - n9;
        quadradic.p2.y = n8 - n10;
        quadradic.p3.x = n7;
        quadradic.p3.y = n8;
        final double n11 = 0.25 * sqrt;
        final double subLength = quadradic.subLength(n * 0.5, n11, n3 * 0.5);
        quadradic.p1.x = n7;
        quadradic.p1.y = n8;
        quadradic.p2.x = n7 + n9;
        quadradic.p2.y = n8 + n10;
        quadradic.p3.x = this.p3.x;
        quadradic.p3.y = this.p3.y;
        return subLength + quadradic.subLength(n11, n2 * 0.5, n3 * 0.5);
    }
    
    public double getLength() {
        return this.getLength(1.0E-6);
    }
    
    public double getLength(final double n) {
        final double n2 = this.p2.x - this.p1.x;
        final double n3 = this.p2.y - this.p1.y;
        final double sqrt = Math.sqrt(n2 * n2 + n3 * n3);
        final double n4 = this.p3.x - this.p2.x;
        final double n5 = this.p3.y - this.p2.y;
        final double sqrt2 = Math.sqrt(n4 * n4 + n5 * n5);
        return this.subLength(sqrt, sqrt2, n * (sqrt + sqrt2));
    }
    
    public String toString() {
        return "M" + this.p1.x + ',' + this.p1.y + 'Q' + this.p2.x + ',' + this.p2.y + ' ' + this.p3.x + ',' + this.p3.y;
    }
    
    static {
        Quadradic.count = 0;
    }
}
