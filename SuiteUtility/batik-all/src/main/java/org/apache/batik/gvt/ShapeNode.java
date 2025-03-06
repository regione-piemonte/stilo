// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import org.apache.batik.util.HaltingThread;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;

public class ShapeNode extends AbstractGraphicsNode
{
    protected Shape shape;
    protected ShapePainter shapePainter;
    private Rectangle2D primitiveBounds;
    private Rectangle2D geometryBounds;
    private Rectangle2D sensitiveBounds;
    private Shape paintedArea;
    private Shape sensitiveArea;
    
    public void setShape(final Shape shape) {
        this.fireGraphicsNodeChangeStarted();
        this.invalidateGeometryCache();
        this.shape = shape;
        if (this.shapePainter != null) {
            if (shape != null) {
                this.shapePainter.setShape(shape);
            }
            else {
                this.shapePainter = null;
            }
        }
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public Shape getShape() {
        return this.shape;
    }
    
    public void setShapePainter(final ShapePainter shapePainter) {
        if (this.shape == null) {
            return;
        }
        this.fireGraphicsNodeChangeStarted();
        this.invalidateGeometryCache();
        this.shapePainter = shapePainter;
        if (this.shapePainter != null && this.shape != this.shapePainter.getShape()) {
            this.shapePainter.setShape(this.shape);
        }
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public ShapePainter getShapePainter() {
        return this.shapePainter;
    }
    
    public void paint(final Graphics2D graphics2D) {
        if (this.isVisible) {
            super.paint(graphics2D);
        }
    }
    
    public void primitivePaint(final Graphics2D graphics2D) {
        if (this.shapePainter != null) {
            this.shapePainter.paint(graphics2D);
        }
    }
    
    protected void invalidateGeometryCache() {
        super.invalidateGeometryCache();
        this.primitiveBounds = null;
        this.geometryBounds = null;
        this.sensitiveBounds = null;
        this.paintedArea = null;
        this.sensitiveArea = null;
    }
    
    public void setPointerEventType(final int pointerEventType) {
        super.setPointerEventType(pointerEventType);
        this.sensitiveBounds = null;
        this.sensitiveArea = null;
    }
    
    public boolean contains(final Point2D p) {
        switch (this.pointerEventType) {
            case 0:
            case 1:
            case 2:
            case 3: {
                if (!this.isVisible) {
                    return false;
                }
            }
            case 4:
            case 5:
            case 6:
            case 7: {
                final Rectangle2D sensitiveBounds = this.getSensitiveBounds();
                return sensitiveBounds != null && sensitiveBounds.contains(p) && this.inSensitiveArea(p);
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean intersects(final Rectangle2D r) {
        final Rectangle2D bounds = this.getBounds();
        return bounds != null && bounds.intersects(r) && this.paintedArea != null && this.paintedArea.intersects(r);
    }
    
    public Rectangle2D getPrimitiveBounds() {
        if (!this.isVisible) {
            return null;
        }
        if (this.shape == null) {
            return null;
        }
        if (this.primitiveBounds != null) {
            return this.primitiveBounds;
        }
        if (this.shapePainter == null) {
            this.primitiveBounds = this.shape.getBounds2D();
        }
        else {
            this.primitiveBounds = this.shapePainter.getPaintedBounds2D();
        }
        if (HaltingThread.hasBeenHalted()) {
            this.invalidateGeometryCache();
        }
        return this.primitiveBounds;
    }
    
    public boolean inSensitiveArea(final Point2D point2D) {
        if (this.shapePainter == null) {
            return false;
        }
        ShapePainter shapePainter = null;
        ShapePainter shapePainter2 = null;
        if (this.shapePainter instanceof StrokeShapePainter) {
            shapePainter = this.shapePainter;
        }
        else if (this.shapePainter instanceof FillShapePainter) {
            shapePainter2 = this.shapePainter;
        }
        else {
            if (!(this.shapePainter instanceof CompositeShapePainter)) {
                return false;
            }
            final CompositeShapePainter compositeShapePainter = (CompositeShapePainter)this.shapePainter;
            for (int i = 0; i < compositeShapePainter.getShapePainterCount(); ++i) {
                final ShapePainter shapePainter3 = compositeShapePainter.getShapePainter(i);
                if (shapePainter3 instanceof StrokeShapePainter) {
                    shapePainter = shapePainter3;
                }
                else if (shapePainter3 instanceof FillShapePainter) {
                    shapePainter2 = shapePainter3;
                }
            }
        }
        switch (this.pointerEventType) {
            case 0:
            case 4: {
                return this.shapePainter.inPaintedArea(point2D);
            }
            case 3:
            case 7: {
                return this.shapePainter.inSensitiveArea(point2D);
            }
            case 1:
            case 5: {
                if (shapePainter2 != null) {
                    return shapePainter2.inSensitiveArea(point2D);
                }
                break;
            }
            case 2:
            case 6: {
                if (shapePainter != null) {
                    return shapePainter.inSensitiveArea(point2D);
                }
                break;
            }
        }
        return false;
    }
    
    public Rectangle2D getSensitiveBounds() {
        if (this.sensitiveBounds != null) {
            return this.sensitiveBounds;
        }
        if (this.shapePainter == null) {
            return null;
        }
        ShapePainter shapePainter = null;
        ShapePainter shapePainter2 = null;
        if (this.shapePainter instanceof StrokeShapePainter) {
            shapePainter = this.shapePainter;
        }
        else if (this.shapePainter instanceof FillShapePainter) {
            shapePainter2 = this.shapePainter;
        }
        else {
            if (!(this.shapePainter instanceof CompositeShapePainter)) {
                return null;
            }
            final CompositeShapePainter compositeShapePainter = (CompositeShapePainter)this.shapePainter;
            for (int i = 0; i < compositeShapePainter.getShapePainterCount(); ++i) {
                final ShapePainter shapePainter3 = compositeShapePainter.getShapePainter(i);
                if (shapePainter3 instanceof StrokeShapePainter) {
                    shapePainter = shapePainter3;
                }
                else if (shapePainter3 instanceof FillShapePainter) {
                    shapePainter2 = shapePainter3;
                }
            }
        }
        switch (this.pointerEventType) {
            case 0:
            case 4: {
                this.sensitiveBounds = this.shapePainter.getPaintedBounds2D();
                break;
            }
            case 1:
            case 5: {
                if (shapePainter2 != null) {
                    this.sensitiveBounds = shapePainter2.getSensitiveBounds2D();
                    break;
                }
                break;
            }
            case 2:
            case 6: {
                if (shapePainter != null) {
                    this.sensitiveBounds = shapePainter.getSensitiveBounds2D();
                    break;
                }
                break;
            }
            case 3:
            case 7: {
                this.sensitiveBounds = this.shapePainter.getSensitiveBounds2D();
                break;
            }
        }
        return this.sensitiveBounds;
    }
    
    public Shape getSensitiveArea() {
        if (this.sensitiveArea != null) {
            return this.sensitiveArea;
        }
        if (this.shapePainter == null) {
            return null;
        }
        ShapePainter shapePainter = null;
        ShapePainter shapePainter2 = null;
        if (this.shapePainter instanceof StrokeShapePainter) {
            shapePainter = this.shapePainter;
        }
        else if (this.shapePainter instanceof FillShapePainter) {
            shapePainter2 = this.shapePainter;
        }
        else {
            if (!(this.shapePainter instanceof CompositeShapePainter)) {
                return null;
            }
            final CompositeShapePainter compositeShapePainter = (CompositeShapePainter)this.shapePainter;
            for (int i = 0; i < compositeShapePainter.getShapePainterCount(); ++i) {
                final ShapePainter shapePainter3 = compositeShapePainter.getShapePainter(i);
                if (shapePainter3 instanceof StrokeShapePainter) {
                    shapePainter = shapePainter3;
                }
                else if (shapePainter3 instanceof FillShapePainter) {
                    shapePainter2 = shapePainter3;
                }
            }
        }
        switch (this.pointerEventType) {
            case 0:
            case 4: {
                this.sensitiveArea = this.shapePainter.getPaintedArea();
                break;
            }
            case 1:
            case 5: {
                if (shapePainter2 != null) {
                    this.sensitiveArea = shapePainter2.getSensitiveArea();
                    break;
                }
                break;
            }
            case 2:
            case 6: {
                if (shapePainter != null) {
                    this.sensitiveArea = shapePainter.getSensitiveArea();
                    break;
                }
                break;
            }
            case 3:
            case 7: {
                this.sensitiveArea = this.shapePainter.getSensitiveArea();
                break;
            }
        }
        return this.sensitiveArea;
    }
    
    public Rectangle2D getGeometryBounds() {
        if (this.geometryBounds == null) {
            if (this.shape == null) {
                return null;
            }
            this.geometryBounds = this.normalizeRectangle(this.shape.getBounds2D());
        }
        return this.geometryBounds;
    }
    
    public Shape getOutline() {
        return this.shape;
    }
}
