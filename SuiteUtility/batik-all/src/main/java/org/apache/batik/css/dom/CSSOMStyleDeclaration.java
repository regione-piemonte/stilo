// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.dom;

import java.util.HashMap;
import org.w3c.dom.css.CSSValue;
import org.apache.batik.css.engine.value.Value;
import org.w3c.dom.DOMException;
import java.util.Map;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;

public class CSSOMStyleDeclaration implements CSSStyleDeclaration
{
    protected ValueProvider valueProvider;
    protected ModificationHandler handler;
    protected CSSRule parentRule;
    protected Map values;
    
    public CSSOMStyleDeclaration(final ValueProvider valueProvider, final CSSRule parentRule) {
        this.valueProvider = valueProvider;
        this.parentRule = parentRule;
    }
    
    public void setModificationHandler(final ModificationHandler handler) {
        this.handler = handler;
    }
    
    public String getCssText() {
        return this.valueProvider.getText();
    }
    
    public void setCssText(final String s) throws DOMException {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        this.values = null;
        this.handler.textChanged(s);
    }
    
    public String getPropertyValue(final String s) {
        final Value value = this.valueProvider.getValue(s);
        if (value == null) {
            return "";
        }
        return value.getCssText();
    }
    
    public CSSValue getPropertyCSSValue(final String s) {
        if (this.valueProvider.getValue(s) == null) {
            return null;
        }
        return this.getCSSValue(s);
    }
    
    public String removeProperty(final String s) throws DOMException {
        final String propertyValue = this.getPropertyValue(s);
        if (propertyValue.length() > 0) {
            if (this.handler == null) {
                throw new DOMException((short)7, "");
            }
            if (this.values != null) {
                this.values.remove(s);
            }
            this.handler.propertyRemoved(s);
        }
        return propertyValue;
    }
    
    public String getPropertyPriority(final String s) {
        return this.valueProvider.isImportant(s) ? "important" : "";
    }
    
    public void setProperty(final String s, final String s2, final String s3) throws DOMException {
        if (this.handler == null) {
            throw new DOMException((short)7, "");
        }
        this.handler.propertyChanged(s, s2, s3);
    }
    
    public int getLength() {
        return this.valueProvider.getLength();
    }
    
    public String item(final int n) {
        return this.valueProvider.item(n);
    }
    
    public CSSRule getParentRule() {
        return this.parentRule;
    }
    
    protected CSSValue getCSSValue(final String s) {
        CSSValue cssValue = null;
        if (this.values != null) {
            cssValue = this.values.get(s);
        }
        if (cssValue == null) {
            cssValue = this.createCSSValue(s);
            if (this.values == null) {
                this.values = new HashMap(11);
            }
            this.values.put(s, cssValue);
        }
        return cssValue;
    }
    
    protected CSSValue createCSSValue(final String s) {
        return new StyleDeclarationValue(s);
    }
    
    public class StyleDeclarationValue extends CSSOMValue implements CSSOMValue.ValueProvider
    {
        protected String property;
        private final /* synthetic */ CSSOMStyleDeclaration this$0;
        
        public StyleDeclarationValue(final String property) {
            super(null);
            ((CSSOMValue)(this.valueProvider = this)).setModificationHandler(new AbstractModificationHandler() {
                private final /* synthetic */ StyleDeclarationValue this$1 = this$1;
                
                protected Value getValue() {
                    return this.this$1.getValue();
                }
                
                public void textChanged(final String s) throws DOMException {
                    if (this.this$1.this$0.values == null || this.this$1.this$0.values.get(this) == null || this.this$1.handler == null) {
                        throw new DOMException((short)7, "");
                    }
                    this.this$1.this$0.handler.propertyChanged(this.this$1.property, s, this.this$1.this$0.getPropertyPriority(this.this$1.property));
                }
            });
            this.property = property;
        }
        
        public Value getValue() {
            return CSSOMStyleDeclaration.this.valueProvider.getValue(this.property);
        }
    }
    
    public interface ValueProvider
    {
        Value getValue(final String p0);
        
        boolean isImportant(final String p0);
        
        String getText();
        
        int getLength();
        
        String item(final int p0);
    }
    
    public interface ModificationHandler
    {
        void textChanged(final String p0) throws DOMException;
        
        void propertyRemoved(final String p0) throws DOMException;
        
        void propertyChanged(final String p0, final String p1, final String p2) throws DOMException;
    }
}
