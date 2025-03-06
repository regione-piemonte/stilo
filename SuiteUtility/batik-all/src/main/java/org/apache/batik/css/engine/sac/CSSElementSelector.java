// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import org.w3c.dom.Element;

public class CSSElementSelector extends AbstractElementSelector
{
    public CSSElementSelector(final String s, final String s2) {
        super(s, s2);
    }
    
    public short getSelectorType() {
        return 4;
    }
    
    public boolean match(final Element element, final String s) {
        final String localName = this.getLocalName();
        if (localName == null) {
            return true;
        }
        String s2;
        if (element.getPrefix() == null) {
            s2 = element.getNodeName();
        }
        else {
            s2 = element.getLocalName();
        }
        return s2.equals(localName);
    }
    
    public int getSpecificity() {
        return (this.getLocalName() != null) ? 1 : 0;
    }
    
    public String toString() {
        final String localName = this.getLocalName();
        if (localName == null) {
            return "*";
        }
        return localName;
    }
}
