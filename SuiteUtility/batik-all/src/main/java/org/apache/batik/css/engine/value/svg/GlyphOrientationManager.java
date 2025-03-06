// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.FloatValue;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.AbstractValueManager;

public abstract class GlyphOrientationManager extends AbstractValueManager
{
    public boolean isInheritedProperty() {
        return true;
    }
    
    public boolean isAnimatableProperty() {
        return false;
    }
    
    public boolean isAdditiveProperty() {
        return false;
    }
    
    public int getPropertyType() {
        return 5;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 28: {
                return new FloatValue((short)11, lexicalUnit.getFloatValue());
            }
            case 29: {
                return new FloatValue((short)13, lexicalUnit.getFloatValue());
            }
            case 30: {
                return new FloatValue((short)12, lexicalUnit.getFloatValue());
            }
            case 13: {
                return new FloatValue((short)11, (float)lexicalUnit.getIntegerValue());
            }
            case 14: {
                return new FloatValue((short)11, lexicalUnit.getFloatValue());
            }
            default: {
                throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
            }
        }
    }
    
    public Value createFloatValue(final short n, final float n2) throws DOMException {
        switch (n) {
            case 11:
            case 12:
            case 13: {
                return new FloatValue(n, n2);
            }
            default: {
                throw this.createInvalidFloatValueDOMException(n2);
            }
        }
    }
}
