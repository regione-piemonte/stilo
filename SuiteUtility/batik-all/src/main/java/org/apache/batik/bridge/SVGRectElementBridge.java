// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.gvt.ShapePainter;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import java.io.Serializable;
import org.apache.batik.dom.svg.LiveAttributeException;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D;
import org.apache.batik.dom.svg.AbstractSVGAnimatedLength;
import org.apache.batik.dom.svg.SVGOMRectElement;
import org.apache.batik.gvt.ShapeNode;
import org.w3c.dom.Element;

public class SVGRectElementBridge extends SVGShapeElementBridge
{
    public String getLocalName() {
        return "rect";
    }
    
    public Bridge getInstance() {
        return new SVGRectElementBridge();
    }
    
    protected void buildShape(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        try {
            final SVGOMRectElement svgomRectElement = (SVGOMRectElement)element;
            final float checkedValue = ((AbstractSVGAnimatedLength)svgomRectElement.getX()).getCheckedValue();
            final float checkedValue2 = ((AbstractSVGAnimatedLength)svgomRectElement.getY()).getCheckedValue();
            final float checkedValue3 = ((AbstractSVGAnimatedLength)svgomRectElement.getWidth()).getCheckedValue();
            final float checkedValue4 = ((AbstractSVGAnimatedLength)svgomRectElement.getHeight()).getCheckedValue();
            float checkedValue5 = ((AbstractSVGAnimatedLength)svgomRectElement.getRx()).getCheckedValue();
            if (checkedValue5 > checkedValue3 / 2.0f) {
                checkedValue5 = checkedValue3 / 2.0f;
            }
            float checkedValue6 = ((AbstractSVGAnimatedLength)svgomRectElement.getRy()).getCheckedValue();
            if (checkedValue6 > checkedValue4 / 2.0f) {
                checkedValue6 = checkedValue4 / 2.0f;
            }
            Serializable shape;
            if (checkedValue5 == 0.0f || checkedValue6 == 0.0f) {
                shape = new Rectangle2D.Float(checkedValue, checkedValue2, checkedValue3, checkedValue4);
            }
            else {
                shape = new RoundRectangle2D.Float(checkedValue, checkedValue2, checkedValue3, checkedValue4, checkedValue5 * 2.0f, checkedValue6 * 2.0f);
            }
            shapeNode.setShape((Shape)shape);
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
        if (animatedLiveAttributeValue.getNamespaceURI() == null) {
            final String localName = animatedLiveAttributeValue.getLocalName();
            if (localName.equals("x") || localName.equals("y") || localName.equals("width") || localName.equals("height") || localName.equals("rx") || localName.equals("ry")) {
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
