// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;

public class GlyphOrientationVerticalManager extends GlyphOrientationManager
{
    public String getPropertyName() {
        return "glyph-orientation-vertical";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.AUTO_VALUE;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        if (lexicalUnit.getLexicalUnitType() != 35) {
            return super.createValue(lexicalUnit, cssEngine);
        }
        if (lexicalUnit.getStringValue().equalsIgnoreCase("auto")) {
            return ValueConstants.AUTO_VALUE;
        }
        throw this.createInvalidIdentifierDOMException(lexicalUnit.getStringValue());
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
}
