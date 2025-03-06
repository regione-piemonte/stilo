// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import java.util.Set;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.Selector;

public class CSSDirectAdjacentSelector extends AbstractSiblingSelector
{
    public CSSDirectAdjacentSelector(final short n, final Selector selector, final SimpleSelector simpleSelector) {
        super(n, selector, simpleSelector);
    }
    
    public short getSelectorType() {
        return 12;
    }
    
    public boolean match(final Element element, final String s) {
        Node previousSibling = element;
        if (!((ExtendedSelector)this.getSiblingSelector()).match(element, s)) {
            return false;
        }
        while ((previousSibling = previousSibling.getPreviousSibling()) != null && previousSibling.getNodeType() != 1) {}
        return previousSibling != null && ((ExtendedSelector)this.getSelector()).match((Element)previousSibling, null);
    }
    
    public void fillAttributeSet(final Set set) {
        ((ExtendedSelector)this.getSelector()).fillAttributeSet(set);
        ((ExtendedSelector)this.getSiblingSelector()).fillAttributeSet(set);
    }
    
    public String toString() {
        return this.getSelector() + " + " + this.getSiblingSelector();
    }
}
