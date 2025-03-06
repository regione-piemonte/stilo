// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

public class DefaultClassCondition extends DefaultAttributeCondition
{
    public DefaultClassCondition(final String s, final String s2) {
        super("class", s, true, s2);
    }
    
    public short getConditionType() {
        return 9;
    }
    
    public String toString() {
        return "." + this.getValue();
    }
}
