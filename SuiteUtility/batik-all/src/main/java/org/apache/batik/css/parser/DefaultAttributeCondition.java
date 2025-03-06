// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

public class DefaultAttributeCondition extends AbstractAttributeCondition
{
    protected String localName;
    protected String namespaceURI;
    protected boolean specified;
    
    public DefaultAttributeCondition(final String localName, final String namespaceURI, final boolean specified, final String s) {
        super(s);
        this.localName = localName;
        this.namespaceURI = namespaceURI;
        this.specified = specified;
    }
    
    public short getConditionType() {
        return 4;
    }
    
    public String getNamespaceURI() {
        return this.namespaceURI;
    }
    
    public String getLocalName() {
        return this.localName;
    }
    
    public boolean getSpecified() {
        return this.specified;
    }
    
    public String toString() {
        if (this.value == null) {
            return "[" + this.localName + "]";
        }
        return "[" + this.localName + "=\"" + this.value + "\"]";
    }
}
