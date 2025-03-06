// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.dom.DOMException;

public abstract class AbstractValueManager extends AbstractValueFactory implements ValueManager
{
    public Value createFloatValue(final short n, final float n2) throws DOMException {
        throw this.createDOMException();
    }
    
    public Value createStringValue(final short n, final String s, final CSSEngine cssEngine) throws DOMException {
        throw this.createDOMException();
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        if (value.getCssValueType() == 1 && value.getPrimitiveType() == 20) {
            return new URIValue(value.getStringValue(), value.getStringValue());
        }
        return value;
    }
}
