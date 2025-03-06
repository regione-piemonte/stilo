// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.value.FloatValue;
import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.LengthManager;

public class BaselineShiftManager extends LengthManager
{
    protected static final StringMap values;
    
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
        return 40;
    }
    
    public String getPropertyName() {
        return "baseline-shift";
    }
    
    public Value getDefaultValue() {
        return SVGValueConstants.BASELINE_VALUE;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 35: {
                final Object value = BaselineShiftManager.values.get(lexicalUnit.getStringValue().toLowerCase().intern());
                if (value == null) {
                    throw this.createInvalidIdentifierDOMException(lexicalUnit.getStringValue());
                }
                return (Value)value;
            }
            default: {
                return super.createValue(lexicalUnit, cssEngine);
            }
        }
    }
    
    public Value createStringValue(final short n, final String s, final CSSEngine cssEngine) throws DOMException {
        if (n != 21) {
            throw this.createInvalidIdentifierDOMException(s);
        }
        final Object value = BaselineShiftManager.values.get(s.toLowerCase().intern());
        if (value == null) {
            throw this.createInvalidIdentifierDOMException(s);
        }
        return (Value)value;
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        if (value.getPrimitiveType() == 2) {
            styleMap.putLineHeightRelative(n, true);
            final int lineHeightIndex = cssEngine.getLineHeightIndex();
            CSSStylableElement cssStylableElement2 = (CSSStylableElement)cssStylableElement.getParentNode();
            if (cssStylableElement2 == null) {
                cssStylableElement2 = cssStylableElement;
            }
            return new FloatValue((short)1, cssEngine.getComputedStyle(cssStylableElement2, s, lineHeightIndex).getFloatValue() * value.getFloatValue() / 100.0f);
        }
        return super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, value);
    }
    
    protected int getOrientation() {
        return 2;
    }
    
    static {
        (values = new StringMap()).put("baseline", SVGValueConstants.BASELINE_VALUE);
        BaselineShiftManager.values.put("sub", SVGValueConstants.SUB_VALUE);
        BaselineShiftManager.values.put("super", SVGValueConstants.SUPER_VALUE);
    }
}
