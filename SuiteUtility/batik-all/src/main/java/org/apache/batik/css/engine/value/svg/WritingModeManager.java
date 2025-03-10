// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class WritingModeManager extends IdentifierManager
{
    protected static final StringMap values;
    
    public boolean isInheritedProperty() {
        return true;
    }
    
    public boolean isAnimatableProperty() {
        return false;
    }
    
    public boolean isAdditiveProperty() {
        return false;
    }
    
    public int getPropertyType() {
        return 15;
    }
    
    public String getPropertyName() {
        return "writing-mode";
    }
    
    public Value getDefaultValue() {
        return SVGValueConstants.LR_TB_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return WritingModeManager.values;
    }
    
    static {
        (values = new StringMap()).put("lr", SVGValueConstants.LR_VALUE);
        WritingModeManager.values.put("lr-tb", SVGValueConstants.LR_TB_VALUE);
        WritingModeManager.values.put("rl", SVGValueConstants.RL_VALUE);
        WritingModeManager.values.put("rl-tb", SVGValueConstants.RL_TB_VALUE);
        WritingModeManager.values.put("tb", SVGValueConstants.TB_VALUE);
        WritingModeManager.values.put("tb-rl", SVGValueConstants.TB_RL_VALUE);
    }
}
