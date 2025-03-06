// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.ArrayList;
import org.apache.batik.anim.values.AnimatableValue;
import org.w3c.dom.Element;
import org.apache.batik.dom.anim.AnimatableElement;
import org.apache.batik.anim.SimpleAnimation;
import org.apache.batik.anim.AbstractAnimation;
import org.apache.batik.dom.anim.AnimationTarget;

public class SVGAnimateElementBridge extends SVGAnimationElementBridge
{
    public String getLocalName() {
        return "animate";
    }
    
    public Bridge getInstance() {
        return new SVGAnimateElementBridge();
    }
    
    protected AbstractAnimation createAnimation(final AnimationTarget animationTarget) {
        return new SimpleAnimation(this.timedElement, this, this.parseCalcMode(), this.parseKeyTimes(), this.parseKeySplines(), this.parseAdditive(), this.parseAccumulate(), this.parseValues(), this.parseAnimatableValue("from"), this.parseAnimatableValue("to"), this.parseAnimatableValue("by"));
    }
    
    protected int parseCalcMode() {
        if ((this.animationType == 1 && !this.targetElement.isPropertyAdditive(this.attributeLocalName)) || (this.animationType == 0 && !this.targetElement.isAttributeAdditive(this.attributeNamespaceURI, this.attributeLocalName))) {
            return 0;
        }
        final String attributeNS = this.element.getAttributeNS(null, "calcMode");
        if (attributeNS.length() == 0) {
            return this.getDefaultCalcMode();
        }
        if (attributeNS.equals("linear")) {
            return 1;
        }
        if (attributeNS.equals("discrete")) {
            return 0;
        }
        if (attributeNS.equals("paced")) {
            return 2;
        }
        if (attributeNS.equals("spline")) {
            return 3;
        }
        throw new BridgeException(this.ctx, this.element, "attribute.malformed", new Object[] { "calcMode", attributeNS });
    }
    
    protected boolean parseAdditive() {
        final String attributeNS = this.element.getAttributeNS(null, "additive");
        if (attributeNS.length() == 0 || attributeNS.equals("replace")) {
            return false;
        }
        if (attributeNS.equals("sum")) {
            return true;
        }
        throw new BridgeException(this.ctx, this.element, "attribute.malformed", new Object[] { "additive", attributeNS });
    }
    
    protected boolean parseAccumulate() {
        final String attributeNS = this.element.getAttributeNS(null, "accumulate");
        if (attributeNS.length() == 0 || attributeNS.equals("none")) {
            return false;
        }
        if (attributeNS.equals("sum")) {
            return true;
        }
        throw new BridgeException(this.ctx, this.element, "attribute.malformed", new Object[] { "accumulate", attributeNS });
    }
    
    protected AnimatableValue[] parseValues() {
        final boolean b = this.animationType == 1;
        final String attributeNS = this.element.getAttributeNS(null, "values");
        final int length = attributeNS.length();
        if (length == 0) {
            return null;
        }
        final ArrayList list = new ArrayList<AnimatableValue>(7);
        int i = 0;
    Label_0225:
        while (i < length) {
            while (attributeNS.charAt(i) == ' ') {
                if (++i == length) {
                    break Label_0225;
                }
            }
            final int beginIndex = i++;
            if (i != length) {
                for (char c = attributeNS.charAt(i); c != ';'; c = attributeNS.charAt(i)) {
                    if (++i == length) {
                        break;
                    }
                }
            }
            final AnimatableValue animatableValue = this.eng.parseAnimatableValue(this.element, this.animationTarget, this.attributeNamespaceURI, this.attributeLocalName, b, attributeNS.substring(beginIndex, i++));
            if (!this.checkValueType(animatableValue)) {
                throw new BridgeException(this.ctx, this.element, "attribute.malformed", new Object[] { "values", attributeNS });
            }
            list.add(animatableValue);
        }
        return list.toArray(new AnimatableValue[list.size()]);
    }
    
    protected float[] parseKeyTimes() {
        final String attributeNS = this.element.getAttributeNS(null, "keyTimes");
        final int length = attributeNS.length();
        if (length == 0) {
            return null;
        }
        final ArrayList list = new ArrayList<Float>(7);
        int i = 0;
    Label_0197:
        while (i < length) {
            while (attributeNS.charAt(i) == ' ') {
                if (++i == length) {
                    break Label_0197;
                }
            }
            final int beginIndex = i++;
            if (i != length) {
                for (char c = attributeNS.charAt(i); c != ' ' && c != ';'; c = attributeNS.charAt(i)) {
                    if (++i == length) {
                        break;
                    }
                }
            }
            final int endIndex = i++;
            try {
                list.add(new Float(Float.parseFloat(attributeNS.substring(beginIndex, endIndex))));
                continue;
            }
            catch (NumberFormatException ex) {
                throw new BridgeException(this.ctx, this.element, ex, "attribute.malformed", new Object[] { "keyTimes", attributeNS });
            }
            break;
        }
        final int size = list.size();
        final float[] array = new float[size];
        for (int j = 0; j < size; ++j) {
            array[j] = list.get(j);
        }
        return array;
    }
    
    protected float[] parseKeySplines() {
        final String attributeNS = this.element.getAttributeNS(null, "keySplines");
        final int length = attributeNS.length();
        if (length == 0) {
            return null;
        }
        final ArrayList<Object> list = new ArrayList<Object>(7);
        int n = 0;
        int i = 0;
    Label_0323:
        while (i < length) {
            while (attributeNS.charAt(i) == ' ') {
                if (++i == length) {
                    break Label_0323;
                }
            }
            final int beginIndex = i++;
            int endIndex;
            if (i != length) {
                char c;
                for (c = attributeNS.charAt(i); c != ' ' && c != ',' && c != ';' && ++i != length; c = attributeNS.charAt(i)) {}
                endIndex = i++;
                Label_0193: {
                    if (c == ' ') {
                        while (true) {
                            while (i != length) {
                                c = attributeNS.charAt(i++);
                                if (c != ' ') {
                                    if (c != ';' && c != ',') {
                                        --i;
                                    }
                                    break Label_0193;
                                }
                            }
                            continue;
                        }
                    }
                }
                if (c == ';') {
                    if (n != 3) {
                        throw new BridgeException(this.ctx, this.element, "attribute.malformed", new Object[] { "keySplines", attributeNS });
                    }
                    n = 0;
                }
                else {
                    ++n;
                }
            }
            else {
                endIndex = i++;
            }
            try {
                list.add(new Float(Float.parseFloat(attributeNS.substring(beginIndex, endIndex))));
                continue;
            }
            catch (NumberFormatException ex) {
                throw new BridgeException(this.ctx, this.element, ex, "attribute.malformed", new Object[] { "keySplines", attributeNS });
            }
            break;
        }
        final int size = list.size();
        final float[] array = new float[size];
        for (int j = 0; j < size; ++j) {
            array[j] = list.get(j);
        }
        return array;
    }
    
    protected int getDefaultCalcMode() {
        return 1;
    }
    
    protected boolean canAnimateType(final int n) {
        return true;
    }
}
