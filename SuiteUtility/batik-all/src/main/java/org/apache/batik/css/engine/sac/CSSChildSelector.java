// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import java.util.Set;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.Selector;

public class CSSChildSelector extends AbstractDescendantSelector
{
    public CSSChildSelector(final Selector selector, final SimpleSelector simpleSelector) {
        super(selector, simpleSelector);
    }
    
    public short getSelectorType() {
        return 11;
    }
    
    public boolean match(final Element element, final String s) {
        final Node parentNode = element.getParentNode();
        return parentNode != null && parentNode.getNodeType() == 1 && ((ExtendedSelector)this.getAncestorSelector()).match((Element)parentNode, null) && ((ExtendedSelector)this.getSimpleSelector()).match(element, s);
    }
    
    public void fillAttributeSet(final Set set) {
        ((ExtendedSelector)this.getAncestorSelector()).fillAttributeSet(set);
        ((ExtendedSelector)this.getSimpleSelector()).fillAttributeSet(set);
    }
    
    public String toString() {
        final SimpleSelector simpleSelector = this.getSimpleSelector();
        if (simpleSelector.getSelectorType() == 9) {
            return String.valueOf(this.getAncestorSelector()) + simpleSelector;
        }
        return this.getAncestorSelector() + " > " + simpleSelector;
    }
}
