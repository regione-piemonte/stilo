// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.ext.awt.image.SpotLight;
import org.apache.batik.ext.awt.image.DistantLight;
import org.apache.batik.ext.awt.image.PointLight;
import java.util.StringTokenizer;
import org.w3c.dom.Node;
import java.awt.Color;
import org.apache.batik.ext.awt.image.Light;
import org.w3c.dom.Element;

public abstract class AbstractSVGLightingElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    protected AbstractSVGLightingElementBridge() {
    }
    
    protected static Light extractLight(final Element element, final BridgeContext bridgeContext) {
        final Color convertLightingColor = CSSUtilities.convertLightingColor(element, bridgeContext);
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final Element element2 = (Element)node;
                final Bridge bridge = bridgeContext.getBridge(element2);
                if (bridge != null) {
                    if (bridge instanceof AbstractSVGLightElementBridge) {
                        return ((AbstractSVGLightElementBridge)bridge).createLight(bridgeContext, element, element2, convertLightingColor);
                    }
                }
            }
        }
        return null;
    }
    
    protected static double[] convertKernelUnitLength(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "kernelUnitLength");
        if (attributeNS.length() == 0) {
            return null;
        }
        final double[] array = new double[2];
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
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "kernelUnitLength", attributeNS });
        }
        if (stringTokenizer.hasMoreTokens() || array[0] <= 0.0 || array[1] <= 0.0) {
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "kernelUnitLength", attributeNS });
        }
        return array;
    }
    
    public static class SVGFePointLightElementBridge extends AbstractSVGLightElementBridge
    {
        public String getLocalName() {
            return "fePointLight";
        }
        
        public Light createLight(final BridgeContext bridgeContext, final Element element, final Element element2, final Color color) {
            return new PointLight(AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "x", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "y", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "z", 0.0f, bridgeContext), color);
        }
    }
    
    public static class SVGFeDistantLightElementBridge extends AbstractSVGLightElementBridge
    {
        public String getLocalName() {
            return "feDistantLight";
        }
        
        public Light createLight(final BridgeContext bridgeContext, final Element element, final Element element2, final Color color) {
            return new DistantLight(AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "azimuth", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "elevation", 0.0f, bridgeContext), color);
        }
    }
    
    public static class SVGFeSpotLightElementBridge extends AbstractSVGLightElementBridge
    {
        public String getLocalName() {
            return "feSpotLight";
        }
        
        public Light createLight(final BridgeContext bridgeContext, final Element element, final Element element2, final Color color) {
            return new SpotLight(AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "x", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "y", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "z", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "pointsAtX", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "pointsAtY", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "pointsAtZ", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "specularExponent", 1.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "limitingConeAngle", 90.0f, bridgeContext), color);
        }
    }
    
    protected abstract static class AbstractSVGLightElementBridge extends AnimatableGenericSVGBridge
    {
        public abstract Light createLight(final BridgeContext p0, final Element p1, final Element p2, final Color p3);
    }
}
