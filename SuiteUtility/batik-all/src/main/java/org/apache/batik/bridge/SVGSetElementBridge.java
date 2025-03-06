// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.dom.anim.AnimatableElement;
import org.apache.batik.anim.SetAnimation;
import org.apache.batik.anim.AbstractAnimation;
import org.apache.batik.dom.anim.AnimationTarget;

public class SVGSetElementBridge extends SVGAnimationElementBridge
{
    public String getLocalName() {
        return "set";
    }
    
    public Bridge getInstance() {
        return new SVGSetElementBridge();
    }
    
    protected AbstractAnimation createAnimation(final AnimationTarget animationTarget) {
        return new SetAnimation(this.timedElement, this, this.parseAnimatableValue("to"));
    }
    
    protected boolean canAnimateType(final int n) {
        return true;
    }
    
    protected boolean isConstantAnimation() {
        return true;
    }
}
