// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Attr;
import org.apache.batik.anim.values.AnimatableStringValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGAnimatedString;

public class SVGOMAnimatedString extends AbstractSVGAnimatedValue implements SVGAnimatedString
{
    protected String animVal;
    
    public SVGOMAnimatedString(final AbstractElement abstractElement, final String s, final String s2) {
        super(abstractElement, s, s2);
    }
    
    public String getBaseVal() {
        return this.element.getAttributeNS(this.namespaceURI, this.localName);
    }
    
    public void setBaseVal(final String s) throws DOMException {
        this.element.setAttributeNS(this.namespaceURI, this.localName, s);
    }
    
    public String getAnimVal() {
        if (this.hasAnimVal) {
            return this.animVal;
        }
        return this.element.getAttributeNS(this.namespaceURI, this.localName);
    }
    
    public AnimatableValue getUnderlyingValue(final AnimationTarget animationTarget) {
        return new AnimatableStringValue(animationTarget, this.getBaseVal());
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            this.animVal = ((AnimatableStringValue)animatableValue).getString();
        }
        this.fireAnimatedAttributeListeners();
    }
    
    public void attrAdded(final Attr attr, final String s) {
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    public void attrModified(final Attr attr, final String s, final String s2) {
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    public void attrRemoved(final Attr attr, final String s) {
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
}
