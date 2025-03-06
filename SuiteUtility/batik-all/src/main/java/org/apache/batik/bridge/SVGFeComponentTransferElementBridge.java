// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.StringTokenizer;
import org.apache.batik.ext.awt.image.ConcreteComponentTransferFunction;
import org.w3c.dom.Node;
import org.apache.batik.ext.awt.image.ComponentTransferFunction;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.ext.awt.image.renderable.ComponentTransferRable8Bit;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeComponentTransferElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    public String getLocalName() {
        return "feComponentTransfer";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final Filter in = AbstractSVGFilterPrimitiveElementBridge.getIn(element, element2, graphicsNode, filter, map, bridgeContext);
        if (in == null) {
            return null;
        }
        final Rectangle2D convertFilterPrimitiveRegion = SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, in.getBounds2D(), rectangle2D, bridgeContext);
        ComponentTransferFunction componentTransferFunction = null;
        ComponentTransferFunction componentTransferFunction2 = null;
        ComponentTransferFunction componentTransferFunction3 = null;
        ComponentTransferFunction componentTransferFunction4 = null;
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 1) {
                final Element element3 = (Element)node;
                final Bridge bridge = bridgeContext.getBridge(element3);
                if (bridge != null) {
                    if (bridge instanceof SVGFeFuncElementBridge) {
                        final SVGFeFuncElementBridge svgFeFuncElementBridge = (SVGFeFuncElementBridge)bridge;
                        final ComponentTransferFunction componentTransferFunction5 = svgFeFuncElementBridge.createComponentTransferFunction(element, element3);
                        if (svgFeFuncElementBridge instanceof SVGFeFuncRElementBridge) {
                            componentTransferFunction = componentTransferFunction5;
                        }
                        else if (svgFeFuncElementBridge instanceof SVGFeFuncGElementBridge) {
                            componentTransferFunction2 = componentTransferFunction5;
                        }
                        else if (svgFeFuncElementBridge instanceof SVGFeFuncBElementBridge) {
                            componentTransferFunction3 = componentTransferFunction5;
                        }
                        else if (svgFeFuncElementBridge instanceof SVGFeFuncAElementBridge) {
                            componentTransferFunction4 = componentTransferFunction5;
                        }
                    }
                }
            }
        }
        final ComponentTransferRable8Bit componentTransferRable8Bit = new ComponentTransferRable8Bit(in, componentTransferFunction4, componentTransferFunction, componentTransferFunction2, componentTransferFunction3);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(componentTransferRable8Bit, element);
        final PadRable8Bit padRable8Bit = new PadRable8Bit(componentTransferRable8Bit, convertFilterPrimitiveRegion, PadMode.ZERO_PAD);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, padRable8Bit, map);
        return padRable8Bit;
    }
    
    protected abstract static class SVGFeFuncElementBridge extends AnimatableGenericSVGBridge
    {
        public ComponentTransferFunction createComponentTransferFunction(final Element element, final Element element2) {
            final int convertType = convertType(element2, this.ctx);
            switch (convertType) {
                case 2: {
                    final float[] convertTableValues = convertTableValues(element2, this.ctx);
                    if (convertTableValues == null) {
                        return ConcreteComponentTransferFunction.getIdentityTransfer();
                    }
                    return ConcreteComponentTransferFunction.getDiscreteTransfer(convertTableValues);
                }
                case 0: {
                    return ConcreteComponentTransferFunction.getIdentityTransfer();
                }
                case 4: {
                    return ConcreteComponentTransferFunction.getGammaTransfer(AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "amplitude", 1.0f, this.ctx), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "exponent", 1.0f, this.ctx), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "offset", 0.0f, this.ctx));
                }
                case 3: {
                    return ConcreteComponentTransferFunction.getLinearTransfer(AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "slope", 1.0f, this.ctx), AbstractSVGFilterPrimitiveElementBridge.convertNumber(element2, "intercept", 0.0f, this.ctx));
                }
                case 1: {
                    final float[] convertTableValues2 = convertTableValues(element2, this.ctx);
                    if (convertTableValues2 == null) {
                        return ConcreteComponentTransferFunction.getIdentityTransfer();
                    }
                    return ConcreteComponentTransferFunction.getTableTransfer(convertTableValues2);
                }
                default: {
                    throw new Error("invalid convertType:" + convertType);
                }
            }
        }
        
        protected static float[] convertTableValues(final Element element, final BridgeContext bridgeContext) {
            final String attributeNS = element.getAttributeNS(null, "tableValues");
            if (attributeNS.length() == 0) {
                return null;
            }
            final StringTokenizer stringTokenizer = new StringTokenizer(attributeNS, " ,");
            final float[] array = new float[stringTokenizer.countTokens()];
            try {
                int n = 0;
                while (stringTokenizer.hasMoreTokens()) {
                    array[n] = SVGUtilities.convertSVGNumber(stringTokenizer.nextToken());
                    ++n;
                }
            }
            catch (NumberFormatException ex) {
                throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "tableValues", attributeNS });
            }
            return array;
        }
        
        protected static int convertType(final Element element, final BridgeContext bridgeContext) {
            final String attributeNS = element.getAttributeNS(null, "type");
            if (attributeNS.length() == 0) {
                throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "type" });
            }
            if ("discrete".equals(attributeNS)) {
                return 2;
            }
            if ("identity".equals(attributeNS)) {
                return 0;
            }
            if ("gamma".equals(attributeNS)) {
                return 4;
            }
            if ("linear".equals(attributeNS)) {
                return 3;
            }
            if ("table".equals(attributeNS)) {
                return 1;
            }
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "type", attributeNS });
        }
    }
    
    public static class SVGFeFuncBElementBridge extends SVGFeFuncElementBridge
    {
        public String getLocalName() {
            return "feFuncB";
        }
    }
    
    public static class SVGFeFuncGElementBridge extends SVGFeFuncElementBridge
    {
        public String getLocalName() {
            return "feFuncG";
        }
    }
    
    public static class SVGFeFuncRElementBridge extends SVGFeFuncElementBridge
    {
        public String getLocalName() {
            return "feFuncR";
        }
    }
    
    public static class SVGFeFuncAElementBridge extends SVGFeFuncElementBridge
    {
        public String getLocalName() {
            return "feFuncA";
        }
    }
}
