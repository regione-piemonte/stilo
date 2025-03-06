// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;

public class ProxyGraphicsNode extends AbstractGraphicsNode
{
    protected GraphicsNode source;
    
    public void setSource(final GraphicsNode source) {
        this.source = source;
    }
    
    public GraphicsNode getSource() {
        return this.source;
    }
    
    public void primitivePaint(final Graphics2D graphics2D) {
        if (this.source != null) {
            this.source.paint(graphics2D);
        }
    }
    
    public Rectangle2D getPrimitiveBounds() {
        if (this.source == null) {
            return null;
        }
        return this.source.getBounds();
    }
    
    public Rectangle2D getTransformedPrimitiveBounds(final AffineTransform tx) {
        if (this.source == null) {
            return null;
        }
        AffineTransform affineTransform = tx;
        if (this.transform != null) {
            affineTransform = new AffineTransform(tx);
            affineTransform.concatenate(this.transform);
        }
        return this.source.getTransformedPrimitiveBounds(affineTransform);
    }
    
    public Rectangle2D getGeometryBounds() {
        if (this.source == null) {
            return null;
        }
        return this.source.getGeometryBounds();
    }
    
    public Rectangle2D getTransformedGeometryBounds(final AffineTransform tx) {
        if (this.source == null) {
            return null;
        }
        AffineTransform affineTransform = tx;
        if (this.transform != null) {
            affineTransform = new AffineTransform(tx);
            affineTransform.concatenate(this.transform);
        }
        return this.source.getTransformedGeometryBounds(affineTransform);
    }
    
    public Rectangle2D getSensitiveBounds() {
        if (this.source == null) {
            return null;
        }
        return this.source.getSensitiveBounds();
    }
    
    public Shape getOutline() {
        if (this.source == null) {
            return null;
        }
        return this.source.getOutline();
    }
}
