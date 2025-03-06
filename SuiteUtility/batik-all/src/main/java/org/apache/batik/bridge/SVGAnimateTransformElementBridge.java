// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.ArrayList;
import org.apache.batik.dom.svg.AbstractSVGTransform;
import org.apache.batik.anim.values.AnimatableTransformListValue;
import org.apache.batik.dom.svg.SVGOMTransform;
import org.w3c.dom.Element;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimatableElement;
import org.apache.batik.anim.TransformAnimation;
import org.apache.batik.anim.AbstractAnimation;
import org.apache.batik.dom.anim.AnimationTarget;

public class SVGAnimateTransformElementBridge extends SVGAnimateElementBridge
{
    public String getLocalName() {
        return "animateTransform";
    }
    
    public Bridge getInstance() {
        return new SVGAnimateTransformElementBridge();
    }
    
    protected AbstractAnimation createAnimation(final AnimationTarget animationTarget) {
        final short type = this.parseType();
        AnimatableValue value = null;
        AnimatableValue value2 = null;
        AnimatableValue value3 = null;
        if (this.element.hasAttributeNS(null, "from")) {
            value = this.parseValue(this.element.getAttributeNS(null, "from"), type, animationTarget);
        }
        if (this.element.hasAttributeNS(null, "to")) {
            value2 = this.parseValue(this.element.getAttributeNS(null, "to"), type, animationTarget);
        }
        if (this.element.hasAttributeNS(null, "by")) {
            value3 = this.parseValue(this.element.getAttributeNS(null, "by"), type, animationTarget);
        }
        return new TransformAnimation(this.timedElement, this, this.parseCalcMode(), this.parseKeyTimes(), this.parseKeySplines(), this.parseAdditive(), this.parseAccumulate(), this.parseValues(type, animationTarget), value, value2, value3, type);
    }
    
    protected short parseType() {
        final String attributeNS = this.element.getAttributeNS(null, "type");
        if (attributeNS.equals("translate")) {
            return 2;
        }
        if (attributeNS.equals("scale")) {
            return 3;
        }
        if (attributeNS.equals("rotate")) {
            return 4;
        }
        if (attributeNS.equals("skewX")) {
            return 5;
        }
        if (attributeNS.equals("skewY")) {
            return 6;
        }
        throw new BridgeException(this.ctx, this.element, "attribute.malformed", new Object[] { "type", attributeNS });
    }
    
    protected AnimatableValue parseValue(final String s, final short n, final AnimationTarget animationTarget) {
        float float1 = 0.0f;
        float float2 = 0.0f;
        int i = 0;
        int n2 = 44;
        int length;
        for (length = s.length(); i < length; ++i) {
            n2 = s.charAt(i);
            if (n2 == 32) {
                break;
            }
            if (n2 == 44) {
                break;
            }
        }
        final float float3 = Float.parseFloat(s.substring(0, i));
        if (i < length) {
            ++i;
        }
        int n3 = 1;
        if (i < length && n2 == 32) {
            while (i < length) {
                n2 = s.charAt(i);
                if (n2 != 32) {
                    break;
                }
                ++i;
            }
            if (n2 == 44) {
                ++i;
            }
        }
        while (i < length && s.charAt(i) == ' ') {
            ++i;
        }
        final int beginIndex;
        if ((beginIndex = i) < length && n != 5 && n != 6) {
            while (i < length) {
                n2 = s.charAt(i);
                if (n2 == 32) {
                    break;
                }
                if (n2 == 44) {
                    break;
                }
                ++i;
            }
            float1 = Float.parseFloat(s.substring(beginIndex, i));
            if (i < length) {
                ++i;
            }
            ++n3;
            if (i < length && n2 == 32) {
                while (i < length) {
                    n2 = s.charAt(i);
                    if (n2 != 32) {
                        break;
                    }
                    ++i;
                }
                if (n2 == 44) {
                    ++i;
                }
            }
            while (i < length && s.charAt(i) == ' ') {
                ++i;
            }
            final int beginIndex2;
            if ((beginIndex2 = i) < length && n == 4) {
                while (i < length) {
                    final char char1 = s.charAt(i);
                    if (char1 == ',') {
                        break;
                    }
                    if (char1 == ' ') {
                        break;
                    }
                    ++i;
                }
                float2 = Float.parseFloat(s.substring(beginIndex2, i));
                if (i < length) {
                    ++i;
                }
                ++n3;
                while (i < length && s.charAt(i) == ' ') {
                    ++i;
                }
            }
        }
        if (i != length) {
            return null;
        }
        final SVGOMTransform svgomTransform = new SVGOMTransform();
        switch (n) {
            case 2: {
                if (n3 == 2) {
                    svgomTransform.setTranslate(float3, float1);
                    break;
                }
                svgomTransform.setTranslate(float3, 0.0f);
                break;
            }
            case 3: {
                if (n3 == 2) {
                    svgomTransform.setScale(float3, float1);
                    break;
                }
                svgomTransform.setScale(float3, float3);
                break;
            }
            case 4: {
                if (n3 == 3) {
                    svgomTransform.setRotate(float3, float1, float2);
                    break;
                }
                svgomTransform.setRotate(float3, 0.0f, 0.0f);
                break;
            }
            case 5: {
                svgomTransform.setSkewX(float3);
                break;
            }
            case 6: {
                svgomTransform.setSkewY(float3);
                break;
            }
        }
        return new AnimatableTransformListValue(animationTarget, svgomTransform);
    }
    
    protected AnimatableValue[] parseValues(final short n, final AnimationTarget animationTarget) {
        final String attributeNS = this.element.getAttributeNS(null, "values");
        final int length = attributeNS.length();
        if (length == 0) {
            return null;
        }
        final ArrayList list = new ArrayList<AnimatableValue>(7);
        int i = 0;
    Label_0199:
        while (i < length) {
            while (attributeNS.charAt(i) == ' ') {
                if (++i == length) {
                    break Label_0199;
                }
            }
            final int beginIndex = i++;
            if (i < length) {
                for (char c = attributeNS.charAt(i); c != ';'; c = attributeNS.charAt(i)) {
                    if (++i == length) {
                        break;
                    }
                }
            }
            final AnimatableValue value = this.parseValue(attributeNS.substring(beginIndex, i++), n, animationTarget);
            if (value == null) {
                throw new BridgeException(this.ctx, this.element, "attribute.malformed", new Object[] { "values", attributeNS });
            }
            list.add(value);
        }
        return list.toArray(new AnimatableValue[list.size()]);
    }
    
    protected boolean canAnimateType(final int n) {
        return n == 9;
    }
}
