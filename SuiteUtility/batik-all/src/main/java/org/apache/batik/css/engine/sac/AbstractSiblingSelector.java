// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SiblingSelector;

public abstract class AbstractSiblingSelector implements SiblingSelector, ExtendedSelector
{
    protected short nodeType;
    protected Selector selector;
    protected SimpleSelector simpleSelector;
    
    protected AbstractSiblingSelector(final short nodeType, final Selector selector, final SimpleSelector simpleSelector) {
        this.nodeType = nodeType;
        this.selector = selector;
        this.simpleSelector = simpleSelector;
    }
    
    public short getNodeType() {
        return this.nodeType;
    }
    
    public boolean equals(final Object o) {
        return o != null && o.getClass() == this.getClass() && ((AbstractSiblingSelector)o).simpleSelector.equals(this.simpleSelector);
    }
    
    public int getSpecificity() {
        return ((ExtendedSelector)this.selector).getSpecificity() + ((ExtendedSelector)this.simpleSelector).getSpecificity();
    }
    
    public Selector getSelector() {
        return this.selector;
    }
    
    public SimpleSelector getSiblingSelector() {
        return this.simpleSelector;
    }
}
