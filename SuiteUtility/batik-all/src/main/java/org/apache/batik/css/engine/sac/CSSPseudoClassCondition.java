// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import java.util.Set;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.Element;

public class CSSPseudoClassCondition extends AbstractAttributeCondition
{
    protected String namespaceURI;
    
    public CSSPseudoClassCondition(final String namespaceURI, final String s) {
        super(s);
        this.namespaceURI = namespaceURI;
    }
    
    public boolean equals(final Object o) {
        return super.equals(o) && ((CSSPseudoClassCondition)o).namespaceURI.equals(this.namespaceURI);
    }
    
    public int hashCode() {
        return this.namespaceURI.hashCode();
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
    
    public boolean match(final Element element, final String s) {
        return element instanceof CSSStylableElement && ((CSSStylableElement)element).isPseudoInstanceOf(this.getValue());
    }
    
    public void fillAttributeSet(final Set set) {
    }
    
    public String toString() {
        return ":" + this.getValue();
    }
}
