// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;
import org.apache.batik.css.engine.value.svg.ICCColor;
import java.awt.Color;
import org.apache.batik.gvt.filter.Mask;
import org.apache.batik.ext.awt.image.renderable.ClipRable;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;
import java.awt.AlphaComposite;
import java.util.Map;
import java.awt.RenderingHints;
import java.awt.Cursor;
import org.apache.batik.ext.awt.MultipleGradientPaint;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.css.engine.value.ListValue;
import java.awt.geom.Rectangle2D;
import org.apache.batik.css.engine.CSSStylableElement;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.dom.Element;
import java.awt.Composite;
import org.apache.batik.util.XMLConstants;
import org.apache.batik.util.CSSConstants;

public abstract class CSSUtilities implements CSSConstants, ErrorConstants, XMLConstants
{
    public static final Composite TRANSPARENT;
    
    protected CSSUtilities() {
    }
    
    public static CSSEngine getCSSEngine(final Element element) {
        return ((SVGOMDocument)element.getOwnerDocument()).getCSSEngine();
    }
    
    public static Value getComputedStyle(final Element element, final int n) {
        final CSSEngine cssEngine = getCSSEngine(element);
        if (cssEngine == null) {
            return null;
        }
        return cssEngine.getComputedStyle((CSSStylableElement)element, null, n);
    }
    
    public static int convertPointerEvents(final Element element) {
        final String stringValue = getComputedStyle(element, 40).getStringValue();
        switch (stringValue.charAt(0)) {
            case 'v': {
                if (stringValue.length() == 7) {
                    return 3;
                }
                switch (stringValue.charAt(7)) {
                    case 'p': {
                        return 0;
                    }
                    case 'f': {
                        return 1;
                    }
                    case 's': {
                        return 2;
                    }
                    default: {
                        throw new IllegalStateException("unexpected event, must be one of (p,f,s) is:" + stringValue.charAt(7));
                    }
                }
                break;
            }
            case 'p': {
                return 4;
            }
            case 'f': {
                return 5;
            }
            case 's': {
                return 6;
            }
            case 'a': {
                return 7;
            }
            case 'n': {
                return 8;
            }
            default: {
                throw new IllegalStateException("unexpected event, must be one of (v,p,f,s,a,n) is:" + stringValue.charAt(0));
            }
        }
    }
    
    public static Rectangle2D convertEnableBackground(final Element element) {
        final Value computedStyle = getComputedStyle(element, 14);
        if (computedStyle.getCssValueType() != 2) {
            return null;
        }
        final ListValue listValue = (ListValue)computedStyle;
        final int length = listValue.getLength();
        switch (length) {
            case 1: {
                return CompositeGraphicsNode.VIEWPORT;
            }
            case 5: {
                return new Rectangle2D.Float(listValue.item(1).getFloatValue(), listValue.item(2).getFloatValue(), listValue.item(3).getFloatValue(), listValue.item(4).getFloatValue());
            }
            default: {
                throw new IllegalStateException("Unexpected length:" + length);
            }
        }
    }
    
    public static boolean convertColorInterpolationFilters(final Element element) {
        return "linearrgb" == getComputedStyle(element, 7).getStringValue();
    }
    
    public static MultipleGradientPaint.ColorSpaceEnum convertColorInterpolation(final Element element) {
        return ("linearrgb" == getComputedStyle(element, 6).getStringValue()) ? MultipleGradientPaint.LINEAR_RGB : MultipleGradientPaint.SRGB;
    }
    
    public static boolean isAutoCursor(final Element element) {
        final Value computedStyle = getComputedStyle(element, 10);
        boolean b = false;
        if (computedStyle != null) {
            if (computedStyle.getCssValueType() == 1 && computedStyle.getPrimitiveType() == 21 && computedStyle.getStringValue().charAt(0) == 'a') {
                b = true;
            }
            else if (computedStyle.getCssValueType() == 2 && computedStyle.getLength() == 1) {
                final Value item = computedStyle.item(0);
                if (item != null && item.getCssValueType() == 1 && item.getPrimitiveType() == 21 && item.getStringValue().charAt(0) == 'a') {
                    b = true;
                }
            }
        }
        return b;
    }
    
    public static Cursor convertCursor(final Element element, final BridgeContext bridgeContext) {
        return bridgeContext.getCursorManager().convertCursor(element);
    }
    
    public static RenderingHints convertShapeRendering(final Element element, RenderingHints renderingHints) {
        final String stringValue = getComputedStyle(element, 42).getStringValue();
        final int length = stringValue.length();
        if (length == 4 && stringValue.charAt(0) == 'a') {
            return renderingHints;
        }
        if (length < 10) {
            return renderingHints;
        }
        if (renderingHints == null) {
            renderingHints = new RenderingHints(null);
        }
        switch (stringValue.charAt(0)) {
            case 'o': {
                renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                break;
            }
            case 'c': {
                renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
                renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                break;
            }
            case 'g': {
                renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                renderingHints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                break;
            }
        }
        return renderingHints;
    }
    
    public static RenderingHints convertTextRendering(final Element element, RenderingHints renderingHints) {
        final String stringValue = getComputedStyle(element, 55).getStringValue();
        final int length = stringValue.length();
        if (length == 4 && stringValue.charAt(0) == 'a') {
            return renderingHints;
        }
        if (length < 13) {
            return renderingHints;
        }
        if (renderingHints == null) {
            renderingHints = new RenderingHints(null);
        }
        switch (stringValue.charAt(8)) {
            case 's': {
                renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
                renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                break;
            }
            case 'l': {
                renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
                renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                break;
            }
            case 'c': {
                renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                renderingHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                renderingHints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                break;
            }
        }
        return renderingHints;
    }
    
    public static RenderingHints convertImageRendering(final Element element, RenderingHints renderingHints) {
        final String stringValue = getComputedStyle(element, 30).getStringValue();
        final int length = stringValue.length();
        if (length == 4 && stringValue.charAt(0) == 'a') {
            return renderingHints;
        }
        if (length < 13) {
            return renderingHints;
        }
        if (renderingHints == null) {
            renderingHints = new RenderingHints(null);
        }
        switch (stringValue.charAt(8)) {
            case 's': {
                renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                renderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                break;
            }
            case 'q': {
                renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                renderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                break;
            }
        }
        return renderingHints;
    }
    
    public static RenderingHints convertColorRendering(final Element element, RenderingHints renderingHints) {
        final String stringValue = getComputedStyle(element, 9).getStringValue();
        final int length = stringValue.length();
        if (length == 4 && stringValue.charAt(0) == 'a') {
            return renderingHints;
        }
        if (length < 13) {
            return renderingHints;
        }
        if (renderingHints == null) {
            renderingHints = new RenderingHints(null);
        }
        switch (stringValue.charAt(8)) {
            case 's': {
                renderingHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
                renderingHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
                break;
            }
            case 'q': {
                renderingHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                renderingHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                break;
            }
        }
        return renderingHints;
    }
    
    public static boolean convertDisplay(final Element element) {
        return !(element instanceof CSSStylableElement) || getComputedStyle(element, 12).getStringValue().charAt(0) != 'n';
    }
    
    public static boolean convertVisibility(final Element element) {
        return getComputedStyle(element, 57).getStringValue().charAt(0) == 'v';
    }
    
    public static Composite convertOpacity(final Element element) {
        final float floatValue = getComputedStyle(element, 38).getFloatValue();
        if (floatValue <= 0.0f) {
            return CSSUtilities.TRANSPARENT;
        }
        if (floatValue >= 1.0f) {
            return AlphaComposite.SrcOver;
        }
        return AlphaComposite.getInstance(3, floatValue);
    }
    
    public static boolean convertOverflow(final Element element) {
        final String stringValue = getComputedStyle(element, 39).getStringValue();
        return stringValue.charAt(0) == 'h' || stringValue.charAt(0) == 's';
    }
    
    public static float[] convertClip(final Element element) {
        final Value computedStyle = getComputedStyle(element, 2);
        final short primitiveType = computedStyle.getPrimitiveType();
        switch (primitiveType) {
            case 24: {
                return new float[] { computedStyle.getTop().getFloatValue(), computedStyle.getRight().getFloatValue(), computedStyle.getBottom().getFloatValue(), computedStyle.getLeft().getFloatValue() };
            }
            case 21: {
                return null;
            }
            default: {
                throw new IllegalStateException("Unexpected primitiveType:" + primitiveType);
            }
        }
    }
    
    public static Filter convertFilter(final Element element, final GraphicsNode graphicsNode, final BridgeContext bridgeContext) {
        final Value computedStyle = getComputedStyle(element, 18);
        final short primitiveType = computedStyle.getPrimitiveType();
        switch (primitiveType) {
            case 21: {
                return null;
            }
            case 20: {
                final String stringValue = computedStyle.getStringValue();
                final Element referencedElement = bridgeContext.getReferencedElement(element, stringValue);
                final Bridge bridge = bridgeContext.getBridge(referencedElement);
                if (bridge == null || !(bridge instanceof FilterBridge)) {
                    throw new BridgeException(bridgeContext, element, "css.uri.badTarget", new Object[] { stringValue });
                }
                return ((FilterBridge)bridge).createFilter(bridgeContext, referencedElement, element, graphicsNode);
            }
            default: {
                throw new IllegalStateException("Unexpected primitive type:" + primitiveType);
            }
        }
    }
    
    public static ClipRable convertClipPath(final Element element, final GraphicsNode graphicsNode, final BridgeContext bridgeContext) {
        final Value computedStyle = getComputedStyle(element, 3);
        final short primitiveType = computedStyle.getPrimitiveType();
        switch (primitiveType) {
            case 21: {
                return null;
            }
            case 20: {
                final String stringValue = computedStyle.getStringValue();
                final Element referencedElement = bridgeContext.getReferencedElement(element, stringValue);
                final Bridge bridge = bridgeContext.getBridge(referencedElement);
                if (bridge == null || !(bridge instanceof ClipBridge)) {
                    throw new BridgeException(bridgeContext, element, "css.uri.badTarget", new Object[] { stringValue });
                }
                return ((ClipBridge)bridge).createClip(bridgeContext, referencedElement, element, graphicsNode);
            }
            default: {
                throw new IllegalStateException("Unexpected primitive type:" + primitiveType);
            }
        }
    }
    
    public static int convertClipRule(final Element element) {
        return (getComputedStyle(element, 4).getStringValue().charAt(0) == 'n') ? 1 : 0;
    }
    
    public static Mask convertMask(final Element element, final GraphicsNode graphicsNode, final BridgeContext bridgeContext) {
        final Value computedStyle = getComputedStyle(element, 37);
        final short primitiveType = computedStyle.getPrimitiveType();
        switch (primitiveType) {
            case 21: {
                return null;
            }
            case 20: {
                final String stringValue = computedStyle.getStringValue();
                final Element referencedElement = bridgeContext.getReferencedElement(element, stringValue);
                final Bridge bridge = bridgeContext.getBridge(referencedElement);
                if (bridge == null || !(bridge instanceof MaskBridge)) {
                    throw new BridgeException(bridgeContext, element, "css.uri.badTarget", new Object[] { stringValue });
                }
                return ((MaskBridge)bridge).createMask(bridgeContext, referencedElement, element, graphicsNode);
            }
            default: {
                throw new IllegalStateException("Unexpected primitive type:" + primitiveType);
            }
        }
    }
    
    public static int convertFillRule(final Element element) {
        return (getComputedStyle(element, 17).getStringValue().charAt(0) == 'n') ? 1 : 0;
    }
    
    public static Color convertLightingColor(final Element element, final BridgeContext bridgeContext) {
        final Value computedStyle = getComputedStyle(element, 33);
        if (computedStyle.getCssValueType() == 1) {
            return PaintServer.convertColor(computedStyle, 1.0f);
        }
        return PaintServer.convertRGBICCColor(element, computedStyle.item(0), (ICCColor)computedStyle.item(1), 1.0f, bridgeContext);
    }
    
    public static Color convertFloodColor(final Element element, final BridgeContext bridgeContext) {
        final Value computedStyle = getComputedStyle(element, 19);
        final float convertOpacity = PaintServer.convertOpacity(getComputedStyle(element, 20));
        if (computedStyle.getCssValueType() == 1) {
            return PaintServer.convertColor(computedStyle, convertOpacity);
        }
        return PaintServer.convertRGBICCColor(element, computedStyle.item(0), (ICCColor)computedStyle.item(1), convertOpacity, bridgeContext);
    }
    
    public static Color convertStopColor(final Element element, float n, final BridgeContext bridgeContext) {
        final Value computedStyle = getComputedStyle(element, 43);
        n *= PaintServer.convertOpacity(getComputedStyle(element, 44));
        if (computedStyle.getCssValueType() == 1) {
            return PaintServer.convertColor(computedStyle, n);
        }
        return PaintServer.convertRGBICCColor(element, computedStyle.item(0), (ICCColor)computedStyle.item(1), n, bridgeContext);
    }
    
    public static void computeStyleAndURIs(final Element element, final Element element2, String substring) {
        final int index = substring.indexOf(35);
        if (index != -1) {
            substring = substring.substring(0, index);
        }
        if (substring.length() != 0) {
            element2.setAttributeNS("http://www.w3.org/XML/1998/namespace", "base", substring);
        }
        getCSSEngine(element2).importCascadedStyleMaps(element, getCSSEngine(element), element2);
    }
    
    protected static int rule(final CSSValue cssValue) {
        return (((CSSPrimitiveValue)cssValue).getStringValue().charAt(0) == 'n') ? 1 : 0;
    }
    
    static {
        TRANSPARENT = AlphaComposite.getInstance(3, 0.0f);
    }
}
