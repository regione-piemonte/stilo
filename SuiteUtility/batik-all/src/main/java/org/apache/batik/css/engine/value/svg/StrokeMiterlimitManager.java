// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.FloatValue;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.AbstractValueManager;

public class StrokeMiterlimitManager extends AbstractValueManager
{
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
        return 25;
    }
    
    public String getPropertyName() {
        return "stroke-miterlimit";
    }
    
    public Value getDefaultValue() {
        return SVGValueConstants.NUMBER_4;
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
            default: {
                throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
            }
        }
    }
    
    public Value createFloatValue(final short n, final float n2) throws DOMException {
        if (n == 1) {
            return new FloatValue(n, n2);
        }
        throw this.createInvalidFloatTypeDOMException(n);
    }
}
