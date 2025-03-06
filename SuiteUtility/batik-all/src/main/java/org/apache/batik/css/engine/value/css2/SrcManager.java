// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.URIValue;
import org.apache.batik.css.engine.value.AbstractValueFactory;
import org.apache.batik.css.engine.value.StringValue;
import org.apache.batik.css.engine.value.ListValue;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class SrcManager extends IdentifierManager
{
    protected static final StringMap values;
    
    public boolean isInheritedProperty() {
        return false;
    }
    
    public boolean isAnimatableProperty() {
        return false;
    }
    
    public boolean isAdditiveProperty() {
        return false;
    }
    
    public int getPropertyType() {
        return 38;
    }
    
    public String getPropertyName() {
        return "src";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.NONE_VALUE;
    }
    
    public Value createValue(LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            default: {
                throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
            }
            case 24:
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
                        case 24: {
                            listValue.append(new URIValue(lexicalUnit.getStringValue(), AbstractValueFactory.resolveURI(cssEngine.getCSSBaseURI(), lexicalUnit.getStringValue())));
                            lexicalUnit = lexicalUnit.getNextLexicalUnit();
                            if (lexicalUnit == null || lexicalUnit.getLexicalUnitType() != 41) {
                                break;
                            }
                            if (!lexicalUnit.getFunctionName().equalsIgnoreCase("format")) {
                                break;
                            }
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
                            final Value value = (Value)SrcManager.values.get(string.toLowerCase().intern());
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
    
    public StringMap getIdentifiers() {
        return SrcManager.values;
    }
    
    static {
        (values = new StringMap()).put("none", ValueConstants.NONE_VALUE);
    }
}
