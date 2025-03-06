// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class OverflowManager extends IdentifierManager
{
    protected static final StringMap values;
    
    public boolean isInheritedProperty() {
        return false;
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
        return "overflow";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.VISIBLE_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return OverflowManager.values;
    }
    
    static {
        (values = new StringMap()).put("auto", ValueConstants.AUTO_VALUE);
        OverflowManager.values.put("hidden", ValueConstants.HIDDEN_VALUE);
        OverflowManager.values.put("scroll", ValueConstants.SCROLL_VALUE);
        OverflowManager.values.put("visible", ValueConstants.VISIBLE_VALUE);
    }
}
