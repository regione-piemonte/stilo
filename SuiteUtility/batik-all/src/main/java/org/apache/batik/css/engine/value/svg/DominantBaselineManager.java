// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class DominantBaselineManager extends IdentifierManager
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
        return "dominant-baseline";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.AUTO_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return DominantBaselineManager.values;
    }
    
    static {
        (values = new StringMap()).put("auto", ValueConstants.AUTO_VALUE);
        DominantBaselineManager.values.put("alphabetic", SVGValueConstants.ALPHABETIC_VALUE);
        DominantBaselineManager.values.put("central", SVGValueConstants.CENTRAL_VALUE);
        DominantBaselineManager.values.put("hanging", SVGValueConstants.HANGING_VALUE);
        DominantBaselineManager.values.put("ideographic", SVGValueConstants.IDEOGRAPHIC_VALUE);
        DominantBaselineManager.values.put("mathematical", SVGValueConstants.MATHEMATICAL_VALUE);
        DominantBaselineManager.values.put("middle", SVGValueConstants.MIDDLE_VALUE);
        DominantBaselineManager.values.put("no-change", SVGValueConstants.NO_CHANGE_VALUE);
        DominantBaselineManager.values.put("reset-size", SVGValueConstants.RESET_SIZE_VALUE);
        DominantBaselineManager.values.put("text-after-edge", SVGValueConstants.TEXT_AFTER_EDGE_VALUE);
        DominantBaselineManager.values.put("text-before-edge", SVGValueConstants.TEXT_BEFORE_EDGE_VALUE);
        DominantBaselineManager.values.put("text-bottom", SVGValueConstants.TEXT_BOTTOM_VALUE);
        DominantBaselineManager.values.put("text-top", SVGValueConstants.TEXT_TOP_VALUE);
        DominantBaselineManager.values.put("use-script", SVGValueConstants.USE_SCRIPT_VALUE);
    }
}
