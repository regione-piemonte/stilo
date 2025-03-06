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
import org.apache.batik.dom.svg.SVGOMEllipseElement;
import org.apache.batik.gvt.ShapeNode;
import org.w3c.dom.Element;

public class SVGEllipseElementBridge extends SVGShapeElementBridge
{
    public String getLocalName() {
        return "ellipse";
    }
    
    public Bridge getInstance() {
        return new SVGEllipseElementBridge();
    }
    
    protected void buildShape(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        try {
            final SVGOMEllipseElement svgomEllipseElement = (SVGOMEllipseElement)element;
            final float checkedValue = ((AbstractSVGAnimatedLength)svgomEllipseElement.getCx()).getCheckedValue();
            final float checkedValue2 = ((AbstractSVGAnimatedLength)svgomEllipseElement.getCy()).getCheckedValue();
            final float checkedValue3 = ((AbstractSVGAnimatedLength)svgomEllipseElement.getRx()).getCheckedValue();
            final float checkedValue4 = ((AbstractSVGAnimatedLength)svgomEllipseElement.getRy()).getCheckedValue();
            shapeNode.setShape(new Ellipse2D.Float(checkedValue - checkedValue3, checkedValue2 - checkedValue4, checkedValue3 * 2.0f, checkedValue4 * 2.0f));
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
        if (animatedLiveAttributeValue.getNamespaceURI() == null) {
            final String localName = animatedLiveAttributeValue.getLocalName();
            if (localName.equals("cx") || localName.equals("cy") || localName.equals("rx") || localName.equals("ry")) {
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
