// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.dom.svg.AnimatedLiveAttributeValue;
import org.apache.batik.css.engine.CSSEngineEvent;
import org.w3c.dom.events.MutationEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import org.apache.batik.dom.svg.SVGOMElement;
import org.w3c.dom.Element;
import org.apache.batik.dom.svg.SVGContext;

public abstract class AnimatableGenericSVGBridge extends AnimatableSVGBridge implements GenericBridge, BridgeUpdateHandler, SVGContext
{
    public void handleElement(final BridgeContext ctx, final Element e) {
        if (ctx.isDynamic()) {
            this.e = e;
            this.ctx = ctx;
            ((SVGOMElement)e).setSVGContext(this);
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
        return 0.0f;
    }
    
    public float getViewportHeight() {
        return 0.0f;
    }
    
    public float getFontSize() {
        return 0.0f;
    }
    
    public void dispose() {
        ((SVGOMElement)this.e).setSVGContext(null);
    }
    
    public void handleDOMNodeInsertedEvent(final MutationEvent mutationEvent) {
    }
    
    public void handleDOMCharacterDataModified(final MutationEvent mutationEvent) {
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
}
