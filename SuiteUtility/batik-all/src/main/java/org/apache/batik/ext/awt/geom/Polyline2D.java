// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.geom;

import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.awt.Shape;

public class Polyline2D implements Shape, Cloneable, Serializable
{
    private static final float ASSUME_ZERO = 0.001f;
    public int npoints;
    public float[] xpoints;
    public float[] ypoints;
    protected Rectangle2D bounds;
    private GeneralPath path;
    private GeneralPath closedPath;
    
    public Polyline2D() {
        this.xpoints = new float[4];
        this.ypoints = new float[4];
    }
    
    public Polyline2D(final float[] array, final float[] array2, final int npoints) {
        if (npoints > array.length || npoints > array2.length) {
            throw new IndexOutOfBoundsException("npoints > xpoints.length || npoints > ypoints.length");
        }
        this.npoints = npoints;
        this.xpoints = new float[npoints + 1];
        this.ypoints = new float[npoints + 1];
        System.arraycopy(array, 0, this.xpoints, 0, npoints);
        System.arraycopy(array2, 0, this.ypoints, 0, npoints);
        this.calculatePath();
    }
    
    public Polyline2D(final int[] array, final int[] array2, final int npoints) {
        if (npoints > array.length || npoints > array2.length) {
            throw new IndexOutOfBoundsException("npoints > xpoints.length || npoints > ypoints.length");
        }
        this.npoints = npoints;
        this.xpoints = new float[npoints];
        this.ypoints = new float[npoints];
        for (int i = 0; i < npoints; ++i) {
            this.xpoints[i] = (float)array[i];
            this.ypoints[i] = (float)array2[i];
        }
        this.calculatePath();
    }
    
    public Polyline2D(final Line2D line2D) {
        this.npoints = 2;
        this.xpoints = new float[2];
        this.ypoints = new float[2];
        this.xpoints[0] = (float)line2D.getX1();
        this.xpoints[1] = (float)line2D.getX2();
        this.ypoints[0] = (float)line2D.getY1();
        this.ypoints[1] = (float)line2D.getY2();
        this.calculatePath();
    }
    
    public void reset() {
        this.npoints = 0;
        this.bounds = null;
        this.path = new GeneralPath();
        this.closedPath = null;
    }
    
    public Object clone() {
        final Polyline2D polyline2D = new Polyline2D();
        for (int i = 0; i < this.npoints; ++i) {
            polyline2D.addPoint(this.xpoints[i], this.ypoints[i]);
        }
        return polyline2D;
    }
    
    private void calculatePath() {
        (this.path = new GeneralPath()).moveTo(this.xpoints[0], this.ypoints[0]);
        for (int i = 1; i < this.npoints; ++i) {
            this.path.lineTo(this.xpoints[i], this.ypoints[i]);
        }
        this.bounds = this.path.getBounds2D();
        this.closedPath = null;
    }
    
    private void updatePath(final float x, final float y) {
        this.closedPath = null;
        if (this.path == null) {
            (this.path = new GeneralPath(0)).moveTo(x, y);
            this.bounds = new Rectangle2D.Float(x, y, 0.0f, 0.0f);
        }
        else {
            this.path.lineTo(x, y);
            float n = (float)this.bounds.getMaxX();
            float n2 = (float)this.bounds.getMaxY();
            float x2 = (float)this.bounds.getMinX();
            float y2 = (float)this.bounds.getMinY();
            if (x < x2) {
                x2 = x;
            }
            else if (x > n) {
                n = x;
            }
            if (y < y2) {
                y2 = y;
            }
            else if (y > n2) {
                n2 = y;
            }
            this.bounds = new Rectangle2D.Float(x2, y2, n - x2, n2 - y2);
        }
    }
    
    public void addPoint(final Point2D point2D) {
        this.addPoint((float)point2D.getX(), (float)point2D.getY());
    }
    
    public void addPoint(final float n, final float n2) {
        if (this.npoints == this.xpoints.length) {
            final float[] xpoints = new float[this.npoints * 2];
            System.arraycopy(this.xpoints, 0, xpoints, 0, this.npoints);
            this.xpoints = xpoints;
            final float[] ypoints = new float[this.npoints * 2];
            System.arraycopy(this.ypoints, 0, ypoints, 0, this.npoints);
            this.ypoints = ypoints;
        }
        this.xpoints[this.npoints] = n;
        this.ypoints[this.npoints] = n2;
        ++this.npoints;
        this.updatePath(n, n2);
    }
    
    public Rectangle getBounds() {
        if (this.bounds == null) {
            return null;
        }
        return this.bounds.getBounds();
    }
    
    private void updateComputingPath() {
        if (this.npoints >= 1 && this.closedPath == null) {
            (this.closedPath = (GeneralPath)this.path.clone()).closePath();
        }
    }
    
    public boolean contains(final Point point) {
        return false;
    }
    
    public boolean contains(final double n, final double n2) {
        return false;
    }
    
    public boolean contains(final int n, final int n2) {
        return false;
    }
    
    public Rectangle2D getBounds2D() {
        return this.bounds;
    }
    
    public boolean contains(final Point2D point2D) {
        return false;
    }
    
    public boolean intersects(final double n, final double n2, final double n3, final double n4) {
        if (this.npoints <= 0 || !this.bounds.intersects(n, n2, n3, n4)) {
            return false;
        }
        this.updateComputingPath();
        return this.closedPath.intersects(n, n2, n3, n4);
    }
    
    public boolean intersects(final Rectangle2D rectangle2D) {
        return this.intersects(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }
    
    public boolean contains(final double n, final double n2, final double n3, final double n4) {
        return false;
    }
    
    public boolean contains(final Rectangle2D rectangle2D) {
        return false;
    }
    
    public PathIterator getPathIterator(final AffineTransform at) {
        if (this.path == null) {
            return null;
        }
        return this.path.getPathIterator(at);
    }
    
    public Polygon2D getPolygon2D() {
        final Polygon2D polygon2D = new Polygon2D();
        for (int i = 0; i < this.npoints - 1; ++i) {
            polygon2D.addPoint(this.xpoints[i], this.ypoints[i]);
        }
        if (new Point2D.Double(this.xpoints[0], this.ypoints[0]).distance(new Point2D.Double(this.xpoints[this.npoints - 1], this.ypoints[this.npoints - 1])) > 0.0010000000474974513) {
            polygon2D.addPoint(this.xpoints[this.npoints - 1], this.ypoints[this.npoints - 1]);
        }
        return polygon2D;
    }
    
    public PathIterator getPathIterator(final AffineTransform at, final double n) {
        return this.path.getPathIterator(at);
    }
}
