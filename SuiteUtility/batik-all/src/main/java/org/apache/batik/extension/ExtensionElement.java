// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension;

import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg.SVGOMElement;

public abstract class ExtensionElement extends SVGOMElement
{
    protected ExtensionElement() {
    }
    
    protected ExtensionElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public boolean isReadonly() {
        return false;
    }
    
    public void setReadonly(final boolean b) {
    }
}
