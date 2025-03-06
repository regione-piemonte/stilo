// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class FillRuleManager extends IdentifierManager
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
        return "fill-rule";
    }
    
    public Value getDefaultValue() {
        return SVGValueConstants.NONZERO_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return FillRuleManager.values;
    }
    
    static {
        (values = new StringMap()).put("evenodd", SVGValueConstants.EVENODD_VALUE);
        FillRuleManager.values.put("nonzero", SVGValueConstants.NONZERO_VALUE);
    }
}
