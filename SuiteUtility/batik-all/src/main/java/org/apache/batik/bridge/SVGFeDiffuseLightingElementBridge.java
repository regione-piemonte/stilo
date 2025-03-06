// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.ext.awt.image.Light;
import org.apache.batik.ext.awt.image.renderable.DiffuseLightingRable8Bit;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeDiffuseLightingElementBridge extends AbstractSVGLightingElementBridge
{
    public String getLocalName() {
        return "feDiffuseLighting";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final float convertNumber = AbstractSVGFilterPrimitiveElementBridge.convertNumber(element, "surfaceScale", 1.0f, bridgeContext);
        final float convertNumber2 = AbstractSVGFilterPrimitiveElementBridge.convertNumber(element, "diffuseConstant", 1.0f, bridgeContext);
        final Light light = AbstractSVGLightingElementBridge.extractLight(element, bridgeContext);
        final double[] convertKernelUnitLength = AbstractSVGLightingElementBridge.convertKernelUnitLength(element, bridgeContext);
        final Filter in = AbstractSVGFilterPrimitiveElementBridge.getIn(element, element2, graphicsNode, filter, map, bridgeContext);
        if (in == null) {
            return null;
        }
        final DiffuseLightingRable8Bit diffuseLightingRable8Bit = new DiffuseLightingRable8Bit(in, SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, in.getBounds2D(), rectangle2D, bridgeContext), light, convertNumber2, convertNumber, convertKernelUnitLength);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(diffuseLightingRable8Bit, element);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, diffuseLightingRable8Bit, map);
        return diffuseLightingRable8Bit;
    }
}
