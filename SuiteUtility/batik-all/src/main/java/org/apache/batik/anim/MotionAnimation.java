// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim;

import java.awt.geom.Point2D;
import org.apache.batik.ext.awt.geom.Cubic;
import org.apache.batik.dom.anim.AnimationTarget;
import org.apache.batik.ext.awt.geom.ExtendedPathIterator;
import java.awt.Shape;
import org.apache.batik.anim.values.AnimatableMotionPointValue;
import org.apache.batik.anim.values.AnimatableAngleValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimatableElement;
import org.apache.batik.anim.timing.TimedElement;
import org.apache.batik.ext.awt.geom.PathLength;
import org.apache.batik.ext.awt.geom.ExtendedGeneralPath;

public class MotionAnimation extends InterpolatingAnimation
{
    protected ExtendedGeneralPath path;
    protected PathLength pathLength;
    protected float[] keyPoints;
    protected boolean rotateAuto;
    protected boolean rotateAutoReverse;
    protected float rotateAngle;
    
    public MotionAnimation(final TimedElement timedElement, final AnimatableElement animatableElement, final int n, final float[] array, final float[] array2, final boolean b, final boolean b2, final AnimatableValue[] array3, final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final AnimatableValue animatableValue3, ExtendedGeneralPath path, float[] keyPoints, final boolean rotateAuto, final boolean rotateAutoReverse, final float n2, final short n3) {
        super(timedElement, animatableElement, n, array, array2, b, b2);
        this.rotateAuto = rotateAuto;
        this.rotateAutoReverse = rotateAutoReverse;
        this.rotateAngle = AnimatableAngleValue.rad(n2, n3);
        if (path == null) {
            path = new ExtendedGeneralPath();
            if (array3 == null || array3.length == 0) {
                if (animatableValue != null) {
                    final AnimatableMotionPointValue animatableMotionPointValue = (AnimatableMotionPointValue)animatableValue;
                    final float x = animatableMotionPointValue.getX();
                    final float y = animatableMotionPointValue.getY();
                    path.moveTo(x, y);
                    if (animatableValue2 != null) {
                        final AnimatableMotionPointValue animatableMotionPointValue2 = (AnimatableMotionPointValue)animatableValue2;
                        path.lineTo(animatableMotionPointValue2.getX(), animatableMotionPointValue2.getY());
                    }
                    else {
                        if (animatableValue3 == null) {
                            throw timedElement.createException("values.to.by.path.missing", new Object[] { null });
                        }
                        final AnimatableMotionPointValue animatableMotionPointValue3 = (AnimatableMotionPointValue)animatableValue3;
                        path.lineTo(x + animatableMotionPointValue3.getX(), y + animatableMotionPointValue3.getY());
                    }
                }
                else if (animatableValue2 != null) {
                    final AnimatableMotionPointValue animatableMotionPointValue4 = (AnimatableMotionPointValue)animatableElement.getUnderlyingValue();
                    final AnimatableMotionPointValue animatableMotionPointValue5 = (AnimatableMotionPointValue)animatableValue2;
                    path.moveTo(animatableMotionPointValue4.getX(), animatableMotionPointValue4.getY());
                    path.lineTo(animatableMotionPointValue5.getX(), animatableMotionPointValue5.getY());
                    this.cumulative = false;
                }
                else {
                    if (animatableValue3 == null) {
                        throw timedElement.createException("values.to.by.path.missing", new Object[] { null });
                    }
                    final AnimatableMotionPointValue animatableMotionPointValue6 = (AnimatableMotionPointValue)animatableValue3;
                    path.moveTo(0.0f, 0.0f);
                    path.lineTo(animatableMotionPointValue6.getX(), animatableMotionPointValue6.getY());
                    this.additive = true;
                }
            }
            else {
                final AnimatableMotionPointValue animatableMotionPointValue7 = (AnimatableMotionPointValue)array3[0];
                path.moveTo(animatableMotionPointValue7.getX(), animatableMotionPointValue7.getY());
                for (int i = 1; i < array3.length; ++i) {
                    final AnimatableMotionPointValue animatableMotionPointValue8 = (AnimatableMotionPointValue)array3[i];
                    path.lineTo(animatableMotionPointValue8.getX(), animatableMotionPointValue8.getY());
                }
            }
        }
        this.path = path;
        this.pathLength = new PathLength(path);
        int n4 = 0;
        final ExtendedPathIterator extendedPathIterator = path.getExtendedPathIterator();
        while (!extendedPathIterator.isDone()) {
            if (extendedPathIterator.currentSegment() != 0) {
                ++n4;
            }
            extendedPathIterator.next();
        }
        final int n5 = (keyPoints == null) ? (n4 + 1) : keyPoints.length;
        final float lengthOfPath = this.pathLength.lengthOfPath();
        if (this.keyTimes != null && n != 2) {
            if (this.keyTimes.length != n5) {
                throw timedElement.createException("attribute.malformed", new Object[] { null, "keyTimes" });
            }
        }
        else if (n == 1 || n == 3) {
            this.keyTimes = new float[n5];
            for (int j = 0; j < n5; ++j) {
                this.keyTimes[j] = j / (float)(n5 - 1);
            }
        }
        else if (n == 0) {
            this.keyTimes = new float[n5];
            for (int k = 0; k < n5; ++k) {
                this.keyTimes[k] = k / (float)n5;
            }
        }
        else {
            final ExtendedPathIterator extendedPathIterator2 = path.getExtendedPathIterator();
            this.keyTimes = new float[n5];
            int n6 = 0;
            for (int l = 0; l < n5 - 1; ++l) {
                while (extendedPathIterator2.currentSegment() == 0) {
                    ++n6;
                    extendedPathIterator2.next();
                }
                this.keyTimes[l] = this.pathLength.getLengthAtSegment(n6) / lengthOfPath;
                ++n6;
                extendedPathIterator2.next();
            }
            this.keyTimes[n5 - 1] = 1.0f;
        }
        if (keyPoints != null) {
            if (keyPoints.length != this.keyTimes.length) {
                throw timedElement.createException("attribute.malformed", new Object[] { null, "keyPoints" });
            }
        }
        else {
            final ExtendedPathIterator extendedPathIterator3 = path.getExtendedPathIterator();
            keyPoints = new float[n5];
            int n7 = 0;
            for (int n8 = 0; n8 < n5 - 1; ++n8) {
                while (extendedPathIterator3.currentSegment() == 0) {
                    ++n7;
                    extendedPathIterator3.next();
                }
                keyPoints[n8] = this.pathLength.getLengthAtSegment(n7) / lengthOfPath;
                ++n7;
                extendedPathIterator3.next();
            }
            keyPoints[n5 - 1] = 1.0f;
        }
        this.keyPoints = keyPoints;
    }
    
    protected void sampledAtUnitTime(final float n, final int n2) {
        float n3 = 0.0f;
        AnimatableMotionPointValue animatableMotionPointValue;
        if (n != 1.0f) {
            int n4;
            for (n4 = 0; n4 < this.keyTimes.length - 1 && n >= this.keyTimes[n4 + 1]; ++n4) {}
            if (n4 == this.keyTimes.length - 1 && this.calcMode == 0) {
                n4 = this.keyTimes.length - 2;
                n3 = 1.0f;
            }
            else if (this.calcMode == 1 || this.calcMode == 2 || this.calcMode == 3) {
                if (n == 0.0f) {
                    n3 = 0.0f;
                }
                else {
                    n3 = (n - this.keyTimes[n4]) / (this.keyTimes[n4 + 1] - this.keyTimes[n4]);
                }
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
            float n9 = this.keyPoints[n4];
            if (n3 != 0.0f) {
                n9 += n3 * (this.keyPoints[n4 + 1] - this.keyPoints[n4]);
            }
            final float n10 = n9 * this.pathLength.lengthOfPath();
            final Point2D pointAtLength = this.pathLength.pointAtLength(n10);
            float n11;
            if (this.rotateAuto) {
                n11 = this.pathLength.angleAtLength(n10);
                if (this.rotateAutoReverse) {
                    n11 += (float)3.141592653589793;
                }
            }
            else {
                n11 = this.rotateAngle;
            }
            animatableMotionPointValue = new AnimatableMotionPointValue(null, (float)pointAtLength.getX(), (float)pointAtLength.getY(), n11);
        }
        else {
            final Point2D pointAtLength2 = this.pathLength.pointAtLength(this.pathLength.lengthOfPath());
            float n12;
            if (this.rotateAuto) {
                n12 = this.pathLength.angleAtLength(this.pathLength.lengthOfPath());
                if (this.rotateAutoReverse) {
                    n12 += (float)3.141592653589793;
                }
            }
            else {
                n12 = this.rotateAngle;
            }
            animatableMotionPointValue = new AnimatableMotionPointValue(null, (float)pointAtLength2.getX(), (float)pointAtLength2.getY(), n12);
        }
        AnimatableValue animatableValue;
        if (this.cumulative) {
            final Point2D pointAtLength3 = this.pathLength.pointAtLength(this.pathLength.lengthOfPath());
            float n13;
            if (this.rotateAuto) {
                n13 = this.pathLength.angleAtLength(this.pathLength.lengthOfPath());
                if (this.rotateAutoReverse) {
                    n13 += (float)3.141592653589793;
                }
            }
            else {
                n13 = this.rotateAngle;
            }
            animatableValue = new AnimatableMotionPointValue(null, (float)pointAtLength3.getX(), (float)pointAtLength3.getY(), n13);
        }
        else {
            animatableValue = null;
        }
        this.value = animatableMotionPointValue.interpolate(this.value, null, n3, animatableValue, n2);
        if (this.value.hasChanged()) {
            this.markDirty();
        }
    }
}
