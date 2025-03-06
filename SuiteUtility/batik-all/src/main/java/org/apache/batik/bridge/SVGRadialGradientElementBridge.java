// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.geom.Rectangle2D;
import org.apache.batik.dom.svg.SVGContext;
import org.apache.batik.ext.awt.RadialGradientPaint;
import org.w3c.dom.Node;
import java.awt.Paint;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.MultipleGradientPaint;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGRadialGradientElementBridge extends AbstractSVGGradientElementBridge
{
    public String getLocalName() {
        return "radialGradient";
    }
    
    protected Paint buildGradient(final Element element, final Element element2, final GraphicsNode graphicsNode, final MultipleGradientPaint.CycleMethodEnum cycleMethodEnum, final MultipleGradientPaint.ColorSpaceEnum colorSpaceEnum, AffineTransform objectBBox, final Color[] array, final float[] array2, final BridgeContext bridgeContext) {
        String chainableAttributeNS = SVGUtilities.getChainableAttributeNS(element, null, "cx", bridgeContext);
        if (chainableAttributeNS.length() == 0) {
            chainableAttributeNS = "50%";
        }
        String chainableAttributeNS2 = SVGUtilities.getChainableAttributeNS(element, null, "cy", bridgeContext);
        if (chainableAttributeNS2.length() == 0) {
            chainableAttributeNS2 = "50%";
        }
        String chainableAttributeNS3 = SVGUtilities.getChainableAttributeNS(element, null, "r", bridgeContext);
        if (chainableAttributeNS3.length() == 0) {
            chainableAttributeNS3 = "50%";
        }
        String chainableAttributeNS4 = SVGUtilities.getChainableAttributeNS(element, null, "fx", bridgeContext);
        if (chainableAttributeNS4.length() == 0) {
            chainableAttributeNS4 = chainableAttributeNS;
        }
        String chainableAttributeNS5 = SVGUtilities.getChainableAttributeNS(element, null, "fy", bridgeContext);
        if (chainableAttributeNS5.length() == 0) {
            chainableAttributeNS5 = chainableAttributeNS2;
        }
        final String chainableAttributeNS6 = SVGUtilities.getChainableAttributeNS(element, null, "gradientUnits", bridgeContext);
        short coordinateSystem;
        if (chainableAttributeNS6.length() == 0) {
            coordinateSystem = 2;
        }
        else {
            coordinateSystem = SVGUtilities.parseCoordinateSystem(element, "gradientUnits", chainableAttributeNS6, bridgeContext);
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
        final float convertLength = SVGUtilities.convertLength(chainableAttributeNS3, "r", coordinateSystem, context);
        if (convertLength == 0.0f) {
            return array[array.length - 1];
        }
        return new RadialGradientPaint(SVGUtilities.convertPoint(chainableAttributeNS, "cx", chainableAttributeNS2, "cy", coordinateSystem, context), convertLength, SVGUtilities.convertPoint(chainableAttributeNS4, "fx", chainableAttributeNS5, "fy", coordinateSystem, context), array2, array, cycleMethodEnum, RadialGradientPaint.SRGB, objectBBox);
    }
}
