// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGAngle;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedAngle;
import org.w3c.dom.Attr;
import org.apache.batik.dom.anim.AnimationTarget;
import org.apache.batik.anim.values.AnimatableValue;

public class SVGOMAnimatedMarkerOrientValue extends AbstractSVGAnimatedValue
{
    protected boolean valid;
    protected AnimatedAngle animatedAngle;
    protected AnimatedEnumeration animatedEnumeration;
    protected BaseSVGAngle baseAngleVal;
    protected short baseEnumerationVal;
    protected AnimSVGAngle animAngleVal;
    protected short animEnumerationVal;
    protected boolean changing;
    
    public SVGOMAnimatedMarkerOrientValue(final AbstractElement abstractElement, final String s, final String s2) {
        super(abstractElement, s, s2);
        this.animatedAngle = new AnimatedAngle();
        this.animatedEnumeration = new AnimatedEnumeration();
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        throw new UnsupportedOperationException("Animation of marker orient value is not implemented");
    }
    
    public AnimatableValue getUnderlyingValue(final AnimationTarget animationTarget) {
        throw new UnsupportedOperationException("Animation of marker orient value is not implemented");
    }
    
    public void attrAdded(final Attr attr, final String s) {
        if (!this.changing) {
            this.valid = false;
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    public void attrModified(final Attr attr, final String s, final String s2) {
        if (!this.changing) {
            this.valid = false;
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    public void attrRemoved(final Attr attr, final String s) {
        if (!this.changing) {
            this.valid = false;
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    public void setAnimatedValueToAngle(final short n, final float n2) {
        this.hasAnimVal = true;
        this.animAngleVal.setAnimatedValue(n, n2);
        this.animEnumerationVal = 2;
        this.fireAnimatedAttributeListeners();
    }
    
    public void setAnimatedValueToAuto() {
        this.hasAnimVal = true;
        this.animAngleVal.setAnimatedValue(1, 0.0f);
        this.animEnumerationVal = 1;
        this.fireAnimatedAttributeListeners();
    }
    
    public void resetAnimatedValue() {
        this.hasAnimVal = false;
        this.fireAnimatedAttributeListeners();
    }
    
    public SVGAnimatedAngle getAnimatedAngle() {
        return (SVGAnimatedAngle)this.animatedAngle;
    }
    
    public SVGAnimatedEnumeration getAnimatedEnumeration() {
        return (SVGAnimatedEnumeration)this.animatedEnumeration;
    }
    
    protected class AnimatedEnumeration implements SVGAnimatedEnumeration
    {
        public short getBaseVal() {
            if (SVGOMAnimatedMarkerOrientValue.this.baseAngleVal == null) {
                SVGOMAnimatedMarkerOrientValue.this.baseAngleVal = new BaseSVGAngle();
            }
            SVGOMAnimatedMarkerOrientValue.this.baseAngleVal.revalidate();
            return SVGOMAnimatedMarkerOrientValue.this.baseEnumerationVal;
        }
        
        public void setBaseVal(final short n) throws DOMException {
            if (n == 1) {
                SVGOMAnimatedMarkerOrientValue.this.baseEnumerationVal = n;
                if (SVGOMAnimatedMarkerOrientValue.this.baseAngleVal == null) {
                    SVGOMAnimatedMarkerOrientValue.this.baseAngleVal = new BaseSVGAngle();
                }
                SVGOMAnimatedMarkerOrientValue.this.baseAngleVal.unitType = 1;
                SVGOMAnimatedMarkerOrientValue.this.baseAngleVal.value = 0.0f;
                SVGOMAnimatedMarkerOrientValue.this.baseAngleVal.reset();
            }
            else if (n == 2) {
                SVGOMAnimatedMarkerOrientValue.this.baseEnumerationVal = n;
                if (SVGOMAnimatedMarkerOrientValue.this.baseAngleVal == null) {
                    SVGOMAnimatedMarkerOrientValue.this.baseAngleVal = new BaseSVGAngle();
                }
                SVGOMAnimatedMarkerOrientValue.this.baseAngleVal.reset();
            }
        }
        
        public short getAnimVal() {
            if (SVGOMAnimatedMarkerOrientValue.this.hasAnimVal) {
                return SVGOMAnimatedMarkerOrientValue.this.animEnumerationVal;
            }
            if (SVGOMAnimatedMarkerOrientValue.this.baseAngleVal == null) {
                SVGOMAnimatedMarkerOrientValue.this.baseAngleVal = new BaseSVGAngle();
            }
            SVGOMAnimatedMarkerOrientValue.this.baseAngleVal.revalidate();
            return SVGOMAnimatedMarkerOrientValue.this.baseEnumerationVal;
        }
    }
    
    protected class BaseSVGAngle extends SVGOMAngle
    {
        public void invalidate() {
            SVGOMAnimatedMarkerOrientValue.this.valid = false;
        }
        
        protected void reset() {
            try {
                SVGOMAnimatedMarkerOrientValue.this.changing = true;
                SVGOMAnimatedMarkerOrientValue.this.valid = true;
                String valueAsString;
                if (SVGOMAnimatedMarkerOrientValue.this.baseEnumerationVal == 2) {
                    valueAsString = this.getValueAsString();
                }
                else {
                    if (SVGOMAnimatedMarkerOrientValue.this.baseEnumerationVal != 1) {
                        return;
                    }
                    valueAsString = "auto";
                }
                SVGOMAnimatedMarkerOrientValue.this.element.setAttributeNS(SVGOMAnimatedMarkerOrientValue.this.namespaceURI, SVGOMAnimatedMarkerOrientValue.this.localName, valueAsString);
            }
            finally {
                SVGOMAnimatedMarkerOrientValue.this.changing = false;
            }
        }
        
        protected void revalidate() {
            if (!SVGOMAnimatedMarkerOrientValue.this.valid) {
                final Attr attributeNodeNS = SVGOMAnimatedMarkerOrientValue.this.element.getAttributeNodeNS(SVGOMAnimatedMarkerOrientValue.this.namespaceURI, SVGOMAnimatedMarkerOrientValue.this.localName);
                if (attributeNodeNS == null) {
                    this.unitType = 1;
                    this.value = 0.0f;
                }
                else {
                    this.parse(attributeNodeNS.getValue());
                }
                SVGOMAnimatedMarkerOrientValue.this.valid = true;
            }
        }
        
        protected void parse(final String s) {
            if (s.equals("auto")) {
                this.unitType = 1;
                this.value = 0.0f;
                SVGOMAnimatedMarkerOrientValue.this.baseEnumerationVal = 1;
            }
            else {
                super.parse(s);
                if (this.unitType == 0) {
                    SVGOMAnimatedMarkerOrientValue.this.baseEnumerationVal = 0;
                }
                else {
                    SVGOMAnimatedMarkerOrientValue.this.baseEnumerationVal = 2;
                }
            }
        }
    }
    
    protected class AnimatedAngle implements SVGAnimatedAngle
    {
        public SVGAngle getBaseVal() {
            if (SVGOMAnimatedMarkerOrientValue.this.baseAngleVal == null) {
                SVGOMAnimatedMarkerOrientValue.this.baseAngleVal = new BaseSVGAngle();
            }
            return (SVGAngle)SVGOMAnimatedMarkerOrientValue.this.baseAngleVal;
        }
        
        public SVGAngle getAnimVal() {
            if (SVGOMAnimatedMarkerOrientValue.this.animAngleVal == null) {
                SVGOMAnimatedMarkerOrientValue.this.animAngleVal = new AnimSVGAngle();
            }
            return (SVGAngle)SVGOMAnimatedMarkerOrientValue.this.animAngleVal;
        }
    }
    
    protected class AnimSVGAngle extends SVGOMAngle
    {
        public short getUnitType() {
            if (SVGOMAnimatedMarkerOrientValue.this.hasAnimVal) {
                return super.getUnitType();
            }
            return SVGOMAnimatedMarkerOrientValue.this.animatedAngle.getBaseVal().getUnitType();
        }
        
        public float getValue() {
            if (SVGOMAnimatedMarkerOrientValue.this.hasAnimVal) {
                return super.getValue();
            }
            return SVGOMAnimatedMarkerOrientValue.this.animatedAngle.getBaseVal().getValue();
        }
        
        public float getValueInSpecifiedUnits() {
            if (SVGOMAnimatedMarkerOrientValue.this.hasAnimVal) {
                return super.getValueInSpecifiedUnits();
            }
            return SVGOMAnimatedMarkerOrientValue.this.animatedAngle.getBaseVal().getValueInSpecifiedUnits();
        }
        
        public String getValueAsString() {
            if (SVGOMAnimatedMarkerOrientValue.this.hasAnimVal) {
                return super.getValueAsString();
            }
            return SVGOMAnimatedMarkerOrientValue.this.animatedAngle.getBaseVal().getValueAsString();
        }
        
        public void setValue(final float n) throws DOMException {
            throw SVGOMAnimatedMarkerOrientValue.this.element.createDOMException((short)7, "readonly.angle", null);
        }
        
        public void setValueInSpecifiedUnits(final float n) throws DOMException {
            throw SVGOMAnimatedMarkerOrientValue.this.element.createDOMException((short)7, "readonly.angle", null);
        }
        
        public void setValueAsString(final String s) throws DOMException {
            throw SVGOMAnimatedMarkerOrientValue.this.element.createDOMException((short)7, "readonly.angle", null);
        }
        
        public void newValueSpecifiedUnits(final short n, final float n2) {
            throw SVGOMAnimatedMarkerOrientValue.this.element.createDOMException((short)7, "readonly.angle", null);
        }
        
        public void convertToSpecifiedUnits(final short n) {
            throw SVGOMAnimatedMarkerOrientValue.this.element.createDOMException((short)7, "readonly.angle", null);
        }
        
        protected void setAnimatedValue(final int n, final float n2) {
            super.newValueSpecifiedUnits((short)n, n2);
        }
    }
}
