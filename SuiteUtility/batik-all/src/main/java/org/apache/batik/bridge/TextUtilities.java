// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.css.engine.value.Value;
import org.apache.batik.gvt.TextNode;
import java.awt.font.TextAttribute;
import java.util.StringTokenizer;
import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.apache.batik.util.CSSConstants;

public abstract class TextUtilities implements CSSConstants, ErrorConstants
{
    public static String getElementContent(final Element element) {
        final StringBuffer sb = new StringBuffer();
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            switch (node.getNodeType()) {
                case 1: {
                    sb.append(getElementContent((Element)node));
                    break;
                }
                case 3:
                case 4: {
                    sb.append(node.getNodeValue());
                    break;
                }
            }
        }
        return sb.toString();
    }
    
    public static ArrayList svgHorizontalCoordinateArrayToUserSpace(final Element element, final String s, final String str, final BridgeContext bridgeContext) {
        final org.apache.batik.parser.UnitProcessor.Context context = UnitProcessor.createContext(bridgeContext, element);
        final ArrayList<Float> list = new ArrayList<Float>();
        final StringTokenizer stringTokenizer = new StringTokenizer(str, ", ", false);
        while (stringTokenizer.hasMoreTokens()) {
            list.add(new Float(UnitProcessor.svgHorizontalCoordinateToUserSpace(stringTokenizer.nextToken(), s, context)));
        }
        return list;
    }
    
    public static ArrayList svgVerticalCoordinateArrayToUserSpace(final Element element, final String s, final String str, final BridgeContext bridgeContext) {
        final org.apache.batik.parser.UnitProcessor.Context context = UnitProcessor.createContext(bridgeContext, element);
        final ArrayList<Float> list = new ArrayList<Float>();
        final StringTokenizer stringTokenizer = new StringTokenizer(str, ", ", false);
        while (stringTokenizer.hasMoreTokens()) {
            list.add(new Float(UnitProcessor.svgVerticalCoordinateToUserSpace(stringTokenizer.nextToken(), s, context)));
        }
        return list;
    }
    
    public static ArrayList svgRotateArrayToFloats(final Element element, final String s, final String str, final BridgeContext bridgeContext) {
        final StringTokenizer stringTokenizer = new StringTokenizer(str, ", ", false);
        final ArrayList<Float> list = new ArrayList<Float>();
        while (stringTokenizer.hasMoreTokens()) {
            try {
                list.add(new Float(Math.toRadians(SVGUtilities.convertSVGNumber(stringTokenizer.nextToken()))));
                continue;
            }
            catch (NumberFormatException ex) {
                throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { s, str });
            }
            break;
        }
        return list;
    }
    
    public static Float convertFontSize(final Element element) {
        return new Float(CSSUtilities.getComputedStyle(element, 22).getFloatValue());
    }
    
    public static Float convertFontStyle(final Element element) {
        switch (CSSUtilities.getComputedStyle(element, 25).getStringValue().charAt(0)) {
            case 'n': {
                return TextAttribute.POSTURE_REGULAR;
            }
            default: {
                return TextAttribute.POSTURE_OBLIQUE;
            }
        }
    }
    
    public static Float convertFontStretch(final Element element) {
        final String stringValue = CSSUtilities.getComputedStyle(element, 24).getStringValue();
        switch (stringValue.charAt(0)) {
            case 'u': {
                if (stringValue.charAt(6) == 'c') {
                    return TextAttribute.WIDTH_CONDENSED;
                }
                return TextAttribute.WIDTH_EXTENDED;
            }
            case 'e': {
                if (stringValue.charAt(6) == 'c') {
                    return TextAttribute.WIDTH_CONDENSED;
                }
                if (stringValue.length() == 8) {
                    return TextAttribute.WIDTH_SEMI_EXTENDED;
                }
                return TextAttribute.WIDTH_EXTENDED;
            }
            case 's': {
                if (stringValue.charAt(6) == 'c') {
                    return TextAttribute.WIDTH_SEMI_CONDENSED;
                }
                return TextAttribute.WIDTH_SEMI_EXTENDED;
            }
            default: {
                return TextAttribute.WIDTH_REGULAR;
            }
        }
    }
    
    public static Float convertFontWeight(final Element element) {
        switch ((int)CSSUtilities.getComputedStyle(element, 27).getFloatValue()) {
            case 100: {
                return TextAttribute.WEIGHT_EXTRA_LIGHT;
            }
            case 200: {
                return TextAttribute.WEIGHT_LIGHT;
            }
            case 300: {
                return TextAttribute.WEIGHT_DEMILIGHT;
            }
            case 400: {
                return TextAttribute.WEIGHT_REGULAR;
            }
            case 500: {
                return TextAttribute.WEIGHT_SEMIBOLD;
            }
            default: {
                return TextAttribute.WEIGHT_BOLD;
            }
        }
    }
    
    public static TextNode.Anchor convertTextAnchor(final Element element) {
        switch (CSSUtilities.getComputedStyle(element, 53).getStringValue().charAt(0)) {
            case 's': {
                return TextNode.Anchor.START;
            }
            case 'm': {
                return TextNode.Anchor.MIDDLE;
            }
            default: {
                return TextNode.Anchor.END;
            }
        }
    }
    
    public static Object convertBaselineShift(final Element element) {
        final Value computedStyle = CSSUtilities.getComputedStyle(element, 1);
        if (computedStyle.getPrimitiveType() != 21) {
            return new Float(computedStyle.getFloatValue());
        }
        switch (computedStyle.getStringValue().charAt(2)) {
            case 'p': {
                return TextAttribute.SUPERSCRIPT_SUPER;
            }
            case 'b': {
                return TextAttribute.SUPERSCRIPT_SUB;
            }
            default: {
                return null;
            }
        }
    }
    
    public static Float convertKerning(final Element element) {
        final Value computedStyle = CSSUtilities.getComputedStyle(element, 31);
        if (computedStyle.getPrimitiveType() == 21) {
            return null;
        }
        return new Float(computedStyle.getFloatValue());
    }
    
    public static Float convertLetterSpacing(final Element element) {
        final Value computedStyle = CSSUtilities.getComputedStyle(element, 32);
        if (computedStyle.getPrimitiveType() == 21) {
            return null;
        }
        return new Float(computedStyle.getFloatValue());
    }
    
    public static Float convertWordSpacing(final Element element) {
        final Value computedStyle = CSSUtilities.getComputedStyle(element, 58);
        if (computedStyle.getPrimitiveType() == 21) {
            return null;
        }
        return new Float(computedStyle.getFloatValue());
    }
}
