// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.ListValue;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;

public class SVGColorManager extends ColorManager
{
    protected String property;
    protected Value defaultValue;
    
    public SVGColorManager(final String s) {
        this(s, ValueConstants.BLACK_RGB_VALUE);
    }
    
    public SVGColorManager(final String property, final Value defaultValue) {
        this.property = property;
        this.defaultValue = defaultValue;
    }
    
    public boolean isInheritedProperty() {
        return false;
    }
    
    public boolean isAnimatableProperty() {
        return true;
    }
    
    public boolean isAdditiveProperty() {
        return true;
    }
    
    public int getPropertyType() {
        return 6;
    }
    
    public String getPropertyName() {
        return this.property;
    }
    
    public Value getDefaultValue() {
        return this.defaultValue;
    }
    
    public Value createValue(LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        if (lexicalUnit.getLexicalUnitType() == 35 && lexicalUnit.getStringValue().equalsIgnoreCase("currentcolor")) {
            return SVGValueConstants.CURRENTCOLOR_VALUE;
        }
        final Value value = super.createValue(lexicalUnit, cssEngine);
        lexicalUnit = lexicalUnit.getNextLexicalUnit();
        if (lexicalUnit == null) {
            return value;
        }
        if (lexicalUnit.getLexicalUnitType() != 41 || !lexicalUnit.getFunctionName().equalsIgnoreCase("icc-color")) {
            throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
        }
        lexicalUnit = lexicalUnit.getParameters();
        if (lexicalUnit.getLexicalUnitType() != 35) {
            throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
        }
        final ListValue listValue = new ListValue(' ');
        listValue.append(value);
        final ICCColor iccColor = new ICCColor(lexicalUnit.getStringValue());
        listValue.append(iccColor);
        for (lexicalUnit = lexicalUnit.getNextLexicalUnit(); lexicalUnit != null; lexicalUnit = lexicalUnit.getNextLexicalUnit()) {
            if (lexicalUnit.getLexicalUnitType() != 0) {
                throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
            }
            lexicalUnit = lexicalUnit.getNextLexicalUnit();
            if (lexicalUnit == null) {
                throw this.createInvalidLexicalUnitDOMException((short)(-1));
            }
            iccColor.append(this.getColorValue(lexicalUnit));
        }
        return listValue;
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        if (value == SVGValueConstants.CURRENTCOLOR_VALUE) {
            styleMap.putColorRelative(n, true);
            return cssEngine.getComputedStyle(cssStylableElement, s, cssEngine.getColorIndex());
        }
        if (value.getCssValueType() != 2) {
            return super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, value);
        }
        final ListValue listValue = (ListValue)value;
        final Value item = listValue.item(0);
        final Value computeValue = super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, item);
        if (computeValue != item) {
            final ListValue listValue2 = new ListValue(' ');
            listValue2.append(computeValue);
            listValue2.append(listValue.item(1));
            return listValue2;
        }
        return value;
    }
    
    protected float getColorValue(final LexicalUnit lexicalUnit) {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 13: {
                return (float)lexicalUnit.getIntegerValue();
            }
            case 14: {
                return lexicalUnit.getFloatValue();
            }
            default: {
                throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
            }
        }
    }
}
