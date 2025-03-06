// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.anim.values.AnimatableNumberValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;
import org.w3c.dom.DOMException;
import org.w3c.dom.Attr;
import org.w3c.dom.svg.SVGAnimatedNumber;

public class SVGOMAnimatedNumber extends AbstractSVGAnimatedValue implements SVGAnimatedNumber
{
    protected float defaultValue;
    protected boolean allowPercentage;
    protected boolean valid;
    protected float baseVal;
    protected float animVal;
    protected boolean changing;
    
    public SVGOMAnimatedNumber(final AbstractElement abstractElement, final String s, final String s2, final float n) {
        this(abstractElement, s, s2, n, false);
    }
    
    public SVGOMAnimatedNumber(final AbstractElement abstractElement, final String s, final String s2, final float defaultValue, final boolean allowPercentage) {
        super(abstractElement, s, s2);
        this.defaultValue = defaultValue;
        this.allowPercentage = allowPercentage;
    }
    
    public float getBaseVal() {
        if (!this.valid) {
            this.update();
        }
        return this.baseVal;
    }
    
    protected void update() {
        final Attr attributeNodeNS = this.element.getAttributeNodeNS(this.namespaceURI, this.localName);
        if (attributeNodeNS == null) {
            this.baseVal = this.defaultValue;
        }
        else {
            final String value = attributeNodeNS.getValue();
            final int length = value.length();
            if (this.allowPercentage && length > 1 && value.charAt(length - 1) == '%') {
                this.baseVal = 0.01f * Float.parseFloat(value.substring(0, length - 1));
            }
            else {
                this.baseVal = Float.parseFloat(value);
            }
        }
        this.valid = true;
    }
    
    public void setBaseVal(final float n) throws DOMException {
        try {
            this.baseVal = n;
            this.valid = true;
            this.changing = true;
            this.element.setAttributeNS(this.namespaceURI, this.localName, String.valueOf(n));
        }
        finally {
            this.changing = false;
        }
    }
    
    public float getAnimVal() {
        if (this.hasAnimVal) {
            return this.animVal;
        }
        if (!this.valid) {
            this.update();
        }
        return this.baseVal;
    }
    
    public AnimatableValue getUnderlyingValue(final AnimationTarget animationTarget) {
        return new AnimatableNumberValue(animationTarget, this.getBaseVal());
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            this.animVal = ((AnimatableNumberValue)animatableValue).getValue();
        }
        this.fireAnimatedAttributeListeners();
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
}
