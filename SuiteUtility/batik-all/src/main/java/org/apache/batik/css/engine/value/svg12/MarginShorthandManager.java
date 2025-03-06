// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg12;

import org.w3c.dom.DOMException;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.value.ShorthandManager;
import org.apache.batik.css.engine.value.AbstractValueFactory;

public class MarginShorthandManager extends AbstractValueFactory implements ShorthandManager
{
    public String getPropertyName() {
        return "margin";
    }
    
    public boolean isAnimatableProperty() {
        return true;
    }
    
    public boolean isAdditiveProperty() {
        return false;
    }
    
    public void setValues(final CSSEngine cssEngine, final PropertyHandler propertyHandler, LexicalUnit nextLexicalUnit, final boolean b) throws DOMException {
        if (nextLexicalUnit.getLexicalUnitType() == 12) {
            return;
        }
        final LexicalUnit[] array = new LexicalUnit[4];
        int n = 0;
        while (nextLexicalUnit != null) {
            if (n == 4) {
                throw this.createInvalidLexicalUnitDOMException(nextLexicalUnit.getLexicalUnitType());
            }
            array[n++] = nextLexicalUnit;
            nextLexicalUnit = nextLexicalUnit.getNextLexicalUnit();
        }
        switch (n) {
            case 1: {
                final LexicalUnit[] array2 = array;
                final int n2 = 3;
                final LexicalUnit[] array3 = array;
                final int n3 = 2;
                final LexicalUnit[] array4 = array;
                final int n4 = 1;
                final LexicalUnit lexicalUnit = array[0];
                array4[n4] = lexicalUnit;
                array2[n2] = (array3[n3] = lexicalUnit);
                break;
            }
            case 2: {
                array[2] = array[0];
                array[3] = array[1];
                break;
            }
            case 3: {
                array[3] = array[1];
                break;
            }
        }
        propertyHandler.property("margin-top", array[0], b);
        propertyHandler.property("margin-right", array[1], b);
        propertyHandler.property("margin-bottom", array[2], b);
        propertyHandler.property("margin-left", array[3], b);
    }
}
