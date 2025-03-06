// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.ListValue;
import org.apache.batik.css.engine.value.URIValue;
import org.apache.batik.css.engine.value.AbstractValueFactory;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.Value;

public class SVGPaintManager extends SVGColorManager
{
    public SVGPaintManager(final String s) {
        super(s);
    }
    
    public SVGPaintManager(final String s, final Value value) {
        super(s, value);
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
    
    public int getPropertyType() {
        return 7;
    }
    
    public Value createValue(LexicalUnit nextLexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (nextLexicalUnit.getLexicalUnitType()) {
            case 35: {
                if (nextLexicalUnit.getStringValue().equalsIgnoreCase("none")) {
                    return ValueConstants.NONE_VALUE;
                }
                break;
            }
            case 24: {
                final String stringValue = nextLexicalUnit.getStringValue();
                final String resolveURI = AbstractValueFactory.resolveURI(cssEngine.getCSSBaseURI(), stringValue);
                nextLexicalUnit = nextLexicalUnit.getNextLexicalUnit();
                if (nextLexicalUnit == null) {
                    return new URIValue(stringValue, resolveURI);
                }
                final ListValue listValue = new ListValue(' ');
                listValue.append(new URIValue(stringValue, resolveURI));
                if (nextLexicalUnit.getLexicalUnitType() == 35 && nextLexicalUnit.getStringValue().equalsIgnoreCase("none")) {
                    listValue.append(ValueConstants.NONE_VALUE);
                    return listValue;
                }
                final Value value = super.createValue(nextLexicalUnit, cssEngine);
                if (value.getCssValueType() == 3) {
                    final ListValue listValue2 = (ListValue)value;
                    for (int i = 0; i < listValue2.getLength(); ++i) {
                        listValue.append(listValue2.item(i));
                    }
                }
                else {
                    listValue.append(value);
                }
                return listValue;
            }
        }
        return super.createValue(nextLexicalUnit, cssEngine);
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        if (value == ValueConstants.NONE_VALUE) {
            return value;
        }
        if (value.getCssValueType() == 2) {
            final ListValue listValue = (ListValue)value;
            if (listValue.item(0).getPrimitiveType() == 20) {
                final Value item = listValue.item(1);
                if (item == ValueConstants.NONE_VALUE) {
                    return value;
                }
                final Value computeValue = super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, item);
                if (computeValue != item) {
                    final ListValue listValue2 = new ListValue(' ');
                    listValue2.append(listValue.item(0));
                    listValue2.append(computeValue);
                    if (listValue.getLength() == 3) {
                        listValue2.append(listValue.item(1));
                    }
                    return listValue2;
                }
                return value;
            }
        }
        return super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, value);
    }
}
