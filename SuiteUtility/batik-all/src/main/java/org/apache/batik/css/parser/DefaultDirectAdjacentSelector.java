// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.Selector;

public class DefaultDirectAdjacentSelector extends AbstractSiblingSelector
{
    public DefaultDirectAdjacentSelector(final short n, final Selector selector, final SimpleSelector simpleSelector) {
        super(n, selector, simpleSelector);
    }
    
    public short getSelectorType() {
        return 12;
    }
    
    public String toString() {
        return this.getSelector() + " + " + this.getSiblingSelector();
    }
}
