// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class TextAnchorManager extends IdentifierManager
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
        return "text-anchor";
    }
    
    public Value getDefaultValue() {
        return SVGValueConstants.START_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return TextAnchorManager.values;
    }
    
    static {
        (values = new StringMap()).put("start", SVGValueConstants.START_VALUE);
        TextAnchorManager.values.put("middle", SVGValueConstants.MIDDLE_VALUE);
        TextAnchorManager.values.put("end", SVGValueConstants.END_VALUE);
    }
}
