// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

public class URIValue extends StringValue
{
    String cssText;
    
    public URIValue(final String cssText, final String s) {
        super((short)20, s);
        this.cssText = cssText;
    }
    
    public String getCssText() {
        return "url(" + this.cssText + ')';
    }
}
