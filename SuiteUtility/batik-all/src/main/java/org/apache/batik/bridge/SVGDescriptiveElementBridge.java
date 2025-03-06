// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.apache.batik.css.engine.CSSEngineEvent;
import org.w3c.dom.events.MutationEvent;
import org.apache.batik.dom.svg.SVGOMElement;
import org.w3c.dom.Element;
import org.apache.batik.dom.svg.SVGContext;

public abstract class SVGDescriptiveElementBridge extends AbstractSVGBridge implements GenericBridge, BridgeUpdateHandler, SVGContext
{
    Element theElt;
    Element parent;
    BridgeContext theCtx;
    
    public void handleElement(final BridgeContext theCtx, final Element theElt) {
        theCtx.getUserAgent().handleElement(theElt, Boolean.TRUE);
        if (theCtx.isDynamic()) {
            final SVGDescriptiveElementBridge svgContext = (SVGDescriptiveElementBridge)this.getInstance();
            svgContext.theElt = theElt;
            svgContext.parent = (Element)theElt.getParentNode();
            svgContext.theCtx = theCtx;
            ((SVGOMElement)theElt).setSVGContext(svgContext);
        }
    }
    
    public void dispose() {
        final UserAgent userAgent = this.theCtx.getUserAgent();
        ((SVGOMElement)this.theElt).setSVGContext(null);
        userAgent.handleElement(this.theElt, this.parent);
        this.theElt = null;
        this.parent = null;
    }
    
    public void handleDOMNodeInsertedEvent(final MutationEvent mutationEvent) {
        this.theCtx.getUserAgent().handleElement(this.theElt, Boolean.TRUE);
    }
    
    public void handleDOMCharacterDataModified(final MutationEvent mutationEvent) {
        this.theCtx.getUserAgent().handleElement(this.theElt, Boolean.TRUE);
    }
    
    public void handleDOMNodeRemovedEvent(final MutationEvent mutationEvent) {
        this.dispose();
    }
    
    public void handleDOMAttrModifiedEvent(final MutationEvent mutationEvent) {
    }
    
    public void handleCSSEngineEvent(final CSSEngineEvent cssEngineEvent) {
    }
    
    public void handleAnimatedAttributeChanged(final AnimatedLiveAttributeValue animatedLiveAttributeValue) {
    }
    
    public void handleOtherAnimationChanged(final String s) {
    }
    
    public float getPixelUnitToMillimeter() {
        return this.theCtx.getUserAgent().getPixelUnitToMillimeter();
    }
    
    public float getPixelToMM() {
        return this.getPixelUnitToMillimeter();
    }
    
    public Rectangle2D getBBox() {
        return null;
    }
    
    public AffineTransform getScreenTransform() {
        return this.theCtx.getUserAgent().getTransform();
    }
    
    public void setScreenTransform(final AffineTransform transform) {
        this.theCtx.getUserAgent().setTransform(transform);
    }
    
    public AffineTransform getCTM() {
        return null;
    }
    
    public AffineTransform getGlobalTransform() {
        return null;
    }
    
    public float getViewportWidth() {
        return this.theCtx.getBlockWidth(this.theElt);
    }
    
    public float getViewportHeight() {
        return this.theCtx.getBlockHeight(this.theElt);
    }
    
    public float getFontSize() {
        return 0.0f;
    }
}
