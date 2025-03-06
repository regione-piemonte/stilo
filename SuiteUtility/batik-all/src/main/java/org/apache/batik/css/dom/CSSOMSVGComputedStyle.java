// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.dom;

import org.apache.batik.css.engine.value.Value;
import org.apache.batik.css.engine.value.svg.SVGColorManager;
import org.apache.batik.css.engine.value.svg.SVGPaintManager;
import org.w3c.dom.css.CSSValue;
import org.apache.batik.css.engine.CSSStylableElement;
import org.apache.batik.css.engine.CSSEngine;

public class CSSOMSVGComputedStyle extends CSSOMComputedStyle
{
    public CSSOMSVGComputedStyle(final CSSEngine cssEngine, final CSSStylableElement cssStylableElement, final String s) {
        super(cssEngine, cssStylableElement, s);
    }
    
    protected CSSValue createCSSValue(final int n) {
        if (n > 59) {
            if (this.cssEngine.getValueManagers()[n] instanceof SVGPaintManager) {
                return (CSSValue)new ComputedCSSPaintValue(n);
            }
            if (this.cssEngine.getValueManagers()[n] instanceof SVGColorManager) {
                return (CSSValue)new ComputedCSSColorValue(n);
            }
        }
        else {
            switch (n) {
                case 15:
                case 45: {
                    return (CSSValue)new ComputedCSSPaintValue(n);
                }
                case 19:
                case 33:
                case 43: {
                    return (CSSValue)new ComputedCSSColorValue(n);
                }
            }
        }
        return super.createCSSValue(n);
    }
    
    public class ComputedCSSPaintValue extends CSSOMSVGPaint implements ValueProvider
    {
        protected int index;
        
        public ComputedCSSPaintValue(final int index) {
            super(null);
            this.valueProvider = this;
            this.index = index;
        }
        
        public Value getValue() {
            return CSSOMSVGComputedStyle.this.cssEngine.getComputedStyle(CSSOMSVGComputedStyle.this.element, CSSOMSVGComputedStyle.this.pseudoElement, this.index);
        }
    }
    
    protected class ComputedCSSColorValue extends CSSOMSVGColor implements ValueProvider
    {
        protected int index;
        
        public ComputedCSSColorValue(final int index) {
            super(null);
            this.valueProvider = this;
            this.index = index;
        }
        
        public Value getValue() {
            return CSSOMSVGComputedStyle.this.cssEngine.getComputedStyle(CSSOMSVGComputedStyle.this.element, CSSOMSVGComputedStyle.this.pseudoElement, this.index);
        }
    }
}
