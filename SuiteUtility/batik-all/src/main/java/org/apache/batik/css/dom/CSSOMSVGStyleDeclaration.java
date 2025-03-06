// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.dom;

import org.w3c.dom.DOMException;
import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.svg.SVGColorManager;
import org.apache.batik.css.engine.value.svg.SVGPaintManager;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSRule;
import org.apache.batik.css.engine.CSSEngine;

public class CSSOMSVGStyleDeclaration extends CSSOMStyleDeclaration
{
    protected CSSEngine cssEngine;
    
    public CSSOMSVGStyleDeclaration(final ValueProvider valueProvider, final CSSRule cssRule, final CSSEngine cssEngine) {
        super(valueProvider, cssRule);
        this.cssEngine = cssEngine;
    }
    
    protected CSSValue createCSSValue(final String s) {
        final int propertyIndex = this.cssEngine.getPropertyIndex(s);
        if (propertyIndex > 59) {
            if (this.cssEngine.getValueManagers()[propertyIndex] instanceof SVGPaintManager) {
                return (CSSValue)new StyleDeclarationPaintValue(s);
            }
            if (this.cssEngine.getValueManagers()[propertyIndex] instanceof SVGColorManager) {
                return (CSSValue)new StyleDeclarationColorValue(s);
            }
        }
        else {
            switch (propertyIndex) {
                case 15:
                case 45: {
                    return (CSSValue)new StyleDeclarationPaintValue(s);
                }
                case 19:
                case 33:
                case 43: {
                    return (CSSValue)new StyleDeclarationColorValue(s);
                }
            }
        }
        return super.createCSSValue(s);
    }
    
    public class StyleDeclarationPaintValue extends CSSOMSVGPaint implements CSSOMSVGColor.ValueProvider
    {
        protected String property;
        private final /* synthetic */ CSSOMSVGStyleDeclaration this$0;
        
        public StyleDeclarationPaintValue(final String property) {
            super(null);
            ((CSSOMSVGPaint)(this.valueProvider = this)).setModificationHandler(new CSSOMSVGPaint.AbstractModificationHandler() {
                private final /* synthetic */ StyleDeclarationPaintValue this$1 = this$1;
                
                protected Value getValue() {
                    return this.this$1.getValue();
                }
                
                public void textChanged(final String s) throws DOMException {
                    if (this.this$1.handler == null) {
                        throw new DOMException((short)7, "");
                    }
                    this.this$1.this$0.handler.propertyChanged(this.this$1.property, s, this.this$1.this$0.getPropertyPriority(this.this$1.property));
                }
            });
            this.property = property;
        }
        
        public Value getValue() {
            return CSSOMSVGStyleDeclaration.this.valueProvider.getValue(this.property);
        }
    }
    
    public class StyleDeclarationColorValue extends CSSOMSVGColor implements CSSOMSVGColor.ValueProvider
    {
        protected String property;
        private final /* synthetic */ CSSOMSVGStyleDeclaration this$0;
        
        public StyleDeclarationColorValue(final String property) {
            super(null);
            ((CSSOMSVGColor)(this.valueProvider = this)).setModificationHandler(new AbstractModificationHandler() {
                private final /* synthetic */ StyleDeclarationColorValue this$1 = this$1;
                
                protected Value getValue() {
                    return this.this$1.getValue();
                }
                
                public void textChanged(final String s) throws DOMException {
                    if (this.this$1.handler == null) {
                        throw new DOMException((short)7, "");
                    }
                    this.this$1.this$0.handler.propertyChanged(this.this$1.property, s, this.this$1.this$0.getPropertyPriority(this.this$1.property));
                }
            });
            this.property = property;
        }
        
        public Value getValue() {
            return CSSOMSVGStyleDeclaration.this.valueProvider.getValue(this.property);
        }
    }
}
