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

public class SpacingManager extends LengthManager
{
    protected String property;
    
    public SpacingManager(final String property) {
        this.property = property;
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
        return 42;
    }
    
    public String getPropertyName() {
        return this.property;
    }
    
    public Value getDefaultValue() {
        return ValueConstants.NORMAL_VALUE;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 35: {
                if (lexicalUnit.getStringValue().equalsIgnoreCase("normal")) {
                    return ValueConstants.NORMAL_VALUE;
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
        if (s.equalsIgnoreCase("normal")) {
            return ValueConstants.NORMAL_VALUE;
        }
        throw this.createInvalidIdentifierDOMException(s);
    }
    
    protected int getOrientation() {
        return 2;
    }
}
