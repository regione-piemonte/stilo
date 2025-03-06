// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.List;
import org.apache.batik.ext.awt.image.renderable.DisplacementMapRable8Bit;
import java.util.ArrayList;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.ext.awt.image.ARGBChannel;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeDisplacementMapElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    public String getLocalName() {
        return "feDisplacementMap";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final float convertNumber = AbstractSVGFilterPrimitiveElementBridge.convertNumber(element, "scale", 0.0f, bridgeContext);
        final ARGBChannel convertChannelSelector = convertChannelSelector(element, "xChannelSelector", ARGBChannel.A, bridgeContext);
        final ARGBChannel convertChannelSelector2 = convertChannelSelector(element, "yChannelSelector", ARGBChannel.A, bridgeContext);
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
        final PadRable8Bit padRable8Bit = new PadRable8Bit(in, convertFilterPrimitiveRegion, PadMode.ZERO_PAD);
        final ArrayList<PadRable8Bit> list = new ArrayList<PadRable8Bit>(2);
        list.add(padRable8Bit);
        list.add((PadRable8Bit)in2);
        final DisplacementMapRable8Bit displacementMapRable8Bit = new DisplacementMapRable8Bit(list, convertNumber, convertChannelSelector, convertChannelSelector2);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(displacementMapRable8Bit, element);
        final PadRable8Bit padRable8Bit2 = new PadRable8Bit(displacementMapRable8Bit, convertFilterPrimitiveRegion, PadMode.ZERO_PAD);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, padRable8Bit2, map);
        return padRable8Bit2;
    }
    
    protected static ARGBChannel convertChannelSelector(final Element element, final String s, final ARGBChannel argbChannel, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, s);
        if (attributeNS.length() == 0) {
            return argbChannel;
        }
        if ("A".equals(attributeNS)) {
            return ARGBChannel.A;
        }
        if ("R".equals(attributeNS)) {
            return ARGBChannel.R;
        }
        if ("G".equals(attributeNS)) {
            return ARGBChannel.G;
        }
        if ("B".equals(attributeNS)) {
            return ARGBChannel.B;
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { s, attributeNS });
    }
}
