// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.ListValue;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.LengthManager;

public class EnableBackgroundManager extends LengthManager
{
    protected int orientation;
    
    public boolean isInheritedProperty() {
        return false;
    }
    
    public boolean isAnimatableProperty() {
        return false;
    }
    
    public boolean isAdditiveProperty() {
        return false;
    }
    
    public int getPropertyType() {
        return 23;
    }
    
    public String getPropertyName() {
        return "enable-background";
    }
    
    public Value getDefaultValue() {
        return SVGValueConstants.ACCUMULATE_VALUE;
    }
    
    public Value createValue(LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            default: {
                throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
            }
            case 35: {
                final String intern = lexicalUnit.getStringValue().toLowerCase().intern();
                if (intern == "accumulate") {
                    return SVGValueConstants.ACCUMULATE_VALUE;
                }
                if (intern != "new") {
                    throw this.createInvalidIdentifierDOMException(intern);
                }
                final ListValue listValue = new ListValue(' ');
                listValue.append(SVGValueConstants.NEW_VALUE);
                lexicalUnit = lexicalUnit.getNextLexicalUnit();
                if (lexicalUnit == null) {
                    return listValue;
                }
                listValue.append(super.createValue(lexicalUnit, cssEngine));
                for (int i = 1; i < 4; ++i) {
                    lexicalUnit = lexicalUnit.getNextLexicalUnit();
                    if (lexicalUnit == null) {
                        throw this.createMalformedLexicalUnitDOMException();
                    }
                    listValue.append(super.createValue(lexicalUnit, cssEngine));
                }
                return listValue;
            }
        }
    }
    
    public Value createStringValue(final short n, final String s, final CSSEngine cssEngine) {
        if (n != 21) {
            throw this.createInvalidStringTypeDOMException(n);
        }
        if (!s.equalsIgnoreCase("accumulate")) {
            throw this.createInvalidIdentifierDOMException(s);
        }
        return SVGValueConstants.ACCUMULATE_VALUE;
    }
    
    public Value createFloatValue(final short n, final float n2) throws DOMException {
        throw this.createDOMException();
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        if (value.getCssValueType() == 2) {
            final ListValue listValue = (ListValue)value;
            if (listValue.getLength() == 5) {
                final Value item = listValue.item(1);
                this.orientation = 0;
                final Value computeValue = super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, item);
                final Value item2 = listValue.item(2);
                this.orientation = 1;
                final Value computeValue2 = super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, item2);
                final Value item3 = listValue.item(3);
                this.orientation = 0;
                final Value computeValue3 = super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, item3);
                final Value item4 = listValue.item(4);
                this.orientation = 1;
                final Value computeValue4 = super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, item4);
                if (item != computeValue || item2 != computeValue2 || item3 != computeValue3 || item4 != computeValue4) {
                    final ListValue listValue2 = new ListValue(' ');
                    listValue2.append(listValue.item(0));
                    listValue2.append(computeValue);
                    listValue2.append(computeValue2);
                    listValue2.append(computeValue3);
                    listValue2.append(computeValue4);
                    return listValue2;
                }
            }
        }
        return value;
    }
    
    protected int getOrientation() {
        return this.orientation;
    }
}
