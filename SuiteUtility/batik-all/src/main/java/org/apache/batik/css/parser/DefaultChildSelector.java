// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.Selector;

public class DefaultChildSelector extends AbstractDescendantSelector
{
    public DefaultChildSelector(final Selector selector, final SimpleSelector simpleSelector) {
        super(selector, simpleSelector);
    }
    
    public short getSelectorType() {
        return 11;
    }
    
    public String toString() {
        final SimpleSelector simpleSelector = this.getSimpleSelector();
        if (simpleSelector.getSelectorType() == 9) {
            return String.valueOf(this.getAncestorSelector()) + simpleSelector;
        }
        return this.getAncestorSelector() + " > " + simpleSelector;
    }
}
