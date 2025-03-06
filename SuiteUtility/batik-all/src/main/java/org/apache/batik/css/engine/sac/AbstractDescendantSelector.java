// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.DescendantSelector;

public abstract class AbstractDescendantSelector implements DescendantSelector, ExtendedSelector
{
    protected Selector ancestorSelector;
    protected SimpleSelector simpleSelector;
    
    protected AbstractDescendantSelector(final Selector ancestorSelector, final SimpleSelector simpleSelector) {
        this.ancestorSelector = ancestorSelector;
        this.simpleSelector = simpleSelector;
    }
    
    public boolean equals(final Object o) {
        return o != null && o.getClass() == this.getClass() && ((AbstractDescendantSelector)o).simpleSelector.equals(this.simpleSelector);
    }
    
    public int getSpecificity() {
        return ((ExtendedSelector)this.ancestorSelector).getSpecificity() + ((ExtendedSelector)this.simpleSelector).getSpecificity();
    }
    
    public Selector getAncestorSelector() {
        return this.ancestorSelector;
    }
    
    public SimpleSelector getSimpleSelector() {
        return this.simpleSelector;
    }
}
