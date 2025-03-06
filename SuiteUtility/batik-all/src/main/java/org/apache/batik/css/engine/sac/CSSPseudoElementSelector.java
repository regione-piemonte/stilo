// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import org.w3c.dom.Element;

public class CSSPseudoElementSelector extends AbstractElementSelector
{
    public CSSPseudoElementSelector(final String s, final String s2) {
        super(s, s2);
    }
    
    public short getSelectorType() {
        return 9;
    }
    
    public boolean match(final Element element, final String anotherString) {
        return this.getLocalName().equalsIgnoreCase(anotherString);
    }
    
    public int getSpecificity() {
        return 0;
    }
    
    public String toString() {
        return ":" + this.getLocalName();
    }
}
