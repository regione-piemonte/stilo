// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.awt.Graphics2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.Paint;
import java.awt.geom.AffineTransform;

public class CanvasGraphicsNode extends CompositeGraphicsNode
{
    protected AffineTransform positionTransform;
    protected AffineTransform viewingTransform;
    protected Paint backgroundPaint;
    
    public void setBackgroundPaint(final Paint backgroundPaint) {
        this.backgroundPaint = backgroundPaint;
    }
    
    public Paint getBackgroundPaint() {
        return this.backgroundPaint;
    }
    
    public void setPositionTransform(final AffineTransform positionTransform) {
        this.fireGraphicsNodeChangeStarted();
        this.invalidateGeometryCache();
        this.positionTransform = positionTransform;
        if (this.positionTransform != null) {
            this.transform = new AffineTransform(this.positionTransform);
            if (this.viewingTransform != null) {
                this.transform.concatenate(this.viewingTransform);
            }
        }
        else if (this.viewingTransform != null) {
            this.transform = new AffineTransform(this.viewingTransform);
        }
        else {
            this.transform = new AffineTransform();
        }
        Label_0139: {
            if (this.transform.getDeterminant() != 0.0) {
                try {
                    this.inverseTransform = this.transform.createInverse();
                    break Label_0139;
                }
                catch (NoninvertibleTransformException ex) {
                    throw new Error(ex.getMessage());
                }
            }
            this.inverseTransform = this.transform;
        }
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public AffineTransform getPositionTransform() {
        return this.positionTransform;
    }
    
    public void setViewingTransform(final AffineTransform viewingTransform) {
        this.fireGraphicsNodeChangeStarted();
        this.invalidateGeometryCache();
        this.viewingTransform = viewingTransform;
        if (this.positionTransform != null) {
            this.transform = new AffineTransform(this.positionTransform);
            if (this.viewingTransform != null) {
                this.transform.concatenate(this.viewingTransform);
            }
        }
        else if (this.viewingTransform != null) {
            this.transform = new AffineTransform(this.viewingTransform);
        }
        else {
            this.transform = new AffineTransform();
        }
        Label_0139: {
            if (this.transform.getDeterminant() != 0.0) {
                try {
                    this.inverseTransform = this.transform.createInverse();
                    break Label_0139;
                }
                catch (NoninvertibleTransformException ex) {
                    throw new Error(ex.getMessage());
                }
            }
            this.inverseTransform = this.transform;
        }
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public AffineTransform getViewingTransform() {
        return this.viewingTransform;
    }
    
    public void primitivePaint(final Graphics2D graphics2D) {
        if (this.backgroundPaint != null) {
            graphics2D.setPaint(this.backgroundPaint);
            graphics2D.fill(graphics2D.getClip());
        }
        super.primitivePaint(graphics2D);
    }
}
