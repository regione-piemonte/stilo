// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class AlignmentBaselineManager extends IdentifierManager
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
        return "alignment-baseline";
    }
    
    public Value getDefaultValue() {
        return ValueConstants.AUTO_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return AlignmentBaselineManager.values;
    }
    
    static {
        (values = new StringMap()).put("after-edge", SVGValueConstants.AFTER_EDGE_VALUE);
        AlignmentBaselineManager.values.put("alphabetic", SVGValueConstants.ALPHABETIC_VALUE);
        AlignmentBaselineManager.values.put("auto", ValueConstants.AUTO_VALUE);
        AlignmentBaselineManager.values.put("baseline", SVGValueConstants.BASELINE_VALUE);
        AlignmentBaselineManager.values.put("before-edge", SVGValueConstants.BEFORE_EDGE_VALUE);
        AlignmentBaselineManager.values.put("hanging", SVGValueConstants.HANGING_VALUE);
        AlignmentBaselineManager.values.put("ideographic", SVGValueConstants.IDEOGRAPHIC_VALUE);
        AlignmentBaselineManager.values.put("mathematical", SVGValueConstants.MATHEMATICAL_VALUE);
        AlignmentBaselineManager.values.put("middle", SVGValueConstants.MIDDLE_VALUE);
        AlignmentBaselineManager.values.put("text-after-edge", SVGValueConstants.TEXT_AFTER_EDGE_VALUE);
        AlignmentBaselineManager.values.put("text-before-edge", SVGValueConstants.TEXT_BEFORE_EDGE_VALUE);
    }
}
