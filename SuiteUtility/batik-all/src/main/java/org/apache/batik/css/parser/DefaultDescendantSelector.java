// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.Selector;

public class DefaultDescendantSelector extends AbstractDescendantSelector
{
    public DefaultDescendantSelector(final Selector selector, final SimpleSelector simpleSelector) {
        super(selector, simpleSelector);
    }
    
    public short getSelectorType() {
        return 10;
    }
    
    public String toString() {
        return this.getAncestorSelector() + " " + this.getSimpleSelector();
    }
}
