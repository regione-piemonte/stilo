// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

import org.apache.batik.css.engine.CSSContext;
import org.w3c.dom.Element;
import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;

public abstract class LengthManager extends AbstractValueManager
{
    static final double SQRT2;
    protected static final int HORIZONTAL_ORIENTATION = 0;
    protected static final int VERTICAL_ORIENTATION = 1;
    protected static final int BOTH_ORIENTATION = 2;
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 15: {
                return new FloatValue((short)3, lexicalUnit.getFloatValue());
            }
            case 16: {
                return new FloatValue((short)4, lexicalUnit.getFloatValue());
            }
            case 17: {
                return new FloatValue((short)5, lexicalUnit.getFloatValue());
            }
            case 19: {
                return new FloatValue((short)6, lexicalUnit.getFloatValue());
            }
            case 20: {
                return new FloatValue((short)7, lexicalUnit.getFloatValue());
            }
            case 18: {
                return new FloatValue((short)8, lexicalUnit.getFloatValue());
            }
            case 21: {
                return new FloatValue((short)9, lexicalUnit.getFloatValue());
            }
            case 22: {
                return new FloatValue((short)10, lexicalUnit.getFloatValue());
            }
            case 13: {
                return new FloatValue((short)1, (float)lexicalUnit.getIntegerValue());
            }
            case 14: {
                return new FloatValue((short)1, lexicalUnit.getFloatValue());
            }
            case 23: {
                return new FloatValue((short)2, lexicalUnit.getFloatValue());
            }
            default: {
                throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
            }
        }
    }
    
    public Value createFloatValue(final short n, final float n2) throws DOMException {
        switch (n) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10: {
                return new FloatValue(n, n2);
            }
            default: {
                throw this.createInvalidFloatTypeDOMException(n);
            }
        }
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        if (value.getCssValueType() != 1) {
            return value;
        }
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
                styleMap.putFontSizeRelative(n, true);
                return new FloatValue((short)1, value.getFloatValue() * cssEngine.getComputedStyle(cssStylableElement, s, cssEngine.getFontSizeIndex()).getFloatValue());
            }
            case 4: {
                styleMap.putFontSizeRelative(n, true);
                return new FloatValue((short)1, value.getFloatValue() * cssEngine.getComputedStyle(cssStylableElement, s, cssEngine.getFontSizeIndex()).getFloatValue() * 0.5f);
            }
            case 2: {
                final CSSContext cssContext = cssEngine.getCSSContext();
                float n2 = 0.0f;
                switch (this.getOrientation()) {
                    case 0: {
                        styleMap.putBlockWidthRelative(n, true);
                        n2 = value.getFloatValue() * cssContext.getBlockWidth(cssStylableElement) / 100.0f;
                        break;
                    }
                    case 1: {
                        styleMap.putBlockHeightRelative(n, true);
                        n2 = value.getFloatValue() * cssContext.getBlockHeight(cssStylableElement) / 100.0f;
                        break;
                    }
                    default: {
                        styleMap.putBlockWidthRelative(n, true);
                        styleMap.putBlockHeightRelative(n, true);
                        final double n3 = cssContext.getBlockWidth(cssStylableElement);
                        final double n4 = cssContext.getBlockHeight(cssStylableElement);
                        n2 = (float)(value.getFloatValue() * (Math.sqrt(n3 * n3 + n4 * n4) / LengthManager.SQRT2) / 100.0);
                        break;
                    }
                }
                return new FloatValue((short)1, n2);
            }
            default: {
                return value;
            }
        }
    }
    
    protected abstract int getOrientation();
    
    static {
        SQRT2 = Math.sqrt(2.0);
    }
}
