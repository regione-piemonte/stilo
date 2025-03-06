// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg12;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.LengthManager;

public class MarginLengthManager extends LengthManager
{
    protected String prop;
    
    public MarginLengthManager(final String prop) {
        this.prop = prop;
    }
    
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
        return 17;
    }
    
    public String getPropertyName() {
        return this.prop;
    }
    
    public Value getDefaultValue() {
        return ValueConstants.NUMBER_0;
    }
    
    public Value createValue(final LexicalUnit lexicalUnit, final CSSEngine cssEngine) throws DOMException {
        if (lexicalUnit.getLexicalUnitType() == 12) {
            return ValueConstants.INHERIT_VALUE;
        }
        return super.createValue(lexicalUnit, cssEngine);
    }
    
    protected int getOrientation() {
        return 0;
    }
}
