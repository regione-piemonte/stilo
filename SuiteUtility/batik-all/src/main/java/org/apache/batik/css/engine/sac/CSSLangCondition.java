// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.css.sac.LangCondition;

public class CSSLangCondition implements LangCondition, ExtendedCondition
{
    protected String lang;
    protected String langHyphen;
    
    public CSSLangCondition(final String str) {
        this.lang = str.toLowerCase();
        this.langHyphen = str + '-';
    }
    
    public boolean equals(final Object o) {
        return o != null && o.getClass() == this.getClass() && ((CSSLangCondition)o).lang.equals(this.lang);
    }
    
    public short getConditionType() {
        return 6;
    }
    
    public String getLang() {
        return this.lang;
    }
    
    public int getSpecificity() {
        return 256;
    }
    
    public boolean match(final Element element, final String s) {
        final String lowerCase = element.getAttribute("lang").toLowerCase();
        if (lowerCase.equals(this.lang) || lowerCase.startsWith(this.langHyphen)) {
            return true;
        }
        final String lowerCase2 = element.getAttributeNS("http://www.w3.org/XML/1998/namespace", "lang").toLowerCase();
        return lowerCase2.equals(this.lang) || lowerCase2.startsWith(this.langHyphen);
    }
    
    public void fillAttributeSet(final Set set) {
        set.add("lang");
    }
    
    public String toString() {
        return ":lang(" + this.lang + ')';
    }
}
