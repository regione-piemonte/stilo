// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import org.apache.batik.css.engine.CSSContext;
import org.w3c.dom.Element;
import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class FontWeightManager extends IdentifierManager
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
        return 28;
    }
    
    public String getPropertyName() {
        return "font-weight";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.NORMAL_VALUE;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        if (lexicalUnit.getLexicalUnitType() != 13) {
            return super.createValue(lexicalUnit, cssEngine);
        }
        final int integerValue = lexicalUnit.getIntegerValue();
        switch (integerValue) {
            case 100: {
                return ValueConstants.NUMBER_100;
            }
            case 200: {
                return ValueConstants.NUMBER_200;
            }
            case 300: {
                return ValueConstants.NUMBER_300;
            }
            case 400: {
                return ValueConstants.NUMBER_400;
            }
            case 500: {
                return ValueConstants.NUMBER_500;
            }
            case 600: {
                return ValueConstants.NUMBER_600;
            }
            case 700: {
                return ValueConstants.NUMBER_700;
            }
            case 800: {
                return ValueConstants.NUMBER_800;
            }
            case 900: {
                return ValueConstants.NUMBER_900;
            }
            default: {
                throw this.createInvalidFloatValueDOMException((float)integerValue);
            }
        }
    }
    
    public Value createFloatValue(final short n, final float n2) throws DOMException {
        if (n == 1) {
            final int n3 = (int)n2;
            if (n2 == n3) {
                switch (n3) {
                    case 100: {
                        return ValueConstants.NUMBER_100;
                    }
                    case 200: {
                        return ValueConstants.NUMBER_200;
                    }
                    case 300: {
                        return ValueConstants.NUMBER_300;
                    }
                    case 400: {
                        return ValueConstants.NUMBER_400;
                    }
                    case 500: {
                        return ValueConstants.NUMBER_500;
                    }
                    case 600: {
                        return ValueConstants.NUMBER_600;
                    }
                    case 700: {
                        return ValueConstants.NUMBER_700;
                    }
                    case 800: {
                        return ValueConstants.NUMBER_800;
                    }
                    case 900: {
                        return ValueConstants.NUMBER_900;
                    }
                }
            }
        }
        throw this.createInvalidFloatValueDOMException(n2);
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        if (value == ValueConstants.BOLDER_VALUE) {
            styleMap.putParentRelative(n, true);
            final CSSContext cssContext = cssEngine.getCSSContext();
            final CSSStylableElement parentCSSStylableElement = CSSEngine.getParentCSSStylableElement(cssStylableElement);
            float floatValue;
            if (parentCSSStylableElement == null) {
                floatValue = 400.0f;
            }
            else {
                floatValue = cssEngine.getComputedStyle(parentCSSStylableElement, s, n).getFloatValue();
            }
            return this.createFontWeight(cssContext.getBolderFontWeight(floatValue));
        }
        if (value == ValueConstants.LIGHTER_VALUE) {
            styleMap.putParentRelative(n, true);
            final CSSContext cssContext2 = cssEngine.getCSSContext();
            final CSSStylableElement parentCSSStylableElement2 = CSSEngine.getParentCSSStylableElement(cssStylableElement);
            float floatValue2;
            if (parentCSSStylableElement2 == null) {
                floatValue2 = 400.0f;
            }
            else {
                floatValue2 = cssEngine.getComputedStyle(parentCSSStylableElement2, s, n).getFloatValue();
            }
            return this.createFontWeight(cssContext2.getLighterFontWeight(floatValue2));
        }
        if (value == ValueConstants.NORMAL_VALUE) {
            return ValueConstants.NUMBER_400;
        }
        if (value == ValueConstants.BOLD_VALUE) {
            return ValueConstants.NUMBER_700;
        }
        return value;
    }
    
    protected Value createFontWeight(final float n) {
        switch ((int)n) {
            case 100: {
                return ValueConstants.NUMBER_100;
            }
            case 200: {
                return ValueConstants.NUMBER_200;
            }
            case 300: {
                return ValueConstants.NUMBER_300;
            }
            case 400: {
                return ValueConstants.NUMBER_400;
            }
            case 500: {
                return ValueConstants.NUMBER_500;
            }
            case 600: {
                return ValueConstants.NUMBER_600;
            }
            case 700: {
                return ValueConstants.NUMBER_700;
            }
            case 800: {
                return ValueConstants.NUMBER_800;
            }
            default: {
                return ValueConstants.NUMBER_900;
            }
        }
    }
    
    public StringMap getIdentifiers() {
        return FontWeightManager.values;
    }
    
    static {
        (values = new StringMap()).put("all", ValueConstants.ALL_VALUE);
        FontWeightManager.values.put("bold", ValueConstants.BOLD_VALUE);
        FontWeightManager.values.put("bolder", ValueConstants.BOLDER_VALUE);
        FontWeightManager.values.put("lighter", ValueConstants.LIGHTER_VALUE);
        FontWeightManager.values.put("normal", ValueConstants.NORMAL_VALUE);
    }
}
