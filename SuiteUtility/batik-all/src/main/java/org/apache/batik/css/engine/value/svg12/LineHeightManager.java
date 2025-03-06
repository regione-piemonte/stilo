// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg12;

import org.apache.batik.css.engine.value.FloatValue;
import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.LengthManager;

public class LineHeightManager extends LengthManager
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
        return 43;
    }
    
    public String getPropertyName() {
        return "line-height";
    }
    
    public Value getDefaultValue() {
        return SVG12ValueConstants.NORMAL_VALUE;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 35: {
                if ("normal".equals(lexicalUnit.getStringValue().toLowerCase())) {
                    return SVG12ValueConstants.NORMAL_VALUE;
                }
                throw this.createInvalidIdentifierDOMException(lexicalUnit.getStringValue());
            }
            default: {
                return super.createValue(lexicalUnit, cssEngine);
            }
        }
    }
    
    protected int getOrientation() {
        return 1;
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        if (value.getCssValueType() != 1) {
            return value;
        }
        switch (value.getPrimitiveType()) {
            case 1: {
                return new LineHeightValue((short)1, value.getFloatValue(), true);
            }
            case 2: {
                return new FloatValue((short)1, value.getFloatValue() * cssEngine.getComputedStyle(cssStylableElement, s, cssEngine.getFontSizeIndex()).getFloatValue() * 0.01f);
            }
            default: {
                return super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, value);
            }
        }
    }
}
