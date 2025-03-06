// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.Paint;
import java.awt.Color;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.gvt.filter.BackgroundRable8Bit;
import org.apache.batik.ext.awt.image.renderable.FloodRable8Bit;
import org.apache.batik.ext.awt.image.renderable.FilterAlphaRable;
import org.apache.batik.ext.awt.image.renderable.FilterColorInterpolation;
import java.util.Map;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;
import java.awt.geom.Rectangle2D;

public abstract class AbstractSVGFilterPrimitiveElementBridge extends AnimatableGenericSVGBridge implements FilterPrimitiveBridge, ErrorConstants
{
    static final Rectangle2D INFINITE_FILTER_REGION;
    
    protected AbstractSVGFilterPrimitiveElementBridge() {
    }
    
    protected static Filter getIn(final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Map map, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "in");
        if (attributeNS.length() == 0) {
            return filter;
        }
        return getFilterSource(element, attributeNS, element2, graphicsNode, map, bridgeContext);
    }
    
    protected static Filter getIn2(final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Map map, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "in2");
        if (attributeNS.length() == 0) {
            throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "in2" });
        }
        return getFilterSource(element, attributeNS, element2, graphicsNode, map, bridgeContext);
    }
    
    protected static void updateFilterMap(final Element element, final Filter filter, final Map map) {
        final String attributeNS = element.getAttributeNS(null, "result");
        if (attributeNS.length() != 0 && attributeNS.trim().length() != 0) {
            map.put(attributeNS, filter);
        }
    }
    
    protected static void handleColorInterpolationFilters(final Filter filter, final Element element) {
        if (filter instanceof FilterColorInterpolation) {
            ((FilterColorInterpolation)filter).setColorSpaceLinear(CSSUtilities.convertColorInterpolationFilters(element));
        }
    }
    
    static Filter getFilterSource(final Element element, final String s, final Element element2, final GraphicsNode graphicsNode, final Map map, final BridgeContext bridgeContext) {
        final Filter filter = map.get("SourceGraphic");
        final Rectangle2D bounds2D = filter.getBounds2D();
        final int length = s.length();
        Filter filter2 = null;
        switch (length) {
            case 13: {
                if ("SourceGraphic".equals(s)) {
                    filter2 = filter;
                    break;
                }
                break;
            }
            case 11: {
                if (s.charAt(1) == "SourceAlpha".charAt(1)) {
                    if ("SourceAlpha".equals(s)) {
                        filter2 = new FilterAlphaRable(filter);
                        break;
                    }
                    break;
                }
                else {
                    if ("StrokePaint".equals(s)) {
                        filter2 = new FloodRable8Bit(bounds2D, PaintServer.convertStrokePaint(element2, graphicsNode, bridgeContext));
                        break;
                    }
                    break;
                }
                break;
            }
            case 15: {
                if (s.charAt(10) == "BackgroundImage".charAt(10)) {
                    if ("BackgroundImage".equals(s)) {
                        filter2 = new PadRable8Bit(new BackgroundRable8Bit(graphicsNode), bounds2D, PadMode.ZERO_PAD);
                        break;
                    }
                    break;
                }
                else {
                    if ("BackgroundAlpha".equals(s)) {
                        filter2 = new PadRable8Bit(new FilterAlphaRable(new BackgroundRable8Bit(graphicsNode)), bounds2D, PadMode.ZERO_PAD);
                        break;
                    }
                    break;
                }
                break;
            }
            case 9: {
                if ("FillPaint".equals(s)) {
                    Paint convertFillPaint = PaintServer.convertFillPaint(element2, graphicsNode, bridgeContext);
                    if (convertFillPaint == null) {
                        convertFillPaint = new Color(0, 0, 0, 0);
                    }
                    filter2 = new FloodRable8Bit(bounds2D, convertFillPaint);
                    break;
                }
                break;
            }
        }
        if (filter2 == null) {
            filter2 = map.get(s);
        }
        return filter2;
    }
    
    protected static int convertInteger(final Element element, final String s, final int n, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, s);
        if (attributeNS.length() == 0) {
            return n;
        }
        try {
            return SVGUtilities.convertSVGInteger(attributeNS);
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { s, attributeNS });
        }
    }
    
    protected static float convertNumber(final Element element, final String s, final float n, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, s);
        if (attributeNS.length() == 0) {
            return n;
        }
        try {
            return SVGUtilities.convertSVGNumber(attributeNS);
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { s, attributeNS, ex });
        }
    }
    
    static {
        INFINITE_FILTER_REGION = new Rectangle2D.Float(-1.7014117E38f, -1.7014117E38f, Float.MAX_VALUE, Float.MAX_VALUE);
    }
}
