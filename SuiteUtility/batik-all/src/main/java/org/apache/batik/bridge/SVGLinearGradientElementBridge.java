// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.apache.batik.dom.svg.SVGContext;
import org.apache.batik.ext.awt.LinearGradientPaint;
import org.w3c.dom.Node;
import java.awt.Paint;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.MultipleGradientPaint;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGLinearGradientElementBridge extends AbstractSVGGradientElementBridge
{
    public String getLocalName() {
        return "linearGradient";
    }
    
    protected Paint buildGradient(final Element element, final Element element2, final GraphicsNode graphicsNode, final MultipleGradientPaint.CycleMethodEnum cycleMethodEnum, final MultipleGradientPaint.ColorSpaceEnum colorSpaceEnum, AffineTransform objectBBox, final Color[] array, final float[] array2, final BridgeContext bridgeContext) {
        String chainableAttributeNS = SVGUtilities.getChainableAttributeNS(element, null, "x1", bridgeContext);
        if (chainableAttributeNS.length() == 0) {
            chainableAttributeNS = "0%";
        }
        String chainableAttributeNS2 = SVGUtilities.getChainableAttributeNS(element, null, "y1", bridgeContext);
        if (chainableAttributeNS2.length() == 0) {
            chainableAttributeNS2 = "0%";
        }
        String chainableAttributeNS3 = SVGUtilities.getChainableAttributeNS(element, null, "x2", bridgeContext);
        if (chainableAttributeNS3.length() == 0) {
            chainableAttributeNS3 = "100%";
        }
        String chainableAttributeNS4 = SVGUtilities.getChainableAttributeNS(element, null, "y2", bridgeContext);
        if (chainableAttributeNS4.length() == 0) {
            chainableAttributeNS4 = "0%";
        }
        final String chainableAttributeNS5 = SVGUtilities.getChainableAttributeNS(element, null, "gradientUnits", bridgeContext);
        short coordinateSystem;
        if (chainableAttributeNS5.length() == 0) {
            coordinateSystem = 2;
        }
        else {
            coordinateSystem = SVGUtilities.parseCoordinateSystem(element, "gradientUnits", chainableAttributeNS5, bridgeContext);
        }
        final SVGContext svgContext = BridgeContext.getSVGContext(element2);
        if (coordinateSystem == 2 && svgContext instanceof AbstractGraphicsNodeBridge) {
            final Rectangle2D bBox = ((AbstractGraphicsNodeBridge)svgContext).getBBox();
            if ((bBox != null && bBox.getWidth() == 0.0) || bBox.getHeight() == 0.0) {
                return null;
            }
        }
        if (coordinateSystem == 2) {
            objectBBox = SVGUtilities.toObjectBBox(objectBBox, graphicsNode);
        }
        final org.apache.batik.parser.UnitProcessor.Context context = UnitProcessor.createContext(bridgeContext, element);
        final Point2D convertPoint = SVGUtilities.convertPoint(chainableAttributeNS, "x1", chainableAttributeNS2, "y1", coordinateSystem, context);
        final Point2D convertPoint2 = SVGUtilities.convertPoint(chainableAttributeNS3, "x2", chainableAttributeNS4, "y2", coordinateSystem, context);
        if (convertPoint.getX() == convertPoint2.getX() && convertPoint.getY() == convertPoint2.getY()) {
            return array[array.length - 1];
        }
        return new LinearGradientPaint(convertPoint, convertPoint2, array2, array, cycleMethodEnum, colorSpaceEnum, objectBBox);
    }
}
