// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.parser.NumberListHandler;
import org.apache.batik.parser.ParseException;
import org.w3c.dom.Element;
import org.apache.batik.parser.DefaultNumberListHandler;
import org.apache.batik.parser.NumberListParser;
import org.w3c.dom.DOMException;
import org.w3c.dom.Attr;
import org.apache.batik.dom.anim.AnimationTarget;
import org.apache.batik.anim.values.AnimatableRectValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGAnimatedRect;

public class SVGOMAnimatedRect extends AbstractSVGAnimatedValue implements SVGAnimatedRect
{
    protected BaseSVGRect baseVal;
    protected AnimSVGRect animVal;
    protected boolean changing;
    protected String defaultValue;
    
    public SVGOMAnimatedRect(final AbstractElement abstractElement, final String s, final String s2, final String defaultValue) {
        super(abstractElement, s, s2);
        this.defaultValue = defaultValue;
    }
    
    public SVGRect getBaseVal() {
        if (this.baseVal == null) {
            this.baseVal = new BaseSVGRect();
        }
        return (SVGRect)this.baseVal;
    }
    
    public SVGRect getAnimVal() {
        if (this.animVal == null) {
            this.animVal = new AnimSVGRect();
        }
        return (SVGRect)this.animVal;
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            final AnimatableRectValue animatableRectValue = (AnimatableRectValue)animatableValue;
            if (this.animVal == null) {
                this.animVal = new AnimSVGRect();
            }
            this.animVal.setAnimatedValue(animatableRectValue.getX(), animatableRectValue.getY(), animatableRectValue.getWidth(), animatableRectValue.getHeight());
        }
        this.fireAnimatedAttributeListeners();
    }
    
    public AnimatableValue getUnderlyingValue(final AnimationTarget animationTarget) {
        final SVGRect baseVal = this.getBaseVal();
        return new AnimatableRectValue(animationTarget, baseVal.getX(), baseVal.getY(), baseVal.getWidth(), baseVal.getHeight());
    }
    
    public void attrAdded(final Attr attr, final String s) {
        if (!this.changing && this.baseVal != null) {
            this.baseVal.invalidate();
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    public void attrModified(final Attr attr, final String s, final String s2) {
        if (!this.changing && this.baseVal != null) {
            this.baseVal.invalidate();
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    public void attrRemoved(final Attr attr, final String s) {
        if (!this.changing && this.baseVal != null) {
            this.baseVal.invalidate();
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    protected class AnimSVGRect extends SVGOMRect
    {
        public float getX() {
            if (SVGOMAnimatedRect.this.hasAnimVal) {
                return super.getX();
            }
            return SVGOMAnimatedRect.this.getBaseVal().getX();
        }
        
        public float getY() {
            if (SVGOMAnimatedRect.this.hasAnimVal) {
                return super.getY();
            }
            return SVGOMAnimatedRect.this.getBaseVal().getY();
        }
        
        public float getWidth() {
            if (SVGOMAnimatedRect.this.hasAnimVal) {
                return super.getWidth();
            }
            return SVGOMAnimatedRect.this.getBaseVal().getWidth();
        }
        
        public float getHeight() {
            if (SVGOMAnimatedRect.this.hasAnimVal) {
                return super.getHeight();
            }
            return SVGOMAnimatedRect.this.getBaseVal().getHeight();
        }
        
        public void setX(final float n) throws DOMException {
            throw SVGOMAnimatedRect.this.element.createDOMException((short)7, "readonly.length", null);
        }
        
        public void setY(final float n) throws DOMException {
            throw SVGOMAnimatedRect.this.element.createDOMException((short)7, "readonly.length", null);
        }
        
        public void setWidth(final float n) throws DOMException {
            throw SVGOMAnimatedRect.this.element.createDOMException((short)7, "readonly.length", null);
        }
        
        public void setHeight(final float n) throws DOMException {
            throw SVGOMAnimatedRect.this.element.createDOMException((short)7, "readonly.length", null);
        }
        
        protected void setAnimatedValue(final float x, final float y, final float w, final float h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }
    
    protected class BaseSVGRect extends SVGOMRect
    {
        protected boolean valid;
        private final /* synthetic */ SVGOMAnimatedRect this$0;
        
        public void invalidate() {
            this.valid = false;
        }
        
        protected void reset() {
            try {
                SVGOMAnimatedRect.this.changing = true;
                SVGOMAnimatedRect.this.element.setAttributeNS(SVGOMAnimatedRect.this.namespaceURI, SVGOMAnimatedRect.this.localName, Float.toString(this.x) + ' ' + this.y + ' ' + this.w + ' ' + this.h);
            }
            finally {
                SVGOMAnimatedRect.this.changing = false;
            }
        }
        
        protected void revalidate() {
            if (this.valid) {
                return;
            }
            final Attr attributeNodeNS = SVGOMAnimatedRect.this.element.getAttributeNodeNS(SVGOMAnimatedRect.this.namespaceURI, SVGOMAnimatedRect.this.localName);
            final String s = (attributeNodeNS == null) ? SVGOMAnimatedRect.this.defaultValue : attributeNodeNS.getValue();
            final float[] array = new float[4];
            final NumberListParser numberListParser = new NumberListParser();
            numberListParser.setNumberListHandler(new DefaultNumberListHandler() {
                protected int count;
                private final /* synthetic */ BaseSVGRect this$1 = this$1;
                
                public void endNumberList() {
                    if (this.count != 4) {
                        throw new LiveAttributeException(this.this$1.this$0.element, this.this$1.this$0.localName, (short)1, s);
                    }
                }
                
                public void numberValue(final float n) throws ParseException {
                    if (this.count < 4) {
                        array[this.count] = n;
                    }
                    if (n < 0.0f && (this.count == 2 || this.count == 3)) {
                        throw new LiveAttributeException(this.this$1.this$0.element, this.this$1.this$0.localName, (short)1, s);
                    }
                    ++this.count;
                }
            });
            numberListParser.parse(s);
            this.x = array[0];
            this.y = array[1];
            this.w = array[2];
            this.h = array[3];
            this.valid = true;
        }
        
        public float getX() {
            this.revalidate();
            return this.x;
        }
        
        public void setX(final float x) throws DOMException {
            this.x = x;
            this.reset();
        }
        
        public float getY() {
            this.revalidate();
            return this.y;
        }
        
        public void setY(final float y) throws DOMException {
            this.y = y;
            this.reset();
        }
        
        public float getWidth() {
            this.revalidate();
            return this.w;
        }
        
        public void setWidth(final float w) throws DOMException {
            this.w = w;
            this.reset();
        }
        
        public float getHeight() {
            this.revalidate();
            return this.h;
        }
        
        public void setHeight(final float h) throws DOMException {
            this.h = h;
            this.reset();
        }
    }
}
