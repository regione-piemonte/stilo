// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.geom;

import java.awt.geom.CubicCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Point2D;

public class Cubic extends AbstractSegment
{
    public Point2D.Double p1;
    public Point2D.Double p2;
    public Point2D.Double p3;
    public Point2D.Double p4;
    private static int count;
    
    public Cubic() {
        this.p1 = new Point2D.Double();
        this.p2 = new Point2D.Double();
        this.p3 = new Point2D.Double();
        this.p4 = new Point2D.Double();
    }
    
    public Cubic(final double x, final double y, final double x2, final double y2, final double x3, final double y3, final double x4, final double y4) {
        this.p1 = new Point2D.Double(x, y);
        this.p2 = new Point2D.Double(x2, y2);
        this.p3 = new Point2D.Double(x3, y3);
        this.p4 = new Point2D.Double(x4, y4);
    }
    
    public Cubic(final Point2D.Double p4, final Point2D.Double p5, final Point2D.Double p6, final Point2D.Double p7) {
        this.p1 = p4;
        this.p2 = p5;
        this.p3 = p6;
        this.p4 = p7;
    }
    
    public Object clone() {
        return new Cubic(new Point2D.Double(this.p1.x, this.p1.y), new Point2D.Double(this.p2.x, this.p2.y), new Point2D.Double(this.p3.x, this.p3.y), new Point2D.Double(this.p4.x, this.p4.y));
    }
    
    public Segment reverse() {
        return new Cubic(new Point2D.Double(this.p4.x, this.p4.y), new Point2D.Double(this.p3.x, this.p3.y), new Point2D.Double(this.p2.x, this.p2.y), new Point2D.Double(this.p1.x, this.p1.y));
    }
    
    private void getMinMax(final double n, final double n2, final double n3, final double n4, final double[] array) {
        if (n4 > n) {
            array[0] = n;
            array[1] = n4;
        }
        else {
            array[0] = n4;
            array[1] = n;
        }
        final double n5 = 3.0 * (n2 - n);
        final double n6 = 6.0 * (n3 - n2);
        final double[] eqn = { n5, n6 - 2.0 * n5, 3.0 * (n4 - n3) - n6 + n5 };
        for (int solveQuadratic = QuadCurve2D.solveQuadratic(eqn), i = 0; i < solveQuadratic; ++i) {
            final double n7 = eqn[i];
            if (n7 > 0.0) {
                if (n7 < 1.0) {
                    final double n8 = (1.0 - n7) * (1.0 - n7) * (1.0 - n7) * n + 3.0 * n7 * (1.0 - n7) * (1.0 - n7) * n2 + 3.0 * n7 * n7 * (1.0 - n7) * n3 + n7 * n7 * n7 * n4;
                    if (n8 < array[0]) {
                        array[0] = n8;
                    }
                    else if (n8 > array[1]) {
                        array[1] = n8;
                    }
                }
            }
        }
    }
    
    public double minX() {
        final double[] array = { 0.0, 0.0 };
        this.getMinMax(this.p1.x, this.p2.x, this.p3.x, this.p4.x, array);
        return array[0];
    }
    
    public double maxX() {
        final double[] array = { 0.0, 0.0 };
        this.getMinMax(this.p1.x, this.p2.x, this.p3.x, this.p4.x, array);
        return array[1];
    }
    
    public double minY() {
        final double[] array = { 0.0, 0.0 };
        this.getMinMax(this.p1.y, this.p2.y, this.p3.y, this.p4.y, array);
        return array[0];
    }
    
    public double maxY() {
        final double[] array = { 0.0, 0.0 };
        this.getMinMax(this.p1.y, this.p2.y, this.p3.y, this.p4.y, array);
        return array[1];
    }
    
    public Rectangle2D getBounds2D() {
        final double[] array = { 0.0, 0.0 };
        this.getMinMax(this.p1.x, this.p2.x, this.p3.x, this.p4.x, array);
        final double[] array2 = { 0.0, 0.0 };
        this.getMinMax(this.p1.y, this.p2.y, this.p3.y, this.p4.y, array2);
        return new Rectangle2D.Double(array[0], array2[0], array[1] - array[0], array2[1] - array2[0]);
    }
    
    protected int findRoots(final double n, final double[] res) {
        return CubicCurve2D.solveCubic(new double[] { this.p1.y - n, 3.0 * (this.p2.y - this.p1.y), 3.0 * (this.p1.y - 2.0 * this.p2.y + this.p3.y), 3.0 * this.p2.y - this.p1.y + this.p4.y - 3.0 * this.p3.y }, res);
    }
    
    public Point2D.Double evalDt(final double n) {
        return new Point2D.Double(3.0 * ((this.p2.x - this.p1.x) * (1.0 - n) * (1.0 - n) + 2.0 * (this.p3.x - this.p2.x) * (1.0 - n) * n + (this.p4.x - this.p3.x) * n * n), 3.0 * ((this.p2.y - this.p1.y) * (1.0 - n) * (1.0 - n) + 2.0 * (this.p3.y - this.p2.y) * (1.0 - n) * n + (this.p4.y - this.p3.y) * n * n));
    }
    
    public Point2D.Double eval(final double n) {
        return new Point2D.Double((1.0 - n) * (1.0 - n) * (1.0 - n) * this.p1.x + 3.0 * (n * (1.0 - n) * (1.0 - n) * this.p2.x + n * n * (1.0 - n) * this.p3.x) + n * n * n * this.p4.x, (1.0 - n) * (1.0 - n) * (1.0 - n) * this.p1.y + 3.0 * (n * (1.0 - n) * (1.0 - n) * this.p2.y + n * n * (1.0 - n) * this.p3.y) + n * n * n * this.p4.y);
    }
    
    public void subdivide(final Segment segment, final Segment segment2) {
        Cubic cubic = null;
        Cubic cubic2 = null;
        if (segment instanceof Cubic) {
            cubic = (Cubic)segment;
        }
        if (segment2 instanceof Cubic) {
            cubic2 = (Cubic)segment2;
        }
        this.subdivide(cubic, cubic2);
    }
    
    public void subdivide(final double n, final Segment segment, final Segment segment2) {
        Cubic cubic = null;
        Cubic cubic2 = null;
        if (segment instanceof Cubic) {
            cubic = (Cubic)segment;
        }
        if (segment2 instanceof Cubic) {
            cubic2 = (Cubic)segment2;
        }
        this.subdivide(n, cubic, cubic2);
    }
    
    public void subdivide(final Cubic cubic, final Cubic cubic2) {
        if (cubic == null && cubic2 == null) {
            return;
        }
        final double n = (this.p1.x + 3.0 * (this.p2.x + this.p3.x) + this.p4.x) * 0.125;
        final double n2 = (this.p1.y + 3.0 * (this.p2.y + this.p3.y) + this.p4.y) * 0.125;
        final double n3 = (this.p2.x - this.p1.x + 2.0 * (this.p3.x - this.p2.x) + (this.p4.x - this.p3.x)) * 0.125;
        final double n4 = (this.p2.y - this.p1.y + 2.0 * (this.p3.y - this.p2.y) + (this.p4.y - this.p3.y)) * 0.125;
        if (cubic != null) {
            cubic.p1.x = this.p1.x;
            cubic.p1.y = this.p1.y;
            cubic.p2.x = (this.p2.x + this.p1.x) * 0.5;
            cubic.p2.y = (this.p2.y + this.p1.y) * 0.5;
            cubic.p3.x = n - n3;
            cubic.p3.y = n2 - n4;
            cubic.p4.x = n;
            cubic.p4.y = n2;
        }
        if (cubic2 != null) {
            cubic2.p1.x = n;
            cubic2.p1.y = n2;
            cubic2.p2.x = n + n3;
            cubic2.p2.y = n2 + n4;
            cubic2.p3.x = (this.p4.x + this.p3.x) * 0.5;
            cubic2.p3.y = (this.p4.y + this.p3.y) * 0.5;
            cubic2.p4.x = this.p4.x;
            cubic2.p4.y = this.p4.y;
        }
    }
    
    public void subdivide(final double n, final Cubic cubic, final Cubic cubic2) {
        if (cubic == null && cubic2 == null) {
            return;
        }
        final Point2D.Double eval = this.eval(n);
        final Point2D.Double evalDt = this.evalDt(n);
        if (cubic != null) {
            cubic.p1.x = this.p1.x;
            cubic.p1.y = this.p1.y;
            cubic.p2.x = (this.p2.x + this.p1.x) * n;
            cubic.p2.y = (this.p2.y + this.p1.y) * n;
            cubic.p3.x = eval.x - evalDt.x * n / 3.0;
            cubic.p3.y = eval.y - evalDt.y * n / 3.0;
            cubic.p4.x = eval.x;
            cubic.p4.y = eval.y;
        }
        if (cubic2 != null) {
            cubic2.p1.x = eval.x;
            cubic2.p1.y = eval.y;
            cubic2.p2.x = eval.x + evalDt.x * (1.0 - n) / 3.0;
            cubic2.p2.y = eval.y + evalDt.y * (1.0 - n) / 3.0;
            cubic2.p3.x = (this.p4.x + this.p3.x) * (1.0 - n);
            cubic2.p3.y = (this.p4.y + this.p3.y) * (1.0 - n);
            cubic2.p4.x = this.p4.x;
            cubic2.p4.y = this.p4.y;
        }
    }
    
    public Segment getSegment(final double n, final double n2) {
        final double n3 = n2 - n;
        final Point2D.Double eval = this.eval(n);
        final Point2D.Double evalDt = this.evalDt(n);
        final Point2D.Double double1 = new Point2D.Double(eval.x + n3 * evalDt.x / 3.0, eval.y + n3 * evalDt.y / 3.0);
        final Point2D.Double eval2 = this.eval(n2);
        final Point2D.Double evalDt2 = this.evalDt(n2);
        return new Cubic(eval, double1, new Point2D.Double(eval2.x - n3 * evalDt2.x / 3.0, eval2.y - n3 * evalDt2.y / 3.0), eval2);
    }
    
    protected double subLength(final double n, final double n2, final double n3) {
        ++Cubic.count;
        final double n4 = this.p3.x - this.p2.x;
        final double n5 = this.p3.y - this.p2.y;
        final double sqrt = Math.sqrt(n4 * n4 + n5 * n5);
        final double n6 = this.p4.x - this.p1.x;
        final double n7 = this.p4.y - this.p1.y;
        final double sqrt2 = Math.sqrt(n6 * n6 + n7 * n7);
        final double n8 = n + n2 + sqrt;
        if (n8 < n3) {
            return (n8 + sqrt2) / 2.0;
        }
        if (n8 - sqrt2 < n3) {
            return (n8 + sqrt2) / 2.0;
        }
        final Cubic cubic = new Cubic();
        final double n9 = (this.p1.x + 3.0 * (this.p2.x + this.p3.x) + this.p4.x) * 0.125;
        final double n10 = (this.p1.y + 3.0 * (this.p2.y + this.p3.y) + this.p4.y) * 0.125;
        final double n11 = (n4 + n6) * 0.125;
        final double n12 = (n5 + n7) * 0.125;
        cubic.p1.x = this.p1.x;
        cubic.p1.y = this.p1.y;
        cubic.p2.x = (this.p2.x + this.p1.x) * 0.5;
        cubic.p2.y = (this.p2.y + this.p1.y) * 0.5;
        cubic.p3.x = n9 - n11;
        cubic.p3.y = n10 - n12;
        cubic.p4.x = n9;
        cubic.p4.y = n10;
        final double sqrt3 = Math.sqrt(n11 * n11 + n12 * n12);
        final double subLength = cubic.subLength(n / 2.0, sqrt3, n3 / 2.0);
        cubic.p1.x = n9;
        cubic.p1.y = n10;
        cubic.p2.x = n9 + n11;
        cubic.p2.y = n10 + n12;
        cubic.p3.x = (this.p4.x + this.p3.x) * 0.5;
        cubic.p3.y = (this.p4.y + this.p3.y) * 0.5;
        cubic.p4.x = this.p4.x;
        cubic.p4.y = this.p4.y;
        return subLength + cubic.subLength(sqrt3, n2 / 2.0, n3 / 2.0);
    }
    
    public double getLength() {
        return this.getLength(1.0E-6);
    }
    
    public double getLength(final double n) {
        final double n2 = this.p2.x - this.p1.x;
        final double n3 = this.p2.y - this.p1.y;
        final double sqrt = Math.sqrt(n2 * n2 + n3 * n3);
        final double n4 = this.p4.x - this.p3.x;
        final double n5 = this.p4.y - this.p3.y;
        final double sqrt2 = Math.sqrt(n4 * n4 + n5 * n5);
        final double n6 = this.p3.x - this.p2.x;
        final double n7 = this.p3.y - this.p2.y;
        return this.subLength(sqrt, sqrt2, n * (sqrt + sqrt2 + Math.sqrt(n6 * n6 + n7 * n7)));
    }
    
    public String toString() {
        return "M" + this.p1.x + ',' + this.p1.y + 'C' + this.p2.x + ',' + this.p2.y + ' ' + this.p3.x + ',' + this.p3.y + ' ' + this.p4.x + ',' + this.p4.y;
    }
    
    static {
        Cubic.count = 0;
    }
}
