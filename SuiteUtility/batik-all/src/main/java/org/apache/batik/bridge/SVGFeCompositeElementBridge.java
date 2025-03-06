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

public class SVGFeCompositeElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    public String getLocalName() {
        return "feComposite";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final CompositeRule convertOperator = convertOperator(element, bridgeContext);
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
        final CompositeRable8Bit compositeRable8Bit = new CompositeRable8Bit(list, convertOperator, true);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(compositeRable8Bit, element);
        final PadRable8Bit padRable8Bit = new PadRable8Bit(compositeRable8Bit, convertFilterPrimitiveRegion, PadMode.ZERO_PAD);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, padRable8Bit, map);
        return padRable8Bit;
    }
    
    protected static CompositeRule convertOperator(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "operator");
        if (attributeNS.length() == 0) {
            return CompositeRule.OVER;
        }
        if ("atop".equals(attributeNS)) {
            return CompositeRule.ATOP;
        }
        if ("in".equals(attributeNS)) {
            return CompositeRule.IN;
        }
        if ("over".equals(attributeNS)) {
            return CompositeRule.OVER;
        }
        if ("out".equals(attributeNS)) {
            return CompositeRule.OUT;
        }
        if ("xor".equals(attributeNS)) {
            return CompositeRule.XOR;
        }
        if ("arithmetic".equals(attributeNS)) {
            return CompositeRule.ARITHMETIC(AbstractSVGFilterPrimitiveElementBridge.convertNumber(element, "k1", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element, "k2", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element, "k3", 0.0f, bridgeContext), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element, "k4", 0.0f, bridgeContext));
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "operator", attributeNS });
    }
}
