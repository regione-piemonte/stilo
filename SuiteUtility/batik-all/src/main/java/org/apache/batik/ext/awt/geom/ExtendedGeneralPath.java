// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.geom;

import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.awt.geom.PathIterator;
import java.awt.geom.Arc2D;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

public class ExtendedGeneralPath implements ExtendedShape, Cloneable
{
    protected GeneralPath path;
    int numVals;
    int numSeg;
    float[] values;
    int[] types;
    float mx;
    float my;
    float cx;
    float cy;
    
    public ExtendedGeneralPath() {
        this.numVals = 0;
        this.numSeg = 0;
        this.values = null;
        this.types = null;
        this.path = new GeneralPath();
    }
    
    public ExtendedGeneralPath(final int rule) {
        this.numVals = 0;
        this.numSeg = 0;
        this.values = null;
        this.types = null;
        this.path = new GeneralPath(rule);
    }
    
    public ExtendedGeneralPath(final int rule, final int initialCapacity) {
        this.numVals = 0;
        this.numSeg = 0;
        this.values = null;
        this.types = null;
        this.path = new GeneralPath(rule, initialCapacity);
    }
    
    public ExtendedGeneralPath(final Shape shape) {
        this();
        this.append(shape, false);
    }
    
    public synchronized void arcTo(final float n, final float n2, final float n3, final boolean b, final boolean b2, final float cx, final float cy) {
        if (n == 0.0f || n2 == 0.0f) {
            this.lineTo(cx, cy);
            return;
        }
        this.checkMoveTo();
        final double n4 = this.cx;
        final double n5 = this.cy;
        if (n4 == cx && n5 == cy) {
            return;
        }
        final Arc2D computeArc = computeArc(n4, n5, n, n2, n3, b, b2, cx, cy);
        if (computeArc == null) {
            return;
        }
        this.path.append(AffineTransform.getRotateInstance(Math.toRadians(n3), computeArc.getCenterX(), computeArc.getCenterY()).createTransformedShape(computeArc), true);
        this.makeRoom(7);
        this.types[this.numSeg++] = 4321;
        this.values[this.numVals++] = n;
        this.values[this.numVals++] = n2;
        this.values[this.numVals++] = n3;
        this.values[this.numVals++] = (b ? 1.0f : 0.0f);
        this.values[this.numVals++] = (b2 ? 1.0f : 0.0f);
        this.values[this.numVals++] = cx;
        this.cx = cx;
        this.values[this.numVals++] = cy;
        this.cy = cy;
    }
    
    public static Arc2D computeArc(final double n, final double n2, double abs, double abs2, double radians, final boolean b, final boolean b2, final double n3, final double n4) {
        final double n5 = (n - n3) / 2.0;
        final double n6 = (n2 - n4) / 2.0;
        radians = Math.toRadians(radians % 360.0);
        final double cos = Math.cos(radians);
        final double sin = Math.sin(radians);
        final double n7 = cos * n5 + sin * n6;
        final double n8 = -sin * n5 + cos * n6;
        abs = Math.abs(abs);
        abs2 = Math.abs(abs2);
        double n9 = abs * abs;
        double n10 = abs2 * abs2;
        final double n11 = n7 * n7;
        final double n12 = n8 * n8;
        final double n13 = n11 / n9 + n12 / n10;
        if (n13 > 1.0) {
            abs *= Math.sqrt(n13);
            abs2 *= Math.sqrt(n13);
            n9 = abs * abs;
            n10 = abs2 * abs2;
        }
        final double n14 = (b == b2) ? -1.0 : 1.0;
        final double n15 = (n9 * n10 - n9 * n12 - n10 * n11) / (n9 * n12 + n10 * n11);
        final double n16 = n14 * Math.sqrt((n15 < 0.0) ? 0.0 : n15);
        final double n17 = n16 * (abs * n8 / abs2);
        final double n18 = n16 * -(abs2 * n7 / abs);
        final double n19 = (n + n3) / 2.0;
        final double n20 = (n2 + n4) / 2.0;
        final double n21 = n19 + (cos * n17 - sin * n18);
        final double n22 = n20 + (sin * n17 + cos * n18);
        final double n23 = (n7 - n17) / abs;
        final double n24 = (n8 - n18) / abs2;
        final double n25 = (-n7 - n17) / abs;
        final double n26 = (-n8 - n18) / abs2;
        final double degrees = Math.toDegrees(((n24 < 0.0) ? -1.0 : 1.0) * Math.acos(n23 / Math.sqrt(n23 * n23 + n24 * n24)));
        double degrees2 = Math.toDegrees(((n23 * n26 - n24 * n25 < 0.0) ? -1.0 : 1.0) * Math.acos((n23 * n25 + n24 * n26) / Math.sqrt((n23 * n23 + n24 * n24) * (n25 * n25 + n26 * n26))));
        if (!b2 && degrees2 > 0.0) {
            degrees2 -= 360.0;
        }
        else if (b2 && degrees2 < 0.0) {
            degrees2 += 360.0;
        }
        final double n27 = degrees2 % 360.0;
        final double n28 = degrees % 360.0;
        final Arc2D.Double double1 = new Arc2D.Double();
        double1.x = n21 - abs;
        double1.y = n22 - abs2;
        double1.width = abs * 2.0;
        double1.height = abs2 * 2.0;
        double1.start = -n28;
        double1.extent = -n27;
        return double1;
    }
    
    public synchronized void moveTo(final float n, final float n2) {
        this.makeRoom(2);
        this.types[this.numSeg++] = 0;
        this.values[this.numVals++] = n;
        this.mx = n;
        this.cx = n;
        this.values[this.numVals++] = n2;
        this.my = n2;
        this.cy = n2;
    }
    
    public synchronized void lineTo(final float n, final float n2) {
        this.checkMoveTo();
        this.path.lineTo(n, n2);
        this.makeRoom(2);
        this.types[this.numSeg++] = 1;
        this.values[this.numVals++] = n;
        this.cx = n;
        this.values[this.numVals++] = n2;
        this.cy = n2;
    }
    
    public synchronized void quadTo(final float x1, final float y1, final float n, final float n2) {
        this.checkMoveTo();
        this.path.quadTo(x1, y1, n, n2);
        this.makeRoom(4);
        this.types[this.numSeg++] = 2;
        this.values[this.numVals++] = x1;
        this.values[this.numVals++] = y1;
        this.values[this.numVals++] = n;
        this.cx = n;
        this.values[this.numVals++] = n2;
        this.cy = n2;
    }
    
    public synchronized void curveTo(final float x1, final float y1, final float x2, final float y2, final float n, final float n2) {
        this.checkMoveTo();
        this.path.curveTo(x1, y1, x2, y2, n, n2);
        this.makeRoom(6);
        this.types[this.numSeg++] = 3;
        this.values[this.numVals++] = x1;
        this.values[this.numVals++] = y1;
        this.values[this.numVals++] = x2;
        this.values[this.numVals++] = y2;
        this.values[this.numVals++] = n;
        this.cx = n;
        this.values[this.numVals++] = n2;
        this.cy = n2;
    }
    
    public synchronized void closePath() {
        if (this.numSeg != 0 && this.types[this.numSeg - 1] == 4) {
            return;
        }
        if (this.numSeg != 0 && this.types[this.numSeg - 1] != 0) {
            this.path.closePath();
        }
        this.makeRoom(0);
        this.types[this.numSeg++] = 4;
        this.cx = this.mx;
        this.cy = this.my;
    }
    
    protected void checkMoveTo() {
        if (this.numSeg == 0) {
            return;
        }
        switch (this.types[this.numSeg - 1]) {
            case 0: {
                this.path.moveTo(this.values[this.numVals - 2], this.values[this.numVals - 1]);
                break;
            }
            case 4: {
                if (this.numSeg == 1) {
                    return;
                }
                if (this.types[this.numSeg - 2] == 0) {
                    this.path.moveTo(this.values[this.numVals - 2], this.values[this.numVals - 1]);
                    break;
                }
                break;
            }
        }
    }
    
    public void append(final Shape shape, final boolean b) {
        this.append(shape.getPathIterator(new AffineTransform()), b);
    }
    
    public void append(final PathIterator pathIterator, boolean b) {
        final double[] a = new double[6];
        while (!pathIterator.isDone()) {
            Arrays.fill(a, 0.0);
            int n = pathIterator.currentSegment(a);
            pathIterator.next();
            if (b && this.numVals != 0) {
                if (n == 0) {
                    final double n2 = a[0];
                    final double n3 = a[1];
                    if (n2 != this.cx || n3 != this.cy) {
                        n = 1;
                    }
                    else {
                        if (pathIterator.isDone()) {
                            break;
                        }
                        n = pathIterator.currentSegment(a);
                        pathIterator.next();
                    }
                }
                b = false;
            }
            switch (n) {
                case 4: {
                    this.closePath();
                    continue;
                }
                case 0: {
                    this.moveTo((float)a[0], (float)a[1]);
                    continue;
                }
                case 1: {
                    this.lineTo((float)a[0], (float)a[1]);
                    continue;
                }
                case 2: {
                    this.quadTo((float)a[0], (float)a[1], (float)a[2], (float)a[3]);
                    continue;
                }
                case 3: {
                    this.curveTo((float)a[0], (float)a[1], (float)a[2], (float)a[3], (float)a[4], (float)a[5]);
                    continue;
                }
            }
        }
    }
    
    public void append(final ExtendedPathIterator extendedPathIterator, boolean b) {
        final float[] a = new float[7];
        while (!extendedPathIterator.isDone()) {
            Arrays.fill(a, 0.0f);
            int n = extendedPathIterator.currentSegment(a);
            extendedPathIterator.next();
            if (b && this.numVals != 0) {
                if (n == 0) {
                    final float n2 = a[0];
                    final float n3 = a[1];
                    if (n2 != this.cx || n3 != this.cy) {
                        n = 1;
                    }
                    else {
                        if (extendedPathIterator.isDone()) {
                            break;
                        }
                        n = extendedPathIterator.currentSegment(a);
                        extendedPathIterator.next();
                    }
                }
                b = false;
            }
            switch (n) {
                case 4: {
                    this.closePath();
                    continue;
                }
                case 0: {
                    this.moveTo(a[0], a[1]);
                    continue;
                }
                case 1: {
                    this.lineTo(a[0], a[1]);
                    continue;
                }
                case 2: {
                    this.quadTo(a[0], a[1], a[2], a[3]);
                    continue;
                }
                case 3: {
                    this.curveTo(a[0], a[1], a[2], a[3], a[4], a[5]);
                    continue;
                }
                case 4321: {
                    this.arcTo(a[0], a[1], a[2], a[3] != 0.0f, a[4] != 0.0f, a[5], a[6]);
                    continue;
                }
            }
        }
    }
    
    public synchronized int getWindingRule() {
        return this.path.getWindingRule();
    }
    
    public void setWindingRule(final int windingRule) {
        this.path.setWindingRule(windingRule);
    }
    
    public synchronized Point2D getCurrentPoint() {
        if (this.numVals == 0) {
            return null;
        }
        return new Point2D.Double(this.cx, this.cy);
    }
    
    public synchronized void reset() {
        this.path.reset();
        this.numSeg = 0;
        this.numVals = 0;
        this.values = null;
        this.types = null;
    }
    
    public void transform(final AffineTransform affineTransform) {
        if (affineTransform.getType() != 0) {
            throw new IllegalArgumentException("ExtendedGeneralPaths can not be transformed");
        }
    }
    
    public synchronized Shape createTransformedShape(final AffineTransform at) {
        return this.path.createTransformedShape(at);
    }
    
    public synchronized Rectangle getBounds() {
        return this.path.getBounds();
    }
    
    public synchronized Rectangle2D getBounds2D() {
        return this.path.getBounds2D();
    }
    
    public boolean contains(final double x, final double y) {
        return this.path.contains(x, y);
    }
    
    public boolean contains(final Point2D p) {
        return this.path.contains(p);
    }
    
    public boolean contains(final double x, final double y, final double w, final double h) {
        return this.path.contains(x, y, w, h);
    }
    
    public boolean contains(final Rectangle2D r) {
        return this.path.contains(r);
    }
    
    public boolean intersects(final double x, final double y, final double w, final double h) {
        return this.path.intersects(x, y, w, h);
    }
    
    public boolean intersects(final Rectangle2D r) {
        return this.path.intersects(r);
    }
    
    public PathIterator getPathIterator(final AffineTransform at) {
        return this.path.getPathIterator(at);
    }
    
    public PathIterator getPathIterator(final AffineTransform at, final double flatness) {
        return this.path.getPathIterator(at, flatness);
    }
    
    public ExtendedPathIterator getExtendedPathIterator() {
        return new EPI();
    }
    
    public Object clone() {
        try {
            final ExtendedGeneralPath extendedGeneralPath = (ExtendedGeneralPath)super.clone();
            extendedGeneralPath.path = (GeneralPath)this.path.clone();
            if (this.values != null) {
                extendedGeneralPath.values = new float[this.values.length];
                System.arraycopy(this.values, 0, extendedGeneralPath.values, 0, this.values.length);
            }
            extendedGeneralPath.numVals = this.numVals;
            if (this.types != null) {
                extendedGeneralPath.types = new int[this.types.length];
                System.arraycopy(this.types, 0, extendedGeneralPath.types, 0, this.types.length);
            }
            extendedGeneralPath.numSeg = this.numSeg;
            return extendedGeneralPath;
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    private void makeRoom(final int n) {
        if (this.values == null) {
            this.values = new float[2 * n];
            this.types = new int[2];
            this.numVals = 0;
            this.numSeg = 0;
            return;
        }
        final int n2 = this.numVals + n;
        if (n2 > this.values.length) {
            int n3 = this.values.length * 2;
            if (n3 < n2) {
                n3 = n2;
            }
            final float[] values = new float[n3];
            System.arraycopy(this.values, 0, values, 0, this.numVals);
            this.values = values;
        }
        if (this.numSeg == this.types.length) {
            final int[] types = new int[this.types.length * 2];
            System.arraycopy(this.types, 0, types, 0, this.types.length);
            this.types = types;
        }
    }
    
    class EPI implements ExtendedPathIterator
    {
        int segNum;
        int valsIdx;
        
        EPI() {
            this.segNum = 0;
            this.valsIdx = 0;
        }
        
        public int currentSegment() {
            return ExtendedGeneralPath.this.types[this.segNum];
        }
        
        public int currentSegment(final double[] array) {
            final int n = ExtendedGeneralPath.this.types[this.segNum];
            switch (n) {
                case 0:
                case 1: {
                    array[0] = ExtendedGeneralPath.this.values[this.valsIdx];
                    array[1] = ExtendedGeneralPath.this.values[this.valsIdx + 1];
                    break;
                }
                case 2: {
                    array[0] = ExtendedGeneralPath.this.values[this.valsIdx];
                    array[1] = ExtendedGeneralPath.this.values[this.valsIdx + 1];
                    array[2] = ExtendedGeneralPath.this.values[this.valsIdx + 2];
                    array[3] = ExtendedGeneralPath.this.values[this.valsIdx + 3];
                    break;
                }
                case 3: {
                    array[0] = ExtendedGeneralPath.this.values[this.valsIdx];
                    array[1] = ExtendedGeneralPath.this.values[this.valsIdx + 1];
                    array[2] = ExtendedGeneralPath.this.values[this.valsIdx + 2];
                    array[3] = ExtendedGeneralPath.this.values[this.valsIdx + 3];
                    array[4] = ExtendedGeneralPath.this.values[this.valsIdx + 4];
                    array[5] = ExtendedGeneralPath.this.values[this.valsIdx + 5];
                    break;
                }
                case 4321: {
                    array[0] = ExtendedGeneralPath.this.values[this.valsIdx];
                    array[1] = ExtendedGeneralPath.this.values[this.valsIdx + 1];
                    array[2] = ExtendedGeneralPath.this.values[this.valsIdx + 2];
                    array[3] = ExtendedGeneralPath.this.values[this.valsIdx + 3];
                    array[4] = ExtendedGeneralPath.this.values[this.valsIdx + 4];
                    array[5] = ExtendedGeneralPath.this.values[this.valsIdx + 5];
                    array[6] = ExtendedGeneralPath.this.values[this.valsIdx + 6];
                    break;
                }
            }
            return n;
        }
        
        public int currentSegment(final float[] array) {
            final int n = ExtendedGeneralPath.this.types[this.segNum];
            switch (n) {
                case 0:
                case 1: {
                    array[0] = ExtendedGeneralPath.this.values[this.valsIdx];
                    array[1] = ExtendedGeneralPath.this.values[this.valsIdx + 1];
                    break;
                }
                case 2: {
                    System.arraycopy(ExtendedGeneralPath.this.values, this.valsIdx, array, 0, 4);
                    break;
                }
                case 3: {
                    System.arraycopy(ExtendedGeneralPath.this.values, this.valsIdx, array, 0, 6);
                    break;
                }
                case 4321: {
                    System.arraycopy(ExtendedGeneralPath.this.values, this.valsIdx, array, 0, 7);
                    break;
                }
            }
            return n;
        }
        
        public int getWindingRule() {
            return ExtendedGeneralPath.this.path.getWindingRule();
        }
        
        public boolean isDone() {
            return this.segNum == ExtendedGeneralPath.this.numSeg;
        }
        
        public void next() {
            switch (ExtendedGeneralPath.this.types[this.segNum++]) {
                case 0:
                case 1: {
                    this.valsIdx += 2;
                    break;
                }
                case 2: {
                    this.valsIdx += 4;
                    break;
                }
                case 3: {
                    this.valsIdx += 6;
                    break;
                }
                case 4321: {
                    this.valsIdx += 7;
                    break;
                }
            }
        }
    }
}
