// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.w3c.dom.DOMException;
import org.w3c.css.sac.LexicalUnit;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.value.ShorthandManager;
import org.apache.batik.css.engine.value.AbstractValueFactory;

public class MarkerShorthandManager extends AbstractValueFactory implements ShorthandManager
{
    public String getPropertyName() {
        return "marker";
    }
    
    public boolean isAnimatableProperty() {
        return true;
    }
    
    public boolean isAdditiveProperty() {
        return false;
    }
    
    public void setValues(final CSSEngine cssEngine, final PropertyHandler propertyHandler, final LexicalUnit lexicalUnit, final boolean b) throws DOMException {
        propertyHandler.property("marker-end", lexicalUnit, b);
        propertyHandler.property("marker-mid", lexicalUnit, b);
        propertyHandler.property("marker-start", lexicalUnit, b);
    }
}
