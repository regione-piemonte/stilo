// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.StringTokenizer;
import org.apache.batik.ext.awt.image.renderable.TurbulenceRable8Bit;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeTurbulenceElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    public String getLocalName() {
        return "feTurbulence";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        if (AbstractSVGFilterPrimitiveElementBridge.getIn(element, element2, graphicsNode, filter, map, bridgeContext) == null) {
            return null;
        }
        final Rectangle2D convertFilterPrimitiveRegion = SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, rectangle2D, rectangle2D, bridgeContext);
        final float[] convertBaseFrenquency = convertBaseFrenquency(element, bridgeContext);
        final int convertInteger = AbstractSVGFilterPrimitiveElementBridge.convertInteger(element, "numOctaves", 1, bridgeContext);
        final int convertInteger2 = AbstractSVGFilterPrimitiveElementBridge.convertInteger(element, "seed", 0, bridgeContext);
        final boolean convertStitchTiles = convertStitchTiles(element, bridgeContext);
        final boolean convertType = convertType(element, bridgeContext);
        final TurbulenceRable8Bit turbulenceRable8Bit = new TurbulenceRable8Bit(convertFilterPrimitiveRegion);
        turbulenceRable8Bit.setBaseFrequencyX(convertBaseFrenquency[0]);
        turbulenceRable8Bit.setBaseFrequencyY(convertBaseFrenquency[1]);
        turbulenceRable8Bit.setNumOctaves(convertInteger);
        turbulenceRable8Bit.setSeed(convertInteger2);
        turbulenceRable8Bit.setStitched(convertStitchTiles);
        turbulenceRable8Bit.setFractalNoise(convertType);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(turbulenceRable8Bit, element);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, turbulenceRable8Bit, map);
        return turbulenceRable8Bit;
    }
    
    protected static float[] convertBaseFrenquency(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "baseFrequency");
        if (attributeNS.length() == 0) {
            return new float[] { 0.001f, 0.001f };
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
            if (stringTokenizer.hasMoreTokens()) {
                throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "baseFrequency", attributeNS });
            }
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "baseFrequency", attributeNS });
        }
        if (array[0] < 0.0f || array[1] < 0.0f) {
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "baseFrequency", attributeNS });
        }
        return array;
    }
    
    protected static boolean convertStitchTiles(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "stitchTiles");
        if (attributeNS.length() == 0) {
            return false;
        }
        if ("stitch".equals(attributeNS)) {
            return true;
        }
        if ("noStitch".equals(attributeNS)) {
            return false;
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "stitchTiles", attributeNS });
    }
    
    protected static boolean convertType(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "type");
        if (attributeNS.length() == 0) {
            return false;
        }
        if ("fractalNoise".equals(attributeNS)) {
            return true;
        }
        if ("turbulence".equals(attributeNS)) {
            return false;
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "type", attributeNS });
    }
}
