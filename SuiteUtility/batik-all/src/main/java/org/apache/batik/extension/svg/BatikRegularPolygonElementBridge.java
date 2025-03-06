// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.apache.batik.bridge.SVGUtilities;
import org.apache.batik.bridge.BridgeException;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import org.apache.batik.bridge.UnitProcessor;
import org.apache.batik.gvt.ShapeNode;
import org.w3c.dom.Element;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.Bridge;
import org.apache.batik.bridge.SVGDecoratedShapeElementBridge;

public class BatikRegularPolygonElementBridge extends SVGDecoratedShapeElementBridge implements BatikExtConstants
{
    public String getNamespaceURI() {
        return "http://xml.apache.org/batik/ext";
    }
    
    public String getLocalName() {
        return "regularPolygon";
    }
    
    public Bridge getInstance() {
        return new BatikRegularPolygonElementBridge();
    }
    
    protected void buildShape(final BridgeContext bridgeContext, final Element element, final ShapeNode shapeNode) {
        final org.apache.batik.parser.UnitProcessor.Context context = UnitProcessor.createContext(bridgeContext, element);
        final String attributeNS = element.getAttributeNS(null, "cx");
        float svgHorizontalCoordinateToUserSpace = 0.0f;
        if (attributeNS.length() != 0) {
            svgHorizontalCoordinateToUserSpace = UnitProcessor.svgHorizontalCoordinateToUserSpace(attributeNS, "cx", context);
        }
        final String attributeNS2 = element.getAttributeNS(null, "cy");
        float svgVerticalCoordinateToUserSpace = 0.0f;
        if (attributeNS2.length() != 0) {
            svgVerticalCoordinateToUserSpace = UnitProcessor.svgVerticalCoordinateToUserSpace(attributeNS2, "cy", context);
        }
        final String attributeNS3 = element.getAttributeNS(null, "r");
        if (attributeNS3.length() != 0) {
            final float svgOtherLengthToUserSpace = UnitProcessor.svgOtherLengthToUserSpace(attributeNS3, "r", context);
            final int convertSides = convertSides(element, "sides", 3, bridgeContext);
            final GeneralPath shape = new GeneralPath();
            for (int i = 0; i < convertSides; ++i) {
                final double n = (i + 0.5) * (6.283185307179586 / convertSides) - 1.5707963267948966;
                final double n2 = svgHorizontalCoordinateToUserSpace + svgOtherLengthToUserSpace * Math.cos(n);
                final double n3 = svgVerticalCoordinateToUserSpace - svgOtherLengthToUserSpace * Math.sin(n);
                if (i == 0) {
                    shape.moveTo((float)n2, (float)n3);
                }
                else {
                    shape.lineTo((float)n2, (float)n3);
                }
            }
            shape.closePath();
            shapeNode.setShape(shape);
            return;
        }
        throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "r", attributeNS3 });
    }
    
    protected static int convertSides(final Element element, final String s, final int n, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, s);
        if (attributeNS.length() == 0) {
            return n;
        }
        int convertSVGInteger;
        try {
            convertSVGInteger = SVGUtilities.convertSVGInteger(attributeNS);
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { s, attributeNS });
        }
        if (convertSVGInteger < 3) {
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { s, attributeNS });
        }
        return convertSVGInteger;
    }
}
