// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.values;

import java.util.Iterator;
import org.w3c.dom.svg.SVGMatrix;
import java.util.Collection;
import java.util.List;
import org.apache.batik.dom.svg.AbstractSVGTransform;
import org.apache.batik.dom.anim.AnimationTarget;
import java.util.Vector;
import org.apache.batik.dom.svg.SVGOMTransform;

public class AnimatableTransformListValue extends AnimatableValue
{
    protected static SVGOMTransform IDENTITY_SKEWX;
    protected static SVGOMTransform IDENTITY_SKEWY;
    protected static SVGOMTransform IDENTITY_SCALE;
    protected static SVGOMTransform IDENTITY_ROTATE;
    protected static SVGOMTransform IDENTITY_TRANSLATE;
    protected Vector transforms;
    
    protected AnimatableTransformListValue(final AnimationTarget animationTarget) {
        super(animationTarget);
    }
    
    public AnimatableTransformListValue(final AnimationTarget animationTarget, final AbstractSVGTransform e) {
        super(animationTarget);
        (this.transforms = new Vector()).add(e);
    }
    
    public AnimatableTransformListValue(final AnimationTarget animationTarget, final List c) {
        super(animationTarget);
        this.transforms = new Vector(c);
    }
    
    public AnimatableValue interpolate(final AnimatableValue animatableValue, final AnimatableValue animatableValue2, final float n, final AnimatableValue animatableValue3, final int n2) {
        final AnimatableTransformListValue animatableTransformListValue = (AnimatableTransformListValue)animatableValue2;
        final AnimatableTransformListValue animatableTransformListValue2 = (AnimatableTransformListValue)animatableValue3;
        final int n3 = (animatableValue3 == null) ? 0 : animatableTransformListValue2.transforms.size();
        final int size = this.transforms.size() + n3 * n2;
        AnimatableTransformListValue animatableTransformListValue3;
        if (animatableValue == null) {
            animatableTransformListValue3 = new AnimatableTransformListValue(this.target);
            (animatableTransformListValue3.transforms = new Vector(size)).setSize(size);
        }
        else {
            animatableTransformListValue3 = (AnimatableTransformListValue)animatableValue;
            if (animatableTransformListValue3.transforms == null) {
                (animatableTransformListValue3.transforms = new Vector(size)).setSize(size);
            }
            else if (animatableTransformListValue3.transforms.size() != size) {
                animatableTransformListValue3.transforms.setSize(size);
            }
        }
        int n4 = 0;
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n3; ++j, ++n4) {
                animatableTransformListValue3.transforms.setElementAt(animatableTransformListValue2.transforms.elementAt(j), n4);
            }
        }
        for (int k = 0; k < this.transforms.size() - 1; ++k, ++n4) {
            animatableTransformListValue3.transforms.setElementAt(this.transforms.elementAt(k), n4);
        }
        if (animatableValue2 != null) {
            final AbstractSVGTransform abstractSVGTransform = animatableTransformListValue.transforms.lastElement();
            AbstractSVGTransform abstractSVGTransform2 = null;
            short n5;
            if (this.transforms.isEmpty()) {
                n5 = abstractSVGTransform.getType();
                switch (n5) {
                    case 5: {
                        abstractSVGTransform2 = AnimatableTransformListValue.IDENTITY_SKEWX;
                        break;
                    }
                    case 6: {
                        abstractSVGTransform2 = AnimatableTransformListValue.IDENTITY_SKEWY;
                        break;
                    }
                    case 3: {
                        abstractSVGTransform2 = AnimatableTransformListValue.IDENTITY_SCALE;
                        break;
                    }
                    case 4: {
                        abstractSVGTransform2 = AnimatableTransformListValue.IDENTITY_ROTATE;
                        break;
                    }
                    case 2: {
                        abstractSVGTransform2 = AnimatableTransformListValue.IDENTITY_TRANSLATE;
                        break;
                    }
                }
            }
            else {
                abstractSVGTransform2 = this.transforms.lastElement();
                n5 = abstractSVGTransform2.getType();
            }
            if (n5 == abstractSVGTransform.getType()) {
                AbstractSVGTransform abstractSVGTransform3;
                if (animatableTransformListValue3.transforms.isEmpty()) {
                    abstractSVGTransform3 = new SVGOMTransform();
                    animatableTransformListValue3.transforms.add(abstractSVGTransform3);
                }
                else {
                    abstractSVGTransform3 = animatableTransformListValue3.transforms.elementAt(n4);
                    if (abstractSVGTransform3 == null) {
                        abstractSVGTransform3 = new SVGOMTransform();
                        animatableTransformListValue3.transforms.setElementAt(abstractSVGTransform3, n4);
                    }
                }
                switch (n5) {
                    case 5:
                    case 6: {
                        final float angle = abstractSVGTransform2.getAngle();
                        final float n6 = angle + n * (abstractSVGTransform.getAngle() - angle);
                        if (n5 == 5) {
                            abstractSVGTransform3.setSkewX(n6);
                            break;
                        }
                        if (n5 == 6) {
                            abstractSVGTransform3.setSkewY(n6);
                            break;
                        }
                        break;
                    }
                    case 3: {
                        final SVGMatrix matrix = abstractSVGTransform2.getMatrix();
                        final SVGMatrix matrix2 = abstractSVGTransform.getMatrix();
                        final float a = matrix.getA();
                        final float d = matrix.getD();
                        abstractSVGTransform3.setScale(a + n * (matrix2.getA() - a), d + n * (matrix2.getD() - d));
                        break;
                    }
                    case 4: {
                        final float x = abstractSVGTransform2.getX();
                        final float y = abstractSVGTransform2.getY();
                        final float n7 = x + n * (abstractSVGTransform.getX() - x);
                        final float n8 = y + n * (abstractSVGTransform.getY() - y);
                        final float angle2 = abstractSVGTransform2.getAngle();
                        abstractSVGTransform3.setRotate(angle2 + n * (abstractSVGTransform.getAngle() - angle2), n7, n8);
                        break;
                    }
                    case 2: {
                        final SVGMatrix matrix3 = abstractSVGTransform2.getMatrix();
                        final SVGMatrix matrix4 = abstractSVGTransform.getMatrix();
                        final float e = matrix3.getE();
                        final float f = matrix3.getF();
                        abstractSVGTransform3.setTranslate(e + n * (matrix4.getE() - e), f + n * (matrix4.getF() - f));
                        break;
                    }
                }
            }
        }
        else {
            final AbstractSVGTransform abstractSVGTransform4 = this.transforms.lastElement();
            AbstractSVGTransform obj = animatableTransformListValue3.transforms.elementAt(n4);
            if (obj == null) {
                obj = new SVGOMTransform();
                animatableTransformListValue3.transforms.setElementAt(obj, n4);
            }
            obj.assign(abstractSVGTransform4);
        }
        animatableTransformListValue3.hasChanged = true;
        return animatableTransformListValue3;
    }
    
    public static AnimatableTransformListValue interpolate(AnimatableTransformListValue animatableTransformListValue, final AnimatableTransformListValue animatableTransformListValue2, final AnimatableTransformListValue animatableTransformListValue3, final AnimatableTransformListValue animatableTransformListValue4, final AnimatableTransformListValue animatableTransformListValue5, final float n, final float n2, final AnimatableTransformListValue animatableTransformListValue6, final int n3) {
        final int n4 = (animatableTransformListValue6 == null) ? 0 : animatableTransformListValue6.transforms.size();
        final int size = n4 * n3 + 1;
        if (animatableTransformListValue == null) {
            animatableTransformListValue = new AnimatableTransformListValue(animatableTransformListValue4.target);
            (animatableTransformListValue.transforms = new Vector(size)).setSize(size);
        }
        else if (animatableTransformListValue.transforms == null) {
            (animatableTransformListValue.transforms = new Vector(size)).setSize(size);
        }
        else if (animatableTransformListValue.transforms.size() != size) {
            animatableTransformListValue.transforms.setSize(size);
        }
        int index = 0;
        for (int i = 0; i < n3; ++i) {
            for (int j = 0; j < n4; ++j, ++index) {
                animatableTransformListValue.transforms.setElementAt(animatableTransformListValue6.transforms.elementAt(j), index);
            }
        }
        final AbstractSVGTransform abstractSVGTransform = animatableTransformListValue2.transforms.lastElement();
        final AbstractSVGTransform abstractSVGTransform2 = animatableTransformListValue3.transforms.lastElement();
        AbstractSVGTransform obj = animatableTransformListValue.transforms.elementAt(index);
        if (obj == null) {
            obj = new SVGOMTransform();
            animatableTransformListValue.transforms.setElementAt(obj, index);
        }
        final short type = abstractSVGTransform.getType();
        float n5;
        float n6;
        if (type == 3) {
            n5 = abstractSVGTransform.getMatrix().getA();
            n6 = abstractSVGTransform2.getMatrix().getD();
        }
        else {
            n5 = abstractSVGTransform.getMatrix().getE();
            n6 = abstractSVGTransform2.getMatrix().getF();
        }
        if (animatableTransformListValue4 != null) {
            final AbstractSVGTransform abstractSVGTransform3 = animatableTransformListValue4.transforms.lastElement();
            final AbstractSVGTransform abstractSVGTransform4 = animatableTransformListValue5.transforms.lastElement();
            if (type == 3) {
                n5 += n * (abstractSVGTransform3.getMatrix().getA() - n5);
                n6 += n2 * (abstractSVGTransform4.getMatrix().getD() - n6);
            }
            else {
                n5 += n * (abstractSVGTransform3.getMatrix().getE() - n5);
                n6 += n2 * (abstractSVGTransform4.getMatrix().getF() - n6);
            }
        }
        if (type == 3) {
            obj.setScale(n5, n6);
        }
        else {
            obj.setTranslate(n5, n6);
        }
        animatableTransformListValue.hasChanged = true;
        return animatableTransformListValue;
    }
    
    public static AnimatableTransformListValue interpolate(AnimatableTransformListValue animatableTransformListValue, final AnimatableTransformListValue animatableTransformListValue2, final AnimatableTransformListValue animatableTransformListValue3, final AnimatableTransformListValue animatableTransformListValue4, final AnimatableTransformListValue animatableTransformListValue5, final AnimatableTransformListValue animatableTransformListValue6, final AnimatableTransformListValue animatableTransformListValue7, final float n, final float n2, final float n3, final AnimatableTransformListValue animatableTransformListValue8, final int n4) {
        final int n5 = (animatableTransformListValue8 == null) ? 0 : animatableTransformListValue8.transforms.size();
        final int size = n5 * n4 + 1;
        if (animatableTransformListValue == null) {
            animatableTransformListValue = new AnimatableTransformListValue(animatableTransformListValue5.target);
            (animatableTransformListValue.transforms = new Vector(size)).setSize(size);
        }
        else if (animatableTransformListValue.transforms == null) {
            (animatableTransformListValue.transforms = new Vector(size)).setSize(size);
        }
        else if (animatableTransformListValue.transforms.size() != size) {
            animatableTransformListValue.transforms.setSize(size);
        }
        int index = 0;
        for (int i = 0; i < n4; ++i) {
            for (int j = 0; j < n5; ++j, ++index) {
                animatableTransformListValue.transforms.setElementAt(animatableTransformListValue8.transforms.elementAt(j), index);
            }
        }
        final AbstractSVGTransform abstractSVGTransform = animatableTransformListValue2.transforms.lastElement();
        final AbstractSVGTransform abstractSVGTransform2 = animatableTransformListValue3.transforms.lastElement();
        final AbstractSVGTransform abstractSVGTransform3 = animatableTransformListValue4.transforms.lastElement();
        AbstractSVGTransform obj = animatableTransformListValue.transforms.elementAt(index);
        if (obj == null) {
            obj = new SVGOMTransform();
            animatableTransformListValue.transforms.setElementAt(obj, index);
        }
        float angle = abstractSVGTransform.getAngle();
        float x = abstractSVGTransform2.getX();
        float y = abstractSVGTransform3.getY();
        if (animatableTransformListValue5 != null) {
            final AbstractSVGTransform abstractSVGTransform4 = animatableTransformListValue5.transforms.lastElement();
            final AbstractSVGTransform abstractSVGTransform5 = animatableTransformListValue6.transforms.lastElement();
            final AbstractSVGTransform abstractSVGTransform6 = animatableTransformListValue7.transforms.lastElement();
            angle += n * (abstractSVGTransform4.getAngle() - angle);
            x += n2 * (abstractSVGTransform5.getX() - x);
            y += n3 * (abstractSVGTransform6.getY() - y);
        }
        obj.setRotate(angle, x, y);
        animatableTransformListValue.hasChanged = true;
        return animatableTransformListValue;
    }
    
    public Iterator getTransforms() {
        return this.transforms.iterator();
    }
    
    public boolean canPace() {
        return true;
    }
    
    public float distanceTo(final AnimatableValue animatableValue) {
        final AnimatableTransformListValue animatableTransformListValue = (AnimatableTransformListValue)animatableValue;
        if (this.transforms.isEmpty() || animatableTransformListValue.transforms.isEmpty()) {
            return 0.0f;
        }
        final AbstractSVGTransform abstractSVGTransform = this.transforms.lastElement();
        final AbstractSVGTransform abstractSVGTransform2 = animatableTransformListValue.transforms.lastElement();
        final short type = abstractSVGTransform.getType();
        if (type != abstractSVGTransform2.getType()) {
            return 0.0f;
        }
        final SVGMatrix matrix = abstractSVGTransform.getMatrix();
        final SVGMatrix matrix2 = abstractSVGTransform2.getMatrix();
        switch (type) {
            case 2: {
                return Math.abs(matrix.getE() - matrix2.getE()) + Math.abs(matrix.getF() - matrix2.getF());
            }
            case 3: {
                return Math.abs(matrix.getA() - matrix2.getA()) + Math.abs(matrix.getD() - matrix2.getD());
            }
            case 4:
            case 5:
            case 6: {
                return Math.abs(abstractSVGTransform.getAngle() - abstractSVGTransform2.getAngle());
            }
            default: {
                return 0.0f;
            }
        }
    }
    
    public float distanceTo1(final AnimatableValue animatableValue) {
        final AnimatableTransformListValue animatableTransformListValue = (AnimatableTransformListValue)animatableValue;
        if (this.transforms.isEmpty() || animatableTransformListValue.transforms.isEmpty()) {
            return 0.0f;
        }
        final AbstractSVGTransform abstractSVGTransform = this.transforms.lastElement();
        final AbstractSVGTransform abstractSVGTransform2 = animatableTransformListValue.transforms.lastElement();
        final short type = abstractSVGTransform.getType();
        if (type != abstractSVGTransform2.getType()) {
            return 0.0f;
        }
        final SVGMatrix matrix = abstractSVGTransform.getMatrix();
        final SVGMatrix matrix2 = abstractSVGTransform2.getMatrix();
        switch (type) {
            case 2: {
                return Math.abs(matrix.getE() - matrix2.getE());
            }
            case 3: {
                return Math.abs(matrix.getA() - matrix2.getA());
            }
            case 4:
            case 5:
            case 6: {
                return Math.abs(abstractSVGTransform.getAngle() - abstractSVGTransform2.getAngle());
            }
            default: {
                return 0.0f;
            }
        }
    }
    
    public float distanceTo2(final AnimatableValue animatableValue) {
        final AnimatableTransformListValue animatableTransformListValue = (AnimatableTransformListValue)animatableValue;
        if (this.transforms.isEmpty() || animatableTransformListValue.transforms.isEmpty()) {
            return 0.0f;
        }
        final AbstractSVGTransform abstractSVGTransform = this.transforms.lastElement();
        final AbstractSVGTransform abstractSVGTransform2 = animatableTransformListValue.transforms.lastElement();
        final short type = abstractSVGTransform.getType();
        if (type != abstractSVGTransform2.getType()) {
            return 0.0f;
        }
        final SVGMatrix matrix = abstractSVGTransform.getMatrix();
        final SVGMatrix matrix2 = abstractSVGTransform2.getMatrix();
        switch (type) {
            case 2: {
                return Math.abs(matrix.getF() - matrix2.getF());
            }
            case 3: {
                return Math.abs(matrix.getD() - matrix2.getD());
            }
            case 4: {
                return Math.abs(abstractSVGTransform.getX() - abstractSVGTransform2.getX());
            }
            default: {
                return 0.0f;
            }
        }
    }
    
    public float distanceTo3(final AnimatableValue animatableValue) {
        final AnimatableTransformListValue animatableTransformListValue = (AnimatableTransformListValue)animatableValue;
        if (this.transforms.isEmpty() || animatableTransformListValue.transforms.isEmpty()) {
            return 0.0f;
        }
        final AbstractSVGTransform abstractSVGTransform = this.transforms.lastElement();
        final AbstractSVGTransform abstractSVGTransform2 = animatableTransformListValue.transforms.lastElement();
        final short type = abstractSVGTransform.getType();
        if (type != abstractSVGTransform2.getType()) {
            return 0.0f;
        }
        if (type == 4) {
            return Math.abs(abstractSVGTransform.getY() - abstractSVGTransform2.getY());
        }
        return 0.0f;
    }
    
    public AnimatableValue getZeroValue() {
        return new AnimatableTransformListValue(this.target, new Vector(5));
    }
    
    public String toStringRep() {
        final StringBuffer sb = new StringBuffer();
        final Iterator<AbstractSVGTransform> iterator = (Iterator<AbstractSVGTransform>)this.transforms.iterator();
        while (iterator.hasNext()) {
            final AbstractSVGTransform abstractSVGTransform = iterator.next();
            if (abstractSVGTransform == null) {
                sb.append("null");
            }
            else {
                final SVGMatrix matrix = abstractSVGTransform.getMatrix();
                switch (abstractSVGTransform.getType()) {
                    case 2: {
                        sb.append("translate(");
                        sb.append(matrix.getE());
                        sb.append(',');
                        sb.append(matrix.getF());
                        sb.append(')');
                        break;
                    }
                    case 3: {
                        sb.append("scale(");
                        sb.append(matrix.getA());
                        sb.append(',');
                        sb.append(matrix.getD());
                        sb.append(')');
                        break;
                    }
                    case 5: {
                        sb.append("skewX(");
                        sb.append(abstractSVGTransform.getAngle());
                        sb.append(')');
                        break;
                    }
                    case 6: {
                        sb.append("skewY(");
                        sb.append(abstractSVGTransform.getAngle());
                        sb.append(')');
                        break;
                    }
                    case 4: {
                        sb.append("rotate(");
                        sb.append(abstractSVGTransform.getAngle());
                        sb.append(',');
                        sb.append(abstractSVGTransform.getX());
                        sb.append(',');
                        sb.append(abstractSVGTransform.getY());
                        sb.append(')');
                        break;
                    }
                }
            }
            if (iterator.hasNext()) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }
    
    static {
        AnimatableTransformListValue.IDENTITY_SKEWX = new SVGOMTransform();
        AnimatableTransformListValue.IDENTITY_SKEWY = new SVGOMTransform();
        AnimatableTransformListValue.IDENTITY_SCALE = new SVGOMTransform();
        AnimatableTransformListValue.IDENTITY_ROTATE = new SVGOMTransform();
        AnimatableTransformListValue.IDENTITY_TRANSLATE = new SVGOMTransform();
        AnimatableTransformListValue.IDENTITY_SKEWX.setSkewX(0.0f);
        AnimatableTransformListValue.IDENTITY_SKEWY.setSkewY(0.0f);
        AnimatableTransformListValue.IDENTITY_SCALE.setScale(0.0f, 0.0f);
        AnimatableTransformListValue.IDENTITY_ROTATE.setRotate(0.0f, 0.0f, 0.0f);
        AnimatableTransformListValue.IDENTITY_TRANSLATE.setTranslate(0.0f, 0.0f);
    }
}
