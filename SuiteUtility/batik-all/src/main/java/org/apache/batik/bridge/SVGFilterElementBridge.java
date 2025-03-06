// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.Node;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.util.ParsedURL;
import java.util.LinkedList;
import java.awt.Paint;
import org.apache.batik.ext.awt.image.renderable.FloodRable8Bit;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.HashMap;
import org.apache.batik.ext.awt.image.renderable.FilterChainRable8Bit;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;
import java.awt.Color;

public class SVGFilterElementBridge extends AnimatableGenericSVGBridge implements FilterBridge, ErrorConstants
{
    protected static final Color TRANSPARENT_BLACK;
    
    public String getLocalName() {
        return "filter";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode) {
        final Rectangle2D convertFilterChainRegion = SVGUtilities.convertFilterChainRegion(element, element2, graphicsNode, bridgeContext);
        if (convertFilterChainRegion == null) {
            return null;
        }
        final PadRable8Bit padRable8Bit = new PadRable8Bit(graphicsNode.getGraphicsNodeRable(true), convertFilterChainRegion, PadMode.ZERO_PAD);
        final FilterChainRable8Bit filterChainRable8Bit = new FilterChainRable8Bit(padRable8Bit, convertFilterChainRegion);
        final float[] convertFilterRes = SVGUtilities.convertFilterRes(element, bridgeContext);
        filterChainRable8Bit.setFilterResolutionX((int)convertFilterRes[0]);
        filterChainRable8Bit.setFilterResolutionY((int)convertFilterRes[1]);
        final HashMap<String, PadRable8Bit> hashMap = new HashMap<String, PadRable8Bit>(11);
        hashMap.put("SourceGraphic", padRable8Bit);
        Filter source = buildFilterPrimitives(element, convertFilterChainRegion, element2, graphicsNode, padRable8Bit, hashMap, bridgeContext);
        if (source == null) {
            return null;
        }
        if (source == padRable8Bit) {
            source = createEmptyFilter(element, convertFilterChainRegion, element2, graphicsNode, bridgeContext);
        }
        filterChainRable8Bit.setSource(source);
        return filterChainRable8Bit;
    }
    
    protected static Filter createEmptyFilter(final Element element, final Rectangle2D rectangle2D, final Element element2, final GraphicsNode graphicsNode, final BridgeContext bridgeContext) {
        return new FloodRable8Bit(SVGUtilities.convertFilterPrimitiveRegion(null, element, element2, graphicsNode, rectangle2D, rectangle2D, bridgeContext), SVGFilterElementBridge.TRANSPARENT_BLACK);
    }
    
    protected static Filter buildFilterPrimitives(Element referencedElement, final Rectangle2D rectangle2D, final Element element, final GraphicsNode graphicsNode, final Filter filter, final Map map, final BridgeContext bridgeContext) {
        final LinkedList list = new LinkedList<ParsedURL>();
        while (true) {
            final Filter buildLocalFilterPrimitives = buildLocalFilterPrimitives(referencedElement, rectangle2D, element, graphicsNode, filter, map, bridgeContext);
            if (buildLocalFilterPrimitives != filter) {
                return buildLocalFilterPrimitives;
            }
            final String xLinkHref = XLinkSupport.getXLinkHref(referencedElement);
            if (xLinkHref.length() == 0) {
                return filter;
            }
            final ParsedURL parsedURL = new ParsedURL(((SVGOMDocument)referencedElement.getOwnerDocument()).getURLObject(), xLinkHref);
            if (list.contains(parsedURL)) {
                throw new BridgeException(bridgeContext, referencedElement, "xlink.href.circularDependencies", new Object[] { xLinkHref });
            }
            list.add(parsedURL);
            referencedElement = bridgeContext.getReferencedElement(referencedElement, xLinkHref);
        }
    }
    
    protected static Filter buildLocalFilterPrimitives(final Element element, final Rectangle2D rectangle2D, final Element element2, final GraphicsNode graphicsNode, Filter filter, final Map map, final BridgeContext bridgeContext) {
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final Element element3 = (Element)node;
                final Bridge bridge = bridgeContext.getBridge(element3);
                if (bridge != null) {
                    if (bridge instanceof FilterPrimitiveBridge) {
                        final Filter filter2 = ((FilterPrimitiveBridge)bridge).createFilter(bridgeContext, element3, element2, graphicsNode, filter, rectangle2D, map);
                        if (filter2 == null) {
                            return null;
                        }
                        filter = filter2;
                    }
                }
            }
        }
        return filter;
    }
    
    static {
        TRANSPARENT_BLACK = new Color(0, true);
    }
}
