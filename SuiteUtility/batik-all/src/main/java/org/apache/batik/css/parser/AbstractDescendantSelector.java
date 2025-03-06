// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.DescendantSelector;

public abstract class AbstractDescendantSelector implements DescendantSelector
{
    protected Selector ancestorSelector;
    protected SimpleSelector simpleSelector;
    
    protected AbstractDescendantSelector(final Selector ancestorSelector, final SimpleSelector simpleSelector) {
        this.ancestorSelector = ancestorSelector;
        this.simpleSelector = simpleSelector;
    }
    
    public Selector getAncestorSelector() {
        return this.ancestorSelector;
    }
    
    public SimpleSelector getSimpleSelector() {
        return this.simpleSelector;
    }
}
