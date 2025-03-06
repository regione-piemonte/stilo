// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.css.engine.value.ValueManager;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.StyleMap;
import java.util.LinkedList;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.FontFaceRule;
import org.apache.batik.css.engine.CSSEngine;
import java.util.List;
import org.apache.batik.gvt.font.GVTFontFamily;
import org.apache.batik.util.SVGConstants;

public class CSSFontFace extends FontFace implements SVGConstants
{
    GVTFontFamily fontFamily;
    
    public CSSFontFace(final List list, final String s, final float n, final String s2, final String s3, final String s4, final String s5, final float n2, final String s6, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10) {
        super(list, s, n, s2, s3, s4, s5, n2, s6, n3, n4, n5, n6, n7, n8, n9, n10);
        this.fontFamily = null;
    }
    
    protected CSSFontFace(final String s) {
        super(s);
        this.fontFamily = null;
    }
    
    public static CSSFontFace createCSSFontFace(final CSSEngine cssEngine, final FontFaceRule fontFaceRule) {
        final StyleMap styleMap = fontFaceRule.getStyleMap();
        final CSSFontFace cssFontFace = new CSSFontFace(getStringProp(styleMap, cssEngine, 21));
        final Value value = styleMap.getValue(27);
        if (value != null) {
            cssFontFace.fontWeight = value.getCssText();
        }
        final Value value2 = styleMap.getValue(25);
        if (value2 != null) {
            cssFontFace.fontStyle = value2.getCssText();
        }
        final Value value3 = styleMap.getValue(26);
        if (value3 != null) {
            cssFontFace.fontVariant = value3.getCssText();
        }
        final Value value4 = styleMap.getValue(24);
        if (value4 != null) {
            cssFontFace.fontStretch = value4.getCssText();
        }
        final Value value5 = styleMap.getValue(41);
        final ParsedURL url = fontFaceRule.getURL();
        if (value5 != null && value5 != ValueConstants.NONE_VALUE) {
            if (value5.getCssValueType() == 1) {
                (cssFontFace.srcs = new LinkedList()).add(getSrcValue(value5, url));
            }
            else if (value5.getCssValueType() == 2) {
                cssFontFace.srcs = new LinkedList();
                for (int i = 0; i < value5.getLength(); ++i) {
                    cssFontFace.srcs.add(getSrcValue(value5.item(i), url));
                }
            }
        }
        return cssFontFace;
    }
    
    public static Object getSrcValue(final Value value, final ParsedURL parsedURL) {
        if (value.getCssValueType() != 1) {
            return null;
        }
        if (value.getPrimitiveType() == 20) {
            if (parsedURL != null) {
                return new ParsedURL(parsedURL, value.getStringValue());
            }
            return new ParsedURL(value.getStringValue());
        }
        else {
            if (value.getPrimitiveType() == 19) {
                return value.getStringValue();
            }
            return null;
        }
    }
    
    public static String getStringProp(final StyleMap styleMap, final CSSEngine cssEngine, final int n) {
        Value value = styleMap.getValue(n);
        final ValueManager[] valueManagers = cssEngine.getValueManagers();
        if (value == null) {
            value = valueManagers[n].getDefaultValue();
        }
        while (value.getCssValueType() == 2) {
            value = value.item(0);
        }
        return value.getStringValue();
    }
    
    public static float getFloatProp(final StyleMap styleMap, final CSSEngine cssEngine, final int n) {
        Value value = styleMap.getValue(n);
        final ValueManager[] valueManagers = cssEngine.getValueManagers();
        if (value == null) {
            value = valueManagers[n].getDefaultValue();
        }
        while (value.getCssValueType() == 2) {
            value = value.item(0);
        }
        return value.getFloatValue();
    }
    
    public GVTFontFamily getFontFamily(final BridgeContext bridgeContext) {
        if (this.fontFamily != null) {
            return this.fontFamily;
        }
        return this.fontFamily = super.getFontFamily(bridgeContext);
    }
}
