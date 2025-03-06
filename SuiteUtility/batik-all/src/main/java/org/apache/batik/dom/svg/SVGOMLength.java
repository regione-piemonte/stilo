// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

public class SVGOMLength extends AbstractSVGLength
{
    protected AbstractElement element;
    
    public SVGOMLength(final AbstractElement element) {
        super((short)0);
        this.element = element;
    }
    
    protected SVGOMElement getAssociatedElement() {
        return (SVGOMElement)this.element;
    }
}
