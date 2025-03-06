// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.apache.batik.bridge.SVGUtilities;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import org.apache.batik.bridge.BridgeException;
import org.apache.batik.bridge.UnitProcessor;
import org.apache.batik.gvt.ShapeNode;
import org.w3c.dom.Element;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.Bridge;
import org.apache.batik.bridge.SVGDecoratedShapeElementBridge;

public class BatikStarElementBridge extends SVGDecoratedShapeElementBridge implements BatikExtConstants
{
    public String getNamespaceURI() {
        return "http://xml.apache.org/batik/ext";
    }
    
    public String getLocalName() {
        return "star";
    }
    
    public Bridge getInstance() {
        return new BatikStarElementBridge();
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
        if (attributeNS3.length() == 0) {
            throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "r", attributeNS3 });
        }
        final float svgOtherLengthToUserSpace = UnitProcessor.svgOtherLengthToUserSpace(attributeNS3, "r", context);
        final String attributeNS4 = element.getAttributeNS(null, "ir");
        if (attributeNS4.length() == 0) {
            throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "ir", attributeNS4 });
        }
        final float svgOtherLengthToUserSpace2 = UnitProcessor.svgOtherLengthToUserSpace(attributeNS4, "ir", context);
        final int convertSides = convertSides(element, "sides", 3, bridgeContext);
        final GeneralPath shape = new GeneralPath();
        final double n = 6.283185307179586 / convertSides;
        for (int i = 0; i < convertSides; ++i) {
            final double n2 = i * n - 1.5707963267948966;
            final double n3 = svgHorizontalCoordinateToUserSpace + svgOtherLengthToUserSpace2 * Math.cos(n2);
            final double n4 = svgVerticalCoordinateToUserSpace - svgOtherLengthToUserSpace2 * Math.sin(n2);
            if (i == 0) {
                shape.moveTo((float)n3, (float)n4);
            }
            else {
                shape.lineTo((float)n3, (float)n4);
            }
            final double n5 = (i + 0.5) * n - 1.5707963267948966;
            shape.lineTo((float)(svgHorizontalCoordinateToUserSpace + svgOtherLengthToUserSpace * Math.cos(n5)), (float)(svgVerticalCoordinateToUserSpace - svgOtherLengthToUserSpace * Math.sin(n5)));
        }
        shape.closePath();
        shapeNode.setShape(shape);
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
