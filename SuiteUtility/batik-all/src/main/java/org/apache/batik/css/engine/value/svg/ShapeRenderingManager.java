// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class ShapeRenderingManager extends IdentifierManager
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
        return "shape-rendering";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.AUTO_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return ShapeRenderingManager.values;
    }
    
    static {
        (values = new StringMap()).put("auto", ValueConstants.AUTO_VALUE);
        ShapeRenderingManager.values.put("crispedges", ValueConstants.CRISPEDGES_VALUE);
        ShapeRenderingManager.values.put("geometricprecision", SVGValueConstants.GEOMETRICPRECISION_VALUE);
        ShapeRenderingManager.values.put("optimizespeed", SVGValueConstants.OPTIMIZESPEED_VALUE);
    }
}
