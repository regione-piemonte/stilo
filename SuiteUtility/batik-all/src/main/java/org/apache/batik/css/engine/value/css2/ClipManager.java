// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.InheritValue;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.RectManager;

public class ClipManager extends RectManager
{
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
        return 19;
    }
    
    public String getPropertyName() {
        return "clip";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.AUTO_VALUE;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return InheritValue.INSTANCE;
            }
            case 35: {
                if (lexicalUnit.getStringValue().equalsIgnoreCase("auto")) {
                    return ValueConstants.AUTO_VALUE;
                }
                break;
            }
        }
        return super.createValue(lexicalUnit, cssEngine);
    }
    
    public Value createStringValue(final short n, final String s, final CSSEngine cssEngine) throws DOMException {
        if (n != 21) {
            throw this.createInvalidStringTypeDOMException(n);
        }
        if (!s.equalsIgnoreCase("auto")) {
            throw this.createInvalidIdentifierDOMException(s);
        }
        return ValueConstants.AUTO_VALUE;
    }
}
