// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import java.util.Set;
import org.w3c.dom.Element;

public class CSSAttributeCondition extends AbstractAttributeCondition
{
    protected String localName;
    protected String namespaceURI;
    protected boolean specified;
    
    public CSSAttributeCondition(final String localName, final String namespaceURI, final boolean specified, final String s) {
        super(s);
        this.localName = localName;
        this.namespaceURI = namespaceURI;
        this.specified = specified;
    }
    
    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return false;
        }
        final CSSAttributeCondition cssAttributeCondition = (CSSAttributeCondition)o;
        return cssAttributeCondition.namespaceURI.equals(this.namespaceURI) && cssAttributeCondition.localName.equals(this.localName) && cssAttributeCondition.specified == this.specified;
    }
    
    public int hashCode() {
        return this.namespaceURI.hashCode() ^ this.localName.hashCode() ^ (this.specified ? -1 : 0);
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
    
    public boolean match(final Element element, final String s) {
        final String value = this.getValue();
        if (value == null) {
            return !element.getAttribute(this.getLocalName()).equals("");
        }
        return element.getAttribute(this.getLocalName()).equals(value);
    }
    
    public void fillAttributeSet(final Set set) {
        set.add(this.localName);
    }
    
    public String toString() {
        if (this.value == null) {
            return '[' + this.localName + ']';
        }
        return '[' + this.localName + "=\"" + this.value + "\"]";
    }
}
