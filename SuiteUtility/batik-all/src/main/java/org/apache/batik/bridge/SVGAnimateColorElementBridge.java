// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.anim.values.AnimatableColorValue;
import org.apache.batik.anim.values.AnimatablePaintValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimatableElement;
import org.apache.batik.anim.ColorAnimation;
import org.apache.batik.anim.AbstractAnimation;
import org.apache.batik.dom.anim.AnimationTarget;

public class SVGAnimateColorElementBridge extends SVGAnimateElementBridge
{
    public String getLocalName() {
        return "animateColor";
    }
    
    public Bridge getInstance() {
        return new SVGAnimateColorElementBridge();
    }
    
    protected AbstractAnimation createAnimation(final AnimationTarget animationTarget) {
        return new ColorAnimation(this.timedElement, this, this.parseCalcMode(), this.parseKeyTimes(), this.parseKeySplines(), this.parseAdditive(), this.parseAccumulate(), this.parseValues(), this.parseAnimatableValue("from"), this.parseAnimatableValue("to"), this.parseAnimatableValue("by"));
    }
    
    protected boolean canAnimateType(final int n) {
        return n == 6 || n == 7;
    }
    
    protected boolean checkValueType(final AnimatableValue animatableValue) {
        if (animatableValue instanceof AnimatablePaintValue) {
            return ((AnimatablePaintValue)animatableValue).getPaintType() == 2;
        }
        return animatableValue instanceof AnimatableColorValue;
    }
}
