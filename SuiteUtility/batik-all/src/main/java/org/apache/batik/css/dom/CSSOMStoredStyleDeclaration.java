// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.dom;

import org.apache.batik.css.engine.value.Value;
import org.w3c.dom.css.CSSRule;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.StyleDeclaration;
import org.apache.batik.css.engine.StyleDeclarationProvider;

public abstract class CSSOMStoredStyleDeclaration extends CSSOMSVGStyleDeclaration implements ValueProvider, ModificationHandler, StyleDeclarationProvider
{
    protected StyleDeclaration declaration;
    
    public CSSOMStoredStyleDeclaration(final CSSEngine cssEngine) {
        super(null, null, cssEngine);
        ((CSSOMStyleDeclaration)(this.valueProvider = this)).setModificationHandler(this);
    }
    
    public StyleDeclaration getStyleDeclaration() {
        return this.declaration;
    }
    
    public void setStyleDeclaration(final StyleDeclaration declaration) {
        this.declaration = declaration;
    }
    
    public Value getValue(final String s) {
        final int propertyIndex = this.cssEngine.getPropertyIndex(s);
        for (int i = 0; i < this.declaration.size(); ++i) {
            if (propertyIndex == this.declaration.getIndex(i)) {
                return this.declaration.getValue(i);
            }
        }
        return null;
    }
    
    public boolean isImportant(final String s) {
        final int propertyIndex = this.cssEngine.getPropertyIndex(s);
        for (int i = 0; i < this.declaration.size(); ++i) {
            if (propertyIndex == this.declaration.getIndex(i)) {
                return this.declaration.getPriority(i);
            }
        }
        return false;
    }
    
    public String getText() {
        return this.declaration.toString(this.cssEngine);
    }
    
    public int getLength() {
        return this.declaration.size();
    }
    
    public String item(final int n) {
        return this.cssEngine.getPropertyName(this.declaration.getIndex(n));
    }
}
