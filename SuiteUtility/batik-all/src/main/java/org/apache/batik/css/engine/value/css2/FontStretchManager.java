// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import org.w3c.dom.Element;
import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.CSSStylableElement;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class FontStretchManager extends IdentifierManager
{
    protected static final StringMap values;
    
    public boolean isInheritedProperty() {
        return true;
    }
    
    public boolean isAnimatableProperty() {
        return true;
    }
    
    public boolean isAdditiveProperty() {
        return false;
    }
    
    public int getPropertyType() {
        return 15;
    }
    
    public String getPropertyName() {
        return "font-stretch";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.NORMAL_VALUE;
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        if (value == ValueConstants.NARROWER_VALUE) {
            styleMap.putParentRelative(n, true);
            final CSSStylableElement parentCSSStylableElement = CSSEngine.getParentCSSStylableElement(cssStylableElement);
            if (parentCSSStylableElement == null) {
                return ValueConstants.SEMI_CONDENSED_VALUE;
            }
            final Value computedStyle = cssEngine.getComputedStyle(parentCSSStylableElement, s, n);
            if (computedStyle == ValueConstants.NORMAL_VALUE) {
                return ValueConstants.SEMI_CONDENSED_VALUE;
            }
            if (computedStyle == ValueConstants.CONDENSED_VALUE) {
                return ValueConstants.EXTRA_CONDENSED_VALUE;
            }
            if (computedStyle == ValueConstants.EXPANDED_VALUE) {
                return ValueConstants.SEMI_EXPANDED_VALUE;
            }
            if (computedStyle == ValueConstants.SEMI_EXPANDED_VALUE) {
                return ValueConstants.NORMAL_VALUE;
            }
            if (computedStyle == ValueConstants.SEMI_CONDENSED_VALUE) {
                return ValueConstants.CONDENSED_VALUE;
            }
            if (computedStyle == ValueConstants.EXTRA_CONDENSED_VALUE) {
                return ValueConstants.ULTRA_CONDENSED_VALUE;
            }
            if (computedStyle == ValueConstants.EXTRA_EXPANDED_VALUE) {
                return ValueConstants.EXPANDED_VALUE;
            }
            if (computedStyle == ValueConstants.ULTRA_CONDENSED_VALUE) {
                return ValueConstants.ULTRA_CONDENSED_VALUE;
            }
            return ValueConstants.EXTRA_EXPANDED_VALUE;
        }
        else {
            if (value != ValueConstants.WIDER_VALUE) {
                return value;
            }
            styleMap.putParentRelative(n, true);
            final CSSStylableElement parentCSSStylableElement2 = CSSEngine.getParentCSSStylableElement(cssStylableElement);
            if (parentCSSStylableElement2 == null) {
                return ValueConstants.SEMI_CONDENSED_VALUE;
            }
            final Value computedStyle2 = cssEngine.getComputedStyle(parentCSSStylableElement2, s, n);
            if (computedStyle2 == ValueConstants.NORMAL_VALUE) {
                return ValueConstants.SEMI_EXPANDED_VALUE;
            }
            if (computedStyle2 == ValueConstants.CONDENSED_VALUE) {
                return ValueConstants.SEMI_CONDENSED_VALUE;
            }
            if (computedStyle2 == ValueConstants.EXPANDED_VALUE) {
                return ValueConstants.EXTRA_EXPANDED_VALUE;
            }
            if (computedStyle2 == ValueConstants.SEMI_EXPANDED_VALUE) {
                return ValueConstants.EXPANDED_VALUE;
            }
            if (computedStyle2 == ValueConstants.SEMI_CONDENSED_VALUE) {
                return ValueConstants.NORMAL_VALUE;
            }
            if (computedStyle2 == ValueConstants.EXTRA_CONDENSED_VALUE) {
                return ValueConstants.CONDENSED_VALUE;
            }
            if (computedStyle2 == ValueConstants.EXTRA_EXPANDED_VALUE) {
                return ValueConstants.ULTRA_EXPANDED_VALUE;
            }
            if (computedStyle2 == ValueConstants.ULTRA_CONDENSED_VALUE) {
                return ValueConstants.EXTRA_CONDENSED_VALUE;
            }
            return ValueConstants.ULTRA_EXPANDED_VALUE;
        }
    }
    
    public StringMap getIdentifiers() {
        return FontStretchManager.values;
    }
    
    static {
        (values = new StringMap()).put("all", ValueConstants.ALL_VALUE);
        FontStretchManager.values.put("condensed", ValueConstants.CONDENSED_VALUE);
        FontStretchManager.values.put("expanded", ValueConstants.EXPANDED_VALUE);
        FontStretchManager.values.put("extra-condensed", ValueConstants.EXTRA_CONDENSED_VALUE);
        FontStretchManager.values.put("extra-expanded", ValueConstants.EXTRA_EXPANDED_VALUE);
        FontStretchManager.values.put("narrower", ValueConstants.NARROWER_VALUE);
        FontStretchManager.values.put("normal", ValueConstants.NORMAL_VALUE);
        FontStretchManager.values.put("semi-condensed", ValueConstants.SEMI_CONDENSED_VALUE);
        FontStretchManager.values.put("semi-expanded", ValueConstants.SEMI_EXPANDED_VALUE);
        FontStretchManager.values.put("ultra-condensed", ValueConstants.ULTRA_CONDENSED_VALUE);
        FontStretchManager.values.put("ultra-expanded", ValueConstants.ULTRA_EXPANDED_VALUE);
        FontStretchManager.values.put("wider", ValueConstants.WIDER_VALUE);
    }
}
