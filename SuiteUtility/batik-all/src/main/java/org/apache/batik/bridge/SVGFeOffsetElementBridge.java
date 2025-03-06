// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.ext.awt.image.renderable.AffineRable8Bit;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeOffsetElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    public String getLocalName() {
        return "feOffset";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final Filter in = AbstractSVGFilterPrimitiveElementBridge.getIn(element, element2, graphicsNode, filter, map, bridgeContext);
        if (in == null) {
            return null;
        }
        final Rectangle2D convertFilterPrimitiveRegion = SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, in.getBounds2D(), rectangle2D, bridgeContext);
        final PadRable8Bit padRable8Bit = new PadRable8Bit(new AffineRable8Bit(new PadRable8Bit(in, convertFilterPrimitiveRegion, PadMode.ZERO_PAD), AffineTransform.getTranslateInstance(AbstractSVGFilterPrimitiveElementBridge.convertNumber(element, "dx", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element, "dy", 0.0f, bridgeContext))), convertFilterPrimitiveRegion, PadMode.ZERO_PAD);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(padRable8Bit, element);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, padRable8Bit, map);
        return padRable8Bit;
    }
}
