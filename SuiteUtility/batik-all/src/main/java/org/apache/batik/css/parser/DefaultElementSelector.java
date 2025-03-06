// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

public class DefaultElementSelector extends AbstractElementSelector
{
    public DefaultElementSelector(final String s, final String s2) {
        super(s, s2);
    }
    
    public short getSelectorType() {
        return 4;
    }
    
    public String toString() {
        final String localName = this.getLocalName();
        if (localName == null) {
            return "*";
        }
        return localName;
    }
}
