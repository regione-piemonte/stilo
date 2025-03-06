// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;

public abstract class AbstractColorManager extends IdentifierManager
{
    protected static final StringMap values;
    protected static final StringMap computedValues;
    
    public Value createValue(LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        if (lexicalUnit.getLexicalUnitType() == 27) {
            lexicalUnit = lexicalUnit.getParameters();
            final Value colorComponent = this.createColorComponent(lexicalUnit);
            lexicalUnit = lexicalUnit.getNextLexicalUnit().getNextLexicalUnit();
            final Value colorComponent2 = this.createColorComponent(lexicalUnit);
            lexicalUnit = lexicalUnit.getNextLexicalUnit().getNextLexicalUnit();
            return this.createRGBColor(colorComponent, colorComponent2, this.createColorComponent(lexicalUnit));
        }
        return super.createValue(lexicalUnit, cssEngine);
    }
    
    public Value computeValue(final CSSStylableElement cssStylableElement, final String s, final CSSEngine cssEngine, final int n, final StyleMap styleMap, final Value value) {
        if (value.getPrimitiveType() != 21) {
            return super.computeValue(cssStylableElement, s, cssEngine, n, styleMap, value);
        }
        final String stringValue = value.getStringValue();
        final Value value2 = (Value)AbstractColorManager.computedValues.get(stringValue);
        if (value2 != null) {
            return value2;
        }
        if (AbstractColorManager.values.get(stringValue) == null) {
            throw new IllegalStateException("Not a system-color:" + stringValue);
        }
        return cssEngine.getCSSContext().getSystemColor(stringValue);
    }
    
    protected Value createRGBColor(final Value value, final Value value2, final Value value3) {
        return new RGBColorValue(value, value2, value3);
    }
    
    protected Value createColorComponent(final LexicalUnit lexicalUnit) throws DOMException {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 13: {
                return new FloatValue((short)1, (float)lexicalUnit.getIntegerValue());
            }
            case 14: {
                return new FloatValue((short)1, lexicalUnit.getFloatValue());
            }
            case 23: {
                return new FloatValue((short)2, lexicalUnit.getFloatValue());
            }
            default: {
                throw this.createInvalidRGBComponentUnitDOMException(lexicalUnit.getLexicalUnitType());
            }
        }
    }
    
    public StringMap getIdentifiers() {
        return AbstractColorManager.values;
    }
    
    private DOMException createInvalidRGBComponentUnitDOMException(final short value) {
        return new DOMException((short)9, Messages.formatMessage("invalid.rgb.component.unit", new Object[] { this.getPropertyName(), new Integer(value) }));
    }
    
    static {
        (values = new StringMap()).put("aqua", ValueConstants.AQUA_VALUE);
        AbstractColorManager.values.put("black", ValueConstants.BLACK_VALUE);
        AbstractColorManager.values.put("blue", ValueConstants.BLUE_VALUE);
        AbstractColorManager.values.put("fuchsia", ValueConstants.FUCHSIA_VALUE);
        AbstractColorManager.values.put("gray", ValueConstants.GRAY_VALUE);
        AbstractColorManager.values.put("green", ValueConstants.GREEN_VALUE);
        AbstractColorManager.values.put("lime", ValueConstants.LIME_VALUE);
        AbstractColorManager.values.put("maroon", ValueConstants.MAROON_VALUE);
        AbstractColorManager.values.put("navy", ValueConstants.NAVY_VALUE);
        AbstractColorManager.values.put("olive", ValueConstants.OLIVE_VALUE);
        AbstractColorManager.values.put("purple", ValueConstants.PURPLE_VALUE);
        AbstractColorManager.values.put("red", ValueConstants.RED_VALUE);
        AbstractColorManager.values.put("silver", ValueConstants.SILVER_VALUE);
        AbstractColorManager.values.put("teal", ValueConstants.TEAL_VALUE);
        AbstractColorManager.values.put("white", ValueConstants.WHITE_VALUE);
        AbstractColorManager.values.put("yellow", ValueConstants.YELLOW_VALUE);
        AbstractColorManager.values.put("activeborder", ValueConstants.ACTIVEBORDER_VALUE);
        AbstractColorManager.values.put("activecaption", ValueConstants.ACTIVECAPTION_VALUE);
        AbstractColorManager.values.put("appworkspace", ValueConstants.APPWORKSPACE_VALUE);
        AbstractColorManager.values.put("background", ValueConstants.BACKGROUND_VALUE);
        AbstractColorManager.values.put("buttonface", ValueConstants.BUTTONFACE_VALUE);
        AbstractColorManager.values.put("buttonhighlight", ValueConstants.BUTTONHIGHLIGHT_VALUE);
        AbstractColorManager.values.put("buttonshadow", ValueConstants.BUTTONSHADOW_VALUE);
        AbstractColorManager.values.put("buttontext", ValueConstants.BUTTONTEXT_VALUE);
        AbstractColorManager.values.put("captiontext", ValueConstants.CAPTIONTEXT_VALUE);
        AbstractColorManager.values.put("graytext", ValueConstants.GRAYTEXT_VALUE);
        AbstractColorManager.values.put("highlight", ValueConstants.HIGHLIGHT_VALUE);
        AbstractColorManager.values.put("highlighttext", ValueConstants.HIGHLIGHTTEXT_VALUE);
        AbstractColorManager.values.put("inactiveborder", ValueConstants.INACTIVEBORDER_VALUE);
        AbstractColorManager.values.put("inactivecaption", ValueConstants.INACTIVECAPTION_VALUE);
        AbstractColorManager.values.put("inactivecaptiontext", ValueConstants.INACTIVECAPTIONTEXT_VALUE);
        AbstractColorManager.values.put("infobackground", ValueConstants.INFOBACKGROUND_VALUE);
        AbstractColorManager.values.put("infotext", ValueConstants.INFOTEXT_VALUE);
        AbstractColorManager.values.put("menu", ValueConstants.MENU_VALUE);
        AbstractColorManager.values.put("menutext", ValueConstants.MENUTEXT_VALUE);
        AbstractColorManager.values.put("scrollbar", ValueConstants.SCROLLBAR_VALUE);
        AbstractColorManager.values.put("threeddarkshadow", ValueConstants.THREEDDARKSHADOW_VALUE);
        AbstractColorManager.values.put("threedface", ValueConstants.THREEDFACE_VALUE);
        AbstractColorManager.values.put("threedhighlight", ValueConstants.THREEDHIGHLIGHT_VALUE);
        AbstractColorManager.values.put("threedlightshadow", ValueConstants.THREEDLIGHTSHADOW_VALUE);
        AbstractColorManager.values.put("threedshadow", ValueConstants.THREEDSHADOW_VALUE);
        AbstractColorManager.values.put("window", ValueConstants.WINDOW_VALUE);
        AbstractColorManager.values.put("windowframe", ValueConstants.WINDOWFRAME_VALUE);
        AbstractColorManager.values.put("windowtext", ValueConstants.WINDOWTEXT_VALUE);
        (computedValues = new StringMap()).put("black", ValueConstants.BLACK_RGB_VALUE);
        AbstractColorManager.computedValues.put("silver", ValueConstants.SILVER_RGB_VALUE);
        AbstractColorManager.computedValues.put("gray", ValueConstants.GRAY_RGB_VALUE);
        AbstractColorManager.computedValues.put("white", ValueConstants.WHITE_RGB_VALUE);
        AbstractColorManager.computedValues.put("maroon", ValueConstants.MAROON_RGB_VALUE);
        AbstractColorManager.computedValues.put("red", ValueConstants.RED_RGB_VALUE);
        AbstractColorManager.computedValues.put("purple", ValueConstants.PURPLE_RGB_VALUE);
        AbstractColorManager.computedValues.put("fuchsia", ValueConstants.FUCHSIA_RGB_VALUE);
        AbstractColorManager.computedValues.put("green", ValueConstants.GREEN_RGB_VALUE);
        AbstractColorManager.computedValues.put("lime", ValueConstants.LIME_RGB_VALUE);
        AbstractColorManager.computedValues.put("olive", ValueConstants.OLIVE_RGB_VALUE);
        AbstractColorManager.computedValues.put("yellow", ValueConstants.YELLOW_RGB_VALUE);
        AbstractColorManager.computedValues.put("navy", ValueConstants.NAVY_RGB_VALUE);
        AbstractColorManager.computedValues.put("blue", ValueConstants.BLUE_RGB_VALUE);
        AbstractColorManager.computedValues.put("teal", ValueConstants.TEAL_RGB_VALUE);
        AbstractColorManager.computedValues.put("aqua", ValueConstants.AQUA_RGB_VALUE);
    }
}
