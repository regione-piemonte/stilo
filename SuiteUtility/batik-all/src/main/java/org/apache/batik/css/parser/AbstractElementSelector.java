// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.ElementSelector;

public abstract class AbstractElementSelector implements ElementSelector
{
    protected String namespaceURI;
    protected String localName;
    
    protected AbstractElementSelector(final String namespaceURI, final String localName) {
        this.namespaceURI = namespaceURI;
        this.localName = localName;
    }
    
    public String getNamespaceURI() {
        return this.namespaceURI;
    }
    
    public String getLocalName() {
        return this.localName;
    }
}
