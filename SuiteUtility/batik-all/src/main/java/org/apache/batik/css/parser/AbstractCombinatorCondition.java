// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.Condition;
import org.w3c.css.sac.CombinatorCondition;

public abstract class AbstractCombinatorCondition implements CombinatorCondition
{
    protected Condition firstCondition;
    protected Condition secondCondition;
    
    protected AbstractCombinatorCondition(final Condition firstCondition, final Condition secondCondition) {
        this.firstCondition = firstCondition;
        this.secondCondition = secondCondition;
    }
    
    public Condition getFirstCondition() {
        return this.firstCondition;
    }
    
    public Condition getSecondCondition() {
        return this.secondCondition;
    }
}
