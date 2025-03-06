// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.dom.anim.AnimationTarget;
import org.apache.batik.anim.values.AnimatableBooleanValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.w3c.dom.DOMException;
import org.w3c.dom.Attr;
import org.w3c.dom.svg.SVGAnimatedBoolean;

public class SVGOMAnimatedBoolean extends AbstractSVGAnimatedValue implements SVGAnimatedBoolean
{
    protected boolean defaultValue;
    protected boolean valid;
    protected boolean baseVal;
    protected boolean animVal;
    protected boolean changing;
    
    public SVGOMAnimatedBoolean(final AbstractElement abstractElement, final String s, final String s2, final boolean defaultValue) {
        super(abstractElement, s, s2);
        this.defaultValue = defaultValue;
    }
    
    public boolean getBaseVal() {
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
            this.baseVal = attributeNodeNS.getValue().equals("true");
        }
        this.valid = true;
    }
    
    public void setBaseVal(final boolean b) throws DOMException {
        try {
            this.baseVal = b;
            this.valid = true;
            this.changing = true;
            this.element.setAttributeNS(this.namespaceURI, this.localName, String.valueOf(b));
        }
        finally {
            this.changing = false;
        }
    }
    
    public boolean getAnimVal() {
        if (this.hasAnimVal) {
            return this.animVal;
        }
        if (!this.valid) {
            this.update();
        }
        return this.baseVal;
    }
    
    public void setAnimatedValue(final boolean animVal) {
        this.hasAnimVal = true;
        this.animVal = animVal;
        this.fireAnimatedAttributeListeners();
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            this.animVal = ((AnimatableBooleanValue)animatableValue).getValue();
        }
        this.fireAnimatedAttributeListeners();
    }
    
    public AnimatableValue getUnderlyingValue(final AnimationTarget animationTarget) {
        return new AnimatableBooleanValue(animationTarget, this.getBaseVal());
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
