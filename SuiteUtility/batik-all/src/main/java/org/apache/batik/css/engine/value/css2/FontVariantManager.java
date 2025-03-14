// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.css2;

import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class FontVariantManager extends IdentifierManager
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
        return "font-variant";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.NORMAL_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return FontVariantManager.values;
    }
    
    static {
        (values = new StringMap()).put("normal", ValueConstants.NORMAL_VALUE);
        FontVariantManager.values.put("small-caps", ValueConstants.SMALL_CAPS_VALUE);
    }
}
