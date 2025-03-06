// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.geom;

import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Polygon;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.awt.Shape;

public class Polygon2D implements Shape, Cloneable, Serializable
{
    public int npoints;
    public float[] xpoints;
    public float[] ypoints;
    protected Rectangle2D bounds;
    private GeneralPath path;
    private GeneralPath closedPath;
    
    public Polygon2D() {
        this.xpoints = new float[4];
        this.ypoints = new float[4];
    }
    
    public Polygon2D(final Rectangle2D rectangle2D) {
        if (rectangle2D == null) {
            throw new IndexOutOfBoundsException("null Rectangle");
        }
        this.npoints = 4;
        this.xpoints = new float[4];
        this.ypoints = new float[4];
        this.xpoints[0] = (float)rectangle2D.getMinX();
        this.ypoints[0] = (float)rectangle2D.getMinY();
        this.xpoints[1] = (float)rectangle2D.getMaxX();
        this.ypoints[1] = (float)rectangle2D.getMinY();
        this.xpoints[2] = (float)rectangle2D.getMaxX();
        this.ypoints[2] = (float)rectangle2D.getMaxY();
        this.xpoints[3] = (float)rectangle2D.getMinX();
        this.ypoints[3] = (float)rectangle2D.getMaxY();
        this.calculatePath();
    }
    
    public Polygon2D(final Polygon polygon) {
        if (polygon == null) {
            throw new IndexOutOfBoundsException("null Polygon");
        }
        this.npoints = polygon.npoints;
        this.xpoints = new float[polygon.npoints];
        this.ypoints = new float[polygon.npoints];
        for (int i = 0; i < polygon.npoints; ++i) {
            this.xpoints[i] = (float)polygon.xpoints[i];
            this.ypoints[i] = (float)polygon.ypoints[i];
        }
        this.calculatePath();
    }
    
    public Polygon2D(final float[] array, final float[] array2, final int npoints) {
        if (npoints > array.length || npoints > array2.length) {
            throw new IndexOutOfBoundsException("npoints > xpoints.length || npoints > ypoints.length");
        }
        this.npoints = npoints;
        this.xpoints = new float[npoints];
        this.ypoints = new float[npoints];
        System.arraycopy(array, 0, this.xpoints, 0, npoints);
        System.arraycopy(array2, 0, this.ypoints, 0, npoints);
        this.calculatePath();
    }
    
    public Polygon2D(final int[] array, final int[] array2, final int npoints) {
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
    
    public void reset() {
        this.npoints = 0;
        this.bounds = null;
        this.path = new GeneralPath();
        this.closedPath = null;
    }
    
    public Object clone() {
        final Polygon2D polygon2D = new Polygon2D();
        for (int i = 0; i < this.npoints; ++i) {
            polygon2D.addPoint(this.xpoints[i], this.ypoints[i]);
        }
        return polygon2D;
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
    
    public Polyline2D getPolyline2D() {
        final Polyline2D polyline2D = new Polyline2D(this.xpoints, this.ypoints, this.npoints);
        polyline2D.addPoint(this.xpoints[0], this.ypoints[0]);
        return polyline2D;
    }
    
    public Polygon getPolygon() {
        final int[] xpoints = new int[this.npoints];
        final int[] ypoints = new int[this.npoints];
        for (int i = 0; i < this.npoints; ++i) {
            xpoints[i] = (int)this.xpoints[i];
            ypoints[i] = (int)this.ypoints[i];
        }
        return new Polygon(xpoints, ypoints, this.npoints);
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
    
    public boolean contains(final Point point) {
        return this.contains(point.x, point.y);
    }
    
    public boolean contains(final int n, final int n2) {
        return this.contains(n, (double)n2);
    }
    
    public Rectangle2D getBounds2D() {
        return this.bounds;
    }
    
    public Rectangle getBounds() {
        if (this.bounds == null) {
            return null;
        }
        return this.bounds.getBounds();
    }
    
    public boolean contains(final double n, final double n2) {
        if (this.npoints <= 2 || !this.bounds.contains(n, n2)) {
            return false;
        }
        this.updateComputingPath();
        return this.closedPath.contains(n, n2);
    }
    
    private void updateComputingPath() {
        if (this.npoints >= 1 && this.closedPath == null) {
            (this.closedPath = (GeneralPath)this.path.clone()).closePath();
        }
    }
    
    public boolean contains(final Point2D point2D) {
        return this.contains(point2D.getX(), point2D.getY());
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
        if (this.npoints <= 0 || !this.bounds.intersects(n, n2, n3, n4)) {
            return false;
        }
        this.updateComputingPath();
        return this.closedPath.contains(n, n2, n3, n4);
    }
    
    public boolean contains(final Rectangle2D rectangle2D) {
        return this.contains(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }
    
    public PathIterator getPathIterator(final AffineTransform at) {
        this.updateComputingPath();
        if (this.closedPath == null) {
            return null;
        }
        return this.closedPath.getPathIterator(at);
    }
    
    public PathIterator getPathIterator(final AffineTransform affineTransform, final double n) {
        return this.getPathIterator(affineTransform);
    }
}
