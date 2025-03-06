// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value.svg;

import org.apache.batik.css.engine.value.ValueConstants;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.StringMap;
import org.apache.batik.css.engine.value.IdentifierManager;

public class PointerEventsManager extends IdentifierManager
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
        return "pointer-events";
    }
    
    public Value getDefaultValue() {
        return SVGValueConstants.VISIBLEPAINTED_VALUE;
    }
    
    public StringMap getIdentifiers() {
        return PointerEventsManager.values;
    }
    
    static {
        (values = new StringMap()).put("all", ValueConstants.ALL_VALUE);
        PointerEventsManager.values.put("fill", SVGValueConstants.FILL_VALUE);
        PointerEventsManager.values.put("fillstroke", SVGValueConstants.FILLSTROKE_VALUE);
        PointerEventsManager.values.put("none", ValueConstants.NONE_VALUE);
        PointerEventsManager.values.put("painted", ValueConstants.PAINTED_VALUE);
        PointerEventsManager.values.put("stroke", ValueConstants.STROKE_VALUE);
        PointerEventsManager.values.put("visible", ValueConstants.VISIBLE_VALUE);
        PointerEventsManager.values.put("visiblefill", SVGValueConstants.VISIBLEFILL_VALUE);
        PointerEventsManager.values.put("visiblefillstroke", SVGValueConstants.VISIBLEFILLSTROKE_VALUE);
        PointerEventsManager.values.put("visiblepainted", SVGValueConstants.VISIBLEPAINTED_VALUE);
        PointerEventsManager.values.put("visiblestroke", SVGValueConstants.VISIBLESTROKE_VALUE);
    }
}
