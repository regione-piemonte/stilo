// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.ext.awt.image.CompositeRule;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import java.util.List;
import org.apache.batik.ext.awt.image.renderable.CompositeRable8Bit;
import java.util.ArrayList;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeBlendElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    public String getLocalName() {
        return "feBlend";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final CompositeRule convertMode = convertMode(element, bridgeContext);
        final Filter in = AbstractSVGFilterPrimitiveElementBridge.getIn(element, element2, graphicsNode, filter, map, bridgeContext);
        if (in == null) {
            return null;
        }
        final Filter in2 = AbstractSVGFilterPrimitiveElementBridge.getIn2(element, element2, graphicsNode, filter, map, bridgeContext);
        if (in2 == null) {
            return null;
        }
        final Rectangle2D rectangle2D2 = (Rectangle2D)in.getBounds2D().clone();
        rectangle2D2.add(in2.getBounds2D());
        final Rectangle2D convertFilterPrimitiveRegion = SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, rectangle2D2, rectangle2D, bridgeContext);
        final ArrayList<Filter> list = new ArrayList<Filter>(2);
        list.add(in2);
        list.add(in);
        final CompositeRable8Bit compositeRable8Bit = new CompositeRable8Bit(list, convertMode, true);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(compositeRable8Bit, element);
        final PadRable8Bit padRable8Bit = new PadRable8Bit(compositeRable8Bit, convertFilterPrimitiveRegion, PadMode.ZERO_PAD);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, padRable8Bit, map);
        return padRable8Bit;
    }
    
    protected static CompositeRule convertMode(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "mode");
        if (attributeNS.length() == 0) {
            return CompositeRule.OVER;
        }
        if ("normal".equals(attributeNS)) {
            return CompositeRule.OVER;
        }
        if ("multiply".equals(attributeNS)) {
            return CompositeRule.MULTIPLY;
        }
        if ("screen".equals(attributeNS)) {
            return CompositeRule.SCREEN;
        }
        if ("darken".equals(attributeNS)) {
            return CompositeRule.DARKEN;
        }
        if ("lighten".equals(attributeNS)) {
            return CompositeRule.LIGHTEN;
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "mode", attributeNS });
    }
}
