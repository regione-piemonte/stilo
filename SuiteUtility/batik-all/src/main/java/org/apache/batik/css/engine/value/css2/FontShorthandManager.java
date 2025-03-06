// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import java.util.HashSet;
import org.apache.batik.css.parser.CSSLexicalUnit;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.ValueManager;
import org.apache.batik.css.engine.value.IdentifierManager;
import org.apache.batik.css.engine.CSSEngine;
import java.util.Set;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ShorthandManager;
import org.apache.batik.css.engine.value.AbstractValueFactory;

public class FontShorthandManager extends AbstractValueFactory implements ShorthandManager
{
    static LexicalUnit NORMAL_LU;
    static LexicalUnit BOLD_LU;
    static LexicalUnit MEDIUM_LU;
    static LexicalUnit SZ_10PT_LU;
    static LexicalUnit SZ_8PT_LU;
    static LexicalUnit FONT_FAMILY_LU;
    protected static final Set values;
    
    public String getPropertyName() {
        return "font";
    }
    
    public boolean isAnimatableProperty() {
        return true;
    }
    
    public boolean isAdditiveProperty() {
        return false;
    }
    
    public void handleSystemFont(final CSSEngine cssEngine, final PropertyHandler propertyHandler, final String s, final boolean b) {
        final LexicalUnit normal_LU = FontShorthandManager.NORMAL_LU;
        final LexicalUnit normal_LU2 = FontShorthandManager.NORMAL_LU;
        final LexicalUnit normal_LU3 = FontShorthandManager.NORMAL_LU;
        final LexicalUnit normal_LU4 = FontShorthandManager.NORMAL_LU;
        final LexicalUnit font_FAMILY_LU = FontShorthandManager.FONT_FAMILY_LU;
        LexicalUnit lexicalUnit;
        if (s.equals("small-caption")) {
            lexicalUnit = FontShorthandManager.SZ_8PT_LU;
        }
        else {
            lexicalUnit = FontShorthandManager.SZ_10PT_LU;
        }
        propertyHandler.property("font-family", font_FAMILY_LU, b);
        propertyHandler.property("font-style", normal_LU, b);
        propertyHandler.property("font-variant", normal_LU2, b);
        propertyHandler.property("font-weight", normal_LU3, b);
        propertyHandler.property("font-size", lexicalUnit, b);
        propertyHandler.property("line-height", normal_LU4, b);
    }
    
    public void setValues(final CSSEngine cssEngine, final PropertyHandler propertyHandler, LexicalUnit lexicalUnit, final boolean b) {
        switch (lexicalUnit.getLexicalUnitType()) {
            case 12: {
                return;
            }
            case 35: {
                final String lowerCase = lexicalUnit.getStringValue().toLowerCase();
                if (FontShorthandManager.values.contains(lowerCase)) {
                    this.handleSystemFont(cssEngine, propertyHandler, lowerCase, b);
                    return;
                }
                break;
            }
        }
        LexicalUnit normal_LU = null;
        LexicalUnit normal_LU2 = null;
        LexicalUnit normal_LU3 = null;
        LexicalUnit lexicalUnit2 = null;
        LexicalUnit normal_LU4 = null;
        final ValueManager[] valueManagers = cssEngine.getValueManagers();
        final int propertyIndex = cssEngine.getPropertyIndex("font-style");
        final int propertyIndex2 = cssEngine.getPropertyIndex("font-variant");
        final int propertyIndex3 = cssEngine.getPropertyIndex("font-weight");
        final int propertyIndex4 = cssEngine.getPropertyIndex("font-size");
        final int propertyIndex5 = cssEngine.getPropertyIndex("line-height");
        final IdentifierManager identifierManager = (IdentifierManager)valueManagers[propertyIndex];
        final IdentifierManager identifierManager2 = (IdentifierManager)valueManagers[propertyIndex2];
        final IdentifierManager identifierManager3 = (IdentifierManager)valueManagers[propertyIndex3];
        final FontSizeManager fontSizeManager = (FontSizeManager)valueManagers[propertyIndex4];
        final StringMap identifiers = identifierManager.getIdentifiers();
        final StringMap identifiers2 = identifierManager2.getIdentifiers();
        final StringMap identifiers3 = identifierManager3.getIdentifiers();
        final StringMap identifiers4 = fontSizeManager.getIdentifiers();
        int n = 0;
        LexicalUnit lexicalUnit3 = null;
        while (n == 0 && lexicalUnit != null) {
            switch (lexicalUnit.getLexicalUnitType()) {
                case 35: {
                    final String intern = lexicalUnit.getStringValue().toLowerCase().intern();
                    if (normal_LU == null && identifiers.get(intern) != null) {
                        normal_LU = lexicalUnit;
                        if (lexicalUnit3 == null) {
                            break;
                        }
                        if (normal_LU3 == null) {
                            normal_LU3 = lexicalUnit3;
                            lexicalUnit3 = null;
                            break;
                        }
                        throw this.createInvalidLexicalUnitDOMException(lexicalUnit3.getLexicalUnitType());
                    }
                    else if (normal_LU2 == null && identifiers2.get(intern) != null) {
                        normal_LU2 = lexicalUnit;
                        if (lexicalUnit3 == null) {
                            break;
                        }
                        if (normal_LU3 == null) {
                            normal_LU3 = lexicalUnit3;
                            lexicalUnit3 = null;
                            break;
                        }
                        throw this.createInvalidLexicalUnitDOMException(lexicalUnit3.getLexicalUnitType());
                    }
                    else {
                        if (lexicalUnit3 == null && normal_LU3 == null && identifiers3.get(intern) != null) {
                            normal_LU3 = lexicalUnit;
                            break;
                        }
                        n = 1;
                        break;
                    }
                    break;
                }
                case 13: {
                    if (lexicalUnit3 == null && normal_LU3 == null) {
                        lexicalUnit3 = lexicalUnit;
                        break;
                    }
                    n = 1;
                    break;
                }
                default: {
                    n = 1;
                    break;
                }
            }
            if (n == 0) {
                lexicalUnit = lexicalUnit.getNextLexicalUnit();
            }
        }
        if (lexicalUnit == null) {
            throw this.createMalformedLexicalUnitDOMException();
        }
        switch (lexicalUnit.getLexicalUnitType()) {
            case 35: {
                if (identifiers4.get(lexicalUnit.getStringValue().toLowerCase().intern()) != null) {
                    lexicalUnit2 = lexicalUnit;
                    lexicalUnit = lexicalUnit.getNextLexicalUnit();
                    break;
                }
                break;
            }
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23: {
                lexicalUnit2 = lexicalUnit;
                lexicalUnit = lexicalUnit.getNextLexicalUnit();
                break;
            }
        }
        if (lexicalUnit2 == null) {
            if (lexicalUnit3 == null) {
                throw this.createInvalidLexicalUnitDOMException(lexicalUnit.getLexicalUnitType());
            }
            lexicalUnit2 = lexicalUnit3;
            lexicalUnit3 = null;
        }
        if (lexicalUnit3 != null) {
            if (normal_LU3 != null) {
                throw this.createInvalidLexicalUnitDOMException(lexicalUnit3.getLexicalUnitType());
            }
            normal_LU3 = lexicalUnit3;
        }
        if (lexicalUnit == null) {
            throw this.createMalformedLexicalUnitDOMException();
        }
        switch (lexicalUnit.getLexicalUnitType()) {
            case 4: {
                lexicalUnit = lexicalUnit.getNextLexicalUnit();
                if (lexicalUnit == null) {
                    throw this.createMalformedLexicalUnitDOMException();
                }
                normal_LU4 = lexicalUnit;
                lexicalUnit = lexicalUnit.getNextLexicalUnit();
                break;
            }
        }
        if (lexicalUnit == null) {
            throw this.createMalformedLexicalUnitDOMException();
        }
        final LexicalUnit lexicalUnit4 = lexicalUnit;
        if (normal_LU == null) {
            normal_LU = FontShorthandManager.NORMAL_LU;
        }
        if (normal_LU2 == null) {
            normal_LU2 = FontShorthandManager.NORMAL_LU;
        }
        if (normal_LU3 == null) {
            normal_LU3 = FontShorthandManager.NORMAL_LU;
        }
        if (normal_LU4 == null) {
            normal_LU4 = FontShorthandManager.NORMAL_LU;
        }
        propertyHandler.property("font-family", lexicalUnit4, b);
        propertyHandler.property("font-style", normal_LU, b);
        propertyHandler.property("font-variant", normal_LU2, b);
        propertyHandler.property("font-weight", normal_LU3, b);
        propertyHandler.property("font-size", lexicalUnit2, b);
        if (propertyIndex5 != -1) {
            propertyHandler.property("line-height", normal_LU4, b);
        }
    }
    
    static {
        FontShorthandManager.NORMAL_LU = (LexicalUnit)CSSLexicalUnit.createString((short)35, "normal", null);
        FontShorthandManager.BOLD_LU = (LexicalUnit)CSSLexicalUnit.createString((short)35, "bold", null);
        FontShorthandManager.MEDIUM_LU = (LexicalUnit)CSSLexicalUnit.createString((short)35, "medium", null);
        FontShorthandManager.SZ_10PT_LU = (LexicalUnit)CSSLexicalUnit.createFloat((short)21, 10.0f, null);
        FontShorthandManager.SZ_8PT_LU = (LexicalUnit)CSSLexicalUnit.createFloat((short)21, 8.0f, null);
        FontShorthandManager.FONT_FAMILY_LU = (LexicalUnit)CSSLexicalUnit.createString((short)35, "Dialog", null);
        CSSLexicalUnit.createString((short)35, "sans-serif", (LexicalUnit)CSSLexicalUnit.createString((short)35, "Helvetica", FontShorthandManager.FONT_FAMILY_LU));
        (values = new HashSet()).add("caption");
        FontShorthandManager.values.add("icon");
        FontShorthandManager.values.add("menu");
        FontShorthandManager.values.add("message-box");
        FontShorthandManager.values.add("small-caption");
        FontShorthandManager.values.add("status-bar");
    }
}
