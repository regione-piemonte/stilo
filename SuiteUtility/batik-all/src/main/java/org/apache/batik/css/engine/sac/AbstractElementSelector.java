// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import java.util.Set;
import org.w3c.css.sac.ElementSelector;

public abstract class AbstractElementSelector implements ElementSelector, ExtendedSelector
{
    protected String namespaceURI;
    protected String localName;
    
    protected AbstractElementSelector(final String namespaceURI, final String localName) {
        this.namespaceURI = namespaceURI;
        this.localName = localName;
    }
    
    public boolean equals(final Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        final AbstractElementSelector abstractElementSelector = (AbstractElementSelector)o;
        return abstractElementSelector.namespaceURI.equals(this.namespaceURI) && abstractElementSelector.localName.equals(this.localName);
    }
    
    public String getNamespaceURI() {
        return this.namespaceURI;
    }
    
    public String getLocalName() {
        return this.localName;
    }
    
    public void fillAttributeSet(final Set set) {
    }
}
