// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class FontStyleManager extends IdentifierManager
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
        return "font-style";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.NORMAL_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return FontStyleManager.values;
    }
    
    static {
        (values = new StringMap()).put("all", ValueConstants.ALL_VALUE);
        FontStyleManager.values.put("italic", ValueConstants.ITALIC_VALUE);
        FontStyleManager.values.put("normal", ValueConstants.NORMAL_VALUE);
        FontStyleManager.values.put("oblique", ValueConstants.OBLIQUE_VALUE);
    }
}
