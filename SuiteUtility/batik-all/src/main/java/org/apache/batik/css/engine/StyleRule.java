// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine;

import org.w3c.css.sac.SelectorList;

public class StyleRule implements Rule
{
    public static final short TYPE = 0;
    protected SelectorList selectorList;
    protected StyleDeclaration styleDeclaration;
    
    public short getType() {
        return 0;
    }
    
    public void setSelectorList(final SelectorList selectorList) {
        this.selectorList = selectorList;
    }
    
    public SelectorList getSelectorList() {
        return this.selectorList;
    }
    
    public void setStyleDeclaration(final StyleDeclaration styleDeclaration) {
        this.styleDeclaration = styleDeclaration;
    }
    
    public StyleDeclaration getStyleDeclaration() {
        return this.styleDeclaration;
    }
    
    public String toString(final CSSEngine cssEngine) {
        final StringBuffer sb = new StringBuffer();
        if (this.selectorList != null) {
            sb.append(this.selectorList.item(0));
            for (int i = 1; i < this.selectorList.getLength(); ++i) {
                sb.append(", ");
                sb.append(this.selectorList.item(i));
            }
        }
        sb.append(" {\n");
        if (this.styleDeclaration != null) {
            sb.append(this.styleDeclaration.toString(cssEngine));
        }
        sb.append("}\n");
        return sb.toString();
    }
}
