// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

public class DefaultPseudoClassCondition extends AbstractAttributeCondition
{
    protected String namespaceURI;
    
    public DefaultPseudoClassCondition(final String namespaceURI, final String s) {
        super(s);
        this.namespaceURI = namespaceURI;
    }
    
    public short getConditionType() {
        return 10;
    }
    
    public String getNamespaceURI() {
        return this.namespaceURI;
    }
    
    public String getLocalName() {
        return null;
    }
    
    public boolean getSpecified() {
        return false;
    }
    
    public String toString() {
        return ":" + this.getValue();
    }
}
