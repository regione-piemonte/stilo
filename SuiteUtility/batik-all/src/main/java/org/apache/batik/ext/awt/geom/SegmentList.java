// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.geom;

import java.util.Iterator;
import java.awt.geom.Rectangle2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.util.LinkedList;
import java.util.List;

public class SegmentList
{
    List segments;
    
    public SegmentList() {
        this.segments = new LinkedList();
    }
    
    public SegmentList(final Shape shape) {
        this.segments = new LinkedList();
        final PathIterator pathIterator = shape.getPathIterator(null);
        final float[] array = new float[6];
        Point2D.Double double1 = null;
        Point2D.Double double2 = null;
        while (!pathIterator.isDone()) {
            switch (pathIterator.currentSegment(array)) {
                case 0: {
                    double1 = (double2 = new Point2D.Double(array[0], array[1]));
                    break;
                }
                case 1: {
                    final Point2D.Double double3 = new Point2D.Double(array[0], array[1]);
                    this.segments.add(new Linear(double1, double3));
                    double1 = double3;
                    break;
                }
                case 2: {
                    final Point2D.Double double4 = new Point2D.Double(array[0], array[1]);
                    final Point2D.Double double5 = new Point2D.Double(array[2], array[3]);
                    this.segments.add(new Quadradic(double1, double4, double5));
                    double1 = double5;
                    break;
                }
                case 3: {
                    final Point2D.Double double6 = new Point2D.Double(array[0], array[1]);
                    final Point2D.Double double7 = new Point2D.Double(array[2], array[3]);
                    final Point2D.Double double8 = new Point2D.Double(array[4], array[5]);
                    this.segments.add(new Cubic(double1, double6, double7, double8));
                    double1 = double8;
                    break;
                }
                case 4: {
                    this.segments.add(new Linear(double1, double2));
                    double1 = double2;
                    break;
                }
            }
            pathIterator.next();
        }
    }
    
    public Rectangle2D getBounds2D() {
        final Iterator iterator = this.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        final Rectangle2D rectangle2D = (Rectangle2D)iterator.next().getBounds2D().clone();
        while (iterator.hasNext()) {
            Rectangle2D.union(iterator.next().getBounds2D(), rectangle2D, rectangle2D);
        }
        return rectangle2D;
    }
    
    public void add(final Segment segment) {
        this.segments.add(segment);
    }
    
    public Iterator iterator() {
        return this.segments.iterator();
    }
    
    public int size() {
        return this.segments.size();
    }
    
    public SplitResults split(final double n) {
        final Iterator<Segment> iterator = this.segments.iterator();
        final SegmentList list = new SegmentList();
        final SegmentList list2 = new SegmentList();
        while (iterator.hasNext()) {
            final Segment segment = iterator.next();
            final Segment.SplitResults split = segment.split(n);
            if (split == null) {
                final Rectangle2D bounds2D = segment.getBounds2D();
                if (bounds2D.getY() > n) {
                    list2.add(segment);
                }
                else if (bounds2D.getY() == n) {
                    if (bounds2D.getHeight() == 0.0) {
                        continue;
                    }
                    list2.add(segment);
                }
                else {
                    list.add(segment);
                }
            }
            else {
                final Segment[] above = split.getAbove();
                for (int i = 0; i < above.length; ++i) {
                    list.add(above[i]);
                }
                final Segment[] below = split.getBelow();
                for (int j = 0; j < below.length; ++j) {
                    list2.add(below[j]);
                }
            }
        }
        return new SplitResults(list, list2);
    }
    
    public static class SplitResults
    {
        final SegmentList above;
        final SegmentList below;
        
        public SplitResults(final SegmentList above, final SegmentList below) {
            if (above != null && above.size() > 0) {
                this.above = above;
            }
            else {
                this.above = null;
            }
            if (below != null && below.size() > 0) {
                this.below = below;
            }
            else {
                this.below = null;
            }
        }
        
        public SegmentList getAbove() {
            return this.above;
        }
        
        public SegmentList getBelow() {
            return this.below;
        }
    }
}
