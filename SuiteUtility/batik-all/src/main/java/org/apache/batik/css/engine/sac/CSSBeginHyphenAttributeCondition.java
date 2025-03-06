// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import org.w3c.dom.Element;

public class CSSBeginHyphenAttributeCondition extends CSSAttributeCondition
{
    public CSSBeginHyphenAttributeCondition(final String s, final String s2, final boolean b, final String s3) {
        super(s, s2, b, s3);
    }
    
    public short getConditionType() {
        return 8;
    }
    
    public boolean match(final Element element, final String s) {
        return element.getAttribute(this.getLocalName()).startsWith(this.getValue());
    }
    
    public String toString() {
        return '[' + this.getLocalName() + "|=\"" + this.getValue() + "\"]";
    }
}
