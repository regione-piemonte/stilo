// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import java.util.Set;
import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.Element;

public class CSSIdCondition extends AbstractAttributeCondition
{
    protected String namespaceURI;
    protected String localName;
    
    public CSSIdCondition(final String namespaceURI, final String localName, final String s) {
        super(s);
        this.namespaceURI = namespaceURI;
        this.localName = localName;
    }
    
    public short getConditionType() {
        return 5;
    }
    
    public String getNamespaceURI() {
        return this.namespaceURI;
    }
    
    public String getLocalName() {
        return this.localName;
    }
    
    public boolean getSpecified() {
        return true;
    }
    
    public boolean match(final Element element, final String s) {
        return element instanceof CSSStylableElement && ((CSSStylableElement)element).getXMLId().equals(this.getValue());
    }
    
    public void fillAttributeSet(final Set set) {
        set.add(this.localName);
    }
    
    public int getSpecificity() {
        return 65536;
    }
    
    public String toString() {
        return '#' + this.getValue();
    }
}
