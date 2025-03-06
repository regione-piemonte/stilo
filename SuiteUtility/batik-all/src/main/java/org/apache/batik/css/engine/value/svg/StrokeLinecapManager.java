// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class StrokeLinecapManager extends IdentifierManager
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
        return 15;
    }
    
    public String getPropertyName() {
        return "stroke-linecap";
    }
    
    public Value getDefaultValue() {
        return SVGValueConstants.BUTT_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return StrokeLinecapManager.values;
    }
    
    static {
        (values = new StringMap()).put("butt", SVGValueConstants.BUTT_VALUE);
        StrokeLinecapManager.values.put("round", SVGValueConstants.ROUND_VALUE);
        StrokeLinecapManager.values.put("square", SVGValueConstants.SQUARE_VALUE);
    }
}
