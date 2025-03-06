// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class ColorInterpolationManager extends IdentifierManager
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
        return "color-interpolation";
    }
    
    public Value getDefaultValue() {
        return SVGValueConstants.SRGB_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return ColorInterpolationManager.values;
    }
    
    static {
        (values = new StringMap()).put("auto", ValueConstants.AUTO_VALUE);
        ColorInterpolationManager.values.put("linearrgb", SVGValueConstants.LINEARRGB_VALUE);
        ColorInterpolationManager.values.put("srgb", SVGValueConstants.SRGB_VALUE);
    }
}
