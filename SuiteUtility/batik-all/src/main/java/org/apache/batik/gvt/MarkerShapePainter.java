// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.awt.geom.Arc2D;
import org.apache.batik.ext.awt.geom.ExtendedGeneralPath;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.geom.ExtendedPathIterator;
import java.util.List;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import org.apache.batik.ext.awt.geom.ShapeExtender;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.geom.ExtendedShape;

public class MarkerShapePainter implements ShapePainter
{
    protected ExtendedShape extShape;
    protected Marker startMarker;
    protected Marker middleMarker;
    protected Marker endMarker;
    private ProxyGraphicsNode startMarkerProxy;
    private ProxyGraphicsNode[] middleMarkerProxies;
    private ProxyGraphicsNode endMarkerProxy;
    private CompositeGraphicsNode markerGroup;
    private Rectangle2D dPrimitiveBounds;
    private Rectangle2D dGeometryBounds;
    
    public MarkerShapePainter(final Shape shape) {
        if (shape == null) {
            throw new IllegalArgumentException();
        }
        if (shape instanceof ExtendedShape) {
            this.extShape = (ExtendedShape)shape;
        }
        else {
            this.extShape = new ShapeExtender(shape);
        }
    }
    
    public void paint(final Graphics2D graphics2D) {
        if (this.markerGroup == null) {
            this.buildMarkerGroup();
        }
        if (this.markerGroup.getChildren().size() > 0) {
            this.markerGroup.paint(graphics2D);
        }
    }
    
    public Shape getPaintedArea() {
        if (this.markerGroup == null) {
            this.buildMarkerGroup();
        }
        return this.markerGroup.getOutline();
    }
    
    public Rectangle2D getPaintedBounds2D() {
        if (this.markerGroup == null) {
            this.buildMarkerGroup();
        }
        return this.markerGroup.getPrimitiveBounds();
    }
    
    public boolean inPaintedArea(final Point2D point2D) {
        if (this.markerGroup == null) {
            this.buildMarkerGroup();
        }
        return this.markerGroup.nodeHitAt(point2D) != null;
    }
    
    public Shape getSensitiveArea() {
        return null;
    }
    
    public Rectangle2D getSensitiveBounds2D() {
        return null;
    }
    
    public boolean inSensitiveArea(final Point2D point2D) {
        return false;
    }
    
    public void setShape(final Shape shape) {
        if (shape == null) {
            throw new IllegalArgumentException();
        }
        if (shape instanceof ExtendedShape) {
            this.extShape = (ExtendedShape)shape;
        }
        else {
            this.extShape = new ShapeExtender(shape);
        }
        this.startMarkerProxy = null;
        this.middleMarkerProxies = null;
        this.endMarkerProxy = null;
        this.markerGroup = null;
    }
    
    public ExtendedShape getExtShape() {
        return this.extShape;
    }
    
    public Shape getShape() {
        return this.extShape;
    }
    
    public Marker getStartMarker() {
        return this.startMarker;
    }
    
    public void setStartMarker(final Marker startMarker) {
        this.startMarker = startMarker;
        this.startMarkerProxy = null;
        this.markerGroup = null;
    }
    
    public Marker getMiddleMarker() {
        return this.middleMarker;
    }
    
    public void setMiddleMarker(final Marker middleMarker) {
        this.middleMarker = middleMarker;
        this.middleMarkerProxies = null;
        this.markerGroup = null;
    }
    
    public Marker getEndMarker() {
        return this.endMarker;
    }
    
    public void setEndMarker(final Marker endMarker) {
        this.endMarker = endMarker;
        this.endMarkerProxy = null;
        this.markerGroup = null;
    }
    
    protected void buildMarkerGroup() {
        if (this.startMarker != null && this.startMarkerProxy == null) {
            this.startMarkerProxy = this.buildStartMarkerProxy();
        }
        if (this.middleMarker != null && this.middleMarkerProxies == null) {
            this.middleMarkerProxies = this.buildMiddleMarkerProxies();
        }
        if (this.endMarker != null && this.endMarkerProxy == null) {
            this.endMarkerProxy = this.buildEndMarkerProxy();
        }
        final CompositeGraphicsNode markerGroup = new CompositeGraphicsNode();
        final List children = markerGroup.getChildren();
        if (this.startMarkerProxy != null) {
            children.add(this.startMarkerProxy);
        }
        if (this.middleMarkerProxies != null) {
            for (int i = 0; i < this.middleMarkerProxies.length; ++i) {
                children.add(this.middleMarkerProxies[i]);
            }
        }
        if (this.endMarkerProxy != null) {
            children.add(this.endMarkerProxy);
        }
        this.markerGroup = markerGroup;
    }
    
    protected ProxyGraphicsNode buildStartMarkerProxy() {
        final ExtendedPathIterator extendedPathIterator = this.getExtShape().getExtendedPathIterator();
        final double[] array = new double[7];
        if (extendedPathIterator.isDone()) {
            return null;
        }
        final int currentSegment = extendedPathIterator.currentSegment(array);
        if (currentSegment != 0) {
            return null;
        }
        extendedPathIterator.next();
        final Point2D.Double double1 = new Point2D.Double(array[0], array[1]);
        double v = this.startMarker.getOrient();
        if (Double.isNaN(v) && !extendedPathIterator.isDone()) {
            final double[] array2 = new double[7];
            int currentSegment2 = extendedPathIterator.currentSegment(array2);
            if (currentSegment2 == 4) {
                currentSegment2 = 1;
                array2[0] = array[0];
                array2[1] = array[1];
            }
            v = this.computeRotation(null, 0, array, currentSegment, array2, currentSegment2);
        }
        final AffineTransform computeMarkerTransform = this.computeMarkerTransform(this.startMarker, double1, v);
        final ProxyGraphicsNode proxyGraphicsNode = new ProxyGraphicsNode();
        proxyGraphicsNode.setSource(this.startMarker.getMarkerNode());
        proxyGraphicsNode.setTransform(computeMarkerTransform);
        return proxyGraphicsNode;
    }
    
    protected ProxyGraphicsNode buildEndMarkerProxy() {
        final ExtendedPathIterator extendedPathIterator = this.getExtShape().getExtendedPathIterator();
        int n = 0;
        if (extendedPathIterator.isDone()) {
            return null;
        }
        final double[] array = new double[7];
        final double[] array2 = new double[2];
        final int currentSegment = extendedPathIterator.currentSegment(array);
        if (currentSegment != 0) {
            return null;
        }
        ++n;
        array2[0] = array[0];
        array2[1] = array[1];
        extendedPathIterator.next();
        double[] array3 = new double[7];
        double[] array4 = { array[0], array[1], array[2], array[3], array[4], array[5], array[6] };
        int currentSegment2 = currentSegment;
        int n2 = 0;
        while (!extendedPathIterator.isDone()) {
            final double[] array5 = array3;
            array3 = array4;
            array4 = array5;
            n2 = currentSegment2;
            currentSegment2 = extendedPathIterator.currentSegment(array4);
            if (currentSegment2 == 0) {
                array2[0] = array4[0];
                array2[1] = array4[1];
            }
            else if (currentSegment2 == 4) {
                currentSegment2 = 1;
                array4[0] = array2[0];
                array4[1] = array2[1];
            }
            extendedPathIterator.next();
            ++n;
        }
        if (n < 2) {
            return null;
        }
        final Point2D segmentTerminatingPoint = this.getSegmentTerminatingPoint(array4, currentSegment2);
        double v = this.endMarker.getOrient();
        if (Double.isNaN(v)) {
            v = this.computeRotation(array3, n2, array4, currentSegment2, null, 0);
        }
        final AffineTransform computeMarkerTransform = this.computeMarkerTransform(this.endMarker, segmentTerminatingPoint, v);
        final ProxyGraphicsNode proxyGraphicsNode = new ProxyGraphicsNode();
        proxyGraphicsNode.setSource(this.endMarker.getMarkerNode());
        proxyGraphicsNode.setTransform(computeMarkerTransform);
        return proxyGraphicsNode;
    }
    
    protected ProxyGraphicsNode[] buildMiddleMarkerProxies() {
        final ExtendedPathIterator extendedPathIterator = this.getExtShape().getExtendedPathIterator();
        double[] array = new double[7];
        double[] array2 = new double[7];
        double[] array3 = new double[7];
        if (extendedPathIterator.isDone()) {
            return null;
        }
        int currentSegment = extendedPathIterator.currentSegment(array);
        final double[] array4 = new double[2];
        if (currentSegment != 0) {
            return null;
        }
        array4[0] = array[0];
        array4[1] = array[1];
        extendedPathIterator.next();
        if (extendedPathIterator.isDone()) {
            return null;
        }
        int currentSegment2 = extendedPathIterator.currentSegment(array2);
        if (currentSegment2 == 0) {
            array4[0] = array2[0];
            array4[1] = array2[1];
        }
        else if (currentSegment2 == 4) {
            currentSegment2 = 1;
            array2[0] = array4[0];
            array2[1] = array4[1];
        }
        extendedPathIterator.next();
        final ArrayList list = new ArrayList<ProxyGraphicsNode>();
        while (!extendedPathIterator.isDone()) {
            int currentSegment3 = extendedPathIterator.currentSegment(array3);
            if (currentSegment3 == 0) {
                array4[0] = array3[0];
                array4[1] = array3[1];
            }
            else if (currentSegment3 == 4) {
                currentSegment3 = 1;
                array3[0] = array4[0];
                array3[1] = array4[1];
            }
            list.add(this.createMiddleMarker(array, currentSegment, array2, currentSegment2, array3, currentSegment3));
            final double[] array5 = array;
            array = array2;
            currentSegment = currentSegment2;
            array2 = array3;
            currentSegment2 = currentSegment3;
            array3 = array5;
            extendedPathIterator.next();
        }
        final ProxyGraphicsNode[] array6 = new ProxyGraphicsNode[list.size()];
        list.toArray(array6);
        return array6;
    }
    
    private ProxyGraphicsNode createMiddleMarker(final double[] array, final int n, final double[] array2, final int n2, final double[] array3, final int n3) {
        final Point2D segmentTerminatingPoint = this.getSegmentTerminatingPoint(array2, n2);
        double v = this.middleMarker.getOrient();
        if (Double.isNaN(v)) {
            v = this.computeRotation(array, n, array2, n2, array3, n3);
        }
        final AffineTransform computeMarkerTransform = this.computeMarkerTransform(this.middleMarker, segmentTerminatingPoint, v);
        final ProxyGraphicsNode proxyGraphicsNode = new ProxyGraphicsNode();
        proxyGraphicsNode.setSource(this.middleMarker.getMarkerNode());
        proxyGraphicsNode.setTransform(computeMarkerTransform);
        return proxyGraphicsNode;
    }
    
    private double computeRotation(final double[] array, final int n, final double[] array2, final int n2, final double[] array3, final int n3) {
        double[] computeInSlope = this.computeInSlope(array, n, array2, n2);
        double[] computeOutSlope = this.computeOutSlope(array2, n2, array3, n3);
        if (computeInSlope == null) {
            computeInSlope = computeOutSlope;
        }
        if (computeOutSlope == null) {
            computeOutSlope = computeInSlope;
        }
        if (computeInSlope == null) {
            return 0.0;
        }
        final double x = computeInSlope[0] + computeOutSlope[0];
        final double y = computeInSlope[1] + computeOutSlope[1];
        if (x == 0.0 && y == 0.0) {
            return Math.toDegrees(Math.atan2(computeInSlope[1], computeInSlope[0])) + 90.0;
        }
        return Math.toDegrees(Math.atan2(y, x));
    }
    
    private double[] computeInSlope(final double[] array, final int n, final double[] array2, final int n2) {
        final Point2D segmentTerminatingPoint = this.getSegmentTerminatingPoint(array2, n2);
        double n3 = 0.0;
        double n4 = 0.0;
        switch (n2) {
            case 1: {
                final Point2D segmentTerminatingPoint2 = this.getSegmentTerminatingPoint(array, n);
                n3 = segmentTerminatingPoint.getX() - segmentTerminatingPoint2.getX();
                n4 = segmentTerminatingPoint.getY() - segmentTerminatingPoint2.getY();
                break;
            }
            case 2: {
                n3 = segmentTerminatingPoint.getX() - array2[0];
                n4 = segmentTerminatingPoint.getY() - array2[1];
                break;
            }
            case 3: {
                n3 = segmentTerminatingPoint.getX() - array2[2];
                n4 = segmentTerminatingPoint.getY() - array2[3];
                break;
            }
            case 4321: {
                final Point2D segmentTerminatingPoint3 = this.getSegmentTerminatingPoint(array, n);
                final boolean b = array2[3] != 0.0;
                final boolean b2 = array2[4] != 0.0;
                final Arc2D computeArc = ExtendedGeneralPath.computeArc(segmentTerminatingPoint3.getX(), segmentTerminatingPoint3.getY(), array2[0], array2[1], array2[2], b, b2, array2[5], array2[6]);
                final double radians = Math.toRadians(computeArc.getAngleStart() + computeArc.getAngleExtent());
                n3 = -computeArc.getWidth() / 2.0 * Math.sin(radians);
                n4 = computeArc.getHeight() / 2.0 * Math.cos(radians);
                if (array2[2] != 0.0) {
                    final double radians2 = Math.toRadians(-array2[2]);
                    final double sin = Math.sin(radians2);
                    final double cos = Math.cos(radians2);
                    final double n5 = n3 * cos - n4 * sin;
                    final double n6 = n3 * sin + n4 * cos;
                    n3 = n5;
                    n4 = n6;
                }
                if (b2) {
                    n3 = -n3;
                    break;
                }
                n4 = -n4;
                break;
            }
            case 4: {
                throw new Error("should not have SEG_CLOSE here");
            }
            default: {
                return null;
            }
        }
        if (n3 == 0.0 && n4 == 0.0) {
            return null;
        }
        return this.normalize(new double[] { n3, n4 });
    }
    
    private double[] computeOutSlope(final double[] array, final int n, final double[] array2, final int n2) {
        final Point2D segmentTerminatingPoint = this.getSegmentTerminatingPoint(array, n);
        double n3 = 0.0;
        double n4 = 0.0;
        switch (n2) {
            case 4: {
                break;
            }
            case 1:
            case 2:
            case 3: {
                n3 = array2[0] - segmentTerminatingPoint.getX();
                n4 = array2[1] - segmentTerminatingPoint.getY();
                break;
            }
            case 4321: {
                final boolean b = array2[3] != 0.0;
                final boolean b2 = array2[4] != 0.0;
                final Arc2D computeArc = ExtendedGeneralPath.computeArc(segmentTerminatingPoint.getX(), segmentTerminatingPoint.getY(), array2[0], array2[1], array2[2], b, b2, array2[5], array2[6]);
                final double radians = Math.toRadians(computeArc.getAngleStart());
                n3 = -computeArc.getWidth() / 2.0 * Math.sin(radians);
                n4 = computeArc.getHeight() / 2.0 * Math.cos(radians);
                if (array2[2] != 0.0) {
                    final double radians2 = Math.toRadians(-array2[2]);
                    final double sin = Math.sin(radians2);
                    final double cos = Math.cos(radians2);
                    final double n5 = n3 * cos - n4 * sin;
                    final double n6 = n3 * sin + n4 * cos;
                    n3 = n5;
                    n4 = n6;
                }
                if (b2) {
                    n3 = -n3;
                    break;
                }
                n4 = -n4;
                break;
            }
            default: {
                return null;
            }
        }
        if (n3 == 0.0 && n4 == 0.0) {
            return null;
        }
        return this.normalize(new double[] { n3, n4 });
    }
    
    public double[] normalize(final double[] array) {
        final double sqrt = Math.sqrt(array[0] * array[0] + array[1] * array[1]);
        final int n = 0;
        array[n] /= sqrt;
        final int n2 = 1;
        array[n2] /= sqrt;
        return array;
    }
    
    private AffineTransform computeMarkerTransform(final Marker marker, final Point2D point2D, final double n) {
        final Point2D ref = marker.getRef();
        final AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(point2D.getX() - ref.getX(), point2D.getY() - ref.getY());
        if (!Double.isNaN(n)) {
            affineTransform.rotate(Math.toRadians(n), ref.getX(), ref.getY());
        }
        return affineTransform;
    }
    
    protected Point2D getSegmentTerminatingPoint(final double[] array, final int i) {
        switch (i) {
            case 3: {
                return new Point2D.Double(array[4], array[5]);
            }
            case 1: {
                return new Point2D.Double(array[0], array[1]);
            }
            case 0: {
                return new Point2D.Double(array[0], array[1]);
            }
            case 2: {
                return new Point2D.Double(array[2], array[3]);
            }
            case 4321: {
                return new Point2D.Double(array[5], array[6]);
            }
            default: {
                throw new Error("invalid segmentType:" + i);
            }
        }
    }
}
