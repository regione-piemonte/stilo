// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.Shape;
import org.apache.batik.gvt.CompositeShapePainter;
import org.apache.batik.gvt.ShapePainter;
import org.apache.batik.gvt.ShapeNode;
import org.w3c.dom.Element;

public abstract class SVGDecoratedShapeElementBridge extends SVGShapeElementBridge
{
    protected SVGDecoratedShapeElementBridge() {
    }
    
    ShapePainter createFillStrokePainter(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        return super.createShapePainter(bridgeContext, element, shapeNode);
    }
    
    ShapePainter createMarkerPainter(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        return PaintServer.convertMarkers(element, shapeNode, bridgeContext);
    }
    
    protected ShapePainter createShapePainter(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        final ShapePainter fillStrokePainter = this.createFillStrokePainter(bridgeContext, element, shapeNode);
        final ShapePainter markerPainter = this.createMarkerPainter(bridgeContext, element, shapeNode);
        final Shape shape = shapeNode.getShape();
        ShapePainter shapePainter;
        if (markerPainter != null) {
            if (fillStrokePainter != null) {
                final CompositeShapePainter compositeShapePainter = new CompositeShapePainter(shape);
                compositeShapePainter.addShapePainter(fillStrokePainter);
                compositeShapePainter.addShapePainter(markerPainter);
                shapePainter = compositeShapePainter;
            }
            else {
                shapePainter = markerPainter;
            }
        }
        else {
            shapePainter = fillStrokePainter;
        }
        return shapePainter;
    }
    
    protected void handleCSSPropertyChanged(final int n) {
        switch (n) {
            case 34:
            case 35:
            case 36: {
                if (!this.hasNewShapePainter) {
                    this.hasNewShapePainter = true;
                    final ShapeNode shapeNode = (ShapeNode)this.node;
                    shapeNode.setShapePainter(this.createShapePainter(this.ctx, this.e, shapeNode));
                    break;
                }
                break;
            }
            default: {
                super.handleCSSPropertyChanged(n);
                break;
            }
        }
    }
}
