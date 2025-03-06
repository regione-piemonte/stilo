// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import org.w3c.dom.Element;
import org.apache.batik.css.engine.value.FloatValue;
import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.LengthManager;

public class FontSizeManager extends LengthManager
{
    protected static final StringMap values;
    
    public StringMap getIdentifiers() {
        return FontSizeManager.values;
    }
    
    public boolean isInheritedProperty() {
        return true;
    }
    
    public boolean isAnimatableProperty() {
        return true;
    }
    
    public boolean isAdditiveProperty() {
        return true;
    }
    
    public String getPropertyName() {
        return "font-size";
    }
    
    public int getPropertyType() {
        return 39;
    }
    
    public Value getDefaultValue() {
        return ValueConstants.MEDIUM_VALUE;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 35: {
                final String intern = lexicalUnit.getStringValue().toLowerCase().intern();
                final Object value = FontSizeManager.values.get(intern);
                if (value == null) {
                    throw this.createInvalidIdentifierDOMException(intern);
                }
                return (Value)value;
            }
            default: {
                return super.createValue(lexicalUnit, cssEngine);
            }
        }
    }
    
    public Value createStringValue(final short n, final String s, final CSSEngine cssEngine) throws DOMException {
        if (n != 21) {
            throw this.createInvalidStringTypeDOMException(n);
        }
        final Object value = FontSizeManager.values.get(s.toLowerCase().intern());
        if (value == null) {
            throw this.createInvalidIdentifierDOMException(s);
        }
        return (Value)value;
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        float floatValue = 1.0f;
        boolean b = false;
        switch (value.getPrimitiveType()) {
            case 1:
            case 5: {
                return value;
            }
            case 7: {
                return new FloatValue((short)1, value.getFloatValue() / cssEngine.getCSSContext().getPixelUnitToMillimeter());
            }
            case 6: {
                return new FloatValue((short)1, value.getFloatValue() * 10.0f / cssEngine.getCSSContext().getPixelUnitToMillimeter());
            }
            case 8: {
                return new FloatValue((short)1, value.getFloatValue() * 25.4f / cssEngine.getCSSContext().getPixelUnitToMillimeter());
            }
            case 9: {
                return new FloatValue((short)1, value.getFloatValue() * 25.4f / (72.0f * cssEngine.getCSSContext().getPixelUnitToMillimeter()));
            }
            case 10: {
                return new FloatValue((short)1, value.getFloatValue() * 25.4f / (6.0f * cssEngine.getCSSContext().getPixelUnitToMillimeter()));
            }
            case 3: {
                b = true;
                floatValue = value.getFloatValue();
                break;
            }
            case 4: {
                b = true;
                floatValue = value.getFloatValue() * 0.5f;
                break;
            }
            case 2: {
                b = true;
                floatValue = value.getFloatValue() * 0.01f;
                break;
            }
        }
        if (value == ValueConstants.LARGER_VALUE) {
            b = true;
            floatValue = 1.2f;
        }
        else if (value == ValueConstants.SMALLER_VALUE) {
            b = true;
            floatValue = 0.8333333f;
        }
        if (b) {
            styleMap.putParentRelative(n, true);
            final CSSStylableElement parentCSSStylableElement = CSSEngine.getParentCSSStylableElement(cssStylableElement);
            float n2;
            if (parentCSSStylableElement == null) {
                n2 = cssEngine.getCSSContext().getMediumFontSize();
            }
            else {
                n2 = cssEngine.getComputedStyle(parentCSSStylableElement, null, n).getFloatValue();
            }
            return new FloatValue((short)1, n2 * floatValue);
        }
        float mediumFontSize = cssEngine.getCSSContext().getMediumFontSize();
        final String stringValue = value.getStringValue();
        Label_0655: {
            switch (stringValue.charAt(0)) {
                case 'm': {
                    break;
                }
                case 's': {
                    mediumFontSize /= (float)1.2;
                    break;
                }
                case 'l': {
                    mediumFontSize *= (float)1.2;
                    break;
                }
                default: {
                    switch (stringValue.charAt(1)) {
                        case 'x': {
                            switch (stringValue.charAt(3)) {
                                case 's': {
                                    mediumFontSize = (float)(mediumFontSize / 1.2 / 1.2 / 1.2);
                                    break Label_0655;
                                }
                                default: {
                                    mediumFontSize = (float)(mediumFontSize * 1.2 * 1.2 * 1.2);
                                    break Label_0655;
                                }
                            }
                            break;
                        }
                        default: {
                            switch (stringValue.charAt(2)) {
                                case 's': {
                                    mediumFontSize = (float)(mediumFontSize / 1.2 / 1.2);
                                    break Label_0655;
                                }
                                default: {
                                    mediumFontSize = (float)(mediumFontSize * 1.2 * 1.2);
                                    break Label_0655;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return new FloatValue((short)1, mediumFontSize);
    }
    
    protected int getOrientation() {
        return 1;
    }
    
    static {
        (values = new StringMap()).put("all", ValueConstants.ALL_VALUE);
        FontSizeManager.values.put("large", ValueConstants.LARGE_VALUE);
        FontSizeManager.values.put("larger", ValueConstants.LARGER_VALUE);
        FontSizeManager.values.put("medium", ValueConstants.MEDIUM_VALUE);
        FontSizeManager.values.put("small", ValueConstants.SMALL_VALUE);
        FontSizeManager.values.put("smaller", ValueConstants.SMALLER_VALUE);
        FontSizeManager.values.put("x-large", ValueConstants.X_LARGE_VALUE);
        FontSizeManager.values.put("x-small", ValueConstants.X_SMALL_VALUE);
        FontSizeManager.values.put("xx-large", ValueConstants.XX_LARGE_VALUE);
        FontSizeManager.values.put("xx-small", ValueConstants.XX_SMALL_VALUE);
    }
}
