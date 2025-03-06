// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim;

import org.apache.batik.anim.values.AnimatableTransformListValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimatableElement;
import org.apache.batik.anim.timing.TimedElement;

public class TransformAnimation extends SimpleAnimation
{
    protected short type;
    protected float[] keyTimes2;
    protected float[] keyTimes3;
    
    public TransformAnimation(final TimedElement timedElement, final AnimatableElement animatableElement, final int calcMode, final float[] array, final float[] array2, final boolean b, final boolean b2, final AnimatableValue[] array3, final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final AnimatableValue animatableValue3, final short type) {
        super(timedElement, animatableElement, (calcMode == 2) ? 1 : calcMode, (float[])((calcMode == 2) ? null : array), array2, b, b2, array3, animatableValue, animatableValue2, animatableValue3);
        this.calcMode = calcMode;
        this.type = type;
        if (calcMode != 2) {
            return;
        }
        final int length = this.values.length;
        float[] array4 = null;
        float[] array5 = null;
        switch (type) {
            case 4: {
                array5 = new float[length];
                array5[0] = 0.0f;
            }
            case 2:
            case 3: {
                array4 = new float[length];
                array4[0] = 0.0f;
                break;
            }
        }
        final float[] array6 = new float[length];
        array6[0] = 0.0f;
        for (int i = 1; i < this.values.length; ++i) {
            switch (type) {
                case 4: {
                    array5[i] = array5[i - 1] + ((AnimatableTransformListValue)this.values[i - 1]).distanceTo3(this.values[i]);
                }
                case 2:
                case 3: {
                    array4[i] = array4[i - 1] + ((AnimatableTransformListValue)this.values[i - 1]).distanceTo2(this.values[i]);
                    break;
                }
            }
            array6[i] = array6[i - 1] + ((AnimatableTransformListValue)this.values[i - 1]).distanceTo1(this.values[i]);
        }
        switch (type) {
            case 4: {
                final float n = array5[length - 1];
                (this.keyTimes3 = new float[length])[0] = 0.0f;
                for (int j = 1; j < length - 1; ++j) {
                    this.keyTimes3[j] = array5[j] / n;
                }
                this.keyTimes3[length - 1] = 1.0f;
            }
            case 2:
            case 3: {
                final float n2 = array4[length - 1];
                (this.keyTimes2 = new float[length])[0] = 0.0f;
                for (int k = 1; k < length - 1; ++k) {
                    this.keyTimes2[k] = array4[k] / n2;
                }
                this.keyTimes2[length - 1] = 1.0f;
                break;
            }
        }
        final float n3 = array6[length - 1];
        (this.keyTimes = new float[length])[0] = 0.0f;
        for (int l = 1; l < length - 1; ++l) {
            this.keyTimes[l] = array6[l] / n3;
        }
        this.keyTimes[length - 1] = 1.0f;
    }
    
    protected void sampledAtUnitTime(final float n, final int n2) {
        if (this.calcMode != 2 || this.type == 5 || this.type == 6) {
            super.sampledAtUnitTime(n, n2);
            return;
        }
        AnimatableTransformListValue animatableTransformListValue = null;
        AnimatableTransformListValue animatableTransformListValue2 = null;
        float n3 = 0.0f;
        AnimatableTransformListValue animatableTransformListValue3;
        AnimatableTransformListValue animatableTransformListValue4;
        float n6;
        AnimatableTransformListValue animatableTransformListValue5;
        AnimatableTransformListValue animatableTransformListValue6;
        float n8;
        if (n != 1.0f) {
            switch (this.type) {
                case 4: {
                    int n4;
                    for (n4 = 0; n4 < this.keyTimes3.length - 1 && n >= this.keyTimes3[n4 + 1]; ++n4) {}
                    animatableTransformListValue = (AnimatableTransformListValue)this.values[n4];
                    animatableTransformListValue2 = (AnimatableTransformListValue)this.values[n4 + 1];
                    n3 = (n - this.keyTimes3[n4]) / (this.keyTimes3[n4 + 1] - this.keyTimes3[n4]);
                    break;
                }
            }
            int n5;
            for (n5 = 0; n5 < this.keyTimes2.length - 1 && n >= this.keyTimes2[n5 + 1]; ++n5) {}
            animatableTransformListValue3 = (AnimatableTransformListValue)this.values[n5];
            animatableTransformListValue4 = (AnimatableTransformListValue)this.values[n5 + 1];
            n6 = (n - this.keyTimes2[n5]) / (this.keyTimes2[n5 + 1] - this.keyTimes2[n5]);
            int n7;
            for (n7 = 0; n7 < this.keyTimes.length - 1 && n >= this.keyTimes[n7 + 1]; ++n7) {}
            animatableTransformListValue5 = (AnimatableTransformListValue)this.values[n7];
            animatableTransformListValue6 = (AnimatableTransformListValue)this.values[n7 + 1];
            n8 = (n - this.keyTimes[n7]) / (this.keyTimes[n7 + 1] - this.keyTimes[n7]);
        }
        else {
            animatableTransformListValue3 = (animatableTransformListValue5 = (animatableTransformListValue = (AnimatableTransformListValue)this.values[this.values.length - 1]));
            animatableTransformListValue4 = (animatableTransformListValue6 = (animatableTransformListValue2 = null));
            n6 = (n8 = (n3 = 1.0f));
        }
        AnimatableTransformListValue animatableTransformListValue7;
        if (this.cumulative) {
            animatableTransformListValue7 = (AnimatableTransformListValue)this.values[this.values.length - 1];
        }
        else {
            animatableTransformListValue7 = null;
        }
        switch (this.type) {
            case 4: {
                this.value = AnimatableTransformListValue.interpolate((AnimatableTransformListValue)this.value, animatableTransformListValue5, animatableTransformListValue3, animatableTransformListValue, animatableTransformListValue6, animatableTransformListValue4, animatableTransformListValue2, n8, n6, n3, animatableTransformListValue7, n2);
                break;
            }
            default: {
                this.value = AnimatableTransformListValue.interpolate((AnimatableTransformListValue)this.value, animatableTransformListValue5, animatableTransformListValue3, animatableTransformListValue6, animatableTransformListValue4, n8, n6, animatableTransformListValue7, n2);
                break;
            }
        }
        if (this.value.hasChanged()) {
            this.markDirty();
        }
    }
}
