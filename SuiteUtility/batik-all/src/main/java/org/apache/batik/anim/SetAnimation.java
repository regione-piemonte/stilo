// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim;

import org.apache.batik.dom.anim.AnimatableElement;
import org.apache.batik.anim.timing.TimedElement;
import org.apache.batik.anim.values.AnimatableValue;

public class SetAnimation extends AbstractAnimation
{
    protected AnimatableValue to;
    
    public SetAnimation(final TimedElement timedElement, final AnimatableElement animatableElement, final AnimatableValue to) {
        super(timedElement, animatableElement);
        this.to = to;
    }
    
    protected void sampledAt(final float n, final float n2, final int n3) {
        if (this.value == null) {
            this.value = this.to;
            this.markDirty();
        }
    }
    
    protected void sampledLastValue(final int n) {
        if (this.value == null) {
            this.value = this.to;
            this.markDirty();
        }
    }
}
