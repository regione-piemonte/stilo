// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.css.sac.Condition;

public class CSSAndCondition extends AbstractCombinatorCondition
{
    public CSSAndCondition(final Condition condition, final Condition condition2) {
        super(condition, condition2);
    }
    
    public short getConditionType() {
        return 0;
    }
    
    public boolean match(final Element element, final String s) {
        return ((ExtendedCondition)this.getFirstCondition()).match(element, s) && ((ExtendedCondition)this.getSecondCondition()).match(element, s);
    }
    
    public void fillAttributeSet(final Set set) {
        ((ExtendedCondition)this.getFirstCondition()).fillAttributeSet(set);
        ((ExtendedCondition)this.getSecondCondition()).fillAttributeSet(set);
    }
    
    public String toString() {
        return String.valueOf(this.getFirstCondition()) + this.getSecondCondition();
    }
}
