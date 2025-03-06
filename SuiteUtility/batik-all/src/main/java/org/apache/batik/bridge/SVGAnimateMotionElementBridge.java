// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.dom.svg.SVGOMElement;
import org.apache.batik.anim.values.AnimatableMotionPointValue;
import org.apache.batik.parser.LengthListHandler;
import org.apache.batik.parser.LengthArrayProducer;
import org.apache.batik.parser.LengthPairListParser;
import java.util.ArrayList;
import org.w3c.dom.Node;
import org.apache.batik.parser.PathParser;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.dom.svg.SVGAnimatedPathDataSupport;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.dom.svg.SVGOMPathElement;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.ext.awt.geom.ExtendedGeneralPath;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimatableElement;
import org.apache.batik.anim.MotionAnimation;
import org.apache.batik.parser.ParseException;
import org.w3c.dom.Element;
import org.apache.batik.parser.AngleHandler;
import org.apache.batik.parser.AngleParser;
import org.apache.batik.anim.AbstractAnimation;
import org.apache.batik.dom.anim.AnimationTarget;

public class SVGAnimateMotionElementBridge extends SVGAnimateElementBridge
{
    public String getLocalName() {
        return "animateMotion";
    }
    
    public Bridge getInstance() {
        return new SVGAnimateMotionElementBridge();
    }
    
    protected AbstractAnimation createAnimation(final AnimationTarget animationTarget) {
        this.animationType = 2;
        this.attributeLocalName = "motion";
        final AnimatableValue lengthPair = this.parseLengthPair("from");
        final AnimatableValue lengthPair2 = this.parseLengthPair("to");
        final AnimatableValue lengthPair3 = this.parseLengthPair("by");
        boolean b = false;
        boolean b2 = false;
        float theAngle = 0.0f;
        short theUnit = 0;
        final String attributeNS = this.element.getAttributeNS(null, "rotate");
        if (attributeNS.length() != 0) {
            if (attributeNS.equals("auto")) {
                b = true;
            }
            else if (attributeNS.equals("auto-reverse")) {
                b = true;
                b2 = true;
            }
            else {
                final AngleParser angleParser = new AngleParser();
                final Handler angleHandler = new Handler();
                angleParser.setAngleHandler(angleHandler);
                try {
                    angleParser.parse(attributeNS);
                }
                catch (ParseException ex) {
                    throw new BridgeException(this.ctx, this.element, ex, "attribute.malformed", new Object[] { "rotate", attributeNS });
                }
                theAngle = angleHandler.theAngle;
                theUnit = angleHandler.theUnit;
            }
        }
        return new MotionAnimation(this.timedElement, this, this.parseCalcMode(), this.parseKeyTimes(), this.parseKeySplines(), this.parseAdditive(), this.parseAccumulate(), this.parseValues(), lengthPair, lengthPair2, lengthPair3, this.parsePath(), this.parseKeyPoints(), b, b2, theAngle, theUnit);
    }
    
    protected ExtendedGeneralPath parsePath() {
        Node node = this.element.getFirstChild();
        while (node != null) {
            if (node.getNodeType() == 1 && "http://www.w3.org/2000/svg".equals(node.getNamespaceURI()) && "mpath".equals(node.getLocalName())) {
                final String xLinkHref = XLinkSupport.getXLinkHref((Element)node);
                final Element referencedElement = this.ctx.getReferencedElement(this.element, xLinkHref);
                if (!"http://www.w3.org/2000/svg".equals(referencedElement.getNamespaceURI()) || !"path".equals(referencedElement.getLocalName())) {
                    throw new BridgeException(this.ctx, this.element, "uri.badTarget", new Object[] { xLinkHref });
                }
                final SVGOMPathElement svgomPathElement = (SVGOMPathElement)referencedElement;
                final AWTPathProducer awtPathProducer = new AWTPathProducer();
                SVGAnimatedPathDataSupport.handlePathSegList(svgomPathElement.getPathSegList(), awtPathProducer);
                return (ExtendedGeneralPath)awtPathProducer.getShape();
            }
            else {
                node = node.getNextSibling();
            }
        }
        final String attributeNS = this.element.getAttributeNS(null, "path");
        if (attributeNS.length() == 0) {
            return null;
        }
        try {
            final AWTPathProducer pathHandler = new AWTPathProducer();
            final PathParser pathParser = new PathParser();
            pathParser.setPathHandler(pathHandler);
            pathParser.parse(attributeNS);
            return (ExtendedGeneralPath)pathHandler.getShape();
        }
        catch (ParseException ex) {
            throw new BridgeException(this.ctx, this.element, ex, "attribute.malformed", new Object[] { "path", attributeNS });
        }
    }
    
    protected float[] parseKeyPoints() {
        final String attributeNS = this.element.getAttributeNS(null, "keyPoints");
        final int length = attributeNS.length();
        if (length == 0) {
            return null;
        }
        final ArrayList list = new ArrayList<Object>(7);
        int i = 0;
    Label_0206:
        while (i < length) {
            while (attributeNS.charAt(i) == ' ') {
                if (++i == length) {
                    break Label_0206;
                }
            }
            final int beginIndex = i++;
            if (i != length) {
                for (char c = attributeNS.charAt(i); c != ' ' && c != ';' && c != ','; c = attributeNS.charAt(i)) {
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
                throw new BridgeException(this.ctx, this.element, ex, "attribute.malformed", new Object[] { "keyPoints", attributeNS });
            }
            break;
        }
        final int size = list.size();
        final float[] array = new float[size];
        for (int j = 0; j < size; ++j) {
            array[j] = (float)list.get(j);
        }
        return array;
    }
    
    protected int getDefaultCalcMode() {
        return 2;
    }
    
    protected AnimatableValue[] parseValues() {
        final String attributeNS = this.element.getAttributeNS(null, "values");
        if (attributeNS.length() == 0) {
            return null;
        }
        return this.parseValues(attributeNS);
    }
    
    protected AnimatableValue[] parseValues(final String s) {
        try {
            final LengthPairListParser lengthPairListParser = new LengthPairListParser();
            final LengthArrayProducer lengthListHandler = new LengthArrayProducer();
            lengthPairListParser.setLengthListHandler(lengthListHandler);
            lengthPairListParser.parse(s);
            final short[] lengthTypeArray = lengthListHandler.getLengthTypeArray();
            final float[] lengthValueArray = lengthListHandler.getLengthValueArray();
            final AnimatableValue[] array = new AnimatableValue[lengthTypeArray.length / 2];
            for (int i = 0; i < lengthTypeArray.length; i += 2) {
                array[i / 2] = new AnimatableMotionPointValue(this.animationTarget, this.animationTarget.svgToUserSpace(lengthValueArray[i], lengthTypeArray[i], (short)1), this.animationTarget.svgToUserSpace(lengthValueArray[i + 1], lengthTypeArray[i + 1], (short)2), 0.0f);
            }
            return array;
        }
        catch (ParseException ex) {
            throw new BridgeException(this.ctx, this.element, ex, "attribute.malformed", new Object[] { "values", s });
        }
    }
    
    protected AnimatableValue parseLengthPair(final String s) {
        final String attributeNS = this.element.getAttributeNS(null, s);
        if (attributeNS.length() == 0) {
            return null;
        }
        return this.parseValues(attributeNS)[0];
    }
    
    public AnimatableValue getUnderlyingValue() {
        return new AnimatableMotionPointValue(this.animationTarget, 0.0f, 0.0f, 0.0f);
    }
    
    protected void initializeAnimation() {
        final String xLinkHref = XLinkSupport.getXLinkHref(this.element);
        Node node;
        if (xLinkHref.length() == 0) {
            node = this.element.getParentNode();
        }
        else {
            node = this.ctx.getReferencedElement(this.element, xLinkHref);
            if (node.getOwnerDocument() != this.element.getOwnerDocument()) {
                throw new BridgeException(this.ctx, this.element, "uri.badTarget", new Object[] { xLinkHref });
            }
        }
        this.animationTarget = null;
        if (node instanceof SVGOMElement) {
            this.targetElement = (SVGOMElement)node;
            this.animationTarget = this.targetElement;
        }
        if (this.animationTarget == null) {
            throw new BridgeException(this.ctx, this.element, "uri.badTarget", new Object[] { xLinkHref });
        }
        this.timedElement = this.createTimedElement();
        this.animation = this.createAnimation(this.animationTarget);
        this.eng.addAnimation(this.animationTarget, (short)2, this.attributeNamespaceURI, this.attributeLocalName, this.animation);
    }
    
    class Handler implements AngleHandler
    {
        float theAngle;
        short theUnit;
        
        Handler() {
            this.theUnit = 1;
        }
        
        public void startAngle() throws ParseException {
        }
        
        public void angleValue(final float theAngle) throws ParseException {
            this.theAngle = theAngle;
        }
        
        public void deg() throws ParseException {
            this.theUnit = 2;
        }
        
        public void grad() throws ParseException {
            this.theUnit = 4;
        }
        
        public void rad() throws ParseException {
            this.theUnit = 3;
        }
        
        public void endAngle() throws ParseException {
        }
    }
}
