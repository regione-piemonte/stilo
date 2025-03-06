// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.StringTokenizer;
import org.apache.batik.ext.awt.image.renderable.ColorMatrixRable;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.ext.awt.image.renderable.ColorMatrixRable8Bit;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeColorMatrixElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    public String getLocalName() {
        return "feColorMatrix";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final Filter in = AbstractSVGFilterPrimitiveElementBridge.getIn(element, element2, graphicsNode, filter, map, bridgeContext);
        if (in == null) {
            return null;
        }
        final Rectangle2D convertFilterPrimitiveRegion = SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, in.getBounds2D(), rectangle2D, bridgeContext);
        final int convertType = convertType(element, bridgeContext);
        ColorMatrixRable colorMatrixRable = null;
        switch (convertType) {
            case 2: {
                colorMatrixRable = ColorMatrixRable8Bit.buildHueRotate(convertValuesToHueRotate(element, bridgeContext));
                break;
            }
            case 3: {
                colorMatrixRable = ColorMatrixRable8Bit.buildLuminanceToAlpha();
                break;
            }
            case 0: {
                colorMatrixRable = ColorMatrixRable8Bit.buildMatrix(convertValuesToMatrix(element, bridgeContext));
                break;
            }
            case 1: {
                colorMatrixRable = ColorMatrixRable8Bit.buildSaturate(convertValuesToSaturate(element, bridgeContext));
                break;
            }
            default: {
                throw new Error("invalid convertType:" + convertType);
            }
        }
        colorMatrixRable.setSource(in);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(colorMatrixRable, element);
        final PadRable8Bit padRable8Bit = new PadRable8Bit(colorMatrixRable, convertFilterPrimitiveRegion, PadMode.ZERO_PAD);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, padRable8Bit, map);
        return padRable8Bit;
    }
    
    protected static float[][] convertValuesToMatrix(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "values");
        final float[][] array = new float[4][5];
        if (attributeNS.length() == 0) {
            array[0][0] = 1.0f;
            array[1][1] = 1.0f;
            array[2][2] = 1.0f;
            array[3][3] = 1.0f;
            return array;
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(attributeNS, " ,");
        int n = 0;
        try {
            while (n < 20 && stringTokenizer.hasMoreTokens()) {
                array[n / 5][n % 5] = SVGUtilities.convertSVGNumber(stringTokenizer.nextToken());
                ++n;
            }
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "values", attributeNS, ex });
        }
        if (n != 20 || stringTokenizer.hasMoreTokens()) {
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "values", attributeNS });
        }
        for (int i = 0; i < 4; ++i) {
            final float[] array2 = array[i];
            final int n2 = 4;
            array2[n2] *= 255.0f;
        }
        return array;
    }
    
    protected static float convertValuesToSaturate(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "values");
        if (attributeNS.length() == 0) {
            return 1.0f;
        }
        try {
            return SVGUtilities.convertSVGNumber(attributeNS);
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "values", attributeNS });
        }
    }
    
    protected static float convertValuesToHueRotate(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "values");
        if (attributeNS.length() == 0) {
            return 0.0f;
        }
        try {
            return (float)Math.toRadians(SVGUtilities.convertSVGNumber(attributeNS));
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "values", attributeNS });
        }
    }
    
    protected static int convertType(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "type");
        if (attributeNS.length() == 0) {
            return 0;
        }
        if ("hueRotate".equals(attributeNS)) {
            return 2;
        }
        if ("luminanceToAlpha".equals(attributeNS)) {
            return 3;
        }
        if ("matrix".equals(attributeNS)) {
            return 0;
        }
        if ("saturate".equals(attributeNS)) {
            return 1;
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "type", attributeNS });
    }
}
