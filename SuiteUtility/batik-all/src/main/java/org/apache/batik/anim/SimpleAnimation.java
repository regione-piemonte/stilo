// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim;

import java.awt.geom.Point2D;
import org.apache.batik.ext.awt.geom.Cubic;
import org.apache.batik.dom.anim.AnimatableElement;
import org.apache.batik.anim.timing.TimedElement;
import org.apache.batik.anim.values.AnimatableValue;

public class SimpleAnimation extends InterpolatingAnimation
{
    protected AnimatableValue[] values;
    protected AnimatableValue from;
    protected AnimatableValue to;
    protected AnimatableValue by;
    
    public SimpleAnimation(final TimedElement timedElement, final AnimatableElement animatableElement, final int n, final float[] array, final float[] array2, final boolean b, final boolean b2, AnimatableValue[] values, final AnimatableValue from, final AnimatableValue to, final AnimatableValue by) {
        super(timedElement, animatableElement, n, array, array2, b, b2);
        this.from = from;
        this.to = to;
        this.by = by;
        if (values == null) {
            if (from != null) {
                values = new AnimatableValue[] { from, null };
                if (to != null) {
                    values[1] = to;
                }
                else {
                    if (by == null) {
                        throw timedElement.createException("values.to.by.missing", new Object[] { null });
                    }
                    values[1] = from.interpolate(null, null, 0.0f, by, 1);
                }
            }
            else if (to != null) {
                values = new AnimatableValue[] { animatableElement.getUnderlyingValue(), to };
                this.cumulative = false;
                this.toAnimation = true;
            }
            else {
                if (by == null) {
                    throw timedElement.createException("values.to.by.missing", new Object[] { null });
                }
                this.additive = true;
                values = new AnimatableValue[] { by.getZeroValue(), by };
            }
        }
        this.values = values;
        if (this.keyTimes != null && n != 2) {
            if (this.keyTimes.length != values.length) {
                throw timedElement.createException("attribute.malformed", new Object[] { null, "keyTimes" });
            }
        }
        else if (n == 1 || n == 3 || (n == 2 && !values[0].canPace())) {
            final int n2 = (values.length == 1) ? 2 : values.length;
            this.keyTimes = new float[n2];
            for (int i = 0; i < n2; ++i) {
                this.keyTimes[i] = i / (float)(n2 - 1);
            }
        }
        else if (n == 0) {
            final int length = values.length;
            this.keyTimes = new float[length];
            for (int j = 0; j < length; ++j) {
                this.keyTimes[j] = j / (float)length;
            }
        }
        else {
            final int length2 = values.length;
            final float[] array3 = new float[length2];
            array3[0] = 0.0f;
            for (int k = 1; k < length2; ++k) {
                array3[k] = array3[k - 1] + values[k - 1].distanceTo(values[k]);
            }
            final float n3 = array3[length2 - 1];
            (this.keyTimes = new float[length2])[0] = 0.0f;
            for (int l = 1; l < length2 - 1; ++l) {
                this.keyTimes[l] = array3[l] / n3;
            }
            this.keyTimes[length2 - 1] = 1.0f;
        }
        if (n == 3 && array2.length != (this.keyTimes.length - 1) * 4) {
            throw timedElement.createException("attribute.malformed", new Object[] { null, "keySplines" });
        }
    }
    
    protected void sampledAtUnitTime(final float n, final int n2) {
        float n3 = 0.0f;
        AnimatableValue animatableValue;
        AnimatableValue animatableValue2;
        if (n != 1.0f) {
            int n4;
            for (n4 = 0; n4 < this.keyTimes.length - 1 && n >= this.keyTimes[n4 + 1]; ++n4) {}
            animatableValue = this.values[n4];
            if (this.calcMode == 1 || this.calcMode == 2 || this.calcMode == 3) {
                animatableValue2 = this.values[n4 + 1];
                n3 = (n - this.keyTimes[n4]) / (this.keyTimes[n4 + 1] - this.keyTimes[n4]);
                if (this.calcMode == 3 && n != 0.0f) {
                    final Cubic cubic = this.keySplineCubics[n4];
                    final float n5 = 0.001f;
                    float n6 = 0.0f;
                    float n7 = 1.0f;
                    Point2D.Double eval;
                    while (true) {
                        final float n8 = (n6 + n7) / 2.0f;
                        eval = cubic.eval(n8);
                        final double x = eval.getX();
                        if (Math.abs(x - n3) < n5) {
                            break;
                        }
                        if (x < n3) {
                            n6 = n8;
                        }
                        else {
                            n7 = n8;
                        }
                    }
                    n3 = (float)eval.getY();
                }
            }
            else {
                animatableValue2 = null;
            }
        }
        else {
            animatableValue = this.values[this.values.length - 1];
            animatableValue2 = null;
        }
        AnimatableValue animatableValue3;
        if (this.cumulative) {
            animatableValue3 = this.values[this.values.length - 1];
        }
        else {
            animatableValue3 = null;
        }
        this.value = animatableValue.interpolate(this.value, animatableValue2, n3, animatableValue3, n2);
        if (this.value.hasChanged()) {
            this.markDirty();
        }
    }
}
