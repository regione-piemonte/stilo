// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

public class SVGOMAnimatedLength extends AbstractSVGAnimatedLength
{
    protected String defaultValue;
    
    public SVGOMAnimatedLength(final AbstractElement abstractElement, final String s, final String s2, final String defaultValue, final short n, final boolean b) {
        super(abstractElement, s, s2, n, b);
        this.defaultValue = defaultValue;
    }
    
    protected String getDefaultValue() {
        return this.defaultValue;
    }
}
