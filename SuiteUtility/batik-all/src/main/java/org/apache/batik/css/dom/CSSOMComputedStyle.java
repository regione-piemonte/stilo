// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.dom;

import org.apache.batik.css.engine.value.Value;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.DOMException;
import java.util.HashMap;
import java.util.Map;
import org.apache.batik.css.engine.CSSStylableElement;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.dom.css.CSSStyleDeclaration;

public class CSSOMComputedStyle implements CSSStyleDeclaration
{
    protected CSSEngine cssEngine;
    protected CSSStylableElement element;
    protected String pseudoElement;
    protected Map values;
    
    public CSSOMComputedStyle(final CSSEngine cssEngine, final CSSStylableElement element, final String pseudoElement) {
        this.values = new HashMap();
        this.cssEngine = cssEngine;
        this.element = element;
        this.pseudoElement = pseudoElement;
    }
    
    public String getCssText() {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.cssEngine.getNumberOfProperties(); ++i) {
            sb.append(this.cssEngine.getPropertyName(i));
            sb.append(": ");
            sb.append(this.cssEngine.getComputedStyle(this.element, this.pseudoElement, i).getCssText());
            sb.append(";\n");
        }
        return sb.toString();
    }
    
    public void setCssText(final String s) throws DOMException {
        throw new DOMException((short)7, "");
    }
    
    public String getPropertyValue(final String s) {
        final int propertyIndex = this.cssEngine.getPropertyIndex(s);
        if (propertyIndex == -1) {
            return "";
        }
        return this.cssEngine.getComputedStyle(this.element, this.pseudoElement, propertyIndex).getCssText();
    }
    
    public CSSValue getPropertyCSSValue(final String s) {
        CSSValue cssValue = this.values.get(s);
        if (cssValue == null) {
            final int propertyIndex = this.cssEngine.getPropertyIndex(s);
            if (propertyIndex != -1) {
                cssValue = this.createCSSValue(propertyIndex);
                this.values.put(s, cssValue);
            }
        }
        return cssValue;
    }
    
    public String removeProperty(final String s) throws DOMException {
        throw new DOMException((short)7, "");
    }
    
    public String getPropertyPriority(final String s) {
        return "";
    }
    
    public void setProperty(final String s, final String s2, final String s3) throws DOMException {
        throw new DOMException((short)7, "");
    }
    
    public int getLength() {
        return this.cssEngine.getNumberOfProperties();
    }
    
    public String item(final int n) {
        if (n < 0 || n >= this.cssEngine.getNumberOfProperties()) {
            return "";
        }
        return this.cssEngine.getPropertyName(n);
    }
    
    public CSSRule getParentRule() {
        return null;
    }
    
    protected CSSValue createCSSValue(final int n) {
        return new ComputedCSSValue(n);
    }
    
    public class ComputedCSSValue extends CSSOMValue implements ValueProvider
    {
        protected int index;
        
        public ComputedCSSValue(final int index) {
            super(null);
            this.valueProvider = this;
            this.index = index;
        }
        
        public Value getValue() {
            return CSSOMComputedStyle.this.cssEngine.getComputedStyle(CSSOMComputedStyle.this.element, CSSOMComputedStyle.this.pseudoElement, this.index);
        }
    }
}
