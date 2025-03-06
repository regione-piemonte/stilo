// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.ext.awt.image.Light;
import org.apache.batik.ext.awt.image.renderable.SpecularLightingRable8Bit;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeSpecularLightingElementBridge extends AbstractSVGLightingElementBridge
{
    public String getLocalName() {
        return "feSpecularLighting";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final float convertNumber = AbstractSVGFilterPrimitiveElementBridge.convertNumber(element, "surfaceScale", 1.0f, bridgeContext);
        final float convertNumber2 = AbstractSVGFilterPrimitiveElementBridge.convertNumber(element, "specularConstant", 1.0f, bridgeContext);
        final float convertSpecularExponent = convertSpecularExponent(element, bridgeContext);
        final Light light = AbstractSVGLightingElementBridge.extractLight(element, bridgeContext);
        final double[] convertKernelUnitLength = AbstractSVGLightingElementBridge.convertKernelUnitLength(element, bridgeContext);
        final Filter in = AbstractSVGFilterPrimitiveElementBridge.getIn(element, element2, graphicsNode, filter, map, bridgeContext);
        if (in == null) {
            return null;
        }
        final SpecularLightingRable8Bit specularLightingRable8Bit = new SpecularLightingRable8Bit(in, SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, in.getBounds2D(), rectangle2D, bridgeContext), light, convertNumber2, convertSpecularExponent, convertNumber, convertKernelUnitLength);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(specularLightingRable8Bit, element);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, specularLightingRable8Bit, map);
        return specularLightingRable8Bit;
    }
    
    protected static float convertSpecularExponent(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "specularExponent");
        if (attributeNS.length() == 0) {
            return 1.0f;
        }
        try {
            final float convertSVGNumber = SVGUtilities.convertSVGNumber(attributeNS);
            if (convertSVGNumber < 1.0f || convertSVGNumber > 128.0f) {
                throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "specularConstant", attributeNS });
            }
            return convertSVGNumber;
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "specularConstant", attributeNS, ex });
        }
    }
}
