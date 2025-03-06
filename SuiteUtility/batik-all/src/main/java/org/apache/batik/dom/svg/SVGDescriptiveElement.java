// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Element;
import org.apache.batik.dom.util.XMLSupport;
import org.apache.batik.dom.AbstractDocument;

public abstract class SVGDescriptiveElement extends SVGStylableElement
{
    protected SVGDescriptiveElement() {
    }
    
    protected SVGDescriptiveElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getXMLlang() {
        return XMLSupport.getXMLLang(this);
    }
    
    public void setXMLlang(final String s) {
        this.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", s);
    }
    
    public String getXMLspace() {
        return XMLSupport.getXMLSpace(this);
    }
    
    public void setXMLspace(final String s) {
        this.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:space", s);
    }
}
