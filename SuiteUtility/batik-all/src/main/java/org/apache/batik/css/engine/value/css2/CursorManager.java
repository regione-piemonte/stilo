// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.URIValue;
import org.apache.batik.css.engine.value.AbstractValueFactory;
import org.apache.batik.css.engine.value.ListValue;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.AbstractValueManager;

public class CursorManager extends AbstractValueManager
{
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
        return 21;
    }
    
    public String getPropertyName() {
        return "cursor";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.AUTO_VALUE;
    }
    
    public Value createValue(LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        final ListValue listValue = new ListValue();
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return ValueConstants.INHERIT_VALUE;
            }
            case 24: {
                do {
                    listValue.append(new URIValue(lexicalUnit.getStringValue(), AbstractValueFactory.resolveURI(cssEngine.getCSSBaseURI(), lexicalUnit.getStringValue())));
                    lexicalUnit = lexicalUnit.getNextLexicalUnit();
                    if (lexicalUnit == null) {
                        throw this.createMalformedLexicalUnitDOMException();
                    }
                    if (lexicalUnit.getLexicalUnitType() != 0) {
                        throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
                    }
                    lexicalUnit = lexicalUnit.getNextLexicalUnit();
                    if (lexicalUnit == null) {
                        throw this.createMalformedLexicalUnitDOMException();
                    }
                } while (lexicalUnit.getLexicalUnitType() == 24);
                if (lexicalUnit.getLexicalUnitType() != 35) {
                    throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
                }
            }
            case 35: {
                final Object value = CursorManager.values.get(lexicalUnit.getStringValue().toLowerCase().intern());
                if (value == null) {
                    throw this.createInvalidIdentifierDOMException(lexicalUnit.getStringValue());
                }
                listValue.append((Value)value);
                lexicalUnit = lexicalUnit.getNextLexicalUnit();
                break;
            }
        }
        if (lexicalUnit != null) {
            throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
        }
        return listValue;
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        if (value.getCssValueType() == 2) {
            final ListValue listValue = (ListValue)value;
            final int length = listValue.getLength();
            final ListValue listValue2 = new ListValue(' ');
            for (int i = 0; i < length; ++i) {
                final Value item = listValue.item(0);
                if (item.getPrimitiveType() == 20) {
                    listValue2.append(new URIValue(item.getStringValue(), item.getStringValue()));
                }
                else {
                    listValue2.append(item);
                }
            }
            return listValue2;
        }
        return super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, value);
    }
    
    static {
        (values = new StringMap()).put("auto", ValueConstants.AUTO_VALUE);
        CursorManager.values.put("crosshair", ValueConstants.CROSSHAIR_VALUE);
        CursorManager.values.put("default", ValueConstants.DEFAULT_VALUE);
        CursorManager.values.put("e-resize", ValueConstants.E_RESIZE_VALUE);
        CursorManager.values.put("help", ValueConstants.HELP_VALUE);
        CursorManager.values.put("move", ValueConstants.MOVE_VALUE);
        CursorManager.values.put("n-resize", ValueConstants.N_RESIZE_VALUE);
        CursorManager.values.put("ne-resize", ValueConstants.NE_RESIZE_VALUE);
        CursorManager.values.put("nw-resize", ValueConstants.NW_RESIZE_VALUE);
        CursorManager.values.put("pointer", ValueConstants.POINTER_VALUE);
        CursorManager.values.put("s-resize", ValueConstants.S_RESIZE_VALUE);
        CursorManager.values.put("se-resize", ValueConstants.SE_RESIZE_VALUE);
        CursorManager.values.put("sw-resize", ValueConstants.SW_RESIZE_VALUE);
        CursorManager.values.put("text", ValueConstants.TEXT_VALUE);
        CursorManager.values.put("w-resize", ValueConstants.W_RESIZE_VALUE);
        CursorManager.values.put("wait", ValueConstants.WAIT_VALUE);
    }
}
