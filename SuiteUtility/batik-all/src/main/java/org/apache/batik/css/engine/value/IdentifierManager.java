// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;

public abstract class IdentifierManager extends AbstractValueManager
{
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 35: {
                final Object value = this.getIdentifiers().get(lexicalUnit.getStringValue().toLowerCase().intern());
                if (value == null) {
                    throw this.createInvalidIdentifierDOMException(lexicalUnit.getStringValue());
                }
                return (Value)value;
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
        final Object value = this.getIdentifiers().get(s.toLowerCase().intern());
        if (value == null) {
            throw this.createInvalidIdentifierDOMException(s);
        }
        return (Value)value;
    }
    
    public abstract StringMap getIdentifiers();
}
