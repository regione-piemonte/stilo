// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim;

import org.apache.batik.dom.anim.AnimatableElement;
import org.apache.batik.anim.timing.TimedElement;
import org.apache.batik.ext.awt.geom.Cubic;

public abstract class InterpolatingAnimation extends AbstractAnimation
{
    protected int calcMode;
    protected float[] keyTimes;
    protected float[] keySplines;
    protected Cubic[] keySplineCubics;
    protected boolean additive;
    protected boolean cumulative;
    
    public InterpolatingAnimation(final TimedElement timedElement, final AnimatableElement animatableElement, final int calcMode, final float[] keyTimes, final float[] keySplines, final boolean additive, final boolean cumulative) {
        super(timedElement, animatableElement);
        this.calcMode = calcMode;
        this.keyTimes = keyTimes;
        this.keySplines = keySplines;
        this.additive = additive;
        this.cumulative = cumulative;
        if (calcMode == 3) {
            if (keySplines == null || keySplines.length % 4 != 0) {
                throw timedElement.createException("attribute.malformed", new Object[] { null, "keySplines" });
            }
            this.keySplineCubics = new Cubic[keySplines.length / 4];
            for (int i = 0; i < keySplines.length / 4; ++i) {
                this.keySplineCubics[i] = new Cubic(0.0, 0.0, keySplines[i * 4], keySplines[i * 4 + 1], keySplines[i * 4 + 2], keySplines[i * 4 + 3], 1.0, 1.0);
            }
        }
        if (keyTimes != null) {
            int n = 0;
            if (((calcMode == 1 || calcMode == 3 || calcMode == 2) && (keyTimes.length < 2 || keyTimes[0] != 0.0f || keyTimes[keyTimes.length - 1] != 1.0f)) || (calcMode == 0 && (keyTimes.length == 0 || keyTimes[0] != 0.0f))) {
                n = 1;
            }
            if (n == 0) {
                for (int j = 1; j < keyTimes.length; ++j) {
                    if (keyTimes[j] < 0.0f || keyTimes[1] > 1.0f || keyTimes[j] < keyTimes[j - 1]) {
                        n = 1;
                        break;
                    }
                }
            }
            if (n != 0) {
                throw timedElement.createException("attribute.malformed", new Object[] { null, "keyTimes" });
            }
        }
    }
    
    protected boolean willReplace() {
        return !this.additive;
    }
    
    protected void sampledLastValue(final int n) {
        this.sampledAtUnitTime(1.0f, n);
    }
    
    protected void sampledAt(final float n, final float n2, final int n3) {
        float n4;
        if (n2 == Float.POSITIVE_INFINITY) {
            n4 = 0.0f;
        }
        else {
            n4 = n / n2;
        }
        this.sampledAtUnitTime(n4, n3);
    }
    
    protected abstract void sampledAtUnitTime(final float p0, final int p1);
}
