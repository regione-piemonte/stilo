// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.anim.values.AnimatableIntegerValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;
import org.w3c.dom.DOMException;
import org.w3c.dom.Attr;
import org.w3c.dom.svg.SVGAnimatedInteger;

public class SVGOMAnimatedInteger extends AbstractSVGAnimatedValue implements SVGAnimatedInteger
{
    protected int defaultValue;
    protected boolean valid;
    protected int baseVal;
    protected int animVal;
    protected boolean changing;
    
    public SVGOMAnimatedInteger(final AbstractElement abstractElement, final String s, final String s2, final int defaultValue) {
        super(abstractElement, s, s2);
        this.defaultValue = defaultValue;
    }
    
    public int getBaseVal() {
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
            this.baseVal = Integer.parseInt(attributeNodeNS.getValue());
        }
        this.valid = true;
    }
    
    public void setBaseVal(final int n) throws DOMException {
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
    
    public int getAnimVal() {
        if (this.hasAnimVal) {
            return this.animVal;
        }
        if (!this.valid) {
            this.update();
        }
        return this.baseVal;
    }
    
    public AnimatableValue getUnderlyingValue(final AnimationTarget animationTarget) {
        return new AnimatableIntegerValue(animationTarget, this.getBaseVal());
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            this.animVal = ((AnimatableIntegerValue)animatableValue).getValue();
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
