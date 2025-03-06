// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.ListValue;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.AbstractValueManager;

public class TextDecorationManager extends AbstractValueManager
{
    protected static final StringMap values;
    
    public boolean isInheritedProperty() {
        return false;
    }
    
    public boolean isAnimatableProperty() {
        return true;
    }
    
    public boolean isAdditiveProperty() {
        return false;
    }
    
    public int getPropertyType() {
        return 18;
    }
    
    public String getPropertyName() {
        return "text-decoration";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.NONE_VALUE;
    }
    
    public Value createValue(LexicalUnit nextLexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (nextLexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 35: {
                if (nextLexicalUnit.getStringValue().equalsIgnoreCase("none")) {
                    return ValueConstants.NONE_VALUE;
                }
                final ListValue listValue = new ListValue(' ');
                while (nextLexicalUnit.getLexicalUnitType() == 35) {
                    final Object value = TextDecorationManager.values.get(nextLexicalUnit.getStringValue().toLowerCase().intern());
                    if (value == null) {
                        throw this.createInvalidIdentifierDOMException(nextLexicalUnit.getStringValue());
                    }
                    listValue.append((Value)value);
                    nextLexicalUnit = nextLexicalUnit.getNextLexicalUnit();
                    if (nextLexicalUnit == null) {
                        return listValue;
                    }
                }
                throw this.createInvalidLexicalUnitDOMException(nextLexicalUnit.getLexicalUnitType());
            }
            default: {
                throw this.createInvalidLexicalUnitDOMException(nextLexicalUnit.getLexicalUnitType());
            }
        }
    }
    
    public Value createStringValue(final short n, final String s, final CSSEngine cssEngine) throws DOMException {
        if (n != 21) {
            throw this.createInvalidStringTypeDOMException(n);
        }
        if (!s.equalsIgnoreCase("none")) {
            throw this.createInvalidIdentifierDOMException(s);
        }
        return ValueConstants.NONE_VALUE;
    }
    
    static {
        (values = new StringMap()).put("blink", ValueConstants.BLINK_VALUE);
        TextDecorationManager.values.put("line-through", ValueConstants.LINE_THROUGH_VALUE);
        TextDecorationManager.values.put("overline", ValueConstants.OVERLINE_VALUE);
        TextDecorationManager.values.put("underline", ValueConstants.UNDERLINE_VALUE);
    }
}
