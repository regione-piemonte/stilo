// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.geom.Rectangle2D;
import org.apache.batik.gvt.ShapePainter;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.apache.batik.dom.svg.LiveAttributeException;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import org.apache.batik.dom.svg.AbstractSVGAnimatedLength;
import org.apache.batik.dom.svg.SVGOMCircleElement;
import org.apache.batik.gvt.ShapeNode;
import org.w3c.dom.Element;

public class SVGCircleElementBridge extends SVGShapeElementBridge
{
    public String getLocalName() {
        return "circle";
    }
    
    public Bridge getInstance() {
        return new SVGCircleElementBridge();
    }
    
    protected void buildShape(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        try {
            final SVGOMCircleElement svgomCircleElement = (SVGOMCircleElement)element;
            final float checkedValue = ((AbstractSVGAnimatedLength)svgomCircleElement.getCx()).getCheckedValue();
            final float checkedValue2 = ((AbstractSVGAnimatedLength)svgomCircleElement.getCy()).getCheckedValue();
            final float checkedValue3 = ((AbstractSVGAnimatedLength)svgomCircleElement.getR()).getCheckedValue();
            final float x = checkedValue - checkedValue3;
            final float y = checkedValue2 - checkedValue3;
            final float n = checkedValue3 * 2.0f;
            shapeNode.setShape(new Ellipse2D.Float(x, y, n, n));
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
        if (animatedLiveAttributeValue.getNamespaceURI() == null) {
            final String localName = animatedLiveAttributeValue.getLocalName();
            if (localName.equals("cx") || localName.equals("cy") || localName.equals("r")) {
                this.buildShape(this.ctx, this.e, (ShapeNode)this.node);
                this.handleGeometryChanged();
                return;
            }
        }
        super.handleAnimatedAttributeChanged(animatedLiveAttributeValue);
    }
    
    protected ShapePainter createShapePainter(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        final Rectangle2D bounds2D = shapeNode.getShape().getBounds2D();
        if (bounds2D.getWidth() == 0.0 || bounds2D.getHeight() == 0.0) {
            return null;
        }
        return super.createShapePainter(bridgeContext, element, shapeNode);
    }
}
