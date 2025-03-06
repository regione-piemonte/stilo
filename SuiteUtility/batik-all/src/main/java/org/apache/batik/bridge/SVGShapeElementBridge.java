// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.css.engine.CSSEngineEvent;
import org.apache.batik.gvt.ShapePainter;
import java.awt.RenderingHints;
import org.apache.batik.gvt.ShapeNode;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public abstract class SVGShapeElementBridge extends AbstractGraphicsNodeBridge
{
    protected boolean hasNewShapePainter;
    
    protected SVGShapeElementBridge() {
    }
    
    public GraphicsNode createGraphicsNode(final BridgeContext bridgeContext, final Element element) {
        final ShapeNode shapeNode = (ShapeNode)super.createGraphicsNode(bridgeContext, element);
        if (shapeNode == null) {
            return null;
        }
        this.associateSVGContext(bridgeContext, element, shapeNode);
        this.buildShape(bridgeContext, element, shapeNode);
        final RenderingHints convertShapeRendering = CSSUtilities.convertShapeRendering(element, CSSUtilities.convertColorRendering(element, null));
        if (convertShapeRendering != null) {
            shapeNode.setRenderingHints(convertShapeRendering);
        }
        return shapeNode;
    }
    
    protected GraphicsNode instantiateGraphicsNode() {
        return new ShapeNode();
    }
    
    public void buildGraphicsNode(final BridgeContext bridgeContext, final Element element, final GraphicsNode graphicsNode) {
        final ShapeNode shapeNode = (ShapeNode)graphicsNode;
        shapeNode.setShapePainter(this.createShapePainter(bridgeContext, element, shapeNode));
        super.buildGraphicsNode(bridgeContext, element, graphicsNode);
    }
    
    protected ShapePainter createShapePainter(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        return PaintServer.convertFillAndStroke(element, shapeNode, bridgeContext);
    }
    
    protected abstract void buildShape(final BridgeContext p0, final Element p1, final ShapeNode p2);
    
    public boolean isComposite() {
        return false;
    }
    
    protected void handleGeometryChanged() {
        super.handleGeometryChanged();
        final ShapeNode shapeNode = (ShapeNode)this.node;
        shapeNode.setShapePainter(this.createShapePainter(this.ctx, this.e, shapeNode));
    }
    
    public void handleCSSEngineEvent(final CSSEngineEvent cssEngineEvent) {
        this.hasNewShapePainter = false;
        super.handleCSSEngineEvent(cssEngineEvent);
    }
    
    protected void handleCSSPropertyChanged(final int n) {
        switch (n) {
            case 15:
            case 16:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52: {
                if (!this.hasNewShapePainter) {
                    this.hasNewShapePainter = true;
                    final ShapeNode shapeNode = (ShapeNode)this.node;
                    shapeNode.setShapePainter(this.createShapePainter(this.ctx, this.e, shapeNode));
                    break;
                }
                break;
            }
            case 42: {
                final RenderingHints convertShapeRendering = CSSUtilities.convertShapeRendering(this.e, this.node.getRenderingHints());
                if (convertShapeRendering != null) {
                    this.node.setRenderingHints(convertShapeRendering);
                    break;
                }
                break;
            }
            case 9: {
                final RenderingHints convertColorRendering = CSSUtilities.convertColorRendering(this.e, this.node.getRenderingHints());
                if (convertColorRendering != null) {
                    this.node.setRenderingHints(convertColorRendering);
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
