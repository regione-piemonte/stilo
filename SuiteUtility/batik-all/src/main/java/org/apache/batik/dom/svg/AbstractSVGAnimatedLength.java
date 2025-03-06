// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.DOMException;
import org.w3c.dom.Attr;
import org.apache.batik.dom.anim.AnimationTarget;
import org.apache.batik.anim.values.AnimatableLengthValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGLength;
import org.w3c.dom.svg.SVGAnimatedLength;

public abstract class AbstractSVGAnimatedLength extends AbstractSVGAnimatedValue implements SVGAnimatedLength, LiveAttributeValue
{
    public static final short HORIZONTAL_LENGTH = 2;
    public static final short VERTICAL_LENGTH = 1;
    public static final short OTHER_LENGTH = 0;
    protected short direction;
    protected BaseSVGLength baseVal;
    protected AnimSVGLength animVal;
    protected boolean changing;
    protected boolean nonNegative;
    
    public AbstractSVGAnimatedLength(final AbstractElement abstractElement, final String s, final String s2, final short direction, final boolean nonNegative) {
        super(abstractElement, s, s2);
        this.direction = direction;
        this.nonNegative = nonNegative;
    }
    
    protected abstract String getDefaultValue();
    
    public SVGLength getBaseVal() {
        if (this.baseVal == null) {
            this.baseVal = new BaseSVGLength(this.direction);
        }
        return (SVGLength)this.baseVal;
    }
    
    public SVGLength getAnimVal() {
        if (this.animVal == null) {
            this.animVal = new AnimSVGLength(this.direction);
        }
        return (SVGLength)this.animVal;
    }
    
    public float getCheckedValue() {
        if (this.hasAnimVal) {
            if (this.animVal == null) {
                this.animVal = new AnimSVGLength(this.direction);
            }
            if (this.nonNegative && this.animVal.value < 0.0f) {
                throw new LiveAttributeException(this.element, this.localName, (short)2, this.animVal.getValueAsString());
            }
            return this.animVal.getValue();
        }
        else {
            if (this.baseVal == null) {
                this.baseVal = new BaseSVGLength(this.direction);
            }
            this.baseVal.revalidate();
            if (this.baseVal.missing) {
                throw new LiveAttributeException(this.element, this.localName, (short)0, null);
            }
            if (this.baseVal.unitType == 0) {
                throw new LiveAttributeException(this.element, this.localName, (short)1, this.baseVal.getValueAsString());
            }
            if (this.nonNegative && this.baseVal.value < 0.0f) {
                throw new LiveAttributeException(this.element, this.localName, (short)2, this.baseVal.getValueAsString());
            }
            return this.baseVal.getValue();
        }
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            final AnimatableLengthValue animatableLengthValue = (AnimatableLengthValue)animatableValue;
            if (this.animVal == null) {
                this.animVal = new AnimSVGLength(this.direction);
            }
            this.animVal.setAnimatedValue(animatableLengthValue.getLengthType(), animatableLengthValue.getLengthValue());
        }
        this.fireAnimatedAttributeListeners();
    }
    
    public AnimatableValue getUnderlyingValue(final AnimationTarget animationTarget) {
        final SVGLength baseVal = this.getBaseVal();
        return new AnimatableLengthValue(animationTarget, baseVal.getUnitType(), baseVal.getValueInSpecifiedUnits(), animationTarget.getPercentageInterpretation(this.getNamespaceURI(), this.getLocalName(), false));
    }
    
    public void attrAdded(final Attr attr, final String s) {
        this.attrChanged();
    }
    
    public void attrModified(final Attr attr, final String s, final String s2) {
        this.attrChanged();
    }
    
    public void attrRemoved(final Attr attr, final String s) {
        this.attrChanged();
    }
    
    protected void attrChanged() {
        if (!this.changing && this.baseVal != null) {
            this.baseVal.invalidate();
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    protected class AnimSVGLength extends AbstractSVGLength
    {
        public AnimSVGLength(final short n) {
            super(n);
        }
        
        public short getUnitType() {
            if (AbstractSVGAnimatedLength.this.hasAnimVal) {
                return super.getUnitType();
            }
            return AbstractSVGAnimatedLength.this.getBaseVal().getUnitType();
        }
        
        public float getValue() {
            if (AbstractSVGAnimatedLength.this.hasAnimVal) {
                return super.getValue();
            }
            return AbstractSVGAnimatedLength.this.getBaseVal().getValue();
        }
        
        public float getValueInSpecifiedUnits() {
            if (AbstractSVGAnimatedLength.this.hasAnimVal) {
                return super.getValueInSpecifiedUnits();
            }
            return AbstractSVGAnimatedLength.this.getBaseVal().getValueInSpecifiedUnits();
        }
        
        public String getValueAsString() {
            if (AbstractSVGAnimatedLength.this.hasAnimVal) {
                return super.getValueAsString();
            }
            return AbstractSVGAnimatedLength.this.getBaseVal().getValueAsString();
        }
        
        public void setValue(final float n) throws DOMException {
            throw AbstractSVGAnimatedLength.this.element.createDOMException((short)7, "readonly.length", null);
        }
        
        public void setValueInSpecifiedUnits(final float n) throws DOMException {
            throw AbstractSVGAnimatedLength.this.element.createDOMException((short)7, "readonly.length", null);
        }
        
        public void setValueAsString(final String s) throws DOMException {
            throw AbstractSVGAnimatedLength.this.element.createDOMException((short)7, "readonly.length", null);
        }
        
        public void newValueSpecifiedUnits(final short n, final float n2) {
            throw AbstractSVGAnimatedLength.this.element.createDOMException((short)7, "readonly.length", null);
        }
        
        public void convertToSpecifiedUnits(final short n) {
            throw AbstractSVGAnimatedLength.this.element.createDOMException((short)7, "readonly.length", null);
        }
        
        protected SVGOMElement getAssociatedElement() {
            return (SVGOMElement)AbstractSVGAnimatedLength.this.element;
        }
        
        protected void setAnimatedValue(final int n, final float n2) {
            super.newValueSpecifiedUnits((short)n, n2);
        }
    }
    
    protected class BaseSVGLength extends AbstractSVGLength
    {
        protected boolean valid;
        protected boolean missing;
        
        public BaseSVGLength(final short n) {
            super(n);
        }
        
        public void invalidate() {
            this.valid = false;
        }
        
        protected void reset() {
            try {
                AbstractSVGAnimatedLength.this.changing = true;
                this.valid = true;
                AbstractSVGAnimatedLength.this.element.setAttributeNS(AbstractSVGAnimatedLength.this.namespaceURI, AbstractSVGAnimatedLength.this.localName, this.getValueAsString());
            }
            finally {
                AbstractSVGAnimatedLength.this.changing = false;
            }
        }
        
        protected void revalidate() {
            if (this.valid) {
                return;
            }
            this.missing = false;
            this.valid = true;
            final Attr attributeNodeNS = AbstractSVGAnimatedLength.this.element.getAttributeNodeNS(AbstractSVGAnimatedLength.this.namespaceURI, AbstractSVGAnimatedLength.this.localName);
            String s;
            if (attributeNodeNS == null) {
                s = AbstractSVGAnimatedLength.this.getDefaultValue();
                if (s == null) {
                    this.missing = true;
                    return;
                }
            }
            else {
                s = attributeNodeNS.getValue();
            }
            this.parse(s);
        }
        
        protected SVGOMElement getAssociatedElement() {
            return (SVGOMElement)AbstractSVGAnimatedLength.this.element;
        }
    }
}
