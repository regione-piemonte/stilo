// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.svg12;

import java.awt.Shape;
import org.apache.batik.gvt.GraphicsNode;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import org.apache.batik.bridge.BridgeContext;
import java.awt.geom.Rectangle2D;
import java.awt.Dimension;
import org.w3c.dom.Element;
import java.lang.ref.SoftReference;
import org.apache.batik.util.SVGConstants;
import org.apache.batik.gvt.AbstractGraphicsNode;

public class MultiResGraphicsNode extends AbstractGraphicsNode implements SVGConstants
{
    SoftReference[] srcs;
    Element[] srcElems;
    Dimension[] minSz;
    Dimension[] maxSz;
    Rectangle2D bounds;
    BridgeContext ctx;
    Element multiImgElem;
    
    public MultiResGraphicsNode(final Element multiImgElem, final Rectangle2D bounds, final Element[] array, final Dimension[] array2, final Dimension[] array3, final BridgeContext ctx) {
        this.multiImgElem = multiImgElem;
        this.srcElems = new Element[array.length];
        this.minSz = new Dimension[array.length];
        this.maxSz = new Dimension[array.length];
        this.ctx = ctx;
        for (int i = 0; i < array.length; ++i) {
            this.srcElems[i] = array[i];
            this.minSz[i] = array2[i];
            this.maxSz[i] = array3[i];
        }
        this.srcs = new SoftReference[array.length];
        this.bounds = bounds;
    }
    
    public void primitivePaint(final Graphics2D graphics2D) {
        final AffineTransform transform = graphics2D.getTransform();
        final double sqrt = Math.sqrt(transform.getShearY() * transform.getShearY() + transform.getScaleX() * transform.getScaleX());
        final double sqrt2 = Math.sqrt(transform.getShearX() * transform.getShearX() + transform.getScaleY() * transform.getScaleY());
        int n = -1;
        final double n2 = this.bounds.getWidth() * sqrt;
        double calcDist = this.calcDist(n2, this.minSz[0], this.maxSz[0]);
        int n3 = 0;
        for (int i = 0; i < this.minSz.length; ++i) {
            final double calcDist2 = this.calcDist(n2, this.minSz[i], this.maxSz[i]);
            if (calcDist2 < calcDist) {
                calcDist = calcDist2;
                n3 = i;
            }
            if ((this.minSz[i] == null || n2 >= this.minSz[i].width) && (this.maxSz[i] == null || n2 <= this.maxSz[i].width) && (n == -1 || n3 == i)) {
                n = i;
            }
        }
        if (n == -1) {
            n = n3;
        }
        final GraphicsNode graphicsNode = this.getGraphicsNode(n);
        if (graphicsNode == null) {
            return;
        }
        final Rectangle2D bounds = graphicsNode.getBounds();
        if (bounds == null) {
            return;
        }
        final double n4 = bounds.getWidth() * sqrt;
        final double n5 = bounds.getHeight() * sqrt2;
        final double n6 = bounds.getX() * sqrt;
        final double n7 = bounds.getY() * sqrt2;
        double a;
        double a2;
        if (n4 < 0.0) {
            a = n6 + n4;
            a2 = n6;
        }
        else {
            a = n6;
            a2 = n6 + n4;
        }
        double a3;
        double a4;
        if (n5 < 0.0) {
            a3 = n7 + n5;
            a4 = n7;
        }
        else {
            a3 = n7;
            a4 = n7 + n5;
        }
        final double n8 = (int)(Math.ceil(a2) - Math.floor(a));
        final double n9 = (int)(Math.ceil(a4) - Math.floor(a3));
        final double n10 = n8 / bounds.getWidth() / sqrt;
        final double n11 = n9 / bounds.getHeight() / sqrt2;
        final AffineTransform transform2 = graphics2D.getTransform();
        graphics2D.setTransform(new AffineTransform(transform2.getScaleX() * n10, transform2.getShearY() * n10, transform2.getShearX() * n11, transform2.getScaleY() * n11, transform2.getTranslateX(), transform2.getTranslateY()));
        graphicsNode.paint(graphics2D);
    }
    
    public double calcDist(final double n, final Dimension dimension, final Dimension dimension2) {
        if (dimension == null) {
            if (dimension2 == null) {
                return 1.0E11;
            }
            return Math.abs(n - dimension2.width);
        }
        else {
            if (dimension2 == null) {
                return Math.abs(n - dimension.width);
            }
            return Math.abs(n - (dimension2.width + dimension.width) / 2.0);
        }
    }
    
    public Rectangle2D getPrimitiveBounds() {
        return this.bounds;
    }
    
    public Rectangle2D getGeometryBounds() {
        return this.bounds;
    }
    
    public Rectangle2D getSensitiveBounds() {
        return this.bounds;
    }
    
    public Shape getOutline() {
        return this.bounds;
    }
    
    public GraphicsNode getGraphicsNode(final int n) {
        if (this.srcs[n] != null) {
            final GraphicsNode value = this.srcs[n].get();
            if (value != null) {
                return value;
            }
        }
        try {
            final GraphicsNode build = this.ctx.getGVTBuilder().build(this.ctx, this.srcElems[n]);
            this.srcs[n] = new SoftReference(build);
            return build;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
