// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.Paint;
import org.apache.batik.ext.awt.image.renderable.FloodRable8Bit;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeFloodElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    public String getLocalName() {
        return "feFlood";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final FloodRable8Bit floodRable8Bit = new FloodRable8Bit(SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, rectangle2D, rectangle2D, bridgeContext), CSSUtilities.convertFloodColor(element, bridgeContext));
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(floodRable8Bit, element);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, floodRable8Bit, map);
        return floodRable8Bit;
    }
}
