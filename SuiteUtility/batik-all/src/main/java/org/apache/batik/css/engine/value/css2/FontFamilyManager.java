// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.StringValue;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.ListValue;
import org.apache.batik.css.engine.value.AbstractValueManager;

public class FontFamilyManager extends AbstractValueManager
{
    protected static final ListValue DEFAULT_VALUE;
    protected static final StringMap values;
    
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
        return 26;
    }
    
    public String getPropertyName() {
        return "font-family";
    }
    
    public Value getDefaultValue() {
        return FontFamilyManager.DEFAULT_VALUE;
    }
    
    public Value createValue(LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            default: {
                throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
            }
            case 35:
            case 36: {
                final ListValue listValue = new ListValue();
                do {
                    switch (lexicalUnit.getLexicalUnitType()) {
                        case 36: {
                            listValue.append(new StringValue((short)19, lexicalUnit.getStringValue()));
                            lexicalUnit = lexicalUnit.getNextLexicalUnit();
                            break;
                        }
                        case 35: {
                            final StringBuffer sb = new StringBuffer(lexicalUnit.getStringValue());
                            lexicalUnit = lexicalUnit.getNextLexicalUnit();
                            if (lexicalUnit != null && lexicalUnit.getLexicalUnitType() == 35) {
                                do {
                                    sb.append(' ');
                                    sb.append(lexicalUnit.getStringValue());
                                    lexicalUnit = lexicalUnit.getNextLexicalUnit();
                                } while (lexicalUnit != null && lexicalUnit.getLexicalUnitType() == 35);
                                listValue.append(new StringValue((short)19, sb.toString()));
                                break;
                            }
                            final String string = sb.toString();
                            final Value value = (Value)FontFamilyManager.values.get(string.toLowerCase().intern());
                            listValue.append((value != null) ? value : new StringValue((short)19, string));
                            break;
                        }
                    }
                    if (lexicalUnit == null) {
                        return listValue;
                    }
                    if (lexicalUnit.getLexicalUnitType() != 0) {
                        throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
                    }
                    lexicalUnit = lexicalUnit.getNextLexicalUnit();
                } while (lexicalUnit != null);
                throw this.createMalformedLexicalUnitDOMException();
            }
        }
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, Value defaultFontFamily) {
        if (defaultFontFamily == FontFamilyManager.DEFAULT_VALUE) {
            defaultFontFamily = cssEngine.getCSSContext().getDefaultFontFamily();
        }
        return defaultFontFamily;
    }
    
    static {
        (DEFAULT_VALUE = new ListValue()).append(new StringValue((short)19, "Arial"));
        FontFamilyManager.DEFAULT_VALUE.append(new StringValue((short)19, "Helvetica"));
        FontFamilyManager.DEFAULT_VALUE.append(new StringValue((short)21, "sans-serif"));
        (values = new StringMap()).put("cursive", ValueConstants.CURSIVE_VALUE);
        FontFamilyManager.values.put("fantasy", ValueConstants.FANTASY_VALUE);
        FontFamilyManager.values.put("monospace", ValueConstants.MONOSPACE_VALUE);
        FontFamilyManager.values.put("serif", ValueConstants.SERIF_VALUE);
        FontFamilyManager.values.put("sans-serif", ValueConstants.SANS_SERIF_VALUE);
    }
}
