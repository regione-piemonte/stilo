// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.URIValue;
import org.apache.batik.css.engine.value.AbstractValueFactory;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.AbstractValueManager;

public class MarkerManager extends AbstractValueManager
{
    protected String property;
    
    public MarkerManager(final String property) {
        this.property = property;
    }
    
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
        return 20;
    }
    
    public String getPropertyName() {
        return this.property;
    }
    
    public Value getDefaultValue() {
        return ValueConstants.NONE_VALUE;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 24: {
                return new URIValue(lexicalUnit.getStringValue(), AbstractValueFactory.resolveURI(cssEngine.getCSSBaseURI(), lexicalUnit.getStringValue()));
            }
            case 35: {
                if (lexicalUnit.getStringValue().equalsIgnoreCase("none")) {
                    return ValueConstants.NONE_VALUE;
                }
                break;
            }
        }
        throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
    }
    
    public Value createStringValue(final short n, final String s, final CSSEngine cssEngine) throws DOMException {
        switch (n) {
            case 21: {
                if (s.equalsIgnoreCase("none")) {
                    return ValueConstants.NONE_VALUE;
                }
                break;
            }
            case 20: {
                return new URIValue(s, AbstractValueFactory.resolveURI(cssEngine.getCSSBaseURI(), s));
            }
        }
        throw this.createInvalidStringTypeDOMException(n);
    }
}
