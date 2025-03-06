// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

public class DefaultIdCondition extends AbstractAttributeCondition
{
    public DefaultIdCondition(final String s) {
        super(s);
    }
    
    public short getConditionType() {
        return 5;
    }
    
    public String getNamespaceURI() {
        return null;
    }
    
    public String getLocalName() {
        return "id";
    }
    
    public boolean getSpecified() {
        return true;
    }
    
    public String toString() {
        return "#" + this.getValue();
    }
}
