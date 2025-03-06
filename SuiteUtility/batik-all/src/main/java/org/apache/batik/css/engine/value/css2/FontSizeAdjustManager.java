// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.FloatValue;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.AbstractValueManager;

public class FontSizeAdjustManager extends AbstractValueManager
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
        return 44;
    }
    
    public String getPropertyName() {
        return "font-size-adjust";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.NONE_VALUE;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 13: {
                return new FloatValue((short)1, (float)lexicalUnit.getIntegerValue());
            }
            case 14: {
                return new FloatValue((short)1, lexicalUnit.getFloatValue());
            }
            case 35: {
                if (lexicalUnit.getStringValue().equalsIgnoreCase("none")) {
                    return ValueConstants.NONE_VALUE;
                }
                throw this.createInvalidIdentifierDOMException(lexicalUnit.getStringValue());
            }
            default: {
                throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
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
    
    public Value createFloatValue(final short n, final float n2) throws DOMException {
        if (n == 1) {
            return new FloatValue(n, n2);
        }
        throw this.createInvalidFloatTypeDOMException(n);
    }
}
