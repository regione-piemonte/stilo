// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.apache.batik.dom.svg.LiveAttributeException;
import java.awt.Shape;
import java.awt.geom.Line2D;
import org.apache.batik.dom.svg.AbstractSVGAnimatedLength;
import org.apache.batik.dom.svg.SVGOMLineElement;
import org.apache.batik.gvt.ShapePainter;
import org.apache.batik.gvt.ShapeNode;
import org.w3c.dom.Element;

public class SVGLineElementBridge extends SVGDecoratedShapeElementBridge
{
    public String getLocalName() {
        return "line";
    }
    
    public Bridge getInstance() {
        return new SVGLineElementBridge();
    }
    
    protected ShapePainter createFillStrokePainter(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        return PaintServer.convertStrokePainter(element, shapeNode, bridgeContext);
    }
    
    protected void buildShape(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        try {
            final SVGOMLineElement svgomLineElement = (SVGOMLineElement)element;
            shapeNode.setShape(new Line2D.Float(((AbstractSVGAnimatedLength)svgomLineElement.getX1()).getCheckedValue(), ((AbstractSVGAnimatedLength)svgomLineElement.getY1()).getCheckedValue(), ((AbstractSVGAnimatedLength)svgomLineElement.getX2()).getCheckedValue(), ((AbstractSVGAnimatedLength)svgomLineElement.getY2()).getCheckedValue()));
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
        if (animatedLiveAttributeValue.getNamespaceURI() == null) {
            final String localName = animatedLiveAttributeValue.getLocalName();
            if (localName.equals("x1") || localName.equals("y1") || localName.equals("x2") || localName.equals("y2")) {
                this.buildShape(this.ctx, this.e, (ShapeNode)this.node);
                this.handleGeometryChanged();
                return;
            }
        }
        super.handleAnimatedAttributeChanged(animatedLiveAttributeValue);
    }
}
