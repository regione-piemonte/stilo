// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.events.EventTarget;
import java.util.Calendar;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGElement;
import org.apache.batik.dom.anim.AnimationTargetListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.apache.batik.css.engine.CSSEngineEvent;
import org.w3c.dom.events.MutationEvent;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.dom.svg.SVGContext;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;
import org.apache.batik.anim.AbstractAnimation;
import org.apache.batik.anim.timing.TimedElement;
import org.apache.batik.dom.svg.SVGOMElement;
import org.apache.batik.dom.anim.AnimatableElement;
import org.apache.batik.dom.svg.SVGAnimationContext;

public abstract class SVGAnimationElementBridge extends AbstractSVGBridge implements GenericBridge, BridgeUpdateHandler, SVGAnimationContext, AnimatableElement
{
    protected SVGOMElement element;
    protected BridgeContext ctx;
    protected SVGAnimationEngine eng;
    protected TimedElement timedElement;
    protected AbstractAnimation animation;
    protected String attributeNamespaceURI;
    protected String attributeLocalName;
    protected short animationType;
    protected SVGOMElement targetElement;
    protected AnimationTarget animationTarget;
    
    public TimedElement getTimedElement() {
        return this.timedElement;
    }
    
    public AnimatableValue getUnderlyingValue() {
        if (this.animationType == 0) {
            return this.animationTarget.getUnderlyingValue(this.attributeNamespaceURI, this.attributeLocalName);
        }
        return this.eng.getUnderlyingCSSValue(this.element, this.animationTarget, this.attributeLocalName);
    }
    
    public void handleElement(final BridgeContext ctx, final Element element) {
        if (ctx.isDynamic() && BridgeContext.getSVGContext(element) == null) {
            final SVGAnimationElementBridge svgContext = (SVGAnimationElementBridge)this.getInstance();
            svgContext.element = (SVGOMElement)element;
            svgContext.ctx = ctx;
            svgContext.eng = ctx.getAnimationEngine();
            svgContext.element.setSVGContext(svgContext);
            if (svgContext.eng.hasStarted()) {
                svgContext.initializeAnimation();
                svgContext.initializeTimedElement();
            }
            else {
                svgContext.eng.addInitialBridge(svgContext);
            }
        }
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
        final String attributeNS = this.element.getAttributeNS(null, "attributeName");
        final int index = attributeNS.indexOf(58);
        if (index == -1) {
            if (this.element.hasProperty(attributeNS)) {
                this.animationType = 1;
                this.attributeLocalName = attributeNS;
            }
            else {
                this.animationType = 0;
                this.attributeLocalName = attributeNS;
            }
        }
        else {
            this.animationType = 0;
            this.attributeNamespaceURI = this.element.lookupNamespaceURI(attributeNS.substring(0, index));
            this.attributeLocalName = attributeNS.substring(index + 1);
        }
        if ((this.animationType == 1 && !this.targetElement.isPropertyAnimatable(this.attributeLocalName)) || (this.animationType == 0 && !this.targetElement.isAttributeAnimatable(this.attributeNamespaceURI, this.attributeLocalName))) {
            throw new BridgeException(this.ctx, this.element, "attribute.not.animatable", new Object[] { this.targetElement.getNodeName(), attributeNS });
        }
        int n;
        if (this.animationType == 1) {
            n = this.targetElement.getPropertyType(this.attributeLocalName);
        }
        else {
            n = this.targetElement.getAttributeType(this.attributeNamespaceURI, this.attributeLocalName);
        }
        if (!this.canAnimateType(n)) {
            throw new BridgeException(this.ctx, this.element, "type.not.animatable", new Object[] { this.targetElement.getNodeName(), attributeNS, this.element.getNodeName() });
        }
        this.timedElement = this.createTimedElement();
        this.animation = this.createAnimation(this.animationTarget);
        this.eng.addAnimation(this.animationTarget, this.animationType, this.attributeNamespaceURI, this.attributeLocalName, this.animation);
    }
    
    protected abstract boolean canAnimateType(final int p0);
    
    protected boolean checkValueType(final AnimatableValue animatableValue) {
        return true;
    }
    
    protected void initializeTimedElement() {
        this.initializeTimedElement(this.timedElement);
        this.timedElement.initialize();
    }
    
    protected TimedElement createTimedElement() {
        return new SVGTimedElement();
    }
    
    protected abstract AbstractAnimation createAnimation(final AnimationTarget p0);
    
    protected AnimatableValue parseAnimatableValue(final String s) {
        if (!this.element.hasAttributeNS(null, s)) {
            return null;
        }
        final String attributeNS = this.element.getAttributeNS(null, s);
        final AnimatableValue animatableValue = this.eng.parseAnimatableValue(this.element, this.animationTarget, this.attributeNamespaceURI, this.attributeLocalName, this.animationType == 1, attributeNS);
        if (!this.checkValueType(animatableValue)) {
            throw new BridgeException(this.ctx, this.element, "attribute.malformed", new Object[] { s, attributeNS });
        }
        return animatableValue;
    }
    
    protected void initializeTimedElement(final TimedElement timedElement) {
        timedElement.parseAttributes(this.element.getAttributeNS(null, "begin"), this.element.getAttributeNS(null, "dur"), this.element.getAttributeNS(null, "end"), this.element.getAttributeNS(null, "min"), this.element.getAttributeNS(null, "max"), this.element.getAttributeNS(null, "repeatCount"), this.element.getAttributeNS(null, "repeatDur"), this.element.getAttributeNS(null, "fill"), this.element.getAttributeNS(null, "restart"));
    }
    
    public void handleDOMAttrModifiedEvent(final MutationEvent mutationEvent) {
    }
    
    public void handleDOMNodeInsertedEvent(final MutationEvent mutationEvent) {
    }
    
    public void handleDOMNodeRemovedEvent(final MutationEvent mutationEvent) {
        this.element.setSVGContext(null);
        this.dispose();
    }
    
    public void handleDOMCharacterDataModified(final MutationEvent mutationEvent) {
    }
    
    public void handleCSSEngineEvent(final CSSEngineEvent cssEngineEvent) {
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
    }
    
    public void handleOtherAnimationChanged(final String s) {
    }
    
    public void dispose() {
        if (this.element.getSVGContext() == null) {
            this.eng.removeAnimation(this.animation);
            this.timedElement.deinitialize();
            this.timedElement = null;
            this.element = null;
        }
    }
    
    public float getPixelUnitToMillimeter() {
        return this.ctx.getUserAgent().getPixelUnitToMillimeter();
    }
    
    public float getPixelToMM() {
        return this.getPixelUnitToMillimeter();
    }
    
    public Rectangle2D getBBox() {
        return null;
    }
    
    public AffineTransform getScreenTransform() {
        return this.ctx.getUserAgent().getTransform();
    }
    
    public void setScreenTransform(final AffineTransform transform) {
        this.ctx.getUserAgent().setTransform(transform);
    }
    
    public AffineTransform getCTM() {
        return null;
    }
    
    public AffineTransform getGlobalTransform() {
        return null;
    }
    
    public float getViewportWidth() {
        return this.ctx.getBlockWidth(this.element);
    }
    
    public float getViewportHeight() {
        return this.ctx.getBlockHeight(this.element);
    }
    
    public float getFontSize() {
        return 0.0f;
    }
    
    public float svgToUserSpace(final float n, final int n2, final int n3) {
        return 0.0f;
    }
    
    public void addTargetListener(final String s, final AnimationTargetListener animationTargetListener) {
    }
    
    public void removeTargetListener(final String s, final AnimationTargetListener animationTargetListener) {
    }
    
    public SVGElement getTargetElement() {
        return (SVGElement)this.targetElement;
    }
    
    public float getStartTime() {
        return this.timedElement.getCurrentBeginTime();
    }
    
    public float getCurrentTime() {
        return this.timedElement.getLastSampleTime();
    }
    
    public float getSimpleDuration() {
        return this.timedElement.getSimpleDur();
    }
    
    public float getHyperlinkBeginTime() {
        return this.timedElement.getHyperlinkBeginTime();
    }
    
    public boolean beginElement() throws DOMException {
        this.timedElement.beginElement();
        return this.timedElement.canBegin();
    }
    
    public boolean beginElementAt(final float n) throws DOMException {
        this.timedElement.beginElement(n);
        return true;
    }
    
    public boolean endElement() throws DOMException {
        this.timedElement.endElement();
        return this.timedElement.canEnd();
    }
    
    public boolean endElementAt(final float n) throws DOMException {
        this.timedElement.endElement(n);
        return true;
    }
    
    protected boolean isConstantAnimation() {
        return false;
    }
    
    protected class SVGTimedElement extends TimedElement
    {
        public Element getElement() {
            return SVGAnimationElementBridge.this.element;
        }
        
        protected void fireTimeEvent(final String s, final Calendar calendar, final int n) {
            AnimationSupport.fireTimeEvent(SVGAnimationElementBridge.this.element, s, calendar, n);
        }
        
        protected void toActive(final float n) {
            SVGAnimationElementBridge.this.eng.toActive(SVGAnimationElementBridge.this.animation, n);
        }
        
        protected void toInactive(final boolean b, final boolean b2) {
            SVGAnimationElementBridge.this.eng.toInactive(SVGAnimationElementBridge.this.animation, b2);
        }
        
        protected void removeFill() {
            SVGAnimationElementBridge.this.eng.removeFill(SVGAnimationElementBridge.this.animation);
        }
        
        protected void sampledAt(final float n, final float n2, final int n3) {
            SVGAnimationElementBridge.this.eng.sampledAt(SVGAnimationElementBridge.this.animation, n, n2, n3);
        }
        
        protected void sampledLastValue(final int n) {
            SVGAnimationElementBridge.this.eng.sampledLastValue(SVGAnimationElementBridge.this.animation, n);
        }
        
        protected TimedElement getTimedElementById(final String s) {
            return AnimationSupport.getTimedElementById(s, SVGAnimationElementBridge.this.element);
        }
        
        protected EventTarget getEventTargetById(final String s) {
            return AnimationSupport.getEventTargetById(s, SVGAnimationElementBridge.this.element);
        }
        
        protected EventTarget getRootEventTarget() {
            return (EventTarget)SVGAnimationElementBridge.this.element.getOwnerDocument();
        }
        
        protected EventTarget getAnimationEventTarget() {
            return SVGAnimationElementBridge.this.targetElement;
        }
        
        public boolean isBefore(final TimedElement timedElement) {
            return (SVGAnimationElementBridge.this.element.compareDocumentPosition(((SVGTimedElement)timedElement).getElement()) & 0x2) != 0x0;
        }
        
        public String toString() {
            if (SVGAnimationElementBridge.this.element != null) {
                final String attributeNS = SVGAnimationElementBridge.this.element.getAttributeNS(null, "id");
                if (attributeNS.length() != 0) {
                    return attributeNS;
                }
            }
            return super.toString();
        }
        
        protected boolean isConstantAnimation() {
            return SVGAnimationElementBridge.this.isConstantAnimation();
        }
    }
}
