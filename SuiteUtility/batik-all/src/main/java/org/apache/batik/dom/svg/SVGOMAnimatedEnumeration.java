// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Attr;
import org.apache.batik.anim.values.AnimatableStringValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;
import org.w3c.dom.Element;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGAnimatedEnumeration;

public class SVGOMAnimatedEnumeration extends AbstractSVGAnimatedValue implements SVGAnimatedEnumeration
{
    protected String[] values;
    protected short defaultValue;
    protected boolean valid;
    protected short baseVal;
    protected short animVal;
    protected boolean changing;
    
    public SVGOMAnimatedEnumeration(final AbstractElement abstractElement, final String s, final String s2, final String[] values, final short defaultValue) {
        super(abstractElement, s, s2);
        this.values = values;
        this.defaultValue = defaultValue;
    }
    
    public short getBaseVal() {
        if (!this.valid) {
            this.update();
        }
        return this.baseVal;
    }
    
    public String getBaseValAsString() {
        if (!this.valid) {
            this.update();
        }
        return this.values[this.baseVal];
    }
    
    protected void update() {
        final String attributeNS = this.element.getAttributeNS(this.namespaceURI, this.localName);
        if (attributeNS.length() == 0) {
            this.baseVal = this.defaultValue;
        }
        else {
            this.baseVal = this.getEnumerationNumber(attributeNS);
        }
        this.valid = true;
    }
    
    protected short getEnumerationNumber(final String s) {
        for (short n = 0; n < this.values.length; ++n) {
            if (s.equals(this.values[n])) {
                return n;
            }
        }
        return 0;
    }
    
    public void setBaseVal(final short baseVal) throws DOMException {
        if (baseVal >= 0 && baseVal < this.values.length) {
            try {
                this.baseVal = baseVal;
                this.valid = true;
                this.changing = true;
                this.element.setAttributeNS(this.namespaceURI, this.localName, this.values[baseVal]);
            }
            finally {
                this.changing = false;
            }
        }
    }
    
    public short getAnimVal() {
        if (this.hasAnimVal) {
            return this.animVal;
        }
        if (!this.valid) {
            this.update();
        }
        return this.baseVal;
    }
    
    public short getCheckedVal() {
        if (this.hasAnimVal) {
            return this.animVal;
        }
        if (!this.valid) {
            this.update();
        }
        if (this.baseVal == 0) {
            throw new LiveAttributeException(this.element, this.localName, (short)1, this.getBaseValAsString());
        }
        return this.baseVal;
    }
    
    public AnimatableValue getUnderlyingValue(final AnimationTarget animationTarget) {
        return new AnimatableStringValue(animationTarget, this.getBaseValAsString());
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
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            this.animVal = this.getEnumerationNumber(((AnimatableStringValue)animatableValue).getString());
            this.fireAnimatedAttributeListeners();
        }
        this.fireAnimatedAttributeListeners();
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
}
