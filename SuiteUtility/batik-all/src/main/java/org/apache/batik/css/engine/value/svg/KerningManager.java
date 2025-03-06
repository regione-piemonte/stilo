// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.LengthManager;

public class KerningManager extends LengthManager
{
    public boolean isInheritedProperty() {
        return true;
    }
    
    public String getPropertyName() {
        return "kerning";
    }
    
    public boolean isAnimatableProperty() {
        return true;
    }
    
    public boolean isAdditiveProperty() {
        return true;
    }
    
    public int getPropertyType() {
        return 41;
    }
    
    public Value getDefaultValue() {
        return ValueConstants.AUTO_VALUE;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 35: {
                if (lexicalUnit.getStringValue().equalsIgnoreCase("auto")) {
                    return ValueConstants.AUTO_VALUE;
                }
                throw this.createInvalidIdentifierDOMException(lexicalUnit.getStringValue());
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
        if (s.equalsIgnoreCase("auto")) {
            return ValueConstants.AUTO_VALUE;
        }
        throw this.createInvalidIdentifierDOMException(s);
    }
    
    protected int getOrientation() {
        return 0;
    }
}
