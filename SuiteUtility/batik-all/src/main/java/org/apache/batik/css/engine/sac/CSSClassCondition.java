// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import org.apache.batik.css.engine.CSSStylableElement;
import org.w3c.dom.Element;

public class CSSClassCondition extends CSSAttributeCondition
{
    public CSSClassCondition(final String s, final String s2, final String s3) {
        super(s, s2, true, s3);
    }
    
    public short getConditionType() {
        return 9;
    }
    
    public boolean match(final Element element, final String s) {
        if (!(element instanceof CSSStylableElement)) {
            return false;
        }
        final String cssClass = ((CSSStylableElement)element).getCSSClass();
        final String value = this.getValue();
        final int length = cssClass.length();
        for (int length2 = value.length(), i = cssClass.indexOf(value); i != -1; i = cssClass.indexOf(value, i + length2)) {
            if ((i == 0 || Character.isSpaceChar(cssClass.charAt(i - 1))) && (i + length2 == length || Character.isSpaceChar(cssClass.charAt(i + length2)))) {
                return true;
            }
        }
        return false;
    }
    
    public String toString() {
        return '.' + this.getValue();
    }
}
