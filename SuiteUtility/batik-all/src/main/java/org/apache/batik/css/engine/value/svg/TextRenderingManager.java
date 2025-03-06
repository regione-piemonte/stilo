// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class TextRenderingManager extends IdentifierManager
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
        return "text-rendering";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.AUTO_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return TextRenderingManager.values;
    }
    
    static {
        (values = new StringMap()).put("auto", ValueConstants.AUTO_VALUE);
        TextRenderingManager.values.put("optimizespeed", SVGValueConstants.OPTIMIZESPEED_VALUE);
        TextRenderingManager.values.put("geometricprecision", SVGValueConstants.GEOMETRICPRECISION_VALUE);
        TextRenderingManager.values.put("optimizelegibility", SVGValueConstants.OPTIMIZELEGIBILITY_VALUE);
    }
}
