// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

public class DefaultOneOfAttributeCondition extends DefaultAttributeCondition
{
    public DefaultOneOfAttributeCondition(final String s, final String s2, final boolean b, final String s3) {
        super(s, s2, b, s3);
    }
    
    public short getConditionType() {
        return 7;
    }
    
    public String toString() {
        return "[" + this.getLocalName() + "~=\"" + this.getValue() + "\"]";
    }
}
