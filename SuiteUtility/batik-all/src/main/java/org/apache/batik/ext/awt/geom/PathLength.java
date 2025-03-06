// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.geom;

import java.awt.geom.PathIterator;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.awt.Shape;

public class PathLength
{
    protected Shape path;
    protected List segments;
    protected int[] segmentIndexes;
    protected float pathLength;
    protected boolean initialised;
    
    public PathLength(final Shape path) {
        this.setPath(path);
    }
    
    public Shape getPath() {
        return this.path;
    }
    
    public void setPath(final Shape path) {
        this.path = path;
        this.initialised = false;
    }
    
    public float lengthOfPath() {
        if (!this.initialised) {
            this.initialise();
        }
        return this.pathLength;
    }
    
    protected void initialise() {
        this.pathLength = 0.0f;
        final PathIterator pathIterator = this.path.getPathIterator(new AffineTransform());
        final SingleSegmentPathIterator src = new SingleSegmentPathIterator();
        this.segments = new ArrayList(20);
        final ArrayList list = new ArrayList<Object>(20);
        int value = 0;
        int n = -1;
        float n2 = 0.0f;
        float n3 = 0.0f;
        float n4 = 0.0f;
        float n5 = 0.0f;
        final float[] coords = new float[6];
        this.segments.add(new PathSegment(0, 0.0f, 0.0f, 0.0f, n));
        while (!pathIterator.isDone()) {
            ++n;
            list.add(new Integer(value));
            final int currentSegment = pathIterator.currentSegment(coords);
            switch (currentSegment) {
                case 0: {
                    this.segments.add(new PathSegment(currentSegment, coords[0], coords[1], this.pathLength, n));
                    n4 = coords[0];
                    n5 = coords[1];
                    n2 = n4;
                    n3 = n5;
                    ++value;
                    pathIterator.next();
                    continue;
                }
                case 1: {
                    this.pathLength += (float)Point2D.distance(n4, n5, coords[0], coords[1]);
                    this.segments.add(new PathSegment(currentSegment, coords[0], coords[1], this.pathLength, n));
                    n4 = coords[0];
                    n5 = coords[1];
                    ++value;
                    pathIterator.next();
                    continue;
                }
                case 4: {
                    this.pathLength += (float)Point2D.distance(n4, n5, n2, n3);
                    this.segments.add(new PathSegment(1, n2, n3, this.pathLength, n));
                    n4 = n2;
                    n5 = n3;
                    ++value;
                    pathIterator.next();
                    continue;
                }
                default: {
                    src.setPathIterator(pathIterator, n4, n5);
                    final FlatteningPathIterator flatteningPathIterator = new FlatteningPathIterator(src, 0.009999999776482582);
                    while (!flatteningPathIterator.isDone()) {
                        final int currentSegment2 = flatteningPathIterator.currentSegment(coords);
                        if (currentSegment2 == 1) {
                            this.pathLength += (float)Point2D.distance(n4, n5, coords[0], coords[1]);
                            this.segments.add(new PathSegment(currentSegment2, coords[0], coords[1], this.pathLength, n));
                            n4 = coords[0];
                            n5 = coords[1];
                            ++value;
                        }
                        flatteningPathIterator.next();
                    }
                    continue;
                }
            }
        }
        this.segmentIndexes = new int[list.size()];
        for (int i = 0; i < this.segmentIndexes.length; ++i) {
            this.segmentIndexes[i] = (int)list.get(i);
        }
        this.initialised = true;
    }
    
    public int getNumberOfSegments() {
        if (!this.initialised) {
            this.initialise();
        }
        return this.segmentIndexes.length;
    }
    
    public float getLengthAtSegment(final int n) {
        if (!this.initialised) {
            this.initialise();
        }
        if (n <= 0) {
            return 0.0f;
        }
        if (n >= this.segmentIndexes.length) {
            return this.pathLength;
        }
        return this.segments.get(this.segmentIndexes[n]).getLength();
    }
    
    public int segmentAtLength(final float n) {
        final int upperIndex = this.findUpperIndex(n);
        if (upperIndex == -1) {
            return -1;
        }
        if (upperIndex == 0) {
            return ((PathSegment)this.segments.get(upperIndex)).getIndex();
        }
        return ((PathSegment)this.segments.get(upperIndex - 1)).getIndex();
    }
    
    public Point2D pointAtLength(final int n, final float n2) {
        if (!this.initialised) {
            this.initialise();
        }
        if (n < 0 || n >= this.segmentIndexes.length) {
            return null;
        }
        final float length = this.segments.get(this.segmentIndexes[n]).getLength();
        float n3;
        if (n == this.segmentIndexes.length - 1) {
            n3 = this.pathLength;
        }
        else {
            n3 = this.segments.get(this.segmentIndexes[n + 1]).getLength();
        }
        return this.pointAtLength(length + (n3 - length) * n2);
    }
    
    public Point2D pointAtLength(final float n) {
        final int upperIndex = this.findUpperIndex(n);
        if (upperIndex == -1) {
            return null;
        }
        final PathSegment pathSegment = this.segments.get(upperIndex);
        if (upperIndex == 0) {
            return new Point2D.Float(pathSegment.getX(), pathSegment.getY());
        }
        final PathSegment pathSegment2 = this.segments.get(upperIndex - 1);
        final float n2 = n - pathSegment2.getLength();
        final double atan2 = Math.atan2(pathSegment.getY() - pathSegment2.getY(), pathSegment.getX() - pathSegment2.getX());
        return new Point2D.Float((float)(pathSegment2.getX() + n2 * Math.cos(atan2)), (float)(pathSegment2.getY() + n2 * Math.sin(atan2)));
    }
    
    public float angleAtLength(final int n, final float n2) {
        if (!this.initialised) {
            this.initialise();
        }
        if (n < 0 || n >= this.segmentIndexes.length) {
            return 0.0f;
        }
        final float length = this.segments.get(this.segmentIndexes[n]).getLength();
        float n3;
        if (n == this.segmentIndexes.length - 1) {
            n3 = this.pathLength;
        }
        else {
            n3 = this.segments.get(this.segmentIndexes[n + 1]).getLength();
        }
        return this.angleAtLength(length + (n3 - length) * n2);
    }
    
    public float angleAtLength(final float n) {
        int upperIndex = this.findUpperIndex(n);
        if (upperIndex == -1) {
            return 0.0f;
        }
        final PathSegment pathSegment = this.segments.get(upperIndex);
        if (upperIndex == 0) {
            upperIndex = 1;
        }
        final PathSegment pathSegment2 = this.segments.get(upperIndex - 1);
        return (float)Math.atan2(pathSegment.getY() - pathSegment2.getY(), pathSegment.getX() - pathSegment2.getX());
    }
    
    public int findUpperIndex(final float n) {
        if (!this.initialised) {
            this.initialise();
        }
        if (n < 0.0f || n > this.pathLength) {
            return -1;
        }
        int i = 0;
        int n2 = this.segments.size() - 1;
        while (i != n2) {
            final int n3 = i + n2 >> 1;
            if (((PathSegment)this.segments.get(n3)).getLength() >= n) {
                n2 = n3;
            }
            else {
                i = n3 + 1;
            }
        }
        while (((PathSegment)this.segments.get(n2)).getSegType() == 0 && n2 != this.segments.size() - 1) {
            ++n2;
        }
        int n4 = -1;
        for (int n5 = 0, size = this.segments.size(); n4 <= 0 && n5 < size; ++n5) {
            final PathSegment pathSegment = this.segments.get(n5);
            if (pathSegment.getLength() >= n && pathSegment.getSegType() != 0) {
                n4 = n5;
            }
        }
        return n4;
    }
    
    protected static class PathSegment
    {
        protected final int segType;
        protected float x;
        protected float y;
        protected float length;
        protected int index;
        
        PathSegment(final int segType, final float x, final float y, final float length, final int index) {
            this.segType = segType;
            this.x = x;
            this.y = y;
            this.length = length;
            this.index = index;
        }
        
        public int getSegType() {
            return this.segType;
        }
        
        public float getX() {
            return this.x;
        }
        
        public void setX(final float x) {
            this.x = x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public void setY(final float y) {
            this.y = y;
        }
        
        public float getLength() {
            return this.length;
        }
        
        public void setLength(final float length) {
            this.length = length;
        }
        
        public int getIndex() {
            return this.index;
        }
        
        public void setIndex(final int index) {
            this.index = index;
        }
    }
    
    protected static class SingleSegmentPathIterator implements PathIterator
    {
        protected PathIterator it;
        protected boolean done;
        protected boolean moveDone;
        protected double x;
        protected double y;
        
        public void setPathIterator(final PathIterator it, final double x, final double y) {
            this.it = it;
            this.x = x;
            this.y = y;
            this.done = false;
            this.moveDone = false;
        }
        
        public int currentSegment(final double[] array) {
            final int currentSegment = this.it.currentSegment(array);
            if (!this.moveDone) {
                array[0] = this.x;
                array[1] = this.y;
                return 0;
            }
            return currentSegment;
        }
        
        public int currentSegment(final float[] array) {
            final int currentSegment = this.it.currentSegment(array);
            if (!this.moveDone) {
                array[0] = (float)this.x;
                array[1] = (float)this.y;
                return 0;
            }
            return currentSegment;
        }
        
        public int getWindingRule() {
            return this.it.getWindingRule();
        }
        
        public boolean isDone() {
            return this.done || this.it.isDone();
        }
        
        public void next() {
            if (!this.done) {
                if (!this.moveDone) {
                    this.moveDone = true;
                }
                else {
                    this.it.next();
                    this.done = true;
                }
            }
        }
    }
}
