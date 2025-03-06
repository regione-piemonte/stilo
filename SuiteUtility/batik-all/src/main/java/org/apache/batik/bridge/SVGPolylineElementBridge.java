// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.geom.GeneralPath;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGPointList;
import org.apache.batik.dom.svg.SVGOMAnimatedPoints;
import org.apache.batik.dom.svg.LiveAttributeException;
import org.apache.batik.parser.AWTPolylineProducer;
import org.apache.batik.dom.svg.SVGOMPolylineElement;
import org.apache.batik.gvt.ShapeNode;
import org.w3c.dom.Element;
import java.awt.Shape;

public class SVGPolylineElementBridge extends SVGDecoratedShapeElementBridge
{
    protected static final Shape DEFAULT_SHAPE;
    
    public String getLocalName() {
        return "polyline";
    }
    
    public Bridge getInstance() {
        return new SVGPolylineElementBridge();
    }
    
    protected void buildShape(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        final SVGOMPolylineElement svgomPolylineElement = (SVGOMPolylineElement)element;
        try {
            final SVGOMAnimatedPoints svgomAnimatedPoints = svgomPolylineElement.getSVGOMAnimatedPoints();
            svgomAnimatedPoints.check();
            final SVGPointList animatedPoints = svgomAnimatedPoints.getAnimatedPoints();
            final int numberOfItems = animatedPoints.getNumberOfItems();
            if (numberOfItems == 0) {
                shapeNode.setShape(SVGPolylineElementBridge.DEFAULT_SHAPE);
            }
            else {
                final AWTPolylineProducer awtPolylineProducer = new AWTPolylineProducer();
                awtPolylineProducer.setWindingRule(CSSUtilities.convertFillRule(element));
                awtPolylineProducer.startPoints();
                for (int i = 0; i < numberOfItems; ++i) {
                    final SVGPoint item = animatedPoints.getItem(i);
                    awtPolylineProducer.point(item.getX(), item.getY());
                }
                awtPolylineProducer.endPoints();
                shapeNode.setShape(awtPolylineProducer.getShape());
            }
        }
        catch (LiveAttributeException ex) {
            throw new BridgeException(bridgeContext, ex);
        }
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
        if (animatedLiveAttributeValue.getNamespaceURI() == null && animatedLiveAttributeValue.getLocalName().equals("points")) {
            this.buildShape(this.ctx, this.e, (ShapeNode)this.node);
            this.handleGeometryChanged();
            return;
        }
        super.handleAnimatedAttributeChanged(animatedLiveAttributeValue);
    }
    
    protected void handleCSSPropertyChanged(final int n) {
        switch (n) {
            case 17: {
                this.buildShape(this.ctx, this.e, (ShapeNode)this.node);
                this.handleGeometryChanged();
                break;
            }
            default: {
                super.handleCSSPropertyChanged(n);
                break;
            }
        }
    }
    
    static {
        DEFAULT_SHAPE = new GeneralPath();
    }
}
