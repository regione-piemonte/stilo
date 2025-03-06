// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.StringTokenizer;
import java.awt.Point;
import java.awt.image.Kernel;
import org.apache.batik.ext.awt.image.renderable.ConvolveMatrixRable8Bit;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Element;

public class SVGFeConvolveMatrixElementBridge extends AbstractSVGFilterPrimitiveElementBridge
{
    public String getLocalName() {
        return "feConvolveMatrix";
    }
    
    public Filter createFilter(final BridgeContext bridgeContext, final Element element, final Element element2, final GraphicsNode graphicsNode, final Filter filter, final Rectangle2D rectangle2D, final Map map) {
        final int[] convertOrder = convertOrder(element, bridgeContext);
        final float[] convertKernelMatrix = convertKernelMatrix(element, convertOrder, bridgeContext);
        final float convertDivisor = convertDivisor(element, convertKernelMatrix, bridgeContext);
        final float convertNumber = AbstractSVGFilterPrimitiveElementBridge.convertNumber(element, "bias", 0.0f, bridgeContext);
        final int[] convertTarget = convertTarget(element, convertOrder, bridgeContext);
        final PadMode convertEdgeMode = convertEdgeMode(element, bridgeContext);
        final double[] convertKernelUnitLength = convertKernelUnitLength(element, bridgeContext);
        final boolean convertPreserveAlpha = convertPreserveAlpha(element, bridgeContext);
        final Filter in = AbstractSVGFilterPrimitiveElementBridge.getIn(element, element2, graphicsNode, filter, map, bridgeContext);
        if (in == null) {
            return null;
        }
        final Rectangle2D convertFilterPrimitiveRegion = SVGUtilities.convertFilterPrimitiveRegion(element, element2, graphicsNode, in.getBounds2D(), rectangle2D, bridgeContext);
        final ConvolveMatrixRable8Bit convolveMatrixRable8Bit = new ConvolveMatrixRable8Bit(new PadRable8Bit(in, convertFilterPrimitiveRegion, PadMode.ZERO_PAD));
        for (int i = 0; i < convertKernelMatrix.length; ++i) {
            final float[] array = convertKernelMatrix;
            final int n = i;
            array[n] /= convertDivisor;
        }
        convolveMatrixRable8Bit.setKernel(new Kernel(convertOrder[0], convertOrder[1], convertKernelMatrix));
        convolveMatrixRable8Bit.setTarget(new Point(convertTarget[0], convertTarget[1]));
        convolveMatrixRable8Bit.setBias(convertNumber);
        convolveMatrixRable8Bit.setEdgeMode(convertEdgeMode);
        convolveMatrixRable8Bit.setKernelUnitLength(convertKernelUnitLength);
        convolveMatrixRable8Bit.setPreserveAlpha(convertPreserveAlpha);
        AbstractSVGFilterPrimitiveElementBridge.handleColorInterpolationFilters(convolveMatrixRable8Bit, element);
        final PadRable8Bit padRable8Bit = new PadRable8Bit(convolveMatrixRable8Bit, convertFilterPrimitiveRegion, PadMode.ZERO_PAD);
        AbstractSVGFilterPrimitiveElementBridge.updateFilterMap(element, padRable8Bit, map);
        return padRable8Bit;
    }
    
    protected static int[] convertOrder(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "order");
        if (attributeNS.length() == 0) {
            return new int[] { 3, 3 };
        }
        final int[] array = new int[2];
        final StringTokenizer stringTokenizer = new StringTokenizer(attributeNS, " ,");
        try {
            array[0] = SVGUtilities.convertSVGInteger(stringTokenizer.nextToken());
            if (stringTokenizer.hasMoreTokens()) {
                array[1] = SVGUtilities.convertSVGInteger(stringTokenizer.nextToken());
            }
            else {
                array[1] = array[0];
            }
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "order", attributeNS, ex });
        }
        if (stringTokenizer.hasMoreTokens() || array[0] <= 0 || array[1] <= 0) {
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "order", attributeNS });
        }
        return array;
    }
    
    protected static float[] convertKernelMatrix(final Element element, final int[] array, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "kernelMatrix");
        if (attributeNS.length() == 0) {
            throw new BridgeException(bridgeContext, element, "attribute.missing", new Object[] { "kernelMatrix" });
        }
        final int n = array[0] * array[1];
        final float[] array2 = new float[n];
        final StringTokenizer stringTokenizer = new StringTokenizer(attributeNS, " ,");
        int n2 = 0;
        try {
            while (stringTokenizer.hasMoreTokens() && n2 < n) {
                array2[n2++] = SVGUtilities.convertSVGNumber(stringTokenizer.nextToken());
            }
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "kernelMatrix", attributeNS, ex });
        }
        if (n2 != n) {
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "kernelMatrix", attributeNS });
        }
        return array2;
    }
    
    protected static float convertDivisor(final Element element, final float[] array, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "divisor");
        if (attributeNS.length() == 0) {
            float n = 0.0f;
            for (int i = 0; i < array.length; ++i) {
                n += array[i];
            }
            return (n == 0.0f) ? 1.0f : n;
        }
        try {
            return SVGUtilities.convertSVGNumber(attributeNS);
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "divisor", attributeNS, ex });
        }
    }
    
    protected static int[] convertTarget(final Element element, final int[] array, final BridgeContext bridgeContext) {
        final int[] array2 = new int[2];
        final String attributeNS = element.getAttributeNS(null, "targetX");
        if (attributeNS.length() == 0) {
            array2[0] = array[0] / 2;
        }
        else {
            try {
                final int convertSVGInteger = SVGUtilities.convertSVGInteger(attributeNS);
                if (convertSVGInteger < 0 || convertSVGInteger >= array[0]) {
                    throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "targetX", attributeNS });
                }
                array2[0] = convertSVGInteger;
            }
            catch (NumberFormatException ex) {
                throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "targetX", attributeNS, ex });
            }
        }
        final String attributeNS2 = element.getAttributeNS(null, "targetY");
        if (attributeNS2.length() == 0) {
            array2[1] = array[1] / 2;
        }
        else {
            try {
                final int convertSVGInteger2 = SVGUtilities.convertSVGInteger(attributeNS2);
                if (convertSVGInteger2 < 0 || convertSVGInteger2 >= array[1]) {
                    throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "targetY", attributeNS2 });
                }
                array2[1] = convertSVGInteger2;
            }
            catch (NumberFormatException ex2) {
                throw new BridgeException(bridgeContext, element, ex2, "attribute.malformed", new Object[] { "targetY", attributeNS2, ex2 });
            }
        }
        return array2;
    }
    
    protected static double[] convertKernelUnitLength(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "kernelUnitLength");
        if (attributeNS.length() == 0) {
            return null;
        }
        final double[] array = new double[2];
        final StringTokenizer stringTokenizer = new StringTokenizer(attributeNS, " ,");
        try {
            array[0] = SVGUtilities.convertSVGNumber(stringTokenizer.nextToken());
            if (stringTokenizer.hasMoreTokens()) {
                array[1] = SVGUtilities.convertSVGNumber(stringTokenizer.nextToken());
            }
            else {
                array[1] = array[0];
            }
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "kernelUnitLength", attributeNS });
        }
        if (stringTokenizer.hasMoreTokens() || array[0] <= 0.0 || array[1] <= 0.0) {
            throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "kernelUnitLength", attributeNS });
        }
        return array;
    }
    
    protected static PadMode convertEdgeMode(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "edgeMode");
        if (attributeNS.length() == 0) {
            return PadMode.REPLICATE;
        }
        if ("duplicate".equals(attributeNS)) {
            return PadMode.REPLICATE;
        }
        if ("wrap".equals(attributeNS)) {
            return PadMode.WRAP;
        }
        if ("none".equals(attributeNS)) {
            return PadMode.ZERO_PAD;
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "edgeMode", attributeNS });
    }
    
    protected static boolean convertPreserveAlpha(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "preserveAlpha");
        if (attributeNS.length() == 0) {
            return false;
        }
        if ("true".equals(attributeNS)) {
            return true;
        }
        if ("false".equals(attributeNS)) {
            return false;
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "preserveAlpha", attributeNS });
    }
}
