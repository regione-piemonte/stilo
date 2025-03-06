// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;

public abstract class RectManager extends LengthManager
{
    protected int orientation;
    
    public Value createValue(LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 41: {
                if (!lexicalUnit.getFunctionName().equalsIgnoreCase("rect")) {
                    break;
                }
            }
            case 38: {
                lexicalUnit = lexicalUnit.getParameters();
                final Value rectComponent = this.createRectComponent(lexicalUnit);
                lexicalUnit = lexicalUnit.getNextLexicalUnit();
                if (lexicalUnit == null || lexicalUnit.getLexicalUnitType() != 0) {
                    throw this.createMalformedRectDOMException();
                }
                lexicalUnit = lexicalUnit.getNextLexicalUnit();
                final Value rectComponent2 = this.createRectComponent(lexicalUnit);
                lexicalUnit = lexicalUnit.getNextLexicalUnit();
                if (lexicalUnit == null || lexicalUnit.getLexicalUnitType() != 0) {
                    throw this.createMalformedRectDOMException();
                }
                lexicalUnit = lexicalUnit.getNextLexicalUnit();
                final Value rectComponent3 = this.createRectComponent(lexicalUnit);
                lexicalUnit = lexicalUnit.getNextLexicalUnit();
                if (lexicalUnit == null || lexicalUnit.getLexicalUnitType() != 0) {
                    throw this.createMalformedRectDOMException();
                }
                lexicalUnit = lexicalUnit.getNextLexicalUnit();
                return new RectValue(rectComponent, rectComponent2, rectComponent3, this.createRectComponent(lexicalUnit));
            }
        }
        throw this.createMalformedRectDOMException();
    }
    
    private Value createRectComponent(final LexicalUnit lexicalUnit) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 35: {
                if (lexicalUnit.getStringValue().equalsIgnoreCase("auto")) {
                    return ValueConstants.AUTO_VALUE;
                }
                break;
            }
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
        }
        throw this.createMalformedRectDOMException();
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        if (value.getCssValueType() != 1) {
            return value;
        }
        if (value.getPrimitiveType() != 24) {
            return value;
        }
        final RectValue rectValue = (RectValue)value;
        this.orientation = 1;
        final Value computeValue = super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, rectValue.getTop());
        final Value computeValue2 = super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, rectValue.getBottom());
        this.orientation = 0;
        final Value computeValue3 = super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, rectValue.getLeft());
        final Value computeValue4 = super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, rectValue.getRight());
        if (computeValue != rectValue.getTop() || computeValue4 != rectValue.getRight() || computeValue2 != rectValue.getBottom() || computeValue3 != rectValue.getLeft()) {
            return new RectValue(computeValue, computeValue4, computeValue2, computeValue3);
        }
        return value;
    }
    
    protected int getOrientation() {
        return this.orientation;
    }
    
    private DOMException createMalformedRectDOMException() {
        return new DOMException((short)12, Messages.formatMessage("malformed.rect", new Object[] { this.getPropertyName() }));
    }
}
