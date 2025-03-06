// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.URIValue;
import org.apache.batik.css.engine.value.AbstractValueFactory;
import org.apache.batik.css.engine.value.StringValue;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.AbstractValueManager;

public class ColorProfileManager extends AbstractValueManager
{
    public boolean isInheritedProperty() {
        return true;
    }
    
    public String getPropertyName() {
        return "color-profile";
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
    
    public Value getDefaultValue() {
        return ValueConstants.AUTO_VALUE;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 35: {
                final String lowerCase = lexicalUnit.getStringValue().toLowerCase();
                if (lowerCase.equals("auto")) {
                    return ValueConstants.AUTO_VALUE;
                }
                if (lowerCase.equals("srgb")) {
                    return SVGValueConstants.SRGB_VALUE;
                }
                return new StringValue((short)21, lowerCase);
            }
            case 24: {
                return new URIValue(lexicalUnit.getStringValue(), AbstractValueFactory.resolveURI(cssEngine.getCSSBaseURI(), lexicalUnit.getStringValue()));
            }
            default: {
                throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
            }
        }
    }
    
    public Value createStringValue(final short n, final String s, final CSSEngine cssEngine) throws DOMException {
        switch (n) {
            case 21: {
                final String lowerCase = s.toLowerCase();
                if (lowerCase.equals("auto")) {
                    return ValueConstants.AUTO_VALUE;
                }
                if (lowerCase.equals("srgb")) {
                    return SVGValueConstants.SRGB_VALUE;
                }
                return new StringValue((short)21, lowerCase);
            }
            case 20: {
                return new URIValue(s, AbstractValueFactory.resolveURI(cssEngine.getCSSBaseURI(), s));
            }
            default: {
                throw this.createInvalidStringTypeDOMException(n);
            }
        }
    }
}
