// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.bridge.BridgeException;
import org.apache.batik.bridge.SVGUtilities;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.Bridge;
import org.apache.batik.bridge.AbstractSVGFilterPrimitiveElementBridge;

public class BatikHistogramNormalizationElementBridge extends AbstractSVGFilterPrimitiveElementBridge implements BatikExtConstants
{
    public String getNamespaceURI() {
        return "http://xml.apache.org/batik/ext";
    }
    
    public String getLocalName() {
        return "histogramNormalization";
    }
    
    public Bridge getInstance() {
        return new BatikHistogramNormalizationElementBridge();
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final Filter in = AbstractSVGFilterPrimitiveElementBridge.getIn(element, element2, graphicsNode, filter, map, bridgeContext);
        if (in == null) {
            return null;
        }
        Rectangle2D bounds2D;
        if (in == map.get("SourceGraphic")) {
            bounds2D = rectangle2D;
        }
        else {
            bounds2D = in.getBounds2D();
        }
        final Rectangle2D convertFilterPrimitiveRegion = SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, bounds2D, rectangle2D, bridgeContext);
        float convertSVGNumber = 1.0f;
        final String attributeNS = element.getAttributeNS(null, "trim");
        if (attributeNS.length() != 0) {
            try {
                convertSVGNumber = SVGUtilities.convertSVGNumber(attributeNS);
            }
            catch (NumberFormatException ex) {
                throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "trim", attributeNS });
            }
        }
        if (convertSVGNumber < 0.0f) {
            convertSVGNumber = 0.0f;
        }
        else if (convertSVGNumber > 100.0f) {
            convertSVGNumber = 100.0f;
        }
        final PadRable8Bit padRable8Bit = new PadRable8Bit(new BatikHistogramNormalizationFilter8Bit(in, convertSVGNumber / 100.0f), convertFilterPrimitiveRegion, PadMode.ZERO_PAD);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, padRable8Bit, map);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(padRable8Bit, element);
        return padRable8Bit;
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
