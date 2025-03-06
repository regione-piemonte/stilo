// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class StrokeLinejoinManager extends IdentifierManager
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
        return "stroke-linejoin";
    }
    
    public Value getDefaultValue() {
        return SVGValueConstants.MITER_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return StrokeLinejoinManager.values;
    }
    
    static {
        (values = new StringMap()).put("miter", SVGValueConstants.MITER_VALUE);
        StrokeLinejoinManager.values.put("round", SVGValueConstants.ROUND_VALUE);
        StrokeLinejoinManager.values.put("bevel", SVGValueConstants.BEVEL_VALUE);
    }
}
