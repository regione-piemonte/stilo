// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

public class DefaultPseudoElementSelector extends AbstractElementSelector
{
    public DefaultPseudoElementSelector(final String s, final String s2) {
        super(s, s2);
    }
    
    public short getSelectorType() {
        return 9;
    }
    
    public String toString() {
        return ":" + this.getLocalName();
    }
}
