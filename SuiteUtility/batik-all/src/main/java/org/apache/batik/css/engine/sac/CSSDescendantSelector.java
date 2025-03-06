// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import java.util.Set;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.Selector;

public class CSSDescendantSelector extends AbstractDescendantSelector
{
    public CSSDescendantSelector(final Selector selector, final SimpleSelector simpleSelector) {
        super(selector, simpleSelector);
    }
    
    public short getSelectorType() {
        return 10;
    }
    
    public boolean match(final Element element, final String s) {
        final ExtendedSelector extendedSelector = (ExtendedSelector)this.getAncestorSelector();
        if (!((ExtendedSelector)this.getSimpleSelector()).match(element, s)) {
            return false;
        }
        for (Node node = element.getParentNode(); node != null; node = node.getParentNode()) {
            if (node.getNodeType() == 1 && extendedSelector.match((Element)node, null)) {
                return true;
            }
        }
        return false;
    }
    
    public void fillAttributeSet(final Set set) {
        ((ExtendedSelector)this.getSimpleSelector()).fillAttributeSet(set);
    }
    
    public String toString() {
        return this.getAncestorSelector() + " " + this.getSimpleSelector();
    }
}
