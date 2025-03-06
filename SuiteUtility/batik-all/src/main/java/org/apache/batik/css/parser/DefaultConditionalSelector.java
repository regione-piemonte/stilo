// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.Condition;
import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.ConditionalSelector;

public class DefaultConditionalSelector implements ConditionalSelector
{
    protected SimpleSelector simpleSelector;
    protected Condition condition;
    
    public DefaultConditionalSelector(final SimpleSelector simpleSelector, final Condition condition) {
        this.simpleSelector = simpleSelector;
        this.condition = condition;
    }
    
    public short getSelectorType() {
        return 0;
    }
    
    public SimpleSelector getSimpleSelector() {
        return this.simpleSelector;
    }
    
    public Condition getCondition() {
        return this.condition;
    }
    
    public String toString() {
        return String.valueOf(this.simpleSelector) + this.condition;
    }
}
