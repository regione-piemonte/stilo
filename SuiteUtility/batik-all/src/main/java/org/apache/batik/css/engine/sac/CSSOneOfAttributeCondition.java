// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import org.w3c.dom.Element;

public class CSSOneOfAttributeCondition extends CSSAttributeCondition
{
    public CSSOneOfAttributeCondition(final String s, final String s2, final boolean b, final String s3) {
        super(s, s2, b, s3);
    }
    
    public short getConditionType() {
        return 7;
    }
    
    public boolean match(final Element element, final String s) {
        final String attribute = element.getAttribute(this.getLocalName());
        final String value = this.getValue();
        final int index = attribute.indexOf(value);
        if (index == -1) {
            return false;
        }
        if (index != 0 && !Character.isSpaceChar(attribute.charAt(index - 1))) {
            return false;
        }
        final int index2 = index + value.length();
        return index2 == attribute.length() || (index2 < attribute.length() && Character.isSpaceChar(attribute.charAt(index2)));
    }
    
    public String toString() {
        return "[" + this.getLocalName() + "~=\"" + this.getValue() + "\"]";
    }
}
