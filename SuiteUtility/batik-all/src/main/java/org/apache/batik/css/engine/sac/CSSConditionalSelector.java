// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.ConditionalSelector;

public class CSSConditionalSelector implements ConditionalSelector, ExtendedSelector
{
    protected SimpleSelector simpleSelector;
    protected Condition condition;
    
    public CSSConditionalSelector(final SimpleSelector simpleSelector, final Condition condition) {
        this.simpleSelector = simpleSelector;
        this.condition = condition;
    }
    
    public boolean equals(final Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        final CSSConditionalSelector cssConditionalSelector = (CSSConditionalSelector)o;
        return cssConditionalSelector.simpleSelector.equals(this.simpleSelector) && cssConditionalSelector.condition.equals(this.condition);
    }
    
    public short getSelectorType() {
        return 0;
    }
    
    public boolean match(final Element element, final String s) {
        return ((ExtendedSelector)this.getSimpleSelector()).match(element, s) && ((ExtendedCondition)this.getCondition()).match(element, s);
    }
    
    public void fillAttributeSet(final Set set) {
        ((ExtendedSelector)this.getSimpleSelector()).fillAttributeSet(set);
        ((ExtendedCondition)this.getCondition()).fillAttributeSet(set);
    }
    
    public int getSpecificity() {
        return ((ExtendedSelector)this.getSimpleSelector()).getSpecificity() + ((ExtendedCondition)this.getCondition()).getSpecificity();
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
