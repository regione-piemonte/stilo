// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.DOMException;
import org.w3c.dom.Attr;
import org.apache.batik.anim.values.AnimatablePreserveAspectRatioValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGPreserveAspectRatio;
import org.w3c.dom.svg.SVGAnimatedPreserveAspectRatio;

public class SVGOMAnimatedPreserveAspectRatio extends AbstractSVGAnimatedValue implements SVGAnimatedPreserveAspectRatio
{
    protected BaseSVGPARValue baseVal;
    protected AnimSVGPARValue animVal;
    protected boolean changing;
    
    public SVGOMAnimatedPreserveAspectRatio(final AbstractElement abstractElement) {
        super(abstractElement, null, "preserveAspectRatio");
    }
    
    public SVGPreserveAspectRatio getBaseVal() {
        if (this.baseVal == null) {
            this.baseVal = new BaseSVGPARValue();
        }
        return (SVGPreserveAspectRatio)this.baseVal;
    }
    
    public SVGPreserveAspectRatio getAnimVal() {
        if (this.animVal == null) {
            this.animVal = new AnimSVGPARValue();
        }
        return (SVGPreserveAspectRatio)this.animVal;
    }
    
    public void check() {
        if (!this.hasAnimVal) {
            if (this.baseVal == null) {
                this.baseVal = new BaseSVGPARValue();
            }
            if (this.baseVal.malformed) {
                throw new LiveAttributeException(this.element, this.localName, (short)1, this.baseVal.getValueAsString());
            }
        }
    }
    
    public AnimatableValue getUnderlyingValue(final AnimationTarget animationTarget) {
        final SVGPreserveAspectRatio baseVal = this.getBaseVal();
        return new AnimatablePreserveAspectRatioValue(animationTarget, baseVal.getAlign(), baseVal.getMeetOrSlice());
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            if (this.animVal == null) {
                this.animVal = new AnimSVGPARValue();
            }
            final AnimatablePreserveAspectRatioValue animatablePreserveAspectRatioValue = (AnimatablePreserveAspectRatioValue)animatableValue;
            this.animVal.setAnimatedValue(animatablePreserveAspectRatioValue.getAlign(), animatablePreserveAspectRatioValue.getMeetOrSlice());
        }
        this.fireAnimatedAttributeListeners();
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
    
    public class AnimSVGPARValue extends AbstractSVGPreserveAspectRatio
    {
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedPreserveAspectRatio.this.element.createDOMException(n, s, array);
        }
        
        protected void setAttributeValue(final String s) throws DOMException {
        }
        
        public short getAlign() {
            if (SVGOMAnimatedPreserveAspectRatio.this.hasAnimVal) {
                return super.getAlign();
            }
            return SVGOMAnimatedPreserveAspectRatio.this.getBaseVal().getAlign();
        }
        
        public short getMeetOrSlice() {
            if (SVGOMAnimatedPreserveAspectRatio.this.hasAnimVal) {
                return super.getMeetOrSlice();
            }
            return SVGOMAnimatedPreserveAspectRatio.this.getBaseVal().getMeetOrSlice();
        }
        
        public void setAlign(final short n) {
            throw SVGOMAnimatedPreserveAspectRatio.this.element.createDOMException((short)7, "readonly.preserve.aspect.ratio", null);
        }
        
        public void setMeetOrSlice(final short n) {
            throw SVGOMAnimatedPreserveAspectRatio.this.element.createDOMException((short)7, "readonly.preserve.aspect.ratio", null);
        }
        
        protected void setAnimatedValue(final short align, final short meetOrSlice) {
            this.align = align;
            this.meetOrSlice = meetOrSlice;
        }
    }
    
    public class BaseSVGPARValue extends AbstractSVGPreserveAspectRatio
    {
        protected boolean malformed;
        
        public BaseSVGPARValue() {
            this.invalidate();
        }
        
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedPreserveAspectRatio.this.element.createDOMException(n, s, array);
        }
        
        protected void setAttributeValue(final String s) throws DOMException {
            try {
                SVGOMAnimatedPreserveAspectRatio.this.changing = true;
                SVGOMAnimatedPreserveAspectRatio.this.element.setAttributeNS(null, "preserveAspectRatio", s);
                this.malformed = false;
            }
            finally {
                SVGOMAnimatedPreserveAspectRatio.this.changing = false;
            }
        }
        
        protected void invalidate() {
            this.setValueAsString(SVGOMAnimatedPreserveAspectRatio.this.element.getAttributeNS(null, "preserveAspectRatio"));
        }
    }
}
