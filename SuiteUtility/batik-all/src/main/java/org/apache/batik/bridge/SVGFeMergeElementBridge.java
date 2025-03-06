// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.Node;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.ext.awt.image.renderable.CompositeRable8Bit;
import org.apache.batik.ext.awt.image.CompositeRule;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeMergeElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    public String getLocalName() {
        return "feMerge";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final List feMergeNode = extractFeMergeNode(element, element2, graphicsNode, filter, map, bridgeContext);
        if (feMergeNode == null) {
            return null;
        }
        if (feMergeNode.size() == 0) {
            return null;
        }
        final Iterator<Filter> iterator = feMergeNode.iterator();
        final Rectangle2D rectangle2D2 = (Rectangle2D)iterator.next().getBounds2D().clone();
        while (iterator.hasNext()) {
            rectangle2D2.add(iterator.next().getBounds2D());
        }
        final Rectangle2D convertFilterPrimitiveRegion = SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, rectangle2D2, rectangle2D, bridgeContext);
        final CompositeRable8Bit compositeRable8Bit = new CompositeRable8Bit(feMergeNode, CompositeRule.OVER, true);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(compositeRable8Bit, element);
        final PadRable8Bit padRable8Bit = new PadRable8Bit(compositeRable8Bit, convertFilterPrimitiveRegion, PadMode.ZERO_PAD);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, padRable8Bit, map);
        return padRable8Bit;
    }
    
    protected static List extractFeMergeNode(final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Map map, final BridgeContext bridgeContext) {
        List<Filter> list = null;
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final Element element3 = (Element)node;
                final Bridge bridge = bridgeContext.getBridge(element3);
                if (bridge != null) {
                    if (bridge instanceof SVGFeMergeNodeElementBridge) {
                        final Filter filter2 = ((SVGFeMergeNodeElementBridge)bridge).createFilter(bridgeContext, element3, element2, graphicsNode, filter, map);
                        if (filter2 != null) {
                            if (list == null) {
                                list = new LinkedList<Filter>();
                            }
                            list.add(filter2);
                        }
                    }
                }
            }
        }
        return list;
    }
    
    public static class SVGFeMergeNodeElementBridge extends AnimatableGenericSVGBridge
    {
        public String getLocalName() {
            return "feMergeNode";
        }
        
        public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Map map) {
            return AbstractSVGFilterPrimitiveElementBridge.getIn(element, element2, graphicsNode, filter, map, bridgeContext);
        }
    }
}
