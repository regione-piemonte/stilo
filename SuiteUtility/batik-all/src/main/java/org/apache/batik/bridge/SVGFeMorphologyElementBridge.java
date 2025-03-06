// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.StringTokenizer;
import org.apache.batik.ext.awt.image.renderable.MorphologyRable8Bit;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeMorphologyElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    public String getLocalName() {
        return "feMorphology";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final float[] convertRadius = convertRadius(element, bridgeContext);
        if (convertRadius[0] == 0.0f || convertRadius[1] == 0.0f) {
            return null;
        }
        final boolean convertOperator = convertOperator(element, bridgeContext);
        final Filter in = AbstractSVGFilterPrimitiveElementBridge.getIn(element, element2, graphicsNode, filter, map, bridgeContext);
        if (in == null) {
            return null;
        }
        final Rectangle2D convertFilterPrimitiveRegion = SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, in.getBounds2D(), rectangle2D, bridgeContext);
        final MorphologyRable8Bit morphologyRable8Bit = new MorphologyRable8Bit(new PadRable8Bit(in, convertFilterPrimitiveRegion, PadMode.ZERO_PAD), convertRadius[0], convertRadius[1], convertOperator);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(morphologyRable8Bit, element);
        final PadRable8Bit padRable8Bit = new PadRable8Bit(morphologyRable8Bit, convertFilterPrimitiveRegion, PadMode.ZERO_PAD);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, padRable8Bit, map);
        return padRable8Bit;
    }
    
    protected static float[] convertRadius(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "radius");
        if (attributeNS.length() == 0) {
            return new float[] { 0.0f, 0.0f };
        }
        final float[] array = new float[2];
        final StringTokenizer stringTokenizer = new StringTokenizer(attributeNS, " ,");
        try {
            array[0] = SVGUtilities.convertSVGNumber(stringTokenizer.nextToken());
            if (stringTokenizer.hasMoreTokens()) {
                array[1] = SVGUtilities.convertSVGNumber(stringTokenizer.nextToken());
            }
            else {
                array[1] = array[0];
            }
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "radius", attributeNS, ex });
        }
        if (stringTokenizer.hasMoreTokens() || array[0] < 0.0f || array[1] < 0.0f) {
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "radius", attributeNS });
        }
        return array;
    }
    
    protected static boolean convertOperator(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "operator");
        if (attributeNS.length() == 0) {
            return false;
        }
        if ("erode".equals(attributeNS)) {
            return false;
        }
        if ("dilate".equals(attributeNS)) {
            return true;
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "operator", attributeNS });
    }
}
