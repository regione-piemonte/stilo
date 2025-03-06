// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.Condition;

public class DefaultAndCondition extends AbstractCombinatorCondition
{
    public DefaultAndCondition(final Condition condition, final Condition condition2) {
        super(condition, condition2);
    }
    
    public short getConditionType() {
        return 0;
    }
    
    public String toString() {
        return String.valueOf(this.getFirstCondition()) + this.getSecondCondition();
    }
}
