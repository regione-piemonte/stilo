// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.ListValue;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.LengthManager;

public class StrokeDasharrayManager extends LengthManager
{
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
        return 34;
    }
    
    public String getPropertyName() {
        return "stroke-dasharray";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.NONE_VALUE;
    }
    
    public Value createValue(LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 35: {
                if (lexicalUnit.getStringValue().equalsIgnoreCase("none")) {
                    return ValueConstants.NONE_VALUE;
                }
                throw this.createInvalidIdentifierDOMException(lexicalUnit.getStringValue());
            }
            default: {
                final ListValue listValue = new ListValue(' ');
                do {
                    listValue.append(super.createValue(lexicalUnit, cssEngine));
                    lexicalUnit = lexicalUnit.getNextLexicalUnit();
                    if (lexicalUnit != null && lexicalUnit.getLexicalUnitType() == 0) {
                        lexicalUnit = lexicalUnit.getNextLexicalUnit();
                    }
                } while (lexicalUnit != null);
                return listValue;
            }
        }
    }
    
    public Value createStringValue(final short n, final String s, final CSSEngine cssEngine) throws DOMException {
        if (n != 21) {
            throw this.createInvalidStringTypeDOMException(n);
        }
        if (s.equalsIgnoreCase("none")) {
            return ValueConstants.NONE_VALUE;
        }
        throw this.createInvalidIdentifierDOMException(s);
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        switch (value.getCssValueType()) {
            case 1: {
                return value;
            }
            default: {
                final ListValue listValue = (ListValue)value;
                final ListValue listValue2 = new ListValue(' ');
                for (int i = 0; i < listValue.getLength(); ++i) {
                    listValue2.append(super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, listValue.item(i)));
                }
                return listValue2;
            }
        }
    }
    
    protected int getOrientation() {
        return 2;
    }
}
