// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import org.w3c.css.sac.Condition;
import org.w3c.css.sac.CombinatorCondition;

public abstract class AbstractCombinatorCondition implements CombinatorCondition, ExtendedCondition
{
    protected Condition firstCondition;
    protected Condition secondCondition;
    
    protected AbstractCombinatorCondition(final Condition firstCondition, final Condition secondCondition) {
        this.firstCondition = firstCondition;
        this.secondCondition = secondCondition;
    }
    
    public boolean equals(final Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        final AbstractCombinatorCondition abstractCombinatorCondition = (AbstractCombinatorCondition)o;
        return abstractCombinatorCondition.firstCondition.equals(this.firstCondition) && abstractCombinatorCondition.secondCondition.equals(this.secondCondition);
    }
    
    public int getSpecificity() {
        return ((ExtendedCondition)this.getFirstCondition()).getSpecificity() + ((ExtendedCondition)this.getSecondCondition()).getSpecificity();
    }
    
    public Condition getFirstCondition() {
        return this.firstCondition;
    }
    
    public Condition getSecondCondition() {
        return this.secondCondition;
    }
}
