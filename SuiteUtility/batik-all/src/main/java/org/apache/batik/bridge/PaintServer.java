// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.BasicStroke;
import org.apache.batik.ext.awt.color.ICCColorSpaceExt;
import java.awt.Color;
import org.apache.batik.css.engine.value.svg.ICCColor;
import java.awt.Stroke;
import java.awt.Paint;
import java.awt.Shape;
import org.apache.batik.gvt.CompositeShapePainter;
import org.apache.batik.gvt.StrokeShapePainter;
import org.apache.batik.gvt.FillShapePainter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.gvt.Marker;
import org.apache.batik.gvt.MarkerShapePainter;
import org.apache.batik.gvt.ShapePainter;
import org.apache.batik.gvt.ShapeNode;
import org.w3c.dom.Element;
import org.apache.batik.util.CSSConstants;
import org.apache.batik.util.SVGConstants;

public abstract class PaintServer implements SVGConstants, CSSConstants, ErrorConstants
{
    protected PaintServer() {
    }
    
    public static ShapePainter convertMarkers(final Element element, final ShapeNode shapeNode, final BridgeContext bridgeContext) {
        final Marker convertMarker = convertMarker(element, CSSUtilities.getComputedStyle(element, 36), bridgeContext);
        final Marker convertMarker2 = convertMarker(element, CSSUtilities.getComputedStyle(element, 35), bridgeContext);
        final Marker convertMarker3 = convertMarker(element, CSSUtilities.getComputedStyle(element, 34), bridgeContext);
        if (convertMarker != null || convertMarker2 != null || convertMarker3 != null) {
            final MarkerShapePainter markerShapePainter = new MarkerShapePainter(shapeNode.getShape());
            markerShapePainter.setStartMarker(convertMarker);
            markerShapePainter.setMiddleMarker(convertMarker2);
            markerShapePainter.setEndMarker(convertMarker3);
            return markerShapePainter;
        }
        return null;
    }
    
    public static Marker convertMarker(final Element element, final Value value, final BridgeContext bridgeContext) {
        if (value.getPrimitiveType() == 21) {
            return null;
        }
        final String stringValue = value.getStringValue();
        final Element referencedElement = bridgeContext.getReferencedElement(element, stringValue);
        final Bridge bridge = bridgeContext.getBridge(referencedElement);
        if (bridge == null || !(bridge instanceof MarkerBridge)) {
            throw new BridgeException(bridgeContext, element, "css.uri.badTarget", new Object[] { stringValue });
        }
        return ((MarkerBridge)bridge).createMarker(bridgeContext, referencedElement, element);
    }
    
    public static ShapePainter convertFillAndStroke(final Element element, final ShapeNode shapeNode, final BridgeContext bridgeContext) {
        final Shape shape = shapeNode.getShape();
        if (shape == null) {
            return null;
        }
        final Paint convertFillPaint = convertFillPaint(element, shapeNode, bridgeContext);
        final FillShapePainter fillShapePainter = new FillShapePainter(shape);
        fillShapePainter.setPaint(convertFillPaint);
        final Stroke convertStroke = convertStroke(element);
        if (convertStroke == null) {
            return fillShapePainter;
        }
        final Paint convertStrokePaint = convertStrokePaint(element, shapeNode, bridgeContext);
        final StrokeShapePainter strokeShapePainter = new StrokeShapePainter(shape);
        strokeShapePainter.setStroke(convertStroke);
        strokeShapePainter.setPaint(convertStrokePaint);
        final CompositeShapePainter compositeShapePainter = new CompositeShapePainter(shape);
        compositeShapePainter.addShapePainter(fillShapePainter);
        compositeShapePainter.addShapePainter(strokeShapePainter);
        return compositeShapePainter;
    }
    
    public static ShapePainter convertStrokePainter(final Element element, final ShapeNode shapeNode, final BridgeContext bridgeContext) {
        final Shape shape = shapeNode.getShape();
        if (shape == null) {
            return null;
        }
        final Stroke convertStroke = convertStroke(element);
        if (convertStroke == null) {
            return null;
        }
        final Paint convertStrokePaint = convertStrokePaint(element, shapeNode, bridgeContext);
        final StrokeShapePainter strokeShapePainter = new StrokeShapePainter(shape);
        strokeShapePainter.setStroke(convertStroke);
        strokeShapePainter.setPaint(convertStrokePaint);
        return strokeShapePainter;
    }
    
    public static Paint convertStrokePaint(final Element element, final GraphicsNode graphicsNode, final BridgeContext bridgeContext) {
        return convertPaint(element, graphicsNode, CSSUtilities.getComputedStyle(element, 45), convertOpacity(CSSUtilities.getComputedStyle(element, 51)), bridgeContext);
    }
    
    public static Paint convertFillPaint(final Element element, final GraphicsNode graphicsNode, final BridgeContext bridgeContext) {
        return convertPaint(element, graphicsNode, CSSUtilities.getComputedStyle(element, 15), convertOpacity(CSSUtilities.getComputedStyle(element, 16)), bridgeContext);
    }
    
    public static Paint convertPaint(final Element element, final GraphicsNode graphicsNode, final Value value, final float n, final BridgeContext bridgeContext) {
        if (value.getCssValueType() == 1) {
            switch (value.getPrimitiveType()) {
                case 21: {
                    return null;
                }
                case 25: {
                    return convertColor(value, n);
                }
                case 20: {
                    return convertURIPaint(element, graphicsNode, value, n, bridgeContext);
                }
                default: {
                    throw new IllegalArgumentException("Paint argument is not an appropriate CSS value");
                }
            }
        }
        else {
            final Value item = value.item(0);
            switch (item.getPrimitiveType()) {
                case 25: {
                    return convertRGBICCColor(element, item, (ICCColor)value.item(1), n, bridgeContext);
                }
                case 20: {
                    final Paint silentConvertURIPaint = silentConvertURIPaint(element, graphicsNode, item, n, bridgeContext);
                    if (silentConvertURIPaint != null) {
                        return silentConvertURIPaint;
                    }
                    final Value item2 = value.item(1);
                    switch (item2.getPrimitiveType()) {
                        case 21: {
                            return null;
                        }
                        case 25: {
                            if (value.getLength() == 2) {
                                return convertColor(item2, n);
                            }
                            return convertRGBICCColor(element, item2, (ICCColor)value.item(2), n, bridgeContext);
                        }
                        default: {
                            throw new IllegalArgumentException("Paint argument is not an appropriate CSS value");
                        }
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Paint argument is not an appropriate CSS value");
                }
            }
        }
    }
    
    public static Paint silentConvertURIPaint(final Element element, final GraphicsNode graphicsNode, final Value value, final float n, final BridgeContext bridgeContext) {
        Paint convertURIPaint = null;
        try {
            convertURIPaint = convertURIPaint(element, graphicsNode, value, n, bridgeContext);
        }
        catch (BridgeException ex) {}
        return convertURIPaint;
    }
    
    public static Paint convertURIPaint(final Element element, final GraphicsNode graphicsNode, final Value value, final float n, final BridgeContext bridgeContext) {
        final String stringValue = value.getStringValue();
        final Element referencedElement = bridgeContext.getReferencedElement(element, stringValue);
        final Bridge bridge = bridgeContext.getBridge(referencedElement);
        if (bridge == null || !(bridge instanceof PaintBridge)) {
            throw new BridgeException(bridgeContext, element, "css.uri.badTarget", new Object[] { stringValue });
        }
        return ((PaintBridge)bridge).createPaint(bridgeContext, referencedElement, element, graphicsNode, n);
    }
    
    public static Color convertRGBICCColor(final Element element, final Value value, final ICCColor iccColor, final float n, final BridgeContext bridgeContext) {
        Color color = null;
        if (iccColor != null) {
            color = convertICCColor(element, iccColor, n, bridgeContext);
        }
        if (color == null) {
            color = convertColor(value, n);
        }
        return color;
    }
    
    public static Color convertICCColor(final Element element, final ICCColor iccColor, final float a, final BridgeContext bridgeContext) {
        final String colorProfile = iccColor.getColorProfile();
        if (colorProfile == null) {
            return null;
        }
        final SVGColorProfileElementBridge svgColorProfileElementBridge = (SVGColorProfileElementBridge)bridgeContext.getBridge("http://www.w3.org/2000/svg", "color-profile");
        if (svgColorProfileElementBridge == null) {
            return null;
        }
        final ICCColorSpaceExt iccColorSpaceExt = svgColorProfileElementBridge.createICCColorSpaceExt(bridgeContext, element, colorProfile);
        if (iccColorSpaceExt == null) {
            return null;
        }
        final int numberOfColors = iccColor.getNumberOfColors();
        final float[] array = new float[numberOfColors];
        if (numberOfColors == 0) {
            return null;
        }
        for (int i = 0; i < numberOfColors; ++i) {
            array[i] = iccColor.getColor(i);
        }
        final float[] intendedToRGB = iccColorSpaceExt.intendedToRGB(array);
        return new Color(intendedToRGB[0], intendedToRGB[1], intendedToRGB[2], a);
    }
    
    public static Color convertColor(final Value value, final float n) {
        return new Color(resolveColorComponent(value.getRed()), resolveColorComponent(value.getGreen()), resolveColorComponent(value.getBlue()), Math.round(n * 255.0f));
    }
    
    public static Stroke convertStroke(final Element element) {
        final float floatValue = CSSUtilities.getComputedStyle(element, 52).getFloatValue();
        if (floatValue == 0.0f) {
            return null;
        }
        final int convertStrokeLinecap = convertStrokeLinecap(CSSUtilities.getComputedStyle(element, 48));
        final int convertStrokeLinejoin = convertStrokeLinejoin(CSSUtilities.getComputedStyle(element, 49));
        final float convertStrokeMiterlimit = convertStrokeMiterlimit(CSSUtilities.getComputedStyle(element, 50));
        final float[] convertStrokeDasharray = convertStrokeDasharray(CSSUtilities.getComputedStyle(element, 46));
        float floatValue2 = 0.0f;
        if (convertStrokeDasharray != null) {
            floatValue2 = CSSUtilities.getComputedStyle(element, 47).getFloatValue();
            if (floatValue2 < 0.0f) {
                float n = 0.0f;
                for (int i = 0; i < convertStrokeDasharray.length; ++i) {
                    n += convertStrokeDasharray[i];
                }
                if (convertStrokeDasharray.length % 2 != 0) {
                    n *= 2.0f;
                }
                if (n == 0.0f) {
                    floatValue2 = 0.0f;
                }
                else {
                    while (floatValue2 < 0.0f) {
                        floatValue2 += n;
                    }
                }
            }
        }
        return new BasicStroke(floatValue, convertStrokeLinecap, convertStrokeLinejoin, convertStrokeMiterlimit, convertStrokeDasharray, floatValue2);
    }
    
    public static float[] convertStrokeDasharray(final Value value) {
        float[] array = null;
        if (value.getCssValueType() == 2) {
            array = new float[value.getLength()];
            float n = 0.0f;
            for (int i = 0; i < array.length; ++i) {
                array[i] = value.item(i).getFloatValue();
                n += array[i];
            }
            if (n == 0.0f) {
                array = null;
            }
        }
        return array;
    }
    
    public static float convertStrokeMiterlimit(final Value value) {
        final float floatValue = value.getFloatValue();
        return (floatValue < 1.0f) ? 1.0f : floatValue;
    }
    
    public static int convertStrokeLinecap(final Value value) {
        switch (value.getStringValue().charAt(0)) {
            case 'b': {
                return 0;
            }
            case 'r': {
                return 1;
            }
            case 's': {
                return 2;
            }
            default: {
                throw new IllegalArgumentException("Linecap argument is not an appropriate CSS value");
            }
        }
    }
    
    public static int convertStrokeLinejoin(final Value value) {
        switch (value.getStringValue().charAt(0)) {
            case 'm': {
                return 0;
            }
            case 'r': {
                return 1;
            }
            case 'b': {
                return 2;
            }
            default: {
                throw new IllegalArgumentException("Linejoin argument is not an appropriate CSS value");
            }
        }
    }
    
    public static int resolveColorComponent(final Value value) {
        switch (value.getPrimitiveType()) {
            case 2: {
                final float floatValue = value.getFloatValue();
                return Math.round(255.0f * ((floatValue > 100.0f) ? 100.0f : ((floatValue < 0.0f) ? 0.0f : floatValue)) / 100.0f);
            }
            case 1: {
                final float floatValue2 = value.getFloatValue();
                return Math.round((floatValue2 > 255.0f) ? 255.0f : ((floatValue2 < 0.0f) ? 0.0f : floatValue2));
            }
            default: {
                throw new IllegalArgumentException("Color component argument is not an appropriate CSS value");
            }
        }
    }
    
    public static float convertOpacity(final Value value) {
        final float floatValue = value.getFloatValue();
        return (floatValue < 0.0f) ? 0.0f : ((floatValue > 1.0f) ? 1.0f : floatValue);
    }
}
